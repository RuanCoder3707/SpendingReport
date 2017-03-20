package com.hruan.coding.SpendingReport.model;

import java.io.Serializable;

public class TransactionResponse implements Serializable{
	private static final long serialVersionUID = 11213282513772324L;

	private String error;
	
	private Transaction[] transactions;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Transaction[] getTransactions() {
		return transactions;
	}

	public void setTransactions(Transaction[] transactions) {
		this.transactions = transactions;
	}

}
