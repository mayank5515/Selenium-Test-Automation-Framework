package com.automation.framework.listeners;

import com.automation.framework.driver.DriverFactory;
import com.automation.framework.utils.ExtentManager;
import com.automation.framework.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;

/**
 * Hooks into TestNG: logs every test to the Extent report and,
 * when a test fails, takes a screenshot and attaches it to the report.
 */
public class TestListener implements ITestListener {

    private static final ExtentReports EXTENT = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        String name = result.getMethod().getMethodName();
        if (result.getParameters().length > 0) {
            name += " " + Arrays.toString(result.getParameters());
        }
        TEST.set(EXTENT.createTest(name).assignCategory(result.getTestClass().getRealClass().getSimpleName()));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        TEST.get().pass("Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = TEST.get();
        test.fail(result.getThrowable());

        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            try {
                Path shot = ScreenshotUtils.capture(driver, result.getMethod().getMethodName());
                // Embedded as base64 so the report stays a single self-contained HTML file
                String base64 = Base64.getEncoder().encodeToString(Files.readAllBytes(shot));
                test.addScreenCaptureFromBase64String(base64, "Failure screenshot");
            } catch (Exception e) {
                test.warning("Could not capture screenshot: " + e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TEST.get().skip("Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        EXTENT.flush();
    }
}
