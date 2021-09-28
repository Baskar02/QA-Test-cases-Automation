package com.mastercard.automation.pom;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class pageobjectmodel {

	public @FindBy(xpath = "//*[@automation-id='about_aut']")
	static WebElement About;
	
	public @FindBy(xpath = "//*[@automation-id='about_aut']")
	static WebElement Insights_n_Solutions;
	
	public @FindBy(xpath = "//*[@automation-id='about_aut']")
	static WebElement Events;
	
	public @FindBy(xpath = "//*[@automation-id='about_aut']")
	static WebElement News_n_Media;
	
	public @FindBy(xpath = "//*[@automation-id='magnifying_glass_aut']")
	static WebElement magnifying_glass_aut;
	
	public @FindBy(xpath = "//*[@automation-id='mastercard_logo']")
	static WebElement mastercard_logo;
	
	public @FindBy(xpath = "//*[@automation-id='search_bar']")
	static WebElement search_bar;
	
	public @FindBy(xpath = "//*[@automation-id='close_search_bar_aut']")
	static WebElement close_search_bar_aut;

	public @FindBy(xpath = "//*[@automation-id='stayinformedhomepage_aut']")
	static WebElement stayinformed;
	
	public @FindBy(xpath = "//*[@automation-id='joincomm_aut']")
	static WebElement jointhecommunity;
		
	public @FindBy(xpath = "//*[@automation-id='JoinTheCommunityFormHeading']")
	static WebElement JoinTheCommunityFormHeading;

	public @FindBy(xpath = "//*[@automation-id='email_aut']")
	static WebElement email;
	
	public @FindBy(xpath = "//*[@automation-id='firstname_aut']")
	static WebElement firstname;

	public @FindBy(xpath = "//*[@automation-id='lastname_aut']")
	static WebElement lastname;
	
	public @FindBy(xpath = "//*[@automation-id='company_aut']")
	static WebElement company;
		
	public @FindBy(xpath = "//*[@automation-id='country_aut']")
	static WebElement country;
	
	public @FindBy(xpath = "//div[@class='recaptcha-checkbox-checkmark']")
	static WebElement recaptchaCheck;
	
	public @FindBy(xpath = "//div[@class='recaptcha-checkbox-checkmark']")
	static WebElement ClosePopup;
	
	public @FindBy(xpath = "//*[@automation-id='mastercard_logo']")
	static WebElement mastercard_logo_footer;
	
	
	public @FindBy(xpath = "//*[@automation-id='contusfooter_aut']")
	static WebElement contusfooter_aut;

	public @FindBy(xpath = "//*[@automation-id='ourpartners_aut']")
	static WebElement ourpartners_aut;
	
	public @FindBy(xpath = "//*[@automation-id='legal_aut']")
	static WebElement legal_aut;

	public @FindBy(xpath = "//*[@automation-id='mcardgblpolicy_aut']")
	static WebElement mcardgblpolicy_aut;
	
	public @FindBy(xpath = "//*[@automation-id='signupfooter_aut']")
	static WebElement signupfooter_aut;
		
	public @FindBy(xpath = "//*[@automation-id='twitterfooter_aut']")
	static WebElement twitterfooter_aut;	
	
	public @FindBy(xpath = "//*[@automation-id='linkedinfooter_aut']")
	static WebElement linkedinfooter_aut;	
}
