package com.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CartPage {
	AndroidDriver androidDriver;
	
	public CartPage(AndroidDriver androidDriver) {
		this.androidDriver=androidDriver;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}


	public String getShoeTypePresent(String shoeType) {
		
		String shoeName=androidDriver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\""+shoeType+"\")")).getText();
		
		return shoeName;
	}

}
