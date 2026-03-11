package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.FlightDataAnalyzerPage;
import utils.CsvWriter;
import utils.DataAnalyzer;
import utils.FlightData;
import utils.HtmlReportGenerator;

import java.util.List;

public class AnalysisSteps {

    private final FlightDataAnalyzerPage analyzerPage = new FlightDataAnalyzerPage();
    private List<FlightData> scrapedFlights; // Veriyi adımlar arasında taşımak için

    @When("the user extracts all flight data and saves it to a CSV file")
    public void the_user_extracts_all_flight_data_and_saves_it_to_a_csv_file() {
        // 1. Yeni sekmeye geç
        analyzerPage.switchToNewTab();

        // 2. Verileri sayfadan kazı
        scrapedFlights = analyzerPage.scrapeFlightData();

        // 3. target klasörünün içine CSV olarak kaydet
        String filePath = "target/flight_data_report.csv";
        CsvWriter.writeToCSV(scrapedFlights, filePath);
    }

    @Then("the user analyzes the data for minimum, maximum, and average prices")
    public void the_user_analyzes_the_data_for_minimum_maximum_and_average_prices() {
        // 1. Konsolda matematiksel hesaplamaları ve şampiyon uçuşu göster
        DataAnalyzer.analyzeFlights(scrapedFlights);

        // 2. Harika HTML ve Grafik Raporumuzu oluştur!
        String htmlPath = "target/FlightAnalysisReport.html";
        HtmlReportGenerator.generateReport(scrapedFlights, htmlPath);
    }
}