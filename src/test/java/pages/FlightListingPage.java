package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
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


    // CASE 2:
    @FindBy(xpath = "//div[contains(@class, 'ctx-filter-airline') and contains(@class, 'card-header')]")
    private WebElement airlinesFilterHeader;

    @FindBy(xpath = "//div[contains(@class, 'summary-marketing-airlines')]")
    private List<WebElement> airlineNames;

    @FindBy(xpath = "//div[@data-testid='flightInfoPrice']")
    private List<WebElement> flightPrices;

    public String getActualRoute() {

        return getValueByJS(".info strong");
    }

    public void filterDepartureTimeUntil18() {
        clickElement(departureTimeFilterMenu);

        clickElement(departureNoonFilterButton);

        Actions actions = new Actions(driver);
        int attempts = 0;

        while (!sliderTimeText.getText().contains("18:00") && attempts < 15) {
            actions.dragAndDropBy(rightSliderHandle, 8, 0).build().perform();
            attempts++;
        }
    }

    public List<WebElement> getAllDepartureTimes() {
        return departureTimesList;
    }

    //Case 2
    public void selectAirlineByName(String airlineName) {
        try {
            String dynamicXpath = "//label[contains(.,'" + airlineName + "')]";
            scrollToElement(airlinesFilterHeader);

            java.util.List<org.openqa.selenium.WebElement> checkboxes = driver.findElements(org.openqa.selenium.By.xpath(dynamicXpath));
            if (checkboxes.isEmpty() || !checkboxes.get(0).isDisplayed()) {
                clickElement(airlinesFilterHeader);
            }

            org.openqa.selenium.WebElement airlineCheckbox = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.xpath(dynamicXpath)));

            org.openqa.selenium.WebElement oldFirstTicket = driver.findElement(org.openqa.selenium.By.xpath("(//div[contains(@class, 'summary-marketing-airlines')])[1]"));

            scrollToElement(airlineCheckbox);
            clickElement(airlineCheckbox);

            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf(oldFirstTicket));
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.xpath("(//div[contains(@class, 'summary-marketing-airlines')])[1]")));

        } catch (Exception e) {
            System.out.println("Havayolu filtresi seçilirken hata: " + e.getMessage());
            throw new RuntimeException("Filtre bulunamadı veya sayfa yenilenemedi!");
        }
    }
    public List<String> getAllAirlineNames() {
        return airlineNames.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();
    }

    public List<Double> getAllFlightPrices() {
        return flightPrices.stream()
                .map(e -> Double.parseDouble(e.getAttribute("data-price")))
                .toList();
    }
}