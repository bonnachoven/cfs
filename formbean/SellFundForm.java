package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class SellFundForm extends FormBean{
	private String fund_id;
	private String action;
	private String shares;
	
	public String getFund_id ()			        { return fund_id; }
	public String getAction ()			        { return action; }
	public String getShares ()		        	{ return shares; }
	
	public void setFund_id(String s)				{ fund_id = s;    }
	public void setAction(String s) 		    { action  = s;   }
	public void setShares(String s)				{ shares = s;    }
	
	public List<String> getValidationErrors () {
		List<String> errors = new ArrayList<String>();

		try {
        	double d = Double.parseDouble(shares);
        	if (d <= 0 || d > Integer.MAX_VALUE) {
        		throw new Exception();
        	}
        } catch (Exception e) {
        	 errors.add("Please type in postitive numbers.");
        }
		if (errors.size() > 0) 	return errors;
        if (!action.equals("sell")) errors.add("Please press sell button to finish action");
		if (shares == null || shares.length() == 0) errors.add("Please enter the shares of fund you want to sell!");
        
		return errors;
	}	
}
