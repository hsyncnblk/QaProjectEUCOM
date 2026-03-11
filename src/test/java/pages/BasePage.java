package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.Driver;

import java.time.Duration;
import java.util.List;
import java.util.Set;

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
    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    protected void waitForTextToBePresentInElement(WebElement element) {
        wait.until(driver -> {
            String text = element.getText();
            return text != null && !text.isEmpty();
        });

    }
    protected void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    protected void scrollToTop() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, 0);");
    }
    protected void scrollUp(int pixels) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, -" + pixels + ");");
    }

    protected void waitBySecond(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected void switchToNewTab() {
        System.out.println("Yeni sekme kontrolü yapılıyor...");
        waitBySecond(3);

        String currentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
                System.out.println("Yeni sekmeye geçildi! URL: " + driver.getCurrentUrl());
                break; // Yeni sekmeyi bulur bulmaz döngüden çık
            }
        }
    }

    protected void jsClick(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
    protected String getValueByJS(String cssSelector) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
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