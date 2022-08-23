package testScript;

import Help.assertion.AssertionHelper;
import Help.browserConfiguration.configReader.ObjectReader;
import Help.logger.LoggerHelper;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import pages.LoginPage;
import testBase.TestBase;

public class LoginTest extends TestBase {
    private final Logger log = LoggerHelper.getLogger(LoginTest.class);

    @Test(description = "Login successfully", priority = 4)
    public void testLoginToApplication(){
        getApplicationUrl(ObjectReader.reader.getUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginToApplication("fff", "4444444");
        boolean dialog = loginPage.verifyDisplayDialogMsg();
        AssertionHelper.updateTestStatus(dialog);
    }

    @Test(description = "username is not valid", priority = 3)
    public void testUsernameIsNotValid(){
        getApplicationUrl(ObjectReader.reader.getUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginToApplication("", "123456");
        boolean msg = loginPage.usernameNull();
        AssertionHelper.updateTestStatus(msg);
    }

    @Test(description = "password is not valid", priority = 2)
    public void testPasswordIsNotValid(){
        getApplicationUrl(ObjectReader.reader.getUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginToApplication("admin01", "");
        boolean msg = loginPage.passwordNull();
        AssertionHelper.updateTestStatus(msg);
    }

    @Test(description = "username and password is not valid", priority = 1)
    public void testUsernameAndPassIsNotValid(){
        getApplicationUrl(ObjectReader.reader.getUrl());
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginToApplication("", "");
        boolean msg = loginPage.allNull();
        AssertionHelper.updateTestStatus(msg);
    }
}
