//Hua-Ming Lee
//huamingl
//08-600
//hw9
//2014/12/1

package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
//import org.genericdao.MatchArg;
//import org.genericdao.RollbackException;

//import databean.Customer;
//import databean.Fund;

public class Model {
	private FundDAO fundDAO;
	private EmployeeDAO employeeDAO;
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;
	private PositionDAO positionDAO;
	private Fund_Price_HistoryDAO fundPriceHistoryDAO;

	public Model(ServletConfig _config) throws ServletException{
		try {
			String jdbcDriver = _config.getInitParameter("jdbcDriverName");
			String jdbcURL = _config.getInitParameter("jdbcURL");
			ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);

			positionDAO = new PositionDAO(pool, "Position");
			transactionDAO = new TransactionDAO(pool, "Transaction");
			customerDAO = new CustomerDAO(pool, "Customer");
			employeeDAO = new EmployeeDAO(pool,"Employee");
			fundDAO = new FundDAO(pool, "Fund");
			fundPriceHistoryDAO = new Fund_Price_HistoryDAO(pool, "FundPriceHistory");
			
		} catch (DAOException e) {
			throw new ServletException(e);
		}
	}

	
	public FundDAO getFundDAO()  { return fundDAO; }
	public CustomerDAO getCustomerDAO()  { return customerDAO; }
	public EmployeeDAO getEmployeeDAO()  { return employeeDAO; }
	public TransactionDAO getTransactionDAO()  { return transactionDAO; }
	public PositionDAO getPositionDAO()   { return positionDAO; }
	public Fund_Price_HistoryDAO getFund_Price_HistoryDAO() { return fundPriceHistoryDAO;}

}
