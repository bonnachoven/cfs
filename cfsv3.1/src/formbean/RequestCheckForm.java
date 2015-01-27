package formbean;	

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class RequestCheckForm extends FormBean {
	private String amount;

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
			return errors;
		}

		try {
		 long amt = Long.parseLong(amount.trim());
			if (amt <0 || amt==0) {
				errors.add("Amount cannot be negative!");
				
			}
		} catch (NumberFormatException e) {
			errors.add("Amount is not an integer");
		}
		return errors;

	}

}
