package com.hruan.coding.SpendingReport;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.hruan.coding.SpendingReport.model.Transaction;



public class SpendingReportTest {
	
	
	protected String allTransactionParams;
	protected String projectedMonthTransactionParams;
	
	protected SpendingReportProcess reportProcess;
	
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(SpendingReportTest.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		System.out.println(result.wasSuccessful());
	}
	
	
	@Before
	public void init() {
		reportProcess = new SpendingReportProcess();
		
		this.allTransactionParams = "{\"args\":{\"uid\":1110590645,\"token\":\"156AF807DBF200A743FACFA4D1415892\",\"api-token\":\"AppTokenForInterview\",\"json-strict-mode\":false,\"json-verbose-response\":false}}";
		this.projectedMonthTransactionParams = "{\"args\":{\"uid\":1110590645,\"token\":\"156AF807DBF200A743FACFA4D1415892\",\"api-token\":\"AppTokenForInterview\",\"json-strict-mode\":false,\"json-verbose-response\":false}, \"year\": 2017, \"month\":3}";
	}
	
	@Test
	public void testGetAllTransactions(){
		SpendingReportProcess reportProcess = new SpendingReportProcess();
		
		Transaction[] transList = reportProcess.getAllTransactions(allTransactionParams);
		assertNotNull(transList);		
	}
	
	@Test
	public void testgetProjectedTransactions(){
		SpendingReportProcess reportProcess = new SpendingReportProcess();
		
		Transaction[] transList = reportProcess.getProjectedTransactions(projectedMonthTransactionParams);
		assertNotNull(transList);		
	}
	
}
