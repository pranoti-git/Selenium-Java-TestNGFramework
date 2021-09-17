package utils.listeners;

import io.qameta.allure.Attachment;
import org.bouncycastle.util.test.TestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.BrowserSetup;

public class TestListeners implements IInvokedMethodListener, ITestListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("Method Started $$ " + method.getTestMethod().getMethodName() + " of Class : " + testResult.getTestClass());
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("Execution Finished For $$ " + method.getTestMethod().getMethodName() + " of Class : " + testResult.getTestClass());
        attachLogs(testResult.getMethod().getConstructorOrMethod().getName());
        if(testResult.getThrowable() != null){
            System.out.println(testResult.getThrowable().getMessage());
            attachScreenshot(BrowserSetup.getDriver());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result){
        System.out.println("Case Pass $$ " + result.getMethod().getMethodName());
        attachScreenshot(BrowserSetup.getDriver());
    }

    @Override
    public void onTestFailure(ITestResult result){
        System.out.println("Case Failed $$ " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped (ITestResult result){
        System.out.println("Case Skipped $$ " + result.getMethod().getMethodName());
    }



    @Attachment(value = "Logs", type = "text/plain")
    public static String attachLogs(String logs) {
        return logs;
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] attachScreenshot(WebDriver driver) {
        System.out.println("Attaching screenshot of URL $$ " + driver.getCurrentUrl());
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


}
