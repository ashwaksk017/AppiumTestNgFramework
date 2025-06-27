package com.function.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.baseUtility.BaseClassAndroid;
import com.pages.CartPage;
import com.pages.LoginPage;

public class AddToCart extends BaseClassAndroid {

	@Test
	public void checkIfShoeTypePresent() {
		try {
			LoginPage lpg = new LoginPage(androidDriver);
			lpg.setCountry(data);
			lpg.setUserName(data);
			lpg.setGender(data);
			CartPage cartPage = lpg.setLetsShopButton();
			String shoeName = cartPage.getShoeTypePresent(data.get("ShoeType"));
			Assert.assertEquals(data.get("ShoeType"), shoeName);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
