package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.EmployeeDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.Employee;
import formbean.ResetPwdForm;

public class ResetPwdAction extends Action {
	private FormBeanFactory<ResetPwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ResetPwdForm.class);
	private CustomerDAO customerDAO;

	public ResetPwdAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "resetCustomerPassword.do";

	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession(false);
		session.setAttribute("errors", errors);

		try {
			ResetPwdForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			if (!form.isPresent()) {
				return "reset-pwd.jsp";
			}
			errors.addAll(form.getValidationErrors());
			Customer customer = customerDAO.getCustomers(form.getUsername());
			if (customer == null) {
				errors.add("This user doesn't exist");
			}

			if (errors.size() > 0) {
				return "reset-pwd.jsp";

			}

			if (errors.size() > 0) {
				return "reset-pwd.jsp";
			}

			customer.setPassword("123");
			customerDAO.update(customer);
			request.setAttribute("msg", "Password has been changed to 123");
			
			return "reset-pwd.jsp";
		}
		catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}

	}
}
