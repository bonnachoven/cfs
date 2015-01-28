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

	private double availableBalance;
	DecimalFormat displayprice = new DecimalFormat("#,##0.00");

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
		
		try {
	

			RequestCheckForm form = formBeanFactory.create(request);
			/*
			 * if (session.getAttribute("user") != null &&
			 * session.getAttribute("user") instanceof Customer) { DecimalFormat
			 * df3 = new DecimalFormat("#,##0.000"); Customer customer =
			 * (Customer) request.getSession(false).getAttribute("user");
			 */
			Customer user = (Customer) session.getAttribute("customer");
			if (user == null && !(user instanceof Customer) ) {
				return "login.jsp";
			}

			 
			if (!form.isPresent()) {
				availableBalance = transactionDAO.getValidBalance(
						user.getCustomer_id(), user.getCash() / 100);
				request.setAttribute("newavbal",
						displayprice.format(availableBalance));
				System.out.println("form not present!");
				return "RequestCheck.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0) {
				availableBalance = transactionDAO.getValidBalance(
						user.getCustomer_id(), user.getCash() / 100);
				request.setAttribute("newavbal",
						displayprice.format(availableBalance));
				return "RequestCheck.jsp";
			}

			// Customer acnt = new Customer();

			long bal = user.getCash();
			//System.out.println("cash balance is" + bal);
			//System.out.println("cash as double" + user.igetCashAsDouble());

			// long amt = form.getAmountAsLong();
			// System.out.println("amount is : " + amt);

			try {
				double newamt= Double.parseDouble(form.getAmount()) * 100;
				amount = (long)newamt;
				//System.out.println("double: "+Double.parseDouble(form.getAmount()));
				//System.out.println("*100: "+ Double.parseDouble(form.getAmount()) * 100);
				//System.out.println("final amount: "+amount);
			} catch (NumberFormatException e) {
				errors.add("Amount should be numerical.");
				request.setAttribute("errors", errors);
			}

		/*	if (amount < 1) {
				errors.add("Amount should be greater than $0.01.");
				request.setAttribute("errors", errors);
			}else if (amount > 10000000) {
				errors.add("Please enter an amount that is lesser than $1000000000");
			}*/


			availableBalance = transactionDAO.getValidBalance(
					user.getCustomer_id(), bal / 100);
			//System.out.println("cash: " +bal);
			//System.out.println("cash/100: " +bal/100);
			//System.out.println( "avail bal: "+ availableBalance);
			//System.out.println("diff"+ (availableBalance - Double.parseDouble(form.getAmount())));
			if (availableBalance - Double.parseDouble(form.getAmount()) > 0) {
				double newavbal = availableBalance
						- Double.parseDouble(form.getAmount());
				
				TransactionBean trans = new TransactionBean();
				trans.setCustomer_id(user.getCustomer_id());
				trans.setExecute_date(null);
				trans.setTransaction_type(2);
				trans.setAmount(amount);
				
				transactionDAO.create(trans);

				request.setAttribute("newavbal", newavbal);
				request.setAttribute("message",
						"Thank You! Your request is processed.");
				return "success-cus.jsp";

			} else {
				errors.add("Your account balance is too low to withdraw a check");
				availableBalance = transactionDAO.getValidBalance(
						user.getCustomer_id(), user.getCash() / 100);
				request.setAttribute("newavbal",
						displayprice.format(availableBalance));
				return "RequestCheck.jsp";

			}
		} catch (FormBeanException | RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}

	}
}
