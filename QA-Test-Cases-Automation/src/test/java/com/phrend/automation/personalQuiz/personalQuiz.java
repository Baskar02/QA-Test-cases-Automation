package com.phrend.automation.personalQuiz;

import java.util.List;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.ford.automation.base.BaseTest;

import config.Configuration;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class personalQuiz extends BaseTest {
	@Given("^Open FireFox browser on English page$")
	public void openFireFoxBrowser() throws Throwable {
		System.out.println("Open FireFox browser");
		System.setProperty("webdriver.gecko.driver", Configuration.PATH_TO_GECKO_DRIVER);
		driver = new FirefoxDriver();

	}

	@Given("^Open Chrome browser on P2$")
	public void openChromeBrowser() throws Throwable {
		System.out.println("Open Chrome browser");
		System.setProperty("webdriver.chrome.driver", Configuration.PATH_TO_CHROME_DRIVER);
		driver = new ChromeDriver();
	}

	@When("^Maximize browser and enter link \"(.*?)\" on English page$")
	public void openTestLink(String link) throws Throwable {
		System.out.println("Maximize browser and enter link");
		driver.manage().window().maximize();
		driver.get(link);
	}

	@When("^Maximize browser and enter phrend link on English page$")
	public void openPhrendLink() throws Throwable {
		System.out.println("Maximize browser and enter link");
		driver.manage().window().maximize();
		driver.get("https://core-master-core-master.phrend.net/");
	}

	@When("^Redirect to link \"(.*?)\" on English page$")
	public void redirectToLinkOnVietnamesePage(String link) throws Throwable {
		System.out.println("Redirect to link");
		Thread.sleep(30000);
		driver.get(link);
	}

	@And("^Click on Sign in link on English page$")
	public void clickOnSignInLink() throws Throwable {
		System.out.println("Click on Sign in link");
		getVisibleElementByXpath("//a[text()='Sign In']").click();
	}

	@When("^Input email \"(.*?)\" and click Continue on English page$")
	public void enterEmail(String email) throws Throwable {
		System.out.println("Input email and click Continue");
		getVisibleElementByXpath("//*[@id='float1']").sendKeys(email);
		getVisibleElementByXpath("//button[text()='Continue']").click();
	}

	@When("^Input password \"(.*?)\" and click Lets Go on English page$")
	public void enterPasword(String password) throws Throwable {
		System.out.println("Input password and click Lets Go");
		getVisibleElementByXpath("//*[@id='user_password']").sendKeys(password);
		getVisibleElementByXpath("//input[@name='commit']").click();
	}

	@And("^User enters Credentials to login$")
	public void userEntersCreditialsToLogin(DataTable userCredentials) throws Throwable {
		System.out.println("User enters Credentials to login");
		// Write the code to handle Data Table
		List<List<String>> data = userCredentials.raw();

		// This is to get the first data of the set (First Row + First Column)
		getVisibleElementByXpath("//*[@id='float1']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//button[text()='Continue']").click();

		// This is to get the first data of the set (First Row + Second Column)
		getVisibleElementByXpath("//*[@id='user_password']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='commit']").click();

	}

	@Then("^See page redirected to profile \"(.*?)\" on English page$")
	public void seeThePageRedirectedToDashboard(String profilePageUrl) throws Throwable {
		System.out.println("See page redirected to profile");
		verifyRedirecToCorrectLink(profilePageUrl);
	}

	@And("^Click on Retake link on English page$")
	public void clickOnRetakeLink() throws Throwable {
		System.out.println("Click on Retake link");
		getVisibleElementByXpath("//a[text()='Retake']").click();
	}

	@Then("^See Personality Quiz popup on English page$")
	public void seePersonalityQuizPopup() throws Throwable {
		System.out.println("See Personality Quiz popup");
		getVisibleElementByXpath("//h4[text()='Personality Quiz']");
	}

	@When("^Click on Lets do it button on English page$")
	public void clickOnLetDoItButton() throws Throwable {
		System.out.println("Click on Lets do it button");
		getVisibleElementByXpath("//button[contains(text(), 'do it')]").click();
	}

	@Then("^See Extraverted Enthusiastic popup on English page$")
	public void seeExtravertedPopup() throws Throwable {
		System.out.println("See Extraverted Enthusiastic popup");
		getVisibleElementByXpath("//h4[text()='Extraverted, Enthusiastic']");
	}

	@When("^Click on the answer \"(.*?)\" of Extraverted Enthusiastic on English page$")
	public void clickOnTheAnswerOfExtravertedEnthusiastic(String answer) throws Throwable {
		System.out.println("Click on the answer: " + answer + " of Extraverted Enthusiastic");
		AnswersEnthusiastic answerOfEnthusiastic = AnswersEnthusiastic.valueOf(answer.toLowerCase());
		switch (answerOfEnthusiastic) {
		case nopenotme:
			getVisibleElementByXpath("//*[@id='quiz']/div/div/div[3]/div[2]/div[2]/div/div/div[1]/label/span").click();
			break;
		case notreally:
			getVisibleElementByXpath(".//*[@id='quiz']/div/div/div[3]/div[2]/div[2]/div/div/div[2]/label/span").click();
			break;
		case ehsoso:
			getVisibleElementByXpath(".//*[@id='quiz']/div/div/div[3]/div[2]/div[2]/div/div/div[3]/label/span").click();
			break;
		case somewhatagree:
			getVisibleElementByXpath(".//*[@id='quiz']/div/div/div[3]/div[2]/div[2]/div/div/div[4]/label/span").click();
			break;
		case yupnailedit:
			getVisibleElementByXpath(".//*[@id='quiz']/div/div/div[3]/div[2]/div[2]/div/div/div[5]/label/span").click();
			break;
		default:
			throw new Exception("Invalid Suggestion name: " + answer);
		}
	}

	@When("^Click on the answer \"(.*?)\" of Critical Quarrelsome on English page$")
	public void clickOnTheAnswerOfCriticalQuarrelsome(String answer) throws Throwable {
		System.out.println("Click on the answer: " + answer + " of Critical Quarrelsome");
		AnswersEnthusiastic answerOfCriticalQuarrel = AnswersEnthusiastic.valueOf(answer.toLowerCase());
		switch (answerOfCriticalQuarrel) {
		case nopenotme:
			getVisibleElementByXpath(".//*[@id='quiz']/div/div/div[3]/div[3]/div[2]/div/div/div[1]/label/span").click();
			break;
		case notreally:
			getVisibleElementByXpath(".//*[@id='quiz']/div/div/div[3]/div[3]/div[2]/div/div/div[2]/label/span").click();
			break;
		case ehsoso:
			getVisibleElementByXpath(".//*[@id='quiz']/div/div/div[3]/div[3]/div[2]/div/div/div[3]/label/span").click();
			break;
		case somewhatagree:
			getVisibleElementByXpath(".//*[@id='quiz']/div/div/div[3]/div[3]/div[2]/div/div/div[4]/label/span").click();
			break;
		case yupnailedit:
			getVisibleElementByXpath(".//*[@id='quiz']/div/div/div[3]/div[3]/div[2]/div/div/div[5]/label/span").click();
			break;
		default:
			throw new Exception("Invalid Suggestion name: " + answer);
		}
	}

	@Then("^See Critical Quarrelsome popup on English page$")
	public void seeCriticalQuarrelsomePopup() throws Throwable {
		System.out.println("See Critical Quarrelsome popup");
		getVisibleElementByXpath("//h4[text()='Critical, quarrelsome']");
	}

	@Then("^See Dependable Self Disciplined popup on English page$")
	public void seeDependableSelfDisciplinedPopup() throws Throwable {
		System.out.println("See Dependable Self-disciplined popup");
		getVisibleElementByXpath("//h4[text()='Dependable, self-disciplined']");
	}

	@And("^User answers ten questions in personality quiz on English page$")
	public void userAnswerTenQuestions(DataTable answers) throws Throwable {
		System.out.println("User answers ten questions in personality quiz");
		// Write the code to handle Data Table
		List<List<String>> data = answers.raw();

		for (int i = 1; i <= 10; i++) {
			String xpathForQuestion = "//*[@id='quiz']/div/div/div[3]/div[%d+1]/div[2]/div/div/";
			String xpathForAnswer = "div[%d]/label/span";
			int j = 1;
			AnswersEnthusiastic answerOfEnthusiastic = AnswersEnthusiastic
					.valueOf(data.get(0).get(i - 1).toLowerCase());
			String locator = null;
			switch (answerOfEnthusiastic) {
			case nopenotme:
				locator = String.format(xpathForQuestion, i).concat(String.format(xpathForAnswer, j));
				getVisibleElementByXpath(locator).click();
				break;
			case notreally:
				locator = String.format(xpathForQuestion, i).concat(String.format(xpathForAnswer, j + 1));
				getVisibleElementByXpath(locator).click();
				break;
			case ehsoso:
				locator = String.format(xpathForQuestion, i).concat(String.format(xpathForAnswer, j + 2));
				getVisibleElementByXpath(locator).click();
				break;
			case somewhatagree:
				locator = String.format(xpathForQuestion, i).concat(String.format(xpathForAnswer, j + 3));
				getVisibleElementByXpath(locator).click();
				break;
			case yupnailedit:
				locator = String.format(xpathForQuestion, i).concat(String.format(xpathForAnswer, j + 4));
				getVisibleElementByXpath(locator).click();
				break;
			default:
				throw new Exception("Invalid Suggestion name: " + answerOfEnthusiastic);
			}
		}
	}

	@Then("^See Your Results popup on English page$")
	public void seeYourResultPopup() throws Throwable {
		System.out.println("See Your Results popup on English page");
		getVisibleElementByXpath("//div[text()='Your Results']");
	}
}
