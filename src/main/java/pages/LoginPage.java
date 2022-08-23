package pages;

import Help.Wait.WaitHelper;
import Help.assertion.VerificationHelper;
import Help.browserConfiguration.configReader.ObjectReader;
import Help.javaScript.JavaScriptHelper;
import Help.logger.LoggerHelper;
import com.aventstack.extentreports.Status;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import testBase.TestBase;

public class LoginPage {
    private WebDriver driver;
    private final Logger log = LoggerHelper.getLogger(LoginPage.class);
    WaitHelper waitHelper;

    @FindBy(xpath = "//div[@class='card-body']//img")
    WebElement logo;

    @FindBy(xpath = "//img[@class='img-fluid']")
    WebElement image;

    @FindBy(xpath = "//input[@id='iusername']")
    WebElement username;

    @FindBy(xpath = "//input[@id='ipassword']")
    WebElement password;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement btnLogin;

    @FindBy(xpath = "//span[normalize-space()='Forgot password?']")
    WebElement forgotPassword;

    @FindBy(xpath = "//div[@class='toast-message']")
    WebElement msgObject;

    @FindBy(id = "notification-modal")
    WebElement dialogSusscess;

    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitHelper = new WaitHelper(driver);
        waitHelper.waitForElement(logo, ObjectReader.reader.getExplicitWait());
        new TestBase().getNavigationScreen(driver);
        TestBase.logExtentReport("Login page object created");
    }

    public void enterUserName(String username){
        log.info("entering user name..." + username);
        logExtentReport("entering user name" + username);
        this.username.sendKeys(username);
    }

    public void enterPassword(String password){
        log.info("Entering password...." + password);
        logExtentReport("entering password....." + password);
        this.password.sendKeys(password);
    }
    public void clickToBtnLogin(){
        log.info("Clicking on button login....");
        logExtentReport("Clicking on button login...");
        VerificationHelper verificationHelper = new VerificationHelper(driver);
        btnLogin.click();
        if(verificationHelper.isDisplayed(dialogSusscess)){
            moveToDashboardPage();
        }
        else {
            checkLoginFailure();
        }
    }
    public Dashboard moveToDashboardPage(){
        return new Dashboard(driver);
    }
    public void loginToApplication(String username, String password){
        enterUserName(username);
        enterPassword(password);
        clickToBtnLogin();
    }
    public boolean verifyDisplayDialogMsg(){
        return new VerificationHelper(driver).isDisplayed(dialogSusscess);
    }
    public boolean usernameNull(){
        if(username.getAttribute("value").isEmpty()){
            String msg = "The username field is required";
            return true;
        }
        return false;
    }
    public boolean passwordNull(){
        if(password.getAttribute("value").isEmpty()){
            String msg = "The password field is required";
            return true;
        }
        return false;
    }
    public boolean allNull(){
        if(password.getAttribute("value").isEmpty() && username.getAttribute("value").isEmpty()){
            String msg = "The user field is required";
            return true;
        }
        return false;
    }
    public boolean checkCharacterPassword(){
        if(password.getAttribute("value").length()>6){
            String msg = "Your password is too short, minimum 6 characters required.";
            return true;
        }
        return false;
    }
    public boolean checkLoginFailure(){
        String msg = "Invalid login Credentials.";
        return true;
    }
    public void logExtentReport(String s1){
        TestBase.test.log(Status.INFO, s1);
    }
}