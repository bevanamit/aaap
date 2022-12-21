package com.automation.aaap.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WazrixTicker {

	private String base_unit;
	private String quote_unit;
	private String low;
	private String high;
	private String last;
	private String type;
	private String open;
	private String volume;
	private String sell;
	private String buy;
	private float at;
	private String name;

	// Getter Methods

	public String getBase_unit() {
		return base_unit;
	}

	public String getQuote_unit() {
		return quote_unit;
	}

	public String getLow() {
		return low;
	}

	public String getHigh() {
		return high;
	}

	public String getLast() {
		return last;
	}

	public String getType() {
		return type;
	}

	public String getOpen() {
		return open;
	}

	public String getVolume() {
		return volume;
	}

	public String getSell() {
		return sell;
	}

	public String getBuy() {
		return buy;
	}

	public float getAt() {
		return at;
	}

	public String getName() {
		return name;
	}

	// Setter Methods

	public void setBase_unit(String base_unit) {
		this.base_unit = base_unit;
	}

	public void setQuote_unit(String quote_unit) {
		this.quote_unit = quote_unit;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public void setSell(String sell) {
		this.sell = sell;
	}

	public void setBuy(String buy) {
		this.buy = buy;
	}

	public void setAt(float at) {
		this.at = at;
	}

	public void setName(String name) {
		this.name = name;
	}
}