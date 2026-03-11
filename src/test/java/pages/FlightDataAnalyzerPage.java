package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.FlightData;
import java.util.ArrayList;
import java.util.List;

public class FlightDataAnalyzerPage extends BasePage {

    private final By flightCardsLocator = By.cssSelector(".flight-list-departure .flight-summary-infos");

    public List<FlightData> scrapeFlightData() {
        System.out.println("Uçuş verileri analiz için toplanıyor...");
        waitBySecond(5);
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
                //
            }
        }
        System.out.println("Toplam " + scrapedData.size() + " uçuş verisi başarıyla çekildi.");
        return scrapedData;
    }
}