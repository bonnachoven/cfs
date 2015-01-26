

package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import model.FavoriteDAO;
import model.Model;
import model.CustomerDAO;
//import model.UserDAO;

import org.genericdao.RollbackException;

//import databeans.Favorite;
//import databeans.User;
import databean.Customer;

public class CustomerManageAction extends Action {
	
//	private UserDAO userDAO;
//	private FavoriteDAO favoriteDAO;
	private CustomerDAO customerDAO;
	
	// constructor
	public CustomerManageAction(Model model) {
//		userDAO = model.getUserDAO();
//		favoriteDAO = model.getFavoriteDAO();
		customerDAO = model.getCustomerDAO();
	}
	
	// get action name
	public String getName() {
		return "customermanage.do";
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
		Customer customer = (Customer) session.getAttribute("customer");
			
		// get favorite list
		return "customer-manage.jsp";
	}
}
