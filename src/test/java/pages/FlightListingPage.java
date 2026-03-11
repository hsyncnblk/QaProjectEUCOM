package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FlightListingPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(FlightListingPage.class);

    @FindBy(xpath = "//div[contains(@class, 'ctx-filter-departure-return-time')]")
    private WebElement departureTimeFilterMenu;

    @FindBy(xpath = "//p[@class='search__filter_departure-noon ']")
    private WebElement departureNoonFilterButton;

    @FindBy(xpath = "//div[@data-testid='departureDepartureTimeSlider']//div[contains(@class,'rc-slider-handle-2')]")
    private WebElement rightSliderHandle;

    @FindBy(xpath = "//div[@data-testid='departureDepartureTimeSlider']/preceding-sibling::div//div[@class='filter-slider-content']")
    private WebElement sliderTimeText;

    @FindBy(xpath = "//div[@data-testid='departureTime']")
    private List<WebElement> departureTimesList;

    @FindBy(xpath = "//div[contains(@class, 'ctx-filter-airline') and contains(@class, 'card-header')]")
    private WebElement airlinesFilterHeader;

    @FindBy(xpath = "//div[contains(@class, 'summary-marketing-airlines')]")
    private List<WebElement> airlineNames;

    @FindBy(xpath = "//div[@data-testid='flightInfoPrice']")
    private List<WebElement> flightPrices;

    @FindBy(css = "#flight-0 .action-select-btn")
    private WebElement gidisSecButonu;

    @FindBy(xpath = "(//button[contains(., 'Seç ve İlerle')])[1]")
    private WebElement paketSecButonu;

    @FindBy(xpath = "(//div[contains(@class, 'flight-list-return')]//button[contains(@class, 'action-select-btn')])[1]")
    private WebElement donusBtn;

    @FindBy(xpath = "(//div[starts-with(@data-testid, 'returnProviderPackageItem')])[1]")
    private WebElement donusPaketKarti;

    public String getActualRoute() {
        return getValueByJS(".info strong");
    }

    public void filterDepartureTime(String timeRange) {
        logger.info("Applying time filter: " + timeRange);
        waitForElementToBeClickable(departureTimeFilterMenu);
        clickElement(departureTimeFilterMenu);
        clickElement(departureNoonFilterButton);

        String targetTime = timeRange.split("-")[1].trim();
        Actions actions = new Actions(driver);
        int attempts = 0;

        while (!sliderTimeText.getText().contains(targetTime) && attempts < 25) {
            actions.dragAndDropBy(rightSliderHandle, 10, 0).build().perform();
            attempts++;
        }
    }

    public List<WebElement> getAllDepartureTimes() {
        waitForVisibilityOfElements(departureTimesList);
        return departureTimesList;
    }

    public void selectAirlineByName(String airlineName) {
        try {
            logger.info("Selecting airline: " + airlineName);
            String dynamicXpath = "//label[contains(.,'" + airlineName + "')]";
            scrollToElement(airlinesFilterHeader);

            List<WebElement> checkboxes = driver.findElements(By.xpath(dynamicXpath));
            if (checkboxes.isEmpty() || !checkboxes.get(0).isDisplayed()) {
                clickElement(airlinesFilterHeader);
            }

            WebElement airlineCheckbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dynamicXpath)));
            WebElement oldFirstTicket = driver.findElement(By.xpath("(//div[contains(@class, 'summary-marketing-airlines')])[1]"));

            scrollToElement(airlineCheckbox);
            clickElement(airlineCheckbox);

            wait.until(ExpectedConditions.stalenessOf(oldFirstTicket));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[contains(@class, 'summary-marketing-airlines')])[1]")));
        } catch (Exception e) {
            logger.error("Failed to select airline: " + airlineName, e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<String> getAllAirlineNames() {
        return airlineNames.stream().map(WebElement::getText).map(String::trim).toList();
    }

    public List<Double> getAllFlightPrices() {
        return flightPrices.stream().map(e -> Double.parseDouble(e.getAttribute("data-price"))).toList();
    }

    public void selectFirstFlight() {
        logger.info("Selecting the first available flight and returning package.");
       // switchToNewTab();

        waitForElementToBeClickable(gidisSecButonu);
        jsClick(gidisSecButonu);

        waitForElementToBeClickable(paketSecButonu);
        jsClick(paketSecButonu);

        waitForElementToBeClickable(donusBtn);
        jsClick(donusBtn);

        waitForElementToBeClickable(donusPaketKarti);
        jsClick(donusPaketKarti);
    }
}