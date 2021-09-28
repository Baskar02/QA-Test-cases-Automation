package RunnerClass.Ford;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
		plugin = {"pretty"},
		features = "feature/Ford_General_All/p3general.feature", glue = {"com.ford.automation.p3_general"},
		tags= {"@P3"}
		)
public class P3FordTest {
}
