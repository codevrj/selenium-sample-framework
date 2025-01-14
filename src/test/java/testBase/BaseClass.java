package testBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;


public class BaseClass {

    public static WebDriver driver;

    public Logger logger;

    public Properties properties;

    @BeforeClass(groups = {"Sanity","Regression","Master","DataDriven"})
    @Parameters({"os", "browser"})
    public void setUp(String os, String browser) throws IOException {

        logger = LogManager.getLogger(this.getClass());       // log4j2 logs

        // Loading config.properties file

        FileReader fileReader = new FileReader("src/test/resources/config.properties");
        properties = new Properties();
        properties.load(fileReader);

        String url = properties.getProperty("baseUrl");  // Reading url from properties file

        String excEnv = properties.getProperty("execution_env");

        if (excEnv.equalsIgnoreCase("remote")) {

            DesiredCapabilities capabilities = new DesiredCapabilities();

            // OS - from xml file
            if (os.equalsIgnoreCase("windows")) {
                capabilities.setPlatform(Platform.WIN11);

            } else if (os.equalsIgnoreCase("linux")) {
                capabilities.setPlatform(Platform.LINUX);

            } else if (os.equalsIgnoreCase("mac")) {
                capabilities.setPlatform(Platform.MAC);

            }else {
                System.out.println("No matching OS found...");
                return;
            }

            // Browser - from xml file
            switch (browser.toLowerCase()) {
                case "chrome": capabilities.setBrowserName("chrome");break;
                case "edge": capabilities.setBrowserName("MicrosoftEdge");break;
                case "firefox": capabilities.setBrowserName("firefox");break;
                default: System.out.println("No matching browser...");return;
            }

            driver = new RemoteWebDriver(new URL("http://localhost:4444/"), capabilities);
        }

        if (excEnv.equalsIgnoreCase("local")) {

            // Browser - from xml file
            switch (browser.toLowerCase()) {
                case "chrome": driver = new ChromeDriver();break;
                case "firefox": driver = new FirefoxDriver();break;
                case "edge": driver = new EdgeDriver();break;
                case "ie": driver = new InternetExplorerDriver();break;
                default:
                    System.out.println("Invalid browser...");return;
            }
        }



        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.get(url);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }

    @AfterClass(groups = {"Sanity","Regression","Master","DataDriven"})
    public void tearDown() {
        driver.quit();
    }


    // Capture screenshot
    public String captureScreen(String tname) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile);

        return targetFilePath;

    }



    // Generate Random Strings
    public static String randomNumber() {
        return RandomStringUtils.randomNumeric(5);
    }

    public static String randomString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

}
