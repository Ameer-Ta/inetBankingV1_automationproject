package com.inetbankingV1.testcases;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.inetbankingV1.pageobjects.Loginpage;

public class TC_LoginTest_001 extends BaseClass {
	
	
	
	    @Test
	    public void loginTest() throws IOException {
	        driver.get(baseURL);
	        logger.info("Navigated to baseURL"); // Use the initialized logger
	        Loginpage lp = new Loginpage(driver);
	        lp.setUserName(username);
	        logger.info("Entered username");
	        lp.setPassword(password);
	        logger.info("Entered password");
	        lp.clicksubmit();
	        
	        if(driver.getTitle().equals("Guru99 Bank Manager HomePage")) {
	            Assert.assertTrue(true);
	            logger.info("Login test passed");
	        } else {
	        	
	        	captureScreen( driver, "loginTest");
	            Assert.assertTrue(false);
	            logger.info("Login test failed");
	        }
	    }
	}



