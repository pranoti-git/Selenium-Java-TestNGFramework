package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserSetup {
    public static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    private Platform platform;
    public WebDriver driver;

    public WebDriver invokeWebDriver(Logger logger) {
        try {
//        String downloadPath = "./AutomationDownloads";
            logger.info("Setting properties for browser");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
            threadDriver.set(driver);
//            return driver;
        return getDriver();
        }
        catch(Exception e){
            logger.error("Exception occurred.\n" + e.getStackTrace());
//            return driver;
            return getDriver();
        }
    }

    public static WebDriver getDriver(){
        return threadDriver.get();
//        return driver;
    }



    private Platform getCurrentPlatform() {
        String currentPlatform = System.getProperty("os.name").toLowerCase();
        if (currentPlatform.contains("win")) {
            this.platform = Platform.WINDOWS;
        } else if (currentPlatform.contains("nix") || currentPlatform.contains("nux") || currentPlatform.contains("ais")) {
            this.platform = Platform.LINUX;
        } else if (currentPlatform.contains("mac")) {
            this.platform = Platform.MAC;
        }
        return platform;
    }
}
