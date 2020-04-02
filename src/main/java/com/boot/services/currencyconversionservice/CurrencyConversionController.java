package com.boot.services.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CurrencyConversionController {

//	@Autowired
//	private Environment environment;
	
	@Autowired
	private CurrencyExchangeServiceProxy currencyExchangeFeignProxy;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/currency-conversion/from/{from}/to/{to}/{quantity}")
	public CurrencyConversion getConvertedCurrency(@PathVariable String from, 
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		//CurrencyConversion calculatedInfo = new CurrencyConversion(1l, from, to, new BigDecimal(60.04), new BigDecimal(60.04).multiply(quantity));
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversion> exchangeResponseEntity 
			= new RestTemplate()
				.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
						//CurrencyConversion.class is very much similar to CurrencyExchange.class. So we are good with this tweak.
						//Otherwise CurrencyExchange.class (client) is to be defined in Currency-Conversion-service (this) project.
						CurrencyConversion.class, uriVariables);
		CurrencyConversion calculatedInfo = exchangeResponseEntity.getBody();
		calculatedInfo.setCalculatedAmount(calculatedInfo.getConversionRate().multiply(quantity));
//		calculatedInfo.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
		return calculatedInfo;
	}

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/{quantity}")
	public CurrencyConversion getConvertedCurrencyUsingFeign(@PathVariable String from, 
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		
		CurrencyConversion calculatedInfo = currencyExchangeFeignProxy.getCurrencyExchange(from, to);
		calculatedInfo.setCalculatedAmount(calculatedInfo.getConversionRate().multiply(quantity));
		logger.info("Conversion Rate {} -> {} * {} is {}", from, to, 
				calculatedInfo.getConversionRate(), calculatedInfo.getCalculatedAmount());
		return calculatedInfo;
	}

}
