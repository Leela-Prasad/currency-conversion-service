package com.microservices.currency.conversion.service.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservices.currency.conversion.service.models.CurrencyConversionBean;
import com.microservices.currency.conversion.service.proxies.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyCoversionController {

	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	
	/**
	 * Invoking rest client with RestTemplate 
	 */
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean getConversionValue(@PathVariable String from,
													 @PathVariable String to,
													 @PathVariable String quantity) {
		
		/*CurrencyConversionBean conversionValue = new CurrencyConversionBean(1,from,to,quantity,
													  new BigDecimal("2"),new BigDecimal("2"));*/
		
		Map<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from",from);
		uriVariables.put("to",to);
		
		ResponseEntity<CurrencyConversionBean> response = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class, uriVariables);
		CurrencyConversionBean conversionBean = response.getBody();
		conversionBean.setQuantity(quantity);
		conversionBean.setCalculatedValue(conversionBean.getConversionMultiple().multiply(new BigDecimal(quantity)));
		return conversionBean;
	}
	
	/**
	 * Invoking rest client with Feign
	 * 
	 * Feign is an alternative for RestTemplate
	 * In RestTemplate we have to expose to all the details
	 * like url in the code, with feign we can have a proxy 
	 * interface where all these url details will be 
	 * encapsulated and it is easy to use. 
	 * 	 
	 */
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean getConversionValueFromFeign(@PathVariable String from,
													 		  @PathVariable String to,
													 		  @PathVariable String quantity) {
		CurrencyConversionBean conversionBean = proxy.getExchangeValue(from, to);
		conversionBean.setQuantity(quantity);
		conversionBean.setCalculatedValue(conversionBean.getConversionMultiple().multiply(new BigDecimal(quantity)));
		return conversionBean;
	}
	
}
