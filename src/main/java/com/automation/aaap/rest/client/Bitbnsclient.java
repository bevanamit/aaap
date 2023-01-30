package com.automation.aaap.rest.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.automation.aaap.rest.models.BitbnsTicker;
import com.automation.aaap.rest.models.Ticker;
import com.automation.aaap.util.IConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Bitbnsclient extends AbstarctWebClient {

	private final WebClient webClient;

	@Autowired
	public Bitbnsclient() {
		this.webClient = WebClient.builder().filter(logRequest()).filter(logResponse())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json").build();
	}

	@Override
	public List<Ticker> fetcTickerData() {
		return convert(webClient.get().uri("https://bitbns.com/order/fetchTickers").retrieve().bodyToMono(String.class)
				.block());
	}

	public String getConfig() {
		return webClient.get().uri("https://bitbns.com/_next/data/5.0.8/fees.json?type=crypto").retrieve()
				.bodyToMono(String.class).block();

	}

	@SuppressWarnings("unchecked")
	public List<Ticker> convert(String stringdata) {
		Map<String, String> data;
		try {
			data = new ObjectMapper().readValue(stringdata, HashMap.class);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			return new ArrayList<Ticker>();
		}
		List<Ticker> tickers = new ArrayList<>();
		for (Map.Entry<String, String> en : data.entrySet()) {
			try {
				ObjectMapper objectMapper = new ObjectMapper();
				BitbnsTicker bt = objectMapper.convertValue(en.getValue(), BitbnsTicker.class);
				// BitbnsTicker bt = objectMapper.readValue(en.getValue(), BitbnsTicker.class);

				Ticker t = new Ticker();
				t.setWalletName(IConstant.BITBNS_NAME);
				t.setBuyPrice(bt.getBid());
				t.setSellPrice(bt.getAsk());
				t.setCurrency(bt.getSymbol().split("/")[1]);
				t.setIdentity(bt.getSymbol().split("/")[0]);

				tickers.add(t);
			} catch (Exception e) {

				continue;
			}
		}
		return tickers;

	}
}
