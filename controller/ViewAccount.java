package controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.FundDAO;
import model.Fund_Price_HistoryDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;

import databean.Customer;
import databean.Fund;
import databean.Fund_Price_History;
import databean.Position;
import databean.TransactionBean;
import databean.ViewAccountRecord;

public class ViewAccount extends Action {

	private CustomerDAO customerDAO;
	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private Fund_Price_HistoryDAO fundPriceHistoryDAO;

	// constructor
	public ViewAccount(Model model) {
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFund_Price_HistoryDAO();
	}

	// get action name
	public String getName() {
		return "viewAccAction.do";
	}

	// return next page name
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession(false);
		session.setAttribute("errors", errors);
		DecimalFormat cash = new DecimalFormat(	"###,##0.00");
		

		// get customer
		Customer customer = (Customer) session.getAttribute("customer");
		request.setAttribute("cash",cash.format(customer.getCash() / 100.0));

		if (customer == null)
			return "login.jsp";

		// get last transaction day
		try {
			TransactionBean[] transactionlist = transactionDAO
					.getTransactions(customer.getCustomer_id());
			if (transactionlist.length == 0) {
				session.setAttribute("lastTransactionDay",
						"There is no record of any transaction");
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date lastTransactionDay = sdf.parse(transactionlist[0]
						.getExecute_date());
				for (TransactionBean transaction : transactionlist) {
					if (sdf.parse(transaction.getExecute_date()).after(
							lastTransactionDay)) {
						lastTransactionDay = sdf.parse(transaction
								.getExecute_date());
					}
				}
				session.setAttribute("lastTransactionDay",
						sdf.format(lastTransactionDay));
			}

		} catch (RollbackException e) {
			e.printStackTrace();
			return "error.jsp";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error.jsp";
		}

		// get position and fund values
		try {
			Position[] positionList = positionDAO.getPositions(customer
					.getCustomer_id());
			ArrayList<ViewAccountRecord> records = new ArrayList<ViewAccountRecord>();
			Double value = 0.0;
			for (Position position : positionList) {
				int fund_id = position.getFund_id();
				long shares = position.getShares();
				Fund fund = fundDAO.getFunds(fund_id);
				long latestprice = 0;
				Date latestDate = null;

				Fund_Price_History[] fphList = fundPriceHistoryDAO
						.getFundPrice(fund_id);
				for (Fund_Price_History fph : fphList) {
					if (latestDate == null) {
						latestDate = fph.getPrice_date_formatted();
						latestprice = fph.getPrice();
					} else {
						if (fph.igetPrice_date_formatted().after(latestDate)) {
							latestDate = fph.getPrice_date_formatted();
							latestprice = fph.getPrice();
						}
					}
				}
				// now we have already got latestprice
				ViewAccountRecord record = new ViewAccountRecord();
				// Format price
				String sLatestPrice = String.valueOf(latestprice);
				record.setCurrentPrice(sLatestPrice.substring(0,
						sLatestPrice.length() - 2)
						+ "."
						+ sLatestPrice.substring(sLatestPrice.length() - 2));

				// Format shares
				String sShares = String.valueOf(shares);
				record.setShares(sShares.substring(0, sShares.length() - 3)
						+ "." + sShares.substring(sShares.length() - 3));

				// count value
				System.out
						.println("Double.value of record.getCurrentPrice() = "
								+ Double.valueOf(record.getCurrentPrice()));
				Double dPrice = Double.valueOf(record.getCurrentPrice().trim());
				Double dShare = Double.valueOf(record.getShares().trim());
				value += dPrice * dShare;

				// set fund name
				record.setFundName(fund.getName());
				// set ticker
				record.setFundTicker(fund.getSymbol());
				records.add(record);

			}
			session.setAttribute("records", records);
			session.setAttribute("value",cash.format(value));
			System.out.println("total value = " + value);

		} catch (RollbackException e) {
			e.printStackTrace();
		}

		return "view-account.jsp";
	}

}
