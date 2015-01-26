package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.FundDAO;
import model.Fund_Price_HistoryDAO;
import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.Fund;
import databean.FundItem;
import databean.TransactionBean;
import formbean.BuyFundForm;

public class BuyFundAction extends Action {
	private FormBeanFactory<BuyFundForm> formBeanFactory = FormBeanFactory
			.getInstance(BuyFundForm.class);

	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	private TransactionDAO transactionDAO;
	private Fund_Price_HistoryDAO fundPriceHisotryDAO;

	public BuyFundAction(Model model) {
		this.fundDAO = model.getFundDAO();
		this.transactionDAO = model.getTransactionDAO();
		this.customerDAO = model.getCustomerDAO();
		this.fundPriceHisotryDAO = model.getFund_Price_HistoryDAO();
	}

	public String getName() {
		return "buyFund.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession(false);
		session.setAttribute("errors", errors);

		DecimalFormat latestPrice = new DecimalFormat("#,##0.00");
		DecimalFormat shares = new DecimalFormat("#,##0.000");

		try {
			if (request.getSession().getAttribute("customer") == null) {
				errors.add("Please Log in first");
				return "login.jsp";
			}

			Customer customer = customerDAO.getCustomers(((Customer) request
					.getSession().getAttribute("customer")).getUsername());
			

			// get fund name;
			Fund[] fundList = fundDAO.getFunds();

			// fundTable to show funds information
			List<FundItem> fundTable = new ArrayList<FundItem>();
			// add fund table rows
			if ((fundList != null) && (fundList.length > 0)) {
				System.out.println("now start to buy fund");
				for (int i = 0; i < fundList.length; i++) {
					FundItem row = new FundItem();
					row.setName(fundList[i].getName());
					row.setSymbol(fundList[i].getSymbol());
					row.setFund_id(fundList[i].getFund_id());
					double displayPrice = 0;
					if (fundPriceHisotryDAO.getLatestFundPrice(fundList[i]
							.getFund_id()) != null) {
						displayPrice = fundPriceHisotryDAO.getLatestFundPrice(
								fundList[i].getFund_id()).getPrice();
					}
					displayPrice = displayPrice / 100;
					System.out.println(displayPrice);
					row.setLatestPrice(latestPrice.format(displayPrice));
					fundTable.add(row);
				}
			}

			session.setAttribute("fundTable", fundTable);

			BuyFundForm form = formBeanFactory.create(request);
			session.setAttribute("form", form);
			if (form.isPresent()) {
				TransactionBean transaction = new TransactionBean();
				transaction.setCustomer_id(customer.getCustomer_id());
				transaction.setFund_id(form.getFund_id());
				System.out.println(form.getFund_id());
				transaction.setExecute_date(null);
				transaction.setShares(0);
				transaction.setTransaction_type(4);
				Double amount = form.getAmountAsDouble() * 100.0;
				transaction.setAmount(amount.longValue());
				transactionDAO.create(transaction);
			}
			
			return "buyFund.jsp";

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		}
	}

}
