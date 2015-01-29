//Name:Xu Zhao
//Andrew ID:xuzhao
//Course Number:08600
//Date:12/05/2014
package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ChangePwdForm extends FormBean {
	private String confirmPassword;
	private String newPassword;
	private String currentPassword;
	private String action;
	
	public String getConfirmPassword() { return confirmPassword; }
	public String getNewPassword()     { return newPassword;     }
	public String getCurrentPassword()    { return currentPassword; }
	public String getAction()		   { return action;          }
	
	public void setConfirmPassword(String s) { confirmPassword = s.trim(); }
	public void setNewPassword(String s)     { newPassword     = s.trim(); }
	public void setCurrentPassword(String s) { currentPassword = s.trim(); }
	public void setAction(String s) 	     { action  = s;  }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (newPassword == null || newPassword.length() == 0) {
			errors.add("New Password is required.");
		}
		
		if (confirmPassword == null || confirmPassword.length() == 0) {
			errors.add("Confirm Password is required.");
		}
		
		if (currentPassword == null || currentPassword.length() == 0) {
			errors.add("Original password is required.");
		}
		
		if (action == null)
			errors.add("Button is required to complete action.");
		
		if (errors.size() > 0) {
			return errors;
		}
	      if (confirmPassword.matches(".*[<>?*\"].*")||currentPassword.matches(".*[<>?*\"].*")||newPassword.matches(".*[<>?*\"].*")) errors.add("Input may not contain angle brackets or quotes");

		if (!action.equals("change"))
			errors.add("Invalid button");
		
		if (!newPassword.equals(confirmPassword)) {
			System.out.println("new Password: " + newPassword);
			System.out.println("confirmPassword :" + confirmPassword);
			errors.add("Passwords do not match");
		}

		return errors;
	}
}
