package com.ford.automation.driverFactory;

import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

/**
 * Created by sonpt on 3/1/2016.
 */
public class DriverFactory {

	public static final String USERNAME = "lisa190"; // "markp13";
	public static final String AUTOMATE_KEY = "QeJGJY5s2Rwtx3oq4yqC"; // "t4drSY5z1KreeGbyEqp9";
	public static final String URL = "http://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com/wd/hub";

	public WebDriver createWebDriver(Browser browser) {
		switch (browser) {
		case CHROME:
			return new ChromeDriver();
		case FIRE_FOX:
			return new FirefoxDriver();
		case SAFARI:
			return new SafariDriver();
		case INTERNET_EXPLORER:
			return new InternetExplorerDriver();
		default:
			return new FirefoxDriver();
		}
	}

	public WebDriver createRemoteWebDriver(Browser browser) throws Exception {
		DesiredCapabilities caps = new DesiredCapabilities();
		WebDriver driver;

		switch (browser) {
		case WINDOW_CHROME_LATEST:

			caps.setCapability("browser", "Chrome");
			caps.setCapability("browser_version", "48.0");
			caps.setCapability("os", "Windows");
			caps.setCapability("os_version", "XP");
			caps.setCapability("resolution", "1024x768");

			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;
		case WINDOW_FIRE_FOX_LATEST:

			caps.setCapability("browser", "Firefox");
			caps.setCapability("browser_version", "37.0");
			caps.setCapability("os", "Windows");
			caps.setCapability("os_version", "8.1");
			caps.setCapability("resolution", "1024x768");

			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;

		case WINDOW_INTERNET_EXPLORER_LATEST:
			caps.setCapability("browser", "IE");
			caps.setCapability("browser_version", "11.0");
			caps.setCapability("os", "Windows");
			caps.setCapability("os_version", "7");
			caps.setCapability("resolution", "1024x768");

			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;
		case MAC_SAFARI_LATEST:
			caps.setCapability("browser", "Safari");
			caps.setCapability("browser_version", "9.0");
			caps.setCapability("os", "OS X");
			caps.setCapability("os_version", "El Capitan");
			caps.setCapability("resolution", "1024x768");

			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;
		case MAC_FIRE_FOX_LATEST:
			caps.setCapability("browser", "Firefox");
			caps.setCapability("browser_version", "45.0");
			caps.setCapability("os", "OS X");
			caps.setCapability("os_version", "El Capitan");
			caps.setCapability("resolution", "1024x768");
			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;

		case MAC_CHROME_LATEST:
			caps.setCapability("browser", "Chrome");
			caps.setCapability("browser_version", "49.0");
			caps.setCapability("os", "OS X");
			caps.setCapability("os_version", "El Capitan");
			caps.setCapability("resolution", "1024x768");
			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;
		case IOS_IP5:
			caps.setCapability("browserName", "iPhone");
			// caps.setCapability("platform", "MAC");
			caps.setPlatform(Platform.MAC);
			caps.setCapability("device", "iPhone 5");

			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;
		case IOS_IP6:
			caps.setCapability("browserName", "iPhone");
			// caps.setCapability("platform", "MAC");
			caps.setPlatform(Platform.MAC);
			caps.setCapability("device", "iPhone 6");

			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;
		case ANDROID_GALAXY_S5:
			caps.setCapability("browserName", "android");
			caps.setCapability("platform", "ANDROID");
			caps.setCapability("device", "Samsung Galaxy S5");

			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;
		case ANDROID_GALAXY_S4:
			caps.setCapability("browserName", "android");
			caps.setCapability("platform", "ANDROID");
			caps.setCapability("device", "Samsung Galaxy S4");

			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;

		case ANDROID_GALAXY_TAB_4:
			caps.setCapability("browserName", "android");
			caps.setCapability("platform", "ANDROID");
			caps.setCapability("device", "Samsung Galaxy Tab 4 10.1");
			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;

		case IOS_IPAD_AIR2:
			caps.setCapability("browserName", "iPad");
			caps.setCapability("platform", "MAC");
			caps.setCapability("device", "iPad Air 2");

			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;
		case WINDOWS_EDGE:
			caps.setCapability("browser", "Edge");
			caps.setCapability("browser_version", "13.0");
			caps.setCapability("os", "Windows");
			caps.setCapability("os_version", "10");
			caps.setCapability("resolution", "1920x1080");

			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;

		default:
			caps.setCapability("browser", "Firefox");
			caps.setCapability("browser_version", "44.0");
			caps.setCapability("os", "Windows");
			caps.setCapability("os_version", "XP");
			caps.setCapability("resolution", "1024x768");

			driver = new RemoteWebDriver(new URL(URL), caps);
			return driver;
		}
	}

}
