//Name:Xu Zhao
//Andrew ID:xuzhao
//Course Number:08600
//Date:12/05/2014
package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;
import model.FundDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databean.Fund;
import formbean.ListForm;

/*
 * Looks up the photos for a given "user".
 * 
 * If successful:
 *   (1) Sets the "userList" request attribute in order to display
 *       the list of users on the navbar.
 *   (2) Sets the "photoList" request attribute in order to display
 *       the list of given user's photos for selection.
 *   (3) Forwards to list.jsp.
 */
public class ListAction extends Action {
	private FormBeanFactory<ListForm> formBeanFactory = FormBeanFactory
			.getInstance(ListForm.class);

	private FundDAO FundDAO;

	public ListAction(Model model) {
		FundDAO = model.getFundDAO();
	}

	public String getName() {
		return "list.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up the request attributes (the errors list and the form bean so
		// we can just return to the jsp with the form if the request isn't
		// correct)
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			ListForm form = formBeanFactory.create(request);
			// request.setAttribute("form",form);
			// request.setAttribute("fundList", FundDAO.getFunds());
			Fund[] FundList = FundDAO.getFunds();

			request.setAttribute("FundList", FundList);
			request.setAttribute("Fund", FundList[0]);


			// Set up user list for nav bar
			if (!form.isPresent()) {
				return "listFund.jsp";
			}
			//String a = form.getFundId();

			Fund fund = new Fund();
			fund.setFund_id(Integer.parseInt(form.getFund_id()));
			fund.setName(form.getName());
			fund.setSymbol(form.getSymbol());

			HttpSession session = request.getSession();
			session.setAttribute("Fund", fund);

			return "researchFund.do";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "error.jsp";
		}
	}
}
