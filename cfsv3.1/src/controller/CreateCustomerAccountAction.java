package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Model;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import formbean.CreateCustomerAccountForm;

/*
 * Processes the parameters from the form in register.jsp.
 * If successful:
 *   (1) creates a new customer bean
 *   (2) sets the "customer" session attribute to the new customer bean
 *   (3) redirects to view the originally requested photo.
 * If there was no photo originally requested to be viewed
 * (as specified by the "redirect" hidden form value),
 * just redirect to manage.do to allow the customer to add some
 * photos.
 */
public class CreateCustomerAccountAction extends Action {
	private FormBeanFactory<CreateCustomerAccountForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateCustomerAccountForm.class);

	private CustomerDAO customerDAO;

	public CreateCustomerAccountAction(Model model) {
		customerDAO = model.getCustomerDAO();

	}

	public String getName() {
		return "createCustomer.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession(false);
		session.setAttribute("errors", errors);

		try {
			CreateCustomerAccountForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			session.setAttribute("customerList", customerDAO.getCustomers());

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "createCustomer.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "createCustomer.jsp";
			}

			// Create the customer bean
			Customer customer = new Customer();
			customer.setUsername(form.getUsername());

			customer.setFirstname(form.getFirstname());
			customer.setLastname(form.getLastname());
			customer.setPassword(form.getPassword());
			customer.setAddr_line1(form.getAddr_line1());
			customer.setAddr_line2(form.getAddr_line2());
			customer.setCity(form.getCity());
			customer.setState(form.getState());
			customer.setZip(form.getZip());

			Customer[] customers = customerDAO.match(MatchArg.equals(
					"username", form.getUsername()));
			if (customers.length > 0) {
				errors.add("customer existed!");
				return "createCustomer.jsp";
			} else {
				customerDAO.createAutoIncrement(customer);
				session.removeAttribute("form");
				request.setAttribute("msg","Customer was created successfully.");
			}


			return "success-admin.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "createCustomer.jsp";
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "createCustomer.jsp";
		}

	}
}
