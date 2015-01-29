package formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.mybeans.form.FormBean;

public class RequestCheckForm extends FormBean {
	private String amount;
	private double amt;
	public String getAmount() {
		return amount;
	}

	// hope this would not create a problem
	public long getAmountAsLong() {
		// Be sure to first call getValidationErrors() to ensure
		// that NullPointer exception or NumberFormatException will not be
		// thrown!
		return Long.parseLong(amount);
	}

	public void setAmount(String amount) {
		this.amount = amount.trim();
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (amount == null || amount.length() == 0) {
			errors.add("Amount is required");
		
		} else if (amount.matches(".*[<>?*\"].*")) {
			errors.add("Amount cannot contain angle brackets or quotes");
		}
		
		if (errors.size() > 0)
			return errors;
		
		try {
			amt = Double.parseDouble(amount);
			amt = (amt * 100);
			amt = amt / 100;
			if (!checkDecimal(amount)) {
				errors.add("Amount should be in x.xx format(tracked to two decimal places).");
			}
			else if (amt < 0.01) {
				errors.add("Please enter an amount that is greater than $0.01");
			} else if (amt > 1000000000) {
				errors.add("Please enter an amount that is lesser than $1000000000");
			}
		} catch (NumberFormatException nfe) {
			errors.add("Amount must be numerical");
		}
		return errors;
	}

	public boolean checkDecimal(String input) {
		Pattern p = Pattern.compile("[+-]?[0-9]+.{0,1}[0-9]{0,2}");
		return p.matcher(input).matches();
	}

	public double getAmountAsDouble() {
		try {
			return Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}

}
