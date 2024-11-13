package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    public WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By username = By.name("username");
    private final By password = By.name("password");
    private final By loginButton = By.xpath("//*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/form/div[3]/button");


    public void setUsername(String username) {
        driver.findElement(this.username).clear();
        driver.findElement(this.username).sendKeys(username);
    }
    public void setPassword(String password) {
        driver.findElement(this.password).clear();
        driver.findElement(this.password).sendKeys(password);
    }
    public void clickLoginButton() {
        driver.findElement(this.loginButton).click();
    }



}
