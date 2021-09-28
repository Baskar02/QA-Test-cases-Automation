package RunnerClass.Lincoln;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        		features = "feature/Lincoln_General_All/p2LincolnGeneral.feature", glue = { "com.ford.automation.p2_general","com.ford.automation.p1_Lincoln_general","com.ford.automation.p2_Lincoln_general" }
//        ,tags= {"@P2-test"}
		)
public class P2LincolnTest {
}
