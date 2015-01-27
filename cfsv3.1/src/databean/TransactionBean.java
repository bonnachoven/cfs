package databean;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.genericdao.PrimaryKey;

@PrimaryKey("transaction_id")
public class TransactionBean implements Comparable<TransactionBean> {
	private int transaction_id;
	private int customer_id;
	private int fund_id;
	private long shares;
	private int transaction_type;
	private long amount;
	private String execute_date;

	public int getTransaction_id() {
		return transaction_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public int getFund_id() {
		return fund_id;
	}

	public long getShares() {
		return shares;
	}

	public int getTransaction_type() {
		return transaction_type;
	}

	public long getAmount() {
		return amount;
	}

	public String getExecute_date() {
		return execute_date;
	}
	
	public void setTransaction_id(int i) {
		transaction_id = i;
	}

	public void setCustomer_id(int i) {
		customer_id = i;
	}

	public void setFund_id(int i) {
		fund_id = i;
	}

	public void setShares(long s) {
		shares = s;
	}

	public void setTransaction_type(int i) {
		transaction_type = i;
	}

	public void setAmount(long l) {
		amount = l;
	}

	public void setAmount(Double d) {
		d = d * 100;
		amount = d.longValue();
	}

	public void setExecute_date(String s) {
		execute_date = s;
	}

	public int compareTo(TransactionBean t) {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		df.setLenient(false);
		try {
			return df.parse(this.execute_date).compareTo(
					df.parse(t.execute_date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
