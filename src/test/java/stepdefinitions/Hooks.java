package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.Driver;
import java.time.Duration;

public class Hooks {

    @Before
    public void setUp() {
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        System.out.println("Test başlıyor, tarayıcı hazırlandı.");
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failed_Scenario_Screenshot");
            System.out.println("Test başarısız! Ekran görüntüsü alındı.");
        }

        Driver.closeDriver();
        System.out.println("Test bitti, tarayıcı kapatıldı.");
    }
}