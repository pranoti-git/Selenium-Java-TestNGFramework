package utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class TestBase {
    private String driver;
    public static Logger logger;

    public TestBase(){
        setLogger();
        invokeBrowser();
    }

    public String getDriver(){
        return driver;
    }

    //@BeforeTest
    private void invokeBrowser(){
        driver = "Chrome driver";
    }

    private void setLogger(){
        logger = LogManager.getLogger(this.getClass());
        logger.info("Logger set");
    }



}
