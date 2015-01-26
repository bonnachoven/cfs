package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("customer_id,fund_id")
public class Position {
	private int    fund_id;
	private int    customer_id;
	private long   shares;
	
	public int    getFund_id()          { return fund_id;        }
	public int    getCustomer_id()      { return customer_id;    }
    public long   getShares()          { return shares;        }

    public void   setFund_id(int i)      { fund_id = i;         }
	public void   setCustomer_id(int i)  { customer_id = i;     }
	public void   setShares(long l)     { shares= l;          }
	
}
