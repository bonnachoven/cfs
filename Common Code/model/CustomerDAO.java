package model;

import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.Customer;

public class CustomerDAO extends GenericDAO<Customer> {
	public CustomerDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(Customer.class, tableName, cp);
	}

	public Customer[] getCustomers() throws RollbackException {
		Customer[] customers = match();
		// We want them sorted by last and first names (as per
		// Customer.compareTo());
		return customers;
	}
	
	public Customer getCustomers(String username) throws RollbackException {
		Customer[] customers = match(MatchArg.equals("username", username));
		// We want them sorted by last and first names (as per
		// Customer.compareTo());
		if (customers.length == 0) {
			return null;
		}
		else {
			return customers[0];			
		}
	}
	
	public void create(Customer customer) throws RollbackException {
		try {
			Transaction.begin();
			createAutoIncrement(customer);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
	public Customer readUsers(int customer_id) throws RollbackException {
		Customer[] cb = match(MatchArg.equals("customer_id", customer_id));
		if (cb == null || cb.length == 0) return null;
		return cb[0];
	}


	public void setPassword(String customerName, String password)
			throws RollbackException {
		try {
			
			Customer dbcustomer = read(customerName);
			Transaction.begin();
			if (dbcustomer == null) {
				throw new RollbackException("Customer " + customerName
						+ " no longer exists");
			}

			dbcustomer.setPassword(password);

			update(dbcustomer);
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
}
