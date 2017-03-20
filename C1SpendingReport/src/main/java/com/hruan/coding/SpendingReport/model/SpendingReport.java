package com.hruan.coding.SpendingReport.model;

import java.io.Serializable;

public class SpendingReport implements Serializable {
	private static final long serialVersionUID = 5798888992758994742L;
	
	private String spent ;
	private String income ;
	
	public SpendingReport() {
		
	}
	
	public SpendingReport(String spent, String income){
		this.spent = spent;
		this.income = income;
	}

	public String getSpent() {
		return spent;
	}

	public void setSpent(String spent) {
		this.spent = spent;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}
	

}
