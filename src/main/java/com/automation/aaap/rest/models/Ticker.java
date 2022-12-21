package com.automation.aaap.rest.models;

public class Ticker{

	public String identity;
	public double buyPrice;
	public double sellPrice;
	public double buyVolume;
	public double sellVolume;
	public String currency;
	public double lastTradePrice;
	public String walletName;
	
	public String getWalletName() {
		return walletName;
	}
	public void setWalletName(String walletName) {
		this.walletName = walletName;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public double getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(double buyPrice) {
		this.buyPrice = buyPrice;
	}
	public double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public double getBuyVolume() {
		return buyVolume;
	}
	public void setBuyVolume(double buyVolume) {
		this.buyVolume = buyVolume;
	}
	public double getSellVolume() {
		return sellVolume;
	}
	public void setSellVolume(double sellVolume) {
		this.sellVolume = sellVolume;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getLastTradePrice() {
		return lastTradePrice;
	}
	public void setLastTradePrice(double lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
	}
	
	
}
