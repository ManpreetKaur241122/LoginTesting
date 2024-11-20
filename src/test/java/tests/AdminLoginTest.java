package tests;



import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pageObjects.LoginPage;
import resources.Base;

public class AdminLoginTest extends Base {
	public WebDriver driver;
	private LoginPage loginPage;
	private WebDriverWait wait;
	

	@BeforeMethod
	public void openApplication() throws IOException {

		driver = initializeBrowser();
                driver.get(prop.getProperty("url"));
		loginPage = new LoginPage(driver); // Initialize LoginPage object
	}

	@AfterMethod
	public void closure() {
		driver.close();

	}
	@Test
        public void testSuccessfulLogin() {
        // Test Case 1:Verify logging into the Application using valid credentials
        loginPage.enterEmail(prop.getProperty("validEmailId"));
        loginPage.enterPassword(prop.getProperty("validPassword"));
        loginPage.clickSubmit();

        // Assert that the user is redirected to the dashboard
        String expectedUrl = prop.getProperty("dashboardUrl"); 
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
    }
	@Test(priority=1)
	public void testLoginWithInvalidCredentials() {
        // Test Case: Verify logging into the Application using invalid credentials (i.e. Invalid email address and Invalid Password)
        loginPage.enterEmail(prop.getProperty("inValidEmailId"));
        loginPage.enterPassword(prop.getProperty("inValidPassword"));
        loginPage.clickSubmit();

        // Assert that an error message is displayed
        WebElement errorMessageDisplay = loginPage.geterrorMessage(); 
        Assert.assertTrue(errorMessageDisplay.isDisplayed());
    }
	@Test(priority=2)
	public void testLoginWithInvalidEmail() {
        // Test Case 3: Verify logging into the Application using invalid email address and valid Password)
        loginPage.enterEmail(prop.getProperty("inValidEmailId"));
        loginPage.enterPassword(prop.getProperty("validPassword"));
        loginPage.clickSubmit();

        // Assert that an error message is displayed
        WebElement errorMessageDisplay = loginPage.geterrorMessage(); 
        Assert.assertTrue(errorMessageDisplay.isDisplayed());
    }
	@Test(priority=3)
	public void testLoginWithInvalidPassword() {
        // Test Case 4: Verify logging into the Application using valid email address and invalid Password)
        loginPage.enterEmail(prop.getProperty("validEmailId"));
        loginPage.enterPassword(prop.getProperty("inValidPassword"));
        loginPage.clickSubmit();

        // Assert that an error message is displayed
        WebElement errorMessageDisplay = loginPage.geterrorMessage(); 
        Assert.assertTrue(errorMessageDisplay.isDisplayed());
    }
	@Test(priority=4)
         public void testEmptyFields() {
        // Test Case 5: Verify logging into the Application without providing any credentials        
	loginPage.clickSubmit();
        // Assert that an error message is displayed
        WebElement errorEmailRequiredDisplay= loginPage.geterrorEmailRequired();
        WebElement errorPasswordRequiredDisplay =loginPage.geterrorpasswordRequired(); 
        Assert.assertTrue(errorEmailRequiredDisplay.isDisplayed());
        Assert.assertTrue(errorPasswordRequiredDisplay.isDisplayed());
    }


	@Test(priority=5)
        public void testLoginUsingKeyboard() throws InterruptedException {
        // Test Case 7:Verify logging into the Application using Keyboard keys (Tab and Enter)
        Actions actions = new Actions(driver);

        // Enter email
        loginPage.enterEmail(prop.getProperty("validEmailId"));

        // Press Tab to focus on the password field
        actions.sendKeys(Keys.TAB).perform();

        // Enter password
        loginPage.enterPassword(prop.getProperty("validPassword"));

        // Press Enter to submit the form
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(5000);
        // Wait for the page to load and assert that the user is redirected to the dashboard
        String expectedUrl = prop.getProperty("dashboardUrl"); 
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);
    }
	@Test(priority=6)
        public void testPlaceholderText() {
        // Test Case:8 Verify E-Mail Address and Password text fields in the Login page have the place holder text
        String expectedEmailPlaceholder =prop.getProperty("emailPlaceholder"); 
        String actualEmailPlaceholder = loginPage.getEmailFieldPlaceholder();
        Assert.assertEquals(actualEmailPlaceholder, expectedEmailPlaceholder, "Email placeholder text does not match.");

        // Verify placeholder text for the password field
        String expectedPasswordPlaceholder = prop.getProperty("passwordPlaceholder"); 
        String actualPasswordPlaceholder = loginPage.getPasswordFieldPlaceholder();
        Assert.assertEquals(actualPasswordPlaceholder, expectedPasswordPlaceholder, "Password placeholder text does not match.");
    }
	@Test(priority=7)
	 public void testLoginAndBackButton() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    // Test Case: Verify Logging into the Application and browsing back using Browser back button

	    //Log in using valid credentials
	    loginPage.enterEmail(prop.getProperty("validEmailId")); 
	    loginPage.enterPassword(prop.getProperty("validPassword"));
	    loginPage.clickSubmit();

	    //Assert that the user is redirected to the dashboard/homepage
	    String expectedUrl = prop.getProperty("dashboardUrl");
	    wait.until(ExpectedConditions.urlToBe(expectedUrl));
	   
	    Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "User should be redirected to the dashboard after login.");

	    //Use the browser back button to return to the login page
	    driver.navigate().back();

	    //Assert that the user still on the dashboard page
	  
	    Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "User should be on the dashboard page");
	}
	@Test(priority=8)
        public void testLogoutAndBackButton() throws InterruptedException {
        //Test Case:10 Verify Logging out from the Application and browsing back using Browser back button
        loginPage.enterEmail(prop.getProperty("validEmailId")); // Use valid credentials
        loginPage.enterPassword(prop.getProperty("validPassword"));
        loginPage.clickSubmit();

        // Assert that the user is redirected to the dashboard/homepage
        String expectedUrl = prop.getProperty("dashboardUrl"); 
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "User should be redirected to the dashboard after login.");

        // Log out from the application
        WebElement DropDownButtonProfile = loginPage.getDropDownButton(); 
        DropDownButtonProfile.click();
        
        // Log out from the application
        WebElement logoutButtonProfile = loginPage.getlogoutButton();
        logoutButtonProfile.click();
       Thread.sleep(5000);

        // Assert that the user is redirected to the login page after logout
       String expectedUrlLogin =prop.getProperty("url").trim();
       String actualUrlLogin = driver.getCurrentUrl();

       Assert.assertEquals( expectedUrlLogin, actualUrlLogin,"User should be redirected to the login page after logout.");
        // Use the browser back button
        driver.navigate().back();
       
        // Assert that the user is still on the login page (should not be logged in)
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrlLogin, "User should remain on the login page after clicking back.");

	}
        
        @Test
        public void testCopyPasswordToClipboard() throws UnsupportedFlavorException, IOException, InterruptedException {
		//Test Case:Verify that user can not copy the password.
            driver.get(prop.getProperty("url")); // Navigate to the login page
            Thread.sleep(3000);
            //Locate and type password into the password field
            WebElement passwordField = loginPage.getPasswordField(); 
            String password = prop.getProperty("validPassword"); 
            passwordField.sendKeys(password);

            //Simulate Ctrl + A (Select All) and Ctrl + C (Copy) using Actions class
            Actions actions = new Actions(driver);
            actions.doubleClick(passwordField) // Double click to select the text in the password field
                   .sendKeys(passwordField, Keys.chord(Keys.CONTROL, "c")) // Send Ctrl + C to copy
                   .perform();

            //Get clipboard content using the custom method
            String clipboardContent = getClipboardContent();

            //Verify that clipboard content matches the typed password
            Assert.assertNotEquals("Clipboard content does not match the password.", password, clipboardContent);
        }

	// Custom method to retrieve clipboard content
        private String getClipboardContent() throws UnsupportedFlavorException, IOException {
        // Get the clipboard contents
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        
        // Check if the clipboard contains text (String flavor)
        if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            // Return the clipboard content as a string
            return (String) contents.getTransferData(DataFlavor.stringFlavor);
        }
        return null;
    }
	

	@Test(priority=12)
       public void testPasswordNotVisibleInPageSource() {
        //Test Case:15 Verify the Password is not visible in the Page Source
        String password = prop.getProperty("validPassword"); 
        
        // Enter the password
        loginPage.enterPassword(prop.getProperty("validPassword"));

        // Get the page source
        String pageSource = driver.getPageSource();

        //  Verify the password is not visible in the page source
        Assert.assertFalse(pageSource.contains(password), "The password should not be visible in the page source.");
    }
	@Test(priority=13)
        public void testLoginPersistence() throws IOException {
        //Test Case:Verify Logging into the Application, closing the Browser without loggingout and opening the application in the Browser again
        driver.get(prop.getProperty("url"));

        //Log in to the application
        String username = prop.getProperty("validEmailId"); 
        String password = prop.getProperty("validPassword"); 
        loginPage.enterEmail(username);
        loginPage.enterPassword(password);
        loginPage.clickSubmit(); 

        String actualURL=driver.getCurrentUrl();
        String expectedURL=prop.getProperty("dashboardUrl");
        Assert.assertEquals(actualURL, expectedURL,"user logged in");
        
        //Close the browser without logging out
        driver.quit();

        //Reopen the browser and navigate to the application again
        driver= initializeBrowser();
        driver.get(prop.getProperty("url"));

        //Verify that the user is still logged in
        Assert.assertEquals(actualURL, expectedURL, "Dashboard should be displayed after reopening the application.");
    }
	
	  @Test
	    public void testPasswordVisibilityToggle() {
	       //Test Case: verify that password text get toggled when pressed toggled button
	        WebElement passwordField =loginPage.getPasswordField(); 
	        WebElement toggleButton = loginPage.gettoggleButton() ;
	        
	        //Enter text into the password field
	        String passwordText = prop.getProperty("validPassword");
	        passwordField.sendKeys(passwordText);

	        //Initially, the password should be hidden
	        Assert.assertEquals(passwordField.getAttribute("type"), "password", "Password is not hidden initially");

	        //Click the toggle button to show the password
	        toggleButton.click();
	        Assert.assertEquals(passwordField.getAttribute("type"), "text", "Password should be visible after toggle");

	        //Click the toggle button again to hide the password
	        toggleButton.click();
	        Assert.assertEquals(passwordField.getAttribute("type"), "password", "Password should be hidden after toggling back");
	    }
	  @Test
	    public void testMaxLoginAttempts() throws InterruptedException {
		//Test Case:verify maximum invalid login attempts
	        driver.get(prop.getProperty("url"));
	         wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	        for (int i = 1; i <= 5; i++) {
	        
	            // Enter invalid login credentials
	           loginPage.getEmailField().clear();
	           loginPage.enterEmail(prop.getProperty("inValidEmailId"));
	           loginPage.getPasswordField().clear();
	           loginPage.enterPassword(prop.getProperty("inValidPassword"));
	           loginPage.clickSubmit();

	            // Wait for error message to appear
	           
	            wait.until(ExpectedConditions.visibilityOf(loginPage.geterrorMessage()));
 
	            Assert.assertTrue(loginPage.geterrorMessage().isDisplayed(),
	                    "Error message should display after unsuccessful login attempt");
	            System.out.println("Attempt " + i + ": Login failed as expected.");
	          
	        }

	        // Attempt the 6th login (should be blocked)
	           loginPage.getEmailField().clear();
	           loginPage.enterEmail(prop.getProperty("inValidEmailId"));
	           loginPage.getPasswordField().clear();
	           loginPage.enterPassword(prop.getProperty("inValidPassword"));
	           loginPage.clickSubmit();


	        //Verify cooldown message after 5th unsuccessful attempt
	        wait.until(ExpectedConditions.visibilityOf(loginPage.getcooldownMessage()));
	        Assert.assertTrue(loginPage.getcooldownMessage().isDisplayed());

	        System.out.println("Cooldown message displayed as expected.");
	        
	    }
	   @Test
	    public void testSessionTimeout() throws InterruptedException {
		//Test Case:Verify session time out
	        //Navigate to the login page
	        driver.get(prop.getProperty("url"));

	        //Perform login
	       
	        loginPage.enterEmail(prop.getProperty("validEmailId"));
	        loginPage.enterPassword(prop.getProperty("validPassword"));
	        loginPage.clickSubmit();

	        //Verify login success by checking for the presence of an element on the dashboard
	        String expectedUrl = prop.getProperty("dashboardUrl"); 
	        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl);

	        //Wait for session timeout (simulate with shorter time for testing)
	        Thread.sleep(3600000); 

	        //Attempt to refresh or navigate to a protected page
	        driver.navigate().refresh();

	        //Check for session expiration by looking for the login page or a "session expired" message
	        boolean sessionExpired = driver.getCurrentUrl().contains("/login") ||
	                driver.getPageSource().contains("session expired");

	        //Assert that the session has expired
	        Assert.assertTrue(sessionExpired, "session did not expired as expected ");
	    }
	  }
	
    


