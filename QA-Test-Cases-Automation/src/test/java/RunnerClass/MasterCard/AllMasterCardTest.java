package RunnerClass.MasterCard;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty"},
        		features = "feature/MasterCard_General_All", glue = {"com.mastercard.automation.functional"}
//        ,tags= {"@P2-test","@P1-test"}
)
public class AllMasterCardTest {
}
