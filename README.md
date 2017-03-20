<pre>
Description: SpendingReport
This Java standalone application will invoke an existing Level Money web serivce to generate monthly spending report. 
Output file will be created under the current director with name like "SpendingReport" + any options + ".json".
A log file "SpendingReport.log" will be created with more debuging info. 
To see the report in the log too, log4j's logging level need to be changed to 'tracce'. 

System Requirements:
1. JDK version: 1.8
2. OS: Windows
3. Environment Path: make sure JDK 1.8 is on your environment Path.

To Compile the project in IDE:
1. Maven is requried.
2. Unzip the files to your local directory
3. Import the project into your IDE, such as Eclipse.

Run the program: 
1. Unzip and extract the files to your local directory. 
You will see "C1SpendingReport" folder which contains all files/resources. 
To just run the application, only the lib folder, SpendingReport.jar, and SpendingReport.bat are required.

2. Open Command Line Prompt and change directory to "C1SpendingReport" directory where the files were extracted to.

3. Enter the following commands for different options/features:
	a) To get monthly Spending/income report: 
		i. Type: SpendingReport (or SpendingReport.bat)
		ii. Result: A report file "SpendingReport.json" will be created under the current directory.
		
	b) To get projected spending/income report for the rest of the month plus the report for all previous months
		i. Type: SpendingReport --crystal-ball
		ii. Result: A report file "SpendingReport_CrystalBall.json" will be created under the current directory. 
		All credit card payment related transaction will show at the bottom of the file.
		
	c) To get spending/income report without including donuts related spendings:
		i. Type: SpendingReport --ignore-donuts
		ii. Result: A report file "SpendingReport_NoDonut.json" will be created under the current directory.
		
	d) To get spending/income report without credit card payments
		i. Type SpendingReport --ignore-cc-payments
		ii. Result: A report file "SpendingReport_NoCC.json" will be created under the current directory.
	
	e) Feature b), c), and d) can be combined in any way/order. For example: 
		i. SpendingReport --ignore-cc-payments --ignore-donuts or 
		ii. SpendingReport --cytal-ball --ignore-donuts or 
		iii. SpendingReport --cytal-ball --ignore-donuts --ignore-cc-payments
		
</pre>
