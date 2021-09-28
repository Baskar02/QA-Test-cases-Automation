package com.ford.automation.p3_general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.functors.AllPredicate;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ford.automation.base.BaseTest;
import config.Configuration;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class P3General extends BaseTest {
	@Given("^Open firefox browser$")
	public void openFireFoxBrowser() throws Throwable {
		System.out.println("Open FireFox browser");
		System.setProperty("webdriver.gecko.driver", Configuration.PATH_TO_GECKO_DRIVER);
		driver = new FirefoxDriver();
	}

	@Given("^Open chrome browser$")
	public void openChromeBrowser() throws Throwable {
		System.out.println("Open Chrome browser");
		//		System.setProperty("webdriver.chrome.driver", Configuration.PATH_TO_CHROME_DRIVER);
		//		driver = new ChromeDriver();
	}

	@When("^Redirect to third party new link \"(.*?)\" on P2$")
	public void redirectt(String link) throws Throwable {
		System.out.println("Redirect to link");
		Thread.sleep(10000);
		driver.get(getProfileURL(link));
		if (isAlertPresent()) {
			driver.switchTo().alert();
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
		}
	}

	@When("^Maximize browser and enter link \"(.*?)\" on pointsprizes site$")
	public void openTestLink(String link) throws Throwable {
		System.out.println("Maximize browser and enter link");
		driver.manage().window().maximize();
		driver.get(getProfileURL(link));
	}

	@When("^Input mail into email field$")
	public void inputMailIntoEmailField(DataTable email) throws Throwable {
		List<List<String>> data = email.raw();
		getVisibleElementByXpath("//*[@id='index-header-email-input']/div/input[1]").sendKeys(data.get(0).get(0));
	}

	@And("^Click on Start Earning$")
	public void clickOnStartEarning() throws Throwable {
		getVisibleElementByXpath("//*[@id='index-header-email-input']/div/span/button").click();
	}

	@Then("^See components on pointsprizes site$")
	public void seeAllComponentsOnPointsprizes() throws Throwable {
		System.out.println("See components on pointsprizes site");
		getVisibleElementByXpath("//*[@id='wall']/div[1]/div[34]/a/div/div/div[2]/span");
	}

	@When("^Select items on pointsprizes site$")
	public void clickOnItems() throws Throwable {
		System.out.println("Select items on the list");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath("//span[contains(text(),'Points')]"));
		int count = 0;
		int interval = 30000;
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				element.click();
				Thread.sleep(interval);
				count++;
				if (count == elements.size())
					break;
			}
		}
		driver.quit();
	}

	@When("^Redirect to Join on grabpoints site$")
	public void redirectToJoin() throws Throwable {
		System.out.println("Redirect to Join on grabpoints site");
		WebElement link = getVisibleElementByXpath("//a[text()='JOIN']");
		driver.get(getProfileURL(link.getAttribute("href")));
		if (isAlertPresent()) {
			driver.switchTo().alert();
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
		}
	}

	@And("^Click on Your Email arrow$")
	public void clickOnYourEmailArrow() throws Throwable {
		System.out.println("Click on Your Email arrow");
		getVisibleElementByXpath("//div[1]/div/div[2]/div[2]/div/form/div/div/div[1]/button").click();
	}

	@And("^Input email into your email$")
	public void inputEmailIntoYourEmail(DataTable email) throws Throwable {
		System.out.println("Input email into your email");
		List<List<String>> data = email.raw();
		getVisibleElementByXpath("//input[@name='email']").sendKeys(data.get(0).get(0));
	}

	@And("^Fill in registration form$")
	public void fillInRegistrationForm(DataTable registration) throws Throwable {
		System.out.println("Fill in registration form");
		List<List<String>> data = registration.raw();
		getVisibleElementByXpath("//input[@id='firstName']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@id='lastName']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@id='password']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@id='passwordVerification']").sendKeys(data.get(0).get(3));
	}

	@And("^Click on Submit button$")
	public void clickOnSubmitButton() throws Throwable {
		System.out.println("Click on Submit button");
		getVisibleElementByXpath("//button[text()='Submit']").click();
		Thread.sleep(5000);
	}

	@And("^Click on Skip button$")
	public void clickOnSkipButton() throws Throwable {
		System.out.println("Click on Skip button");
		getVisibleElementByXpath("//span[text()='Skip!']").click();
	}

	@When("^Redirect to link \"(.*?)\" in P3 Ford$")
	public void redirectToLink(String link) throws Throwable {
		System.out.println("Redirect to link");
		Thread.sleep(10000);
		driver.get(getProfileURL(link));
	}

	@When("^Go to google mail site in P3 Ford$")
	public void gotoGmailSite() throws Throwable {
		System.out.println("Go to google mail site");
		driver.navigate().to(
				"https://accounts.google.com/ServiceLogin?service=mail&continue=https://mail.google.com/mail/&hl=vi#identifier");
	}

	@When("^Input credentials on gmail site in P3 Ford$")
	public void inputUserNameAndNext(DataTable credentials) throws Throwable {
		System.out.println("Input credentials on gmail site");
		List<List<String>> data = credentials.raw();
		getVisibleElementByXpath("//input[@id='identifierId']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//span[text()='Tiếp theo']").click();
		getVisibleElementByXpath("//input[@name='password']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//span[text()='Tiếp theo']").click();
	}

	@When("^Click on first mail item in P3 Ford$")
	public void clickOnFirstMailItem() throws Throwable {
		System.out.println("Click on first mail item");
		getVisibleElementByXpath("//table/tbody/tr[contains(@class,'zE')]//b[contains(text(),'Ford Delivery')]").click();
	}

	@When("^Redirect to Login on grabpoints site$")
	public void redirectToLogin() throws Throwable {
		System.out.println("Redirect to Login on grabpoints site");
		WebElement link = getVisibleElementByXpath("//a[text()='LOGIN']");
		driver.get(getProfileURL(link.getAttribute("href")));
		if (isAlertPresent()) {
			driver.switchTo().alert();
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
		}
	}

	@And("^Click on No to not push notifications$")
	public void clickOnNo() throws Throwable {
		System.out.println("Click on No to push notifications");
		getVisibleElementByXpath("html/body/gp-dialog/div/div[2]/dialog-content/div/div[2]/div[2]/span").click();
		Thread.sleep(5000);
	}

	@And("^Click on Demographic questions$")
	public void clickOnDemographicQuestions() throws Throwable {
		System.out.println("Click on Demographic questions");
		getVisibleElementByXpath("//h3[text()='Demographic questions']").click();
	}

	@And("^Answer the remaining demographic questions$")
	public void answerTheRemainingDemographicQuestion() throws Throwable {
		System.out.println("Answer the remaining demographic questions");
		getVisibleElementByXpath(
				"//div[1]/div[2]/div/div[2]/div/div[1]/div/div[1]/div/div[1]/div/form/div/div[2]/div/div/div/input")
		.click();
		getVisibleElementByXpath("//li[2]/span[text()='People']").click();
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
		getVisibleElementByXpath("//input[@id='REWARD']").click();
		getVisibleElementByXpath("//input[@id='REWARD']").sendKeys("Oscar");
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
	}

	@When("^Click on Connect to Facebook$")
	public void clickConnectToFacebook(DataTable credentials) throws Throwable {
		System.out.println("Click on Connect to Facebook");
		getVisibleElementByXpath("//h3[contains(text(),'Connect to Facebook')]").click();
		String winHandleBefore = driver.getWindowHandle();
		for (String windHandle : driver.getWindowHandles()) {
			driver.switchTo().window(windHandle);
		}
		List<List<String>> data = credentials.raw();
		getVisibleElementByXpath("//input[@id='email']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@id='pass']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//label[@id='loginbutton']").click();
		getVisibleElementByXpath("//button[contains(text(),'Continue as')]").click();
		getVisibleElementByXpath("//button[text()='OK']").click();
		Thread.sleep(30000);
		driver.close();
		driver.switchTo().window(winHandleBefore);
	}

	@When("^Click on Follow us on Twitter$")
	public void clickFollowUsOnTwitter(DataTable credentials) throws Throwable {
		System.out.println("Click on Follow us on Twitter");
		getVisibleElementByXpath("//h3[contains(text(),'Follow us on Twitter')]").click();
		String winHandleBefore = driver.getWindowHandle();
		for (String windHandle : driver.getWindowHandles()) {
			driver.switchTo().window(windHandle);
		}
		List<List<String>> data = credentials.raw();
		getVisibleElementByXpath("//*[@id='oauth_form']/fieldset[1]/div[1]/label").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//*[@id='oauth_form']/fieldset[1]/div[2]/label").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@id='allow']").click();

		Thread.sleep(30000);
		driver.close();
		driver.switchTo().window(winHandleBefore);
	}

	@And("^Fill in all information$")
	public void fillInAllInfo() throws Throwable {
		System.out.println("Fill in all information");
		getVisibleElementByXpath(
				"//div[1]/div[2]/div/div[2]/div/div[1]/div/div[1]/div/div[1]/div/form/div/div[2]/div/div/div/input")
		.click();
		getVisibleElementByXpath("//li[2]/span[text()='Male']").click();
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
		getVisibleElementByXpath("//input[@id='BIRTHDAY']").click();
		getVisibleElementByXpath(
				"//div[@id='BIRTHDAY_root']/div/div/div/div/div[2]/div/select[contains(@class,'picker__select--year')]")
		.click();
		getVisibleElementByXpath(
				"//div[@id='BIRTHDAY_root']/div/div/div/div/div[2]/div/select[2]/option[text()='2001']").click();
		getVisibleElementByXpath("//*[@id='BIRTHDAY_table']/tbody/tr[1]/td[5]/div[text()='1']").click();
		getVisibleElementByXpath("//button[text()='Close']").click();
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
		getVisibleElementByXpath("//input[@id='STATE']").click();
		getVisibleElementByXpath("//input[@id='STATE']").sendKeys("HoangMai");
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
		getVisibleElementByXpath("//input[@id='CITY']").click();
		getVisibleElementByXpath("//input[@id='CITY']").sendKeys("Hanoi");
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
		getVisibleElementByXpath(
				"//div[1]/div[2]/div/div[2]/div/div[1]/div/div[1]/div/div[5]/div/form/div/div[2]/div/div/div/input")
		.click();
		getVisibleElementByXpath("//li[2]/span[text()='Caucasian']").click();
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
		getVisibleElementByXpath(
				"//div[1]/div[2]/div/div[2]/div/div[1]/div/div[1]/div/div[6]/div/form/div/div[2]/div/div/div/input")
		.click();
		getVisibleElementByXpath("//li[2]/span[text()='No schooling completed']").click();
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
		getVisibleElementByXpath(
				"//div[1]/div[2]/div/div[2]/div/div[1]/div/div[1]/div/div[7]/div/form/div/div[2]/div/div/div/input")
		.click();
		getVisibleElementByXpath("//li[2]/span[text()='Single']").click();
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
		getVisibleElementByXpath(
				"//div[1]/div[2]/div/div[2]/div/div[1]/div/div[1]/div/div[8]/div/form/div/div[2]/div/div/div/input")
		.click();
		getVisibleElementByXpath("//li[2]/span[text()='Employed for wages']").click();
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
		getVisibleElementByXpath(
				"//div[1]/div[2]/div/div[2]/div/div[1]/div/div[1]/div/div[9]/div/form/div/div[2]/div/div/div/input")
		.click();
		getVisibleElementByXpath("//li[2]/span[contains(text(),'9999')]").click();
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
		getVisibleElementByXpath(
				"//div[1]/div[2]/div/div[2]/div/div[1]/div/div[1]/div/div[10]/div/form/div/div[2]/div/div/div/input")
		.click();
		getVisibleElementByXpath("//li[2]/span[text()='Yes']").click();
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
		getVisibleElementByXpath(
				"//div[1]/div[2]/div/div[2]/div/div[1]/div/div[1]/div/div[11]/div/form/div/div[2]/div/div/div/input")
		.click();
		getVisibleElementByXpath("//li[2]/span[text()='People']").click();
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
		getVisibleElementByXpath("//input[@id='REWARD']").click();
		getVisibleElementByXpath("//input[@id='REWARD']").sendKeys("Oscar");
		getVisibleElementByXpath("//div[1]/div[2]/div/div[2]/div/div[2]/div/div[1]/div/span").click();
	}

	@And("^Click on Tutorial$")
	public void clickOnTutorial() throws Throwable {
		System.out.println("Click on Tutorial");
		getVisibleElementByXpath("//h3[text()='Tutorial']").click();
	}

	@And("^Fill in Tutorial$")
	public void fillInTutorial() throws Throwable {
		System.out.println("Fill in Tutorial");
		getVisibleElementByXpath("//span[contains(text(),'Begin tour')]").click();
		getVisibleElementByXpath("//span[text()='Next']").click();
		getVisibleElementByXpath("//span[text()='Next']").click();
		getVisibleElementByXpath("//span[text()='Next']").click();
		getVisibleElementByXpath("//span[text()='Next']").click();
		getVisibleElementByXpath("//span[text()='Next']").click();
		getVisibleElementByXpath("//span[text()='Next']").click();
		getVisibleElementByXpath("//span[text()='Start earning points']").click();
	}

	@Then("^See page redirected to correct link \"(.*?)\" on grabpoints site$")
	public void seeThePageRedirectedToCorrectLink(String link) throws Throwable {
		System.out.println("See page redirected to correct link");
		verifyRedirecToCorrectLink(link);
		Thread.sleep(5000);
	}

	@And("^Enter credentials on grabpoints site$")
	public void enterCredentials(DataTable credentials) throws Throwable {
		System.out.println("Enter credentials");
		List<List<String>> data = credentials.raw();
		getVisibleElementByXpath("//input[@id='email']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@id='password']").sendKeys(data.get(0).get(1));
	}

	@When("^Maximize browser and enter link \"(.*?)\" in P3 Ford$")
	public void openTestLinkInP3(String link) throws Throwable {
		System.out.println("Maximize browser and enter link");
		driver.manage().window().maximize();
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.open('','testwindow','width=400,height=200')");
		driver.close();
		driver.switchTo().window("testwindow");
		js.executeScript("window.moveTo(0,0);");
		js.executeScript("window.resizeTo(1450,1000);");
		driver.manage().window().maximize();
		driver.get(getProfileURL(link));
	}

	@Then("^See all components in service price calculator page in P3 Ford$")
	public void seeAllComponentsInServicePriceCalculatorPage() throws Throwable {
		System.out.println("See all components in service price calculator page");
		Thread.sleep(5000);
		getVisibleElementByXpath("//span[text()='All Vehicles']");
		getVisibleElementByXpath("//span[text()='Owners']");
		getVisibleElementByXpath("//span[text()='Locate a Dealer']");
		getVisibilityElementByXpath("//span[text()='Year']");
		getVisibleElementByXpath("//span[text()='Year']");
		getVisibleElementByXpath("//div[@id='s2id_select1']/a[@class='select2-choice']");
		getVisibleElementByXpath("//span[text()='Model']");
		getVisibleElementByXpath("//div[@id='s2id_select2']/a[@class='select2-choice']");
		getVisibleElementByXpath("//span[text()='Style']");
		getVisibleElementByXpath("//div[@id='s2id_select3']/a[@class='select2-choice']");
		getVisibleElementByXpath("//span[text()='Customer Type']");
		getVisibleElementByXpath("//div[@id='s2id_select4']/a[@class='select2-choice']");
		getVisibleElementByXpath("(//button[contains(text(),'Next')])[2]");
		getVisibleElementByXpath("//a[text()='Book a Service']");
		getVisibleElementByXpath("//a[text()='Call your Ford Dealer']");
		getVisibleElementByXpath("//a[text()='Terms and Conditions']");
		getVisibleElementByXpath("//h3[text()='Disclosures']");
	}

	@When("^Select values in all the drop downs in P3 Ford$")
	public void clickOnSelectYearDropdownList(DataTable parameters) throws Throwable {
		System.out.println("Select values in all the drop downs");
		Thread.sleep(10000);
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath("//div[@id='s2id_select1']/a[@class='select2-choice']").click();
		getVisibleElementByXpath("//div[contains(text(),'"+data.get(0).get(0)+"')]").click();
		getVisibleElementByXpath("//div[@id='s2id_select2']/a[@class='select2-choice']").click();
		getVisibleElementByXpath("(//div[@class='select2-search']/input)[6]").clear();
		getVisibleElementByXpath("(//div[@class='select2-search']/input)[6]").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("(//div[@class='select2-search']/input)[6]").sendKeys(Keys.ENTER);
		getVisibleElementByXpath("//div[@id='s2id_select3']/a[@class='select2-choice']").click();
		getVisibleElementByXpath("//div[contains(text(),'"+data.get(0).get(2)+"')]").click();
		getVisibleElementByXpath("//div[@id='s2id_select4']/a[@class='select2-choice']").click();
		getVisibleElementByXpath("//div[contains(text(),'"+data.get(0).get(3)+"')]").click();
		getVisibleElementByXpath("(//button[contains(text(),'Next')])[2]").click();
	}

	@Then("^See all information after selecting values in all the drop downs in P3 Ford$")
	public void seeAllInformationAfterSelectingValuesInAllTheDropDowns() throws Throwable {
		System.out.println("See all information after selecting values in all the drop downs");
		waitTillElemVisiblebyXpath("//span[contains(text(),'ESTIMATED KILOMETRES TO DATE')]", 240);
		getVisibleElementByXpath("//span[contains(text(),'ESTIMATED KILOMETRES TO DATE')]");
		getVisibleElementByXpath("//input[@class='ng-isolate-scope ng-pristine ng-valid']");
		getVisibleElementByXpath("//span[contains(text(),'FIRST REGISTRATION DATE')]");
		getVisibleElementByXpath("//div[@id='s2id_select6']/a[@class='select2-choice']/span[contains(text(),'January')]");
		getVisibleElementByXpath("//div[@id='s2id_select7']//span[contains(text(),'2017')]");
		getVisibleElementByXpath("//button[contains(text(),'Calculate')]");
		getVisibleElementByXpath("//div[@class='item-holder']/a[@class='item active']");
		getVisibleElementByXpath("//div[@class='item-holder']/a[@class='item active']//span[text()='Kms']");
		getVisibleElementByXpath("//div[@class='item-holder']//span[contains(text(),'15,000')]");
		getVisibleElementByXpath("//div[@class='item-holder']//span[text()='Service']");
		getVisibleElementByXpath("//div[@class='item-holder']//span[contains(text(),'1')]/small[text()='Year']");
		getVisibleElementByXpath("//div[@id='global-ux']/div[3]/div[2]/div[3]/div/div/div/div[2]/div[1]/div/div/ul/li[3]/div/a[@class='item']");
		getVisibleElementByXpath("//div[@id='global-ux']/div[3]/div[2]/div[3]/div/div/div/div[2]/div[1]/div/div/ul/li[3]/div/a[@class='item']//span[text()='Kms']");
		getVisibleElementByXpath("//div[@id='global-ux']/div[3]/div[2]/div[3]/div/div/div/div[2]/div[1]/div/div/ul/li[3]/div/a[@class='item']//span[contains(text(),'30,000')]");
		getVisibleElementByXpath("//div[@id='global-ux']/div[3]/div[2]/div[3]/div/div/div/div[2]/div[1]/div/div/ul/li[3]/div/a[@class='item']//span[text()='Service']");
		getVisibleElementByXpath("//div[@id='global-ux']/div[3]/div[2]/div[3]/div/div/div/div[2]/div[1]/div/div/ul/li[3]/div/a[@class='item']//span[contains(text(),'2')]/small[text()='Years']");
		getVisibleElementByXpath("//h2[text()='Payment Details']");
		getVisibleElementByXpath("//a[text()='Terms and Conditions']");
		getVisibleElementByXpath("//img[@alt='LAD']");
		getVisibleElementByXpath("//div[@class='content desktop hideForMobile']//a[text()='Book Service']");
		getVisibleElementByXpath("//img[@alt='Call Your Dealer']");
		getVisibleElementByXpath("//div[@class='content desktop hideForMobile']//a[text()='Call Your Dealer']");
		getVisibleElementByXpath("//img[@alt='View PDF']");
		getVisibleElementByXpath("//div[@class='content desktop hideForMobile']//a[text()='View&Print PDF']");
		getVisibleElementByXpath("//h3[text()='Disclosures']");
	}

	@When("^Input into Estimated Kilometres to date and select First Registration Date in P3 Ford$")
	public void inputIntoEstimatedKilometresToDateAndSelectFirstRegistrationDate(DataTable parameters)
			throws Throwable {
		System.out.println("Input into Estimated Kilometres to date and select First Registration Date");
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath("//span[contains(text(),'ESTIMATED KILOMETRES TO DATE')]/following-sibling::input").clear();
		getVisibleElementByXpath("//span[contains(text(),'ESTIMATED KILOMETRES TO DATE')]/following-sibling::input").sendKeys(data.get(0).get(0));
	}

	@And("^Click on Calculate button in P3 Ford$")
	public void clickOnCalculateButton() throws Throwable {
		System.out.println("Click on Calculate button");
		Thread.sleep(5000);
		getVisibleElementByXpath("//button[contains(text(),'Calculate')]").click();
	}

	@Then("^See all Kilometers and Service number section highlighted accordingly in P3 Ford$")
	public void seeAllKilometersAndServiceNumberSectionHightlightedAccordingly(DataTable parameters) throws Throwable {
		System.out.println("See all Kilometers and Service number section highlighted accordingly");
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath(
				"//span[text()='Kms']/following-sibling::span[contains(text(), '" + data.get(0).get(0) + "')]");
		getVisibleElementByXpath(
				"//span[text()='Service']/following-sibling::span[contains(text(), '" + data.get(0).get(1) + "')]");
		getVisibleElementByXpath(
				"//span[text()='Kms']/following-sibling::span[contains(text(), '" + data.get(0).get(2) + "')]");
		getVisibleElementByXpath(
				"//span[text()='Service']/following-sibling::span[contains(text(), '" + data.get(0).get(3) + "')]");
		getVisibleElementByXpath(
				"//span[text()='Kms']/following-sibling::span[contains(text(), '" + data.get(0).get(4) + "')]");
		getVisibleElementByXpath(
				"//span[text()='Service']/following-sibling::span[contains(text(), '" + data.get(0).get(5) + "')]");
	}

	@When("^Click on highlighted Kilometers and Service number in P3 Ford$")
	public void clickOnHighlightedKilometersAndServiceNumber() throws Throwable {
		System.out.println("Click on highlighted Kilometers and Service number");
		getVisibleElementByXpath("//span[text()='Kms']/preceding::a[@class='item active']").click();
		Thread.sleep(5000);
		if(driver.findElement(By.xpath("//p[contains(text(),'Based on the information')]")).isDisplayed()==false) {
			getVisibleElementByXpath("//a[@class='icon-holder']/div").click();
		}
		
	}

	@Then("^See all components on Payment Details in P3 Ford$")
	public void seeAllComponentsOnPaymentDetails() throws Throwable {
		System.out.println("See all components on Payment Details");
		Thread.sleep(5000);
		getVisibleElementByXpath("//p[text()='Standard Service Price' or text()='Standard Payment Price' or text()='Service Price']");
		getVisibleElementByXpath("//p[contains(text(), 'Additional maintenance items')]");
		getVisibleElementByXpath("//p[contains(text(), 'Service Price']");
	}

	@When("^Select up to 3 items on Additional Maintenance Items in P3 Ford$")
	public void selectUpTo3ItemsOnAdditionalServiceItems() throws Throwable {
		System.out.println("Select up to 3 items on Additional Service Items");
		Thread.sleep(5000);
		List<WebElement> elements = driver.findElements(By.xpath("//input[@id='checkbox-0']"));
		int count = 0;
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				element.click();
				count++;
				if (count == 3)
					break;
			}
		}
	}

	@When("^Select up to 3 items on Additional Maintenance Items and Verify Service Price in P3 Ford$")
	public void selectUpTo3ItemsOnAdditionalServiceItemsAndVerifyServicePrice() throws Throwable {
		System.out.println("Select up to 3 items on Additional Service Items and Verifying Service Price");
		Thread.sleep(2000);
		String before=getVisibleElementByXpath("//span[contains(@class, 'service-price')]").getText();
		int beforePrice=Integer.parseInt(before.substring(before.indexOf("$")+1, before.indexOf("*")-1));
		int i=0;
		for (i=0;i<3;i++) {
			getVisibleElementByXpath("//input[@id='checkbox-" + i + "']/following-sibling::label/span[@class='ng-binding']").click();
		}
		String after=getVisibleElementByXpath("//span[contains(@class, 'service-price')]").getText();
		int afterPrice=Integer.parseInt(after.substring(after.indexOf("$")+1, after.indexOf("*")-1));
		if(beforePrice!=afterPrice) {
			Assert.assertTrue("Total Service Price is not matched, Before Service items added" + beforePrice + ", After Service items added:" + afterPrice, true);
		} else {
			Assert.assertFalse("Total Service Price is not matched, Before Service items added" + beforePrice + ", After Service items added:" + afterPrice, true);
		}
	}

	

	@When("^Click on View and Print PDF in P3 Ford$")
	public void clickOnViewAndPrintPdf() throws Throwable {
		System.out.println("Click on View and Print PDF");
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@class='content desktop hideForMobile']//a[text()='View & Print PDF']"))));
		getVisibleElementByXpath("//div[@class='content desktop hideForMobile']//a[text()='View & Print PDF']").click();
	}

	@Then("^See all components on Latest Offers Postcode in P3 Ford$")
	public void seeAllComponentsOnLatestOffersPostcode() throws Throwable {
		System.out.println("See all components on Latest Offers Postcode");
		getVisibleElementByXpath("//h4[text()='Want to see offers in your local area?']");
		getVisibleElementByXpath("//span[text()='Enter your postcode']");
		getVisibleElementByXpath("//input[@name='postcode']");
		getVisibleElementByXpath("//button[text()='Submit']");
		getVisibleElementByXpath("//a[text()='Cancel']");
	}

	@When("^Input wrong postcode into postcode field and verify validation message displayed in P3 Ford$")
	public void inputWrongPostcodeIntoPostcodeFieldAndVerifyValidationMessageDisplayed(DataTable parameters)
			throws Throwable {
		System.out.println("Input wrong postcode into postcode field and verify validation message displayed");
		List<List<String>> data = parameters.raw();
		waitTillElemVisiblebyXpath("//div[contains(@class,'overlay-content-inner-offers')]", 240);
		getVisibleElementByXpath("//div[contains(@class,'overlay-content-inner-offers')]").click();
		getVisibleElementByXpath("//input[@name='postcode']").clear();
		getVisibleElementByXpath("//input[@name='postcode']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//button[text()='Submit']").click();
		String Errorcode = getVisibleElementByXpath("//div[@class='form-textfield']/label/small").getText();
		Assert.assertEquals(data.get(0).get(1), Errorcode);
	}

	@When("^Input correct postcode into postcode field in P3 Ford$")
	public void inputCorrectPostcodeIntoPostcodeField(DataTable parameter) throws Throwable {
		System.out.println("Input correct postcode into postcode field");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//div[contains(@class,'overlay-content-inner-offers')]").click();
		getVisibleElementByXpath("//input[@name='postcode']").clear();
		getVisibleElementByXpath("//input[@name='postcode']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//button[text()='Submit']").click();
	}

	@Then("^See all components on Latest Offers left panel in P3 Ford$")
	public void seeAllComponentsOnLatestOffersLeftPanel(DataTable parameter) throws Throwable {
		System.out.println("See all components on Latest Offers left panel");
		List<List<String>> data = parameter.raw();
		for (int i = 0; i <= 20; i++) {
			getVisibleElementByXpath(
					"//div[@class='offers-column search-filter-holder']//span[text()='" + data.get(0).get(i) + "']");
		}
		getVisibleElementByXpath(
				"//div[@class='offers-column search-filter-holder']//span[contains(@class,'ui-slider-handle')]");
	}

	@Then("^See all components on Latest Offers main page in P3 Ford$")
	public void seeAllComponentsOnLatestOffersMainPage(DataTable parameter) throws Throwable {

		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//p[contains(text(),'Prices for July 21, 2014 Postal Code')]");
		getVisibleElementByXpath("//a[text()='" + data.get(0).get(0) + "']");
		getVisibleElementByXpath("//h2[text()='Latest Offers']");
		getVisibleElementByXpath("//p[text()='Our latest offers are updated regularly, so check in for a good deal.']");
		getVisibleElementByXpath("//span[text()='Sort by']");
		getVisibleElementByXpath("//a[text()='View Disclaimer']");
		getVisibleElementByXpath("//h4[text()='TRUCK']");
		getVisibleElementByXpath("//h4[text()='SUV']");
		getVisibleElementByXpath("//h4[text()='CAR']");
		for (int i = 1; i <= 23; i++) {
			getVisibleElementByXpath("//span[text()='" + data.get(0).get(i) + "']");
		}
	}

	@Then("^Verify all the vehicles have details about model year details of offers available in P3 Ford$")
	public void verifyAllTheVehiclesHaveDetailsAboutModelYearDetailsOfOffersAvailable() throws Throwable {
		String xpath2 = "/div/a/p";
		System.out.println("Verify all the vehicles have details about model year details of offers available");
		for (int i = 1; i <= 5; i++) {
			String xpath1 = "//div[@id='item" + i + "_1']";
			getVisibleElementByXpath(xpath1.concat(xpath2));
		}

		for (int j = 1; j <= 5; j++) {
			String xpath1 = "//div[@id='item" + j + "_2']";
			getVisibleElementByXpath(xpath1.concat(xpath2));
		}

		for (int k = 1; k <= 3; k++) {
			String xpath1 = "//div[@id='item" + k + "_3']";
			getVisibleElementByXpath(xpath1.concat(xpath2));
		}
	}

	@Then("^Verify all the vehicles have details about model year details of offers available for ZA in P3 Ford$")
	public void verifyAllTheVehiclesHaveDetailsAboutModelYearDetailsOfOffersAvailableZA() throws Throwable {
		waitTillElementVisible_Xpath("(//div[@class='vehicle-details'])[1]/h5");
		getVisibleElementByXpath("(//div[@class='vehicle-details'])[1]/h5");
		getVisibleElementByXpath("(//div[@class='vehicle-details'])[1]/h6");
		getVisibleElementByXpath("(//button[text()='QUICK VIEW'])[1]");


	}
	@When("^Verify Sort by in Latest Offer page in P3 Ford$")
	public void verifySortByInLatestOfferPage() throws Throwable {
		System.out.println("Click on Sort by in Latest Offer page");

		getVisibleElementByXpath("//span[text()='Sort by']").click();
		getVisibleElementByXpath("//a[text()='Price High To Low']");
		getVisibleElementByXpath("//a[text()='Price Low To High']");
		getVisibleElementByXpath("//a[text()='Default']");

	}

	@When("^Click on Quick View CTA button on any of the latest offer$")
	public void clickonQuickViewCTAbuttononanyofthelatestoffer() throws Throwable {
		System.out.println("Click on Quick View CTA on Latest Offer page");
		getVisibleElementByXpath("(//button[text()='Quick View'])[1]").click();
	}


	@Then("^Verify the items on Quick View Overlay$")
	public void verifyQuickViewoverlay() throws Throwable {
		System.out.println("Verify the items on Quick View Overlay");
		getVisibleElementByXpath("//span[contains(text(),'BACK')]");
		getVisibleElementByXpath("//h1[contains(text(),'Quick View')]");
		getVisibleElementByXpath("//div[@class='medium-6 xlarge-6 columns']/div/img");
		getVisibleElementByXpath("//td[contains(text(), 'Retail Price:')]");
		getVisibleElementByXpath("//td[contains(text(), 'Loan Amount:')]");
		getVisibleElementByXpath("//td[contains(text(), 'Residue Value:')]");
		getVisibleElementByXpath("//td[contains(text(), 'Interest Rate:')]");
		getVisibleElementByXpath("//td[contains(text(), 'Term:')]");
		getVisibleElementByXpath("//td[contains(text(), 'Total Repayment:')]");
		getVisibleElementByXpath("//span[@class='overlay-close']/i[@class=\"icon-plus icon-x\"]");
		getVisibleElementByXpath("//a[contains(text(),'ENQUIRE')]");
		getVisibleElementByXpath("//a[contains(text(),'EXPLORE')]");
		getVisibleElementByXpath("//button[contains(text(),'ADD TO SHORTLIST')]");
		getVisibleElementByXpath("//span[@class='add-shortlist-icon' and contains(text(),'+')]");
	}

	@When("^Click on the Grid view from top pane of the page$")
	public void clickonGridviewGridviewfromtoppane() throws Throwable {
		System.out.println("Click on Grid View item on Latest Offer page");
		getVisibleElementByXpath("//label[@class='icon icon-grid']").click();
	}



	@Then("^Verify that all the vehicles are displayed in Grid View$")
	public void verifyvehiclesgridview() throws Throwable {
		System.out.println("Verify that all the vehicles are displayed in Grid View");
		waitTillElemVisiblebyXpath("//div[@class='specialOfferGrid column grid']", 240);
		getVisibleElementByXpath("//div[@class='specialOfferGrid column grid']");
	}

	@When("^Click on the List view from top pane of the page$")
	public void clickonListviewGridviewfromtoppane() throws Throwable {
		System.out.println("Click on List View item on Latest Offer page");
		getVisibleElementByXpath("//label[@class='icon icon-list']").click();
	}



	@Then("^Verify that all the vehicles are displayed in List View$")
	public void verifyvehicleslistview() throws Throwable {
		System.out.println("Verify that all the vehicles are displayed in List View");
		waitTillElemVisiblebyXpath("//div[@class='offerLeftPadding specialOfferList list']", 240);
		getVisibleElementByXpath("//div[@class='offerLeftPadding specialOfferList list']");
	}

	@When("^Click on Short list button from overlay$")
	public void click_on_Short_list_button_from_overlay() throws Throwable{
		System.out.println("Click on Short list button");
		getVisibleElementByXpath("//button[contains(text(),'ADD TO SHORTLIST')]").click();

	}

	@Then("^Ensure vehicle is added in short listed sections$")
	public void ensure_vehicle_is_added_in_short_listed_sections() throws Throwable {
		waitTillElemVisiblebyXpath("//button[@class='cta-button cta-button-secondary removeFromShortList' and contains(text(),'REMOVE FROM SHORTLIST')]", 240);
		getVisibleElementByXpath("//button[@class='cta-button cta-button-secondary removeFromShortList' and contains(text(),'REMOVE FROM SHORTLIST')]");
	}

	@SuppressWarnings("unchecked")
	@Then("^Verify the ascending sorting option are working in P3 Ford$")
	public void verifyTheAscendingSortingOptionAreWorking() throws Throwable {
		System.out.println("Verify the ascending sorting option are working");
		@SuppressWarnings("rawtypes")
		ArrayList entries = new ArrayList();

		String amount1 = getVisibleElementByXpath("//span[text()='$46,493']").getText().toString();
		String amount1Trim = amount1.replace("$", " ");
		String amount1Trim1 = amount1Trim.replace(",", " ");
		String amount1Trim2 = amount1Trim1.trim();
		double price1 = Double.valueOf(amount1Trim2).doubleValue();

		String amount2 = getVisibleElementByXpath("//span[text()='$48,493']").getText().toString();
		String amount2Trim = amount2.replace("$", " ");
		String amount2Trim1 = amount2Trim.replace(",", " ");
		String amount2Trim2 = amount2Trim1.trim();
		double price2 = Double.valueOf(amount2Trim2).doubleValue();

		String amount3 = getVisibleElementByXpath("//span[text()='$56,493']").getText().toString();
		String amount3Trim = amount3.replace("$", " ");
		String amount3Trim1 = amount3Trim.replace(",", " ");
		String amount3Trim2 = amount3Trim1.trim();
		double price3 = Double.valueOf(amount3Trim2).doubleValue();

		String amount4 = getVisibleElementByXpath("//span[text()='$40,693']").getText().toString();
		String amount4Trim = amount4.replace("$", " ");
		String amount4Trim1 = amount4Trim.replace(",", " ");
		String amount4Trim2 = amount4Trim1.trim();
		double price4 = Double.valueOf(amount4Trim2).doubleValue();

		String amount5 = getVisibleElementByXpath("//span[text()='$40,493']").getText().toString();
		String amount5Trim = amount5.replace("$", " ");
		String amount5Trim1 = amount5Trim.replace(",", " ");
		String amount5Trim2 = amount5Trim1.trim();
		double price5 = Double.valueOf(amount5Trim2).doubleValue();

		String amount6 = getVisibleElementByXpath("//span[text()='$46,493']").getText().toString();
		String amount6Trim = amount6.replace("$", " ");
		String amount6Trim1 = amount6Trim.replace(",", " ");
		String amount6Trim2 = amount6Trim1.trim();
		double price6 = Double.valueOf(amount6Trim2).doubleValue();

		entries.add(new AscendingPrice(price1));
		entries.add(new AscendingPrice(price2));
		entries.add(new AscendingPrice(price3));
		entries.add(new AscendingPrice(price4));
		entries.add(new AscendingPrice(price5));
		entries.add(new AscendingPrice(price6));

		Collections.sort(entries);
	}

	@SuppressWarnings("deprecation")
	@Then("^See the ascending orders in P3 Ford$")
	public void seeTheAscendingSortingOption(DataTable parameter) throws Throwable {
		System.out.println("See the ascending sorting option");
		List<List<String>> data = parameter.raw();
		List<String> allPriceValues_Text = new ArrayList<>();
		List<String> allPriceValues_Numbers = new ArrayList<String>();

		List<WebElement> allAUOffers_Webelement = driver.findElements(By.xpath("//span[@class='title']/following-sibling::p[1]"));
		for(int i=0;i<allAUOffers_Webelement.size();i++){
			String priceValues= allAUOffers_Webelement.get(i).getText();
			allPriceValues_Text.add(priceValues);
		}

		for(int j=0;j<allPriceValues_Text.size();j++) {
			allPriceValues_Numbers.add(allPriceValues_Text.get(j).substring(1,allPriceValues_Text.get(j).indexOf("Drive Away")-1));
			System.out.println(allPriceValues_Text.get(j).substring(1,allPriceValues_Text.get(j).indexOf("Drive Away")-1));
		}

		getVisibleElementByXpath("//span[text()='Sort by']").click();
		getVisibleElementByXpath("//span[text()='Sort by']").click();

		getVisibleElementByXpath("//a[text()='" + data.get(0).get(0) + "']").click();

		List<String> afterSortallPriceValues_Text = new ArrayList<>();
		List<String> afterSortallPriceValues_Numbers = new ArrayList<String>();
		List<WebElement> afterSortallAUOffers_Webelement = driver.findElements(By.xpath("//span[@class='title']/following-sibling::p[1]"));
		for(int i=0;i<allAUOffers_Webelement.size();i++){
			String afterSortPriceValues= afterSortallAUOffers_Webelement.get(i).getText();
			afterSortallPriceValues_Text.add(afterSortPriceValues);
		}

		for(int j=0;j<afterSortallPriceValues_Text.size();j++) {
			afterSortallPriceValues_Numbers.add(afterSortallPriceValues_Text.get(j).substring(1,afterSortallPriceValues_Text.get(j).indexOf("Drive Away")-1));
			System.out.println("After Sort "+afterSortallPriceValues_Text.get(j).substring(1,afterSortallPriceValues_Text.get(j).indexOf("Drive Away")-1));
		}

		boolean isEqual = afterSortallPriceValues_Numbers.equals(allPriceValues_Numbers);
		if (isEqual==false) {
			Assert.assertTrue("Total Service Price issue", true);
		} else {
			Assert.assertFalse("Latest Offer has sorted with Price Low To High", true);
		}

		//		getVisibleElementByXpath("//p[contains(text(),'$25,590')]");
		//		getVisibleElementByXpath("//p[contains(text(),'$27,690')]");
		//		getVisibleElementByXpath("//p[contains(text(),'$28,990')]");
		//		getVisibleElementByXpath("//p[contains(text(),'$31,190')]");
		//		getVisibleElementByXpath("//p[contains(text(),'$32,190')]");
		//		getVisibleElementByXpath("//p[contains(text(),'$32,490')]");
		//		getVisibleElementByXpath("//p[contains(text(),'$36,490')]");
	}

	@When("^Click on the filters on the left pane of the page in P3 Ford$")
	public void clickOnTheFiltersOnTheLeftPaneOfThePage(DataTable parameter) throws Throwable {
		System.out.println("Click on the filters on the left pane of the page");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//div[@class='offers-column search-filter-holder']/div/div/form/div[2]/div[3]/label/span[contains(text(),'" + data.get(0).get(0) + "')]").click();
	}

	@Then("^See all the filters are behaving correctly in P3 Ford$")
	public void seeAllTheFilersAreBehavingCorrectly() throws Throwable {
		System.out.println("See all the filters are behaving correctly");
		getVisibleElementByXpath("//span[@class='ng-scope']");
		getVisibleElementByXpath("//span[@class='reset ng-scope' and contains(text(),'Reset All')]");
		getVisibleElementByXpath("//p/span[@class='ng-binding' and contains(text(),'3')]");
		getVisibleElementByXpath("//p[contains(text(),'$27,690')]");
		getVisibleElementByXpath("//p[contains(text(),'$31,190')]");
		getVisibleElementByXpath("//p[contains(text(),'$32,190')]");

	}

	@When("^Click on any offer in P3 Ford$")
	public void clickOnAnyOffer(DataTable parameter) throws Throwable {
		System.out.println("Click on any offer");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//span[text()='" + data.get(0).get(0) + "']").click();

	}

	@Then("^See all components in offer details page load correctly in P3 Ford$")
	public void seeAllComponentsInOfferDetailsPageLoadCorrectly(DataTable parameter) throws Throwable {
		System.out.println("See all components in offer details page load correctly");
		List<List<String>> data = parameter.raw();
		Thread.sleep(5000);
		getVisibleElementByXpath("//b[text()='" + data.get(0).get(0) + "']");
		getVisibleElementByXpath("//span[text()='" + data.get(0).get(1) + "']");
		getVisibleElementByXpath("//b[text()='" + data.get(0).get(2) + "']");

		for (int i = 3; i <= 9; i++) {
			getVisibleElementByXpath("//li[contains(text(),'" + data.get(0).get(i) + "')]");
		}
	}

	@When("^Click on General Features$")
	public void ClickonGeneralFeatures() throws Throwable {
		System.out.println("Click on General Features");
		getVisibleElementByXpath("//p[contains(text(),'General Features')]").click();

	}


	@Then("^see all general features display correctly$")
	public void see_all_general_features_display_correctly(DataTable parameter) throws Throwable {
		System.out.println("see all general features display correctly");
		List<List<String>> data = parameter.raw();
		Thread.sleep(5000);
		getVisibleElementByXpath("//b[contains(text(),'" + data.get(0).get(0) + "')]");
		getVisibleElementByXpath("//span[contains(text(),'" + data.get(0).get(1) + "')]");
		getVisibleElementByXpath("//span[contains(text(),'" + data.get(0).get(2) + "')]");
		getVisibleElementByXpath("//span[contains(text(),'" + data.get(0).get(3) + "')]");
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(4) + "')]");
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(5) + "')]");
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(6) + "')]");
		getVisibleElementByXpath("//h2[contains(text(),'" + data.get(0).get(7) + "')]");
		getVisibleElementByXpath("//span[@class='postcode-span ng-binding ng-scope']");

	}


	@When("^Click on Fiesta Ambiente$")
	public void Click_on_Fiesta_Ambiente() throws Throwable {
		System.out.println("Click on Fiesta Ambiente");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//p[contains(text(),'Fiesta Ambiente')]").click();

	}
	@When("^Click on Vehicle Specifications$")
	public void Click_on_Vehicle_Specifications() throws Throwable {
		System.out.println("Click on Vehicle Specifications");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile' and contains(text(),'Vehicle Specifications')]").click();

	}


	@Then("^see all Fiesta Ambiente features display correctly$")
	public void see_all_Fiesta_Ambiente_features_display_correctly(DataTable parameter) throws Throwable {
		System.out.println("see all Fiesta Ambiente features display correctly");
		List<List<String>> data = parameter.raw();
		Thread.sleep(5000);
		getVisibleElementByXpath("//h4[contains(text(),'" + data.get(0).get(0) + "')]");
		getVisibleElementByXpath("//h4[contains(text(),'" + data.get(0).get(1) + "')]");
		getVisibleElementByXpath("//h4[contains(text(),'" + data.get(0).get(2) + "')]");

	}

	@Then("^View the Vehicle Specifications displayed$")
	public void view_the_vehicle_specifications_displayed(DataTable parameter) throws Throwable {
		System.out.println("View the Vehicle Specifications displayed");
		List<List<String>> data = parameter.raw();
		Thread.sleep(5000);
		getVisibleElementByXpath("//div[@class='drive-away-price ng-scope']/b[contains(text(),'" + data.get(0).get(0) + "')]");
		getVisibleElementByXpath("//p[contains(text(),'" + data.get(0).get(1) + "')]");
		getVisibleElementByXpath("//li[contains(text(),'" + data.get(0).get(2) + "')]");
		getVisibleElementByXpath("//li[contains(text(),'" + data.get(0).get(3) + "')]");
		getVisibleElementByXpath("//li[contains(text(),'" + data.get(0).get(4) + "')]");
		getVisibleElementByXpath("//li[contains(text(),'" + data.get(0).get(5) + "')]");
		getVisibleElementByXpath("//li[contains(text(),'" + data.get(0).get(6) + "')]");
		getVisibleElementByXpath("//li[contains(text(),'" + data.get(0).get(7) + "')]");
		getVisibleElementByXpath("//li[contains(text(),'" + data.get(0).get(8) + "')]");
		getVisibleElementByXpath("//li[contains(text(),'" + data.get(0).get(9) + "')]");

	}

	@Then("^See all components in Predelivery page loaded correctly in P3 Ford$")
	public void seeAllComponentsInPredeliveryPageLoadedCorrectly(DataTable parameter) throws Throwable {
		System.out.println("See all components in Predelivery page loaded correctly");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//img[@alt='logo']");
		for (int i = 0; i <= 4; i++) {
			getVisibleElementByXpath(
					"//div[@class='desktop hideForMobile']//span[contains(text(),'" + data.get(0).get(i) + "')]");
		}

		for (int j = 5; j <= 23; j++) {
			getVisibleElementByXpath("//span[text()='" + data.get(0).get(j) + "']");
		}

		for (int k = 24; k <= 32; k++) {
			getVisibleElementByXpath("//input[@name='" + data.get(0).get(k) + "']");
		}

		getVisibleElementByXpath("//p[text()='* REQUIRED FIELDS']");

		getVisibleElementByXpath("//div[@id='s2id_autogen1']/a");
		getVisibleElementByXpath("//div[@id='s2id_autogen3']/a");
		getVisibleElementByXpath("//div[@id='s2id_select-model']/a");
		getVisibleElementByXpath("//*[@id='s2id_select-series']/a");
		getVisibleElementByXpath("//div[@id='s2id_select-color']/a");
		getVisibleElementByXpath("//small[contains(text(),'Field must have 17 characters or leave field blank')]");
	}

	@When("^Fill in Pre Delivery form in P3 Ford$")
	public void FillInPreDeliveryForm(DataTable parameter) throws Throwable {
		System.out.println("Fill in Pre Delivery form");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//div[@id='s2id_autogen1']/a").click();
		getVisibleElementByXpath("(//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(0) + "')])[1]").click();
		getVisibleElementByXpath("//input[@name='Firstname']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='Lastname']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='Email']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='MobilePhone']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//input[@name='Address1']").sendKeys(data.get(0).get(5));
		getVisibleElementByXpath("//input[@name='Suburb']").sendKeys(data.get(0).get(6));
		getVisibleElementByXpath("//input[@name='Postcode']").sendKeys(data.get(0).get(7));
		getVisibleElementByXpath("//div[@id='s2id_autogen3']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(8) + "')]").click();
		getVisibleElementByXpath("//div[@id='s2id_select-model']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(9) + "')]").click();
		getVisibleElementByXpath("//div[@id='s2id_select-series']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(10) + "')]").click();
		getVisibleElementByXpath("//div[@id='s2id_select-color']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(11) + "')]").click();
		getVisibleElementByXpath("//input[@name='VIN']").sendKeys(data.get(0).get(12));
		Thread.sleep(5000);
		getVisibleElementByXpath("//div[@id='s2id_select-dealership']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(13) + "')]").click();
		getVisibleElementByXpath("//div[@id='s2id_select-dealer']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(14) + "')]").click();
		getVisibleElementByXpath("//span[contains(text(),'I have read and understood the')]").click();
	}

	@When("^Click on Next button in Pre Delivery form in P3 Ford$")
	public void clickOnNextButtonInPreDeliveryForm() throws Throwable {
		System.out.println("Click on Next button in Pre Delivery form");
		getVisibleElementByXpath("//button[@type='submit' and contains(text(),'Next')]").click();
	}

	@Then("^See all records in Pre Delivery page in P3 Ford$")
	public void seeAllRecordsInPreDeliveryPage(DataTable parameter) throws Throwable {
		System.out.println("See all records in Pre Delivery page");
		List<List<String>> data = parameter.raw();

		for (int i = 0; i <= 10; i++) {
			getVisibleElementByXpath(
					"//header[@class='predelivery-header']//span[text()='" + data.get(0).get(i) + "']");
		}

		for (int j = 11; j <= 18; j++) {
			getVisibleElementByXpath("//span[text()='" + data.get(0).get(j) + "']");
		}
	}

	@When("^Click on Save and Continue button in Pre Delivery page in P3 Ford$")
	public void clickOnSaveAndContinueButtonInPreDeliveryPage() throws Throwable {
		System.out.println("Click on Save and Continue button in Pre Delivery page");
		getVisibleElementByXpath("//button[@type='button' and contains(text(),'Save & Continue')]").click();
	}

	@Then("^Verify message is successfully saved in Pre Delivery page in P3 Ford$")
	public void verifyMessageIsSuccessfullySavedInPreDeliveryPage(DataTable parameter) throws Throwable {
		System.out.println("Verify message is successfully saved in Pre Delivery page");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//strong[contains(text(),'" + data.get(0).get(0) + "')]");
		Thread.sleep(5000);
	}

	@When("^Click on icon play button in Pre Delivery page in P3 Ford$")
	public void clickOnIconPlayButtonInPreDeliveryPage() throws Throwable {
		System.out.println("Click on icon play button in Pre Delivery page");
		getVisibleElementByXpath(
				"//div[@id='global-ux']/div[2]/div[2]/div/form/div/div[3]/div[2]/div/div/div[1]/div[4]/div/table/tbody/tr[2]/td[2]/a/i[@class='icon-play']")
		.click();
	}

	@Then("^See video frame in Pre Delivery page in P3 Ford$")
	public void seeVideoFrameInPreDeliveryPage() throws Throwable {
		System.out.println("See video frame in Pre Delivery page");
		getVisibleElementByXpath("//object[@id='bcExperienceObj0']");
		getVisibleElementByXpath(
				"//div[@class='overlay-container visible overlay-video']//i[@class='icon-plus icon-x']");
	}

	@And("^Click on Close button in video frame in P3 Ford$")
	public void clickOnCloseButtonInVideoFrame() throws Throwable {
		System.out.println("Click on Close button in video frame");
		getVisibleElementByXpath(
				"//div[@class='overlay-container visible overlay-video']//i[@class='icon-plus icon-x']").click();
	}

	@When("^Click on Step 4 in Pre Delivery page in P3 Ford$")
	public void clickOnStep4InPreDeliveryPage() throws Throwable {
		System.out.println("Click on Step 4 in Pre Delivery page");
		getVisibleElementByXpath("//span[text()='Step 4']").click();
	}

	@Then("^See Personalise your Ford form in Pre Delivery page in P3 Ford$")
	public void seePersonaliseYourFordFormInPreDeliveryPage(DataTable parameter) throws Throwable {
		System.out.println("See Personalise your Ford form in Pre Delivery page");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//span[text()='PERSONALISE FOR PICK-UP']");
		for (int i = 0; i <= 7; i++) {
			getVisibleElementByXpath("//h5[text()='" + data.get(0).get(i) + "']");
		}
		getVisibleElementByXpath("//input[@name='P-MOBILE-PHONE']");
	}

	@When("^Click on Next button in Personalise for pick up section in P3 Ford$")
	public void clickOnNextButtonInPreDeliveryPage() throws Throwable {
		System.out.println("Click on Next button in Personalise for pick up section");
		getVisibleElementByXpath(
				"//div[@class='form predelivery-step is-active-step']//button[contains(text(),'Next')]").click();
	}

	@Then("^See all records after hitting Next in Step 4 in P3 Ford$")
	public void seeAllRecordsAfterHittingNextInStep4(DataTable parameter) throws Throwable {
		System.out.println("See all records after hitting Next in Step 4");
		List<List<String>> data = parameter.raw();

		for (int i = 0; i <= 31; i++) {
			getVisibleElementByXpath("//span[text()='" + data.get(0).get(i) + "']");
		}
		getVisibleElementByXpath("//input[@name='recaptcha' or @name='INDIVIDUAL_CUST_SEX_CODE']");
	}

	@When("^Click on View Summary as PDF in Summary page in P3 Ford$")
	public void clickOnViewSummaryAsPdfInSummaryPage() throws Throwable {
		System.out.println("Click on View Summary as PDF in Summary page");
		getVisibleElementByXpath("//button[@type='button' and contains(text(),'View As PDF')]").click();
		Thread.sleep(5000);
	}

	@When("^Input into captcha field in Summary page in P3 Ford$")
	public void inputIntoCaptchaFieldInSummaryPage(DataTable parameter) throws Throwable {
		System.out.println("Input into captcha field in Summary page");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//input[@name='recaptcha' or @name='INDIVIDUAL_CUST_SEX_CODE']").sendKeys(EnterCaptcha());
	}

	@Then("^See Confirmation page displayed in P3 Ford$")
	public void seeConfirmationPageDisplayed(DataTable parameter) throws Throwable {
		System.out.println("See Confirmation page displayed");
		List<List<String>> data = parameter.raw();
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==false) {
			Thread.sleep(40000);
			getVisibleElementByXpath("//img[@alt='logo']");

			for (int i = 0; i <= 2; i++) {
				getVisibleElementByXpath(
						"//div[@class='desktop hideForMobile']//span[text()='" + data.get(0).get(i) + "']");
			}

			for (int j = 3; j <= 6; j++) {
				getVisibleElementByXpath("//strong[contains(text(),'" + data.get(0).get(j) + "')]");
			}
			getVisibleElementByXpath("//a[text()='phone compatibility']");
			getVisibleElementByXpath("//a[contains(text(),'Android')]");
			getVisibleElementByXpath("//a[text()='iOS']");
			getVisibleElementByXpath("//div[@class='desktop hideForMobile']//a[text()='Close and Return to Ford Home']");
		}
	}

	@When("^Click on Submit To Dealer in Summary page in P3 Ford$")
	public void clickOnSubmitToDealerInSummaryPage() throws Throwable {
		System.out.println("Click on Submit To Dealer in Summary page");
		getVisibleElementByXpath("//button[@type='button' and contains(text(),'Submit To Dealer')]").click();
	}

	@Then("^See all components loaded correctly in Offers page in P3 Ford$")
	public void seeAllComponentsLoadedCorrectlyInOffersPage(DataTable parameter) throws Throwable {
		System.out.println("See all components loaded correctly in Offers page");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//img[@alt='logo']");

		for (int i = 0; i <= 9; i++) {
			getVisibleElementByXpath("//span[text()='" + data.get(0).get(i) + "']");
		}

		for (int j = 10; j <= 15; j++) {
			getVisibleElementByXpath(
					"//div[contains(@class,'desktop hideForMobile')]//a[text()='" + data.get(0).get(j) + "']");
		}

		getVisibleElementByXpath("//div[contains(@class,'desktop hideForMobile')]//strong[text()='FIESTA']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']//a[contains(text(),'2017')]/strong[text()='Fiesta']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']//a[contains(text(),'2017')]/strong[text()='Mustang']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']//a[contains(text(),'2016')]/strong[text()='Focus']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']//a[contains(text(),'2016')]/strong[text()='Fiesta']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']//a[contains(text(),'New')]/strong[text()='Fiesta']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']//a[contains(text(),'New')]/strong[text()='Focus']");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//strong[text()='Figo']");
		getVisibleElementByXpath("//h3[text()='Disclosures']");
	}

	@When("^Click on Detalhes da Oferta for first vehicle$")
	public void redirectToPasswordResetLink() throws Throwable {
		System.out.println("Click on Detalhes da Oferta for first vehicle");
		Thread.sleep(30000);
		getVisibleElementByXpath("//a[text()='Detalhes da Oferta']").click();
	}

	@Then("^See all components loaded correctly in Offer Details page in P3 Ford$")
	public void seeAllComponentsLoadedCorrectlyInOfferDetailsPage(DataTable parameter) throws Throwable {
		System.out.println("See all components loaded correctly in Offer Details page");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//a[text()='Request a Quote']");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']/h2[text()='Awards']");
		for (int i = 0; i <= 2; i++) {
			getVisibleElementByXpath("//div[contains(@class,'hideForMobile')]/h3[contains(text(),'" + data.get(0).get(i) + "')]");
		}
	}

	@When("^Redirect to link \"(.*?)\" on P3$")
	public void zRedirectLink(String link) throws Throwable {
		System.out.println("Redirect to link");
		Thread.sleep(10000);
		driver.get(getProfileURL(link));
	}

	@And("^Click on Activation Email on MailInator page on P3$")
	public void clickOnActivationEmailOnMailInatorPage() throws Throwable {
		System.out.println("Click on Activation Email on MailInator page");
		driver.navigate().refresh();
		do {
			driver.navigate().refresh();
			Thread.sleep(5000);
		} while (driver.findElements(By.cssSelector("div.all_message-min-check-container")).size() == 0);// (By.cssSelector("div.col-lg-1.col-md-1.col-sm-1.hidden-xs")).size()==0);
		driver.findElement(By.xpath("//ul[@id='inboxpane']/li//div[contains(text(),'moments ago')]")).click();	
	}


	@And("^Click on Email on MailInator page$")
	public void Click_on_Email_on_MailInator_page() throws Throwable {
		System.out.println("Click on Email on MailInator page");
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==false) {
			int recsc=0;
			do {
				if(recsc==45) {
					driver.navigate().refresh();
				}
				Thread.sleep(2000);
				recsc++;
				if (isAlertPresent()) {
					driver.switchTo().alert();
					driver.switchTo().alert().accept();
					driver.switchTo().defaultContent();
				}
			} while (driver.findElements(By.cssSelector("div.all_message-min-check-container")).size() == 0 && recsc<60);// (By.cssSelector("div.col-lg-1.col-md-1.col-sm-1.hidden-xs")).size()==0);

			do {
				Thread.sleep(2000);
				recsc++;
			} while (driver.findElements(By.xpath("//ul[@id='inboxpane']/li//div[contains(text(),'moments ago') or contains(text(),'minute ago')]")).size() == 0 && recsc<60);// (By.cssSelector("div.col-lg-1.col-md-1.col-sm-1.hidden-xs")).size()==0);

			driver.findElement(By.xpath("//ul[@id='inboxpane']/li//div[contains(text(),'moments ago') or contains(text(),'minute ago')]")).click();
		}
	}

	@When("^Verify Ford Delivery Checklist mail$")
	public void Verify_Ford_Delivery_Checklist_mail() throws Throwable {
		System.out.println("Verify Ford Delivery Checklist mail");
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==false) {
			Thread.sleep(10000);
			try {
				driver.switchTo().frame(driver.findElement(By.cssSelector("#publicshowmaildivcontent")))
				.findElement(By.tagName("body"));
			} catch (Exception e) {
				driver.switchTo().frame(driver.findElement(By.id("msg_body")));
			}
			WebElement link = getVisibleElementByXpath("//td[contains(text(),'Your Ford Delivery Checklist')]");
			driver.switchTo().defaultContent();
		}
	}


	@Then("^It navigate you on the latest offers page$")
	public void It_navigate_you_on_the_latest_offers_page() throws Throwable {
		System.out.println("It navigate you on the latest offers page");
		getVisibleElementByXpath("//div[contains(@class,'buildPrice') and contains(@class,'section')]//following-sibling::div[contains(@class,'richtext') and contains(@class,'section')]//span[contains(text(),'Latest Offers')]");
	}

	@And("^B&P configuration should show the vehicle models of selected nameplate that has laters offers$")
	public void BP_configuration_should_show_the_vehicle_models_of_selected_nameplate_that_has_laters_offers() throws Throwable {
		System.out.println("B&P configuration should show the vehicle models of selected nameplate that has laters offers");
		getVisibleElementByXpath("//div[contains(@id,'model') and contains(@class,'secondary-nav-controls') and contains(@class,'active')]//a[contains(text(),'Latest Offer') and not(contains(@class,'hide'))]");
	}

	@When("^Click on Latest offer button on B&P config page$")
	public void Click_on_Latest_offer_button_on_BP_config_page() throws Throwable {
		System.out.println("Click on Latest offer button on B&P config page");
		getVisibleElementByXpath("//div[contains(@id,'model') and contains(@class,'secondary-nav-controls') and contains(@class,'active')]//a[contains(text(),'Latest Offer') and not(contains(@class,'hide'))]").click();	
	}

	@Then("^User directed to the respective configuration page of B&P More Offers Section$")
	public void User_directed_to_the_respective_configuration_page_of_BP_More_Offers_Section() throws Throwable {
		System.out.println("User directed to the respective configuration page of B&P More Offers Section");
		getVisibleElementByXpath("//*[contains(text(),'Latest Offers')]");
		getVisibleElementByXpath("//*[contains(text(),'More Offers')]");
	}


	@When("^Fill in NZ Pre Delivery form in P3 Ford$")
	public void FillInNZPreDeliveryForm(DataTable parameter) throws Throwable {
		System.out.println("Fill in NZ Pre Delivery form");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//div[@id='s2id_autogen1']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(0) + "')]").click();
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_FIRST_NAME']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//div[@class='Last1 textfield']//input[@type='text']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//div[@class='textfield Email1']//input[@type='text']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//div[@class='Number1 textfield']//input[@type='text']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//div[@id='s2id_select-model']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(5) + "')]").click();
		getVisibleElementByXpath("//div[@id='s2id_select-series']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(6) + "')]").click();
		getVisibleElementByXpath("//div[@id='s2id_select-color']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(7) + "')]").click();
		getVisibleElementByXpath("//div[@id='s2id_select-dealership']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(8) + "')]").click();
		getVisibleElementByXpath("//div[@id='s2id_select-dealer']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(9) + "')]").click();
		getVisibleElementByXpath("//div[@id='s2id_select-dealerAdreess']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(10) + "')]").click();

	}
	@Then("^See all records in NZ Pre Delivery page in P3 Ford$")
	public void seeAllRecordsInNZPreDeliveryPage(DataTable parameter) throws Throwable {
		System.out.println("See all records in NZ Pre Delivery page");
		List<List<String>> data = parameter.raw();

		for (int i = 0; i <= 13; i++) {
			getVisibleElementByXpath(
					"//*[text()='" + data.get(0).get(i) + "']");
		}

		for (int j = 14; j <= 21; j++) {
			getVisibleElementByXpath("//span[text()='" + data.get(0).get(j) + "']");
		}
	}
	@When("^Click on Next button in NZ Pre Delivery page in P3 Ford$")
	public void clickOnNextButtonInNZPreDeliveryPage() throws Throwable {
		System.out.println("Click on Next button in NZ Pre Delivery page");
		getVisibleElementByXpath("//div[@class='predelivery-step is-active-step']//button[@type='button'][contains(text(),'Next')]").click();
	}

	@Then("^See Personalise your Ford form in NZ Pre Delivery page in P3 Ford$")
	public void seePersonaliseYourFordFormInNZPreDeliveryPage(DataTable parameter) throws Throwable {
		System.out.println("See Personalise your Ford form in NZ Pre Delivery page");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//strong[contains(text(),'PREFERRED DELIVERY TIME')]");
		for (int i = 0; i <= 5; i++) {
			getVisibleElementByXpath("//strong[text()='" + data.get(0).get(i) + "']");
		}
	}
	@When("^Click on Next button in Personalise for pick up section in NZ P3 Ford$")
	public void clickOnNextButtonInNZPreDeliveryPageforPickUpSection() throws Throwable {
		System.out.println("Click on Next button in Personalise for pick up section in NZ P3 Ford");
		getVisibleElementByXpath("//div[@class='form predelivery-step is-active-step']//button[@type='button'][contains(text(),'Next')]").click();
	}
	@Then("^Select the Preferred Delivery Time in NZ P3 Ford$")
	public void selectthePreferredTime(DataTable parameter) throws Throwable {
		System.out.println("Select the Preferred Delivery Time in NZ P3 Ford");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("/html[1]/body[1]/div[3]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[3]/div[5]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/label[1]/div[1]/a[1]").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(0) + "')]").click();
		getVisibleElementByXpath("/html[1]/body[1]/div[3]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[3]/div[5]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/label[1]/div[1]/a[1]").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(1) + "')]").click();		
		getVisibleElementByXpath("/html[1]/body[1]/div[3]/div[2]/div[2]/div[1]/div[1]/form[1]/div[1]/div[3]/div[5]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/label[1]/div[1]/a[1]").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(2) + "')]").click();
	}
	@When("^Click on Email PDF in Summary page in P3 Ford$")
	public void clickOnEmailPDFInSummaryPage() throws Throwable {
		System.out.println("Click on Email PDF in Summary page in P3 Ford");
		getVisibleElementByXpath("//button[@type='button' and contains(text(),'Email PDF')]").click();
		Thread.sleep(3000);
		getVisibleElementByXpath("//div[@id='emailpdflayer']//input[1]").sendKeys(UniqueKey+"mailinator.com");
		Thread.sleep(5000);
	}

	@Then("^See all records after hitting Next in Step 4 in NZ P3 Ford$")
	public void seeAllRecordsAfterHittingNextInStep4InNZ(DataTable parameter) throws Throwable {
		System.out.println("See all records after hitting Next in Step 4 in NZ");
		List<List<String>> data = parameter.raw();

		for (int i = 0; i <= 17; i++) {
			getVisibleElementByXpath("//*[text()='" + data.get(0).get(i) + "']");
		}
	}
}