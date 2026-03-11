package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class PassengerInfoPage extends BasePage {

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

    // Radyo butonlarında <input> yerine <label> elementine tıklamak native click için çok daha güvenlidir
    @FindBy(xpath = "//label[contains(@class, 'gender-male') or contains(., 'Erkek')]")
    private WebElement maleGenderLabel;

    @FindBy(id = "priceInfoGrandTotal")
    private WebElement grandTotalPriceElement;
    @FindBy(id = "continue-button")
    private WebElement proceedToPaymentButton;

    @FindBy(xpath = "//span[normalize-space()='Ödeme yap']")
    private WebElement paySubmitButton;

    public void fillPassengerForm(String email, String phone, String fname, String lname, String tc) {
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
        scrollToElement(proceedToPaymentButton);
        clickElement(proceedToPaymentButton);
    }
    public boolean isPayButtonVisible() {
        try {
            waitBySecond(8);

            By payButtonLocator = By.cssSelector("button[data-testid='payment-form-submit-button']");

            WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(payButtonLocator));

            return button != null;

        } catch (Exception e) {
            System.out.println("HATA: 'Ödeme yap' butonu 8 saniye içinde ekrana gelmedi.");
            return false;
        }
    }
}