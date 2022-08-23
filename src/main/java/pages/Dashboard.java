package pages;

import Help.Wait.WaitHelper;
import Help.browserConfiguration.configReader.ObjectReader;
import Help.logger.LoggerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import testBase.TestBase;
public class Dashboard {
    private WebDriver driver;
    private final Logger log = LoggerHelper.getLogger(Dashboard.class);
    WaitHelper waitHelper;

    @FindBy(xpath = "//h6[contains(text(),'Chào mừng Admin 01')]")
    WebElement logo;

    public Dashboard(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        waitHelper = new WaitHelper(driver);
        waitHelper.waitForElement(logo, ObjectReader.reader.getExplicitWait());
        new TestBase().getNavigationScreen(driver);
        TestBase.logExtentReport("Dashboard created");
    }
}
