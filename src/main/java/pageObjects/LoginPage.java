package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    WebDriver driver;

    @FindBy(id = "email")
	WebElement emailField;

    @FindBy(id = "password")
	WebElement passwordField;

    @FindBy(css = "button[type='submit']") 
    WebElement submitButton;
    
    @FindBy(xpath = "//div[@role='alert']") 
    
    WebElement errorMessage;
    
    @FindBy(xpath = "//label[normalize-space()='The email field is required.']") 
    WebElement errorEmailRequired;
    
    @FindBy(xpath = "//label[normalize-space()='The password field is required.']") 
    WebElement errorpasswordRequired;
    
    @FindBy(xpath = "//div[@class='navbar-nav flex-row order-md-last']") 
    WebElement DropDownButton;
    
    @FindBy(xpath = "//a[@onclick='window.logout()']") 
    WebElement logoutButton;
    
    @FindBy(xpath = "//a[@aria-label='Show password']//*[name()='svg']") 
    WebElement  toggleButton;
    
    @FindBy(xpath = "//label[contains(text(),'Too many login attempts. Please try again in')]") 
    WebElement  cooldownMessage;
    
    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public WebElement getEmailField() {
        return emailField;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }
    
    public WebElement geterrorMessage() {
        return errorMessage;
    }
    
    public WebElement geterrorEmailRequired() {
        return errorEmailRequired;
    }
    
    public WebElement getDropDownButton() {
        return DropDownButton;
    }
    
    public WebElement geterrorpasswordRequired() {
        return errorpasswordRequired;
    }
    
    public WebElement getlogoutButton() {
        return logoutButton;
    }
    
    
    public WebElement gettoggleButton() {
        return toggleButton;
    }
    
    public WebElement getcooldownMessage() {
        return cooldownMessage;
    }
    
    
        
    // Method to set email
    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }

    // Method to set password
    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }

    // Method to click submit
    public void clickSubmit() {
        submitButton.click();
    }
 // Method to get the email field placeholder text
    public String getEmailFieldPlaceholder() {
        return emailField.getAttribute("placeholder");
    }

    // Method to get the password field placeholder text
    public String getPasswordFieldPlaceholder() {
        return passwordField.getAttribute("placeholder");
    }
   
    
}