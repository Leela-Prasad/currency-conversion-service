package com.microservices.currency.conversion.service.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.currency.conversion.service.models.CurrencyConversionBean;

/**
 * Feign Client for a microservice
 * which will encapsulate all the url 
 * details of a microservice.
 *
 */
@FeignClient(name= "xyz",url="localhost:8000")
public interface CurrencyExchangeServiceProxy {

	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public abstract CurrencyConversionBean getExchangeValue(@PathVariable("from") String from,
															@PathVariable("to") String to);
}
