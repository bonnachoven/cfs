/*skandan chockalingam - schockal@andrew.cmu.edu
Java J2EE Programming - Assignment 9
 12/10/2014
*/
/*skandan chockalingam - schockal@andrew.cmu.edu
Java J2EE Programming - Assignment 9
 12/10/2014
*/
package databean;

import java.text.DecimalFormat;

public class HistoryBean {
	private int customer_id;
    private String execute_date;
    private long shares;
    private int transaction_type;
    private long amount;
    private String name;
    private String symbol;
    private int transaction_id;

    public int gettransaction_id(){return transaction_id;}
    public int getCustomer_id()	{return customer_id;}
    public String getExecute_date()        { return execute_date; }
    public long getShares()		{return shares;}
    public int getTransaction_type()		{return transaction_type;}
    public long getAmount()	{return amount;}
    public String getName()	{return name;}
    public String getSymbol()	{return symbol;}
    
    public void settransaction_id(int id){transaction_id=id;}
    public void setcustomer_id(int i) {customer_id=i;}
    public void setexecute_date(String s)  { execute_date = s;    }
    public void setshares(long e)	{
    	/*double s=e/1000;
    	shares=Double.parseDouble(new DecimalFormat("#####.###").format(s));
*/	shares=e;
    	 }
    public void settransaction_type(int name) {transaction_type=name;}
    public void setamount(long amt){
    	/*amount=Double.parseDouble(new DecimalFormat().format(amt/1000));*/
    	System.out.println("entered:");
    	amount=amt;
    }
    public void setname(String nam){name=nam;}
    public void setsymbol(String symb){ symbol=symb;}
}
