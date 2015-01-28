package controller;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.EmployeeDAO;
import model.Model;
import model.MyDAOException;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.Employee;
import databean.TransactionBean;
import formbean.DepositCheckForm;
import formbean.RequestCheckForm;

public class DepositCheckAction extends Action {
	private FormBeanFactory<DepositCheckForm> formBeanFactory = FormBeanFactory
			.getInstance(DepositCheckForm.class);

	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private EmployeeDAO employeeDAO;
	int id;
	Customer selectedcustomer;

	public DepositCheckAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "depositCheck.do";
	}

	public String perform(HttpServletRequest request) {
		System.out.println("i m in depositCheck.do");

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		HttpSession session = request.getSession();
		Employee employee = (Employee) session.getAttribute("employee");

		try {
			if (employee == null) {
				errors.add("Please login first");
				return "login.do";
			} else if (!(employee instanceof Employee)) {
				errors.add("Sorry! Only Employee can access this funtion.");
				return "login.do";
			}

			System.out.println("i m in depositCheck.do");

			// Fetch the items now, so that in case there is no form or there
			// are errors
			// We can just dispatch to the JSP to show the item list (and any
			// errors)
			System.out.println("settign attribute for form");
			DepositCheckForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			/*
			 * if (session.getAttribute("user") != null &&
			 * session.getAttribute("user") instanceof Customer) { DecimalFormat
			 * df3 = new DecimalFormat("#,##0.000"); Customer customer =
			 * (Customer) request.getSession(false).getAttribute("user");
			 */
			
			// System.out.println("input" + form.getAmount());
			if (!form.isPresent()) {
				System.out.println("form not present!");
				String id_string = request.getParameter("customer_id");
				//System.out.println(id);
				try {
					id = Integer.parseInt(id_string);
				} catch (NumberFormatException e) {
					errors.add("ID is not ineteger. Select customer again.");
					request.setAttribute("customerList", customerDAO.match());
					request.setAttribute("errors", errors);
					return "customerSearch.jsp";
				}

				//System.out.println("ID as integer: " + id);

				selectedcustomer = customerDAO.read(id);
				if (selectedcustomer == null) {
					errors.add("customer not found");
					request.setAttribute("customerList", customerDAO.match());
					request.setAttribute("errors", errors);
					return "customerSearch.jsp";
				}

				request.setAttribute("selectedcustomer", selectedcustomer);

				return "depositCheck.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0) {
				request.setAttribute("selectedcustomer", selectedcustomer);
				//System.out.println("there is an error!!!!!");
				return "depositCheck.jsp";
			}

			/*
			 * Customer customer = (Customer) session
			 * .getAttribute("currentcustomer"); if (customer == null) {
			 * errors.add("Customer does not exist"); return
			 * "searchCustomer.jsp"; }
			 */

			//
			// System.out.println("the sent customer id in url is: "+request.getParameter("customer_id"));
			// int
			// customer_id=Integer.parseInt(request.getParameter("customer_id"));
			// Customer selectedcustomer ;

			String id_string = request.getParameter("customer_id");

			try {
				id = Integer.parseInt(id_string);
			} catch (NumberFormatException e) {
				errors.add("ID is not an integer. Please select customer again.");
				request.setAttribute("customerList", customerDAO.match());
				request.setAttribute("errors", errors);
				return "customerSearch.jsp";

			}

			selectedcustomer = customerDAO.read(id);

			long amount;

			try {
				double newamt = Double.parseDouble(form.getAmount()) * 100;
				amount = (long) newamt;
			} catch (NumberFormatException e) {
				errors.add("Amount should be numerical.");
				request.setAttribute("errors", errors);
				request.setAttribute("selectedcustomer", selectedcustomer);
				return "depositCheck.jsp";

			}

			/*if (amount < 1) {
				errors.add("Amount should be greater than $0.01.");
				request.setAttribute("errors", errors);
				request.setAttribute("selectedcustomer", selectedcustomer);
				return "depositCheck.jsp";
			}*/

			selectedcustomer = customerDAO.readUsers(id);
			request.setAttribute("selectedcustomer", selectedcustomer);

			TransactionBean trans = new TransactionBean();
			trans.setCustomer_id(selectedcustomer.getCustomer_id());
			trans.setTransaction_type(1);
			trans.setExecute_date(null);
			trans.setAmount(amount);
			System.out.println("i m in create new transaction");
			transactionDAO.create(trans);

			// for deposit check i just need to push values in the transaction
			// db... skandan will add the amt to cash balance after transition
			// day

			// Customer acnt = new Customer();
			// String fn = user.getFirstname();
			// System.out.println("first name: " + user.getFirstname());

			/*
			 * long bal = selectedcustomer.getCash();
			 * System.out.println("cash balance is" + bal);
			 * 
			 * long amt = 0; System.out.println("amount is : " + amount);
			 * 
			 * System.out.println("the total balance is :" + (bal + amount));
			 * System.out.println("I am changing the cash balance");
			 * 
			 * bal = bal + amount; selectedcustomer.setCash(bal);
			 * customerDAO.setCash(selectedcustomer.getCustomer_id(),
			 * selectedcustomer.getCash());
			 */

			// and then update in the db

			request.setAttribute("msg",
					"Thank You! Your request is processed.");
			return "success-admin.jsp";

		} catch (FormBeanException | RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}

	}
}
