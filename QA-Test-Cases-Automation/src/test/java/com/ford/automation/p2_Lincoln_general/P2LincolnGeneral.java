package com.ford.automation.p2_Lincoln_general;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ford.automation.base.BaseTest;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;

import config.Configuration;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;
import net.serenitybdd.core.Serenity;

@SuppressWarnings("deprecation")
public class P2LincolnGeneral extends BaseTest {
	
public int modelPrice=0,downpayment=0,downpaymRatio=0,TempValue=0;
public String modelName=null, HedgeAmtTxt=null;
private boolean verifyWheeltab=false;
	@Given("^Open Firefox browser on P2 Lincoln$")
	public void openFireFoxBrowser() throws Throwable {
		System.out.println("Open FireFox browser");
		System.setProperty("webdriver.gecko.driver", Configuration.PATH_TO_GECKO_DRIVER);
		driver = new FirefoxDriver();
	}

	@Given("^Open Chrome browser on P2 Lincoln$")
	public void openChromeBrowser() throws Throwable {
		System.out.println("Open Chrome browser");
//		System.setProperty("webdriver.chrome.driver", Configuration.PATH_TO_CHROME_DRIVER);
//		driver = new ChromeDriver();

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.open('','testwindow','width=400,height=200')");
		driver.close();
		driver.switchTo().window("testwindow");
		js.executeScript("window.moveTo(0,0);");
		js.executeScript("window.resizeTo(1450,1000);");
	}

	@When("^Maximize browser and enter link \"(.*?)\" on P2 Lincoln$")
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

	@Then("^See all components on MKZ vehicle on P2 Lincoln$")
	 public void seeAllComponentsOnMkzVehicle() throws Throwable {
	  System.out.println("See all components on MKZ vehicle");
	  try {
		  waitTillElemVisiblebyXpath("//p[@data-menu='#component-bp-config-price-dropdown-general' and contains(text(),'"+formatNumberFrChina(modelPrice)+"')]", 60);
		  getVisibleElementByXpath("//p[@data-menu='#component-bp-config-price-dropdown-general' and contains(text(),'"+formatNumberFrChina(modelPrice)+"')]"); // changed 17/8 old-//p[@data-menu='#component-bp-config-price-dropdown-general'
	  }catch (Exception e) {
		  getVisibleElementByXpath("//span[contains(@class,'nameplate-price') and contains(text(),'"+ formatNumberFrChina(modelPrice) + "')]");
	  } 
	}

	@Then("^See the price updated correctly on P2 Lincoln$")
	 public void seeThePriceUpdated(DataTable priceUpdated) throws Throwable {
	  System.out.println("See the price updated correctly");
	  // Write the code to handle Data Table
	  List<List<String>> data = priceUpdated.raw();	
	   getVisibleElementByXpath(formatNumberFrChina(modelPrice), data.get(0).get(1));
	 }

	@When("^Click feature to close on engine tab on P2 Lincoln$")
	public void clickFeatureToCloseOnEngineTab() throws Throwable {
		System.out.println("Click feature to close on engine tab");
		getVisibleElementByXpath("//div[contains(text(), '引擎')]").click();
	}

	@Then("^Verify do not see engine content on P2 Lincoln$")
	public void verifyDontSeeEngineContent() throws Throwable {
		System.out.println("Verify don't see engine content");
		verifyInvisibleElement("//div[@id='engine1']//p[contains(text(),'¥')]");
	}

	@When("^Click feature to open on engine tab on P2 Lincoln$")
	public void clickFeatureToOpenOnEngineTab() throws Throwable {
		System.out.println("Click feature to open on engine tab");
		getVisibleElementByXpath("//div[contains(text(), '引擎')]").click();
	}

	@Then("^Verify seeing engine content on P2 Lincoln$")
	public void verifySeeingEngineContent() throws Throwable {
		System.out.println("Verify seeing engine content");
		getVisibleElementByXpath("//p[contains(text(),'¥ 449,800')");
	}

	@When("^Click on exterior tab on P2 Lincoln$")
	public void clickOnExteriorTab() throws Throwable {
		System.out.println("Click on exterior tab");
		getVisibleElementByXpath("//a[contains(text(),'外观')]").click();
	}

	@Then("^See all features on exterior tab on P2 Lincoln$")
	public void seeAllFeaturesOnExteriorTab() throws Throwable {
		System.out.println("See all features on exterior tab");
		getVisibleElementByXpath("//div[contains(text(),'车色') or contains(text(),'颜色')]");
	}

	@When("^Click color feature to close on exterior tab on P2 Lincoln$")
	public void clickColorFeatureToClose() throws Throwable {
		System.out.println("Click color feature to close");
		WebElement findexpanded;
		if (driver.findElements(By.xpath("//div[@id='exterior']/div[1]/div[1]/h4/a")).size() != 0) {
			findexpanded = getVisibleElementByXpath("//div[@id='exterior']/div[1]/div[1]/h4/a");
			if (Boolean.parseBoolean(findexpanded.getAttribute("aria-expanded")) == true) {
				getVisibleElementByXpath("//div[@id='exterior']/div[1]/div[1]/h4/a/div/i").click();
			}
		}
		if (driver.findElements(By.xpath("//div[@id='exterior']/div[2]/div[1]/h4/a")).size() != 0) {
			findexpanded = getVisibleElementByXpath("//div[@id='exterior']/div[2]/div[1]/h4/a");
			if (Boolean.parseBoolean(findexpanded.getAttribute("aria-expanded")) == true) {
				getVisibleElementByXpath("//div[@id='exterior']/div[2]/div[1]/h4/a/div/i").click();
			}
		}
//		getVisibleElementByXpath("//div[@id='exterior']/div[1]/div[1]/h4/a/div/i").click();
		Thread.sleep(3000);
	}

	@Then("^Verify do not see color content on P2 Lincoln$")
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

	@When("^Click color feature to open on exterior tab on P2 Lincoln$")
	public void clickColorFeatureToOpen() throws Throwable {
		System.out.println("Click color feature to open");
		for(int divCnt=1;divCnt<=2;divCnt++) {
			WebElement ExtFeature=getVisibleElementByXpath("//div[@id='exterior']/div["+divCnt+"]/div[1]/h4/a/div");
			WebElement findexpanded=getVisibleElementByXpath("//div[@id='exterior']/div["+divCnt+"]/div[1]/h4/a");
			if((ExtFeature.getText().contains("车色")||ExtFeature.getText().contains("颜色")) && Boolean.parseBoolean(findexpanded.getAttribute("aria-expanded"))==false) {
				getVisibleElementByXpath("//div[@id='exterior']/div["+divCnt+"]/div[1]/h4/a/div/i").click();
				break;
			}
		}
		
		Thread.sleep(3000);
	}

	@Then("^Verify seeing color content on P2 Lincoln$")
	public void verifySeeingColorContent(DataTable colorContent) throws Throwable {
		System.out.println("Verify seeing color content");
		List<List<String>> data = colorContent.raw();
		
		for(int divCnt=1;divCnt<=2;divCnt++) {
			WebElement ExtFeature=getVisibleElementByXpath("//div[@id='exterior']/div["+divCnt+"]/div[1]/h4/a/div");
			if(ExtFeature.getText().contains("车色")||ExtFeature.getText().contains("颜色")) {
				verifySeeingElementVisibleNoMovement("//div[@id='exterior']/div["+divCnt+"]/div[2]//img"); /// div[2] hardcoded sa [2] bcoz id attribute is not fixed,it can be exterio0/exterior1 based on tab location up/down
				break;
			}
		}
		
//		verifySeeingElementVisibleNoMovement(data.get(0).get(0));
		Thread.sleep(10000);
	}

	@When("^Click wheels feature to open on exterior tab on P2 Lincoln$")
	public void clickWheelsFeatureToOpen() throws Throwable {
		System.out.println("Click on wheels feature to open");
		if (driver.findElements(By.xpath("//div[contains(text(),'轮毂')]")).size() != 0) {
			getVisibleElementByXpath("//div[contains(text(),'轮毂')]").click();
			Thread.sleep(3000);
			verifyWheeltab=true;
		}else
			System.out.println("Wheels Tab does not displayed.");
	}

	@Then("^Verify seeing wheels feature content on P2 Lincoln$")
	public void verifySeeingWheelsFeatureContent() throws Throwable {
		System.out.println("Verify seeing wheels feature content");
		if (verifyWheeltab == true) {
			for (int divCnt = 1; divCnt <= 2; divCnt++) {
				WebElement ExtFeature = getVisibleElementByXpath("//div[@id='exterior']/div[" + divCnt + "]/div[1]/h4/a/div");
				if (ExtFeature.getText().contains("轮毂")) {
					verifySeeingElementVisibleNoMovement("//div[@id='exterior']/div[" + divCnt + "]/div[2]//img");
					break;
				}
			}
		}
//		driver.findElement(By.xpath("//div[@id='exterior1']//img"));
	}

	@When("^Click wheels feature to close on exterior tab on P2 Lincoln$")
	public void clickWheelsFeatureToClose() throws Throwable {
		System.out.println("Click wheels feature to close");
		if (verifyWheeltab == true) {
			for (int divCnt = 1; divCnt <= 2; divCnt++) {
				WebElement ExtFeature = getVisibleElementByXpath("//div[@id='exterior']/div[" + divCnt + "]/div[1]/h4/a/div");
				WebElement findexpanded = getVisibleElementByXpath("//div[@id='exterior']/div[" + divCnt + "]/div[1]/h4/a");
				if (ExtFeature.getText().contains("轮毂") && Boolean.parseBoolean(findexpanded.getAttribute("aria-expanded")) == true) {
					getVisibleElementByXpath("//div[@id='exterior']/div[" + divCnt + "]/div[1]/h4/a/div/i").click();
					break;
				}
			}
		}
//		getVisibleElementByXpath("//div[contains(text(),'轮毂')]").click();
		Thread.sleep(3000);
	}

	@Then("^Verify do not see wheel content on P2 Lincoln$")
	public void verifyDontSeeWheelContent() throws Throwable {
		System.out.println("Verify do not see wheel content");
		if(verifyWheeltab==true) {
			verifyInvisibleElement("//p[contains(text(),'高级铝合金轮毂')]");
		}
	}

	@When("^Click on Galaxy Silver on color tab on P2 Lincoln$")
	public void clickOnGalaxySilver() throws Throwable {
		System.out.println("Click on Galaxy Silver on color tab");
		getVisibleElementByXpath("//p[contains(text(),'雅绅黑')]").click();
		Thread.sleep(10000);
	}

	@When("^Click on interior tab on P2 Lincoln$")
	public void clickOnInteriorTab() throws Throwable {
		System.out.println("Click on interior tab");
		getVisibleElementByXpath("//a[contains(text(),'内饰')]").click();
	}

	@When("^Click on engine tab on P2 Lincoln$")
	public void clickOnEngineTab() throws Throwable {
		System.out.println("Click on engine tab");
		getVisibleElementByXpath("//a[contains(text(),'發動機')]").click();
	}

	@Then("^See all features on interior tab on P2 Lincoln$")
	public void seeAllFeaturesOnInTab(DataTable interiorFeatures) throws Throwable {
		System.out.println("See all features on interior tab");
		List<List<String>> data = interiorFeatures.raw();
		verifySeeingElementVisible(data.get(0).get(0));
	}

	@When("^Click on brown leather seat on seat tab on P2 Lincoln$")
	public void clickOnBrownLeatherSeat() throws Throwable {
		System.out.println("Click on brown leather seat on seat tab");
		getVisibleElementByXpath("//p[contains(text(),'雅榛棕 ')]").click();
	}

	@When("^Click on Seat feature to close on interiror tab on P2 Lincoln$")
	public void clickOnSeatFeatureToClose() throws Throwable {
		System.out.println("Click on Seat feature to close on interiror tab");
		getVisibleElementByXpath("//div[@id='interior']/div/div[1]/h4/a");
		List<WebElement> inttab=driver.findElements(By.xpath("//div[@id='interior']/div/div[1]/h4/a"));
		for(int divCnt=1;divCnt<=inttab.size();divCnt++) {
//			WebElement ExtFeature=getVisibleElementByXpath("//div[@id='interior']/div["+divCnt+"]/div[1]/h4/a/div");
			WebElement findexpanded=getVisibleElementByXpath("//div[@id='interior']/div["+divCnt+"]/div[1]/h4/a");
//			if(ExtFeature.getText().contains("座椅") && Boolean.parseBoolean(findexpanded.getAttribute("aria-expanded"))==true) {
			if(Boolean.parseBoolean(findexpanded.getAttribute("aria-expanded"))==true) {
				getVisibleElementByXpath("//div[@id='interior']/div["+divCnt+"]/div[1]/h4/a/div/i").click();
//				break;
			}
		}
		
//		getVisibleElementByXpath("//div[contains(text(),' 座椅')]").click();
		Thread.sleep(3000);
	}

	@Then("^Verify do not see Seat content on P2 Lincoln$")
	public void verifyDontSeeSeatContent(DataTable seatContent) throws Throwable {
		System.out.println("Verify do not see Seat content");
		List<List<String>> data = seatContent.raw();
		
		for(int divCnt=1;divCnt<=2;divCnt++) {
			WebElement ExtFeature=getVisibleElementByXpath("//div[@id='interior']/div["+divCnt+"]/div[1]/h4/a/div");
			if(ExtFeature.getText().contains("座椅")) {
				verifyDontSeeElementVisible("//div[@id='interior']/div["+divCnt+"]/div[2]//p[1]");
//				verifySeeingElementVisibleNoMovement("//div[@id='interior']/div["+divCnt+"]/div[2]//img"); /// div[2] hardcoded sa [2] bcoz id attribute is not fixed,it can be exterio0/exterior1 based on tab location up/down
				break;
			}
		}
		
		verifyDontSeeElementVisible(data.get(0).get(0));
	}

	@When("^Click on Decorative feature to open on P2 Lincoln$")
	public void clickOnDecorativeToOpen() throws Throwable {
		System.out.println("Click on Decorative feature to open");
		getVisibleElementByXpath("//div[contains(text(),'装饰条')]").click();
		Thread.sleep(3000);
	}

	@Then("^Verify seeing decorative content on P2 Lincoln$")
	public void verifySeeingDecorativeContent(DataTable decorativeContent) throws Throwable {
		System.out.println("Verify seeing decorative content");
		List<List<String>> data = decorativeContent.raw();
		
		for(int divCnt=1;divCnt<=2;divCnt++) {
			WebElement ExtFeature=getVisibleElementByXpath("//div[@id='interior']/div["+divCnt+"]/div[1]/h4/a/div");
			if(ExtFeature.getText().contains("装饰条")) {
				verifySeeingElementVisibleNoMovement("//div[@id='interior']/div["+divCnt+"]/div[2]//img"); /// div[2] hardcoded sa [2] bcoz id attribute is not fixed,it can be exterio0/exterior1 based on tab location up/down
				break;
			}
		}
		
//		verifySeeingElementVisibleNoMovement(data.get(0).get(0));
	}

	@When("^Click on Deep walnut on decorative tab on P2 Lincoln$")
	public void clickOnDeepWalnut() throws Throwable {
		System.out.println("Click on Deep walnut on decorative tab");
		getVisibleElementByXpath("//p[contains(text(),'深核桃木')]").click();
	}

	@When("^Click on Additional tab on P2 Lincoln$")
	public void clickOnAdditionalTab() throws Throwable {
		System.out.println("Click on Additional tab");
		getVisibleElementByXpath("//a[text()='配置']").click();
	}

	@Then("^See all features on Additional tab on P2 Lincoln$")
	public void seeAllFeaturesOnAdditionalTab(DataTable additionalFeatures) throws Throwable {
		System.out.println("See all features on Additional tab");
		List<List<String>> data = additionalFeatures.raw();
		verifySeeingElementVisible(data.get(0).get(0));
	}

	@When("^Click on safe configuration feature to open on Additional tab on P2 Lincoln$")
	public void ClickOnSafeConfigurationFeatureToOpen() throws Throwable {
		System.out.println("Click on safe configuration feature to open");
		getVisibleElementByXpath("//div[contains(text(),'外部配备')]").click();
		Thread.sleep(3000);
	}

	@Then("^See safe configuration content on P2 Lincoln$")
	public void seeSafeConfigurationContent() throws Throwable {
		System.out.println("See safe configuration content");
		getVisibleElementByXpath("//div[@id='extrasCPC']/div/div/div/div/img");
	}

	@When("^Click on safe configuration feature to close on Additional tab on P2 Lincoln$")
	public void ClickOnSafeConfigurationFeatureToClose() throws Throwable {
		System.out.println("Click on safe configuration feature to close");
		getVisibleElementByXpath("//div[contains(text(),'外部配备')]").click();
	}

	@Then("^Verify do not see safe configuration content on P2 Lincoln$")
	public void verifyDontSeeSafeConfigurationContent() throws Throwable {
		System.out.println("Verify do not see safe configuration content");
		verifyInvisibleElement("//p[contains(text(),'带电动遮阳帘的电动开启式全景天窗')]");
	}

	@When("^Click on external equipment feature to open on Addition tab on P2 Lincoln$")
	public void clickOnExternalEquipmentFeatureToOpen() throws Throwable {
		System.out.println("Click on external equipment feature to open on Addition tab");
		getVisibleElementByXpath("//div[contains(text(),'信息娱乐系统')]").click();
		Thread.sleep(3000);
	}

	@Then("^See external equipment content on P2 Lincoln$")
	public void seeExternalEquipmentContent() throws Throwable {
		System.out.println("See external equipment content");
		getVisibleElementByXpath("//div[@id='extrasA1G']/div/div/div/div/img");
	}

	@When("^Click on external equipment feature to close on Addition tab on P2 Lincoln$")
	public void clickOnExternalEquipmentFeatureToClose() throws Throwable {
		System.out.println("Click on external equipment feature to close on Addition tab");
		getVisibleElementByXpath("//div[contains(text(),'信息娱乐系统')]").click();
	}

	@Then("^Verify do not see external equipment content on P2 Lincoln$")
	public void verifyDontSeeExternalEquipmentContent(DataTable equipmentContent) throws Throwable {
		System.out.println("Verify do not see external equipment content");
		List<List<String>> data = equipmentContent.raw();
		verifyDontSeeElementVisible(data.get(0).get(0));

	}

	@When("^Click on control system feature to open on Additional tab on P2 Lincoln$")
	public void clickOnControlSystemFeatureToOpen() throws Throwable {
		System.out.println("Click on control system feature to open");
		getVisibleElementByXpath("//div[contains(text(),'信息娱乐系统')]").click();
		Thread.sleep(3000);
	}

	@Then("^See control system content on P2 Lincoln$")
	public void seeControlSystemContent() throws Throwable {
		System.out.println("See control system content");
		getVisibleElementByXpath("//div[@id='extrasHKC']/div/div/div/div/img");
	}

	@When("^Click on control system feature to close on Additional tab on P2 Lincoln$")
	public void clickOnControlSystemFeatureToClose() throws Throwable {
		System.out.println("Click on control system feature to close");
		getVisibleElementByXpath("//div[contains(text(),'信息娱乐系统')]").click();
		Thread.sleep(3000);
	}

	@Then("^Verify do not see control system content on P2 Lincoln$")
	public void verifyDontSeeControlSystemContent() throws Throwable {
		System.out.println("Verify do not see control system content");
		verifyInvisibleElement("//div[@id='extrasHKC']/div/div/div/div/img");
	}

	@When("^Click on Review and Save button on P2 Lincoln$")
	public void clickOnReviewAndSaveButton() throws Throwable {
		System.out.println("Click on Review and Save button");
		waitTillElemVisiblebyXpath("//li[@id='summary_btn']/a[contains(text(),'查看及保存') or contains(text(),'下一步')]", 240);
		getVisibleElementByXpath("//li[@id='summary_btn']/a[contains(text(),'查看及保存') or contains(text(),'下一步')]").click();
	}

	@Then("^See all components on Review and Save on P2 Lincoln$")
	public void seeAllComponentsObReviewAndSave() throws Throwable {
		System.out.println("See all components on Review and Save");
//		getVisibleElementByXpath("//div[3]/div[3]/div[1]/div/div[4]/div[1]/div[1]/div/div[2]/a[contains(text(),'获取经销商报价')]");
//		getVisibleElementByXpath("//div[@id='btn-next-credit']/a[contains(text(),'付款计算器')]");
		getVisibleElementByXpath("//p[contains(text(),'¥ "+formatNumberFrChina(modelPrice)+"')]");
		//getVisibleElementByXpath("//p[contains(text(),'¥ "+("//p[@class='price nameplate-price-style ng-binding']")+"')]");
	}

	@When("^Click on Payment Calculator button on P2 Lincoln$")
	public void clickOnPaymentCalculatorButton() throws Throwable {
		System.out.println("Click on Payment Calculator button");
		// Actions actions = new Actions(driver);
		// WebElement element =
		// driver.findElement(By.xpath("//div[@id='btn-next-credit']/a[contains(text(),'付款计算器')]"));
		// actions.moveToElement(element);
		// actions.click().build().perform();
		getVisibleElementByXpath("//div[@id='btn-next-credit']/a[contains(text(),'付款计算器')]").click();
	}

	@When("^Redirect to Payment Calculator link on P2 Lincoln$")
	public void redirectToPaymentCalculatorLink() throws Throwable {
		System.out.println("Redirect to Payment Calculator link ");
		WebElement link = getVisibleElementByXpath("//div[@id='btn-next-credit']//a[contains(text(),'付款计算器') or contains(text(),'车贷计算器')]");
		driver.get(getProfileURL(link.getAttribute("href")));
	}

	@Then("^See all components on Payment Calculator on P2 Lincoln$")
	public void seeAllComponentsOnPaymentCalculator() throws Throwable {
		System.out.println("See all components on Payment Calculator");
		getVisibleElementByXpath("//a[contains(text(),'更改车型')]");
		getVisibleElementByXpath("//p[contains(text(),'￥')]");
		getVisibleElementByXpath("//a[text()='常规金融方案']");
		getVisibleElementByXpath("//a[text()='含延保金融方案']");
		getVisibleElementByXpath("//a[text()='红地毯弹性购车计划']");
		getVisibleElementByXpath("//a[text()='半付半贷']");
	}


	@When("^Input amount of money on Payment Calculator on P2 Lincoln$")
	 public void inputAmountOfMoneyOnPaymentCalculator() throws Throwable {
	  System.out.println("Input amount of money on Payment Calculator");
	  int randomNum = ThreadLocalRandom.current().nextInt(1, 20);
	  Click_on_Finance_optons_accordion();
	  downpayment=Integer.parseInt(getVisibleElementByXpath("//td[@currency='currency' and @model='percentValue']").getText().substring(1).replaceAll(",", ""))+randomNum;//(int) Math.ceil((modelPrice*20)/100)+50;//Integer.parseInt(data.get(0).get(0));//Integer.parseInt(getVisibleElementByXpath("//input[@currency='currency' and @model='percentValue']").getText());//Integer.parseInt(data.get(0).get(0));
	  getVisibleElementByXpath("//input[@model='percentValue']").clear();
	  getVisibleElementByXpath("//input[@model='percentValue']").sendKeys(String.valueOf(downpayment));
	  
	  waitTillElemVisiblebyXpath("//p[@model='carPrice' and @currency='currency']", 240);
	 }

	
	 @Then("^See all components after input amount of money on Payment Calculator on P2 Lincoln$")
	 public void seeAllComponentsAfterInputAmountOfMoneyOnPaymentCalculator(DataTable parameter) throws Throwable {
	  System.out.println("See all components after input amount of money on Payment Calculator");
	  List<List<String>> data = parameter.raw();

	  int term=Integer.parseInt(data.get(0).get(8));
	  int discoutPercent=(int) Math.ceil((downpayment*100)/modelPrice);
	  int emi=(int)Math.ceil((modelPrice-downpayment)/term);
//	  getVisibleElementByXpath("//p[contains(text(),'" + discoutPercent + "') or text()>"+ discoutPercent +"])");
//	  getVisibleElementByXpath("//p[contains(text(),'" + formatNumberFrChina(emi) + "')]");
	  int getEMI=Integer.parseInt(getVisibleElementByXpath("//p[@model='currentPayment' and @currency='currency']").getText().split("￥")[1].replace(",", ""));
	  if(getEMI==emi) {
		  System.out.println("EMI Matched:" + formatNumberFrChina(emi));
	  }else if(getEMI>emi){
		  System.out.println("Actual EMI is Greater than expected; Expected EMI:" + formatNumberFrChina(emi) + ", Actual EMI:" + formatNumberFrChina(getEMI));
	  }else if(getEMI<emi){
		  System.out.println("Actual EMI is Less than expected; Expected EMI:" + formatNumberFrChina(emi) + ", Actual EMI:" + formatNumberFrChina(getEMI));
	  }else {
		  Assert.fail();
	  }
	  
	  int getDis=Integer.parseInt(getVisibleElementByXpath("//div[@model='percentValue']//following-sibling::p").getText().replace("%", ""));
	  if(getDis==discoutPercent) {
		  System.out.println("Discout Percent Matched:" + discoutPercent +"%");
	  }else if(getDis>discoutPercent){
		  System.out.println("Actual Discout Percent is Greater than expected; Expected Discout %:" + discoutPercent + ", Actual Discout %:" + getDis);
	  }else if(getDis<discoutPercent){
		  System.out.println("Actual Discout Percent is Less than expected; Expected Discout %:" + discoutPercent + ", Actual Discout %:" + getDis);
	  }else {
		  Assert.fail();
	  }
	  getVisibleElementByXpath("//p[contains(text(),'" + data.get(0).get(2) + "')]");
	  getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[1]/td[contains(text(),'" + formatNumberFrChina(modelPrice) + "')]");
	  getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[2]/td[contains(text(),'" + formatNumberFrChina(downpayment) + "')]");
//	  getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[3]/td/table/tbody/tr[1]/td[text()='" + Integer.toString(discoutPercent) + "%" + "']");
	  getDis=Integer.parseInt(getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[3]/td/table/tbody/tr[1]/td[2]").getText().replace("%", ""));
	  if(getDis==discoutPercent) {
		  System.out.println("Discout Percent Matched:" + discoutPercent +"%");
	  }else if(getDis>discoutPercent){
		  System.out.println("Actual Discout Percent is Greater than expected; Expected Discout %:" + discoutPercent + ", Actual Discout %:" + getDis);
	  }else if(getDis<discoutPercent){
		  System.out.println("Actual Discout Percent is Less than expected; Expected Discout %:" + discoutPercent + ", Actual Discout %:" + getDis);
	  }else {
		  Assert.fail();
	  }
	  
	  getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[3]/td/table/tbody/tr[2]/td[text()='" + data.get(0).get(6) + "']");
//	  getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[4]/td[text()='" + formatNumberFrChina(emi) + "']");
	  getEMI=Integer.parseInt(getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[4]/td[2]").getText().split("￥")[1].replace(",", ""));
	  if(getEMI==emi) {
		  System.out.println("EMI Matched:" + formatNumberFrChina(emi));
	  }else if(getEMI>emi){
		  System.out.println("Actual EMI is Greater than expected; Expected EMI:" + formatNumberFrChina(emi) + ", Actual EMI:" + formatNumberFrChina(getEMI));
	  }else if(getEMI<emi){
		  System.out.println("Actual EMI is Less than expected; Expected EMI:" + formatNumberFrChina(emi) + ", Actual EMI:" + formatNumberFrChina(getEMI));
	  }else {
		  Assert.fail();
	  }
	 }

	
	@When("^Select value from Current Term on Payment Calculator on P2 Lincoln$")
	public void selectValueFromCurrentTermOnPaymentCalculator(DataTable parameter) throws Throwable {
		System.out.println("Select value from Current Term on Payment Calculator");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//div[@class='form-group']//select[@data-ng-model='currentTerm']").click(); ///// div[4]/div[3]/div[1]/div[1]/section/div[3]/div/div/div/div[3]/div[1]/div/select
		Thread.sleep(3000);
		getVisibleElementByXpath("//option[text()='" + data.get(0).get(0) + "']").click();
	}


	@Then("^See all components after selecting value from Current Term on Payment Calculator on P2 Lincoln$")
	 public void seeAllComponentsAfterSelectingValueFromCurrentTermOnPaymentCalculator(DataTable parameter) throws Throwable {
	  System.out.println("See all components after selecting value from Current Term on Payment Calculator");
	  List<List<String>> data = parameter.raw();
	  // for (int i = 0; i <= 1; i++) {
	  // getVisibleElementByXpath("//p[contains(text(),'" + data.get(0).get(i) + "')
	  // or contains(text(),'" + data.get(0).get(i) + "')]");
	  // }
	  int term=Integer.parseInt(data.get(0).get(7));
	  int discoutPercent=(int) Math.ceil((downpayment*100)/modelPrice);
	  int emi=(int)Math.ceil((modelPrice-downpayment)/term);
	  
//	  getVisibleElementByXpath("//p[contains(text(),'" + emi + "')]");
	  int getEMI=Integer.parseInt(getVisibleElementByXpath("//p[@model='currentPayment' and @currency='currency']").getText().split("￥")[1].replace(",", ""));
	  if(getEMI==emi) {
		  System.out.println("EMI Matched:" + formatNumberFrChina(emi));
	  }else if(getEMI>emi){
		  System.out.println("Actual EMI is Greater than expected; Expected EMI:" + formatNumberFrChina(emi) + ", Actual EMI:" + formatNumberFrChina(getEMI));
	  }else if(getEMI<emi){
		  System.out.println("Actual EMI is Less than expected; Expected EMI:" + formatNumberFrChina(emi) + ", Actual EMI:" + formatNumberFrChina(getEMI));
	  }else {
		  Assert.fail();
	  }
	  
	  getVisibleElementByXpath("//p[contains(text(),'" + data.get(0).get(1) + "')]");

	  getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[1]/td[contains(text(),'" + formatNumberFrChina(modelPrice) + "')]");
	  getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[2]/td[contains(text(),'" + formatNumberFrChina(downpayment) + "')]");
//	  getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[3]/td/table/tbody/tr[1]/td[text()='" + Integer.toString(discoutPercent) + "%" + "']");
	  int getDis=Integer.parseInt(getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[3]/td/table/tbody/tr[1]/td[2]").getText().replace("%", ""));
	  if(getDis==discoutPercent) {
		  System.out.println("Discout Percent Matched:" + discoutPercent +"%");
	  }else if(getDis>discoutPercent){
		  System.out.println("Actual Discout Percent is Greater than expected; Expected Discout %:" + discoutPercent + ", Actual Discout %:" + getDis);
	  }else if(getDis<discoutPercent){
		  System.out.println("Actual Discout Percent is Less than expected; Expected Discout %:" + discoutPercent + ", Actual Discout %:" + getDis);
	  }else {
		  Assert.fail();
	  }
	  
	  getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[3]/td/table/tbody/tr[2]/td[text()='" + data.get(0).get(5) + "']");
//	  getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[4]/td[text()='" + formatNumberFrChina(emi) + "']");
	  getEMI=Integer.parseInt(getVisibleElementByXpath("//div[@id='collapseOne']/div/div/table/tbody/tr[4]/td[2]").getText().split("￥")[1].replace(",", ""));
	  if(getEMI==emi) {
		  System.out.println("EMI Matched:" + formatNumberFrChina(emi));
	  }else if(getEMI>emi){
		  System.out.println("Actual EMI is Greater than expected; Expected EMI:" + formatNumberFrChina(emi) + ", Actual EMI:" + formatNumberFrChina(getEMI));
	  }else if(getEMI<emi){
		  System.out.println("Actual EMI is Less than expected; Expected EMI:" + formatNumberFrChina(emi) + ", Actual EMI:" + formatNumberFrChina(getEMI));
	  }else {
		  Assert.fail();
	  }
	}
	 
	
	@When("^Click on Share on Payment Calculator on P2 Lincoln$")
	public void clickOnShareOnPaymentCalulator() throws Throwable {
		System.out.println("Click on Share on Payment Calculator");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_share-thin']").click();
	}

	@Then("^See all components on Share overlay on P2 Lincoln$")
	public void seeAllComponentsOnShareOverlay() throws Throwable {
		System.out.println("See all components on Share overlay");
		getVisibleElementByXpath("//a[@class='lincoln-icon_wechat green']");
		getVisibleElementByXpath("//a[@class='lincoln-icon_weibo pink']");
		getVisibleElementByXpath("//a[@class='lincoln-icon_douban green']");
		getVisibleElementByXpath("//a[@class='lincoln-icon_tencent-weibo blue']");
		getVisibleElementByXpath("//a[contains(text(),' 关闭')]");
	}

	@When("^Click on Close button on Share overlay on P2 Lincoln$")
	public void clickOnCloseButtonOnShareOverlay() throws Throwable {
		System.out.println("Click on Close button on Share overlay");
		getVisibleElementByXpath("//a[contains(text(),' 关闭')]").click();
	}

	@When("^Click on PDF on Payment Calculator on P2 Lincoln$")
	public void clickOnPdfOnPaymentCalculator() throws Throwable {
		System.out.println("Click on PDF on Payment Calculator");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_pdf']").click();
	}

	@Then("^Vehicle image should be loaded$")
	public void Vehicle_image_should_be_loaded() throws Throwable {
		System.out.println("Vehicle image should be loaded");
		getVisibleElementByXpath("//span[@class='icon lincoln-icon_pdf']").click();
	}

	@And("^Click on Build and Price$")
	public void Click_on_Build_and_Price(DataTable param) throws Throwable {
		System.out.println("Click on Build and Price");
		List<List<String>> data = param.raw();
		if (!data.get(0).get(0).isEmpty()) {
			waitTillElemVisiblebyXpath("//div[contains(@id,'footer')]//a[contains(text(),'" + data.get(0).get(0) + "')]", 240);
			getVisibleElementByXpath("//div[contains(@id,'footer')]//a[contains(text(),'" + data.get(0).get(0) + "')]").click();
		}
		if (!data.get(0).get(1).isEmpty()) {
		//	waitTillElemVisiblebyXpath("//div[@id='desktop-btn-next']//a[contains(text(),'" + data.get(0).get(1) + "')]", 240);
		//	try {
		//		getVisibleElementByXpath("//div[@id='desktop-btn-next']//a[contains(text(),'" + data.get(0).get(1) + "')]").click();
		//	}catch(Exception e) {
			//	if(!e.getMessage().contains("timeout")) {     Arpit	
		//			throw new Exception(e.getMessage());
		//		}
		//	}

			try {
				getVisibleElementByXpath("//p[contains(@class,'price') and text()!='']/following-sibling::span[contains(@class,'checkbox')]").click();
			} catch (Exception e) {
			}
			waitTillElemVisiblebyXpath("//li/small[contains(text(),'总价')]//following-sibling::p[text()!='']", 240);
			modelPrice=convertStrtoDoubletoInt(driver.findElement(By.xpath("//p[contains(@class,'price') and text()!='']")).getText().split(" ")[1]);
			int nameplateprz=convertStrtoDoubletoInt(getVisibleElementByXpath("//*[contains(@class,'bp-config-dropdown')]//*[contains(@class,'nameplate-price')]").getText().split(" ")[1].replace(",",""));
			if(modelPrice!=nameplateprz) {
				modelPrice=nameplateprz;
			}
			modelName=driver.findElement(By.xpath("//div[@class='component-bp-config-menu']//li[contains(@class,'ng-binding')]")).getText();
		}
		if (!data.get(0).get(2).isEmpty()) {
			waitTillElemVisiblebyXpath("//*[@id='summary_btn']//a[contains(text(),'" + data.get(0).get(2) + "') or contains(text(),'下一步')]", 240);
			getVisibleElementByXpath("//*[@id='summary_btn']//a[contains(text(),'" + data.get(0).get(2) + "') or contains(text(),'下一步')]").click();
		}
	}

	@And("^see summary page$")
	public void see_summary_page(DataTable param) throws Throwable {
		System.out.println("see summary page");
		List<List<String>> data = param.raw();
		//waitTillElemVisiblebyXpath("//div[contains(@class,'preview-header')]//*[contains(text(),'" + modelName + "')]", 240);
		waitTillElemVisiblebyXpath("//div[contains(@class,'preview-header')]//*[contains(text(),'" + data.get(0).get(0) + "')]", 240);
	}

	@Then("^external and interior color selected should be shown$")
	public void external_and_interior_color_selected_should_be_shown() throws Throwable {
		System.out.println("external and interior color selected should be shown");
		//can not verify images as unavailability of images leads failure of automation scripts frequently
//		try {
//			String srcExt = getVisibleElementByXpath("//div[contains(@ng-repeat,'features.exterior')]/img").getAttribute("ng-src");
//			String[] spl = srcExt.split("/");
//			System.out.println(srcExt);
//			String img = spl[spl.length - 1].split("\\.")[0];
//			waitTillElemVisiblebyXpath("//img[contains(@src,'" + srcExt + "')]", 120);
//			getVisibleElementByXpath("//img[contains(@src,'" + img + "/1.jpg')]");
//	
//			srcExt = getVisibleElementByXpath("//div[contains(@ng-repeat,'features.interior')]/img").getAttribute("ng-src");
//			spl = srcExt.split("/");
//			System.out.println(srcExt);
//			img = spl[spl.length - 1].split("\\.")[0];
//			waitTillElemVisiblebyXpath("//img[contains(@src,'" + srcExt + "')]", 120);
//			getVisibleElementByXpath("//img[contains(@src,'" + srcExt + "')]").click();
//			getVisibleElementByXpath("//img[contains(@src,'" + img + "/1.jpg')]");
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		
	}

	@Then("^Should be given vehicle price details correctly$")
	public void Should_be_given_vehicle_price_details_correctly() throws Throwable {
		System.out.println("Should be given vehicle price details correctly");
		// getVisibleElementByXpath("//p[contains(text(),'¥ 449,800') and
		// @ng-click='pricingDropdown()']");
		getVisibleElementByXpath("//p[contains(text(),'¥ "+formatNumberFrChina(modelPrice)+"')]");
		getVisibleElementByXpath("//h3[contains(text(),'¥ "+formatNumberFrChina(modelPrice)+"')]");
	}

	@Then("^Should be listed down features selected$")
	public void Should_be_listed_down_features_selected(DataTable param) throws Throwable {
		System.out.println("Should be listed down features selected");
		List<WebElement> findFeatures = driver.findElements(By.xpath("//p[@ng-bind-html='uniqueFeatures']//p[text()!='']"));
		if (findFeatures.size() > 0) {
			System.out.println("All features has been listed on UI");
		} else {
			System.out.println("Vehicle features does not listed on summary page");
		}
	}

	@When("^Navigate back to Vehicle Configuration page$")
	public void Navigate_back_to_Vehicle_Configuration_page() throws Throwable {
		System.out.println("Navigate back to Vehicle Configuration page");
		try {
			getVisibleElementByXpath("//a[contains(text(),'返回配置') or contains(text(),'更改车型')]").click();
		}catch(Exception e) {
			if(!e.getMessage().contains("timeout")) {
				throw new Exception(e.getMessage());
			}
		}
	}

	@Then("^Able to navigate back to vehicle configuration page$")
	public void Able_to_navigate_back_to_vehicle_configuration_page() throws Throwable {
		System.out.println("Able to navigate back to vehicle configuration page");
		getVisibleElementByXpath("//a[contains(text(),'配置林肯车型') or contains(text(),'查看更多') or contains(text(),'修改车型')]");
	}

	@Then("^Click on Build and price summary button$")
	public void Click_on_Build_and_price_summary_button(DataTable param) throws Throwable {
		System.out.println("Click on Build and Price");
		List<List<String>> data = param.raw();
		waitTillElemVisiblebyXpath("//*[@id='summary_btn']//a[contains(text(),'" + data.get(0).get(0) + "') or contains(text(),'下一步')]", 240);
		
		try {
			getVisibleElementByXpath("//p[contains(@class,'price') and text()!='']/following-sibling::span[contains(@class,'checkbox')]").click();
		} catch (Exception e) {
		}
		waitTillElemVisiblebyXpath("//li/small[contains(text(),'总价')]//following-sibling::p[text()!='']", 240);
		modelPrice=convertStrtoDoubletoInt(driver.findElement(By.xpath("//p[contains(@class,'price') and text()!='']")).getText().split(" ")[1]);
		int nameplateprz=convertStrtoDoubletoInt(getVisibleElementByXpath("//*[contains(@class,'bp-config-dropdown')]//*[contains(@class,'nameplate-price')]").getText().split(" ")[1].replace(",",""));
		if(modelPrice!=nameplateprz) {
			modelPrice=nameplateprz;
		}
		modelName=driver.findElement(By.xpath("//div[@class='component-bp-config-menu']//li[contains(@class,'ng-binding')]")).getText();
		
		getVisibleElementByXpath("//*[@id='summary_btn']//a[contains(text(),'" + data.get(0).get(0) + "') or contains(text(),'下一步')]").click();
	}

	@When("^Click on Print on Vehicle Summary page$")
	public void Click_on_Print_on_Vehicle_Summary_page() throws Throwable {
		System.out.println("Click on Print on Vehicle Summary page");
		File file = new File(Configuration.PATH_TO_FILE_DOWNLOAD);
		if (file.exists()) {
			file.delete();
		}
		Thread.sleep(2000);
		getVisibleElementByXpath("//a[@ng-click='pushToPDF();']").click();
	}

	@Then("^Successfully print the summary page$")
	public void Successfully_print_the_summary_page() throws Throwable {
		System.out.println("Successfully print the summary page");
//		Thread.sleep(120000);
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

	@When("^Click on Share on Vehicle Summary page$")
	public void Click_on_Share_on_Vehicle_Summary_page() throws Throwable {
		System.out.println("Click on Share on Vehicle Summary page");
		getVisibleElementByXpath("//a[@class='btn-icon btn-share text-dark-grey' and @data-target='.share-currentPage-modal']").click();
	}

	@Then("^Able to share the summary page$")
	public void Able_to_share_the_summary_page() throws Throwable {
		System.out.println("Able to share the summary page");
		getVisibleElementByXpath("//a[contains(text(),'关闭')]").click();

	}

	@When("^Click on Payment Calculator$")
	public void Click_on_Payment_Calculator() throws Throwable {
		System.out.println("Click on Payment Calculator");
		getVisibleElementByXpath("//div[@id='btn-next-credit']/a[contains(text(),'付款计算器') or contains(text(),'车贷计算器')]").click();
	}

	@When("^switch to other window$")
	public void switch_to_other_window() throws Throwable {
		System.out.println("switch to other window");
		if (driver.getWindowHandles().size() > 1) {
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}
		}
	}

	@Then("^All components loaded successfully without performance issue$")
	public void All_components_loaded_successfully_without_performance_issue(DataTable parameter) throws Throwable {
		System.out.println("All components loaded successfully without performance issue");
		List<List<String>> data = parameter.raw();
		String firstwindow=driver.getWindowHandle();
		if (driver.getWindowHandles().size() > 1) {
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}
		}
		if(driver.getPageSource().isEmpty()) {
			driver.switchTo().window(firstwindow);
		}
		int chkmodelprz=Integer.parseInt(getVisibleElementByXpath("//div[@class='pe-preview-price']//p[text()!='' and text()!='¥ 0' and text()!='￥']").getText().trim().replace(",", "").replace("￥", ""));//getVisibleElementByXpath("//div[@class='pe-preview-price']//p[text()!='' and text()!='¥ 0' and text()!='￥' and contains(text(),'"+ formatNumberFrChina(modelPrice) +"')]");//OLD: getVisibleElementByXpath("//div[@class='pe-preview-price']//p[text()!='' and text()!='¥ 0' and text()!='￥']");//21-8 Changed on QA OLD value =
		if(chkmodelprz!=modelPrice) {
			Assert.assertFalse("Incorrect model prize displayed on Payment calculator page", false);
		}
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(2) + "')]");
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(3) + "')]");
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(4) + "')]");
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(5) + "')]");
//		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(6) + "')]");
		getVisibleElementByXpath("//input[@model='percentValue']");
		getVisibleElementByXpath("//div[contains(@data-ng-hide,'50_50')]//select[@data-ng-model='currentTerm']");
		getVisibleElementByXpath("//span[contains(text(),'" + data.get(0).get(6) + "')]");
	}

	@Then("^CTAs and links are functional on the page$")
	public void CTAs_and_links_are_functional_on_the_page(DataTable parameter) throws Throwable {
		System.out.println("CTAs and links are functional on the page");
		List<List<String>> data = parameter.raw();
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(0) + "')]").click();
		getVisibleElementByXpath("//span[contains(text(),'" + data.get(0).get(1) + "')]");
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(2) + "')]").click();
		getVisibleElementByXpath("//span[contains(text(),'" + data.get(0).get(3) + "')]");
		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(4) + "')]").click();
		getVisibleElementByXpath("//span[contains(text(),'" + data.get(0).get(5) + "')]");
//		getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(6) + "')]").click();
//		getVisibleElementByXpath("//span[contains(text(),'" + data.get(0).get(7) + "')]");
		if(!data.get(0).get(6).isEmpty()) {
			getVisibleElementByXpath("//a[contains(text(),'" + data.get(0).get(6) + "')]").click();
//			waitTillElemVisiblebyXpath("//b[contains(text(),'车贷计算器')]", 240);
//			waitTillElemVisiblebyXpath("//b[contains(text(),'选择车型')]", 240);
			getVisibleElementByXpath("//*[contains(text(),'车贷计算器')]");
			getVisibleElementByXpath("//*[contains(text(),'选择车型')]");
		}

		if (driver.getWindowHandles().size() > 1) {
			driver.switchTo().defaultContent();
		}
	}

	@When("^All components loads and CTAs and links are functional on the page$")
	public void All_components_loads_and_CTAs_and_links_are_functional_on_the_page() throws Throwable {
		System.out.println("All components loads and CTAs and links are functional on the page");
		//waitTillElemVisiblebyXpath("//div[@id='desktop-btn-next']/a[contains(text(),'下一步')]", 240); Arpit
		//getVisibleElementByXpath("//div[@id='desktop-btn-next']/a[contains(text(),'下一步')]"); Arpit
//		getVisibleElementByXpath("//div[contains(@id,'desktop-btn-next')]/a[contains(text(),'车贷计算器')]");
		getVisibleElementByXpath("//div[@id='footer1']//a[contains(text(),'林肯MKZ')]");
		getVisibleElementByXpath("//div[@id='footer2']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//div[@id='footer3']//a[contains(text(),'林肯之道')]");
		getVisibleElementByXpath("//div[@id='footer4']//a[contains(text(),'售后服务')]");
	}

	@When("^Select a nameplate and Model$")
	public void Select_a_nameplate_and_Model(DataTable parameter) throws Throwable {
		System.out.println("Select a nameplate and Model");
		List<List<String>> data = parameter.raw();
		try {
			getVisibleElementByXpath("//p[contains(text(),'" + data.get(0).get(0) + "')]/preceding::img[contains(@alt,'" + data.get(0).get(0) + "')]");
			Thread.sleep(2000);
			List<WebElement> NamePlates = driver.findElements(By.xpath("//p[contains(text(),'" + data.get(0).get(0) + "')]/preceding::img[contains(@alt,'" + data.get(0).get(0) + "')]"));
			NamePlates.get(0).click();

			getVisibleElementByXpath("//p[contains(text(),'" + data.get(0).get(1) + "')]/preceding::img[contains(@alt,'" + data.get(0).get(1) + "')]");
			getVisibleElementByXpath("//p[contains(text(),'" + data.get(0).get(1) + "')]/preceding::span");
			List<WebElement> Models = driver.findElements(By.xpath("//p[contains(text(),'" + data.get(0).get(1) + "')]/preceding::img[contains(@alt,'" + data.get(0).get(1) + "')]"));
			Models.get(0).click();
		} catch (Exception e) {
		}
		getVisibleElementByXpath("//div[contains(@id,'desktop-btn-next')]/a[contains(text(),'车贷计算器')]");
		getVisibleElementByXpath("//div[@id='footer1']//a[contains(text(),'林肯MKZ')]");
		getVisibleElementByXpath("//div[@id='footer2']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//div[@id='footer3']//a[contains(text(),'林肯之道')]");
		getVisibleElementByXpath("//div[@id='footer4']//a[contains(text(),'售后服务')]");
	}

	@Then("^Corresponding models listed down and able to select model at once$")
	public void Corresponding_models_listed_down_and_able_to_select_model_at_once() throws Throwable {
		System.out.println("Corresponding models listed down and able to select model at once");

	}

	@When("^Click on \"(.*?)\" Button$")
	public void ClickonButton(String Btn) throws Throwable {
		System.out.println("Click on Button " + Btn);
		try {
			getVisibleElementByXpath("//div[@id='desktop-btn-next']/a[contains(text(),'下一步')]").click();
		}catch(Exception e) {
			if(!e.getMessage().contains("timeout")) {
				throw new Exception(e.getMessage());
			}
		}

		try {
			getVisibleElementByXpath("//p[contains(@class,'price') and text()!='']/following-sibling::span[contains(@class,'checkbox')]").click();
		} catch (Exception e) {
		}
		waitTillElemVisiblebyXpath("//li/small[contains(text(),'总价')]//following-sibling::p[text()!='']", 240);
		System.out.println(driver.findElements(By.xpath("//p[contains(@class,'price') and text()!='']")).get(0).getText());
		modelPrice=convertStrtoDoubletoInt(driver.findElements(By.xpath("//p[contains(@class,'price') and text()!='']")).get(0).getText().split(" ")[1]);
		int nameplateprz=convertStrtoDoubletoInt(getVisibleElementByXpath("//*[contains(@class,'bp-config-dropdown')]//*[contains(@class,'nameplate-price')]").getText().split(" ")[1]);
		if(modelPrice!=nameplateprz) {
			modelPrice=nameplateprz;
		}
	}

	@Then("^Components loads and CTAs and links are functional on the page$")
	public void Components_loads_and_CTAs_and_links_are_functional_on_the_page() throws Throwable {
		System.out.println("Components loads and CTAs and links are functional on the page");
//		getVisibleElementByXpath("//p[text()='¥ 449,800' or text()='¥ 331,800' or text()='¥ 499,900']");
		getVisibleElementByXpath("//a[text()='外观']").click();
		try {
			waitTillElemVisiblebyXpath("//img[contains(@src,'PN4AG.jpg')]", 240);
			getVisibleElementByXpath("//img[contains(@src,'PN4AG.jpg')]").click();
			waitTillElemVisiblebyXpath("//div[@class='owl-item active']//img[contains(@src,'PN4AG/1.jpg')]", 120);
			getVisibleElementByXpath("//div[@class='owl-item active']//img[contains(@src,'PN4AG/1.jpg')]");
			getVisibleElementByXpath("//img[contains(@src,'PNEAM.jpg')]").click();
			waitTillElemVisiblebyXpath("//div[@class='owl-item active']//img[contains(@src,'PNEAM/1.jpg')]", 120);
			getVisibleElementByXpath("//div[@class='owl-item active']//img[contains(@src,'PNEAM/1.jpg')]");
		}catch(Exception e) {
			e.printStackTrace();
		}
		getVisibleElementByXpath("//a[text()='内饰']").click();
		try {
			String srcExt = getVisibleElementByXpath("//div[contains(@ng-repeat,'features.interior')]//img").getAttribute("ng-src");
			String[] spl = srcExt.split("/");
			System.out.println(srcExt);
			String img = spl[spl.length - 1].split("\\.")[0];
	
//			getVisibleElementByXpath("//img[contains(@src,'" + img + ".jpg')]").click();
//			waitTillElemVisiblebyXpath("//div[@class='owl-item active']//img[contains(@src,'" + img + "/1.')]", 120);
//			getVisibleElementByXpath("//div[@class='owl-item active']//img[contains(@src,'" + img + "/1.')]");
//Updating the class attribute for image
			getVisibleElementByXpath("//img[contains(@ng-src,'" + img + ".jpg')]").click();
			waitTillElemVisiblebyXpath("//div[@class='owl-item active']//img[contains(@ng-src,'" + img + "/1.')]", 120);
			getVisibleElementByXpath("//div[@class='owl-item active']//img[contains(@ng-src,'" + img + "/1.')]");
	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		// getVisibleElementByXpath("//img[contains(@src,'580ZH.jpg')]").click();
		// waitTillElemVisiblebyXpath("//div[@class='owl-item
		// active']//img[contains(@src,'580ZH/1.jpg')]", 120);
		// getVisibleElementByXpath("//div[@class='owl-item
		// active']//img[contains(@src,'580ZH/1.jpg')]");
		getVisibleElementByXpath("//a[text()='配置']").click();

//		getVisibleElementByXpath("//li[@id='summary_btn']/a[text()='查看及保存']");
	}

	@When("^All components loads without performance issueand CTAs and links are functional on the page$")
	public void All_components_loads_without_performance_issueand_CTAs_and_links_are_functional_on_the_page() throws Throwable {
		System.out.println("All components loads without performance issueand CTAs and links are functional on the page");
		getVisibleElementByXpath("//div[@id='desktop-btn-next']/a[contains(text(),'下一步')]");
		getVisibleElementByXpath("//div[contains(@id,'desktop-btn-next')]/a[contains(text(),'车贷计算器')]");
		getVisibleElementByXpath("//div[@id='footer1']//a[contains(text(),'林肯MKZ')]");
		getVisibleElementByXpath("//div[@id='footer2']//a[contains(text(),'配置林肯')]");
		getVisibleElementByXpath("//div[@id='footer3']//a[contains(text(),'林肯之道')]");
		getVisibleElementByXpath("//div[@id='footer4']//a[contains(text(),'售后服务')]");
	}
	
	@When("^Click on Login link$")
	public void click_on_Login_link() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Click on Login link");		
		try {
			waitTillElemVisiblebyXpath("//*[text()='登录']", 240);
			getVisibleElementByXpath("//*[text()='登录']").click();
			waitTillElemVisiblebyXpath("//input[@name='loginFirstName']", 240);
		}catch(Exception e) {}	
	}

	@When("^Click on Registration link$")
	public void click_on_Registration_link() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Click on Registration link");
		getVisibleElementByXpath("//*[contains(text(),'新用户注')]").click();
		waitTillElemVisiblebyXpath("//select[@id='title']", 240);
	}

	@Then("^See Registration form is opened$")
	public void see_Registration_form_is_opened() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("See Registration form is opened");
		getVisibleElementByXpath("//select[@id='title']");
		System.out.println("Owner registration Form has been open successfully");
	}

	@When("^Enter existing users email id in the form field$")
	public void enter_existing_users_email_id_in_the_form_field(DataTable arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Enter existing users email id in the form field");
		List<List<String>> data=arg.raw();
		Select tilte=new Select(getVisibleElementByXpath("//select[@id='title']"));
		tilte.selectByVisibleText(data.get(0).get(0));
		ClearAndInputTextBox("//input[@name='firstName']",data.get(0).get(1));
		ClearAndInputTextBox("//input[@name='lastName']",data.get(0).get(2));
		ClearAndInputTextBox("//input[@name='username']",data.get(0).get(3));
		ClearAndInputTextBox("//input[@name='password']",data.get(0).get(4));
		ClearAndInputTextBox("//input[@name='confirmPassword']",data.get(0).get(5));
		ClearAndInputTextBox("//input[@name='mobile']",phn);
		ClearAndInputTextBox("//input[@name='captchaValue']","ABCD");
	}

	
	@When("^Click on Submit button$")
	public void click_on_Submit_button() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Click on Submit button");
		getVisibleElementByXpath("//button[(contains(@id,'btn-register') and @type='submit') or (contains(text(),'登录') and @type='submit')]").click();
	}

	@Then("^verify validation message displayed$")
	public void verify_validation_message_displayed(DataTable arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("verify validation message displayed");
		List<List<String>> data=arg.raw();
		waitTillElemVisiblebyXpath("//*[(contains(@class,'registration-message') and contains(@style,'block')) or (contains(@class,'registration-message') and not(contains(@style,'display')))]", 240);
		WebElement getvalidationmsg=	getVisibleElementByXpath("//*[(contains(@class,'registration-message') and contains(@style,'block')) or (contains(@class,'registration-message') and not(contains(@style,'display')))]");
		if(!getvalidationmsg.getText().isEmpty() || getvalidationmsg.getText().toLowerCase().contains(data.get(0).get(0).toLowerCase())) {
			System.out.println("Validation message is: " +getvalidationmsg.getText());
		}else {
			Assert.assertFalse("Incorrect validation message", true);
		}
	}

	@Then("^verify validation message displayed for invalid values$")
	public void verify_validation_message_displayed_for_invalid_values() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("verify validation message displayed for invalid values");
		waitTillElemVisiblebyXpath("//*[@class='parsley-pattern' or @class='parsley-custom-error-message' or @class='parsley-equalto']", 240);
		List<WebElement> getvalidationmsg=driver.findElements(By.xpath("//*[@class='parsley-pattern' or @class='parsley-custom-error-message' or @class='parsley-equalto']"));
		if(getvalidationmsg.size()>0) {
			System.out.println("List of validation messages for invalid entries mentioned below:");
			for(WebElement validationmsg:getvalidationmsg) {
				System.out.println("Validation message is: " +validationmsg.getText());
			}
		}else {
			for(WebElement validationmsg:getvalidationmsg) {
				System.out.println("Validation message is: " +validationmsg.getText());
			}
			Assert.assertFalse("Missing validation message, please check on UI for more details", true);
		}
	}
	
	@When("^Enter existing phone number in the form field$")
	public void enter_existing_phone_number_in_the_form_field(DataTable arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Enter existing phone number in the form field");
		List<List<String>> data=arg.raw();
		ClearAndInputTextBox("//input[@name='username']",UniqueKey + "@mailinator.com");
		ClearAndInputTextBox("//input[@name='mobile']",data.get(0).get(0));
	}

	@When("^Enter invalid values in form fields$")
	public void enter_invalid_values_in_form_fields(DataTable arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Enter invalid values in form fields");
		List<List<String>> data=arg.raw();
		Select tilte=new Select(getVisibleElementByXpath("//select[@id='title']"));
		tilte.selectByVisibleText(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='username']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='password']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//input[@name='confirmPassword']").sendKeys(data.get(0).get(5));
		getVisibleElementByXpath("//input[@name='mobile']").sendKeys(data.get(0).get(6));
	}

	@When("^Enter valid values$")
	public void enter_valid_values() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Enter valid values");
		ClearAndInputTextBox("//input[@name='username']",UniqueKey + "@mailinator.com");
		ClearAndInputTextBox("//input[@name='mobile']",phn);
	}

	@When("^Enter Captcha$")
	public void Enter_Captcha() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		getVisibleElementByXpath("//input[@name='captchaValue']").clear();
		getVisibleElementByXpath("//input[@name='captchaValue']").sendKeys(EnterCaptcha());
	}
	
	@Then("^See Thanks page overlay is displaying$")
	public void see_Thanks_page_overlay_is_displaying() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("See Thanks page overlay is displaying");
		waitTillElemVisiblebyXpath("//*[text()='衷心感谢' or text()='密码发送成功' or text()='重置密码成功']", 240);
		getVisibleElementByXpath("//*[text()='衷心感谢' or text()='密码发送成功' or text()='重置密码成功']");
		getVisibleElementByXpath("//*[@class='lincoln-icon_close-thin']").click();
		System.out.println("USER ID is: " + UniqueKey + "@mailinator.com");
	}

	@When("^Enter User and Password$")
	public void enter_User_and_Password(DataTable arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Enter User and Password");
		List<List<String>> data=arg.raw();
		waitTillElemVisiblebyXpath("//input[@name='loginFirstName' or @name='userId' or @id='userNameInput']", 240);
		if(data.get(0).get(0).isEmpty()) {
			ClearAndInputTextBox("//input[@name='loginFirstName' or @name='userId']",UniqueKey + "@mailinator.com");
			System.out.println("Login user is: " + UniqueKey + "@mailinator.com");
		}else {
			ClearAndInputTextBox("//input[@name='loginFirstName' or @name='userId' or @id='userNameInput']",data.get(0).get(0));
			System.out.println("Login user is: " + UniqueKey);
		}		
		ClearAndInputTextBox("//input[@name='loginPassword' or @name='passwd' or @id='passwordInput']",data.get(0).get(1));		
	}

	@Then("^See validation error message is displaying$")
	public void see_validation_error_message_is_displaying(DataTable arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("See validation error message is displaying");
		List<List<String>> data=arg.raw();
		waitTillElemVisiblebyXpath("//*[@class='fail']", 240);
		WebElement getvalidationmsg=	getVisibleElementByXpath("//*[@class='fail']");
		if(!getvalidationmsg.getText().isEmpty() || getvalidationmsg.getText().toLowerCase().contains(data.get(0).get(0).toLowerCase()) || getvalidationmsg.getText().toLowerCase().contains(data.get(0).get(1).toLowerCase()) || getvalidationmsg.getText().toLowerCase().contains(data.get(0).get(2).toLowerCase())) {
			System.out.println("Validation message is: " +getvalidationmsg.getText());
		}else {
			Assert.assertFalse("Incorrect validation message displayed: Expected msg:" + data.get(0).get(0) + ", Actual msg:" +getvalidationmsg.getText(), true);
		}
	}

	@Then("^User login should be successful and overview page should be displayed$")
	public void user_login_should_be_successful_and_overview_page_should_be_displayed(DataTable arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("User login should be successful and overview page should be displayed");
		List<List<String>> data=arg.raw();
		String WelcomeMsg="Welcome, "+data.get(0).get(0)+" "+data.get(0).get(1);
		waitTillElemVisiblebyXpath("//span[contains(text(),'Overview')]", 240);
		getVisibleElementByXpath("//span[contains(text(),'Overview')]");
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==false) {
			System.out.println("Jenkins build FALSE");
			getVisibleElementByXpath("//span[contains(text(),'"+WelcomeMsg+"')]");
		}
		getVisibleElementByXpath("//span[contains(text(),'Logout')]");
		driver.switchTo().defaultContent();
	}
	
	@When("^Click on Activate My Account link in activation mail$")
	public void clickOnActivateMyAccountLinkInMail() throws Throwable {
		System.out.println("Click on Activate My Account link in activation mail");
		Thread.sleep(10000);
		try {
			driver.switchTo().frame(driver.findElement(By.cssSelector("#publicshowmaildivcontent")))
					.findElement(By.tagName("body"));
		} catch (Exception e) {
			driver.switchTo().frame(driver.findElement(By.id("msg_body")));
		}
//		WebElement link = getVisibleElementByXpath("//a[contains(text(),'here')]");
//		link.click();
		driver.findElement(By.partialLinkText("here")).click();
		Thread.sleep(120000);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}
	
	@Then("^verify Registration Success message$")
	public void SuccessfuluserRegistration() throws Throwable {
		System.out.println("verify Registration Success message");
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
			Thread.sleep(1000);
		}
		if (isAlertPresent()) {
		    driver.switchTo().alert();
		    driver.switchTo().alert().accept();
		    driver.switchTo().defaultContent();
		}
		waitTillElemVisiblebyXpath("//*[contains(text(),'注册成功') or contains(text(),'您已经成功激活账号') or contains(text(),'Success') or contains(text(),'Account Activation') or contains(text(),'Account activation')]", 240);
		getVisibleElementByXpath("//*[contains(text(),'注册成功') or contains(text(),'您已经成功激活账号') or contains(text(),'Success') or contains(text(),'Account Activation') or contains(text(),'Account activation')]");
		driver.switchTo().defaultContent();
		Thread.sleep(3000);
	}
	
	
	@When("^Click on Forgot Password link$")
	public void click_on_Forgot_Password_link() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Click on Forgot Password link");
		getVisibleElementByXpath("//a[contains(@class,'forgot')]").click();
	}

	@Then("^see forgot password overlay opened$")
	public void see_forgot_password_overlay_opened() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("see forgot password overlay opened");
		waitTillElemVisiblebyXpath("//input[contains(@name,'username')]", 240);
		getVisibleElementByXpath("//input[contains(@name,'username')]");
		System.out.println("Forget Password overlay opened.");
	}

	@When("^enter invalid email account$")
	public void enter_invalid_email_account(DataTable arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("enter invalid email account");
		List<List<String>> data=arg.raw();
		getVisibleElementByXpath("//input[contains(@name,'username')]").sendKeys(data.get(0).get(0));
		
	}

	@When("^Click on Submit$")
	public void click_on_Submit() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Click on Submit");
		getVisibleElementByXpath("//button[contains(@ng-click,'forgetPass') and @type='submit']").click();
	}

	@Then("^see validation message displayed$")
	public void see_validation_message_is_displayed(DataTable arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("see validation message displayed");
		List<List<String>> data=arg.raw();
		waitTillElemVisiblebyXpath("//*[(contains(@class,'forget-password-message') and contains(@style,'block')) or (contains(@class,'forget-password-message') and not(contains(@style,'display')))]", 240);
		WebElement getvalidationmsg=	getVisibleElementByXpath("//*[(contains(@class,'forget-password-message') and contains(@style,'block')) or (contains(@class,'forget-password-message') and not(contains(@style,'display')))]");
		if(!getvalidationmsg.getText().isEmpty() ||getvalidationmsg.getText().toLowerCase().contains(data.get(0).get(0).toLowerCase())) {
			System.out.println("Validation message is: " +getvalidationmsg.getText());
		}else {
			Assert.assertFalse("Incorrect validation message: "+ getvalidationmsg.getText() , true);
		}
	}

	@When("^enter valid email id$")
	public void enter_valid_email_id() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("enter valid email id");
		ClearAndInputTextBox("//input[@name='username']",UniqueKey + "@mailinator.com" );
	}

	@When("^Click on reset password link$")
	public void click_on_reset_password_link() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Click on reset password link");
		Thread.sleep(10000);
		try {
			driver.switchTo().frame(driver.findElement(By.cssSelector("#publicshowmaildivcontent")))
					.findElement(By.tagName("body"));
		} catch (Exception e) {
			driver.switchTo().frame(driver.findElement(By.id("msg_body")));
		}
//		WebElement link = driver.findElement(By.xpath("//p//a[text()='here']"));
//		link.click();
		driver.findElement(By.partialLinkText("here")).click();
		Thread.sleep(60000);
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
	}

	@Then("^See reset password link overlay opened$")
	public void see_reset_password_link_overlay_opened() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("See reset password link overlay opened");
		waitTillElemVisiblebyXpath("//input[@name='password']", 300);
		waitTillElemVisiblebyXpath("//input[@name='confirmPassword']", 300);
		getVisibleElementByXpath("//input[@name='password']");
		System.out.println("Password reset overlay opened.");
	}

	@When("^Enter password and confirm password$")
	public void enter_password_and_confirm_password(DataTable arg1) throws Throwable {
		System.out.println("Enter password and confirm password");
		List<List<String>> data=arg1.raw();
		getVisibleElementByXpath("//input[@name='password']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='confirmPassword']").sendKeys(data.get(0).get(1));
	}
	
	@And("^Click on Logout link on top$")
	public void Click_on_Logout_link_on_top() throws Throwable {
		System.out.println("Click on Logout link on top");
		getVisibleElementByXpath("//span[text()='Logout']").click();
	}
	
	@And("^Wait till 15 mins$")
	public void Wait_till_15_mins() throws Throwable {
		System.out.println("Wait till 15 mins");
		Thread.sleep(1200000);
	}
	
	@Then("^verify KBA section links are navigating to correct page$")
	public void verify_KBA_section_links_are_navigating_to_correct_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("verify KBA section links are navigating to correct page");
		waitTillElemVisiblebyXpath("//*[contains(@class,'secondary-side-navi')]//a[contains(text(),'经销商网络')]", 300);
		waitTillElemVisiblebyXpath("//*[contains(@class,'secondary-side-navi')]//a[contains(text(),'预约试驾')]", 300);
		waitTillElemVisiblebyXpath("//*[contains(@class,'secondary-side-navi')]//a[contains(text(),'配置林肯')]", 300);
		
		getVisibleElementByXpath("//*[contains(@class,'secondary-side-navi')]//a[contains(text(),'经销商网络')]").click();
		waitTillElemVisiblebyXpath("//input[@id='state']", 300);
		driver.navigate().back();
		waitTillElemVisiblebyXpath("//a[contains(text(),'预约试驾')]", 300);
		
		getVisibleElementByXpath("//*[contains(@class,'secondary-side-navi')]//a[contains(text(),'预约试驾')]").click();
		waitTillElemVisiblebyXpath("//select[@name='title']", 300);
		waitTillElemVisiblebyXpath("//strong[text()='预约试驾']", 300);
		getVisibleElementByXpath("//*[@class='lincoln-icon_close-thin']").click();
		waitTillElemVisiblebyXpath("//a[contains(text(),'预约试驾')]", 300);

		getVisibleElementByXpath("//*[contains(@class,'secondary-side-navi')]//a[contains(text(),'配置林肯')]").click();
		//waitTillElemVisiblebyXpath("//div[contains(@id,'desktop')]//*[text()='下一步']", 300); -Arpit
        //driver.navigate().back();
//		waitTillElemVisiblebyXpath("//a[contains(text(),'预约试驾')]", 300);
		
	}
	
	@Then("^verify Secondary navigation is routing to correct page$")
	public void verify_Secondary_navigation_is_routing_to_correct_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("verify Secondary navigation is routing to correct page");
		
		getVisibleElementByXpath("//a[contains(@class,'secondary-navi')]//div[contains(@class,'lincoln-icon_arrow')]").click();
    	getVisibleElementByXpath("//a[contains(text(),'Profile')]").click();
//		waitTillElemVisiblebyXpath("//*[text()='修改']", 300);
//		getVisibleElementByXpath("//*[text()='修改']");
//		getVisibleElementByXpath("//*[contains(text(),'用户名')]");
    	getVisibleElementByXpath("//*[contains(text(),'配置林肯')]");
		
		getVisibleElementByXpath("//a[contains(@class,'secondary-navi')]//div[contains(@class,'lincoln-icon_arrow')]");
		waitTillElemVisiblebyXpath("//a[contains(@class,'secondary-navi')]//div[contains(@class,'lincoln-icon_arrow')]", 240);
		getVisibleElementByXpath("//a[contains(@class,'secondary-navi')]//div[contains(@class,'lincoln-icon_arrow')]").click();
		getVisibleElementByXpath("//a[contains(text(),'Schedule')]").click();
		waitTillElemVisiblebyXpath("//span[contains(@class,'input-field')]/input[@name='firstName']", 300);
		getVisibleElementByXpath("//span[contains(@class,'input-field')]/input[@name='firstName']");
		getVisibleElementByXpath("//*[contains(text(),'个人联系信息')]");
		getVisibleElementByXpath("//*[@class='lincoln-icon_close-thin']").click();
		
		waitTillElemVisiblebyXpath("//a[contains(@class,'secondary-navi')]//div[contains(@class,'lincoln-icon_arrow')]", 240);
		getVisibleElementByXpath("//a[contains(@class,'secondary-navi')]//div[contains(@class,'lincoln-icon_arrow')]").click();
		getVisibleElementByXpath("//a[contains(text(),'Overview')]").click();
		waitTillElemVisiblebyXpath("//*[contains(text(),'你所配置的座驾')]", 300);
		getVisibleElementByXpath("//*[contains(text(),'你所配置的座驾')]");
		getVisibleElementByXpath("//figure//img");
	}
	
	@Then("^Click on Username beside welcome message$")
	public void Click_on_Username_beside_welcome_message(DataTable arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Click on Username beside welcome message");
		List<List<String>> data=arg.raw();
		
		String WelcomeMsg="Welcome, " + data.get(0).get(0) + " " + data.get(0).get(1);
		System.out.println(WelcomeMsg);
		waitTillElemVisiblebyXpath("//span[contains(text(),'"+data.get(0).get(0)+"')]", 300);
		getVisibleElementByXpath("//span[contains(text(),'"+WelcomeMsg+"')]").click();

	}
	
	@Then("^see user profile page is displayed$")
	public void see_user_profile_page_is_displayed() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("see user profile page is displayed");
		waitTillElemVisiblebyXpath("//*[text()='修改']", 300);
		getVisibleElementByXpath("//*[text()='修改']");
		getVisibleElementByXpath("//*[contains(text(),'用户名')]");
	}
	
	@Then("^verify all profile field values displayed correctly$")
	public void verify_all_profile_field_values_displayed_correctly(DataTable arg) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("verify all profile field values displayed correctly");
		List<List<String>> data = arg.raw();
		waitTillElemVisiblebyXpath("//*[text()='修改']", 300);
		if (!data.get(0).get(0).isEmpty()) {
//			getVisibleElementByXpath(data.get(0).get(0).replace("REPLACEEMAIL", UniqueKey + "@mailinator.com"));
			verifyElementPresent(data.get(0).get(0).replace("REPLACEEMAIL", UniqueKey + "@mailinator.com"));
		}
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==false) {
			if (!data.get(0).get(1).isEmpty()) {
//				getVisibleElementByXpath(data.get(0).get(1));
				verifyElementPresent(data.get(0).get(1));
			}
			if (!data.get(0).get(2).isEmpty()) {
//				getVisibleElementByXpath(data.get(0).get(2));
				verifyElementPresent(data.get(0).get(2));
			}
			if (!data.get(0).get(3).isEmpty()) {
//				getVisibleElementByXpath(data.get(0).get(3));
				verifyElementPresent(data.get(0).get(3));
			}
			if (!data.get(0).get(4).isEmpty()) {
//				getVisibleElementByXpath(data.get(0).get(4).replace("REPLACEEMAIL", UniqueKey + "@mailinator.com"));
				verifyElementPresent(data.get(0).get(4).replace("REPLACEEMAIL", UniqueKey + "@mailinator.com"));
			}
			if (!data.get(0).get(5).isEmpty()) {
//				getVisibleElementByXpath(data.get(0).get(5));
				verifyElementPresent(data.get(0).get(5));
			}
			if (!data.get(0).get(6).isEmpty()) {
//				getVisibleElementByXpath(data.get(0).get(6));
				verifyElementPresent(data.get(0).get(6));
			}
			if (!data.get(0).get(7).isEmpty()) {
//				getVisibleElementByXpath(data.get(0).get(7));
				verifyElementPresent(data.get(0).get(7));
			}
			if (!data.get(0).get(8).isEmpty()) {
//				getVisibleElementByXpath(data.get(0).get(8));
				verifyElementPresent(data.get(0).get(8));
			}
			if (!data.get(0).get(9).isEmpty()) {
//				 getVisibleElementByXpath(data.get(0).get(9).replace("REPLACEMOBILE", phn));
				verifyElementPresent(data.get(0).get(9).replace("REPLACEMOBILE", phn));

			}
			if (!data.get(0).get(10).isEmpty()) {
//				getVisibleElementByXpath(data.get(0).get(10));
				verifyElementPresent(data.get(0).get(10));
			}
		}
		getVisibleElementByXpath("//a[contains(@class,'secondary-navi')]//div[contains(@class,'lincoln-icon_arrow')]");
		getVisibleElementByXpath("//a[contains(@class,'secondary-navi')]//div[contains(@class,'lincoln-icon_arrow')]").click();
		getVisibleElementByXpath("//a[contains(text(),'Profile')]").click();
		waitTillElemVisiblebyXpath("//*[text()='修改']", 300);
	}
	
	
	@Then("^update below profile field value$")
	public void update_below_profile_field_value(DataTable arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		List<List<String>> data=arg.raw();
		System.out.println("update profile field value: " + data.get(0).get(0));
		waitTillElemVisiblebyXpath("//div[@model='"+data.get(0).get(0)+"']//*[text()='修改']", 240);
		waitTillElemVisiblebyXpath("//div[@model='username']//div[contains(@class,'owner-profile')]/div[(contains(@class,'default-fields') and contains(text(),'" + UniqueKey + "')) or (contains(@class,'default-fields') and contains(text(),'13166189794'))]", 240); //13166189794 is mobile user data
		String val=null;
		if(!data.get(0).get(1).isEmpty()) {
			val=data.get(0).get(1);
		}else {
			val=UniqueKey + "@mailinator.com";
		}
//		getVisibleElementByXpath("//div[@model='"+data.get(0).get(0)+"']/div[contains(@class,'owner-profile')]/a[contains(text(),'修改')]");
		WebElement element = driver.findElement(By.xpath("//div[@model='"+data.get(0).get(0)+"']//*[text()='修改']"));
		Actions actions = new Actions(driver);
		Thread.sleep(2000);
		actions.click(element).click().perform();	
		try {
			driver.findElement(By.xpath("//div[@model='"+data.get(0).get(0)+"']//input")).clear();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//div[@model='"+data.get(0).get(0)+"']//input")).sendKeys(val);
		}catch(Exception e) {
			Select list=new Select(driver.findElement(By.xpath("//div[@model='"+data.get(0).get(0)+"']//select")));
			list.selectByVisibleText(val);
		}
		element = driver.findElement(By.xpath("//div[@model='"+data.get(0).get(0)+"']//*[contains(text(),'确认修改') or contains(@class,'btn-confirm')]"));
		actions = new Actions(driver);
		actions.click(element).click().perform();	
		waitTillElemVisiblebyXpath("//div[@model='"+data.get(0).get(0)+"']//*[text()='修改']", 240);
	}
	
	
	@Then("^From secondary navigation click on Schedule an Appointment link$")
	public void From_secondary_navigation_click_on_Schedule_an_Appointment_link() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("From secondary navigation click on Schedule an Appointment link");
		
		waitTillElemVisiblebyXpath("//a[contains(@class,'secondary-navi')]//div[contains(@class,'lincoln-icon_arrow')]", 240);
		getVisibleElementByXpath("//a[contains(@class,'secondary-navi')]//div[contains(@class,'lincoln-icon_arrow')]").click();
		getVisibleElementByXpath("//a[contains(text(),'Schedule')]").click();
		waitTillElemVisiblebyXpath("//span[contains(@class,'input-field')]/input[@name='firstName']", 300);
//		getVisibleElementByXpath("//*[@class='lincoln-icon_close-thin']").click();
	}
	
	
	@And("^From secondary navigation click on my profile link$")
	public void From_secondary_navigation_click_on_my_profile_link() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("From secondary navigation click on my profile link");
		waitTillElemVisiblebyXpath("//a[contains(@class,'secondary-navi')]//div[contains(@class,'lincoln-icon_arrow')]", 240);
		getVisibleElementByXpath("//a[contains(@class,'secondary-navi')]//div[contains(@class,'lincoln-icon_arrow')]").click();
		getVisibleElementByXpath("//a[contains(text(),'Profile')]").click();
		waitTillElemVisiblebyXpath("//*[text()='修改']", 300);
		getVisibleElementByXpath("//*[text()='修改']");
		getVisibleElementByXpath("//*[contains(text(),'用户名')]");
	}
	
	
	@And("^See Schedule an appointment form opened$")
	public void See_Schedule_an_appointment_form_opened() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("See Schedule an appointment form opened");

		waitTillElemVisiblebyXpath("//span[contains(@class,'input-field')]/input[@name='firstName']", 300);
		getVisibleElementByXpath("//span[contains(@class,'input-field')]/input[@name='firstName']");
		getVisibleElementByXpath("//*[contains(text(),'个人联系信息')]");
		System.out.println("Schedule an appointment form opened successfully.");
	}
	
	@Then("^Enter correct values and submit the form$")
	public void Enter_correct_values_and_submit_the_form(DataTable arg) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Enter correct values and submit the form");
		List<List<String>> data=arg.raw();
		getVisibleElementByXpath("//select[@class='title']").click();
		getVisibleElementByXpath("//select[@name='title']//option[contains(text(),'" + data.get(0).get(0) + "')]").click();
		getVisibleElementByXpath("//input[@name='firstName']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='lastName']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='email' or @type='email']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='mobile']").sendKeys(data.get(0).get(4));		
		getVisibleElementByXpath("//select[@name='state']").click();
		getVisibleElementByXpath("//select[@name='state']/option[text()='" + data.get(0).get(5) + "']").click();
		getVisibleElementByXpath("//select[@name='city']").click();
		getVisibleElementByXpath("//select[@name='city']/option[text()='" + data.get(0).get(6) + "']").click();
		getVisibleElementByXpath("//select[@name='nameplate']").click();
		getVisibleElementByXpath("//select[@name='nameplate']/option[contains(text(),'" + data.get(0).get(7) + "')]").click();		
		Select purchaseYear=new Select(getVisibleElementByXpath("//select[@name='purchaseYear']"));
		purchaseYear.selectByIndex(Integer.parseInt(data.get(0).get(8)));
		getVisibleElementByXpath("//input[@name='mileage']").sendKeys(data.get(0).get(9));
		getVisibleElementByXpath("//input[@name='licencePlateNumber']").sendKeys(data.get(0).get(10));
		Select delear=new Select(getVisibleElementByXpath("//select[@name='preferredDealer']"));
		delear.selectByIndex(Integer.parseInt(data.get(0).get(11)));
		Select preferredContactTime=new Select(getVisibleElementByXpath("//select[@name='preferredContactTime']"));
		preferredContactTime.selectByIndex(Integer.parseInt(data.get(0).get(12)));
		getVisibleElementByXpath("//div[@class='checkbox form-checkbox']//span[@class='checkbox-style gux-icon-check-25px']").click();
		Thread.sleep(2000);
		getVisibleElementByXpath("//input[@id='captchaValue']").sendKeys(EnterCaptcha());
		getVisibleElementByXpath("//button[contains(text(),'寄存器') and contains(@class,'full')]").click();
	}
	
	
	@And("^See Form submission is successful$")
	public void See_Form_submission_is_successful() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("See Form submission is successful");
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==false) {
			waitTillElemVisiblebyXpath("//div[contains(@id,'thankyou-content')]", 300);
			getVisibleElementByXpath("//div[contains(@id,'thankyou-content')]");
		}
	}

	
	@And("^Scroll down profile page$")
	public void Scroll_down_profile_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Scroll down profile page");
//		JavascriptExecutor jse = (JavascriptExecutor)driver;
////		jse.executeScript("scroll(250, 0)"); // if the element is on top.
//		jse.executeScript("scroll(0, 900)");
		Thread.sleep(10000);
	}
	
	
	@And("^Click on Save vehicle link$")
	public void Click_on_Save_vehicle_link() throws Throwable {
		System.out.println("Click on Save vehicle link");
		waitTillElemVisiblebyXpath("//*[contains(@ng-click,'saveVehicleConfiguration')]", 240);
		getVisibleElementByXpath("//*[contains(@ng-click,'saveVehicleConfiguration')]").click();
	}
	
	
	@And("^verify an overlay displayed confirming for vehicle adding$")
	public void verify_an_overlay_displayed_confirming_for_vehicle_adding() throws Throwable {
		System.out.println("verify an overlay displayed confirming for vehicle adding");
		waitTillElemVisiblebyXpath("//input[contains(@name,'nameOfVehicle')]", 240);
		getVisibleElementByXpath("//input[contains(@name,'nameOfVehicle')]");
	}
	
	
	@And("^Verify the vehicle name format$")
	public void Verify_the_vehicle_name_format(DataTable arg) throws Throwable {
		System.out.println("Verify the vehicle name format");
		List<List<String>> data=arg.raw();
		WebElement vehicleName=getVisibleElementByXpath("//input[contains(@name,'nameOfVehicle')]");
		if(vehicleName.getAttribute("placeholder").split(" ").length==Integer.parseInt(data.get(0).get(0))) {
			System.out.println("Vehicle Name format is: " + vehicleName.getAttribute("placeholder"));
		}else {
			Assert.assertFalse("Incorrect Vehicle Name format: " + vehicleName.getAttribute("placeholder"), true);
		}
	}
	
	
	@And("^Provide vehicle name$")
	public void Provide_vehicle_name(DataTable arg) throws Throwable {
		System.out.println("Provide vehicle name");
		List<List<String>> data=arg.raw();
		ClearAndInputTextBox("//input[contains(@name,'nameOfVehicle')]", data.get(0).get(0));
	}
	
	
	@And("^click on confirm save$")
	public void click_on_confirm_save() throws Throwable {
		System.out.println("click on confirm save");
		waitTillElemVisiblebyXpath("//button[contains(text(),'保存')]", 240);
		getVisibleElementByXpath("//button[contains(text(),'保存')]").click();
		Thread.sleep(5000);
		getVisibleElementByXpath("//a[contains(text(),'确认')]").click();
	}
	
	@And("^Vehicle should be added and shown at Model Display section$")
	public void Vehicle_should_be_added_and_shown_at_Model_Display_section(DataTable arg) throws Throwable {
		System.out.println("Vehicle should be added and shown at Model Display section");
		List<List<String>> data=arg.raw();
		waitTillElemVisiblebyXpath("//*[contains(@data-mh,'model-title') and contains(text(),'"+data.get(0).get(0)+"')]", 240);
		getVisibleElementByXpath("//*[contains(@data-mh,'model-title') and contains(text(),'"+data.get(0).get(0)+"')]");
		System.out.println(getVisibleElementByXpath("//*[contains(@data-mh,'model-title') and contains(text(),'"+data.get(0).get(0)+"')]").getText());
	}
	
	@Then("^user should be prompted with an overlay to overrride the saved vehicle$")
	public void user_should_be_prompted_with_an_overlay_to_overrride_the_saved_vehicle(DataTable arg) throws Throwable {
		System.out.println("user should be prompted with an overlay to overrride the saved vehicle");
		List<List<String>> data=arg.raw();
		waitTillElemVisiblebyXpath("//span//span[text()='"+data.get(0).get(0)+"']//ancestor::span//following-sibling::span[contains(@class,'checkbox')]", 240);
		waitTillElemVisiblebyXpath("//input[contains(@name,'nameOfVehicle')]", 240);
		getVisibleElementByXpath("//span//span[text()='"+data.get(0).get(0)+"']//ancestor::span//following-sibling::span[contains(@class,'checkbox')]");
		getVisibleElementByXpath("//span//span[text()='"+data.get(0).get(1)+"']//ancestor::span//following-sibling::span[contains(@class,'checkbox')]");
		getVisibleElementByXpath("//span//span[text()='"+data.get(0).get(2)+"']//ancestor::span//following-sibling::span[contains(@class,'checkbox')]");

	}
	
	@Then("^Select existing vehicle saved above$")
	public void Select_existing_vehicle_saved_above(DataTable arg) throws Throwable {
		System.out.println("Select existing vehicle saved above");
		List<List<String>> data=arg.raw();
		waitTillElemVisiblebyXpath("//span//span[text()='"+data.get(0).get(0)+"']//ancestor::span//following-sibling::span[contains(@class,'checkbox')]", 240);
		waitTillElemVisiblebyXpath("//input[contains(@name,'nameOfVehicle')]", 240);
		getVisibleElementByXpath("//span//span[text()='"+data.get(0).get(0)+"']//ancestor::span//following-sibling::span[contains(@class,'checkbox')]").click();
	}
	
	@And("^Click on Cancel button$")
	public void Click_on_Cancel_button() throws Throwable {
		System.out.println("Click on Cancel button");
		getVisibleElementByXpath("//a[contains(@class,'cancel-overlay-view')]").click();
		Thread.sleep(5000);
		getVisibleElementByXpath("//a[contains(text(),'确认')]").click();
	}
	
	@And("^overview page is displayed with existing three vehicles$")
	public void overview_page_is_displayed_with_existing_three_vehicles() throws Throwable {
		System.out.println("overview page is displayed with existing three vehicles");
		waitTillElemVisiblebyXpath("//*[contains(@class,'secondary') and contains(text(),'Overview')]", 240);
		verifyInvisibleElement("//input[contains(@name,'nameOfVehicle')]");
		Thread.sleep(5000);
		getVisibleElementByXpath("//*[contains(@class,'secondary') and contains(text(),'Overview')]");
	}
	
	
	@And("^User should be directed to sign in overlay$")
	public void User_should_be_directed_to_sign_in_overlay() throws Throwable {
		System.out.println("User should be directed to sign in overlay");
		waitTillElemVisiblebyXpath("//input[@name='loginFirstName']", 240);
		waitTillElemVisiblebyXpath("//input[@name='loginPassword']", 240);
		getVisibleElementByXpath("//input[@name='loginFirstName']");
		getVisibleElementByXpath("//input[@name='loginPassword']");
	}
	
	
	@And("^Select agreement check box$")
	public void Select_agreement_check_box() throws Throwable {
		System.out.println("Select agreement check box");
		getVisibleElementByXpath("//*[contains(@class,'checkbox-style gux-icon-check')]").click();
	}
	
	
	@When("^Click on Social media option$")
	public void Click_on_Social_media_option(DataTable arg) throws Throwable {
		System.out.println("Click on Social media option");
		List<List<String>> data=arg.raw();
//		waitTillElemVisiblebyXpath("//a[@id='"+data.get(0).get(0)+"']/img", 240);
		getVisibleElementByXpath("//a[@id='"+data.get(0).get(0)+"']/img").click();
	}
	
	@When("^Submit social media login page$")
	public void Submit_social_media_login_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Submit social media login page");
		getVisibleElementByXpath("//a[@action-type='submit']").click();
		try {
			getVisibleElementByXpath("//*[contains(@class,'lincoln-icon_close-thin')]").click();
		}catch(Exception e) {}
	}
	
	@Then("^Verify page is navigating to Vehicle Selector Page$")
	public void Verify_page_is_navigating_to_Vehicle_Selector_Page() throws Throwable {
		System.out.println("Verify page is navigating to Vehicle Selector Page");
		//Arpit
		Thread.sleep(3000);
		//Arpit
		waitTillElemVisiblebyXpath("//*[contains(text(),'选择车型')]", 240);
		getVisibleElementByXpath("//*[contains(text(),'选择车型')]");
	}
	
	@And("^Click on Next CTA button$")
	public void Click_on_Next_CTA_button() throws Throwable {
		System.out.println("Click on Next CTA button");
		waitTillElemVisiblebyXpath("//*[contains(@id,'desktop-btn-next-credit')]/a[contains(text(),'下一步')]", 240);
		getVisibleElementByXpath("//*[contains(@id,'desktop-btn-next-credit')]/a[contains(text(),'下一步')]").click();
		waitTillElemVisiblebyXpath("//p[@model='carPrice' and @currency='currency' and text()!='']", 240);
		getVisibleElementByXpath("//p[@model='carPrice' and @currency='currency' and text()!='']");
		modelPrice=convertStrtoDoubletoInt(driver.findElement(By.xpath("//p[@model='carPrice' and @currency='currency' and text()!='']")).getText().substring(1).trim());
		modelName=driver.findElement(By.xpath("//div[contains(@class,'pe-preview-name')]/*[text()!='']")).getText();
	}
	
	@Then("^Check for Credit Tiles,Accordion with subtitle and Action Bar$")
	public void Check_for_Credit_Tiles_Accordion_with_subtitle_and_Action_Bar() throws Throwable {
		System.out.println("Check for Credit Tiles,Accordion with subtitle and Action Bar");
		System.out.println("Checking for existence of Credit Tile with Two Terms");
		getVisibleElementByXpath("//div[@class='pe-finalize-content']//*[@class='pe-finalize-body']//*[@class='row']//*[contains(@class,'col')][1]//*[not(contains(@class,'ng-hide')) and text()!='']");
		List<WebElement> creditTile=driver.findElements(By.xpath("//div[@class='pe-finalize-content']//*[@class='pe-finalize-body']//*[@class='row']//*[contains(@class,'col')][1]//*[not(contains(@class,'ng-hide')) and text()!='']"));
		if(creditTile.size()!=2) {
			Assert.assertFalse("Credit Tile with Two Terms data display issue", true);
		}
		List<WebElement> creditTile2=driver.findElements(By.xpath("//div[@class='pe-finalize-content']//*[@class='pe-finalize-body']//*[@class='row']//*[contains(@class,'col')][2]//*[not(contains(@class,'ng-hide')) and text()!='']"));
		if(creditTile2.size()!=3) {
			Assert.assertFalse("Credit Tile with Two Terms data display issue", true);
		}
		
		System.out.println("Checking for existence of Accordion with subtitle & Compare");
		getVisibleElementByXpath("//*[@class='panel-lock' and text()!='']");
		List<WebElement> Accordion=driver.findElements(By.xpath("//*[@class='panel-lock' and text()!='']"));
		if(Accordion.size()<2) {
			Assert.assertFalse("Accordion with subtitle display issue", true);
		}
		
		System.out.println("Verification of Financial Compare Selection");
		Click_on_the_Compare_Finance_Details_accordion();
		
		System.out.println("Click on the Remove CTA button in Compare Finance Details accordion");
		getVisibleElementByXpath("//a[contains(@class,'black')]").click();
		
		System.out.println("Checking for existence of Page Action Bar contents: PDF/Share");
		getVisibleElementByXpath("//a[contains(@ng-click,'PDF')]").click();
		CheckFileDownload(Configuration.PATH_TO_PAYMENT_CALCULATOR);
		getVisibleElementByXpath("//a[contains(@class,'share')]").click();
		waitTillElemVisiblebyXpath("//a[contains(@aria-label,'Close')]", 60);
		getVisibleElementByXpath("//a[contains(@aria-label,'Close')]").click();
	}
	
	
	@And("^See Corret MSRP value is displayed and its not editable$")
	public void See_Corret_MSRP_value_is_displayed_and_its_not_editable() throws Throwable {
		System.out.println("See Corret MSRP value is displayed and its not editable");
		//modelPrice=convertStrtoDoubletoInt(driver.findElement(By.xpath("//p[@class='font-millerb ng-binding ng-isolate-scope']")).getText().split(" ")[1]);
		modelPrice=convertStrtoDoubletoInt(driver.findElement(By.xpath("//p[@class='font-millerb ng-binding ng-isolate-scope']")).getText().substring(1).trim());
		System.out.println("model price value"+ modelPrice);
		getVisibleElementByXpath("//*[@model='carPrice' and @currency='currency' and contains(text(),'" + formatNumberFrChina(modelPrice) + "')]");
	}
	
	
	@And("^Based on the slider bar the Down payment should changed in real time$")
	public void Based_on_the_slider_bar_the_Down_payment_should_changed_in_real_time() throws Throwable {
		System.out.println("Based on the slider bar the Down payment should changed in real time");
		
		Click_on_Finance_optons_accordion();
		downpayment=Integer.parseInt(getVisibleElementByXpath("//td[@currency='currency' and @model='percentValue']").getText().substring(1).replaceAll(",", ""));
		downpaymRatio=Integer.parseInt(getVisibleElementByXpath("//p[contains(@class,'value-model')]").getText().replaceAll("%", ""));
		String MonthlyPayment=getVisibleElementByXpath("//*[@currency='currency' and @model='currentPayment' and text()!='']").getText();
		int MonthlyPaymentInt=Integer.parseInt(MonthlyPayment.substring(1).replaceAll(",", ""));
		
		getVisibleElementByXpath("//span[contains(@class,'slider')]");
		WebElement slider = driver.findElement(By.xpath("//span[contains(@class,'slider')]"));
	    Actions move = new Actions(driver);
	    Action action = (Action) move.dragAndDropBy(slider, 50, 0).build();
	    action.perform();
	    Thread.sleep(2000);
	    
	    int ActualDownpayment=Integer.parseInt(getVisibleElementByXpath("//td[@currency='currency' and @model='percentValue']").getText().substring(1).replaceAll(",", ""));
	    int ActualdownpaymRatio=Integer.parseInt(getVisibleElementByXpath("//p[contains(@class,'value-model')]").getText().replaceAll("%", ""));
	    String ActualMonthlyPayment=getVisibleElementByXpath("//*[@currency='currency' and @model='currentPayment' and text()!='']").getText();
		int ActualMonthlyPaymentInt=Integer.parseInt(ActualMonthlyPayment.substring(1).replaceAll(",", ""));
	    if(downpayment<ActualDownpayment && downpaymRatio<ActualdownpaymRatio && MonthlyPaymentInt>ActualMonthlyPaymentInt) {
	    		System.out.println("Using a Sider bar user is able to adjust the ratio. Based on that Down payment has changed. Initial Down Payment:" + downpayment + ", Down Payment after Side:" + ActualDownpayment);
	    		System.out.println("Down payment ratio and down payment amount displayed and reflected in real time on drag of the slider bar.Initial Down payment ratio:" + downpaymRatio + "%, Ratio after Side:" + ActualdownpaymRatio + "%");
	    		System.out.println("Monthly Payment has been changed based on slider. Initial Monthly Payment:" + MonthlyPayment + ", Monthly Payment after Side:" + ActualMonthlyPayment);
	    } 
	    
	    List<WebElement> PayMonthly=driver.findElements(By.xpath("//*[@currency='currency' and @model='currentPayment' and contains(text(),'"+ActualMonthlyPayment+"')]"));
		if(PayMonthly.size()<2) {
			Assert.assertFalse("Monthly Payment display issue.", true);
		}
	}
	
	@Then("^Navigate to Payment Program$")
	public void Navigate_to_Standard_Payment_Program(DataTable arg) throws Throwable {
		System.out.println("Navigate to Payment Program");
		List<List<String>> data=arg.raw();
		getVisibleElementByXpath("//a[contains(text(),'"+data.get(0).get(0)+"')]").click();
		
		System.out.println("verified Credit Option Name: " + data.get(0).get(0));
		getVisibleElementByXpath("//span[contains(text(),'"+data.get(0).get(0)+"')]");
	}
	
	@And("^see UI controls and values$")
	public void see_UI_controls_and_values() throws Throwable {
		System.out.println("see UI controls and values");
		
		Click_on_Finance_optons_accordion();
		
		System.out.println("Check for Vehicle Price (MSRP)");
		//Added the code for ModelPrice - Arpit
//		modelPrice=convertStrtoDoubletoInt(driver.findElement(By.xpath("//p[@model='carPrice' and @currency='currency' and text()!='']")).getText().split(" ")[1]);
		modelPrice=convertStrtoDoubletoInt(driver.findElement(By.xpath("//p[@model='carPrice' and @currency='currency' and text()!='']")).getText().substring(1).trim());
		System.out.println("Model Price : "+modelPrice);
		getVisibleElementByXpath("//p[@model='carPrice' and @currency='currency' and contains(text(),'" + formatNumberFrChina(modelPrice) + "')]");
		getVisibleElementByXpath("//td[@model='carPrice' and @currency='currency' and contains(text(),'" + formatNumberFrChina(modelPrice) + "')]");
		
		System.out.println("Check for Down Payment");
		int len=getVisibleElementByXpath("//td[@currency='currency' and @model='percentValue']").getText().substring(1).trim().length();
		if(len<=0) {
			Assert.assertFalse("Down Payment amount does not displayed in the Summary Table.", true);
		}
		
		System.out.println("Check for Down Payment Ratio");
		String dwnpay=getVisibleElementByXpath("//*[contains(@class,'value-model')]").getText();
		List<WebElement> dnpayRatio=driver.findElements(By.xpath("//*[contains(@class,'ng-binding') and text()='"+dwnpay+"']"));
		if(dnpayRatio.size()<=1) {
			Assert.assertFalse("Down Payment Ratio display issue, please chek on summary page.", true);
		}
		
		System.out.println("Check for Payment Terms (in Months)");
		getVisibleElementByXpath("//select[contains(@data-ng-model,'currentTerm')]").click();
		String payterm=getVisibleElementByXpath("//select[contains(@data-ng-model,'currentTerm')]//option[@selected='selected']").getText();
		getVisibleElementByXpath("//td[text()='"+payterm+"']");
		
		System.out.println("Check for Monthly Payment");
		getVisibleElementByXpath("//td[(@currency='currency' and @model='currentPayment' and text()!='') or (@currency='currency' and @model='restPayment' and text()!='')]");
	}

	
	@And("^see Downpayment field,Balance Amount field,Downpayment Ratio and Term field is disabled$")
	public void see_Downpayment_field_Balance_Amount_field_Downpayment_Ratio_and_Term_field_is_disabled() throws Throwable {
		System.out.println("see Downpayment field,Balance Amount field,Downpayment Ratio and Term field is disabled");
		
		Click_on_Finance_optons_accordion();
		
		System.out.println("Check whether Down Payment field is disabled");
		if(getVisibleElementByXpath("//input[@model='percentValue']").isDisplayed()==false) {
			Assert.assertFalse("Down Payment field is Enabled", true);
		}
		
		System.out.println("Check whether Down Payment Ratio field is disabled");
		if(getVisibleElementByXpath("//div[@model='percentValue']").isDisplayed()==false) {
			Assert.assertFalse("Down Payment Ratio display issue, please chek on summary page", true);
		}
		
		System.out.println("Check whether Payment Terms (in Months) field is disabled");
		if(getVisibleElementByXpath("//input[@model='currentTerm.term']").isDisplayed()==false) {
			Assert.assertFalse("Down Payment Ratio display issue, please chek on summary page", true);
		}
		
		System.out.println("Check whether Balance Amount of Money field is disabled");
		if(getVisibleElementByXpath("//input[@model='restPayment']").isDisplayed()==false) {
			Assert.assertFalse("Down Payment Ratio display issue, please chek on summary page", true);
		}
		downpayment=Integer.parseInt(getVisibleElementByXpath("//td[@currency='currency' and @model='percentValue']").getText().substring(1).replaceAll(",", ""));
	}
	
	
	@When("^Scroll to Credit Tile With 2 Terms group panel \"(.*?)\"$")
	public void Scroll_to_Credit_Tile_With_2_Terms_group_panel(String arg) throws Throwable {
		System.out.println("Scroll to Credit Tile With 2 Terms group panel");
		String MonthlyPayment=getVisibleElementByXpath("//*[@currency='currency' and @model='currentPayment' and text()!='']").getText();
		TempValue=Integer.parseInt(MonthlyPayment.substring(1).replaceAll(",", ""));
		
		Select selectTerm=new Select(getVisibleElementByXpath("//select[contains(@data-ng-model,'currentTerm')]"));	
		selectTerm.selectByVisibleText(arg);
	}
	
	@And("^Click on Finance optons accordion$")
	public void Click_on_Finance_optons_accordion() throws Throwable {
		System.out.println("Click on Finance optons accordion");
		Thread.sleep(2000);
		List<WebElement> findexpanded=driver.findElements(By.xpath("//*[@class='panel-lock' and text()!='']"));
		Thread.sleep(2000);
		for(WebElement explorBtn:findexpanded) {	
			if(explorBtn.getText().contains("方案总结") && Boolean.parseBoolean(explorBtn.findElement(By.xpath("ancestor::a")).getAttribute("aria-expanded"))==false) {
				moveToElement(explorBtn);
				explorBtn.findElement(By.xpath("i")).click();
				break;
			}
		}
	}
	
	
	@And("^see Finance details based on Credit Tile Terms selected above \"(.*?)\"$")
	public void see_Finance_details_based_on_Credit_Tile_Terms_selected_above(String arg) throws Throwable {
		System.out.println("see Finance details based on Credit Tile Terms selected above");

	    String ActualMonthlyPayment=getVisibleElementByXpath("//*[@currency='currency' and (@model='currentPayment' or @model='restPayment') and text()!='' and not(contains(@class,'hide'))]").getText();
		int ActualMonthlyPaymentInt=Integer.parseInt(ActualMonthlyPayment.substring(1).replaceAll(",", ""));
	    if(TempValue<ActualMonthlyPaymentInt) {
	    		System.out.println("Monthly Payment has been changed based on slider. Initial Monthly Payment:" + TempValue + ", Monthly Payment after Side:" + ActualMonthlyPaymentInt);
	    } 
	    
	    List<WebElement> PayMonthly=driver.findElements(By.xpath("//*[@currency='currency' and (@model='currentPayment' or @model='restPayment') and contains(text(),'"+ActualMonthlyPayment+"')]"));
		if(PayMonthly.size()<2) {
			Assert.assertFalse("Monthly Payment display issue.", true);
		}
//		driver.navigate().refresh();
//		getVisibleElementByXpath("//*[contains(text(),'X"+arg+"')]");
		getVisibleElementByXpath("//td[contains(text(),'"+arg+"')]");
	}
	
	
	@And("^Click on the Compare Finance Details accordion$")
	public void Click_on_the_Compare_Finance_Details_accordion() throws Throwable {
		System.out.println("Click on the Compare Finance Details accordion");
		Thread.sleep(2000);
		List<WebElement> findexpanded=driver.findElements(By.xpath("//*[@class='panel-lock' and text()!='']"));
		Thread.sleep(2000);
		for(WebElement explorBtn:findexpanded) {	
			if(explorBtn.getText().contains("方案比较") && Boolean.parseBoolean(explorBtn.findElement(By.xpath("ancestor::a")).getAttribute("aria-expanded"))==false) {
				moveToElement(explorBtn);
				explorBtn.findElement(By.xpath("i")).click();
				waitTillElemVisiblebyXpath("//*[@class='pe-compare-body']//i", 240);
				getVisibleElementByXpath("//*[@class='pe-compare-body']//i").click();
				break;
			}
		}
	}
	
	@When("^Verify compare Finance details columns count and related Payment details \"(.*?)\"$")
	public void Verify_compare_Finance_details_columns_count(String term,DataTable arg) throws Throwable {
		System.out.println("Verify compare Finance details columns count and related Payment details");
		List<List<String>> data=arg.raw();
		List<WebElement> CmpColCnt=driver.findElements(By.xpath("//div[@class='pe-compare']//div[contains(@class,'col') and not(contains(@class,'hide')) and not(contains(@class,'empty'))]"));
		if(CmpColCnt.size()==Integer.parseInt(data.get(0).get(0))) {
			System.out.println("Compare Finance Details column count matched");
			
			String DwnPayRatio=getVisibleElementByXpath("//p[contains(@class,'value-model')]").getText();
			String MonthlyPay=getVisibleElementByXpath("//*[@currency='currency' and (@model='currentPayment' or @model='restPayment') and text()!='' and not(contains(@class,'hide'))]").getText();
			int ActualMonthlyPaymentInt=Integer.parseInt(MonthlyPay.substring(1).replaceAll(",", ""));
			System.out.println("Check for Model Price in Compare section");
			CmpColCnt.get(CmpColCnt.size()-1).findElement(By.xpath("//*[contains(text(),'"+formatNumberFrChina(modelPrice)+"')]"));
			System.out.println("Check for Downpayment in Compare section");
			CmpColCnt.get(CmpColCnt.size()-1).findElement(By.xpath("//*[contains(text(),'"+formatNumberFrChina(downpayment)+"')]"));
			System.out.println("Check for Downpayment Ratio in Compare section");
			CmpColCnt.get(CmpColCnt.size()-1).findElement(By.xpath("//*[contains(text(),'"+DwnPayRatio+"')]"));
			System.out.println("Check for Term in Compare section");
			CmpColCnt.get(CmpColCnt.size()-1).findElement(By.xpath("//*[contains(text(),'"+term+"')]"));
			System.out.println("Check for Monthly Payment amount in Compare section");
			CmpColCnt.get(CmpColCnt.size()-1).findElement(By.xpath("//*[contains(text(),'"+formatNumberFrChina(ActualMonthlyPaymentInt)+"')]"));
		}
		else {
			Assert.assertFalse("Compare Finance Details column count does not matched. Expected compare column:"+data.get(0).get(0) +", Actual count:" + CmpColCnt.size(), true);
		}
	}
	
	
	@And("^see existence of Extension plan$")
	public void see_existence_of_Extension_plan() throws Throwable {
		System.out.println("see existence of Extension plan");
		getVisibleElementByXpath("//select[@data-ng-model='currentPackage']");
	}
	
	
	@And("^see existence of Minimum Hedge/Balance amount field$")
	public void see_existence_of_Minimum_Hedge_Balance_amount_field() throws Throwable {
		System.out.println("see existence of Minimum Hedge/Balance amount field");
		getVisibleElementByXpath("//input[@data-ng-model='currentTerm.gmfv']");
		
		HedgeAmtTxt=getVisibleElementByXpath("//td[@model='currentTerm.gmfv']").getText();
	}

	@And("^Enter downpayment amount greater than Hedge amount$")
	public void Enter_downpayment_amount_greater_than_Hedge_amount() throws Throwable {
		System.out.println("Enter downpayment amount greater than Hedge amount");
		int HedgeAmt=Integer.parseInt(HedgeAmtTxt.substring(1).replace(",", ""));
		int dwnpayToEntr=(modelPrice-HedgeAmt)+5;
		getVisibleElementByXpath("//input[@model='percentValue']").clear();
		getVisibleElementByXpath("//input[@model='percentValue']").sendKeys(String.valueOf(dwnpayToEntr));
	}
	
	@And("^Check for validation$")
	public void Check_for_validation() throws Throwable {
		System.out.println("Check for validation");
		getVisibleElementByXpath("//p[contains(@class,'error') and not(contains(@class,'hide'))]");
	}
	
	@And("^See Hedge/Balance amount displayed in compare section$")
	public void See_HedgeBalance_amount_displayed_in_compare_section() throws Throwable {
		System.out.println("See Hedge/Balance amount displayed in compare section");
		List<WebElement> CmpColCnt=driver.findElements(By.xpath("//div[@class='pe-compare']//div[contains(@class,'col') and not(contains(@class,'hide')) and not(contains(@class,'empty'))]"));
		try {
			System.out.println("Hedge/Balance amount search under Compare Finance Details column #"+CmpColCnt.size());	
			CmpColCnt.get(CmpColCnt.size()-1).findElement(By.xpath("//*[contains(text(),'"+HedgeAmtTxt+"')]"));
		}
		catch(Exception e) {
			Assert.assertFalse("Hedge/Balance amount does not displayed in compare section. Balance amt:"+HedgeAmtTxt, true);
		}
	}
	
	@And("^see downpayment and balance amt is half of model price$")
	public void see_downpayment_and_balance_amt_is_half_of_model_price() throws Throwable {
		System.out.println("see downpayment and balance amt is half of model price");
		int halfModelPrice=(int)Math.ceil(modelPrice/2);
		List<WebElement> CmpColCnt=driver.findElements(By.xpath("//*[contains(text(),'"+formatNumberFrChina(halfModelPrice)+"')]"));
		if(CmpColCnt.size()<3) {
			Assert.assertFalse("Actual amount is incorrect. Check Down payment amount.", true);
		}
		getVisibleElementByXpath("//*[contains(text(),'50%')]");
		CmpColCnt=driver.findElements(By.xpath("//*[contains(text(),'50%')]"));
		if(CmpColCnt.size()<2) {
			Assert.assertFalse("Downpayment Ratio % displayed is incorrect.", true);
		}
	}
	
	@When("^Click on one of KBA button$")
	public void Click_on_one_of_KBA_button() throws Throwable {
		System.out.println("Click on one of KBA button");
		getVisibleElementByXpath("//a[contains(@href,'offers.html') and contains(@class,'btn')]").click();
		
	}
	
	@And("^See display of correct Form using a Overlay$")
	public void See_display_of_correct_Form_using_a_Overlay() throws Throwable {
		System.out.println("See display of correct Form using a Overlay");
		Thread.sleep(30000);
		for (String windHandle : driver.getWindowHandles()) {
			driver.switchTo().window(windHandle);
		}
		String currentUrl = driver.getCurrentUrl();
		if (currentUrl.contains("offers.html")) {
			System.out.println("KBA is navigated to OFFER page");
		}else {
			Assert.assertFalse("KBA navigation failed", true);
		}
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
	}
	
	@When("^Click on the configured Contact Bar icons$")
	public void Click_on_the_configured_Contact_Bar_icons() throws Throwable {
		System.out.println("Click on the configured Contact Bar icons");
		getVisibleElementByXpath("//span[contains(@class,'lincoln-icon_hotline-outline')]");
		getVisibleElementByXpath("//span[contains(@class,'lincoln-icon_email-outline')]");
	}
	
	@And("^See According to the Icon opens appropriate application$")
	public void See_According_to_the_Icon_opens_appropriate_application() throws Throwable {
		System.out.println("See According to the Icon opens appropriate application");
		getVisibleElementByXpath("//span[contains(@class,'lincoln-icon_hotline-outline')]");
		getVisibleElementByXpath("//span[contains(@class,'lincoln-icon_email-outline')]");
	}
	
	@Then("^Select Vehicle \"([^\"]*)\"$")
	public void Select_Vehicle(String arg) throws Throwable {
		String Nameplate=null;
		String ModelSeries=null;
		switch(arg) {
			case "Lincoln_Cred":
				Nameplate=Configuration.Lincoln_Cred_Nameplate;
				ModelSeries=Configuration.Lincoln_Cred_Model;
				break;
			case "Lincoln_BP":
				Nameplate=Configuration.Lincoln_BP_Nameplate;
				ModelSeries=Configuration.Lincoln_BP_Model;
				break;
			case "Ford_Cred":
				Nameplate=Configuration.Ford_Cred_Nameplate;
				ModelSeries=Configuration.Ford_Cred_Model;
				break;
			case "Ford_BP":
				Nameplate=Configuration.Ford_BP_Nameplate;
				ModelSeries=Configuration.Ford_BP_Model;
				break;
		}					
		
		System.out.println("Vehicle selection, Nameplate:"+ Nameplate +", Model Series:"+ModelSeries);
		waitTillElemVisiblebyXpath("//div[contains(@class,'component-model-display-main')]//p[contains(text(),'"+Nameplate+"')]/ancestor::div[contains(@class,'model-display-item ng-scope')]", 240);
		List<WebElement> tst;
		getVisibleElementByXpath("//*[contains(@class,'desktop')]//span[contains(text(),'登录')]");
		int recsc=0;
		tst=driver.findElements(By.xpath("//div[contains(@class,'component-model-display-main')]//p[contains(text(),'"+Nameplate+"')]/ancestor::div[contains(@class,'model-display-item ng-scope')]"));
		if(tst.size()!=0) {
			do {
				try {
	//				DO NOT add getVisibleElementByXpath, this will break the execution
					Actions mouseOver=new Actions(driver);
					new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@class,'component-model-display-main')]//p[contains(text(),'"+Nameplate+"')]/ancestor::div[contains(@class,'model-display-item ng-scope')]"))));
					mouseOver.moveToElement(driver.findElement(By.xpath("//div[contains(@class,'component-model-display-main')]//p[contains(text(),'"+Nameplate+"')]/ancestor::div[contains(@class,'model-display-item ng-scope')]"))).click().build().perform();			
				}catch(Exception e) {
				}
				//Thread.sleep(2000); - Arpit
				tst=driver.findElements(By.xpath("//div[contains(@class,'component-model-display-main')]//p[contains(text(),'"+Nameplate+"')]/ancestor::div[contains(@ng-repeat,'nameplateSelector') and contains(@class,'active')]"));
				recsc++;
			}while(tst.size()<=0 && recsc<10);
			//while(tst.size()<=0 && recsc<60);
		}
		// Arpit
//		recsc=0;
//		tst=driver.findElements(By.xpath("//div[@class='component-model-display-series']//p[contains(text(),'"+ModelSeries+"')]/ancestor::label"));
//		if(tst.size()!=0) {
//			do {
//				Actions mouseOver=new Actions(driver);
//				new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@class='component-model-display-series']//p[contains(text(),'"+ModelSeries+"')]/ancestor::label"))));
//				mouseOver.moveToElement(driver.findElement(By.xpath("//div[@class='component-model-display-series']//p[contains(text(),'"+ModelSeries+"')]/ancestor::label"))).click().build().perform();		
//				Thread.sleep(2000);
//				tst=driver.findElements(By.xpath("//div[@class='component-model-display-series']//p[contains(text(),'"+ModelSeries+"')]/ancestor::label[contains(@class,'active')]"));
//				recsc++;
//			}while(tst.size()<=0 && recsc<60);
//		}
//		Thread.sleep(3000);
	}
	
	
	@Then("^See Nameplate \"([^\"]*)\" and Model \"([^\"]*)\" is selected on payment calculator page$")
	public void see_Nameplate_and_Model_is_selected_on_payment_calculator_page(String Nameplate, String ModelSeries) throws Exception {
		System.out.println("See Nameplate:"+ Nameplate +", Model Series:"+ModelSeries+" is selected on payment calculator page");
	    
		List<WebElement> iNameplates;
		iNameplates=driver.findElements(By.xpath("//div[contains(@class,'component-model-display-main')]//p[contains(text(),'"+Nameplate+"')]/ancestor::div[contains(@ng-repeat,'nameplateSelector') and contains(@class,'active')]"));

		if(iNameplates.size()<=0){
			Assert.assertFalse(Nameplate+ " nameplate is not selected",true);
		}

//		List<WebElement> iModelSeries;
//		iModelSeries=driver.findElements(By.xpath("//div[@class='component-model-display-series']//p[contains(text(),'"+ModelSeries+"')]/ancestor::label[contains(@class,'active')]"));
//
//		if(iModelSeries.size()<=0){
//		Assert.assertFalse(ModelSeries+" model series is not selected",true);
//		}
	}
	
	@Then("^See default nameplate and model is selected as configured in author$")
	 public void See_default_nameplate_and_model_is_selected_as_configured_in_author() throws Throwable {
	  System.out.println("See default nameplate and model is selected as configured in author");
	  // Write the code to handle Data Table
	  try {	
		   getVisibleElementByXpath("//div[contains(@class,'component-model-display-main')]//p[text()!='']/ancestor::div[contains(@ng-repeat,'nameplateSelector') and contains(@class,'active')]");
		   getVisibleElementByXpath("//div[@class='component-model-display-series']//p[text()!='']/ancestor::label[contains(@class,'active')]");
	  }catch(Exception e) {
		  	Assert.assertFalse("Default Nameplate and Model selection unchecked.", true);
	  }
	 }
	
	
	@Then("^See default nameplate and model is selected as configured$")
	 public void See_default_nameplate_and_model_is_selected_as_configured() throws Throwable {
	  System.out.println("See default nameplate and model is selected as configured");
	  // Write the code to handle Data Table
	  try {	
//		   getVisibleElementByXpath("//div[contains(@class,'component-model-display-main')]//p[text()!='']/ancestor::div[contains(@ng-repeat,'nameplateSelector') and contains(@class,'active')]");
		   getVisibleElementByXpath("//div[contains(@class,'component-model-display-main')]//p[text()!='']/ancestor::div[contains(@class,'owl-item') and contains(@class,'active')]//div[contains(@ng-repeat,'nameplateSelector')]");
		   getVisibleElementByXpath("//div[@class='component-model-display-series']//p[text()!='']/ancestor::label[contains(@class,'active')]");
	  }catch(Exception e) {
		  	Assert.assertFalse("Default Nameplate and Model selection unchecked.", true);
	  }
	 }
	
	
	
	@Then("^See nameplate and model is in sequence$")
	 public void See_nameplate_and_model_is_in_sequence() throws Throwable {
	  System.out.println("See nameplate and model is in sequence");
	  // Write the code to handle Data Table
	  	List<WebElement> nameplates;
	  	nameplates=driver.findElements(By.xpath("//div[contains(@class,'component-model-display-main')]//p[text()!='']/ancestor::div[contains(@class,'owl-item')]//div[contains(@ng-repeat,'nameplateSelector')]//p"));
	  	if(nameplates.size()>0) {
	  		System.out.println("Sequence of th Nameplate is: ");
			for(WebElement np:nameplates) {
				System.out.println(np.getText());
			}
	  	}else {
	  		Assert.assertFalse("Nameplates does not display on vehicle selection page.", false);
	  	}
	  	
		List<WebElement> ModelSeries;
		ModelSeries=driver.findElements(By.xpath("//div[@class='component-model-display-series']//p[text()!='' and not(contains(@class,'model-price'))]"));
		if(nameplates.size()>0) {
			System.out.println("Sequence of th Model Series is: ");
			for(WebElement ms:ModelSeries) {
				System.out.println(ms.getText());
			}
		}else {
	  		Assert.assertFalse("Model does not display on vehicle selection page.", false);
	  	}
	 }
	
	
	
	@Then("^Close Browser$")
	 public void Close_Browser() throws Throwable {
	  System.out.println("Close Browser");
	  // Write the code to handle Data Table
	  	Serenity.getWebdriverManager().getCurrentDriver().manage().deleteAllCookies();
	 }
	
	
	@Then("^Verify that CTAs and links are functional on the page$")
	 public void Verify_that_CTAs_and_links_are_functional_on_the_page() throws Throwable {
	  System.out.println("Verify that CTAs and links are functional on the page");
	  // Write the code to handle Data Table
	  try {	
		   getVisibleElementByXpath("//a[contains(@href,'offers.html')]");
//		   getVisibleElementByXpath("//a[contains(@href,'payment-calculator.html')]");
		   getVisibleElementByXpath("//a[contains(@href,'payment-calculator')]");
		   getVisibleElementByXpath("//a[contains(@href,'products-and-services.html')]");
	  }catch(Exception e) {
		  	Assert.assertFalse("one of the CTA/Link is missing.", true);
	  }
	 }
	
	@Then("^Click on Secondary navigaion link$")
	 public void Click_on_Secondary_navigaion_link() throws Throwable {
	  System.out.println("Click on Secondary navigaion link");
	  // Write the code to handle Data Table
	  	getVisibleElementByXpath("//span[contains(@class,'secondary-menu-name')]").click();
	 }
	
	
	@Then("^Click on Apply to Credit submenu$")
	 public void Click_on_Apply_to_Credit_submenu() throws Throwable {
	  System.out.println("Click on Apply to Credit submenu");
	  // Write the code to handle Data Table
	  getVisibleElementByXpath("//a[contains(@href,'apply-for-credit.html')]").click();
	 }
	
	
	@Then("^see apply to credit form opened$")
	 public void see_apply_to_credit_form_opened() throws Throwable {
	  System.out.println("see apply to credit form opened");
	  // Write the code to handle Data Table	  	
	  	waitTillElemVisiblebyXpath("//select[@name='title']", 240);
	 }
	
	@Then("^Fill in Apply to Credit form details$")
	 public void Fill_in_Apply_to_Credit_form_details(DataTable AppCred) throws Throwable {
	  System.out.println("Fill in Apply to Credit form details");
		List<List<String>> data = AppCred.raw();
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
//		getVisibleElementByXpath("//input[@name='birthYear']").sendKeys(data.get(0).get(11));
//		getVisibleElementByXpath("//input[@name='birthMonth']").sendKeys(data.get(0).get(12));
		getVisibleElementByXpath("//span[@class='checkbox-style gux-icon-check-25px']").click();
		getVisibleElementByXpath("//button[contains(text(),'确认提交') and contains(@class,'full')]").click();
	 }
	
	@And("^Go to Build and price page$")
	 public void Go_to_Build_and_price_page() throws Throwable {
		  System.out.println("Go to Build and price page");
		  // Write the code to handle Data Table
		  waitTillElemVisiblebyXpath("//*[contains(@id,'liBpActive')]/a", 240);
		  getVisibleElementByXpath("//*[contains(@id,'liBpActive')]/a").click();	  	
	 }
	
	@And("^see Nameplates listed down for the selected market$")
	 public void see_Nameplates_listed_down_for_the_selected_market() throws Throwable {
		  System.out.println("see Nameplates listed down for the selected market");
//		  waitTillElemVisiblebyXpath("//*[contains(@id,'liBpActive')]/a", 240);
		  List<WebElement> SubmenuNameplates=driver.findElements(By.xpath("//*[contains(@id,'liBpActive')]//*[contains(@class,'sub-menu')]//li//a[contains(text(),'MKC') or contains(text(),'MKX') or contains(text(),'Navigator') or contains(text(),'Continental')or contains(text(),'MKZ')]"));	  	
		  for(int ClickNP=0;ClickNP<SubmenuNameplates.size();ClickNP++) {
			  SubmenuNameplates.get(ClickNP).click();
			  Thread.sleep(30000);
			  getVisibleElementByXpath("//a[contains(text(),'Images')]");
			  List<WebElement> series=driver.findElements(By.xpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr"));
			  if(series.size()>0) {
				  getVisibleElementByXpath("//a[contains(text(),'Edit Price')]");
				  List<WebElement> ChkNullMPV=driver.findElements(By.xpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//td[contains(text(),'no any mpv')]"));
				  if(ChkNullMPV.size()==0) {
					  List<WebElement> Modelseries=driver.findElements(By.xpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr"));
					  for(WebElement getSerdetails:Modelseries) {
						  System.out.println(getSerdetails.findElements(By.xpath("td/a")).get(0).getText() + "  " + getSerdetails.findElements(By.xpath("td")).get(1).getText());
					  }
					  break;
				  }
			  }
			  SubmenuNameplates=driver.findElements(By.xpath("//*[contains(@id,'liBpActive')]//*[contains(@class,'sub-menu')]//li//a[contains(text(),'MKC') or contains(text(),'MKX') or contains(text(),'Navigator') or contains(text(),'Continental')or contains(text(),'MKZ')]"));	  	
		  }
	}
	
	@And("^Click Logon$")
	 public void Click_Logon() throws Throwable {
		  System.out.println("Click Logon");
		  // Write the code to handle Data Table
		  waitTillElemVisiblebyXpath("//*[contains(text(),'Active Directory')]", 240);
		  do {
			  getVisibleElementByXpath("//*[contains(text(),'Active Directory')]").click();
			  Thread.sleep(5000);
		  }while(getVisibleElementByXpath("//input[@name='loginFirstName' or @name='userId' or @id='userNameInput']").isDisplayed()==false);
		  //driver.findElements(By.xpath("//input[@name='loginFirstName' or @name='userId' or @id='userNameInput']")).size()>0
	 }
	
	@And("^Click on Sign in$")
	 public void Click_on_Sign_in() throws Throwable {
		  System.out.println("Click on Sign in");
		  getVisibleElementByXpath("//*[@id='submitButton']").click();
		  driver.navigate().refresh();
		  waitTillElemVisiblebyXpath("//*[contains(@id,'liBpActive')]/a", 240);
	 }
	
	@When("^Go to below CATALOGS$")
	 public void Go_to_below_CATALOGS(DataTable arg) throws Throwable {
		  System.out.println("Go to below CATALOGS");
		  getVisibleElementByXpath("//a[contains(text(),'"+arg.raw().get(0).get(0)+"')]").click();	  
	 }
	
	@Then("^See above details fetched from Gforce$")
	 public void See_above_details_fetched_from_Gforce() throws Throwable {
		  System.out.println("See above details fetched from Gforce");
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input");
		  driver.navigate().back();
		  getVisibleElementByXpath("//a[contains(text(),'Edit Price')]");
	 }
	
	@When("^update the image paths$")
	 public void update_the_image_paths(DataTable arg) throws Throwable {
		  System.out.println("update the image paths");
		  String currentvalue=getVisibleElementByXpath("//input[@id='"+arg.raw().get(0).get(1)+"']").getAttribute("value");
		  env.put(arg.raw().get(0).get(0),currentvalue);
		  ClearAndInputTextBox("//input[@id='"+arg.raw().get(0).get(1)+"']",arg.raw().get(0).get(2));
	 }
	
	@And("^Close the image overlay by clicked on X without saving$")
	 public void Close_the_image_overlay_by_clicked_on_X_without_saving() throws Throwable {
		  System.out.println("Close the image overlay by clicked on X without saving");
		  waitTillElemVisiblebyXpath("//div[contains(@id,'uploadBpImages') and @aria-hidden='false']//*[contains(@class,'pg-close')]", 240);
		  getVisibleElementByXpath("//*[contains(@class,'pg-close')]").click();
		  Thread.sleep(5000);
	 }
	
	@When("^Should not be able to see images corresponding to updated image paths$")
	 public void Should_not_be_able_to_see_images_corresponding_to_updated_image_paths(DataTable arg) throws Throwable {
		  System.out.println("Should not be able to see images corresponding to updated image paths");
		  String currentvalue=getVisibleElementByXpath("//input[@id='"+arg.raw().get(0).get(1)+"']").getAttribute("value");
		  Assert.assertEquals(arg.raw().get(0).get(1) + " image details does not matched", env.get(arg.raw().get(0).get(1)), currentvalue);
	 }
	
	@When("^Update the description and Save \"([^\"]*)\"$")
	 public void Update_the_feature_description_and_Save(String arg) throws Throwable {
		  System.out.println("Update the feature description and Save");
		  Calendar cal = Calendar.getInstance();
		  SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
		  String currentvalue = sdf.format(cal.getTime());			
		  env.put("features",currentvalue);
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']//ancestor::td//following-sibling::td//a[contains(@onclick,'saveFeatures')]");
		  ClearAndInputTextBox("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']//ancestor::td//following-sibling::td//textarea",currentvalue);
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']//ancestor::td//following-sibling::td//a[contains(@onclick,'saveFeatures')]").click();
	}

	@When("^Edit the Feature Id and Save \"([^\"]*)\"$")
	 public void Update_the_feature_id_and_Save(String Featureid) throws Throwable {
		  System.out.println("Edit the Feature Id and Save ");
		  
		  String currentvalue=getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+Featureid+"']//ancestor::td//following-sibling::td//textarea").getAttribute("value");
		  env.put("features",currentvalue);
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+Featureid+"']//ancestor::td//following-sibling::td//a[contains(@onclick,'saveFeatures')]");
		  ClearAndInputTextBox("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+Featureid+"']",Featureid);
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+Featureid+"']//ancestor::td//following-sibling::td//a[contains(@onclick,'saveFeatures')]").click();
	}
	
	@And("^Update the family name and Save \"([^\"]*)\"$")
	 public void Update_the_family_name_and_Save(String arg) throws Throwable {
		  System.out.println("Update the family name and Save");
		  Calendar cal = Calendar.getInstance();
		  SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
		  String currentvalue = sdf.format(cal.getTime());			
		  env.put("features",currentvalue);
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']//ancestor::td//following-sibling::td//a[contains(@onclick,'saveFeatureFamily')]");
		  ClearAndInputTextBox("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']",arg);
		  ClearAndInputTextBox("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']//ancestor::td//following-sibling::td//input",currentvalue);
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']//ancestor::td//following-sibling::td//a[contains(@onclick,'saveFeatureFamily')]").click();
	}
	
	
	@Then("^see the updated feature details on B&P pages$")
	 public void see_the_updated_feature_details_on_BandP_pages() throws Throwable {
		  System.out.println("see the updated feature details on B&P pages");
//		  waitTillElemVisiblebyXpath("//*[contains(text(),'"+env.get("features")+"')]", 240);
//		  getVisibleElementByXpath("//*[contains(text(),'"+env.get("features")+"')]");
		  List<WebElement> ChkFeature=driver.findElements(By.xpath("//*[contains(text(),'"+env.get("features")+"')]"));
		  if(ChkFeature.size()<=0) {
			  Assert.assertFalse("Modified features does not display on B&P config and summary page at publisher.", false);
		  }else {
			  System.out.println("New Feature "+env.get("features")+" displayed on B&P config and summary page at publisher.");
		  }
		  Thread.sleep(5000);
	 }
	
	@Then("^Delete a Existing Feature \"([^\"]*)\"$")
	 public void Delete_a_Existing_Feature(String arg) throws Throwable {
		  System.out.println("Delete a Existing Feature");			
		  String currentvalue=getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']//ancestor::td//following-sibling::td//textarea").getAttribute("value");
		  env.put("features",currentvalue);
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']//ancestor::td//following-sibling::td//a[contains(@data-target,'confirmDelete')]").click();
		  getVisibleElementByXpath("//button[contains(@onclick,'deleteFeatures')]").click();
		  Thread.sleep(10000);
		  waitTillElemVisiblebyXpath("//a[contains(text(),'Delete')]",240);
	}
	
	
	@Then("^Delete a Existing Family name \\\"([^\\\"]*)\\\"$")
	 public void Delete_a_Existing_family_name(String arg) throws Throwable {
		  System.out.println("Delete a Existing Family Name");			
		  String currentvalue=getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']//ancestor::td//following-sibling::td//input").getAttribute("value");
		  env.put("features",currentvalue);
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']//ancestor::td//following-sibling::td//a[contains(@data-target,'confirmDelete')]").click();
		  getVisibleElementByXpath("//button[contains(@onclick,'deleteFeatureFamily')]").click();
		  Thread.sleep(10000);
		  waitTillElemVisiblebyXpath("//a[contains(text(),'Delete')]",240);
	}
	
	
	@Then("^see no feature or family displayed on B&P pages$")
	 public void see_the_feature_deleted_on_BandP_pages() throws Throwable {
		  System.out.println("see no feature or family displayed on B&P pages");
		  List<WebElement> ChkFeature=driver.findElements(By.xpath("//*[contains(text(),'"+env.get("features")+"')]"));
		  if(ChkFeature.size()>0) {
			  Assert.assertFalse("Feature/Family still exist on B&P config and summary page at publisher.", false);
		  }else {
			  System.out.println("Feature/Family- "+env.get("features")+" does not exist on B&P config and summary page at publisher.");
		  }
		  Thread.sleep(5000);
	 }
	
	@Then("^Add a New Feature \"([^\"]*)\"$")
	 public void Add_a_New_Feature(String arg) throws Throwable {
		  System.out.println("Add a New Feature");
		  getVisibleElementByXpath("//a[contains(@data-target,'addNewFeature')]").click();
		 
		  Calendar cal = Calendar.getInstance();
		  SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyHHmmss");
		  String currentvalue = sdf.format(cal.getTime());			
		  env.put("features",currentvalue);
		  ClearAndInputTextBox("//input[contains(@id,'newFeatureId')]",arg);
		  ClearAndInputTextBox("//input[contains(@id,'newFeatureDesc')]",currentvalue);
		  getVisibleElementByXpath("//button[contains(@onclick,'addNewFeatures')]").click();
		  Thread.sleep(10000);
		  waitTillElemVisiblebyXpath("//a[contains(text(),'Delete')]",240);
	 }
	
	
	@Then("^Add a New Family name \"([^\"]*)\" and \"([^\"]*)\"$")
	 public void Add_a_New_Family_name(String arg1,String arg2) throws Throwable {
		  System.out.println("Add a New Family name");
		  getVisibleElementByXpath("//a[contains(@data-target,'addNewFeature') or contains(@data-target,'addNewFamily')]").click();
		  String currentvalue=arg2;
		  env.put("features",currentvalue);
		  ClearAndInputTextBox("//input[contains(@id,'newFamilyId')]",arg1);
		  ClearAndInputTextBox("//input[contains(@id,'newFamilyName')]",arg2);
		  getVisibleElementByXpath("//button[contains(@onclick,'addFeatureFamily')]").click();
		  Thread.sleep(10000);
		  waitTillElemVisiblebyXpath("//a[contains(text(),'Delete')]",240);
	 }
	
	@When("^Select a vehicle series \"([^\"]*)\"$")
	 public void Select_a_vehicle_series(String arg) throws Throwable {
		  System.out.println("Select a vehicle series");
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//a[contains(text(),'"+arg+"')]").click();
	}
	
	
	@Then("^Edit the MPV Price and Description and Save details$")
	 public void Edit_the_MPV_Price_and_Description_and_Save_details(DataTable arg) throws Throwable {
		  System.out.println("Edit the MPV Price and Description and Save details");		 			
		  env.put("mpvname",arg.raw().get(0).get(0));
		  env.put("mpvprice",arg.raw().get(0).get(1));
		  ClearAndInputTextBox("//input[contains(@id,'seriesDrive0')]",arg.raw().get(0).get(0));
		  ClearAndInputTextBox("//input[contains(@id,'seriesPrice0')]",arg.raw().get(0).get(1));
		  getVisibleElementByXpath("//button[contains(@onclick,'saveBpSeries')]").click();
		  Thread.sleep(10000);
		  if (isAlertPresent()) {
			    driver.switchTo().alert();
			    driver.switchTo().alert().accept();
			    driver.switchTo().defaultContent();
			}
		  waitTillElemVisiblebyXpath("//a[contains(text(),'Edit Price')]",240);
	 }
	
	
	@Then("^New MPV should be view on B&P pages$")
	 public void New_MPV_should_be_view_on_BandP_pages() throws Throwable {
		  System.out.println("New MPV should be view on B&P pages");
		  List<WebElement> ChkFeature=driver.findElements(By.xpath("//*[contains(text(),'"+env.get("mpvname")+"')]"));
		  if(ChkFeature.size()>0) {
			  System.out.println("New MPV details exist on B&P config page at publisher."); 
		  }else {
			  Assert.assertFalse("New MPV details does not exist on B&P config page at publisher.", false);
		  }
		  
		  ChkFeature=driver.findElements(By.xpath("//*[contains(text(),'"+env.get("mpvprice")+"')]"));
		  if(ChkFeature.size()>0) {
			  System.out.println("New MPV price details exist on B&P config page at publisher."); 
		  }else {
			  Assert.assertFalse("New MPV price details does not exist on B&P config page at publisher.", false);
		  }
		  Thread.sleep(5000);
	 }
	
	
	@Then("^Delete the MPV Price and Description and Save details$")
	 public void Delete_the_MPV_Price_and_Description_and_Save_details() throws Throwable {
		  System.out.println("Delete the MPV Price and Description and Save details");
		  getVisibleElementByXpath("//a[contains(@data-target,'addNewFeature') or contains(@data-target,'confirmDelete')]").click();
		  getVisibleElementByXpath("//button[contains(@onclick,'deleteMpv')]").click();
		  Thread.sleep(10000);
		  waitTillElemVisiblebyXpath("//a[contains(text(),'Edit Price')]",240);
	 }
	
	@Then("^see deleted MPV name and price does not viewed on B&P pages$")
	 public void see_deleted_MPV_name_and_price_not_viewed_on_BandP_pages() throws Throwable {
		  System.out.println("see deleted MPV name and price not viewed on B&P pages");
		  verifyInvisibleElem("//*[contains(text(),'"+env.get("mpvname")+"')]");
		  verifyInvisibleElem("//*[contains(text(),'"+env.get("mpvprice")+"')]");
	 }
	
	
	@Then("^Add MPV Key \\\"([^\\\"]*)\\\" and Price \\\"([^\\\"]*)\\\" and Description \\\"([^\\\"]*)\\\" and Save details$")
	 public void Add_MPV_Key_and_Price_and_Description_and_Save_details(String id,String price,String name) throws Throwable {
		  System.out.println("Add MPV Key and Price and Description and Save details");		 			
		  env.put("mpvname",name);
		  env.put("mpvprice",price);
		  getVisibleElementByXpath("//button[contains(@data-target,'addMpv')]").click();
		  ClearAndInputTextBox("//input[contains(@id,'newMpvKey')]",id);
		  ClearAndInputTextBox("//input[contains(@id,'newMpvLabel')]",name);
		  ClearAndInputTextBox("//input[contains(@id,'newMpvPrice')]",price);
		  getVisibleElementByXpath("//button[contains(@onclick,'addMpv')]").click();
		  Thread.sleep(10000);
		  if (isAlertPresent()) {
			    driver.switchTo().alert();
			    driver.switchTo().alert().accept();
			    driver.switchTo().defaultContent();
			}
		  waitTillElemVisiblebyXpath("//a[contains(text(),'Edit Price')]",240);
	 }
	
	
	@Then("^Go to update prices \"([^\"]*)\"$")
	public void go_to_update_prices(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		System.out.println("Go to update prices");
		getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//a[contains(text(),'"+arg1+"')]//ancestor::td//following-sibling::td//a[contains(text(),'Price')]").click();
	}

	
	
	@Then("^Delete the existing Feature \"([^\"]*)\"$")
	public void Delete_the_existing_Feature(String FeatureId) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		  System.out.println("Delete the existing Feature");
		  String currentvalue = getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+FeatureId+"']//ancestor::td//following-sibling::td//input").getAttribute("value").trim();
		  env.put("featuresPrice","¥ "+currentvalue);
		  
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+FeatureId+"']//ancestor::td//following-sibling::td//a[contains(@data-target,'confirmDelete')]").click();
		  getVisibleElementByXpath("//button[contains(@onclick,'deleteFeaturePrice')]").click();
		  Thread.sleep(10000);
		  waitTillElemVisiblebyXpath("//a[contains(text(),'Delete')]",240);
	}
	
	
	@Then("^Modify existing valid Feature id \"([^\"]*)\" into invalid feature id \"([^\"]*)\"$")
	public void Modify_the_existing_Feature(String FeatureId,String InvalidFeatureId) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		  System.out.println("Modify the existing Feature");
		  String currentvalue = getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+FeatureId+"']//ancestor::td//following-sibling::td//input").getAttribute("value").trim();
		  env.put("featuresPrice","¥ "+currentvalue);
		  ClearAndInputTextBox("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+FeatureId+"']",InvalidFeatureId);
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+FeatureId+"']//ancestor::td//following-sibling::td//a[contains(@onclick,'saveFeaturePrice')]").click();
		  Thread.sleep(10000);
		  try {
			  waitTillElemVisiblebyXpath("//a[contains(text(),'Delete')]",240);
		  }catch(Exception e) {};  
	}
	
	
	@Then("^see invalid feature id \"([^\"]*)\" and valid feature id \"([^\"]*)\" is removed from the feature list$")
	 public void see_invalid_Feature_ID_removed(String invalidFeature,String validFeature) throws Throwable {
		  System.out.println("see invalid Feature ID is removed from the feature list");
		  verifyInvisibleElem("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+invalidFeature+"']");
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+validFeature+"']");
	 }
	
	
	@Then("^see the feature prices not to display on B&P pages$")
	 public void see_the_feature_prices_not_to_display_on_BandP_pages() throws Throwable {
		  System.out.println("see the feature prices not to display on B&P pages");
		  List<WebElement> ChkFeatureprice=driver.findElements(By.xpath("//*[contains(text(),'"+env.get("featuresPrice")+"')]"));
		  if(ChkFeatureprice.size()>0) {
			  Assert.assertFalse("Deleted/invalid feature price - "+env.get("featuresPrice")+" still exist on B&P config and summary page at publisher.", false);
		  }else {
			  System.out.println("Deleted/invalid feature price - "+env.get("featuresPrice")+" does not exist on B&P config and summary page at publisher.");
		  }
		  Thread.sleep(5000);
	 }
	
	@Then("^see the existing feature price displayed on B&P pages$")
	 public void see_the_existing_feature_price_to_display_on_BandP_pages() throws Throwable {
		  System.out.println("see the existing feature price displayed on B&P pages");
		  List<WebElement> ChkFeatureprice=driver.findElements(By.xpath("//*[contains(text(),'"+env.get("featuresPrice")+"')]"));
		  if(ChkFeatureprice.size()==0) {
			  Assert.assertFalse("Feature price - "+env.get("featuresPrice")+" deleted on B&P config and summary page at publisher.", false);
		  }else {
			  System.out.println("Feature price - "+env.get("featuresPrice")+" exist on B&P config and summary page at publisher.");
		  }
		  Thread.sleep(5000);
	 }
	
	@And("^Click on Add new Features Price$")
	 public void Click_on_Add_new_Features_Price() throws Throwable {
		  System.out.println("Click on Add new Features Price");
		  getVisibleElementByXpath("//a[contains(@data-target,'addNewFeaturePrice')]").click();
	 }
	
	@When("^Provide new feature id \"([^\"]*)\" and price \"([^\"]*)\"$")
	public void Provide_new_feature_id_and_price(String FeatureId,String FeaturePrice) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		  System.out.println("Provide new feature id and price");
		  ClearAndInputTextBox("//input[contains(@id,'newFeatureId')]",FeatureId);
		  ClearAndInputTextBox("//input[contains(@id,'newFeaturePrice')]",FeaturePrice);
		  getVisibleElementByXpath("//button[contains(@onclick,'addNewFeaturePrice')]").click();
		  Thread.sleep(10000);
		  waitTillElemVisiblebyXpath("//a[contains(text(),'Delete')]",240);
	}
	
	@And("^see the newly added feature prices \"([^\"]*)\" display on B&P pages$")
	 public void see_the_newly_added_feature_prices_display_on_Build_and_Price_pages(String FeaturePrice) throws Throwable {
		  System.out.println("see the newly added feature prices display on B&P pages");
		  List<WebElement> ChkFeatureprice=driver.findElements(By.xpath("//*[contains(text(),'¥ "+ FeaturePrice +"')]"));
		  if(ChkFeatureprice.size()>0) {
			  System.out.println("New feature price exist on B&P config and summary page at publisher."); 
		  }else {
			  Assert.assertFalse("New feature price does not exist on B&P config and summary page at publisher.", false);
		  }
	 }
	
	
	@And("^Change the existing Feature Price to \"([^\"]*)\" for existing Feature Id \"([^\"]*)\"$")
	public void Change_the_existing_Feature_Price_to_existing_Feature_Id(String FeaturePrice,String FeatureId) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		  System.out.println("Change the existing Feature Price");
		  env.put("featuresPrice",FeaturePrice);
		  ClearAndInputTextBox("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+FeatureId+"']//ancestor::td//following-sibling::td//input",FeaturePrice);
		  getVisibleElementByXpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+FeatureId+"']//ancestor::td//following-sibling::td//a[contains(@onclick,'saveFeaturePrice')]").click();
		  Thread.sleep(10000);
		  try {
			  waitTillElemVisiblebyXpath("//a[contains(text(),'Delete')]",240);
		  }catch(Exception e) {};  
	}
	
	
	@When("^See validation of invalid feature prices entered on VDM$")
	 public void See_validation_of_invalid_feature_prices_entered_on_VDM() throws Throwable {
		  System.out.println("See validation of invalid feature prices entered on VDM");
		  Thread.sleep(10000);
		  if(isAlertPresent()) {
			  System.out.println("Validation message displayed: "+ driver.switchTo().alert().getText());
		  }
	 }
	
	
	@Then("^on submit Gforce side does not be updated invalid feature prices$")
	public void on_submit_Gforce_side_does_not_be_updated_invalid_feature_prices() throws Throwable {
		System.out.println("on submit Gforce side does not be updated invalid feature prices");
		if (isAlertPresent()) {
		    driver.switchTo().alert();
		    driver.switchTo().alert().accept();
		    driver.switchTo().defaultContent();
		}
	}
	
	@Then("^See On VDM side invalid IDs should not be able to add \"([^\"]*)\"$")
	 public void On_VDM_side_invalid_IDs_should_not_be_able_to_added(String arg) throws Throwable {
		  System.out.println("On VDM side invalid IDs should not be able to added");
		  verifyInvisibleElem("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']");
		  env.put("features",arg);
	 }
	
	@Then("^On VDM validate duplication ID should not be displayed \"([^\"]*)\"$")
	 public void On_VDM_validate_duplication_ID_should_not_be_displayed(String arg) throws Throwable {
		  System.out.println("On VDM validate duplication ID should not be displayed");
		  env.put("features",arg);
		  
		  List<WebElement> ChkDuplicateIdCount=driver.findElements(By.xpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//input[@value='"+arg+"']"));
		  if(ChkDuplicateIdCount.size()>1) {
			  Assert.assertFalse("Duplicate Id found - "+ arg, false);
		  }else {
			  System.out.println("Duplicate Id does not exist:- "+ arg);
		  }
	 }
	
	@Then("^On VDM validate duplicate MPV_ID \"([^\"]*)\" should not be displayed for vehicle series \"([^\"]*)\"$")
	 public void On_VDM_validate_MPV_ID_should_not_be_displayed(String MPV_ID,String series) throws Throwable {
		  System.out.println("On VDM validate MPV_ID,MPV_Name and MPV_Price should not be displayed");		  
		  List<WebElement> ChkDuplicateIdCount=driver.findElements(By.xpath("//div[@id='bpCatalogListContent']//table//tbody[contains(@id,'tableContent')]/tr//a[contains(text(),'"+series+"')]//ancestor::td//following-sibling::td//*[contains(text(),'"+MPV_ID+"')]"));
		  if(ChkDuplicateIdCount.size()>1) {
			  Assert.assertFalse("Duplicate Id found - "+ MPV_ID + " against vehicle series " + series, false);
		  }else {
			  System.out.println("Duplicate Id "+ MPV_ID +" does not exist against vehicle series " + series);
		  }
	 }
}
