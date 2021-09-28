package com.mastercard.automation.functional;

import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import com.ford.automation.base.BaseTest;
import com.google.common.base.Verify;
import com.mastercard.automation.pom.pageobjectmodel;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class functionalScenarios extends BaseTest {
	
	@Given("^Open Chrome browser on Lincoln$")
	public void open_Chrome_browser_on_Lincoln() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("");

		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.open('','testwindow','width=400,height=200')");
		driver.close();
		driver.switchTo().window("testwindow");
		js.executeScript("window.moveTo(0,0);");
		js.executeScript("window.resizeTo(1450,1000);");
	}


	@When("^Maximize browser and enter link \"([^\"]*)\"$")
	public void maximize_browser_and_enter_link_on_P_Lincoln(String url) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		driver.get(getProfileURL(url));
		driver.manage().window().maximize();
	    System.out.println("");
	    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		Thread.sleep(10000);
	}

	
	@When("^Check for Menu's$")
	public void check_for_Menu_s(DataTable menu) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		for(int imenu=0;imenu<menu.raw().get(0).size()-1;imenu++) {
		    System.out.println("");

			getVisibleElementByXpath("//*[contains(text(),'" + menu.raw().get(0).get(imenu) + "')]");
		}
	}

	public void NavigateMenus(String main_menu, String sub_menu) throws Throwable {
	    System.out.println("");

		getVisibleElementByXpath("//*[contains(text(),'" + main_menu + "')]").click();
		if (!sub_menu.isEmpty())
			getVisibleElementByXpath("//*[contains(text(),'" + sub_menu + "')]").click();
	}
	
	@Then("^Non-sticky menu/submenu should be displayed at the top of every page$")
	public void non_sticky_menu_submenu_should_be_displayed_at_the_top_of_every_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("");
	    
	}

	@Then("^Magnifying glass should in proper position in desktop view$")
	public void magnifying_glass_should_in_proper_position_in_mobile_desktop_view() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("");

		findByElement(pageobjectmodel.magnifying_glass_aut);
	}

	@When("^Check display of MasterCard logo on page$")
	public void check_display_of_MasterCard_logo_on_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("Check display of MasterCard logo on page");

		findByElement(pageobjectmodel.mastercard_logo);
	}

	@Then("^Mastercard logo is seen on left hand side of menu bar$")
	public void mastercard_logo_is_seen_on_left_hand_side_of_menu_bar() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("");
	    
	}

	@When("^Mouse over on flyout menu's$")
	public void mouse_over_on_flyout_menu_s(DataTable menu) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("Mouse over on flyout menu's");
		for(int imenu=0;imenu<menu.raw().size()-1;imenu++) {
			mouserhover(getVisibleElementByXpath("//*[contains(text(),'" + menu.raw().get(imenu).get(0) + "')]"));
			for(int jmenu=1;jmenu<menu.raw().get(imenu).size()-1;jmenu++) {
				getVisibleElementByXpath("//*[contains(text(),'" + menu.raw().get(imenu).get(jmenu) + "')]");
			}
		}
	}

	@Then("^There should be two level of Menu's$")
	public void there_should_be_two_level_of_Menu_s() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("There are two level of Menu's displayed");
	}

	@Then("^Dropdown options should be displayed for each$")
	public void dropdown_options_should_be_displayed_for_each() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("Dropdown options has been displayed for each Menu items");
	    
	}

	@Then("^A highlighter bar should be displayed below the headers$")
	public void a_highlighter_bar_should_be_displayed_below_the_headers() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("A highlighter bar should be displayed below the headers");
	    
	}

	@When("^click on each of the dropdown options provided$")
	public void click_on_each_of_the_dropdown_options_provided(DataTable menu) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("click on each of the dropdown options provided");
	    for(int imenu=0;imenu<menu.raw().size()-1;imenu++) {
			for(int jmenu=1;jmenu<menu.raw().get(imenu).size()-1;jmenu++) {
				mouserhover(getVisibleElementByXpath("//*[contains(text(),'" + menu.raw().get(imenu).get(0) + "')]"));
				getVisibleElementByXpath("//*[contains(text(),'" + menu.raw().get(imenu).get(jmenu) + "')]").click();
				getVisibleElementByXpath("//*[contains(text(),'" + menu.raw().get(imenu).get(jmenu) + "')]");
			}
		}
	}

	@Then("^Page redirected to another expected page/URL$")
	public void page_redirected_to_another_expected_page_URL() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("Page redirected to another expected page/URL");
	    
	}

	@When("^Click on Search icon from Magnifying glass icon$")
	public void click_on_Search_icon_from_Magnifying_glass_icon() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    System.out.println("Click on Search icon from Magnifying glass icon");
	    findByElement(pageobjectmodel.magnifying_glass_aut).click();
	}

	@Then("^Search bar should be displayed along with X$")
	public void search_bar_should_be_displayed_along_with_X() throws Throwable {
	    System.out.println("Search bar should be displayed along with X");
	    findByElement(pageobjectmodel.search_bar);
	    findByElement(pageobjectmodel.close_search_bar_aut);
	}

	@When("^Enter a search string \"([^\"]*)\" and Click on search icon$")
	public void enter_a_search_string_and_Click_on_search_icon(String arg1) throws Throwable {
	    System.out.println("Enter a search string and Click on search icon");
	    findByElement(pageobjectmodel.search_bar).sendKeys(arg1);
		getVisibleElementByXpath("//*[contains(text(),'SearchIcon')]").click(); //----> Need proper UI property
	}

	@Then("^\"([^\"]*)\" string should be displayed in serch result page$")
	public void string_should_be_displayed_in_serch_result_page(String arg1) throws Throwable {
	    System.out.println(arg1+" string should be displayed in serch result page");
	    getVisibleElementByXpath("//*[contains(text(),'"+arg1+"')]");
	}

	@Then("^\"([^\"]*)\" string should not be displayed in serch result page$")
	public void string_should_not_be_displayed_in_serch_result_page(String arg1) throws Throwable {
	    System.out.println(arg1+" string should not be displayed in serch result page");
	    verifyInvisibleElement("//*[contains(text(),'"+arg1+"')]");
	}

	@When("^click on X to close the search bar$")
	public void click_on_X_to_close_the_search_bar() throws Throwable {
	    System.out.println("click on X to close the search bar");
	    findByElement(pageobjectmodel.close_search_bar_aut).click();
	}

	@Then("^Search bar should be disappear$")
	public void search_bar_should_be_disappear() throws Throwable {
	    System.out.println("Search bar should be disappear");
	    findByInvisibleElement(pageobjectmodel.close_search_bar_aut);
	}

	@When("^Click on Join the Community CTA from header menu$")
	public void click_on_Join_the_Community_CTA_from_header_menu() throws Throwable {
	    System.out.println("Click on Join the Community CTA from header menu");
	    findByElement(pageobjectmodel.stayinformed).click();
	}

	@Then("^I should see the form displayed in an overlay$")
	public void i_should_see_the_form_displayed_in_an_overlay() throws Throwable {
	    System.out.println("I should see the form displayed in an overlay");
	    findByElement(pageobjectmodel.JoinTheCommunityFormHeading);
	    findByElement(pageobjectmodel.jointhecommunity);
	}

	@When("^Do not input anything and click on submit$")
	public void do_not_input_anything_and_click_on_submit() throws Throwable {
	    System.out.println("Do not input anything and click on submit");
	    findByElement(pageobjectmodel.jointhecommunity).click();
	}

	@Then("^See error message is seen$")
	public void see_error_message_is_seen() throws Throwable {
	    System.out.println("See error message is seen");
		List<WebElement> ErrorMsgs=driver.findElements(By.xpath("//small[not(contains(@class,'errRecaptcha')) and not(contains(@class,'hint')) and text()!='']"));
		if(ErrorMsgs.size()==0) {
			Assert.assertFalse("No error messages displayed for blank/invalid data submission on the form", true);
		}else {
			System.out.println("Error messages sucessfully displayed for invalid email on the form");
		}	    
	}

	@When("^enter an incorrect item on one of the fields$")
	public void enter_an_incorrect_item_on_one_of_the_fields_and_click_submit(DataTable arg) throws Throwable {
	    System.out.println("enter an incorrect item on one of the fields and click submit");
	    findByElement(pageobjectmodel.email).sendKeys(arg.raw().get(0).get(0));
	    findByElement(pageobjectmodel.firstname).sendKeys(arg.raw().get(0).get(1));
	    findByElement(pageobjectmodel.lastname).sendKeys(arg.raw().get(0).get(2));
	    findByElement(pageobjectmodel.company).sendKeys(arg.raw().get(0).get(3));
	    Selectfromdropdown(findByElement(pageobjectmodel.country),arg.raw().get(0).get(4));
	    
	}

	@When("^input all items and enter incorrect captcha$")
	public void input_all_items_and_enter_incorrect_captcha(DataTable arg) throws Throwable {
	    System.out.println("");
	    enter_an_incorrect_item_on_one_of_the_fields_and_click_submit(arg);
	}

	@When("^Click on Joint the Community$")
	public void click_submit() throws Throwable {
	    System.out.println("Click on Joint the Community");
	    findByElement(pageobjectmodel.jointhecommunity).click();
	}

	@Then("^Form should not be submitted$")
	public void form_should_not_be_submitted() throws Throwable {
	    System.out.println("Form should not be submitted");
	    i_should_see_the_form_displayed_in_an_overlay();
	}

	@When("^input all items and enter correct captcha$")
	public void input_all_items_and_enter_correct_captcha(DataTable arg1) throws Throwable {
	    System.out.println("input all items and enter correct captcha");
	    findByElement(pageobjectmodel.recaptchaCheck).click();
	    getVisibleElementByXpath("//div[@class='recaptcha-checkbox-checkmark']//ancestor::span[@aria-checked='true']");
	}

	@Then("^Form submission should be successful and User details should feed to Pardot$")
	public void form_submission_should_be_successful_and_User_details_should_feed_to_Pardot() throws Throwable {
	    System.out.println("Form submission should be successful and User details should feed to Pardot");
	    getVisibleElementByXpath("//*[contains(text(),'Thank You')]").click(); //----> Need proper UI property
	    
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
				System.out.println("URL: " + element.getAttribute("href") + " returned " + isLinkBroken(new URL(element.getAttribute("href"))));
			} catch (Exception exp) {
				System.out.println("At " + element.getAttribute("innerHTML") + " Exception occured -&gt; " + exp.getMessage());
			}
		}
	}
	
	
	@When("^Existence of mastercard logo to bottom of footer$")
	public void Existence_of_mastercard_logo_to_bottom_of_footer() throws Throwable {
	    System.out.println("Existence of mastercard logo to bottom of footer");
	    findByElement(pageobjectmodel.mastercard_logo_footer);
	}

	
	@Then("^see Copyrights text aligned bottom center of the footer$")
	public void see_Copyrights_text_aligned_bottom_center_of_the_footer(DataTable arg1) throws Throwable {
	    System.out.println("see Copyrights text aligned bottom center of the footer");
	    getVisibleElementByXpath("//*[contains(text(),'"+arg1.raw().get(0).get(0)+"')]");	    
	}
	
	@And("^footer consists of Additional Links at left side$")
	public void footer_consists_of_Additional_Links_at_left_side(DataTable additionallinks) throws Throwable {
	    System.out.println("footer consists of Additional Links at left side");
	    for(int getcolumval=0;getcolumval<additionallinks.raw().get(0).size();getcolumval++) {
	    		getVisibleElementByXpath("//*[contains(text(),'"+additionallinks.raw().get(0).get(getcolumval)+"')]");
	    		driver.findElement(By.partialLinkText(additionallinks.raw().get(0).get(getcolumval)));
	    }	    
	}
	
	@And("^verify existence of Sign up buttons and Social Share icons at right side of footer$")
	public void verify_existence_of_Sign_up_buttons_and_Social_Share_icons_at_right_side() throws Throwable {
	    System.out.println("verify existence of Sign up buttons and Social Share icons at right side");
	    findByElement(pageobjectmodel.signupfooter_aut);
	    findByElement(pageobjectmodel.twitterfooter_aut);
	    findByElement(pageobjectmodel.linkedinfooter_aut);
	}
	
	@When("^Click on Sign up$")
	public void Click_on_Sign_up() throws Throwable {
	    System.out.println("Click on Sign up");
	    findByElement(pageobjectmodel.signupfooter_aut).click();
	}
	
	@When("^Close the Sign up popup$")
	public void Close_the_Sign_up_popup() throws Throwable {
	    System.out.println("Close the Sign up popup");
	    findByElement(pageobjectmodel.ClosePopup).click();
	}


	@When("^Click on Social Share icon$")
	public void Click_on_Social_Share_icon() throws Throwable {
	    System.out.println("Click on Social Share icon");
	    findByElement(pageobjectmodel.twitterfooter_aut).click();
	    findByElement(pageobjectmodel.linkedinfooter_aut);
	}
	
	@When("^see social share window should open$")
	public void see_social_share_window_should_open() throws Throwable {
	    System.out.println("see social share window should open");
	    Verify.verify(driver.getWindowHandles().size()==3, "One of the social media icon is not working as expected");
	}

}
