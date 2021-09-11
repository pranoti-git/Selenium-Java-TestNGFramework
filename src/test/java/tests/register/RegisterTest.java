package tests.register;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.TestBase;

public class RegisterTest {
    String driver;
    TestBase testBase;

    @BeforeClass
    public void initialize(){
        testBase = new TestBase();
        driver = testBase.getDriver();
        System.out.println("Driver is : " +driver);
    }

    @Test
    public void testMethod(){
        System.out.println("I am in test method : " + driver);
    }
}
