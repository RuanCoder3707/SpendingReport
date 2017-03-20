package com.hruan.coding.SpendingReport;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class SpendingReportFeatureTest {
	protected String allTransactionParams;
	private String option;
	
	
	public SpendingReportFeatureTest(String option) {
		this.option = option;
	}
	
	@Test
	public void testGenereateReport(){
		try {
			SpendingReportProcess reportProcess = new SpendingReportProcess();
			
			reportProcess.generateReport(Arrays.asList(new String[] {this.option}));
			assertTrue(true);
		} catch (Exception e) {
			assertFalse(false);
		}
	}
	
	@Parameterized.Parameters
	public static Collection<String> commandOption() {
		return Arrays.asList(new String[] {
				"--ignore-donuts", "--ignore-cc-payments", "--crystal-ball"
		});
		
	}
	
	
	
}
