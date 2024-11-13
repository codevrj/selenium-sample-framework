package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;

import java.time.Duration;

public class TC003DDTLoginTestCase extends BaseClass {


    @Test(dataProvider = "LoginData" , dataProviderClass = DataProviders.class , groups = "DataDriven")  // data provider name and mention the class name.
    public void loginTestDDT(String username, String password , String expectedResult) {

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        logger.info("*** Login Test Started ***");

        // login page
        loginPage.setUsername(username);
        logger.info("username is provided.");
        loginPage.setPassword(password);
        logger.info("password is provided.");
        loginPage.clickLoginButton();
        logger.info("login button is clicked.");

        // dashboard page
        boolean result = dashboardPage.isDashboardTextExists();


        // Valid username and password - login successful - test passed     -- logout need
        // Valid username and password - login unsuccessful - test failed
        // Invalid username and password - login successful - test failed
        // Invalid username and password - login unsuccessful - test passed   -- logout need


        // All positive & negative test cases executed successfully as following ;

        if(expectedResult.equalsIgnoreCase("valid")) {
            if (result==true) {
                Assert.assertTrue(true);
                dashboardPage.clickProfileIcon();
                dashboardPage.clickLogoutButton();
                logger.info("Test Passed.");
            } else {
                logger.info("Test Failed.");
                Assert.fail();
            }
        }else if(expectedResult.equalsIgnoreCase("invalid")) {
            if (result==true) {
                logger.info("Test Failed.");
                dashboardPage.clickProfileIcon();
                dashboardPage.clickLogoutButton();
                Assert.fail();
            } else {
                logger.info("Test Passed.");
                Assert.assertTrue(true);
            }
        }

        logger.info("*** Login Test Ended ***");
    }
}
