
package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CustomerSearchForm extends FormBean {
	private String username;
	

	public String getUsername() {
		return username;
	}

	
	public void setUsername(String s) {
		username = s.trim();
	}

	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		if (username == null || username.length() == 0) {
			errors.add("Username is required");
		}

		if (errors.size() > 0) {
			return errors;
		}


		return errors;
	}
}
