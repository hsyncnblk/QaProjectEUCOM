package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PassengerInfoPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(PassengerInfoPage.class);

    @FindBy(id = "contact_email")
    private WebElement emailInput;

    @FindBy(id = "contact_cellphone")
    private WebElement phoneInput;

    @FindBy(xpath = "//input[@name='firstName_0']")
    private WebElement firstNameInput;

    @FindBy(xpath = "//input[@name='lastName_0']")
    private WebElement lastNameInput;

    @FindBy(xpath = "//input[@data-testid='reservation-publicid-TR-input']")
    private WebElement tcNoInput;

    @FindBy(xpath = "//select[@name='birthDateDay_0']")
    private WebElement birthDaySelect;

    @FindBy(xpath = "//select[@name='birthDateMonth_0']")
    private WebElement birthMonthSelect;

    @FindBy(xpath = "//select[@name='birthDateYear_0']")
    private WebElement birthYearSelect;

    @FindBy(xpath = "//label[contains(@class, 'gender-male') or contains(., 'Erkek')]")
    private WebElement maleGenderLabel;

    @FindBy(id = "continue-button")
    private WebElement proceedToPaymentButton;

    private final By payButtonLocator = By.cssSelector("button[data-testid='payment-form-submit-button']");

    public void fillPassengerForm(String email, String phone, String fname, String lname, String tc) {
        logger.info("Filling passenger information form...");
        waitForVisibilityOfElement(emailInput);
        sendKeysToElement(emailInput, email);
        sendKeysToElement(phoneInput, phone);
        sendKeysToElement(firstNameInput, fname);
        sendKeysToElement(lastNameInput, lname);
        sendKeysToElement(tcNoInput, tc);

        new Select(birthDaySelect).selectByValue("01");
        new Select(birthMonthSelect).selectByValue("01");
        new Select(birthYearSelect).selectByValue("1990");

        clickElement(maleGenderLabel);
    }

    public void clickContinue() {
        logger.info("Proceeding to payment page...");
        scrollToElement(proceedToPaymentButton);
        waitForElementToBeClickable(proceedToPaymentButton);
        clickElement(proceedToPaymentButton);
    }

    public boolean isPayButtonVisible() {
        try {
            WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(payButtonLocator));
            return button.isDisplayed();
        } catch (Exception e) {
            logger.error("Payment button did not become visible within the timeout period.");
            return false;
        }
    }
}