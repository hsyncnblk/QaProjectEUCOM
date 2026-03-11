package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html",
                "json:target/json-reports/cucumber.json"
        },
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        tags = "@AnalysisPath",
        dryRun = false
)
public class TestRunner {
}