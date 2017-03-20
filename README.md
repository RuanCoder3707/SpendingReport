<<<<<<< HEAD
# SpendingReport

This Java standalone application will invoke an existing Level Money web serivce to generate monthly spending report. Due to time limiation, this package is only packed with a windows batch file to run in Windows environment

System Requirements:
JDK version: 1.8
OS: Windows
Environment Path: make sure JDK 1.8 is on your environment Path.

To Compile the project in IDE:
1. Maven is requried.
2. Unzip the files to your local directory
3. Import the project into your IDE, such as Eclipse.

Run the program: 
1. Unzip and extract the files to your local directory. To just run the application, only the lib folder, SpendingReport.jar, and SpendingReport.bat are required.
2. Open Command Line Prompt and change directory to the local directory where the files were extracted to.
3. Enter the following commands for different options/features:
	a) To get monthly Spending/income report: 
		type: SpendingReport (or SpendingReport.bat)
		Result: A report file "SpendingReport.json" will be created under the current directory.
		
	b) To get projected spending/income report for the rest of the month plus the report for all previous months
		type: SpendingReport --crystal-ball
		Result: A report file "SpendingReport.json" will be created under the current directory. All credit card payment related transaction will show at the bottom of the file.
		
	c) To get spending/income report without including donuts related spendings:
		type: SpendingReport --ignore-donuts
		Result: A report file "SpendingReport.json" will be created under the current directory.
		
	d) To get spending/income report without credit card payments
		-- type SpendingReport --ignore-cc-payments
		Result: A report file "SpendingReport.json" will be created under the current directory.
	
	e) Feature b), c), and d) can be combined in any way/order. For example: 
		SpendingReport --ignore-cc-payments --ignore-donuts or 
		SpendingReport --cytal-ball --ignore-donuts or 
		SpendingReport --cytal-ball --ignore-donuts --ignore-cc-payments
=======
# SpendingReport

This Java standalone application will invoke an existing Level Money web serivce to generate monthly spending report. Due to time limiation, this package is only packed with a windows batch file to run in Windows environment

System Requirements:
JDK version: 1.8
OS: Windows
Environment Path: make sure JDK 1.8 is on your environment Path.

To Compile the project in IDE:
1. Maven is requried.
2. Unzip the files to your local directory
3. Import the project into your IDE, such as Eclipse.

Run the program: 
1. Unzip and extract the files to your local directory. To just run the application, only the lib folder, SpendingReport.jar, and SpendingReport.bat are required.
2. Open Command Line Prompt and change directory to the local directory where the files were extracted to.
3. Enter the following commands for different options/features:
	a) To get monthly Spending/income report: 
		type: SpendingReport (or SpendingReport.bat)
		Result: A report file "SpendingReport.json" will be created under the current directory.
		
	b) To get projected spending/income report for the rest of the month plus the report for all previous months
		type: SpendingReport --crystal-ball
		Result: A report file "SpendingReport.json" will be created under the current directory. All credit card payment related transaction will show at the bottom of the file.
		
	c) To get spending/income report without including donuts related spendings:
		type: SpendingReport --ignore-donuts
		Result: A report file "SpendingReport.json" will be created under the current directory.
		
	d) To get spending/income report without credit card payments
		-- type SpendingReport --ignore-cc-payments
		Result: A report file "SpendingReport.json" will be created under the current directory.
	
	e) Feature b), c), and d) can be combined in any way/order. For example: 
		SpendingReport --ignore-cc-payments --ignore-donuts or 
		SpendingReport --cytal-ball --ignore-donuts or 
		SpendingReport --cytal-ball --ignore-donuts --ignore-cc-payments
>>>>>>> refs/remotes/origin/master
