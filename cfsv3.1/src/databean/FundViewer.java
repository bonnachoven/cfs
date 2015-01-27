package databean;

public class FundViewer {
	private String Name;
	private String symbol;
	private String shares;
	private String total;
	private int fund_id;
	
	public FundViewer() {}
	
	public String getName()       {  return Name;  }
	public String getSymbol()         {  return symbol;    }
	public String getShares()         {  return shares;    }
	public String getTotal()          {  return total;     }
	public int  getFund_id()           {  return fund_id;    }
	
	public void setName(String v) {  this.Name = v; }
	public void setSymbol(String v)   {  this.symbol = v;   }
	public void setShares (String v)  {  this.shares = v;   }
	public void setTotal (String v)   {  this.total = v;    }
	public void setFund_id (int v)     {  this.fund_id = v;   }
}
