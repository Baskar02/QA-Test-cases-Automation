Feature: P2 General Test Scenarios - Owner VN/CN, B&P and Credit


################################# SIT Node #################################

@P2
Scenario: P2 Verify New Owner Registration for Australia 
	Given Open chrome browser on P2 
	And Generate unique string 
	When Maximize browser and enter link "https://www.mailinator.com/" on P2 
	And Input email to inbox field 
		| 18062017155002 | 
	And Click on Go button  
	When Redirect to link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home.html#overlay/content/ford/au/en_au/site-wide-content/overlays/form-overlay/login.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P2 
	And See the Login overlay on P2 
	And Click on Register link on Login overlay on P2 
	Then See the Register overlay on P2 
	When Enter credentials to register on P2 
		|Name062017155002|Test12345|Test12345|18062017155002@mailinator.com|FirstName|Lastname|
	And Click on Next button on P2 
	And Select vehicle credentials on P2 
		|New EcoSport|MPB2XXMXC2FS23326|
	And Click on Next button on P2 
	And Fill in Vehicle Info credentials for AU on P2 
		|1234|0916834845|ABCDEF| 
	And Click on Register button on P2 
	And Click on Close button on P2 
	When Redirect to link "https://www.mailinator.com/v3/index.jsp?zone=public&query=REPLACETOUNIQUE" on P2 
	And Click on Activation Email on MailInator page on P2 
	When Click on Activate My Account link in activation mail on P2 
	Then Registration Success message is displayed


@P2
Scenario: P2 Verify Forgot Username and Forgot Password on Owner Login page for VIETNAM 
	Given Open chrome browser on P2 
	When Redirect to link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/vn/en_vn/home/owner/HomePage-unauthenticated.html" on P2 
	And Click on Login link on P2 
	And See the Login overlay on P2
	And Click on Login button on P2
	Then Mandatory exceptions displayed for username and password
	|Username is mandatory|Password is mandatory|  
	Then Click on Forgot your username
	When See forgot overlay opened
	Then Enter email id
	||
	When click on Submit button
	Then Successful message displayed
    When Redirect to link "https://www.mailinator.com/v2/inbox.jsp?zone=public&query=REPLACETOUNIQUE" on P2
   	And Click on Forgot username Email on MailInator page
    Then Verify Username name in Forgot username Email
	||
	When Redirect to link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/vn/en_vn/home/owner/HomePage-unauthenticated.html" on P2 
	And Click on Login link on P2 
	And See the Login overlay on P2
	Then Click on Forgot your Password
	When See forgot overlay opened
	Then Enter email id and Username
	|||
	When click on Submit button
	Then Successful message displayed
    When Redirect to link "https://www.mailinator.com/v2/inbox.jsp?zone=public&query=REPLACETOUNIQUE" on P2
   	And Click on Forgot username Email on MailInator page
   	And Click on Password reset
    Then See Reset Password Overlay opened
    When Enter New password and confirm password
    |Test1234567|
    And click on Submit button
    Then Successful message displayed
	


@P2-NonJenkinsSc
Scenario: P2 Verify loading page for Owner for VIETNAM - Login using New User 
	Given Open chrome browser on P2 
	When Maximize browser and enter link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/vn/en_vn/home/owner/HomePage-unauthenticated.html" on P2 
	And Click on Login link on P2 
	And See the Login overlay on P2 
	When Enter credentials to login on P2 
		||Test1234567|
	And Click on Login button on P2 
	And Click on My Profile link on P2 
	And See VehicleName and Vincode on Profile overlay on P2 
		|New EcoSport|//*[@id='tabs-0'] |RL04TBBAMFSR05380| //*[@id='sticky-header']/table[3]/thead/tr/td[2]/span/span |
	And Click on Account Info tab on Profile overlay on P2 
	And See all account information in Account Info tab on Profile overlay on P2 
		||//*[@id='userName'] |FirstName|//*[@id='firstName'] | Lastname|//*[@id='lastName'] ||//*[@id='email'] |Mr|//*[@id='title'] |An Giang|//*[@id='state'] |12345|//*[@id='postCode'] ||//*[@id='mobileNumber'] ||//*[@id='workphoneNumber'] |NickName|//*[@id='nickName']|
	And Click on Add Vehicle on Profile overlay on P2 
	Then See that Add vehicle form is displayed on Add Vehicle tab on P2 Production 
	When Add vehicle with Vehicle name and Vincode on P2 Production 
		|New Fiesta|RL05DFBAMFMR09354|
	When Click on Confirm to Add Vehicle on P2 
	Then See the first vehicle tab name on P2 
	And See the second vehicle tab name on P2 
	When Click on second vehicle tab on P2 
	Then Verify second vehicle tab is loaded correctly on P2 
	When Click on first vehicle tab on P2 
	Then Verify first vehicle tab is loaded correctly on P2 
	When Click on second vehicle tab on P2 
	Then Verify second vehicle tab is loaded correctly on P2 
	When Click on Delete Vehicle on P2 
	Then See delete vehicle confirmation overlay 
	And Click on Confirm to Delete Vehicle on P2 
	And Do not See Vehicle tab name on P2 
		|New Fiesta|
	When Click on My Profile link on P2  
	And Click on Account Info tab on Profile overlay on P2 
	And See all account information in Account Info tab on Profile overlay on P2
		||//*[@id='userName'] |FirstName|//*[@id='firstName'] | Lastname|//*[@id='lastName'] ||//*[@id='email'] |Mr|//*[@id='title'] |An Giang|//*[@id='state'] |12345|//*[@id='postCode'] ||//*[@id='mobileNumber'] ||//*[@id='workphoneNumber'] |NickName|//*[@id='nickName']|
	And Click on Change Email Address link on Account Info on P2 
	And Verify Change Email to new email on Account Info on P2 
		||
	And Click on Change Email Address link on Account Info on P2 
	And Verify Change Email to new email on Account Info on P2 
		||
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test123456|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test1234567|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test123456|	
	And Click on first vehicle tab on P2 
	Then Verify first vehicle tab is loaded correctly on P2 
	When Click on Update New Version link on Profile overlay on P2 
	And Click on Download Now link on Profile overlay on P2
 	Then Logout China Owner
	When Maximize browser and enter link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/vn/en_vn/home/owner/HomePage-unauthenticated.html" on P2 
	And Click on Login link on P2 
	And See the Login overlay on P2 
	When Enter credentials to login on P2 
		||Test123456|
	And Click on Login button on P2 
	And Click on My Profile link on P2 	
 	Then Logout China Owner



@P2
Scenario: P2 Verify loading page for Owner for VIETNAM - Login using existing User
Given Open chrome browser on P2 
	When Maximize browser and enter link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/vn/en_vn/home/owner/HomePage-unauthenticated.html" on P2 
	And Click on Login link on P2 
	And See the Login overlay on P2 
	When Enter credentials to login on P2 
		|121017121847|Test1234567|
	And Click on Login button on P2 
	And Click on My Profile link on P2 
	And See VehicleName and Vincode on Profile overlay on P2 
		|New EcoSport|//*[@id='tabs-0'] |RL04TBBAMFSR05380| //*[@id='sticky-header']/table[3]/thead/tr/td[2]/span/span |
	And Click on Account Info tab on Profile overlay on P2 
	And See all account information in Account Info tab on Profile overlay on P2 
		|121017121847|//*[@id='userName'] |FirstName|//*[@id='firstName'] | Lastname|//*[@id='lastName'] |121017121847@mailinator.com|//*[@id='email'] |Mr|//*[@id='title'] |An Giang|//*[@id='state'] |12345|//*[@id='postCode'] |01210171218|//*[@id='mobileNumber'] |01210171218|//*[@id='workphoneNumber'] |NickName|//*[@id='nickName']|
	And Click on Add Vehicle on Profile overlay on P2 
	Then See that Add vehicle form is displayed on Add Vehicle tab on P2 Production 
	When Add vehicle with Vehicle name and Vincode on P2 Production 
		|New Fiesta|RL05DFBAMFMR09354|
	When Click on Confirm to Add Vehicle on P2 
	Then See the first vehicle tab name on P2 
	And See the second vehicle tab name on P2 
	When Click on second vehicle tab on P2 
	Then Verify second vehicle tab is loaded correctly on P2 
	When Click on first vehicle tab on P2 
	Then Verify first vehicle tab is loaded correctly on P2 
	When Click on second vehicle tab on P2 
	Then Verify second vehicle tab is loaded correctly on P2 
	When Click on Delete Vehicle on P2 
	Then See delete vehicle confirmation overlay 
	And Click on Confirm to Delete Vehicle on P2 
	And Do not See Vehicle tab name on P2 
		|New Fiesta|
	When Click on My Profile link on P2  
	And Click on Account Info tab on Profile overlay on P2 
	And See all account information in Account Info tab on Profile overlay on P2 
		|121017121847|//*[@id='userName'] |FirstName|//*[@id='firstName'] | Lastname|//*[@id='lastName'] |121017121847@mailinator.com|//*[@id='email'] |Mr|//*[@id='title'] |An Giang|//*[@id='state'] |12345|//*[@id='postCode'] |01210171218|//*[@id='mobileNumber'] |01210171218|//*[@id='workphoneNumber'] |NickName|//*[@id='nickName']|
	And Click on Change Email Address link on Account Info on P2 
	And Verify Change Email to new email on Account Info on P2 
		|1210171218470@mailinator.com|
	And Click on Change Email Address link on Account Info on P2 
	And Verify Change Email to new email on Account Info on P2 
		|121017121847@mailinator.com|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test123456|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test1234567|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test123456|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test1234567|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test1234567|	
	And Click on first vehicle tab on P2 
	Then Verify first vehicle tab is loaded correctly on P2 
	When Click on Update New Version link on Profile overlay on P2 
	And Click on Download Now link on Profile overlay on P2 
	Then Logout China Owner


@P2-NonJenkinsSc
Scenario: P2 Verify New Owner Registration for VIETNAM 
	Given Open chrome browser on P2 
	And Generate unique string 
	When Maximize browser and enter link "https://www.mailinator.com/" on P2 
	And Input email to inbox field 
		| 18062017155002 | 
	And Click on Go button 
	When Redirect to link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/vn/en_vn/home/owner/HomePage-unauthenticated.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P2 
	And Click on Login link on P2 
	And See the Login overlay on P2 
	And Click on Register link on Login overlay on P2 
	Then See the Register overlay on P2 
	When Enter credentials to register on P2 
		|Name062017155002|Test12345|Test12345|18062017155002@mailinator.com|FirstName|Lastname|
	And Click on Next button on P2 
	And Select vehicle credentials on P2 
		|New EcoSport|RL04TBBAMFSR05380|
	And Click on Next button on P2 
	And Fill in Vehicle Info credentials on P2 
		|Mr|NickName|An Giang|12345|0916834845|0123456789|ABCDEF| 
	And Click on Register button on P2 
	And Click on Close button on P2 
	When Redirect to link "https://www.mailinator.com/v2/inbox.jsp?zone=public&query=REPLACETOUNIQUE" on P2 
	And Click on Activation Email on MailInator page on P2 
	When Click on Activate My Account link in activation mail on P2 
	Then Registration Success message is displayed


@P2
Scenario: P2 Verify Forgot Username and Forgot Password on Owner Login page for VIETNAM 
	Given Open chrome browser on P2 
	When Redirect to link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/vn/en_vn/home/owner/HomePage-unauthenticated.html" on P2 
	And Click on Login link on P2 
	And See the Login overlay on P2
	And Click on Login button on P2
	Then Mandatory exceptions displayed for username and password
	|Username is mandatory|Password is mandatory|  
	Then Click on Forgot your username
	When See forgot overlay opened
	Then Enter email id
	||
	When click on Submit button
	Then Successful message displayed
    When Redirect to link "https://www.mailinator.com/v2/inbox.jsp?zone=public&query=REPLACETOUNIQUE" on P2
   	And Click on Forgot username Email on MailInator page
    Then Verify Username name in Forgot username Email
	||
	When Redirect to link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/vn/en_vn/home/owner/HomePage-unauthenticated.html" on P2 
	And Click on Login link on P2 
	And See the Login overlay on P2
	Then Click on Forgot your Password
	When See forgot overlay opened
	Then Enter email id and Username
	|||
	When click on Submit button
	Then Successful message displayed
    When Redirect to link "https://www.mailinator.com/v2/inbox.jsp?zone=public&query=REPLACETOUNIQUE" on P2
   	And Click on Forgot username Email on MailInator page
   	And Click on Password reset
    Then See Reset Password Overlay opened
    When Enter New password and confirm password
    |Test1234567|
    And click on Submit button
    Then Successful message displayed
	


@P2-NonJenkinsSc
Scenario: P2 Verify loading page for Owner for VIETNAM - Login using New User 
	Given Open chrome browser on P2 
	When Maximize browser and enter link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/vn/en_vn/home/owner/HomePage-unauthenticated.html" on P2 
	And Click on Login link on P2 
	And See the Login overlay on P2 
	When Enter credentials to login on P2 
		||Test1234567|
	And Click on Login button on P2 
	And Click on My Profile link on P2 
	And See VehicleName and Vincode on Profile overlay on P2 
		|New EcoSport|//*[@id='tabs-0'] |RL04TBBAMFSR05380| //*[@id='sticky-header']/table[3]/thead/tr/td[2]/span/span |
	And Click on Account Info tab on Profile overlay on P2 
	And See all account information in Account Info tab on Profile overlay on P2 
		||//*[@id='userName'] |FirstName|//*[@id='firstName'] | Lastname|//*[@id='lastName'] ||//*[@id='email'] |Mr|//*[@id='title'] |An Giang|//*[@id='state'] |12345|//*[@id='postCode'] ||//*[@id='mobileNumber'] ||//*[@id='workphoneNumber'] |NickName|//*[@id='nickName']|
	And Click on Add Vehicle on Profile overlay on P2 
	Then See that Add vehicle form is displayed on Add Vehicle tab on P2 Production 
	When Add vehicle with Vehicle name and Vincode on P2 Production 
		|New Fiesta|RL05DFBAMFMR09354|
	When Click on Confirm to Add Vehicle on P2 
	Then See the first vehicle tab name on P2 
	And See the second vehicle tab name on P2 
	When Click on second vehicle tab on P2 
	Then Verify second vehicle tab is loaded correctly on P2 
	When Click on first vehicle tab on P2 
	Then Verify first vehicle tab is loaded correctly on P2 
	When Click on second vehicle tab on P2 
	Then Verify second vehicle tab is loaded correctly on P2 
	When Click on Delete Vehicle on P2 
	Then See delete vehicle confirmation overlay 
	And Click on Confirm to Delete Vehicle on P2 
	And Do not See Vehicle tab name on P2 
		|New Fiesta|
	When Click on My Profile link on P2  
	And Click on Account Info tab on Profile overlay on P2 
	And See all account information in Account Info tab on Profile overlay on P2
		||//*[@id='userName'] |FirstName|//*[@id='firstName'] | Lastname|//*[@id='lastName'] ||//*[@id='email'] |Mr|//*[@id='title'] |An Giang|//*[@id='state'] |12345|//*[@id='postCode'] ||//*[@id='mobileNumber'] ||//*[@id='workphoneNumber'] |NickName|//*[@id='nickName']|
	And Click on Change Email Address link on Account Info on P2 
	And Verify Change Email to new email on Account Info on P2 
		||
	And Click on Change Email Address link on Account Info on P2 
	And Verify Change Email to new email on Account Info on P2 
		||
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test123456|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test1234567|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test123456|	
	And Click on first vehicle tab on P2 
	Then Verify first vehicle tab is loaded correctly on P2 
	When Click on Update New Version link on Profile overlay on P2 
	And Click on Download Now link on Profile overlay on P2
 	Then Logout China Owner
	When Maximize browser and enter link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/vn/en_vn/home/owner/HomePage-unauthenticated.html" on P2 
	And Click on Login link on P2 
	And See the Login overlay on P2 
	When Enter credentials to login on P2 
		||Test123456|
	And Click on Login button on P2 
	And Click on My Profile link on P2 	
 	Then Logout China Owner



@P2
Scenario: P2 Verify loading page for Owner for VIETNAM - Login using existing User
Given Open chrome browser on P2 
	When Maximize browser and enter link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/vn/en_vn/home/owner/HomePage-unauthenticated.html" on P2 
	And Click on Login link on P2 
	And See the Login overlay on P2 
	When Enter credentials to login on P2 
		|121017121847|Test1234567|
	And Click on Login button on P2 
	And Click on My Profile link on P2 
	And See VehicleName and Vincode on Profile overlay on P2 
		|New EcoSport|//*[@id='tabs-0'] |RL04TBBAMFSR05380| //*[@id='sticky-header']/table[3]/thead/tr/td[2]/span/span |
	And Click on Account Info tab on Profile overlay on P2 
	And See all account information in Account Info tab on Profile overlay on P2 
		|121017121847|//*[@id='userName'] |FirstName|//*[@id='firstName'] | Lastname|//*[@id='lastName'] |121017121847@mailinator.com|//*[@id='email'] |Mr|//*[@id='title'] |An Giang|//*[@id='state'] |12345|//*[@id='postCode'] |01210171218|//*[@id='mobileNumber'] |01210171218|//*[@id='workphoneNumber'] |NickName|//*[@id='nickName']|
	And Click on Add Vehicle on Profile overlay on P2 
	Then See that Add vehicle form is displayed on Add Vehicle tab on P2 Production 
	When Add vehicle with Vehicle name and Vincode on P2 Production 
		|New Fiesta|RL05DFBAMFMR09354|
	When Click on Confirm to Add Vehicle on P2 
	Then See the first vehicle tab name on P2 
	And See the second vehicle tab name on P2 
	When Click on second vehicle tab on P2 
	Then Verify second vehicle tab is loaded correctly on P2 
	When Click on first vehicle tab on P2 
	Then Verify first vehicle tab is loaded correctly on P2 
	When Click on second vehicle tab on P2 
	Then Verify second vehicle tab is loaded correctly on P2 
	When Click on Delete Vehicle on P2 
	Then See delete vehicle confirmation overlay 
	And Click on Confirm to Delete Vehicle on P2 
	And Do not See Vehicle tab name on P2 
		|New Fiesta|
	When Click on My Profile link on P2  
	And Click on Account Info tab on Profile overlay on P2 
	And See all account information in Account Info tab on Profile overlay on P2 
		|121017121847|//*[@id='userName'] |FirstName|//*[@id='firstName'] | Lastname|//*[@id='lastName'] |121017121847@mailinator.com|//*[@id='email'] |Mr|//*[@id='title'] |An Giang|//*[@id='state'] |12345|//*[@id='postCode'] |01210171218|//*[@id='mobileNumber'] |01210171218|//*[@id='workphoneNumber'] |NickName|//*[@id='nickName']|
	And Click on Change Email Address link on Account Info on P2 
	And Verify Change Email to new email on Account Info on P2 
		|1210171218470@mailinator.com|
	And Click on Change Email Address link on Account Info on P2 
	And Verify Change Email to new email on Account Info on P2 
		|121017121847@mailinator.com|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test123456|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test1234567|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test123456|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test1234567|
	And Click on Change Password on Account Info on P2
	And Verify Change Password to new password on Account Info on P2 
		|Test1234567|	
	And Click on first vehicle tab on P2 
	Then Verify first vehicle tab is loaded correctly on P2 
	When Click on Update New Version link on Profile overlay on P2 
	And Click on Download Now link on Profile overlay on P2 
	Then Logout China Owner


@Ford-Sanitys
Scenario: P2 B&P AU Verify loading landing page on Build and Price
		Given Open chrome browser on P2	
        When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/build-and-price/billboard.html" on P2
	  	And Click on Build and Price on P2
        When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/build-and-price/build-and-price.html?v=Focus&n=Focus_2015_AU&u=P&l=2000&c=2000&y=2015&vc=Cars" on P2
	  	When Click on Models on P2 INT
		Then Verify Drive away price with SL calls
	  	|//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]|0|
	  	When Click on Drive on P2 INT
	  	Then See Drive list on P2 INT
        When Click on Color on P2
        And Click on Ruby Red color on P2
        When Click on Trim on P2
        And Click on Cloth Seat Trim on P2
	  	When Click on Accessories on P2 INT
	  	Then Click on Exterior on P2 INT
	  	And Click on Sort by Name on P2 INT
	  	Then See the names are sorted for Exterior on P2 INT
	  	Then Click on Interior on P2 INT
	  	And Click on Sort by Name on P2 INT
	  	Then See the names are sorted for Interior on P2 INT
	  	Then Click on Safety&Security on P2 INT
	  	And Click on Safety Triangle on P2 INT
	  	Then Click on Exterior on P2 INT
	  	And Click on Accessory Name Item on P2 INT
		Then Verify Drive away price with SL calls
		|//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]|4|
	  	And Click on Sort by Price on P2 INT
	  	Then See the prices are sorted on P2 INT
	  	And Click on Accessory Price Item on P2 INT
		Then Verify Drive away price with SL calls
		|//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]|5|
	  	When Click on Review & Save button on P2
		Then See Pricing Summary on P2
		Then Verify Drive away price with SL calls
  		|//div[contains(@class,'txt-price')]|0|
		When Click on Share button on P2
		Then See the sharing popup on P2
		And Verify the Download File on P2


@Frod-Sanity
Scenario: P2 B&P INDIA Verify loading landing page on Build and Price
  Given Open chrome browser on P2
  When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/in/en_in/home/build-and-price/build-and-price.html?v=Ford%20EcoSport&n=EcoSport_FIN&u=P&l=10001&c=Agra&y=2017&vc=Sports_Utility_Vehicles" on P2
  And Click on Accessories on P2 IND
  Then See the price updated correctly on P2
  |//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'₹')] |
  When Click on Car Cover on P2 IND
  Then See the price updated correctly on P2
  |//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'₹')] |
  When Click on Color and Trim on P2 IND
  Then See the price updated correctly on P2
  |//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'₹')] |
  And Click on color on P2 IND
  |Smoke Grey| 
  Then See the price updated correctly on P2
  |//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'₹')] |
  When Click on Review & Save button on P2
  Then See Pricing Summary on P2
  And See the price updated correctly on P2
  |//div[contains(@class,'txt-price')] |
  And See your vehicle details on P2
  |Smoke Grey| //tr[2]/td[@class='txt-col2 ng-binding'] | Car Cover | //tr[3]/td[@class='txt-col2 ng-binding'] |



@P2
Scenario: P2 B&P INDIA Vehicle selection,Sort By Price/Fuel/Body Style,Pkg,Color/Trim,Assosseries,Hamburger menu,Change Vehicle,Book a test drive,Review & save,start a new build,Save as PDF,Share,Req a Quote
  Given Open chrome browser on P2
  When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/in/en_in/home/build-and-price/billboard.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P2
  Then See CTAs and links are functional on the page
  When Click on Build & Price CTA    
  When Select a Vehicle
  |Figo|
  When Click on Hamburger menu
  Then Click on start a New build
  Then Click on YES for confirmation overlay
  And see user is navigate to vehicle selection page
  And Select a Vehicle
  |Figo|
  And Click on Accessories on P2 IND
  Then See the price updated correctly
  |//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'₹')]|NA|
  When Click on Car Cover on P2 IND
  Then See the price updated correctly
  |//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'₹')]|NA|
  When Click on Color and Trim on P2 IND
  Then See the price updated correctly
  |//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'₹')]|NA|
  And Click on color on P2 IND
  |Smoke Grey| 
  Then See the price updated correctly
  |//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'₹')]|NA|
  When Click on Hamburger menu  
  And Click on Change Vehicle 
  Then Click on YES on confirmation overlay 
  And see user is navigate to vehicle selection page
  And Select a Vehicle
  |Figo|
  When Click on Hamburger menu  
  And Click on Book a Test Drive 
  Then User enters the details and submits the test drive request
  |Mr|TestFullName|DO-NOT-DELETE|080817174330@mailinator.com|0123456789|TestAddress|An Giang|0-3 Months|
  And Verify thank you page is seen and Close Overlay
  When Click on Hamburger menu
  When Click on Review & Save button
  Then See Pricing Summary on P2
  And See the price updated correctly
  |//div[contains(@class,'txt-price')]|//*[contains(text(),'Total Price')]/ancestor::td/following-sibling::td|
  When Click on Back link in Review & Save page
  Then see User is directing to Build and Price page
  When Click on Hamburger menu
  When Click on Go Back to Ford.com
  Then See confirmation overlay is displayed
  When user clicks on Cancel button
  Then See user stays on Build and Price page
  When Click on Change Vehicle link under vehicle nameplate
  Then Click on YES on confirmation overlay 
  And see user is navigate to vehicle selection page
  And Select a Vehicle
  |Figo|
  When Click on Drive Away Price arrow
  Then Drive Away Price should be expanded and correct price should be displayed
  And Close expanded window
  When Click on Review & Save button on P2
  Then See Pricing Summary on P2
  And See the price updated correctly
  |//div[contains(@class,'txt-price')]|//*[contains(text(),'Total Price')]/ancestor::td/following-sibling::td|
  When Click on Save as PDF link
  Then User is able to download the pdf file
  When Click on Share link
  Then See share popup is displaying
  When Click on the Request a quote Button 
  Then user can make a request after filling all the details in the page
  |Mr|TestFullName|DO-NOT-DELETE|080817174330@mailinator.com|0123456789|TestAddress|An Giang|0-3 Months|
  And Verify thank you page is seen and Close Overlay

 ####################### AUST POLK ####################### 
@Ford-Sanity
Scenario: P2 B&P AU Validate the Select Location overlay,Postalcode on Build and Price
		Given Open chrome browser on P2
		When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/build-and-price/billboard.html" on P2
		And Click on Build and Price on P2
		When Input into Postcode field on POLK on P2
		|	1a0000	|
		And Click on Submit button on POLK Build and Price on P2
		Then See validation of invalid Postalcode on POLK on P2
		|Invalid postal code.|This field is required|Invalid postal code.|
		When Input into Postcode field on POLK on P2
		| 2055 |
		And Click on Submit button on POLK Build and Price on P2
		Then See all components on Select Vehicles page on POLK P2
		| 2055 |Your Location|Fiesta ST|Mondeo|Mustang|Ranger|EcoSport|
		When Click on any Vehicle on Select Vehicles page on POLK P2
		|fiesta|
		Then See all components on Vehice Build and Price POLK P2
        | model | drive | color | trim |  optionsPackage | Fiesta ST Hatch | $ 35,891 |
   	

@Ford-Sanity		
Scenario: P2 B&P AU Verify Close Location selection overlay on Build and Price
		Given Open chrome browser on P2
		When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/build-and-price/billboard.html" on P2
		And Click on Build and Price on P2
		And verify Cancel button exist
		When Click on Cancel button on POLK Build and Price on P2



@Ford-Sanity
Scenario: P2 B&P AU Verify Navigating to vehicle Selection by entering valid Postal code on Build and Price
		Given Open chrome browser on P2
		When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/build-and-price/billboard.html" on P2
		And Click on Build and Price on P2
		And verify Cancel button exist
		When Input into Postcode field on POLK on P2
		|3551|
		And Click on Submit button on POLK Build and Price on P2
		Then Verify not seeing prices on POLK Build and Price on P2 



@P2
Scenario: P2 B&P AU Verify Sorting the Vehicle based on different Criteria on Build and Price
	Given Open chrome browser on P2
	When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/build-and-price/billboard.html" on P2
	And Click on Build and Price on P2
	And verify Cancel button exist
	And Input into Postcode field on POLK on P2
	|2000|
	And Click on Submit button on POLK Build and Price on P2


@Ford-Sanity
Scenario: P2 B&P AU Verify seeing parameters passing to Service Layer at form loading on POLK Build and Price on P2
	Given Open chrome browser on P2
	When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/build-and-price/billboard.html" on P2
	And Click on Build and Price on P2
	And verify Cancel button exist
	And Input into Postcode field on POLK on P2
	|2000|
	And Click on Submit button on POLK Build and Price on P2
  	When Select a Vehicle
    |focus|	
	Then see Drive away price
	When Click on Drive on P2 INT
	Then See Drive list on P2 INT
    When Click on Color on P2
    And Click on Ruby Red color on P2
    When Click on Trim on P2
    And Click on Cloth Seat Trim on P2
	When Click on Accessories on P2 INT
		
@TestF
@Ford-Sanitys
Scenario: P2 B&P AU Verify Drive away price of Personal Type Vehicles,Drive Transmission key/Accessories price sending to SL,drive away price after changing Model/Engine transmission/Color and Trim,Accessories price/Optional price sending to SL & drive away price after selecting accessories/Optional pkg 
	Given Open chrome browser on P2
	When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/th/en_th/home/build-and-price.html" on P2
	And Click on TH Build and Price on P2
	#And verify Cancel button exist
	#And Input into Postcode field on POLK on P2
	#|2000|
	#And Click on Submit button on POLK Build and Price on P2     
  	When Select a Vehicle
 	|everest|	
	Then see TH Drive away price
	When Click on Models on P2 INT
  	Then Verify Drive away price with SL calls
  	|//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]|0|
  	When Click on kind of vehicle on P2 INT
  	|Focus Active|
  	Then Verify Drive away price with SL calls
  	|//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]|1| 	
  	When Click on Drive on P2 INT
	Then See Drive list on P2 INT
    When Click on Color on P2
    And Click on Absolute Black color on P2
    #When Click on Trim on P2
	#When Click on Accessories on P2 INT
	Then Click on Exterior on P2 INT
	And Click on Sort by Name on P2 INT
	Then See the names are sorted for Exterior on P2 INT
	Then Click on Interior on P2 INT
	And Click on Sort by Name on P2 INT
	Then See the names are sorted for Interior on P2 INT
	Then Click on Utility&Safety on P2 INT
	And Click on Belt Pad on P2 INT
	Then Click on Exterior on P2 INT
	And Click on Accessory Name Item on P2 INT
    Then Verify Drive away price with SL calls
	|//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]|5|
	And Click on Sort by Price on P2 INT
	Then See the prices are sorted on P2 INT
	And Click on Accessory Price Item on P2 INT
	Then Verify Drive away price with SL calls
    |//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]|6|

@Ford-Sanitys
Scenario: P2 B&P AU Verify the drive away price of commercial type vehicles 
	Given Open chrome browser on P2
	When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/build-and-price/billboard.html" on P2
	And Click on Build and Price on P2
	And verify Cancel button exist
	And Input into Postcode field on POLK on P2
	|4000|
	And Select Usage Type
	|Commercial|
	And Click on Submit button on POLK Build and Price on P2     
  	When Select a Vehicle
 	|ranger|
	Then see Drive away price
	When Click on Models on P2 INT
  	Then Verify Drive away price with SL calls
  	|//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]|0|


@Ford-Sanitys
Scenario: P2 B&P AU Verify the drive away price in Review and Save page,Verify the drive away price after Download PDF,Shared feature,Back Button Feature,Verify Change Postal Code link in Review and Save page
	Given Open chrome browser on P2
	When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/build-and-price/billboard.html" on P2
	And Click on Build and Price on P2
	And verify Cancel button exist
	And Input into Postcode field on POLK on P2
	|4000|
	And Select Usage Type
	|Commercial|
	And Click on Submit button on POLK Build and Price on P2
  	When Select a Vehicle
 	|ranger|
	Then see Drive away price
	When Click on Models on P2 INT
 	When Click on Review & Save button on P2
	Then See Pricing Summary on P2
	And Verify No Cost Option text for zero price
  	|No Cost Option| 	
  	And Verify Drive away price with SL calls
  	|//div[contains(@class,'txt-price')]|0|
	When Click on Save as PDF link
	Then User is able to download the pdf file 
	And Verify Drive away price with SL calls
  	|//div[contains(@class,'txt-price')]|0|	
	When Click on Share link
	Then See share popup is displaying
	And Verify Drive away price with SL calls
  	|//div[contains(@class,'txt-price')]|0|	
  	When Click on Back link in Review & Save page
	Then see User is directing to Build and Price page
	And Verify Drive away price with SL calls	
    |//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]|0|
  


@Ford-Sanitys
Scenario: P2 B&P AU Verify Functionality of Change Postal Code link and Change Vehicle link in B&P configuration page 
	Given Open chrome browser on P2
	When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/build-and-price/billboard.html" on P2
	And Click on Build and Price on P2
	And verify Cancel button exist
	And Input into Postcode field on POLK on P2
	|4000|
	And Select Usage Type
	|Commercial|
	And Click on Submit button on POLK Build and Price on P2
  	When Select a Vehicle
 	|ranger|	
	Then see Drive away price
	And Verify Drive away price with SL calls
  	|//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]|0|	
	When Click on Change Vehicle link under vehicle nameplate
	Then Click on YES on confirmation overlay 
	And see user is navigate to vehicle selection page
	And Select a Vehicle
	|mondeo|	
	Then see Drive away price
	And Verify Drive away price with SL calls
  	|//div[@class='rcol']//span[@class='ng-binding' and contains(text(),'$')]|0|

@CR
Scenario: CRs AU Vehicle Display within Test Drive Form and verify Showroom selector within Test Drive Form
	Given Open chrome browser on P2
	When Maximize browser and enter link "https://wwwdev.brandap.ford.com/content/guxap-uat-demo/au1/en_au/home/VehicleNameplate.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P2
  	And Select any Vehicle
 	|Fiesta|	
	And Select a Model
	|0|
	Then See the selected vehicle displayed in the test drive form
	When Click on Change Model
	Then Select a Model
	|1|
	And See the selected vehicle displayed in the test drive form
	When Enter all items correctly and Submit
	|Mr|TestFirstName|TestLastName|080817174330@mailinator.com|0123456789|0123456789|2001|
	Then Verify thank you page is seenP2


@CR
Scenario: CRs AU Vehicle Display within RAQ Form and verify Showroom selector within RAQ Form
	Given Open chrome browser on P2
	When Maximize browser and enter link "https://wwwdev.brandap.ford.com/content/guxap-uat-demo/au1/en_au/home/VehicleNameplateRAQcr.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P2
  	And Select any Vehicle
 	|Fiesta|	
	And Select a Model
	|0|
	Then See the selected vehicle displayed in the test drive form
	When Click on Change Model
	Then Select a Model
	|1|
	And See the selected vehicle displayed in the test drive form
	When Enter all items correctly and Submit
	|Mr|TestFirstName|TestLastName|080817174330@mailinator.com|0123456789|0123456789|2001|
	Then Verify thank you page is seenP2



