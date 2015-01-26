package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean {
	private String amount;


	public String getAmount() {
		return amount;
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
		if (errors.size() > 0) {
			return errors;
		}
		return errors;

	}
	
}
