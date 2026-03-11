package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.FlightData;

import java.util.ArrayList;
import java.util.List;

public class FlightDataAnalyzerPage extends BasePage {
    private static final Logger logger = LogManager.getLogger(FlightDataAnalyzerPage.class);
    private final By flightCardsLocator = By.cssSelector(".flight-list-departure .flight-summary-infos");

    public List<FlightData> scrapeFlightData() {
        logger.info("Extracting flight data for analysis...");

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(flightCardsLocator));

        List<FlightData> scrapedData = new ArrayList<>();
        List<WebElement> cards = driver.findElements(flightCardsLocator);

        for (WebElement card : cards) {
            try {
                String airline = card.findElement(By.cssSelector(".summary-marketing-airlines")).getText().trim();
                String depTime = card.findElement(By.cssSelector(".flight-departure-time")).getText().trim();
                String duration = card.findElement(By.cssSelector(".summary-duration span")).getText().trim();
                String priceStr = card.findElement(By.cssSelector(".summary-average-price")).getAttribute("data-price");
                double price = Double.parseDouble(priceStr);

                scrapedData.add(new FlightData(airline, depTime, duration, price));
            } catch (Exception e) {
                logger.warn("Failed to scrape data from a specific flight card. It might be an ad or missing data. Error: " + e.getMessage());
            }
        }
        logger.info("Successfully extracted " + scrapedData.size() + " flights.");
        return scrapedData;
    }
}