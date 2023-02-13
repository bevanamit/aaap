package com.automation.aaap.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automation.aaap.AppConfig;
import com.automation.aaap.models.ConfigModel;
import com.automation.aaap.models.TickerResult;
import com.automation.aaap.rest.client.BinanceClient;
import com.automation.aaap.rest.client.Bitbnsclient;
import com.automation.aaap.rest.client.Coindcxclient;
import com.automation.aaap.rest.client.Wazrixclient;
import com.automation.aaap.rest.client.Zebpayclient;
import com.automation.aaap.rest.models.BinanceP2PTicker;
import com.automation.aaap.rest.models.Ticker;
import com.automation.aaap.util.IConstant;

@Service
public class TickerService {
	@Autowired
	private Zebpayclient zebpayclient;

	@Autowired
	private Bitbnsclient bitbnsclient;

	@Autowired
	private Wazrixclient wazrixclient;

	@Autowired
	private Coindcxclient coindcxclient;

	@Autowired
	BinanceClient binanceClient;

	@Autowired
	AppConfig appConfig;
	String c[] = { "BTC", "ETH", "USDT", "USDC", "BNB", "XRP", "BUSD", "ADA", "DOGE", "MATIC", "DAI", "LTC", "DOT",
			"TRX", "SHIB" };
	List<String> topCoins = Arrays.asList(c);

	private List<CompletableFuture<List<Ticker>>> getTickerFeatures(String wallets) {
		if (wallets != null)
			wallets = wallets.toUpperCase();
		List<CompletableFuture<List<Ticker>>> tickerFutures = new ArrayList<>();
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

		if (wallets == null || wallets.contains("W")) {
			tickerFutures.add(wazFeature);
		}
		if (wallets == null || wallets.contains("Z")) {
			tickerFutures.add(zebFeature);
		}
		if (wallets == null || wallets.contains("B")) {
			tickerFutures.add(bitbnsFeature);
		}
		if (wallets == null || wallets.contains("C")) {
			// tickerFutures.add(coindcxFeature);
		}
		return tickerFutures;
	}

	public List<TickerResult> getTickersDataFromOneway(String one, String two) {
		appConfig.updateConfig();
		String wallets = "WZBC";
		if (wallets.contains(one) && wallets.contains(two)) {
			List<List<Ticker>> finalList = excuteArbitrageTickerFutures(one.concat(two));

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
			return filterTickerResults(listOfticker);

		} else {
			throw new RuntimeException("Invalid ones");
		}
	}

	private List<List<Ticker>> excuteArbitrageTickerFutures(String wallets) {
		List<CompletableFuture<List<Ticker>>> tickerFutures = getTickerFeatures(wallets);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalList;
	}

	public List<TickerResult> getArbitrageTickersAll(String wallets) {
		appConfig.updateConfig();
		List<List<Ticker>> finalList = excuteArbitrageTickerFutures(wallets);

		return filterTickerResults(getTickerAribataryResult(
				finalList.stream().flatMap(list -> list.stream()).collect(Collectors.toList())));

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

	private List<TickerResult> getTickerAribataryResult(List<Ticker> tickers) {

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

	private Double getPercentage(Double buy , Double sell) {
		Double diff = sell - buy;
		Double percentage = (diff) / buy * 100;
		return percentage;

	}
	private List<TickerResult> filterTickerResults(List<TickerResult> ts) {

		List<TickerResult> res = new ArrayList<TickerResult>();
		ConfigModel buy = null;
		for (TickerResult t : ts) {
			if (t.getBuyWallet().equals(IConstant.BITBNS_NAME)) {
				ConfigModel c = AppConfig.bnsConfig.get(t.getIdentity().toUpperCase());
				if (c == null || c.isWithdrawl() == false)
					continue;
				else
					buy = c;

			}
			if (t.getBuyWallet().equals(IConstant.WAZ_NAME)) {
				ConfigModel c = AppConfig.wazConfig.get(t.getIdentity().toUpperCase());
				if (c == null || c.isWithdrawl() == false)
					continue;
				else
					buy = c;
			}

			if (t.getSellWallet().equals(IConstant.BITBNS_NAME)) {
				ConfigModel c = AppConfig.bnsConfig.get(t.getIdentity().toUpperCase());
				if (c == null || c.isDepositable() == false)
					continue;

			}
			if (t.getSellWallet().equals(IConstant.WAZ_NAME)) {
				ConfigModel c = AppConfig.wazConfig.get(t.getIdentity().toUpperCase());
				if (c == null || c.isDepositable() == false)
					continue;
			}

			if (buy != null) {
				t.setWithdrawalCharge(buy.getWithdrawalCharge());
				t.setExchangeChargeValue(t.getBuy() * buy.getWithdrawalCharge());
				t.setMinimumWithdrawal(buy.getMinimumWithdrawal());
			}

			res.add(t);

		}
		res.sort(Comparator.comparing(TickerResult::getPercentage).reversed());

		return filterTop(res);
	}

	public List<TickerResult> filterTop(List<TickerResult> res) {

		return res.stream().filter(x -> topCoins.contains(x.identity)).collect(Collectors.toList());

	}

	public HashMap<String, HashMap<String, String>> getNinanceData() {

		String symbols[] = { "USDT", "BTC", "BUSD", "BNB", "ETH", "ADA", "TRX", "SHIB", "MATIC", "WRX", "XRP", "SOL" };
		HashMap<String, BinanceP2PTicker> listP2 = new HashMap<>();
		HashMap<String, BinanceP2PTicker> listTrade = new HashMap<>();
		for (String s : symbols) {
			listP2.put(s, binanceClient.getP2pData(s, "BUY"));
		}
		for (String s : symbols) {
			if (!s.equals("USDT") && !s.equals("BUSD")) {
				listTrade.put(s, binanceClient.getTradeData(s));
			}
		}
		BinanceP2PTicker b = listP2.put("USDT", binanceClient.getP2pData("USDT", "SELL"));
		HashMap<String, HashMap<String, String>> res = new HashMap<>();
		for (String s : symbols) {
			if (!s.equals("USDT") && !s.equals("BUSD")) {

				double buy = Double.valueOf(listP2.get(s).getBuy());
				double volume = listP2.get(s).getVolume();
				double price = buy * volume;
				double sell = Double.valueOf(listTrade.get(s).getBuy());
				double sellDollar = volume * sell;
				double returnprice = sellDollar * Double.valueOf(b.getBuy());
				Double diff = returnprice - price;
				Double percentage = (diff) / price * 100;
				HashMap<String, String> m = new HashMap<String, String>();
				m.put("identity", s);
				m.put("buy", String.valueOf(buy));
				m.put("price", String.valueOf(price));
				m.put("sell", String.valueOf(sell));
				m.put("sellDollar", String.valueOf(sellDollar));
				m.put("returnprice", String.valueOf(returnprice));
				m.put("percentage", String.valueOf(percentage));

				res.put(s, m);
			}
		}

		return res;

	}

	public HashMap<String, BinanceP2PTicker> getP2pForBinance() {

		String symbols[] = { "USDT", "BTC", "BUSD", "BNB", "ETH", "ADA", "TRX", "SHIB", "MATIC", "WRX", "XRP", "SOL" };
		HashMap<String, BinanceP2PTicker> listP2 = new HashMap<>();
		for (String s : symbols) {
			listP2.put(s, binanceClient.getP2pData(s, "BUY"));
		}

		return listP2;
	}
	public HashMap<String, BinanceP2PTicker> getP2pForBinanceSell() {

		String symbols[] = { "USDT", "BTC", "BUSD", "BNB", "ETH", "ADA", "TRX", "SHIB", "MATIC", "WRX", "XRP", "SOL" };
		HashMap<String, BinanceP2PTicker> listP2 = new HashMap<>();
		for (String s : symbols) {
			listP2.put(s, binanceClient.getP2pDataForSell(s, "SELL"));
		}

		return listP2;
	}

	public List<TickerResult> getWBn() {
		List<List<Ticker>> waz = excuteArbitrageTickerFutures("W");
		HashMap<String, BinanceP2PTicker> listP2 = getP2pForBinance();
		String symbols[] = { "USDT", "BTC", "BUSD", "BNB", "ETH", "ADA", "TRX", "SHIB", "MATIC", "WRX", "XRP", "SOL" };
		List<TickerResult> listOfticker = new ArrayList<>();
		Map<String,Ticker> map = new HashMap<>();
		for (Ticker i : waz.get(0)) { 
			if(i.currency.toUpperCase().equals("INR"))
			map.put(i.getIdentity().toUpperCase(),i);
		}
		
		
		for (String s : symbols) {
			double buy = Double.valueOf(listP2.get(s).getBuy());
			TickerResult tr = new TickerResult();
			tr.setSell(map.get(s).sellPrice);
			tr.setBuy(buy);
			tr.setSellWallet("BINANCE");
			tr.setBuyWallet("WAZRIX");
			tr.setIdentity(s);
			tr.setPercentage(getPercentage(buy,map.get(s).sellPrice));
			tr.setCurrency("INR");
			
			listOfticker.add(tr);
		}
		listOfticker.sort(Comparator.comparing(TickerResult::getPercentage).reversed());
		return listOfticker;
	}
	public List<TickerResult> getBnw() {
		List<List<Ticker>> waz = excuteArbitrageTickerFutures("W");
		HashMap<String, BinanceP2PTicker> listP2 = getP2pForBinanceSell();
		String symbols[] = { "USDT", "BTC", "BUSD", "BNB", "ETH", "ADA", "TRX", "SHIB", "MATIC", "WRX", "XRP", "SOL" };
		List<TickerResult> listOfticker = new ArrayList<>();
		Map<String,Ticker> map = new HashMap<>();
		for (Ticker i : waz.get(0)) { 
			if(i.currency.toUpperCase().equals("INR"))
			map.put(i.getIdentity().toUpperCase(),i);
		}
		
		
		for (String s : symbols) {
			double sell = Double.valueOf(listP2.get(s).getSell());
			TickerResult tr = new TickerResult();
			tr.setSell(sell);
			tr.setBuy(map.get(s).buyPrice);
			tr.setSellWallet("WAZRIX");
			tr.setBuyWallet("BINANCE");
			tr.setIdentity(s);
			tr.setPercentage(getPercentage(map.get(s).buyPrice,sell));
			tr.setCurrency("INR");
			
			listOfticker.add(tr);
		}
		listOfticker.sort(Comparator.comparing(TickerResult::getPercentage).reversed());
		return listOfticker;
	}
}
