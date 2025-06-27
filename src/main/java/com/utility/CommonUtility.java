package com.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import com.baseUtility.BaseClassAndroid;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.clipboard.ClipboardContentType;

public class CommonUtility extends BaseClassAndroid {

	public static String getDirePath() {
		System.out.println(System.getProperty("user.dir"));
		String dir = System.getProperty("user.dir").replaceAll("\\\\", "/");
		String finalDir = dir.replaceAll("/", "//");
		return finalDir;
	}

	public HashMap<String, String> getTestData(String TCID) throws FilloException, IOException {

		HashMap<String, String> data = new HashMap<String, String>();
		String testDataFilePath = loadConfig().getProperty("testDataPath");

		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(testDataFilePath);
		String strQuery = "Select * from Sheet1 where ClassName='" + TCID + "'";
		Recordset recordset = connection.executeQuery(strQuery);

		while (recordset.next()) {
			ArrayList<String> columnNames = recordset.getFieldNames();
			for (String name : columnNames) {
				data.put(name, recordset.getField(name));
			}
		}

		recordset.close();
		connection.close();

		return data;

	}

}
