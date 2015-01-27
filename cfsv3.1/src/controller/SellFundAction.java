package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanFactory;

import databean.Customer;
import databean.FundViewer;
import databean.Position;
import databean.TransactionBean;
import formbean.SellFundForm;

public class SellFundAction extends Action {
	private FormBeanFactory<SellFundForm> formBeanFactory = FormBeanFactory
			.getInstance(SellFundForm.class);

	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;

	public String getName() {
		return "sellFund.do";
	}

	public SellFundAction(Model m) {
		fundDAO = m.getFundDAO();
		positionDAO = m.getPositionDAO();
		customerDAO = m.getCustomerDAO();
		transactionDAO = m.getTransactionDAO();
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();

		List<String> errors = new ArrayList<String>();
		session.setAttribute("errors", errors);

		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null)
			return "login.jsp";

		try {
			SellFundForm form = formBeanFactory.create(request);

			DecimalFormat df3 = new DecimalFormat("#,##0.000");
			FundViewer[] fundList = null;
			Position[] pl = positionDAO.getFunds(customer.getCustomer_id());

			if (pl != null && pl.length > 0) {
				fundList = new FundViewer[pl.length];

				// prepare list of funds of the customer
				for (int i = 0; i < pl.length; i++) {
					fundList[i] = new FundViewer();
					fundList[i].setFund_id(pl[i].getFund_id());
					fundList[i].setName(fundDAO.read(pl[i].getFund_id())
							.getName());
					fundList[i].setSymbol(fundDAO.read(pl[i].getFund_id())
							.getSymbol());
					fundList[i].setShares(df3.format(transactionDAO
							.getValidShares(customer.getCustomer_id(),
									pl[i].getShares() / 1000.0,
									pl[i].getFund_id())));
				}
			}

			// error prevention
			session.setAttribute("fundList", fundList);
			if (!form.isPresent()) {
				session.removeAttribute("msg");
				return "sellFund.jsp";
			}

			errors.addAll(form.getValidationErrors());

			if (errors.size() > 0) {
				session.removeAttribute("msg");
				return "sellFund.jsp";
			}
				

			Double sharesSell = Double.parseDouble(form.getShares());
			Double sharesHeld = transactionDAO
					.getValidShares(
							customer.getCustomer_id(),
							positionDAO.read(customer.getCustomer_id(),
									Integer.parseInt(form.getFund_id()))
									.getShares() / 1000.0, Integer
									.parseInt(form.getFund_id()));

			if (sharesSell > sharesHeld) {
				errors.add("The shares you want to sell exceeds the shares you have!");
			}
			if (sharesSell < 0.001) {
				errors.add("Please type in shares bigger than 0.001!!");
			}

			if (errors.size() > 0)
				return "sellFund.jsp";

			TransactionBean transbean = new TransactionBean();
			transbean.setTransaction_type(3);
			transbean.setExecute_date(null);
			transbean.setCustomer_id(customer.getCustomer_id());
			transbean.setFund_id(Integer.parseInt(form.getFund_id()));
			transbean.setShares((long) (sharesSell * 1000.0));
			transactionDAO.create(transbean);
			session.removeAttribute("form");
			request.setAttribute(
					"msg",
					"You had successfully sold your fund, transaction will be finished on the next transition day");

			fundList = null;
			pl = positionDAO.getFunds(customer.getCustomer_id());

			// show the new form to customer (after sell fund action)
			if (pl != null && pl.length > 0) {
				fundList = new FundViewer[pl.length];

				for (int i = 0; i < pl.length; i++) {
					fundList[i] = new FundViewer();
					fundList[i].setFund_id(pl[i].getFund_id());
					fundList[i].setName(fundDAO.read(pl[i].getFund_id())
							.getName());
					fundList[i].setSymbol(fundDAO.read(pl[i].getFund_id())
							.getSymbol());
					fundList[i].setShares(df3.format(transactionDAO
							.getValidShares(customer.getCustomer_id(),
									pl[i].getShares() / 1000.0,
									pl[i].getFund_id())));
				}
			}
			session.setAttribute("fundList", fundList);
			return "sellFund.jsp";

		} catch (Exception e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
