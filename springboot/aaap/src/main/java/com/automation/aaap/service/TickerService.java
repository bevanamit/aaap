package com.automation.aaap.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automation.aaap.models.TickerResult;
import com.automation.aaap.rest.client.Bitbnsclient;
import com.automation.aaap.rest.client.Coindcxclient;
import com.automation.aaap.rest.client.Wazrixclient;
import com.automation.aaap.rest.client.Zebpayclient;
import com.automation.aaap.rest.models.Ticker;

@Service
public class TickerService {
	@Autowired
	private Zebpayclient zebpayclient;

	@Autowired
	private Bitbnsclient bitbnsclient;

	@Autowired
	private Wazrixclient wazrixclient;
	
	@Autowired
	Coindcxclient coindcxclient;

	private List<CompletableFuture<List<Ticker>>> getTickers(String wallets) {
		CompletableFuture<List<Ticker>> zebFeature = CompletableFuture.supplyAsync(() -> {
			return zebpayclient.fetcTickerData();
		});
		CompletableFuture<List<Ticker>> wazFeature = CompletableFuture.supplyAsync(() -> {
			return wazrixclient.fetcTickerData();
		});
		CompletableFuture<List<Ticker>> bitbnsFeature = CompletableFuture.supplyAsync(() -> {
			return bitbnsclient.fetcTickerData();
		});
		
		CompletableFuture<List<Ticker>> coindcxFeature = CompletableFuture.supplyAsync(() -> {
			return coindcxclient.fetcTickerData();
		});

		List<CompletableFuture<List<Ticker>>> tickerFutures = new ArrayList<>();
		if (wallets == null) {
			tickerFutures.add(bitbnsFeature);
			tickerFutures.add(wazFeature);
			tickerFutures.add(zebFeature);
			tickerFutures.add(coindcxFeature);
		} else {
			if (wallets.contains("B") || wallets.contains("b")) {
				tickerFutures.add(bitbnsFeature);
			}
			if (wallets.contains("W") || wallets.contains("w")) {
				tickerFutures.add(wazFeature);
			}
			if (wallets.contains("z") || wallets.contains("Z")) {
				tickerFutures.add(zebFeature);
			}
			if (wallets.contains("c") || wallets.contains("C")) {
				tickerFutures.add(coindcxFeature);
			}
		}

		return tickerFutures;
	}

	public List<TickerResult> getTickersDataViceversa(String one, String two) {
		String wallets = "WZBC";
		if (wallets.contains(one) && wallets.contains(two)) {
			getTickers(one.concat(two));
			List<CompletableFuture<List<Ticker>>> tickerFutures = getTickers(one.concat(two));
			CompletableFuture<Void> allFutures = CompletableFuture
					.allOf(tickerFutures.toArray(new CompletableFuture[tickerFutures.size()]));
			CompletableFuture<List<List<Ticker>>> allCompletableFuture = allFutures.thenApply(future -> {
				return tickerFutures.stream().map(completableFuture -> completableFuture.join())
						.collect(Collectors.toList());
			});
			CompletableFuture<List<List<Ticker>>> completableFuture = allCompletableFuture.toCompletableFuture();
			List<List<Ticker>> finalList = null;
			try {
				finalList = (List<List<Ticker>>) completableFuture.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HashMap<String, List<Ticker>> mapByIdentity = mapByIdentityGetMapIdentityTickers(
					finalList.stream().flatMap(list -> list.stream()).collect(Collectors.toList()));
			List<TickerResult> listOfticker = new ArrayList<>();
			for (Map.Entry<String, List<Ticker>> e : mapByIdentity.entrySet()) {

				if (e.getValue().size() > 1) {
					Ticker buyTicker = null;
					Ticker sellTicker = null;
					if (e.getValue().get(0).getWalletName().startsWith(one)) {
						buyTicker = e.getValue().get(0);
						sellTicker = e.getValue().get(1);

					} else {
						buyTicker = e.getValue().get(1);
						sellTicker = e.getValue().get(0);
					}
					if (buyTicker.getBuyPrice() == 0 || sellTicker.getSellPrice() == 0
							|| buyTicker.getWalletName() == sellTicker.getWalletName()) {
						continue;
					} else {
						TickerResult tr = new TickerResult();
						tr.setSell(sellTicker.getBuyPrice());
						tr.setBuy(buyTicker.getSellPrice());
						tr.setSellWallet(sellTicker.getWalletName());
						tr.setBuyWallet(buyTicker.getWalletName());
						tr.setIdentity(e.getKey());
						tr.setPercentage(getPercentage(buyTicker, sellTicker));
						tr.setCurrency(buyTicker.getCurrency());
						listOfticker.add(tr);
					}
				}

			}
			listOfticker.sort(Comparator.comparing(TickerResult::getPercentage).reversed());
			return listOfticker;

		} else {
			throw new RuntimeException("Invalid ones");
		}
	}

	public List<TickerResult> getTickersData(String wallets) {
		List<CompletableFuture<List<Ticker>>> tickerFutures = getTickers(wallets);
		CompletableFuture<Void> allFutures = CompletableFuture
				.allOf(tickerFutures.toArray(new CompletableFuture[tickerFutures.size()]));
		CompletableFuture<List<List<Ticker>>> allCompletableFuture = allFutures.thenApply(future -> {
			return tickerFutures.stream().map(completableFuture -> completableFuture.join())
					.collect(Collectors.toList());
		});
		CompletableFuture<List<List<Ticker>>> completableFuture = allCompletableFuture.toCompletableFuture();
		List<List<Ticker>> finalList = null;
		try {
			finalList = (List<List<Ticker>>) completableFuture.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return getTickerResults(finalList.stream().flatMap(list -> list.stream()).collect(Collectors.toList()));

	}

	private HashMap<String, List<Ticker>> mapByIdentityGetMapIdentityTickers(List<Ticker> tickers) {
		List<Ticker> currncyFiltered = tickers.parallelStream().filter(x -> x.getCurrency().equalsIgnoreCase("INR"))
				.collect(Collectors.toList());

		HashMap<String, List<Ticker>> mapByIdentity = new HashMap<>();
		currncyFiltered.forEach(x -> {
			if (mapByIdentity.get(x.getIdentity()) == null) {
				List<Ticker> ts = new ArrayList<>();
				ts.add(x);
				mapByIdentity.put(x.getIdentity(), ts);
			} else {
				List<Ticker> l = mapByIdentity.get(x.getIdentity());
				l.add(x);
			}
		});
		return mapByIdentity;
	}

	public List<TickerResult> getTickerResults(List<Ticker> tickers) {

		List<TickerResult> listOfticker = new ArrayList<>();
		// Filter by currencyes

		HashMap<String, List<Ticker>> mapByIdentity = mapByIdentityGetMapIdentityTickers(tickers);

		for (Map.Entry<String, List<Ticker>> e : mapByIdentity.entrySet()) {

			Ticker buyTicker = getBuyTicker(e.getValue());
			Ticker sellTicker = getSellTicker(e.getValue());
			if (buyTicker.getBuyPrice() == 0 || sellTicker.getSellPrice() == 0
					|| buyTicker.getWalletName() == sellTicker.getWalletName()) {
				continue;
			} else {
				TickerResult tr = new TickerResult();
				tr.setSell(sellTicker.getBuyPrice());
				tr.setBuy(buyTicker.getSellPrice());
				tr.setSellWallet(sellTicker.getWalletName());
				tr.setBuyWallet(buyTicker.getWalletName());
				tr.setIdentity(e.getKey());
				tr.setPercentage(getPercentage(buyTicker, sellTicker));
				tr.setCurrency(buyTicker.getCurrency());
				listOfticker.add(tr);
			}
		}
		listOfticker.sort(Comparator.comparing(TickerResult::getPercentage).reversed());
		return listOfticker;

	}

	private Ticker getBuyTicker(List<Ticker> tickers) {
		return tickers.stream().min(Comparator.comparing(Ticker::getSellPrice)).get();

	}

	private Ticker getSellTicker(List<Ticker> tickers) {
		return tickers.stream().max(Comparator.comparing(Ticker::getBuyPrice)).get();

	}

	private Double getPercentage(Ticker buyTicker, Ticker sellTicker) {

		Double buy = buyTicker.getSellPrice();
		Double sell = sellTicker.getBuyPrice();
		Double diff = sell - buy;
		Double percentage = (diff) / buy * 100;
		return percentage;

	}
}
