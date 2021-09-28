package RunnerClass.Ford;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        		features = "feature/Cross_Browser/JenkinsTest.feature", glue = {"com.ford.automation.p1_general"}
        ,tags= {"@Ford-Sanity"}

)
public class Jenkins_FordTest {
}
