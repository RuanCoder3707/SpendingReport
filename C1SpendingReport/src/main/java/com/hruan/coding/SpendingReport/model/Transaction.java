package com.hruan.coding.SpendingReport.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7901530593631165929L;
	
	@JsonProperty("transaction-id")
	private String transactionId;
	
	private String merchant;
	
	@JsonProperty("is-pending")
	private boolean isPending;
	
	@JsonProperty("transaction-time")	
	private LocalDate transactionTime;
	
	private long amount;
	
	//transaction time in year-month;
	@JsonIgnore
	private String transactionYearMonth;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public boolean isPending() {
		return isPending;
	}

	public void setPending(boolean isPending) {
		this.isPending = isPending;
	}

	public LocalDate getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(LocalDate transactionTime) {
		this.transactionTime = transactionTime;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getTransactionYearMonth() {
		return transactionYearMonth;
	}

	public void setTransactionYearMonth(String transactionYearMonth) {
		this.transactionYearMonth = transactionYearMonth;
	}
	

}
