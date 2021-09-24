package tests.register;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.register.RegisterPage;
import utils.GenericHelper;
import utils.TestBase;
import utils.listeners.TestListeners;

import java.util.ArrayList;
import java.util.List;

public class RegisterTest extends TestBase{
    RegisterPage registerPage;
    GenericHelper genericHelper;
    private Logger logger;


    @BeforeClass(alwaysRun = true)
    public void initialize(){
        logger = LogManager.getLogger(this.getClass());
        registerPage = new RegisterPage(driver);
        genericHelper = new GenericHelper(driver);
    }

    @BeforeMethod(alwaysRun = true)
    public void navigateToRegisterPage(){
        TestBase.log("Navigating to Register Page");
        genericHelper.navigateTo("http://demo.guru99.com/test/newtours/register.php");
    }

    //@Test(priority = 0, groups = "P3")
    public void verifyTitleOfRegisterPage_0001(){
        String expectedTitle = "Register: Mercury Tours and Travels";
        String actualTitle = genericHelper.getTitleOfWebPage();
        Assert.assertTrue(expectedTitle.equals(actualTitle),"Title not matched. Actual title is : '" + actualTitle + "'. Expected Title is : '" + expectedTitle +"'");
        TestBase.log("Case Pass : Verify Title Of Register Page 0001");
    }

    @Test(priority = 1, groups = "P1")
    public void verifyMandatoryFieldsDisplayedInRegisterPage_0002(){
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertTrue(genericHelper.isDisplayed(registerPage.usernameInput),"Username Input not displayed");
        softAssert.assertTrue(genericHelper.isDisplayed(registerPage.passwordInput),"Password Input not displayed");
        softAssert.assertTrue(genericHelper.isDisplayed(registerPage.confirmPassInput),"Confirm Password Input not displayed");
        softAssert.assertTrue(genericHelper.isDisplayed(registerPage.submitButton),"Submit Button not displayed");

        softAssert.assertAll();
        TestBase.log("Case Pass : Verify Mandatory Fields Displayed In Register Page 0002");
    }

    @Test(priority = 2, groups = "P1", dependsOnMethods = "verifyMandatoryFieldsDisplayedInRegisterPage_0002")
    public void verifyRegistrationWithMandatoryFields_0003() {
        String expectedMessage = "Thank you for registering. You may now sign-in using the user name and password you've just entered.";

        registerPage.setUsername("qwerty");
        registerPage.setPassword("123");
        registerPage.setConfirmPassword("123");
//        Thread.sleep(3000);
        TestListeners.attachScreenshot("After Filling Register Form",driver);
        registerPage.clickSubmitButton();
//        Thread.sleep(2000);

        Assert.assertTrue(registerPage.getSuccessMessage().equals(expectedMessage),"Success Message not matched");
        TestBase.log("Case Pass : Verify Registration With Mandatory Fields 0003");
    }

    //@Test(priority = 3, groups = "P2", dependsOnMethods = "verifyMandatoryFieldsDisplayedInRegisterPage_0002")
    public void verifyRegistrationWithBlankMandatoryFields_0004() {
        registerPage.setUsername("");
        registerPage.setPassword("");
        registerPage.setConfirmPassword("");
//        Thread.sleep(3000);
        registerPage.clickSubmitButton();
//        Thread.sleep(2000);

        Assert.assertTrue(!genericHelper.isDisplayed(registerPage.successMessage),"Success Message is displayed for Blank Mandatory Fields");
        TestBase.log("Case Pass : Verify Registration With Blank Mandatory Fields 0004");
    }

    //@Test(priority = 4, groups = "P3")
    public void verifyAvailableCountries_0005(){
        SoftAssert softAssert = new SoftAssert();
        List<String> expectedCountries = new ArrayList<>();
        expectedCountries.add("INDIA");
        expectedCountries.add("UNITED STATES");
        expectedCountries.add("GERMANY");
        expectedCountries.add("ARCTIC");

        registerPage.openCountryDropdown();
        List<String> actualCountries = registerPage.getListOfCountries();

        for(String country : expectedCountries){
            softAssert.assertTrue(actualCountries.contains(country),"Following country not displayed : " + country);
        }

        for(String country : actualCountries){
            softAssert.assertTrue(expectedCountries.contains(country),"Following country displayed Extra : " + country);
        }

        softAssert.assertAll();
        TestBase.log("Case Pass : Verify Available Countries 0005");
    }

}
