package com.boot.services.currencyconversionservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//name has to match the spring app name of currency exchange service
//@FeignClient(name = "currency-exchange-service", url = "localhost:8000")
//No more URL hard coding once ribbon is in place. We want to switch between
//multiple currency exchange servers.

//@FeignClient(name = "currency-exchange-service")
//With zuul api gateway in place, name has to match gateway application name
@FeignClient(name = "netflix-zuul-api-gateway-server")

//Ribbon refers to the base URLs (machine1:port1,machine2:port2,machine3:port3 etc) set for "currency-exchange-service" in 
//currency-conversion-service/application.properties
//Property is currency-exchange-service.ribbon.listOfServers
//@RibbonClient(name = "currency-exchange-service")

//After enabling Eureka service registrations
//With Eureka having all the servers or applications registered in it, 
//currency-exchange-service.ribbon.listOfServers is of no use.
//We still want to switch between multiple currency-exchange-service serers, hence this remains same.
@RibbonClient(name = "currency-exchange-service")

public interface CurrencyExchangeServiceProxy {
	//@GetMapping("/currency-exchange/from/{from}/to/{to}")
	//With zuul api gateway in place, all URLs have too have relevant spring application name prefixed.
	//This is because the port number used for zuul is common across all spring applications. 
	
	//Hence the discrimination is only possible by mentioning spring application name before service URI
	@GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	
	//CurrencyConversion.class is very much similar to CurrencyExchange.class. So we are good with this tweak.
	//Otherwise CurrencyExchange.class (client) is to be defined (for return type) in Currency-Conversion-service (this) project.
	public CurrencyConversion getCurrencyExchange(@PathVariable String from, @PathVariable String to);

}
