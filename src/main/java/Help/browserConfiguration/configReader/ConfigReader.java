package Help.browserConfiguration.configReader;

import Help.browserConfiguration.BrowserType;

public interface ConfigReader {
    public int getImpliciteWait();
    public int getExplicitWait();
    public int getPageLoadTime();
    public BrowserType getBrowserType();
    public String getUrl();
}
