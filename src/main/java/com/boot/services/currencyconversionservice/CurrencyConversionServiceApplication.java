package com.boot.services.currencyconversionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import brave.sampler.Sampler;

@SpringBootApplication

//Scans for feign clients/proxies in this package
@EnableFeignClients("com.boot.services.currencyconversionservice")

//Registers this server/service to Eureka naming server mentioned in application.properties
//This server/service will be registered with the name as spring.application.name
//eureka.client.service-url.default-zone is the property that points to eureka url
@EnableDiscoveryClient

public class CurrencyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceApplication.class, args);
	}

	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
}
