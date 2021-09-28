package RunnerClass.Ford;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
		plugin = {"pretty"},
		features = "feature/Ford_General_All/p2general.feature", glue = { "com.ford.automation.p2_general" },
		tags= {"@TestF"}
		)

public class P1FordTest {

}
