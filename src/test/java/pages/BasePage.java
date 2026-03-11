package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.Driver;

import java.time.Duration;
import java.util.List;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage() {
        this.driver = Driver.getDriver();
        PageFactory.initElements(driver, this);
        int waitTime = Integer.parseInt(ConfigReader.getProperty("explicit_wait", "20"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
    }

    protected void clickElement(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void sendKeysToElement(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(text);
    }

    protected void sendKeyToElement(WebElement element, Keys key) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.sendKeys(key);
    }

    protected void waitForVisibilityOfElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));

    }

    protected void waitForVisibilityOfElements(List<WebElement> elements) {
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }
    // Element görünür olana kadar bekleyen metod
    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    protected void waitForTextToBePresentInElement(WebElement element) {
        // wait nesnesi zaten constructor'da tanımlı, onu kullanıyoruz
        wait.until(driver -> {
            String text = element.getText();
            return text != null && !text.isEmpty();
        });

    }

    protected String getValueByJS(String cssSelector) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // JS kodunu buraya hapsettik, dışarıdan sadece selector gönderiyoruz
        String query = "var el = document.querySelector('" + cssSelector + "'); " +
                "if(!el) return 'ELEMENT_YOK'; " +
                "var text = el.textContent; " +
                "if(!text || text.trim() === '') { " +
                "  text = window.getComputedStyle(el, '::before').getPropertyValue('content') + " +
                "         window.getComputedStyle(el, '::after').getPropertyValue('content'); " +
                "} " +
                "return text;";

        Object result = js.executeScript(query);
        return (result != null) ? result.toString().replace("\"", "").trim() : "";
    }
}