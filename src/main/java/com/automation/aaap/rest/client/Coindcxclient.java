package com.automation.aaap.rest.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.automation.aaap.rest.models.CoindcxTicker;
import com.automation.aaap.rest.models.Ticker;
import com.automation.aaap.rest.models.ZebPayTicker;

@Service
public class Coindcxclient extends AbstarctWebClient {

	private final WebClient webClient;

	@Autowired
	public Coindcxclient() {
		this.webClient = WebClient.builder().filter(logRequest()).filter(logResponse())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json").build();
		}

	@Override
	public List<Ticker> fetcTickerData() {
		return convert(webClient.get().uri("https://api.coindcx.com/exchange/ticker").retrieve()
				.bodyToMono(CoindcxTicker[].class).block());
	}

	public List<Ticker> convert(CoindcxTicker[] data) {
		List<Ticker> tickers = new ArrayList<>();
		for(CoindcxTicker cd: data) {
			
			if(!cd.getMarket().contains("INR")) {
				continue;
			}
						
			Ticker t ;
			try {
		    t = new Ticker();
		    t.setWalletName("COINDCX");
			t.setBuyPrice(Double.parseDouble(cd.getBid()));
			t.setSellPrice(Double.parseDouble(cd.getAsk()));
			t.setCurrency("INR");
			t.setIdentity(cd.getMarket().split("INR")[0]);
			}catch (Exception e) {
				continue;
			}
			tickers.add(t);
		}
		return tickers;

	}
}
