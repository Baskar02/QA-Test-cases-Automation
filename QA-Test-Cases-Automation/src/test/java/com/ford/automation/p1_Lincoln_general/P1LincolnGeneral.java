package com.ford.automation.p1_Lincoln_general;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.ford.automation.base.BaseTest;
import com.google.common.base.Verify;

import config.Configuration;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;

@SuppressWarnings("deprecation")
public class P1LincolnGeneral extends BaseTest {
	@Given("^Open Firefox browser on Lincoln$")
	public void openFireFoxBrowser() throws Throwable {
		System.out.println("Open FireFox browser");
		System.setProperty("webdriver.gecko.driver", Configuration.PATH_TO_GECKO_DRIVER);
		driver = new FirefoxDriver();
	}

	@Given("^Open Chrome browser on Lincoln$")
	public void openChromeBrowser() throws Throwable {
		System.out.println("Open Chrome browser");
//		System.setProperty("webdriver.chrome.driver", Configuration.PATH_TO_CHROME_DRIVER);
		
//		driver = new ChromeDriver();
//
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.open('','testwindow','width=400,height=200')");
		driver.close();
		driver.switchTo().window("testwindow");
		js.executeScript("window.moveTo(0,0);");
		js.executeScript("window.resizeTo(1450,1000);");
	}

	@When("^Maximize browser and enter link \"(.*?)\" on P1 Lincoln$")
	public void openTestLink(String link) throws Throwable {
		System.out.println("Maximize browser and enter link");
		driver.manage().window().maximize();
		driver.get(getProfileURL(link));
		if (isAlertPresent()) {
		    driver.switchTo().alert();
		    driver.switchTo().alert().accept();
		    driver.switchTo().defaultContent();
		}
	}

	@Then("^See the latest version of JS \"(.*?)\" on P1 Lincoln$")
	public void seeTheLatestVersionOfJS(String version) throws Throwable {
		System.out.println("See the latest version of JS");
		// log.debug("See the latest version of JS");
		String versionStringToVerify = "var aemGuxfoapUiReleaseVersion = '" + version + "'";

		Boolean correctVersion = driver.getPageSource().contains(versionStringToVerify);
		if (!correctVersion) {
			throw new Exception("Incorrect version!");
		}
	}

	@When("^Redirect to link \"(.*?)\" on P1 Lincoln$")
	public void redirectToLink(String link) throws Throwable {
		System.out.println("Redirect to link");
		Thread.sleep(30000);
		driver.get(getProfileURL(link));
		Thread.sleep(5000);
		if (isAlertPresent()) {
		    driver.switchTo().alert();
		    driver.switchTo().alert().accept();
		    driver.switchTo().defaultContent();
		}
	}

	@Then("^See all components on home page loaded without performance issue on P1 Lincoln$")
	public void seeHomePageLoadWithoutIssue() throws Throwable {
		System.out.println("See all components on home page loaded without performance issue on Lincoln");
		getVisibleElementByXpath("//a[@class='navbar-brand logo']/img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'预约试驾')]");
		// getVisibleElementByXpath("//span[@class='select2-selection__rendered' and
		// @title='请选择省市']");
		// getVisibleElementByXpath("//span[@class='select2-selection__rendered' and
		// @title='请选择地区']");
		// getVisibleElementByXpath("//button[contains(text(),' 确认提交')]");
	}

	@Then("^See all components on Article page loaded without performance issue on P1 Lincoln$")
	public void seeAllComponentsOnArticlePageLoadedWithoutPerformanceIssue(DataTable parameter) throws Throwable {
		System.out.println("See all components on Article page loaded without performance issue");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//*[contains(@class,'article-details')]/*[contains(@class,'title') and text()!='']");//("//*[contains(@class,'title')]/*[text()!='']");
		getVisibleElementByXpath("//*[contains(@class,'title')]/following-sibling::p[text()!='']");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_pdf']");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_share-thin']");
		Thread.sleep(3000);
	}

	@When("^Click on Share button on Press Release page on P1 Lincoln$")
	public void clickOnShareButtonOnPressReleasePage() throws Throwable {
		System.out.println("Click on Share button on Press Release page");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_share-thin']").click();
	}

	@When("^Click on Arrange a test drive on Main Navigation on P1 Lincoln$")
	public void clickOnArrangeTestDriveOnMainNavigation() throws Throwable {
		System.out.println("Click on Arrange a test drive on Main Navigation");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[text()='预约试驾']").click();
	}

	@Then("^See Test Drive form on P1 Lincoln$")
	public void seeTestDriveForm() throws Throwable {
		System.out.println("See Test Drive form");
		getVisibleElementByXpath("//strong[text()='预约试驾']");
		getVisibleElementByXpath("//span[contains(text(),'请选择称呼 ')]");
		getVisibleElementByXpath("//select[@class='title']");
		getVisibleElementByXpath("//span[contains(text(),'姓名')]");
		getVisibleElementByXpath("//input[@name='lastName']");
		getVisibleElementByXpath("//input[@name='firstName']");
		getVisibleElementByXpath("//span[contains(text(),'电子邮箱')]");
		getVisibleElementByXpath("//input[@name='email' or @type='email']");
		getVisibleElementByXpath("//span[contains(text(),'手机号码 ')]");
		getVisibleElementByXpath("//input[@name='mobile']");
		getVisibleElementByXpath("//span[contains(text(),'省')]");
		getVisibleElementByXpath("//select[@name='state']");
		getVisibleElementByXpath("//span[contains(text(),'市')]");
		getVisibleElementByXpath("//select[@name='city']");
		getVisibleElementByXpath("//span[contains(text(),'希望哪间经销商与你联系？')]");
		getVisibleElementByXpath("//select[@name='preferredDealer']");
		getVisibleElementByXpath("//span[contains(text(),'请选择偏好林肯车系')]");
		getVisibleElementByXpath("//select[@name='nameplate']");
		getVisibleElementByXpath("//span[contains(text(),'何时计划购买')]");
		getVisibleElementByXpath("//option[text()='3个月内']/ancestor::select");//// select[@name='mktPurchaseTime']");
		getVisibleElementByXpath("//label[contains(text(),'请输入验证码')]");
		getVisibleElementByXpath("//img[@alt='Image']");
		getVisibleElementByXpath("//div[text()='刷新']");
		getVisibleElementByXpath("//input[@id='captchaValue']");
		getVisibleElementByXpath("//div[@class='checkbox form-checkbox']//span[@class='checkbox-style gux-icon-check-25px']");
		getVisibleElementByXpath("//button[contains(text(),' 确认提交')]");
	}

	@When("^Fill in Test Drive Form on P1 Lincoln$")
	public void fillInTestDriveForm(DataTable parameters) throws Throwable {
		System.out.println("Fill in Test Drive Form");
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath("//select[@class='title']").click();
		getVisibleElementByXpath("//select[@name='title']//option[contains(text(),'" + data.get(0).get(0) + "')]").click();
		// driver.findElement(By.name("lastName")).sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(1));
		// driver.findElement(By.name("firstName")).sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='email' or @type='email']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='mobile']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//select[@name='state']").click();
		getVisibleElementByXpath("//select[@name='state']/option[text()='" + data.get(0).get(5) + "']").click();
		getVisibleElementByXpath("//select[@name='city']").click();
		getVisibleElementByXpath("//select[@name='city']/option[text()='" + data.get(0).get(6) + "']").click();
		getVisibleElementByXpath("//select[@name='preferredDealer']").click();
		getVisibleElementByXpath("//select[@name='preferredDealer']/option[contains(text(),'" + data.get(0).get(7) + "')]").click();
		getVisibleElementByXpath("//select[@name='nameplate']").click();
		getVisibleElementByXpath("//select[@name='nameplate']/option[contains(text(),'" + data.get(0).get(8) + "')]").click();
		getVisibleElementByXpath("//option[text()='3个月内']/ancestor::select").click();//// select[@name='mktPurchaseTime']").click();
		getVisibleElementByXpath("//option[text()='" + data.get(0).get(9) + "']").click(); //// select[@name='mktPurchaseTime']/option[text()='" + data.get(0).get(9) + "']
		getVisibleElementByXpath("//input[@id='captchaValue']").sendKeys(EnterCaptcha());
		getVisibleElementByXpath("//div[@class='checkbox form-checkbox']//span[@class='checkbox-style gux-icon-check-25px']").click();
		getVisibleElementByXpath("//button[contains(text(),'确认提交') and contains(@class,'full')]").click(); // changes propery 14/8 during perf testing - old //button[contains(text(),'
																											// 确认提交')]
	}

	@Then("^See all components on home page loaded without performance issue on P1 INT Lincoln$")
	public void seeHomePageLoadWithoutIssueInInt() throws Throwable {
		System.out.println("See all components on home page loaded without performance issue on Lincoln");
		getVisibleElementByXpath("//a[@class='navbar-brand logo']/img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@class='secondary-navi-tab nav navbar-nav']//span[text()='林肯MKZ']");
		getVisibleElementByXpath("//ul[@class='secondary-navi-tab nav navbar-nav']//span[contains(text(),'车型概览 ')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[text()='经销商网络']");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[text()='预约试驾']");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[text()='配置林肯']");
		getVisibleElementByXpath("//a[contains(text(),'外观360')]");
		getVisibleElementByXpath("//a[contains(text(),'内饰360')]");
		getVisibleElementByXpath("//div[contains(@class,'hidden-xs')]/a[text()='配置MKZ']");
	}

	@Then("^See all components on MKC Overview P1 Lincoln$")
	public void seeAllComponentsOnMkcOverview() throws Throwable {
		System.out.println("See all components on MKC Overview");
		getVisibleElementByXpath("//span[@class='secondary-menu-name' and contains(text(),'MKC')]");
		getVisibleElementByXpath("//span[@class='secondary-navi-title hidden-xs' and contains(text(),'Overview')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'모델 및 사양')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'브로셔 다운로드')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'시승 신청')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//span[text()='LINCOLN MKC']");
		getVisibleElementByXpath("//div[@class='hidden-xs']//span[text()='Lincoln MKC Video']");
		getVisibleElementByXpath("//span[text()='360° View and Colorizer']");
		getVisibleElementByXpath("//ul[@class='nav-tabswidth']//b[contains(text(),'360° Exterior')]");
		getVisibleElementByXpath("//ul[@class='nav-tabswidth']//b[contains(text(),'Colorizer')]");
		getVisibleElementByXpath("//div[contains(@class,'hidden-xs')]/a[text()='모델 비교']");
	}

	@When("^Click on video play button on MKC Overview P1 Lincoln$")
	public void clickOnVideoPlayButtonOnMkcOverview() throws Throwable {
		System.out.println("Click on video play button on MKC Overview");
		getVisibleElementByXpath("//div[@class='hidden-xs']//span[@class='lincoln-icon_play-thin']").click();
	}

	@Then("^See all components on video overlay on P1 Lincoln$")
	public void seeAllComponentsOnVideoOverlay() throws Throwable {
		System.out.println("See all components on video overlay");
		getVisibleElementByXpath("//span[@id='but_pause']");
		getVisibleElementByXpath("//div[@id='vid_5348660500001']/div[2]");
	}

	@Then("^See Thank you overlay on P1 Lincoln$")
	public void seeThankYouOverlay() throws Throwable {
		System.out.println("See Thank you overlay");
		// waitTillElemVisiblebyXpath("//h4[contains(text(),'衷心感谢')]", 240);
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==false) {
			Thread.sleep(60000);
			getVisibleElementByXpath("//*[contains(text(),'衷心感谢')]"); //OLD xpath //h4[contains(text(),'衷心感谢')]
		}
	}

	@When("^Click on Close on Thank you overlay on P1 Lincoln$")
	public void clickOnCloseOnThankYouOverlay() throws Throwable {
		System.out.println("Click on Close on Thank you overlay");
		try {
		
			getVisibleElementByXpath("//span[@class='modal-close style-small hidden-xs']").click();
		}catch(Exception e) {
			System.out.println("Thank you overlay does not exist");
		}	
	}

	@When("^Click on Compare Model on P1 INT Lincoln$")
	public void clickOnCompareModelInInt() throws Throwable {
		System.out.println("Click on Compare Model");
		// getVisibleElementByXpath("//div[4]/div[3]/div[9]/section/div[1]/div/div[2]/a[text()='车型对比']").click();
		waitTillElemVisiblebyXpath("//a[@class='lincoln-icon_angle-right btn btn-solid bg-gold current-window' and text()='车型对比']", 300);
		getVisibleElementByXpath("//a[@class='lincoln-icon_angle-right btn btn-solid bg-gold current-window' and text()='车型对比']").click();
	}

	@Then("^See all components on Compare Model page on P1 INT Lincoln$")
	public void seeAllComponentsOnCompareModelPageInInt() throws Throwable {
		System.out.println("See all components on Compare Model page");
		getVisibleElementByXpath("//div[contains(@class,'hidden-xs')]//a[contains(text(),' 配置对比')]");
		getVisibleElementByXpath("//span[text()='MKZ 尊悦版']");
		getVisibleElementByXpath("//img[@alt='MKZ 尊悦版']");
		getVisibleElementByXpath("//span[text()='MKZ 尊享版']");
		getVisibleElementByXpath("//img[@alt='MKZ 尊享版']");
		getVisibleElementByXpath("//span[text()='MKZ 尊耀版']");
		getVisibleElementByXpath("//img[@alt='MKZ 尊耀版']");
		getVisibleElementByXpath("//span[text()='MKZ 尊雅版']");
		getVisibleElementByXpath("//img[@alt='MKZ 尊雅版']");
		getVisibleElementByXpath("//span[text()='MKZ H 混合动力 尊雅版']");
		getVisibleElementByXpath("//img[@alt='MKZ H 混合动力 尊雅版']");
		getVisibleElementByXpath("//span[text()='MKZ H 混合动力 尊耀版']");
		getVisibleElementByXpath("//img[@alt='MKZ H 混合动力 尊耀版']");
		getVisibleElementByXpath("//span[text()='MKZ H 混合动力 尊享版']");
		getVisibleElementByXpath("//img[@alt='MKZ H 混合动力 尊享版']");
	}

	@When("^Click on Configuration Comparison button on P1 INT Lincoln$")
	public void clickOnConfigurationComparisonButtonInInt() throws Throwable {
		System.out.println("Click on Configuration Comparison button");
		getVisibleElementByXpath("//div[contains(@class,'hidden-xs')]/a[contains(text(),'配置对比')]").click();
		Thread.sleep(5000);
	}

	@And("^Click on Select vehicle button on P1 Liconln$")
	public void clickOnSelectVehicleButton() throws Throwable {
		System.out.println("Click on Select vehicle button");
		getVisibleElementByXpath("//div[contains(@class,'hidden-xs')]/a[contains(text(),' 取消选择')]").click();
	}

	@When("^Click on three models on Compare Model page on P1 INT Lincoln$")
	public void clickOnThreeModelsOnCompareModelPageInInt() throws Throwable {
		System.out.println("Click on three models on Compare Model page");
		Thread.sleep(3000);
		getVisibleElementByXpath("//span[text()='MKZ 尊悦版']").click();
		Thread.sleep(3000);
		getVisibleElementByXpath("//span[text()='MKZ 尊享版']");
		Thread.sleep(3000);
		getVisibleElementByXpath("//span[text()='MKZ 尊耀版']");
	}

	@When("^Click on Continue Comparison button on P1 INT Lincoln$")
	public void clickOnContinueComparisonButtonInInt() throws Throwable {
		System.out.println("Click on Continue Comparison button");
		getVisibleElementByXpath("//a[contains(text(),'下一步')]").click();
		Thread.sleep(5000);
	}

	@Then("^See all components in Compare page on P1 Lincoln$")
	public void seeAllComponentsInComparePage(DataTable parameter) throws Throwable {
		System.out.println("See all components in Compare page");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//h2[contains(text(),'" + data.get(0).get(0) + "')]");
		for (int i = 1; i <= 3; i++) {
			getVisibleElementByXpath("//div[contains(@class,'modal-compare-info')]/h4[contains(text(),'" + data.get(0).get(i) + "')]");
		}

		for (int j = 4; j <= 7; j++) {
			getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(j) + "')]");
		}

		getVisibleElementByXpath("//div[@id='nameplateOne']/h4/a/div/div/span");
	}

	@When("^Select province and city in Mini LAD on P1 Lincoln$")
	public void selectProvinceAndCityInMiniLad(DataTable parameter) throws Throwable {
		System.out.println("Select province and city in Mini LAD");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//span[@class='select2-selection__rendered' and @title='选择省']").click();
		getVisibleElementByXpath("//li[@class='select2-results__option' and text()='" + data.get(0).get(0) + "']").click();
		Thread.sleep(3000);
		getVisibleElementByXpath("//span[@class='select2-selection__rendered' and @title='选择城市']").click();
		getVisibleElementByXpath("//li[contains(text(),'" + data.get(0).get(1) + "')]").click();
	}

	@And("^Click on Search button in Mini LAD on P1 Lincoln$")
	public void clickOnSearchButtonInMiniLad() throws Throwable {
		System.out.println("Click on Search button in Mini LAD");
		getVisibleElementByXpath("//button[contains(text(),'搜索')]").click();
	}

	@Then("^See the result in Map on P1 Lincoln$")
	public void seeTheResultInMap() throws Throwable {
		System.out.println("See the result in Map");
		Thread.sleep(30000);
		getVisibleElementByXpath("//div[@class='amap-marker-label' and text()='1']");
		getVisibleElementByXpath("//section[contains(@class,'dealer-result-container') and not(contains(@class,'ng-hide'))]//div[contains(@class,'filter-dealer-result-container')]//*[contains(@class,'number') and text()!='']");
		getVisibleElementByXpath("//section[contains(@class,'dealer-result-container') and not(contains(@class,'ng-hide'))]//div[contains(@class,'filter-dealer-result-container')]//*[contains(@class,'dealer-name') and text()!='']");
	}

	@When("^Click on Dealer Information in Map on P1 Lincoln$")
	public void clickOnDealerInformationInMap() throws Throwable {
		System.out.println("Click on Dealer Information in Map");
		getVisibleElementByXpath("//a[contains(text(),'经销商信息')]").click();
	}

	@When("^Click on Dealer Details in Map on P1 Lincoln$")
	public void clickOnDealerDetailsInMap() throws Throwable {
		System.out.println("Click on Dealer Details in Map");
		getVisibleElementByXpath("//div[3]/div[3]/div[1]/div/section/div[1]/div[2]/section/div[2]/div[1]/div[1]/div[3]/div[3]/a[@class='btn-detail-info']").click();
	}

	@Then("^See Dealer Details information in Map on P1 Lincoln$")
	public void seeDealerDetailsInformationInMap() throws Throwable {
		System.out.println("See Dealer Details information in Map");
		getVisibleElementByXpath("//p[contains(text(),'021-52707000')]");
		getVisibleElementByXpath("//div[@id='dealer-detail']/div[1]/div[1]/h4/a/div/div/p[text()='营业时间']");
	}

	@When("^Click on MKZ secondary navigation on P1 Lincoln$")
	public void clickOnMkzSecondaryNavigation() throws Throwable {
		System.out.println("Click on MKZ secondary navigation");
		getVisibleElementByXpath("//span[contains(text(),'林肯 MKZ')]").click();
	}

	@When("^Click on MKZ secondary navigation on P1 INT Lincoln$")
	public void clickOnMkzSecondaryNavigationInInt() throws Throwable {
		System.out.println("Click on MKZ secondary navigation");
		getVisibleElementByXpath("//ul[@class='secondary-navi-tab nav navbar-nav']//span[text()='林肯MKZ']").click();
		// getVisibleElementByXpath("//li[@class='dropdown first']").click();
	}

	@Then("^See MKZ dropdown list on P1 INT Lincoln$")
	public void seeMkzDropdownListInInt(DataTable parameters) throws Throwable {
		System.out.println("See MKZ dropdown list");
		List<List<String>> data = parameters.raw();
		for (int i = 0; i <= 7; i++) {
			getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(i) + "')]");
		}
	}

	@Then("^See MKZ dropdown list on P1 Lincoln$")
	public void seeMkzDropdownList(DataTable parameters) throws Throwable {
		System.out.println("See MKZ dropdown list");
		List<List<String>> data = parameters.raw();
		for (int i = 0; i <= 8; i++) {
			getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(i) + "')]");
		}
	}

	@When("^Click on option in MKZ dropdown list on P1 Lincoln$")
	public void clickOnOptionInMkzDropdownList(DataTable option) throws Throwable {
		System.out.println("Click on option in MKZ dropdown list");
		List<List<String>> data = option.raw();
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(0) + "')]").click();
	}

	@Then("^See all images on MKZ Gallery on P1 Lincoln$")
	public void seeAllImagesOnMkzGallery(DataTable parameters) throws Throwable {
		System.out.println("See all images on MKZ Gallery");
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[text()='" + data.get(0).get(0) + "']");
		for (int i = 1; i <= 4; i++) {
			getVisibleElementByXpath("//figure/img[@alt='" + data.get(0).get(i) + "']");
		}
	}

	@Then("^See all exterior images on MKZ Gallery on P1 PERF Lincoln$")
	public void seeAllExteriorImagesOnMkzGalleryInPerf(DataTable parameters) throws Throwable {
		System.out.println("See all exterior images on MKZ Gallery");
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[text()='" + data.get(0).get(0) + "']");
		for (int i = 1; i <= 4; i++) {
			getVisibleElementByXpath("//div[1]/section//figure/img[@alt='" + data.get(0).get(i) + "']");
		}
	}

	@Then("^See all exterior images on MKZ Gallery on P1 INT Lincoln$")
	public void seeAllExteriorImagesOnMkzGalleryInInt(DataTable parameters) throws Throwable {
		System.out.println("See all exterior images on MKZ Gallery");
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[text()='" + data.get(0).get(0) + "']");
//		try {
//			for (int i = 1; i <= 4; i++) {
//				getVisibleElementByXpath("//div[1]/section//figure/img[@alt='" + data.get(0).get(i) + "']");
//			}
//		}catch(Exception e){
			for (int i = 1; i <= 4; i++) {
				getVisibleElementByXpath("//div[@data-url='.exterior']//figure/img[@alt='" + data.get(0).get(i) + "']");
			}
//		}
	}

	@Then("^See all interior images on MKZ Gallery on P1 INT Lincoln$")
	public void seeAllInteriorImagesOnMkzGalleryInInt(DataTable parameters) throws Throwable {
		System.out.println("See all interior images on MKZ Gallery");
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[text()='" + data.get(0).get(0) + "']");
//		try {
//			for (int i = 1; i <= 4; i++) {
//				getVisibleElementByXpath("//div[2]/section//figure/img[@alt='" + data.get(0).get(i) + "']");
//			}
//		}
//		catch(Exception e){
			for (int i = 1; i <= 4; i++) {
				getVisibleElementByXpath("//div[@data-url='.interior']//figure/img[@alt='" + data.get(0).get(i) + "']");
			}
//		}
	}

	@Then("^See all videos on MKZ Gallery on P1 Lincoln$")
	public void seeAllVideosOnMkzGallery(DataTable parameters) throws Throwable {
		System.out.println("See all videos on MKZ Gallery");
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[text()='" + data.get(0).get(0) + "']");
		for (int i = 1; i <= 2; i++) {
			getVisibleElementByXpath("//img[@alt='" + data.get(0).get(i) + "']");
		}
	}

	@When("^Click on image on MKZ Gallery on P1 Lincoln$")
	public void clickOnImageOnMkzGallery() throws Throwable {
		System.out.println("Click on image on MKZ Gallery");
		getVisibleElementByXpath("//figure/img[@alt='Int_Image1']").click();
	}

	@When("^Click on exterior image on MKZ Gallery on P1 INT Lincoln$")
	public void clickOnImageOnMkzGalleryInInt() throws Throwable {
		System.out.println("Click on exterior image on MKZ Gallery");
		// getVisibleElementByXpath("//div[4]/div[3]/div[1]/div[1]/section/div[2]/div[1]/div/div[1]/div[1]/div/div/a/figure/img").click();
		getVisibleElementByXpath("//a[@data-target='.exterior']//img");
		driver.findElements(By.xpath("//a[@data-target='.exterior']//img")).get(0).click();
	}

	@Then("^See all components on exterior image on P1 PERF Lincoln$")
	public void seeAllComponentsOnExteriorImageInPerf() throws Throwable {
		System.out.println("See all components on exterior image");
		getVisibleElementByXpath("//div[6]/div/img[@alt='image 1']");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_download-thin']");
		getVisibleElementByXpath("//a[@class='btn-icon btn-share js-download-image']");
		getVisibleElementByXpath("//span[@class='lincoln-icon_share-thin']");
		getVisibleElementByXpath("//a[@class='btn-icon btn-share last']");
		getVisibleElementByXpath("//span[@class='modal-close style-small hidden-xs']");
	}

	@Then("^See all components on exterior image on P1 INT Lincoln$")
	public void seeAllComponentsOnExteriorImageInInt() throws Throwable {
		System.out.println("See all components on exterior image");
		getVisibleElementByXpath("//div[contains(@class,'exterior')]//img[@alt='image 1']");
		getVisibleElementByXpath("//div[contains(@class,'exterior')]//span[@class='lincoln-icon_share-thin']");
		getVisibleElementByXpath("//div[contains(@class,'exterior')]//a[@class='btn-icon btn-share last']");
		getVisibleElementByXpath("//div[contains(@class,'exterior')]//span[@class='modal-close style-small hidden-xs']");
	}

	@When("^Click on Share button on exterior image on P1 INT Lincoln$")
	public void clickOnShareButtonOnExeriorImageInInt() throws Throwable {
		System.out.println("Click on Share button on exterior image");
		getVisibleElementByXpath("//span[@class='lincoln-icon_share-thin']").click();
	}

	@Then("^See all components on Share overlay on P1 INT Lincoln$")
	public void seeAllComponentsOnShareOverlayInInt() throws Throwable {
		System.out.println("See all components on Share overlay");
		// getVisibleElementByXpath("//a[@class='lincoln-icon_weibo']");
		// getVisibleElementByXpath("//a[@class='lincoln-icon_tencent-weibo']");
		// getVisibleElementByXpath("//a[@class='lincoln-icon_tencent-weibo blue']");
		// getVisibleElementByXpath("//a[@class='lincoln-icon_douban']");
		// getVisibleElementByXpath("//a[@class='lincoln-icon_wechat']");
		getVisibleElementByXpath("//input[@class='form-control style-border-orange']");
		getVisibleElementByXpath("//a[contains(text(),'关闭')]");
	}

	@Then("^See all components on Share overlay on Press Release page on P1 INT Lincoln$")
	public void seeAllComponentsOnShareOVerlayOnPressReleasePage() throws Throwable {
		System.out.println("See all components on Share overlay on Press Release page");
		getVisibleElementByXpath("//a[@class='lincoln-icon_wechat green']");
		getVisibleElementByXpath("//a[@class='lincoln-icon_weibo pink']");
		getVisibleElementByXpath("//a[@class='lincoln-icon_douban green']");
		getVisibleElementByXpath("//a[@class='lincoln-icon_tencent-weibo blue']");
		getVisibleElementByXpath("//input[@class='form-control style-border-orange']");
		getVisibleElementByXpath("//a[contains(text(),'关闭')]");
	}

	@When("^Click on Close button on Share overlay on P1 Lincoln$")
	public void clickOnCloseButtonOnShareOverlay() throws Throwable {
		System.out.println("Click on Close button on Share overlay");
		getVisibleElementByXpath("//a[contains(text(),'关闭')]").click();
	}

	@When("^Click on Go back button on Press Release page on P1 Lincoln$")
	public void clickOnGoBackButtonOnPressReleasePage() throws Throwable {
		System.out.println("Click on Go back button on Press Release page");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'返回上层')]").click();
	}

	@When("^Click on Close on Share overlay on P1 INT Lincoln$")
	public void clickOnCloseOnShareOverlayInInt() throws Throwable {
		System.out.println("Click on Close on Share overlay");
		getVisibleElementByXpath("//a[contains(text(),'关闭')]").click();
		Thread.sleep(3000);
	}

	@When("^Click on Share button on exterior image on P1 PERF Lincoln$")
	public void clickOnShareButtonOnExteriorImage() throws Throwable {
		System.out.println("Click on Share button on exterior image");
		getVisibleElementByXpath("//span[@class='lincoln-icon_share-thin']").click();
	}

	@When("^Click on Close on exterior image on P1 INT Lincoln$")
	public void clickOnCloseOnExteriorImageInInt() throws Throwable {
		System.out.println("Click on Close on exterior image");
		// getVisibleElementByXpath("//div[4]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/span[@class='modal-close
		// style-small hidden-xs']").click();
		getVisibleElementByXpath("//div[@data-modal-type='exterior']//span[@class='modal-close style-small hidden-xs']").click();
	}

	@When("^Click on interior image on MKZ Gallery on P1 INT Lincoln$")
	public void clickOnInteriorImageOnMkzGalleryInInt() throws Throwable {
		System.out.println("Click on interior image on MKZ Gallery");
		// getVisibleElementByXpath("//div[4]/div[3]/div[1]/div[1]/section/div[2]/div[1]/div/div[1]/div[1]/div/div/a/figure/img[@alt='image
		// 1']").click();
		getVisibleElementByXpath("//a[@data-target='.interior']//img");
		driver.findElements(By.xpath("//a[@data-target='.interior']//img")).get(0).click();
	}

	@Then("^See all components on interior image on P1 INT Lincoln$")
	public void seeAllComponentsOnInteriorImageInInt() throws Throwable {
		System.out.println("See all components on interior image");
		getVisibleElementByXpath("//div[contains(@class,'interior')]//img[@alt='image 1']");
		getVisibleElementByXpath("//div[contains(@class,'interior')]//span[@class='lincoln-icon_share-thin']");
		getVisibleElementByXpath("//div[contains(@class,'interior')]//a[@class='btn-icon btn-share last']");
		getVisibleElementByXpath("//div[contains(@class,'interior')]//span[@class='modal-close style-small hidden-xs']");
	}

	@When("^Click on Share button on interior image on P1 INT Lincoln$")
	public void clickOnShareButtonOnInteriorImageInInt() throws Throwable {
		System.out.println("Click on Share button on interior image");
		getVisibleElementByXpath("//span[@class='lincoln-icon_share-thin']").click();
	}

	@When("^Click on Close on interior image on P1 INT Lincoln$")
	public void clickOnCloseOnInteriorImageInInt() throws Throwable {
		System.out.println("Click on Close on Interior image");
		getVisibleElementByXpath("//div[4]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/span[@class='modal-close style-small hidden-xs']").click();
	}

	@Then("^See all components on Share overlay on P1 Lincoln$")
	public void seeAllComponentsOnShareOverlay() throws Throwable {
		System.out.println("See all components on Share overlay");
		getVisibleElementByXpath("//a[@class='lincoln-icon_wechat green']");
		getVisibleElementByXpath("//a[@class='lincoln-icon_weibo pink']");
		getVisibleElementByXpath("//a[@class='lincoln-icon_douban green']");
		getVisibleElementByXpath("//a[@class='lincoln-icon_tencent-weibo blue']");
		getVisibleElementByXpath("//input[@class='form-control style-border-orange']");
		getVisibleElementByXpath("//a[contains(text(),' 关闭')]");
	}

	@When("^Click on Download button on exterior image on P1 DEV Lincoln$")
	public void clickOnShareButtonOnExteriorImageInDev() throws Throwable {
		System.out.println("Click on Download button on exterior image");
		getVisibleElementByXpath("//div[4]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/div/div/div/a[1]/span[@class='icon lincoln-icon_download-thin']").click();
	}

	@When("^Input position to Mini LAD and Search on P1 Lincoln$")
	public void inputPositionAndSearch(DataTable position) throws Throwable {
		System.out.println("Input position to Mini LAD and Search on Lincoln");
		// Write the code to handle Data Table
		List<List<String>> data = position.raw();
		getVisibleElementByXpath("//*[@id='state' or contains(@id,'select2-')]");
//		List<WebElement> SelLocation = driver.findElements(By.xpath("//*[@id='state' or contains(@id,'select2-')]"));
//
//		SelLocation.get(0).click();
//		getVisibleElementByXpath("//li[contains(text(),'" + data.get(0).get(0) + "')]").click();
//		Thread.sleep(2000);
//
//		getVisibleElementByXpath("//*[@id='select2-second-select-option-container']").click();
//		getVisibleElementByXpath("//li[contains(text(),'" + data.get(0).get(1) + "')]").click();

		Select oLst= new Select(getVisibleElementByXpath("//select[contains(@data-ng-model,'mainCtrl.selectedProvince')]"));
		oLst.selectByVisibleText(data.get(0).get(0));
		Thread.sleep(4000);
		oLst= new Select(getVisibleElementByXpath("//select[contains(@data-ng-model,'mainCtrl.selectedCity')]"));
		oLst.selectByVisibleText(data.get(0).get(1));
		
		Thread.sleep(4000);
		getVisibleElementByXpath("//button[@type='submit' and @class='btn btn-large btn-solid bg-gold']").click();
	}

	
	
	@When("^Input position to Main LAD and Search on P1 Lincoln$")
	public void inputPositionAndSearchInMainLad(DataTable position) throws Throwable {
		System.out.println("Input position to Mini LAD and Search on Lincoln");
		// Write the code to handle Data Table
		List<List<String>> data = position.raw();
		getVisibleElementByXpath("//input[@id='state']").clear();
		getVisibleElementByXpath("//input[@id='state']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//div[3]/div[3]/div[1]/div/section/div[1]/div[3]/div/form/div/div[3]/a").click();
	}

	@And("^Click on search icon on P1 Lincoln$")
	public void clickOnSearchIcon() throws Throwable {
		System.out.println("Click on search icon");
		getVisibleElementByXpath("//i[@class='lincoln-icon_search']");
	}

	@When("^Click on LAD Expander button on P1 Lincoln$")
	public void clickLADExpander() throws Throwable {
		System.out.println("Click on LAD Expander button on Lincoln");
		getVisibleElementByXpath("//i[@class='lincoln-icon_add']").click();
	}

	@Then("^Verify Delear info overlay on page$")
	public void Verify_Delear_info_overlay_on_page() throws Throwable {
		System.out.println("Verify Delear info overlay on page");
		Thread.sleep(30000);
		waitTillElemVisiblebyXpath("//div[contains(text(),'经销商查询')]", 240);
		getVisibleElementByXpath("//div[contains(text(),'经销商查询')]");
	}

	@Then("^Verify Delear details on Map$")
	public void Verify_Delear_details_on_Map() throws Throwable {
		System.out.println("Verify Delear details on Map");
		Thread.sleep(30000);
		waitTillElemVisiblebyXpath("//div[@class='result-container']", 240);
		getVisibleElementByXpath("//div[@class='result-container']");
		getVisibleElementByXpath("//a[@class='amap-info-close']").click();
		
	}

	@When("^See the first result on Map on P1 Lincoln$")
	public void seeTheFirstResultOnMap(DataTable foundPosition) throws Throwable {
		System.out.println("See the first result on Map on P1 Lincoln");
		// Write the code to handle Data Table
		List<List<String>> data = foundPosition.raw();
		getVisibleElementByXpath("//*[text()='" + data.get(0).get(0) + "']");
		// getVisibleElementByXpath("//div[@id='map']//span[text()='"+data.get(0).get(0)+"']");
	}

	@When("^Click on LAD Close button on P1 Lincoln$")
	public void clickLADClose() throws Throwable {
		System.out.println("Click on LAD Close button on Lincoln");
		getVisibleElementByXpath("//i[@class='lincoln-icon_add']").click();
	}

	@When("^Click on Dealer details link on P1 Lincoln$")
	public void clickOnDealerDetailsLink() throws Throwable {
		System.out.println("Click on Dealer details link");
		getVisibleElementByXpath("//div[1]/div[1]/div[3]/div[3]//span[@class='lincoln-icon_detail-information']").click();
	}

	@Then("^See Dealer details on P1 Lincoln$")
	public void seeDealerDetails() throws Throwable {
		System.out.println("See Dealer details on Lincoln");
		getVisibleElementByXpath("//p[@class='bigger' and text()='Opening Hours']");
		getVisibleElementByXpath("//td[text()='Monday']");
		getVisibleElementByXpath("//td[text()='Tuesday']");
		getVisibleElementByXpath("//td[text()='Wednesday']");
		getVisibleElementByXpath("//td[text()='Thursday']");
		getVisibleElementByXpath("//td[text()='Friday']");
		getVisibleElementByXpath("//td[text()='Saturday']");
		getVisibleElementByXpath("//td[text()='Sunday']");
		getVisibleElementByXpath("//p[@class='bigger' and contains(text(),'Service')]");
	}

	@When("^Click on Dealer information link on P1 Lincoln$")
	public void clickOnDealerInformationLink() throws Throwable {
		System.out.println("Click on Dealer information link");
		// getVisibleElementByXpath("html/body/div[3]/div[3]/div[3]/section/div[2]/div[1]/div/div/div[3]/div/div/div[2]/a").click();//div[contains(text(),'经销商查询')]
		getVisibleElementByXpath("//a[contains(text(),'经销商信息')]").click();
	}

	@When("^Click on Locate Dealer link on P1 Lincoln$")
	public void clickLocateDealerLink() throws Throwable {
		System.out.println("Click on Locate Dealer link on Lincoln");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(), 'Locate Dealer')]").click();
	}

	@Then("^See page redirected to correct link \"(.*?)\" on P1 Lincoln$")
	public void seeThePageRedirectedToCorrectLink(String link) throws Throwable {
		System.out.println("See page redirected to correct link");
		verifyRedirecToCorrectLink(link);
		Thread.sleep(5000);
	}

	@When("^Click on Immediate Appointment link of Request a Test Drive on P1 Lincoln$")
	public void clickOnRequestTestDriveLink() throws Throwable {
		System.out.println("Click on Request a Test Drive link");
		getVisibleElementByXpath("//a[text()='Immediate Appointment']").click();
	}

	@When("^Click on Request A Test Drive at the footer on P1 Lincoln$")
	public void clickOnRequestTestDriveAtTheFooter() throws Throwable {
		System.out.println("Click on Request A Test Drive at the footer");
		getVisibleElementByXpath("//a[text()='Request A Test Drive']").click();
	}

	@When("^Click on Request a Quote at the footer on P1 Lincoln$")
	public void clickOnRequestQuoteAtTheFooter() throws Throwable {
		System.out.println("Click on Request a Quote at the footer");
		getVisibleElementByXpath("//a[text()='获取经销商报价']").click();
		Thread.sleep(3000);
	}

	@When("^Click on Request a brochure at the footer on P1 Lincoln$")
	public void clickOnRequestBrochureAtTheFooter() throws Throwable {
		System.out.println("Click on Request a brochure at the footer");
		getVisibleElementByXpath("//div[@id='footer2']//a[text()='获取车型手册']").click();
	}

	@When("^Click on Keep me informed at the footer on P1 Lincoln$")
	public void clickOnKeepMeInformedAtTheFooter() throws Throwable {
		System.out.println("Click on Keep me informed at the footer");
		getVisibleElementByXpath("//div[@id='footer2']//a[text()='获取最新资讯']").click();
	}

	@Then("^See all components on Keep me informed overlay on P1 Lincoln$")
	public void seeAllComponentsOnKeepMeInformedOverlay(DataTable parameter) throws Throwable {
		System.out.println("See all components on Keep me informed overlay");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[text()='林肯动向']");
		getVisibleElementByXpath("//div[@class='hidden-xs']//strong[text()='个人联系信息']");
		getVisibleElementByXpath("//div[@class='hidden-xs']//strong[text()='座驾信息']");

		for (int i = 0; i <= 4; i++) {
			getVisibleElementByXpath("//span[contains(text(),'" + data.get(0).get(i) + "')]");
		}

		for (int j = 5; j <= 9; j++) {
			getVisibleElementByXpath("//input[@name='" + data.get(0).get(j) + "' or @type='" + data.get(0).get(j) + "']");
		}

		getVisibleElementByXpath("//span[@class='checkbox-style gux-icon-check-25px']");

		for (int k = 10; k <= 15; k++) {
			getVisibleElementByXpath("//label[contains(text(),'" + data.get(0).get(k) + "')]");
		}

		getVisibleElementByXpath("//button[contains(text(),'确认提交')]");
	}

	@When("^Fill in Keep me informed on P1 Lincoln$")
	public void fillInKeepMeInformed(DataTable parameter) throws Throwable {
		System.out.println("Fill in Keep me informed");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='email' or @type='email']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='mobile']").sendKeys(data.get(0).get(3));
		// getVisibleElementByXpath("//div[4]/div[5]/div/div/div/div/section/div/div/div/div[1]/form/div[2]/div/div/div[2]/div/div[1]/label/span[contains(@class,'checkbox-style')]").click();
		// getVisibleElementByXpath("//div[4]/div[5]/div/div/div/div/section/div/div/div/div[1]/form/div[2]/div/div/div[3]/div/div[1]/div[1]/label/span[contains(@class,'checkbox-style')]").click();
		getVisibleElementByXpath("//input[@name='captchaValue']").sendKeys(EnterCaptcha());
		// getVisibleElementByXpath("//div[4]/div[5]/div/div/div/div/section/div/div/div/div[1]/form/div[2]/div/div/div[5]/div/div/label/span[contains(@class,'checkbox-style')]").click();
		getVisibleElementByXpath("//span[@class='checkbox-style gux-icon-check-25px']");
		List<WebElement> Chkbox = driver.findElements(By.xpath("//span[@class='checkbox-style gux-icon-check-25px']"));
		for (WebElement chkbox : Chkbox) {
			chkbox.click();
			Thread.sleep(1000);
		}
		getVisibleElementByXpath("//button[contains(text(),'确认提交') and contains(@class,'full')]").click(); // changes propery 14/8 during perf testing - old //button[contains(text(),'
																											// 确认提交')]
	}

	@Then("^See all components on Request a brochure overlay on P1 Lincoln$")
	 public void seeAllComponentsOnRequestBrochureOverlay(DataTable parameter) throws Throwable {
	  System.out.println("See all components on Request a brochure overlay");
	  List<List<String>> data = parameter.raw();
	  getVisibleElementByXpath("//strong[contains(text(),'索取车型手册')]");
	  for (int i = 0; i <= 0; i++) {
	   getVisibleElementByXpath("//div[contains(@class,'owl-item active')]//img[contains(@alt,'" + data.get(0).get(i) + "')]");////div[@class='owl-item active' or @class='owl-item current-item active']//img[@alt='" + data.get(0).get(i) + "']
	   getVisibleElementByXpath("//div[contains(@class,'owl-item active')]//figcaption[contains(text(),'" + data.get(0).get(i) + "')]");
	  }
	  getVisibleElementByXpath("//a[@id='dowloadButton' and contains(text(),'下载电子手册')]");
	  getVisibleElementByXpath("//a[@id='mailForm' and contains(text(),'获取实体手册')]");
	 }

	@When("^Select a vehicle from Vehicle list on Request a brochure overlay on P1 Lincoln$")
	public void selectVehicleFromVehicleListOnRequestBrochureOverlay(DataTable parameter) throws Throwable {
		System.out.println("Select a vehicle from Vehicle list on Request a brochure overlay");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//div[contains(@class,'owl-item active')]//img[contains(@alt,'" + data.get(0).get(0) + "')]").click();
	}

	@When("^Click on download button on Request a brochure overlay on P1 Lincoln$")
	public void clickOnDownloadButtonOnRequestBrochureOverlay() throws Throwable {
		System.out.println("Click on download button on Request a brochure overlay");
		getVisibleElementByXpath("//a[@id='dowloadButton' and contains(text(),'下载电子手册')]").click();
		Thread.sleep(5000);
	}

	@When("^See brochure downloaded successfully$")
	public void See_brochure_downloaded_successfully(DataTable param) throws Throwable {
		System.out.println("See brochure downloaded successfully");
		Thread.sleep(120000);
		List<List<String>> data = param.raw();
		String Brochurepath = config.Configuration.PATH_TO_DOWNLOAD_BROCHURE + data.get(0).get(0) + "_Brochure.pdf";
		File file = new File(Brochurepath);
		int i=0;
		do {
			Thread.sleep(5000);
			i++;
		}while(!file.exists() && i<=72);
		
		if (file.exists()) {
			System.out.println("Brochure downloaded successfully: " + Brochurepath);
			file.delete();
		} else {
			Assert.assertFalse("Brochure Download Failed", true);
		}

	}

	@When("^Click on mailto button on Request a brochure overlay on P1 Lincoln$")
	public void clickOnMailtoButtonOnRequestBrochureOverlay() throws Throwable {
		System.out.println("Click on mailto button on Request a brochure overlay");
		getVisibleElementByXpath("//a[@id='mailForm' and contains(text(),'获取实体手册')]").click();
	}

	@Then("^See all components on Request a brochure mailto on P1 Lincoln$")
	public void seeAllComponentsOnRequestBrochureMailto(DataTable parameter) throws Throwable {
		System.out.println("See all components on Request a brochure mailto");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//div[@class='hidden-xs']//strong[text()='个人联系信息']");
		getVisibleElementByXpath("//div[@class='hidden-xs']//strong[text()='座驾信息']");

		for (int i = 0; i <= 11; i++) {
			getVisibleElementByXpath("//span[@class='title' and contains(text(),'" + data.get(0).get(i) + "')]");
		}

		for (int j = 12; j <= 14; j++) {
			getVisibleElementByXpath("//select[@name='" + data.get(0).get(j) + "']");
		}

		for (int k = 16; k <= 21; k++) {
			getVisibleElementByXpath("//input[@name='" + data.get(0).get(k) + "' or @type='" + data.get(0).get(k) + "']");
		}

		getVisibleElementByXpath("//span[@class='checkbox-style gux-icon-check-25px']");
		getVisibleElementByXpath("//label[contains(text(),'请输入验证码')]");
		getVisibleElementByXpath("//button[contains(text(),' 确认提交')]");
	}

	@When("^Fill in Request a brochure on P1 Lincoln$")
	public void fillInRequestBrochure(DataTable parameter) throws Throwable {
		System.out.println("Fill in Request a brochure on P1 Lincoln");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//select[@name='title']").click();
		getVisibleElementByXpath("//option[text()='" + data.get(0).get(0) + "']").click();
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='email' or @type='email']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='mobile']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//select[@name='state']").click();
		getVisibleElementByXpath("//option[contains(text(),'" + data.get(0).get(5) + "')]").click();
		getVisibleElementByXpath("//select[@name='city']").click();
		getVisibleElementByXpath("//select[@name='city']/option[text()='" + data.get(0).get(6) + "']").click();
		getVisibleElementByXpath("//input[@name='addressLine1']").sendKeys(data.get(0).get(7));
		getVisibleElementByXpath("//input[@name='postCode']").sendKeys(data.get(0).get(8));
		getVisibleElementByXpath("//select[@name='nameplate']").click();
		getVisibleElementByXpath("//option[contains(text(),'" + data.get(0).get(9) + "')]").click();
		getVisibleElementByXpath("//option[text()='3个月内']/ancestor::select").click();// select[@name='mktPurchaseTime']").click();
		getVisibleElementByXpath("//option[text()='" + data.get(0).get(10) + "']").click();
		getVisibleElementByXpath("//input[@name='captchaValue']").sendKeys(EnterCaptcha());
		// getVisibleElementByXpath("//div[4]/div[5]/div/div/div/div/section/div[2]/div/div/div[1]/form/div[2]/div/div/div[4]/div/div[1]/div[1]/label/span[2]").click();
		// getVisibleElementByXpath("//div[4]/div[5]/div/div/div/div/section/div[2]/div/div/div[1]/form/div[2]/div/div/div[6]/div/div/label/span[2]").click();

		List<WebElement> Chkbox = driver.findElements(By.xpath("//span[@class='checkbox-style gux-icon-check-25px']"));
		Chkbox.get(0).click();
		Thread.sleep(2000);
		Chkbox.get(2).click();
		getVisibleElementByXpath("//button[contains(text(),'确认提交') and contains(@class,'full')]").click(); // changes propery 14/8 during perf testing - old //button[contains(text(),'
																											// 确认提交')]
	}

	@When("^Fill in Request a Test Drive form on P1 Lincoln$")
	public void fillInRequestTestDriveForm(DataTable requestTestDriveCredentials) throws Throwable {
		System.out.println("Fill in Request a Test Drive form");
		// Write the code to handle Data Table
		List<List<String>> data = requestTestDriveCredentials.raw();

		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='email']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='mobile']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//select[@name='preferredDealer']").click();
		getVisibleElementByXpath("//option[contains(text(),'" + data.get(0).get(4) + "')]").click();
		getVisibleElementByXpath("//select[@name='model']").click();
		getVisibleElementByXpath("//option[contains(text(),'" + data.get(0).get(5) + "')]").click();
		getVisibleElementByXpath("//input[@id='captchaValue']").sendKeys(data.get(0).get(6));
		getVisibleElementByXpath("html/body/div[3]/div[5]/div/div/div/div/section/div/div/div/div[1]/form/div[2]/div/div/div[3]/div/div/label/span[2]").click();
		getVisibleElementByXpath("//button[contains(text(),'Submit')]").click();
	}

	@When("^Click on Go Immediately link of Request a Quote on P1 Lincoln$")
	public void clickOnRequestQuoteLink() throws Throwable {
		System.out.println("Click on Request a Quote link");
		getVisibleElementByXpath("//a[@href='/content/lincoln-uat-demo-en/others/en_sk/home/forms/request-a-quote.html' and text()='Go Immediately']").click();
	}

	@And("^Fill in Request a Quote form on P1 Lincoln$")
	public void fillInRequestQuoteForm(DataTable requestQuoteCredentials) throws Throwable {
		System.out.println("Fill in Request a Quote form");
		// Write the code to handle Data Table
		List<List<String>> data = requestQuoteCredentials.raw();
		getVisibleElementByXpath("//select[@name='title']").click();
		getVisibleElementByXpath("//option[contains(text(),'" + data.get(0).get(0) + "')]").click();
		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='email' or @type='email']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='mobile']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//select[@name='state']").click();
		getVisibleElementByXpath("//select[@name='state']/option[contains(text(),'" + data.get(0).get(5) + "')]").click();
		getVisibleElementByXpath("//select[@name='city']").click();
		getVisibleElementByXpath("//select[@name='city']/option[contains(text(),'" + data.get(0).get(6) + "')]").click();
		getVisibleElementByXpath("//select[@name='preferredDealer']").click();
		getVisibleElementByXpath("//option[contains(text(),'" + data.get(0).get(7) + "')]").click();
		getVisibleElementByXpath("//select[@name='nameplate']").click();
		getVisibleElementByXpath("//option[contains(text(),'" + data.get(0).get(8) + "')]").click();
		getVisibleElementByXpath("//option[text()='3个月内']/ancestor::select").click();// select[@name='mktPurchaseTime']").click();
		getVisibleElementByXpath("//option[contains(text(),'" + data.get(0).get(9) + "')]").click();
		getVisibleElementByXpath("//input[@id='captchaValue']").sendKeys(EnterCaptcha());
		getVisibleElementByXpath("//span[@class='checkbox-style gux-icon-check-25px']").click();
		getVisibleElementByXpath("//button[contains(text(),'确认提交') and contains(@class,'full')]").click();// changed propery 14/8 during perf testing - old //button[contains(text(),'
																											// 确认提交')]
	}

	@Then("^See all components on Homepage on P1 Lincoln$")
	public void seeHomePageComponents() throws Throwable {
		System.out.println("See all components on Homepage on Lincoln");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li/a/span[contains(text(),'销售')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li/a/span[contains(text(),'车型手册')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li/a/span[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li/a/span[contains(text(),'登录')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li/a/span[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li/a/span[contains(text(),'联系我们')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li/a//span/span[contains(text(),'中文')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li/a/span[contains(text(),'EN')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li/a/span[@class='lincoln-icon_search']");

		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']/li/a[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']/li/a[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']/li/a[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']/li/a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']/li/a[contains(text(),'预约试驾')]");

		getVisibleElementByXpath("//h3[text()='搜索经销商']");
		getVisibleElementByXpath("//p[text()='*你的位置']");
		getVisibleElementByXpath("//span[contains(text(),'选择省*')]");
		getVisibleElementByXpath("//span[contains(text(),'选择城市*')]");
		getVisibleElementByXpath("//button[contains(text(),'搜索')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKZ')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯CONTINENTAL')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKC')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKX')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯NAVIGATOR')]");
		getVisibleElementByXpath("//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//a[contains(text(),'车型对比')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯金融')]");
		getVisibleElementByXpath("//a[contains(text(),'获取经销商报价')]");
		getVisibleElementByXpath("//a[contains(text(),'索取车型手册')]");
		getVisibleElementByXpath("//a[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯之道')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯时刻')]");
		getVisibleElementByXpath("//a[contains(text(),'百年荣耀')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯之道·云服务')]");
		getVisibleElementByXpath("//a[contains(text(),'品牌动向')]");
		getVisibleElementByXpath("//a[contains(text(),'新闻资讯')]");
		getVisibleElementByXpath("//a[contains(text(),'成为经销商')]");
		getVisibleElementByXpath("//a[contains(text(),'创建我的林肯ID')]");
		getVisibleElementByXpath("//a[contains(text(),'SYNC')]");
		getVisibleElementByXpath("//a[contains(text(),'	销售')]");

		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/icon-weibo.jpg']");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/icon-email.jpg']");
		getVisibleElementByXpath("//a[contains(text(),'隐私政策')]");
		getVisibleElementByXpath("//a[contains(text(),'服务条款与条件')]");
		getVisibleElementByXpath("//a[contains(text(),'免责声明')]");
	}

	@When("^Click on Vehicle Selector link on P1 Lincoln$")
	public void clickOnSalesLink() throws Throwable {
		System.out.println("Click on Vehicle Selector link");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li/a/span[contains(text(),'销售')]").click();
	}

	@Then("^See page redirected to link contains \"(.*?)\" on P1 Lincoln$")
	public void seeThePageRedirectedToLinkContains(String link) throws Throwable {
		System.out.println("See page redirected to link contains");

		Thread.sleep(30000);

		String url = driver.getCurrentUrl();
		System.out.println("xxxxxxxxxxxxxxxx" + url);
		if (!url.contains(link)) {
			throw new Exception("Redirect to incorrect link");
		}
	}

	@When("^Click on Lincoln Logo on P1 Lincoln$")
	public void clickOnLincolnLogo() throws Throwable {
		System.out.println("Click on Lincoln Logo");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/logo.png']").click();
	}

	@When("^Click on Model link on P1 Lincoln$")
	public void clickOnModelLink() throws Throwable {
		System.out.println("Click on Model link");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li/a/span[contains(text(),'车型手册')]").click();
	}

	@Then("^See Model overlay on P1 Lincoln$")
	public void seeModelOverlay() throws Throwable {
		System.out.println("See Model overlay on P1 Lincoln");
		getVisibleElementByXpath("//span[text()='索取车型手册']");
		getVisibleElementByXpath("//span[contains(text(),'400-988-6789咨询')]");
		getVisibleElementByXpath("html/body/div[3]/div[5]/div/div/div/div/div/div[3]/div/div[1]/div[1]/div/div[5]/div/figcaption");
		getVisibleElementByXpath("html/body/div[3]/div[5]/div/div/div/div/div/div[3]/div/div[1]/div[1]/div/div[6]/div/figcaption");
		getVisibleElementByXpath("html/body/div[3]/div[5]/div/div/div/div/div/div[3]/div/div[1]/div[1]/div/div[7]/div/figcaption");
		getVisibleElementByXpath("html/body/div[3]/div[5]/div/div/div/div/div/div[3]/div/div[1]/div[1]/div/div[8]/div/figcaption");
		getVisibleElementByXpath("html/body/div[3]/div[5]/div/div/div/div/div/div[3]/div/div[1]/div[2]/div[1]/span");
		getVisibleElementByXpath("html/body/div[3]/div[5]/div/div/div/div/div/div[3]/div/div[1]/div[2]/div[2]/span");
		getVisibleElementByXpath("//*[@id='dowloadButton']");
		getVisibleElementByXpath("//*[@id='mailForm']");
	}

	@When("^Click on Close icon of Model overlay on P1 Lincoln$")
	public void clickOnCloseIconOfModelOverlay() throws Throwable {
		System.out.println("Click on Close icon of Model overlay");
		getVisibleElementByXpath("html/body/div[3]/div[5]/div/div/div/div/span/i").click();
	}

	@And("^Click on Get the latest information link on P1 Lincoln$")
	public void clickOnGetTheLatestInformationLink() throws Throwable {
		System.out.println("Click on Get the latest information link");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li/a/span[contains(text(),'获取最新资讯')]").click();
	}

	@Then("^See the latest information overlay on P1 Lincoln$")
	public void seeTheLatestInformationOverlay() throws Throwable {
		System.out.println("See the latest information overlay");
		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[text()='林肯动向']");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[text()='进一步了解林肯的最新动向和未来活动信息？请留下您的资料，一切轻松掌握。（＊号为必填项目）']");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p/strong[text()='个人联系信息']");
		getVisibleElementByXpath("//span[@class='title' and contains(text(),'姓名')]");
		getVisibleElementByXpath("//input[@name='firstName']");
		getVisibleElementByXpath("//input[@name='lastName']");
		getVisibleElementByXpath("//span[@class='title' and contains(text(),'电子邮箱 ')]");
		getVisibleElementByXpath("//input[@name='email']");
		getVisibleElementByXpath("//span[@class='title' and contains(text(),'手机号码')]");
		getVisibleElementByXpath("//input[@name='mobile']");

		getVisibleElementByXpath("//div[@class='hidden-xs']/p/strong[text()='座驾信息']");
		getVisibleElementByXpath("//span[@class='title' and text()='我想了解以下CONTINENTAL车型的更多信息']");
		getVisibleElementByXpath("html/body/div[3]/div[5]/div/div/div/div/section/div/div/div/div[1]/form/div[2]/div/div/div[2]/div/div[1]/label/span");
		getVisibleElementByXpath("//label[@class='control-input component-checkbox' and contains(text(),'林肯MKZ')]");
		getVisibleElementByXpath("html/body/div[3]/div[5]/div/div/div/div/section/div/div/div/div[1]/form/div[2]/div/div/div[2]/div/div[2]/label/span");
		getVisibleElementByXpath("//label[@class='control-input component-checkbox' and contains(text(),'林肯MKS')]");
		getVisibleElementByXpath("//label[contains(text(),'请输入验证码')]");
		getVisibleElementByXpath("//div[@class='recaptcha-txt1']");
		getVisibleElementByXpath("//div[@class='recaptcha-txt2']");
		getVisibleElementByXpath("//*[@id='captchaValue']");
	}

	@When("^Click on Close icon of Information overlay on P1 Lincoln$")
	public void clickOnCloseIConOfInformationOverlay() throws Throwable {
		System.out.println("Click on Close icon of Information overlay");
		getVisibleElementByXpath("html/body/div[3]/div[5]/div/div/div/div/span/i").click();
	}

	@Then("^See all components on Overview on P1 Lincoln$")
	public void seeAllComponentsOnOverview() throws Throwable {
		System.out.println("See all components on Overview");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='Locate Dealer']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		// getVisibleElementByXpath("//ul[@class='login-bar
		// navbar-right']//span[text()='EN']");
		// getVisibleElementByXpath("//ul[@class='login-bar
		// navbar-right']//span[text()='AR']");
		// getVisibleElementByXpath("//ul[@class='login-bar
		// navbar-right']//span[text()='Home']");
		// getVisibleElementByXpath("//ul[@class='login-bar
		// navbar-right']//span[text()='Vehicles']");
		// getVisibleElementByXpath("//ul[@class='login-bar
		// navbar-right']//span[text()='Sales']");
		// getVisibleElementByXpath("//div[3]/div[1]/nav/div/ul/li[4]//span[text()='Newsroom']");
		// getVisibleElementByXpath("//div[3]/div[1]/nav/div/ul/li[5]//span[text()='Credit']");
		// getVisibleElementByXpath("//div[3]/div[1]/nav/div/ul/li[6]//span[text()='Owners']");
		// getVisibleElementByXpath("//a[contains(text(),'Lincoln MKC')]");
		// getVisibleElementByXpath("//a[contains(text(),'Dealer locator')]");
		// getVisibleElementByXpath("//ul[@class='secondary-side-navi
		// pull-right']/li/a[contains(text(),'Request a Brochure')]");
		// getVisibleElementByXpath("//ul[@class='secondary-side-navi
		// pull-right']/li/a[contains(text(),'Request a Quote')]");
		// getVisibleElementByXpath("html/body/div[3]/div[3]/div[1]/section/div/div[1]/div/div[4]/div/div[2]/div/div/p/a");
		// //Explore MKC button
		// getVisibleElementByXpath("html/body/div[3]/div[3]/div[4]/section/div/div[1]/div/div[3]/div/div[2]/div[2]/div/p[3]/a");
		// //Learn More button
		// getVisibleElementByXpath("//h2[contains(text(), 'Colorizer & 360°')]");
		// getVisibleElementByXpath("//a[text()='Colorizer']");
		// getVisibleElementByXpath("//a[text()='360° Exterior']");
		// getVisibleElementByXpath("//h2[text()='Find Your Nearest Dealer']");
		// getVisibleElementByXpath("//a[contains(text(),'Use My Location')]");
		// getVisibleElementByXpath("//*[@id='state']");
		// getVisibleElementByXpath("//a[text()='Use advanced search']");
		// getVisibleElementByXpath("//span[text()='Parts and Services']");
		// getVisibleElementByXpath("//h4/span[text()='Request a Test Drive']");
		// getVisibleElementByXpath("//a[text()='Immediate Appointment']");
		// getVisibleElementByXpath("//a[@href='/content/lincoln-uat-demo-en/others/en_sk/home/forms/request-a-quote.html'
		// and text()='Go Immediately']");
		// getVisibleElementByXpath("//h3[text()='MKC Models']");
		// getVisibleElementByXpath("html/body/div[3]/div[3]/div[10]/section/div[1]/div/div[2]/a");
		// //Model Compare button
		// getVisibleElementByXpath("//img[@alt='MKC Select']");
		// getVisibleElementByXpath("//h4[text()='MKC Select']");
		// getVisibleElementByXpath("//img[@alt='MKC Reserve']");
		// getVisibleElementByXpath("//h4[text()='MKC Reserve']");
		// getVisibleElementByXpath("//img[@alt='MKC Preferred']");
		// getVisibleElementByXpath("//h4[text()='MKC Preferred']");
		// getVisibleElementByXpath("html/body/div[3]/div[3]/div[10]/section/div[2]/div[2]/div[2]/span");
		// //lincoln-arrow-right icon
		// getVisibleElementByXpath("html/body/div[3]/div[3]/div[10]/section/div[2]/div[2]/div[1]/span");
		// //lincoln-arrow-lef icon
		// getVisibleElementByXpath("html/body/div[3]/div[3]/div[12]/div/div[1]/div/div/div/a");
		// //Share link
		// getVisibleElementByXpath("//div[@class='media-body
		// media-middle']/span[contains(text(),'Call at : 1600-6003')]");
		// getVisibleElementByXpath("//div[@class='media-body
		// media-middle']/span[contains(text(),'Call at : 080 - 300 - 3673')]");
		// getVisibleElementByXpath("//div[@class='media-body
		// media-middle']/span[contains(text(), 'info@lincoln.com')]");
	}

	@When("^Click on lincoln search icon on P1 Lincoln$")
	public void clickOnLicolnSearchIcon() throws Throwable {
		System.out.println("Click on lincoln search icon");
		getVisibleElementByXpath("//li[3]/a//span[@class='lincoln-icon_search']").click();
	}

	@Then("^See all components on Search overlay on P1 Lincoln$")
	public void seeAllComponentsOnSearchOverlay() throws Throwable {
		System.out.println("See all components on Search overlay");
		getVisibleElementByXpath("//p[text()='Search']");
		getVisibleElementByXpath("//input[@placeholder='Enter Keyword']");
		getVisibleElementByXpath("//button[@id='btn_search_overlay']");
	}

	@When("^Click Close button on Search overlay on P1 Lincoln$")
	public void clickCloseButtonOnSearchOverlay() throws Throwable {
		System.out.println("Click Close button on Search overlay");
		getVisibleElementByXpath("//div[5]/div/div/div[1]/div/div/div/div/span[contains(@class,'modal-close')]").click();
	}

	@When("^Click on Home on P1 Lincoln$")
	public void clickOnHome() throws Throwable {
		System.out.println("Click on Home");
		getVisibleElementByXpath("//nav//span[text()='Home']").click();
	}

	@When("^Click on Vehicle First Navigation on P1 Lincoln$")
	public void clickOnVehicleFirstNavigation(DataTable mainNav) throws Throwable {
		System.out.println("Click on Vehicle First Navigation");
		// Write the code to handle Data Table
		List<List<String>> data = mainNav.raw();
		for (int i = 1; i <= 5; i++) {
			String locator = null;
			String xpathForFirstPartNavigation = "//nav/div/ul/li[%d+1]";
			String xpathForSecondPartNavigation = "//span[text()='" + data.get(0).get(i - 1) + "']";
			VehicleFirstNavigation navigation = VehicleFirstNavigation.valueOf(data.get(0).get(i - 1).toLowerCase());
			switch (navigation) {
			case vehicles:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				break;

			case sales:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				break;

			case newsroom:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				break;

			case credit:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				break;

			case owners:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				break;

			default:
				throw new Exception("Invalid Suggestion name: " + navigation);
			}
		}
	}

	@And("^Click on down arrow icon on P1 Lincoln$")
	public void clickOnDownArrowIcon() throws Throwable {
		System.out.println("Click on down arrow icon");
		getVisibleElementByXpath("//nav/ul[1]/li/a/div[@class='icon lincoln-icon_arrow-down-thin']").click();
	}

	@Then("^See list of secondary navigation on P1 Lincoln$")
	public void listOfSecondaryNavigation() throws Throwable {
		System.out.println("See list of secondary navigation");
		getVisibleElementByXpath("//nav/ul[1]/li/ul/li[1]/a[contains(text(),'Overview')]");
		getVisibleElementByXpath("//nav/ul[1]/li/ul/li[2]/a[contains(text(),'Exterior')]");
		getVisibleElementByXpath("//nav/ul[1]/li/ul/li[3]/a[contains(text(),'Interior')]");
		getVisibleElementByXpath("//nav/ul[1]/li/ul/li[4]/a[contains(text(),'Technology')]");
		getVisibleElementByXpath("//nav/ul[1]/li/ul/li[5]/a[contains(text(),'Performance')]");
		getVisibleElementByXpath("//nav/ul[1]/li/ul/li[6]/a[contains(text(),'Gallery')]");
		getVisibleElementByXpath("//nav/ul[1]/li/ul/li[7]/a[contains(text(),'Compare')]");
	}

	@When("^Click on Vehicle Secondary Navigation on P1 Lincoln$")
	public void clickOnVehicleSecondaryNavigation(DataTable secondNav) throws Throwable {
		System.out.println("Click on Vehicle Secondary Navigation");
		// Write the code to handle Data Table
		List<List<String>> data = secondNav.raw();
		for (int i = 1; i <= 7; i++) {
			String locator = null;
			String xpathForFirstPartNavigation = "//nav/ul[1]/li/ul/li[%d]";
			String xpathForSecondPartNavigation = "/a[contains(text(),'" + data.get(0).get(i - 1) + "')]";
			VehicleSecondaryNavigation navigation = VehicleSecondaryNavigation.valueOf(data.get(0).get(i - 1).toLowerCase());
			switch (navigation) {
			case overview:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				break;

			case exterior:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				break;

			case interior:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				break;

			case technology:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				break;

			case performance:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				break;

			case gallery:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				break;

			case compare:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				break;

			default:
				throw new Exception("Invalid Suggestion name: " + navigation);
			}
			getVisibleElementByXpath(locator).click();
			Thread.sleep(10000);
			driver.get(getProfileURL(data.get(0).get(7)));
			getVisibleElementByXpath(data.get(0).get(8)).click();
		}
	}

	@When("^Click on component on secondary naviagation on P1 Lincoln$")
	public void clickOnComponentOnSecondaryNavigation(DataTable navigation) throws Throwable {
		// Write the code to handle Data Table
		List<List<String>> data = navigation.raw();
		System.out.println("Click on " + data.get(0).get(0) + " on secondary navigation");
		getVisibleElementByXpath("//nav/ul[1]/li/ul//a[contains(text(),'" + data.get(0).get(0) + "')]").click();
	}

	@When("^Click on More button on Exterior on P1 Lincoln$")
	public void clickOnMoreButtonOnExterior() throws Throwable {
		System.out.println("Click on More button on Exterior");
		getVisibleElementByXpath("//div[1]/section//a[contains(@class,'hidden-xs') and text()='More']").click();
	}

	@Then("^See more exterior image on P1 Lincoln$")
	public void seeMoreExteriorImage() throws Throwable {
		System.out.println("See more exterior image");
		getVisibleElementByXpath("//div[1]/section/div[2]/div[1]//img[@alt='image8']");
		getVisibleElementByXpath("//div[1]/section/div[2]/div[1]//img[@alt='image9']");
		getVisibleElementByXpath("//div[1]/section/div[2]/div[1]//img[@alt='image10']");
		getVisibleElementByXpath("//div[1]/section/div[2]/div[1]//img[@alt='image11']");
		getVisibleElementByXpath("//div[1]/section/div[2]/div[1]//img[@alt='image12']");
	}

	@When("^Click on exterior image on Gallery on P1 Lincoln$")
	public void clickOnExteriorImageOnGallery(DataTable image) throws Throwable {
		// Write the code to handle Data Table
		List<List<String>> data = image.raw();
		for (int i = 1; i <= 12; i++) {
			String locator;
			String locator1;
			String locator2;
			String xpathForFirstPartImage = "//div[1]/section/div[2]/div[1]/div/div[1]/div[1]/div[%d]";
			String xpathForSecondPartImage = "//img[@alt='" + data.get(0).get(i - 1) + "']";
			String xpathForFirstPartViewImage = "//div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[1]/div//div[%d+6]";
			String xpathForSecondPartViewImage = "//img[@alt='" + data.get(0).get(i - 1) + "']";

			VehicleImage vehicleImage = VehicleImage.valueOf(data.get(0).get(i - 1).toLowerCase());
			switch (vehicleImage) {

			case image1:

				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image2:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image3:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image4:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image5:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image6:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image7:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image8:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image9:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image10:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image11:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image12:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			default:
				throw new Exception("Invalid Suggestion name: " + vehicleImage);
			}
			System.out.println("Click on " + data.get(0).get(i - 1) + " exterior on Gallery");
			getVisibleElementByXpath(locator).click();
			Thread.sleep(5000);
			getVisibleElementByXpath(locator1);
			System.out.println("Verify " + data.get(0).get(i - 1) + " exterior open from image");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[2]/div[2]/span[@class='lincoln-icon_arrow-right']");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[2]/div[1]/span[@class='lincoln-icon_arrow-left']");
			getVisibleElementByXpath("//span[@class='icon lincoln-icon_download-thin']");
			getVisibleElementByXpath("//a[@class='btn-icon btn-share js-download-image']");
			getVisibleElementByXpath("//span[@class='lincoln-icon_share-thin']");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/div/div/div/a[2]");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/div/div/span[@class='gallery-item-count']");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/span[contains(@class,'modal-close')]");
			if (i == 1) {
				String xpathForPrevFirstPartViewImage = "//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[1]/div/div[6]";
				String xpathForPrevSecondPartViewImage = "//img[@alt='" + data.get(0).get(11) + "']";
				locator2 = xpathForPrevFirstPartViewImage.concat(xpathForPrevSecondPartViewImage);
			} else {
				int j = i + 6;
				String xpathForPrevFirstPartViewImage = "//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[1]/div/div[" + j + "]";
				String xpathForPrevSecondPartViewImage = "//img[@alt='" + data.get(0).get(i - 1) + "']";
				locator2 = xpathForPrevFirstPartViewImage.concat(xpathForPrevSecondPartViewImage);
			}
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[2]/div[1]/span[@class='lincoln-icon_arrow-left']").click();
			Thread.sleep(5000);
			getVisibleElementByXpath(locator2);
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/span[contains(@class,'modal-close')]").click();
			Thread.sleep(5000);
		}
	}

	@When("^Click on More button on Interior on P1 Lincoln$")
	public void clickOnMoreButtonOnInterior() throws Throwable {
		System.out.println("Click on More button on Interior");
		getVisibleElementByXpath("//div[2]/section//a[contains(@class,'hidden-xs') and text()='More']").click();
	}

	@Then("^See more interior image on P1 Lincoln$")
	public void seeMoreInteriorImage() throws Throwable {
		System.out.println("See more interior image");
		getVisibleElementByXpath("//div[2]/section/div[2]/div[1]//img[@alt='image8']");
	}

	@When("^Click on interior image on Gallery on P1 Lincoln$")
	public void clickOnInteriorImageOnGallery(DataTable image) throws Throwable {
		// Write the code to handle Data Table
		List<List<String>> data = image.raw();
		for (int i = 1; i <= 8; i++) {
			String locator;
			String locator1;
			String locator2;
			String xpathForFirstPartImage = "//div[1]/section/div[2]/div[1]/div/div[1]/div[%d]";
			String xpathForSecondPartImage = "//img[@alt='" + data.get(0).get(i - 1) + "']";
			String xpathForFirstPartViewImage = "//div[3]/div[3]/div[1]/div[2]/section/div[2]/div[2]/div[1]/div/section/section/div/div[1]/div/div[%d+4]";
			String xpathForSecondPartViewImage = "//img[@alt='" + data.get(0).get(i - 1) + "']";
			VehicleImage vehicleImage = VehicleImage.valueOf(data.get(0).get(i - 1).toLowerCase());
			switch (vehicleImage) {
			case image1:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image2:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image3:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image4:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image5:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image6:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image7:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			case image8:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				locator1 = String.format(xpathForFirstPartViewImage, i).concat(xpathForSecondPartViewImage);
				break;

			default:
				throw new Exception("Invalid Suggestion name: " + vehicleImage);
			}
			System.out.println("Click on " + data.get(0).get(i - 1) + " interior on Gallery");
			getVisibleElementByXpath(locator).click();
			Thread.sleep(5000);
			System.out.println("Verify " + data.get(0).get(i - 1) + " interior open from image");
			getVisibleElementByXpath(locator1);
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[2]/section/div[2]/div[2]/div[1]/div/span[contains(@class,'modal-close')]");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[2]/section/div[2]/div[2]/div[1]/div/section/section/div/div[2]/div[2]/span[@class='lincoln-icon_arrow-right']");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[2]/section/div[2]/div[2]/div[1]/div/section/section/div/div[2]/div[1]/span[@class='lincoln-icon_arrow-left']");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[2]/section/div[2]/div[2]/div[1]/div/section/div/div/div/a/span[@class='lincoln-icon_share-thin']");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[2]/section/div[2]/div[2]/div[1]/div/section/div/div/div/a");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[2]/section/div[2]/div[2]/div[1]/div/section/div/div/span[@class='gallery-item-count']");
			if (i == 8) {
				String xpathForNextFirstPartViewImage = "//div[3]/div[3]/div[1]/div[2]/section/div[2]/div[2]/div[1]/div/section/section/div/div[1]/div/div[5]";
				String xpathForNextSecondPartViewImage = "//img[@alt='" + data.get(0).get(0) + "']";
				locator2 = xpathForNextFirstPartViewImage.concat(xpathForNextSecondPartViewImage);
			} else {
				String xpathForNextFirstPartViewImage = "//div[3]/div[3]/div[1]/div[2]/section/div[2]/div[2]/div[1]/div/section/section/div/div[1]/div/div[%d+5]";
				String xpathForNextSecondPartViewImage = "//img[@alt='" + data.get(0).get(i) + "']";
				locator2 = String.format(xpathForNextFirstPartViewImage, i).concat(xpathForNextSecondPartViewImage);
			}
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[2]/section/div[2]/div[2]/div[1]/div/section/section/div/div[2]/div[2]/span[@class='lincoln-icon_arrow-right']").click();
			Thread.sleep(5000);
			getVisibleElementByXpath(locator2);
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[2]/section/div[2]/div[2]/div[1]/div/span[contains(@class,'modal-close')]").click();
			Thread.sleep(5000);
		}
	}

	@Then("^Verify overlay open from image Gallery on P1 Lincoln$")
	public void verifyGalleryOverlayOpen(DataTable image) throws Throwable {
		// Write the code to handle Data Table
		List<List<String>> data = image.raw();
		for (int i = 1; i <= 8; i++) {
			System.out.println("Verify " + data.get(0).get(i - 1) + " overlay open from image");
			String locator = null;
			String xpathForFirstPartImage = "//div[%d+6]";
			String xpathForSecondPartImage = "//img[@alt='" + data.get(0).get(i - 1) + "']";
			VehicleImage vehicleImage = VehicleImage.valueOf(data.get(0).get(i - 1).toLowerCase());
			switch (vehicleImage) {
			case image1:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				break;

			case image2:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				break;

			case image3:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				break;

			case image4:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				break;

			case image5:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				break;

			case image6:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				break;

			case image7:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				break;

			case image8:
				locator = String.format(xpathForFirstPartImage, i).concat(xpathForSecondPartImage);
				break;

			default:
				throw new Exception("Invalid Suggestion name: " + vehicleImage);
			}
			getVisibleElementByXpath(locator);
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/span[contains(@class,'modal-close')]");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[2]/div[2]/span[@class='lincoln-icon_arrow-right']");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[2]/div[1]/span[@class='lincoln-icon_arrow-left']");
			getVisibleElementByXpath("//span[@class='icon lincoln-icon_download-thin']");
			getVisibleElementByXpath("//a[@class='btn-icon btn-share js-download-image']");
			getVisibleElementByXpath("//span[@class='lincoln-icon_share-thin']");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/div/div/div/a[2]");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/div/div/span[@class='gallery-item-count']");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/span[contains(@class,'modal-close')]").click();
		}
	}

	@When("^Click on video carousel item on P1 Lincoln$")
	public void clickOnVideoCarouselItem(DataTable video) throws Throwable {
		// Write the code to handle Data Table
		List<List<String>> data = video.raw();
		for (int i = 1; i <= 6; i++) {
			String locator = null;
			String xpathForFirstPartVideo = "//div[3]/div[3]/div[1]/div[4]/div[1]/div[1]/div/div[%d+3]";
			String xpathForSecondPartVideo = "//img[@alt='" + data.get(0).get(i - 1) + "']";
			VehicleVideo vehicleVideo = VehicleVideo.valueOf(data.get(0).get(i - 1).toLowerCase());
			switch (vehicleVideo) {
			case item3:
				locator = String.format(xpathForFirstPartVideo, i).concat(xpathForSecondPartVideo);
				break;

			case item5:
				locator = String.format(xpathForFirstPartVideo, i).concat(xpathForSecondPartVideo);
				break;

			case item6:
				locator = String.format(xpathForFirstPartVideo, i).concat(xpathForSecondPartVideo);
				break;

			case item2:
				locator = String.format(xpathForFirstPartVideo, i).concat(xpathForSecondPartVideo);
				break;

			case item1:
				locator = String.format(xpathForFirstPartVideo, i).concat(xpathForSecondPartVideo);
				break;

			case item4:
				locator = String.format(xpathForFirstPartVideo, i).concat(xpathForSecondPartVideo);
				break;

			default:
				throw new Exception("Invalid Suggestion name: " + vehicleVideo);
			}
			System.out.println("Click on " + data.get(0).get(i - 1) + " video on Gallery");
			getVisibleElementByXpath(locator).click();
			Thread.sleep(5000);
			getVisibleElementByXpath("//div[@id='bright-cove-model']/div[2]/img[@class='vjs-poster']");
			getVisibleElementByXpath("//div[@id='bright-cove-model']/div[2]/i[@class='lincoln-icon_play-thin']").click();
			getVisibleElementByXpath("//span[@id='but_pause' and contains(@class,'modal-close')]");
			getVisibleElementByXpath("//span[@id='but_pause' and contains(@class,'modal-close')]").click();
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[4]/div[1]/div[2]/div[2]/span[@class='lincoln-icon_arrow-right']");
			getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[4]/div[1]/div[2]/div[2]/span[@class='lincoln-icon_arrow-right']").click();
		}
	}

	@When("^Click on exterior image on P1 Lincoln$")
	public void clickOnFirstExteriorImage(DataTable exteriorImage) throws Throwable {
		// Write the code to handle Data Table
		List<List<String>> data = exteriorImage.raw();
		System.out.println("Click on " + data.get(0).get(0) + " exterior image");
		getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[1]/div/div[1]//img[@alt='" + data.get(0).get(0) + "']").click();
	}

	@Then("^See exterior image on P1 Lincoln$")
	public void seeExteriorImage(DataTable exteriorImage) throws Throwable {
		// Write the code to handle Data Table
		List<List<String>> data = exteriorImage.raw();
		System.out.println("See " + data.get(0).get(0) + " exterior image");
		getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[1]/div//img[@alt='" + data.get(0).get(0) + "']");
	}

	@And("^Verify next button on P1 Lincoln$")
	public void verifyNextButton(DataTable exteriorImage) throws Throwable {
		String locator;
		// Write the code to handle Data Table
		List<List<String>> data = exteriorImage.raw();
		for (int i = 1; i <= 12; i++) {
			if (i != 12) {
				int j = i + 6;
				String xpathForNextFirstPartImage = "//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[1]/div/div[" + j + "]";
				String xpathForNextSecondPartImage = "//img[@alt='" + data.get(0).get(i - 1) + "']";
				locator = xpathForNextFirstPartImage.concat(xpathForNextSecondPartImage);
				getVisibleElementByXpath("//div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[2]/div[2]/span[@class='lincoln-icon_arrow-right']").click();
				Thread.sleep(5000);
				System.out.println("Click next button on " + data.get(0).get(i) + " exterior image");
			} else {
				String xpathForNextFirstPartImage = "//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[1]/div/div[7]";
				String xpathForNextSecondPartImage = "//img[@alt='" + data.get(0).get(0) + "']";
				locator = xpathForNextFirstPartImage.concat(xpathForNextSecondPartImage);
				getVisibleElementByXpath("//div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[2]/div[2]/span[@class='lincoln-icon_arrow-right']").click();
				Thread.sleep(5000);
				System.out.println("Click next button on " + data.get(0).get(0) + " exterior image");
			}
			getVisibleElementByXpath(locator);
		}

	}

	@And("^Verify previous button on P1 Lincoln$")
	public void verifyPreviousButton(DataTable exteriorImage) throws Throwable {
		String locator;
		// Write the code to handle Data Table
		List<List<String>> data = exteriorImage.raw();
		for (int i = 1; i <= 12; i++) {
			if (i == 1) {
				String xpathForPrevFirstPartImage = "//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[1]/div/div[18]";
				String xpathForPrevSecondPartImage = "//img[@alt='" + data.get(0).get(i - 1) + "']";
				locator = xpathForPrevFirstPartImage.concat(xpathForPrevSecondPartImage);
				getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[2]/div[1]/span[@class='lincoln-icon_arrow-left']").click();
				Thread.sleep(5000);
				System.out.println("Click previous button on " + data.get(0).get(0) + " exterior image");
			} else {
				int j = 19 - i;
				String xpathForPrevFirstPartImage = "//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[1]/div/div[" + j + "]";
				String xpathForPrevSecondPartImage = "//img[@alt='" + data.get(0).get(i - 1) + "']";
				locator = xpathForPrevFirstPartImage.concat(xpathForPrevSecondPartImage);
				getVisibleElementByXpath("//div[3]/div[3]/div[1]/div[1]/section/div[2]/div[2]/div[1]/div/section/section/div/div[2]/div[1]/span[@class='lincoln-icon_arrow-left']").click();
				Thread.sleep(5000);
				System.out.println("Click previous button on " + data.get(0).get(i - 1) + " exterior image");
			}
			getVisibleElementByXpath(locator);
		}

	}

	@Then("^See all checkboxes of compare models on P1 Lincoln$")
	public void seeAllCheckboxesOfCompareModels() throws Throwable {
		System.out.println("See all checkboxes of compare models");
//		for (int i = 1; i <= 6; i++) {
			// String xpathForFirstString =
			// "//div[4]/div[3]/div[1]/div/section/div[2]/div/div/div[%d]";
			// String xpathForSecondString = "/h3/label/span[@class='checkbox-style
			// gux-icon-check-25px']";
			// String locator = String.format(xpathForFirstString,
			// i).concat(xpathForSecondString);
//			String locator = String.format("//div[%d]/h3/label/span[@class='checkbox-style gux-icon-check-25px']", i);
//			getVisibleElementByXpath(locator);
//		}
		getVisibleElementByXpath("//span[@class='checkbox-style gux-icon-check-25px']");
		Thread.sleep(3000);
		Serenity.takeScreenshot();
	}

	@Then("^Verify user is directed to compare model page on P1 Lincoln$")
	public void verifySeenModelComparePage() throws Throwable {
		System.out.println("Verify user is directed to compare model page");
		getVisibleElementByXpath("//div[@class='select-vehicle-header']//a[contains(text(),'Active Compare')]");
		Thread.sleep(10000);
	}

	@When("^Click on Active Compare on P1 Lincoln$")
	public void clickOnActiveCompare() throws Throwable {
		System.out.println("Click on Active Compare");
		// getVisibleElementByXpath("//div[@class='select-vehicle-header']//a[contains(text(),'Active
		// Compare')]").click();
		getVisibleElementByXpath("//div[contains(@class,'hidden-xs')]/a[contains(text(),' 配置对比') or contains(text(),'Active Compare')  or contains(text(),'비교 모델 선택')]").click();
		Thread.sleep(3000);
		Serenity.takeScreenshot();
	}

	@When("^Select up to 2 items on the list on P1 Lincoln$")
	public void clickOnSelection2Items() throws Throwable {
		System.out.println("Select up to 2 items on the list");
		// getVisibleElementByXpath("//div[4]/div[3]/div[1]/div/section/div[2]/div/div/div[1]/h3/label/span[1]").click();
		// Thread.sleep(3000);
		// getVisibleElementByXpath("//div[4]/div[3]/div[1]/div/section/div[2]/div/div/div[2]/h3/label/span[1]").click();
		getVisibleElementByXpath("//div[1]/h3/label/span[1]").click();
		Thread.sleep(3000);
		getVisibleElementByXpath("//div[2]/h3/label/span[1]").click();

	}

	@When("^Select up to \"(.*?)\" items on the list on P1 Lincoln$")
	public void clickOnSelection3Items(int numberOfItems) throws Throwable {
		System.out.println("Select up to 3 items on the list");
//		getVisibleElementByXpath("//div[1]/h3/label/span[1]").click();
//		getVisibleElementByXpath("//div[2]/h3/label/span[1]").click();
//		getVisibleElementByXpath("//div[3]/h3/label/span[1]").click();

		getVisibleElementByXpath("//div[1]/h3/label/span[1]");
//		List<WebElement> selveh=driver.findElements(By.xpath("//div/h3/label/span[1]"));
		for(int i=1;i<=numberOfItems;i++) {
			getVisibleElementByXpath("//div["+i+"]/h3/label/span[1]").click();
		}
	}

	@When("^Click on Add Vehicle button on P1 Lincoln$")
	public void clickOnAddVehicleButton() throws Throwable {
		System.out.println("Click on Add Vehicle button");
		getVisibleElementByXpath("//button[contains(text(),'添加车型') or contains(text(),'Confirm')]").click();
	}

	@When("^Click on Add Vehicle button$")
	public void clickOnAddVehicleBtn() throws Throwable {
		System.out.println("Click on Add Vehicle button");
		getVisibleElementByXpath("//div[contains(@class,'modal-compare-form selectBoxWrapper3')]//a[contains(@class,'btn btn-small btn-solid outline-gold full-width') and contains(text(),'Add Vehicle') or contains(@class,'btn btn-small btn-solid outline-gold full-width') and contains(text(),'추가 선택')]").click();
	}

	@When("^Click on Confirm butn$")
	public void clickOnConfirmBtn() throws Throwable {
		System.out.println("Click on Confirm button");
		getVisibleElementByXpath("//button[@class='btn btn-small btn-solid bg-gold full-width' and contains(text(),'Confirm') or contains(text(),'확인')]").click();
	}

	@Then("^See \"(.*?)\" components on Select Model overlay on P1 Lincoln$")
	public void seeAllComponentsAreNotSelected(int numberOfItems) throws Throwable {
		System.out.println("See all components on Select Model overlay");
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath("//span[@class='checkbox-style gux-icon-check-25px']"));
		int count = 0;
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				count++;
				if (count == numberOfItems)
					break;
			}
		}
		Thread.sleep(5000);
	}

	@When("^Click on the third checkbox on Select Model overlay on P1 Lincoln$")
	public void clickOnTheThirdCheckboxOnSelectModelOverlay() throws Throwable {
		System.out.println("Click on the third checkbox on Select Model overlay");
		getVisibleElementByXpath("//div[3]/div[2]/label/span[contains(@class,'checkbox-style')]");
		getVisibleElementByXpath("//div[3]/div[2]/label/span[contains(@class,'checkbox-style')]").click();
	}

	@And("^Click on Confirm button on Select Model overlay on P1 Lincoln$")
	public void clickOnConfirmButtonOnSelectModelOverlay() throws Throwable {
		System.out.println("Click on Confirm button on Select Model overlay on P1 Lincoln");
		getVisibleElementByXpath("//button[@ng-show='modelBtn3' and contains(text(),'Confirm Button')]").click();
	}

	@Then("^See all components on Model compare overlay after adding vehicle on P1 Lincoln$")
	public void seeAllComponentsOnModelCompareOverlay() throws Throwable {
		System.out.println("See all components on Model compare overlay on P1 Lincoln");
		getVisibleElementByXpath("//h2[text()='3 Cars Have Been Selected']");
		getVisibleElementByXpath("//div[@class='modal-compare-details modelDesc1 show']//h4[contains(text(),'MKC Select')]");
		getVisibleElementByXpath("//div[@class='modal-compare-details modelDesc2 show']//h4[contains(text(),'MKC Reserve')]");
		getVisibleElementByXpath("//div[@class='modal-compare-details modelDesc3 show']//h4[contains(text(),'MKC Preferred')]");
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath("//a[contains(text(),'Request Test Drive')]"));
		int count = elements.size();
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				count--;
				if (count == 1)
					break;
			}
		}

		List<WebElement> elements1 = driver.findElements(By.xpath("//div[@class='select-compare-vehicle col-xs-12']//a[contains(text(),'Select Another Models')]"));
		int count1 = elements1.size();
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				count1--;
				if (count1 == 1)
					break;
			}
		}
	}

	@When("^Click on Compare Models button on P1 Lincoln$")
	public void clickOnCompareModelsButton() throws Throwable {
		System.out.println("Click on Compare Models button");
		getVisibleElementByXpath("//a[text()='下一步' or text()='Compare Models' or text()='비교하기']").click(); // appended xpath for Compare
		// Models button
		Thread.sleep(5000);
	}

	@Then("^See \"(.*?)\" components that are selected to compare on P1 Lincoln$")
	public void seeAllComponentsAreSelectedToCompare(int numberOfItems) throws Throwable {
		System.out.println("See all components that are selected to compare");
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath("//a[contains(text(),'MKC')]"));
		int count = 0;
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				count++;
				if (count == numberOfItems)
					break;
			}
		}
		Serenity.takeScreenshot();
	}

	@When("^Select model of third vehicle on Model Compare on P1 Lincoln$")
	public void selectModelOfThirdVehicleOnModelCompare(DataTable parameter) throws Throwable {
		System.out.println("Select model of third vehicle on Model Compare");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//select[@name='third-primary-model']").click();
		Thread.sleep(3000);
		getVisibleElementByXpath("//option[contains(text(),'" + data.get(0).get(0) + "')]").click();
		Thread.sleep(3000);
		getVisibleElementByXpath("//select[@name='third-second-model']").click();
		Thread.sleep(3000);
		getVisibleElementByXpath("//option[contains(text(),'" + data.get(0).get(1) + "')]").click();
		Thread.sleep(3000);
	}

	@When("^Click on Disclosure on P1 Lincoln$")
	public void clickOnDisclosure() throws Throwable {
		System.out.println("Click on Disclosure");
		getVisibleElementByXpath("//div[contains(text(),'DISCLOSURES')]").click();
	}

	@When("^Verify see Disclosure Content on P1 Lincoln$")
	public void verifySeeDisclosureConent() throws Throwable {
		System.out.println("Verify see Disclosure Content");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'[1] Some features are only available on select models.')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'[2] Overseas model shown. Philippine specifications may vary.')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'[3] The company reserves the right to change any detail regarding specification, prices, components and colours without prior notice. Some items in photos are optional accessories, and may appear slightly different from actual items.')]");
	}

	@When("^Click on Disclosure to Collapse on P1 Lincoln$")
	public void clickOnDisclosureToCollapse() throws Throwable {
		System.out.println("Click on Disclosure to Collapse");
		getVisibleElementByXpath("//div[contains(text(),'DISCLOSURES')]").click();
	}

	@When("^Verify do not see Disclosure content on P1 Lincoln$")
	public void verifyDontSeeDisclosureContent() throws Throwable {
		System.out.println("Verify don't see Disclosure content");
		verifyInvisibleElement("//div[@class='hidden-xs']/p[contains(text(),'[1] Some features are only available on select models.')]");
		verifyInvisibleElement("//div[@class='hidden-xs']/p[contains(text(),'[2] Overseas model shown. Philippine specifications may vary.')]");
		verifyInvisibleElement("//div[@class='hidden-xs']/p[contains(text(),'[3] The company reserves the right to change any detail regarding specification, prices, components and colours without prior notice. Some items in photos are optional accessories, and may appear slightly different from actual items.')]");
	}

	@When("^Verify footer links are visible on P1 Lincoln$")
	public void verifyFooterLinksAreVisible() throws Throwable {
		System.out.println("Verify footer links are visible");
		getVisibleElementByXpath("//img[@alt='facebook']");
		getVisibleElementByXpath("//img[@alt='twitter']");
		getVisibleElementByXpath("//img[@alt='youtube']");
		getVisibleElementByXpath("//img[@alt='instagram']");

		getVisibleElementByXpath("//div[@class='footer-content']/ul/li/a[text()='SUVs']");
		getVisibleElementByXpath("//div[@class='footer-content']/ul/li/a[text()='Build and Price']");
		getVisibleElementByXpath("//div[@class='footer-content']/ul/li/a[text()='Newsroom']");

	}

	@Then("^See Add Vehicle button is not visible")
	public void See_Add_Vehicle_button_is_not_visible() throws Throwable {
		System.out.println("See Add Vehicle button is not visible");
		verifyInvisibleElement("//*[contains(text(),'Add')]");
	}

	@Then("^See all components on vehicle selector page on P1 Lincoln$")
	public void seeAllComponentsOnVehicleSelectorPage() throws Throwable {
		System.out.println("See all components on vehicle selector page");
		getVisibleElementByXpath("//div[@id='desktop-btn-next']/a[contains(text(),'下一步')]");
		getVisibleElementByXpath("//div[@id='desktop-btn-next-credit']/a[contains(text(),'付款计算器')]");
	}

	@When("^Select MKC SUV vehicle on P1 Lincoln$")
	public void selectMkcSuvVehilce() throws Throwable {
		System.out.println("Select MKC SUV vehicle");
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath("//p[contains(text(),'449,800')]"));
		int count = elements.size();
		int interval = 3000;
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				element.click();
				Thread.sleep(interval);
				count--;
				if (count == 0)
					break;
			}
		}
	}

	@When("^Select MKC SUV vehicle on P1 Lincoln Chrome$")
	public void selectMkcSuvVehilceChrome() throws Throwable {
		System.out.println("Select MKC SUV vehicle");
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		// Actions actions = new Actions(driver);
		// List<WebElement> elements =
		// driver.findElements(By.xpath("//p[contains(text(),'449,800')]"));

		// int count = 0;
		// for (WebElement element : elements) {
		// // actions.moveToElement(element);
		// // actions.click().build().perform();
		//
		// count++;
		// if (count == 2)
		// break;
		// }
	}

	@And("^Click on Select Model button on P1 Lincoln$")
	public void clickOnSelectModelButton() throws Throwable {
		System.out.println("Click on Select Model button");
		getVisibleElementByXpath("//div[@id='desktop-btn-next']/a[contains(text(),'下一步')]").click();
	}

	@Then("^See all components on MKZ vehicle on P1 Lincoln$")
	public void seeAllComponentsOnMkzVehicle() throws Throwable {
		System.out.println("See all components on MKZ vehicle");
		getVisibleElementByXpath("//p[@data-menu='#component-bp-config-price-dropdown-general' and contains(text(),'¥ 449,800')]");
		// getVisibleElementByXpath("//li[@id='summary_btn']/a[contains(text(),
		// '查看及保存')]");
		// getVisibleElementByXpath("//a[contains(text(),'發動機')]");
		// getVisibleElementByXpath("//a[contains(text(),'外觀')]");
		// getVisibleElementByXpath("//a[contains(text(),'室內')]");
		// getVisibleElementByXpath("//a[contains(text(),'額外')]");
		// getVisibleElementByXpath("//div[contains(text(),' 自适应前大灯')]");
	}

	@When("^Click on first header link on MKZ vehicle on P1 Lincoln$")
	public void clickOnFirstHeaderLinkOnMkzVehicle(DataTable firstHeaderLink) throws Throwable {
		// Write the code to handle Data Table
		List<List<String>> data = firstHeaderLink.raw();
		for (int i = 1; i <= 3; i++) {
			System.out.println("Click on " + data.get(0).get(i - 1) + " header link");
			String locator = null;
			String xpathForFirstPartNavigation = "//div[2]/div[1]/nav/ul/li[1]/a[%d]";
			String xpathForSecondPartNavigation = "/span[contains(text(),'" + data.get(0).get(i - 1) + "')]";
			FirstHeaderLink link = FirstHeaderLink.valueOf(data.get(0).get(i - 1));
			switch (link) {
			case 车型手册:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath("//div[@class='hidden-xs']//span[contains(text(),'索取车型手册')]");
				getVisibleElementByXpath("//a[@id='dowloadButton' and contains(text(),'Download')]");
				getVisibleElementByXpath("//a[@id='mailForm' and contains(text(),'Email')]");
				getVisibleElementByXpath("//div[2]/div[6]/div/div/div/div/span[@aria-label='Close']");
				break;

			case 获取最新资讯:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'林肯动向')]");
				getVisibleElementByXpath("//div[@class='hidden-xs']//strong[contains(text(),'个人联系信息')]");
				getVisibleElementByXpath("//div[@class='hidden-xs']//strong[contains(text(),'座驾信息')]");
				getVisibleElementByXpath("//button[contains(text(),' 寄存器')]");
				getVisibleElementByXpath("//div[2]/div[6]/div/div/div/div/span[@aria-label='Close']");
				break;

			case 信用:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'车贷申请')]");
				getVisibleElementByXpath("//div[@class='hidden-xs']//strong[contains(text(),'个人联系信息')]");
				getVisibleElementByXpath("//div[2]/div[6]/div/div/div/div/span[@aria-label='Close']");
				break;

			default:
				throw new Exception("Invalid Suggestion name: " + link);
			}
			getVisibleElementByXpath("//div[2]/div[6]/div/div/div/div/span[@aria-label='Close']").click();
			Thread.sleep(5000);
		}
	}

	@And("^Click on second header link on MKZ vehicle on P1 Lincoln$")
	public void clickOnSecondHeaderLinkOnMkzVehicle(DataTable secondHeaderLink) throws Throwable {
		// Write the code to handle Data Table
		List<List<String>> data = secondHeaderLink.raw();
		for (int i = 1; i <= 3; i++) {
			System.out.println("Click on " + data.get(0).get(i - 1) + " header link");
			String locator;
			String xpathForFirstPartNavigation = "//div[2]/div[1]/nav/ul/li[%d+1]/a";
			String xpathForSecondPartNavigation = "/span[contains(text(),'" + data.get(0).get(i - 1) + "')]";
			SecondHeaderLink link = SecondHeaderLink.valueOf(data.get(0).get(i - 1));
			switch (link) {
			case 登录:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				break;

			case 经销商网络:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				break;

			case 联系我们:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath("//div[@class='hidden-xs']/h1[text()='联系我们']");
				getVisibleElementByXpath("//p[text()='热线400-988-6789']");
				getVisibleElementByXpath("//p[text()='交流互动']");
				getVisibleElementByXpath("//p[contains(text(),'扫一扫关注林肯中国微信')]");
				getVisibleElementByXpath("//p[text()='关注林肯中国微博']");
				getVisibleElementByXpath("//div[2]/div[8]/div/div/div[1]/div/div/div/div/span[contains(@class,'modal-close')]").click();
				break;

			default:
				throw new Exception("Invalid Suggestion name: " + secondHeaderLink);
			}
			driver.get(getProfileURL("https://wwwdev.brandap.ford.com/content/lincoln-uat-demo/sk/en_sk/home/build-and-price-vehicle-configuration.WSPAD-CC9-2015-LincolnMKZCHN.YZBAF.html"));
			Thread.sleep(5000);
		}
	}

	@And("^Click on Main Navigation on MKZ vehicle on P1 Lincoln$")
	public void clickOnMainNavigationOnMkzVehicle(DataTable mainNav) throws Throwable {
		// Write the code to handle Data Table
		List<List<String>> data = mainNav.raw();
		for (int i = 1; i <= 5; i++) {
			System.out.println("Click on " + data.get(0).get(i - 1) + " on Main Navigation");
			String locator = null;
			String xpathForFirstPartNavigation = "//div[2]/div[1]/nav/div/ul/li[%d]";
			String xpathForSecondPartNavigation = "//span[contains(text(),'" + data.get(0).get(i - 1) + "')]";
			MainNavigation navigation = MainNavigation.valueOf(data.get(0).get(i - 1));
			switch (navigation) {
			case 林肯车型:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath("//a[contains(text(),'SUV')]");
				getVisibleElementByXpath("//a[contains(text(),'轿车')]");
				getVisibleElementByXpath("//div[@id='vehicle-all']/div/ul/li[1]//span[contains(text(),'林肯MKX')]");
				getVisibleElementByXpath("//div[@id='vehicle-all']/div/ul/li[2]//span[contains(text(),'林肯MKC')]");
				getVisibleElementByXpath("//div[@id='vehicle-all']/div/ul/li[3]//span[contains(text(),'林肯NAVIGATOR')]");
				// getVisibleElementByXpath("//a[contains(text(),'轿车')]").click();
				getVisibleElementByXpath("//div[@id='vehicle-all']/div/ul/li[4]//span[contains(text(),'林肯大陆CONTINENTAL')]");
				getVisibleElementByXpath("//div[@id='vehicle-all']/div/ul/li[5]//span[contains(text(),'林肯MKZ')]");
				getVisibleElementByXpath("//a[contains(text(),'SUV')]").click();
				getVisibleElementByXpath("//div[@id='vehicle-1']/div/ul/li[1]//span[contains(text(),'林肯MKX')]");
				getVisibleElementByXpath("//div[@id='vehicle-1']/div/ul/li[2]//span[contains(text(),'林肯MKC')]");
				getVisibleElementByXpath("//div[@id='vehicle-1']/div/ul/li[3]//span[contains(text(),'林肯NAVIGATOR')]");
				getVisibleElementByXpath("//a[contains(text(),'轿车')]").click();
				getVisibleElementByXpath("//div[@id='vehicle-2']/div/ul/li[1]//span[contains(text(),'林肯大陆CONTINENTAL')]");
				getVisibleElementByXpath("//div[@id='vehicle-2']/div/ul/li[2]//span[contains(text(),'林肯MKZ')]");
				break;

			case 购车服务:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/ul/li[1]/a[contains(text(),'林肯金融')]");
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/ul/li[2]/a[contains(text(),'对比车型')]");
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/ul/li[3]/a[contains(text(),'配置林肯')]");
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/ul/li[4]/a[contains(text(),'优惠方案')]");
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/ul/li[5]/a[contains(text(),'车型报价')]");
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/ul/li[6]/a[contains(text(),'下载车册')]");
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/ul/li[7]/a[contains(text(),'关注林肯')]");
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/div/div/div[1]/div/a/img");
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/div/div/div[2]/div/a/img");
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/div/div/div[3]/div/a/img");
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/div/div/div[1]/div/section//a[contains(text(),'预约试驾')]");
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/div/div/div[2]/div/section//h2[contains(text(),'金融产品与服务')]");
				getVisibleElementByXpath("//div[@id='menu-1']/div/div/div/div/div[3]/div/section//h2[contains(text(),'配置我的林肯')]");
				break;

			case 走近林肯:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath("//div[@id='menu-2']/div/div/ul/li[1]/a[contains(text(),'林肯之道')]");
				getVisibleElementByXpath("//div[@id='menu-2']/div/div/ul/li[2]/a[contains(text(),'林肯时刻')]");
				getVisibleElementByXpath("//div[@id='menu-2']/div/div/ul/li[3]/a[contains(text(),'百年荣耀')]");
				getVisibleElementByXpath("//div[@id='menu-2']/div/div/ul/li[4]/a[contains(text(),'林肯之道·云服务')]");
				getVisibleElementByXpath("//div[@id='menu-2']/div/div/ul/li[5]/a[contains(text(),'品牌动向')]");
				getVisibleElementByXpath("//div[@id='menu-2']/div/div/ul/li[6]/a[contains(text(),'新闻中心')]");
				getVisibleElementByXpath("//div[@id='menu-2']/div/div/div/div/div[1]/div/a/img");
				getVisibleElementByXpath("//div[@id='menu-2']/div/div/div/div/div[2]/div/a/img");
				getVisibleElementByXpath("//div[@id='menu-2']/div/div/div/div/div[3]/div/a/img");
				getVisibleElementByXpath("//div[@id='menu-2']/div/div/div/div/div[1]/div/section//h2[contains(text(),'全系豪华')]");
				getVisibleElementByXpath("//div[@id='menu-2']/div/div/div/div/div[2]/div/section//h2[contains(text(),'林肯之道')]");
				getVisibleElementByXpath("//div[@id='menu-2']/div/div/div/div/div[3]/div/section//h2[contains(text(),'北京车展')]");
				break;

			case 车主专区:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath("//div[@id='menu-3']/div/div/ul/li[1]/a[contains(text(),'售后服务')]");
				getVisibleElementByXpath("//div[@id='menu-3']/div/div/ul/li[2]/a[contains(text(),'车辆保养')]");
				getVisibleElementByXpath("//div[@id='menu-3']/div/div/ul/li[3]/a[contains(text(),'车贷指南')]");
				getVisibleElementByXpath("//div[@id='menu-3']/div/div/ul/li[4]/a[contains(text(),'获取最新咨询')]");
				getVisibleElementByXpath("//div[@id='menu-3']/div/div/ul/li[5]/a[contains(text(),'服务')]");
				getVisibleElementByXpath("//div[@id='menu-3']/div/div/div/div/div[1]/div/a/img");
				getVisibleElementByXpath("//div[@id='menu-3']/div/div/div/div/div[2]/div/a/img");
				getVisibleElementByXpath("//div[@id='menu-3']/div/div/div/div/div[3]/div/a/img");
				getVisibleElementByXpath("//div[@id='menu-3']/div/div/div/div/div[1]/div/section/a/h2[contains(text(),'车辆保养')]");
				getVisibleElementByXpath("//div[@id='menu-3']/div/div/div/div/div[2]/div/section/a/h2[contains(text(),'经销商网络')]");
				getVisibleElementByXpath("//div[@id='menu-3']/div/div/div/div/div[3]/div/section/a/h2[contains(text(),'获取最新咨询')]");
				break;

			case 预约试驾:
				locator = String.format(xpathForFirstPartNavigation, i).concat(xpathForSecondPartNavigation);
				getVisibleElementByXpath(locator).click();
				getVisibleElementByXpath("//div[2]/div[6]/div/div/div/div/div/div[2]/div/div[1]/h2[contains(text(),'预约试驾')]");
				getVisibleElementByXpath("//select[@name='title']").click();
				getVisibleElementByXpath("//select[@name='title']/option[text()='先生']").click();
				getVisibleElementByXpath("//input[@name='lastName']").sendKeys("Dang");
				getVisibleElementByXpath("//input[@name='firstName']").sendKeys("Son");
				getVisibleElementByXpath("//input[@name='email']").sendKeys("a@gmail.com");
				getVisibleElementByXpath("//input[@name='mobile']").sendKeys("13012345678");
				getVisibleElementByXpath("//select[@name='state']").click();
				getVisibleElementByXpath("//select[@name='state']/option[contains(text(),'省名 1')]").click();
				getVisibleElementByXpath("//select[@name='city']").click();
				getVisibleElementByXpath("//select[@name='city']/option[contains(text(),'城市名 1')]").click();
				getVisibleElementByXpath("//select[@name='preferredDealer']").click();
				getVisibleElementByXpath("//select[@name='preferredDealer']/option[contains(text(),'经销商名称 1')]").click();
				getVisibleElementByXpath("//select[@name='model']").click();
				getVisibleElementByXpath("//select[@name='model']/option[contains(text(),'林肯 MKZ')]").click();
				getVisibleElementByXpath("//select[@name='inMarketDate']").click();
				getVisibleElementByXpath("//select[@name='inMarketDate']/option[contains(text(),'3 个月')]").click();
				getVisibleElementByXpath("//div[2]/div[6]/div/div/div/div/section/div/div/div/div[1]/form/div[2]/div/div/div[7]/div/div/label/span[2]").click();
				getVisibleElementByXpath("//input[@id='captchaValue']").sendKeys("ABCDF");
				getVisibleElementByXpath("//button[contains(text(),' 确认提交')]").click();
				break;

			default:
				throw new Exception("Invalid Suggestion name: " + navigation);
			}

			Thread.sleep(5000);
		}
	}

	@Then("^See the price updated correctly on P1 Lincoln$")
	public void seeThePriceUpdated(DataTable priceUpdated) throws Throwable {
		System.out.println("See the price updated correctly");
		// Write the code to handle Data Table
		List<List<String>> data = priceUpdated.raw();
		getVisibleElementByXpath(data.get(0).get(0), data.get(0).get(1));
	}

	@When("^Click feature to close on engine tab on P1 Lincoln$")
	public void clickFeatureToCloseOnEngineTab() throws Throwable {
		System.out.println("Click feature to close on engine tab");
		getVisibleElementByXpath("//div[contains(text(), '引擎')]").click();
	}

	@Then("^Verify do not see engine content on P1 Lincoln$")
	public void verifyDontSeeEngineContent() throws Throwable {
		System.out.println("Verify don't see engine content");
		verifyInvisibleElement("//div[@id='engine1']//p[contains(text(),'¥')]");
	}

	@When("^Click feature to open on engine tab on P1 Lincoln$")
	public void clickFeatureToOpenOnEngineTab() throws Throwable {
		System.out.println("Click feature to open on engine tab");
		getVisibleElementByXpath("//div[contains(text(), '引擎')]").click();
	}

	@Then("^Verify seeing engine content on P1 Lincoln$")
	public void verifySeeingEngineContent() throws Throwable {
		System.out.println("Verify seeing engine content");
		getVisibleElementByXpath("//p[contains(text(),'¥ 449,800')");
	}

	@When("^Click on exterior tab on P1 Lincoln$")
	public void clickOnExteriorTab() throws Throwable {
		System.out.println("Click on exterior tab");
		getVisibleElementByXpath("//a[contains(text(),'外观')]").click();
	}

	@Then("^See all features on exterior tab on P1 Lincoln$")
	public void seeAllFeaturesOnExteriorTab() throws Throwable {
		System.out.println("See all features on exterior tab");
		getVisibleElementByXpath("//div[contains(text(),'车色')]");
	}

	@When("^Click color feature to close on exterior tab on P1 Lincoln$")
	public void clickColorFeatureToClose() throws Throwable {
		System.out.println("Click color feature to close");
		getVisibleElementByXpath("//div[@id='exterior']/div[1]/div[1]/h4/a/div/i").click();
		Thread.sleep(3000);
	}

	@Then("^Verify do not see color content on P1 Lincoln$")
	public void verifyDontSeeColorContent() throws Throwable {
		System.out.println("Verify do not see color content");
		verifyInvisibleElement("//p[contains(text(),'铂钻白')]");
		verifyInvisibleElement("//p[contains(text(),'雅绅黑')]");
		verifyInvisibleElement("//p[contains(text(),'星河银')]");
		verifyInvisibleElement("//p[contains(text(),'曙光金')]");
		verifyInvisibleElement("//p[contains(text(),'苍穹灰')]");
		verifyInvisibleElement("//p[contains(text(),'墨玉绿')]");
		verifyInvisibleElement("//p[contains(text(),'朱砂红')]");
		verifyInvisibleElement("//p[contains(text(),'典雅灰')]");
		verifyInvisibleElement("//p[contains(text(),'宝石红')]");
	}

	@When("^Click color feature to open on exterior tab on P1 Lincoln$")
	public void clickColorFeatureToOpen() throws Throwable {
		System.out.println("Click color feature to open");
		getVisibleElementByXpath("//div[@id='exterior']/div[1]/div[1]/h4/a/div/i").click();
		Thread.sleep(3000);
	}

	@Then("^Verify seeing color content on P1 Lincoln$")
	public void verifySeeingColorContent(DataTable colorContent) throws Throwable {
		System.out.println("Verify seeing color content");
		List<List<String>> data = colorContent.raw();
		verifySeeingElementVisibleNoMovement(data.get(0).get(0));
		Thread.sleep(10000);
	}

	@When("^Click wheels feature to open on exterior tab on P1 Lincoln$")
	public void clickWheelsFeatureToOpen() throws Throwable {
		System.out.println("Click on wheels feature to open");
		getVisibleElementByXpath("//div[contains(text(),'轮毂')]").click();
		Thread.sleep(3000);
	}

	@Then("^Verify seeing wheels feature content on P1 Lincoln$")
	public void verifySeeingWheelsFeatureContent() throws Throwable {
		System.out.println("Verify seeing wheels feature content");
		driver.findElement(By.xpath("//div[@id='exterior1']//img"));
	}

	@When("^Click wheels feature to close on exterior tab on P1 Lincoln$")
	public void clickWheelsFeatureToClose() throws Throwable {
		System.out.println("Click wheels feature to close");
		getVisibleElementByXpath("//div[contains(text(),'轮毂')]").click();
		Thread.sleep(3000);
	}

	@Then("^Verify do not see wheel content on P1 Lincoln$")
	public void verifyDontSeeWheelContent() throws Throwable {
		System.out.println("Verify do not see wheel content");
		verifyInvisibleElement("//p[contains(text(),'高级铝合金轮毂')]");
	}

	@When("^Click on Galaxy Silver on color tab on P1 Lincoln$")
	public void clickOnGalaxySilver() throws Throwable {
		System.out.println("Click on Galaxy Silver on color tab");
		getVisibleElementByXpath("//p[contains(text(),'雅绅黑')]").click();
		Thread.sleep(10000);
	}

	@When("^Click on interior tab on P1 Lincoln$")
	public void clickOnInteriorTab() throws Throwable {
		System.out.println("Click on interior tab");
		getVisibleElementByXpath("//a[contains(text(),'内饰')]").click();
	}

	@When("^Click on engine tab on P1 Lincoln$")
	public void clickOnEngineTab() throws Throwable {
		System.out.println("Click on engine tab");
		getVisibleElementByXpath("//a[contains(text(),'發動機')]").click();
	}

	@Then("^See all features on interior tab on P1 Lincoln$")
	public void seeAllFeaturesOnInTab(DataTable interiorFeatures) throws Throwable {
		System.out.println("See all features on interior tab");
		List<List<String>> data = interiorFeatures.raw();
		verifySeeingElementVisible(data.get(0).get(0));
	}

	@When("^Click on brown leather seat on seat tab on P1 Lincoln$")
	public void clickOnBrownLeatherSeat() throws Throwable {
		System.out.println("Click on brown leather seat on seat tab");
		getVisibleElementByXpath("//p[contains(text(),'雅榛棕 ')]").click();
	}

	@When("^Click on Seat feature to close on interiror tab on P1 Lincoln$")
	public void clickOnSeatFeatureToClose() throws Throwable {
		System.out.println("Click on Seat feature to close on interiror tab");
		getVisibleElementByXpath("//div[contains(text(),' 座椅')]").click();
		Thread.sleep(3000);
	}

	@Then("^Verify do not see Seat content on P1 Lincoln$")
	public void verifyDontSeeSeatContent(DataTable seatContent) throws Throwable {
		System.out.println("Verify do not see Seat content");
		List<List<String>> data = seatContent.raw();
		verifyDontSeeElementVisible(data.get(0).get(0));
	}

	@When("^Click on Decorative feature to open on P1 Lincoln$")
	public void clickOnDecorativeToOpen() throws Throwable {
		System.out.println("Click on Decorative feature to open");
		getVisibleElementByXpath("//div[contains(text(),'装饰条')]").click();
		Thread.sleep(3000);
	}

	@Then("^Verify seeing decorative content on P1 Lincoln$")
	public void verifySeeingDecorativeContent(DataTable decorativeContent) throws Throwable {
		System.out.println("Verify seeing decorative content");
		List<List<String>> data = decorativeContent.raw();
		verifySeeingElementVisibleNoMovement(data.get(0).get(0));
	}

	@When("^Click on Deep walnut on decorative tab on P1 Lincoln$")
	public void clickOnDeepWalnut() throws Throwable {
		System.out.println("Click on Deep walnut on decorative tab");
		getVisibleElementByXpath("//p[contains(text(),'深核桃木')]").click();
	}

	@When("^Click on Additional tab on P1 Lincoln$")
	public void clickOnAdditionalTab() throws Throwable {
		System.out.println("Click on Additional tab");
		getVisibleElementByXpath("//a[text()='配置']").click();
	}

	@Then("^See all features on Additional tab on P1 Lincoln$")
	public void seeAllFeaturesOnAdditionalTab(DataTable additionalFeatures) throws Throwable {
		System.out.println("See all features on Additional tab");
		List<List<String>> data = additionalFeatures.raw();
		verifySeeingElementVisible(data.get(0).get(0));
	}

	@When("^Click on safe configuration feature to open on Additional tab on P1 Lincoln$")
	public void ClickOnSafeConfigurationFeatureToOpen() throws Throwable {
		System.out.println("Click on safe configuration feature to open");
		getVisibleElementByXpath("//div[contains(text(),'配置')]").click();
	}

	@Then("^See safe configuration content on P1 Lincoln$")
	public void seeSafeConfigurationContent() throws Throwable {
		System.out.println("See safe configuration content");
		getVisibleElementByXpath("//div[@id='extrasCPC']/div/div/div/div/img");
	}

	@When("^Click on safe configuration feature to close on Additional tab on P1 Lincoln$")
	public void ClickOnSafeConfigurationFeatureToClose() throws Throwable {
		System.out.println("Click on safe configuration feature to close");
		getVisibleElementByXpath("//div[contains(text(),' 安全配置')]").click();
		Thread.sleep(3000);
	}

	@Then("^Verify do not see safe configuration content on P1 Lincoln$")
	public void verifyDontSeeSafeConfigurationContent() throws Throwable {
		System.out.println("Verify do not see safe configuration content");
		verifyInvisibleElement("//p[contains(text(),'LESS REAR SEAT BELTS')]");
	}

	@When("^Click on external equipment feature to open on Addition tab on P1 Lincoln$")
	public void clickOnExternalEquipmentFeatureToOpen() throws Throwable {
		System.out.println("Click on external equipment feature to open on Addition tab");
		getVisibleElementByXpath("//div[contains(text(),' 外部配备')]").click();
		Thread.sleep(3000);
	}

	@Then("^See external equipment content on P1 Lincoln$")
	public void seeExternalEquipmentContent() throws Throwable {
		System.out.println("See external equipment content");
		getVisibleElementByXpath("//div[@id='extrasA1G']/div/div/div/div/img");
	}

	@When("^Click on external equipment feature to close on Addition tab on P1 Lincoln$")
	public void clickOnExternalEquipmentFeatureToClose() throws Throwable {
		System.out.println("Click on external equipment feature to close on Addition tab");
		getVisibleElementByXpath("//div[contains(text(),' 外部配备')]").click();
		Thread.sleep(3000);
	}

	@Then("^Verify do not see external equipment content on P1 Lincoln$")
	public void verifyDontSeeExternalEquipmentContent(DataTable equipmentContent) throws Throwable {
		System.out.println("Verify do not see external equipment content");
		List<List<String>> data = equipmentContent.raw();
		verifyDontSeeElementVisible(data.get(0).get(0));

	}

	@When("^Click on control system feature to open on Additional tab on P1 Lincoln$")
	public void clickOnControlSystemFeatureToOpen() throws Throwable {
		System.out.println("Click on control system feature to open");
		getVisibleElementByXpath("//div[contains(text(),'信息娱乐系统')]").click();
		Thread.sleep(3000);
	}

	@Then("^See control system content on P1 Lincoln$")
	public void seeControlSystemContent() throws Throwable {
		System.out.println("See control system content");
		getVisibleElementByXpath("//div[@id='extrasHKC']/div/div/div/div/img");
	}

	@When("^Click on control system feature to close on Additional tab on P1 Lincoln$")
	public void clickOnControlSystemFeatureToClose() throws Throwable {
		System.out.println("Click on control system feature to close");
		getVisibleElementByXpath("//div[contains(text(),'信息娱乐系统')]").click();
		Thread.sleep(3000);
	}

	@Then("^Verify do not see control system content on P1 Lincoln$")
	public void verifyDontSeeControlSystemContent() throws Throwable {
		System.out.println("Verify do not see control system content");
		verifyInvisibleElement("//div[@id='extrasHKC']/div/div/div/div/img");
	}

	@When("^Click on Review and Save button on P1 Lincoln$")
	public void clickOnReviewAndSaveButton() throws Throwable {
		System.out.println("Click on Review and Save button");
		getVisibleElementByXpath("//li[@id='summary_btn']/a[contains(text(),'查看及保存')]").click();
	}

	@And("^Click on first header links on P1 Lincoln$")
	public void clickOnFirstHeaderLinks(DataTable links) throws Throwable {
		System.out.println("Click on first header links");
		List<List<String>> data = links.raw();
		clickOnLinks(data.get(0).get(0));
	}

	@And("^Click on header links on P1 Lincoln$")
	public void clickOnHeaderLinks() throws Throwable {
		String locator;
		String xpath = "//div[3]/div[1]/nav/ul/li[%d]";
		for (int i = 1; i <= 6; i++) {
			System.out.println("Click on " + i + " header links");
			locator = String.format(xpath, i);
			getVisibleElementByXpath(locator).click();
			Thread.sleep(5000);
		}
	}

	@And("^Click on second navigation links on P1 Lincoln$")
	public void clickOnSecondNavigationLinks(DataTable links) throws Throwable {
		System.out.println("Click on second navigation links");
		List<List<String>> data = links.raw();
		clickOnLinks(data.get(0).get(0));
	}

	@And("^Click on links in first tab of second navigation on P1 Lincoln$")
	public void clickOnLinksInFirstTabOfSecondNavigation(DataTable links) throws Throwable {
		System.out.println("Click on links in first tab of second navigation");
		List<List<String>> data = links.raw();
		clickOnLinks(data.get(0).get(0));
	}

	@And("^Click on tab of second navigation on P1 Lincoln$")
	public void clickOnTabOfSecondNavigation(DataTable tab) throws Throwable {
		System.out.println("Click on tab of second navigation");
		List<List<String>> data = tab.raw();
		getVisibleElementByXpath(data.get(0).get(0)).click();
		Thread.sleep(5000);
	}

	@And("^Click on links in second tab of second navigation on P1 Lincoln$")
	public void clickOnLinksInSecondTabOfSecondNavigation(DataTable links) throws Throwable {
		System.out.println("Click on links in second tab of second navigation");
		List<List<String>> data = links.raw();
		clickOnLinksAndCloseTabs(data.get(0).get(0));
		clickOnLinks(data.get(0).get(1));
		clickOnLinksByCtrlEnter(data.get(0).get(2));
	}

	@When("^Click on Credit dropdown list on P1 Lincoln$")
	public void clickOnCreditDropdownList() throws Throwable {
		System.out.println("Click on Credit dropdown list");
		// getVisibleElementByXpath("//a[@class='dropdown-toggle' and
		// contains(text(),'Credit')]").click();
		getVisibleElementByXpath("//span[contains(text(),'Credit')]").click();
	}

	@Then("^See the list of Credit on P1 Lincoln$")
	public void seeTheListOfCredit() throws Throwable {
		System.out.println("See the list of Credit");
		getVisibleElementByXpath("//a[contains(text(),'Finance Offers')]");
		getVisibleElementByXpath("//a[contains(text(),'Finance Guide')]");
		getVisibleElementByXpath("//a[contains(text(),'Finance Overview')]");
		getVisibleElementByXpath("//a[contains(text(),'Finance Product Overview')]");
		getVisibleElementByXpath("//a[contains(text(),'Finance_Product_Overview_Details_1')]");
		getVisibleElementByXpath("//a[contains(text(),'Finance_Product_Overview_Details_2')]");
		getVisibleElementByXpath("//a[contains(text(),'Finance_Product_Overview_Details_3')]");
		getVisibleElementByXpath("//a[contains(text(),'Payment Estimator')]");
	}

	@When("^Click on item on Credit dropdown list on P1 Lincoln$")
	public void clickOnItemOnCreditDropdownList(DataTable item) throws Throwable {
		List<List<String>> data = item.raw();
		System.out.println("Click on " + data.get(0).get(0) + " on Credit dropdown list");
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(0) + "')]").click();
	}

	@Then("^See all components on Finance Overview on P1 Lincoln$")
	public void seeAllComponentsOnFinanceOverview() throws Throwable {
		System.out.println("See all components on Finance Overview");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'车型手册')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'信用')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'登录')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'联系我们')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'中文')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'EN')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'预约试驾')]");
		// getVisibleElementByXpath("//a[@class='dropdown-toggle' and
		// contains(text(),'Credit')]");
		getVisibleElementByXpath("//span[contains(text(),'Credit')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'配置林肯')]");
		// getVisibleElementByXpath("//a[contains(text(),'了解更多')]");
		// getVisibleElementByXpath("//a[contains(text(),'阅读全文')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_hotline-outline')]");
		getVisibleElementByXpath("//span[contains(text(),'400 - 820 - 5071')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_wechat')]");
		getVisibleElementByXpath("//span[contains(text(),'ID: Lincoln_IN_China')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_email-outline')]");
		getVisibleElementByXpath("//span[contains(text(),'info12@lincoln.com')]");
		getVisibleElementByXpath("//div[contains(text(),'DISCLOSURES')]");
	}

	@And("^See all components on Finance Offers on P1 Lincoln$")
	public void seeAllComponentsOnFinanceOffers() throws Throwable {
		System.out.println("See all components on Finance Offers");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'车型手册')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'信用')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'登录')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'联系我们')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'中文')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'EN')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//span[contains(text(),'Credit')]");
		getVisibleElementByXpath("//span[contains(text(),'Finance Offers')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//strong[contains(text(),'MKX/MKC/MKZ')]");
		getVisibleElementByXpath("//table[contains(@class,'hidden-xs')]//h4[contains(text(),'车贷期限')]");
		getVisibleElementByXpath("//table[contains(@class,'hidden-xs')]//h4[contains(text(),'12个月')]");
		getVisibleElementByXpath("//table[contains(@class,'hidden-xs')]//h4[contains(text(),'24个月')]");
		getVisibleElementByXpath("//table[contains(@class,'hidden-xs')]//h4[contains(text(),'36个月')]");
		getVisibleElementByXpath("//table[contains(@class,'hidden-xs')]//td[contains(text(),'MKX')]");
		getVisibleElementByXpath("//table[contains(@class,'hidden-xs')]//td[contains(text(),'MKZ')]");
		getVisibleElementByXpath("//table[contains(@class,'hidden-xs')]//td[contains(text(),'%')]");
		getVisibleElementByXpath("//u[contains(text(),'车贷计算器')]");
	}

	@And("See all components on Finance Guide on P1 Lincoln")
	public void seeAllComponentsOnFinanceGuide() throws Throwable {
		System.out.println("See all components on Finance Guide");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'车型手册')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'信用')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'登录')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'联系我们')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'中文')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'EN')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//span[contains(text(),'Credit')]");
		getVisibleElementByXpath("//span[contains(text(),'Finance Guide')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'申请材料')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'轻松体验林肯金融的卓越服务')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'身份证明')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'收入类证明')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'驾驶证')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'居住类证明')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'融資過程')]");
		getVisibleElementByXpath("//span[contains(text(),'客戶應用')]");
		getVisibleElementByXpath("//span[contains(text(),'信貸審查')]");
		getVisibleElementByXpath("//span[contains(text(),'合約結算')]");
		getVisibleElementByXpath("//a[contains(text(),'阅读全文')]");
		getVisibleElementByXpath("//span[@class='icon hidden-xs lincoln-icon_hotline-outline']");
		getVisibleElementByXpath("//div[contains(text(),'400-820-5071')]");
		getVisibleElementByXpath("//span[@class='icon hidden-xs lincoln-icon_email-outline']");
		getVisibleElementByXpath("//div[contains(text(),'lnfokr@lincoln.com')]");
	}

	@And("^See all components on Finance Product Overview on P1 Lincoln$")
	public void seeAllComponentsOnFinanceProductOverview() throws Throwable {
		System.out.println("See all components on Finance Product Overview");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'车型手册')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'信用')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'登录')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'联系我们')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'中文')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'EN')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//span[contains(text(),'Credit')]");
		getVisibleElementByXpath("//span[contains(text(),'Finance Product Overview')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'金融产品与服务')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/h3[contains(text(),'林肯红地毯弹性购车计划')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/h3[contains(text(),'林肯含延保金融专案')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/h3[contains(text(),'林肯金融“小时贷”专享方案')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/h4[contains(text(),'林肯金融致力于提供各种为你量身定制的产品、方案与服务，充分满足你的金融服务 需求.')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/h3[contains(text(),'林肯金融服务，除却融资，还有更多。')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'点击查看详情')]");
		getVisibleElementByXpath("//a[contains(text(),'阅读全文')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_hotline-outline')]");
		getVisibleElementByXpath("//span[contains(text(),'400 - 820 - 5071')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_wechat')]");
		getVisibleElementByXpath("//span[contains(text(),'ID- Lincoln_IN_China')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_email-outline')]");
		getVisibleElementByXpath("//span[contains(text(),'info12@lincoln.com')]");
		getVisibleElementByXpath("//div[contains(text(),'DISCLOSURES')]");
	}

	@And("^See all components on Finance Product Overview Details 1 on P1 Lincoln$")
	public void seeAllComponentsOnFinanceProductOverviewDetailsOne() throws Throwable {
		System.out.println("See all components on Finance Product Overview Detail 1");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'车型手册')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'信用')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'登录')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'联系我们')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'中文')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'EN')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//span[contains(text(),'Credit')]");
		getVisibleElementByXpath("//span[contains(text(),'Finance_Product_Overview_Details_1')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'回去')]");
		getVisibleElementByXpath("//div[@class='component-box-layout hidden-xs']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_hotline-outline')]");
		getVisibleElementByXpath("//span[contains(text(),'400 - 820 - 5071')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_wechat')]");
		getVisibleElementByXpath("//span[contains(text(),'ID: Lincoln_IN_China')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_email-outline')]");
		getVisibleElementByXpath("//span[contains(text(),'info12@lincoln.com')]");
		getVisibleElementByXpath("//div[contains(text(),'DISCLOSURES')]");
	}

	@And("^See all components on Finance Product Overview Details 2 on P1 Lincoln$")
	public void seeAllComponentsOnFinanceProductOverviewDetailsTwo() throws Throwable {
		System.out.println("See all components on Finance Product Overview Detail 1");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'车型手册')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'信用')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'登录')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'联系我们')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'中文')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'EN')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//span[contains(text(),'Credit')]");
		getVisibleElementByXpath("//span[contains(text(),'Finance_Product_Overview_Details_2')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/h1[contains(text(),'测试')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'回去')]");
		getVisibleElementByXpath("//div[@class='component-box-layout hidden-xs']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_hotline-outline')]");
		getVisibleElementByXpath("//span[contains(text(),'400 - 820 - 5071')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_wechat')]");
		getVisibleElementByXpath("//span[contains(text(),'ID: Lincoln_IN_China')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_email-outline')]");
		getVisibleElementByXpath("//span[contains(text(),'info12@lincoln.com')]");
		getVisibleElementByXpath("//div[contains(text(),'DISCLOSURES')]");
	}

	@And("^See all components on Finance Product Overview Details 3 on P1 Lincoln$")
	public void seeAllComponentsOnFinanceProductOverviewDetailsThree() throws Throwable {
		System.out.println("See all components on Finance Product Overview Detail 1");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'车型手册')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'信用')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'登录')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'联系我们')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'中文')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'EN')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//span[contains(text(),'Credit')]");
		getVisibleElementByXpath("//span[contains(text(),'Finance_Product_Overview_Details_3')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/h1[contains(text(),'测试')]");
		getVisibleElementByXpath("//h4[text()='Test Heading Left']");
		getVisibleElementByXpath("//h4[text()='Test Heading Right']");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'回去')]");
		getVisibleElementByXpath("//div[@class='component-box-layout hidden-xs']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_hotline-outline')]");
		getVisibleElementByXpath("//span[contains(text(),'400 - 820 - 5071')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_wechat')]");
		getVisibleElementByXpath("//span[contains(text(),'ID: Lincoln_IN_China')]");
		getVisibleElementByXpath("//span[contains(@class,'hidden-xs lincoln-icon_email-outline')]");
		getVisibleElementByXpath("//span[contains(text(),'info12@lincoln.com')]");
		getVisibleElementByXpath("//div[contains(text(),'DISCLOSURES')]");
	}

	@And("^See all components on Payment Estimator on P1 Lincoln$")
	public void seeAllComponentsOnPaymentEstimator() throws Throwable {
		System.out.println("See all components on Payment Estimator");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'车型手册')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'信用')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'登录')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'联系我们')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'中文')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[contains(text(),'EN')]");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//span[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//span[contains(text(),'Credit')]");
		getVisibleElementByXpath("//span[contains(text(),'Payment Estimator')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//div[@class='pe-preview-name']/h1");
		getVisibleElementByXpath("//div[@class='pe-preview-price']/small[contains(text(),'市场指导价')]");
		getVisibleElementByXpath("//div[@class='pe-preview-price']/p");
		getVisibleElementByXpath("//a[contains(text(),'常规金融方案')]");
		getVisibleElementByXpath("//a[contains(text(),'半付半贷')]");
		// List<List<String>> data = tab.raw();
		// clickOnLinks(data.get(0).get(0));
		// getVisibleElementByXpath("//div[contains(text(),'方案总结')]");
		// seeComponents(data.get(0).get(1));
		// getVisibleElementByXpath("//div[contains(text(),'基本参数')]");
		// getVisibleElementByXpath("//div[contains(text(),'基本参数')]").click();
		// getVisibleElementByXpath("//h3[contains(text(),'添加当前车辆')]");
		// getVisibleElementByXpath("//i[@class='lincoln-icon_add']");
		// getVisibleElementByXpath("//i[@class='lincoln-icon_add']").click();
		// getVisibleElementByXpath("//h3[contains(text(),'MKZ2017 Premiere')]");
		// getVisibleElementByXpath("//a[contains(text(),'删除当前车辆')]");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_pdf']");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_share-thin']");
		getVisibleElementByXpath("//div[contains(text(),'DISCLOSURES')]");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_pdf']").click();
		// getVisibleElementByXpath("//span[@class='icon
		// lincoln-icon_share-thin']").click();
		// getVisibleElementByXpath("//a[@aria-label='Close' and
		// contains(text(),'close')]").click();
	}

	@And("^Verify do not see the content of Disclosures on P1 Lincoln$")
	public void veriftDontSeeTheContentOfDisclosures() throws Throwable {
		System.out.println("Verify do not see the content of Disclosures");
		verifyInvisibleElement("//div[@class='hidden-xs']/p[contains(text(),'本网站所展示车型仅供参考')]");
	}

	@When("^Click on Disclosures on P1 Lincoln$")
	public void clickOnDisclosures() throws Throwable {
		System.out.println("Click on Disclosures");
		getVisibleElementByXpath("//div[contains(text(),'DISCLOSURES')]").click();
		Thread.sleep(5000);
	}

	@Then("^See the content of Disclosures on P1 Lincoln$")
	public void seeTheContentOfDisclosures() throws Throwable {
		System.out.println("See the content of Disclosures");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'本网站所展示车型仅供参考')]");
	}

	@When("^Click on Disclosures to close on P1 Lincoln$")
	public void clickOnDisclosuresToClose() throws Throwable {
		System.out.println("Click on Disclosures to close");
		getVisibleElementByXpath("//i[@class='lincoln-icon_arrow-down']").click();
		Thread.sleep(5000);
	}

	@Then("^See all components on RTL Lincoln$")
	public void seeAllComponentsOnRTL(DataTable components) throws Throwable {
		System.out.println("See all components on RTL");
		List<List<String>> data = components.raw();
		verifySeeingElementVisible(data.get(0).get(0));
		verifySeeingElementVisible(data.get(0).get(1));
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//input[@id='state']");
		getVisibleElementByXpath("//a[@class='btn-search']/i[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//a[contains(text(),'استخدام البحث المتقدم')]");
		getVisibleElementByXpath("//h2[contains(text(),'لبحث عن أقرب موزع لديك')]");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_share-thin']");
		getVisibleElementByXpath("//a[@class='btn-icon btn-share text-dark-grey']");
		getVisibleElementByXpath("//i[@class='lincoln-icon_arrow-down']");
		getVisibleElementByXpath("//div[contains(text(),'إفصاحات')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'هي بعض الميزات المتوفرة فقط على نماذج مختارة')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'نموذج الخارج هو مبين. قد تختلف مواصفات الفلبينية')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),' تحتفظ الشركة بالحق في تغيير أي من التفاصيل بشأن مواصفات وأسعار والمكونات والألوان من دون إشعار مسبق. بعض العناصر الموجودة في الصور هي الملحقات الاختيارية، وقد تظهر مختلف قليلا من العناصر الفعلية.')]");
		verifySeeingElementVisible(data.get(0).get(2));
		verifySeeingElementVisible(data.get(0).get(3));
		verifySeeingElementVisible(data.get(0).get(4));
		verifySeeingElementVisible(data.get(0).get(5));
		verifySeeingElementVisible(data.get(0).get(6));
	}

	@When("^Click Disclosure to close on RTL Lincoln$")
	public void clickDisclosureToClose() throws Throwable {
		System.out.println("Click Disclosure to close");
		getVisibleElementByXpath("//i[@class='lincoln-icon_arrow-down']").click();
	}

	@Then("^Verify do not see content on Disclosure on RTL Lincoln$")
	public void verifyDontSeeContentOnDisclosure(DataTable content) throws Throwable {
		System.out.println("Verify do not see content on Disclosure");
		List<List<String>> data = content.raw();
		verifyDontSeeElementVisible(data.get(0).get(0));
	}

	@When("^Click Disclosure to open on RTL Lincoln$")
	public void clickDisclosureToOpen() throws Throwable {
		System.out.println("Click Disclosure to open");
		getVisibleElementByXpath("//i[@class='lincoln-icon_arrow-down']").click();
	}

	@Then("^Verify seeing content on Disclosure on RTL Lincoln$")
	public void verifySeeingContentOnDisclosure(DataTable content) throws Throwable {
		System.out.println("Verify seeing content on Disclosure");
		List<List<String>> data = content.raw();
		verifySeeingElementVisible(data.get(0).get(0));
	}

	@When("^Click on links of footer on RTL Lincoln$")
	public void clickOnLinksOfFooter(DataTable footerLinks) throws Throwable {
		System.out.println("Click on links of footer");
		List<List<String>> data = footerLinks.raw();
		clickOnLinksByCtrlEnter(data.get(0).get(0));
		clickOnLinksByCtrlEnter(data.get(0).get(1));
		clickOnLinksByCtrlEnter(data.get(0).get(2));
	}

	@When("^Click on links of third list at footer on RTL Lincoln$")
	public void clickOnLinksOfThirdListAtFooter(DataTable footerLinks) throws Throwable {
		System.out.println("Click on links of third list at footer");
		List<List<String>> data = footerLinks.raw();
		clickOnLinksAndClosePopup(data.get(0).get(0), data.get(0).get(1));
	}

	@When("^Click on first header links on RTL Lincoln$")
	public void clickOnFirstHeaderLinksOnRtl(DataTable headerLinks) throws Throwable {
		System.out.println("Click on first header links");
		List<List<String>> data = headerLinks.raw();
		clickOnLinks(data.get(0).get(0));
	}

	@When("^Click on second header links on RTL Lincoln$")
	public void clickOnSecondHeaderLinksOnRtl(DataTable headerLinks) throws Throwable {
		System.out.println("Click on second header links");
		List<List<String>> data = headerLinks.raw();
		clickOnLinksAndCloseTabs(data.get(0).get(0));
	}

	@Then("^See all components on after sales care page on P1 Lincoln$")
	public void seeAllComponentsOnAfterSalesCarePage() throws Throwable {
		System.out.println("See all components on after sales care page");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='登陆']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li[1]//span[text()='注册']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li[2]//span[text()='注册']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='经销商网络']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='联系我们']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='中文']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='EN']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'预约试驾')]");

		// getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//span[contains(text(),'新车有限保修')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//p[contains(text(),'林肯品牌授权经销商售出的所有林肯汽车都享有新车有限保修。在新车保修期3年或者10万公里内（以先到者为准），林肯车辆上发现的由材料或装配工艺导致的故障都在保修范围内。你只需将爱车送至任一林肯品牌授权经销商，因为那里有熟知林肯汽车性能与构造，技术娴熟的技师为你提供专业服务，排忧解难。')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//span[contains(text(),'三包有效期')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//p[contains(text(),'按照《家用汽车产品修理、更换、退货责任规定》，可凭有效的三包凭证在 2年或者5万公里三包有效期内，（以先到者为准）享受三包服务。')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//span[contains(text(),'林肯纯正原厂配件')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//p[contains(text(),'为回馈客户对林肯品牌的支持，值此品牌进入中国之际，特向新购车车主推出延保回馈计划。凡于林肯中国2014年10月正式上市至2015年10月14日之间在林肯中国授权经销商处第一次购买MKC或MKZ新车并同时购买林肯新车延保计划至尊方案（以下称“延保方案”）的客户，在首次购车后30天至五年内，如再次于任何一家林肯中国授权经销商处购买林肯新车（含增购及置换），可获得等同于购买该延保方案金额的现金返还。如客户在五年内曾使用延保方案，再次购买新车时，如符合本活动条款，仍可获得延保方案的全额现金返还。具体条款如下')]");
		getVisibleElementByXpath("//a[text()='Test CTA']");

		getVisibleElementByXpath("//h3[text()='经销商查询']");
		getVisibleElementByXpath("//p[contains(text(),'您的位置')]");
		getVisibleElementByXpath("//button[contains(text(),' 确认提交')]");

		getVisibleElementByXpath("//div[@class='hidden-xs']//span[contains(text(),'400-060-0980')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[text()='林肯24小时道路救援电话']");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'车辆保养套餐建议价格')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//span[contains(text(),'车辆保养')]");

		getVisibleElementByXpath("//h4[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKZ')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯大陆CONTINENTAL')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKC')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKX')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯NAVIGATOR')]");

		getVisibleElementByXpath("//h4[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'林肯金融')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'车型对比')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'礼遇方案')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取经销商报价')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取车型手册')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'经销商网络')]");

		getVisibleElementByXpath("//h4[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@id='footer3']/li[1]/a[contains(text(),'林肯之道')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'林肯时刻')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'百年荣耀')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'林肯之道•云服务')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'品牌动向')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'新闻中心')]");

		getVisibleElementByXpath("//h4[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'售后服务')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'车主手册')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'SYNC')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'My Lincoln Mobile')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'创建我的林肯ID')]");
	}

	@When("^Click on first header links on after sales care page on P1 Lincoln$")
	public void clickOnFirstHeaderLinksOnAfterSalesCarePage(DataTable firstHeaderLinks) throws Throwable {
		System.out.println("Click on first header links on after sales care page");
		List<List<String>> data = firstHeaderLinks.raw();
		clickOnLinks(data.get(0).get(0));
	}

	@When("^Click on first header link on after sales care page on P1 Lincoln$")
	public void clickOnFirstHeaderLinkOnAfterSalesCarePage(DataTable firstHeaderLink) throws Throwable {
		List<List<String>> data = firstHeaderLink.raw();
		System.out.println("Click on first header link " + data.get(0).get(0) + " on After Sales Care page");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'" + data.get(0).get(0) + "')]").click();
	}

	@When("^Click on links in each item of first header links on after sales page on P1 Lincoln$")
	public void clickOnLinksInEachItemOfFirstHeaderLinksOnAfterSalesPage(DataTable linksInSecondItem) throws Throwable {
		System.out.println("Click on links in each item of first header links on after sales page");
		List<List<String>> data = linksInSecondItem.raw();
		clickOnLinks(data.get(0).get(0));
		clickOnLinks(data.get(0).get(1));
	}

	@And("^Click on dropdown list of second navigation on P1 Lincoln$")
	public void clickOnDropdownListOfSecondNavigation() throws Throwable {
		System.out.println("Click on dropdown list of second navigation");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'车主专区')]").click();
	}

	@And("^Click on link in dropdown list of second navigation on P1 Lincoln$")
	public void clickOnLinkInDropdownListOfSecondNavigation(DataTable itemOfDropdownList) throws Throwable {
		List<List<String>> data = itemOfDropdownList.raw();
		System.out.println("Click on " + data.get(0).get(0) + " link in dropdown list of second navigation");
		getVisibleElementByXpath("//ul[@class='dropdown-menu first']//a[contains(text(),'" + data.get(0).get(0) + "')]").click();
	}

	@Then("^See all components on connectivity page on P1 Lincoln$")
	public void seeAllComponentsOnConnectivityPage() throws Throwable {
		System.out.println("See all components on connectivity page on P1 Lincoln");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='登陆']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li[1]//span[text()='注册']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li[2]//span[text()='注册']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='经销商网络']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='联系我们']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='中文']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='EN']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'预约试驾')]");

		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'配置林肯')]");

		getVisibleElementByXpath("//h2[contains(text(),'林肯互联')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'是科技，把人还给生活。当你选择全新林肯互联科技，无论是驾驶爱车还是置身别处，都将享受更即时的服务响应，不再错过每个重要时刻。')]");
		getVisibleElementByXpath("//h2[contains(text(),'SYNC')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'SYNC')]");
		getVisibleElementByXpath("//p[contains(text(),'也可以同步使用手机等便携设备。')]");
		getVisibleElementByXpath("//h2[contains(text(),'林肯车主应用')]");
		getVisibleElementByXpath("//p[contains(text(),'林肯车主应用是一个易于操作且人性化的移动应用，')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']/p[contains(text(),'可轻松实现远程发动、上锁、解锁等操作。')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'即刻前往')]");

		getVisibleElementByXpath("//h4[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKZ')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯大陆CONTINENTAL')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKC')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKX')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯NAVIGATOR')]");

		getVisibleElementByXpath("//h4[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'林肯金融')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'车型对比')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'礼遇方案')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取经销商报价')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取车型手册')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'经销商网络')]");

		getVisibleElementByXpath("//h4[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@id='footer3']/li[1]/a[contains(text(),'林肯之道')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'林肯时刻')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'百年荣耀')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'林肯之道•云服务')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'品牌动向')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'新闻中心')]");

		getVisibleElementByXpath("//h4[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'售后服务')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'车主手册')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'SYNC')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'My Lincoln Mobile')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'创建我的林肯ID')]");
	}

	@Then("^See all components on manuals page on P1 Lincoln$")
	public void seeAllComponentsOnManualsPage() throws Throwable {
		System.out.println("See all components on manuals page");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='登陆']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li[1]//span[text()='注册']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li[2]//span[text()='注册']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='经销商网络']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='联系我们']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='中文']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='EN']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'预约试驾')]");

		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'配置林肯')]");

		getVisibleElementByXpath("//div[@class='hidden-xs']/h3[contains(text(),'车主手册')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//strong[contains(text(),'车主手册')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//strong[contains(text(),'快速操作指南')]");
		getVisibleElementByXpath("//figcaption[contains(text(),'林肯 林肯MKX')]");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/WSPAD-CD9-2017-LincolnMKXCHN/nameplate.jpg']");
		getVisibleElementByXpath("//figcaption[contains(text(),'林肯 林肯MKC')]");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/WSPAD-TME-2017-LincolnMKCCHN/nameplate.jpg']");
		getVisibleElementByXpath("//figcaption[contains(text(),'林肯 林肯大陆CONTINENTAL')]");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/WSPAD-CPL-2017-LincolnContinentalCHN/nameplate.jpg']");
		getVisibleElementByXpath("//figcaption[contains(text(),'林肯 林肯NAVIGATOR')]");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/WSPAD-TB5-2017-LincolnNavigatorCHN/nameplate.jpg']");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'下载车主手册')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'下载快速操作指南')]");

		getVisibleElementByXpath("//h4[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKZ')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯大陆CONTINENTAL')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKC')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKX')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯NAVIGATOR')]");

		getVisibleElementByXpath("//h4[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'林肯金融')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'车型对比')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'礼遇方案')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取经销商报价')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取车型手册')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'经销商网络')]");

		getVisibleElementByXpath("//h4[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@id='footer3']/li[1]/a[contains(text(),'林肯之道')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'林肯时刻')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'百年荣耀')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'林肯之道•云服务')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'品牌动向')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'新闻中心')]");

		getVisibleElementByXpath("//h4[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'售后服务')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'车主手册')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'SYNC')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'My Lincoln Mobile')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'创建我的林肯ID')]");
	}

	@Then("^See all components on videos page on P1 Lincoln$")
	public void seeAllComponentsOnVideosPage() throws Throwable {
		System.out.println("See all components on videos page");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='登陆']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li[1]//span[text()='注册']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li[2]//span[text()='注册']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='经销商网络']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='联系我们']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='中文']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='EN']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'预约试驾')]");

		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'配置林肯')]");

		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'外观设计')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'一体式设计电动全景天窗')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='一体式设计电动全景天窗']");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'雨刮器联动前大灯')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='雨刮器联动前大灯']");

		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'内饰设计')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'驾驶座记忆功能')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='驾驶座记忆功能']");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'LED座舱氛围灯')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='LED座舱氛围灯']");

		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'舒适与便利')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'感应式自动开启后备箱')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='感应式自动开启后备箱']");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'车门密码开启系统')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='车门密码开启系统']");

		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'安全与创新')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'车道保持辅助系统')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='车道保持辅助系统']");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'倒车影像系统')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='倒车影像系统']");

		getVisibleElementByXpath("//h4[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKZ')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯大陆CONTINENTAL')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKC')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKX')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯NAVIGATOR')]");

		getVisibleElementByXpath("//h4[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'林肯金融')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'车型对比')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'礼遇方案')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取经销商报价')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取车型手册')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'经销商网络')]");

		getVisibleElementByXpath("//h4[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@id='footer3']/li[1]/a[contains(text(),'林肯之道')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'林肯时刻')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'百年荣耀')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'林肯之道•云服务')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'品牌动向')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'新闻中心')]");

		getVisibleElementByXpath("//h4[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'售后服务')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'车主手册')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'SYNC')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'My Lincoln Mobile')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'创建我的林肯ID')]");
	}

	@Then("^See all components on videosvss page on P1 Lincoln$")
	public void seeAllComponentsOnVideosvssPage() throws Throwable {
		System.out.println("See all components on videosvss page");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='登陆']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li[1]//span[text()='注册']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li[2]//span[text()='注册']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='经销商网络']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='联系我们']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='中文']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='EN']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'预约试驾')]");

		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'经销商网络')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//div[@class='body-wrapper']/div[2]//a[contains(text(),'配置林肯')]");

		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'外观设计')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='一体式设计电动全景天窗']");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'雨刮器联动前大灯')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='雨刮器联动前大灯']");

		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'内饰设计')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'主动噪音控制系统')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='主动噪音控制系统']");

		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'舒适与便利')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'林肯迎宾感应')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='林肯迎宾感应']");

		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'安全与创新')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'车道保持辅助系统')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='车道保持辅助系统']");
		getVisibleElementByXpath("//div[@class='owl-item active']//p[contains(text(),'倒车影像系统')]");
		getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='倒车影像系统']");

		getVisibleElementByXpath("//h4[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKZ')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯大陆CONTINENTAL')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKC')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKX')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯NAVIGATOR')]");

		getVisibleElementByXpath("//h4[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'林肯金融')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'车型对比')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'礼遇方案')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取经销商报价')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取车型手册')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'经销商网络')]");

		getVisibleElementByXpath("//h4[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@id='footer3']/li[1]/a[contains(text(),'林肯之道')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'林肯时刻')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'百年荣耀')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'林肯之道•云服务')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'品牌动向')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'新闻中心')]");

		getVisibleElementByXpath("//h4[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'售后服务')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'车主手册')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'SYNC')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'My Lincoln Mobile')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'创建我的林肯ID')]");
	}

	@Then("^See all components on after sales care thom page on P1 Lincoln$")
	public void seeAllComponentsOnAfterSalesCareThomPage() throws Throwable {
		System.out.println("See all components on after sales care thom page");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='登陆']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li[1]//span[text()='注册']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']/li[2]//span[text()='注册']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='经销商网络']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='联系我们']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='中文']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[text()='EN']");
		getVisibleElementByXpath("//ul[@class='login-bar navbar-right']//span[@class='lincoln-icon_search']");
		getVisibleElementByXpath("//div[@class='main-nav']//img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'预约试驾')]");

		getVisibleElementByXpath("//div[@class='hidden-xs']//span[contains(text(),'新车有限保修')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//p[contains(text(),'林肯品牌授权经销商售出的所有林肯汽车都享有新车有限保修。在新车保修期3年或者10万公里内（以先到者为准），林肯车辆上发现的由材料或装配工艺导致的故障都在保修范围内。你只需将爱车送至任一林肯品牌授权经销商，因为那里有熟知林肯汽车性能与构造，技术娴熟的技师为你提供专业服务，排忧解难。')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//span[contains(text(),'三包有效期')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//p[contains(text(),'按照《家用汽车产品修理、更换、退货责任规定》，可凭有效的三包凭证在 2年或者5万公里三包有效期内，（以先到者为准）享受三包服务。')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//span[contains(text(),'林肯纯正原厂配件')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//p[contains(text(),'纯于心，正于品，林肯不仅为你带来百年传奇，更为你的传奇座驾延续提供恒久保障。林肯原厂配件均经过严苛测试，保证与原车的匹配性，让座驾始终保持最佳状态，守护你的每次出行。坚持纯正林肯血统配件，是你的第一选择。')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//span[contains(text(),'林肯中国忠诚客户延保回馈计划')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//p[contains(text(),'为回馈客户对林肯品牌的支持，值此品牌进入中国之际，特向新购车车主推出延保回馈计划。凡于林肯中国2014年10月正式上市至2015年10月14日之间在林肯中国授权经销商处第一次购买MKC或MKZ新车并同时购买林肯新车延保计划至尊方案（以下称“延保方案”）的客户，在首次购车后30天至五年内，如再次于任何一家林肯中国授权经销商处购买林肯新车（含增购及置换），可获得等同于购买该延保方案金额的现金返还。如客户在五年内曾使用延保方案，再次购买新车时，如符合本活动条款，仍可获得延保方案的全额现金返还。具体条款如下：')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[text()='Test CTA']");
		getVisibleElementByXpath("//h3[text()='经销商查询']");
		getVisibleElementByXpath("//p[contains(text(),'您的位置')]");
		getVisibleElementByXpath("//button[contains(text(),' 确认提交')]");

		getVisibleElementByXpath("//h4[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKZ')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯大陆CONTINENTAL')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKC')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯MKX')]");
		getVisibleElementByXpath("//a[contains(text(),'林肯NAVIGATOR')]");

		getVisibleElementByXpath("//h4[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'林肯金融')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'车型对比')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'礼遇方案')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取经销商报价')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取车型手册')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'获取最新资讯')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'经销商网络')]");

		getVisibleElementByXpath("//h4[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@id='footer3']/li[1]/a[contains(text(),'林肯之道')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'林肯时刻')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'百年荣耀')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'林肯之道•云服务')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'品牌动向')]");
		getVisibleElementByXpath("//ul[@id='footer3']//a[contains(text(),'新闻中心')]");

		getVisibleElementByXpath("//h4[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'售后服务')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'车主手册')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'SYNC')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'My Lincoln Mobile')]");
		getVisibleElementByXpath("//ul[@id='footer4']//a[contains(text(),'创建我的林肯ID')]");
	}

	@And("^Click on links at footer on P1 Lincoln$")
	public void clickOnLinksAtFooter(DataTable footerLinks) throws Throwable {
		System.out.println("Click on links at footer");
		List<List<String>> data = footerLinks.raw();
		clickOnLinks(data.get(0).get(0));
		clickOnLinks(data.get(0).get(1));
		clickOnLinks(data.get(0).get(2));
		clickOnLinks(data.get(0).get(3));
		getVisibleElementByXpath("//div[@class='content clearfix']/div[@class='modal fade component-modal modal-remote style-form modal-standard register-form-db in']//span[@class='modal-close style-small hidden-xs']").click();
	}

	@And("^Click on link at footer on P1 Lincoln$")
	public void clickOnLinkAtFooter(DataTable footerLink) throws Throwable {
		List<List<String>> data = footerLink.raw();
		System.out.println("Click on " + data.get(0).get(0) + " link at footer");
		getVisibleElementByXpath("//ul[@id='footer2']//a[contains(text(),'" + data.get(0).get(0) + "')]").click();
	}

	@Then("^See all components on owner manual on P1 Lincoln$")
	public void seeAllComponentsOnOwnerManual() throws Throwable {
		System.out.println("See all components on owner manual");
		getVisibleElementByXpath("//div[@class='hidden-xs']//strong[contains(text(),'索取车型手册')]");
		getVisibleElementByXpath("//div[@class='hidden-xs']//p[contains(text(),'直接点击MKZ、林肯大陆CONTINENTAL、MKC、MKX 、NAVIGATOR，下载相应车型电子版产品手册。若想获取实体精美产品手册，请填写以下信息，我们将免费为你寄送。欢迎拨打服务专线：400-988-6789咨询。（＊号为必填项目）')]");
		getVisibleElementByXpath("//figcaption[contains(text(),'林肯 林肯大陆CONTINENTAL')]");
		getVisibleElementByXpath("//img[@alt='林肯大陆CONTINENTAL']");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'Download Owner’s Manual')]");
		getVisibleElementByXpath("//a[@id='dowloadButton']");
		getVisibleElementByXpath("//a[@id='mailForm']");
	}

	@When("^Click on Download Owner Manual on P1 Lincoln$")
	public void clickOnDownloadOwnerManual() throws Throwable {
		System.out.println("Click on Download Owner Manual");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'Download Owner’s Manual')]").click();
	}

	@And("^Click on Download button on P1 Lincoln$")
	public void clickOnDownloadButton() throws Throwable {
		System.out.println("Click on Download button");
		getVisibleElementByXpath("//a[@id='dowloadButton']").click();
	}

	@And("^Click on Mail From button on P1 Lincoln$")
	public void clickOnMailFromButton() throws Throwable {
		System.out.println("Click on Mail From button");
		getVisibleElementByXpath("//a[@id='mailForm']").click();
	}

	@Then("^See all components on mail from on P1 Lincoln$")
	public void seeAllComponentsOnMailFrom() throws Throwable {
		System.out.println("See all components on mail from");
		getVisibleElementByXpath("//div[@class='hidden-xs']//strong[contains(text(),'个人联系信息')]");
		getVisibleElementByXpath("//span[contains(text(),'请选择称呼 ')]");
		getVisibleElementByXpath("//select[@name='title']");
		getVisibleElementByXpath("//span[contains(text(),'姓名')]");
		getVisibleElementByXpath("//input[@name='lastName']");
		getVisibleElementByXpath("//input[@name='firstName']");
		getVisibleElementByXpath("//span[contains(text(),'电子邮箱')]");
		getVisibleElementByXpath("//input[@name='email']");
		getVisibleElementByXpath("//span[contains(text(),'手机号码 ')]");
		getVisibleElementByXpath("//input[@name='mobile']");
		getVisibleElementByXpath("//span[@class='title' and contains(text(),'省 ')]");
		getVisibleElementByXpath("//select[@name='state']");
		getVisibleElementByXpath("//span[@class='title' and contains(text(),'市')]");
		getVisibleElementByXpath("//select[@name='city']");
		getVisibleElementByXpath("//span[@class='title' and contains(text(),'地址')]");
		getVisibleElementByXpath("//input[@name='addressLine1']");
		getVisibleElementByXpath("//span[@class='title' and contains(text(),'邮编')]");
		getVisibleElementByXpath("//input[@name='postcode']");
		getVisibleElementByXpath("//div[@class='hidden-xs']//strong[contains(text(),'座驾信息')]");
		getVisibleElementByXpath("//span[@class='title' and contains(text(),'请选择偏好林肯车系')]");
		getVisibleElementByXpath("//select[@name='nameplate']");
		getVisibleElementByXpath("//span[@class='title' and contains(text(),'何时计划购买 ')]");
		getVisibleElementByXpath("//select[@name='inMarketDate']");
		getVisibleElementByXpath("//span[@class='title' and contains(text(),'您是否愿意被经销商联系')]");
		getVisibleElementByXpath("//div[@class='row']/div[1]/label[@class='control-input component-checkbox']/span[contains(@class,'checkbox-style')]");
		getVisibleElementByXpath("//div[@class='row']/div[1]/label[@class='control-input component-checkbox']/span[contains(text(),'是')]");
		getVisibleElementByXpath("//div[@class='row']/div[2]/label[@class='control-input component-checkbox']/span[contains(@class,'checkbox-style')]");
		getVisibleElementByXpath("//div[@class='row']/div[2]/label[@class='control-input component-checkbox']/span[contains(text(),'否 ')]");
		getVisibleElementByXpath("//label[contains(text(),'请输入验证码')]");
		getVisibleElementByXpath("//input[@id='captchaValue']");
		getVisibleElementByXpath("//div[@class='checkbox form-checkbox']/label[@class='control-input component-checkbox']/span[contains(@class,'checkbox-style')]");
		getVisibleElementByXpath("//div[@class='checkbox form-checkbox']/label[@class='control-input component-checkbox']//span[contains(text(),'我已阅读并同意相关个人隐私条款')]");
		getVisibleElementByXpath("//div[@class='parbase button']/button[contains(text(),' 确认提交')]");
	}

	@Then("^See all components on mkc overview on P1 Lincoln$")
	public void seeAllComponentOnMkcOverview() throws Throwable {
		System.out.println("See all components on mkc overview");
		getVisibleElementByXpath("//a[@class='navbar-brand logo']/img[@alt='Lincoln']");
		getVisibleElementByXpath("//span[contains(text(),'Lincoln MKC Video')]");
		getVisibleElementByXpath("//span[contains(text(),'View Full Gallery')]");
		getVisibleElementByXpath("//h2[contains(text(),'Find Your Nearest Dealer')]");
		getVisibleElementByXpath("//input[@id='state']");
	}

	@And("^Click on Gallery picture on P1 Lincoln$")
	public void clickOnGalleryPicture() throws Throwable {
		System.out.println("Click on Gallery Picture");
		getVisibleElementByXpath("//img[@alt='image 2']").click();
		getVisibleElementByXpath("//h2[contains(text(),'Colorizer')]");
	}

	@Then("^See gallery overlay on P1 Lincoln$")
	public void seeGalleryOverlay() throws Throwable {
		System.out.println("See gallery overlay");
		getVisibleElementByXpath("//div[@class='owl-item']//img[@src='/content/dam/lincoln-uat/sk1/gallery/Brand%20Gallery%203.jpg']");
		getVisibleElementByXpath("//a[@class='btn-icon btn-share js-download-image']");
		getVisibleElementByXpath("//div[@class='social-share-row']//a[@class='btn-icon btn-share last']");
		getVisibleElementByXpath("//span[@class='modal-close style-small hidden-xs']");
	}

	@When("^Click on Download on Gallery overlay on P1 Lincoln$")
	public void clickOnDownloadOnGalleryOverlay() throws Throwable {
		System.out.println("Click on Download on Gallery overlay");
		getVisibleElementByXpath("//a[@class='btn-icon btn-share js-download-image']").click();
	}

	@When("^Click on Share on Gallery overlay on P1 Lincoln$")
	public void clickOnShareOnGalleryOverlay() throws Throwable {
		System.out.println("Click on Share on Gallery overlay");
		getVisibleElementByXpath("//div[@class='social-share-row']//a[@class='btn-icon btn-share last']").click();
	}

	@When("^Click on Model Compare button on P1 Lincoln$")
	public void clickOnModelCompareButton() throws Throwable {
		System.out.println("Click on Model Compare button");
		getVisibleElementByXpath("//div[contains(@class,'hidden-xs')]/a[contains(text(),'配置对比')]").click();
	}

	@Then("^See all components on Model Compare page on P1 Lincoln$")
	public void seeAllComponentsOnModelCompagePage() throws Throwable {
		System.out.println("See all components on Model Compare page");
		getVisibleElementByXpath("//div[contains(@class,'hidden-xs')]/a[contains(text(),' 配置对比')]");
//		getVisibleElementByXpath("//span[text()='MKC 尊悦版']");
//		getVisibleElementByXpath("//span[text()='MKC 尊享版']");
//		getVisibleElementByXpath("//span[(contains(text(),'MKC 尊雅版') and contains(text(),'前驱')) or contains(text(),'MKC 尊雅版')]");
//		getVisibleElementByXpath("//span[(contains(text(),'MKC 尊雅版') and contains(text(),'全驱')) or contains(text(),'MKC 尊雅版')]");
//		getVisibleElementByXpath("//span[text()='MKC 尊耀版']");
//		Serenity.takeScreenshot();
	}

	@Then("^See all components on Model Compare page$")
	public void See_all_components_on_Model_Compare_page(DataTable args1) throws Throwable {
		System.out.println("See all components on Model Compare page");
		List<List<String>> datas = args1.raw();
		driver.navigate().refresh();
		waitTillElemVisiblebyXpath("//div[contains(@class,'hidden-xs')]/a[contains(text(),'비교 모델 선택') or contains(text(),'Active Compare')]", 240);
		getVisibleElementByXpath("//div[contains(@class,'hidden-xs')]/a[contains(text(),'비교 모델 선택') or contains(text(),'Active Compare')]");
		for (int data = 0; data < datas.size(); data++) {
			waitTillElemVisiblebyXpath("//span[contains(text(),'" + datas.get(0).get(data) + "')]", 240);
			getVisibleElementByXpath("//span[contains(text(),'" + datas.get(0).get(data) + "')]");
		}
	}

	@When("^Click any model image to trigger comparator$")
	public void Click_any_model_image_to_trigger_comparator(DataTable args1) throws Throwable {
		System.out.println("Click any model image to trigger comparator");
		List<List<String>> data = args1.raw();
		getVisibleElementByXpath("//img[@alt='" + data.get(0).get(0) + "']").click();
	}

	@Then("^Populate selected model specs in MC page$")
	public void Populate_selected_model_specs_in_MC_page(DataTable args1) throws Throwable {
		System.out.println("Populate selected model specs in MC page");
		List<List<String>> data = args1.raw();
		waitTillElemVisiblebyXpath("//h4[contains(text(),'" + data.get(0).get(0) + "')]", 240);
		getVisibleElementByXpath("//h4[contains(text(),'" + data.get(0).get(0) + "')]").click();
		if(!data.get(0).get(1).isEmpty()) {
			getVisibleElementByXpath("//h4[contains(text(),'" + data.get(0).get(1) + "')]").click();
		}	
	}

	@Then("^Can only add vehicle from models under this selected NP$")
	public void Can_only_add_vehicle_from_models_under_this_selected_NP(DataTable args1) throws Throwable {
		System.out.println("Can only add vehicle from models under this selected NP");
		List<List<String>> datas = args1.raw();
		getVisibleElementByXpath("//div[contains(@class,'modal-compare-form selectBoxWrapper2')]//a[contains(text(),'Add Vehicle') or contains(text(),'추가 선택')]").click();
		getVisibleElementByXpath("//button[@class='btn btn-small btn-solid bg-gold full-width' and contains(text(),'Confirm') or contains(text(),'확인')]");
		for (int data = 0; data < datas.size(); data++) {
			getVisibleElementByXpath("//*[text()='" + datas.get(0).get(data) + "']");
		}
	}

	@And("^Select model of third vehicle on Model Compare$")
	public void Select_model_of_third_vehicle_on_Model_Compare(DataTable args1) throws Throwable {
		System.out.println("Select model of third vehicle on Model Compare");
		List<List<String>> datas = args1.raw();
		// getVisibleElementByXpath("//a[@ng-click='showAddModelBtn(3)' and
		// contains(text(),'Add Vehicle')]").click();
		getVisibleElementByXpath("//button[@class='btn btn-small btn-solid bg-gold full-width' and contains(text(),'Confirm') or contains(text(),'확인')]");
		for (int data = 0; data < datas.size(); data++) {
			getVisibleElementByXpath("//*[text()='" + datas.get(0).get(data) + "']");
		}
		// List<WebElement> modelselect = driver
		// .findElements(By.xpath("//span[contains(@class,'checkbox-style
		// gux-icon-check')]"));
		// modelselect.get(2).click();

		try {
//			List<WebElement> modelselect = driver.findElements(By.xpath("//*[text()='" + datas.get(0).get(0) + "']//preceding::span[contains(@class,'checkbox-style gux-icon-check')]"));
//			modelselect.get(0).click();
			getVisibleElementByXpath("//input[not(contains(@checked,'checked'))]//following-sibling::span[contains(@class,'checkbox-style gux-icon-check-25px')]").click();
		} catch (Exception e) {
//			List<WebElement> modelselect = driver.findElements(By.xpath("//*[text()='" + datas.get(0).get(0) + "']//preceding::span[contains(@class,'checkbox-style gux-icon-check')]"));
//			modelselect.get(1).click();
			getVisibleElementByXpath("//input[not(contains(@checked,'checked'))]//following-sibling::span[contains(@class,'checkbox-style gux-icon-check-25px')]").click();
		}
	}

	@When("^Click on Select Another Models of MKC Select on P1 Lincoln$")
	public void clickOnSelectAnotherModelsOfMkcSelect() throws Throwable {
		System.out.println("Click on Select Another Models of MKC Select");
		getVisibleElementByXpath("//div[1]/div/div[1]/div/div/p[2]/a[contains(text(),'Select Another Models')]").click();
	}

	@Then("^See all components on Select Model overlay on P1 Lincoln$")
	public void seeAllComponentsOnSelectModelOverlay() throws Throwable {
		System.out.println("See all components on Select Model overlay");
		getVisibleElementByXpath("//h2[contains(text(),'Select Model')]");
		getVisibleElementByXpath("//h3[contains(text(),'MKC Presidential')]");
		getVisibleElementByXpath("//button[contains(text(),'Confirm Button')]");
		getVisibleElementByXpath("//a[contains(text(),'Cancel Button')]");
	}

	@When("^Select MKC Presidential on Select Model overlay on P1 Lincoln$")
	public void selectMkcPresidentialOnSelectModelOverlay() throws Throwable {
		System.out.println("Select MKC Presidential on Select Model overlay");
		getVisibleElementByXpath("//div[4]/div[2]/label/span[contains(@class,'checkbox')]").click();
	}

	@And("^Click Confirm on Select Model overlay on P1 Lincoln$")
	public void clickConfirmOnSelectModelOverlay() throws Throwable {
		System.out.println("Click Confirm on Select Model overlay");
		getVisibleElementByXpath("//button[@ng-show='modelBtn1' and contains(text(),'Confirm Button')]");
	}

	@Then("^See the model compare page is updated on P1 Lincoln$")
	public void seeTheModelCompagePageIsUpdated() throws Throwable {
		System.out.println("See the model compare page is updated");
		getVisibleElementByXpath("//div[contains(@class,'select-compare-vehicle')]//h4[contains(text(),'MKC Presidential')]");
		verifyDontSeeElementVisible("//div[contains(@class,'select-compare-vehicle')]//h4[contains(text(),'MKC Select')]");
	}

	@When("^Fill in the contact form on P1 Lincoln$")
	public void fillInTheContactForm() throws Throwable {
		System.out.println("Fill in the contact form");

	}

	@Then("^See all components on LAD page on P1 Lincoln$")
	public void seeAllComponentsOnLadPage() throws Throwable {
		System.out.println("See all components on LAD page");
		getVisibleElementByXpath("//div[@class='panel-lock' and contains(text(),' 经销商查询')]");
		getVisibleElementByXpath("//span[contains(text(),'你的地点')]");
		getVisibleElementByXpath("//span[contains(text(),'经销商名称')]");
		getVisibleElementByXpath("//button[contains(text(),'搜索经销商')]");
	}

	@When("^Click on Select province on LAD page on P1 Lincoln$")
	public void clickOnSelectProvinceOnLadPage() throws Throwable {
		System.out.println("Click on Select province on LAD page");
		getVisibleElementByXpath("//select[contains(@class,'select-province')]").click();
	}

	@Then("^See all provinces in Select province on P1 Lincoln$")
	public void seeAllProvincesInSelectProvince() throws Throwable {
		System.out.println("See all provinces in Select province");
		getVisibleElementByXpath("//select[contains(@class,'select-province')]/option[text()='上海']");
		getVisibleElementByXpath("//select[contains(@class,'select-province')]/option[text()='云南']");
	}

	@And("^Select a province in Select province on P1 Lincoln$")
	public void selectProvinceInSelectProvince() throws Throwable {
		System.out.println("Select a province in Select province");
		getVisibleElementByXpath("//select[contains(@class,'select-province')]/option[text()='上海']").click();
	}

	@When("^Click on Select city on LAD page on P1 Lincoln$")
	public void clickOnSelectCityOnLadPage() throws Throwable {
		System.out.println("Click on Select city on LAD page");
		getVisibleElementByXpath("//select[contains(@class,'select-city')]").click();
	}

	@Then("^See all cities in Select city on P1 Lincoln$")
	public void seeAllCitiesInSelectCity() throws Throwable {
		System.out.println("See all cities in Select city");
		getVisibleElementByXpath("//select[contains(@class,'select-city')]/option[text()='上海']");
	}

	@And("^Select a city in Select city on P1 Lincoln$")
	public void selectCityInSelectCity() throws Throwable {
		System.out.println("Select a city in Select city");
		getVisibleElementByXpath("//select[contains(@class,'select-city')]/option[text()='上海']").click();
	}

	@When("^Click on Search button on LAD page on P1 Lincoln$")
	public void clickOnSearchButtonOnLadPage() throws Throwable {
		System.out.println("Click on Search button on LAD page");
		getVisibleElementByXpath("//button[contains(text(),'搜索经销商')]").click();
	}

	@When("^Click on Video in MKZ page on P1 Lincoln$")
	public void clickOnVideoInMkzPage() throws Throwable {
		System.out.println("Click on Video in MKZ page");
		waitTillElemVisiblebyXpath("//div[contains(@class,'billboard-carousel carousel owl-oneitem')]//div[@class='hidden-xs']//span[@class='lincoln-icon_play-thin']", 240);
		getVisibleElementByXpath("//div[contains(@class,'billboard-carousel carousel owl-oneitem')]//div[@class='hidden-xs']//span[@class='lincoln-icon_play-thin']").click();
	}

	@Then("^See all components in Video overlay on P1 Lincoln$")
	public void seeAllComponentsInVideoOverlay() throws Throwable {
		System.out.println("See all components in Video overlay");
		getVisibleElementByXpath("//button[@class='vjs-big-play-button']");
		getVisibleElementByXpath("//span[@class='lincoln-icon_share-thin']");
		getVisibleElementByXpath("//i[@class='lincoln-icon_close-thin']");
	}

	@When("^Play video in Video overlay on P1 Lincoln$")
	public void playVideoInVideoOverlay() throws Throwable {
		System.out.println("Play video in Video overlay");
		getVisibleElementByXpath("//button[@class='vjs-big-play-button']").click();
	}

	@Then("^Verify do not see Video Play button on P1 Lincoln$")
	public void verifyDonotSeeVideoPlayButton() throws Throwable {
		System.out.println("Verify do not see Video Play button");
		verifyDontSeeElementVisible("//button[@class='vjs-big-play-button']");
	}

	@Then("^See all components on Press Release page on P1 Lincoln$")
	public void seeAllComponentsOnPressReleasePage() throws Throwable {
		System.out.println("See all components on Press Release page");
		getVisibleElementByXpath("//a[@class='navbar-brand logo']/img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'预约试驾')]");
		// getVisibleElementByXpath("//span[text()='新闻中心']");
		// getVisibleElementByXpath("//p[text()='没有结果']");
		getVisibleElementByXpath("//button[contains(text(),'选择排序')]");
	}

	@When("^Click on Select Article on Press Release page on P1 Lincoln$")
	public void clickOnSelectArticleOnPressReleasePage() throws Throwable {
		System.out.println("Click on Select Article on Press Release page");
		// List<List<String>> data = parameter.raw();
		waitTillElemVisiblebyXpath("//button[contains(text(),'选择排序')]", 240);
		getVisibleElementByXpath("//button[contains(text(),'选择排序')]").click();
		Thread.sleep(3000);
		// getVisibleElementByXpath("//a[text()='"+data.get(0).get(0)+"']").click();
	}

	@When("^Select Year on Select Article dropdown list on P1 Lincoln$")
	public void selectYearOnSelectAtticleDropdownList(DataTable parameter) throws Throwable {
		System.out.println("Select Year on Select Article dropdown list");
		List<List<String>> data = parameter.raw();
		try {
			getVisibleElementByXpath("//a[text()='" + data.get(0).get(0) + "']").click();
		} catch (Exception e) {
			getVisibleElementByXpath("//button[@data-toggle='dropdown']").click();
			getVisibleElementByXpath("//a[text()='" + data.get(0).get(0) + "']").click();
		}
	}

	@Then("^See all articles in this year on Press Release page on P1 Lincoln$")
	public void seeAllArticlesInThisYearOnPressReleasePage(DataTable parameter) throws Throwable {
		System.out.println("See all articles in this year on Press Release page");
		Thread.sleep(10000);
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//span[text()='新闻中心']");
	
		getVisibleElementByXpath("//h2[text()='" + data.get(0).get(0) + "']");		
//		for (int i = 1; i < 4; i++) {
//			getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(i) + "')]");
//		}

		List<WebElement> findArticals=driver.findElements(By.xpath("//div[contains(@class,'list-body')]//*[@class='title']//a"));
		if(findArticals.size()<=0) {
			Assert.assertFalse("Press Release Articles are displayed properly", true);
		}
		moveToElement(findArticals.get(0));
		getVisibleElementByXpath("//a[contains(text(),' 了解更多')]");
	}

	@When("^Click an article on Press Release page on P1 Lincoln$")
	public void clickAnArticleOnPressReleasePage(DataTable parameter) throws Throwable {
		System.out.println("Click an article on Press Release page");
		List<List<String>> data = parameter.raw();
		List<WebElement> findArticals=driver.findElements(By.xpath("//div[contains(@class,'list-body')]//*[@class='title']//a"));
//		if(findArticals.size()<=0) {
//			Assert.assertFalse("Press Release Articles are displayed properly", true);
//		}
		moveToElement(findArticals.get(0));
		findArticals.get(0).click();
//		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(0) + "')]").click();
	}

	@When("^Click on Sort by and select year on Press Release page on P1 Lincoln$")
	public void clickOnSortByOnPressRelasePage() throws Throwable {
		System.out.println("Click on Sort by on Press Release page");
		getVisibleElementByXpath("//button[contains(text(),'选择排序')]").click();
		Thread.sleep(3000);
		getVisibleElementByXpath("//a[text()='2017']").click();
	}

	@Then("^See all components on the year Press Release page on P1 Lincoln$")
	public void seeAllComponentsOnTheYearPressReleasePage() throws Throwable {
		System.out.println("See all components on the year Press Release page");
		getVisibleElementByXpath("//a[@class='navbar-brand logo']/img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//span[text()='新闻中心']");
		getVisibleElementByXpath("//h2[text()='2017']");
		getVisibleElementByXpath("//button[contains(text(),'2017')]");
	}

	@Then("^See all components on Article page on P1 Lincoln$")
	public void seeAllComponentsOnArticlePage() throws Throwable {
		System.out.println("See all components on Article page");
		getVisibleElementByXpath("//a[@class='navbar-brand logo']/img[@alt='Lincoln']");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'林肯车型')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'购车服务')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'走近林肯')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'车主专区')]");
		getVisibleElementByXpath("//ul[@class='menu-list navbar-right']//a[contains(text(),'预约试驾')]");
		getVisibleElementByXpath("//h2[text()='新款林肯MKZ H混合动力与全新林肯Navigator概念车亮相2017上海车展，以“静谧的豪华”定义未来']");
		getVisibleElementByXpath("//p[text()='2017/04/19']");
		getVisibleElementByXpath("//div[@class='hidden-xs']//img[@src='/content/dam/lincoln/cn/l_cn_zh/homepage/experience-lincoln/press-release/1249259819698.jpeg']");
		getVisibleElementByXpath("//p[text()='王旖旎']");
		getVisibleElementByXpath("//p[text()='联系电话：+86.21.38581833']");
		getVisibleElementByXpath("//p[text()='电子邮件：ywang254@lincoln.com']");
		getVisibleElementByXpath("//p[text()='地址：上海浦东新区世纪大道211号上海信息大厦35楼 200120']");
		getVisibleElementByXpath("//p[text()='徐赛赛']");
		getVisibleElementByXpath("//a[text()='sxu19@lincoln.com']");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'返回上层')]");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_pdf']");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_share-thin']");
	}

	@When("^Click on Share button on Article page on P1 Lincoln$")
	public void clickOnShareButtonOnArticlePage() throws Throwable {
		System.out.println("Click on Share button on Article page");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_share-thin']").click();
	}

	@When("^Click on Download button on Article page on P1 Lincoln$")
	public void clickOnDownloadButtonOnArticlePage() throws Throwable {
		System.out.println("Click on Download button on Article page");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_pdf']").click();
	}

	@When("^Click on Return to top button on Article page on P1 Lincoln$")
	public void clickOnReturnToTopButtonOnArticlePage() throws Throwable {
		System.out.println("Click on Return to top button on Article page");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[contains(text(),'返回上层')]").click();
	}

	@When("^Click on interior 360 in MKZ page on P1 Lincoln$")
	public void clickOnInterior360InMkzPage() throws Throwable {
		System.out.println("Click on interior 360 in MKZ page");
		getVisibleElementByXpath("//a[text()='内饰360º']").click();
	}

	@Then("^See interior 360 image in MKZ page on P1 Lincoln$")
	public void seeInterior360ImageInMkzPage() throws Throwable {
		System.out.println("See interior 360 image in MKZ page");
		getVisibleElementByXpath("//div[contains(@class,'active')]//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/interior/曜岩黑座椅/1.jpg']");
//		getVisibleElementByXpath("//span[text()='曜岩黑']");
		// getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/interior/曜岩黑座椅/btn-yao%20yan%20black-曜岩黑.jpg']");
		// getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/interior/赤陶棕座椅/btn-red%20tan%20brown%20seat-赤陶棕.jpg']");
		// getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/interior/卡布奇诺色座椅/btn-cappuccino-卡布奇诺.jpg']");
		// getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/interior/翡墨灰座椅/btn-feixi%20gray%20seat-翡墨灰.jpg']");

		// String xpath2 = "img";
		// for(int i=1; i<=4; i++){
		// String xpath1 = "//div[@id='collapseFive']/div/div/div[2]/ul/li["+i+"]/";
		// String xpath = xpath1.concat(xpath2);
		// getVisibleElementByXpath(xpath);
		// }
	}

	@When("^Click on brown image on interior 360 in MKZ page on P1 Lincoln$")
	public void clickOnBrownImageOnInterior360InMkzPage() throws Throwable {
		System.out.println("Click on brown image on interior 360 in MKZ page");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/interior/赤陶棕座椅/btn-red%20tan%20brown%20seat-赤陶棕.jpg']").click();
	}

	@Then("^See brown image on interior 360 in MKZ page on P1 Lincoln$")
	public void seeBrownImageOnInterior360InMkzPage() throws Throwable {
		System.out.println("See brown image on interior 360 in MKZ page");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/interior/赤陶棕座椅/1.jpg']");
	}

	@When("^Click on exterior 360 in MKZ page on P1 Lincoln$")
	public void clickOnExterior360InMkzPage() throws Throwable {
		System.out.println("Click on exterior 360 in MKZ page");
		getVisibleElementByXpath("//a[text()='外观360º']").click();
	}

	@Then("^See exterior 360 image in MKZ page on P1 Lincoln$")
	public void seeExterior360ImageInMkzPage() throws Throwable {
		System.out.println("See exterior 360 image in MKZ page");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/勃艮第红/1.jpg']");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/勃艮第红/btn-burgundy-red-勃艮第红.jpg']");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/午夜蓝/btn-midnight-blue-午夜蓝.jpg']");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/天鹅黑/btn-swan-black-天鹅黑.jpg']");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/翡墨灰/btn-fei-ink-gray-翡墨灰.jpg']");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/铂钻白/btn-platinum-diamond-white-铂钻白.jpg']");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/星河银/btn-galaxy-silver-星河银.jpg']");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/宝石红/btn-gem-red-宝石红.jpg']");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/苍穹灰/btn-the-sky-is-gray-苍穹灰.jpg']");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/雅典金/btn-athens-gold-雅典金.jpg']");

		// String xpath2 = "img";
		// for(int i=1; i<=9; i++){
		// String xpath1 = "//div[@id='collapseFour']/div/div/div[2]/ul/li["+i+"]/";
		// String xpath = xpath1.concat(xpath2);
		// getVisibleElementByXpath(xpath);
		// }
	}

	@When("^Click on midnight blue on exterior 360 in MKZ page on P1 Lincoln$")
	public void clickOnMidnightBlueOnExterior360InMkzPage() throws Throwable {
		System.out.println("Click on midnight blue on exterior 360 in MKZ page");
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/午夜蓝/btn-midnight-blue-午夜蓝.jpg']").click();
	}

	@Then("^See midnight blue image on exterior 360 in MKZ page on P1 Lincoln$")
	public void seeMidnightBlueImageOnExterior360InMkzPage() throws Throwable {
		System.out.println("See midnight blue image on exterior 360 in MKZ page");
		waitTillElemVisiblebyXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/午夜蓝/1.jpg']", 240);
		getVisibleElementByXpath("//img[@src='/content/dam/lincoln/cn/l_cn_zh/nameplate/mkz/360/exterior/午夜蓝/1.jpg']");
	}

	@Then("^See all components on MKC Gallery page in P1 INT Lincoln$")
	public void seeAllComponentsOnMkcGalleryPage(DataTable parameter) throws Throwable {
		System.out.println("See all components on MKC Gallery page");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//span[@class='secondary-menu-name' and text()='Lincoln MKC']");
		getVisibleElementByXpath("//span[@class='secondary-navi-title hidden-xs' and contains(text(),'Gallery')]");

		for (int i = 0; i <= 2; i++) {
			getVisibleElementByXpath("//div[@class='hidden-xs']//strong[text()='" + data.get(0).get(i) + "']");
		}

		for (int j = 3; j <= 16; j++) {
			getVisibleElementByXpath("//figure/img[@alt='" + data.get(0).get(j) + "']");
		}

		getVisibleElementByXpath("//img[@alt='video 1']");
	}

	@When("^Click on any image on Exterior section in P1 INT Lincoln$")
	public void clickOnAnyImageOnExteriorSection(DataTable parameter) throws Throwable {
		System.out.println("Click on any image on Exterior section");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//figure/img[@alt='" + data.get(0).get(0) + "']").click();
	}

	@Then("^See all components on Exterior overlay in P1 INT Lincoln$")
	public void seeAllComponentsOnExteriorOverlay() throws Throwable {
		System.out.println("See all components on Exterior overlay");

	}

	@Then("^See all components on Finance Overview page on P1 Lincoln$")
	public void seeAllComponentsOnFinanceOverviewPage() throws Throwable {
		System.out.println("See all components on Finance Overview page");
		getVisibleElementByXpath("//span[@class='secondary-menu-name' and contains(text(),'세일즈 프로모션')]");
		getVisibleElementByXpath("//span[@class='secondary-navi-title hidden-xs' and contains(text(),'링컨 파이낸셜 서비스')]");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[text()='브로셔 다운로드']");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[text()='시승 신청']");
		getVisibleElementByXpath("//div[@class='hidden-xs']//a[text()='링컨 파이낸셜 서비스 바로가기']");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_share-thin']");
		getVisibleElementByXpath("//div[contains(text(),'DISCLOSURES')]");
	}

	@When("^Click on Brochures on second navigation on P1 Lincoln$")
	public void clickOnBrochuresOnSecondNavigation() throws Throwable {
		System.out.println("Click on Brochures on second navigation");
		getVisibleElementByXpath("//ul[@class='secondary-side-navi pull-right']//a[text()='브로셔 다운로드']").click();
	}

	@Then("^See all components on Korean Brochures overlay on P1 Lincoln$")
	public void seeAllComponentsOnKoreanBrochuresOverlay(DataTable parameter) throws Throwable {
		System.out.println("See all components on Korean Brochures overlay");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[text()='브로셔 다운로드']");
		for (int i = 0; i <= 3; i++) {
			getVisibleElementByXpath("//div[@class='owl-item active']//figcaption[text()='" + data.get(0).get(i) + "']");
			getVisibleElementByXpath("//div[@class='owl-item active']//img[@alt='" + data.get(0).get(i) + "']");
		}
		getVisibleElementByXpath("//a[@id='dowloadButton' and contains(text(),'다운로드')]");
	}

	@When("^Click on Campaign from header menu$")
	public void click_on_Campaign_from_header_menu() throws Throwable {
		System.out.println("Click on Campaign from header menu");
		getVisibleElementByXpath("//a[contains(@class,'link-overlay') and text()='Campaign']").click();
	}

	@When("^Click on submit$")
	public void click_on_submit() throws Throwable {
		System.out.println("Click on submit");
		getVisibleElementByXpath("//button[(@class='btn  btn-solid bg-gold  form-btn-full' or contains(@class,'change-password')) and @type='submit']").click();
	}

	@Then("^validation message is displayed$")
	public void validation_message_is_displayed(DataTable parameter) throws Throwable {
		System.out.println("Validation of messages.");
		List<List<String>> data = parameter.raw();
		if (!data.get(0).get(0).isEmpty()) {
			getVisibleElementByXpath("//li[text()='" + data.get(0).get(0) + "']");
		}

		if (!data.get(0).get(1).isEmpty()) {
			getVisibleElementByXpath("//li[text()='" + data.get(0).get(1) + "']");
		}

		if (!data.get(0).get(2).isEmpty()) {
			getVisibleElementByXpath("//li[text()='" + data.get(0).get(2) + "']");
		}

		if (!data.get(0).get(3).isEmpty()) {
			getVisibleElementByXpath("//li[text()='" + data.get(0).get(3) + "']");
		}

		if (!data.get(0).get(4).isEmpty()) {
			getVisibleElementByXpath("//li[text()='" + data.get(0).get(4) + "']");
		}

		if (!data.get(0).get(5).isEmpty()) {
			getVisibleElementByXpath("//li[text()='" + data.get(0).get(5) + "']");
		}

		if (!data.get(0).get(6).isEmpty()) {
			getVisibleElementByXpath("//li[text()='" + data.get(0).get(6) + "']");
		}
	}

	@When("^On overlay, enter invalid values in fields$")
	public void on_overlay_enter_invalid_values_in_fields(DataTable arg1) throws Throwable {
		List<List<String>> data = arg1.raw();
		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='email']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='mobile']").sendKeys(data.get(0).get(3));
	}

	@When("^On overlay, input all items and refresh the captcha, then enter incorrect captcha$")
	public void on_overlay_input_all_items_and_refresh_the_captcha_then_enter_incorrect_captcha(DataTable arg1) throws Throwable {

	}

	@When("^On overlay, enter all items correctly$")
	public void on_overlay_enter_all_items_correctly(DataTable arg1) throws Throwable {
		List<List<String>> data = arg1.raw();
		getVisibleElementByXpath("//input[@name='firstName']").clear();
		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='lastName']").clear();
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='email']").clear();
		getVisibleElementByXpath("//input[@name='email']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='mobile']").clear();
		getVisibleElementByXpath("//input[@name='mobile']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//label[contains(text(),'" + data.get(0).get(4) + "')]//child::span").click();
		// getVisibleElementByXpath("//input[@name='captchaValue']").clear();
		// getVisibleElementByXpath("//input[@name='captchaValue']").sendKeys(data.get(0).get(5));
		getVisibleElementByXpath("//span[text()='我已阅读并同意相关个人隐私条款' or text()='Agree to the terms of Privacy Policy']//ancestor::span//following-sibling::span[contains(@class,'checkbox-style')]").click();
	}

	@Then("^Thank you page is seen and close overlay$")
	public void thank_you_page_is_seen() throws Throwable {
		getVisibleElementByXpath("//div[@class='hidden-xs']/h2[contains(text(),'衷心感谢')]").click();
		getVisibleElementByXpath("//i[@class='lincoln-icon_close-thin']").click();
	}


	@When("^Input text to Search and click Search$")
	public void inputTxtAndSearch(DataTable position) throws Throwable {
		System.out.println("Input text to Search Input and click Search");
		getVisibleElementByXpath("//input[@id='state']").clear();
		Thread.sleep(2000);
		getVisibleElementByXpath("//input[@id='state']").sendKeys(position.raw().get(0).get(0));
		getVisibleElementByXpath("//*[contains(@class,'locate-a-dealer')]//*[contains(@class,'lincoln-icon_search')]").click();
	}
	
	
	@When("^Appropriate validation message displayed$")
	public void Appropriate_validation_message_displayed() throws Throwable {
		System.out.println("Appropriate validation message displayed");
		Thread.sleep(2000);
		getVisibleElementByXpath("//div[contains(@ng-show,'displayError') or contains(@data-ng-show,'displayError') and not(contains(@class,'hide'))]");
	}
	
	
	@Then("^Dealer should be searched and shown on the page$")
	public void Dealer_should_be_searched_and_shown_on_the_page() throws Throwable {
		System.out.println("Dealer should be searched and shown on the page");
		Thread.sleep(2000);
		getVisibleElementByXpath("//div[contains(@class,'mini-locate-a-dealer-result') and not(contains(@class,'ng-hide'))]//*[contains(@class,'lincoln-icon_add')]");
		getVisibleElementByXpath("//div[contains(@class,'mini-locate-a-dealer-result') and not(contains(@class,'ng-hide'))]//*[contains(@data-ng-click,'openDealerInfo') or contains(@data-ng-click,'getAddress') and text()!='']");
		
	}
	
	@When("^Click on \"([^\"]*)\"$")
	public void click_on(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		getVisibleElementByXpath("//div[contains(@class,'mini-locate-a-dealer-result') and not(contains(@class,'ng-hide'))]//*[contains(@class,'lincoln-icon_add')]").click();
	}

	@Then("^Dealer section should be expanded and dealers should be displayed$")
	public void dealer_section_should_be_expanded_and_dealers_should_be_displayed() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Dealer section should be expanded and dealers should be displayed");
		getVisibleElementByXpath("//div[contains(@class,'expandable-result') and contains(@style,'display') and not(contains(@style,'none'))]//a[contains(@class,'titleLink')]");
		getVisibleElementByXpath("//div[contains(@class,'expandable-result') and contains(@style,'display') and not(contains(@style,'none'))]//*[text()='1']");
	}

	@Then("^Check for an option of 5 dealers display based on the nearest location criteria$")
	public void Check_for_an_option_of_5_dealers_display_based_on_the_nearest_location_criteria() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Check for an option of 5 dealers display based on the nearest location criteria");
		int locCount=driver.findElements(By.cssSelector("span[data-number],div[data-number]")).size();
		Verify.verify(locCount>=5, "only "+locCount+" showrooms(expected is 5) are currently being shown");
	}
	
	@Then("^Dealer section should be compressed and First dealer should be shown$")
	public void dealer_section_should_be_compressed_and_First_dealer_should_be_shown() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		verifyInvisibleElem("//div[contains(@class,'expandable-result') and contains(@style,'display') and not(contains(@style,'none'))]//a[contains(@class,'titleLink')]");
		verifyInvisibleElem("//div[contains(@class,'expandable-result') and contains(@style,'display') and not(contains(@style,'none'))]//*[text()='1']");
		getVisibleElementByXpath("//div[contains(@class,'mini-locate-a-dealer-result') and not(contains(@class,'ng-hide'))]//*[contains(@data-ng-click,'openDealerInfo') or contains(@data-ng-click,'getAddress') and text()!='']"); //Your nearest dealer - 1st Dealer search
	}

}
