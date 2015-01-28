
package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ResetPwdForm extends FormBean {
	private String username;
	private String action;
	
	public void setUsername(String s) { username = s.trim(); }
	public String getUsername() {	return username;	}
	public void setAction(String s) {
		this.action = s;
	}

	public String getAction() {
		return this.action;
	}


	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (username == null || username.length() == 0)
			errors.add("Username is required");
		
		if (action == null || action.length() == 0)
			errors.add("No action!");

		if (!action.equals("reset")) {
			errors.add("Action Error: it is supposed to be reset, but now it is "
					+ action);
		}

		

		if (errors.size() > 0)
			return errors;
		
        if (username.matches(".*[<>?*\"].*")) errors.add("User Name may not contain angle brackets or quotes");

		return errors;
	}

}
