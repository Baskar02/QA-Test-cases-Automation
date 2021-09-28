package com.ford.automation.p1_general;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.interactions.internal.Locatable;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ford.automation.base.BaseTest;

import config.Configuration;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import com.mkyong.rest.*;

public class P1General extends BaseTest {
	private static String CompareModelname=null,NewsrmArt=null,Newsrmdt=null,Newsrmdata=null;
	private int Temp=0;
	private HashMap<String,Integer> showdiff=new HashMap<String,Integer>();
	@Given("^Open FireFox browser on P1General$")
	public void openFireFoxBrowser() throws Throwable {
		System.out.println("Open FireFox browsers.");
		System.setProperty("webdriver.gecko.driver", Configuration.PATH_TO_GECKO_DRIVER);
		driver = new FirefoxDriver();

	}

	@Given("^Open FireFox browser$")
	public void openFireFoxBrowser2() throws Throwable {
		System.out.println("Open FireFox browser");
		driver = new FirefoxDriver();

	}

	@Given("^Open Chrome browser on P1General$")
	public void openChromeBrowser() throws Throwable {
		System.out.println("Open Chrome browser");
		//		System.setProperty("webdriver.chrome.driver", Configuration.PATH_TO_CHROME_DRIVER);
		//		ChromeOptions options = new ChromeOptions();
		//		if(System.getProperty("os.name").toLowerCase().contains("linux")) {
		//			System.out.println("Setting up ChromeOptions");
		//			options.setBinary("/usr/bin/google-chrome-stable");
		//			options.addArguments("--headless");
		//		}
		//		options.addArguments("--start-maximized");
		//		driver = new ChromeDriver(options);
		// driver = new ChromeDriver();
		////		driver.manage().timeouts().pageLoadTimeout(240, TimeUnit.SECONDS);
		////       driver.manage().timeouts().setScriptTimeout(240, TimeUnit.SECONDS);
	}

	@Given("^Open \"(.*?)\" browser$")
	public void openSpecificBrowser(String browserName) throws Throwable {
		if ("FireFox".equals(browserName)) {
			openFireFoxBrowser();
		} else if ("Chrome".equals(browserName)) {
			openChromeBrowser();
		}
	}

	@When("^Maximize browser and enter link \"(.*?)\"$")
	public void openTestLink(String link) throws Throwable {
		System.out.println("Maximize browser and enter link");
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.open('','testwindow','width=400,height=200')");
		driver.close();
		driver.switchTo().window("testwindow");
		js.executeScript("window.moveTo(0,0);");
		js.executeScript("window.resizeTo(1450,1000);");
		driver.manage().window().maximize();
		driver.get(getProfileURL(link));
	}

	@When("^Redirect to link \"(.*?)\" on P1$")
	public void redirect(String link) throws Throwable {
		System.out.println("Redirect to link");
		driver.navigate().refresh();
		Thread.sleep(5000);
		driver.get(getProfileURL(link));
		Thread.sleep(5000);
		//		if (isAlertPresent()) {
		//		    driver.switchTo().alert();
		//		    driver.switchTo().alert().accept();
		//		    driver.switchTo().defaultContent();
		//		}
	}

	@When("^Maximize browser and enter link \"(.*?)\" and check if link is broken$")
	public void openTestLinkAndCheckIfLinkIsBroken(String link) throws Throwable {
		System.out.println("Maximize browser and enter link and check if link is broken");
		driver.manage().window().maximize();
		driver.get(getProfileURL(link));
		List<WebElement> allImages = findAllLinks(driver);
		System.out.println("Total number of elements found: " + allImages.size());

		for (WebElement element : allImages) {
			try {
				System.out.println("URL: " + element.getAttribute("href") + " returned "
						+ isLinkBroken(new URL(element.getAttribute("href"))));

			} catch (Exception exp) {
				System.out.println(
						"At " + element.getAttribute("innerHTML") + " Exception occured -&gt; " + exp.getMessage());
			}
		}
	}

	@Then("^See all components in newsroom page$")
	public void seeAllComponentsInNewsroomPage() throws Throwable {
		System.out.println("See all components in newsroom page");
		waitTillElementVisible_Xpath("//div[text()='News']");
		getVisibleElementByXpath("//div[text()='News']");
		getVisibleElementByXpath("//span[contains(text(),'Sort By') or contains(text(),'Filter By')]");
	}
	@When("^Verify Sort By Functionality$")
	public void verify_Sort_By_Functionality() throws Throwable {
		String date = driver.findElement(By.xpath("//div[@class='date-year']")).getText(); 
		getVisibleElementByXpath("//span[contains(text(),'Sort By') or contains(text(),'Filter By')]").click();
		Thread.sleep(5000);
		getVisibleElementByXpath("//span[contains(text(),'Sort By') or contains(text(),'Filter By')]/following::li[3]/a");
		clickOnLinks("//span[contains(text(),'Sort By') or contains(text(),'Filter By')]/following::li[3]/a");
		String date_After_Sorting = driver.findElement(By.xpath("//div[@class='date-year']")).getText();

		if(!date.equals(date_After_Sorting)) {
			System.out.println("Sort Functionality Works fine");
			getVisibleElementByXpath("//div[@class='date-year']");
		}
		else {
			System.err.println("Sort Functionality Works fails");
		}
	}

	@Then("^Verify the content of news article page$")
	public void verify_the_content_of_news_article_page() throws Throwable {
		String newsTitleText = "";
		System.out.println("Verify the content of news article page");
		List<WebElement> newsTitle = driver.findElements(By.xpath("(//div[@class='date-year']/preceding-sibling::h3/a)"));
		for (int i=1;i<=newsTitle.size();i++) {
			if(newsTitle.get(i).isDisplayed()==true) {
				newsTitleText = getVisibleElementByXpath("(//div[@class='date-year']/preceding-sibling::h3/a)[1]").getText();
				System.out.println("Text: "+newsTitleText);
				break;
			}
			else
				continue;
		}
		getVisibleElementByXpath("//span[text()='Sort By']").click();
		//getVisibleElementByXpath("(//div[@class='date-year']/preceding-sibling::h3/a)[1]").click();
		Thread.sleep(3000);
		getVisibleElementByXpath("//a[text()='" + newsTitleText + "']");
		driver.navigate().back();
	}

	@Then("^See all components in newsroom page Perf$")
	public void seeAllComponentsInNewsroomPagePerf() throws Throwable {
		System.out.println("See all components in newsroom page Perf");
		getVisibleElementByXpath("//div[text()='News']");
		getVisibleElementByXpath("//span[contains(text(),'Sort By')]");
		getVisibleElementByXpath("//a[text()='The 15th Henry Ford Awards: Now Open for Entries']");
		getVisibleElementByXpath(
				"//a[text()='Ford Philippines Sales Rise 19 Percent']");
		getVisibleElementByXpath(
				"//a[contains(text(),'Ford Raises the Bar for Refined and Rugged Capable SUVs with the Launch of the All-New Everest')]");
		getVisibleElementByXpath("//a[text()='The Voice of the Philippines']");
		getVisibleElementByXpath(
				"//a[text()='FORD PHILIPPINES GOES FURTHER WITH FORD FORZA TRIATHLON TEAM']");
	}

	@Then("^Click on Search Icon from Home Page$")
	public void click_on_Search_Icon_from_Home_Page() throws Throwable {
		System.out.println("Click on Home page Search Icon");
		getVisibilityElementByXpath("(//i[contains(@class, 'icon-search')]/..)[1]").click();
	}

	@And ("^Input any word into Search field in newsroom popup page$")
	public void inputAnyWordIntoSearchFieldInNewsroomPopupPage(DataTable searchedWord) throws Throwable {
		System.out.println("Input any word into Search field in newsroom page");
		List<List<String>> data = searchedWord.raw();
		getVisibleElementByXpath("//input[@class='search typeahead' or @class='typeahead']").clear();
		getVisibleElementByXpath("//input[@class='search typeahead' or @class='typeahead']").sendKeys(data.get(0).get(0));
		try {
			getVisibleElementByXpath("//i[@class='icon-search']").click();
		}catch(Exception e) {
			getVisibleElementByXpath("//input[@class='search typeahead' or @class='typeahead']").sendKeys(Keys.ENTER);
		}
	}

	@And ("^Input any word into Search field in newsroom page$")
	public void inputAnyWordIntoSearchFieldInNewsroomPage(DataTable searchedWord) throws Throwable {
		System.out.println("Input any word into Search field in newsroom page");

		List<List<String>> data = searchedWord.raw();
		getVisibleElementByXpath("//input[@class='search']").clear();
		getVisibleElementByXpath("//input[@class='search']").sendKeys(data.get(0).get(0));
		try {
			getVisibleElementByXpath("//i[@class='icon-search']").click();
		}catch(Exception e) {
			getVisibleElementByXpath("//input[@class='search typeahead' or @class='typeahead']").sendKeys(Keys.ENTER);
		}
	}

	@Then("^See the searched word display in newsroom page$")
	public void seeTheSearchedWordDisplayInNewsroomPage(DataTable searchedWord) throws Throwable {
		System.out.println("See the searched word display in newsroom page");
		Thread.sleep(5000);
		List<List<String>> data = searchedWord.raw();
		getVisibleElementByXpath("//*[text()='" + data.get(0).get(0) + "']");
	}

	@Then("^See the latest version of JS \"(.*?)\"$")
	public void seeTheLatestVersionOfJS(String version) throws Throwable {
		System.out.println("See the latest version of JS");
		String versionStringToVerify = "var aemGuxfoapUiReleaseVersion = '" + version + "'";

		Boolean correctVersion = driver.getPageSource().contains(versionStringToVerify);
		if (!correctVersion) {
			throw new Exception("Incorrect version!");
		}
	}

	@When("^Redirect to link \"(.*?)\"$")
	public void redirectToLink(String link) throws Throwable {
		System.out.println("Redirect to link");
		// Thread.sleep(3000);
		driver.get(getProfileURL(link));
	}

	@When("^Redirect to link \"(.*?)\" and check if link is broken$")
	public void redirectToLinkAndCheckIfLinkIsBroken(String link) throws Throwable {
		System.out.println("Redirect to link and check if link is broken");
		Thread.sleep(10000);
		driver.get(getProfileURL(link));
		List<WebElement> allImages = findAllLinks(driver);
		System.out.println("Total number of elements found: " + allImages.size());

		for (WebElement element : allImages) {
			try {
				System.out.println("URL: " + element.getAttribute("href") + " returned "
						+ isLinkBroken(new URL(element.getAttribute("href"))));
			} catch (Exception exp) {
				System.out.println(
						"At " + element.getAttribute("innerHTML") + " Exception occured -&gt; " + exp.getMessage());
			}
		}
	}

	@Then("^See all components on home page loaded without performance issue$")
	public void seeHomePageLoadWithoutIssue() throws Throwable {
		System.out.println("See all components on home page loaded without performance issue");
		getVisibleElementByLinkText("BUILD & PRICE");
	}

	@Then("^See all components on AU home page is loaded without performance issue$")
	public void seeHomePageLoadWithoutIssue_AU() throws Throwable {
		System.out.println("See all components on home page loaded without performance issue");

		getVisibleElementByLinkText("BUILD & PRICE");

	}

	@When("^Select province and city in China Mini LAD$")
	public void selectProvinceAndCityInChinaMiniLad(DataTable parameters) throws Throwable {
		System.out.println("Select province and city in China Mini LAD");
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath("//div[@id='s2id_autogen5']/a[@class='select2-choice']").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(0) + "')]").click();
		getVisibleElementByXpath("//div[@id='s2id_autogen7']/a[@class='select2-choice']").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(1) + "')]").click();
	}

	@And("^Click on Search button in China Mini LAD$")
	public void clickOnSearchButtonInChinaMiniLad() throws Throwable {
		System.out.println("Click on Search button in China Mini LAD");
		getVisibleElementByXpath("//button[contains(text(),'搜寻')]").click();
	}

	@Then("^See dealer in China Mini LAD Map$")
	public void seeDealerInChinaMiniLadMap() throws Throwable {
		System.out.println("See dealer in China Mini LAD Map");
		getVisibleElementByXpath("//p[text()='1']");
		getVisibleElementByXpath("//span[text()='1']");
		getVisibleElementByXpath("//div[@id='map']//h3[text()='上海江铃汽车销售服务有限公司']");
		getVisibleElementByXpath("//div[@id='map']//a[contains(text(),'销售:021-57432116')]");
		getVisibleElementByXpath("//div[@id='map']//a[@href='tel:服务：021-62176667']");
		getVisibleElementByXpath("//div[@id='map']//a[contains(text(),'方向')]");
	}

	@When("^Click on Dealer Details in China Mini LAD Map$")
	public void clickOnDealerDetailsInChinaMiniLadMap() throws Throwable {
		System.out.println("Click on Dealer Details in China Mini LAD Map");
		getVisibleElementByXpath("//div[@class='dealer-result-holder']//span[@class='icon-details']").click();
	}

	@Then("^See Dealer Details expander in China Mini LAD Map$")
	public void seeDealerDetailsExpanderInChinMiniLadMap() throws Throwable {
		System.out.println("See Dealer Details expander in China Mini LAD Map");
		getVisibleElementByXpath("//a[text()='mailto:shjl@jmc.com.cn']");
		getVisibleElementByXpath("//span[text()='经销商车型']");
	}

	@When("^Select province and city in China Main LAD$")
	public void selectProvinceAndCityInChinaMainLad(DataTable parameters) throws Throwable {
		System.out.println("Select province and city in China Main LAD");
		List<List<String>> data = parameters.raw();
		getVisibleElementByXpath("//div[@id='s2id_autogen1']/a");
		getVisibleElementByXpath("//div[@id='s2id_autogen3']/a");
		getVisibleElementByXpath("//div[@id='s2id_autogen5']/a");
		getVisibleElementByXpath("//div[@id='s2id_autogen1']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(0) + "')]").click();
		getVisibleElementByXpath("//div[@id='s2id_autogen3']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(1) + "')]").click();
		getVisibleElementByXpath("//div[@id='s2id_autogen5']/a").click();
		getVisibleElementByXpath("//div[contains(text(),'" + data.get(0).get(2) + "')]").click();
	}

	@Then("^See dealer in China Map$")
	public void seeDealerInChinaMap() throws Throwable {
		System.out.println("See dealer in China Map");
		getVisibleElementByXpath("//*[text()='1']");
	}

	@When("^Click on Dealer Details in China Map$")
	public void clickOnDealerDetailsInChinaMap() throws Throwable {
		System.out.println("Click on Dealer Details in China Map");
		getVisibleElementByXpath("//div[@id='dealer-locator-china']/div[7]/div/div/div[2]/div/div/div[1]/div/div/div/div[3]/div[3]/p/span/span[@class='icon-details']").click();
	}

	@Then("^See Dealer Details expander in China Map$")
	public void seeDealerDetailsExpanderInChinaMap() throws Throwable {
		System.out.println("See Dealer Details expander in China Map");
		getVisibleElementByXpath("//a[contains(@class,'dealerWebsiteLink') and text()!='']");
		getVisibleElementByXpath("//h3[@class='dl-dealer-name dl-dealer-name-details' and text()!='']");

	}

	@When("^Click on other place of the map apart from the dealer info white box$")
	public void Clickonotherplaceofthemapapartfromthedealerinfowhitebox() throws Throwable {
		System.out.println("Click on Dealer Details in China Map");
		WebElement element = driver.findElement(By.xpath("//div[contains(@class,'amap-pan-right')]"));
		Locatable hoverItem = (Locatable) element;
		Mouse mouse = ((HasInputDevices) driver).getMouse();
		mouse.click(hoverItem.getCoordinates());
		System.out.println(getVisibleElementByXpath("//span[contains(@class,'marker-label')]").getSize().getHeight());

	}

	@Then("^Dealer info popup white box must disappear$")
	public void Dealerinfopopupwhiteboxmustdisappear() throws Throwable {
		System.out.println("See Dealer Details expander in China Map");
		Assert.assertFalse("Dealer info popup white box displayed.",driver.findElements(By.xpath("//div[contains(@class,'maps')]//div[contains(@class,'info-window-content') and contains(@class,'dl-info-window')]")).size()!=0);
	}

	@And("^Click on Search button in China Main LAD$")
	public void clickOnSearchButtonInChinaMainLad() throws Throwable {
		System.out.println("Click on Search button in China Main LAD");
		getVisibleElementByXpath("//button[contains(text(),'搜寻经销商')]").click();
	}

	@When("^Input position \"(.*?)\" to Mini LAD and Search$")
	public void inputPositionAndSearch(String position) throws Throwable {
		System.out.println("Input position to Mini LAD and Search");
		Thread.sleep(3000);
		getVisibleElementByXpath("//input[@id='mini-lad-input']").clear();
		getVisibleElementByXpath("//input[@id='mini-lad-input']").sendKeys(position);
		waitTillElemClickableByXpath("//i[@class='icon-search']/..", 120);
		Thread.sleep(3000);
		getVisibleElementByXpath("//i[@class='icon-search']/..").click();//button[@class='submit-btn search-submit']
	}

	@When("^Click on LAD Expander button$")
	public void clickLADExpander() throws Throwable {
		System.out.println("Click on LAD Expander button");
		Thread.sleep(30);
		getVisibleElementByXpath("//div[@class='expander-head']/a[contains(@class,'expander')]").click();
	}

	@When("^See the first result \"(.*?)\" on Map$")
	public void seeTheFirstResultOnMap(String foundPosition) throws Throwable {
		System.out.println("See the first result on Map");
		WebDriverWait wait = new WebDriverWait(driver, 240);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='dealer-info expander-body expanded']//h4/a[text()='" + foundPosition + "']")));
		getVisibleElementByXpath("//div[@class='dealer-info expander-body expanded']//h4/a[text()='" + foundPosition + "']");
	}

	@When("^Click on Dealer Detail$")
	public void clickOnDealerDetail() throws Throwable {
		System.out.println("Click on Dealer Detail");
		getVisibleElementByXpath("//i[@class='icon-details']").click();
	}

	@Then("^See page redirected to correct link \"(.*?)\"$")
	public void seeThePageRedirectedToCorrectLink(String link) throws Throwable {
		System.out.println("See page redirected to correct link");

		Thread.sleep(10000); 

		String url = driver.getCurrentUrl();
		if (!url.equals(link)) {
			throw new Exception("Redirect to incorrect link");
		}
	}

	@Then("^See page redirected to link contains \"(.*?)\"$")
	public void seeThePageRedirectedToLinkContains(String link) throws Throwable {
		System.out.println("See page redirected to link contains");

		Thread.sleep(30000);

		String url = driver.getCurrentUrl();
		System.out.println("xxxxxxxxxxxxxxxx" + url);
		if (!url.contains(link)) {
			throw new Exception("Redirect to incorrect link");
		}
	}

	@Then("^Verify map container is loaded$")
	public void verifyMapContainerLoaded(String foundPosition) throws Throwable {
		System.out.println("Click on Dealer Detail");
		getVisibleElementByXpath("//div[@class='map-container']");
		getVisibleElementByXpath("//h3[contains(text(),'" + foundPosition + "')]");
	}

	@Then("^See all components on \"([^\"]*)\" page loaded without performance issue$")
	public void seeNamePlatePageWithoutIssue(String subMenuItem) throws Throwable {
		System.out.println("See all components on "+subMenuItem+" page loaded without performance issue");
		waitTillElementExist(
				"//div[contains(@class,'service-ok')][1]//div[@class='heading']/a[text()='Compare Models' or text()='Overview All Models']");
		getVisibleElementByXpath(
				"//div[contains(@class,'secondaryNavigation')]//div[@class='desktop hideForMobile']//a[text()='Book A Test Drive' or text()='Book a Test Drive']");
		getVisibleElementByXpath(
				"//div[contains(@class,'secondaryNavigation')]//div[@class='desktop hideForMobile']//a[text()='Download a Brochure' or text()='Download a Brochure']");
	}

	@When("^Click on Main Nav \"(.*?)\"$")
	public void clickOnMainNav(String mainNav) throws Throwable {
		System.out.println("Click on Main Nav");
		getVisibleElementByXpath("//span[text()='All Vehicles']").click();
	}

	@When("^Click on Main Navigation \"(.*?)\"$")
	public void clickOnMainNavigation(String mainNav) throws Throwable {
		System.out.println("Click on Main Nav");
		getVisibleElementByXpath("//span[contains(text(),'Vehicles')]").click();
	}

	@When("^Click on tab in Vehicle \"(.*?)\"$")
	public void clickOnTabInVehicle(String tabName) throws Throwable {
		System.out.println("Click on tab in Vehicle");
		getVisibleElementByXpath("//a[@class='vehicle-menu-item-title' and text()='" + tabName + "']").click();
	}

	@Then("^Verify vehicle name in menu \"(.*?)\"$")
	public void verifyVehicleNameInMenu(String vehicleName) throws Throwable {
		System.out.println("Verify vehicle name in menu");
		getVisibleElementByXpath("//span[text()='" + vehicleName + "']//ancestor::a[contains(@href,'" + vehicleName.toLowerCase() + "')]");
	}

	@When("^Click on View All Vehicle$")
	public void clickOnViewAllVehicle() throws Throwable {
		System.out.println("Verify vehicle name in menu");
		getVisibleElementByXpath("//a[text()='View All Vehicles']").click();

	}

	@When("^Click on a model \"(.*?)\"$")
	public void clickOnAModel(String modelName) throws Throwable {
		System.out.println("Click on a model");
		Thread.sleep(2000);
		clickOnLinks("//img[@class='model-shot' and @alt='"+modelName+"' or @alt='"+modelName.toLowerCase()+"']");
	}
	
	@When("^Click on Menu Item on Main Nav \"(.*?)\"$")
	public void clickOnMenuItemOnMainNav(String item) throws Throwable {
		System.out.println("Click on Menu Item on Main Nav");
		clickOnLinks("//span[text()='" + item + "']//ancestor::a[contains(@href,'" + item.toLowerCase() + "')]");
	}

	@When("^Click on SubMenu Item \"(.*?)\"$")
	public void clickOnSubMenuItemOnMainNav(String item) throws Throwable {
		System.out.println("Click on Menu Item on Main Nav");
		clickOnLinks("//*[text()='" + item + "']");
	}

	@When("^Search SubMenu link accros the Main Navigation link \"(.*?)\"$")
	public void Search_SubMenu_link_accros_the_Main_Navigation_link(String item) throws Throwable {
		System.out.println("Search SubMenu link accros the Main Navigation link");
		WebDriverWait wait = new WebDriverWait(driver,240);
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[contains(@class,'vehicle-menu')]//*[contains(@class,'vehicle-menu-item')]/a")));
		List<WebElement> subtabs=driver.findElements(By.xpath("//*[contains(@class,'vehicle-menu')]//*[contains(@class,'vehicle-menu-item')]/a"));

		if(subtabs.size()>0) {
			for(int i=1;i<subtabs.size();i++) {
				System.out.println("i value"+i);
				clickOnLinks("(//*[contains(@class,'vehicle-menu')]//*[contains(@class,'vehicle-menu-item')]/a)"+"["+i+"]");
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='vehicle-img']/img")));
				Thread.sleep(3000);

				try {
					if (driver.findElement(By.xpath("//img[@class='model-shot' and @alt='"+item+"']")).isDisplayed()) {
						clickOnLinks("//img[@class='model-shot' and @alt='"+item+"']");
						break;
					}

					else if (driver.findElement(By.xpath("//img[@class='model-shot' and @alt='"+item.toLowerCase()+"']")).isDisplayed()) {
						clickOnLinks("//img[@class='model-shot' and @alt='"+item.toLowerCase()+"']");
						break;
					}
					else{
						System.out.println("Given Model is not Displayed");
					}
				} catch (NoSuchElementException e) {
					System.err.println("Given Model is not Displayed");
					continue;

				}

			}
		}

	}

	@When("^Click on Secondary menu switcher$")
	public void clickOnSecondaryMenuSwitcher() throws Throwable {
		System.out.println("Click on Secondary menu switcher");
		getVisibleElementByXpath("//h2[contains(text(),'Explore')]").click();
	}

	@When("^Click on Secondary Menu \"(.*?)\"$")
	public void clickToOpenSecondaryMenu(String secondaryMenu) throws Throwable {
		System.out.println("Click on Secondary Menu: " + "//div[contains(@class,'desktop')]//a[text()='" + secondaryMenu + "']");
		getVisibleElementByXpath("//a[text()='" + secondaryMenu + "']").click();
	}

	@When("^Click on Model compare$")
	public void Click_on_Model_compare() throws Throwable {
		System.out.println("Click on Secondary Menu: " + "//div[contains(@class,'desktop')]//a[text()='Model Compare']");
		getVisibleElementByXpath("//div[contains(@class,'desktop')]//a[text()='Model Compare']").click();
	}

	@When("^Click on image on Gallery$")
	public void clickOnImageOnGallery() throws Throwable {
		System.out.println("Click on image on Gallery");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		waitTillElemClickableByXpath("//div[@class='images-container']/div[@class='image section']/div/a", 120);
		getVisibleElementByXpath("//div[@class='images-container']/div[@class='image section']/div/a").click();
	}

	@Then("^Verify overlay open from image Gallery$")
	public void verifyGalleryOverlayOpen() throws Throwable {
		System.out.println("Verify overlay open from image");
		Thread.sleep(5000);
		getVisibleElementByXpath("//div[@class='box-image-overlay box-content' or contains(@class,'common-overlay')]");
	}

	@When("^Click on Show Details$")
	public void clickOnShowDetails() throws Throwable {
		System.out.println("Click on Show Details");
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebElement em = getVisibleElementByXpath("//span[text()='Hide Details']");
			try {
				em.click();
			} catch (Exception e) {
				System.out.println("No Hide Details");
			}
			;
		} catch (Exception e) {
		}
		;
		getVisibleElementByXpath(
				"//span[@class='accordion-details-button' and text()='Show Details' or text()='View Details']").click();
		Thread.sleep(5000);
	}

	@When("^Click on View Details$")
	public void clickOnViewDetails() throws Throwable {
		System.out.println("Click on View Details");
		getVisibleElementByXpath("//span[@class='accordion-details-button' and text()='Show Details' or text()='View Details' or text()='More']").click();

	}

	@Then("^Verify overlay open from image$")
	public void verifyOverlayOpenFromImage() throws Throwable {
		System.out.println("Verify overlay open from image");
		// getVisibleElementByXpath("//img[@src='https://wwwdev.brandap.ford.com/content/ford/ph/en_ph/fiesta-content/image-overlays/gallery-exterior/stylish-4-door-sedan/_jcr_content/par/box/generalParsys/image/image.imgs.full.high.jpg/1492425446786.jpg']");
		waitTillElementExist("//i[@class='icon-plus icon-x']");
		getVisibleElementByXpath("//i[@class='icon-plus icon-x']");
		getVisibleElementByXpath("//span[@class='accordion-details-button' and text()='Show Details']");
	}

	@When("^Click on the X button on the top left$")
	public void clickOnTheXbuttonOnTheTopLeft() throws Throwable {
		System.out.println("Click on the X button on the top left");

		getVisibleElementByXpath("//div[@id='global-ux']/div[3]/div[4]/div/span/i[@class='icon-plus icon-x']").click();
	}

	@Then("^Verify seen Share and Download button$")
	public void verifySeenShareAndDownload() throws Throwable {
		System.out.println("Verify seen Share and Download button");
		getVisibleElementByXpath("//i[@class='icon-share']");
		getVisibleElementByXpath("//i[contains(@class,'icon-download')]");
	}

	@When("^Click on Share$")
	public void clickOnShare() throws Throwable {
		System.out.println("Click on Share");
		getVisibleElementByXpath("//i[@class='icon-share']").click();

	}

	@When("^Click on Download$")
	public void clickOnDownload() throws Throwable {
		System.out.println("Click on Download");
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//p[contains(@class, 'action')]//i[contains(@class,'icon-download')]"))));
		getVisibleElementByXpath("//p[contains(@class, 'action')]//i[contains(@class,'icon-download')]").click();
	}

	@When("^Scroll down overlay container$")
	public void scrollDownOverlayContainer() throws Throwable {
		System.out.println("Scroll down overlay container");
		getVisibleElementByXpath("//div[contains(@class,'overlay-container')]").sendKeys(Keys.PAGE_DOWN);
	}

	@Then("^See the sharing popup$")
	public void seeSharingPopup() throws Throwable {
		System.out.println("See the sharing popup");
		getVisibleElementByXpath("//a[@class='icon-share-twitter']");
		getVisibleElementByXpath("//a[@class='icon-share-pinterest']");
		getVisibleElementByXpath("//a[@class='icon-share-email']");
		getVisibleElementByXpath("//a[@class='icon-share-facebook']").click();

	}

	@When("^Click to close overlay$")
	public void clickToCloseOverlay() throws Throwable {
		System.out.println("Click to close overlay");
		WebDriverWait wait = new WebDriverWait(driver, 120);
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//i[@class='icon-plus icon-x']"))));
		getVisibleElementByXpath("//i[@class='icon-plus icon-x']").click();
	}

	@When("^Click on video carousel item$")
	public void clickOnVideoCarouselItem() throws Throwable {
		System.out.println("Click on video carousel item");
		// moveToElement("//a[@data-overlay_id='overview-videos_video']");
		getVisibleElementByXpath("//a[@data-overlay_id='gallery-videos_video']/picture/img").click();
	}

	@Then("^See video overlay$")
	public void seeVideoOverlay() throws Throwable {
		System.out.println("See video overlay");

		getVisibleElementByXpath("//object[@id='bcExperienceObj0']");
	}

	@When("^Click on media carousel$")
	public void clickOnMediaCarousel() throws Throwable {

	}

	@Then("^Verify 360 images$")
	public void verify360Images() throws Throwable {
		System.out.println("See 360 images");
		getVisibleElementByXpath("//div[@class='image-360']/ul[@class='slides']/li/picture/img");
	}

	@When("^Click on Colorize tab$")
	public void clickOnColorizeTab() throws Throwable {
		System.out.println("Click on Colorize tab");
		getVisibleElementByXpath("//a[text()='Colorizer' or contains(text(),'Colouriser')]").click();

	}

	@When("^Verify Image with Color is seen$")
	public void verifyImageWithColorIsSeen() throws Throwable {
		System.out.println("Verify Image with Color is seen");
		getVisibleElementByXpath("//div[@id='colorizer']/ul[@id='colorizerMainImg']/li[@class='item active']/img");
	}

	@When("^Click on Black color to change$")
	public void clickOnBlackColorToChange() throws Throwable {
		System.out.println("Click on Black color to change");
		getVisibleElementByXpath("//div[@id='colorizer']/ul[@id='colorizerBtn']/li[@class='item']/img[@src='Black' or @src='Panther Black' or contains(@src,'black')]").click();

	}

	@Then("^Verify the color of image and title are changed$")
	public void verifyTheColorOfImageAndTitleAreChanged() throws Throwable {
		System.out.println("Verify the color of image and title are changed");
		getVisibleElementByXpath("//div[@id='colorizer']/ul[@id='colorizerMainImg']/li[@class='item']/div/span[text()='Black' or @alt='Panther Black' or contains(text(),'Black')]");

	}

	@When("^Click on Disclosure$")
	public void clickOnDisclosure() throws Throwable {
		System.out.println("Click on Disclosure");
		getVisibleElementByXpath("//h3[contains(text(),'Disclosures')]").click();
	}

	@When("^Verify see Disclosure Content$")
	public void verifySeeDisclosureConent() throws Throwable {
		System.out.println("Verify see Disclosure Content");
		getVisibleElementByXpath("//div[contains(@class,'disclosure-text')]//p[text()!='']");
	}

	@When("^Click on Disclosure to Collapse$")
	public void clickOnDisclosureToCollapse() throws Throwable {
		System.out.println("Click on Disclosure to Collapse");
		getVisibleElementByXpath("//h3[contains(text(),'Disclosures') and @class='disclosure-heading']").click();
	}

	@When("^Verify don't see Disclosure content$")
	public void verifyDontSeeDisclosureContent() throws Throwable {
		System.out.println("Verify don't see Disclosure content");
		verifyInvisibleElement("//div[contains(@class,'disclosure-text')]//p[text()!='']");
		//OLD CODE -- "//p[contains(text(),'[1] The company reserves the right to change any detail regarding specifications,')]");
	}

	@When("^Verify footer links are visible$")
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

	@When("^Click on Model Compare button$")
	public void clickOnModelCompareButton() throws Throwable {
		System.out.println("Click on Model Compare button");
		getVisibleElementByXpath(
				"//div[contains(@class,'service-ok')][1]//div[@class='heading']/a[text()='Model Compare' or text()='Overview All Models' or text()='Compare Models']")
		.click();
	}

	@When("^Click on Model Compare button on PERF$")
	public void clickOnModelCompareButtonperf() throws Throwable {
		System.out.println("Click on Model Compare button");
		getVisibleElementByXpath(
				"//div[contains(@class,'service-ok')][1]//div[@class='heading']/a[text()='Model Compare' or text()='Overview All Models']")
		.click();
	}

	@Then("^Verify user is taken to model compare page$")
	public void verifySeenModelComparePage() throws Throwable {
		System.out.println("Verify user is taken to model compare page");
		//		getVisibleElementByXpath("//div[@class='desktop-heading']//div[contains(text(),'Model Compare') or contains(text(),'Models & Specifications') or contains(text(), 'Compare Models')]");
		getVisibilityElementByXpath("//div[@class='desktop-heading']//div[contains(text(),'Model Compare') or contains(text(),'Models & Specifications') or contains(text(), 'Compare Models')]");
	}

	@Then("^Verify user is on Active Compare page$")
	public void verifyUserisOnActiveComparePage() throws Throwable {
		System.out.println("Verify user is on Active Compare page");
		// getVisibleElementByXpath(xpath)
	}

	@When("^Click on Add Models To Compare button$")
	public void clickOnAddModelsToCompareButton() throws Throwable {
		System.out.println("Click on Add Models To Compare page");
		getVisibleElementByXpath("//a[@class='btn btn-white addto-compare ' and contains(text(),'Add Models')]");
		getVisibleElementByXpath("//a[@class='btn btn-white addto-compare ' and contains(text(),'Add Models')]").click();

	}

	@Then("^Click on New Model checkbox \"([^\"]*)\"$")
	public void click_on_New_Model_checkbox(String ModelName) throws Throwable {
		System.out.println("Click on Hatchback checkbox");
		getVisibleElementByXpath("//div[contains(@class,'active')]//h3[text()='"+ModelName+"']/preceding::div[@class='input-checkbox'][1]").click();
		Thread.sleep(2000);
	}

	@And("^Click on Confirm button$")
	public void clickOnConfirmButton() throws Throwable {
		System.out.println("Click on Confirm button");
		String xpath = "//a[contains(@class,'btn confirm') and text()='Confirm']"; 
		Thread.sleep(3000);
		List <WebElement> confirmButton = driver.findElements(By.xpath(xpath));
		for (WebElement webElement : confirmButton) {
			try {
				if (webElement.isDisplayed()) {
					webElement.click();
					break;
				}
			} catch (Exception e) {
				continue;
			}
		} 
	}

	@And("^Click on Confirm buttons$")
	public void clickOnConfirmButton_AddModels() throws Throwable {
		System.out.println("Click on Confirm button");
		getVisibleElementByXpath("//div[@id='model-group-id-01' or @id='model-group-id-03']//*[contains(text(),'Confirm')]").click(); //Old xpath //div[@id='model-group-id-01' or @id='model-group-id-03']//a[contains(text(),'Confirm')] 
	}

	@And("^Click on Other Model checkbox$")
	public void click_on_Other_Model_checkbox() throws Throwable {
		System.out.println("Click on Fiesta Ambiente checkbox");
		List<WebElement> selectsinglevehicle=driver.findElements(By.xpath("//div[contains(@class,'popup-select-other-model active')]//div[@class='input-checkbox' and not(contains(@class,'input-checkbox checked'))][1]"));
		selectsinglevehicle.get(0).click();
		Thread.sleep(2000);
	}


	@And("^Click on Titanium 1.0L EcoBoost Petrol checkbox$")
	public void clickOnTitaniumLEcoBoostPetrolCheckbox() throws Throwable {
		System.out.println("Click on Fiesta Ambiente checkbox");
		List<WebElement> selectsinglevehicle=driver.findElements(By.xpath("//div[contains(@class,'popup-select-other-model active')]//div[@class='input-checkbox' and not(contains(@class,'input-checkbox checked'))][1]"));
		selectsinglevehicle.get(0).click();
		Thread.sleep(2000);
	}


	@When("^Click on Active Compare button$")
	public void clickOnActiveCompareButton() throws Throwable {
		System.out.println("Click on Active Compare Button");
		getVisibleElementByXpath("//div[@class='desktop-heading']//a[text()='Active Compare']").click();

	}

	@When("^Select up to (\\d+) items on the list$")
	public void clickOnSelection3Items(int vehicleCnt) throws Throwable {
		System.out.println("Select up to 2 items on the list");
		List<WebElement> elements = driver.findElements(By.cssSelector("div.models-list.clearfix div.item div.name-item.on-select-mode div.select-model.on")); 
		int count = 0;
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				waitTillElemClickableBycssSelector("div.models-list.clearfix div.item div.name-item.on-select-mode div.select-model.on", 120);
				waitTillElemVisiblebyXpath("//div[@class='select-model on']", 120);
				element.click();
				count++;
				if (count == vehicleCnt)
					break;
			}
		}
	}

	@When("^Click on Compare button$")
	public void clickOnCompareButton() throws Throwable {
		System.out.println("Click on Compare button");
		getVisibleElementByXpath("//a[text()='Compare']").click();
	}

	@Then("^Verify user is taken to model compare results page and the selected item's specifications are seen$")
	public void verifySeenComparePage() throws Throwable {
		System.out.println(
				"Verify user is taken to model compare results page and the selected item's specifications are seen");
		getVisibleElementByXpath("//h1[text()='Model Compare Details']");
		getVisibleElementByXpath("//div[text()='Chassis' and @class='group-label']");
		getVisibleElementByXpath("//div[text()='Comfort & Convenience' and @class='group-label']");
		getVisibleElementByXpath("//div[text()='Engine' and @class='group-label']");
		getVisibleElementByXpath("//div[text()='Steering System' and @class='group-label']");
		getVisibleElementByXpath("//div[text()='Interior' and @class='group-label']");
		getVisibleElementByXpath("//div[text()='Warranty' and @class='group-label']");
		getVisibleElementByXpath("//div[text()='Safety & Security' and @class='group-label']");
		getVisibleElementByXpath("//div[text()='Power Features' and @class='group-label']");
	}

	@When("^Click on select other models to compare button$")
	public void clickOnSelectOtherToCompareButton() throws Throwable {
		System.out.println("Click on select other models to compare button");
		getVisibleElementByXpath("//a[contains(@class,'select-to-compare')]").click();
	}

	@Then("^Verify a popup of the list of items are seen$")
	public void verifyPopupOfTheListOfItemsAreSeen() throws Throwable {
		System.out.println("Verify a popup of the list of items are seen");
		getVisibleElementByXpath("//div[@class='input-checkbox']/input[@type='checkbox' and @name='model-input-name']");
	}

	@Then("^Select other item and click Confirm$")
	public void selectOtherItemAndConfirm() throws Throwable {
		System.out.println("Select other item and click Confirm");
		Thread.sleep(5000);
		getVisibleElementByXpath("//div[@class='input-checkbox']/input[@type='checkbox' and @name='model-input-name']")
		.click();
		getVisibleElementByXpath("//a[text()='Confirm']").click();
	}

	@Then("^See all components on Engineering page loaded without performance issue$")
	public void verifyAllComponentsOnEngineeringPageLoaded() throws Throwable {
		System.out.println("See all components on Engineering page loaded without performance issue");
		getVisibleElementByXpath("//a[contains(@href,'boron-steel.html')]");
		getVisibleElementByXpath("//a[contains(@href,'ecoboost.html')]");
		getVisibleElementByXpath("//a[contains(@href,'adaptive-cruise-control.html')]");
		getVisibleElementByXpath("//a[contains(@href,'active-city-stop.html')]");
		getVisibleElementByXpath("//a[contains(@href,'active_park_assist.html')]");
	}

	@Then("^Click on boron steel in the engineering page$")
	public void clickOnBoronSteel() throws Throwable {
		System.out.println("See all components on Engineering page loaded without performance issue");
		getVisibleElementByXpath("//a[contains(@href,'boron-steel.html')]").click();
	}

	@When("^Go to the hotspot component and hover on the plus sign$")
	public void mouseHoverPlusSign() throws Throwable {
		System.out.println("Go to the hotspot component and hover on the + sign");
		getVisibleElementByXpath("//a[contains(@class,'hotspot-trigger')]").click();

	}

	@When("^Click on locate a dealer at the header$")
	public void clickOnLocateADealerMenuItem() throws Throwable {
		System.out.println("Click on locate a dealer at the header");
		getVisibleElementByXpath("//span[text()='Locate A Dealer' or text()='Locate a Dealer']").click();
	}

	@When("^Input text to Search Input and click Search \"(.*?)\"$")
	public void inputTextAndSearch(String position) throws Throwable {
		System.out.println("Input text to Search Input and click Search");
		waitTillElemVisiblebyXpath("//input[@id='search-field']", 240);
		getVisibleElementByXpath("//input[@id='search-field']").clear();
		Thread.sleep(2000);
		getVisibleElementByXpath("//input[@id='search-field']").sendKeys(position);
		getVisibleElementByXpath("//button[@class='button search-submit']").click();
	}


	@And("^Click on Submit on AU LAD$")
	public void Click_on_Submit_on_AU_LAD() throws Throwable {
		System.out.println("Click on Submit on AU LAD");
		getVisibleElementByXpath("//button[@type='submit']").click();
	}


	@Then("^Verify seen search result$")
	public void verifySeenSearchResult() throws Throwable {
		System.out.println("Verify seen search result");
		getVisibleElementByXpath("//a[contains(text(),' Ford Manila') or contains(text(),'Sunshine Ford')]");
	}

	@Then("^Verify Delears details on Map$")
	public void VerifyDelearsdetailsonMap() throws Throwable {
		System.out.println("Verify Delears details on Map");
		getVisibleElementByXpath("//div[@data-main-ctrl-dealer-website='dealerWebsite(dealer, placement, index)']//a[contains(text(),'Sunshine Ford')]");
	}

	@When("^Click on Secondary menu switcher on Fiesta$")
	public void clickOnSecondaryMenu() throws Throwable {
		System.out.println("Click on Secondary menu switcher");
		Thread.sleep(10000);
		getVisibleElementByXpath("//h2[contains(text(),'Explore')]").click();
	}

	@Then("^Verify image hotspot popup is seen$")
	public void verifyImageHotspot() throws Throwable {
		System.out.println("Verify image hotspot popup is seen");
		getVisibleElementByXpath("//*[text()='HINGE PILLAR')]");
	}

	@When("^Click on the X button on the top left of image overlay$")
	public void clickOnTopLeftOfImageOverlay() throws Throwable {
		System.out.println("Click on the X button on the top left of image overlay");
		getVisibleElementByXpath("//i[@class='icon-plus icon-x')]").click();
	}

	@When("^Click on view more button$")
	public void clickOnViewMoreButton() throws Throwable {
		System.out.println("Click on view more button");
		Thread.sleep(4000);
		getVisibleElementByXpath("//a[text()='View More']").click();
	}

	@Then("^Verify see more images$")
	public void verifySeeMoreImages() throws Throwable {
		System.out.println("Verify see more images");
		getVisibleElementByXpath("//img[contains(@scr,'exterior8.jpg') or contains(@src,'1459500302521.jpg')]");
	}

	@When("^Click on image on Accessories$")
	public void clickOnImageOnAccessories() throws Throwable {
		System.out.println("Click on image on Accessories");
		driver.manage().timeouts().implicitlyWait(250, TimeUnit.SECONDS);
		getVisibleElementByXpath(
				"//div[contains(@class,'accessories')]/div[contains(@class,'filter-content')]/ul/li/a/img").click();
	}

	@Then("^Verify overlay open from image Accessories$")
	public void verifyAccessoriesOverlayOpen() throws Throwable {
		System.out.println("Verify overlay open from image");
		getVisibleElementByXpath("//i[@class='icon-plus icon-x']");
		// getVisibleElementByXpath("//div[@class='overlay-container visible']");
	}

	@When("^See all components on News Room page loaded without performance issue$")
	public void verifyComponentsOnNewsRoomLoadedOK() throws Throwable {
		System.out.println("Verify overlay open from image");
		getVisibleElementByXpath("//div[contains(@class,'header-news')]/div[@class='title-news' and text()='News']");
		getVisibleElementByXpath("//div[@class='blocks-news']");
	}

	@When("^Click on Sort By$")
	public void clickOnSortBy() throws Throwable {
		System.out.println("Click on Sort By");
		getVisibleElementByXpath("//span[contains(text(),'Sort By') or contains(text(),'Filter By')]").click();
	}

	@When("^Click on Sort by September$")
	public void clickOnSeptember() throws Throwable {
		System.out.println("Click on Sort by September");
		getVisibleElementByXpath("//a[text()='Sep - 2016']").click();
	}

	@Then("^Verify all months in filter$")
	public void verifyAllMonthInFilter() throws Throwable {
		System.out.println("Verify all months in filter");
		getVisibleElementByXpath("//a[text()='Oct - 2016']");
		getVisibleElementByXpath("//a[text()='Aug - 2016']");
		getVisibleElementByXpath("//a[text()='Jun - 2016']");
		getVisibleElementByXpath("//a[text()='Feb - 2016']");
	}

	@Then("^Verify all months in filter Perf$")
	public void verifyAllMonthInFilterPerf() throws Throwable {
		System.out.println("Verify all months in filter Perf");
		getVisibleElementByXpath("//a[text()='Mar - 2015']");
		getVisibleElementByXpath("//a[text()='Aug - 2015']");
		getVisibleElementByXpath("//a[text()='Jun - 2015']");
		getVisibleElementByXpath("//a[text()='May - 2015']");
	}

	@When("^Click on Sort by October$")
	public void clickOnOctober() throws Throwable {
		System.out.println("Click on Sort by October");
		getVisibleElementByXpath("//a[text()='Aug - 2015']").click();
	}

	@Then("^Verify message Results Found on page$")
	public void verifyMessageMessage() throws Throwable {
		System.out.println("Verify message No Results Found on page");
		getVisibleElementByXpath("//a[text()='Article1' or text()='The 15th Henry Ford Awards: Now Open for Entries']");
	}

	@When("^Click on No Sort$")
	public void clickOnNoSort() throws Throwable {
		System.out.println("Click on No Sort");
		getVisibleElementByXpath("//a[text()='no sort']").click();
	}

	@When("^Click on View More$")
	public void clickOnViewMore() throws Throwable {
		System.out.println("Click on Learn More");
		getVisibleElementByXpath("//div[@class='view-more' and contains(text(),'View More News')]").click();
	}

	@Then("^Verify seen more view$")
	public void verifySeenMoreView() throws Throwable {
		System.out.println("Verify seen more view");
		getVisibleElementByXpath("//a[text()='FORD PHILIPPINES GOES FURTHER WITH FORD FORZA TRIATHLON TEAM']");
	}

	@When("^Click on Learn More$")
	public void clickOnLearnMore() throws Throwable {
		System.out.println("Click on Learn More");
		WebElement element = driver.findElement(By.xpath(
				"//div[@class='blocks-news']/div/div[@class='txt-description' and contains(text(),'Ford is the first automaker to test fully autonomous vehicles in winter weather,')]/following-sibling::div/a[text()='Learn More']"));
		moveToElement(element);
		Thread.sleep(3000);
		List<WebElement> elements = driver.findElements(By.xpath("//a[contains(text(),'Learn More')]"));
		for (WebElement ele : elements) {
			if (ele.isDisplayed()) {
				ele.click();
				break;
			}
		}
	}

	@And("^Capture Screenshot \"(.*?)\" on P1 phase$")
	public void captureScreenshot(String obj) throws Throwable {
		wait = new WebDriverWait(driver, 10);
		System.out.println("Capture Screenshot on P1");
		actualImageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(actualImageFile, new File(actual + obj + " " + GetTimeStampValue() + ".png"));
	}

	@After
	public void cleanUp() {
		try {
			// driver.quit();
		} catch (Exception ex) {
			// the overlay is dissapeared!
		} finally {

		}
	}

	@And("^Fill in step one in AU Predelivery$")
	public void fillInStepOneInAuPredelivery() throws Throwable {
		System.out.println("Fill in step one in AU Predelivery");
		getVisibleElementByXpath("//div[@id='s2id_autogen1']/a[@class='select2-choice']").click();

	}

	@When("^see all vehicles and their models are listed respectively in selection boxes$")
	public void select_vehicles_and_their_models() throws Throwable {
		System.out.println("see all vehicles and their models are listed respectively in selection boxes");
		waitTillElemVisiblebyXpath("//span[contains(@class,'select2-chosen')]", 240);
		List<WebElement> SelVeh=driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
		int SelVehsize=SelVeh.size();
		for(int nameplate=0;nameplate<SelVehsize;nameplate+=2){
			SelVeh=driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
			SelVeh.get(nameplate).click();
			Thread.sleep(2000);
			List<WebElement> nameplates=driver.findElements(By.xpath("//ul[@role='listbox']/li"));
			if(nameplates.size()>1) {
				System.out.println("Select Nameplate dropdown" + nameplate + " containing " + nameplates.size() + " Nameplates");
				nameplates.get(1).findElement(By.xpath("div")).click();
			}else {
				System.out.println("Select Nameplate dropdown" + nameplate + " containing 0 Nameplates");
				nameplates.get(0).findElement(By.xpath("div")).click();
			}

			for(int selnameplt=1;selnameplt<nameplates.size();selnameplt++) {
				SelVeh=driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
				SelVeh.get(nameplate).click();				
				nameplates=driver.findElements(By.xpath("//ul[@role='listbox']/li"));
				nameplates.get(selnameplt).findElement(By.xpath("div")).click();
				Thread.sleep(2000);
				SelVeh=driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
				SelVeh.get(nameplate+1).click();
				Thread.sleep(1000);
				List<WebElement> CheckModels=driver.findElements(By.xpath("//ul[@role='listbox']/li"));
				if(CheckModels.size()>1) {
					System.out.println("Select Model dropdown" + (selnameplt) + " containing " + (CheckModels.size()-1) + " Models");
					CheckModels.get(1).findElement(By.xpath("div")).click();
				}else {
					System.out.println("Select Model dropdown" + (selnameplt) + " containing 0 Models");
					CheckModels.get(0).findElement(By.xpath("div")).click();
				}

			}
		}
	}

	@When("^Select 1 vehicle and model$")
	public void select_vehicle_and_model(DataTable arg) throws Throwable {
		System.out.println("Select 1 vehicle and model");
		List<List<String>> data=arg.raw();
		try {
			List<WebElement> SelVeh = driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
			SelVeh.get(0).click();
			Thread.sleep(2000);
			getVisibleElementByXpath("//ul[@role='listbox']/*//*[contains(text(),'" + data.get(0).get(0) + "')]").click();
			Thread.sleep(2000);
			SelVeh = driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
			SelVeh.get(1).click();
			Thread.sleep(2000);
			getVisibleElementByXpath("//ul[@role='listbox']/*//*[contains(text(),'" + data.get(0).get(1) + "')]").click();
		} catch (Exception e) {

			List<WebElement> selectVehicle = driver.findElements(By.xpath("//div[contains(@class,'model-select-item ng-scope') and not(contains(@class,'active'))]//Select[contains(@data-ng-model,'selectedVehicle')]"));
			Select SelVeh = new Select(selectVehicle.get(0));
			SelVeh.selectByVisibleText(data.get(0).get(0));
			Thread.sleep(2000);
			List<WebElement> selectModel = driver.findElements(By.xpath("//div[contains(@class,'model-select-item ng-scope') and not(contains(@class,'active'))]//Select[contains(@data-ng-model,'selectedModel')]"));
			SelVeh = new Select(selectModel.get(0));
			SelVeh.selectByVisibleText(data.get(0).get(1));
		}
	}

	@When("^click on Add Vehicle$")
	public void click_on_Add_Vehicle() throws Throwable {
		System.out.println("click on Add Vehicle");
		List<WebElement> AddVeh=driver.findElements(By.xpath("//*[text()='Add Vehicle' and not(contains(@class,'disable'))]"));
		AddVeh.get(0).click();
	}

	@Then("^see Model and detail are added successfully$")
	public void see_Model_and_detail_are_added_successfully(DataTable param) throws Throwable {
		System.out.println("see Model and detail are added successfully");
		List<List<String>> data=param.raw();
		getVisibleElementByXpath("//div[contains(@class,'active')]//*[text()='Remove']");
		List<WebElement> vehiclePrz=driver.findElements(By.xpath("//p[contains(@class,'ng-binding') and contains(text(),'$')]"));
		for(WebElement gtprz:vehiclePrz) {
			if(gtprz.getText().replace("$", "").replace(",", "").trim().isEmpty()) {
				Assert.assertFalse("Starting Price of vehicle is empty", false);
			}
		}
	}

	@Then("^Open (\\d+)nd and (\\d+)rd columns and see above selected model is not listed there$")
	public void open_nd_and_rd_columns_and_see_above_selected_model_is_not_listed_there(int arg1, int arg2,DataTable arg) throws Throwable {
		System.out.println("Open 2nd and 3rd columns and see above selected model is not listed there");
		List<List<String>> data=arg.raw();
		getVisibleElementByXpath("//span[contains(@class,'select2-chosen')]");
		List<WebElement> SelVeh=driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
		SelVeh.get(2).click();
		Thread.sleep(2000);
		getVisibleElementByXpath("//ul[@role='listbox']/*//*[contains(text(),'"+data.get(0).get(0)+"')]").click();
		Thread.sleep(2000);
		SelVeh=driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
		SelVeh.get(3).click();
		Thread.sleep(2000);
		try {
			driver.findElement(By.xpath("//ul[@role='listbox']/*//*[contains(text(),'"+data.get(0).get(1)+"')]")).click();
		}catch(Exception e) {
			System.out.println(data.get(0).get(1) + " Model does not exist while adding Second vehicle");
			driver.findElements(By.xpath("//ul[@role='listbox']/*//div")).get(1).click();
		}	

		getVisibleElementByXpath("//span[contains(@class,'select2-chosen')]");
		SelVeh=driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
		SelVeh.get(4).click();
		Thread.sleep(2000);
		getVisibleElementByXpath("//ul[@role='listbox']/*//*[contains(text(),'"+data.get(0).get(0)+"')]").click();
		Thread.sleep(2000);
		SelVeh=driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
		SelVeh.get(5).click();
		Thread.sleep(2000);
		try {
			driver.findElement(By.xpath("//ul[@role='listbox']/*//*[contains(text(),'"+data.get(0).get(1)+"')]")).click();
		}catch(Exception e) {
			System.out.println(data.get(0).get(1) + " Model does not exist while adding Third vehicle");
			driver.findElements(By.xpath("//ul[@role='listbox']/*//div")).get(1).click();
		}	
	}

	@When("^Add (\\d+) more models from different nameplates$")
	public void add_more_models_from_different_nameplates(int arg1, DataTable arg2) throws Throwable {
		System.out.println("Add 2 more models from different nameplates");
		List<List<String>> data=arg2.raw();
		getVisibleElementByXpath("//span[contains(@class,'select2-chosen')]");
		try {
			List<WebElement> SelVeh = driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
			SelVeh.get(2).click();
			Thread.sleep(2000);
			getVisibleElementByXpath("//ul[@role='listbox']/*//*[contains(text(),'" + data.get(0).get(0) + "')]").click();
			Thread.sleep(2000);
			SelVeh = driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
			SelVeh.get(3).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//ul[@role='listbox']/*//*[contains(text(),'" + data.get(0).get(1) + "')]")).click();
			Thread.sleep(2000);
			List<WebElement> AddVeh = driver.findElements(By.xpath("//*[text()='Add Vehicle' and not(contains(@class,'disable'))]"));
			AddVeh.get(0).click();
		} catch (Exception e) {
			List<WebElement> selectVehicle = driver.findElements(By.xpath("//div[contains(@class,'model-select-item ng-scope') and not(contains(@class,'active'))]//Select[contains(@data-ng-model,'selectedVehicle')]"));
			Select SelVeh = new Select(selectVehicle.get(0));
			SelVeh.selectByVisibleText(data.get(0).get(0));
			Thread.sleep(2000);
			List<WebElement> selectModel = driver.findElements(By.xpath("//div[contains(@class,'model-select-item ng-scope') and not(contains(@class,'active'))]//Select[contains(@data-ng-model,'selectedModel')]"));
			SelVeh = new Select(selectModel.get(0));
			SelVeh.selectByVisibleText(data.get(0).get(1));
			Thread.sleep(2000);
			List<WebElement> AddVeh = driver.findElements(By.xpath("//*[text()='Add Vehicle' and not(contains(@class,'disable'))]"));
			AddVeh.get(0).click();
		}

		getVisibleElementByXpath("//span[contains(@class,'select2-chosen')]");
		try {
			List<WebElement> SelVeh = driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
			SelVeh.get(4).click();
			Thread.sleep(2000);
			getVisibleElementByXpath("//ul[@role='listbox']/*//*[contains(text(),'" + data.get(0).get(2) + "')]").click();
			Thread.sleep(2000);
			SelVeh = driver.findElements(By.xpath("//span[contains(@class,'select2-chosen')]"));
			SelVeh.get(5).click();
			Thread.sleep(2000);
			driver.findElement(By.xpath("//ul[@role='listbox']/*//*[contains(text(),'" + data.get(0).get(3) + "')]")).click();
			Thread.sleep(2000);
			List<WebElement> AddVeh = driver.findElements(By.xpath("//*[text()='Add Vehicle' and not(contains(@class,'disable'))]"));
			AddVeh.get(0).click();
		} catch (Exception e) {
			List<WebElement> selectVehicle = driver.findElements(By.xpath("//div[contains(@class,'model-select-item ng-scope') and not(contains(@class,'active'))]//Select[contains(@data-ng-model,'selectedVehicle')]"));
			Select SelVeh = new Select(selectVehicle.get(0));
			SelVeh.selectByVisibleText(data.get(0).get(2));
			Thread.sleep(2000);
			List<WebElement> selectModel = driver.findElements(By.xpath("//div[contains(@class,'model-select-item ng-scope') and not(contains(@class,'active'))]//Select[contains(@data-ng-model,'selectedModel')]"));
			SelVeh = new Select(selectModel.get(0));
			SelVeh.selectByVisibleText(data.get(0).get(3));
			Thread.sleep(2000);
			List<WebElement> AddVeh = driver.findElements(By.xpath("//*[text()='Add Vehicle' and not(contains(@class,'disable'))]"));
			AddVeh.get(0).click();
		}
	}

		@Then("^Verify models and their details are added successfully$")
		public void verify_models_and_their_details_are_added_successfully(DataTable arg2) throws Throwable {
			System.out.println("Verify models and their details are added successfully");
			List<List<String>> data=arg2.raw();
			getVisibleElementByXpath("//*[text()="+data.get(0).get(1)+"]");
			getVisibleElementByXpath("//*[text()="+data.get(0).get(3)+"]");
			List<WebElement> StartingPrice=driver.findElements(By.xpath("//p[contains(text(),'$')]"));
			Assert.assertEquals("Compare Starting Price of vehicle", data.get(0).get(0), StartingPrice.get(1).getText().replace("$", "").replace(",", "").trim());
			Assert.assertEquals("Compare Starting Price of vehicle", data.get(0).get(2), StartingPrice.get(2).getText().replace("$", "").replace(",", "").trim());
		}
		
		
	@Then("^see all the componets and CTAs are functioning$")
	public void see_all_the_componets_and_CTAs_are_functioning() throws Throwable {
		System.out.println("see all the componets and CTAs are functioning");
		waitTillElemVisiblebyXpath("//*[contains(text(),'Model Details')]", 240);
		getVisibleElementByXpath("//*[contains(text(),'Back to Overview')]");
		getVisibleElementByXpath("//li[contains(@id,'li-id')]//*[contains(text(),'Add Models') and not(contains(@class,'show'))]");
		getVisibleElementByXpath("//*[contains(text(),'OWNERS')]");
		getVisibleElementByXpath("//span[contains(text(),'Vehicles')]");
		getVisibleElementByXpath("//span[contains(text(),'Offers')]");
		getVisibleElementByXpath("//span[contains(text(),'Buying Tools')]");
		getVisibleElementByXpath("//span[contains(text(),'Owners')]");
		getVisibleElementByXpath("//span[contains(text(),'Locate a Dealer')]");
	}

	@When("^Click on Back to Overview CTA$")
	public void click_on_Back_to_Overview_CTA() throws Throwable {
		System.out.println("Click on Back to Overview CTA");
		getVisibleElementByXpath("//*[contains(text(),'Back to Overview') or contains(text(),'Back To Overview')]").click();
		waitTillElemVisiblebyXpath("//div[@class='desktop-heading']//*[contains(text(),'Active Compare')]", 240);
	}

	@Then("^Model Compare page should be displayed$")
	public void model_Compare_page_should_be_displayed() throws Throwable {
		System.out.println("Model Compare page should be displayed");
		getVisibleElementByXpath("//div[contains(text(),'Compare Models')]");	
	}

	@When("^Click on any model link CTA on Model Compare page$")
	public void click_on_any_model_link_CTA_on_Model_Compare_page(DataTable arg) throws Throwable {
		System.out.println("Click on any model link CTA on Model Compare page");
		List<List<String>> data=arg.raw();
		getVisibleElementByXpath("//div[@class='name-item']//a[contains(text(),'"+data.get(0).get(1)+"')]").click();
		waitTillElemVisiblebyXpath("//*[contains(text(),'Model Details')]", 240);
		waitTillElemVisiblebyXpath("//h2[text()='"+data.get(0).get(1)+"')]", 240);
	}

	@Then("^see user is taken to model details page$")
	public void see_user_is_taken_to_model_details_page() throws Throwable {
		System.out.println("see user is taken to model details page");
		getVisibleElementByXpath("//*[contains(text(),'Model Details')]");

	}

	@Then("^see select other models to compare CTA is disabled$")
	public void see_select_other_models_to_compare_CTA_is_disabled() throws Throwable {
		System.out.println("see select other models to compare CTA is disabled");
		getVisibleElementByXpath("//div[@class='show']//a[contains(text(),'Select other Models')]");
	}

	@When("^Click on Book a Test Drive CTA$")
	public void click_on_Book_a_Test_Drive_CTA() throws Throwable {
		System.out.println("Click on Book a Test Drive CTA");
		Thread.sleep(10000);
		waitTillElemClickableByXpath("(//div[@class='show']//a[contains(text(),'Book A Test Drive')])[1]", 240);
		clickOnLinks("(//div[@class='show']//a[contains(text(),'Book A Test Drive')])[1]");
	}

	@Then("^Test drive form should be opened$")
	public void test_drive_form_should_be_opened() throws Throwable {
		System.out.println("Test drive form should be opened");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//h1[contains(text(),'Test Drive')]");	
	}


	@And("^Click on Confirm button on model compare$")
	public void clickOnConfirmCTA() throws Throwable {
		System.out.println("Click on Confirm button on model compare");
		getVisibleElementByXpath("//div[contains(@class,'active')]//a[contains(text(),'Confirm')]").click(); 
	}


	@When("^Click on select other models to compare CTA$")
	public void clickOnSelectOtherToCompareCTA() throws Throwable {
		System.out.println("Click on select other models to compare CTA");
		getVisibleElementByXpath("//div[@data-toggle='model-id-02' and @class='show']/a[contains(@class,'select-to-compare')]").click();
	}


	@Then("^see all componets loads successfully without performance issue$")
	public void see_all_componets_loads_successfully_without_performance_issue() throws Throwable {
		System.out.println("see all componets loads successfully without performance issue");
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//h1[contains(text(),'Test Drive')]");	
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_FIRST_NAME']");
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_LAST_NAME']");
		getVisibleElementByXpath("//input[@name='Email_ID']");
		getVisibleElementByXpath("//input[@name='MOBILENO']");
		getVisibleElementByXpath("//span[contains(text(),'State')]/following-sibling::div//span[@id!='']");
		getVisibleElementByXpath("//span[contains(text(),'City')]/following-sibling::div//span[@id!='']");
		getVisibleElementByXpath("//span[contains(text(),'Preferred Dealer')]/following-sibling::div//span[@id!='']");
		getVisibleElementByXpath("//span[contains(text(),'Model')]/following-sibling::div//span[@id!='']");
		getVisibleElementByXpath("//span[contains(text(),'I plan to buy a new vehicle')]/following-sibling::div//span[@id!='']");
		getVisibleElementByXpath("//div[contains(@class,'form-submit')]//*[contains(text(),'Test Drive')]");

	}

	@When("^On overlay do not input anything and click on submit$")
	public void on_overlay_do_not_input_anything_and_click_on_submit() throws Throwable {
		getVisibleElementByXpath("//button[text()='Request A Test Drive']").click();
	}

	@Then("^Verify error message is seen$")
	public void verify_error_message_is_seen() throws Throwable {
		System.out.println("Verify error message is seen");
		List<WebElement> ErrorMsgs=driver.findElements(By.xpath("//small[not(contains(@class,'errRecaptcha')) and not(contains(@class,'hint')) and text()!='']"));
		if(ErrorMsgs.size()==0) {
			Assert.assertFalse("No error messages displayed for blank/invalid data submission on the form", true);
		}else {
			System.out.println("Error messages sucessfully displayed for invalid email on the form");
		}
	}

	@When("^On overlay enter an incorrect item on one of the fields and click submit$")
	public void on_overlay_enter_an_incorrect_item_on_one_of_the_fields_and_click_submit(DataTable param) throws Throwable {
		System.out.println("On overlay enter an incorrect item on one of the fields and click submit");
		List<List<String>> data=param.raw();
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_FIRST_NAME']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_LAST_NAME']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='Email_ID']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='MOBILENO']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//span[contains(text(),'State')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+data.get(0).get(4)+"']").click();
		getVisibleElementByXpath("//span[contains(text(),'City')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+data.get(0).get(5)+"']").click();
		getVisibleElementByXpath("//span[contains(text(),'Preferred Dealer')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[contains(text(),'"+data.get(0).get(6)+"')]").click();
		getVisibleElementByXpath("//span[contains(text(),'Model')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+data.get(0).get(7)+"']").click();
		getVisibleElementByXpath("//span[contains(text(),'I plan to buy a new vehicle')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+data.get(0).get(8)+"']").click();
		getVisibleElementByXpath("//div[@class='acknowledgement_checkbox checkbox']/div/fieldset/label").click();
		getVisibleElementByXpath("//div[contains(@class,'form-submit')]//*[contains(text(),'Test Drive')]").click();
	}

	@When("^On overlay enter all items correctly and click on submit$")
	public void on_overlay_enter_all_items_correctly_and_click_on_submit(DataTable param) throws Throwable {
		System.out.println("On overlay enter all items correctly and click on submit");
		List<List<String>> data=param.raw();
		getVisibleElementByXpath("//input[@name='Email_ID']").clear();
		Thread.sleep(2000);
		getVisibleElementByXpath("//input[@name='Email_ID']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='TITLE']").sendKeys("ABCD");
		getVisibleElementByXpath("//div[contains(@class,'form-submit')]//*[contains(text(),'Test Drive')]").click();

	}


	@Then("^Verify thank you page is seen$")
	public void verify_thank_you_page_is_seen() throws Throwable {
		System.out.println("Verify thank you page is seen");
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==false) {
			waitTillElemVisiblebyXpath("//div[@class='desktop hideForMobile']//*[contains(text(),'Thank You')]", 240);
			getVisibleElementByXpath("//div[@class='desktop hideForMobile']//*[contains(text(),'Thank You')]");
		}
	}


	@Then("^Close overlay$")
	public void close_overlay() throws Throwable {
		System.out.println("Close overlay");
		waitTillElemVisiblebyXpath("//*[@class='icon-plus icon-x']", 240);
		getVisibleElementByXpath("//*[@class='icon-plus icon-x']").click();
	}


	@Then("^Verify brochure download overlay is seen$")
	public void verify_brochure_download_overlay_is_seen() throws Throwable {
		System.out.println("Verify brochure download overlay is seen");
		waitTillElemVisiblebyXpath("//*[text()='Download PDF Brochure']", 240);
		int GetVehicleCount=driver.findElements(By.xpath("//*[contains(@id,'checkboxLabel_')]")).size();
		if(GetVehicleCount==0) {
			Assert.assertFalse("Vehicle image does not displayed to download brochure.",true);
		}else {
			System.out.println("Total " + GetVehicleCount + " vehicles displayed to download browchure");
		}
	}

	@When("^Do not select any vehicle and click on Download button$")
	public void do_not_select_any_vehicle_and_click_on_Download_button() throws Throwable {
		System.out.println("Do not select any vehicle and click on Download button");
		List<WebElement> unchkAllVehicle=driver.findElements(By.xpath("//*[contains(@id,'checkboxLabel_') and contains(@class,'checked')]//preceding::div/img"));
		for(WebElement e:unchkAllVehicle) {
			e.click();
			Thread.sleep(2000);
		}
		getVisibleElementByXpath("//*[text()='Download PDF Brochure']").click();
		Thread.sleep(2000);
	}

	@Then("^see validation message is displayed$")
	public void see_validation_message_is_displayed() throws Throwable {
		System.out.println("see validation message is displayed");
		getVisibleElementByXpath("//div[@id='brochureError' and text()!='']");
	}

	@Then("^See validation message is displayed$")
	public void See_validation_message_is_displayed() throws Throwable {
		System.out.println("See validation message is displayed");
		getVisibleElementByXpath("//small[not(contains(@class,'errRecaptcha')) and not(contains(@class,'hint')) and text()!='']");
	}

	@When("^Select a vehicle on brochure download overlay$")
	public void select_a_vehicle_on_brochure_download_overlay() throws Throwable {
		System.out.println("Select a vehicle on brochure download overlay");
		driver.findElements(By.xpath("//*[contains(@id,'checkboxLabel_')]//preceding::div/img")).get(0).click();

	}

	@When("^click on Get By Email button$")
	public void click_on_Get_By_Email_button() throws Throwable {
		System.out.println("click on Get By Email button");
		getVisibleElementByXpath("//*[contains(text(),'by mail')]").click();
	}

	@Then("^verify Email form is opened$")
	public void verify_Email_form_is_opened() throws Throwable {
		System.out.println("verify Email form is opened");
		waitTillElemVisiblebyXpath("//input[@type='text' and @name='INDIVIDUAL_CUST_FIRST_NAME']", 240);
		getVisibleElementByXpath("//input[@type='text' and @name='INDIVIDUAL_CUST_FIRST_NAME']");
		getVisibleElementByXpath("//input[@type='text' and @name='PRIMARY_EMAIL']");
		getVisibleElementByXpath("//input[@type='text' and @name='recaptcha']");
	}

	@Then("^Fill all the details on Email form$")
	public void fill_all_the_details_on_Email_form(DataTable param) throws Throwable {
		System.out.println("Fill all the details on Email form");
		List<List<String>> data=param.raw();
		getVisibleElementByXpath("//input[@type='text' and @name='INDIVIDUAL_CUST_FIRST_NAME']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@type='text' and @name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys("ABCD");
	}

	@When("^Click on submit email form$")
	public void click_on_submit_email_form() throws Throwable {
		System.out.println("Click on submit email form");
		getVisibleElementByXpath("//*[text()='Submit']").click();
	}

	@Then("^verify Thank you message$")
	public void verify_Thank_you_message() throws Throwable {
		System.out.println("Verify thank you page is seen");
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==false) {
			waitTillElemVisiblebyXpath("//div[@class='desktop hideForMobile']//*[text()='Thank You']", 240);
			getVisibleElementByXpath("//div[@class='desktop hideForMobile']//*[text()='Thank You']");
		}
	}

	@Then("^Select a vehicle and click on download brochure button$")
	public void select_a_vehicle_and_click_on_download_brochure_button() throws Throwable {
		System.out.println("Select a vehicle and click on download brochure button");
		Boolean selectvehicle=false;
		waitTillElemVisiblebyXpath("//*[contains(@for,'brochureBtn') and contains(@for,'download') and contains(@for,'brochure')]", 300);
		List<WebElement> unchkAllVehicle=driver.findElements(By.xpath("//*[contains(@id,'checkboxLabel_') and contains(@class,'checked')]//preceding::div/img"));
		for(WebElement e:unchkAllVehicle) {
			e.click();
			Thread.sleep(2000);
			selectvehicle=true;
		}
		if(unchkAllVehicle.size()>0 && selectvehicle==true) {
			driver.findElements(By.xpath("//*[contains(@id,'checkboxLabel_')]//preceding::div/img")).get(0).click();
		}
		Thread.sleep(5000);
		getVisibleElementByXpath("//*[text()='Download PDF Brochure']").click();
		Thread.sleep(2000);

	}

	@Then("^Verify PDF file is downloaded and thank you page is seen$")
	public void verify_PDF_file_is_downloaded_and_thank_you_page_is_seen(DataTable param) throws Throwable {
		System.out.println("Verify thank you page is seen");
		waitTillElemVisiblebyXpath("//div[@class='desktop hideForMobile']//*[text()='Thank You']", 240);
		getVisibleElementByXpath("//div[@class='desktop hideForMobile']//*[text()='Thank You']");	
		List<List<String>> data = param.raw();
		String Brochurepath = config.Configuration.PATH_TO_DOWNLOAD_BROCHURE + data.get(0).get(0) + ".pdf";
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
			Assert.assertFalse("Brochure download failed, pdf file does not exist locally", true);
		}
	}



	@Then("^user is navigated to Ford Fleet Registration overlay$")
	public void user_is_navigated_to_Ford_Fleet_Registration_overlay() throws Throwable {
		System.out.println("user is navigated to Ford Fleet Registration overlay");
		waitTillElemVisiblebyXpath("//div[contains(@class,'form-checkbox')]//span[text()!='']", 240);	
		getVisibleElementByXpath("//span[contains(text(),'Title')]/following-sibling::div//span[@id!='']");
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_FIRST_NAME']");
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_LAST_NAME']");
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']");
		getVisibleElementByXpath("//input[@name='BUSINESS_PHONE_NUMBER']");
		getVisibleElementByXpath("//input[@name='BUSINESS_CUST_FIRST_LINE_NAME']");
		getVisibleElementByXpath("//input[@name='BUSINESS_NUMBER']");
		getVisibleElementByXpath("//input[@name='FLEET_SIZE']");

		getVisibleElementByXpath("//div[contains(@class,'form-submit')]//*[contains(text(),'Test Drive') or contains(text(),'Register')]");
	}


	@When("^Enter an incorrect item on one of the fields and click submit$")
	public void enter_an_incorrect_item_on_one_of_the_fields_and_click_submit(DataTable arg1) throws Throwable {
		System.out.println("Enter an incorrect item on one of the fields and click submit");
		List<List<String>> data=arg1.raw();
		getVisibleElementByXpath("//span[contains(text(),'Title')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+data.get(0).get(0)+"']").click();
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_FIRST_NAME']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_LAST_NAME']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='BUSINESS_PHONE_NUMBER']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//input[@name='BUSINESS_CUST_FIRST_LINE_NAME']").sendKeys(data.get(0).get(5));
		getVisibleElementByXpath("//input[@name='BUSINESS_NUMBER']").sendKeys(data.get(0).get(6));
		getVisibleElementByXpath("//input[@name='FLEET_SIZE']").sendKeys(data.get(0).get(7));
		for(WebElement ChkChkbox:driver.findElements(By.xpath("//div[contains(@class,'form-checkbox')]//span"))) {
			ChkChkbox.click();
			Thread.sleep(1000);
		}
		if(driver.findElements(By.xpath("//label[@for='user_legal_agreement']/following-sibling::small")).size()>0) {			
			getVisibleElementByXpath("//label[@for='user_legal_agreement']//span").click();
		}
		getVisibleElementByXpath("//div[contains(@class,'form-submit')]//*[contains(text(),'Test Drive') or contains(text(),'Register')]").click();
	}

	@When("^Enter an incorrect item on one of the fields and click submit kmi$")
	public void enter_an_incorrect_item_on_one_of_the_fields_and_click_submit_kmi(DataTable arg1) throws Throwable {
		System.out.println("Enter an incorrect item on one of the fields and click submit kmi");
		List<List<String>> data=arg1.raw();
		getVisibleElementByXpath("//span[contains(text(),'Title')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+data.get(0).get(0)+"']").click();
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_FIRST_NAME']").sendKeys(data.get(0).get(1));
		getVisibleElementByXpath("//input[@name='INDIVIDUAL_CUST_LAST_NAME']").sendKeys(data.get(0).get(2));
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(3));
		getVisibleElementByXpath("//input[@name='MOBILE_PHONE_NUMBER']").sendKeys(data.get(0).get(4));
		getVisibleElementByXpath("//input[@name='CUSTOMER_POSTAL_CODE']").sendKeys(data.get(0).get(5));
		getVisibleElementByXpath("//span[contains(text(),'plan') or contains(text(),'vehicle')]/following-sibling::div//span[@id!='']").click();
		getVisibleElementByXpath("//li/div[text()='"+data.get(0).get(6)+"']").click();
		for(WebElement ChkChkbox:driver.findElements(By.xpath("//div[contains(@class,'form-checkbox')]//span"))) {
			ChkChkbox.click();
			Thread.sleep(1000);
		}
		if(driver.findElements(By.xpath("//label[@for='user_legal_agreement']/following-sibling::small")).size()>0) {			
			getVisibleElementByXpath("//label[@for='user_legal_agreement']//span").click();
		}
		getVisibleElementByXpath("//div[contains(@class,'form-submit')]//*[contains(text(),'Test Drive') or contains(text(),'Register') or contains(text(),'Submit')]").click();
	}

	@When("^Click on Print button$")
	public void click_on_Print_button() throws Throwable {
		System.out.println("Click on Print button");
		getVisibleElementByXpath("//input[contains(@value,'Print')]").click();
		Thread.sleep(5000);
	}

	@Then("^See print functionality is enabled and working successfully$")
	public void see_print_functionality_is_enabled_and_working_successfully() throws Throwable {
		System.out.println("See print functionality is enabled and working successfully");
		if(driver.getWindowHandles().size()>1) {
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}			
		}
		getVisibleElementByXpath("//*[contains(@class,'print') and contains(text(),'Print')]").click();
		Thread.sleep(5000);
		if(driver.getWindowHandles().size()>1) {
			Assert.assertFalse("print functionality is not working, seems disabled.", true);			
		}else {
			System.out.println("print functionality is enabled and working successfully");
		}
	}

	@When("^enter all items correctly and click on submit$")
	public void enter_all_items_correctly_and_click_on_submit(DataTable arg1) throws Throwable {

		System.out.println("enter all items correctly and click on submit");
		List<List<String>> data=arg1.raw();
		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").clear();
		Thread.sleep(2000);
		getVisibleElementByXpath("//input[@name='PRIMARY_EMAIL']").sendKeys(data.get(0).get(0));
		getVisibleElementByXpath("//input[@name='recaptcha']").sendKeys("ABCD");
		getVisibleElementByXpath("//div[contains(@class,'form-submit')]//*[contains(text(),'Test Drive') or contains(text(),'Register') or contains(text(),'Submit')]").click();
	}

	@When("^Click on Video$")
	public void Click_on_Video() throws Throwable {
		System.out.println("Click on Video");
		getVisibleElementByXpath("//div[contains(@class,'media-carousel-video')]//picture").click();
		Thread.sleep(5000);
		waitTillElemVisiblebyXpath("//div[contains(@class,'overlay-container') and contains(@class,'overlay-video') and contains(@class,'visible')]", 240);
	}

	@Then("^see video is playing without error$")
	public void see_video_is_playing_without_error() throws Throwable {
		System.out.println("see video is playing without error");
		getVisibleElementByXpath("//button[contains(@class,'vjs-button') and contains(@title,'Pause')]");
		getVisibleElementByXpath("//div[contains(@id,'videoplayer')]//div[contains(@class,'vjs-playing')]");
	}


	@When("^Input text to Search and click Search$")
	public void inputTxtAndSearch(DataTable position) throws Throwable {
		System.out.println("Input text to Search Input and click Search");
		getVisibleElementByXpath("//input[@id='state']").clear();
		Thread.sleep(2000);
		getVisibleElementByXpath("//input[@id='state']").sendKeys(position.raw().get(0).get(0));
		getVisibleElementByXpath("//*[contains(@class,'mini-locate-a-dealer')]//*[contains(@class,'lincoln-icon_search')]").click();
	}


	@When("^Appropriate validation message displayed$")
	public void Appropriate_validation_message_displayed() throws Throwable {
		System.out.println("Appropriate validation message displayed");
		Thread.sleep(2000);
		getVisibleElementByXpath("//p[contains(@data-ng-show,'errorMessage') and contains(@class,'error')]");
	}


	@Then("^Dealer should be searched and shown on the page$")
	public void Dealer_should_be_searched_and_shown_on_the_page() throws Throwable {
		System.out.println("Dealer should be searched and shown on the page");
		Thread.sleep(2000);
		getVisibleElementByXpath("//div[contains(@class,'miniDealerLocator') and contains(@class,'service-ok')]//div[contains(@class, 'expander-head')]/a[contains(@class,'expander')]");
		getVisibleElementByXpath("//div[contains(@class,'miniDealerLocator') and contains(@class,'service-ok')]//a[contains(@data-ng-click,'showDealerInfo') and text()!='']");

	}

	@When("^Click on \"([^\"]*)\"$")
	public void click_on(String arg1) throws Throwable {
		getVisibleElementByXpath("//div[contains(@class,'miniDealerLocator') and contains(@class,'service-ok')]//div[contains(@class, 'expander-head')]/a[contains(@class,'expander')]").click();
	}

	@Then("^Dealer section should be expanded and dealers should be displayed$")
	public void dealer_section_should_be_expanded_and_dealers_should_be_displayed() throws Throwable {
		clickOnLinks("(//span[@class='marker-label'])[1]");
		getVisibleElementByXpath("//div[contains(@class,'miniDealerLocator') and contains(@class,'service-ok')]//a[contains(@class,'DealerUrl') and text()!='']");
		getVisibleElementByXpath("//div[contains(@class,'miniDealerLocator') and contains(@class,'service-ok')]//span[contains(@class,'marker') and text()='1']");
	}

	@Then("^Dealer section should be compressed and First dealer should be shown$")
	public void dealer_section_should_be_compressed_and_First_dealer_should_be_shown() throws Throwable {
		verifyInvisibleElem("//div[contains(@class,'dealer-info') and contains(@class,'expanded')]//a[contains(@class,'DealerUrl') and text()!='']");
		verifyInvisibleElem("//div[contains(@class,'dealer-info') and contains(@class,'expanded')]//span[contains(@class,'marker') and text()='1']");
		getVisibleElementByXpath("//div[contains(@data-ng-class,'showResults') and contains(@class,'search-completed')]//a[contains(@class,'ng-binding') and text()!='']"); //Your nearest dealer - 1st Dealer search
	}

	@And("^Select any model from the list and unselect after that$")
	public void Select_any_model_from_the_list_and_unselect_after_that() throws Throwable {
		System.out.println("Select any model from the list and unselect after that");
		String selectModel=getVisibleElementByXpath("//div[contains(@class,'active')]//div[@class='input-checkbox'][1]//following-sibling::h3").getText().trim();
		getVisibleElementByXpath("//div[contains(@class,'active')]//div[@class='input-checkbox'][1]").click();
		getVisibleElementByXpath("//div[contains(@class,'active')]//h3[text()='"+selectModel+"']/preceding-sibling::div[contains(@class,'input-checkbox')][1]").click();
	}

	@When("^Select any model$")
	public void Select_any_model() throws Throwable {
		System.out.println("Select any model");
		CompareModelname=getVisibleElementByXpath("//div[contains(@class,'active')]//div[@class='input-checkbox'][1]//following-sibling::h3").getText().trim();
		getVisibleElementByXpath("//div[contains(@class,'active')]//div[@class='input-checkbox'][1]").click();
	}

	@Then("^See the old model should be replaced with the new selected models$")
	public void See_the_old_model_should_be_replaced_with_the_new_selected_models() throws Throwable {
		System.out.println("See the old model should be replaced with the new selected models");
		getVisibleElementByXpath("//li//*[contains(@class,'active') and contains(@class,'model-name') and contains(text(),'"+CompareModelname+"')]");
	}

	@And("^Select a model and click on Cancel button$")
	public void Select_a_model_and_click_on_Cancel_button() throws Throwable {
		System.out.println("Select a model and click on Cancel button");
		Select_any_model();
		getVisibleElementByXpath("//div[contains(@class,'active')]//a[contains(@class,'cancel')]").click();
	}

	@Then("^See selected vehicle should not be added in compare model page$")
	public void See_selected_vehicle_should_not_be_added_in_compare_model_page() throws Throwable {
		System.out.println("See selected vehicle should not be added in comapre model page");
		verifyInvisibleElem("//li//*[contains(@class,'active') and contains(@class,'model-name') and contains(text(),'"+CompareModelname+"')]");
	}

	@Then("^Compare model result page should be displayed with selected model listed with details$")
	public void Compare_model_result_page_should_be_displayed_with_selected_model_listed_with_details() throws Throwable {
		System.out.println("Compare model result page should be displayed with selected model listed with details");
		List<WebElement> ModelDetails = driver.findElements(By.xpath("//*[contains(@class,'model-name') and contains(@class,'active')]"));
		if(ModelDetails.size()!=3) {
			Assert.assertFalse("Selected model does not listed on compared model page, discrepancy in compared vehicles count.", false);
		}

		List<WebElement> detailsSection = driver.findElements(By.xpath("//*[contains(@class,'compare-details')]//li[contains(@class,'show') and contains(@data-toggle,'model-id')]"));
		if(detailsSection.size()<3) {
			Assert.assertFalse("Compared model details does not displayed on compared model page.", false);
		}
	}

	@When("^Scroll down profile page (\\d+)$")
	public void Scroll_down_profile_page(int arg1) throws Throwable {
		System.out.println("Scroll down profile page");
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(0, 1500)");
	}

	@Then("^Model name & remove button should be sticky on the page top$")
	public void Model_name_remove_button_should_be_sticky_on_the_page_top() throws Throwable {
		System.out.println("Model name & remove button should be sticky on the page top");
		getVisibleElementByXpath("//div[contains(@class,'compare-model-select-sticky') and contains(@class,'active')]//button[contains(@data-ng-click,'removeVehicle')]");
		getVisibleElementByXpath("//div[contains(@class,'compare-model-select-sticky') and contains(@class,'active')]//*[contains(@class,'model-selected-title')]");
	}

	@Then("^Click on Remove button for any model$")
	public void Click_on_Remove_button_for_any_model() throws Throwable {
		System.out.println("Click on Remove button for any model");
		CompareModelname=getVisibleElementByXpath("//div[contains(@class,'compare-model-select-sticky') and contains(@class,'active')]//*[contains(@class,'model-selected-title')]").getText().trim();
		getVisibleElementByXpath("//div[contains(@class,'compare-model-select-sticky') and contains(@class,'active')]//button[contains(@data-ng-click,'removeVehicle')]").click();
	}

	@Then("^Selected model should be removed$")
	public void Selected_model_should_be_removed() throws Throwable {
		System.out.println("Selected model should be removed");
		verifyInvisibleElem("//div[contains(@class,'compare-model-select-sticky') and contains(@class,'active')]//*[contains(@class,'model-selected-title') and contains(text(),'"+CompareModelname+"')]");
	}

	@When("^Click on any Article$")
	public void Click_on_any_Article() throws Throwable {
		System.out.println("Click on any Article");
		NewsrmArt=getVisibleElementByXpath("//div[contains(@class,'row')]//div[contains(@class,'blocks-news')]//a[text()!='' and not(contains(@class,'cta-button'))]").getText().trim();
		Newsrmdt=getVisibleElementByXpath("//div[contains(@class,'row')]//div[contains(@class,'blocks-news')]//div[contains(@class,'date-year')]").getText().trim();
		Newsrmdata=getVisibleElementByXpath("//div[contains(@class,'row')]//div[contains(@class,'blocks-news')]//div[contains(@class,'txt-description')]").getText().trim();
		getVisibleElementByXpath("//div[contains(@class,'row')]//div[contains(@class,'blocks-news')]//a[text()!='' and not(contains(@class,'cta-button'))]").click();
	}

	@Then("^Article page should display$")
	public void Article_page_should_display() throws Throwable {
		System.out.println("Article page should display");
		String ActualArt=getVisibleElementByXpath("//*[contains(@class,'title-article') and text()!='']").getText().trim();
		String Actualdt=getVisibleElementByXpath("//*[contains(@class,'date-time-article') and text()!='']").getText().trim();
		if(ActualArt!=NewsrmArt || Actualdt!=Newsrmdt) {
			if(ActualArt.toLowerCase()!=NewsrmArt.toLowerCase() || Actualdt!=Newsrmdt) {
				Assert.assertFalse("Discrepency in Article heading/Date, please check", false);
			}
		}
		driver.navigate().back();
	}


	@Then("^See Select all Models checkbox available above model display$")
	public void See_Select_all_Models_checkbox_available_above_model_display() throws Throwable {
		System.out.println("See Select all Models checkbox available above model display");
		getVisibleElementByXpath("//label[@class='select-all-wrap']//span");
	}


	@When("^check Select all the models$")
	public void check_Select_all_the_models() throws Throwable {
		System.out.println("check Select all the models");
		List<WebElement> modelselected = driver.findElements(By.cssSelector("div.models-list.clearfix div.item div.name-item.on-select-mode div.select-model.on.is-checked"));
		System.out.println("# of models selected are:" + modelselected.size());
		getVisibleElementByXpath("//label[@class='select-all-wrap']//span").click();
	}

	@When("^All the models available for comparing should be checked$")
	public void All_the_models_available_for_comparing_should_be_checked() throws Throwable {
		System.out.println("All the models available for comparing should be checked");
		List<WebElement> modelselected = driver.findElements(By.cssSelector("div.models-list.clearfix div.item div.name-item.on-select-mode div.select-model.on.is-checked"));
		System.out.println("# of models selected are:" + modelselected.size());
		if(modelselected.size()==0) {
			Assert.assertFalse("Models does not be selected on select all check box", false);
		}
	}

	@When("^Select any value in Sort By dropdown list$")
	public void Select_any_value_in_Sort_By_dropdown_list(DataTable param) throws Throwable {
		System.out.println("Select any value in Sort By dropdown list");
		List<List<String>> data=param.raw();
		getVisibleElementByXpath("//div[contains(@class,'compare-models-sort')]//span[text()!='']").click();
		getVisibleElementByXpath("//li//div[text()='"+param.raw().get(0).get(0)+"']").click();
		Thread.sleep(2000);
	}

	@When("^Models should be sorted based on the selected value in Sort By dropdown list \"(.*?)\"$")
	public void Models_should_be_sorted_based_on_the_selected_value_in_Sort_By_dropdown_list(String SortBy) throws Throwable {
		System.out.println("Models should be sorted based on the selected value in Sort By dropdown list");
		ArrayList<String> arrayList = new ArrayList<>();
		ArrayList<String> originalmodelList = new ArrayList<>();
		List<WebElement> ModelList=(ArrayList<WebElement>) driver.findElements(By.xpath("//div[@data-starting-price!='']"));
		for(WebElement models:ModelList) {
			arrayList.add(models.getAttribute("data-price"));
			originalmodelList.add(models.getAttribute("data-price"));
		}
		if(SortBy=="high first") {
			Collections.sort(arrayList,Collections.reverseOrder());
		}else if(SortBy=="low first") {
			Collections.sort(arrayList);
		}
		if(!arrayList.equals(originalmodelList)) {
			Assert.assertFalse("Sort By " + SortBy + " is not working", false);
		}
	}

	@When("^Click on any vehicle and drag & drop and see Vehicle is reordered at desired drop position$")
	public void Click_on_any_vehicle_and_drag_and_drop_it_manually_to_reorder() throws Throwable {
		System.out.println("Click on any vehicle and drag & drop and see Vehicle is reordered at desired drop position");
		getVisibleElementByXpath("//*[contains(@class,'model-name') and text()!='']//ancestor::li[contains(@class,'ui-sortable-handle')]");
		ArrayList<String> arrayList = new ArrayList<>();
		ArrayList<String> originalmodelList = new ArrayList<>();
		List<WebElement> ModelList=(ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@class,'model-name') and text()!='']"));
		for(WebElement models:ModelList) {
			arrayList.add(models.getText().trim());
		}
		String model1=arrayList.get(0);
		String model2=arrayList.get(1);
		arrayList.set(0, model2);
		arrayList.set(1, model1);
		WebElement dragModel=driver.findElements(By.xpath("//*[contains(@class,'model-name') and text()!='']//ancestor::li[contains(@class,'ui-sortable-handle')]")).get(0);
		WebElement dropModel=driver.findElements(By.xpath("//*[contains(@class,'model-name') and text()!='']//ancestor::li[contains(@class,'ui-sortable-handle')]")).get(1);
		Actions action = new Actions(driver);
		action.dragAndDrop(dragModel, dropModel).build().perform();

		ModelList=(ArrayList<WebElement>) driver.findElements(By.xpath("//*[contains(@class,'model-name') and text()!='']"));
		for(WebElement models:ModelList) {
			originalmodelList.add(models.getText().trim());
		}
		if(!arrayList.equals(originalmodelList)) {
			Assert.assertFalse("Model reorder Drag and Drop failed", false);
		}
	}

	@Then("^Feature accordion should be hidden by default$")
	public void Feature_accordion_should_be_hidden_by_default() throws Throwable {
		System.out.println("Feature accordion should be hidden by default");
		List<WebElement> featureaccordion = driver.findElements(By.xpath("//div[contains(@class,'accordion-wrap') and contains(@class,'opened')]"));
		if(featureaccordion.size()!=0) {
			Assert.assertFalse("one of the feature accordion is not hidden by default, please check", false);
		}
	}

	@When("^Click on any feature accordion$")
	public void Click_on_any_feature_accordion() throws Throwable {
		System.out.println("Click on any feature accordion");
		getVisibleElementByXpath("//label[contains(@class,'filter-compare-model-difference')]//*[contains(@class,'show-difference')]");
		getVisibleElementByXpath("//label[contains(@class,'filter-compare-model-difference')]//*[contains(@class,'show-difference')]");
		int i=driver.findElements(By.xpath("//div[contains(@class,'accordion-wrap ng-scope') and not(contains(@class,'opened'))]")).size();
		driver.findElements(By.xpath("//section[contains(@class,'compare-accordions')]//div[contains(@class,'accordion-wrap') and not(contains(@class,'opened'))]")).get(i-1).click();
	}

	@Then("^The accordion should anchor to the bottom of the section$")
	public void The_accordion_should_anchor_to_the_bottom_of_the_section() throws Throwable {
		System.out.println("The accordion should anchor to the bottom of the section");
		getVisibleElementByXpath("//div[contains(@class,'accordion-wrap')]/div[contains(@class,'accordion-head')]//following-sibling::div[contains(@class,'accordion-content')]");
	}

	@When("^Click on feature category dropdown filter$")
	public void Click_on_feature_category_dropdown_filter() throws Throwable {
		System.out.println("Click on feature category dropdown filter");
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(1000, 0)"); // if the element is on top.
		getVisibleElementByXpath("//span[@class='site-link-title' and text()='Vehicles']");
		getVisibleElementByXpath("//span[@class='site-link-title' and text()='Shop']");
		getVisibleElementByXpath("//div[contains(@class,'filter-compare-model-dropdown')]//a").click();
	}

	@Then("^Feature categories filter should contain all available features categories$")
	public void Feature_categories_filter_should_contain_all_available_features_categories() throws Throwable {
		System.out.println("Feature categories filter should contain all available features categories");
		ArrayList<String> addFeature = new ArrayList<>();
		List<WebElement> FeatureList=driver.findElements(By.xpath("//div[contains(@class,'model-compare-filter-dropdown')]//li/div[text()!='' and not(contains(text(),'Select all'))]"));
		for(WebElement feature:FeatureList) {
			addFeature.add(feature.getText().trim());
		}
		driver.findElements(By.xpath("//div[contains(@class,'model-compare-filter-dropdown')]//li/div[text()!='']")).get(0).click();

		List<WebElement> FeatureListAcc=driver.findElements(By.xpath("//div[contains(@class,'accordion-wrap') and not(contains(@class,'opened'))]//h2[text()!='']"));
		for(WebElement feature:FeatureListAcc) {
			if(!addFeature.contains(feature.getText().trim())) {
				Assert.assertFalse("Missing feature, name:"+feature.getText().trim(), false);
			}
		}
	}

	@When("^Click on Feature category filter and select any value$")
	public void Click_on_Feature_category_filter_and_select_any_value() throws Throwable {
		System.out.println("Click on Feature category filter and select any value");
		getVisibleElementByXpath("//div[contains(@class,'filter-compare-model-dropdown')]//a").click();
		CompareModelname=getVisibleElementByXpath("//div[contains(@class,'model-compare-filter-dropdown')]//li/div[text()!='' and not(contains(text(),'Select all'))]").getText().trim();
		Temp=driver.findElements(By.xpath("//div[contains(@class,'model-compare-filter-dropdown')]//li/div[text()!='' and not(contains(text(),'Select all'))]")).size();
		getVisibleElementByXpath("//div[contains(@class,'model-compare-filter-dropdown')]//li/div[text()!='' and not(contains(text(),'Select all'))]").click();
	}

	@Then("^Model compare results should be filtered based on selected feature category$")
	public void Model_compare_results_should_be_filtered_based_on_selected_feature_category() throws Throwable {
		System.out.println("Model compare results should be filtered based on selected feature category");
		getVisibleElementByXpath("//div[contains(@class,'accordion-wrap') and not(contains(@class,'hide'))]//h2[contains(text(),'"+CompareModelname+"')]");
		int sortedFeature=driver.findElements(By.xpath("//div[contains(@class,'accordion-wrap') and contains(@class,'hide')]//h2[not(contains(text(),'"+CompareModelname+"'))]")).size();
		if(sortedFeature!=(Temp-1)) {
			Assert.assertFalse("Feature hide is not working", false);
		}

	}

	@When("^Click the checkbox Show difference only$")
	public void Click_the_checkbox_Show_difference_only() throws Throwable {
		System.out.println("Click the checkbox Show difference only");
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("scroll(1000, 0)"); // if the element is on top.
		getVisibleElementByXpath("//span[@class='site-link-title' and text()='Vehicles']");
		getVisibleElementByXpath("//span[@class='site-link-title' and text()='Shop']");
		getVisibleElementByXpath("//div[contains(@class,'filter-compare-model-dropdown')]//a").click();
		getVisibleElementByXpath("//div[contains(@class,'model-compare-filter-dropdown')]//li/div[text()!='' and contains(text(),'Select all')]").click();
		Thread.sleep(2000);
		List<WebElement> FeatureListAcc=driver.findElements(By.xpath("//div[contains(@class,'accordion-wrap')]//h2[text()!='']"));
		for(WebElement feature:FeatureListAcc) {
			String featurename=feature.getText().trim();
			int totalfeature=driver.findElements(By.xpath("//div[contains(@class,'accordion-wrap')]/div[contains(@class,'accordion-head')]//following-sibling::div[contains(@class,'accordion-content')]//ul[contains(@class,'features-list')]//span[contains(@data-minheight,'"+featurename+"')]")).size();
			showdiff.put(featurename,totalfeature);
		}
		getVisibleElementByXpath("//label[contains(@class,'filter-compare-model-difference')]//*[contains(@class,'show-difference')]").click();
	}

	@Then("^Features with difference among models must be displayed$")
	public void Features_with_difference_among_models_must_be_displayed() throws Throwable {
		System.out.println("Features with difference among models must be displayed");
		getVisibleElementByXpath("//div[contains(@class,'accordion-wrap') and not(contains(@class,'hide'))]//h2[text()!='']");
		List<WebElement> sortedFeature=driver.findElements(By.xpath("//div[contains(@class,'accordion-wrap') and not(contains(@class,'hide'))]//h2[text()!='']"));
		for(WebElement feature:sortedFeature) {
			String featurename=feature.getText().trim();
			int totalfeature=driver.findElements(By.xpath("//div[contains(@class,'accordion-wrap')]/div[contains(@class,'accordion-head')]//following-sibling::div[contains(@class,'accordion-content')]//ul[contains(@class,'features-list')]//span[contains(@data-minheight,'"+featurename+"') and text()!='']")).size();
			if(showdiff.get(featurename).intValue()==totalfeature) {
				Assert.assertFalse("error in features with difference among models", false);
			}
		}

	}

	@When("^Click on the X icon for any model on compare model result page$")
	public void Click_on_the_X_icon_for_any_model_on_compare_model_result_page() throws Throwable {
		System.out.println("Click on the X icon for any model on compare model result page");
		CompareModelname=getVisibleElementByXpath("//*[contains(@class,'model-name') and text()!='']").getText().trim();
		getVisibleElementByXpath("//*[contains(@class,'model-name') and text()!='']//span[contains(@class,'remove-icon')]").click();
	}

	@Then("^The selected model should be removed using it$")
	public void The_selected_model_should_be_removed_using_it() throws Throwable {
		System.out.println("The selected model should be removed using it");
		verifyInvisibleElement("//*[contains(@class,'model-name') and contains(text(),'"+CompareModelname+"')]"); 
	}

	@When("^Click on Select other model button$")
	public void Click_on_Select_other_model_button() throws Throwable {
		System.out.println("Click on Select other model button");
		getVisibleElementByXpath("//span[contains(@data-ng-click,'openSelectModelsOverlay') and contains(@data-ng-click,'add')]").click();
	}

	@And("^Select any other available model in model select page and click on Confirm$")
	public void Select_any_other_available_model_in_model_select_page_and_click_on_Confirm() throws Throwable {
		System.out.println("Select any other available model in model select page and click on Confirm");
		CompareModelname=getVisibleElementByXpath("//*[contains(@class,'model-name') and text()!='']").getText().trim();
		getVisibleElementByXpath("//*[contains(@class,'model-name') and text()!='']//span[contains(@class,'remove-icon')]").click();
	}

	@Then("^Right side existing model will be replaced by the selected model$")
	public void Right_side_existing_model_will_be_replaced_by_the_selected_model() throws Throwable {
		System.out.println("Right side existing model will be replaced by the selected model");
		verifyInvisibleElement("//*[contains(@class,'model-name') and contains(text(),'"+CompareModelname+"')]"); 
	}

	@Then("^The selected model should be displayed$")
	public void The_selected_model_should_be_displayed() throws Throwable {
		System.out.println("The selected model should be displayed");		
		List<WebElement> model=driver.findElements(By.xpath("//*[contains(@class,'model-name')]"));
		boolean displaymodel=false;
		for(WebElement mdl:model) {
			if(mdl.getText().trim().contains(CompareModelname)) {
				displaymodel=true; 
			}
		}
		if(displaymodel==false) {
			Assert.assertFalse("Newly added model does not exist.", false);
		}
	}

	@Then("^Page UI open correctly and Reseptive country page opened as per selection$")
	public void Page_UI_open_correctly_and_Reseptive_country_page_opened_as_per_selection(DataTable args) throws Throwable {
		System.out.println("Page UI open correctly and Reseptive country page opened as per selection");
		for(List<String> Rw:args.raw()) {
			for(String Col:Rw) {
				getVisibleElementByXpath("//a[contains(text(),'"+Col+"')]").click();
				Thread.sleep(2000);
				getVisibleElementByXpath("//span[contains(@class,'site-link-title')]");
				driver.navigate().back();
			}
		}
	}		

	@Then("^Bright cove should be playing$")
	public void Bright_cove_should_be_playing() throws Throwable {
		System.out.println("Bright cove should be playing");
	//	getVisibleElementByXpath("//button[@class='vjs-icon-placeholder']");//div[contains(@id,'videoplayer')]//div[contains(@class,'video-js') and (not(contains(@class,'vjs-paused')) or not(contains(@class,'vjs-ended')) and contains(@class,'vjs-playing'))]");
		do {
			Thread.sleep(5000);
		}while(driver.findElements(By.xpath("//div[contains(@id,'videoplayer')]//div[contains(@class,'video-js') and (contains(@class,'vjs-paused') or contains(@class,'vjs-ended'))]")).size()==0);
	}

	@And("^Click on the play Icon$")
	public void Click_on_the_play_Icon() throws Throwable {
		System.out.println("Click on the play Icon");
		Thread.sleep(3000);
		try {
			getVisibleElementByXpath("//*[(contains(@class,'start-image') and not(contains(@style,'none'))) or contains(@title,'Play Video')]").click();
		}catch(Exception e) {
			System.out.println("Play icon not found"+e);
		}
	}
	@Then("^Click on any video item$")

	public void Click_on_any_video_item() throws Throwable {

		System.out.println("Click on any video item");

		waitTillElementExist("//picture[@class='media-item-picture']//img[@data-src='/content/ford/in/en_in/home/owner/how-to-videos/jcr:content/par/splitter_1858874550/splitter0/mediacarouselitem/image.imgs.full.high.jpg/1511855898720.jpg']");

		getVisibleElementByXpath("//picture[@class='media-item-picture']//img[@data-src='/content/ford/in/en_in/home/owner/how-to-videos/jcr:content/par/splitter_1858874550/splitter0/mediacarouselitem/image.imgs.full.high.jpg/1511855898720.jpg']").click();

	}


	@Then("^Verify Bright cove should be playing$")
	public void Verify_Bright_cove_should_be_playing() throws Throwable {
		System.out.println("Verify Bright cove should be playing");
		getVisibleElementByXpath("//div[contains(@class,'end-image') and contains(@style,'block')]");
		do {
			Thread.sleep(5000);
		}while(driver.findElements(By.xpath("//div[contains(@class,'end-image') and contains(@style,'block')]")).size()==0);

	}

	@Then("^See all components on Mondeo page loaded without performance issue$")
	public void seeMondeoPageWithoutIssue() throws Throwable {
		System.out.println("See all components on Mondeo page loaded without performance issue");
		waitTillElementExist(
				"//div[contains(@class,'service-ok')][1]//div[@class='heading']/a[text()='Model Compare' or text()='Overview All Models' or text()='Compare Models']");
		getVisibleElementByXpath(
				"//div[contains(@class,'secondaryNavigation')]//div[@class='desktop hideForMobile']//a[text()='Request A Test Drive' or text()='Request a Test Drive' or text()='Book a Test Drive']");
		getVisibleElementByXpath(
				"//div[contains(@class,'secondaryNavigation')]//div[@class='desktop hideForMobile']//a[text()='Request A Brochure' or text()='Request a Brochure' or text()='Download a Brochure']");
	}

}

