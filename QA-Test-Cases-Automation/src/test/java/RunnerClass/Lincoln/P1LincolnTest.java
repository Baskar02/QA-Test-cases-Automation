package RunnerClass.Lincoln;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.thucydides.core.annotations.Managed;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(plugin = { "pretty" }, features = "feature/Lincoln_General_All/p1LincolnGeneral.feature", glue = {
		"com.ford.automation.p1_Lincoln_general" }
//		,tags= {"@P1-test"}
		)

public class P1LincolnTest {
}
