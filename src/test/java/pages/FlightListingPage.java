package pages;

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
}