package framework.utils;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import framework.common.UIMethods;
import framework.selenium.SeleniumDriverManager;

public class ReportNGListener implements ITestListener {
	WebDriver driver = SeleniumDriverManager.getManager().getDriver();
	Logger log = Logger.getLogger(getClass());

	@Override
	public void onStart(ITestContext result) {
		PropertyConfigurator.configure("log4j.properties");
		log.info("Start Of Execution(TEST)-> " + result.getName());
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		log.info("Test Started-> " + result.getName());
	}
	
	@Override
	public void onTestSuccess(ITestResult result) {
		log.info("Test Pass-> " + result.getName());
	}
	
	@Override
	public void onTestFailure(ITestResult result) {
		try {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		String filePath = System.getProperty("user.dir") + "/test-output/html/screenshots/";
		String failureImageFileName;
		if(result.getMethod().toString().contains("java.lang")) {
			failureImageFileName =  result.getName() + "_" + result.getParameters()[0] + ".png"; 
		} else {
			failureImageFileName =  result.getName() + ".png"; 
		}
		UIMethods.takeScreenShot(filePath, failureImageFileName);
		Reporter.log("<a href=\"" + filePath + failureImageFileName 
				+ "\"><img src=\"file:///" + filePath + failureImageFileName 
				+ "\" alt=\"\"" + "height='100' width='100'/> " + "<br />"); 
		Reporter.setCurrentTestResult(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.error("Test Failed-> " + result.getName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		log.warn("Test Skipped-> " + result.getName());
	}
	
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		log.info("Success percentage failure -> " + result.toString());
	}
		
	@Override
	public void onFinish(ITestContext context) {
		log.info("END Of Execution(TEST)-> " + context.getName());
	}	
}
