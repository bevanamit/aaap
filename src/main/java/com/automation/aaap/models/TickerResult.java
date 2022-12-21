package com.automation.aaap.models;

public class TickerResult {
	public String identity;
	public String currency;
	public Double buy;
	public String buyWallet;
	public Double sell;
	public String sellWallet;
	public Double percentage;

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Double getBuy() {
		return buy;
	}

	public void setBuy(Double buy) {
		this.buy = buy;
	}

	public String getBuyWallet() {
		return buyWallet;
	}

	public void setBuyWallet(String buyWallet) {
		this.buyWallet = buyWallet;
	}

	public Double getSell() {
		return sell;
	}

	public void setSell(Double sell) {
		this.sell = sell;
	}

	public String getSellWallet() {
		return sellWallet;
	}

	public void setSellWallet(String sellWallet) {
		this.sellWallet = sellWallet;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

}
