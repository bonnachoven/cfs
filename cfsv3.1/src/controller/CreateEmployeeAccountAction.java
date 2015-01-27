package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.EmployeeDAO;
import model.Model;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Employee;
import formbean.CreateEmployeeAccountForm;

/*
 * Processes the parameters from the form in register.jsp.
 * If successful:
 *   (1) creates a new Employee bean
 *   (2) sets the "Employee" session attribute to the new Employee bean
 *   (3) redirects to view the originally requested photo.
 * If there was no photo originally requested to be viewed
 * (as specified by the "redirect" hidden form value),
 * just redirect to manage.do to allow the Employee to add some
 * photos.
 */
public class CreateEmployeeAccountAction extends Action {
	private FormBeanFactory<CreateEmployeeAccountForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateEmployeeAccountForm.class);

	private EmployeeDAO employeeDAO;

	public CreateEmployeeAccountAction(Model model) {
		employeeDAO = model.getEmployeeDAO();

	}

	public String getName() {
		return "createEmployee.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession(false);
		session.setAttribute("errors", errors);

		try {
			CreateEmployeeAccountForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			session.setAttribute("employeeList", employeeDAO.getEmployees());

			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "createEmployee.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "createEmployee.jsp";
			}

			// Create the Employee bean
			Employee employee = new Employee();
			employee.setUsername(form.getUsername());

			employee.setFirstname(form.getFirstname());
			employee.setLastname(form.getLastname());
			employee.setPassword(form.getPassword());
			Employee[] employees = employeeDAO.match(MatchArg.equals(
					"username", form.getUsername()));
			if (employees.length > 0) {
				errors.add("employee existed!");
				return "createEmployee.jsp";
			} else {
				// employeeDAO.createAutoIncrement(employee);
				employeeDAO.create(employee);
				session.removeAttribute("form");
				request.setAttribute("msg","Employee was created successfully.");
				return "success-admin.jsp";
			}
			


			
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "createEmployee.jsp";
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "createEmployee.jsp";
		}

	}
}
