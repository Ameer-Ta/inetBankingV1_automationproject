package com.inetbankingV1.testcases;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.inetbanking.utilies.ReadConfig;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	ReadConfig readconfig = new ReadConfig();
	public String baseURL = readconfig.getApplicationURL();
	public String username = readconfig.getUsername();
	public String password = readconfig.getPassword();
	public String chromepath=readconfig.getChromepath();
	public static WebDriver driver;
	public static Logger logger; // Make this a static logger


	@Parameters("browser")
	@BeforeClass
	public void setup(String br) {

		// Initialize the logger here
		logger = Logger.getLogger("ebanking");
		PropertyConfigurator.configure("C:\\Users\\Ameer\\eclipse-workspace\\inetBankingV1\\src\\main\\resources\\Log4j.properties");

		try {
			// Browser setup based on parameter
			if (br.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				driver = new ChromeDriver(chromeOptions);
			} 
			else if (br.equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				FirefoxOptions firefoxOptions = new FirefoxOptions();
				firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
				driver = new FirefoxDriver(firefoxOptions);
			}
			else if (br.equalsIgnoreCase("ie")) {
				WebDriverManager.iedriver().setup();
				InternetExplorerOptions ieOptions = new InternetExplorerOptions();
				ieOptions.ignoreZoomSettings();
				ieOptions.setCapability("nativeEvents", false); // Optional
				driver = new InternetExplorerDriver(ieOptions);
			} 
			else {
				throw new IllegalArgumentException("Browser type not supported: " + br);
			}
			driver.get(baseURL);

			// Maximize browser window
			driver.manage().window().maximize();
			logger.info("Browser launched successfully");

		} catch (Exception e) {
			logger.error("Error during browser setup: " + e.getMessage());
			throw e; // Rethrow the exception after logging
		}
	}
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
	public void captureScreen(WebDriver driver, String tname) throws IOException{
		TakesScreenshot ts = ( TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir")+"/Screenshots/" +tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
		
	}
}





