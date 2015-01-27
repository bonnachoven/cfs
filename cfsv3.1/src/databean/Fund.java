//Name:Xu Zhao
//Andrew ID:xuzhao
//Course Number:08600
//Date:12/05/2014
package databean;



import org.genericdao.PrimaryKey;

@PrimaryKey("fund_id")
public class Fund {
	private int    fund_id;
	private String name;
	private String symbol;
	
	public int    getFund_id()                { return fund_id;           }
    public String getName()              { return name;         }
    public String getSymbol()                { return symbol;           }

    public void   setFund_id(int i)           { fund_id = i;              }
	public void   setName(String s)      { name = s;            }
	public void   setSymbol(String s)        { symbol= s;               }
	
}
