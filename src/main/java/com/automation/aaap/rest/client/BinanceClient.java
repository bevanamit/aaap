package com.automation.aaap.rest.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.automation.aaap.rest.models.BinanceP2PTicker;
import com.automation.aaap.rest.models.CoindcxTicker;
import com.automation.aaap.rest.models.Ticker;
import com.automation.aaap.rest.models.ZebPayTicker;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import reactor.core.publisher.Mono;

@Service
public class BinanceClient extends AbstarctWebClient {

	private final WebClient webClient;

	@Autowired
	public BinanceClient() {
		this.webClient = WebClient.builder().filter(logRequest()).filter(logResponse())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json").build();
	}

	public BinanceP2PTicker getP2pData(String symbol,String tradetype) {
		return convert(webClient.post().uri("https://p2p.binance.com/bapi/c2c/v2/friendly/c2c/adv/search")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(new RequestBinance(symbol,tradetype)), RequestBinance.class).retrieve().bodyToMono(String.class)
				.block());

	}
	
	public BinanceP2PTicker getP2pDataForSell(String symbol,String tradetype) {
		return convert1(webClient.post().uri("https://p2p.binance.com/bapi/c2c/v2/friendly/c2c/adv/search")
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(new RequestBinance(symbol,tradetype)), RequestBinance.class).retrieve().bodyToMono(String.class)
				.block());

	}

	public BinanceP2PTicker getTradeData(String symbol) {

		String url = "https://www.binance.com/api/v3/depth?symbol=" + symbol.toUpperCase() + "USDT&limit=10";
		String data = webClient.get().uri(url).accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(String.class).block();
		DocumentContext jsonContext = JsonPath.parse(data);
		
		String price =	jsonContext
				.read("$['asks'][0][0]");
		String volume =	jsonContext
				.read("$['asks'][0][1]");
		BinanceP2PTicker b = new BinanceP2PTicker();
		b.setBuy(price);
		b.setVolume(Double.valueOf(volume));
		return b;
	}
	public BinanceP2PTicker convert1(String data) {
		DocumentContext jsonContext = JsonPath.parse(data);
	String price =	jsonContext
		.read("$['data'][0]['adv']['price']");
	
	String volume =	jsonContext
			.read("$['data'][0]['adv']['tradableQuantity']");
		BinanceP2PTicker b = new BinanceP2PTicker();
		b.setSell(price);
		b.setVolume(Double.valueOf(volume));
		return b;
	}
	
	public BinanceP2PTicker convert(String data) {
		DocumentContext jsonContext = JsonPath.parse(data);
	String price =	jsonContext
		.read("$['data'][0]['adv']['price']");
	
	String volume =	jsonContext
			.read("$['data'][0]['adv']['tradableQuantity']");
		BinanceP2PTicker b = new BinanceP2PTicker();
		b.setBuy(price);
		b.setVolume(Double.valueOf(volume));
		return b;
	}

	@Override
	public List<Ticker> fetcTickerData() {

		return null;
	}
}

class RequestBinance {

	private boolean proMerchantAds = false;
	private float page = 1;
	private float rows = 10;
	ArrayList<Object> payTypes = new ArrayList<Object>();
	ArrayList<Object> countries = new ArrayList<Object>();
	private String publisherType = null;
	private String asset;
	private String fiat = "INR";
	private String tradeType = "BUY";

	public RequestBinance(String asset,String tradetype) {
		this.asset = asset;
		if(tradetype != null) {
			this.tradeType= tradetype;
		}
	}

	public RequestBinance() {
		// TODO Auto-generated constructor stub
	}

	public boolean isProMerchantAds() {
		return proMerchantAds;
	}

	public void setProMerchantAds(boolean proMerchantAds) {
		this.proMerchantAds = proMerchantAds;
	}

	public float getPage() {
		return page;
	}

	public void setPage(float page) {
		this.page = page;
	}

	public float getRows() {
		return rows;
	}

	public void setRows(float rows) {
		this.rows = rows;
	}

	public ArrayList<Object> getPayTypes() {
		return payTypes;
	}

	public void setPayTypes(ArrayList<Object> payTypes) {
		this.payTypes = payTypes;
	}

	public ArrayList<Object> getCountries() {
		return countries;
	}

	public void setCountries(ArrayList<Object> countries) {
		this.countries = countries;
	}

	public String getPublisherType() {
		return publisherType;
	}

	public void setPublisherType(String publisherType) {
		this.publisherType = publisherType;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getFiat() {
		return fiat;
	}

	public void setFiat(String fiat) {
		this.fiat = fiat;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

}
