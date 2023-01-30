package com.automation.aaap.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("minimumWithdrawal")
public class TickerResult {
	public String identity;
	public Double buy;
	public String buyWallet;
	public Double sell;
	public String sellWallet;
	public double percentage;
	public Double exchangeChargeValue;
	public double withdrawalCharge;
	public Double minimumWithdrawal;
	public String currency;

	public Double getExchangeChargeValue() {
		return exchangeChargeValue;
	}

	public void setExchangeChargeValue(Double exchangeChargeValue) {
		this.exchangeChargeValue = exchangeChargeValue;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public void setWithdrawalCharge(double withdrawalCharge) {
		this.withdrawalCharge = withdrawalCharge;
	}

	
	public Double getWithdrawalCharge() {
		return withdrawalCharge;
	}

	public void setWithdrawalCharge(Double withdrawalCharge) {
		this.withdrawalCharge = withdrawalCharge;
	}

	public Double getMinimumWithdrawal() {
		return minimumWithdrawal;
	}

	public void setMinimumWithdrawal(Double minimumWithdrawal) {
		this.minimumWithdrawal = minimumWithdrawal;
	}

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
