package com.automation.aaap.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.automation.aaap.ExcelGenerator;
import com.automation.aaap.models.TickerResult;
import com.automation.aaap.service.TickerService;

@RestController
public class MyController {

	@Autowired
	private TickerService tickerService;

	@GetMapping(value = "/tickers")
	public List<TickerResult> getTickers(@RequestParam Optional<String> wallets) {

		return tickerService.getArbitrageTickersAll(wallets.orElse(null));

	}

	@GetMapping("/doc/tickers")
	public ResponseEntity<Resource> getTickersDoc(@RequestParam Optional<String> wallets) {
		String filename = "crypto.xlsx";
		InputStreamResource file = new InputStreamResource(
				ExcelGenerator.excelGenerato(tickerService.getArbitrageTickersAll(wallets.orElse(null))));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

	@GetMapping(value = "/tickers/vv")
	public List<TickerResult> getTickersViceversa(@RequestParam String one, @RequestParam String two) {

		return tickerService.getTickersDataFromOneway(one.toUpperCase(), two.toUpperCase());

	}
	
	@GetMapping(value = "/bnb")
	public HashMap<String , HashMap<String,String>> getBnb() {

		return tickerService.getNinanceData();

	}
	

	@GetMapping(value = "/doc/tickers/vv")
	public ResponseEntity<Resource> getTickersViceversaDoc(@RequestParam String one, @RequestParam String two) {
		String filename = "vvcrypto.xlsx";
		InputStreamResource file = new InputStreamResource(ExcelGenerator
				.excelGenerato(tickerService.getTickersDataFromOneway(one.toUpperCase(), two.toUpperCase())));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

}
