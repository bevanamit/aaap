package com.automation.aaap.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public abstract class Config {
	/*
	 * static String[] WAZ = { "1INCH", "AAVE", "ACH", "ADX", "ALICE", "ALPHA",
	 * "AMP", "APE", "AUDIO", "AVA", "AXS", "BAL", "BAND", "BAT", "BETA", "BICO",
	 * "BLZ", "BNB", "BTC", "BUSD", "CHR", "CHZ", "COCOS", "CMP", "COTI", "CREAM",
	 * "CRV", "CTSI", "CVC", "DATA", "DENT", "DUSK", "ENJ", "ETH", "FET", "FRONT",
	 * "FTM", "FUN", "GALA", "GRT", "GTO", "HOT", "IOTX", "JASMY", "LINK", "LRC",
	 * "LTC", "MATIC", "MIR", "MKR", "MTL", "NKN", "OGN", "OMG", "OOKI", "PHA",
	 * "PNT", "PUNDIX", "PUSH", "QKC", "QUICK", "REEF", "REN", "REQ", "RLC", "SAND",
	 * "SHIB", "SLP", "SNT", "SNX", "STMX", "STORJ", "SUSHI", "SXP", "T", "TLM",
	 * "TRB", "TRX", "UFT", "UMA", "UNI", "USDC", "USDT", "VIB", "WRX", "XLM",
	 * "XRP", "YFI", "YFII", "ZRX" }; public static HashSet<String> WAZ_SET = new
	 * HashSet<>(Arrays.asList(WAZ));
	 * 
	 * public static HashMap<String, Double> getwithdrawlFeeMapWAZ() {
	 * 
	 * HashMap<String, Double> withdrawlFeeMapWAZ = new HashMap<String, Double>();
	 * 
	 * withdrawlFeeMapWAZ.put("1INCH", 14.03); withdrawlFeeMapWAZ.put("ADX", 47.0);
	 * withdrawlFeeMapWAZ.put("AVA", 1.0); withdrawlFeeMapWAZ.put("BAT", 31.0);
	 * withdrawlFeeMapWAZ.put("BNB", 0.001); withdrawlFeeMapWAZ.put("BTC", 0.0006);
	 * withdrawlFeeMapWAZ.put("COTI", 99.0); withdrawlFeeMapWAZ.put("CREAM", 0.518);
	 * withdrawlFeeMapWAZ.put("CTSI", 53.7); withdrawlFeeMapWAZ.put("DENT", 9187.0);
	 * withdrawlFeeMapWAZ.put("DUSK", 72.77); withdrawlFeeMapWAZ.put("ETH", 0.01);
	 * withdrawlFeeMapWAZ.put("FRONT", 35.03); withdrawlFeeMapWAZ.put("FTM", 79.8);
	 * withdrawlFeeMapWAZ.put("FUN", 970.0);
	 * 
	 * withdrawlFeeMapWAZ.put("HOT", 4.08); withdrawlFeeMapWAZ.put("LRC", 27.9);
	 * withdrawlFeeMapWAZ.put("LTC", 0.01); withdrawlFeeMapWAZ.put("MATIC", 7.3);
	 * withdrawlFeeMapWAZ.put("MKR", 0.01041); withdrawlFeeMapWAZ.put("MTL", 18.02);
	 * withdrawlFeeMapWAZ.put("OMG", 6.06); withdrawlFeeMapWAZ.put("PNT", 46.83);
	 * withdrawlFeeMapWAZ.put("PUSH", 20.74);
	 * 
	 * withdrawlFeeMapWAZ.put("QKC", 770.0); withdrawlFeeMapWAZ.put("REQ", 67.0);
	 * withdrawlFeeMapWAZ.put("SAND", 13.0); withdrawlFeeMapWAZ.put("SHIB",
	 * 716521.0); withdrawlFeeMapWAZ.put("SNT", 276.0);
	 * withdrawlFeeMapWAZ.put("STMX", 1476.0); withdrawlFeeMapWAZ.put("STORG",
	 * 21.0); withdrawlFeeMapWAZ.put("TRX", 5.0); withdrawlFeeMapWAZ.put("UFT",
	 * 20.17);
	 * 
	 * withdrawlFeeMapWAZ.put("UMA", 3.463); withdrawlFeeMapWAZ.put("UNI", 1.12);
	 * withdrawlFeeMapWAZ.put("USDC", 5.9); withdrawlFeeMapWAZ.put("USDT", 4.09);
	 * withdrawlFeeMapWAZ.put("WRX", 1.0); withdrawlFeeMapWAZ.put("YFI", 0.001026);
	 * withdrawlFeeMapWAZ.put("YFII", 0.004821); withdrawlFeeMapWAZ.put("XRP",
	 * 0.25);
	 * 
	 * return withdrawlFeeMapWAZ;
	 * 
	 * }
	 * 
	 * public static HashMap<String, Double> getwithdrawlFeeMapBIN() {
	 * 
	 * HashMap<String, Double> withdrawlFeeMapWAZ = new HashMap<String, Double>();
	 * 
	 * withdrawlFeeMapWAZ.put("1INCH", 4.59); withdrawlFeeMapWAZ.put("ADX", 0.0);
	 * withdrawlFeeMapWAZ.put("ADX", 23.67); withdrawlFeeMapWAZ.put("BAL", 1.39);
	 * withdrawlFeeMapWAZ.put("BAT", 48.2); withdrawlFeeMapWAZ.put("BNB", 0.0075);
	 * withdrawlFeeMapWAZ.put("BTC", 0.0005); withdrawlFeeMapWAZ.put("BUSD", 1.84);
	 * withdrawlFeeMapWAZ.put("COTI", 0.0); withdrawlFeeMapWAZ.put("CREAM", 0.0);
	 * withdrawlFeeMapWAZ.put("CTSI", 0.0); withdrawlFeeMapWAZ.put("CRV", 17.117);
	 * withdrawlFeeMapWAZ.put("CRV", 84.6);
	 * 
	 * withdrawlFeeMapWAZ.put("DENT", 14180.0); withdrawlFeeMapWAZ.put("ETH",
	 * 0.007416); withdrawlFeeMapWAZ.put("ENJ", 34.38);
	 * withdrawlFeeMapWAZ.put("FRONT", 0.0); withdrawlFeeMapWAZ.put("FTM", 0.0);
	 * withdrawlFeeMapWAZ.put("FUN", 0.0); withdrawlFeeMapWAZ.put("GRT", 157.27);
	 * withdrawlFeeMapWAZ.put("HOT", 0.0); withdrawlFeeMapWAZ.put("LRC",39.24);
	 * withdrawlFeeMapWAZ.put("LTC", 0.01); withdrawlFeeMapWAZ.put("LINK", 1.36);
	 * withdrawlFeeMapWAZ.put("MATIC", 11.33); withdrawlFeeMapWAZ.put("MKR",
	 * 0.0164); withdrawlFeeMapWAZ.put("MTL", 0.0); withdrawlFeeMapWAZ.put("OMG",
	 * 8.39); withdrawlFeeMapWAZ.put("PNT", 0.0); withdrawlFeeMapWAZ.put("PUSH",
	 * 30.8137); withdrawlFeeMapWAZ.put("QKC", 0.0); withdrawlFeeMapWAZ.put("REQ",
	 * 101.6); withdrawlFeeMapWAZ.put("SAND", 20.632);
	 * withdrawlFeeMapWAZ.put("SHIB", 1086155.0); withdrawlFeeMapWAZ.put("SNT",
	 * 0.0); withdrawlFeeMapWAZ.put("STMX", 0.0); withdrawlFeeMapWAZ.put("STMX",
	 * 5.7); withdrawlFeeMapWAZ.put("STORG", 0.0); withdrawlFeeMapWAZ.put("TRX",
	 * 5.0); withdrawlFeeMapWAZ.put("TLM", 596.3209);
	 * 
	 * withdrawlFeeMapWAZ.put("UFT", 0.0);
	 * 
	 * withdrawlFeeMapWAZ.put("UMA", 0.0); withdrawlFeeMapWAZ.put("UNI",1.71);
	 * withdrawlFeeMapWAZ.put("USDC", 1.67); withdrawlFeeMapWAZ.put("USDT", 8.99);
	 * withdrawlFeeMapWAZ.put("WRX", 0.0); withdrawlFeeMapWAZ.put("XRP", 0.1);
	 * withdrawlFeeMapWAZ.put("XRP", 0.01);
	 * 
	 * withdrawlFeeMapWAZ.put("YFI", 0.001625); withdrawlFeeMapWAZ.put("YFII",
	 * 0.0075); withdrawlFeeMapWAZ.put("ZRX", 48.19);
	 * 
	 * return withdrawlFeeMapWAZ;
	 * 
	 * }
	 * 
	 */}
