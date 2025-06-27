package com.pages;

import java.util.HashMap;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.utility.AndroidUtility;
import com.utility.CommonUtility;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage {
	AndroidDriver androidDriver;

	public LoginPage(AndroidDriver androidDriver) {
		this.androidDriver = androidDriver;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
	}

	@AndroidFindBy(xpath = "//android.widget.Spinner[@resource-id=\"com.androidsample.generalstore:id/spinnerCountry\"]")
	private WebElement countryDropDownClick;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text=\"Angola\"]")
	private WebElement countrySelectionClick;
	
	
	@AndroidFindBy(xpath = "//android.widget.EditText[@resource-id=\"com.androidsample.generalstore:id/nameField\"]")
	private WebElement userName;

	@AndroidFindBy(xpath = "//android.widget.RadioButton[@resource-id=\"com.androidsample.generalstore:id/radioFemale\"]")
	private WebElement genderFemale;

	@AndroidFindBy(xpath = "//android.widget.RadioButton[@resource-id=\"com.androidsample.generalstore:id/radioMale\"]")
	private WebElement genderMale;

	@AndroidFindBy(xpath = "//android.widget.Button[@resource-id=\"com.androidsample.generalstore:id/btnLetsShop\"]")
	private WebElement letsShopButton;


	public CartPage setLetsShopButton() {
		letsShopButton.click();
		return new CartPage(androidDriver);
	}

	public void setUserName(HashMap<String, String> data) {
		userName.sendKeys(data.get("UserName"));
	}

	public void setGender(HashMap<String, String> data) {
		if (data.equals(data.get("Gender"))) {
			genderFemale.click();
		} else {
			genderMale.click();
		}

	}

	/*
	 * public Object setLetsShopButton(WebElement letsShopButton) {
	 * letsShopButton.click(); return driver; }
	 */

	public void setCountry(HashMap<String, String> data) throws InterruptedException {
		Thread.sleep(5000);
		countryDropDownClick.click();
		System.out.println("Attempting to select "+data.get("country"));
		AndroidUtility.scrollIntoView(data);
		
		

	}

}
