//Name:Xu Zhao
//Andrew ID:xuzhao
//Course Number:08600
//Date:12/05/2014
package formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean{
    private String name;
    private String symbol;

	
    public String getName()  { return name; }
    public String getSymbol()  { return symbol; }

    


	
    public void setName(String s)  { name = s.trim(); }
    public void setSymbol(String s)  { symbol = s.trim(); }




    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (name == null || name.length() == 0) errors.add("Fund name is required");
        if (symbol == null || symbol.length() == 0) errors.add("Ticker is required");
        if (symbol.length() > 4) errors.add("Ticker name is between 1 to 4 characters");
        if (name.matches(".*[<>?*\"].*")||symbol.matches(".*[<>?*\"].*")) errors.add("Input may not contain angle brackets or quotes");

      
		
     if (errors.size() > 0) return errors;
	return errors;

		
    }
}
