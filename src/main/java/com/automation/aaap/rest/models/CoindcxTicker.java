package com.automation.aaap.rest.models;


public class CoindcxTicker {
	private String market;
	private String change_24_hour;
	private String high;
	private String low;
	private String volume;
	private String last_price;
	private String bid;
	private String ask;
	private float timestamp;

// Getter Methods 

	public String getMarket() {
		return market;
	}

	public String getChange_24_hour() {
		return change_24_hour;
	}

	public String getHigh() {
		return high;
	}

	public String getLow() {
		return low;
	}

	public String getVolume() {
		return volume;
	}

	public String getLast_price() {
		return last_price;
	}

	public String getBid() {
		return bid;
	}

	public String getAsk() {
		return ask;
	}

	public float getTimestamp() {
		return timestamp;
	}

// Setter Methods 

	public void setMarket(String market) {
		this.market = market;
	}

	public void setChange_24_hour(String change_24_hour) {
		this.change_24_hour = change_24_hour;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public void setLast_price(String last_price) {
		this.last_price = last_price;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public void setAsk(String ask) {
		this.ask = ask;
	}

	public void setTimestamp(float timestamp) {
		this.timestamp = timestamp;
	}

}
