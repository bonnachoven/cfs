package databean;

import java.sql.Date;


public class ResearchFund {
	private String name = null;
	private String symbol = null;
	private String price = null;
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
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String string) {
		this.price = string;
	}
	
	public String getPrice_date() {
		return price_date;
	}
	public void setPrice_date(String s) {
		this.price_date = s ;
	}
	
}
