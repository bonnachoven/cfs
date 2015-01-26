package databean;

import java.io.Serializable;


public class ViewAccountRecord  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fundName = null;
	private String fundTicker = null;
	private String shares = null;
	private String currentPrice = null;
	
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	
	public String getFundTicker() {
		return fundTicker;
	}
	public void setFundTicker(String fundTicker) {
		this.fundTicker = fundTicker;
	}
	
	public String getShares() {
		return shares;
	}
	public void setShares(String shares) {
		this.shares = shares;
	}
	
	public String getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
	
}
