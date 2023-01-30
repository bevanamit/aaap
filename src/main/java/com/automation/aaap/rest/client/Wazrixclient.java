package com.automation.aaap.rest.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import com.automation.aaap.rest.models.Ticker;
import com.automation.aaap.rest.models.WazrixTicker;
import com.automation.aaap.util.IConstant;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Wazrixclient extends AbstarctWebClient {

	private final WebClient webClient;

	@Autowired
	public Wazrixclient() {
		final int size = 16 * 1024 * 1024;
		final ExchangeStrategies strategies = ExchangeStrategies.builder()
				.codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size)).build();
		this.webClient = WebClient.builder().exchangeStrategies(strategies).filter(logRequest()).filter(logResponse())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json").build();
	}

	@Override
	public List<Ticker> fetcTickerData() {
		return convert(webClient.get().uri("https://api.wazirx.com/api/v2/tickers").retrieve().bodyToMono(String.class)
				.block());
	}

	public String getConfig() {
		return webClient.get().uri("https://api.wazirx.com//api/v2/market-status").retrieve().bodyToMono(String.class)
				.block();

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
				WazrixTicker wz = objectMapper.convertValue(en.getValue(), WazrixTicker.class);

				Ticker t = new Ticker();
				t.setWalletName(IConstant.WAZ_NAME);
				t.setBuyPrice(Double.parseDouble(wz.getBuy()));
				t.setSellPrice(Double.parseDouble(wz.getSell()));
				t.setCurrency(wz.getName().split("/")[1]);
				t.setIdentity(wz.getName().split("/")[0]);
				tickers.add(t);
			} catch (Exception e) {

				continue;
			}
		}
		return tickers;

	}
}
