package com.microservices.currency.conversion.service.proxies;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
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
// Change this name to point to zuul proxy so
// that invocation of exchange microservice
// will happen through zuul.
// since we are routing to zuul the request uri
// should be pre appended with destination microservice
// that we want to invoke so that zuul will know
// which microservice it has to route to.
@FeignClient(name="zuul-api-gateway-service")
// Ribbon Client will get instance
// details from this property(xyz.ribbon.listOfServers) 
//defined in application.properties
// Ribbon will point to microservice name
// on which in needs to load balance
@RibbonClient(name="currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	public abstract CurrencyConversionBean getExchangeValue(@PathVariable("from") String from,
															@PathVariable("to") String to);
}
