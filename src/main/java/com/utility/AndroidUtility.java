package com.utility;

import java.util.HashMap;

import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import com.baseUtility.BaseClassAndroid;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.clipboard.ClipboardContentType;

public class AndroidUtility extends BaseClassAndroid{
	
AndroidDriver driver;
	

	public static void longPressGesture(WebElement element) {

		((JavascriptExecutor) androidDriver).executeScript("mobile: longClickGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "duration", 2000));
	}

	public static void scrollUntillEnd() {
		boolean canScrollMore;
		do {
			canScrollMore = (Boolean) ((JavascriptExecutor) androidDriver).executeScript("mobile: scrollGesture", ImmutableMap
					.of("left", 100, "top", 100, "width", 200, "height", 200, "direction", "down", "percent", 4.0));
		} while (canScrollMore);

	}

	public static boolean scrollFlingGesture(WebElement element) {
		boolean canScrollMore = (Boolean) ((JavascriptExecutor) androidDriver).executeScript("mobile: flingGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "direction", "down", "speed", 500));

		return canScrollMore;
	}

	public static void scrollIntoView(HashMap<String, String> data) {
		androidDriver.findElement(AppiumBy
				.androidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + data.get("country") + "\"));"));
		androidDriver.findElement(AppiumBy.xpath("//android.widget.TextView[@text=\""+data.get("country")+"\"]")).click();
				
	}

	public static void swipeGesture(WebElement ele, String direction) {
		((JavascriptExecutor) androidDriver).executeScript("mobile: swipeGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) ele).getId(),

						"direction", direction, "percent", 0.75));
	}

	public static void dragDrop(WebElement element, int x, int y) {
		((JavascriptExecutor) androidDriver).executeScript("mobile: dragGesture",
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(), "endX", x, "endY", y));
	}

	public static void deviceRotation(int degree) {
		// Set device rotation to landscape 90
		// Set device rotation to portrait 180
		DeviceRotation landscapeRotation = new DeviceRotation(0, 0, degree);
		androidDriver.rotate(landscapeRotation);
	}

	public static void copyText(String text) {
		androidDriver.setClipboardText(text);
	}

	public static void pasteText(ClipboardContentType text, WebElement element) {

		element.sendKeys(androidDriver.getClipboard(text));
	}

	public static void keyBoardEvent(ClipboardContentType text, WebElement element) {
		/*
		 * AndroidDriver dr = (AndroidDriver) driver;
		 * dr.pressKeyCode(AndroidKeyCode.BACK);
		 */

		// driver.pressKey(new KeyEvent(AndroidKeyCode.Home));
	}


}
