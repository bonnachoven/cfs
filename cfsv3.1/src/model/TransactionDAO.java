package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.Customer;
import databean.TransactionBean;


public class TransactionDAO extends GenericDAO<TransactionBean> {
	public TransactionDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(TransactionBean.class, tableName, cp);
	}
	
	public TransactionBean[] getAllPendingTrans () throws RollbackException {
		TransactionBean[] tbs = null;
		try {
			Transaction.begin();
			tbs =  match(MatchArg.equals("execute_date", null));
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
		return tbs;
	}
	
	public TransactionBean[] getTransactions(int customer_id) throws RollbackException {
		TransactionBean[] list = match(MatchArg.equals("customer_id", customer_id));
		return list;
	}
	
	public TransactionBean[] getTransactions(int customer_id, String transaction_type) throws RollbackException {
		MatchArg matchArg1 = MatchArg.equals("customerId", customer_id);
		MatchArg matchArg2 = MatchArg.equals("transaction_type", transaction_type);
		TransactionBean[] list =  match(MatchArg.and(matchArg1,matchArg2));
		return list;
	}
		
	public double getValidShares (int customer_id, double shares, int fund_id) throws RollbackException {
		TransactionBean[] tbs = null;
		try {
			Transaction.begin();
			
			tbs =  match(MatchArg.equals("execute_date", null), MatchArg.equals("customer_id", customer_id));
			
			if (tbs != null) {
				for (TransactionBean t : tbs) {
					switch(t.getTransaction_type()) {
					case 3:
						if (t.getFund_id() == fund_id) {
							shares -= t.getShares() / 1000.0;			
						}
						break;
					default:
						break;
					}
				}
			}
			
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		
		return shares;
	}
	
	public double getValidBalance (int customer_id, double amount) throws RollbackException {
		TransactionBean[] tbs = null;
		try {
			Transaction.begin();
			tbs =  match(MatchArg.equals("execute_date", null), MatchArg.equals("customer_id", customer_id));
			if (tbs != null) {
				for (TransactionBean t : tbs) {
					switch(t.getTransaction_type()) {

					case 4:    //buy fund
						amount -= t.getAmount() / 100.00;
						break;
					case 2:    //request check
						amount -= t.getAmount() / 100.00;
						break;
					case 1:    //deposit check
						amount += t.getAmount() / 100.00;
						break;	
					default:   //sell fund
						break;
					}
				}
			}
			
			Transaction.commit();
		} finally {if (Transaction.isActive()) Transaction.rollback();}
		
		return amount;
	}
	
	public String getLastDate(Customer c) throws RollbackException{
		TransactionBean[] transaction = match(MatchArg.notEquals("execute_date", null), MatchArg.equals("customer_id", c.getCustomer_id()));
		if(transaction.length == 0) return null;
		Arrays.sort(transaction);
		return transaction[transaction.length-1].getExecute_date();
	}
	
	public Date getLatestDate () throws RollbackException, ParseException {
		Date date = null;
		try {
			Transaction.begin();
		
			TransactionBean[] t =  match(MatchArg.notEquals("execute_date", null));
			if (t != null && t.length != 0) {
				Arrays.sort(t);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				date = dateFormat.parse(t[t.length - 1].getExecute_date());
			}
			
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
		return date;
	}
}