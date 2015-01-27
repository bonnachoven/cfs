package databean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;




@PrimaryKey("username")
public class Employee implements Comparable<Employee> {
	private int employee_id;
	private String  username = null;
	private int     salt           = 0;
	private String password;
	private String  firstname      = null;
	private String  lastname       = null;
	

	

	public boolean checkPassword(String password) {
		return this.password.equals(password);
	}
	
	public int compareTo(Employee other) {
		// Order first by lastName and then by firstName
		int c = lastname.compareTo(other.lastname);
		if (c != 0) return c;
		c = firstname .compareTo(other.firstname );
		if (c != 0) return c;
		return username.compareTo(other.username);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Employee) {
			Employee other = (Employee) obj;
			return (other.employee_id == employee_id);
		}
		return false;
	}

	public int getEmployee_id() {	return this.employee_id; }
	public String getPassword()     { return password;  }
	public String  getUsername()       { return username;       }
	public int     getSalt()           { return salt;           }
	public String  getFirstname()      { return firstname;      }
	public String  getLastname()       { return lastname;       }
	public int     hashCode()          { return username.hashCode(); }
	
	
	public void setEmployee_id (int x ) { this.employee_id = x;}
	public void setPassword(String s)     {this.password = s;}
	public void setSalt(int x)               { salt = x;           }
	public void setFirstname(String s)       { firstname = s;      }
	public void setLastname(String s)        { lastname = s;       }
	public void setUsername(String s)        { username = s.trim();       }
	
	public String toString() {
		return "employee("+getUsername()+")";
	}

	private String hash(String clearPassword) {
		if (salt == 0) return null;

		MessageDigest md = null;
		try {
		  md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
		  throw new AssertionError("Can't find the SHA1 algorithm in the java.security package");
		}

		String saltString = String.valueOf(salt);
		
		md.update(saltString.getBytes());
		md.update(clearPassword.getBytes());
		byte[] digestBytes = md.digest();

		// Format the digest as a String
		StringBuffer digestSB = new StringBuffer();
		for (int i=0; i<digestBytes.length; i++) {
		  int lowNibble = digestBytes[i] & 0x0f;
		  int highNibble = (digestBytes[i]>>4) & 0x0f;
		  digestSB.append(Integer.toHexString(highNibble));
		  digestSB.append(Integer.toHexString(lowNibble));
		}
		String digestStr = digestSB.toString();

		return digestStr;
	}

	private int newSalt() {
		Random random = new Random();
		return random.nextInt(8192)+1;  // salt cannot be zero
	}

}

