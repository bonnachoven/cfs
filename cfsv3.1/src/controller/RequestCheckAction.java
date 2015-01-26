package controller;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Model;
import model.MyDAOException;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.TransactionBean;
import formbean.RequestCheckForm;

public class RequestCheckAction extends Action {
	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory
			.getInstance(RequestCheckForm.class);

	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;

	private long amount;

	public RequestCheckAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();

	}

	public String getName() {
		return "requestCheck.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		System.out.println("i m in request.do");
		try {
			// Fetch the items now, so that in case there is no form or there
			// are errors
			// We can just dispatch to the JSP to show the item list (and any
			// errors)

			RequestCheckForm form = formBeanFactory.create(request);
			/*
			 * if (session.getAttribute("user") != null &&
			 * session.getAttribute("user") instanceof Customer) { DecimalFormat
			 * df3 = new DecimalFormat("#,##0.000"); Customer customer =
			 * (Customer) request.getSession(false).getAttribute("user");
			 */

			
			//System.out.println("input" + form.getAmount());
			if (!form.isPresent()) {
				System.out.println("form not present!");
				return "RequestCheck.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0) {
				return "RequestCheck.jsp";
			}

			
			 Customer user = (Customer) session.getAttribute("customer");
			 if (user==null) {
					return "login.jsp";
				}
			//Customer acnt = new Customer();
			String fn = user.getFirstname();
			System.out.println("first name: " + user.getFirstname());
			
			long bal = user.getCash();
			System.out.println("cash balance is" + bal);
			
			//long amt = form.getAmountAsLong();
			//System.out.println("amount is : " + amt);
 
			
			try{
			amount = (long)Double.parseDouble(form.getAmount())*100;
			}catch(NumberFormatException e){
				errors.add("Amount should be numerical.");
				request.setAttribute("errors", errors);
			}
			
			if (amount<1){
				errors.add("Amount should be greater than $0.01.");
				request.setAttribute("errors", errors);
			}
			
			System.out.println("the difference is :" + (bal - amount));
			System.out.println("I am changing the cash balance");
			
			if ((bal - amount) > 0) { // this is to check if withdrawing check
									// would
									// cause cash balance to go negative
				bal = bal - amount;
				user.setCash(bal);
				customerDAO.setCash(user.getCustomer_id(), user.getCash());
 
				TransactionBean trans = new TransactionBean();
				trans.setCustomer_id(user.getCustomer_id());
				trans.setTransaction_type(2);
				trans.setAmount(amount);
				System.out.println("i m in create new transaction");
				transactionDAO.create(trans);
				
				// and then update in the db
				
				request.setAttribute("message",
						"Thank You! Your request is processed.");
				return "success-cus.jsp";

			} else {
				errors.add("Your account balance is too low to withdraw a check");
				return "error.jsp";
				
			}
		} catch (FormBeanException | RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
		
	}
}
