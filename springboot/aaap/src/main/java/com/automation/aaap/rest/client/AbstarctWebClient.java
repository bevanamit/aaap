package com.automation.aaap.rest.client;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import com.automation.aaap.rest.models.Ticker;

import reactor.core.publisher.Mono;

public abstract class AbstarctWebClient {
	private static final Logger logger = LoggerFactory.getLogger(AbstarctWebClient.class);

	LocalDateTime requestTime = LocalDateTime.now();

	protected ExchangeFilterFunction logRequest() {
		return ExchangeFilterFunction.ofRequestProcessor(clinetRequest -> {
			requestTime = LocalDateTime.now();
			logger.info("Request {} {}", clinetRequest.method(), clinetRequest.url());
			clinetRequest.headers()
					.forEach((name, values) -> values.forEach(value -> logger.info("{}={}", name, value)));
			return Mono.just(clinetRequest);
		});
	}

	protected ExchangeFilterFunction logResponse() {
		return ExchangeFilterFunction.ofResponseProcessor(clinetResponse -> {
			logger.info("The time take by external system to process req is {}",
					(Duration.between(requestTime, LocalDateTime.now())));
			logger.info("Response status code {} ", clinetResponse.statusCode());
			return Mono.just(clinetResponse);
		});
	}
	
	public abstract List<Ticker> fetcTickerData();
}
