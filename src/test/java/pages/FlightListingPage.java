package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class FlightListingPage extends BasePage {

    @FindBy(xpath = "//div[contains(@class, 'ctx-filter-departure-return-time')]")
    private WebElement departureTimeFilterMenu;

    @FindBy(xpath = "//div[@data-testid='departureDepartureTimeSlider']//div[contains(@class,'rc-slider-handle-2')]")
    private WebElement rightSliderHandle;

    @FindBy(xpath = "//div[@data-testid='departureDepartureTimeSlider']/preceding-sibling::div//div[@class='filter-slider-content']")
    private WebElement sliderTimeText;

    @FindBy(xpath = "//p[@class='search__filter_departure-noon ']")
    private WebElement departureNoonFilterButton;

    @FindBy(xpath = "//div[@data-testid='departureTime']")
    private List<WebElement> departureTimesList;

    @FindBy(xpath = "//div[contains(@class, 'ctx-filter-airline') and contains(@class, 'card-header')]")
    private WebElement airlinesFilterHeader;

    @FindBy(xpath = "//div[contains(@class, 'summary-marketing-airlines')]")
    private List<WebElement> airlineNames;

    @FindBy(xpath = "//div[@data-testid='flightInfoPrice']")
    private List<WebElement> flightPrices;

    @FindBy(xpath = "(//button[contains(@class, 'select-flight-button')])[1]")
    private WebElement firstFlightSelectButton;


    public String getActualRoute() {
        return getValueByJS(".info strong");
    }

    public void filterDepartureTime(String timeRange) {
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
        return departureTimesList;
    }


    public void selectAirlineByName(String airlineName) {
        try {
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
            throw new RuntimeException("Havayolu filtresi (" + airlineName + ") seçilemedi: " + e.getMessage());
        }
    }

    public List<String> getAllAirlineNames() {
        return airlineNames.stream().map(WebElement::getText).map(String::trim).toList();
    }

    public List<Double> getAllFlightPrices() {
        return flightPrices.stream().map(e -> Double.parseDouble(e.getAttribute("data-price"))).toList();
    }

    public void selectFirstFlight() {
        waitForVisibilityOfElement(firstFlightSelectButton);
        scrollToElement(firstFlightSelectButton);
        clickElement(firstFlightSelectButton);
    }
}