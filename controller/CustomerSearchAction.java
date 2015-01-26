/* Name: Jhalak Goyal
 * Andrew ID: jgoyal
 * Course: 08-600 J2EE Programming
 * Date: 12/06/2014
 */

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.EmployeeDAO;
import model.Model;
import model.MyDAOException;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import formbean.CustomerSearchForm;

/*
 * Looks up the favorites for a given "user".
 * 
 * If successful:
 *   (1) Sets the "userList" request attribute in order to display
 *       the list of users on the navbar.
 *   (2) Sets the "FavoriteList" request attribute in order to display
 *       the list of given user's Favorites for selection.
 *   (3) Forwards to list.jsp.
 */
public class CustomerSearchAction extends Action {
	private FormBeanFactory<CustomerSearchForm> formBeanFactory = FormBeanFactory
			.getInstance(CustomerSearchForm.class);

	private CustomerDAO customerDAO;
	private EmployeeDAO employeeDAO;

	private String name;

	private String id;

	private int customer_id;

	public CustomerSearchAction(Model model) {
		customerDAO = model.getCustomerDAO();
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "customerSearch.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up the request attributes (the errors list and the form bean so
		// we can just return to the jsp with the form if the request isn't
		// correct)
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			
			System.out.println("setting list of customers");
			request.setAttribute("customerList", customerDAO.getCustomers());

			//to get the username
			CustomerSearchForm form = formBeanFactory.create(request);

			if (!form.isPresent()) {
				id= request.getParameter("customer_id");
				try{
					customer_id=Integer.parseInt(id);
				} catch (NumberFormatException e) {
					return "customerSearch.jsp";
				}
				Customer selectedcustomer = customerDAO.readUsers(customer_id);
				request.setAttribute("selectedcustomer", selectedcustomer);
				
				System.out.println("form not present");
				return "customerSearch.jsp";
			}

			// Check for any validation errors
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "customerSearch.jsp";
			}
			
			
			return "depositCheck.jsp";

			

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
			
		}

	}
}
