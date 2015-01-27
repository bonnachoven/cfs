package controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
//import model.UserDAO;
import model.CustomerDAO;
import model.EmployeeDAO;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.Employee;
import formbean.LoginForm;


/*
 * Processes the parameters from the form in login.jsp.
 * If successful, set the "user" session attribute to the
 * user's User bean and then redirects to view the originally
 * requested photo.  If there was no photo originally requested
 * to be viewed (as specified by the "redirect" hidden form
 * value), just redirect to manage.do to allow the user to manage
 * his photos.
 */
public class LoginAction extends Action {
	private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	
//	private UserDAO userDAO;
	private CustomerDAO customerDAO;
	private EmployeeDAO  employeeDAO;

	public LoginAction(Model model) {
		customerDAO = model.getCustomerDAO();
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() { return "login.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        HttpSession session = request.getSession(false);
        session.setAttribute("errors",errors);
        
        try {
        	
	    	LoginForm form = formBeanFactory.create(request);
	    	
	    	session.setAttribute("form",form);


	        // If no params were passed, return with no errors so that the form will be
	        // presented (we assume for the first time).
	        if (!form.isPresent()) {
	            return "login.jsp";
	        }

	        // Any validation errors?
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "login.jsp";
	        }

	        
	        // Look up the user
	        if (form.getType().equals("c")) {
	        	Customer customer = customerDAO.getCustomers(form.getUsername());
	        	if (customer == null) {
		            errors.add("This customer does not exist");
		            System.out.println("This customer does not exist");
		            return "login.jsp";
		        }
	        	
	        	if (!customer.checkPassword(form.getPassword())) {
	        		
		            errors.add("Incorrect password");
		            return "login.jsp";
		        }
	        	session.setAttribute("customer",customer);
				return "customermanage.do";
	        }
	        else {
	        	Employee employee = employeeDAO.getEmployees(form.getUsername());
	        	if (employee == null) {
		            errors.add("This employee does not exist");
		            return "login.jsp";
		        }
	        	
	        	
	        	
	        	if (!employee.checkPassword(form.getPassword())) {
		            errors.add("Incorrect password");
		            return "login.jsp";
		        }
	        	session.setAttribute("employee",employee);
				return "employeemanage.do";
	        }
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
