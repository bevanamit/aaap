package com.automation.aaap.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitbnsTicker {
	private String symbol;
	
	private float timestamp;
	private float datetime;
	private String high;
	private String low;
	private float bid;
	private String bidVolume;
	private float ask;
	private String askVolume;
	private String vwap;
	private float open;
	private float close;
	private float last;
	private float baseVolume;
	private String quoteVolume;
	private String previousClose;
	private float change;
	private float percentage;
	private float average;

	// Getter Methods

	public String getSymbol() {
		return symbol;
	}

	

	public float getTimestamp() {
		return timestamp;
	}

	public float getDatetime() {
		return datetime;
	}

	public String getHigh() {
		return high;
	}

	public String getLow() {
		return low;
	}

	public float getBid() {
		return bid;
	}

	public String getBidVolume() {
		return bidVolume;
	}

	public float getAsk() {
		return ask;
	}

	public String getAskVolume() {
		return askVolume;
	}

	public String getVwap() {
		return vwap;
	}

	public float getOpen() {
		return open;
	}

	public float getClose() {
		return close;
	}

	public float getLast() {
		return last;
	}

	public float getBaseVolume() {
		return baseVolume;
	}

	public String getQuoteVolume() {
		return quoteVolume;
	}

	public String getPreviousClose() {
		return previousClose;
	}

	public float getChange() {
		return change;
	}

	public float getPercentage() {
		return percentage;
	}

	public float getAverage() {
		return average;
	}

	// Setter Methods

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	

	public void setTimestamp(float timestamp) {
		this.timestamp = timestamp;
	}

	public void setDatetime(float datetime) {
		this.datetime = datetime;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public void setBid(float bid) {
		this.bid = bid;
	}

	public void setBidVolume(String bidVolume) {
		this.bidVolume = bidVolume;
	}

	public void setAsk(float ask) {
		this.ask = ask;
	}

	public void setAskVolume(String askVolume) {
		this.askVolume = askVolume;
	}

	public void setVwap(String vwap) {
		this.vwap = vwap;
	}

	public void setOpen(float open) {
		this.open = open;
	}

	public void setClose(float close) {
		this.close = close;
	}

	public void setLast(float last) {
		this.last = last;
	}

	public void setBaseVolume(float baseVolume) {
		this.baseVolume = baseVolume;
	}

	public void setQuoteVolume(String quoteVolume) {
		this.quoteVolume = quoteVolume;
	}

	public void setPreviousClose(String previousClose) {
		this.previousClose = previousClose;
	}

	public void setChange(float change) {
		this.change = change;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public void setAverage(float average) {
		this.average = average;
	}
}

class Info {
	private float highest_buy_bid;
	private float lowest_sell_bid;
	private float last_traded_price;
	private float yes_price;
	@JsonIgnore(value = true)
	Volume VolumeObject;

	// Getter Methods

	public float getHighest_buy_bid() {
		return highest_buy_bid;
	}

	public float getLowest_sell_bid() {
		return lowest_sell_bid;
	}

	public float getLast_traded_price() {
		return last_traded_price;
	}

	public float getYes_price() {
		return yes_price;
	}

	public Volume getVolume() {
		return VolumeObject;
	}

	// Setter Methods

	public void setHighest_buy_bid(float highest_buy_bid) {
		this.highest_buy_bid = highest_buy_bid;
	}

	public void setLowest_sell_bid(float lowest_sell_bid) {
		this.lowest_sell_bid = lowest_sell_bid;
	}

	public void setLast_traded_price(float last_traded_price) {
		this.last_traded_price = last_traded_price;
	}

	public void setYes_price(float yes_price) {
		this.yes_price = yes_price;
	}

	public void setVolume(Volume volumeObject) {
		this.VolumeObject = volumeObject;
	}
}

class Volume {
	private String max;
	private String min;
	private float volume;

	// Getter Methods

	public String getMax() {
		return max;
	}

	public String getMin() {
		return min;
	}

	public float getVolume() {
		return volume;
	}

	// Setter Methods

	public void setMax(String max) {
		this.max = max;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}
}