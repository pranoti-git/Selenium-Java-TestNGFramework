package pageObjects.register;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import utils.GenericHelper;

import java.util.List;

public class RegisterPage {

    @FindBy(xpath = "//input[@id='email']")
    public WebElement usernameInput;

    @FindBy(xpath = "//input[@name='password']")
    public WebElement passwordInput;

    @FindBy(xpath = "//input[@name='confirmPassword']")
    public WebElement confirmPassInput;

    @FindBy(xpath = "//input[@name='submit']")
    public WebElement submitButton;

    @FindBy(xpath = "//select")
    WebElement countryDropdown;

    @FindBys(
    @FindBy(xpath = "//option")
    )
    List<WebElement> countryOptions;

    @FindBy(xpath = "//b[contains(text(),'Dear')]/ancestor::p/following-sibling::p[1]/font")
    public WebElement successMessage;

    private Logger logger;
    private GenericHelper genericHelper;

    public RegisterPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        this.logger = LogManager.getLogger(this.getClass());;
        genericHelper = new GenericHelper(driver);
    }

    public void setUsername(String uname){
        logger.info("Setting Username as : " + uname);
        genericHelper.setText(usernameInput,uname);
    }

    public void setPassword(String pass){
        logger.info("Setting Password as : " + pass);
        genericHelper.setText(passwordInput,pass);
    }

    public void setConfirmPassword(String cpass){
        logger.info("Setting Confirm Password as : " + cpass);
        genericHelper.setText(confirmPassInput,cpass);
    }

    public void clickSubmitButton(){
        logger.info("Clicking Submit button");
        genericHelper.click(submitButton);
    }

    public String getSuccessMessage(){
        logger.info("Getting Success Message");
        return genericHelper.getTextOfElement(successMessage);
    }

    public void openCountryDropdown(){
        logger.info("Opening country dropdown");
        genericHelper.click(countryDropdown);
    }

    public List<String> getListOfCountries(){
        logger.info("Getting options in countries");
        return genericHelper.getTextOfElementList(countryOptions);
    }

}
