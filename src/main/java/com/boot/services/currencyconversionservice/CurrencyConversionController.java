package com.boot.services.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CurrencyConversionController {
	@Autowired
	private Environment environment;
	@GetMapping("/currency-conversion/from/{from}/to/{to}/{quantity}")
	public CurrencyConversion getConvertedCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		//CurrencyConversion calculatedInfo = new CurrencyConversion(1l, from, to, new BigDecimal(60.04), new BigDecimal(60.04).multiply(quantity));
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversion> exchangeResponseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables);
		CurrencyConversion calculatedInfo = exchangeResponseEntity.getBody();
		calculatedInfo.setCalculatedAmount(calculatedInfo.getConversionRate().multiply(quantity));
		calculatedInfo.setPort(Integer.valueOf(environment.getProperty("local.server.port")));
		return calculatedInfo;
	}

}
