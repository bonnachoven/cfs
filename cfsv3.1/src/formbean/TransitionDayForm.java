
package formbean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mybeans.form.FormBean;

public class TransitionDayForm extends FormBean{
	private String date;
	//private long price;
	private String[] price;
	private int fund_id;
	
	
	
	public int getFund_id ()			        { return fund_id; }
	public String[] getPrice ()			        { return price; }
	public String getDate ()		        	{ return date; }
	
	public void setFund_id(int s)				{ fund_id = s;    }
	public void setPrice(String[] l) 		    { price  = l;   }
	public void setDate(String d)				{ date = d;    }
	
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
					Date date = formatter.parse(getDate());
					if(lastdate==null)
					{
						System.out.println("inside if");
					     
					}
					else
					{
						Date last=formatter.parse(lastdate);
						if(last.after(date))
						{
							errors.add("enter a date after the last transition day");
						}
						else if(lastdate.equals(getDate()))
						{
							errors.add("duplicate date entered");
						}
					}
			 
				} catch (Exception e) {
					errors.add("enter valid date");
					e.printStackTrace();
				}
		 }
		 }
		for(int i=0;i<price.length;i++){
		try {
        	if (Long.parseLong(price[i])<=0) {
        		System.out.println("long error"+price);
        		
        		errors.add("enter a positive value");
        	}
        	else if(Long.parseLong(price[i])>1000000000)
        	{
        		errors.add("Entered value overflows");
        	}
        } catch (Exception e) {
        	 errors.add("Please enter valid integer");
        }
		}
		
		/* if (!action.equals("Transit Day")) errors.add("Please press Transition Day button to finish action");
		*/	
       
		return errors;
	}	
}
