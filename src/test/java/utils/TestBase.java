package utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

public abstract class TestBase {
    private BrowserSetup browserSetup;
    protected WebDriver driver;
    protected WebDriverWait wait;
    private Logger logger;
    public static StringBuilder classLogs;

    @BeforeTest(alwaysRun = true)
    public void setLogger(){
        System.out.println("*************** Setting Logger ***************");
        logger = LogManager.getLogger(TestBase.class);
        log("*************** Logger Set ***************");
    }

    @BeforeTest(alwaysRun = true,dependsOnMethods = "setLogger")
    public void invokeBrowser(){
        log("*************** Invoking Browser ***************");
        browserSetup = new BrowserSetup();
        driver = browserSetup.invokeWebDriver(logger);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver,15);
        log("*************** Invoking Finished ***************");
    }

    @BeforeTest(alwaysRun = true,dependsOnMethods = "invokeBrowser")
    public void navigateToBaseURL(){
        String url = "http://demo.guru99.com/test/newtours/";
        log("Navigating to Base URL " + url);
        driver.get(url);
    }

    public static void log(String log){
        if(classLogs == null) classLogs = new StringBuilder();
        classLogs.append(log).append(System.lineSeparator());
    }



    @AfterTest
    public void closeDriver(){
        log("Closing Web Browser");
        driver.quit();
        log("Web Browser Closed");
    }



}
