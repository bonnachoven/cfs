//Hua-Ming Lee
//huamingl
//08-600
//hw9
//2014/12/1

package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class LoginForm extends FormBean {
	private String username;
	private String password;
	private String type;

	public void setUsername(String s) { username = s.trim(); }
	public void setPassword(String s) { password = s.trim();	}
    public void setType(String s) {	type = s;}
	
	public String getUsername() {	return username;	}
	public String getPassword() {		return password;	}
    public String getType() {	return type;}
	

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (username == null || username.length() == 0)
			errors.add("Username is required");
		if (password == null || password.length() == 0)
			errors.add("Password is required");

		if (errors.size() > 0)
			return errors;
		
        if (username.matches(".*[<>?*\"].*")) errors.add("User Name may not contain angle brackets or quotes");

		return errors;
	}

}
