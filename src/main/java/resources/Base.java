package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {
    // WebDriver instance for global access across classes
    WebDriver driver;        
    // Properties object for global access to configuration settings
    public Properties prop;  
    
    /**
     * Initializes the WebDriver based on the specified browser in the properties file.
     * 
     * @return WebDriver instance for the chosen browser
     * @throws IOException if the properties file cannot be read
     */
    
    public WebDriver initializeBrowser() throws IOException {
        
        // Load properties file
        prop = new Properties();
        String propertiesPath = System.getProperty("user.dir") + "\\src\\main\\java\\resources\\data.properties";
        FileInputStream fis = new FileInputStream(propertiesPath);
        prop.load(fis);
        
        // Retrieve the browser name from properties
        String browserName = prop.getProperty("browser");
        
        // Initialize the WebDriver based on the specified browser
        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            //driver.get(prop.getProperty("url"));
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("ie")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }else {
            throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
        
                                     
        // Set an implicit wait time to handle element visibility
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));  

        return driver; // Return the initialized WebDriver instance
    }
}
