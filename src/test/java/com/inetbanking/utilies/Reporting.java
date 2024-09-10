package com.inetbanking.utilies;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporting extends TestListenerAdapter
{

	public ExtentSparkReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest logger;

    public void onStart(ITestContext testContext) {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // Timestamp for the report name
        String repName = "Test-Report-" + timestamp + ".html";

        htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/" + repName); // Report output path
        try {
			htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/extent-config.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host name", "localhost");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Ameer");

        // Configuration of the report
        htmlReporter.config().setDocumentTitle("InetBanking Test Project");
        htmlReporter.config().setReportName("Functional Test Report");
        htmlReporter.config().setTheme(Theme.DARK);  // ChartLocation has been deprecated, so remove this line

    }

    public void onTestSuccess(ITestResult tr) {
        logger = extent.createTest(tr.getName()); // Create new entry in the report
        logger.log(Status.PASS, MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN)); // Mark test as passed
    }

    public void onTestFailure(ITestResult tr) {
        logger = extent.createTest(tr.getName()); // Create new entry in the report
        logger.log(Status.FAIL, MarkupHelper.createLabel(tr.getName(), ExtentColor.RED)); // Mark test as failed

        String screenshotPath = System.getProperty("user.dir") + "\\Screenshots\\" + tr.getName() + ".png"; // Screenshot path

        File f = new File(screenshotPath);

        if (f.exists()) {
            logger.fail("Screenshot is below: " + logger.addScreenCaptureFromPath(screenshotPath)); // Add screenshot to the report
        }
    }

    public void onTestSkipped(ITestResult tr) {
        logger = extent.createTest(tr.getName()); // Create new entry in the report
        logger.log(Status.SKIP, MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE)); // Mark test as skipped
    }

    public void onFinish(ITestContext testContext) {
        extent.flush(); // Write everything to the report
    }
}
