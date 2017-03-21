<pre>
Description: SpendingReport
This Java standalone application will invoke an existing Level Money web serivce to generate monthly spending reports. 
Output file will be created under the current director with name like "SpendingReport_CrystalBall.json".
A log file "SpendingReport.log" will be created with more debuging information. 
To see the report in the log too, log4j's logging level needs to be changed to 'trace'. 

System Requirements:
1. Oracle JDK version: 1.8
2. OS: Windows
3. Environment Path: make sure JDK 1.8 is on your environment Path.

To compile the project in IDE:
1. Maven is requried.
2. Unzip the downloaded zip file to your local directory
3. Import the project into your IDE, such as Eclipse.

To run the program: 
1. Unzip and extract the files to your local directory. 
You will see "C1SpendingReport" folder which contains all files/resources. 
To run the application, only the lib folder, SpendingReport.jar, and SpendingReport.bat are required.

2. Open Command Line Prompt and change directory to "C1SpendingReport" directory where the files are extracted to.

3. Enter the following commands for different options/features:
	a) To get monthly Spending/income report: 
		i. Type: SpendingReport (or SpendingReport.bat)
		ii. Result: A report file "SpendingReport.json" will be created under the current directory.
		
	b) To get projected spending/income report for the rest of the month plus the report for all previous months
		i. Type: SpendingReport --crystal-ball
		ii. Result: A report file "SpendingReport_CrystalBall.json" will be created under the current directory. 
		
	c) To get spending/income report without including donuts related spendings:
		i. Type: SpendingReport --ignore-donuts
		ii. Result: A report file "SpendingReport_NoDonut.json" will be created under the current directory.
		
	d) To get spending/income report without credit card payment transactions:
		i. Type SpendingReport --ignore-cc-payments
		ii. Result: A report file "SpendingReport_NoCC.json" will be created under the current directory.		
		All credit card payment related transaction will show at the end of the file.
	
	e) Feature b), c), and d) can be combined in any way/order. For example: 
		i. SpendingReport --ignore-cc-payments --ignore-donuts
		ii. SpendingReport --crystal-ball --ignore-donuts
		iii. SpendingReport --crystal-ball --ignore-donuts --ignore-cc-payments
Note: if you are receiving secret key related error when running the program, it is because your local JRE does not use unlimited JCE policy files. 
Please copy the two policy files, local_policy.jar and US_export_policy.jar, in the JCE folder and replace your local's JRE's policy files. 
Your local JCE policy files are located at %JAVA_HOME%\jre\lib\security. 
"%JAVA_HOME%" is your jdk installation location such as C:\Program Files\Java\jdk1.8.0_91.

</pre>
