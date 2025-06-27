package com.reports;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.math3.stat.inference.TestUtils;

public class ExtentReporterNG {
	
	public static ExtentReports extent;

	public static ExtentReports getReportObject() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String path = System.getProperty("user.dir")+"//reports//DoorApp//"+ now.toString().replaceAll(":","-")+".html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", System.getProperty("user.name"));
		try {
			extent.setSystemInfo("Machine", InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to capture hostname.");
		}
		return extent;
	}
}
