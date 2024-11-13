package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {

    WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By dashboardHeader = By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[1]/span/h6");

    private final By profileIcon = By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[3]/ul/li/span");

    private final By logoutButton = By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[3]/ul/li/ul/li[4]/a");


    public String getPageTitle(){
        return driver.getTitle();
    }

    public boolean isDashboardTextExists(){
        try {
            return driver.findElement(dashboardHeader).isDisplayed();
        }catch (Exception e){
            return false;
        }
    }

    public void clickProfileIcon(){
        driver.findElement(profileIcon).click();
    }

    public void clickLogoutButton(){
        driver.findElement(logoutButton).click();
    }

}
