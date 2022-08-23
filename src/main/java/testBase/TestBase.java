package testBase;

import Help.Wait.WaitHelper;
import Help.browserConfiguration.BrowserType;
import Help.browserConfiguration.configReader.ObjectReader;
import Help.browserConfiguration.configReader.PropertyReader;
import Help.javaScript.JavaScriptHelper;
import Help.logger.LoggerHelper;
import Help.resource.ResourceHelper;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v85.io.IO;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import sun.util.resources.cldr.aa.CalendarData_aa_ER;
import utils.ExtentManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public static ExtentReports extent;
    public static ExtentTest test;
    public WebDriver driver;
    private Logger log = LoggerHelper.getLogger(TestBase.class);
    public static File reportDirectery;

    @BeforeSuite
    public void beforeSuite() throws Exception{
        extent = ExtentManager.getInstance();
    }
    @BeforeTest
    public void beforeTest() throws Exception{
        ObjectReader.reader = new PropertyReader();
        reportDirectery = new File(ResourceHelper.getResourcePath("src/main/resources/screenShots"));
        setUpDriver(ObjectReader.reader .getBrowserType());
        test = extent.createTest(getClass().getSimpleName());
    }
    @BeforeMethod
    public void beforeMethod(Method method){
        test.log(Status.INFO, method.getName() + "********************** Test start ********************");
        log.info("*************"+method.getName()+" Strated ****************");
    }
    public void setUpDriver(BrowserType btype) throws Exception{
        driver = getBrowserObject(btype);
        log.info("Initialize Web driver: "+driver.hashCode());
        WaitHelper wait = new WaitHelper(driver);
        wait.setImplicitWait(ObjectReader.reader.getImpliciteWait(), TimeUnit.SECONDS);
        wait.pageLoadTime(ObjectReader.reader.getPageLoadTime(), TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }
    public WebDriver getBrowserObject(BrowserType type) throws Exception {
        try {
            switch (type){
                case Chrome:
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    return driver;
                case Firefox:
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    return driver;
                default:
                    throw new Exception("Driver not found: " +type.name());
            }
        }
        catch (IOException e){
            log.info(e.getMessage());
            throw e;
        }
    }

    public String captureScreen(String fileName, WebDriver driver){
        if(driver == null){
            log.info("driver is null");
            return null;
        }
        if(fileName == ""){
            fileName = "blank";
        }
        Reporter.log("capture screen method called");
        File destFile = null;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
        File screFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            destFile = new File(reportDirectery+"/"+fileName+"_"+formater.format(calendar.getTime())+".png");
            Files.copy(screFile.toPath(), destFile.toPath());
            Reporter.log("<a href='"+destFile.getAbsolutePath()+"'><img src='"+destFile.getAbsolutePath()+"'height='100' width='100'/></a>");
        } catch (IOException e) {
           e.printStackTrace();
        }
        return destFile.toString();
    }

    public void getNavigationScreen(WebDriver driver){
        log.info("Capturing ui navigation screen...");
        new JavaScriptHelper(driver).zoomInBy60Percentage();
        String screen = captureScreen("", driver);
        new JavaScriptHelper(driver).zoomInBy100Percentage();
        try{
            test.addScreenCaptureFromPath(screen);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public static void logExtentReport(String s1){
        test.log(Status.INFO, s1);
    }

    public void getApplicationUrl(String url){
        driver.get(url);
        logExtentReport("navigating to...." + url);
    }
    @AfterMethod
    public void afterMethod(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE){
            test.log(Status.FAIL, result.getThrowable());
            String imagePath = captureScreen(result.getName(), driver);
            test.addScreenCaptureFromPath(imagePath);
        }
        else if(result.getStatus() == ITestResult.SUCCESS){
            test.log(Status.PASS, result.getName()+ "is pass");
        }
        else if(result.getStatus() == ITestResult.SKIP){
            test.log(Status.SKIP, result.getThrowable());
        }
        log.info("**************"+result.getName()+"Finished***************");
        extent.flush();
    }
    @AfterTest
    public void afterTest(){
        if(driver != null){
            driver.quit();
        }
    }
}
