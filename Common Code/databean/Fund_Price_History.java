package databean;

import java.sql.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("fund_id,price_date")
public class Fund_Price_History {
	private int fund_id = -1;
	private Date price_date = null;
	private long price = 0;

	public int getFund_id() {
		return fund_id;
	}

	public void setFund_id(int fund_id) {
		this.fund_id = fund_id;
	}

	public Date getPrice_date() {
		return price_date;
	}

	public void setPrice_date(Date price_date) {
		this.price_date = price_date;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}
}
