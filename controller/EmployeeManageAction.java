

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import model.FavoriteDAO;
import model.Model;
//import model.CustomerDAO;
import model.EmployeeDAO;
//import model.UserDAO;

import org.genericdao.RollbackException;

//import databeans.Favorite;
//import databeans.User;
import databean.Employee;

public class EmployeeManageAction extends Action {
	
//	private UserDAO userDAO;
//	private FavoriteDAO favoriteDAO;
//	private CustomerDAO customerDAO;

	private EmployeeDAO employeeDAO;
	// constructor
	public EmployeeManageAction(Model model) {
//		userDAO = model.getUserDAO();
//		favoriteDAO = model.getFavoriteDAO();
//		customerDAO = model.getCustomerDAO();
		employeeDAO = model.getEmployeeDAO();
	}
	
	// get action name
	public String getName() {
		return "employeemanage.do";
	}
	
	// return next page name
	public String perform(HttpServletRequest request) {
		// get session
		HttpSession session = request.getSession(false);
		
		// set title attribute
//		String title = "Manage List Page";
//		session.setAttribute("title", title);
		
		// set error attribute
		List<String> errors = new ArrayList<String>();
		session.setAttribute("errors", errors);
				
		
		// get user
		Employee employee = (Employee) session.getAttribute("employee");
			
		// get favorite list
		return "employee-manage.jsp";
	}
}
