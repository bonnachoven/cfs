
/*skandan chockalingam - schockal@andrew.cmu.edu
Java J2EE Programming - Assignment 9
 12/10/2014
*/
package controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.FundDAO;
import model.Fund_Price_HistoryDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;
import databean.Employee;
import databean.Fund;
import databean.Fund_Price_History;
import databean.Position;
import databean.TransactionBean;
import formbean.TransitionDayForm;

/*
 * Processes the parameters from the form in login.jsp.
 * If successful, set the "user" session attribute to the
 * user's User bean and then redirects to view the originally
 * requested photo.  If there was no photo originally requested
 * to be viewed (as specified by the "redirect" hidden form
 * value), just redirect to manage.do to allow the user to manage
 * his photos.
 */
public class TransitionDay extends Action {
	
	/*private FormBeanFactory<TransitionDayForm> formBeanFactory = FormBeanFactory.getInstance(TransitionDayForm.class);
*/
	
	private TransactionDAO transDAO;
	private Fund_Price_HistoryDAO fphisDAO;
	private FundDAO fundDAO;
	private CustomerDAO cusDAO;
	private PositionDAO posDAO;
	
	
	
	public TransitionDay(Model model) {
		//transDAO = model.getTransitionDAO();
		fundDAO=model.getFundDAO();
		fphisDAO=model.getFund_Price_HistoryDAO();
		cusDAO=model.getCustomerDAO();
		posDAO=model.getPositionDAO();
		transDAO=model.getTransactionDAO();
	}

	public String getName() { return "transitionDay.do"; }
    

	
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
       // errors.add("enter the date in yyyy-mm-dd");
        request.setAttribute("errors",errors);
        String button=request.getParameter("action");
         try {
        	TransitionDayForm form = new TransitionDayForm();
        	
        	//check if the admin is logged in to the system ....else prevent from accessing the transition day
        	Employee employee = (Employee) request.getSession(false).getAttribute("employee");
        	if(employee==null)
        		return "login.jsp";
        
      
        	 Fund[] funds=null;
        	 funds = fundDAO.getFunds();
        	 request.setAttribute("funds", funds);
        	 
        	 String s = null;
		     String lasttransitionday=fphisDAO.getLastTransitionDayDate();
		     request.setAttribute("date", lasttransitionday);
        	 if (request.getParameter("action")==null) { return "transitionDay.jsp"; }
		    
		     
		      for(int i=0;i<funds.length;i++)
		     {
		    	 TransitionDayForm tdf=new TransitionDayForm();
		    	 s=request.getParameter("date");
		    	 tdf.setdate(request.getParameter("date"));
		    	 tdf.setfund_id(funds[i].getFund_id());
		    	 if(request.getParameter("price"+i)!=null && !request.getParameter("price"+i).equals(""))
		    	 {	 
		    		 try{
		    		 tdf.setprice((long)Double.parseDouble(request.getParameter("price"+i))*100);}
		    		 catch(NumberFormatException e)
		    		 { errors.add("enter a valid number for price field");
		    		 	return "transitionDay.jsp";
		    		 }
		    		}
		    	 else
		    		 tdf.setprice(0);
		    	 System.out.println("lasttransitionday:"+lasttransitionday);
		    	 errors = tdf.getValidationErrors(lasttransitionday);
		    	 if(errors.size()>0){System.out.println("eroor size:"+errors.size());
		    	 	request.setAttribute("errors", errors);
		    		 return "transitionDay.jsp";
		    	 }
		    	 
		    	
		    	 Fund_Price_History fph=new Fund_Price_History();
		    	 fph.setFund_id(tdf.getfund_id());
		    	 fph.setPrice(tdf.getprice());
		    	 fph.setPrice_date(tdf.getdate());
		    	 try{
		    		 //duplicate primary key values on the fundprice history
		    	 fphisDAO.create(fph);
		    	 }catch(Exception e){
		    		 System.out.println("duplicate");
		    		 errors.add("duplicate date entered, enter a date higher than the preivously ended transition day");
		    		 request.setAttribute("errors", errors);
		    		 return "transitionDay.jsp";
		    		 
		    	 }
		    	 
		     }
		     
		      TransactionBean bean[]=transDAO.getAllPendingTrans();
		      int count;
		      System.out.println("size:"+bean.length);
		      for(TransactionBean b:bean)
		      {
		    	  count=b.getTransaction_type();

		    	  switch(count)
		    	  {
		    	  	//deposit cash on customer account
		    	  	case 1:
		    	  			cusDAO.updateCash(b.getCustomer_id(),b.getAmount(),true);
		    	  			b.setExecute_date(s);
		    	  			transDAO.update(b);
		    	  			break;
		    	  	
		    	  	//request check on customer account
		    	  	case 2:
		    	  			cusDAO.updateCash(b.getCustomer_id(),b.getAmount(),false);
		    	  			b.setExecute_date(s);
		    	  			transDAO.update(b);
		    	  			break;
		    	  	
		    	  	//sell fund
		    	  	case 3:
		    	  		//update the shares on the position table
		    	  			
		    	  				
		    	  			Position p=new Position();
		    	  			p.setCustomer_id(b.getCustomer_id());
		    	  			p.setFund_id(b.getFund_id());
		    	  			long shares =posDAO.getShares(b.getFund_id(), b.getCustomer_id());
		    	  			if(shares!=0)
		    	  			{
		    	  				shares-=b.getShares();
		    	  				p.setShares(shares);
		    	  				posDAO.update(p);
		    	  			}
		    	  			
		    	  			
		    	  			
		    	  			//update the cash price on the customer table
		    	  			long price = (fphisDAO.getLatestFundPrice(b.getFund_id()).getPrice());
		    	  			long cash = b.getShares()/1000*price;
		    	  			cusDAO.updateCash(b.getCustomer_id(), cash, true);
		    	  			b.setExecute_date(s);
		    	  			transDAO.update(b);
		    	  			break;
		    	  			
		    	  			
		    	  	//buy fund
		    		case 4:
		    			
		    				Position position=new Position();
		    				position.setCustomer_id(b.getCustomer_id());
		    				position.setFund_id(b.getFund_id());
		    				long currentshares =posDAO.getShares(b.getFund_id(), b.getCustomer_id());
		    				long latestprice = (fphisDAO.getLatestFundPrice(b.getFund_id()).getPrice());
		    	  			double latestshares = (double)b.getAmount()/(double)latestprice;
		    	  			latestshares*=1000;
		    	  			System.out.println("price:"+latestprice+"amt:"+b.getAmount());
		    	  			
		    	  			System.out.println("total shares"+(long)latestshares);
		    				
		    				if(currentshares==-1)
		    				{
		    					
		    					position.setShares((long)latestshares);
		    					posDAO.create(position);
		    					
		    				}
		    				else 
		    				{	
		    					latestshares+=currentshares;
		    					position.setShares((long)latestshares);
		    					posDAO.update(position);
		    				}
		    				//update the cash price on the customer table
		    				cusDAO.updateCash(b.getCustomer_id(), b.getAmount(), false);
		    				b.setExecute_date(s);
		    	  			transDAO.update(b);
		    				break;
		    				
		    				/*
		    				
		    	  			*/
		    		 
		    		 default:
		    			 System.out.println("default");
		    	  
		    	  }
		      }
  
        }
        catch (Exception e) {
     			// TODO Auto-generated catch block
        	 System.out.println("length:");
         	e.printStackTrace();
     	}    	
        
        return "transitionSuccess.jsp";
    } 
}


