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
import formbean.ChangePwdForm;

public class ChangePwdAction extends Action {
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory
			.getInstance(ChangePwdForm.class);
	private CustomerDAO customerDAO;
	private EmployeeDAO employeeDAO;

	public ChangePwdAction(Model model) {
		customerDAO = model.getCustomerDAO();
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "changePassword.do";

	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession(false);
		session.setAttribute("errors", errors);

		try {
			if ((Customer) request.getSession().getAttribute("customer") != null) {
				// now it is customer
				System.out.println("Now it is a customer");

				Customer user = (Customer) request.getSession().getAttribute(
						"customer");

				ChangePwdForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);

				if (!form.isPresent()) {
					System.out.println("form doesn't exist");
					return "changePswCus.jsp";
				}

				errors.addAll(form.getValidationErrors());
				for (String error : errors) {
					System.out.println(error);
				}
				if (errors.size() > 0) {
					System.out.println("has error");
					for (String e : errors) {
						System.out.println(e);
					}
					return "changePswCus.jsp";

				}

				if (!form.getCurrentPassword().equals(
						customerDAO.getCustomers(user.getUsername())
								.getPassword())) {
					errors.add("Current password is incorrect");
				}
				if (errors.size() > 0) {
					System.out.println("still has error");
					return "changePswCus.jsp";
				}
				user.setPassword(form.getNewPassword());
				customerDAO.update(user);

				request.setAttribute("msg",
						"Password was changed successfully.");
				session.removeAttribute("form");
				return "changePswCus.jsp";

			} else if (((Employee) request.getSession()
					.getAttribute("employee")) != null) {
				Employee user = (Employee) request.getSession().getAttribute(
						"employee");
				// now it is an employee
				ChangePwdForm form = formBeanFactory.create(request);
				request.setAttribute("form", form);

				if (!form.isPresent()) {
					return "changePswEmp.jsp";
				}

				errors.addAll(form.getValidationErrors());

				if (errors.size() > 0) {
					return "changePswEmp.jsp";
				}

				if (!form.getCurrentPassword().equals(
						employeeDAO.read(user.getUsername()).getPassword())) {
					errors.add("Incorrect original password");
				}

				if (errors.size() > 0) {
					return "changePswEmp.jsp";
				}

				user.setPassword(form.getNewPassword());
				employeeDAO.update(user);

				request.setAttribute("msg",
						"Password was changed successfully.");
				session.removeAttribute("form");
				return "changePswEmp.jsp";
			} else {
				if (request.getSession().getAttribute("employee") != null) {
					request.getSession().removeAttribute("employee");
				}

				if (request.getSession().getAttribute("customer") != null) {
					request.getSession().removeAttribute("customer");
				}
				return "login.do";
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
