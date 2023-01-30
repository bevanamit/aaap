package com.automation.aaap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automation.aaap.models.ConfigModel;
import com.automation.aaap.rest.client.Bitbnsclient;
import com.automation.aaap.rest.client.Wazrixclient;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

@Service
public class AppConfig {

	@Autowired
	private Bitbnsclient bitbnsclient;

	@Autowired
	Wazrixclient wazrixclient;

	public static HashMap<String, ConfigModel> bnsConfig = new HashMap<>();
	public static HashMap<String, ConfigModel> wazConfig = new HashMap<>();

	public void updateBnsConfig() {
		String data = bitbnsclient.getConfig();

		DocumentContext jsonContext = JsonPath.parse(data);
		String jsonpathForWiathdralFee = "$['data']['r1']['data']['coins']";
		LinkedHashMap<String, LinkedHashMap<String, Double>> jsonpathCreatorName = jsonContext
				.read(jsonpathForWiathdralFee);
		for (Entry<String, LinkedHashMap<String, Double>> e : jsonpathCreatorName.entrySet()) {
			ConfigModel c = new ConfigModel();
			c.setIdentity(e.getKey());
			if (e.getValue().get("withdrawalFee") != null) {
				Number n = e.getValue().get("withdrawalFee");
				c.setWithdrawalCharge(n.doubleValue());
			}
			if (e.getValue().get("minimumWithdrawal") != null) {
				Number n = e.getValue().get("minimumWithdrawal");
				c.setMinimumWithdrawal(n.doubleValue());
			}

			bnsConfig.put(e.getKey().toUpperCase(), c);

		}

		String jsonpathForDepositCoins = "$['data']['r1']['data']['depositCoins']";
		JSONArray depositCoins = jsonContext.read(jsonpathForDepositCoins);
		depositCoins.forEach(x -> {
			if (bnsConfig.get(x.toString().toUpperCase()) != null) {
				bnsConfig.get(x.toString().toUpperCase()).setDepositable(true);
			}
		});

		String jsonpathForwithdrawCoins = "$['data']['r1']['data']['withdrawCoins']";
		JSONArray withdrawCoins = jsonContext.read(jsonpathForwithdrawCoins);
		withdrawCoins.forEach(x -> {
			if (bnsConfig.get(x.toString().toUpperCase()) != null) {
				bnsConfig.get(x.toString().toUpperCase()).setWithdrawl(true);
			}
		});

	}

	public void updateWazConfig() {
		String data = wazrixclient.getConfig();

		DocumentContext jsonContext = JsonPath.parse(data);
		String jsonpathForWiathdralFee = "$['assets']";
		ArrayList<LinkedHashMap<String, Object>> jsonpathCreatorName = jsonContext.read(jsonpathForWiathdralFee);

		jsonpathCreatorName.forEach(e -> {
			
			ConfigModel c = new ConfigModel();
			String name = (String) e.get("type");
			c.setIdentity(name.toUpperCase());
			if (e.get("withdrawFee") != null) {
				Number n = (Number) e.get("withdrawFee");
				c.setWithdrawalCharge(n.doubleValue());
			}
			if (e.get("minWithdrawAmount") != null) {
				String n = (String) e.get("minWithdrawAmount");
				c.setMinimumWithdrawal(Double.valueOf(n));
			}

			if (e.get("deposit") != null) {
				String n = (String) e.get("deposit");
				if (n.equals("enabled"))
					c.setDepositable(true);
			}
			if (e.get("withdrawal") != null) {
				String n = (String) e.get("withdrawal");
				if (n.equals("enabled"))
					c.setWithdrawl(true);
			}
			if (e.get("listingType") != null) {
				String n = (String) e.get("listingType");
				if (n.equals("default"))
					wazConfig.put(name.toUpperCase(), c);
			}

		});

	}

	public void updateConfig() {
		updateBnsConfig();
		updateWazConfig();
		System.out.println(wazConfig);
	}
}
