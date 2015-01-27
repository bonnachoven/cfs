package model;

import java.util.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databean.Fund_Price_History;


public class Fund_Price_HistoryDAO extends GenericDAO<Fund_Price_History> {
	public Fund_Price_HistoryDAO(ConnectionPool pool,String tableName) 
			throws DAOException {
		super(Fund_Price_History.class, tableName, pool);
	}
//	public void create(Fund_Price_History fph) throws RollbackException {
//			create(fph);
//		}
	/*
	 * Given a date, return all fund price history in that day
	 */
	public Fund_Price_History[] getFundPrice(Date price_date)  throws RollbackException {
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals("price_date", price_date));
		return Fund_Price_Historys;
	}
	public String getLastTransitionDayDate() throws RollbackException{
        Fund_Price_History[] Fund_Price_Historys = match();
        if(Fund_Price_Historys.length == 0) return null;
        return Fund_Price_Historys[Fund_Price_Historys.length-1].getPrice_date();
    }

	public Fund_Price_History[] getFundPrice(String symbol)
			throws RollbackException {
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals(
				"symbol", symbol));
		if(Fund_Price_Historys.length == 0) return null;
		return Fund_Price_Historys;
	}
	
	
	public long getCurrentPrice(String Symbol) throws RollbackException{
		
		long prices = 0;
		Fund_Price_History[] fundPriceHistorys = this.getFundPrice(Symbol);
		prices = fundPriceHistorys[fundPriceHistorys.length-1].getPrice();
		return prices;
	}
	
	public Fund_Price_History getLatestFundPrice(String symbol)
			throws RollbackException {
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals(
				"symbol", symbol));
		if(Fund_Price_Historys.length == 0) return null;
		return Fund_Price_Historys[Fund_Price_Historys.length-1];
	}
	
	public Fund_Price_History getLatestFundPrice(int fund_id)
			throws RollbackException {
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals(
				"fund_id", fund_id));
		if(Fund_Price_Historys.length == 0) return null;
		return Fund_Price_Historys[Fund_Price_Historys.length-1];
	}
	
	/*
	 * Given start date and end date, and fund_id, return all fund price history between the dates.
	 */
	//////////////////////////problem: how to match three arguments?
	//////////////////////////choices: return all, but the array may be really large
	/* choice one
	public Fund_Price_History[] getFundPrice(int fund_id, Date price_date_start, Date price_date_end)
			throws RollbackException {
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals("fund_id", fund_id));
		return Fund_Price_Historys;
	}
	*/
	
	/*
	 * choice two
	 * problem: results need to be sorted by date.
	 */
	public Fund_Price_History[] getFundPrice(int fund_id) throws RollbackException {
		Fund_Price_History[] Fund_Price_Historys = match(MatchArg.equals("fund_id", fund_id));
		return Fund_Price_Historys;
	}
}
