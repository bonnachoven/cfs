package controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanFactory;

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
import formbean.ListForm;
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
    
    private FormBeanFactory<TransitionDayForm> formBeanFactory = FormBeanFactory.getInstance(TransitionDayForm.class);    
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
            //TransitionDayForm form = new TransitionDayForm();
            TransitionDayForm form = formBeanFactory.create(request);

            //check if the admin is logged in to the system ....else prevent from accessing the transition day
            Employee employee = (Employee) request.getSession(false).getAttribute("employee");
            if(employee==null)
                return "login.jsp";

            
            Fund[] funds=null;
            funds = fundDAO.getFunds();
            request.setAttribute("funds", funds);

            String lasttransitionday=fphisDAO.getLastTransitionDayDate();
            request.setAttribute("date", lasttransitionday);

            if (!form.isPresent()) { return "transitionDay.jsp"; }

            
            errors=form.getValidationErrors(lasttransitionday);
            if(errors.size()>0){
                request.setAttribute("errors", errors);
                return "transitionDay.jsp";}
            
            
            //check if some other concurrently accessing machine created new funds
            String date=form.getDate();
            String prices[]=form.getPrice();
            if(funds.length!=prices.length){
                request.setAttribute("additional","additional funds were added, page is refershed now, please enter values for new funds");
                return "transitionDay.jsp";}

            
            // write the values to the fundpricehistory table 
            for(int i=0;i<prices.length;i++){
                Fund_Price_History fph=new Fund_Price_History();
                fph.setFund_id(funds[i].getFund_id());
                String price=prices[i];
                fph.setPrice((long)Double.parseDouble(price)*100);
                fph.setPrice_date(date);
                fphisDAO.create(fph);}
            
            //get the list of all pending trnasactions from the transaction table
            
            TransactionBean bean[]=transDAO.getAllPendingTrans();
            System.out.println("pneding:"+bean.length);
            int count;
            for(TransactionBean b:bean){
                count=b.getTransaction_type();
                switch(count){
                //deposit cash on customer account
                case 1:
                    cusDAO.updateCash(b.getCustomer_id(),b.getAmount(),true);
                    b.setExecute_date(date);
                    transDAO.update(b);
                    break;

                    //request check on customer account
                case 2:
                    cusDAO.updateCash(b.getCustomer_id(),b.getAmount(),false);
                    b.setExecute_date(date);
                    transDAO.update(b);
                    break;

                    //sell fund
                case 3:
                    //update the shares on the position table

                    System.out.println("here");
                    Position p=new Position();
                    p.setCustomer_id(b.getCustomer_id());
                    p.setFund_id(b.getFund_id());
                    
                    long shares =posDAO.getShares(b.getFund_id(), b.getCustomer_id());
                    
                    if(shares>0){
                        shares-=b.getShares();
                        if(shares>0)
                            p.setShares(shares);
                        posDAO.update(p);}
                    
                    //update the cash price on the customer table
                    long price = (fphisDAO.getLatestFundPrice(b.getFund_id()).getPrice());
                    long cash = b.getShares()/1000*price;
                    cusDAO.updateCash(b.getCustomer_id(), cash, true);
                    b.setExecute_date(date);
                    transDAO.update(b);
                    break;


                    //buy fund
                case 4:
                	
                    System.out.println("inside:");
                    System.out.println("in buy account:");
                    Position position=new Position();
                    position.setCustomer_id(b.getCustomer_id());
                    position.setFund_id(b.getFund_id());
                    long currentshares =posDAO.getShares(b.getFund_id(), b.getCustomer_id());
                    if(currentshares==-1)
                    {
                        System.out.println("creating:");
                        //position.setShares(0);
                        //posDAO.create(position);
                     
                        
                        Position position2 = new Position();
            			position2.setCustomer_id(b.getCustomer_id());
            			position2.setFund_id(b.getFund_id());
            			position2.setShares(0);
            			posDAO.create(position2);
                        
                        
                        System.out.println("created succesffully:");
                        
                    }
                    else
                    {
                        long latestprice = (fphisDAO.getLatestFundPrice(b.getFund_id()).getPrice());
                        double latestshares = (double)b.getAmount()/(double)latestprice;
                        latestshares*=1000;
                        /*System.out.println("price:"+latestprice+"amt:"+b.getAmount());

                        System.out.println("total shares"+(long)latestshares);*/
                        latestshares+=currentshares;
                        position.setShares((long)latestshares);
                        posDAO.update(position);
                    }                    
                    //update the cash price on the customer table
                    cusDAO.updateCash(b.getCustomer_id(), b.getAmount(), false);
                    System.out.println("date:"+date);
                    b.setExecute_date(date);
                    transDAO.update(b);
                    System.out.println("updated trans dao:");
                    Transaction.commit();
                    break;
                default:
                    System.out.println("default");

                }
            }

        }
        catch(Exception e){
            System.out.println("final exceptions:");
            errors.add("error:");
            return "transitionDay.jsp";     
        }
        
        
        return "transitionSuccess.jsp";
    }
}
    