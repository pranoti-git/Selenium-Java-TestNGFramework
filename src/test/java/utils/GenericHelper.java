package utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GenericHelper {
    WebDriver driver;
    private Logger logger;
    JavascriptExecutor js;

    public GenericHelper(WebDriver driver){
        this.driver = driver;
        logger = LogManager.getLogger(this.getClass());
        js = (JavascriptExecutor)driver;
    }

    public void navigateTo(String url){
        logger.info("Navigating to : " + url);
        driver.navigate().to(url);
//        try{
//            driver.navigate().to(url);
//        }
//        catch (Exception e){
//            logger.error("Some issue occurred while navigation. \n"+ e.getStackTrace());
//        }
    }

    public String getTitleOfWebPage(){
        logger.info("Getting title of Web Page");
        logger.info("Title is : " + driver.getTitle());
        return driver.getTitle();
    }

    public void setText(WebElement element, String text){
        element.clear();
        element.sendKeys(text);
        logger.info("Text set : " + text);
    }

    public void click(WebElement element){
        element.click();
        logger.info("Clicked");
    }

    public void clickByJS(WebElement element){
        js.executeScript("arguments[0].click()",element);
        logger.info("Clicked using JS");
    }

    public void clickWithRetryByJS(WebElement element){
        try{
            element.click();
            logger.info("Clicked");
        }
        catch (Exception e){
            logger.info("Retry click by JS");
            js.executeScript("arguments[0].click()",element);
            logger.info("Clicked using JS");
        }
    }

    public void clickElementWithFocus(WebElement element){
        logger.info("Clicking element after scrolling");
        scrollToElement(element);
        click(element);
    }

    public void scrollToElement(WebElement element){
        logger.info("Scrolling to Element");
        js.executeScript("arguments[0].scrollIntoView(true)",element);
        logger.info("Scrolled to element");
    }

    public void scrollToTop(){
        logger.info("Scrolling to Top");
        js.executeScript("window.scrollTo(0,0)");
        logger.info("Scrolled to Top");
    }

    public void scrollToBottom(){
        logger.info("Scrolling to Bottom");
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        logger.info("Scrolled to Bottom");
    }

    public void setBrowserZoomLevel(float level){
        logger.info("Setting browser zoom level to " + level +" %");
        js.executeScript("document.body.style.zoom = '" + Float.toString(level/100)+"'");
        logger.info("Zoom level set");
    }

    public String getXpath(WebElement element){
        String elementToString = element.toString();
        return elementToString.substring(elementToString.indexOf('/'),elementToString.length()-1);
    }

    public String getTextOfElement(WebElement element){
        logger.info("Text is : " + element.getText());
        return element.getText();
    }

    public List<String> getTextOfElementList(List<WebElement> elementList){
        List<String> textList = new ArrayList<>();
        for(WebElement element:elementList){
            textList.add(element.getText());
        }
        logger.info("Text for List is : \n" + textList);
        return textList;
    }

    public void clickFromList(List<WebElement> elements,String text){
        boolean elementFound = false;
        logger.info("Clicking from list with text : " + text);
        for(WebElement element:elements){
            if(element.getText().equals(text)){
                click(element);
                elementFound = true;
                break;
            }
        }
        if(!elementFound){
            logger.error("Element with following text not found : " + text);
        }
    }

    public void refreshPage(){
        logger.info("Refreshing page : " + driver.getCurrentUrl());
        driver.navigate().refresh();
    }

    public boolean isDisplayed(WebElement element){
        try{
            return element.isDisplayed();
        }
        catch (Exception e){
            return false;
        }
    }

    public void pressEscapeKey(){
        logger.info("Pressing Escape key");
        Actions action = new Actions(driver);
        Action pressEscKey = action.sendKeys(Keys.ESCAPE).build();
        pressEscKey.perform();
    }
}

