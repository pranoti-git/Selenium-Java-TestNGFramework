package utils.listeners;

import io.qameta.allure.Attachment;
import io.qameta.allure.Allure;
import org.bouncycastle.util.test.TestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.BrowserSetup;

import java.io.ByteArrayInputStream;

public class TestListeners implements IInvokedMethodListener, ITestListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("Method Started $$ " + method.getTestMethod().getMethodName() + " of Class : " + testResult.getTestClass());
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("Execution Finished For $$ " + method.getTestMethod().getMethodName() + " of Class : " + testResult.getTestClass());
//        attachLogs(testResult.getMethod().getConstructorOrMethod().getName());
        attachLogs(testResult.getMethod().getMethodName(),testResult.getMethod().getMethodName());
        if(testResult.getThrowable() != null){
            System.out.println(testResult.getThrowable().getMessage());
            attachScreenshot(method.getTestMethod().getMethodName(),BrowserSetup.getDriver());
        }
    }

    @Override
    public void onTestSuccess(ITestResult testResult){
        attachScreenshot(testResult.getMethod().getMethodName(),BrowserSetup.getDriver());
        System.out.println("Case Pass $$ " + testResult.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult testResult){
        System.out.println("Case Failed $$ " + testResult.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped (ITestResult testResult){
        System.out.println("Case Skipped $$ " + testResult.getMethod().getMethodName());
    }

    @Attachment(value = "Logs", type = "text/plain")
    public static String attachLogs(String logs) {
        return logs;
    }

    @Attachment(value = "Logs", type = "text/plain")
    public void attachLogs(String name,String logs) {
        System.out.println("Attaching Logs for " + name);
        Allure.addAttachment("Logs_"+name,logs);
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] attachScreenshot(WebDriver driver) {
        System.out.println("Attaching screenshot of URL $$ " + driver.getCurrentUrl());
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public void attachScreenshot(String name,WebDriver driver) {
        System.out.println("Attaching screenshot of URL $$ " + driver.getCurrentUrl());
        Allure.addAttachment("Screenshot_"+name, new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
    }


}
