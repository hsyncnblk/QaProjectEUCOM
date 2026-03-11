package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import pages.PassengerInfoPage;
import utils.ConfigReader;
import utils.Driver;
import java.util.Map;

public class PassengerSteps {

    private final PassengerInfoPage passengerPage = new PassengerInfoPage();

    @And("the user fills in the passenger information using keys:")
    public void the_user_fills_in_the_passenger_information_using_keys(io.cucumber.datatable.DataTable dataTable) {
        Map<String, String> keys = dataTable.asMaps(String.class, String.class).get(0);

        String email = ConfigReader.getProperty(keys.get("email"));
        String phone = ConfigReader.getProperty(keys.get("phone"));
        String fName = ConfigReader.getProperty(keys.get("firstName"));
        String lName = ConfigReader.getProperty(keys.get("lastName"));
        String tcNo = ConfigReader.getProperty(keys.get("tcNo"));

        passengerPage.fillPassengerForm(email, phone, fName, lName, tcNo);
    }

    @And("the user proceeds to the payment page")
    public void the_user_proceeds_to_the_payment_page() {
        passengerPage.clickContinue();
    }
    @Then("verify that the payment page is displayed")
    public void verify_that_the_payment_page_is_displayed() {
        Assert.assertTrue("HATA: Ödeme butonu (payment-form-submit-button) bulunamadı!",
                passengerPage.isPayButtonVisible());

        System.out.println("Sistem Onayı: Ödeme sayfasına başarıyla ulaşıldı ve buton doğrulandı.");
    }


}