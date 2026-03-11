package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import pages.FlightListingPage;
import pages.HomePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Locale;

public class FlightSearchSteps {
    private static final Logger logger = LogManager.getLogger(FlightSearchSteps.class);
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

    @Then("verify that all displayed flights have departure times within {int} and {int}")
    public void verify_that_all_displayed_flights_have_departure_times_within_range(int startHour, int endHour) {
        logger.info("Verifying flight hours are between " + startHour + " and " + endHour);
        List<WebElement> times = listingPage.getAllDepartureTimes();

        for (WebElement timeElement : times) {
            int hour = Integer.parseInt(timeElement.getText().split(":")[0]);
            Assert.assertTrue("FAILED: Flight time " + hour + " is out of the [" + startHour + "-" + endHour + "] range!",
                    hour >= startHour && hour <= endHour);
        }
    }

    @And("verify the search results match the selected route {string} to {string}")
    public void verify_the_search_results_match_the_selected_route(String departure, String arrival) {
        String cleanActualText = listingPage.getActualRoute().toLowerCase(TR_LOCALE).replaceAll("\\s+", " ").trim();
        String expectedDep = departure.toLowerCase(TR_LOCALE).trim();
        String expectedArr = arrival.toLowerCase(TR_LOCALE).trim();

        Assert.assertTrue("FAILED: Departure city mismatch. Expected: " + expectedDep, cleanActualText.contains(expectedDep));
        Assert.assertTrue("FAILED: Arrival city mismatch. Expected: " + expectedArr, cleanActualText.contains(expectedArr));
    }

    @And("the user selects {string} from the airline filters")
    public void the_user_selects_from_the_airline_filters(String airlineName) {
        listingPage.selectAirlineByName(airlineName);
    }

    @Then("verify that all displayed flights are {string} flights")
    public void verify_that_all_displayed_flights_are_flights(String expectedAirline) {
        List<String> actualAirlines = listingPage.getAllAirlineNames();
        for (String airline : actualAirlines) {
            Assert.assertTrue("FAILED: Found different airline: " + airline,
                    airline.contains(expectedAirline) || airline.contains("AJet") || airline.contains("AnadoluJet"));
        }
    }

    @And("verify that flight prices are sorted in ascending order")
    public void verify_that_flight_prices_are_sorted_in_ascending_order() {
        List<Double> prices = listingPage.getAllFlightPrices();
        for (int i = 0; i < prices.size() - 1; i++) {
            Assert.assertTrue("FAILED: Prices are not ascending: " + prices.get(i) + " > " + prices.get(i+1),
                    prices.get(i) <= prices.get(i+1));
        }
    }

    @And("the user selects the first available flight")
    public void the_user_selects_the_first_available_flight() {
        listingPage.selectFirstFlight();
    }
}