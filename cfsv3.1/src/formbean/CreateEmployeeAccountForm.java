
package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateEmployeeAccountForm extends FormBean{
    private String username;
    private String password;
    private String firstname;
    private String lastname;

	
    public String getUsername()  { return username; }
    public String getPassword()  { return password; }
	public String getFirstname()  { return firstname; }
    public String getLastname()  { return lastname; }
    


	
    public void setUsername(String s)  { username = s.trim(); }
    public void setPassword(String s)  { password = s.trim(); }
	public void setFirstname(String s)  { firstname = s.trim(); }
    public void setLastname(String s)  { lastname = s.trim(); }
    



    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (firstname == null || firstname.length() == 0) errors.add("employee firstname is required");
        if (lastname == null || lastname.length() == 0) errors.add("employee lastname is required");
        if (username == null || username.length() == 0) errors.add("employee username is required");
        if (password == null || password.length() == 0) errors.add("Password is required");
        if (firstname.matches(".*[<>?*\"].*")||lastname.matches(".*[<>?*\"].*")||username.matches(".*[<>?*\"].*")||password.matches(".*[<>?*\"].*")) errors.add("Input may not contain angle brackets or quotes");

		
     if (errors.size() > 0) return errors;
	return errors;

		
    }
}
