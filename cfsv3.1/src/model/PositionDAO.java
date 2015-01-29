package model;

import java.util.ArrayList;
import java.util.List;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databean.Customer;
import databean.Position;

public class PositionDAO extends GenericDAO<Position> {
	
	public PositionDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(Position.class, tableName, cp);
	}
	
	public Position[] getFunds(int id) throws RollbackException {
		Position[]funds = match(MatchArg.equals("customer_id", id));
		// We want them sorted by last and first names (as per
		// User.compareTo());
		return funds;
	}
	
	public long getShares(int fund_id, int customer_id){
		long shares=0;
		try{

			Position p = read(customer_id,fund_id);

			if (p == null) {
				System.out.println("posdao error:");
				shares=-1;
				return shares;
			}

			shares= p.getShares();
		}		
		catch (RollbackException e) {
			e.printStackTrace();
			shares=-1;
		} finally {
			//if (Transaction.isActive()) Transaction.rollback();
		}	return shares;
	}
	
	public int getFund_idByCustomer_id(int cusId){
		int Fund_id=0;
		try{
			Transaction.begin();
			Position p = read(cusId);
			
			if (p == null) {
				throw new RollbackException(cusId+" does not have such fund.");
			}

			Fund_id= p.getFund_id();
			Transaction.commit();
		}		
		catch (RollbackException e) {
			e.printStackTrace();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}	return Fund_id;
	}
	
	public List<Position> getAllPositionByCustomer(Customer c) throws RollbackException{
		Position[] pl = match(MatchArg.equals("customer_id", c.getCustomer_id()));
		if(pl.length == 0 || pl == null) return null;
		List<Position> positionList = new ArrayList<Position>();
		for(Object o : pl) {
			positionList.add((Position) o);
		}
		return positionList;
	}
	
	public Position[] getPositions(int customerId) throws RollbackException {
		Position[] list = match(MatchArg.equals("customer_id", customerId));
		return list;
	}
	
}
