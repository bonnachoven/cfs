package formbean;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.mybeans.form.FormBean;

public class BuyFundForm extends FormBean {

	private String buyAmount;
	private String fund_id;

	public String getBuyAmount() {
		return buyAmount;
	}
	
	public double getAmountAsDouble() {
		try {
			return Double.parseDouble(buyAmount);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}

	public void setBuyAmount(String buyAmount) {
		this.buyAmount = buyAmount;
	}

	public int getFund_id() {
		return Integer.parseInt(fund_id);
	}
	
	public void setFund_id(String fund_id) {
		this.fund_id = fund_id;
	}
	

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		if (buyAmount == null || buyAmount.length() == 0) {
			errors.add("Please enter an amount.");
		}
		if (fund_id == null || fund_id.length() == 0) {
			errors.add("Please choose a fund");
		}
		if (buyAmount != null && buyAmount.matches(".*[<>?*\"].*"))
			errors.add("Buy amount format error!");
		if (!checkDecimal(buyAmount)) {
			errors.add("Only numbers with a maximum of 2 decimals places are allowed for amount.");
		}
		if (errors.size() > 0)
			return errors;
		try {
			double amt = Double.parseDouble(buyAmount);
			amt = Math.round(amt * 100);
			amt = amt / 100;
			if (amt < 0.01) {
				errors.add("Please enter an amount that is greater than $0.01");
			} else if (amt > 1000000000) {
				errors.add("Please enter an amount that is lesser than $1000000000");
			}
		} catch (NumberFormatException nfe) {
			errors.add("Please enter amount in digits. Do not use letters");
		}

		return errors;
	}

	public boolean checkDecimal(String input) {
		Pattern p = Pattern.compile("[+-]?[0-9]+.{0,1}[0-9]{0,2}");
		return p.matcher(input).matches();
	}

}
