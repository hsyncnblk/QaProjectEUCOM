package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import pages.FlightListingPage;
import pages.HomePage;
import java.util.List;

public class FlightSearchSteps {

    private final HomePage homePage = new HomePage();
    private final FlightListingPage listingPage = new FlightListingPage();

    @Given("the user navigates to the Enuygun homepage")
    public void the_user_navigates_to_the_enuygun_homepage() {
        homePage.goToHomePage();
    }

    @When("the user searches for a round-trip flight from {string} to {string} departing on {string} and returning on {string}")
    public void the_user_searches_for_a_round_trip_flight(String departure, String arrival, String depDate, String retDate) {
        homePage.searchForFlight(departure, arrival, depDate, retDate);
    }

    @And("the user applies the departure time filter {string} on the flight listing page")
    public void the_user_applies_the_departure_time_filter(String timeFilter) {
        listingPage.filterDepartureTimeUntil18();
    }

    @Then("verify that all displayed flights have departure times within the specified range")
    public void verify_that_all_displayed_flights_have_departure_times_within_the_specified_range() {
        List<WebElement> times = listingPage.getAllDepartureTimes();
        System.out.println("Kontrol edilen uçuş sayısı: " + times.size());

        for (WebElement timeElement : times) {
            String timeText = timeElement.getText();
            int hour = Integer.parseInt(timeText.split(":")[0]);

            Assert.assertTrue("Hata! Uçuş saati aralık dışında: " + timeText,
                    hour >= 10 && hour <= 18);
        }
    }

    @And("verify the search results match the selected route {string} to {string}")
    public void verify_the_search_results_match_the_selected_route(String departure, String arrival) {
        java.util.Locale tr = new java.util.Locale("tr", "TR");

        String rawText = listingPage.getActualRoute();

        String cleanActualText = rawText.toLowerCase(tr)
                .replaceAll("\\s+", " ")
                .trim();

        String expectedDep = departure.toLowerCase(tr).trim();
        String expectedArr = arrival.toLowerCase(tr).trim();


        Assert.assertTrue("HATA! Sayfa metni kalkış şehrini içermiyor. Mevcut: " + cleanActualText,
                cleanActualText.contains(expectedDep));

        Assert.assertTrue("HATA! Sayfa metni varış şehrini içermiyor. Mevcut: " + cleanActualText,
                cleanActualText.contains(expectedArr));
    }
    // CASE 2
    @And("the user selects {string} from the airline filters")
    public void the_user_selects_from_the_airline_filters(String airlineName) {
        listingPage.selectAirlineByName(airlineName);
        System.out.println("Havayolu filtresi uygulandı: " + airlineName);
    }

    @Then("verify that all displayed flights are {string} flights")
    public void verify_that_all_displayed_flights_are_flights(String expectedAirline) {
        List<String> actualAirlines = listingPage.getAllAirlineNames();
        System.out.println("Kontrol edilen bilet sayısı: " + actualAirlines.size());

        for (String airline : actualAirlines) {
            Assert.assertTrue("HATA! Listede farklı bir havayolu var: " + airline,
                    airline.contains(expectedAirline) || airline.contains("AJet") || airline.contains("AnadoluJet"));
        }
    }

    @And("verify that flight prices are sorted in ascending order")
    public void verify_that_flight_prices_are_sorted_in_ascending_order() {
        List<Double> prices = listingPage.getAllFlightPrices();

        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue("HATA! Fiyatlar sıralı değil. " + prices.get(i) + " > " + prices.get(i+1),
                    prices.get(i) <= prices.get(i+1));
        }
        System.out.println("Fiyatların küçükten büyüğe sıralandığı doğrulandı!");
    }
}