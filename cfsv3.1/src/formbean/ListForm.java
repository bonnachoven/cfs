package formbean;

import org.mybeans.form.FormBean;


public class ListForm extends FormBean{
	private String fund_id;
	private String name;
	private String symbol;
	
	public String getFund_id()                { return fund_id;           }
    public String getName()             { return name;         }
    public String getSymbol()          { return symbol;     }

    public void   setFund_id(String s)           { fund_id = s;              }
	public void   setName(String s)      { name = s;            }
	public void   setSymbol(String s)     { symbol= s;        }
}