package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import utils.ConfigReader;

import java.util.List;

public class HomePage extends BasePage {

    @FindBy(xpath = "//label[@data-testid='search-round-trip-label']")
    private WebElement roundTripRadioButton;

    @FindBy(xpath = "//input[@data-testid='endesign-flight-origin-autosuggestion-input']")
    private WebElement originInput;

    @FindBy(xpath = "//input[@data-testid='endesign-flight-destination-autosuggestion-input']")
    private WebElement destinationInput;

    @FindBy(xpath = "//input[@data-testid='enuygun-homepage-flight-departureDate-datepicker-input']")
    private WebElement departureDateInput;

    @FindBy(xpath = "//input[@data-testid='enuygun-homepage-flight-returnDate-datepicker-input']")
    private WebElement returnDateInput;

    @FindBy(xpath = "//button[@data-testid='enuygun-homepage-flight-submitButton']")
    private WebElement searchButton;

    @FindBy(xpath = "//div[contains(@data-testid, 'autosuggestion-custom-item')]")
    private List<WebElement> suggestionItems;

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

    public void acceptCookiesIfPresent() {
        try {
            waitForVisibilityOfElement(acceptCookiesButton);
            clickElement(acceptCookiesButton);
            System.out.println("Çerezler kabul edildi.");
        } catch (Exception e) {
            System.out.println("Çerez pop-up'ı görülmedi, devam ediliyor.");
        }
    }


    public HomePage() {
        super();
    }


    public void goToHomePage() {
        String url = ConfigReader.getProperty("base_url");
        driver.get(url);
        acceptCookiesIfPresent();
    }

    public void searchForFlight(String departure, String arrival, String departureDate, String returnDate) {

        clickElement(roundTripRadioButton);

        sendKeysToElement(originInput, departure);
        waitForVisibilityOfElements(suggestionItems);
        clickElement(suggestionItems.get(0));

        sendKeysToElement(destinationInput, arrival);
        waitForVisibilityOfElements(suggestionItems);
        clickElement(suggestionItems.get(0));

        clickElement(departureDateInput);
        selectDateFromCalendar(departureDate);

        clickElement(returnDateInput);
        selectDateFromCalendar(returnDate);

        clickElement(searchButton);

        switchToNewTab();
    }
    private void selectDateFromCalendar(String date) {
        String dynamicXpath = "//button[@title='" + date + "']";
        WebElement dateElement = driver.findElement(By.xpath(dynamicXpath));
        clickElement(dateElement);
    }
}