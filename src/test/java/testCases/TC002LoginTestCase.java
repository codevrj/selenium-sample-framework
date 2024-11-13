package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC002LoginTestCase extends BaseClass {


    @Test(groups = {"Sanity","Master"})                              // Added login test as sanity test ,    This test belongs to Sanity and Master groups
    public void loginTest() {

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        logger.info("*** Login Test Started ***");
        loginPage.setUsername(properties.getProperty("username"));
        logger.info("username is provided.");

        loginPage.setPassword(properties.getProperty("password"));
        logger.info("password is provided.");

        loginPage.clickLoginButton();
        logger.info("login button is clicked.");

        Assert.assertTrue(dashboardPage.isDashboardTextExists(), "Test Failed.Dashboard Text is not found");
        logger.info("Test Passed.");

        logger.info("*** Login Test Ended ***");
    }

}
