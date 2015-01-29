package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.FundDAO;
import model.Fund_Price_HistoryDAO;
import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.Fund;
import databean.FundItem;
import databean.TransactionBean;
import formbean.BuyFundForm;
import formbean.RequestCheckForm;

public class RequestCheckAction extends Action {
	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory
			.getInstance(RequestCheckForm.class);

	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	
	public RequestCheckAction(Model model) {
		
		this.transactionDAO = model.getTransactionDAO();
		this.customerDAO = model.getCustomerDAO();

	}

	public String getName() {
		return "requestCheck.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession(false);
		session.setAttribute("errors", errors);

		DecimalFormat amount = new DecimalFormat("#,##0.00");

		try {
			if (request.getSession().getAttribute("customer") == null) {
				errors.add("Please Log in first");
				return "login.jsp";
			}

			Customer customer = customerDAO.getCustomers(((Customer) request
					.getSession().getAttribute("customer")).getUsername());

			
			Double validBalance = transactionDAO.getValidBalance(
					customer.getCustomer_id(),
					(customer.igetCashAsDouble()/100.0));

			String validBalanceString = amount.format(validBalance);
			request.setAttribute("balance", validBalanceString);
			
			

			RequestCheckForm form = formBeanFactory.create(request);

			request.setAttribute("form", form);
			if (form.isPresent()) {
				
				Double k = form.getAmountAsDouble();
				System.out.println("Amount in the form is " + k);
				if (k > validBalance) {
					errors.add("You don't have enough balance to withdraw a check of this amount");
				}
				
				errors.addAll(form.getValidationErrors());
		        if (errors.size() != 0) {
		            return "RequestCheck.jsp";
		        }
		        
				TransactionBean transaction = new TransactionBean();
				transaction.setCustomer_id(customer.getCustomer_id());
			
				transaction.setExecute_date(null);
	
				transaction.setTransaction_type(2);
				
				Double amount1 = form.getAmountAsDouble();
				System.out.println("Amount in the form is " + amount1);
				if (amount1 > validBalance) {
					errors.add("You don't have enough balance to withdraw a check of this amount");
				}
				else {
					validBalance -= amount1;
					transaction.setAmount(amount1);
					transactionDAO.create(transaction);
					request.setAttribute("balance", validBalance);
					request.removeAttribute("form");
				}
			}

			return "RequestCheck.jsp";

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "RequestCheck.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "RequestCheck.jsp";
		}
	}

}
