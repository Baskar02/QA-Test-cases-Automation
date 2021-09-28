package com.ford.automation.p2_general;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.AssertFalse;

import org.opencv.core.TermCriteria;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.ford.automation.base.BaseTest;
import com.ford.automation.driverFactory.Browser;
import com.ford.automation.qa.BrowserHelper;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.ford.automation.driverFactory.DriverFactory;
import com.mkyong.rest.JSONService;
import com.mkyong.rest.Product;

import config.Configuration;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import net.sourceforge.htmlunit.corejs.javascript.regexp.SubString;

@SuppressWarnings("deprecation")
public class P2General extends BaseTest {
	// public static String UniqueKey=null,Password=null,phn=null;
	//	public static String UniqueKey = "180917105610", Password = null, phn = "01809171056";
	public static int getVehiclePrice=0, addConfigPrice=0; 
	public List<String> VehiclBfrSort = new ArrayList<String>();
	public List<String> VehiclAftrSort = new ArrayList<String>();
	protected int modelPrice=0,downpayment=0,amtfinanced=0,downpaymRatio=0,TempValue=0,paympkg=0,monthlyPay=0;
	protected long Bs=0,Dollar=0;
	protected String term=null,tempStr=null;

	@Given("^Open firefox browser on P2$")
	public void openFirefoxBrowser() throws Throwable {
		System.out.println("Open FireFox browser");
		System.setProperty("webdriver.gecko.driver", Configuration.PATH_TO_GECKO_DRIVER);
		driver = new FirefoxDriver();
	}

	@Given("^Open chrome browser on P2$")
	public void openChromeBrowser() throws Throwable {
		System.out.println("Open Chrome browser");
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.open('','testwindow','width=400,height=200')");
		driver.close();
		driver.switchTo().window("testwindow");
		js.executeScript("window.moveTo(0,0);");
		js.executeScript("window.resizeTo(1450,1000);");
	}

	@Given("^Open ie browser on P2$")
	public void openInternetExplorerBrowser() throws Throwable {
		System.out.println("Open Internet Explorer browser");
		System.setProperty("webdriver.ie.driver", Configuration.PATH_TO_IE_DRIVER);
		driver = new InternetExplorerDriver();
	}

	@Then("^See the latest version of JS \"(.*?)\" on P2$")
	public void seeTheLatestVersionOfJS(String version) throws Throwable {
		System.out.println("See the latest version of JS");
		String versionStringToVerify = "var aemGuxfoapUiReleaseVersion = '" + version + "'";

		Boolean correctVersion = driver.getPageSource().contains(versionStringToVerify);
		if (!correctVersion) {
			throw new Exception("Incorrect version!");
		}
	}

	@Given("^Generate unique string$")
	public void generate_unique_string() throws Throwable {
		System.out.println("Generate unique number");

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
		String strDate = sdf.format(cal.getTime());
		UniqueKey = strDate;
		phn = "0"+strDate.substring(0, 10);
	}

	@When("^Maximize browser and enter link \"(.*?)\" on P2$")
	public void openTestLink(String link) throws Throwable {
		System.out.println("Maximize browser and enter link");
		driver.manage().window().maximize();
		driver.get(getProfileURL(link));		
	}

	@And("^Enter link \"(.*?)\" on browser \"(.*?)\" on P2$")
	public void enterLink(String url, Browser browser) throws Throwable {
		System.out.println("Enter link on browser stack");
		try {
			DriverFactory driverFactory = new DriverFactory();
			WebDriver driver = driverFactory.createRemoteWebDriver(browser);
			// Launch website
			driver.navigate().to(url);
			if (!BrowserHelper.checkMobile(browser)) {
				// Maximize the browser
				driver.manage().window().maximize();
			}

			System.out.println(driver.getTitle());
			Thread.sleep(5000);
			driver.get(getProfileURL(url));
			Thread.sleep(5000);
			System.out.println("Redirect to link");
			driver.findElement(By.cssSelector("a.not-loggedin i.icon-profile")).click();
			System.out.println("Click on Login link");
			driver.quit();

		} catch (WebDriverException e) {
			System.out.println("Fail in " + browser.toString());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Fail in " + browser.toString());
			e.printStackTrace();
		}

	}

	@And("^Input email to inbox field$")
	public void inputEmailToInboxField(DataTable email) throws Throwable {
		System.out.println("Input email to Inbox Field");
		getVisibleElementByXpath("//*[@id='addOverlay']").sendKeys(UniqueKey);
	}

	@And("^Click on Go button$")
	public void clickOnGoButton() throws Throwable {
		System.out.println("Click on Go button");
		getVisibleElementByXpath("//button[contains(text(), 'GO')]").click();
		Thread.sleep(5000);
	}

	@Then("^See page redirected to correct link \"(.*?)\" on P2$")
	public void seeThePageRedirectedToCorrectLink(String link) throws Throwable {
		System.out.println("See page redirected to correct link");
		if (link.contains("REPLACETOUNIQUE")) {
			verifyRedirecToCorrectLink(link.replace("REPLACETOUNIQUE", UniqueKey));
		} else {
			verifyRedirecToCorrectLink(link);
		}
		driver.switchTo().defaultContent();
	}

	@When("^Redirect to link \"(.*?)\" on P2$")
	public void z(String link) throws Throwable {
		System.out.println("Redirect to link");
		Thread.sleep(5000);
		driver.get(getProfileURL(link.replace("REPLACETOUNIQUE", UniqueKey)));
		Thread.sleep(5000);
	}

	@When("^Redirect to new link \"(.*?)\" on P2$")
	public void redirect(String link) throws Throwable {
		System.out.println("Redirect to link");
		Thread.sleep(10000);
		driver.get(getProfileURL(link));
	}

	@When("^Redirect to link \"(.*?)\" and check if the link is broken on P2$")
	public void redirectToLinkAndCheckIfTheLinkIsBroken(String link) throws Throwable {
		System.out.println("Redirect to link");
		Thread.sleep(10000);
		driver.get(getProfileURL(link));
		List<WebElement> allImages = findAllLinks(driver);
		System.out.println("Total number of elements found: " + allImages.size());

		for (WebElement element : allImages) {
			try {
				System.out.println("URL: " + element.getAttribute("href") + " returned "
						+ isLinkBroken(new URL(element.getAttribute("href"))));
				// System.out.println("URL: " + element.getAttribute("outerhtml")+ " returned "
				// + isLinkBroken(new URL(element.getAttribute("href"))));
			} catch (Exception exp) {
				System.out.println(
						"At " + element.getAttribute("innerHTML") + " Exception occured -&gt; " + exp.getMessage());
			}
		}
	}

	@When("^Click on Login link on P2$")
	public void clickOnLoginLink() throws Throwable {
		System.out.println("Click on Login link");
		getVisibleElementByXpath("//span[contains(text(), 'Log')]//ancestor::a").click();
	}

	@When("^Click on Login link on P2 Production$")
	public void clickOnLoginLinkInProduction() throws Throwable {
		System.out.println("Click on Login link");
		getVisibleElementByXpath("//span[contains(text(), 'Sign In')]").click();
	}

	@When("^Click on Login link on P2 brandap$")
	public void clickOnLoginLinkBrandAp() throws Throwable {
		System.out.println("Click on Login link");
		try {
			getVisibleElementByXpath(
					"//span[contains(text(), 'Log In') or contains(text(), 'Sign In') or contains(text(),'Login')]")
			.click();
		} catch (Exception e) {
			getVisibleElementByXpath("//a[contains(text(), 'Log in')]").click();
		}
	}

	@Then("^See the Login overlay on P2$")
	public void seeTheLoginOverlay() throws Throwable {
		System.out.println("See the Login overlay");
		waitTillElementExist("//input[@name='userName']");
		getVisibleElementByXpath("//input[@name='userName']");
	}

	@Then("^Mandatory exceptions displayed for username and password$")
	public void Verifyexception(DataTable Param) throws Throwable {
		System.out.println("Mandatory exceptions displayed for username and password");
		List<List<String>> data = Param.raw();
		Thread.sleep(10000);
		int ElementCount = driver.findElements(By.xpath("//small[@class='error' and text()!='']")).size();
		if (ElementCount > 0) {
			System.out.println("Mandatory Userid and Password exception message exist");
		} else {
			System.out.println("Mandatory Userid and Password exception message does not exist.");
		}
	}

	@Then("^Click on Forgot your Password$")
	public void ClickForgotPassword() throws Throwable {
		System.out.println("Click on Forgot your Password");
		getVisibleElementByXpath("//a[text()='Forgot your Password?' or contains(@href,'forgot-password.html')]").click();
	}

	@Then("^Click on Forgot your username$")
	public void ClickForgotUsername() throws Throwable {
		System.out.println("Click on Forgot your username");
		getVisibleElementByXpath("//a[text()='Forgot your username?' or contains(@href,'forgot-username.html')]").click();
	}

	@Then("^See forgot overlay opened$")
	public void See_forgot_overlay_opened() throws Throwable {
		System.out.println("See forgot overlay opened");
		waitTillElemVisiblebyXpath("//input[@name='PRIMARY_EMAIL']", 240);
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']");
	}

	@Then("^Enter email id$")
	public void Enter_email_id(DataTable Param) throws Throwable {
		System.out.println("Enter email id");
		List<List<String>> data = Param.raw();
		if (data.get(0).get(0).isEmpty()) {
			getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(UniqueKey + "@mailinator.com");
		} else {
			getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(0));
			if(data.get(0).get(0).contains("@")) {
				UniqueKey=data.get(0).get(0).split("@")[0];
			}
		}
	}

	@Then("^Enter email id and Username$")
	public void Enter_email_id_and_Username(DataTable Param) throws Throwable {
		System.out.println("Enter email id and Username");
		List<List<String>> data = Param.raw();
		if (data.get(0).get(0).isEmpty()) {
			getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(UniqueKey + "@mailinator.com");
		} else {
			getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(0));
		}

		if (data.get(0).get(1).isEmpty()) {
			getVisibleElementByXpath("//input[@name='userName']").sendKeys(UniqueKey);
		} else {
			getVisibleElementByXpath("//input[@name='userName']").sendKeys(data.get(0).get(1));
			UniqueKey=data.get(0).get(1);
		}
	}

	@Then("^Successful message displayed$")
	public void Successful_message_displayed() throws Throwable {
		System.out.println("Successful message displayed");
		waitTillElemVisiblebyXpath("//div[@class='desktop hideForMobile']//*[text()='Successful' or contains(text(),'发送用户名邮件成功') or contains(text(),'密码重置申请已成功') or contains(text(),'重新设置密码成功')]", 240);
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//*[text()='Successful' or contains(text(),'发送用户名邮件成功') or contains(text(),'密码重置申请已成功') or contains(text(),'重新设置密码成功')]");
		driver.switchTo().defaultContent();
	}

	@When("^click on Submit button$")
	public void ClickSubmit() throws Throwable {
		System.out.println("click on Submit button");
		waitTillElemVisiblebyXpath("//button[text()='Submit' or contains(text(),'提交')]", 240);
		getVisibleElementByXpath("//button[text()='Submit' or contains(text(),'提交')]").click();
	}

	@When("^Click on Register link on Login overlay on P2$")
	public void clickOnRegisterLink() throws Throwable {
		System.out.println("Click on Register link on Login overlay");
		getVisibleElementByXpath("//div[@class='bottom-login']//a[contains(@href,'register.html')]").click();
	}

	@When("^Click on Register link on Login overlay on China$")
	public void clickOnRegisterBtn() throws Throwable {
		System.out.println("Click on Register link on Login overlay on China");
		getVisibleElementByXpath("//div[@class='bottom-login']//a[contains(@href,'register.html')]").click();
	}

	@Then("^See the Register overlay on P2$")
	public void seeTheRegisterOverlay() throws Throwable {
		System.out.println("See the Register overlay");
		waitTillElementExist("//input[@name='confirmPassword']");
		getVisibleElementByXpath("//input[@name='confirmPassword']");
	}

	@Then("^See the Register overlay on P2 brandap$")
	public void seeTheRegisterOverlayBrandap() throws Throwable {
		System.out.println("See the Register overlay");
		waitTillElementExist("//input[@name='confirmPassword']");
		getVisibleElementByXpath("//input[@name='confirmPassword']");
	}

	@When("^Enter credentials to sign in on browser stack on P2$")
	public void enterCredentialsToSignInOnBrowserStack(DataTable userCredentials) throws Throwable {
		System.out.println("Enter credentials to sign in");
		List<List<String>> data = userCredentials.raw();
		getVisibleElementByXpath("//*[@id='user_email_login']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//*[@id='user_password']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//*[@id='user_submit']").click();
		getVisibleElementByXpath("//a[contains(text(),'Enable Local Testing')]").click();
	}

	@And("^Select IE8 on browser stack on P2$")
	public void selectInternetExplorerOnBrowserStack() throws Throwable {
		System.out.println("Select IE8 on browser stack");
		getVisibleElementByXpath("//a[contains(text(),'8')]").click();
	}

	@When("^Enter credentials to register on P2$")
	public void enterCredentialsToRegister(DataTable userCredentials) throws Throwable {
		System.out.println("Enter credentials to register");
		List<List<String>> data = userCredentials.raw();
		Password = data.get(0).get(1);
		getVisibleElementByXpath("//input[@name='userName' or @name='vehiclecategory']").sendKeys(UniqueKey);
		System.out.println("New user id is:-" + UniqueKey);
		getVisibleElementByXpath("//input[@type='password' and @name='INDIVIDUAL_CUST_FIRST_NAME' or @name='password']")
		.sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='confirmPassword']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='email']").sendKeys(UniqueKey + "@mailinator.com");
		System.out.println("New user email id is:-" + UniqueKey + "@mailinator.com");
		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(5));
	}

	@When("^Enter credentials to register on China P2$")
	public void enterCredentialsToRegisterChina(DataTable userCredentials) throws Throwable {
		System.out.println("Enter credentials to register on China P2");
		List<List<String>> data = userCredentials.raw();
		Password = data.get(0).get(1);
		getVisibleElementByXpath("//input[@name='userName' or @name='vehiclecategory']").sendKeys(UniqueKey);
		System.out.println("New user id is:-" + UniqueKey);
		getVisibleElementByXpath("//input[@type='password' and @name='INDIVIDUAL_CUST_FIRST_NAME' or @name='password']")
		.sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='confirmPassword']").sendKeys(Password);
		getVisibleElementByXpath("//input[@name='email']").sendKeys(UniqueKey + "@mailinator.com");
		System.out.println("New user email id is:-" + UniqueKey + "@mailinator.com");
		Select seltitle = new Select(getVisibleElementByXpath("//Select[@name='title']"));
		seltitle.selectByIndex(0);
		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(5));
	}

	@When("^Enter credentials to register on P2 brandap$")
	public void enterCredentialsToRegisterBrandap(DataTable userCredentials) throws Throwable {
		System.out.println("Enter credentials to register");
		List<List<String>> data = userCredentials.raw();
		getVisibleElementByXpath("//input[@name='userName']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='password']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='confirmPassword']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='email']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(5));
	}

	@And("^Click on Next button on P2$")
	public void clickOnNextButton() throws Throwable {
		System.out.println("Click on Next button");
		getVisibleElementByXpath("//button[contains(text(),'Next') or contains(text(),'下一页')]").click();
	}

	@And("^Select vehicle credentials on P2$")
	public void selectVehicleCredentials(DataTable vehicleCredentials) throws Throwable {
		System.out.println("Click on select Vehicle");
		List<List<String>> data = vehicleCredentials.raw();
		waitTillElemVisiblebyXpath("//input[@name='vehicleName']", 240);
		getVisibleElementByXpath("//input[@name='vehicleName']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='vincode']").sendKeys(data.get(0).get(1));
	}

	@And("^Select vehicle credentials on P2 QA$")
	public void selectVehicleCredentialsInQa(DataTable vehicleCredentials) throws Throwable {
		System.out.println("Click on select Vehicle");
		List<List<String>> data = vehicleCredentials.raw();
		getVisibleElementByXpath("//input[@name='vehicleName']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='vincode']").sendKeys(data.get(0).get(1));
	}

	@And("^Fill in Vehicle Info credentials on china P2$")
	public void fillInVehicleInfochina(DataTable vehicleInfoCredentials) throws Throwable {
		System.out.println("Fill in Vehicle Info credentials on china P2");
		List<List<String>> data = vehicleInfoCredentials.raw();
		waitTillElemVisiblebyXpath("//input[@name='postCode']", 240);
		if(!data.get(0).get(0).isEmpty()) {
			getVisibleElementByXpath("//span[contains(text(),'Miss') or contains(text(),'安徽')]").click();
			getVisibleElementByXpath("//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(0) + "')]").click();
		}
		getVisibleElementByXpath("//input[@name='postCode']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='mobileNumber']").sendKeys(phn);
		getVisibleElementByXpath("//label[@for='terms_and_conditions']").click();
		getVisibleElementByXpath("//label[@for='user_legal_agreement']").click();
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys(EnterCaptcha());
	}

	@And("^Fill in Vehicle Info credentials on P2$")
	public void fillInVehicleInfo(DataTable vehicleInfoCredentials) throws Throwable {
		System.out.println("Fill in Vehicle Infomation");
		List<List<String>> data = vehicleInfoCredentials.raw();

		waitTillElemVisiblebyXpath("//input[@name='nickName']", 240);
		if(!data.get(0).get(0).isEmpty()) {
			getVisibleElementByXpath("//span[contains(text(),'Miss') or contains(text(),'安徽')]").click();
			getVisibleElementByXpath(
					"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(0) + "')]").click();
		}
		getVisibleElementByXpath("//input[@name='nickName']").sendKeys(data.get(0).get(1));
		if(!data.get(0).get(2).isEmpty()) {
			getVisibleElementByXpath("//span[contains(text(), 'Select State')]").click();
			getVisibleElementByXpath(
					"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(2) + "')]").click();
		}
		getVisibleElementByXpath("//input[@name='postCode']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='mobileNumber' or @name='homePhoneNumber']").sendKeys(phn);
		getVisibleElementByXpath("//input[@name='workphoneNumber' or @name='workPhoneNumber']").sendKeys(phn);
		getVisibleElementByXpath("//span[contains(text(), 'I have read and understood')]").click();
		getVisibleElementByXpath("//span[contains(text(), 'I agree to the')]").click();

		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys(EnterCaptcha());
	}

	@And("^Fill in Vehicle Info credentials for AU on P2$")
	public void fillInVehicleInfoAU(DataTable vehicleInfoCredentials) throws Throwable {
		System.out.println("Fill in Vehicle Infomation on AU");
		List<List<String>> data = vehicleInfoCredentials.raw();
		getVisibleElementByXpath("//input[@name='postCode']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='mobileNumber' or @name='homePhoneNumber']").sendKeys(phn);
		getVisibleElementByXpath("//span[contains(text(), 'I read and understood')]").click();
		getVisibleElementByXpath("//span[contains(text(), 'I agree to the')]").click();
		getVisibleElementByXpath("//span[contains(text(), 'acknowledge')]").click();
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys(data.get(0).get(2));
	}



	@And("^Fill in Vehicle Info credentials on P2 QA$")
	public void fillInVehicleInfoInQa(DataTable vehicleInfoCredentials) throws Throwable {
		System.out.println("Fill in Vehicle Infomation");
		List<List<String>> data = vehicleInfoCredentials.raw();
		getVisibleElementByXpath("//span[contains(text(),'Miss')]").click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(0) + "')]").click();
		getVisibleElementByXpath("//span[contains(text(), 'Select State')]").click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(1) + "')]").click();
		getVisibleElementByXpath("//input[@name='postCode']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='mobileNumber']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='workphoneNumber']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//span[contains(text(), 'I have read and understood')]").click();
		getVisibleElementByXpath("//span[contains(text(), 'I agree to the')]").click();
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys(data.get(0).get(5));
	}

	@And("^Fill in correct captcha on P2$")
	public void fillInCorrectCaptcha(DataTable vehicleInfoCredentials) throws Throwable {
		System.out.println("Fill in Correct capcha");
		List<List<String>> data = vehicleInfoCredentials.raw();
		getVisibleElementByXpath("//input[@name='recaptcha']").clear();
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys(data.get(0).get(0));
	}

	@And("^Fill in Vehicle Info credentials on P2 Production$")
	public void fillInVehicleInfoInProduction(DataTable vehicleInfoCredentials) throws Throwable {
		System.out.println("Fill in Vehicle Infomation");
		List<List<String>> data = vehicleInfoCredentials.raw();
		getVisibleElementByXpath("//span[@class='select2-chosen' and contains(text(), 'Mr')]").click();
		getVisibleElementByXpath("//div[@class='select2-result-label' and text()='" + data.get(0).get(0) + "']")
		.click();
		getVisibleElementByXpath("//span[@class='select2-chosen' and contains(text(), 'Ayudthya')]").click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(1) + "')]").click();
		getVisibleElementByXpath("//input[@name='postCode']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='mobileNumber']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//label[@for='terms_and_conditions']//span[contains(text(), 'I agree to the')]")
		.click();
		getVisibleElementByXpath("//label[@for='user_legal_agreement']//span[contains(text(), 'I agree to the')]")
		.click();
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys(data.get(0).get(4));
	}

	@And("^Fill in Vehicle Info credentials on P2 brandap$")
	public void fillInVehicleInfoBrandap(DataTable vehicleInfoCredentials) throws Throwable {
		System.out.println("Fill in Vehicle Infomation");
		List<List<String>> data = vehicleInfoCredentials.raw();
		getVisibleElementByXpath("//span[@class='select2-chosen' and contains(text(), 'QLD')]").click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(0) + "')]").click();
		getVisibleElementByXpath("//input[@name='postCode']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='mobileNumber']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath(
				"//label[@for='privacyStatement']//span[contains(text(), 'I read and understood to the ')]").click();
		getVisibleElementByXpath("//label[@for='terms_and_conditions']//span[contains(text(), 'I agree to the')]")
		.click();
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys(data.get(0).get(3));
	}

	@And("^Click on User Legal Agreement checkbox on P2$")
	public void clickOnUserLegalAgreementCheckbox() throws Throwable {
		System.out.println("Click on User Legal Agreement checkbox");
		getVisibleElementByXpath(".//*[@id='contactInfoForm']/div[1]/div[9]/div/fieldset/label").click();
	}

	@And("^Click on Register button on P2$")
	public void clickOnRegisterButton() throws Throwable {
		System.out.println("Click on Register button");
		getVisibleElementByXpath("//button[text()='Register' or text()='注册']").click();
	}

	@And("^Click on Close button on P2$")
	public void clickOnCloseButton() throws Throwable {
		System.out.println("Click on Close button");
		Thread.sleep(5000);
		getVisibleElementByXpath("//i[@class='icon-plus icon-x']").click();

	}

	@And("^Click on OK button on P2 PROD$")
	public void clickOnOkButtonInProd() throws Throwable {
		System.out.println("Click on Ok button");
		getVisibleElementByXpath("//button[text()='注册' or text()='OK']").click();
	}

	@And("^Click on OK Chinese button on P2 PROD$")
	public void clickOnOkChineseButtonInProd() throws Throwable {
		System.out.println("Click on Ok button");
		getVisibleElementByXpath("//button[text()='注册']").click();
	}

	@And("^Click on Activation Email on MailInator page on P2$")
	public void clickOnActivationEmailOnMailInatorPage() throws Throwable {
		System.out.println("Click on Activation Email on MailInator page");
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

		recsc=0;
		do {
			Thread.sleep(2000);
			recsc++;
		} while (driver.findElements(By.xpath("//ul[@id='inboxpane']/li//div[contains(text(),'moments ago') or contains(text(),'minute ago')]")).size() == 0 && recsc<60);// (By.cssSelector("div.col-lg-1.col-md-1.col-sm-1.hidden-xs")).size()==0);

		driver.findElement(By.xpath("//ul[@id='inboxpane']/li//div[contains(text(),'moments ago') or contains(text(),'minute ago')]")).click();
	}

	@When("^Click on Activate My Account link in activation mail on P2$")
	public void clickOnActivateMyAccountLink() throws Throwable {
		System.out.println("Click on Activate My Account link in activation mail");
		Thread.sleep(10000);
		try {
			driver.switchTo().frame(driver.findElement(By.cssSelector("#publicshowmaildivcontent"))).findElement(By.tagName("body"));
		} catch (Exception e) {
			driver.switchTo().frame(driver.findElement(By.id("msg_body")));
		}
		WebElement link = getVisibleElementByXpath("//span[@class='cl-ford-blue' and contains(text(),'[ACTIVATE MY ACCOUNT]') or @class='cl-ford-blue' and contains(text(),'激活我的账号')]");
		link.click();
		Thread.sleep(120000);
	}

	@And("^Click on Forgot username Email on MailInator page$")
	public void Click_on_Forgot_username_Email_on_MailInator_page() throws Throwable {
		System.out.println("Click on Forgot username Email on MailInator page");
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
		} while (driver.findElements(By.xpath("//ul[@id='inboxpane']/li//div[contains(text(),'moments ago') or contains(text(),'minute ago')]")).size() == 0 && recsc<60);
		driver.findElement(By.xpath("//ul[@id='inboxpane']/li//div[contains(text(),'moments ago') or contains(text(),'minute ago')]")).click();
	}

	@Then("^Verify Username name in Forgot username Email$")
	public void Verify_Username_name_in_Forgot_username_Email(DataTable param) throws Throwable {
		System.out.println("Verify Username name in Forgot username Email");
		List<List<String>> data = param.raw();
		String user = null;
		if (data.get(0).get(0).isEmpty()) {
			waitTillElemVisiblebyXpath("//*[contains(text(),'" + UniqueKey + "')]", 240);
			user = UniqueKey;
		} else {
			waitTillElemVisiblebyXpath("//*[contains(text(),'" + data.get(0).get(0) + "')]", 240);
			user = data.get(0).get(0);
		}
		getVisibleElementByXpath("//*[contains(text(),'" + user + "')]");
		Thread.sleep(60000);
	}

	@And("^Click on Password reset$")
	public void Click_on_Password_reset() throws Throwable {
		System.out.println("Click on Password reset");
		driver.switchTo().frame("msg_body");
		waitTillElemVisiblebyXpath("//a[contains(text(),'PASSWORD RESET') or contains(text(),'密码重置')]", 240);
		getVisibleElementByXpath("//a[contains(text(),'PASSWORD RESET') or contains(text(),'密码重置')]").click();
		driver.switchTo().defaultContent();
	}

	@And("^See Reset Password Overlay opened$")
	public void See_Reset_Password_Overlay_opened() throws Throwable {
		System.out.println("See Reset Password Overlay opened");
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		waitTillElemVisiblebyXpath("//input[@name='password']", 240);
		getVisibleElementByXpath("//input[@name='password']");
		getVisibleElementByXpath("//input[@name='confirmPassword']");
	}

	@And("^Enter New password and confirm password$")
	public void Enter_New_password_and_confirm_password(DataTable param) throws Throwable {
		System.out.println("Enter New password and confirm password");
		List<List<String>> data = param.raw();
		getVisibleElementByXpath("//input[@name='password']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='confirmPassword']").sendKeys(data.get(0).get(0));
	}

	@Then("^Registration Success message is displayed$")
	public void SuccessfulRegistration() throws Throwable {
		System.out.println("Registration Success message is displayed");
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			if (driver.getTitle().contains("User")) {
				waitTillElemVisiblebyXpath("//h2[contains(text(),'注册成功') or contains(text(),'Success')]", 240);
				getVisibleElementByXpath("//h2[contains(text(),'注册成功') or contains(text(),'Success')]");
				driver.switchTo().defaultContent();
				break;
			}
		}
		Thread.sleep(3000);
	}

	@When("^Enter credentials to login on P2$")
	public void enterCredentialsToLogin(DataTable userCredentials) throws Throwable {
		System.out.println("Enter credentials to register");
		String user = null, pass = null;
		List<List<String>> data = userCredentials.raw();
		waitTillElementExist("//input[@name='userName']");
		if (data.get(0).get(0).isEmpty()) {
			user = UniqueKey;
			// pass=Password;
			pass = data.get(0).get(1).toString();
		} else {
			user = data.get(0).get(0).toString();
			pass = data.get(0).get(1).toString();
		}

		getVisibleElementByXpath("//input[@name='userName']").sendKeys(user);
		getVisibleElementByXpath("//input[@name='password']").sendKeys(pass);
	}

	@And("^Click on Login button on P2$")
	public void clickOnLoginButton() throws Throwable {
		System.out.println("Click on Login button");
		getVisibleElementByXpath("//button[text()='Login' or text()='登录']").click();
	}

	@And("^Click on Login button on P2 brandap$")
	public void clickOnLoginButtonBrandap() throws Throwable {
		System.out.println("Click on Login button");
		getVisibleElementByXpath("//button[text()='登录']").click();
	}

	@When("^Click on My Profile link on P2$")
	public void clickOnMyProfileLink() throws Throwable {
		System.out.println("Click on My Profile link");
		getVisibleElementByXpath("//a[contains(@href,'myprofile.html')]/i[@class='site-link-icon icon-profile']")
		.click();
	}

	@When("^Logout China Owner$")
	public void LogoutChinaOwner() throws Throwable {
		System.out.println("Logout China Owner");
		waitTillElemVisiblebyXpath("//a[contains(@href,'logout') and not(contains(@class,'mob'))]", 240);
		getVisibleElementByXpath("//a[contains(@href,'logout')]").click();
		waitTillElementExist("//a[contains(@class,'logout-confirm')]");
		driver.findElement(By.cssSelector("a.logout-confirm")).click();
	}

	@When("^Click on My Profile link on P2 brandap$")
	public void clickOnMyProfileLinkBrandap() throws Throwable {
		System.out.println("Click on My Profile link");
		getVisibleElementByXpath("//article[2]//span[text()='My Ford Profile']").click();
	}

	@And("^See VehicleName and Vincode on Profile overlay on P2$")
	public void seeVehicleNameAndVincode(DataTable userCredentials) throws Throwable {
		System.out.println("See VehicleName and Vincode on Profile overlay");
		List<List<String>> data = userCredentials.raw();
		getVisibleElementByXpath(data.get(0).get(0), data.get(0).get(1));
		getVisibleElementByXpath(data.get(0).get(2), data.get(0).get(3));
	}

	@And("^Click on Account Info tab on Profile overlay on P2$")
	public void clickOnAccountInfoTabOnProfileOverlay() throws Throwable {
		System.out.println("Click on Account Info tab on Profile overlay");
		getVisibleElementByXpath("//*[@id='anchor-account-info' or @id='account-info']").click();
	}

	@And("^See all account information in Account Info tab on Profile overlay on P2$")
	public void seeAllAccountInformationInAccountInfoTab(DataTable userCredentials) throws Throwable {
		System.out.println("See all account information in Account Info tab on Profile overlay");
		List<List<String>> data = userCredentials.raw();
		String user = null, email = null;
		if (data.get(0).get(0).isEmpty()) {
			user = UniqueKey;
		} else {
			user = data.get(0).get(0);
		}
		if (data.get(0).get(6).isEmpty()) {
			email = UniqueKey + "@mailinator.com";
		} else {
			email = data.get(0).get(6);
		}
		getVisibleElementByXpath(user, data.get(0).get(1));
		getVisibleElementByXpath(data.get(0).get(2), data.get(0).get(3));
		getVisibleElementByXpath(data.get(0).get(4), data.get(0).get(5));
		getVisibleElementByXpath(email, data.get(0).get(7));
		getVisibleElementByXpath(data.get(0).get(8), data.get(0).get(9));
		if (!data.get(0).get(10).isEmpty()) {
			getVisibleElementByXpath(data.get(0).get(10), data.get(0).get(11));
		}

		getVisibleElementByXpath(data.get(0).get(12), data.get(0).get(13));
		if (!data.get(0).get(14).isEmpty()) {
			getVisibleElementByXpath(data.get(0).get(14), data.get(0).get(15));
		} else {
			getVisibleElementByXpath(phn, data.get(0).get(15));
		}

		if (!data.get(0).get(16).isEmpty() && !data.get(0).get(16).equals("NA")) {
			getVisibleElementByXpath(data.get(0).get(16), data.get(0).get(17));
		} else if (!data.get(0).get(16).equals("NA")) {
			getVisibleElementByXpath(phn, data.get(0).get(17));
		}
		if (!data.get(0).get(18).isEmpty()) {
			getVisibleElementByXpath(data.get(0).get(18), data.get(0).get(19));
		}
	}

	@When("^Click on Register link on Login overlay on P2 PROD$")
	public void clickOnRegisterLinkInProd() throws Throwable {
		System.out.println("Click on Register link on Login overlay");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//a[text()='现在就注册']").click();
	}

	@Then("^See the Register overlay on P2 PROD$")
	public void seeTheRegisterOverlayInProd() throws Throwable {
		System.out.println("See the Register overlay");
		getVisibleElementByXpath("//input[@name='confirmPassword']");
	}

	@When("^Enter credentials to register on P2 PROD$")
	public void enterCredentialsToRegisterInProd(DataTable userCredentials) throws Throwable {
		System.out.println("Enter credentials to register");
		List<List<String>> data = userCredentials.raw();
		getVisibleElementByXpath("//input[@name='userName']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='password']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='confirmPassword']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='email']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//select[@name='title']").click();
		getVisibleElementByXpath("//option[contains(text(),'" + data.get(0).get(4) + "')]").click();
		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(5));
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(6));
		getVisibleElementByXpath("//*[@id='accountInfoForm']/div[2]/div/button").click();
	}

	@And("^Select vehicle credentials on P2 PROD$")
	public void selectVehicleCredentialsInProd(DataTable vehicleCredentials) throws Throwable {
		System.out.println("Click on select Vehicle");
		List<List<String>> data = vehicleCredentials.raw();
		getVisibleElementByXpath("//input[@name='vehicleName']").sendKeys(data.get(0).get(0));
		Thread.sleep(3000);
		getVisibleElementByXpath("//input[@name='vincode']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//*[@id='vehicleInfoForm']/div[2]/div/button").click();
	}

	@And("^Click on Register button on P2 PROD$")
	public void clickOnNextButtonInProd() throws Throwable {
		System.out.println("Click on Register button");
		getVisibleElementByXpath("//button[contains(text(),'注册')]");
	}

	@And("^Select vehicle on P2 DEV$")
	public void selectVehicleInDev(DataTable vehicle) throws Throwable {
		System.out.println("Select Vehicle");
		List<List<String>> data = vehicle.raw();
		getVisibleElementByXpath("//input[@name='vincode']").sendKeys(data.get(0).get(0));
	}

	@And("^Fill in Vehicle Info credentials on P2 PROD$")
	public void fillInVehicleInfoInProd(DataTable vehicleInfoCredentials) throws Throwable {
		System.out.println("Fill in Vehicle Infomation");
		List<List<String>> data = vehicleInfoCredentials.raw();
		getVisibleElementByXpath("//div[@id='s2id_autogen7']/a[@class='select2-choice']").click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(0) + "')]").click();
		getVisibleElementByXpath("//input[@name='postCode']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='mobileNumber']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//label[@for='terms_and_conditions']").click();
		getVisibleElementByXpath("//label[@for='user_legal_agreement']").click();
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys(data.get(0).get(3));
	}

	@When("^Click on My Profile link on P2 Production$")
	public void clickOnMyProfileLinkInProduction() throws Throwable {
		System.out.println("Click on My Profile link");
		getVisibleElementByXpath("//a[text()='Ford Owner Profile']").click();
	}

	@And("^Click on Account Info tab on Profile overlay on P2 Production$")
	public void clickOnAccountInfoTabInProduction() throws Throwable {
		System.out.println("Click on Account Information tab on Profile overlay");
		getVisibleElementByXpath("//a[@id='account-info']").click();
	}

	@And("^See all account information in Account Info tab on Profile overlay on P2 Production$")
	public void seeAllAccountInformationInProduction(DataTable userCredentials) throws Throwable {
		System.out.println("See all account information in Account Info tab on Profile overlay");
		List<List<String>> data = userCredentials.raw();
		String user = null, email = null;
		if (data.get(0).get(0).isEmpty()) {
			user = UniqueKey;
		} else {
			user = data.get(0).get(0);
		}

		if (data.get(0).get(6).isEmpty()) {
			email = UniqueKey + "@mailinator.com";
		} else {
			email = data.get(0).get(6);
		}
		getVisibleElementByXpath(user, data.get(0).get(1));
		getVisibleElementByXpath(data.get(0).get(2), data.get(0).get(3));
		getVisibleElementByXpath(data.get(0).get(4), data.get(0).get(5));
		getVisibleElementByXpath(email, data.get(0).get(7));
		getVisibleElementByXpath(data.get(0).get(8), data.get(0).get(9));
		getVisibleElementByXpath(data.get(0).get(10), data.get(0).get(11));
		getVisibleElementByXpath(data.get(0).get(12), data.get(0).get(13));
		getVisibleElementByXpath(data.get(0).get(14), data.get(0).get(15));
	}

	@And("^Click on Change Email Address link on Account Info on P2$")
	public void clickOnChangeEmailAddressLinkOnAccountInfo() throws Throwable {
		System.out.println("Click on Change Email Address link on Account Info");
		waitTillElemVisiblebyXpath("//a[contains(text(),'Change Email') or contains(text(),'更改邮箱地址')]", 240);
		getVisibleElementByXpath("//a[contains(text(),'Change Email') or contains(text(),'更改邮箱地址')]").click();
	}

	@And("^Verify Change Email to new email on Account Info on P2$")
	public void verifyChangeEmailToNewEmailOnAccountInfo(DataTable newEmail) throws Throwable {
		System.out.println("Verify Change Email to new email on Account Info");
		List<List<String>> data = newEmail.raw();
		String email = null;
		getVisibleElementByXpath("//input[@type='email']").clear();
		if (data.get(0).get(0).isEmpty()) {
			email = UniqueKey + "0@mailinator.com";
		} else {
			email = data.get(0).get(0);
		}
		System.out.println("New Email id is: " + email);
		getVisibleElementByXpath("//input[@type='email']").sendKeys(email);
		Thread.sleep(2000);
		getVisibleElementByXpath("//tr[contains(@class,'editmode')]//a[text()='Confirm Changes' or text()='确认更改']").click(); 
	}

	@And("^Click on Change Password on Account Info on P2$")
	public void clickOnChangePasswordOnAccountInfo() throws Throwable {
		System.out.println("Click on change password on account info");
		waitTillElemVisiblebyXpath("//a[text()='New Password' or text()='修改密码' or text()='Change Password']", 240);
		getVisibleElementByXpath("//a[text()='New Password' or text()='修改密码' or text()='Change Password']").click();
	}

	@And("^Verify Change Password to new password on Account Info on P2$")
	public void verifyChangePasswordOnAccountInfo(DataTable newPasword) throws Throwable {
		System.out.println("Verify Change Password to new password on Account Info");
		List<List<String>> data = newPasword.raw();
		String password = null;
		if (data.get(0).get(0).isEmpty()) {
			password = UniqueKey;
		} else {
			password = data.get(0).get(0);
		}
		System.out.println("New password is: " + password);
		getVisibleElementByXpath("//input[@type='password']").clear();
		getVisibleElementByXpath("//input[@type='password']").sendKeys(password);
		Thread.sleep(2000);
		getVisibleElementByXpath("//tr[contains(@class,'editmode')]//a[text()='Confirm Changes' or text()='确认更改']").click(); 
	}

	@When("^Click on Logout link on P2$")
	public void clickOnLogoutLink() throws Throwable {
		System.out.println("Click on Logout link");
		getVisibleElementByXpath("//a[@href='#logout' and contains(@class,'link-logout-overlay')]").click();
	}

	@Then("^See the Logout overlay on P2$")
	public void seeLogoutOverlay() throws Throwable {
		System.out.println("See the Logout overlay");
		getVisibleElementByXpath("//div[@class='title-logout']");
		getVisibleElementByXpath("//div[@class='button-logout']");

	}

	@When("^Click on Cancel in Logout overlay on P2")
	public void clickCancelInLogoutOverlay() throws Throwable {
		System.out.println("Click on Cancel in Logout overlay");
		getVisibleElementByXpath("//a[contains(@class,'logout-cancel')]").click();
	}

	@Then("^See Logout overlay disappeared on P2")
	public void seeLogoutOverlayDisapeared() throws Throwable {
		System.out.println("See the Logout overlay");
		verifyInvisibleElement("//div[@class='button-logout']");
	}

	@When("^Click on Confirm in Logout overlay on P2")
	public void clickOnConfirmInLogoutOverlay() throws Throwable {
		System.out.println("Click on Confirm in Logout overlay");
		getVisibleElementByXpath("//a[@class='logout-confirm']").click();
	}

	@Then("^See user is logged out and redirect to Homepage \"(.*?)\" on P2$")
	public void seeUserIsLoggedOut(String homepageLink) throws Throwable {
		System.out.println("See user is logged out and redirect to Homepage");
		verifyRedirecToCorrectLink(homepageLink);
	}

	@And("^Do not see Profile link anymore on P2$")
	public void donnotSeeProfileLink() throws Throwable {
		System.out.println("Don't see Profile link anymore");
		verifyInvisibleElement("//a[contains(@href,'myprofile.html')]/i[@class='site-link-icon icon-profile']");

	}

	@And("^Do not see Profile link anymore on P2 Production$")
	public void donnotSeeProfileLinkInProduction() throws Throwable {
		System.out.println("Don't see Profile link anymore");
		verifyInvisibleElement("//a[contains(@href,'my-vehicle-status')]/i[@class='site-link-icon icon-profile']");

	}

	@When("^Click on Add Vehicle on Profile overlay on P2$")
	public void clickOnAddVehicle() throws Throwable {
		System.out.println("Click on Add Vehicle");
		getVisibleElementByXpath("//a[@id='anchor-add-vehicle' or @id='add-vehicle']").click();
	}

	@When("^Click on Add Vehicle on Profile overlay on P2 Production$")
	public void clickOnAddVehicleInProduction() throws Throwable {
		System.out.println("Click on Add Vehicle");
		getVisibleElementByXpath("//a[@id='add-vehicle']").click();
	}

	@When("^Click on Confirm to Add Vehicle on P2$")
	public void clickOnConfirmToAddVehicle() throws Throwable {
		System.out.println("Click on Confirm to Add Vehicle");
		waitTillElemVisiblebyXpath("//button[text()='OK']", 240);
		getVisibleElementByXpath("//button[text()='OK']").click();
	}

	@Then("^See that Add vehicle form is displayed on Add Vehicle tab on P2 Production$")
	public void seeAddVehicleFormDisplayed() throws Throwable {
		System.out.println("See that Add vehicle form is displayed on Add Vehicle tab on P2");
		getVisibleElementByXpath("//input[@name='vincode']");
		getVisibleElementByXpath("//button[text()='Add Vehicle' or text()='添加车辆']");
	}

	@Then("^Add vehicle with Vincode on P2$")
	public void addVehicleVincode(DataTable secondVehicle) throws Throwable {
		System.out.println("Add vehicle with Vincode");
		List<List<String>> data = secondVehicle.raw();
		getVisibleElementByXpath("//input[@name='vincode']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//button[text()='Add Vehicle']").click();
	}

	@Then("^Add vehicle with Vehicle name and Vincode on P2 Production$")
	public void addVehicle(DataTable secondVehicle) throws Throwable {
		System.out.println("Add vehicle with given Vehicle name and Vincode");
		List<List<String>> data = secondVehicle.raw();
		getVisibleElementByXpath("//input[@name='vehicleName']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='vincode']").clear();
		getVisibleElementByXpath("//input[@name='vincode']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//button[text()='Add Vehicle']").click();
	}

	@Then("^Add vehicle with Vehicle name and Vincode on China P2$")
	public void addVehicleChina(DataTable secondVehicle) throws Throwable {
		System.out.println("Add vehicle with Vehicle name and Vincode on China P2");
		List<List<String>> data = secondVehicle.raw();
		getVisibleElementByXpath("//input[@type='text' and @name='vehicleName']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@type='text' and @name='vincode']").clear();
		getVisibleElementByXpath("//input[@type='text' and @name='vincode']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//button[text()='Add Vehicle' or text()='添加车辆']").click();
	}
	@When("^Click on Delete Vehicle on P2$")
	public void clickOnDeleteVehicle() throws Throwable {
		System.out.println("Click on Delete Vehicle");
		getVisibleElementByXpath(
				"//div[@class='tabs-content ownerVehicleContent active']//a[contains(@class, 'link-delete-vehicle-overlay') and text()='Delete Vehicle' or text()='删除车辆']")
		.click();
	}

	@And("^Click on Confirm to Delete Vehicle on P2 Production$")
	public void clickOnConfirmToDeleteVehicleInProduction() throws Throwable {
		System.out.println("Click on Confirm to Delete Vehicle");
		Actions actions = new Actions(driver);
		actions.moveToElement(
				driver.findElement(By.xpath("//a[@class='delete-vehicle-confirm' and contains(text(), 'Confirm')]")))
		.click().perform();

	}

	@And("^Click on Confirm to Delete Vehicle on P2$")
	public void clickOnConfirmToDeleteVehicle() throws Throwable {
		System.out.println("Click on Confirm to Delete Vehicle");
		Thread.sleep(2000);
		waitTillElementExist(
				"//div[@class='tabs-content ownerVehicleContent active']//a[@class='delete-vehicle-confirm']");
		driver.findElement(By.cssSelector("div.tabs-content.ownerVehicleContent.active a.delete-vehicle-confirm"))
		.click();
	}

	@Then("^Do not See Vehicle tab name on P2$")
	public void notSeeVehicleTabName(DataTable tabName) throws Throwable {
		System.out.println("Don't See Vehicle tab name");

		List<List<String>> data = tabName.raw();
		verifyInvisibleElement("//a[text()='" + data.get(0).get(0) + "' and @class='ownerVehicleTitle']");
	}

	@Then("^See the first vehicle tab name on P2$")
	public void seeTheFirstVehicleTabName() throws Throwable {
		System.out.println("See the first vehicle tab name");
		waitTillElemVisiblebyXpath("//*[@id='tabs-0']", 240);
		getVisibleElementByXpath("//*[@id='tabs-0']");
	}

	@Then("^See the second vehicle tab name on P2$")
	public void seeTheSecondVehicleTabName() throws Throwable {
		System.out.println("See the second vehicle tab name");
		getVisibleElementByXpath("//*[@id='tabs-1']");
		Thread.sleep(5000);
	}

	@When("^Click on first vehicle tab on P2$")
	public void clickOnFirstVehicleTab() throws Throwable {
		System.out.println("Click on first Vehicle tab");
		getVisibleElementByXpath("//*[@id='tabs-0']").click();
		Thread.sleep(5000);
	}

	@Then("^Verify first vehicle tab is loaded correctly on P2$")
	public void verifyFirstVehicleTabLoadedCorrectly() throws Throwable {
		System.out.println("Verify first vehicle tab is loaded correctly");
		getVisibleElementByXpath("//*[@id='tabs-0']");
	}

	@Then("^Close the SYNC overlay on P2$")
	public void ClosetheSYNCoverlayonP2() throws Throwable {
		System.out.println("Close the SYNC overlay");
		getVisibleElementByXpath("//i[@class='icon-plus icon-x']");
	}

	@When("^Click on second vehicle tab on P2$")
	public void clickOnSecondVehicleTab() throws Throwable {
		System.out.println("Click on second Vehicle tab");
		getVisibleElementByXpath("//*[@id='tabs-1']").click();
	}

	@Then("^Verify second vehicle tab is loaded correctly on P2$")
	public void verifySecondVehicleTabLoadedCorrectly() throws Throwable {
		System.out.println("Verify second vehicle tab is loaded correctly on P2");
		getVisibleElementByXpath("//*[@id='tabs-1']");
	}

	@Then("^Verify content on Vehicle Tab: Vehicle Name=\"(.*?)\" Service Date=\"(.*?)\" Vin=\"(.*?)\" Warranty=\"(.*?)\" Exp.Date=\"(.*?)\" End Odo Meter=\"(.*?)\" Sync Version=\"(.*?)\" Manual=\"(.*?)\"  Recall=\"(.*?)\"$")
	public void verifyContentOnVehicleTab(String vehicleName, String serviceDate, String vin, String warranty,
			String expDate, String endOdometer, String syncVersion, String manual, String recall) throws Throwable {
		System.out.println("Verify content on Vehicle Tab:");
		getVisibleElementByXpath("//span[text()='" + vehicleName + "']");
		getVisibleElementByXpath("//span[text()='" + serviceDate + "']");
		getVisibleElementByXpath("//span[text()='" + vin + "']");
		getVisibleElementByXpath("//span[contains(text(),'" + warranty + "')]");
		getVisibleElementByXpath("//span[contains(text(),'" + expDate + "')]");
		getVisibleElementByXpath("//span[contains(text(),'" + endOdometer + "')]");
		getVisibleElementByXpath("//span[contains(text(),'" + syncVersion + "')]");
		getVisibleElementByXpath("//span[contains(text(),'" + manual + "')]");
		getVisibleElementByXpath("//span[contains(text(),'" + recall + "')]");

	}

	@Then("^See delete vehicle confirmation overlay  on P2 Production$")
	public void seeDeleteVehicleConfirmationOverlayInProduction() throws Throwable {
		System.out.println("See delete vehicle confirmation overlay");
		getVisibleElementByXpath("//a[contains(@class,'delete-vehicle-cancel') and contains(text(), 'Cancel')]");
		getVisibleElementByXpath("//a[@class='delete-vehicle-confirm' and contains(text(), 'Confirm')]");
	}

	@Then("^See delete vehicle confirmation overlay  on P(\\d+)$")
	public void see_delete_vehicle_confirmation_overlay_on_P(int arg1) throws Throwable {
		System.out.println("See delete vehicle confirmation overlay");
		waitTillElementExist(
				"//div[@class='tabs-content ownerVehicleContent active']//a[@class='delete-vehicle-confirm' and contains(text(), 'Yes, I am sure')]");
		getVisibleElementByXpath(
				"//div[@class='tabs-content ownerVehicleContent active']//a[contains(@class,'delete-vehicle-cancel') and contains(text(), 'Cancel')]");
		getVisibleElementByXpath(
				"//div[@class='tabs-content ownerVehicleContent active']//a[@class='delete-vehicle-confirm' and contains(text(), 'Yes, I am sure')]");
	}

	@Then("^See delete vehicle confirmation overlay$")
	public void see_delete_vehicle_confirmation_overlay() throws Throwable {
		System.out.println("See delete vehicle confirmation overlay");
		waitTillElementExist(
				"//div[@class='tabs-content ownerVehicleContent active']//a[@class='delete-vehicle-confirm' and contains(text(), 'Yes, I am sure') or contains(text(),'是的，我确定')]");
		getVisibleElementByXpath(
				"//div[@class='tabs-content ownerVehicleContent active']//a[contains(@class,'delete-vehicle-cancel') and contains(text(), 'Cancel') or contains(text(),'取消')]");
		getVisibleElementByXpath(
				"//div[@class='tabs-content ownerVehicleContent active']//a[@class='delete-vehicle-confirm' and contains(text(), 'Yes, I am sure') or contains(text(),'是的，我确定')]");
	}

	@And("^Click on Update New Version link on Profile overlay on P2$")
	public void clickOnUpdateNewVersionLinkOnProfileOverlay() throws Throwable {
		System.out.println("Click on Update New Version link on Profile overlay");
		if(driver.findElements(By.xpath("//a[text()='Update New Version' or text()='SYNC_UPDATE_NEW_VERSION' or text()='更新新版本']")).size()>0) {
			getVisibleElementByXpath("//a[text()='Update New Version' or text()='SYNC_UPDATE_NEW_VERSION' or text()='更新新版本']").click();
			try {
				waitTillElemVisiblebyXpath("//a[text()='Request Download']", 240);
				driver.findElement(By.xpath("//a[text()='Request Download']")).click();
				Thread.sleep(10000);
				moveToElement("//a[text()='I agree']");
				driver.findElement(By.xpath("//a[text()='I agree']")).click();
				Thread.sleep(15000);
				WebDriverWait wait = new WebDriverWait(driver, 240);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='OK']")));
				driver.findElement(By.xpath("//a[text()='OK']")).click();
			} catch (Exception e) {
			}
			Thread.sleep(5000);
			waitTillElementExist("//section/div[4]//img[@src='/etc/designs/guxfoap/clientlibs/guxfoap/img/downloadSYNCHistory/downloadRequested.png']");
			getVisibleElementByXpath("//section/div[4]//img[@src='/etc/designs/guxfoap/clientlibs/guxfoap/img/downloadSYNCHistory/downloadRequested.png']");
			getVisibleElementByXpath("//div[@class='desktop hideForMobile']/h3[text()='Your download is ready']");
			getVisibleElementByXpath("//div[@class='desktop hideForMobile']/p[contains(text(),'Please press OK then refresh the Updates page and select') and contains(text(),'Start Download')]");
			getVisibleElementByXpath("//section/div[4]//div[1]/p[3]/a[text()='OK']");
			getVisibleElementByXpath("//section/div[4]//div[1]/p[3]/a[text()='OK']").click();
			try {
				waitTillElementExist("//a[text()='Download Now' or text()='下载手册' or text()='下载手册']");
				driver.findElement(By.xpath("//a[text()='Download Now' or text()='下载手册' or text()='下载手册']")).click();
			} catch (Exception e) {
				waitTillElementExist("//a[text()='Start Download']");
				getVisibleElementByXpath("//a[text()='Start Download']").click();
			}
			getVisibleElementByXpath("//i[@class='icon-plus icon-x']").click();

		}else if(driver.findElements(By.xpath("//a[contains(@class,'update-your-sync-link') and text()!='']")).size()>0) {
			System.out.println("Your current model is not equipped with SYNC®");
		}
	}

	@And("^See Your download is ready overlay on P2$")
	public void seeYourDownloadIsReadyOverlay() throws Throwable {
		System.out.println("See Your download is ready overlay");
		try {
			waitTillElemVisiblebyXpath("//a[text()='Request Download']", 240);
			driver.findElement(By.xpath("//a[text()='Request Download']")).click();
			Thread.sleep(5000);
			moveToElement("//a[text()='I agree']");
			driver.findElement(By.xpath("//a[text()='I agree']")).click();
			Thread.sleep(10000);
			WebDriverWait wait = new WebDriverWait(driver, 240);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='OK']")));
			driver.findElement(By.xpath("//a[text()='OK']")).click();
		} catch (Exception e) {
		}

		waitTillElementExist("//section/div[4]//img[@src='/etc/designs/guxfoap/clientlibs/guxfoap/img/downloadSYNCHistory/downloadRequested.png']");
		getVisibleElementByXpath("//section/div[4]//img[@src='/etc/designs/guxfoap/clientlibs/guxfoap/img/downloadSYNCHistory/downloadRequested.png']");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']/h3[text()='Your download is ready']");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']/p[contains(text(),'Please press OK then refresh the Updates page and select') and contains(text(),'Start Download')]");
		getVisibleElementByXpath("//section/div[4]//div[1]/p[3]/a[text()='OK']");
	}

	@When("^Click on OK button on your download is ready overlay on P2$")
	public void clickOnOkButtonOnYourDownloadIsReadyOverlay() throws Throwable {
		System.out.println("Click on OK button on your download is ready overlay");
		getVisibleElementByXpath("//section/div[4]//div[1]/p[3]/a[text()='OK']").click();
	}

	@Then("^See all components on Update your SYNC overlay on P2$")
	public void seeAllComponentsOnUpdateYourSyncOverlay() throws Throwable {
		System.out.println("See all components on Update your SYNC overlay");
		getVisibleElementByXpath("//h2[text()='Update your SYNC']");
		getVisibleElementByXpath("//span[text()='Download']");
		getVisibleElementByXpath("//span[text()='Install SYNC']");
		getVisibleElementByXpath("//a[text()='Start Download']");
		getVisibleElementByXpath("//div[contains(text(),'forget to report your update')]");
		getVisibleElementByXpath("//a[text()='Why do I need to report my update?']");
		getVisibleElementByXpath("//a[text()='Report']");
		getVisibleElementByXpath("//a[text()='Check update history']");
		getVisibleElementByXpath("//div[@class='sync-down']/i[@class='icon-sync-down']");
		getVisibleElementByXpath("//div[@class='cloud-update']/i[@class='icon-cloud-update']");
	}

	@When("^Click on Report button on Update your SYNC overlay on P2$")
	public void clickOnReportButtonOnUpdateYourSyncOverlay() throws Throwable {
		System.out.println("Click on Report button on Update your SYNC overlay");
		getVisibleElementByXpath("//a[text()='Report']").click();
	}

	@Then("^See all components on report your sync overlay on P2$")
	public void seeAllComponentsOnReportYourSyncOverlay() throws Throwable {
		System.out.println("See all components on report your sync overlay");
		getVisibleElementByXpath(
				"//img[@src='/etc/designs/guxfoap/clientlibs/guxfoap/img/downloadSYNCHistory/downloadSYNCHistory.png']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/p[contains(text(),'Select a drive to transfer the SYNC')]");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']/p[contains(text(),'Report Installation')]");
		getVisibleElementByXpath("//input[@id='sync-upload']");
		getVisibleElementByXpath("//div[@class='report-button']/button[@class='cta-button']");
	}

	@When("^Click on Submit button on report your sync overlay on P2$")
	public void clickOnSubmitButtonOnReportYourSyncOverlay() throws Throwable {
		System.out.println("Click on Submit button on report your sync overlay");
		getVisibleElementByXpath("//div[@class='report-button']/button[@class='cta-button']").click();
	}

	@Then("^See Oops overlay on P2$")
	public void seeOopsOverlay() throws Throwable {
		System.out.println("See Oops overlay");
		getVisibleElementByXpath(
				"//img[@src='/etc/designs/guxfoap/clientlibs/guxfoap/img/downloadSYNCHistory/installationError.png']");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']/h3[text()='Oops!']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/p[text()='Something went wrong. Please try again.']");
		getVisibleElementByXpath(
				"//div[contains(@class,'installationFail')]//div[@class='desktop hideForMobile']//a[text()='OK']");
	}

	@When("^Click on OK button on Oops overlay on P2$")
	public void clickOnOkButtonOnOopsOverlay() throws Throwable {
		System.out.println("Click on OK button on Oops overlay");
		getVisibleElementByXpath(
				"//div[contains(@class,'installationFail')]//div[@class='desktop hideForMobile']//a[text()='OK']")
		.click();
	}

	@When("^Click on Why do I need to report my update link on P2$")
	public void clickOnWhyDoNeedToReportMyUpdateLink() throws Throwable {
		System.out.println("Click on Why do I need to report my update link");
		getVisibleElementByXpath("//a[text()='Why do I need to report my update?']").click();
	}

	@Then("^See Report Sync Update overlay on P2$")
	public void seeReportSyncUpdateOverlay() throws Throwable {
		System.out.println("See Report Sync Update overlay");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']/h2[text()='Report SYNC™ Update']");
		getVisibleElementByXpath("//i[contains(@class,'icon-have-questions-faqs')]");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/h3[text()='Why do I need to report my update?']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/p[text()='Each successful installation keeps your vehicle’s software record up to date and prevents you from re-installing old updates in the future.']");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//a[text()='OK']");
	}

	@When("^Click on OK button on Report Sync Update overlay on P2$")
	public void clickOnOkButtonOnReportSyncUpdateOverlay() throws Throwable {
		System.out.println("Click on OK button on Report Sync Update overlay");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//a[text()='OK']").click();
	}

	@When("^Click on Check update history link on P2$")
	public void clickOnCheckUpdateHistoryLink() throws Throwable {
		System.out.println("Click on Check update history link");
		getVisibleElementByXpath("//a[text()='Check update history']").click();
	}

	@And("^See all components on sync history overlay on P2$")
	public void seeAllComponentsOnSyncHistoryOverlay() throws Throwable {
		System.out.println("See all components on sync history overlay");
		getVisibleElementByXpath("//button[@class='sync-history-back']");
	}

	@When("^Click on Back button on sync history overlay on P2$")
	public void clickOnBackButtonOnSyncHistoryOverlay() throws Throwable {
		System.out.println("Click on Back button on sync hitory overlay");
		getVisibleElementByXpath("//button[@class='sync-history-back']").click();
	}

	@When("^Click on Start Download button on Update Your Sync overlay on P2$")
	public void clickOnStartDownloadButtonOnUpdateYourSyncPage() throws Throwable {
		System.out.println("Click on Start Download button on Update Your Sync overlay");
		getVisibleElementByXpath("//a[text()='Start Download']").click();
	}

	@And("^See all components on Download Sync overlay on P2$")
	public void seeAllComponentsOnDownloadSyncOverlay() throws Throwable {
		System.out.println("See all components on Download Sync overlay");
		getVisibleElementByXpath("//a[text()='I agree']");
	}

	@When("^Click on I agree button on Dowload Sync overlay on P2$")
	public void clickOnAgreeButtonOnDownloadSyncOverlay() throws Throwable {
		System.out.println("Click on I agree button on Dowload Sync overlay");
		getVisibleElementByXpath("//a[text()='I agree']").click();
	}

	@And("^Click on Ok button on Download Sync overlay on P2$")
	public void clickOnOkButtonOnDownloadSyncPage() throws Throwable {
		System.out.println("Click on Ok button on Download Sync overlay on P2");
		getVisibleElementByXpath("//a[text()='OK']").click();
	}

	@When("^Click on OK button on Update Your Sync page on P2$")
	public void clickOnOkButtonOnUpdateYourSyncPage() throws Throwable {
		System.out.println("Click on OK button on Update Your Sync page");
		getVisibleElementByXpath("//a[text()='OK']").click();
	}

	@And("^Click on Download Now link on Profile overlay on P2$")
	public void clickOnDownloadNowLinkOnProfileOverlay() throws Throwable {
		System.out.println("Click on Download Now link on Profile overlay");
		String mainWindowHandle = driver.getWindowHandles().iterator().next();
		try {
			waitTillElementExist("//a[text()='Download Now' or text()='下载手册' or text()='下载手册']");
			driver.findElement(By.xpath("//a[text()='Download Now' or text()='下载手册' or text()='下载手册']")).click();
		} catch (Exception e) {
			waitTillElementExist("//a[text()='Start Download']");
			getVisibleElementByXpath("//a[text()='Start Download']").click();
		}
		Thread.sleep(5000);
		if (driver.getWindowHandles().size() > 1) {
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}
		}
		try {
			getVisibleElementByXpath("//input[@type='button' and contains(@value,'Click here')]");
		}catch(Exception e) {}
		driver.switchTo().window(mainWindowHandle);
	}

	@And("^See all components on Owner Download Manual overlay on P2$")
	public void seeAllComponentsOnOwnerDownloadManualOverlay() throws Throwable {
		System.out.println("See all components on Owner Download Manual overlay");
		getVisibleElementByXpath("//h1[contains(text(),'Manual')]");
		getVisibleElementByXpath("//a[contains(text(),'Manual PDF')]");
	}

	@When("^Redirect to Download Owner Manual PDF on P2$")
	public void redirectToDownloadOwnerManualPdf() throws Throwable {
		System.out.println("Redirect to Download Owner Manual PDF");
		Thread.sleep(30000);
		WebElement link = getVisibleElementByXpath("//a[contains(text(),'Manual PDF')]");
		driver.get(getProfileURL(link.getAttribute("href")));
	}

	@When("^Click on Owner Manual PDF on Owner Download Manual overlay on P2$")
	public void clickOnOwnerManualPdfOnOwnerDownloadManualOverlay() throws Throwable {
		System.out.println("Click on Owner Manual PDF on Owner Download Manual overlay");
		getVisibleElementByXpath("//a[contains(text(),'Manual PDF')]").click();
	}

	@When("^Click on Forgot Username link on Login overlay on P2$")
	public void clickOnForgotUsername() throws Throwable {
		System.out.println("Click on Forgot Username link on Login overlay");
		getVisibleElementByXpath(
				"//div[contains(@class,'login-form')]//div[contains(@class,'textfield-userName')]//small[contains(@class,'hint')]//a")
		.click();
	}

	@Then("^See the Forgot Username overlay on P2$")
	public void seeTheForgotUsernameOverlay() throws Throwable {
		System.out.println("See the Forgot Username overlay");
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']");

	}

	@When("^Click on Forgot Password link on Login overlay on P2$")
	public void clickOnForgotPassword() throws Throwable {
		System.out.println("Click on Forgot Password link on Login overlay");
		getVisibleElementByXpath(
				"//div[contains(@class,'login-form')]//div[contains(@class,'password')]//small[contains(@class,'hint')]//a")
		.click();
	}

	@Then("^See the Forgot Password overlay on P2$")
	public void seeTheForgotPasswordOverlay() throws Throwable {
		System.out.println("See the Forgot Password overlay");
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']");
	}

	@When("^Input email to forgot username form on P2$")
	public void inputEmailToForgotUserName(DataTable email) throws Throwable {
		System.out.println("Input email to forgot username form");

		List<List<String>> data = email.raw();
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(0));
	}

	@When("^Click button Submit to forgot username on P2$")
	public void clickSubmitForgotUserName() throws Throwable {
		System.out.println("Click button Submit to forgot username");
		getVisibleElementByXpath("//button[text()='Submit']").click();
	}

	@When("^Go to google mail site on P2$")
	public void gotoGmailSite() throws Throwable {
		System.out.println("Go to google mail site");
		driver.navigate().to(
				"https://accounts.google.com/ServiceLogin?service=mail&continue=https://mail.google.com/mail/&hl=vi#identifier");
	}

	@When("^Input credentials on gmail site on P2$")
	public void inputUserNameAndNext(DataTable credentials) throws Throwable {
		System.out.println("Input credentials on gmail site");

		List<List<String>> data = credentials.raw();
		getVisibleElementByXpath("//input[@id='identifierId']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//span[text()='Tiếp theo']").click();
		getVisibleElementByXpath("//input[@name='password']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//span[text()='Tiếp theo']").click();
	}

	@When("^Click on Activation mail on Gmail page$")
	public void clickOnActivationMailOnGmailPage() throws Throwable {
		System.out.println("Click on Activation mail on Gmail page");
		getVisibleElementByXpath("//b[contains(text(), 'Activation Email')]").click();
	}

	@When("^See successfully submit to forgot Username on P2$")
	public void seeSuccessfullySubmitToForgotUserName() throws Throwable {
		System.out.println("See successfully submit to forgot Username");
		getVisibleElementByXpath("//h1[text()='Successful']");
	}

	@When("^Click on Ok button on Forgot Username overlay on P2 Production$")
	public void clickOnOkButtonOnForgotUsernameOverlayInProduction() throws Throwable {
		System.out.println("Click on Ok button on Forgot Username overlay");
		getVisibleElementByXpath("//button[text()='Ok']").click();
	}

	@When("^Click on Ok button on Forgot Username overlay on P2$")
	public void clickOnOkButtonOnForgotUsernameOverlay() throws Throwable {
		System.out.println("Click on Ok button on Forgot Username overlay");
		getVisibleElementByXpath("//button[text()='OK']").click();
	}

	@When("^Click on Ok button on Forgot Password overlay on P2 Production$")
	public void clickOnOkButtonOnForgotPasswordOverlayInProduction() throws Throwable {
		System.out.println("Click on Ok button on Forgot Password overlay");
		getVisibleElementByXpath("//button[text()='Ok']").click();
	}

	@When("^Click on Ok button on Forgot Password overlay on P2$")
	public void clickOnOkButtonOnForgotPasswordOverlay() throws Throwable {
		System.out.println("Click on Ok button on Forgot Password overlay");
		getVisibleElementByXpath("//button[text()='OK']").click();
	}

	@When("^Click on Ok button on Reset Password overlay on P2$")
	public void clickOnOkButtonOnResetPassword() throws Throwable {
		System.out.println("Click on Ok button on Reset Password overlay");
		getVisibleElementByXpath("//a[text()='Ok']").click();
	}

	@When("^Click on first New Forgot Username gmail item on P2$")
	public void clickOnFirstNewForgotUsernameGmailItem() throws Throwable {
		System.out.println("Click on first New Forgot Username gmail item");
		getVisibleElementByXpath("//table/tbody/tr[contains(@class,'zE')]//b").click();
	}

	@Then("^Verify the username is received on P2$")
	public void verifyTheUsernameIsReceived(DataTable userName) throws Throwable {
		System.out.println("Verify the username is received");

		List<List<String>> data = userName.raw();
		getVisibleElementByXpath("//p[contains(text(),'" + data.get(0).get(0) + "')]");
	}

	@When("^Input credentials to forgot password form on P2$")
	public void inputEmailAndUserNameToForgotUserName(DataTable credentials) throws Throwable {
		System.out.println("Input email and username to forgot password form");

		List<List<String>> data = credentials.raw();
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='userName']").sendKeys(data.get(0).get(1));
	}

	@When("^Click button Submit to forgot Password on P2$")
	public void clickSubmitForgotPassword() throws Throwable {
		System.out.println("Click button Submit to forgot Password");
		getVisibleElementByXpath("//button[text()='Submit']").click();
	}

	@When("^See successfully submit to forgot Password on P2$")
	public void seeSuccessfullySubmitToForgotPassword() throws Throwable {
		System.out.println("See successfully submit to forgot Password");
		getVisibleElementByXpath("//h1[text()='Successful']");
	}

	@When("^Click on first New Forgot Password gmail item on P2$")
	public void clickOnFirstNewForgotPasswordGmailItem() throws Throwable {
		System.out.println("Click on first New Forgot Password mail item");
		getVisibleElementByXpath(
				"//table/tbody/tr[contains(@class,'zE')]/td/div/div/div/span/b[text()='Forgot password']").click();
	}

	@When("^Input password and next on P2$")
	public void inputPasswordAndNext(DataTable password) throws Throwable {
		System.out.println("Input password and next");

		List<List<String>> data = password.raw();
		getVisibleElementByXpath("//*[@id='Passwd']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//*[@id='signIn']").click();
	}

	@When("^Redirect to Password Reset link on P2$")
	public void redirectToPasswordResetLink() throws Throwable {
		System.out.println("Redirect to Password Reset link");
		Thread.sleep(30000);
		WebElement link = getVisibleElementByXpath("//a[text()='PASSWORD RESET']");
		driver.get(getProfileURL(link.getAttribute("href")));
	}

	@When("^Input credentials on Reset Password overlay on P2$")
	public void inputNewPasswordOnResetPasswordOverlay(DataTable credentials) throws Throwable {
		System.out.println("Input new password on Reset password overlay");

		List<List<String>> data = credentials.raw();
		getVisibleElementByXpath("//input[@name='password']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='confirmPassword']").sendKeys(data.get(0).get(1));
	}

	@And("^Click on Submit button on Reset Password overlay on P2$")
	public void clickOnSubmitButtonOnResetPasswordOverlay() throws Throwable {
		System.out.println("Click on Submit button on Reset Password overlay");
		getVisibleElementByXpath("//button[text()='Submit']").click();
	}

	@And("^Click on Ok button on Successful page on P2$")
	public void clickOnOkbuttonOnSuccessfulPage() throws Throwable {
		System.out.println("Click on Ok button on Successful page");
		getVisibleElementByXpath("//a[text()='Ok']").click();
	}

	@And("^Input credentials on Account Activated overlay on P2$")
	public void inputUsernameAndPasswordOnAccountActivatedOverlay(DataTable credentials) throws Throwable {
		System.out.println("Input Username and Password on Account Activated overlay");

		List<List<String>> data = credentials.raw();
		getVisibleElementByXpath("//input[@name='userName']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='password']").sendKeys(data.get(0).get(1));
	}

	@And("^Click on Login button on Account Activated overlay on P2$")
	public void clickOnLoginButtonOnAccountActivatedOverlay() throws Throwable {
		System.out.println("Click on Login button on Account Activated overlay");
		getVisibleElementByXpath("//button[text()='Login']").click();
	}

	@When("^Click on Build and Price on P2$")
	public void clickOnBuildAndPrice() throws Throwable {
		System.out.println("Click on Build and Price");
		waitTillElemClickableByXpath("(//h2/a[text()='Build & Price'])[1]", 240);
		getVisibleElementByXpath("(//h2/a[text()='Build & Price'])[1]").click();
	}
	
	@When("^Click on TH Build and Price on P2$")
	public void clickOnTHBuildAndPrice() throws Throwable {
		System.out.println("Click on TH Build and Price");
		waitTillElemClickableByXpath("(//a[text()='Build & Price'])[1]", 240);
		getVisibleElementByXpath("(//a[text()='Build & Price'])[1]").click();
	}

	@When("^verify Cancel button exist$")
	public void verify_Cancel_button_exist() throws Throwable {
		System.out.println("verify Cancel button exist");
		waitTillElementExist("//button[text()='Cancel']");
		waitTillElementVisible("button.cta-button.cancel-btn.cta-button-secondary");
	}

	@When("^Input into Postcode field on POLK on P2$")
	public void inputIntoPostcodeField(DataTable postcode) throws Throwable {
		System.out.println("Input into Postcode field on POLK");
		List<List<String>> data = postcode.raw();
		waitTillElemVisiblebyXpath("//input[@id='dd']",240);
		getVisibleElementByXpath("//input[@id='dd']").clear();
		Thread.sleep(2000);
		getVisibleElementByXpath("//input[@id='dd']").sendKeys(data.get(0).get(0));
	}


	@And("^Select Usage Type$")
	public void Select_Usage_Type(DataTable usagetype) throws Throwable {
		System.out.println("Select Usage Type");
		List<List<String>> data = usagetype.raw();
		getVisibleElementByXpath("//label[text()='"+data.get(0).get(0)+"']").click();
	}


	@Then("^See validation of invalid Postalcode on POLK on P2$")
	public void seeValidationOfInvalidPostalCode(DataTable validationOfPostalCode) throws Throwable {
		System.out.println("See validation of invalid Postalcode on POLK");
		List<List<String>> data = validationOfPostalCode.raw();
		getVisibleElementByXpath("//small[contains(text(),'" + data.get(0).get(0) + "') or contains(text(),'" + data.get(0).get(1) + "')]");
	}

	@When("^Click on Submit button on POLK Build and Price on P2$")
	public void clickOnSubmitButtonOnPolkBuildAndPrice() throws Throwable {
		System.out.println("Click on Submit button on POLK Build and Price");
		getVisibleElementByXpath("//button[text()='Submit']").click();
	}

	@When("^Click on Cancel button on POLK Build and Price on P2$")
	public void clickOnCancelButtonOnPolkBuildAndPrice() throws Throwable {
		System.out.println("Click on Cancel button on POLK Build and Price");
		getVisibleElementByXpath("//button[text()='Cancel']").click();
	}

	@Then("^Verify not seeing prices on POLK Build and Price on P2$")
	public void verifyNotSeeingPricesOnPolk() throws Throwable {
		System.out.println("Verify not seeing prices on POLK Build and Price");
		verifyDontSeeElementVisible("//div[contains(text(),'$')]");
	}

	@Then("^See all components on Select Vehicle page on POLK Build and Price on P2$")
	public void seeAllComponentsOnSelectVehiclePageOnPolk(DataTable location) throws Throwable {
		System.out.println("See all components on Select Vehicle page on POLK Build and Price");
		List<List<String>> data = location.raw();
		getVisibleElementByXpath("//h1[contains(text(),'Build & Price - Select Vehicles')]");
		getVisibleElementByXpath("//h3[contains(text(),'Your Location')]");
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(0) + "')]");
		getVisibleElementByXpath("//span[contains(text(),'Sort By')]");
		getVisibleElementByXpath("//dt[contains(text(),'Passenger Vehicles')]");
		getVisibleElementByXpath("//h3[text()='Fiesta']");
		getVisibleElementByXpath("//h3[text()='All-New Mondeo']");
		getVisibleElementByXpath("//h3[text()='Mustang']");
		getVisibleElementByXpath("//h3[text()='Falcon and G Series MkII']");
	}

	@When("^Click on Sort by criteria on POLK Build and Price on P2$")
	public void clickOnSortByCriteriaOnPolk(DataTable criteria) throws Throwable {
		System.out.println("Click on Sort by criteria on POLK Build and Price");
		List<List<String>> data = criteria.raw();
		getVisibleElementByXpath("//span[contains(text(),'Sort By')]").click();
		getVisibleElementByXpath("//a[text()='Price']");
		getVisibleElementByXpath("//a[text()='Fuel']");
		getVisibleElementByXpath("//a[text()='Body Style']");
		getVisibleElementByXpath("//a[text()='" + data.get(0).get(0) + "']").click();
	}

	@Then("^See all components on Select Vehicles page on POLK P2$")
	public void seeAllComponentsOnSelectVehiclesPageOnPolk(DataTable parameter) throws Throwable {
		System.out.println("See all components on Select Vehicles page on POLK");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//h1[text()='Build & Price – Select Vehicle']");
		waitTillElementVisible_Xpath("//dt[text()='Commercial Vehicles']");
		getVisibleElementByXpath("//dt[text()='Commercial Vehicles']");
		getVisibleElementByXpath("//a[text()='" + data.get(0).get(0) + "']");

		for (int i = 1; i <= 6; i++) {
			getVisibleElementByXpath("//h3[text()='" + data.get(0).get(i) + "']");
		}
	}

	@When("^Click on any Vehicle on Select Vehicles page on POLK P2$")
	public void clickOnAnyVehicleOnSelectVehiclesPageOnPolk(DataTable parameter) throws Throwable {
		System.out.println("Click on any Vehicle on Select Vehicles page on POLK");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//img[contains(@src,'" + data.get(0).get(0) + "')]");
		clickOnLinks("//img[contains(@src,'" + data.get(0).get(0) + "')]");
	}

	@Then("^See all components on chosen Vehicle on POLK P2$")
	public void seeAllComponentsOnChosenVehicleOnPolk(DataTable parameter) throws Throwable {
		System.out.println("See all component on chosen Vehicle on POLK");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//h2[text()='Choose the Path For Your']");
		getVisibleElementByXpath("//b[text()='" + data.get(0).get(0) + "']");
		getVisibleElementByXpath("//h3[text()='See Offers Near You']");
		getVisibleElementByXpath("//h3[text()='Build your own']");
		getVisibleElementByXpath("//a[text()='See Offers Near You']");
		getVisibleElementByXpath("//a[text()='Start Your Build']");
	}

	@When("^Click on Start your build button on POLK P2$")
	public void clickOnStartYourBuildButtonOnPolk() throws Throwable {
		System.out.println("Click on Start your build button on POLK");
		getVisibleElementByXpath("//a[text()='Start Your Build']").click();
	}

	@Then("^See all components on Vehice Build and Price POLK P2$")
	public void seeAllComponentsOnVehiceBuildAndPricePolk(DataTable parameter) throws Throwable {
		System.out.println("See all components on Vehice Build and Price POLK");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//div[@class='nav-header-center']//a[@class='logo']");
		waitTillElemVisiblebyXpath("//div[@class='price-title']//p[contains(text(),'Drive Away Price')]", 240);

		for (int i = 0; i <= 4; i++) {
			getVisibleElementByXpath("//div[@class='left-scrolling-content']//div[@id='" + data.get(0).get(i) + "']");
		}

		getVisibleElementByXpath("//*[contains(text(),'" + data.get(0).get(5) + "')]");
		getVisibleElementByXpath("//div[@class='price-title']//p[contains(text(),'Drive Away Price')]");
		getVisibleElementByXpath("//div[@class='heading']//a[text()='Summary']");
//		//getVisibleElementByXpath("//div[@class='heading']//a[text()='Request a Call']");
//		getVisibleElementByXpath("//div[@class='reviewAndCTA cta-desktop']//a[text()='Request a Call']");
//		getVisibleElementByXpath("//div[@class='reviewAndCTA cta-desktop']//a[text()='Review & Save']");

		waitTillElemVisiblebyXpath("//div[@class='price-title']//span[contains(text(),'$')]", 240);

		WebElement driveAwayPrice=getVisibleElementByXpath("//div[@class='price-title']//span[contains(text(),'$')]");

		if(driveAwayPrice.getText().split(" ")[1] != null) {
			System.out.println("Drive Away price is :" + driveAwayPrice.getText());
		}else {
			Assert.assertFalse("Issue in Drive Away Price, for more info please check on UI: ", true);
		}

	}

	@Then("^See all components on Select Vehice page on POLK Build and Price after Sorting by Body Style$")
	public void seeAllComponentsOnSelectVehiclePageOnPolkAfterSortingByBodyStyle() throws Throwable {
		System.out.println(
				"See all components on Select Vehice page on POLK Build and Price after Sorting by Body Style");
		getVisibleElementByXpath("//dt[contains(text(),'Passenger Vehicles')]");
		getVisibleElementByXpath("//h3[text()='Fiesta']");
		getVisibleElementByXpath("//h3[text()='All-New Mondeo']");
		getVisibleElementByXpath("//h3[text()='Mustang']");
		getVisibleElementByXpath("//h3[text()='Falcon and G Series MkII']");
	}

	@When("^Click on nameplate on POLK Build and Price on P2$")
	public void clickOnVehicleOnPolk(DataTable nameplate) throws Throwable {
		System.out.println("Click on nameplate on POLK Build and Price");
		List<List<String>> data = nameplate.raw();
		getVisibleElementByXpath("//img[@src='" + data.get(0).get(0) + "']").click();
	}

	@Then("^See all components on nameplate page on POLK Build and Price on P2$")
	public void seeAllComponentsOnNamePlatePageOnPolk(DataTable nameplate) throws Throwable {
		System.out.println("See all components on nameplate page on POLK Build and Price");
		List<List<String>> data = nameplate.raw();
		getVisibleElementByXpath("//b[text()='" + data.get(0).get(0) + "']");
		getVisibleElementByXpath("//h3[text()='See Offers Near You']");
		getVisibleElementByXpath("//a[text()='See Offers Near You']");
		getVisibleElementByXpath("//h3[text()='Build your own']");
		getVisibleElementByXpath("//a[text()='Start Your Build']");
	}

	@When("^Click on feature on nameplate page on POLK Build and Price on P2$")
	public void clickOnFeatureOnNamePlatePageOnPolk(DataTable feature) throws Throwable {
		System.out.println("Click on feature on nameplate page on POLK Build and Price");
		List<List<String>> data = feature.raw();
		getVisibleElementByXpath("//a[text()='" + data.get(0).get(0) + "']").click();
	}

	@Then("^See all components on Latest Offers page on POLK Build and Price on P2$")
	public void seeAllComponentsOnLatestOffersPageOnPolk() throws Throwable {
		System.out.println("See all components on Lastest Offers page on POLK Build and Price");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//h2[text()='Go Further With Good Value']");
		getVisibleElementByXpath("//h2[text()='Latest Offers']");
		getVisibleElementByXpath("//span[text()='Sort by']");
		getVisibleElementByXpath("//h4[text()='CAR']");
		getVisibleElementByXpath("//span[text()='Fiesta Ambiente']");
		getVisibleElementByXpath("//span[text()='Fiesta Trend']");
		getVisibleElementByXpath("//span[text()='Fiesta Sport']");

	}

	@Then("^See all components on Start Your Build page on POLK Build and Price on P2$")
	public void seeAllComponentsOnStartYourBuildPageOnPolk(DataTable parameters) throws Throwable {
		System.out.println("See all components on Start Your Build page on POLK Build and Price");
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath("//strong[text()='" + data.get(0).get(0) + "']");
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(1) + "')]");
		getVisibleElementByXpath("//span[contains(text(),'" + data.get(0).get(2) + "')]");
		getVisibleElementByXpath("//a[text()='" + data.get(0).get(3) + "']");
		getVisibleElementByXpath("//p[contains(text(),'" + data.get(0).get(4) + "')]");
		getVisibleElementByXpath("//a[text()='" + data.get(0).get(5) + "']");
	}

	@Then("^Verify seeing parameters passing to Service Layer on POLK Build and Price on P2$")
	public void verifySeeingParametersPassingToServiceLayerOnPolk(DataTable parameters) throws Throwable {
		System.out.println("Verify seeing parameters passing to Service Layer on POLK Build and Price");
		List<List<String>> data = parameters.raw();
		waitTillElemVisiblebyXpath(data.get(0).get(1), 30);
		waitTillElemVisiblebyXpath(data.get(0).get(3), 30);
		getVisibleElementByXpath(data.get(0).get(0), data.get(0).get(1));
		getVisibleElementByXpath(data.get(0).get(2), data.get(0).get(3));
	}

	@When("^Click on button on POLK Build and Price on P2$")
	public void clickOnDriveButtonOnPolk(DataTable button) throws Throwable {
		System.out.println("Click on Drive button on POLK Build and Price");
		List<List<String>> data = button.raw();
		getVisibleElementByXpath("//span[text()='" + data.get(0).get(0) + "']").click();
	}

	@Then("^Verify not seeing invalid thing on POLK Build and Price on P2$")
	public void verifyNotSeeingInvalidThingOnPolk(DataTable parameters) throws Throwable {
		System.out.println("Verify not seeing invalid thing on POLK Build and Price");
		List<List<String>> data = parameters.raw();
		verifyDontSeeElementVisible("//p[text()='" + data.get(0).get(0) + "']");
	}

	@Then("^See page redirected to link contains \"(.*?)\" on P2$")
	public void seeThePageRedirectedToLinkContains(String link) throws Throwable {
		System.out.println("See page redirected to link contains");
		String url = driver.getCurrentUrl();
		System.out.println("xxxxxxxxxxxxxxxx" + url);
		if (!url.contains(link)) {
			throw new Exception("Redirect to incorrect link");
		}
	}

	@When("^Select a Vehicle on P2$")
	public void selectAVehicle(DataTable vehicleName) throws Throwable {
		System.out.println("Select a Vehicle");

		List<List<String>> data = vehicleName.raw();
		getVisibleElementByXpath("//div[@class='item-content']/h3[text()='" + data.get(0).get(0) + "']/preceding-sibling::div/a/img").click();
	}

	@Then("^See the browser redirect to Build and Price page \"(.*?)\" on P2$")
	public void seeRedirectionToBuildAndPrice(String baseLink) throws Throwable {
		System.out.println("See page redirected to Build and Price page");
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		String url = driver.getCurrentUrl();
		if (!url.contains(baseLink)) {
			throw new Exception("Redirect to incorrect link");
		}
	}

	@When("^Click on Packages on P2$")
	public void clickOnPakages() throws Throwable {
		System.out.println("Click on Pakages");
		getVisibleElementByXpath("//span[contains(text(),'Packages')]").click();
	}

	@When("^Click on Packages on P2 Production$")
	public void clickOnPakagesInProd() throws Throwable {
		System.out.println("Click on Pakages");
		getVisibleElementByXpathNoMovement("//div[7]//span[text()='Packages']").click();
	}

	@When("^Click on kind of vehicle on P2$")
	public void clickOnKindOfVehicle(DataTable kindOfVehicle) throws Throwable {
		System.out.println("Click on kind of vehicle");

		List<List<String>> data = kindOfVehicle.raw();
		try {
			driver.findElement(By.xpath("//span[contains(text(),'" + data.get(0).get(0) + "')]")).click();
		} catch (Exception e) {
		}
	}

	@Then("^See the price updated correctly on P2$")
	public void seeThePriceUpdated(DataTable priceUpdated) throws Throwable {
		System.out.println("See the price updated correctly");
		Thread.sleep(5000);

		List<List<String>> data = priceUpdated.raw();
		WebElement getPrice=getVisibleElementByXpath(data.get(0).get(0));
		if(getPrice.getText().length()<2) {
			Assert.assertFalse("Updated Price does not display", true);
		}else {
			System.out.println("Updated Price displayed is:"+getPrice.getText() );
		}
	}


	@Then("^See the price updated is not empty$")
	public void See_the_price_updated_is_not_empty(DataTable priceUpdated) throws Throwable {
		System.out.println("See the price updated is not empty");
		Thread.sleep(5000);

		List<List<String>> data = priceUpdated.raw();
		WebElement getPrice=getVisibleElementByXpath(data.get(0).get(1));
		if(getPrice.getText().length()>1) {
			System.out.println("Price on screen is: "+ getPrice.getText());
		}else {
			Assert.assertFalse("Price does not display:" + getPrice.getText(), true);
		}

	}


	@And("^Click on Figo Hatchback on P2 Mx$")
	public void clickOnTransitFigo() throws Throwable {
		System.out.println("Click on Figo Hatchback");
		getVisibleElementByXpath("//div[@id='showroom-vehicle-card']/div/dl[2]/dd/div/div[1]/div/div[1]/a/img").click(); 
	}

	@And("^Click on color on P2 IND$")
	public void clickOnColorInIndia(DataTable parameter) throws Throwable {
		List<List<String>> data = parameter.raw();
		System.out.println("Click on " + data.get(0).get(0) + " in Color and Trim");
		getVehiclePrice=super.convertStrtoDoubletoInt(driver.findElement(By.xpath("//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'₹')]")).getText().split("₹")[1].split("RSS")[0]);
		getVisibleElementByXpath("//label[contains(@ng-model,'carSlidesExt')]//span[text()!='' and contains(@class,'label-text') and position()=1]").click();
		tempStr=getVisibleElementByXpath("//label[contains(@ng-model,'carSlidesExt')]//span[text()!='' and contains(@class,'label-text') and position()=1]").getText();
		addConfigPrice=super.convertStrtoDoubletoInt(driver.findElement(By.xpath("//label[contains(@ng-model,'carSlidesExt')]//span[text()!='' and contains(@class,'label-text') and position()=1]//following-sibling::span/p")).getText().split("₹")[1].split("RSS")[0]);
	}	

	@And("^See your vehicle details on P2$")
	public void seeYourVehicleDetails(DataTable parameters) throws Throwable {
		System.out.println("See your vehicle details");
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath(tempStr, data.get(0).get(1));
		getVisibleElementByXpath(data.get(0).get(2), data.get(0).get(3));
	}

	@Then("^See the price updated correctly without movement on P2 $")
	public void seeThePriceUpdatedNoMovement(DataTable priceUpdated) throws Throwable {
		System.out.println("See the price updated correctly");

		List<List<String>> data = priceUpdated.raw();
		getVisibleElementByXpathNoMovement(data.get(0).get(0), data.get(0).get(1));
	}

	@When("^Click on Color & Trim on P2$")
	public void clickOnColorAndTrim() throws Throwable {
		System.out.println("Click on Color & Trim");
		getVisibleElementByXpathNoMovement("//span[contains(text(),'Color & Trim')]").click();
	}

	@When("^Click on Color on P2$")
	public void clickOnColor() throws Throwable {
		System.out.println("Click on Color");
		getVisibleElementByXpathNoMovement("//a[contains(text(),'Colour')]").click();
	}

	@When("^Click on Race Red color on P2$")
	public void clickOnRaceRedColor() throws Throwable {
		System.out.println("Click on Race Red color");
		getVisibleElementByXpathNoMovement("//span[contains(text(),'Mars Red')]").click();
	}

	@When("^Click on Ruby Red color on P2$")
	public void clickOnRubyRedColor() throws Throwable {
		System.out.println("Click on Ruby Red color");
		getVisibleElementByXpathNoMovement("//span[contains(text(),'Ruby Red')]").click();
	}

	@When("^Click on Absolute Black color on P2$")
	public void clickOnAbsoluteBlackColor() throws Throwable {
		System.out.println("Click on Absolute Black color");
		getVisibleElementByXpathNoMovement("//span[contains(text(),'Absolute Black')]").click();
	}
	
	@When("^Click on Trim on P2$")
	public void clickOnTrim() throws Throwable {
		System.out.println("Click on Trim");
		getVisibleElementByXpathNoMovement("//a[contains(text(),'Trim')]").click();
	}

	@When("^Click on Cloth Seat Trim on P2$")
	public void clickOnClothSeatTrim() throws Throwable {
		System.out.println("Click on Cloth Seat Trim");
		getVisibleElementByXpathNoMovement("//span[contains(text(),'Cloth Seat Trim')]").click();
	}
	@When("^Click on Accessories on P2$")
	public void clickOnAccessories() throws Throwable {
		System.out.println("Click on Accessories");
		getVisibleElementByXpathNoMovement("//a[contains(text(),'Accessories')]").click();
	}

	@When("^Click on Accessories on P2 IND$")
	public void clickOnAccessoriesInIndia() throws Throwable {
		System.out.println("Click on Accessories");
		waitTillElementVisible_Xpath("//a[contains(text(),'Accessories')]");
		getVisibleElementByXpath("//a[contains(text(),'Accessories')]").click();
		getVehiclePrice=super.convertStrtoDoubletoInt(driver.findElement(By.xpath("//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'₹')]")).getText().split("₹")[1].split("RSS")[0]);
	}

	@When("^Click on Car Cover on P2 IND$")
	public void clickOnCarCoverInIndia() throws Throwable {
		System.out.println("Click on Car Cover");
		Thread.sleep(10000);
		getVisibleElementByXpath("//span[contains(text(),'Car Cover - Regular')]").click();
		addConfigPrice=super.convertStrtoDoubletoInt(driver.findElement(By.xpath("//span[contains(text(),'Car Cover - Regular')]//following-sibling::span/p")).getText().split("₹")[1].split("RSS")[0]);
	}

	@When("^Click on Color and Trim on P2 IND$")
	public void clickOnColorAndTrimInIndia() throws Throwable {
		System.out.println("Click on Color and Trim");
		getVehiclePrice=super.convertStrtoDoubletoInt(driver.findElement(By.xpath("//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'₹')]")).getText().split("₹")[1].split("RSS")[0]);
		getVisibleElementByXpathNoMovement("//div[@class='left-sidebar-nav']//span[contains(text(),'Color')]").click();
		addConfigPrice=0;
	}

	@And("^Click on Smoke Grey on P2 IND$")
	public void clickOnSmokeGreyInIndia() throws Throwable {
		System.out.println("Click on Smoke Grey");
		getVisibleElementByXpath("//span[contains(text(),'Smoke Grey')]");
	}

	@And("^Click on Leather Gear Knob Plum option on P2$")
	public void clickOnLeatherGearKnobOption() throws Throwable {
		System.out.println("Click on Leather Gear Knob Plum");
		getVisibleElementByXpathNoMovement("//span[contains(text(),'Leather Gear Knob')]").click();
	}

	@And("^Click on Tow ball option on P2 Aus$")
	public void clickOnTowBallOptionInAus() throws Throwable {
		System.out.println("Click on Tow ball option");
		getVisibleElementByXpath("//span[contains(text(),'Tow ball')]").click();
	}

	@When("^Click on Fancy Fuel Lid option on P2 Aus$")
	public void clickOnFancyFuelLidOptionInAus() throws Throwable {
		System.out.println("Click on Fancy Fuel Lid option");
		getVisibleElementByXpath("//span[contains(text(),'Fancy Fuel Lid')]").click();
	}

	@And("^Click on Transit Gasolina on P2 Mx$")
	public void clickOnTransitGasolinaInMx() throws Throwable {
		System.out.println("Click on Transit Gasolina");
		getVisibleElementByXpath("//img[@src='/content/dam/Ford/website-assets/latam/mx/nameplate/transit-gasolinia-2016/thumbnails/FMX_nav_showroom_camiones_23_Transit_Gasolina_2016.jpeg']").click();
	}

	@When("^Click on Accessories on P2 in Production$")
	public void clickOnAccessoriesInProd() throws Throwable {
		System.out.println("Click on Accessories");
		getVisibleElementByXpathNoMovement("//div[7]//span[contains(text(),'Accessories')]").click();
	}

	@And("^Click on Sort by Accessory Price on P2$")
	public void clickOnSortByAccessoryPrice() throws Throwable {
		System.out.println("Click on Sort By Price On Accessories");
		getVisibleElementByXpathNoMovement("//div[@id='accessories']/ul/li[2]/div[2]/div[2]/label[contains(text(),'Sort by Accessory Price')]").click();
	}

	@Then("^See the prices are sorted on P2$")
	public void seeThePricesAreSorted() throws Throwable {
		System.out.println("See the prices are sorted");
		getVisibleElementByXpathNoMovement("//p[@class='ng-binding' and contains(text(),'450')]");
	}

	@When("^Click on Accessory Price Item on P2$")
	public void clickOnAccessoryPriceItem() throws Throwable {
		System.out.println("Click on Accessory Price Item");
		getVisibleElementByXpathNoMovement("//p[@class='ng-binding' and contains(text(),'450')]").click();
	}

	@And("^Click on Sort by Accessory Name on P2$")
	public void clickOnSortByAccessoryName() throws Throwable {
		System.out.println("Click on Sort By Name on Accessories");
		getVisibleElementByXpathNoMovement(
				"//div[@id='accessories']/ul/li[2]/div[2]/div[2]/label[contains(text(),'Sort by Accessory Name')]")
		.click();
	}

	@Then("^See the names are sorted on P2$")
	public void seeTheNamesAreSorted() throws Throwable {
		System.out.println("See the names are sorted");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Alloy Wheels')]");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Car Cover')]");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Chrome Bumper Protector')]");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Daytime Running Lamps')]");
	}

	@When("^Click on Accessory Name Item on P2$")
	public void clickOnAccessoryNameItem() throws Throwable {
		System.out.println("Click on Accessory Name Item");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Alloy Wheels')]").click();
	}

	@Then("^See Pricing Summary on P2 Production$")
	public void seePricingSummaryInProduction() throws Throwable {
		System.out.println("See pricing Summary");
		getVisibleElementByXpath("//h3[contains(text(),'Drive Away Price')]");
	}

	@Then("^See landing page components loaded without performance issue on Credit on P2 Production$")
	public void seeLandingPageComponentsLoadedOnCreditInProduct() throws Throwable {
		System.out.println("See landing page components loaded without performance issue");
		getVisibleElementByXpath("//a[text()='View Finance Plans']"); // View Finance Plans
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']/p/a[text()='Why Ford Credit']"); // Why Ford
		// Credit
		getVisibleElementByXpath("//a[text()='View Offers']"); // View Offers
		getVisibleElementByXpath("//input[@id='mini-lad-input']"); // LAD input
		getVisibleElementByXpath("//a[text()='Use Advanced Search']");
		getVisibleElementByXpath("//div[@class='content desktop hideForMobile']//a[text()='Why Ford Credit']");
		getVisibleElementByXpath("//div[@class='content desktop hideForMobile']//a[text()='Finance Plans']");
		getVisibleElementByXpath("//div[@class='content desktop hideForMobile']//a[text()='Customer Guide']");
	}

	@When("^Click on View Finance Plans on P2$")
	public void clickOnViewFinancePlans() throws Throwable {
		System.out.println("Click on View Finance Plans");
		getVisibleElementByXpath("//a[text()='View Finance Plans']").click();
	}

	@When("^Click on Why Ford Credit on P2$")
	public void clickOnWhyFordCredit() throws Throwable {
		System.out.println("Click on Why Ford Credit");
		getVisibleElementByXpath("//a[text()='Why Ford Credit']").click();
	}

	@When("^Click on View Offers on P2$")
	public void clickOnViewOffers() throws Throwable {
		System.out.println("Click on View Offers");
		getVisibleElementByXpath("//a[text()='View Offers']").click();
	}

	@Then("^See mini LAD on P2$")
	public void seeMiniLad() throws Throwable {
		System.out.println("See mini LAD");
		getVisibleElementByXpath("//input[@id='mini-lad-input']");
	}

	@When("^Input location in mini LAD on P2$")
	public void inputLocationInMiniLad(DataTable location) throws Throwable {
		System.out.println("Input location in mini LAD");

		List<List<String>> data = location.raw();
		getVisibleElementByXpath("//*[@id='mini-lad-input']").clear();
		getVisibleElementByXpath("//*[@id='mini-lad-input']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//button[@class='submit-btn search-submit']").click();
	}

	@When("^Click on LAD Expander button on P2$")
	public void clickLADExpander() throws Throwable {
		System.out.println("Click on LAD Expander button");
		getVisibleElementByXpath("//div[@class='expander-head']/a[contains(@class,'expander')]").click();
	}

	@When("^See the first result on Map on P2$")
	public void seeTheFirstResultOnMap(DataTable foundPosition) throws Throwable {
		System.out.println("See the first result on Map");

		List<List<String>> data = foundPosition.raw();
		getVisibleElementByXpath("//h4/a[@class='pinboxDealerUrl' and contains(text(),'" + data.get(0).get(0) + "')]");
	}

	@When("^See the first result on Dealer Map on P2$")
	public void seeTheFirstResultOnDealerMap(DataTable foundPosition) throws Throwable {
		System.out.println("See the first result on Dealer Map");

		List<List<String>> data = foundPosition.raw();
		getVisibleElementByXpath("//h3[contains(text(),'" + data.get(0).get(0) + "')]");
	}

	@When("^Click on Dealer Detail on P2$")
	public void clickOnDealerDetail() throws Throwable {
		System.out.println("Click on Dealer Detail");
		getVisibleElementByXpath("//i[@class='icon-details']").click();
	}

	@When("^Click on drop down menu on P2$")
	public void clickOnDropDownMenu() throws Throwable {
		System.out.println("Click on drop down menu");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//strong[text()='Ford Credit']").click();
	}

	@Then("^See drop down menu on P2$")
	public void seeDropDownMenu() throws Throwable {
		System.out.println("See drop down menu");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='Ford Credit']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='About Ford Credit']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='Finance Plans']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='Why Ford Credit']");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='Offers']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='Customer Guide']");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='Self-help/FAQs']");
	}

	@When("^Click on Ford Credit on drop down menu on P2$")
	public void clickOnFordCreditOnDropDownMenu() throws Throwable {
		System.out.println("Click on Ford Credit on drop down menu");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='Ford Credit']").click();
	}

	@When("^Click on About Ford Credit on drop down menu on P2$")
	public void clickOnAboutFordCreditOnDropDownMenu() throws Throwable {
		System.out.println("Click on About Ford Credit on drop down menu");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='About Ford Credit']").click();
	}

	@When("^Click on Finance Plans on drop down menu on P2$")
	public void clickOnFinancePlansOnDropDownMenu() throws Throwable {
		System.out.println("Click on Finance Plans on drop down menu");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='Finance Plans']").click();
	}

	@When("^Click on Why Ford Credit on drop down menu on P2$")
	public void clickOnWhyFordCreditOnDropDownMenu() throws Throwable {
		System.out.println("Click on Why Ford Credit on drop down menu");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='Why Ford Credit']").click();
	}

	@When("^Click on Offers on drop down menu on P2$")
	public void clickOnOffersOnDropDownMenu() throws Throwable {
		System.out.println("Click on Offers on drop down menu");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='Offers']")
		.click();
	}

	@When("^Click on Customer Guide on drop down menu on P2$")
	public void clickOnCustomerGuideOnDropDownMenu() throws Throwable {
		System.out.println("Click on Customer Guide on drop down menu");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='Customer Guide']").click();
	}

	@When("^Click on Self help FAQs on drop down menu on P2$")
	public void clickOnSelfHelpFaqsOnDropDownMenu() throws Throwable {
		System.out.println("Click on Self help FAQs on drop down menu");
		getVisibleElementByXpath(
				"//div[@class='desktop hideForMobile']/ul[@class='main-menu']//a[text()='Self-help/FAQs']").click();
	}

	@When("^Click on Contact Us link on P2$")
	public void clickOnContactUsLink() throws Throwable {
		System.out.println("Click on Contact Us link");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//a[@href='#contact' and text()='Contact Us']")
		.click();
	}

	@Then("^See the Contact on P2$")
	public void seeTheContact() throws Throwable {
		System.out.println("See the Contact");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//span[text()='Question About Ford Credit?']");
		getVisibleElementByXpath("//span[text()='FAQs']");
		getVisibleElementByXpath("//span[@class='btn hideForMobile']/span[@data-phonenum='1800-419-2812']");
		getVisibleElementByXpath("//span[text()='Email']");
		getVisibleElementByXpath("//span[text()='Twitter']");
	}

	@When("^Click on Application Form link on P2$")
	public void clickOnApplicationFormLink() throws Throwable {
		System.out.println("Click on Application Form link");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//a[text()='Application Form']").click();
	}

	@When("^Click on Loan Process link on P2$")
	public void clickOnLoanProcessLink() throws Throwable {
		System.out.println("Click on Loan Process link");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//a[text()='Loan Process']").click();
	}

	@When("^Click on Models on P2 INT$")
	public void clickOnModels() throws Throwable {
		System.out.println("Click on Models");
		getVisibleElementByXpathNoMovement("//a[contains(text(),'Models')]").click();
	}

	@When("^Click on kind of vehicle on P2 INT$")
	public void clickOnKindOfVehicleInt(DataTable kindOfVehicle) throws Throwable {
		System.out.println("Click on kind of vehicle");

		List<List<String>> data = kindOfVehicle.raw();
		getVisibleElementByXpathNoMovement("//span[contains(text(),'" + data.get(0).get(0) + "')]").click();
	}

	@When("^Click on Drive on P2 INT$")
	public void clickOnDriver() throws Throwable {
		System.out.println("Click on Drive");
		//		getVisibleElementByXpathNoMovement("//span[contains(text(),'Drive') and contains(@class,'Color')]").click();
		getVisibleElementByXpathNoMovement("//a[contains(text(),'Transmission')]").click();
	}

	@Then("^See Drive list on P2 INT$")
	public void seeDriveList() throws Throwable {
		System.out.println("See Drive list");
		getVisibleElementByXpathNoMovement(
				//				"//span[contains(text(),'1.5L Ti-VCT DOHC Petrol/5-speed Manual Transmission')]");
				"//span[contains(text(),'1.5L EcoBoost Petrol Engine/8-Speed Automatic Transmission')]");
	}

	@When("^Click on Accessories on P2 INT$")
	public void clickOnAccessoriesInt() throws Throwable {
		System.out.println("Click on Accessories");
		//		getVisibleElementByXpathNoMovement("//span[contains(text(),'accessories')]").click();
		getVisibleElementByXpathNoMovement("//a[contains(text(),'Accessories')]").click();
	}

	@Then("^Click on Exterior on P2 INT$")
	public void click_on_Exterior() throws Throwable {
		System.out.println("Click on Exterior");
		getVisibleElementByXpathNoMovement("//a[contains(text(),'Exterior')]").click();
	}


	@Then("^Click on Interior on P2 INT$")
	public void click_on_Interior() throws Throwable {
		System.out.println("Click on Interior");
		getVisibleElementByXpathNoMovement("//a[contains(text(),'Interior')]").click();
	}

	@Then("^Click on Safety&Security on P2 INT$")
	public void click_on_Safety_Security() throws Throwable {
		System.out.println("Click on Safety & Security");
		getVisibleElementByXpathNoMovement("//a[contains(text(),'Safety & Security')]").click();
	}
	
	@Then("^Click on Utility&Safety on P2 INT$")
	public void click_on_Utility_Safety() throws Throwable {
		System.out.println("Click on Utility & Safety");
		getVisibleElementByXpathNoMovement("//a[contains(text(),'Utility & Safety')]").click();
	}

	@Then("^Click on Safety Triangle on P2 INT$")
	public void click_on_Safety_Triangle() throws Throwable {
		System.out.println("Click on Safety Triangle");
		getVisibleElementByXpathNoMovement("//span[contains(text(),'Safety triangle')]").click();
	}
	
	@Then("^Click on Belt Pad on P2 INT$")
	public void click_on_Belt_Pad() throws Throwable {
		System.out.println("Click on Belt Pad");
		getVisibleElementByXpathNoMovement("//span[contains(text(),'BELT PAD')]").click();
	}
	
	@And("^Click on Sort by Name on P2 INT$")
	public void clickOnSortByNameInt() throws Throwable {
		System.out.println("Click on Sort By Name");
		getVisibleElementByXpathNoMovement("//label[contains(text(), 'Sort by Name')]").click();
	}
	@Then("^See the names are sorted for Exterior on P2 INT$")
	public void see_the_names_are_sorted_for_Exterior() throws Throwable {
		System.out.println("See the names are sorted for Exterior");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Alloy Wheel Lock Nut Set')]");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Bumper Protection - Brushed Stainless Steel (Hatch)')]");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Folding J Style Kayak Carrier - Rhino-Rack')]");

	}


	@Then("^See the names are sorted for Interior on P2 INT$")
	public void see_the_names_are_sorted_for_Interior() throws Throwable {
		System.out.println("See the names are sorted for Interior");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Bonnet Protector - Clear')]");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Boot Scuff Guard')]");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Cargo Organiser - Large')]");
	}

	@Then("^See the names are sorted on P2 INT$")
	public void seeTheNamesAreSortedInt() throws Throwable {
		System.out.println("See the names are sorted");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Alloy wheel lock nut set')]");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'AlloyFiestaAmb_Trend')]");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Bonnet Protector - clear - EGR')]");
	}

	@And("^Click on Sort by Price on P2 INT$")
	public void clickOnSortByPriceInt() throws Throwable {
		System.out.println("Click on Sort By Price");
		getVisibleElementByXpath("//*[@id='accessories'] //label[contains(text(), 'Sort by Price')]").click();
	}

	@Then("^See the prices are sorted on P2 INT$")
	public void seeThePricesAreSortedInt() throws Throwable {
		System.out.println("See the prices are sorted");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//p[@class='ng-binding' and contains(text(),'$ 51')]");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//p[@class='ng-binding' and contains(text(),'$ 71')]");
		getVisibleElementByXpath(
				"//*[@id='accessories']//li[@class='ng-scope active']//p[@class='ng-binding' and contains(text(),'$ 139')]");
	}
	@When("^Click on Accessory Name Item on P2 INT$")
	public void clickOnAccessoryNameItemInt() throws Throwable {
		System.out.println("Click on Accessory Name Item");
		getVisibleElementByXpathNoMovement(
				"//*[@id='accessories']//li[@class='ng-scope active']//span[contains(text(),'Alloy Wheel Lock Nut Set')]")		
		.click();
	}

	@When("^Click on Accessory Price Item on P2 INT$")
	public void clickOnAccessoryPriceItemInt() throws Throwable {
		System.out.println("Click on Accessory Price Item");
		getVisibleElementByXpath("//span[contains(text(),'Towing - plug adaptor - 7 pin flat to 7 pin round')]").click();
	}

	@When("^Click on Review & Save button on P2$")
	public void clickOnReviewAndSaveButton() throws Throwable {
		System.out.println("Click on Review & Save button");
		try {
			getVehiclePrice=super.convertStrtoDoubletoInt(driver.findElement(By.xpath("//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'₹')]")).getText().split("₹")[1]);
			addConfigPrice=0;
		}catch(Exception e) {}

		getVisibleElementByXpath("//a[text()='Review & Save' or text()='Review and Save']").click();
	}

	@Then("^See Pricing Summary on P2$")
	public void seePricingSummary() throws Throwable {
		System.out.println("See pricing Summary");
		Thread.sleep(5000);
		waitTillElemVisiblebyXpath("//h3[contains(text(),'Drive Away Price*') or contains(text(),'Pricing  Summary') or contains(text(),'Review & Save')]", 360);
		getVisibleElementByXpath("//h3[contains(text(),'Drive Away Price*') or contains(text(),'Pricing  Summary') or contains(text(),'Review & Save')]");
	}

	@When("^Click on Request a Quote button on Review & Save on P2$")
	public void clickOnRequestQuoteButtonOnReviewAndSave() throws Throwable {
		System.out.println("Click on Request a Quote button");
		getVisibleElementByXpath("//a[contains(text(),'Request a Quote')]").click();

	}

	@And("^Specify the Contact Information on P2$")
	public void specifyTheContactInformation(DataTable contact) throws Throwable {
		System.out.println("Specify the Contact Information");

		List<List<String>> data = contact.raw();
		getVisibleElementByXpath("//span[@id='select2-chosen-4' and contains(text(),'Please Select')]").click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(0) + "')]").click();
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_FIRST_NAME']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='MOBILE_PHONE_NUMBER']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='CUSTOMER_ADDRESS_LINE1']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//span[@id='select2-chosen-6' and contains(text(),'Please Select')]").click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(5) + "')]").click();
		getVisibleElementByXpath("//span[@id='select2-chosen-8' and contains(text(),'Please Select')]").click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(6) + "')]").click();
		getVisibleElementByXpath("//span[@id='select2-chosen-10' and contains(text(),'series1')]").click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(7) + "')]").click();
		getVisibleElementByXpath("//span[@id='select2-chosen-12' and contains(text(),'Please Select')]").click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(8) + "')]").click();
		getVisibleElementByXpath("//span[contains(text(), 'News and Announcements')]").click();
		getVisibleElementByXpath("//span[contains(text(), 'Special offers & Discounts')]").click();
		getVisibleElementByXpath("//span[contains(text(), 'Information for owners')]").click();
		getVisibleElementByXpath("//span[contains(text(), 'Information for owners')]").click();
		getVisibleElementByXpath("//span[contains(text(), 'Events & Activaties Invitations')]").click();
		getVisibleElementByXpath("//span[contains(text(), 'No thank you')]").click();
		getVisibleElementByXpath("//span[contains(text(), 'I acknowledge I will receive news')]").click();
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys("ABCDE");
	}

	@And("^Specify the Contact Information on P2 Production$")
	public void specifyTheContactInformationInProd(DataTable contact) throws Throwable {
		System.out.println("Specify the Contact Information");

		List<List<String>> data = contact.raw();
		getVisibleElementByXpath("//span[@class='select2-chosen' and contains(text(),'Please Select')]").click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(0) + "')]").click();
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_FIRST_NAME']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_LAST_NAME']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='MOBILE_PHONE_NUMBER']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//span[@class='select2-chosen' and contains(text(),'Please Select Province')]")
		.click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(5) + "')]").click();
		getVisibleElementByXpath("//span[@class='select2-chosen' and contains(text(),'Please Select Khet/Amphoe')]")
		.click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(6) + "')]").click();
		getVisibleElementByXpath("//span[@class='select2-chosen' and contains(text(),'Please Select Dealer')]").click();
		getVisibleElementByXpath(
				"//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(7) + "')]").click();
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys("ABCDE");
	}

	@Then("^Close the Thank you page on P2$")
	public void closeThankYouPage() throws Throwable {
		System.out.println("Close the Thank you page");
		getVisibleElementByXpath("//i[contains(@class,'icon-plus')]").click();
	}

	@And("^Click on Submit button on Request code on P2$")
	public void clickOnSubmitButtonOnRequestCode() throws Throwable {
		System.out.println("Click on Submit button on Request code");
		getVisibleElementByXpath("//button[contains(text(),'Submit')]").click();
	}

	@And("^Click on Submit button on Request code on P2 Production$")
	public void clickOnSubmitButtonOnRequestCodeInProduct() throws Throwable {
		System.out.println("Click on Submit button on Request code");
		getVisibleElementByXpath("//button[contains(text(),'Request Quote')]").click();
	}

	@And("^Click on Cancel button on Request code on P2$")
	public void clickOnCancelButtonOnRequestCode() throws Throwable {
		System.out.println("Click Cancel button on Request code on P2");
		getVisibleElementByXpath("//button[contains(text(),'Cancel')]").click();
	}

	@When("^Click on Payment Presenter button on Review & Save on P2$")
	public void clickOnPaymentPresenterButtonOnReviewAndSave() throws Throwable {
		System.out.println("Click on Payment Presenter button on Revew & Save");
		getVisibleElementByXpath("//a[contains(text(),'Payment Presenter')]").click();
		Thread.sleep(10000);
	}

	@And("^See the model updated correctly on P2$")
	public void seeTheModelUpdated(DataTable modelUpdated) throws Throwable {
		System.out.println("See the model updated correctly");
		getVisibleElementByXpath("//*[@id='s2id_select-models']/a").click();

		List<List<String>> data = modelUpdated.raw();
		getVisibleElementByXpath(data.get(0).get(0), data.get(0).get(1));
	}

	@Then("^See Payment Estimator on P2$")
	public void seePaymentEstimatorOnChinesePage() throws Throwable {
		System.out.println("See Payment Estimator");
		getVisibleElementByXpath("//*[@id='nameplateName']");
		getVisibleElementByXpath("//span[contains(text(), '¥')]");
		getVisibleElementByXpath("//a[contains(text(), 'Change Vehicle')]");
		getVisibleElementByXpath("//span[contains(text(), 'Downpayment')]");
		getVisibleElementByXpath("//span[contains(text(), '贷款金额')]");
		getVisibleElementByXpath("//span[contains(text(),'延保套餐')]");
		getVisibleElementByXpath("//h3[contains(text(),'方案总结')]");
		getVisibleElementByXpath("//h3[contains(text(),'方案比较')]");
		getVisibleElementByXpath("//span[text()='Save']");
		getVisibleElementByXpath("//span[text()='Email']");
		getVisibleElementByXpath("//span[text()='Print']");
		getVisibleElementByXpath("//h2[text() = 'Apply for Credit']");
		getVisibleElementByXpath("//h2[text() = 'Finance Options']");
		getVisibleElementByXpath("//h2[contains(text(), 'Shopper')]");
		getVisibleElementByXpath("//span[contains(text(), 'Questions About Ford Credit?')]");
		getVisibleElementByXpath("//span[contains(text(), 'FAQs')]");
		getVisibleElementByXpath("//span[contains(text(), '400-888-3231')]");
		getVisibleElementByXpath("//*[@id='btn-cb-wechat']");
	}

	@When("^Click on Share button on P2$")
	public void clickOnShareButton() throws Throwable {
		System.out.println("Click on Share button");
		getVisibilityElementByXpath("//i[@class='icon-share']").click();
	}

	@Then("^See the sharing popup on P2$")
	public void seeSharingPopup() throws Throwable {
		System.out.println("See the sharing popup");
		getVisibleElementByXpath("//a[@class='icon-share-twitter']");
		getVisibleElementByXpath("//a[@class='icon-share-pinterest']");
		getVisibleElementByXpath("//a[@class='icon-share-email']");
		getVisibleElementByXpath("//a[@class='icon-share-facebook']").click();
	}

	// click download PDF and verify Dialog box exist and Close the dialog box and
	// pass it, if not then false
	@Then("^Verify the Download File on P2$")
	public void VerifyDownloadFile() throws Throwable {
		System.out.println("Verify the download file");
		getVisibleElementByXpath("//i[@class='icon-empty-file-csv']").click();
		Thread.sleep(10000);
		String str="";
		File dir = new File("/Users/sureshnagarajan/Downloads");
		File[] files = dir.listFiles();
		//		if (files == null || files.length == 0) {
		//			return null;
		//		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		str = lastModifiedFile.toString();

		if(str.contains("build_and_price")) {
			System.out.println("File Downloaded Sucessfully");
		}
		else {
			System.out.println("File is not Downloaded");
		}
		// FirefoxProfile profile = new FirefoxProfile();
		// profile.setPreference("browser.download.folderList", 2);
		// profile.setPreference("browser.download.dir","c:\\");
		// profile.setPreference( "plugin.disable_full_page_plugin_for_types",
		// "application/pdf" );
		// profile.setPreference("browser.download.manager.alertOnEXEOpen", false);
		// profile.setPreference("browser.helperApps.neverAsksaveToDisk",
		// "application/msword,application/csv,text/csv,image/png ,image/jpeg,
		// application/pdf, text/html,text/plain,application/octet-stream");
		// profile.setPreference("browser.download.manager.showWhenStarting",false);
		// profile.setPreference( "pdfjs.disabled", true );
		// WebDriver driver = new FirefoxDriver(profile);
		//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//		driver.get(getProfileURL(link));
		//		driver.navigate().to(link);
		//		Thread.sleep(10000);
		//		getVisibleElementByXpath("//i[@class='icon-empty-file-csv']").click();
		// WebElement linkSaveAsPDF =
		// getVisibleElementByXpath("//span[contains(text(),'Save as PDF')]");
		// driver.get(getProfileURL(linkSaveAsPDF.getAttribute("href")));
	}

	@Then("^See landing page components loaded without performance issue on Credit on P2$")
	public void seeLandingPageComponentsLoadedOnCredit() throws Throwable {
		System.out.println("See landing page components loaded without performance issue");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//a[text()='点击观看视频']");
		getVisibleElementByXpath("//div[@id='splitter']/div/div/div[1]//img");
		getVisibleElementByXpath("//div[@id='splitter']/div/div/div[2]//img");
		getVisibleElementByXpath("//div[@id='splitter']/div/div/div[3]//img");
		getVisibleElementByXpath("//span[contains(text(),'常见问题')]");
		getVisibleElementByXpath("//span[contains(text(),'400-888-3231')]");
		getVisibleElementByXpath("//span[contains(text(),'Email')]");
		getVisibleElementByXpath("//span[contains(text(),'福特微金融')]");
		// getVisibleElementByXpath("//h2[contains(@class,'hideForMobile') and
		// text()='金融产品']"); // Financial product
		// getVisibleElementByXpath("//h2[text()='车贷指南']"); // Car loan guide
		// getVisibleElementByXpath("//h2[text()='车贷计算器']"); // Car loan calculator
		// getVisibleElementByXpath("//span[text()='了解更多关于福特金融？']"); // Read more about
		// Ford finance?
	}

	@Then("^See all components loaded without performance issue on Payment Estimator on P2$")
	public void seeAllComponentsLoadedWithoutPerformanceIssueOnPaymentEstimator(DataTable components) throws Throwable {
		System.out.println("See all components loaded without performance issue on Payment Estimator");
		List<List<String>> data = components.raw();
		getVisibleElementByXpath("//h4[@id='nameplateName']");
		getVisibleElementByXpath("//span[@id='select2-chosen-1']");
		getVisibleElementByXpath("//span[@class='ng-binding' and contains(text(),'¥')]");
		getVisibleElementByXpath("//div[contains(@class,'desktop')]//a[text()='Change Vehicle']");
		getVisibleElementByXpath("//input[@name='downPayment']");
		getVisibleElementByXpath("//span[contains(@class,'ui-slider-handle')]");
		getVisibleElementByXpath("//div[@class='rs-txt ng-binding']");
		getVisibleElementByXpath("//input[@name='amountFinanced']");
		getVisibleElementByXpath("//div[@id='s2id_autogen4' or @id='s2id_autogen6']/a");
		getVisibleElementByXpath("//span[contains(text(),'Apply for Ford Credit today')]");
		getVisibleElementByXpath(
				"//div[contains(@class,'hideForMobile')]//span[contains(text(),'Buy or lease with flexible terms and special offers')]");
		getVisibleElementByXpath(
				"//div[contains(@class,'hideForMobile')]//span[contains(text(),'Find out how to apply and what to bring to the dealership')]");
		verifySeeingElementVisible(data.get(0).get(0));
		verifySeeingElementVisible(data.get(0).get(1));
		verifySeeingElementVisible(data.get(0).get(2));
	}

	@When("^Click on Change Vehicle button on P2$")
	public void clickOnChangeVehicleButton() throws Throwable {
		System.out.println("Click on Change Vehicle button");
		getVisibleElementByXpath("//*[contains(@class,'vehicle-section-one')]//a[contains(@class,'cta-button-secondary') or contains(@class,'cta-button-primary')]").click();
		//		getVisibleElementByXpath("//div[contains(@class,'confirm-overlay-inner')]//button").click();
	}

	@When("^Click on Change Vehicle button on P2 Mexico$")
	public void clickOnChangeVehicleButtonInMexico() throws Throwable {
		System.out.println("Click on Change Vehicle button");
		getVisibleElementByXpath(
				"//div[contains(@class,'desktop')]//a[text()='Cambiar de vehículo' or text()='Cambar de vehiculo']")
		.click();
	}

	@Then("^See the vehicle selection overlay on P2$")
	public void seeVehicleSelectionOverlay() throws Throwable {
		System.out.println("See the vehicle selection overlay");
		getVisibleElementByXpath(
				"//div[@id='showroom-vehicle-card']/div[2]/div[1]//span[contains(text(),'Model Year')]");
		getVisibleElementByXpath("//div[@id='showroom-vehicle-card']/div[2]/div[1]//label[contains(text(),'Current')]");
		getVisibleElementByXpath(
				"//div[@id='showroom-vehicle-card']/div[2]/div[1]//label[contains(text(),'Previous')]");
		getVisibleElementByXpath("//h1[contains(text(),'Select Models')]");
	}

	@When("^Click on Focus on P2$")
	public void clickOnFocus() throws Throwable {
		System.out.println("Click on Focus");
		getVisibleElementByXpath(
				"//div[5]//img[@src='/etc/designs/guxfoap/clientlibs/guxfoap/img/modelDetailsCompare/car-grayscale.png']")
		.click();
	}

	@When("^Click on Sort By menu list on P2$")
	public void clickOnSortByMenuList() throws Throwable {
		System.out.println("Click on Sort By menu list");
		getVisibleElementByXpath("//span[text()='Sort By']").click();
	}

	@Then("^See options of Sort By menu list on P2$")
	public void seeOptionsOfSortByMenuList() throws Throwable {
		System.out.println("See options of Sort By menu list");
		getVisibleElementByXpath("//a[text()='Sort By Category']");
		getVisibleElementByXpath("//a[text()='Sort By Price']");
	}

	@And("^Select Sort By Price option on P2$")
	public void selectSortByPriceOption() throws Throwable {
		System.out.println("Select Sort By Price option");
		getVisibleElementByXpath("//a[text()='Sort By Price']").click();
	}

	@Then("^See list of models sorting by price on P2$")
	public void seeListOfModelsSortingByPrice(DataTable modelsList) throws Throwable {
		System.out.println("See list of models sorting by price");
		List<List<String>> data = modelsList.raw();
		verifySeeingElementVisible(data.get(0).get(0));
	}

	@When("^Select Sort By Category option on P2$")
	public void selectSortByCategoryOption() throws Throwable {
		System.out.println("Select Sort By Category option");
		getVisibleElementByXpath("//a[text()='Sort By Category']").click();
	}

	@Then("^See list of models sorting by category on P2$")
	public void seeListOfModelsSortingByCategory(DataTable modelsList) throws Throwable {
		System.out.println("See list of models sorting by category");
		List<List<String>> data = modelsList.raw();
		verifySeeingElementVisible(data.get(0).get(0));
	}

	@When("^Click on model sorting by category on P2$")
	public void clickOnModels(DataTable models) throws Throwable {
		System.out.println("Click on model sorting by category");
		List<List<String>> data = models.raw();
		try {
			getVisibleElementByXpath(data.get(0).get(0)).click();
		} catch (Exception e) {
			getVisibleElementByXpath(data.get(0).get(1)).click();
		}
	}

	// @Then("^See all components of MSRP model on P2$")
	// public void seeAllComponentsOfMsrpModel() throws Throwable
	// {
	// System.out.println("See all components of MSRP model");
	// getVisibleElementByXpath("//h4[@id='nameplateName' and
	// contains(text(),'福特经典福克斯')]");
	// getVisibleElementByXpath("//div[@class='vehicle-price']/span[text()='¥99,800']");
	// getVisibleElementByXpath("//img[@src='/content/dam/Ford/website-assets/ap/ch/nameplate/focus/hatch-1-8L-Ambiente-MT/hatch-1-8L-Ambiente-MT.png']");
	// getVisibleElementByXpath("//div[@id='s2id_select-models']/a");
	// }

	@Then("^See all components of MSRP model on P2$")
	public void seeAllComponentsOfMsrpModel() throws Throwable {
		System.out.println("See all components of MSRP model");
		// getVisibleElementByXpath("//h4[@id='nameplateName' and
		// contains(text(),'福特福睿斯')]");
		// getVisibleElementByXpath("//div[@class='vehicle-price']/span[text()='¥96,800']");
		getVisibleElementByXpath("//div[@id='s2id_select-models']/a");
	}

	@And("^Select kind in Mustang menu list on P2$")
	public void selectKindInMustangMenuList(DataTable parameter) throws Throwable {
		System.out.println("Select kind in Mustang menu list");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(0) + "')]").click();
	}

	@Then("^See all components of Focus model on P2 Mexico$")
	public void seeAllComponentsOfMexicoModel() throws Throwable {
		System.out.println("See all components of Mexico model");
		getVisibleElementByXpath("//h4[@id='nameplateName' and contains(text(),'Focus')]");
		getVisibleElementByXpath("//div[@class='vehicle-price']/span[contains(text(),'260')]");
		try {
			getVisibleElementByXpath("//img[@src='/content/dam/Ford/website-assets/latam/mx/nameplate/focus/smt-4-ptas/smt-4-ptas.png']");
		} catch (Exception e) {
			System.out.println("Vehicle image does not exist.");
		}

		getVisibleElementByXpath("//div[@id='s2id_select-models']/a");
	}

	@When("^Select time on Term on P2$")
	public void selectTimeOnTerm(DataTable term) throws Throwable {
		System.out.println("Select time on Term on P2");
		List<List<String>> data = term.raw();
		getVisibleElementByXpath("//div[@id='s2id_autogen2']/a[@class='select2-choice']").click();
		getVisibleElementByXpath("//div[@class='select2-result-label' and contains(text(),'" + data.get(0).get(0) + "')]").click();
	}

	// @Then("^See all components of Mustang model on P2$")
	// public void seeAllComponentsOfMustangModel() throws Throwable
	// {
	// System.out.println("See all components of Mustang model");
	// getVisibleElementByXpath("//h4[@id='nameplateName' and
	// contains(text(),'MUSTANG')]");
	// getVisibleElementByXpath("//div[@class='vehicle-price']/span[text()='¥399,800']");
	// getVisibleElementByXpath("//img[@src='/content/dam/Ford/website-assets/ap/ch/nameplate/new-mustang/2-3T-performance/2-3T-performance.png']");
	// getVisibleElementByXpath("//div[@id='s2id_select-models']/a");
	// }

	@Then("^See all components of Mustang model on P2$")
	public void seeAllComponentsOfMustangModel() throws Throwable {
		System.out.println("See all components of Mustang model");
		getVisibleElementByXpath("//h4[@id='nameplateName']");
		getVisibleElementByXpath("//div[@class='vehicle-price']/span[text()='¥104,300']");
		getVisibleElementByXpath("//div[@id='s2id_select-models']/a");
	}

	@When("^Click on Mustang menu list on P2$")
	public void clickOnMustangMenuList() throws Throwable {
		System.out.println("Click on Mustang menu list");
		Thread.sleep(3000);
		try {
			getVisibleElementByXpath("//div[@id='s2id_select-models']/a").click();
		} catch (Exception e) {

		}
	}

	@Then("^See options on Mustang menu list on P2$")
	public void seeOptionsOnMustangMenuList(DataTable mustangList) throws Throwable {
		System.out.println("See options on Mustang menu list");
		List<List<String>> data = mustangList.raw();
		verifySeeingElementVisible(data.get(0).get(0));
	}

	@When("^Select kind of Mustang on P2$")
	public void selectKindOfMustang(DataTable mustangOption) throws Throwable {
		System.out.println("Select kind of Mustang");
		List<List<String>> data = mustangOption.raw();
		// selectOptionOnMenuList(data.get(0).get(0),data.get(0).get(1));
		String xpath = "//ul[@class='select2-results']/li[%d]";
		for (int i = 1; i <= 4; i++) {
			MustangModels mustang = MustangModels.valueOf(data.get(0).get(i - 1));
			String locator;
			try {
				getVisibleElementByXpath("//div[@id='s2id_select-models']/a").click();
			} catch (Exception e) {
			}
			switch (mustang) {
			case M23T性能版:
				locator = String.format(xpath, i);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath(data.get(0).get(5), data.get(0).get(4));
				break;

			case M23T运动版:
				locator = String.format(xpath, i);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath(data.get(0).get(6), data.get(0).get(4));
				break;

			case V8GT性能版:
				locator = String.format(xpath, i);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath(data.get(0).get(7), data.get(0).get(4));
				break;

			case V8GT运动版:
				locator = String.format(xpath, i);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath(data.get(0).get(8), data.get(0).get(4));
				break;

			default:
				throw new Exception("Invalid Suggestion name: " + mustang);
			}

		}

	}

	@When("^Click on Save button on P2 Credit$")
	public void clickOnSaveButtonOnCredit() throws Throwable {
		System.out.println("Click on Save button");
		getVisibleElementByXpath("//span[text()='Save']").click();
	}

	@When("^Click on Email button on P2 Credit$")
	public void clickOnEmailButtonOnCredit() throws Throwable {
		System.out.println("Click on Email button");
		getVisibleElementByXpath("//span[text()='Email']").click();
	}

	@When("^Click on Print button on P2 Credit$")
	public void clickOnPrintButtonOnCredit() throws Throwable {
		System.out.println("Click on Print button");
		getVisibleElementByXpath("//span[text()='Print']").click();
	}

	protected WebElement getVisibleElementByXpathDriverFactory(String xpath, Browser browser) throws Throwable {
		// driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);

		DriverFactory driverFactory = new DriverFactory();
		WebDriver driver = driverFactory.createRemoteWebDriver(browser);
		int count = 36; // total is 1m 30'
		int interval = 10000; // 10s
		while (count > 0) {

			List<WebElement> elements = driver.findElements(By.xpath(xpath));
			for (WebElement element : elements) {
				boolean isDisplayed = false;
				try {
					moveToElement(element);
					isDisplayed = element.isDisplayed();
				} catch (Exception ex) {
					isDisplayed = false;
				}

				if (isDisplayed) {
					return element;
				}
			}
			Thread.sleep(interval);
			count--;
		}

		throw new Exception("No found xpath: " + xpath);

	}

	protected void moveToElement(WebElement element) throws Throwable {
		JavascriptExecutor je = (JavascriptExecutor) driver;

		je.executeScript("arguments[0].scrollIntoView(true);", element);
		je.executeScript("window.scrollBy(0,-100)", ""); // down over the sticker menu
	}

	@Then("^See the price updated correctly$")
	public void seeThePriceUpdatedcorrectly(DataTable priceUpdated) throws Throwable {
		System.out.println("See the price updated correctly");
		Thread.sleep(5000);
		List<List<String>> data = priceUpdated.raw();
		int NewVehiclePrice=getVehiclePrice+addConfigPrice;
		CompareIndiaVehiclePrice(formatNumberIncludeCommas(NewVehiclePrice),data.get(0).get(0));
		if(!data.get(0).get(1).isEmpty() && !data.get(0).get(1).equals("NA"))
		{
			CompareIndiaVehiclePrice(formatNumberIncludeCommas(NewVehiclePrice),data.get(0).get(1));
		}	
	}

	@Then("^See CTAs and links are functional on the page$")
	public void see_CTAs_and_links_are_functional_on_the_page() throws Throwable{
		System.out.println("See CTAs and links are functional on the page");
		waitTillElemVisiblebyXpath("//*[@class='site-link-title' and contains(text(),'Shop')]", 240);
		getVisibleElementByXpath("//*[@class='site-link-title' and contains(text(),'Vehicles')]");
		getVisibleElementByXpath("//*[@class='site-link-title' and contains(text(),'SUV')]");
		getVisibleElementByXpath("//*[@class='site-link-title' and contains(text(),'Shop')]");
		getVisibleElementByXpath("//*[@class='site-link-title' and contains(text(),'Safety')]");
		getVisibleElementByXpath("//*[@class='site-link-title' and contains(text(),'Technology')]");
		getVisibleElementByXpath("//*[@class='site-link-title' and contains(text(),'Owner')]");
		getVisibleElementByXpath("//*[@class='site-link-title' and contains(text(),'Sign In')]");
	}

	@When("^Click on Build & Price CTA$")
	public void click_on_Build_Price_CTA() throws Throwable{
		System.out.println("Click on Build & Price CTA");
		getVisibleElementByXpath("//a[contains(text(),'Build & Price')]").click();

	}

	@Then("^Verify that page is navigated to Select Location page$")
	public void verify_that_page_is_navigated_to_Select_Location_page() throws Throwable{
		System.out.println("Verify that page is navigated to Select Location page");
		waitTillElemVisiblebyXpath("//button[contains(text(),'Submit') or contains(text(),'Cancel')]", 240);
		getVisibleElementByXpath("//*[contains(text(),'Select City')]");
	}

	@Then("^Enter Location$")
	public void enter_Location(DataTable arg1) throws Throwable{
		System.out.println("Enter Location");
		List<List<String>> data = arg1.raw();
		getVisibleElementByXpath("//span[contains(@class,'select2-chosen')]").click();
		waitTillElemVisiblebyXpath("//input[@type='text' and contains(@class,'input')]", 240);
		getVisibleElementByXpath("//input[@type='text' and contains(@class,'input')]").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//span[text()='"+data.get(0).get(0)+"']").click();
	}

	@Then("^click on submit$")
	public void click_on_submit() throws Throwable{
		System.out.println("click on submit");
		getVisibleElementByXpath("//button[contains(text(),'Submit')]").click();
		waitTillElemVisiblebyXpath("//*[text()='Your Location']",240);
	}

	@Then("^Verify that page is Navigated to select vehicle page$")
	public void verify_that_page_is_Navigated_to_select_vehicle_page() throws Throwable{
		System.out.println("Verify that page is Navigated to select vehicle page");
		waitTillElemVisiblebyXpath("//*[contains(text(),'Fiesta')]/preceding-sibling::*//img", 240);
		getVisibleElementByXpath("//*[text()='Build & Price - Select Vehicles']");
	}

	@When("^Click on Sort By \"(.*?)\"$")
	public void click_on_Sort_By(String arg1) throws Throwable {
		System.out.println("Click on Sort By " +arg1);
		waitTillElemVisiblebyXpath("//div[contains(@class,'sort')]//span[not(contains(@class,'hide'))]", 240);
		waitTillElemVisiblebyXpath("//h3[contains(text(),'Fiesta') or contains(text(),'Mustang') or contains(text(),'EcoSport')]", 240);
		List<WebElement> getVehickeList=driver.findElements(By.xpath("//div[contains(@class,'vehicle-card-group')]//dl[not(contains(@style,'none'))]//div[contains(@class,'item-content')]//h3"));
		for(int CmpVehicle=0;CmpVehicle<getVehickeList.size();CmpVehicle++) {
			VehiclBfrSort.add(getVehickeList.get(CmpVehicle).getText().trim());
		}

		getVisibleElementByXpath("//span[contains(text(),'Sort By')]").click();
		getVisibleElementByXpath("//*[@sortby='"+arg1+"']").click();
		Thread.sleep(5000);
	}

	@Then("^Available vehicles should be sorted as per sort selection \"(.*?)\"$")
	public void available_vehicles_should_be_sorted_as_per_sort_selection(String arg1, DataTable arg2) throws Throwable {
		System.out.println("Available vehicles are sorted as per sort selection by "+arg1);
		List<List<String>> data=arg2.raw();
		List<WebElement> getVehickeList=driver.findElements(By.xpath("//div[contains(@class,'vehicle-card-group')]//dl[not(contains(@style,'none'))]//div[contains(@class,'item-content')]//h3"));
		for(int CmpVehicle=0;CmpVehicle<getVehickeList.size();CmpVehicle++) {
			VehiclAftrSort.add(getVehickeList.get(CmpVehicle).getText().trim());
		}

		if(!VehiclBfrSort.equals(VehiclAftrSort)) {
			Assert.assertTrue("Vehicle Sort By "+ arg1 + " working as expected.", true);	
		}else {
			Assert.assertFalse("Vehicle Sort By "+ arg1 + " does not work as expected.", true);
		}
	}

	@When("^Select a Vehicle$")
	public void select_a_Vehicle(DataTable arg1) throws Throwable{
		System.out.println("Select a Vehicle");
		List<List<String>> data=arg1.raw();
		waitTillElementVisible_Xpath("//img[contains(@src,'" + data.get(0).get(0) + "')]");
		getVisibleElementByXpath("//img[contains(@src,'" + data.get(0).get(0) + "')]");
		clickOnLinks("//img[contains(@src,'" + data.get(0).get(0) + "')]");
	}

	@When("^Select a China Vehicle$")
	public void select_a_ChinaVehicle(DataTable arg1) throws Throwable{
		System.out.println("Select a China Vehicle");
		List<List<String>> data=arg1.raw();

		if(data.get(0).get(0).contains("ANY")) {
			getVisibleElementByXpath("//*[text()!='']/preceding-sibling::*//img[contains(@src,'/nameplate/') or contains(@src,'/img/')]").click();
		}else {	
			waitTillElemVisiblebyXpath("//*[contains(text(),'"+data.get(0).get(0)+"')]", 240);
			getVisibleElementByXpath("//*[contains(text(),'"+data.get(0).get(0)+"')]").click();
		}
		waitTillElemVisiblebyXpath("//*[contains(text(),'Change Vehicle')]", 240);
		try {
			Thread.sleep(10000);	
			getVehiclePrice=super.convertStrtoDoubletoInt(driver.findElement(By.xpath("//div[contains(@class,'vehicle-price')]//span[text()!='']")).getText().split("¥")[1]);
			addConfigPrice=0;
		}catch(Exception e) {}
	}

	@Then("^Click on start a New build$")
	public void click_on_start_your_build_CTA() throws Throwable{
		System.out.println("Click on start a New build");
		getVisibleElementByXpath("//section//a[contains(text(),'tart')]").click();
	}

	@Then("^See page navigating to Build and Price page$")
	public void see_page_navigating_to_Build_and_Price_page(){

	}

	@When("^Click on Hamburger menu$")
	public void click_on_Hamburger_menu() throws Throwable {
		System.out.println("Click on Hamburger menu");
		driver.navigate().refresh();
		waitTillElemVisiblebyXpath("//span[contains(@class,'hamburger')]", 240);
		getVisibleElementByXpath("//span[contains(@class,'hamburger')]").click();	
	}

	@When("^Click on Change Vehicle$")
	public void click_on_Change_Vehicle() throws Throwable {
		System.out.println("Click on Change Vehicle");
		getVisibleElementByXpath("//*[contains(text(),'Change Vehicle')]").click();
	}

	@Then("^Click on YES on confirmation overlay$")
	public void click_on_YES_on_confirmation_overlay() throws Throwable{
		System.out.println("Click on YES on confirmation overlay");
		waitTillElemVisiblebyXpath("//*[contains(@class,'changeVehicle')]//*[contains(text(),'Yes')]", 240);
		waitTillElemVisiblebyXpath("//*[contains(@class,'changeVehicle')]//*[contains(text(),'Cancel')]", 240);
		getVisibleElementByXpath("//*[contains(@class,'changeVehicle')]//*[contains(text(),'Yes')]").click();
	}

	@Then("^Click on YES for confirmation overlay$")
	public void click_on_YES_for_confirmation_overlay() throws Throwable{
		System.out.println("Click on YES on confirmation overlay");
		waitTillElemVisiblebyXpath("//*[contains(@class,'startBuild')]//*[contains(text(),'Yes')]", 240);
		waitTillElemVisiblebyXpath("//*[contains(@class,'startBuild')]//*[contains(text(),'Cancel')]", 240);
		getVisibleElementByXpath("//*[contains(@class,'startBuild')]//*[contains(text(),'Yes')]").click();
	}

	@Then("^see user is navigate to vehicle selection page$")
	public void see_user_is_navigate_to_vehicle_selection_page() throws Throwable{
		System.out.println("see user is navigate to vehicle selection page");
		waitTillElemVisiblebyXpath("//*[contains(text(),'Fiesta')]/preceding-sibling::*//img", 240);
		waitTillElemVisiblebyXpath("//*[contains(text(),'Build & Price – Select Vehicle')]", 240);
		waitTillElemVisiblebyXpath("//*[contains(text(),'Your Location')]", 240);
		getVisibleElementByXpath("//*[contains(text(),'Build & Price – Select Vehicle')]");
		getVisibleElementByXpath("//*[contains(text(),'Your Location')]");
	}

	@When("^Click on Book a Test Drive$")
	public void click_on_Book_a_Test_Drive() throws Throwable {
		System.out.println("Click on Book a Test Drive");
		getVisibleElementByXpath("//section//*[contains(text(),'Book')]").click();
	}

	@Then("^User enters the details and submits the test drive request$")
	public void user_enters_the_details_and_submits_the_test_drive_request(DataTable param) throws Throwable{
		System.out.println("User enters the details and submits the test drive request");
		List<List<String>> data=param.raw();
		waitTillElemVisiblebyXpath("//span[contains(text(),'Title')]/following-sibling::div//span[@id!='']", 240);
		getVisibleElementByXpath("//span[contains(text(),'Title')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+data.get(0).get(0)+"']").click();
		getVisibleElementByXpath("//span[contains(text(),'Full Name')]/following-sibling::*//input").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='MOBILE_PHONE_NUMBER']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//input[@name='CUSTOMER_ADDRESS_LINE1']").sendKeys(data.get(0).get(5));
		getVisibleElementByXpath("//span[contains(text(),'Province')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+data.get(0).get(6)+"']").click();
		getVisibleElementByXpath("//span[contains(text(),'Model')]/following-sibling::div//span[@id!='']").click();
		List<WebElement> SelModel=driver.findElements(By.xpath("//div[contains(@class,'select2-drop')]//li/div"));
		SelModel.get(1).click();
		getVisibleElementByXpath("//span[contains(text(),'Series')]/following-sibling::div//span[@id!='']").click();
		List<WebElement> SelSeries=driver.findElements(By.xpath("//div[contains(@class,'select2-drop')]//li/div"));
		SelSeries.get(1).click();
		getVisibleElementByXpath("//span[contains(text(),'vehicle')]/following-sibling::div//span[@id!='']").click();
		List<WebElement> SelTerm=driver.findElements(By.xpath("//div[contains(@class,'select2-drop')]//li/div"));
		SelTerm.get(1).click();
		Thread.sleep(2000);
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys(EnterCaptcha());
		getVisibleElementByXpath("//div[contains(@class,'form-submit')]//*[contains(text(),'rive')]").click();
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==true) {
			System.out.println("Close overlay");
			waitTillElemVisiblebyXpath("//*[@class='icon-plus icon-x']", 240);
			getVisibleElementByXpath("//*[@class='icon-plus icon-x']").click();
		}
	}

	@Then("^Verify thank you page is seen and Close Overlay$")
	public void Verify_thank_you_page_is_seen_and_Close_Overlay() throws Throwable {
		System.out.println("Verify thank you page is seen and Close Overlay");
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==false) {
			waitTillElemVisiblebyXpath("//div[@class='desktop hideForMobile']//*[contains(text(),'Thank You')]", 240);
			getVisibleElementByXpath("//div[@class='desktop hideForMobile']//*[contains(text(),'Thank You')]");

			try {
				System.out.println("Close overlay");
				waitTillElemVisiblebyXpath("//*[@class='icon-plus icon-x']", 240);
				getVisibleElementByXpath("//*[@class='icon-plus icon-x']").click();
			} catch (Exception e) {

			}
		}
	}

	@When("^Click on Review & Save button$")
	public void click_on_Review_Save_button() throws Throwable{
		System.out.println("Click on Review & Save button");
		getVisibleElementByXpath("//section//*[contains(text(),'Review')]").click();
	}

	@When("^Click on Back link in Review & Save page$")
	public void click_on_Back_link_in_Review_Save_page() throws Throwable{
		System.out.println("Click on Back link in Review & Save page");
		getVisibleElementByXpath("//*[contains(text(),'Back')]").click();
	}

	@Then("^see User is directing to Build and Price page$")
	public void see_User_is_directing_to_Build_and_Price_page() throws Throwable{
		System.out.println("see User is directing to Build and Price page");
		waitTillElemVisiblebyXpath("//div[not(contains(@class,'mobile-btns'))]//*[contains(text(),'Review') and contains(@class,'cta')]", 240);
		getVisibleElementByXpath("//div[not(contains(@class,'mobile-btns'))]//*[contains(text(),'Review') and contains(@class,'cta')]");
	}

	@When("^Click on Go Back to Ford\\.com$")
	public void click_on_Go_Back_to_Ford_com() throws Throwable{
		System.out.println("Click on Go Back to Ford.com");
		getVisibleElementByXpath("//section//*[contains(text(),'Go Back')]").click();
	}

	@Then("^See confirmation overlay is displayed$")
	public void see_confirmation_overlay_is_displayed() throws Throwable{
		System.out.println("See confirmation overlay is displayed");
		waitTillElemVisiblebyXpath("//*[contains(@class,'exit')]//*[contains(text(),'Yes')]", 240);
		waitTillElemVisiblebyXpath("//*[contains(@class,'exit')]//*[contains(text(),'Yes')]", 240);
		getVisibleElementByXpath("//*[contains(@class,'exit')]//*[contains(text(),'Yes')]");
	}

	@When("^user clicks on Cancel button$")
	public void user_clicks_on_Cancel_button() throws Throwable{
		System.out.println("user clicks on Cancel button");
		getVisibleElementByXpath("//*[contains(@class,'exit')]//*[contains(text(),'Cancel')]").click();
	}

	@Then("^See user stays on Build and Price page$")
	public void see_user_stays_on_Build_and_Price_page() throws Throwable{
		System.out.println("See user stays on Build and Price page");
		waitTillElemVisiblebyXpath("//div/*[contains(text(),'Change Vehicle')]", 240);
		getVisibleElementByXpath("//div/*[contains(text(),'Change Vehicle')]");
		getVisibleElementByXpath("//div[not(contains(@class,'mobile-btns'))]//*[contains(text(),'Review') and contains(@class,'cta')]");
	}

	@When("^Click on Change Vehicle link under vehicle nameplate$")
	public void click_on_Change_Vehicle_link_under_vehicle_nameplate() throws Throwable {
		System.out.println("Click on Change Vehicle link under vehicle nameplate");
		getVisibleElementByXpath("//div/*[contains(text(),'Select Vehicle')]").click();
	}

	@When("^Click on Change Model$")
	public void Click_on_Change_Vehicle_link() throws Throwable {
		System.out.println("Click on Change Model");
		getVisibleElementByXpath("//*[contains(text(),'Change')]").click();
	}


	@When("^Click on Drive Away Price arrow$")
	public void click_on_Drive_Away_Price_arrow() throws Throwable {
		System.out.println("Click on Drive Away Price arrow");
		getVisibleElementByXpath("//a[@class='priceDrop']/*[contains(@class,'icon-chevron-thin-down')]").click();
	}

	@Then("^Drive Away Price should be expanded and correct price should be displayed$")
	public void drive_Away_Price_should_be_expanded_and_correct_price_should_be_displayed() throws Throwable{
		System.out.println("Drive Away Price should be expanded and correct price should be displayed");
		int NewVehiclePrice=getVehiclePrice+addConfigPrice;
		CompareIndiaVehiclePrice(formatNumberIncludeCommas(NewVehiclePrice),"//*[contains(text(),'Total Price')]/ancestor::div/following-sibling::span");
		CompareIndiaVehiclePrice(formatNumberIncludeCommas(NewVehiclePrice),"//*[text()='Drive Away Price']/ancestor::div/following-sibling::div/span");
	}

	@Then("^Close expanded window$")
	public void close_expanded_window() throws Throwable{
		System.out.println("Close expanded window");
		getVisibleElementByXpath("//a[@class='priceDrop']/*[contains(@class,'icon-chevron-thin-up')]").click();
	}

	@When("^Click on Save as PDF link$")
	public void click_on_Save_as_PDF_link() throws Throwable{
		System.out.println("Click on Save as PDF link");
		getVisibleElementByXpath("//*[contains(@class,'desktop')]//*[contains(text(),'Save as PDF') or contains(text(),'Print')]").click();
	}

	@Then("^User is able to download the pdf file$")
	public void user_is_able_to_download_the_pdf_file() throws Throwable {
		System.out.println("User is able to download the pdf file");
		File file = new File(Configuration.PATH_TO_FILE_DOWNLOAD);
		int i=0;
		do {
			Thread.sleep(5000);
			i++;
		}while(!file.exists() && i<=72);

		if (file.exists()) {
			System.out.println("File downloaded and Ready to Print: " + Configuration.PATH_TO_FILE_DOWNLOAD);
			file.delete();
		} else {
			Assert.assertFalse("File Download Failed", true);
		}
	}

	@Then("^User is able to download the PDF$")
	public void user_is_able_to_download_the_pdf() throws Throwable {
		System.out.println("User is able to download the pdf");
		CheckFileDownload(Configuration.PATH_VE_FILE_DOWNLOAD);
	}

	@When("^Click on Share link$")
	public void click_on_Share_link() throws Throwable {

		System.out.println("Click on Share link");
		getVisibleElementByXpath("//*[contains(@class,'desktop')]//*[contains(text(),'Share')]").click();
	}

	@Then("^See share popup is displaying$")
	public void see_share_popup_is_displaying() throws Throwable{
		System.out.println("See share popup is displaying");
		waitTillElemVisiblebyXpath("//a[@class='icon-share-facebook']",240);
		waitTillElemVisiblebyXpath("//a[@class='icon-share-twitter']",240);
		waitTillElemVisiblebyXpath("//a[@class='icon-share-pinterest']",240);
		getVisibleElementByXpath("//a[@class='icon-share-facebook']").click();
	}

	@When("^Click on the Request a quote Button$")
	public void click_on_the_Request_a_quote_Button() throws Throwable{
		System.out.println("Click on the Request a quote Button");
		getVisibleElementByXpath("//a[contains(text(),'Request a Quote')]").click();
		waitTillElemVisiblebyXpath("//span[contains(text(),'Full Name')]/following-sibling::*//input",240);
	}


	@When("^Click on the Request a quote Btn$")
	public void click_on_the_Request_a_quote_Btn() throws Throwable{
		System.out.println("Click on the Request a quote Button");
		getVisibleElementByXpath("//a[contains(text(),'Request a Quote')]").click();
		waitTillElemVisiblebyXpath("//input[@name='Name' or @name='INDIVIDUAL_CUST_FIRST_NAME']",240);
	}


	@Then("^user can make a request after filling all the details in the page$")
	public void user_can_make_a_request_after_filling_all_the_details_in_the_page(DataTable param) throws Throwable{
		System.out.println("user can make a request after filling all the details in the page");
		List<List<String>> data=param.raw();
		getVisibleElementByXpath("//span[contains(text(),'Title')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+data.get(0).get(0)+"']").click();
		getVisibleElementByXpath("//span[contains(text(),'Full Name')]/following-sibling::*//input").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='MOBILE_PHONE_NUMBER']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//input[@name='CUSTOMER_ADDRESS_LINE1']").sendKeys(data.get(0).get(5));
		getVisibleElementByXpath("//span[contains(text(),'State')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+data.get(0).get(6)+"']").click();
		getVisibleElementByXpath("//span[contains(text(),'Model')]/following-sibling::div//span[@id!='']").click();
		List<WebElement> SelModel=driver.findElements(By.xpath("//div[contains(@class,'select2-drop')]//li/div"));
		SelModel.get(1).click();
		getVisibleElementByXpath("//span[contains(text(),'Series')]/following-sibling::div//span[@id!='']").click();
		List<WebElement> SelSeries=driver.findElements(By.xpath("//div[contains(@class,'select2-drop')]//li/div"));
		SelSeries.get(1).click();
		getVisibleElementByXpath("//span[contains(text(),'In')]/following-sibling::div//span[@id!='']").click();
		List<WebElement> SelTerm=driver.findElements(By.xpath("//div[contains(@class,'select2-drop')]//li/div"));
		SelTerm.get(1).click();
		Thread.sleep(2000);
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys(EnterCaptcha());
		getVisibleElementByXpath("//div[contains(@class,'form-submit')]//*[contains(text(),'Submit')]").click();  
	}


	@Then("^see Drive away price$")
	public void see_Drive_away_price() throws Throwable {
		System.out.println("see Drive away price");
		waitTillElemVisiblebyXpath("//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]", 240);

		WebDriverWait wait = new WebDriverWait(driver, 360);
		wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//div[@class='rcol']//span[@class='ng-binding']")), "$"));
		waitForPageLoad(driver);
		WebElement DriveAwayPrice=null;
		try {
			DriveAwayPrice=getVisibleElementByXpath("//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]");
		}catch(Exception e) {
			Thread.sleep(120000);
			DriveAwayPrice=getVisibleElementByXpath("//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]");
		}
		if(!DriveAwayPrice.getText().split("$")[0].isEmpty()) {
			System.out.println("Drive Away Price* is:" + DriveAwayPrice.getText());
		}else {
			Assert.assertFalse("Drive Away Price is empty", true);
		}
		String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
		String netData = ((JavascriptExecutor)driver).executeScript(scriptToExecute).toString();
		System.out.println("Test Network Tab: " +netData);
		netData.trim();
		String dfyServiceURL = netData.substring(netData.indexOf("dfy")-14, netData.indexOf("usageType")+11);
		System.out.println("DYF_Service_URL: "+dfyServiceURL);
	}
	
	@Then("^see TH Drive away price$")
	public void see_TH_Drive_away_price() throws Throwable {
		System.out.println("see TH Drive away price");
		waitTillElemVisiblebyXpath("//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'THB')]", 240);

		WebDriverWait wait = new WebDriverWait(driver, 360);
		wait.until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath("//div[@class='rcol']//span[@class='ng-binding']")), "THB"));
		waitForPageLoad(driver);
		WebElement DriveAwayPrice=null;
		try {
			DriveAwayPrice=getVisibleElementByXpath("//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'THB')]");
		}catch(Exception e) {
			Thread.sleep(120000);
			DriveAwayPrice=getVisibleElementByXpath("//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'THB')]");
		}
		if(!DriveAwayPrice.getText().split("$")[0].isEmpty()) {
			System.out.println("Drive Away Price* is:" + DriveAwayPrice.getText());
		}else {
			Assert.assertFalse("Drive Away Price is empty", true);
		}
		String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
		String netData = ((JavascriptExecutor)driver).executeScript(scriptToExecute).toString();
		System.out.println("Test Network Tab: " +netData);
		netData.trim();
		String dfyServiceURL = netData.substring(netData.indexOf("dfy")-14, netData.indexOf("usageType")+11);
		System.out.println("DYF_Service_URL: "+dfyServiceURL);
	}

	public List networkTabDFYServiceURL() {
		String scriptToExecute = "var performance = window.performance || window.mozPerformance || window.msPerformance || window.webkitPerformance || {}; var network = performance.getEntries() || {}; return network;";
		String netData = ((JavascriptExecutor)driver).executeScript(scriptToExecute).toString();
		System.out.println("Test Network Tab: " +netData);
		netData.trim();
		String strarray[] = netData.split(" ");
		List<String> str = new ArrayList<String>();
		List<String> dfyServices = new ArrayList<String>();
		str = Arrays.asList(strarray);
		for (int i=0;i<str.size();i++) {
			if (str.get(i).contains("dfyservices")==true && str.get(i).contains("calcBPD")) {
				String dfyServiceURL = str.get(i).substring(str.get(i).indexOf("calcBPD")-67, str.get(i).indexOf("usageType")+11);
				System.out.println("DYF_Service_URL: "+dfyServiceURL);
				dfyServices.add(dfyServiceURL);
			}
			else {
				//System.out.println("No dfy services");
			}
		}
		return dfyServices;
	}
	static void waitForPageLoad(WebDriver wdriver) {
		WebDriverWait wait = new WebDriverWait(wdriver, 60);

		Predicate<WebDriver> pageLoaded = new Predicate<WebDriver>() {

			public boolean apply(WebDriver input) {
				return ((JavascriptExecutor) input).executeScript("return document.readyState").equals("complete");
			}

		};
		//wait.until(pageLoaded);
	}

	@Then("^Verify Drive away price with SL calls$")
	public void verify_Drive_away_price_with_SL_calls(DataTable arg1) throws Throwable {
		System.out.println("Verify Drive away price with SL calls");
		Thread.sleep(5000);
		List<List<String>> data=arg1.raw();
		Map<Integer,String> getValuesFrmDict=new HashMap<Integer,String>();
		List<String> dfyService_List = networkTabDFYServiceURL();
		for(String s: dfyService_List){
			   System.out.println("Ssss"+s);
			}
		int i=Integer.parseInt(data.get(0).get(1)); 
		String dfyService_URL = (String) dfyService_List.get(i);
		System.out.println("Xxxx"+dfyService_URL);
		String GetSLresponse = "";
		try {
			GetSLresponse = JSONService.getJsonResponse(dfyService_URL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String GetSLresponse=JSONService.getJsonResponse(data.get(0).get(1));
		String getDriveAwayPrice;
		getDriveAwayPrice = getVisibleElementByXpath(data.get(0).get(0)).getText().replace(",","").split(" ")[1];
		getValuesFrmDict=findDriveAwayPriceAU(GetSLresponse);
		int dicItemCnt=getValuesFrmDict.keySet().size();
		String SLdriveawayprice=getValuesFrmDict.get(dicItemCnt-1);
		Double parseDAPrice=Double.parseDouble(SLdriveawayprice);
		int iSLvalue=(int) Math.floor(parseDAPrice);
		int iDAprice=Integer.parseInt(getDriveAwayPrice);
		if(iSLvalue!=iDAprice) {
			if ((iSLvalue - iDAprice) == 1 || (iSLvalue - iDAprice) == -1) {
				System.out.println("Drive Away Price from SL and UI matched.");
			} else {
				Assert.assertFalse("Drive Away Price from SL and UI did not matched. SL value:" + iSLvalue + ", UI value:" + iDAprice, true);
			}
		}else {
			System.out.println("Drive Away Price from SL and UI matched.");
		}
	}

	@Then("^Verify the Model key sending to SL$")
	public void verify_the_Model_key_sending_to_SL(DataTable arg1) throws Throwable {
		List<List<String>> data=arg1.raw();
		Map<Integer,String> getValuesFrmDict=new HashMap<Integer,String>();
		String GetSLresponse=JSONService.getJsonResponse(data.get(0).get(1));
		getValuesFrmDict=findDriveAwayPriceAU(GetSLresponse);
		int dicItemCnt=getValuesFrmDict.keySet().size();
		String SLmodelkey=getValuesFrmDict.get(dicItemCnt-2);

		if(SLmodelkey.isEmpty()) {
			Assert.assertFalse("Model key is empty.", true);
		}else {
			System.out.println("Model key response from SL is: " + SLmodelkey);
			if(SLmodelkey.equals(data.get(0).get(0))) {
				System.out.println("Model key response from SL matched with the expected Key.");
			}else {
				Assert.assertFalse("expected Model key does not matched with the SL response. " + "", true);
			}
		}
	}

	@When("^Click On below color on P2$")
	public void clickColor(DataTable arg1) throws Throwable {
		System.out.println("Click on color");
		List<List<String>> data=arg1.raw();
		getVisibleElementByXpathNoMovement("//span[contains(text(),'"+ data.get(0).get(0) +"')]").click();
		Thread.sleep(3000);
	}

	@When("^Verify No Cost Option text for zero price$")
	public void Verify_No_Cost_Option_text_for_zero_price(DataTable arg1) throws Throwable {
		System.out.println("Verify No Cost Option text for zero price");
		List<List<String>> data=arg1.raw();
		List<WebElement> NoCoseOption = driver.findElements(By.xpath("//*[contains(text(),'"+ data.get(0).get(0) +"')]"));
		if(NoCoseOption.size()>0) {
			System.out.println("No Cost Option text for zero price is exist.");
		}else {
			Assert.assertFalse("No Cost Option text for zero price does not exist.", true);
		}
	}

	@When("^Verify Text should no exist on UI$")
	public void Verify_Text_exist_on_UI(DataTable arg1) throws Throwable {
		System.out.println("Verify Text should no exist on UI");
		List<List<String>> data=arg1.raw();
		List<WebElement> NoCoseOption = driver.findElements(By.xpath("//*[contains(text(),'"+ data.get(0).get(0) +"')]"));
		if(NoCoseOption.size()>0) {
			Assert.assertFalse(data.get(0).get(0) +" is exist.", true);
		}else {
			System.out.println(data.get(0).get(0) +" does not on UI exist as expected.");
		}
	}

	@When("^Select Drive$")
	public void select_Drive(DataTable arg1) throws Throwable {
		List<List<String>> data = arg1.raw();
		waitTillElemVisiblebyXpath("//span[contains(text(),'" + data.get(0).get(0) + "')]", 240);
		getVisibleElementByXpathNoMovement("//span[contains(text(),'" + data.get(0).get(0) + "')]").click();
	}


	@When("^Click on Accessory Sub Tab$")
	public void click_on_Accessory_Sub_Tab(DataTable arg1) throws Throwable {
		List<List<String>> data=arg1.raw();
		waitTillElemVisiblebyXpath("//a[contains(text(),'"+ data.get(0).get(0) +"') and not(contains(@class,'hide'))]", 240);
		getVisibleElementByXpathNoMovement("//a[contains(text(),'" + data.get(0).get(0) + "') and not(contains(@class,'hide'))]").click();
	}

	@When("^Click on Sub Tab$")
	public void click_on_Sub_Tab(DataTable arg1) throws Throwable {
		List<List<String>> data=arg1.raw();
		waitTillElemVisiblebyXpath("//a[contains(text(),'"+ data.get(0).get(0) +"') and not(contains(@class,'hide'))]", 240);
		getVisibleElementByXpathNoMovement("//a[contains(text(),'" + data.get(0).get(0) + "') and not(contains(@class,'hide'))]").click();
	}

	@When("^Select Accessories$")
	public void select_Accessories(DataTable arg1) throws Throwable {
		List<List<String>> data=arg1.raw();
		waitTillElemVisiblebyXpath("//span[contains(text(),'"+ data.get(0).get(0) +"')]", 240);
		getVisibleElementByXpathNoMovement("//span[contains(text(),'"+ data.get(0).get(0) +"')]").click();
		Thread.sleep(3000);
	}

	@When("^Ciick on Change Postal Code link$")
	public void Ciick_on_Change_Postal_Code_link() throws Throwable {
		System.out.println("Ciick on Change Postal Code link");
		getVisibleElementByXpath("//a[contains(text(),'Postal')]").click();
		waitTillElemVisiblebyXpath("//input[@id='dd']", 240);
	}

	@When("^All components loads successfully without performance issue$")
	public void All_components_loads_successfully_without_performance_issue() throws Throwable {
		System.out.println("All components loads successfully without performance issue");
		getVisibleElementByXpath("//div[contains(@class,'vehicle-price')]//span[text()!='']//preceding-sibling::p[text()!='']");
		getVisibleElementByXpath("//div[contains(@class,'vehicle-price')]//span[text()!='']"); //vehicle Price
		getVisibleElementByXpath("//span[contains(@class,'title')]/following-sibling::*[contains(@id,'select-models')]//a[contains(@class,'select2-choice')]");
		getVisibleElementByXpath("//*[contains(@class,'vehicle-section-one')]//a[contains(@class,'cta-button-secondary') or contains(@class,'cta-button-primary')]");
		getVisibleElementByXpath("//*[contains(@class,'disclosure-accordion') and text()!='']");
		getVisibleElementByXpath("//ul[contains(@class,'accordion-regular')]//li[text()!='' and @class='accordion-item']");
	}


	@And("^Check displaying of a vehicle name format$")
	public void Check_displaying_of_a_vehicle_name_format() throws Throwable {
		System.out.println("Check displaying of a vehicle name format");
		System.out.println("Verify display of a vehicle name");
		List<WebElement> vehicleName=driver.findElements(By.xpath("//div[contains(@class,'item-content')]//h3[text()!='']"));
		if(vehicleName.size()<=0) {
			Assert.assertFalse("Vehicle names does not exist", true);
		}

		System.out.println("Verify display of a vehicle Starting Price");
		List<WebElement> StartingPrz=driver.findElements(By.xpath("//div[contains(@class,'item-content')]//h3/following-sibling::div//span[text()!='' and contains(@class,'price')]"));
		if(StartingPrz.size()<=0) {
			Assert.assertFalse("Vehicle price display issue", true);
		}
		if(vehicleName.size()!=StartingPrz.size()) {
			System.out.println("Vehicle price is missing on UI; Vehicle displayed# " + vehicleName.size() + ", Vehicle Prizes displayed# " + StartingPrz.size());
		}
	}

	@Then("^Verify display of vehicle image$")
	public void Verify_display_of_vehicle_image() throws Throwable {
		System.out.println("Verify display of vehicle image");
		getVisibleElementByXpath("//*[@id='nameplateName']"); 
		getVisibleElementByXpath("//div[contains(@class,'vehicle-price')]//span[text()!='']//preceding-sibling::p[text()!='']"); 
		getVisibleElementByXpath("//div[contains(@class,'vehicle-price')]//span[text()!='']"); //vehicle Price
		List<WebElement> getVehImg=driver.findElements(By.xpath("//div[contains(@class,'vehicle-img')]//img"));
		if(getVehImg.size()<=0) {
			System.out.println("Vehicle image is missing on Page");
		}else {
			System.out.println("Vehicle image displayed");
		}
	}

	@Then("^Check elements in Financing Package Selection$")
	public void Check_elements_in_Financing_Package_Selection() throws Throwable {
		System.out.println("Check elements in Financing Package Selection");
		getVisibleElementByXpath("//div[contains(@class,'vehicle-price')]//span[text()!='']//preceding-sibling::p[text()!='']"); 
		getVisibleElementByXpath("//div[contains(@class,'vehicle-price')]//span[text()!='']"); //vehicle Price

		getVisibleElementByXpath("//input[contains(@name,'downPayment')]"); // downpayment
		getVisibleElementByXpath("//div[contains(@class,'rs-txt') and text()!='']"); //downpay ratio
		getVisibleElementByXpath("//input[contains(@name,'amountFinanced')]"); //loan amt
		getVisibleElementByXpath("//div[contains(@id,'s2id_autogen4') or contains(@id,'s2id_autogen6')]"); //terms
		try {
			getVisibleElementByXpath("//div[contains(@id,'s2id_autogen6')]"); //extended warrenty
		}catch(Exception e) {}
		getVisibleElementByXpath("//div[@class='product-desc']//ul/li[text()!='']"); //Financial Product cards

	}

	@Then("^Check elements in Financing Package Selection for AR$")
	public void Check_elements_in_Financing_Package_SelectionAR() throws Throwable {
		System.out.println("Check elements in Financing Package Selection");
		getVisibleElementByXpath("//div[contains(@class,'vehicle-price')]//span[text()!='']//preceding-sibling::p[text()!='']"); 
		getVisibleElementByXpath("//div[contains(@class,'vehicle-price')]//span[text()!='']"); //vehicle Price

		getVisibleElementByXpath("//input[contains(@name,'loanAmount')]"); // downpayment
		getVisibleElementByXpath("//input[contains(@name,'ratepayment')]"); //loan tna
		getVisibleElementByXpath("//div[contains(@class,'terms-dropdown')]//*[contains(@id,'select')]"); //terms
		getVisibleElementByXpath("//input[contains(@name,'withvat')]"); //CFT con IVA
		getVisibleElementByXpath("//input[contains(@name,'downpayment')]");//Desembolso inicial
		getVisibleElementByXpath("//input[contains(@name,'tea')]");//T.E.A.
		getVisibleElementByXpath("//input[contains(@name,'withoutvat')]");//CFT sin IVA

		getVisibleElementByXpath("//*[contains(@class,'accordion-title')]//h3[text()!='']");

	}

	@Then("^Check default value of Down Payment and Amount Financed$")
	public void Check_default_value_of_Down_Payment_and_Amount_Financed() throws Throwable {
		System.out.println("Check default value of Down Payment and Amount Financed");
		Click_on_Finance_optons();
		int addon=0;
		downpayment=Integer.parseInt(getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().substring(1).replaceAll(",", ""));
		try {
			amtfinanced = Integer.parseInt(driver.findElement(By.xpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-amountFinanced') and text()!='']")).getText().substring(1).replaceAll(",", ""));
		} catch (Exception e) {
			amtfinanced = Integer.parseInt(getVisibleElementByXpath("//input[@name='amountFinanced']").getAttribute("value").substring(1).replaceAll(",", ""));
		}
		int ratio=Integer.parseInt(getVisibleElementByXpath("//div[@slider]//span").getAttribute("style").replaceAll("[^-?\\d+]", ""));
		int calculatedDownpay=(int) Math.ceil((getVehiclePrice*20)/100);
		if(driver.findElements(By.xpath("//input[@name='addonAmount']")).size()>0) {
			if(driver.findElement(By.xpath("//input[@name='addonAmount']")).isDisplayed()) {
				calculatedDownpay=Integer.parseInt(driver.findElement(By.xpath("//input[@name='downPayment']")).getAttribute("value").replaceAll("[^-?\\d+]",""));
				addon=Integer.parseInt(driver.findElement(By.xpath("//input[@name='addonAmount']")).getAttribute("value").replaceAll("[^-?\\d+]",""));
			}
		}

		int calculatedFinance=getVehiclePrice-(calculatedDownpay-addon);
		if(downpayment==calculatedDownpay || downpayment==(calculatedDownpay-1) || downpayment==(calculatedDownpay+1)) {
			System.out.println("Downpayment Amount displayed on UI is as expected: " + formatNumberFrChina(downpayment));
		}else {
			Assert.assertFalse("Downpayment Amount displayed on UI does not matched, expected: " + formatNumberFrChina(calculatedDownpay) + ", Actual:" + formatNumberFrChina(downpayment),true);
		}

		if(amtfinanced==calculatedFinance || amtfinanced==(calculatedFinance-1) || amtfinanced==(calculatedFinance+1)) {
			System.out.println("Financed Amount displayed on UI is as expected: " + formatNumberFrChina(amtfinanced));
		}else {
			Assert.assertFalse("Financed Amount displayed on UI does not matched, expected: " + formatNumberFrChina(calculatedFinance) + ", Actual:" + formatNumberFrChina(amtfinanced),true);
		}
	}

	@When("^Input valid value for Down Payment$")
	public void Input_valid_value_for_Down_Payment() throws Throwable {
		System.out.println("Input valid value for Down Payment");
		Click_on_Finance_optons();

		downpayment=Integer.parseInt(getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().substring(1).replaceAll(",", ""));
		try {
			amtfinanced = Integer.parseInt(driver.findElement(By.xpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-amountFinanced') and text()!='']")).getText().substring(1).replaceAll(",", ""));
		} catch (Exception e) {
			amtfinanced = Integer.parseInt(getVisibleElementByXpath("//input[@name='amountFinanced']").getAttribute("value").substring(1).replaceAll(",", ""));
		}
		downpaymRatio=Integer.parseInt(getVisibleElementByXpath("//div[contains(@class,'rs-txt') and text()!='']").getText().replace("%", ""));
		String paddedString1 = Strings.padEnd("1", String.valueOf(downpayment).length()-1, '0'); //"007"
		System.out.println("New Downpayment amount is:"+paddedString1);
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").clear();
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(String.valueOf(downpayment+Integer.parseInt(paddedString1)));
	}


	@Then("^Down Payment Ratio is changed accordingly and Amount Financed varies with any changes from Down Payment$")
	public void Down_Payment_Ratio_is_changed_accordingly_and_Amount_Financed_varies_with_any_changes_from_Down_Payment() throws Throwable {
		System.out.println("Down Payment Ratio is changed accordingly and Amount Financed varies with any changes from Down Payment");
		Thread.sleep(2000);
		int amtfinc = 0;
		int dwnpayment=Integer.parseInt(getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().substring(1).replaceAll(",", ""));
		try {
			amtfinc = Integer.parseInt(driver.findElement(By.xpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-amountFinanced') and text()!='']")).getText().substring(1).replaceAll(",", ""));
		} catch (Exception e) {
			amtfinc = Integer.parseInt(getVisibleElementByXpath("//input[@name='amountFinanced']").getAttribute("value").substring(1).replaceAll(",", ""));
		}

		int dwnpaymRatio=Integer.parseInt(getVisibleElementByXpath("//div[contains(@class,'rs-txt') and text()!='']").getText().replace("%", ""));

		if(downpayment<dwnpayment && amtfinanced>amtfinc && downpaymRatio<dwnpaymRatio) {
			System.out.println("Down Payment Ratio is changed and Amount Financed varies with changes from Down Payment" + formatNumberFrChina(downpayment));
		}else {
			Assert.assertFalse("Down Payment Ratio and Amount Financed does not vary with changes from Down Payment",true);
		}
	}

	@When("^Input valid value for Amount Financed$")
	public void Input_valid_value_for_Amount_Financed() throws Throwable {
		System.out.println("Input valid value for Amount Financed");
		Click_on_Finance_optons();

		downpayment=Integer.parseInt(getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().substring(1).replaceAll(",", ""));
		try {
			amtfinanced = Integer.parseInt(driver.findElement(By.xpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-amountFinanced') and text()!='']")).getText().substring(1).replaceAll(",", ""));
		} catch (Exception e) {
			amtfinanced = Integer.parseInt(getVisibleElementByXpath("//input[@name='amountFinanced']").getAttribute("value").substring(1).replaceAll(",", ""));
		}
		downpaymRatio=Integer.parseInt(getVisibleElementByXpath("//div[contains(@class,'rs-txt') and text()!='']").getText().replace("%", ""));
		String paddedString1 = Strings.padEnd("1", String.valueOf(amtfinanced).length()-1, '0'); //"007"
		System.out.println("New Amount Financed is:"+paddedString1);
		try {
			getVisibleElementByXpath("//input[contains(@name,'amountFinanced')]").clear();
			getVisibleElementByXpath("//input[contains(@name,'amountFinanced')]").sendKeys(String.valueOf(amtfinanced-Integer.parseInt(paddedString1)));
		}catch(Exception e) {}	
	}


	@Then("^Down Payment Ratio is changed accordingly and Down Payment varies with any changes from Amount Financed$")
	public void Down_Payment_Ratio_is_changed_accordingly_and_Down_Payment_varies_with_any_changes_from_Amount_Financed() throws Throwable {
		System.out.println("Down Payment Ratio is changed accordingly and Down Payment varies with any changes from Amount Financed");
		Thread.sleep(2000);
		int amtfinc=0;
		int dwnpayment=Integer.parseInt(getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().substring(1).replaceAll(",", ""));
		try {
			amtfinc = Integer.parseInt(driver.findElement(By.xpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-amountFinanced') and text()!='']")).getText().substring(1).replaceAll(",", ""));
		} catch (Exception e) {
			amtfinc = Integer.parseInt(getVisibleElementByXpath("//input[@name='amountFinanced']").getAttribute("value").substring(1).replaceAll(",", ""));
		}
		int dwnpaymRatio=Integer.parseInt(getVisibleElementByXpath("//div[contains(@class,'rs-txt') and text()!='']").getText().replace("%", ""));

		if(downpayment<=dwnpayment && amtfinanced>=amtfinc && downpaymRatio<=dwnpaymRatio) {
			System.out.println("Down Payment Ratio is changed and Amount Financed varies with changes from Down Payment" + formatNumberFrChina(downpayment));
		}else {
			Assert.assertFalse("Down Payment Ratio and Amount Financed does not vary with changes from Down Payment",true);
		}
	}

	@When("^Drag Down Payment ratio to actual final point$")
	public void Drag_Down_Payment_ratio_to_actual_final_point() throws Throwable {
		System.out.println("Drag Down Payment ratio to actual final point");
		Click_on_Finance_optons();

		downpayment=Integer.parseInt(getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().substring(1).replaceAll(",", ""));
		try {
			amtfinanced = Integer.parseInt(driver.findElement(By.xpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-amountFinanced') and text()!='']")).getText().substring(1).replaceAll(",", ""));
		} catch (Exception e) {
			amtfinanced = Integer.parseInt(getVisibleElementByXpath("//input[@name='amountFinanced']").getAttribute("value").substring(1).replaceAll(",", ""));
		}
		downpaymRatio=Integer.parseInt(getVisibleElementByXpath("//div[contains(@class,'rs-txt') and text()!='']").getText().replace("%", ""));

		dragDownpaymentRatio(2000);
	}

	public void dragDownpaymentRatio(int sliderValue) throws Throwable {
		getVisibleElementByXpath("//span[contains(@class,'slider')]");
		WebElement slider = driver.findElement(By.xpath("//span[contains(@class,'slider')]"));
		Actions move = new Actions(driver);
		Action action = (Action) move.dragAndDropBy(slider, sliderValue, 0).build();
		action.perform();
		Thread.sleep(2000);

	}
	@Then("^Amount Financed equals$")
	public void Amount_Financed_equals(DataTable arg) throws Throwable {
		System.out.println("Amount Financed equals");
		List<List<String>> data=arg.raw();
		Thread.sleep(2000);
		int amtfinc=0;
		try {
			amtfinc = Integer.parseInt(driver.findElement(By.xpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-amountFinanced') and text()!='']")).getText().substring(1).replaceAll(",", ""));
		} catch (Exception e) {
			amtfinc = Integer.parseInt(getVisibleElementByXpath("//input[@name='amountFinanced']").getAttribute("value").substring(1).replaceAll(",", ""));
		}
		if(amtfinc==Integer.parseInt(data.get(0).get(0))) {
			System.out.println("The actual final point of Down Payment Ratio will be the equivalent percentage of Amount Financed equals to ￥40,000");
		}else {
			Assert.assertFalse("Amount Financed does not equals to ￥40,000",true);
		}
	}

	@When("^Input Down Payment amount$")
	public void Input_Down_Payment_amount() throws Throwable {
		System.out.println("Input Down Payment amount");
		Click_on_Finance_optons();

		downpayment=Integer.parseInt(getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().substring(1).replaceAll(",", ""))+1;	
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").clear();
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(String.valueOf(downpayment));
		Thread.sleep(2000);
		downpayment=Integer.parseInt(getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().substring(1).replaceAll(",", ""));
		try {
			amtfinanced = Integer.parseInt(driver.findElement(By.xpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-amountFinanced') and text()!='']")).getText().substring(1).replaceAll(",", ""));
		} catch (Exception e) {
			amtfinanced = Integer.parseInt(getVisibleElementByXpath("//input[@name='amountFinanced']").getAttribute("value").substring(1).replaceAll(",", ""));
		}
		downpaymRatio=Integer.parseInt(getVisibleElementByXpath("//div[contains(@class,'rs-txt') and text()!='']").getText().replace("%", ""));
		term=getVisibleElementByXpath("//div[contains(@id,'s2id_autogen4') or contains(@id,'s2id_autogen6')]//span[text()!='']").getText();
		monthlyPay=Integer.parseInt(getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-monthlyPayment') and text()!='']").getText().substring(1).replaceAll(",", ""));
		try {
			if(driver.findElements(By.xpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-ewPackage') and text()!='']")).size()>0) {
				paympkg=Integer.parseInt(getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-ewPackage') and text()!='']").getText().substring(1).replaceAll(",", ""));
			}
		}catch(Exception e) {}	
	}

	@When("^Click on the Compare Finance accordion$")
	public void Click_on_the_Compare_Finance_Details_accordion(DataTable arg) throws Throwable {
		System.out.println("Click on the Compare Finance Details accordion");
		List<List<String>> data=arg.raw();
		Thread.sleep(2000);
		List<WebElement> findexpanded=driver.findElements(By.xpath("//*[contains(@class,'accordion-title')]//h3"));
		Thread.sleep(2000);
		for(WebElement explorBtn:findexpanded) {	
			if(explorBtn.getText().contains(data.get(0).get(0)) && explorBtn.findElement(By.xpath("ancestor::div[contains(@class,'accordion-title')]")).getAttribute("class").contains("active")==false) {
				moveToElement(explorBtn);
				explorBtn.findElement(By.xpath("following-sibling::div//i")).click();
				waitTillElemVisiblebyXpath("//div[contains(@class,'compare-financial-options')]//*[@class='icon-plus-circled']", 240);
				getVisibleElementByXpath("//div[contains(@class,'compare-financial-options')]//*[@class='icon-plus-circled']").click();
				break;
			}
		}

	}

	@Then("^Verify compare Finance details columns count and related Payment details$")
	public void Verify_compare_Finance_details_columns_count_and_related_Payment_details(DataTable arg) throws Throwable {
		System.out.println("Verify compare Finance details columns count and related Payment details");
		List<List<String>> data=arg.raw();
		List<WebElement> CmpColCnt=driver.findElements(By.xpath("//div[contains(@class,'compare-financial-options')]//div[contains(@class,'vehicle-item ng-scope') and not(contains(@class,'hide')) and not(contains(@class,'empty'))]"));
		if(CmpColCnt.size()==Integer.parseInt(data.get(0).get(0)) && CmpColCnt.size()>0) {
			System.out.println("Compare Finance Details column count matched");

			System.out.println("Check for Model Price in Compare section");
			CmpColCnt.get(CmpColCnt.size()-1).findElement(By.xpath("//*[contains(text(),'"+formatNumberFrChina(getVehiclePrice)+"')]"));
			System.out.println("Check for Downpayment in Compare section");
			CmpColCnt.get(CmpColCnt.size()-1).findElement(By.xpath("//*[contains(text(),'"+formatNumberFrChina(downpayment)+"')]"));
			System.out.println("Check for Term in Compare section");
			CmpColCnt.get(CmpColCnt.size()-1).findElement(By.xpath("//*[contains(text(),'"+term+"')]"));
			System.out.println("Check for Monthly Payment amount in Compare section");
			CmpColCnt.get(CmpColCnt.size()-1).findElement(By.xpath("//*[contains(text(),'"+formatNumberFrChina(monthlyPay)+"')]"));

			try {
				System.out.println("Check for Financed amount in Compare section");
				CmpColCnt.get(CmpColCnt.size()-1).findElement(By.xpath("//*[contains(text(),'"+formatNumberFrChina(amtfinanced)+"')]"));				
				System.out.println("Check for Package amount in Compare section");
				CmpColCnt.get(CmpColCnt.size()-1).findElement(By.xpath("//*[contains(text(),'"+formatNumberFrChina(paympkg)+"')]"));
			}catch(Exception e) {}		
		}else if(CmpColCnt.size()==Integer.parseInt(data.get(0).get(0)) && CmpColCnt.size()==0){
			System.out.println("Compare Finance Details column count is: 0");
		}
		else {
			Assert.assertFalse("Compare Finance Details column count does not matched. Expected compare column#:"+data.get(0).get(0) +", Actual#:" + CmpColCnt.size(), true);
		}
	}

	@And("^Click on Finance optons$")
	public void Click_on_Finance_optons() throws Throwable {
		System.out.println("Click on Finance optons");
		Thread.sleep(2000);
		List<WebElement> findexpanded=driver.findElements(By.xpath("//*[contains(@class,'accordion-title')]//h3"));
		Thread.sleep(2000);
		for(WebElement explorBtn:findexpanded) {	
			if(explorBtn.getText().contains("方案总结") && explorBtn.findElement(By.xpath("ancestor::div[contains(@class,'accordion-title')]")).getAttribute("class").contains("active")==false) {
				moveToElement(explorBtn);
				explorBtn.findElement(By.xpath("following-sibling::div//i")).click();
				break;
			}
		}
	}

	@When("^Click on a Remove button$")
	public void Click_on_a_Remove_button() throws Throwable {
		System.out.println("Click on a Remove button");
		Thread.sleep(2000);
		getVisibleElementByXpath("//div[contains(@class,'compare-financial-options')]//div[contains(@class,'vehicle-item ng-scope') and not(contains(@class,'hide')) and not(contains(@class,'empty'))]//*[contains(@class,'cta')]").click();
	}

	@When("^See Add new Vehicle model is shown at the end$")
	public void See_Add_new_Vehicle_model_is_shown_at_the_end() throws Throwable {
		System.out.println("See Add new Vehicle model is shown at the end");
		Thread.sleep(2000);
		waitTillElemVisiblebyXpath("//div[contains(@class,'compare-financial-options')]//*[@class='icon-plus-circled']", 240);
		getVisibleElementByXpath("//div[contains(@class,'compare-financial-options')]//*[@class='icon-plus-circled']").click();
	}

	@When("^See Down Payment and Amount Financed in Summary list vary correctly$")
	public void See_Down_Payment_and_Amount_Financed_in_Summary_list_vary_correctly(DataTable arg) throws Throwable {
		System.out.println("See Down Payment and Amount Financed in Summary list vary correctly");
		List<List<String>> data=arg.raw();
		Thread.sleep(2000);
		int addon=0;
		if(driver.findElements(By.xpath("//input[@name='addonAmount']")).size()>0) {
			if(driver.findElement(By.xpath("//input[@name='addonAmount']")).isDisplayed()) {
				addon=Integer.parseInt(driver.findElement(By.xpath("//input[@name='addonAmount']")).getAttribute("value").replaceAll("[^-?\\d+]",""));
			}
		}
		downpayment=(getVehiclePrice+addon)-Integer.parseInt(data.get(0).get(0));
		amtfinanced=Integer.parseInt(data.get(0).get(0));
		int amtfinc;
		int dwnpayment=Integer.parseInt(getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().replaceAll("[^-?\\d+]", ""));
		try {
			amtfinc = Integer.parseInt(driver.findElement(By.xpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-amountFinanced') and text()!='']")).getText().replaceAll("[^-?\\d+]", ""));
		} catch (Exception e) {
			amtfinc = Integer.parseInt(getVisibleElementByXpath("//input[@name='amountFinanced']").getAttribute("value").substring(1).replaceAll(",", ""));
		}
		if(downpayment==dwnpayment && amtfinanced==amtfinc) {
			System.out.println("Downpayment and Amount Financed displayed properly in Summary list with runtime changed Down Payment ratio");
		}else {
			System.out.println("Downpayment Amount expected: " + formatNumberFrChina(downpayment) + ", Actual:" + formatNumberFrChina(dwnpayment));
			System.out.println("Amount Financed expected: " + formatNumberFrChina(amtfinanced) + ", Actual:" + formatNumberFrChina(amtfinc));
			Assert.assertFalse("Discrepency in Downpayment and Amount Financed display in Summary list with the runtime changed Down Payment ratio",true);
		}
	}

	@When("^Select card$")
	public void Select_card(DataTable arg) throws Throwable {
		System.out.println("Select card");
		List<List<String>> data=arg.raw();
		if (driver.findElements(By.xpath("//div[contains(@class,'product-list')]//li//h3[contains(text(),'"+data.get(0).get(0)+"')]")).size() > 0) {
			getVisibleElementByXpath("//div[contains(@class,'product-list')]//li//h3[contains(text(),'"+data.get(0).get(0)+"')]").click();
		}
	}

	@Then("^See the EW-package with its value is shown in Summary list$")
	public void See_the_EW_package_with_its_value_is_shown_in_Summary_list() throws Throwable {
		System.out.println("See the EW-package with its value is shown in Summary list");
		if (driver.findElements(By.xpath("//div[contains(@class,'product-list')]//li//div[contains(@data-ng-if,'product.selectedTerm.ewPackage')]")).size() > 0) {
			String pkgdata = getVisibleElementByXpath("//div[contains(@class,'product-list')]//li//div[contains(@data-ng-if,'product.selectedTerm.ewPackage')]").getText();
			String pkgAmt = null;
			String pattern = "\\d,\\d*";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(pkgdata);
			while (m.find()) {
				if (!m.group(0).isEmpty()) {
					System.out.println(m.group(0).trim());
					pkgAmt = m.group(0).trim();
					break;
				}
			}
			String pkgName = pkgdata.split(pkgAmt)[1].trim();
			Click_on_Finance_optons();

			getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-ewPackage-price') and contains(text(),'" + pkgAmt + "')]/preceding-sibling::*[contains(text(),'" + pkgName + "')]");
			getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-ewPackage-price') and contains(text(),'" + pkgAmt + "')]");
		}
	}

	@Then("^See all the information in Summary list varies correctly$")
	public void See_all_the_information_in_Summary_list_varies_correctly(DataTable arg) throws Throwable {
		System.out.println("See all the information in Summary list varies correctly");
		List<List<String>> data=arg.raw();
		dragDownpaymentRatio(2000);
		Thread.sleep(2000);
		int addon=0;
		if(driver.findElements(By.xpath("//input[@name='addonAmount']")).size()>0) {
			if(driver.findElement(By.xpath("//input[@name='addonAmount']")).isDisplayed()) {
				addon=Integer.parseInt(driver.findElement(By.xpath("//input[@name='addonAmount']")).getAttribute("value").replaceAll("[^-?\\d+]",""));
			}
		}

		downpayment=(getVehiclePrice+addon)-Integer.parseInt(data.get(0).get(0));
		amtfinanced=Integer.parseInt(data.get(0).get(0));
		String monthlyAmt=getVisibleElementByXpath("//div[contains(@class,'product-list')]//h3[text()!='']//following-sibling::div[@class='desc']//li[1]//b[text()!='']").getText();
		String Terms=getVisibleElementByXpath("//div[contains(@class,'product-list')]//h3[text()!='']//following-sibling::div[@class='desc']//li[2]//b[text()!='']").getText().trim().substring(1, 2);
		Click_on_Finance_optons();
		getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[(contains(@class,'payment-price') or contains(@class,'payment-transactionPrice')) and contains(text(),'"+formatNumberFrChina(getVehiclePrice)+"')]");
		getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and contains(text(),'"+formatNumberFrChina(downpayment)+"')]");
		try {
			driver.findElement(By.xpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-amountFinanced') and contains(text(),'"+formatNumberFrChina(amtfinanced)+"')]"));
		}catch(Exception e) {}
		getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-term') and contains(text(),'"+Terms+"')]");
		getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-monthlyPayment') and contains(text(),'"+monthlyAmt+"')]");
	}

	public void CheckSummaryList(int payprice, int dwnpay, int amtfinance,int term, int monthlypay) throws Throwable {
		getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-price') and contains(text(),'"+formatNumberFrChina(payprice)+"')]");
		getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and contains(text(),'"+formatNumberFrChina(dwnpay)+"')]");
		getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-amountFinanced') and contains(text(),'"+formatNumberFrChina(amtfinanced)+"')]");
		getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-term') and contains(text(),'"+term+"')]");
		getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-monthlyPayment') and contains(text(),'"+formatNumberFrChina(monthlypay)+"')]");
	}

	@Then("^Three financing options are shown in accordions$")
	public void Three_financing_options_are_shown_in_accordions() throws Throwable {
		System.out.println("Three financing options are shown in accordions");
		getVisibleElementByXpath("//*[contains(@class,'creditOptionAccordion')]//li");
		List<WebElement> vehicleName=driver.findElements(By.xpath("//*[contains(@class,'creditOptionAccordion')]//li"));
		if(vehicleName.size()<3) {
			Assert.assertFalse("Three financing options display issue on accordions", true);
		}
	}

	@When("^Expand an accordion$")
	public void Expand_an_accordion() throws Throwable {
		System.out.println("Expand an accordion");
		getVisibleElementByXpath("//*[contains(@class,'creditOptionAccordion')]//li");
		List<WebElement> vehicleName=driver.findElements(By.xpath("//*[contains(@class,'creditOptionAccordion')]//li//*[contains(@class,'accordion-title')]"));
		for(WebElement acc:vehicleName) {
			acc.click();
			Thread.sleep(2000);
		}
	}

	@Then("^More detail of option is shown$")
	public void More_detail_of_option_is_shown() throws Throwable {
		System.out.println("More detail of option is shown");
		getVisibleElementByXpath("//*[contains(@class,'creditOptionAccordion')]//li");
		List<WebElement> vehicleName=driver.findElements(By.xpath("//*[contains(@class,'creditOptionAccordion')]//li//*[contains(@class,'accordion-title')]"));
		for(WebElement acc:vehicleName) {
			acc.click();
			Thread.sleep(2000);
			List<WebElement> Moredetail=driver.findElements(By.xpath("//*[contains(@class,'creditOptionAccordion')]//li//*[contains(@class,'accordion-body active')][1]//table/thead//td[text()!='']"));
			for(WebElement sdetail:Moredetail) {
				System.out.println(sdetail.getText());
				Thread.sleep(1000);
			}
		}
	}

	@When("^Click on Compare Financing options$")
	public void Click_on_Compare_Financing_options() throws Throwable {
		System.out.println("Click on Compare Financing options");
		getVisibleElementByXpath("//*[contains(@class,'compare-button')]").click();
	}

	@Then("^Checkbox is shown at each option for user to select and Cancel button is shown$")
	public void Checkbox_is_shown_at_each_option_for_user_to_select_and_Cancel_button_is_shown() throws Throwable {
		System.out.println("Checkbox is shown at each option for user to select and Cancel button is shown");
		getVisibleElementByXpath("//*[contains(@class,'canscel-buttom')]");
		getVisibleElementByXpath("//*[contains(@class,'form-checkbox')]//fieldset//label");
	}

	@When("^Select 2 options$")
	public void Select_2_options() throws Throwable {
		System.out.println("Select 2 options");		
		List<WebElement> vehicleName=driver.findElements(By.xpath("//*[contains(@class,'form-checkbox')]//fieldset//label"));
		for(int chk=0;chk<2;chk++) {
			vehicleName.get(chk).click();
			Thread.sleep(2000);
		}
	}

	@Then("^Compare Selected options button is shown$")
	public void Compare_Selected_options_button_is_shown() throws Throwable {
		System.out.println("Compare Selected options button is shown");
		getVisibleElementByXpath("//*[@class='cta-button']");
	}

	@When("^Click on Cancel btn$")
	public void Click_on_Cancel_btn() throws Throwable {
		System.out.println("Click on Cancel btn");
		getVisibleElementByXpath("//*[contains(@class,'canscel-buttom')]").click();
	}

	@Then("^System discards all selected checkboxes$")
	public void System_discards_all_selected_checkboxes() throws Throwable {
		System.out.println("System discards all selected checkboxes");
		verifyInvisibleElem("//*[contains(@class,'form-checkbox')]//fieldset//label");
	}

	@And("^Click on Compare Selected options button$")
	public void Click_on_Compare_Selected_options_button() throws Throwable {
		System.out.println("Click on Compare Selected options button");
		getVisibleElementByXpath("//*[@class='cta-button']").click();
	}

	@When("^Click on a Change button$")
	public void Click_on_a_Change_button() throws Throwable {
		System.out.println("Click on a Change button");
		getVisibleElementByXpath("//*[contains(@class,'edit-button')]").click();
	}

	@Then("^popup is shown with the rest option for user to select$")
	public void popup_is_shown_with_the_rest_option_for_user_to_select() throws Throwable {
		System.out.println("popup is shown with the rest option for user to select");
		waitTillElemVisiblebyXpath("//div[@class='popup-content-holder']/div[not(contains(@class,'checkbox section disable'))]//*[contains(@class,'credit-overlay-input')]", 240);
		getVisibleElementByXpath("//div[@class='popup-content-holder']/div[not(contains(@class,'checkbox section disable'))]//*[contains(@class,'credit-overlay-input')]");
	}

	@When("^Select the option$")
	public void Select_the_option() throws Throwable {
		System.out.println("Select the option");
		getVisibleElementByXpath("//div[@class='popup-content-holder']/div[not(contains(@class,'checkbox section disable'))]//*[contains(@class,'credit-overlay-input')]");
	}

	@Then("^option is changed properly$")
	public void option_is_changed_properly() throws Throwable {
		System.out.println("option is changed properly");

		String opt=getVisibleElementByXpath("//div[@class='popup-content-holder']/div[not(contains(@class,'checkbox section disable'))]//*[contains(@class,'credit-overlay-input')]//*[text()!='']").getText().trim();
		getVisibleElementByXpath("//div[@class='popup-content-holder']/div[not(contains(@class,'checkbox section disable'))]//*[contains(@class,'credit-overlay-input')]").click();
		Thread.sleep(2000);
		waitTillElemVisiblebyXpath("//table[contains(@class,'credit-summary-table')]//thead//th[contains(text(),'"+opt+"')]", 240);
		getVisibleElementByXpath("//table[contains(@class,'credit-summary-table')]//thead//th[contains(text(),'"+opt+"')]");
		getVisibleElementByXpath("//table[contains(@class,'credit-summary-table')]//tbody//td//*[text()!='']");
	}

	@When("^Click on Add button$")
	public void Click_on_Add_button() throws Throwable {
		System.out.println("Click on Add button");
		getVisibleElementByXpath("//*[contains(@class,'add-button')]").click();
	}

	@And("^Click on Remove btn and see Relative option is removed$")
	public void Click_on_Remove_button() throws Throwable {
		System.out.println("Click on Remove btn and see Relative option is removed");
		String opt=getVisibleElementByXpath("//table[contains(@class,'credit-summary-table')]/thead//th[contains(@class,'column') and text()!='']").getText().trim();
		getVisibleElementByXpath("//*[contains(@class,'remove-button')]").click();
		Thread.sleep(2000);
		verifyInvisibleElem("//table[contains(@class,'credit-summary-table')]//thead//th[contains(text(),'"+opt+"')]");
	}

	@And("^Remove button is replaced by Change button$")
	public void Remove_button_is_replaced_by_Change_button() throws Throwable {
		System.out.println("Remove button is replaced by Change button");
		getVisibleElementByXpath("//*[contains(@class,'edit-button')]");
		verifyInvisibleElem("//*[contains(@class,'remove-button')]");
	}

	@When("^Click Save$")
	public void Click_Save() throws Throwable{
		System.out.println("Click Save");
		getVisibleElementByXpath("//a[contains(@class,'save-btn')]//*[text()='Save' or text()='save' or text()='SAVE']").click();
	}

	@Then("^Current Vehicle Financing package is saved successfully in PDF$")
	public void Current_Vehicle_Financing_package_is_saved_successfully_in_PDF() throws Throwable {
		System.out.println("Current Vehicle Financing package is saved successfully in PDF");
		CheckFileDownload(Configuration.PATH_TO_CN_CREDIT_PE);
	}

	@And("^Check existence of EMAIL and PRINT button$")
	public void Check_existence_of_EMAIL_and_PRINT_button() throws Throwable {
		System.out.println("Check existence of EMAIL and PRINT button");
		getVisibleElementByXpath("//a[contains(@class,'mail-btn')]//*[text()='Email' or text()='email' or text()='EMAIL']");
		getVisibleElementByXpath("//a[contains(@class,'print-btn')]//*[text()='Print' or text()='print' or text()='PRINT']");
	}

	@Then("^Trending Bar is shown correctly in disabled mode for the begining$")
	public void Trending_Bar_is_shown_correctly_in_disabled_mode_for_the_begining() throws Throwable {
		System.out.println("Trending Bar is shown correctly in disabled mode for the begining");
		getVisibleElementByXpath("//div[contains(@class,'progress-bar-holder') and contains(@class,'disable')]");
		getVisibleElementByXpath("//div[contains(@class,'nextBtn')]//button");
	}

	@And("^Default value of Trending Bar is 50-50$")
	public void Default_value_of_Trending_Bar_is_50_50() throws Throwable {
		System.out.println("Default value of Trending Bar is 50-50");
		getVisibleElementByXpath("//div[contains(@class,'progress-bar-holder') and contains(@class,'disable')]//*[contains(@class,'start-box')]//*[contains(text(),'50%') or contains(text(),'50 %')]");
		getVisibleElementByXpath("//div[contains(@class,'progress-bar-holder') and contains(@class,'disable')]//*[contains(@class,'finish-box')]//*[contains(text(),'50%') or contains(text(),'50 %')]");
	}
	@When("^Select an answer$")
	public void Select_an_answer() throws Throwable{
		System.out.println("Select an answer");
		getVisibleElementByXpath("//label[contains(@for,'radio')]").click();
	}

	@Then("^Trending Bar is immediately updated$")
	public void Trending_Bar_is_immediately_updated() throws Throwable {
		System.out.println("Trending Bar is immediately updated");
		getVisibleElementByXpath("//div[contains(@class,'progress-bar-holder') and not(contains(@class,'disable'))]//*[contains(@class,'start-box')]//*[text()!='']");
		getVisibleElementByXpath("//div[contains(@class,'progress-bar-holder') and not(contains(@class,'disable'))]//*[contains(@class,'finish-box')]//*[text()!='']");
	}

	@Then("^Question is shown under Trending Bar with answer ratios and Next button in disabled mode$")
	public void Question_is_shown_under_Trending_Bar_with_answer_ratios_and_Next_button_in_disabled_mode() throws Throwable {
		System.out.println("Question is shown under Trending Bar with answer ratios and Next button in disabled mode");
		getVisibleElementByXpath("//div[contains(@class,'progress-bar-holder') and contains(@class,'disable')]//following-sibling::div[contains(@class,'question-block')]");
		getVisibleElementByXpath("//div[contains(@class,'nextBtn') and contains(@class,'disable')]");
	}

	@Then("^Next button is enabled$")
	public void Next_button_is_enabled() throws Throwable {
		System.out.println("Next button is enabled");
		getVisibleElementByXpath("//div[contains(@class,'nextBtn') and not(contains(@class,'disable'))]");
	}

	@When("^Click on Next button$")
	public void Click_on_Next_button() throws Throwable {
		System.out.println("Click on Next button");
		term=getVisibleElementByXpath("//div[contains(@class,'question-block')]//*[contains(text(),'?')]").getText().trim() + "~" + getVisibleElementByXpath("//label[contains(@for,'radio') and contains(@class,'checked')]").getText().trim();
		getVisibleElementByXpath("//div[contains(@class,'nextBtn') and not(contains(@class,'disable'))]").click();
	}

	@Then("^System shows second question and Previous button is shown$")
	public void System_shows_second_question_and_Previous_button_is_shown() throws Throwable {
		System.out.println("System shows second question and Previous button is shown");
		waitTillElemVisiblebyXpath("//div[contains(@class,'prevBtn')]//button", 240);
		getVisibleElementByXpath("//div[contains(@class,'question-block')]//*[contains(text(),'?')]");
		getVisibleElementByXpath("//div[contains(@class,'prevBtn')]//button");
	}

	@When("^Click on Previous button$")
	public void Click_on_Previous_button() throws Throwable {
		System.out.println("Click on Previous button");
		getVisibleElementByXpath("//div[contains(@class,'prevBtn')]//button").click();
	}

	@Then("^System back to the previous question with the selected answer$")
	public void System_back_to_the_previous_question_with_the_selected_answer() throws Throwable {
		System.out.println("System back to the previous question with the selected answer");
		Thread.sleep(10000);
		String term2=getVisibleElementByXpath("//div[contains(@class,'question-block')]//*[contains(text(),'?')]").getText().trim() + "~" + getVisibleElementByXpath("//label[contains(@for,'radio') and contains(@class,'checked')]").getText().trim();
		if (term2.contains(term)){
			System.out.println("Question matched with selected answer");
		}else {
			Assert.assertFalse("Question does not matched with selected answer", true);
		}
	}

	@And("^Answer to the last question$")
	public void Answer_to_the_last_question() throws Throwable {
		System.out.println("Answer to the last question");
		getVisibleElementByXpath("//div[contains(@class,'nextBtn') and contains(@class,'disable')]");
		do {
			Select_an_answer();
			Click_on_Next_button();
			Thread.sleep(2000);
		}while(driver.findElements(By.xpath("//*[contains(@class,'option-tool-holder') and not(contains(@class,'ng-hide'))]//a[contains(@ng-click,'startOver')]")).size()==0);
	}

	@Then("^System redirects to Result page with the highest accumulated percentage based on the Users responses in large print$")
	public void System_redirects_to_Result_page_with_the_highest_accumulated_percentage_based_on_the_Users_responses_in_large_print() throws Throwable {
		System.out.println("System redirects to Result page with the highest accumulated percentage based on the Users responses in large print");
		getVisibleElementByXpath("//*[contains(@class,'option-tool-holder') and not(contains(@class,'ng-hide'))]//a[contains(@ng-click,'startOver')]");
		List<WebElement> PgLabels=driver.findElements(By.xpath("//div[contains(@class,'option-tool-holder') and not(contains(@class,'ng-hide'))]//*[contains(@class,'title')]//*[text()!='']"));
		for(WebElement PageElement:PgLabels) {
			System.out.println(PageElement.getText().trim());
			Thread.sleep(2000);
		}
	}

	@When("^Click on See how your answer affected result$")
	public void Click_on_See_how_your_answer_affected_result() throws Throwable {
		System.out.println("Click on See how your answer affected result");
		getVisibleElementByXpath("//div[contains(@class,'option-tool-holder') and not(contains(@class,'ng-hide'))]//a[contains(@href,'result-accordion') and text()!='']").click();
	}

	@Then("^system opens the accordion lower on the page$")
	public void system_opens_the_accordion_lower_on_the_page() throws Throwable {
		System.out.println("system opens the accordion lower on the page");
		Thread.sleep(2000);
		getVisibleElementByXpath("//div[contains(@class,'option-tool-holder') and not(contains(@class,'ng-hide'))]//div[contains(@class,'accordion-title active')]");		
	}

	@When("^Click on Start Over button$")
	public void Click_on_Start_Over_button() throws Throwable {
		System.out.println("Click on Start Over button");
		getVisibleElementByXpath("//*[contains(@class,'option-tool-holder') and not(contains(@class,'ng-hide'))]//a[contains(@ng-click,'startOver')]").click();
	}

	@Then("^system returns the user to the start of the Finance Options Tool flow$")
	public void system_returns_the_user_to_the_start_of_the_Finance_Options_Tool_flow() throws Throwable {
		System.out.println("system returns the user to the start of the Finance Options Tool flow");
		Thread.sleep(5000);
		getVisibleElementByXpath("//div[contains(@class,'nextBtn') and contains(@class,'disable')]");		
	}

	@When("^Click on CTA$")
	public void Click_on_CTA(DataTable arg1) throws Throwable {
		System.out.println("Click on CTA");
		List<List<String>> data=arg1.raw();
		waitTillElemVisiblebyXpath("//div[contains(@class,'sort')]//span[not(contains(@class,'hide'))]", 240);

		List<WebElement> getVehickeList=driver.findElements(By.xpath("//div[contains(@class,'vehicle-card-group')]//dl[not(contains(@style,'none'))]//div[contains(@class,'item-content')]//h3"));
		for(int CmpVehicle=0;CmpVehicle<getVehickeList.size();CmpVehicle++) {
			VehiclBfrSort.add(getVehickeList.get(CmpVehicle).getText().trim());
		}

		getVisibleElementByXpath("//div[contains(@class,'overlay-aside')]//label[contains(text(),'"+data.get(0).get(0)+"')]").click();
		Thread.sleep(5000);
	}

	@Then("^See Vehicle Filtered accordingly$")
	public void See_Vehicle_Filtered_accordingly() throws Throwable {
		System.out.println("Available vehicles are sorted as per sort selection by");

		List<WebElement> getVehickeList=driver.findElements(By.xpath("//div[contains(@class,'vehicle-card-group')]//dl[not(contains(@style,'none'))]//div[contains(@class,'item-content')]//h3"));
		for(int CmpVehicle=0;CmpVehicle<getVehickeList.size();CmpVehicle++) {
			VehiclAftrSort.add(getVehickeList.get(CmpVehicle).getText().trim());
		}

		if(!VehiclBfrSort.equals(VehiclAftrSort)) {
			Assert.assertTrue("Vehicle Filter working as expected.", true);	
		}else {
			Assert.assertFalse("Vehicle Filter does not work as expected.", true);
		}
	}

	@When("^Select Vehicle$")
	public void Select_Vehicle(DataTable arg1) throws Throwable{
		System.out.println("Select a Vehicle");
		List<List<String>> data=arg1.raw();
		if(data.get(0).get(0).contains("ANY")) {
			getVisibleElementByXpath("//*[text()!='']/preceding-sibling::*//img[contains(@src,'/nameplate/') or contains(@src,'/img/')]").click();
		}else {	
			waitTillElemVisiblebyXpath("//*[text()='"+data.get(0).get(0)+"' and not(contains(@id,'json'))]", 240);
			getVisibleElementByXpath("//*[contains(text(),'"+data.get(0).get(0)+"') and not(contains(@id,'json'))]").click();
		}
		waitTillElemVisiblebyXpath("//*[contains(@class,'vehicle-section-one')]//a[contains(@class,'cta-button-secondary') or contains(@class,'cta-button-primary')]", 240);
		try {
			Thread.sleep(10000);	
			getVehiclePrice=super.convertStrtoDoubletoInt(driver.findElement(By.xpath("//div[contains(@class,'vehicle-price')]//span[text()!='']")).getText().substring(1).trim().replace(".", "").replace(",", ""));
			addConfigPrice=0;
		}catch(Exception e) {}
	}

	@Then("^Check elements in Financing Package Selection for MX$")
	public void Check_elements_in_Financing_Package_SelectionMX() throws Throwable {
		System.out.println("Check elements in Financing Package Selection");
		getVisibleElementByXpath("//div[contains(@class,'vehicle-price')]//span[text()!='']//preceding-sibling::p[text()!='']"); 
		getVisibleElementByXpath("//div[contains(@class,'vehicle-price')]//span[text()!='']"); //vehicle Price
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]"); // downpayment
		getVisibleElementByXpath("//div[contains(@id,'s2id_autogen')]"); //terms
		getVisibleElementByXpath("//input[contains(@name,'adminFee')]"); 
		getVisibleElementByXpath("//label[contains(@for,'MultiAnnualInsurance')]"); 
		getVisibleElementByXpath("//div[@class='product-item']//ul/li[text()!='']"); //Financial Product cards

	}

	@Then("^Check elements in Financing Package Selection for BR$")
	public void Check_elements_in_Financing_Package_SelectionBR() throws Throwable {
		System.out.println("Check elements in Financing Package Selection");
		getVisibleElementByXpath("//div[contains(@class,'vehicle-price')]//span[text()!='']//preceding-sibling::p[text()!='']"); 
		getVisibleElementByXpath("//div[contains(@class,'vehicle-price')]//span[text()!='']"); //vehicle Price
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]"); // downpayment
		getVisibleElementByXpath("//div[contains(@id,'s2id_autogen')]"); //terms
		getVisibleElementByXpath("//div[contains(@id,'s2id_select-states')]"); 
		getVisibleElementByXpath("//div[@class='product-item']//ul/li[text()!='']"); //Financial Product cards
	}


	@And("^Click on Terms field to check options$")
	public void Click_on_Terms_field_to_check_options() throws Throwable {
		System.out.println("Click on Terms field to check options");
		getVisibleElementByXpath("//div[contains(@id,'s2id_autogen')]").click(); 
		Thread.sleep(2000);
		List<WebElement> TermOptions=getVisibleElements("//div[contains(@class,'drop')]//li[contains(@class,'select') and contains(@class,'results')]//div");
		for(WebElement TermOpt:TermOptions) {
			System.out.println(TermOpt.getText());
		}
		term=TermOptions.get(TermOptions.size()-1).getText();
		TermOptions.get(TermOptions.size()-1).click();
	}

	@Then("^Check default Min and Max value of Down Payment for MX$")
	public void Check_default_value_of_Down_Payment(DataTable arg) throws Throwable {
		System.out.println("Check default Min and Max value of Down Payment for MX");

		Click_on_accordion(arg.raw().get(0).get(0));
		Click_on_accordion(arg.raw().get(0).get(0));
		String DPay=getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().split(" ")[0];
		downpayment=Integer.parseInt(DPay.substring(1).trim().replace(".", "").replace(",", ""));

		System.out.println("Check for Valid Min Downpayment Amount displayed on UI# "+downpayment);
		int calculatedDownpay=(int) Math.ceil((getVehiclePrice*20)/100);
		if(downpayment==calculatedDownpay || downpayment==(calculatedDownpay-1) || downpayment==(calculatedDownpay+1)) {
			System.out.println("default and Min Downpayment Amount displayed on UI is as expected: " + DPay);
		}else {
			Assert.assertFalse("default and Min Downpayment Amount displayed on UI does not matched, expected: " + calculatedDownpay + ", Actual:" + downpayment,true);
		}

		System.out.println("Check for Valid Max Downpayment Amount displayed on UI# "+calculatedDownpay);
		calculatedDownpay=(int) Math.ceil((getVehiclePrice*80)/100);
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").clear();
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(String.valueOf(calculatedDownpay));
		getVisibleElementByXpath("//input[contains(@name,'downPayment') and contains(@class,'valid-max-payment')]");

		System.out.println("Check for Invalid Max Downpayment Amount displayed on UI# "+calculatedDownpay+1);
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").clear();
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(String.valueOf(calculatedDownpay+1));
		getVisibleElementByXpath("//input[contains(@name,'downPayment') and contains(@class,'invalid-max-payment')]");
	}


	@Then("^Check default Min and Max value of Down Payment for BR$")
	public void Check_default_value_of_Down_PaymentBR(DataTable arg) throws Throwable {
		System.out.println("Check default Min and Max value of Down Payment for BR");

		Click_on_accordion(arg.raw().get(0).get(0));
		Click_on_accordion(arg.raw().get(0).get(0));
		String DPay=getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().split(" ")[0];
		downpayment=Integer.parseInt(DPay.substring(1).trim().replace(".", "").replace(",", ""));

		System.out.println("Check for Valid Min Downpayment Amount displayed on UI# "+downpayment);
		int calculatedDownpay=(int) Math.ceil((getVehiclePrice*30)/100);
		if(downpayment==calculatedDownpay || downpayment==(calculatedDownpay-1) || downpayment==(calculatedDownpay+1)) {
			System.out.println("default and Min Downpayment Amount displayed on UI is as expected: " + DPay);
		}else {
			Assert.assertFalse("default and Min Downpayment Amount displayed on UI does not matched, expected: " + calculatedDownpay + ", Actual:" + downpayment,true);
		}

		System.out.println("Check for Valid Max Downpayment Amount displayed on UI# "+calculatedDownpay);
		calculatedDownpay=(int) Math.ceil((getVehiclePrice*80)/100);
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").clear();
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(String.valueOf(calculatedDownpay));
		getVisibleElementByXpath("//input[contains(@name,'downPayment') and contains(@class,'valid-max-payment')]");

		downpayment=getVehiclePrice+50;
		System.out.println("Check for Invalid Max Downpayment Amount displayed on UI# "+downpayment);
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").clear();
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(String.valueOf(downpayment));
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(Keys.TAB);
		Thread.sleep(2000);
		getVisibleElementByXpath("//*[contains(@class,'show-error-downpayment')]//*[not(contains(@class,'hide')) or (contains(@class,'noDataFromCFC') and not(contains(@class,'hide')))]");

	}


	@Then("^Check Customer Rate Display in Promo Box$")
	public void Check_Customer_Rate_Display_in_Promo_Box(DataTable arg) throws Throwable {
		System.out.println("Check Customer Rate Display in Promo Box");
		Click_on_accordion(arg.raw().get(0).get(0));
		Click_on_accordion(arg.raw().get(0).get(0));
		String CRate=getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-customer-rate') and text()!='']").getText();
		System.out.println("Customer Rate Displayed in Promo Box is: " + CRate);
	}

	@When("^Change the Vehicle Model of a nameplate$")
	public void Change_the_Vehicle_Model_of_a_nameplate() throws Throwable {
		System.out.println("Change the Vehicle Model of a nameplate");
		getVisibleElementByXpath("//div[contains(@id,'s2id_select-models')]").click(); 
		Thread.sleep(2000);
		List<WebElement> TermOptions=getVisibleElements("//div[contains(@class,'drop')]//li[contains(@class,'select') and contains(@class,'results')]//div");
		TermOptions.get(2).click();
	}

	@When("^Change the Vehicle Model$")
	public void Change_the_Vehicle_Model(DataTable arg) throws Throwable {
		System.out.println("Change the Vehicle Model of a nameplate");
		getVisibleElementByXpath("//div[contains(@id,'s2id_select-models')]").click(); 
		Thread.sleep(2000);
		List<WebElement> TermOptions=getVisibleElements("//div[contains(@class,'drop')]//li[contains(@class,'select')]//div");
		for(WebElement el:TermOptions) {
			if(el.getText().contains(arg.raw().get(0).get(0))) {
				el.click();
				break;
			}
		}
		Thread.sleep(2000);
	}

	@When("^Click on accordion \"([^\"]*)\"$")
	public void Click_on_accordion(String arg) throws Throwable {
		System.out.println("Click on accordion");
		Thread.sleep(2000);
		List<WebElement> findexpanded=driver.findElements(By.xpath("//*[contains(@class,'accordion-title')]//h3"));
		Thread.sleep(2000);
		for(WebElement explorBtn:findexpanded) {	
			if(explorBtn.getText().contains(arg) && explorBtn.findElement(By.xpath("ancestor::*[contains(@class,'accordion-item')]")).getAttribute("class").contains("active")==false) {
				moveToElement(explorBtn);
				explorBtn.findElement(By.xpath("ancestor::*[contains(@class,'accordion-item')]//i")).click();
				break;
			}
		}
	}

	@Then("^Summary list expands includes all payment details$")
	public void Summary_list_expands_includes_all_payment_details(DataTable arg) throws Throwable {
		System.out.println("Summary list expands includes all payment details");
		for(int sumfield=0;sumfield<=9;sumfield++) {
			getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'"+arg.raw().get(0).get(sumfield)+"') and text()!='']");
			getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'"+arg.raw().get(0).get(sumfield)+"') and text()!='']//preceding-sibling::*[contains(@class,'title') and text()!='']");
		}
		tempStr=getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText();
	}


	@When("^Change Term and Downpayment$")
	public void Change_Term_and_Downpayment() throws Throwable {
		System.out.println("Change Term and Downpayment");
		Click_on_Terms_field_to_check_options();
		Click_on_accordion("Amortizaci");
		term=getVisibleElementByXpath("//div[contains(@id,'s2id_autogen')]").getText();
		downpayment=Integer.parseInt(tempStr.substring(1).trim().split(" ")[0].replace(".", ""))+50;
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").clear();
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(String.valueOf(downpayment));
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(Keys.TAB);
		Click_on_accordion("Amortizaci");
	}	

	@Then("^Information of Terms and DownPayment is updated correctly in Summary list$")
	public void Information_of_Terms_and_DownPayment_is_updated_correctly_in_Summary_list() throws Throwable {
		System.out.println("Information of Terms and DownPayment is updated correctly in Summary list");
		Click_on_accordion("Amortizaci");
		Click_on_accordion("Payment Details");
		Click_on_accordion("Payment Details");
		int actualdownpay=Integer.parseInt(getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().substring(1).trim().split(" ")[0].replace(".", ""));
		if(downpayment!=actualdownpay) {
			Assert.assertFalse("Actual downpayment:"+actualdownpay + ", Expected downpayment:"+downpayment, true);
		}
		getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-loan-term') and contains(text(),'"+term.split(" ")[0]+"')]");
	}


	@Then("^Information of Terms and DownPayment is updated correctly in Amortisation Table$")
	public void Information_of_Terms_and_DownPayment_is_updated_correctly_in_Amortisation_Table() throws Throwable {
		System.out.println("Information of Terms and DownPayment is updated correctly in Amortisation Table");
		Click_on_accordion("Payment Details");
		Click_on_accordion("Amortizaci");
		Click_on_accordion("Amortizaci");
		int actualterm=Integer.parseInt(term.split(" ")[0]);
		String mnthpay=RegExpression(getVisibleElementByXpath("//*[contains(@class,'financial-list')]//li[1]//*[text()!='' and contains(@class,'data')]").getText(),"\\d*\\.\\d*");
		getVisibleElementByXpath("//*[@class='table-holder show-desktop-only']//tbody//tr[1]/td[contains(text(),'1 Mes')]");
		getVisibleElementByXpath("//*[@class='table-holder show-desktop-only']//tbody//tr[1]/td[contains(text(),'"+mnthpay+"')]");

		for(int i=2;i<=actualterm;i++) {
			getVisibleElementByXpath("//*[@class='table-holder show-desktop-only']//tbody//tr["+i+"]/td[contains(text(),'"+i+" Months')]");
		}
	}


	@Then("^See the vehicle selection overlay for AR$")
	public void seeVehicleSelectionOverlayAR() throws Throwable {
		System.out.println("See the vehicle selection overlay for AR");
		getVisibleElementByXpath("//header[contains(@class,'page-title') and text()!='']");
		verifyInvisibleElem("//div[contains(@class,'header-overlay') and contains(@style,'none')]//div[contains(@class,'back-arrow')]//i");
	}


	@When("^On vehicle selector page Click to return back to previously selected vehicle$")
	public void On_vehicle_selector_page_Click_to_return_back_to_previously_selected_vehicle(DataTable arg1) throws Throwable{
		System.out.println("On vehicle selector page Click to return back to previously selected vehicle");
		List<List<String>> data=arg1.raw();
		getVisibleElementByXpath("//div[contains(@class,'back-arrow')]//i").click();
		All_components_loads_successfully_without_performance_issue();
		getVisibleElementByXpath("//*[contains(@class,'vehicle-section-one')]//*[contains(@id,'nameplateName') and contains(text(),'"+data.get(0).get(0)+"')]");
	}

	@When("^Change Term and Valid & Invalid Downpayment for AR$")
	public void Change_Term_and_DownpaymentAR(DataTable arg) throws Throwable {
		System.out.println("Change Term and Valid & Invalid Downpayment for AR");

		System.out.println("Select Term and check");
		getVisibleElementByXpath("//div[contains(@class,'terms-dropdown')]//*[contains(@id,'select')]").click(); 
		Thread.sleep(2000);
		List<WebElement> TermOptions=getVisibleElements("//div[contains(@class,'drop')]//li[contains(@class,'select') and contains(@class,'results')]//div");
		for(WebElement TermOpt:TermOptions) {
			System.out.println("Terms# "+TermOpt.getText());
		}
		TermOptions.get(TermOptions.size()-1).click();		
		getVisibleElementByXpath("//input[contains(@name,'loanAmount')]").clear();
		Thread.sleep(4000);
		term=getVisibleElementByXpath("//div[contains(@class,'terms-dropdown')]//*[contains(@id,'select')]").getText();

		downpayment=getVehiclePrice+50;
		System.out.println("Check for Invalid Max Downpayment Amount displayed on UI# "+downpayment);
		getVisibleElementByXpath("//input[contains(@name,'loanAmount')]").clear();
		getVisibleElementByXpath("//input[contains(@name,'loanAmount')]").sendKeys(String.valueOf(downpayment));
		getVisibleElementByXpath("//input[contains(@name,'loanAmount')]").sendKeys(Keys.TAB);
		Thread.sleep(2000);
		getVisibleElementByXpath("//*[contains(@class,'show-error-downpayment')]//*[not(contains(@class,'hide'))]");


		Click_on_accordion("Detall");
		Click_on_accordion("Detall");
		List<WebElement> finaceColoumn=getVisibleElements("//*[@class='table-holder show-desktop-only']//thead//tr//th");
		for(int i=0;i<finaceColoumn.size();i++) {
			if(finaceColoumn.get(i).getText().contains(arg.raw().get(0).get(0))) {
				TempValue=++i;
				break;
			}
		}
		tempStr=getVisibleElementByXpath("//*[@class='table-holder show-desktop-only']//tbody//tr[1]/td["+TempValue+"]").getText();
		downpayment=Integer.parseInt(tempStr.substring(1).trim().split(" ")[0].replace(".", ""));
		System.out.println("Check for Valid Downpayment Amount displayed on UI# "+(10+downpayment));
		getVisibleElementByXpath("//input[contains(@name,'loanAmount')]").clear();
		getVisibleElementByXpath("//input[contains(@name,'loanAmount')]").sendKeys(String.valueOf(downpayment));
		Click_on_accordion("Detall");

	}

	@Then("^Information of Terms and DownPayment is updated correctly in Payment details$")
	public void Information_of_Terms_and_DownPayment_is_updated_correctly_in_Payment_details() throws Throwable {
		System.out.println("Information of Terms and DownPayment is updated correctly in Payment details");
		Click_on_accordion("Detall");
		Click_on_accordion("Detall");
		int actualterm=Integer.parseInt(term.split(" ")[0]);
		getVisibleElementByXpath("//*[@class='table-holder show-desktop-only']//tbody//tr[1]/td[contains(text(),'1 Mes')]");
		for(int i=2;i<=actualterm;i++) {
			getVisibleElementByXpath("//*[@class='table-holder show-desktop-only']//tbody//tr["+i+"]/td[contains(text(),'"+i+" Months') or contains(text(),'"+i+" Meses')]");
		}

		tempStr=getVisibleElementByXpath("//*[@class='table-holder show-desktop-only']//tbody//tr[1]/td["+TempValue+"]").getText();
		int Actdownpayment=Integer.parseInt(tempStr.substring(1).trim().split(" ")[0].replace(".", ""));
		if(Actdownpayment!=downpayment) {
			Assert.assertFalse("Actual downpayment amt is not taching with expected amt",true);
		}
	}


	@When("^Enter down payment \"(.*?)\"$")
	public void enter_down_payment(String arg) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		Click_on_accordion(arg);
		Click_on_accordion(arg);
		String DPay=getVisibleElementByXpath("//*[contains(@class,'payment-summary')]//*[contains(@class,'payment-downPayment') and text()!='']").getText().split(" ")[0];
		downpayment=Integer.parseInt(DPay.substring(1).trim().replace(".", "").replace(",", ""));


		downpayment=getVehiclePrice+50;
		System.out.println("Check for Downpayment Amount displayed on UI# "+downpayment);
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").clear();
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(String.valueOf(downpayment));
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(Keys.TAB);
		Thread.sleep(2000);		
	}

	@Then("^Monthly Instalments with FP Label and without FP Label values matched with SL$")
	public void monthly_Instalments_with_FP_Label_and_without_FP_Label_values_matched_with_SL(DataTable arg1) throws Exception {
		List<List<String>> data=arg1.raw();
		String GetSLresponse=JSONService.getJsonResponse(data.get(0).get(0));
		int SLwithFP=convertStrtoDoubletoInt(parseSLforBRcredit(GetSLresponse,"monthlyCETWithFP"));
		int SLwithoutFP=convertStrtoDoubletoInt(parseSLforBRcredit(GetSLresponse,"monthlyCETWithoutFP"));

		List<WebElement> AmtFP=driver.findElements(By.xpath("//div[contains(@class,'financial-products-card')]//*[contains(@class,'financial-list')]//div"));

		int UIwithFP=Integer.parseInt(AmtFP.get(0).getText().trim().substring(1).replace(".", "").replaceAll(",", ""));
		int UIwithoutFP=Integer.parseInt(AmtFP.get(1).getText().trim().substring(1).replace(".", "").replaceAll(",", ""));

		if(SLwithFP==UIwithFP || (SLwithFP+1)==UIwithFP) {
			System.out.println("Monthly Instalments with FP Label amount matched wth SL amount.");
		}else {
			Assert.assertFalse("Monthly Instalments with FP Label amount does not matched wth SL amount. SL value:" + SLwithFP + ", UI value:" + UIwithFP, true);
		}

		if(SLwithoutFP==UIwithoutFP || (SLwithoutFP+1)==UIwithoutFP) {
			System.out.println("Monthly Instalments without FP Label amount matched wth SL amount.");
		}else {
			Assert.assertFalse("Monthly Instalments without FP Label amount does not matched wth SL amount. SL value:" + SLwithoutFP + ", UI value:" + UIwithoutFP, true);
		}
	}

	public String parseSLforBRcredit(String getSLresponse,String StrToBeSplitted) {
		return getSLresponse.split(""+StrToBeSplitted+"\":")[1].split(",")[0];	
	}

	@Given("^Check Summary section values matched with SL calls with vehicle \"([^\"]*)\" and state \"([^\"]*)\" and slurl \"([^\"]*)\"$")
	public void check_Summary_section_values_matched_with_SL_calls_with_vehicle_and_state_and_slurl(String veh, String state, String slurl) throws Throwable {
		String version=getVisibleElementByXpath("//span[contains(@class,'title')]/following-sibling::*[contains(@id,'select-models')]//a[contains(@class,'select2-choice')]//*[text()!='']").getText().trim().replace(" ", "_");
		String vehName=veh+"_"+version;

		Click_on_Terms_field_to_check_options();
		String VT=term.trim().split(" ")[0];
		slurl=slurl.replace("VMN", version).replace("VT", VT).replace("VDP", String.valueOf(downpayment));

		getVisibleElementByXpath("//div[contains(@id,'s2id_autogen')]").click(); 
		Thread.sleep(2000);
		getVisibleElementByXpath("//div[contains(@class,'drop')]//li[contains(@class,'select') and contains(@class,'results')]//div[contains(text(),'"+state+"')]").click();

		String GetSLresponse=JSONService.getJsonResponse(slurl);

		int monthlyFinRate=convertStrtoDoubletoInt(parseSLforBRcredit(GetSLresponse,"monthlyFinRate"));
		int monthlyCETWithoutFP=convertStrtoDoubletoInt(parseSLforBRcredit(GetSLresponse,"monthlyCETWithoutFP"));
		int annualCETWithoutFP=convertStrtoDoubletoInt(parseSLforBRcredit(GetSLresponse,"annualCETWithoutFP"));
		int monthlyCETWithFP=convertStrtoDoubletoInt(parseSLforBRcredit(GetSLresponse,"monthlyCETWithFP"));
		int annualCETWithFP=convertStrtoDoubletoInt(parseSLforBRcredit(GetSLresponse,"annualCETWithFP"));

		int UImonthlyFinRate=Integer.parseInt(getVisibleElementByXpath("//input[contains(@name,'downPayment')]").getText().trim().substring(1).replace(".", "").replaceAll(",", ""));
		int UImonthlyCETWithoutFP=Integer.parseInt(getVisibleElementByXpath("//input[contains(@name,'downPayment')]").getText().trim().substring(1).replace(".", "").replaceAll(",", ""));
		int UIannualCETWithoutFP=Integer.parseInt(getVisibleElementByXpath("//input[contains(@name,'downPayment')]").getText().trim().substring(1).replace(".", "").replaceAll(",", ""));
		int UImonthlyCETWithFP=Integer.parseInt(getVisibleElementByXpath("//input[contains(@name,'downPayment')]").getText().trim().substring(1).replace(".", "").replaceAll(",", ""));
		int UIannualCETWithFP=Integer.parseInt(getVisibleElementByXpath("//input[contains(@name,'downPayment')]").getText().trim().substring(1).replace(".", "").replaceAll(",", ""));
	}

	@When("^Term is highest and Min downpayment equals (\\d+)% of MSRP$")
	public void term_is_highest_and_Min_downpayment_equals_of_MSRP(int arg1) throws Throwable {
		Click_on_Terms_field_to_check_options();

		int calculatedDownpay=(int) Math.ceil((getVehiclePrice*30)/100);

		System.out.println("Check for Valid Downpayment Amount displayed on UI# "+calculatedDownpay);
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").clear();
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(String.valueOf(calculatedDownpay));
		getVisibleElementByXpath("//input[contains(@name,'downPayment')]").sendKeys(Keys.TAB);	
	}

	@Then("^Monthly Instalments with FP Label value equals \"([^\"]*)\"$")
	public void monthly_Instalments_with_FP_Label_value_equals(String arg1) throws Exception {
		List<WebElement> AmtFP=driver.findElements(By.xpath("//div[contains(@class,'financial-products-card')]//*[contains(@class,'financial-list')]//div"));
		String UIwithFP=AmtFP.get(0).getText().trim();
		if(UIwithFP.contains(arg1)) {
			System.out.println("Monthly Instalments with FP Label value equals to -");
		}
		else {
			Assert.assertFalse("Monthly Instalments with FP Label value does not equals to -",true);
		}
	}

	@Then("^Click on Tabs \"([^\"]*)\"$")
	public void Click_on_Tabs(String arg1) throws Throwable {
		System.out.println("Click on Tabs "+arg1);
		getVisibleElementByXpath("//a[contains(@href,'"+arg1+"')]").click();
	}

	@Then("^Click on ShowPrice \"([^\"]*)\"$")
	public void Click_on_ShowPrice(String arg1) throws Throwable {
		System.out.println("Click on ShowPrice "+arg1);
		getVisibleElementByXpath("//div[@id='"+arg1+"']//a[contains(@ng-click,'showPricePop')]").click();
	}

	@When("^Click on the Show Pricing link in each models$")
	public void Click_on_the_Show_Pricing_link_in_each_models() throws Throwable {
		System.out.println("Click on the Show Pricing link in each models");

		getVisibleElementByXpath("//div[@id='model']//label[contains(@class,'checkbox')]//div[contains(@class,'control__indicator')]");
		List<WebElement> models=driver.findElements(By.xpath("//div[@id='model']//label[contains(@class,'checkbox')]//*[contains(@class,'label-text')]"));
		System.out.println("Total # of Models displayed:"+models.size());
		System.out.println("Model Names are mentioned below:-");
		for(WebElement modeldetails:models) {
			System.out.println(modeldetails.getText().trim());
		}
	}

	@When("^An overlay should show model prices in two different currencies$")
	public void An_overlay_should_show_model_prices_in_two_different_currencies() throws Throwable {
		System.out.println("An overlay should show model prices in two different currencies");
		List<WebElement> modelsinfo=driver.findElements(By.xpath("//div[@id='model']//a[contains(@class,'extra-info')]"));

		for(WebElement modeldetails:modelsinfo) {
			modeldetails.click();
			getVisibleElementByXpath("//div[contains(@ng-repeat,'vehicle.models') and contains(@class,'active')]//p[contains(@class,'txt-price') and contains(text(),'Bs') or contains(text(),'$')]");
			List<WebElement> currency=driver.findElements(By.xpath("//div[contains(@ng-repeat,'vehicle.models') and contains(@class,'active')]//p[contains(@class,'txt-price') and contains(text(),'Bs') or contains(text(),'$')]"));
			if(currency.size()!=2) {
				Assert.assertFalse("Model prices does not displayed in two different currencies",false);
			}
			getVisibleElementByXpath("//div[contains(@ng-repeat,'vehicle.models') and contains(@class,'active')]//div[contains(@ng-bind-html,'model.moreInfo')]//p[text()!='']");
			getVisibleElementByXpath("//section//a[contains(@class,'cancel-btn')]").click();
		}
	}


	@When("^Select any Model$")
	public void Select_any_Model() throws Throwable {
		System.out.println("Select any Model");	   
		getVisibleElementByXpath("//div[@id='model']//label[contains(@class,'checkbox')]//div[contains(@class,'control__indicator')]").click();
		tempStr=getVisibleElementByXpath("//div[@id='model']//label[contains(@class,'checkbox')]//span[contains(@class,'label-text')]").getText().trim(); //Get Model Name for further comparision
	}

	@When("^Select any Color$")
	public void Select_any_Color() throws Throwable {
		System.out.println("Select any Color");	   
		getVisibleElementByXpath("//div[@id='colorandtrim']//*[contains(@class,'label-text')]").click();
	}

	@When("^Click on the Show Pricing link in beside Review and Save CTA Button$")
	public void Click_on_the_Show_Pricing_link_in_beside_Review_and_Save_CTA_Button() throws Throwable {
		System.out.println("Click on the Show Pricing link in beside Review and Save CTA Button");	   
		getVisibleElementByXpath("//a[contains(@class,'priceDrop') and text()!='']").click();
	}

	public String get_the_price_in_Show_Price() throws Throwable {
		System.out.println("See the price in Show Price");	   
		waitTillElemVisiblebyXpath("//div[contains(@class,'panel-price') and contains(@class,'active')]//div[contains(text(),'Bs') or contains(text(),'$')]", 10000);

		String fetchPrice= GetOnlyNumberFromStr(getVisibleElementByXpath("//div[contains(@class,'panel-price') and contains(@class,'active')]//div[contains(text(),'Bs')]").getText().trim(),"-?\\d+") + "~" +
				GetOnlyNumberFromStr(getVisibleElementByXpath("//div[contains(@class,'panel-price') and contains(@class,'active')]//div[contains(text(),'$')]").getText().trim(),"-?\\d+");
		getVisibleElementByXpath("//div[contains(@class,'panel-price') and contains(@class,'active')]//a[contains(@class,'close-btn')]").click();
		return fetchPrice;
	}

	@When("^See the model price in Show Price$")
	public void See_the_modelprice_in_Show_Price() throws Throwable {
		System.out.println("See the price in Show Price");
		String BsDollar=get_the_price_in_Show_Price();
		Bs= Long.parseLong(BsDollar.split("~")[0]);
		Dollar= Long.parseLong(BsDollar.split("~")[1]);
	}

	@When("^See the color and trim price in Show Price$")
	public void See_the_color_and_trim_price_in_Show_Price() throws Throwable {
		System.out.println("See the color and trim price in Show Price");	
		String BsDollar=get_the_price_in_Show_Price();
		Bs= Bs+Long.parseLong(BsDollar.split("~")[0]);
		Dollar= Dollar+Long.parseLong(BsDollar.split("~")[1]);
	}

	@When("^See the Total price in Show Price$")
	public void See_the_Total_price_in_Show_Price() throws Throwable {
		System.out.println("See the Total price in Show Price");	
		String BsDollar=get_the_price_in_Show_Price();
		long TBs= Long.parseLong(BsDollar.split("~")[0]);
		long TDollar=Long.parseLong(BsDollar.split("~")[1]);
		if(TBs!=Bs) {
			Assert.assertFalse("Total Bs price does not matched with the model+color&Trim price", false);
		}
		if(TDollar!=Dollar) {
			Assert.assertFalse("Total $ price does not matched with the model+color&Trim price", false);
		}
	}


	@When("^See Review and Save page also given accurate total prices$")
	public void See_Review_and_Save_page_also_given_accurate_total_prices() throws Throwable {
		System.out.println("See Review and Save page also given accurate total prices");
		getVisibleElementByXpath("//table[contains(@class,'table-pricing')]//*[contains(text(),'Total')]//ancestor::td//following-sibling::td[contains(@class,'txt-price')]");
		List<WebElement> currency=driver.findElements(By.xpath("//table[contains(@class,'table-pricing')]//*[contains(text(),'Total')]//ancestor::td//following-sibling::td[contains(@class,'txt-price')]"));
		long BsCur=Long.parseLong(currency.get(0).getText().split(" ")[1].replace(".","").replace(",",""));
		long DollarCur=Long.parseLong(currency.get(1).getText().split(" ")[1].replace(".","").replace(",",""));

		if(BsCur!=Bs) {
			Assert.assertFalse("Total Bs price does not matched with the model+color&Trim price", false);
		}
		if(DollarCur!=Dollar) {
			Assert.assertFalse("Total $ price does not matched with the model+color&Trim price", false);
		}
	}


	@When("^See disclosure id configured$")
	public void See_disclosure_id_configured() throws Throwable {
		System.out.println("See disclosure id configured");
		getVisibleElementByXpath("//div[contains(@class,'panel-price') and contains(@class,'active')]//sup[contains(@class,'gux-disclosure')]");
		List<WebElement> currency=driver.findElements(By.xpath("//div[contains(@class,'panel-price') and contains(@class,'active')]//sup[contains(@class,'gux-disclosure')]"));
		if(currency.size()<2) {
			Assert.assertFalse("Disclosure id configuration is missing for all currencies, # of Disclosure displayed-"+currency.size(),false);
		}
	}

	@When("^corresponding disclosure should be displaying in Disclosure box$")
	public void corresponding_disclosure_should_be_displaying_in_Disclosure_box() throws Throwable {
		System.out.println("corresponding disclosure should be displaying in Disclosure box");
		getVisibleElementByXpath("//div[contains(@class,'panel-price') and contains(@class,'active')]//sup[contains(@class,'gux-disclosure')]");
		List<WebElement> disclosures=driver.findElements(By.xpath("//div[contains(@class,'panel-price') and contains(@class,'active')]//sup[contains(@class,'gux-disclosure')]"));
		for(WebElement clkDisclosureId:disclosures) {
			clkDisclosureId.click();
			getVisibleElementByXpath("//div[contains(@class,'gux-tooltip-overlay')]");
			getVisibleElementByXpath("//div[contains(@class,'gux-tooltip-overlay')]//*[contains(@class,'gux-tooltip-overlay-title') and text()!='']");
			getVisibleElementByXpath("//div[contains(@class,'gux-tooltip-overlay')]//*[contains(@class,'gux-tooltip-overlay-text') and text()!='']");
			getVisibleElementByXpath("//div[contains(@class,'gux-tooltip-overlay')]//a[contains(@class,'close-icon')]").click();
		}
		Thread.sleep(3000);
		getVisibleElementByXpath("//div[contains(@class,'panel-price') and contains(@class,'active')]//a[contains(@class,'close-btn')]").click();
	}

	@Then("^user can make a request of quote after filling all the form details$")
	public void user_can_make_a_request_of_quote_after_filling_all_the_form_details(DataTable param) throws Throwable{
		System.out.println("user can make a request after filling all the details in the page");
		List<List<String>> data=param.raw();
		getVisibleElementByXpath("//input[@name='Name' or @name='INDIVIDUAL_CUST_FIRST_NAME']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='CUSTOMER_ADDRESS_LINE1']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='MOBILE_PHONE_NUMBER']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//span[contains(@class,'select2-chosen')]");
		List<WebElement> selectitems=driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
		for(WebElement items:selectitems) {
			items.click();
			getVisibleElementByXpath("//li/div[contains(@class,'select2-result-label') and not(contains(text(),'Please Select'))]").click();
			Thread.sleep(2000);
		}
		getVisibleElementByXpath("//div[contains(@class,'form-checkbox')]//label");
		List<WebElement> SelTerm=driver.findElements(By.xpath("//div[contains(@class,'form-checkbox')]//label"));
		for(WebElement items:SelTerm) {
			items.click();
			Thread.sleep(1000);
		}
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys(EnterCaptcha());
		getVisibleElementByXpath("//div[contains(@class,'form-submit')]//*[contains(text(),'Submit')]").click();  
	}


	@When("^verify that Nameplate name is removed from the B&P Pages$")
	public void verify_that_Nameplate_name_is_removed_from_the_BandP_Pages() throws Throwable {
		System.out.println("verify that Nameplate name is removed from the B&P Pages");
		verifyInvisibleElem("//*[@ng-hide='isoVE' and contains(@class,'hide') and text()!='']");
		getVisibleElementByXpath("//*[contains(text(),'"+tempStr+"')]");
	}


	@When("^Check the prices displaying is followed the Currency format and Rounding off option$")
	public void Check_the_prices_displaying_is_followed_the_Currency_format_and_Rounding_off_option(DataTable arg1) throws Throwable{
		System.out.println("Select a Vehicle");
		List<List<String>> data=arg1.raw();
		getVisibleElementByXpath("//td[contains(@class,'txt-price') and contains(text(),'Bs')]");
		List<WebElement> selectitems=driver.findElements(By.xpath("//td[contains(@class,'txt-price') and contains(text(),'Bs')]"));
		String BSCurrLen=data.get(0).get(0).split(" " )[1].trim();
		for(WebElement items:selectitems) {
			String BScurrency=items.getText().trim().split(" ")[1].trim();
			BScurrency = BScurrency.replaceAll("\\d", "#");
			int pricelen=BScurrency.length();
			if(!BSCurrLen.substring(BSCurrLen.length()-pricelen).equals(BScurrency)) {
				Assert.assertFalse("Prices displaying does not followed the Currency format in market configuration", false);
			}
		}

		getVisibleElementByXpath("//td[contains(@class,'txt-price') and contains(text(),'$')]");
		selectitems=driver.findElements(By.xpath("//td[contains(@class,'txt-price') and contains(text(),'$')]"));
		String DolrCurrLen=data.get(0).get(1).split(" " )[1].trim();
		for(WebElement items:selectitems) {
			String Dcurrency=items.getText().trim().split(" ")[1].trim();
			Dcurrency = Dcurrency.replaceAll("\\d", "#");
			int pricelen=Dcurrency.length();
			if(!DolrCurrLen.substring(DolrCurrLen.length()-pricelen).equals(Dcurrency)) {
				Assert.assertFalse("Prices displaying does not followed the Currency format in market configuration", false);
			}
		}
	}

	@When("^Select any Vehicle$")
	public void select_any_Vehicle(DataTable arg1) throws Throwable{
		System.out.println("Select any Vehicle");
		List<List<String>> data=arg1.raw();
		waitTillElemVisiblebyXpath("//*[contains(text(),'"+data.get(0).get(0)+"')]/preceding-sibling::*//img", 240);
		getVisibleElementByXpath("//*[contains(text(),'"+data.get(0).get(0)+"')]/preceding-sibling::*//img").click();
		waitTillElemVisiblebyXpath("//span[@class='title']", 240);
	}


	@And("^Select a Model$")
	public void select_a_Model(DataTable arg1) throws Throwable{
		System.out.println("Select a Model");
		List<List<String>> data=arg1.raw();
		waitTillElemVisiblebyXpath("//span[@class='title']", 240);
		List<WebElement> selectitems=driver.findElements(By.xpath("//span[@class='title']"));
		JavascriptExecutor js = (JavascriptExecutor) driver;  
		tempStr=js.executeScript("return arguments[0].textContent;",selectitems.get(Integer.parseInt(data.get(0).get(0)))).toString();
		selectitems.get(Integer.parseInt(data.get(0).get(0))).click();
	}

	@Then("^See the selected vehicle displayed in the test drive form$")
	public void See_the_selected_vehicle_displayed_in_the_test_drive_form() throws Throwable{
		System.out.println("See the selected vehicle displayed in the test drive form");
		waitTillElemVisiblebyXpath("//span[@class='vehicledisplaytitle' and contains(text(),'"+tempStr+"')]", 240);
		getVisibleElementByXpath("//span[@class='vehicledisplaytitle' and contains(text(),'"+tempStr+"')]");
	}

	@When("^Enter all items correctly and Submit$")
	public void Enter_all_items_correctly_and_Submit_RAQ(DataTable param) throws Throwable {
		System.out.println("enter all items correctly and click on submit");
		List<List<String>> data=param.raw();
		getVisibleElementByXpath("//span[contains(text(),'Name')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+data.get(0).get(0)+"']").click();
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_FIRST_NAME']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_LAST_NAME']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='MOBILE_PHONE_NUMBER']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//input[@name='HOME_PHONE_NUMBER']").sendKeys(data.get(0).get(5));
		getVisibleElementByXpath("//input[@name='searchPostcode']").sendKeys(data.get(0).get(6));
		Thread.sleep(2000);
		getVisibleElementByXpath("//span[contains(text(),'Preferred Dealership')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[not(contains(text(),'Please select')) and not(contains(text(),'Please Select'))]").click();
		getVisibleElementByXpath("//span[contains(text(),'Model')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[not(contains(text(),'Please select')) and not(contains(text(),'Please Select'))]").click();

		getVisibleElementByXpath("//label[contains(@for,'legalAgreement')]//span[text()!='']").click();
		getVisibleElementByXpath("//label[contains(@for,'CONTACT_PERMISSION_TYPE_CODE') and contains(text(),'No')]").click();
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys(EnterCaptcha());
		getVisibleElementByXpath("//button[contains(@class,'cta-button') and text()!='']").click();
	}

	@When("^Enter province \"([^\"]*)\" and some text in the autosearch box for City field \"([^\"]*)\"$")
	public void enter_province_and_some_text_in_the_autosearch_box_for_City_field(String arg1, String arg2) throws Throwable {
		System.out.println("Enter province and some text in the autosearch box for City field");
		getVisibleElementByXpath("//span[contains(text(),'省')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+arg1+"']").click();
		getVisibleElementByXpath("//span[contains(text(),'市')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//input[@role='combobox']").sendKeys(arg2);

	}

	@Then("^system should display the cities starting with \"([^\"]*)\"$")
	public void system_should_display_the_cities_starting_with_below_name(String arg1) throws Throwable {
		System.out.println("system should display the cities starting with");
		getVisibleElementByXpath("//li//*[starts-with(text(),'"+arg1+"')]").click();
	}

	@Then("^No values should be searched and show a validation message$")
	public void no_values_should_be_searched_and_show_a_validation_message() throws Throwable {
		System.out.println("No values should be searched and show a validation message");
		getVisibleElementByXpath("//li[contains(@class,'no-results')]");
	}

	@When("^Select the desired dropdown options for nameplate$")
	public void Select_the_desired_dropdown_options_for_nameplate() throws Throwable {
		System.out.println("Select the desired dropdown options for nameplate");
		getVisibleElementByXpath("//span[contains(text(),'车型')]/following-sibling::div//span[@id!='']");
		getVisibleElementByXpath("//span[contains(text(),'系列')]/following-sibling::div//span[@id!='']");
	}

	@Then("^See correspondign models should be displayed to the user$")
	public void See_correspondign_models_should_be_displayed_to_the_user() throws Throwable {
		System.out.println("See correspondign models should be displayed to the user");
		getVisibleElementByXpath("//span[contains(text(),'车型')]/following-sibling::div//span[@id!='']").click();
		List<WebElement> Nameplates=driver.findElements(By.xpath("//li/div[text()!='' and not(contains(text(),'undefined'))]"));
		Thread.sleep(3000);
		getVisibleElementByXpath("//li/div[text()!='' and not(contains(text(),'undefined'))]").click();
		boolean exitfindings=false;
		for(int NPitems=0;NPitems<Nameplates.size();NPitems++) {
			getVisibleElementByXpath("//span[contains(text(),'车型')]/following-sibling::div//span[@id!='']").click();
			Nameplates=driver.findElements(By.xpath("//li/div[text()!='' and not(contains(text(),'undefined'))]"));
			Nameplates.get(NPitems).click();
			getVisibleElementByXpath("//span[contains(text(),'系列')]/following-sibling::div//span[@id!='']").click();
			List<WebElement> series=driver.findElements(By.xpath("//li/div[text()!='' and not(contains(text(),'undefined'))]"));
			Thread.sleep(3000);
			if(driver.findElements(By.xpath("//li[contains(text(),'undefined')]")).size()==0) {
				getVisibleElementByXpath("//span[contains(text(),'系列')]/following-sibling::div//span[@id!='']").click();
			}
			if(series.size()==0) {

			}else{
				exitfindings=true;
				break;
			}
		}
		if(exitfindings==false) {
			Assert.assertFalse("Series does not found", false);
		}
	}

	@Then("^Verify thank you page is seenP2$")
	public void verify_thank_you_page_is_seenP2() throws Throwable {
		System.out.println("Verify thank you page is seen");
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==false) {
			waitTillElemVisiblebyXpath("//div[@class='desktop hideForMobile']//*[contains(text(),'Thank You')]", 240);
			getVisibleElementByXpath("//div[@class='desktop hideForMobile']//*[contains(text(),'Thank You')]");
		}
	}
}
