package utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import utils.listeners.TestListeners;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

@Listeners(TestListeners.class)

public abstract class TestBase {
    private BrowserSetup browserSetup;
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static Logger logger;
    public static StringBuilder classLogs;

    @BeforeTest(alwaysRun = true)
    public void setLogger(){
        System.out.println("\n\n\n\n\n*************** Setting Logger ***************");
        logger = LogManager.getLogger(TestBase.class);
        TestBase.log("*************** Logger Set ***************");
    }

    @BeforeTest(alwaysRun = true,dependsOnMethods = "setLogger")
    public void invokeBrowser(){
        TestBase.log("*************** Invoking Browser ***************");
        browserSetup = new BrowserSetup();
        driver = browserSetup.invokeWebDriver(logger);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,15);
        TestBase.log("*************** Invoking Finished ***************");
    }

    @BeforeTest(alwaysRun = true,dependsOnMethods = "invokeBrowser")
    public void navigateToBaseURL(){
        String url = "http://demo.guru99.com/test/newtours/";
        TestBase.log("Navigating to Base URL " + url);
        try{
            driver.get(url);
        }
        catch (Exception e){
            Assert.fail("Unable to navigate to "+ url +"\nFollowing Error occurred\n" + e.getMessage());
        }
    }

    public static void log(String log){
        if(classLogs == null) classLogs = new StringBuilder();
        logger.info(log);
        Calendar current = Calendar.getInstance();
        log = current.getTime() + " ## " + log;
        classLogs.append(log).append(System.lineSeparator());
    }


    @AfterTest(alwaysRun = true)
    public void closeDriver(){
        TestBase.log("Closing Web Browser");
        try{
            driver.quit();
        }catch (Exception e){
            Assert.fail("Exception occurred while Closing WebBrowser. \n" + e.getMessage());
        }
        finally {
            driver.quit();
        }
        TestBase.log("Web Browser Closed");
    }
}
