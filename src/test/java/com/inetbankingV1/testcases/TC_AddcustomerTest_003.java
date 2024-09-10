package com.inetbankingV1.testcases;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.inetbankingV1.pageobjects.AddCustomerPage;
import com.inetbankingV1.pageobjects.Loginpage;

public class TC_AddcustomerTest_003  extends BaseClass{

	
	@Test
	public void addNewCustomer() throws InterruptedException, IOException 
	{
		Loginpage lp =  new Loginpage(driver);
		lp.setUserName(username);
		logger.info("username provided");
		lp.setPassword(password);
		logger.info("password provided");

		lp.clicksubmit();
		
		Thread.sleep(3000);
		
		AddCustomerPage addcust= new AddCustomerPage(driver);
		
		addcust.clickAddNewCustomer();
		
		logger.info("providing customer details");

		addcust.custName("ameer");
		addcust.custgender("male");
		addcust.custdob("02","05","1993");
		addcust.custaddress("INDIA");
		addcust.custcity("Hyderabad");
		addcust.custstate("Telegana");
		addcust.custpinno("506132");
		addcust.custtelephoneno("9502568722");
		
		String email= randomString()+"@gmail.com";
		addcust.custemailid(email);
		addcust.custpassword("abcdef");
		addcust.custsubmit();
		Thread.sleep(3000);
		
		logger.info("validation started");
		
		boolean res= driver.getPageSource().contains("Customer Registered Successfully!!!");
		
		if(res==true)
		{
			Assert.assertTrue(true);
		}
		else
		{
			captureScreen(driver, "addNewCustomer");
			Assert.assertTrue(false);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public static String randomString() {
	    String generatedString = RandomStringUtils.randomAlphabetic(5);
	    return generatedString; 
	}
}

