@echo off

rem accept up to 4 parameters: first 3 options 
set options=%1 %2 %3

java -classpath "%cd%\SpendingReport.jar;%cd%\lib\*;" com.hruan.coding.SpendingReport.SpendingReportProcess %options%