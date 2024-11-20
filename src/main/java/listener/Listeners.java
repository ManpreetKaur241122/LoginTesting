package listener;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.Base;
import utilities.ExtentReporter;

public class Listeners extends Base implements ITestListener {
	
	ExtentReports extentReport = ExtentReporter.getExtentReport();
	ExtentTest extentTest;
	
	@Override
	public void onTestStart(ITestResult result) {
		
		extentTest = extentReport.createTest(result.getName()+" execution started");
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
		extentTest.log(Status.PASS,"Test Passed");
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		
		extentTest.fail(result.getThrowable());
		
		
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
		
		
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		
		
		
	}

	@Override
	public void onStart(ITestContext context) {
		
		
		
	}

	@Override
	public void onFinish(ITestContext context) {
		
		extentReport.flush();
		
	}
	
	

}