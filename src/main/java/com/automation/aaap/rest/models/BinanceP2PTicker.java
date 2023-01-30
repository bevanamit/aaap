package com.automation.aaap.rest.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BinanceP2PTicker {
	public String buy;
    public String sell;
    public String market;
    public double volume;
    public String pair;
    public String instantBuy;
    public String instantSell;
    public double volumeQt;
    public String virtualCurrency;
    public String currency;
    @JsonProperty("24hoursHigh") 
    public String _24hoursHigh;
    @JsonProperty("24hoursLow") 
    public String _24hoursLow;
    public double volumeEx;
    public String pricechange;
    public String quickTradePrice;
	public String getBuy() {
		return buy;
	}
	public void setBuy(String buy) {
		this.buy = buy;
	}
	public String getSell() {
		return sell;
	}
	public void setSell(String sell) {
		this.sell = sell;
	}
	public String getMarket() {
		return market;
	}
	public void setMarket(String market) {
		this.market = market;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public String getPair() {
		return pair;
	}
	public void setPair(String pair) {
		this.pair = pair;
	}
	public String getInstantBuy() {
		return instantBuy;
	}
	public void setInstantBuy(String instantBuy) {
		this.instantBuy = instantBuy;
	}
	public String getInstantSell() {
		return instantSell;
	}
	public void setInstantSell(String instantSell) {
		this.instantSell = instantSell;
	}
	public double getVolumeQt() {
		return volumeQt;
	}
	public void setVolumeQt(double volumeQt) {
		this.volumeQt = volumeQt;
	}
	public String getVirtualCurrency() {
		return virtualCurrency;
	}
	public void setVirtualCurrency(String virtualCurrency) {
		this.virtualCurrency = virtualCurrency;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String get_24hoursHigh() {
		return _24hoursHigh;
	}
	public void set_24hoursHigh(String _24hoursHigh) {
		this._24hoursHigh = _24hoursHigh;
	}
	public String get_24hoursLow() {
		return _24hoursLow;
	}
	public void set_24hoursLow(String _24hoursLow) {
		this._24hoursLow = _24hoursLow;
	}
	public double getVolumeEx() {
		return volumeEx;
	}
	public void setVolumeEx(double volumeEx) {
		this.volumeEx = volumeEx;
	}
	public String getPricechange() {
		return pricechange;
	}
	public void setPricechange(String pricechange) {
		this.pricechange = pricechange;
	}
	public String getQuickTradePrice() {
		return quickTradePrice;
	}
	public void setQuickTradePrice(String quickTradePrice) {
		this.quickTradePrice = quickTradePrice;
	}
	@Override
	public String toString() {
		return "ZebPayTicker [buy=" + buy + ", sell=" + sell + ", market=" + market + ", volume=" + volume + ", pair="
				+ pair + ", instantBuy=" + instantBuy + ", instantSell=" + instantSell + ", volumeQt=" + volumeQt
				+ ", virtualCurrency=" + virtualCurrency + ", currency=" + currency + ", _24hoursHigh=" + _24hoursHigh
				+ ", _24hoursLow=" + _24hoursLow + ", volumeEx=" + volumeEx + ", pricechange=" + pricechange
				+ ", quickTradePrice=" + quickTradePrice + "]";
	}
    
    
    
}
