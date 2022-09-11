package pages;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginGruuBank {
    @FindBy(xpath = "//input[@name='uid']")
    WebElement userId;
    @FindBy(xpath = "//input[@name='password']")
    WebElement password;
    @FindBy(xpath = "//input[@name='btnLogin']")
    WebElement btnLogin;
    @FindBy(xpath = "//input[@name='btnReset']")
    WebElement btnReset;
}
