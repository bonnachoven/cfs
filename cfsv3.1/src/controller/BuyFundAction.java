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

			
			Double validBalance = transactionDAO.getValidBalance(
					customer.getCustomer_id(),
					(customer.igetCashAsDouble()));

			request.setAttribute("balance", validBalance);
			
			
			// get fund name;
			Fund[] fundList = fundDAO.getFunds();

			// fundTable to show funds information
			List<FundItem> fundTable = new ArrayList<FundItem>();
			// add fund table rows
			if ((fundList != null) && (fundList.length > 0)) {

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

			request.setAttribute("form", form);
			if (form.isPresent()) {
				
				errors.addAll(form.getValidationErrors());
		        if (errors.size() != 0) {
		            return "buyFund.jsp";
		        }
		        
				TransactionBean transaction = new TransactionBean();
				transaction.setCustomer_id(customer.getCustomer_id());
				transaction.setFund_id(form.getFund_id());
				transaction.setExecute_date(null);
				transaction.setShares(0);
				transaction.setTransaction_type(4);
				
				Double amount = form.getAmountAsDouble();
				System.out.println("Amount in the form is " + amount);
				if (amount > validBalance) {
					errors.add("You don't have enough balance to buy the fund");
				}
				else {
					validBalance -= amount;
					transaction.setAmount(amount);
					transactionDAO.create(transaction);
					request.setAttribute("balance", validBalance);
					request.removeAttribute("form");
				}
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
