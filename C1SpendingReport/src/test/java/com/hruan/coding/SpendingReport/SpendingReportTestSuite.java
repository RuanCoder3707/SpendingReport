package com.hruan.coding.SpendingReport;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({SpendingReportTest.class, SpendingReportFeatureTest.class})
public class SpendingReportTestSuite {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(SpendingReportTestSuite.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}

		System.out.println(result.wasSuccessful());
	}
	
}
