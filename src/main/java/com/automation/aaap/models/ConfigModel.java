package com.automation.aaap.models;

public class ConfigModel {

	public String identity;
	public Double exchangeChargeValue;
	public double withdrawalCharge;
	public Double minimumWithdrawal;
	public boolean isWithdrawl;
	public boolean isDepositable;

	public boolean isWithdrawl() {
		return isWithdrawl;
	}

	public void setWithdrawl(boolean isWithdrawl) {
		this.isWithdrawl = isWithdrawl;
	}

	public boolean isDepositable() {
		return isDepositable;
	}

	public void setDepositable(boolean isDepositable) {
		this.isDepositable = isDepositable;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Double getExchangeChargeValue() {
		return exchangeChargeValue;
	}

	public void setExchangeChargeValue(Double exchangeChargeValue) {
		this.exchangeChargeValue = exchangeChargeValue;
	}

	public double getWithdrawalCharge() {
		return withdrawalCharge;
	}

	public void setWithdrawalCharge(double withdrawalCharge) {
		this.withdrawalCharge = withdrawalCharge;
	}

	public Double getMinimumWithdrawal() {
		return minimumWithdrawal;
	}

	public void setMinimumWithdrawal(Double minimumWithdrawal) {
		this.minimumWithdrawal = minimumWithdrawal;
	}

}
