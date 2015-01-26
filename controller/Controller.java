//Hua-Ming Lee
//huamingl
//08-600
//hw9
//2014/12/1

package controller;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.EmployeeDAO;
import model.FundDAO;
import model.Fund_Price_HistoryDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;

import databean.Customer;
import databean.Employee;
import databean.Fund;
import databean.Fund_Price_History;
import databean.Position;
import databean.TransactionBean;

public class Controller extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		Model model = new Model(getServletConfig());

		Action.add(new LoginAction(model));
		Action.add(new CustomerManageAction(model));
		Action.add(new EmployeeManageAction(model));
		Action.add(new ViewAccount(model));
		Action.add(new CreateFundAction(model));
		Action.add(new CreateEmployeeAccountAction(model));
		Action.add(new CreateCustomerAccountAction(model));
		Action.add(new LogoutAction(model));
		Action.add(new ChangePwdAction(model));
		Action.add(new SellFundAction(model));
		Action.add(new BuyFundAction(model));
		Action.add(new ResetPwdAction(model));
		Action.add(new ViewCusAccount(model));
		Action.add(new ResearchFundAction(model));
		Action.add(new ListAction(model));
		Action.add(new History(model));
		Action.add(new TransitionDay(model));
		Action.add(new RequestCheckAction(model));
		Action.add(new DepositCheckAction(model));
		Action.add(new CustomerSearchAction(model));
		
		
		
		
		initializeTable(model.getCustomerDAO(), 
				model.getEmployeeDAO(),
				model.getTransactionDAO(), 
				model.getFundDAO(),
				model.getPositionDAO(),
				model.getFund_Price_HistoryDAO()
				);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nextPage = performTheAction(request);
		sendToNextPage(nextPage, request, response);
	}

	private String performTheAction(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String servletPath = request.getServletPath();
		Customer customer = (Customer) session.getAttribute("customer");
		Employee employee = (Employee) session.getAttribute("employee");

		String action = getActionName(servletPath);

		// System.out.println("servletPath="+servletPath+" requestURI="+request.getRequestURI()+"  user="+user);

		if (action.equals("register.do") || action.equals("login.do")
				|| action.equals("list.do") || action.equals("update.do")) {
			// Allow these actions without logging in
			return Action.perform(action, request);
		}

		if (customer == null && employee == null) {
			// If the user hasn't logged in, direct him to the login page
			return Action.perform("login.do", request);
		}

		// Let the logged in user run his chosen action
		return Action.perform(action, request);
	}

	private void sendToNextPage(String nextPage, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		if (nextPage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					request.getServletPath());
			return;
		}

		if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
		}

		if (nextPage.endsWith(".jsp")) {
			RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
					+ nextPage);
			d.forward(request, response);
			return;
		}

		throw new ServletException(Controller.class.getName()
				+ ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
	}

	private String getActionName(String path) {
		int slash = path.lastIndexOf('/');
		return path.substring(slash + 1);
	}
	
	public void initializeTable(CustomerDAO customerDAO, EmployeeDAO employeeDAO, TransactionDAO transactionDAO,FundDAO fundDAO,PositionDAO positionDAO, 
			Fund_Price_HistoryDAO fundPriceHistoryDAO) {

		try {
			Employee admin = employeeDAO.read("admin");
			if (admin == null) {
				admin = new Employee();
				admin.setUsername("admin");
				admin.setPassword("passw0rd");
				employeeDAO.create(admin);
			}
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		
		try {
			Customer customer = customerDAO.getCustomers("zm");
			if (customer == null) {
				Customer newcustomer1 = new Customer();
				newcustomer1.setUsername("zm");
				newcustomer1.setAddr_line1("417 S Craig Stree");
				newcustomer1.setAddr_line2("Apt 201A");
				newcustomer1.setCash(20000l);
				newcustomer1.setCity("Pittsburgh");
				newcustomer1.setFirstname("Mi");
				newcustomer1.setLastname("Zhou");
				newcustomer1.setPassword("passw0rd");
				newcustomer1.setZip("15213");
				newcustomer1.setState("PA");
				customerDAO.create(newcustomer1);
			}
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		
		try {
			Fund fund1 = fundDAO.getFunds("Google");
			if (fund1 == null) {
				fund1 = new Fund();
				fund1.setName("Google");
				fund1.setSymbol("GOOG");
				System.out.println(fund1.getName());
				// fundDAO.createAutoIncrement(fund1);
				fundDAO.create(fund1);
			}
			

			Fund fund2 = fundDAO.getFunds("IBM");
			if (fund2 == null) {
				fund2 = new Fund();
				fund2.setName("IBM");
				fund2.setSymbol("IBM");
				fundDAO.createAutoIncrement(fund2);
			}
			
			
			Fund fund3 = fundDAO.getFunds("Facebook");
			if (fund3 == null) {
				fund3 = new Fund();
				fund3.setName("Facebook");
				fund3.setSymbol("FB");
				fundDAO.createAutoIncrement(fund3);
			}

		} catch (RollbackException e) {
			e.printStackTrace();
		}
		
		try {
			TransactionBean transaction1 = new TransactionBean();
			Customer zm = customerDAO.getCustomers("zm");
			transaction1.setCustomer_id(zm.getCustomer_id());
			Calendar cal1 = Calendar.getInstance();
			cal1.set(2014, 11, 6);
			transaction1.setExecute_date("2015-01-23");
			Fund tfund1 = fundDAO.getFunds("Google");
			transaction1.setFund_id(tfund1.getFund_id());
			transaction1.setShares(6343);
			transaction1.setTransaction_type(3);
			transactionDAO.createAutoIncrement(transaction1);

			TransactionBean transaction2 = new TransactionBean();
			zm = customerDAO.getCustomers("zm");
			transaction2.setAmount(100000);
			transaction2.setCustomer_id(zm.getCustomer_id());
			Calendar cal2 = Calendar.getInstance();
			cal2.set(2014, 2, 22);
			transaction2.setExecute_date("2015-01-23");
			Fund tfund2 = fundDAO.getFunds("IBM");
			transaction2.setFund_id(tfund2.getFund_id());
//			transaction2.setShares(2130);
			transaction2.setTransaction_type(4);
			transactionDAO.createAutoIncrement(transaction2);
			 
			TransactionBean transaction3 = new TransactionBean();
			zm = customerDAO.getCustomers("zm");
			transaction3.setAmount(2000);
			transaction3.setCustomer_id(zm.getCustomer_id());
			Calendar cal3 = Calendar.getInstance();
			cal3.set(2015, 1, 18);
			transaction3.setExecute_date("2015-01-23");
			Fund tfund3 = fundDAO.getFunds("Facebook");
			transaction3.setFund_id(tfund3.getFund_id());
			transaction3.setTransaction_type(4);
			transactionDAO.createAutoIncrement(transaction3);


		} catch (RollbackException e) {
			e.printStackTrace();
		}
		
		try {
			Position position1 = new Position();
			Customer zm = customerDAO.getCustomers("zm");
			position1.setCustomer_id(zm.getCustomer_id());
			position1.setFund_id(1);
			position1.setShares(12300);
			positionDAO.create(position1);
			
			Position position2 = new Position();
			position2.setCustomer_id(zm.getCustomer_id());
			position2.setFund_id(2);
			position2.setShares(52617);
			positionDAO.create(position2);
		}
		catch (RollbackException e) { e.printStackTrace(); }
		
		try {
			Fund_Price_History fph1 = new Fund_Price_History();
			Fund fund1 = fundDAO.getFunds("Google");
			fph1.setFund_id(fund1.getFund_id());
			fph1.setPrice(50000);
			Calendar c1 = Calendar.getInstance();
			c1.set(2015, 1, 21);
			fph1.setPrice_date_formatted(c1.getTime());
			fundPriceHistoryDAO.create(fph1);
			
			
			Fund_Price_History fph2 = new Fund_Price_History();
			Fund fund2 = fundDAO.getFunds("IBM");
			fph2.setFund_id(fund2.getFund_id());
			fph2.setPrice(10000);
			Calendar c2 = Calendar.getInstance();
			c2.set(2015, 1, 20);
			fph2.setPrice_date_formatted(c2.getTime());
			fundPriceHistoryDAO.create(fph2);
			
			
			
			Fund_Price_History fph3 = new Fund_Price_History();
			Fund fund3 = fundDAO.getFunds("Google");
			fph3.setFund_id(fund3.getFund_id());
			fph3.setPrice(10000);
			Calendar c3 = Calendar.getInstance();
			c3.set(2015, 1, 22);
			fph3.setPrice_date_formatted(c3.getTime());
			fundPriceHistoryDAO.create(fph3);
			
		}
		catch (RollbackException e) { e.printStackTrace(); }

	}
	
}
