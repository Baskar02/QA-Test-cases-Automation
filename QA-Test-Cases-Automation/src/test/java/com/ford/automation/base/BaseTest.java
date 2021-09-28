package com.ford.automation.base;

import org.im4java.core.CompareCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.junit.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.cucumber.listener.Reporter;
import com.phrend.automation.personalQuiz.CustomHtmlUnitDriver;

import config.Configuration;
import junit.framework.Assert;
import net.thucydides.core.annotations.Managed;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class BaseTest {
	public static String getHost=null;
	public static boolean isDevops=false;
	public static String UniqueKey = null, Password = null, phn = null;
	public static Map<String, String> env = new HashMap<String, String>();
	static {
		try {
			if (!System.getProperty("profileId").isEmpty()) {
				getHost = "https://www" + System.getProperty("profileId") + ".";	//DO NOT CHANGE HERE
			}
		} catch(Exception e) {
			getHost="https://www"+"qa"+"."; 										///Manual Environment Specifier: int/dev/perf/qa
		}	
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==true) {
			if(System.getProperty("executionfor").contains("Lincoln")) {
				UniqueKey = "241017115805";
				phn = "02410171158";
			}else if(System.getProperty("executionfor").contains("Ford")) {
				UniqueKey = "121017121847";
				phn = "01210171218";
			}
		}else {
			//Update if Non-Jenkins run
			UniqueKey = "150518023934";
			phn = "01505180239";
		}
	}
	
	@Managed
	public WebDriver driver;
	
//	public static WebDriver driver = null;

//	protected CustomHtmlUnitDriver driverCustom = new CustomHtmlUnitDriver();
	// Test Screenshot directory
	protected String testScreenShotDirectory;
	// Element Screenshot paths
	protected String baselineScreenShotPath;
	protected String actualScreenShotPath;
	protected String differenceScreenShotPath;
	// Image files
	protected File expectedImageFile;
	protected File actualImageFile;
	protected String currentDir = System.getProperty("C:\\");
	protected String actual = "c:\\tmp\\actual";
	protected String expectedLandingPage = "c:\\tmp\\expectedLandingPage.png";
	protected String differenceLandingPage = "c:\\tmp\\differenceLandingPage.png";
	protected String expectedLocateDealerPage = "c:\\tmp\\expectedLocateDealerPage.png";
	protected String differenceLocateDealerPage = "c:\\tmp\\differenceLocateDealerPage.png";
	protected String expectedVehiclePage = "c:\\tmp\\expectedVehiclePage.png";
	protected String differenceVehiclePage = "c:\\tmp\\differenceVehiclePage.png";
	protected String expectedFocusPage = "c:\\tmp\\expectedFocusPage.png";
	protected String differenceFocusPage = "c:\\tmp\\differenceFocusPage.png";
	protected String expectedFiestaPage = "c:\\tmp\\expectedFiestaPage.png";
	protected String differenceFiestaPage = "c:\\tmp\\differenceFiestaPage.png";
	// Main screenshot directory
	protected String parentScreenShotsLocation = currentDir + "\\ScreenShots\\";
	protected String testName;
	protected WebDriverWait wait;
	
//	@AfterClass
//    public static void teardown() {
//        Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
//        Reporter.setSystemInfo("user", System.getProperty("user.name"));
//        Reporter.setSystemInfo("os", "Mac OSX");
//        Reporter.setTestRunnerOutput("Cucumber Junit Test Runner");
//    }
	
	protected void clickOnLinks(String xpath) throws Throwable {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		WebDriverWait wait = new WebDriverWait(driver, 240);
		
		int count = 0;
		
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				wait.until(ExpectedConditions.elementToBeClickable(element));
				element.click();
				
				count++;
				if (count == elements.size())
					break;
			}
		}
	}

	protected Map<Integer, String> findDriveAwayPriceAU(String inputString) {
		Map<Integer, String> dictionary = new HashMap<Integer, String>();
		Pattern regex = Pattern.compile("(\\d+(?:\\.\\d+)?)");
		 Matcher matcher = regex.matcher(inputString.replace("\"", " "));
		 int cnt=0;
		 while(matcher.find()){
	        System.out.println("Parsing values from Json response " + matcher.group(1));
	        dictionary.put(cnt, matcher.group(1));
	        cnt++;
		} 
		 return dictionary;
	}
	
	protected void selectOptionOnMenuList(String xpath) throws Throwable {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		boolean isDisplayed = false;
		int count = elements.size();
		int interval = 3000;
		while (count > 0) {
			for (WebElement element : elements) {
				try {
					moveToElement(element);
					isDisplayed = element.isDisplayed();
				}

				catch (Exception ex) {
					isDisplayed = false;
				}

				if (isDisplayed) {
					element.click();
				}
				Thread.sleep(interval);
				count--;
			}
		}
	}

	protected void verifyDontSeeElementVisible(String xpath) throws Throwable {
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		int count = 20; // total is 1m 30'
		int interval = 10000; // 10s
		while (count > 0) {
			boolean isVisible = false;
			List<WebElement> elements = driver.findElements(By.xpath(xpath));
			for (WebElement element : elements) {
				moveToElement(element);
				if (element.isDisplayed()) {
					isVisible = true;
				}
			}

			if (!isVisible) {
				return;
			}
			Thread.sleep(interval);
			count--;
		}
		throw new Exception("Element is visible: " + xpath);
	}

	protected List<WebElement> verifySeeingElementVisible(String xpath) throws Throwable {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		int count = elements.size();
		int interval = 1000;
		boolean isDisplayed = false;
		while (count > 0)
			for (WebElement element : elements) {
				try {
					moveToElement(element);
					isDisplayed = element.isDisplayed();
				}

				catch (Exception ex) {
					isDisplayed = false;
				}

				if (isDisplayed) {
					Thread.sleep(interval);
					count--;
				}

			}

		return elements;

	}

	protected List<WebElement> verifySeeingElementVisibleNoMovement(String xpath) throws Throwable {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		int count = elements.size();
		int interval = 3000;
		boolean isDisplayed = false;
		while (count > 0)
			for (WebElement element : elements) {
				try {
					isDisplayed = element.isDisplayed();
				}

				catch (Exception ex) {
					isDisplayed = false;
				}

				if (isDisplayed) {
					Thread.sleep(interval);
					count--;
				}

			}

		return elements;

	}

	protected void clickOnLinksAndCloseTabs(String xpath) throws Throwable {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		int count = 0;
		int interval = 3000;
		String originalHandle = driver.getWindowHandle();
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				element.click();
				Thread.sleep(interval);
				count++;
				if (count == elements.size())
					break;
			}
		}

		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				driver.close();
			}
		}

		driver.switchTo().window(originalHandle);
	}

	protected void clickOnLinksByCtrlEnter(String xpath) throws Throwable {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		int count = 0;
		int interval = 10000;
		String originalHandle = driver.getWindowHandle();
		String keysPressed = Keys.chord(Keys.CONTROL, Keys.RETURN);
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				element.sendKeys(keysPressed);
				Thread.sleep(interval);
				count++;
				if (count == elements.size())
					break;
			}
		}

		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				driver.close();
			}
		}

		driver.switchTo().window(originalHandle);
	}

	protected void clickOnLinksAndClosePopup(String xpath, String closeXpath) throws Throwable {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		int count = 0;
		int interval = 3000;
		for (WebElement element : elements) {
			if (element.isDisplayed()) {
				element.click();
				Thread.sleep(interval);
				count++;
				driver.findElement(By.xpath(closeXpath));
				if (count == elements.size())
					break;
			}
		}
	}

	protected void seeComponents(String xpath) throws Throwable {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		int count = 0;
		int interval = 3000;
		for (WebElement element : elements) {
			moveToElement(element);
			if (element.isDisplayed()) {
				Thread.sleep(interval);
				count++;
				if (count == elements.size())
					break;
			}
		}
	}

	protected void verifyRedirecToCorrectLink(String url) throws Exception {

		int count = 18; // total is 1m 30'
		int interval = 10000; // 10s
		String currentUrl = "";
		while (count > 0) {
			boolean isCorrectLink = false;
			currentUrl = driver.getCurrentUrl();

			if (url.equals(currentUrl)) {
				isCorrectLink = true;
			}

			if (isCorrectLink) {
				return;
			}

			count--;
			Thread.sleep(interval);
		}

		throw new Exception("Redirect to incorrect link");

	}

	protected void verifyRedirecToCorrectLinkAndName(String url, String name, String xpath) throws Exception {

		int count = 18; // total is 1m 30'
		int interval = 10000; // 10s
		String currentUrl = "";
		String elementXpath = "";
		while (count > 0) {
			boolean isCorrectLink = false;
			currentUrl = driver.getCurrentUrl();
			elementXpath = driver.findElement(By.xpath(xpath)).getText().toString();
			if (url.equals(currentUrl) && elementXpath.contains(name)) {
				isCorrectLink = true;
			}

			if (isCorrectLink) {
				return;
			}

			count--;
			Thread.sleep(interval);
		}

		throw new Exception("Redirect to incorrect link");

	}

	protected void verifyRedirecToContainLink(String url) throws Exception {

		int count = 18; // total is 1m 30'
		int interval = 10000; // 10s
		String currentUrl = "";
		while (count > 0) {
			boolean isCorrectLink = false;
			currentUrl = driver.getCurrentUrl();

			if (currentUrl.contains(url)) {
				isCorrectLink = true;
			}

			if (isCorrectLink) {
				return;
			}

			count--;
			Thread.sleep(interval);
		}

		throw new Exception("Redirect to link which doesn't contain given url! ");

	}

	protected WebElement getVisiblePriceByXpath(String price, String xpath) throws Throwable {
		// driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		int count = 36; // total is 3m
		int interval = 5000; // 5s
		while (count > 0) {

			List<WebElement> priceTabs = driver.findElements(By.xpath(xpath));
			for (WebElement priceTab : priceTabs) {
				boolean isDisplayed = false;
				try {
					moveToElement(priceTab);
					String priceUpdated = priceTab.getText().toString();
					if (priceUpdated.contains(price)) {
						isDisplayed = true;
					}
				} catch (Exception ex) {
					isDisplayed = false;
				}

				if (isDisplayed) {
					return priceTab;
				}
			}
			Thread.sleep(interval);
			count--;
		}

		throw new Exception("No found xpath: " + xpath);

	}

//	protected WebElement getVisibleElementByXpath(String element, String xpath) throws Throwable {
//		// driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
//		// int count = 36; // total is 1m 30'
//		// int interval = 5000; // 5s
//		// while(count>0)
//		// {
//		Thread.sleep(3000);
//		waitTillElementExist(xpath);
//		List<WebElement> webElements = driver.findElements(By.xpath(xpath));
//		for (WebElement webElement : webElements) {
//			boolean isDisplayed = false;
//			try {
//				moveToElement(webElement);
//				String webElementUpdated = webElement.getText().toString();
//				if (webElementUpdated.contains(element)) {
//					isDisplayed = true;
//				}
//			} catch (Exception ex) {
//				isDisplayed = false;
//			}
//
//			if (isDisplayed) {
//				return webElement;
//			}
//		}
//		// Thread.sleep(interval);
//		// count--;
//		// }
//		return null;
//
//		// throw new Exception ("No found xpath: " + xpath);
//
//	}
	
	protected WebElement getVisibleElementByXpath(String element, String xpath) throws Throwable {
		Thread.sleep(3000);
		waitTillElementExist(xpath);
		List<WebElement> webElements = driver.findElements(By.xpath(xpath));
		String webElementUpdated =null;
		for (WebElement webElement : webElements) {
			boolean isDisplayed = false;
			try {
				moveToElement(webElement);
				webElementUpdated = webElement.getText().toString();
				if (webElementUpdated.contains(element)) {
					isDisplayed = true;
				}
			} catch (Exception ex) {
				isDisplayed = false;
			}

			if (isDisplayed) {
				return webElement;
			}
		}

//		return null;

		throw new Exception ("Value does not match; Expected value: " + element + " , Actual value: " + webElementUpdated + ". Given XPath: " + xpath);
	}
	
	
	protected WebElement CompareIndiaVehiclePrice(String element, String xpath) throws Throwable {
		Thread.sleep(3000);
		waitTillElementExist(xpath);
		List<WebElement> webElements = driver.findElements(By.xpath(xpath));
		String webElementUpdated =null;
		for (WebElement webElement : webElements) {
			boolean isDisplayed = false;
			try {
				moveToElement(webElement);
				webElementUpdated = webElement.getText().toString().replace(",", "");
				if (webElementUpdated.contains(element)) {
					isDisplayed = true;
				}
			} catch (Exception ex) {
				isDisplayed = false;
			}

			if (isDisplayed) {
				return webElement;
			}
		}

//		return null;

		throw new Exception ("Value does not match; Expected value: " + element + " , Actual value: " + webElementUpdated + ". Given XPath: " + xpath);
	}
	
	protected WebElement getVisibleElementByXpathNoMovement(String element, String xpath) throws Throwable {
		driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		int count = 36; // total is 1m 30'
		int interval = 5000; // 5s
		while (count > 0) {

			List<WebElement> webElements = driver.findElements(By.xpath(xpath));
			for (WebElement webElement : webElements) {
				boolean isDisplayed = false;
				try {
					String webElementUpdated = webElement.getText().toString();
					if (webElementUpdated.contains(element)) {
						isDisplayed = true;
					}
				} catch (Exception ex) {
					isDisplayed = false;
				}

				if (isDisplayed) {
					return webElement;
				}
			}
			Thread.sleep(interval);
			count--;
		}

		throw new Exception("No found xpath: " + xpath);

	}

	protected WebElement getVisibleElementByXpath(String xpath) throws Throwable {

		Thread.sleep(3000);
		waitTillElementExist(xpath);
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

		throw new Exception("No found xpath: " + xpath);

	}
	protected WebElement getVisibilityElementByXpath(String xpath) throws Throwable {

		Thread.sleep(3000);
		waitTillElementVisible_Xpath(xpath);
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

		throw new Exception("No found xpath: " + xpath);

	}	
	protected WebElement getVisibleElementByLinkText(String linkText) throws Throwable {

		Thread.sleep(3000);
		waitTillElementExist_LinkText(linkText);
		List<WebElement> elements = driver.findElements(By.linkText(linkText));
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

		throw new Exception("No found xpath: " + linkText);

	}
	
	public WebElement findByElement(WebElement element) throws Throwable {

		Thread.sleep(3000);
//		waitTillElementExist(xpath);
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
		throw new Exception("No found xpath: " + element.getAttribute("name"));
	}
	
	
	public void findByInvisibleElement(WebElement element) throws Throwable {
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		int count = 10; // total is 1m 30'
		int interval = 5000; // 10s
		while (count > 0) {
			boolean isVisible = false;
			
			moveToElement(element);
			if (element.isDisplayed()) {
				isVisible = true;
			}

			if (!isVisible) {
				return;
			}
			Thread.sleep(interval);
			count--;
		}
		throw new Exception("Element is still visible: " + element.getAttribute("name"));
	}
	
	public void Selectfromdropdown(WebElement element, String itemToselect) {
		Select drpdwn=new Select(element);
		drpdwn.selectByVisibleText(itemToselect);
	}
	
	public WebElement mouserhover(WebElement element) throws Throwable {

		Thread.sleep(3000);
//		waitTillElementExist(xpath);
		boolean isDisplayed = false;
		try {
			moveToElement(element);
			isDisplayed = element.isDisplayed();
		} catch (Exception ex) {
			isDisplayed = false;
		}

		if (isDisplayed) {
			Actions action=new Actions(driver);
			action.moveToElement(element).build().perform();
			return element;
		}
		throw new Exception("No found xpath: " + element.getAttribute("name"));
	}
	
	public void verifyElementPresent(String xpath) {
		List<WebElement> pageElement=driver.findElements(By.xpath(xpath));
		if(pageElement.size()==0) {
			Assert.assertFalse("Element does not exist on page. " +xpath,false);
		}
	}
	
	public void waitTillElementExist(String str) {
		WebDriverWait wait = new WebDriverWait(driver, 240);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(str)));
	}
	
	public void waitTillElementExist_LinkText(String str) {
		WebDriverWait wait = new WebDriverWait(driver, 240);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(str)));
	}
	
	public void waitTillElementVisible_Xpath(String str) {
		WebDriverWait wait = new WebDriverWait(driver, 240);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(str)));
	}

	public void waitTillElementVisible(String str) {
		WebDriverWait wait = new WebDriverWait(driver, 240);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(str)));
	}

	public void waitTillElemVisiblebyXpath(String str, int timetowait) {
		WebDriverWait wait = new WebDriverWait(driver, timetowait);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(str)));
	}
	
	public void waitTillElemClickableByXpath(String str, int timetowait) {
		WebDriverWait wait = new WebDriverWait(driver, timetowait);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(str)));
	}

	public void waitTillElemClickableBycssSelector(String str, int timetowait) {
		WebDriverWait wait = new WebDriverWait(driver, timetowait);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(str)));
	}

	
	protected List<WebElement> getVisibleElements(String xpath) throws Throwable {
		// driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		// int count = 36; // total is 1m 30'
		// int interval = 10000; // 10s
		// while(count>0)
		// {

		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		// for (WebElement element : elements)
		// {
		// boolean isDisplayed = false;
		// try
		// {
		// moveToElement(element);
		// isDisplayed = element.isDisplayed();
		// }
		// catch (Exception ex)
		// {
		// isDisplayed = false;
		// }
		//
		// if (isDisplayed)
		// {
		return elements;
		// }
		// }
		// Thread.sleep(interval);
		// count--;
		// }

		// throw new Exception ("No found xpath: " + xpath);

	}

	protected WebElement getVisibleElementByXpathNoMovement(String xpath) throws Throwable {
		// driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		int count = 36; // total is 1m 30'
		int interval = 10000; // 10s
		while (count > 0) {

			List<WebElement> elements = driver.findElements(By.xpath(xpath));
			for (WebElement element : elements) {
				boolean isDisplayed = false;
				try {
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

	protected void clickVisibleElementByXpath(String xpath) throws Throwable {
		// driver.manage().timeouts().implicitlyWait(180, TimeUnit.SECONDS);
		int count = 3; // total is 1m 30'
		int interval = 1000; // 10s
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
					element.click();
				}
			}
			Thread.sleep(interval);
			count--;
		}

		throw new Exception("No found xpath: " + xpath);

	}

	protected void verifyInvisibleElement(String xpath) throws Throwable {
		driver.manage().timeouts().implicitlyWait(90, TimeUnit.SECONDS);
		int count = 20; // total is 1m 30'
		int interval = 10000; // 10s
		while (count > 0) {
			boolean isVisible = false;
			List<WebElement> elements = driver.findElements(By.xpath(xpath));
			for (WebElement element : elements) {
				moveToElement(element);
				if (element.isDisplayed()) {
					isVisible = true;
				}
			}

			if (!isVisible) {
				return;
			}
			Thread.sleep(interval);
			count--;
		}
		throw new Exception("Element is visible: " + xpath);
	}

	
	protected void verifyInvisibleElem(String xpath) throws Throwable {
			Thread.sleep(5000);
			boolean isVisible = false;
			List<WebElement> elements = driver.findElements(By.xpath(xpath));
			for (WebElement element : elements) {
				moveToElement(element);
				if (element.isDisplayed()) {
					isVisible = true;
				}
			}

			if (!isVisible) {
				return;
			}

		throw new Exception("Element is visible: " + xpath);
	}
	
	protected void moveToElement(String xpath) throws Throwable {
		WebElement element = driver.findElement(By.xpath(xpath));

		JavascriptExecutor je = (JavascriptExecutor) driver;

		je.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	protected void moveToElement(WebElement element) throws Throwable {
		JavascriptExecutor je = (JavascriptExecutor) driver;

		je.executeScript("arguments[0].scrollIntoView(true);", element);
		je.executeScript("window.scrollBy(0,-100)", ""); // down over the sticker menu
	}

	/* ImageMagick */

	// Close popup if exists
	protected void handlePopup(String selector) throws Throwable {
		List<WebElement> popup = driver.findElements(By.cssSelector(selector));
		if (!popup.isEmpty()) {
			popup.get(0).click();
		}
	}

	// Take Screenshot with AShot
	protected Screenshot takeScreenshot(WebElement element) throws Throwable {
		Screenshot elementScreenShot = new AShot().coordsProvider(new WebDriverCoordsProvider()).takeScreenshot(driver,
				element);
		// Print element size
		String size = "Height: " + elementScreenShot.getImage().getHeight() + "\n" + "Width: "
				+ elementScreenShot.getImage().getWidth() + "\n";
		System.out.print("Size: " + size);
		return elementScreenShot;
	}

	/*
	 * //Sceenshot paths protected void declareScreenShotPaths(String baselnie,
	 * String actual, String diff) throws Throwable { //BaseLine, Current,
	 * Difference Photo Paths baselineScreenShotPath = this.expected + baselnie;
	 * actualScreenShotPath = this.actual + actual; differenceScreenShotPath =
	 * this.difference + diff;
	 * 
	 * //Baseline, Current Photo Files baselineImageFile = new
	 * File(baselineScreenShotPath); actualImageFile = new
	 * File(actualScreenShotPath); }
	 */
	// ImageMagick Compare Method
	protected boolean compareImagesWithImageMagick(String exp, String cur, String diff)
			throws IOException, InterruptedException, IM4JavaException {
		String imPath = "C:\\ImageMagick-6.9.6-6-portable-Q16-x64";
		// ConvertCmd cmd = new ConvertCmd();
		// cmd.setSearchPath(imPath);
		ProcessStarter.setGlobalSearchPath(imPath);

		// This instance wraps the compare command
		CompareCmd compare = new CompareCmd();

		// For metric-output
		// compare.setErrorConsumer(StandardStream.STDERR);
		IMOperation cmpOp = new IMOperation();

		// Set the compare metric
		cmpOp.fuzz(0.5);
		cmpOp.metric("AE");

		// Add the expected image
		cmpOp.addImage(exp);

		// Add the current image
		cmpOp.addImage(cur);

		// This stores the difference
		cmpOp.addImage(diff);

		try {
			// Do the compare
			System.out.println("Comparison Started!");
			compare.run(cmpOp);
			System.out.println("Comparison Finished!");
			// DisplayCmd.show(diff);
			return true;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		} catch (InterruptedException ex) {
			ex.printStackTrace();
			return false;
		} catch (IM4JavaException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	// //Compare Operation
	// protected void doComparison(Screenshot elementScreenShot) throws Exception
	// {
	// //Did we capture baselnie image before?
	// if (expectedImageFile.exists()){
	// //Compare screenshot with baseline
	// System.out.println("Comparison method will be called!\n");
	//
	// System.out.println("Baseline: " + expected + "\n" +
	// "Actual: " + actual + "\n" +
	// "Diff: " + difference);
	//
	// boolean compareResult = compareImagesWithImageMagick(expected, actual,
	// difference);
	//
	// //If comparison results true then print that it passed
	// if (compareResult){
	// System.out.println("Comparison Passed!");
	// }
	// } else {
	// System.out.println("BaselineScreenShot is not exist! We put it into test
	// screenshot folder.\n");
	// //Put the screenshot to the specified folder
	// ImageIO.write(elementScreenShot.getImage(), "PNG", expectedImageFile);
	// }
	// }

	protected void createFolder(String path) {
		File testDirectory = new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		} else {
			System.out.println("Directory already exists: " + path);
		}
	}

	protected String GetTimeStampValue() throws IOException {
		Calendar cal = Calendar.getInstance();
		Date time = cal.getTime();
		String timeStamp = time.toString();
		System.out.println("Time stamp: " + timeStamp);
		String sysTime = timeStamp.replace(":", "-");
		System.out.println("System time: " + sysTime);
		return sysTime;
	}

	protected static List<WebElement> findAllLinks(WebDriver driver) {
		List<WebElement> elementList = new ArrayList<WebElement>();
		elementList = driver.findElements(By.tagName("a"));
		elementList.addAll(driver.findElements(By.tagName("img")));
		List<WebElement> finalList = new ArrayList<WebElement>();

		for (WebElement element : elementList) {
			if (element.getAttribute("href") != null) {
				finalList.add(element);
			}
		}
		return finalList;
	}

	public static String isLinkBroken(URL url) throws Exception {
		String response = "";
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		try {
			connection.connect();
			response = connection.getResponseMessage();
			connection.disconnect();
			return response;
		} catch (Exception exp) {
			return exp.getMessage();
		}
	}

	public class AscendingPrice implements Comparator<Object> {
		private double price;

		public AscendingPrice(double newPrice) {
			setPrice(newPrice);
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public int compare(Object o1, Object o2) {
			Double p1 = ((AscendingPrice) o1).getPrice();
			Double p2 = ((AscendingPrice) o2).getPrice();

			if (p1 < p2) {
				return 1;
			} else if (p1 > p2) {
				return -1;
			} else {
				return 0;
			}
		}

	}
	
	public int convertStrtoDoubletoInt(String ActualString)
	{	
		return (int)Math.floor(Double.parseDouble(ActualString.replace(",", "")));
	}
	
	public String formatNumberIncludeCommas(int NumToBeFormatted) {
		return Integer.toString(NumToBeFormatted);//NumberFormat.getNumberInstance(Locale.US).format(NumToBeFormatted);
	}
	
	public String formatNumberFrChina(int NumToBeFormatted) {
		String pattern = "#,##,###";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(NumToBeFormatted);
		System.out.println(output);
		return output;// NumberFormat.getNumberInstance(Locale.US).format(NumToBeFormatted);
	}
	
	public String getProfileURL(String url) {
		if(!url.contains("www.")) {
			String URLwithoutHost=url.split("\\.",2)[1];
			return getHost+URLwithoutHost;
		}else {
			return url;
		}
	}
	

	public void ClearAndInputTextBox(String xpath,String testdata) throws Throwable {
		getVisibleElementByXpath(xpath).clear();
		Thread.sleep(1000);
		getVisibleElementByXpath(xpath).sendKeys(testdata);
	}
	
	public String EnterCaptcha() {
		if(Boolean.parseBoolean(System.getProperty("isJenkinsJob"))==false) {
			return JOptionPane.showInputDialog("Please Enter CAPTCHA: ");
		}else {
			return "ABCD";
		}
	}
	
	
	public void CheckFileDownload(String FileNamePath) throws Throwable {
		System.out.println("File downloading started...");
		
		File file = new File(FileNamePath);
		int i=0;
		do {
			Thread.sleep(5000);
			i++;
		}while(!file.exists() && i<=72);
		
		if (file.exists()) {
			System.out.println("File downloaded successfully and Ready to Print: " + FileNamePath);
			file.delete();
		} else {
			Assert.assertFalse("File Download Failed", true);
		}

	}
	
	public String RegExpression(String StringToBeParsed,String pattern) {
	    Pattern r = Pattern.compile(pattern);
	    Matcher m = r.matcher(StringToBeParsed);
	    while(m.find()) {
	    	 if(!m.group(0).isEmpty()) {
	    		System.out.println(m.group(0).trim());
	    		return m.group(0).trim();
	    	  	}
	     }
		return null;
	}
	
	
	public String GetOnlyNumberFromStr(String StringToBeParsed,String pattern) {
//		  String line = "(Bs 10.000.000,50) +";
//	      String pattern = "-?\\d+";
	      Pattern r = Pattern.compile(pattern);
	      Matcher m = r.matcher(StringToBeParsed);
	      String amt = null;
	      while(m.find()) {
	    	  	if(!m.group(0).isEmpty()) {
	    	  		amt=amt+m.group(0).trim();
	    	  	}
	      }
	      return amt.replace("null", "");
	}
	
	public boolean isInView(WebElement element) {
        return (Boolean) ((JavascriptExecutor) driver).executeScript(
                "var element = arguments[0],                                " +
                        "  box = element.getBoundingClientRect(),           " +
                        "  centerX = box.left + box.width / 2,              " +
                        "  centerY = box.top + box.height / 2,              " +
                        "  e = document.elementFromPoint(centerX, centerY); " +
                        "for (; e; e = e.parentElement) {                   " +
                        "  if (e === element)                               " +
                        "    return true;                                   " +
                        "}                                                  " +
                        "return false;                                      "
                , element);
    }
	
	public boolean isAlertPresent() {
	    try {
	        driver.switchTo().alert();
	        return true;
	    } // try
	    catch (Exception e) {
	        return false;
	    } // catch
	}
}
