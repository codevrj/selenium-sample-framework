package testCases;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import testBase.BaseClass;

import java.time.Duration;

public class TC001LoginTestCase extends BaseClass {

    @Test(groups = {"Regression","Master"})           // This test belongs to both Master and Regression groups
    public void loginTest() {

        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        logger.info("*** Login Test Started ***");
        try {
            loginPage.setUsername("Admin");
            logger.info("username is provided");

            loginPage.setPassword("admin123");
            logger.info("password is provided");

            loginPage.clickLoginButton();
            logger.info("login button is clicked");


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.titleContains("OrangeHRM"));

            Assert.assertEquals(dashboardPage.getPageTitle(), "OrangeHRM");
            logger.info("Test Passed.");

        }catch (Exception e){
            logger.error("Test Failed.");
            logger.debug("Debug logs.");
            Assert.fail();
        }
        logger.info("*** Login Test Ended ***");
    }
}
