package com.inetbankingV1.testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.inetbanking.utilies.XLUtils;
import com.inetbankingV1.pageobjects.Loginpage;

public class TC_Loginpage_002 extends BaseClass
{
@Test(dataProvider="LoginData")
	public void loginDDT(String username, String password) throws InterruptedException
	{
		Loginpage lp = new Loginpage(driver);
		lp.setUserName(username);
		logger.info("username provided");
		lp.setPassword(password);
		logger.info("password provided");

		lp.clicksubmit();
		Thread.sleep(3000);

		
		if(isAlertPresent()==true)
		{
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			Assert.assertTrue(false);
			logger.warn("Login  failed");

			
		}
		else
		{
			Assert.assertTrue(true);
			lp.clicklogout();
			Thread.sleep(3000);
			driver.switchTo().alert().accept();
			logger.warn("login passed");

		}
		
		
	}

public boolean isAlertPresent() 
{
	try{
		driver.switchTo().alert();
		return true;
	}
	catch(Exception e)
	{
		return false;
	}
}

@DataProvider(name="LoginData")
String[][]getData() throws IOException
{
	String path= System.getProperty("user.dir")+"/src/test/java/com/inetbanking/testData/TestData.xlsx";
	int rownum= XLUtils.getRowCount(path, "Sheet1");
	int colnum= XLUtils.getCelCount(path, "Sheet1", 1);
	
	String logindata[][]= new String [rownum][colnum];
	
	for(int i=1;i<=rownum;i++)
	{
		for(int j=0;j<colnum;j++)
		{
			logindata[i-1][j]=XLUtils.getCellData(path, "Sheet1", i, j);
		}
	}
	

	return logindata;
}
}