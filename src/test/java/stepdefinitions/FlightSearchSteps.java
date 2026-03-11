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
import java.util.Locale;

public class FlightSearchSteps {

    private final HomePage homePage = new HomePage();
    private final FlightListingPage listingPage = new FlightListingPage();
    private final Locale TR_LOCALE = new Locale("tr", "TR");

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
        listingPage.filterDepartureTime(timeFilter);
    }

    @Then("verify that all displayed flights have departure times within the specified range")
    public void verify_that_all_displayed_flights_have_departure_times_within_the_specified_range() {
        List<WebElement> times = listingPage.getAllDepartureTimes();
        for (WebElement timeElement : times) {
            int hour = Integer.parseInt(timeElement.getText().split(":")[0]);
            Assert.assertTrue("HATA! Uçuş saati " + hour + " aralık dışında!", hour >= 10 && hour <= 18);
        }
    }

    @And("verify the search results match the selected route {string} to {string}")
    public void verify_the_search_results_match_the_selected_route(String departure, String arrival) {
        String cleanActualText = listingPage.getActualRoute().toLowerCase(TR_LOCALE).replaceAll("\\s+", " ").trim();
        String expectedDep = departure.toLowerCase(TR_LOCALE).trim();
        String expectedArr = arrival.toLowerCase(TR_LOCALE).trim();

        Assert.assertTrue("HATA! Kalkış şehri eksik: " + expectedDep, cleanActualText.contains(expectedDep));
        Assert.assertTrue("HATA! Varış şehri eksik: " + expectedArr, cleanActualText.contains(expectedArr));
    }

    // --- CASE 2 ---
    @And("the user selects {string} from the airline filters")
    public void the_user_selects_from_the_airline_filters(String airlineName) {
        listingPage.selectAirlineByName(airlineName);
    }

    @Then("verify that all displayed flights are {string} flights")
    public void verify_that_all_displayed_flights_are_flights(String expectedAirline) {
        List<String> actualAirlines = listingPage.getAllAirlineNames();
        for (String airline : actualAirlines) {
            Assert.assertTrue("Farklı havayolu bulundu: " + airline,
                    airline.contains(expectedAirline) || airline.contains("AJet") || airline.contains("AnadoluJet"));
        }
    }

    @And("verify that flight prices are sorted in ascending order")
    public void verify_that_flight_prices_are_sorted_in_ascending_order() {
        List<Double> prices = listingPage.getAllFlightPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue("Sıralama hatası: " + prices.get(i) + " > " + prices.get(i+1),
                    prices.get(i) <= prices.get(i+1));
        }
    }
}