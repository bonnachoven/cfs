
package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateCustomerAccountForm extends FormBean{
    private String username;
    private String firstname;
    private String lastname;
    private String password;
	private String addr_line1;
	private String addr_line2;
	private String state;
    private String zip;
	private String city;

	public String getUsername()    { return username; }
	public String getFirstname()    { return firstname; }
	public String getLastname()     { return lastname;  }
	public String getPassword()     { return password;  }
	public String getAddr_line1() 	{ return addr_line1; }
	public String getAddr_line2() 	{ return addr_line2;	}
	public String getState() 			{ return state;		}
	public String getZip() 			{ return zip;		}
	public String getCity() 		{ return city;		}


	
 


	
    public void setUsername(String s)  { username = s.trim(); }
    public void setPassword(String s)  { password = s.trim(); }
	public void setFirstname(String s)  { firstname = s.trim(); }
    public void setLastname(String s)  { lastname = s.trim(); }
    public void setAddr_line1(String s)    {	addr_line1  = s.trim(); }
	public void setAddr_line2(String s)    {	addr_line2  = s.trim(); }
	public void setState(String s) 		  { state       = s.trim();	}
	public void setCity(String s) 		  { city       = s.trim();	}
	public void setZip(String s) 		  { zip        = s.trim(); }
    



    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (firstname == null || firstname.length() == 0) errors.add("customer firstname is required");
        if (lastname == null ||lastname.length() == 0) errors.add("customer lastname is required");
        if (username == null || username.length() == 0) errors.add("customer username is required");
        if (password == null || password.length() == 0) errors.add("Password is required");
        if (firstname.matches(".*[<>?*\"].*")||lastname.matches(".*[<>?*\"].*")||username.matches(".*[<>?*\"].*")||password.matches(".*[<>?*\"].*")) errors.add("Input may not contain angle brackets or quotes");

     
	
		
     if (errors.size() > 0) return errors;
	return errors;

		
    }
}
