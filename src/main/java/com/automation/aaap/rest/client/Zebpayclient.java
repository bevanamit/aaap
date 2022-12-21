package com.automation.aaap.rest.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.automation.aaap.rest.models.Ticker;
import com.automation.aaap.rest.models.ZebPayTicker;

@Service
public class Zebpayclient extends AbstarctWebClient {

	private final WebClient webClient;

	@Autowired
	public Zebpayclient() {
		this.webClient = WebClient.builder().filter(logRequest()).filter(logResponse())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json").build();
		}

	@Override
	public List<Ticker> fetcTickerData() {
		return convert(webClient.get().uri("https://www.zebapi.com/pro/v1/market/").retrieve()
				.bodyToMono(ZebPayTicker[].class).block());
	}

	public List<Ticker> convert(ZebPayTicker[] data) {
		List<Ticker> tickers = new ArrayList<>();
		for(ZebPayTicker zt: data) {
			if(zt.getQuickTradePrice() != null ) {
				continue;
			}
			Ticker t ;
			try {
		    t = new Ticker();
		    t.setWalletName("ZEBPAY");
			t.setBuyPrice(Double.parseDouble(zt.getBuy()));
			t.setSellPrice(Double.parseDouble(zt.getSell()));
			t.setCurrency(zt.getCurrency());
			t.setIdentity(zt.getVirtualCurrency());
			}catch (Exception e) {
				System.out.println(zt);
				continue;
			}
			tickers.add(t);
		}
		return tickers;

	}
}
