package databean;

public class FundItem {

	private String name;
	private String symbol;
	private String latestPrice;
	private int fund_id;

	public FundItem() {
	}

	public FundItem(Fund f, String latestPrice) {
		this.name = f.getName();
		this.symbol = f.getSymbol();
		this.latestPrice = latestPrice;
	}

	public void setFund_id(int id) {
		this.fund_id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public void setLatestPrice(String latestPrice) {
		this.latestPrice = latestPrice;
	}

	public String getName() {
		return name;
	}

	public String getSymbol() {
		return symbol;
	}

	public String getLatestPrice() {
		return latestPrice;
	}

	public int getFund_id() {
		return fund_id;
	}
}
