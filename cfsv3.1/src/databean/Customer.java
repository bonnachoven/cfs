
package databean;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;

@PrimaryKey("customer_id")
public class Customer {
	
	private int    customer_id;
    private String username;
    private String firstname;
    private String lastname;
	private String password;
	private long cash;
	private String addr_line1;
	private String addr_line2;
	private String zip;
	private String city;
	private String state;
	
	private int     salt           = 0;
	
	public int    getCustomer_id()   { return customer_id;}
	public String getFirstname()    { return firstname; }
	public String getLastname()     { return lastname;  }
	public String getPassword()     { return password;  }
	public String getUsername() 	{ return username;	}
	public long   getCash() 		{ return cash;		}
	public Double igetCashAsDouble() {
		return Double.parseDouble(String.valueOf(cash));
	}
	public String getAddr_line1() 	{ return addr_line1; }
	public String getAddr_line2() 	{ return addr_line2;}
	public String getZip() 			{ return zip;		}
	public String getCity() 		{ return city;		}
	public String getState() 		{ return state;		}


	public void setCustomer_id(int i) 	  {	customer_id = i; }
	public void setUsername(String s) 		  {	username      = s; }
	public void setPassword(String s)     {this.password = s;}
    public void setFirstname(String s) 	  {	firstname  = s; }
	public void setLastname(String s)     {	lastname   = s; }
	public void setCash(long l) 		  {	cash       = l;	}
	public void setAddr_line1(String s)    {	addr_line1  = s; }
	public void setAddr_line2(String s)    {	addr_line2  = s; }
	public void setCity(String s) 		  { city       = s;	}
	public void setZip(String s) 		  { zip        = s; }
	public void setState(String s) 		  { state       = s; }

	
	public int     getSalt()           { return salt;           }
	
	public void setSalt(int x)               { salt = x;           }
	
	public int compareTo(Customer other) {
		// Order first by lastName and then by firstName
		int c = lastname.compareTo(other.lastname);
		if (c != 0) return c;
		c = firstname.compareTo(other.firstname);
		if (c != 0) return c;
		return username.compareTo(other.username);
	}
	
	
	public boolean checkPassword(String password) {
		return this.password.equals(password);
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
