package com.boot.services.currencyconversionservice;

import java.math.BigDecimal;

public class CurrencyConversion {
	private Long id;
	private String from;
	private String to;
	private BigDecimal conversionRate;
	private BigDecimal calculatedAmount;
	private int port;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public BigDecimal getConversionRate() {
		return conversionRate;
	}
	public void setConversionRate(BigDecimal conversionRate) {
		this.conversionRate = conversionRate;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public BigDecimal getCalculatedAmount() {
		return calculatedAmount;
	}
	public void setCalculatedAmount(BigDecimal calculatedAmount) {
		this.calculatedAmount = calculatedAmount;
	}
	public CurrencyConversion(Long id, String from, String to, 
			BigDecimal conversionRate, BigDecimal calculatedAmount) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionRate = conversionRate;
		this.calculatedAmount = calculatedAmount;
	}
	public CurrencyConversion() {
		super();
	}
	
}
