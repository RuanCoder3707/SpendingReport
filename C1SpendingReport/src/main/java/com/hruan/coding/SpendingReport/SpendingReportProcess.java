package com.hruan.coding.SpendingReport;

import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hruan.coding.SpendingReport.model.SpendingReport;
import com.hruan.coding.SpendingReport.model.Transaction;
import com.hruan.coding.SpendingReport.model.TransactionResponse;

public class SpendingReportProcess {
	private static ResourceBundle appProp =  ResourceBundle.getBundle("ApplicationResources");
	private static Log log = LogFactory.getFactory().getInstance(SpendingReportProcess.class);	
	
	public static void main(String[] args){
		try {
			
			SpendingReportProcess report = new SpendingReportProcess();
			
			report.generateReport(Arrays.asList(args));
			
		} catch (Exception e) {
			log.debug(e);
		}
		
		
	}
	public void generateReport(List<String> options) throws Exception{
		log.debug("Generating Spending Report with options : " + String.join(", ", options));
		//get all transactions
		String criteria = this.composeAllTransactionCriteria();
		Transaction[] transactions = this.getAllTransactions(criteria);	
		
		if(options.contains(OPTION.ProjectedTransactionForMonth.toString())){
			criteria = this.composeProjectedTransactionCriteria();
			Transaction[]  projectedTxns = this.getProjectedTransactions(criteria);			
			if(projectedTxns != null){				 
				transactions = Stream.concat(Stream.of(transactions), Stream.of(projectedTxns)).toArray(Transaction[]::new);
			}			
		}
		
		this.printReport(transactions, options);
		
	}
	
	public Transaction[] getAllTransactions(String criteria) {
		log.debug("Get All Transactions Criteria: " + criteria);
		
		String restServiceURL = appProp.getString("c1.level.money.api.getalltransaction.url");
				
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
		
			HttpPost httpRequest = new HttpPost(restServiceURL);
			
			httpRequest.addHeader("content-type", "application/json");
			httpRequest.addHeader("Accept", "application/json");
						
			ObjectMapper objMapper = new ObjectMapper();
			objMapper.findAndRegisterModules();
			
			httpRequest.setEntity(new StringEntity(criteria));
			
			try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
			
				TransactionResponse responseObj = objMapper.readValue(httpResponse.getEntity().getContent(), TransactionResponse.class);
				if(responseObj == null || !"no-error".equalsIgnoreCase(responseObj.getError())){
					log.debug("Error returned from the service call " + responseObj.getError());
					return null;
				}
				
				return responseObj.getTransactions();
			}
		
			
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
		}
		
		return null;
		
	}
	
	public Transaction[] getProjectedTransactions(String criteria) {
		log.debug("Get projefted Transactions for a month criteria: " + criteria);
		
		String restServiceURL = appProp.getString("c1.level.money.api.getprojectedtransactionformonth.url");
				
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
		
			HttpPost httpRequest = new HttpPost(restServiceURL);
			
			httpRequest.addHeader("content-type", "application/json");
			httpRequest.addHeader("Accept", "application/json");
						
			ObjectMapper objMapper = new ObjectMapper();
			objMapper.findAndRegisterModules();
			
			httpRequest.setEntity(new StringEntity(criteria));
			
			try (CloseableHttpResponse httpResponse = httpClient.execute(httpRequest)) {
			
				TransactionResponse responseObj = objMapper.readValue(httpResponse.getEntity().getContent(), TransactionResponse.class);
				if(responseObj == null || !"no-error".equalsIgnoreCase(responseObj.getError())){
					log.debug("Error returned from the service call " + responseObj.getError());
					return null;
				}
				
				return responseObj.getTransactions();
			}
		
			
		} catch (Exception e) {
			log.debug(e);
		}
		
		return null;
		
	}
	
	public void printReport(Transaction[] transactions) throws Exception {
		this.printReport(transactions, null);
	}
	
	public void printReport(Transaction[] transactions, List<String> options) throws Exception{
		if(transactions == null || transactions.length == 0){
			log.debug("No Report Data Available!");
			return;
		}
		
		
		List<Transaction> transactioinList = new ArrayList<Transaction>(Arrays.asList(transactions));	
		transactioinList.forEach(txn -> this.transformTransactionTime(txn));
		
		boolean ignoreCCPayment = options != null && options.contains(OPTION.IgnoreCCPayments.toString()) ? true: false;
		Transaction[] creditPaymentTxns = null;
		if(ignoreCCPayment) {
			creditPaymentTxns = this.getCCPaymentTransactions(transactions);
			if(creditPaymentTxns != null){				
				transactioinList.removeAll(Arrays.asList(creditPaymentTxns));
			}
		}
		
		Map<String, Long> incomeMap = transactioinList.stream().filter(t -> t.getAmount() >= 0 && !t.isPending())
				.collect(Collectors.groupingBy(Transaction::getTransactionYearMonth, Collectors.summingLong(Transaction::getAmount)));			
		
		boolean ignoreDonutTxn = options != null && options.contains(OPTION.IgnoreDonut.toString()) ? true: false;
		
		Map<String, Long> spentMap = null;
		if(!ignoreDonutTxn) {
			spentMap = transactioinList.stream().filter(t -> !t.isPending() && t.getAmount() < 0)		
				.collect(Collectors.groupingBy(Transaction::getTransactionYearMonth, Collectors.summingLong(Transaction::getAmount)));	
		}
		else {
			log.debug("Ignoring Donut Transactions...");
			spentMap = transactioinList.stream()
					.filter(t -> !t.isPending() && t.getAmount() < 0 && !Arrays.asList(appProp.getString("transaction.merchant.donut").toLowerCase().split("\\|")).contains(t.getMerchant().toLowerCase()))		
					.collect(Collectors.groupingBy(Transaction::getTransactionYearMonth, Collectors.summingLong(Transaction::getAmount)));	
		}
		Map<String, SpendingReport> report = this.composeFinalReport(incomeMap, spentMap);
		
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.findAndRegisterModules();
		
		log.trace(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(report));
		
		//write a report file--assume report is local to current directory
		String outputFilePath = "SpendingReport.json";		
		FileOutputStream out = new FileOutputStream(outputFilePath); 
		out.write("######Below are Spending Reports for all transactions######\r\n".getBytes());
		objMapper.writerWithDefaultPrettyPrinter().writeValue(out, report);
		
		
		//Print payment report
		if(creditPaymentTxns != null && creditPaymentTxns.length > 0){
			log.trace("######Below are Credit Card Payments Transactions######");
			log.trace(objMapper.writerWithDefaultPrettyPrinter().writeValueAsString(creditPaymentTxns));
			out = new FileOutputStream(outputFilePath, true); 
			out.write("\r\n######Below are Credit Card Payments Transactions######\r\n".getBytes());			
			objMapper.writerWithDefaultPrettyPrinter().writeValue(out, creditPaymentTxns);
			
		}
	}
	
	
	private String composeAllTransactionCriteria() {		
		return "{\"args\":{\"uid\":1110590645,\"token\":\"156AF807DBF200A743FACFA4D1415892\",\"api-token\":\"AppTokenForInterview\",\"json-strict-mode\":false,\"json-verbose-response\":false}}";	
	}
	
	private String composeProjectedTransactionCriteria() {		
		return "{\"args\":{\"uid\":1110590645,\"token\":\"156AF807DBF200A743FACFA4D1415892\",\"api-token\":\"AppTokenForInterview\",\"json-strict-mode\":false,\"json-verbose-response\":false}, \"year\": 2017, \"month\":3}";	
	}
	
	private Transaction[] readDataFromLocal() throws Exception{
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.findAndRegisterModules();
		
		TransactionResponse res = objMapper.readValue(new File("C:\\hruan\\json.txt"), TransactionResponse.class);
		
		return res.getTransactions();
		
	}
	
	private void transformTransactionTime(Transaction txn){
		LocalDate transactionTime = txn.getTransactionTime();
		
		if(transactionTime.getMonthValue() < 10){
			txn.setTransactionYearMonth(transactionTime.getYear() + "-0" + transactionTime.getMonthValue());
		}
		else {
			txn.setTransactionYearMonth(transactionTime.getYear() + "-" + transactionTime.getMonthValue());
		}
		
		
	}
	
	private Map<String, SpendingReport> composeFinalReport(Map<String, Long> incomeMap, Map<String, Long> spentMap){
		Map<String, SpendingReport> mergedMap = new HashMap<String, SpendingReport>();
		
		String date = null	;	
		NumberFormat currencyFormater = NumberFormat.getCurrencyInstance(Locale.US); 
		
		currencyFormater.setGroupingUsed(false);
		for(Iterator<String> iterator = incomeMap.keySet().iterator(); iterator.hasNext(); ) {
			date = iterator.next();
			mergedMap.put(date, new SpendingReport(currencyFormater.format(spentMap.get(date)*(-1)/10000.0), currencyFormater.format(incomeMap.get(date)/10000.0)));
		}

		
		//sort by date before return
		Map<String, SpendingReport> sortedreport = mergedMap.entrySet().stream().sorted(
				(e1,e2)->e1.getKey().compareTo(e2.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (e1, e2) -> e1, LinkedHashMap::new));
		
		//calculate average
		double averageIncome = incomeMap.values().stream().collect(Collectors.averagingLong(v ->v.longValue()));
		double averageSpent = spentMap.values().stream().collect(Collectors.averagingLong(v ->v.longValue()));
		
		sortedreport.put("average", new SpendingReport(currencyFormater.format(averageSpent*(-1)/10000.0), currencyFormater.format(averageIncome/10000.0)));
		
		return sortedreport;
	}
	
	private Transaction[] getCCPaymentTransactions(Transaction[] allTransactions){
		log.debug("Get Credit Card Payment transactioins...");
		if(allTransactions == null || allTransactions.length == 0){
			return null;
		}
		
		List<Transaction> transactioinList = new ArrayList<Transaction>(Arrays.asList(allTransactions));	
		return transactioinList.stream()
				.filter(t -> transactioinList.stream().anyMatch(p -> (p.getAmount() < 0 && p.getAmount() + t.getAmount() == 0 && t.getTransactionTime().minusDays(1).isBefore(p.getTransactionTime())))).toArray(Transaction[]::new);
		
	}
	
	private enum OPTION {
		IgnoreDonut(appProp.getString("option.ignore.donuts")), IgnoreCCPayments(appProp.getString("option.ignore.cc.payments")), 
		ProjectedTransactionForMonth(appProp.getString("option.projected.transaction.for.a.month")), FileOutput(appProp.getString("option.report.output"));
		private final String value;
		private OPTION (final String value){
			this.value = value;
		}
		
		
		@Override
		public String toString(){
			return this.value;
		}
	}
	
	
	
}
