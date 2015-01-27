package databean;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import org.genericdao.PrimaryKey;

@PrimaryKey("fund_id,price_date")
public class Fund_Price_History {
	private int fund_id = -1;
	private String price_date = null;
	private long price = 0;

	public int getFund_id() {
		return fund_id;
	}

	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}

	public String getPrice_date() {
		return price_date;
	}
	
	public Date igetPrice_date_formatted() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar  c =Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.parseInt(price_date.substring(0,4)));
		c.set(Calendar.MONTH, Integer.parseInt(price_date.substring(5,7)) - 1);
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(price_date.substring(8,10)));
		return c.getTime();
		
	}

	public void setPrice_date(String price_date) {
		this.price_date = price_date;
	}
	
	public void isetPrice_date_formatted(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		this.price_date = formatter.format(date);
	}
	

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
}
