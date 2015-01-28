
package formbean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mybeans.form.FormBean;

public class TransitionDayForm extends FormBean{
	private String date;
	private long price;
	private int fund_id;
	private String action;
	public TransitionDayForm(){}
	public int getfund_id ()			        { return fund_id; }
	public long getprice ()			        { return price; }
	public String getdate ()		        	{ return date; }
	public String getaction()	{return action;}
	
	public void setfund_id(int s)				{ fund_id = s;    }
	public void setprice(long l) 		    { price  = l;   }
	public void setdate(String d)				{ date = d;    }
	public void setaction(String s) 		{ action=s;}
	
	public List<String> getValidationErrors (String lastdate) {
		List<String> errors = new ArrayList<String>();
		System.out.println(lastdate);
		 if (date == null || date.length() == 0)
		 {
			 errors.add("Please enter the transition day!");
		 }
		 else {
			 if(date.length()>10)
			 {
				 errors.add("enter a valid date");
			 }
			 else 
			 {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = formatter.parse(getdate());
					if(lastdate==null)
					{
						System.out.println("inside if");
					     
					}
					else
					{
						Date last=formatter.parse(lastdate);
						if(last.after(date))
						{
							System.out.println("date,check:"+last+date);
						     
							errors.add("enter a date after the last transition day");
						}
					}
			 
				} catch (Exception e) {
					errors.add("enter valid date");
					e.printStackTrace();
				}
		 }
		 }
		
		try {
        	if (price <= 0) {
        		System.out.println("long error"+price);
        		
        		errors.add("enter a positive value");
        	}
        	else if(price>1000000000)
        	{
        		errors.add("Entered value overflows");
        	}
        } catch (Exception e) {
        	 errors.add("Please enter valid integer");
        }
		if (errors.size() > 0) 	return errors;
		/* if (!action.equals("Transit Day")) errors.add("Please press Transition Day button to finish action");
		*/	
       
		return errors;
	}	
}
