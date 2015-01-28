
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
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;
import databean.Customer;
import databean.Fund;
import databean.HistoryBean;
import databean.TransactionBean;

/*
 * Processes the parameters from the form in login.jsp.
 * If successful, set the "user" session attribute to the
 * user's User bean and then redirects to view the originally
 * requested photo.  If there was no photo originally requested
 * to be viewed (as specified by the "redirect" hidden form
 * value), just redirect to manage.do to allow the user to manage
 * his photos.
 */
public class History extends Action {
	/*private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	*/
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private CustomerDAO  customerDAO;
	private TransactionDAO transactionDAO;
	public History(Model m) {
		fundDAO = m.getFundDAO();
    	positionDAO=m.getPositionDAO();
    	customerDAO  = m.getCustomerDAO();
    	transactionDAO=m.getTransactionDAO();
	}

	public String getName() { return "transactionHistory.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
      
        // Look up the user
        Customer user = (Customer) request.getSession(false).getAttribute("customer");
    	 
       if (user == null) {
            errors.add("User Name not found");
            return "login.jsp";
        }
        try{
        //int customer_id=user.getCustomer_id();
       //int customer_id=1;
        TransactionBean bean[]=transactionDAO.getTransactions(user.getCustomer_id());
        
        Fund fund=new Fund();
        List<HistoryBean> list = new ArrayList<HistoryBean>();
        HistoryBean[] his;
        for(int i=0;i<bean.length;i++)
        {
        	 HistoryBean b=new HistoryBean();
        	if(bean[i].getFund_id()==0)
        	{
        		b.setname("");
        		b.setsymbol("");
        		b.setshares(0);
        	}
        	else
        		{
        			fund=fundDAO.getFunds(bean[i].getFund_id());
        			b.setname(fund.getName());
                	b.setshares(bean[i].getShares());
                	b.setsymbol(fund.getSymbol());
        		}
        	
        	
        	b.setamount(bean[i].getAmount());
        	b.setcustomer_id(bean[i].getCustomer_id());
        	b.setexecute_date(bean[i].getExecute_date());

        	System.out.println("null pending transactions"+b.getExecute_date());
        	b.settransaction_id(bean[i].getTransaction_id());
        	b.settransaction_type(bean[i].getTransaction_type());
        	list.add(b);
        }
       // System.out.println("length:"+bean.length);
        
        his=list.toArray(new HistoryBean[list.size()]);
        request.setAttribute("history", his);
        request.setAttribute("username", user.getUsername());
      return "history.jsp";
        } catch (Exception e) {
        	errors.add(e.getMessage());
        	return "error-list.jsp";
        }
       
    }
}
