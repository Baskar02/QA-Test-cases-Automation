package com.ford.automation.qa;

import com.ford.automation.driverFactory.Browser;

public class BrowserHelper {

	public static Boolean checkMobile(Browser browser) {
		switch (browser) {
		case IOS_IP5:
		case IOS_IP6:
		case IOS_IP6_PLUS:
			// case IOS_IPAD_AIR2:
		case ANDROID_GALAXY_S4:
		case ANDROID_GALAXY_S5:
		case ANDROID_GALAXY_TAB_4:

			return true;
		default:
			return false;

		}
	}
}
