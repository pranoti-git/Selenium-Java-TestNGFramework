package utils.listeners;

import io.qameta.allure.Attachment;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.reporters.Files;
import utils.BrowserSetup;
import utils.TestBase;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class TestListeners implements IInvokedMethodListener, ITestListener {

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if(TestBase.classLogs!=null){
            TestBase.classLogs.delete(0,TestBase.classLogs.length());
        }
        System.out.println("Method Started $$ " + method.getTestMethod().getMethodName() + " of Class : " + testResult.getTestClass());
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("Execution Finished For $$ " + method.getTestMethod().getMethodName() + " of Class : " + testResult.getTestClass());
//                attachLogs(testResult.getMethod().getConstructorOrMethod().getName());
//        attachLogs(method.getTestMethod().getMethodName(),method.getTestMethod().getMethodName());
        if(TestBase.classLogs != null){
            attachLogs(method.getTestMethod().getMethodName(),TestBase.classLogs);

        }

        if(testResult.getThrowable() != null){
            System.out.println(testResult.getThrowable().getMessage());
            attachScreenshot(method.getTestMethod().getMethodName(),BrowserSetup.getDriver());
        }
    }

    @Override
    public void onTestSuccess(ITestResult testResult){
        System.out.println("Case Pass $$ " + testResult.getMethod().getMethodName());
        attachScreenshot(testResult.getMethod().getMethodName(),BrowserSetup.getDriver());
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
    public void attachLogs(String name,StringBuilder logs){
        System.out.println("Attaching Logs for " + name);
        try{
            Allure.addAttachment("Logs_"+name, logs.toString()); // Files.readFile(new File("src/test/output/logs/Automation.log"))
        }
        catch (Exception e){
            System.out.println("Unable to attach logs. " + e.getMessage());
        }
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
