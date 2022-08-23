package Help.javaScript;

import Help.logger.LoggerHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;


public class JavaScriptHelper {
    private WebDriver driver;
    private Logger log = LoggerHelper.getLogger(JavaScriptHelper.class);

    public JavaScriptHelper(WebDriver driver){
        this.driver = driver;
        log.info("JavaScriptHelper has been initialised");
    }

    /**
     * This method will execute java script
     * @param script
     * @return
     */
    public Object executeScript(String script){
        JavascriptExecutor exe = (JavascriptExecutor)driver;
        return exe.executeScript(script);
    }

    /**
     * This method will execute Java script with multiple arguments
     * @param script
     * @param args
     * @return
     */
    public Object executeScript(String script, Object...args){
        JavascriptExecutor exe = (JavascriptExecutor)driver;
        return exe.executeScript(script,args);
    }

    /**
     * This method will zoom screen by 100%
     */
    public void zoomInBy100Percentage(){
        executeScript("document.body.style.zoom='100%'");
    }

    /**
     * This method will zoom screen by 60%
     */
    public void zoomInBy60Percentage(){
        executeScript("document.body.style.zoom='40%'");
    }


}
