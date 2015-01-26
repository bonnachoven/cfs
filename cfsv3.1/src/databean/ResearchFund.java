package databean;

import java.sql.Date;


public class ResearchFund {
	private String name = null;
	private String symbol = null;
	private Long price = null;
	private String price_date = null;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	
	public String getPrice_date() {
		return price_date;
	}
	public void setPrice_date(String s) {
		this.price_date = s ;
	}
	
}
