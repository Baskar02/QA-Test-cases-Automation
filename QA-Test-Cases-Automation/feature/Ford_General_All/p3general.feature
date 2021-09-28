Feature: P3 General Test Scenarios


@Ford-Sanity
Scenario: P3 Service Price Calculator
	Given Open chrome browser
	When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/owners/service/calculator.html" in P3 Ford
	Then See all components in service price calculator page in P3 Ford
	When Select values in all the drop downs in P3 Ford
	|2020|ZG ESCAPE|2.0L Diesel Automatic AWD|Other|
	#|2020|SA FOCUS1.5L Petrol Automatic FWD|Other|
	And Input into Estimated Kilometres to date and select First Registration Date in P3 Ford
	|70,000|
	And Click on Calculate button in P3 Ford
	Then See all Kilometers and Service number section highlighted accordingly in P3 Ford
	| 60,000 | 4 | 75,000 | 5 | 90,000 | 6 | 
	When Click on highlighted Kilometers and Service number in P3 Ford 
	Then See all components on Payment Details in P3 Ford
	When Select up to 3 items on Additional Maintenance Items and Verify Service Price in P3 Ford
	And Click on View and Print PDF in P3 Ford
	
	
	

@P3
Scenario: P3 Hot Deals Page
Given Open chrome browser
When Maximize browser and enter link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/ar/en_ar/home/offers/Offers.html" in P3 Ford
And Click on Detalhes da Oferta for first vehicle
Then See all components loaded correctly in Offer Details page in P3 Ford
| KBB Official Guide | J.D. Power | Best Resale Value |
		



@Ford-Sanitys
Scenario: P3 Latest Offers AU
 Given Open chrome browser
 When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/latest-offers.html" in P3 Ford
 When Input wrong postcode into postcode field and verify validation message displayed in P3 Ford
 | 0123 |There is no region for your postcode.|
 And Input correct postcode into postcode field in P3 Ford
 | 2055 |
 Then Verify all the vehicles have details about model year details of offers available in P3 Ford
 When Click on Sort by in Latest Offer page in P3 Ford
 | Price Low To High | 
 Then See the ascending orders in P3 Ford 
 And Click on the filters on the left pane of the page in P3 Ford
 | CAR | 
 Then See all the filters are behaving correctly in P3 Ford
 And Click on any offer in P3 Ford
  | Focus Trend |
Then see all general features display correctly
|Focus Trend|Reverse Camera|Cruise Control|SYNC|Disclaimer|Request a Call|Request a Test Drive|More Offers|
When Click on Vehicle Specifications
Then View the Vehicle Specifications displayed
|$27,690|2019 Plate Focus Trend|Fuel Efficient|8 Speed|8 inch|SYNC3|Apple CarPlay|Satellite Navigation|Pre-Collision|Rear View Camera|




@Ford-Sanity
Scenario: P3 Latest Offers ZA
 Given Open chrome browser
 When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/za/en_za/home/ford-offers.html" in P3 Ford
 Then Verify all the vehicles have details about model year details of offers available for ZA in P3 Ford
When Click on the Grid view from top pane of the page
Then Verify that all the vehicles are displayed in Grid View
When Click on the List view from top pane of the page
Then Verify that all the vehicles are displayed in List View
When Click on Quick View CTA button on any of the latest offer
Then Verify the items on Quick View Overlay
When Click on Short list button from overlay
Then Ensure vehicle is added in short listed sections


@P3
Scenario: P3 More Offers Section - Vehicle Configuration Page
Given Open chrome browser on P2
When Maximize browser and enter link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/au/en_au/home/build-and-price/billboard.html" in P3 Ford
And Click on Build and Price on P2
And verify Cancel button exist
And Input into Postcode field on POLK on P2
|4000|
And Click on Submit button on POLK Build and Price on P2
And Select a Vehicle
|Mondeo|	
Then It navigate you on the latest offers page
And B&P configuration should show the vehicle models of selected nameplate that has laters offers
When Click on Latest offer button on B&P config page
Then User directed to the respective configuration page of B&P More Offers Section
When Redirect to link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/au/en_au/home/build-and-price/billboard.html" in P3 Ford
And Click on Build and Price on P2
And Select a Vehicle
|Fiesta|	
Then B&P configuration page shows all the vehicle models that has laters offers




@Ford-Sanitys
Scenario: P3 Pre Delivery 
	Given Open chrome browser
	When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/predelivery.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" in P3 Ford
  When Fill in Pre Delivery form in P3 Ford
  |	Mr | TestFirstName | TestLastName | 080817174330@mailinator.com | 0916834845 | DenLu | Hanoi | 2055 | ACT | Focus ST | 2018 Focus ST Hatch | Frozen White | JH3SCV6U967402221 | ACT |John McGrath Ford (Phillip) |
  And Click on Next button in Pre Delivery form in P3 Ford
  Then See all records in Pre Delivery page in P3 Ford
  |	 Focus ST | 2018 Focus ST Hatch | Frozen White | JH3SCV6U967402221 | John McGrath Ford (Phillip) / 22140 | YOUR NEW FORD | MODEL | SERIES | COLOUR | VIN | DEALER | Step 1 | Basics of your Ford | Step 2 | Key features of your Ford | Step 3 | Other features of your Ford | Step 4 | Personalise your Ford |
  When Click on Save and Continue button in Pre Delivery page in P3 Ford
  Then Verify message is successfully saved in Pre Delivery page in P3 Ford
  | YOUR CURRENT PROGRESS HAS BEEN SUCCESSFULLY SAVED, AN EMAIL HAS BEEN SENT TO YOUR EMAIL ADDRESS WITH A LINK TO GET BACK TO THIS SECTION. |
  And Click on Step 4 in Pre Delivery page in P3 Ford
  Then See Personalise your Ford form in Pre Delivery page in P3 Ford
  | MOBILE PHONE | ADD YOUR FAVOURITE STATIONS | SATNAV SETTINGS |CLIMATE CONTROL | TIME FORMAT | DRIVER SEAT POSITION | AUTOMATIC HEADLIGHTS | AMBIENT LIGHTING | HAVE WE COVERED EVERYTHING? |
  When Click on Next button in Personalise for pick up section in P3 Ford
  Then See all records after hitting Next in Step 4 in P3 Ford
  |	 Focus ST | Focus ST Hatch | Frozen White | JH3SCV6U967402221 | John McGrath Ford (Phillip) / 22140 | YOUR NEW FORD | MODEL | SERIES | COLOUR | VIN | DEALER | YOUR DETAILS | NAME | Mr TestFirstName TestLastName | EMAIL | 080817174330@mailinator.com | PHONE | 0916834845 | ADDRESS | DenLu | SUBURB | Hanoi | 2055 | STATE | ACT | YOUR PERSONALISED ITEMS | CLIMATE CONTROL | 22 Degrees | TIME FORMAT | 24-HOUR | DRIVER SEAT POSITION | MEDIUM |
  When Click on View Summary as PDF in Summary page in P3 Ford
  And Input into captcha field in Summary page in P3 Ford
  | ABCD | 
  And Click on Submit To Dealer in Summary page in P3 Ford
  Then See Confirmation page displayed in P3 Ford
  |	Ford Australia - Ford Orientation Guide | Confirmation | TO DO LIST | Bring your mobile phone when you pick up your vehicle | Check your | Download the Ford Owners app for | Register for |
  When Redirect to third party new link "https://www.mailinator.com/v2/inbox.jsp?zone=public&query=080817174330" on P2 
  And Click on Email on MailInator page
  When Verify Ford Delivery Checklist mail
  
  
  
  
@P3
Scenario: P3 NZ Pre Delivery 
	Given Open chrome browser
	When Maximize browser and enter link "https://wwwint.brandap.ford.com/content/ford/nz/en_nz/home/predelivery/ap/predelivery_training.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" in P3 Ford
  When Fill in NZ Pre Delivery form in P3 Ford
  |	Mr | TestFirstName | TestLastName | 080817174330@mailinator.com | 0916834845 | Focus | Trend Petrol Hatch | Frozen White | Auckland | John Andrew Ford | 2 Great North Rd, Grey Lynn |
  And Click on Next button in Pre Delivery form in P3 Ford
  Then See all records in NZ Pre Delivery page in P3 Ford
  |	Focus | Trend Petrol Hatch | Frozen White | Auckland | John Andrew Ford | 2 Great North Rd, Grey Lynn | YOUR NEW FORD | Model | Series | Colour | Delivery Calendar | Dealer City | Dealer Name | Dealer Address | Step 1 | Basics of your Ford | Step 2 | Key features of your Ford | Step 3 | Advanced features of your Ford | Step 4 | Personalize your Ford |
  When Click on Next button in NZ Pre Delivery page in P3 Ford
  And Click on Step 4 in Pre Delivery page in P3 Ford
  Then See Personalise your Ford form in NZ Pre Delivery page in P3 Ford
  | PREFERRED DELIVERY TIME | TIME FORMAT | ADD YOUR FAVOURITE STATIONS | MOBILE PHONE | Any Special requests | Friends & Family members accompanying you |
  Then Select the Preferred Delivery Time in NZ P3 Ford
  | 2 | 20 | PM |
  When Click on Next button in Personalise for pick up section in NZ P3 Ford
  Then See all records after hitting Next in Step 4 in NZ P3 Ford
  | Focus | Trend Petrol Hatch | Frozen White | Auckland | John Andrew Ford | 2 Great North Rd, Grey Lynn | YOUR NEW FORD | Model | Series | Colour | Delivery Calendar | Dealer City | Dealer Name | Dealer Address | YOUR PERSONALISED OPTIONS | PREFERRED DELIVERY TIME | 2:20 PM | Features you do not require to learn |
  When Click on View Summary as PDF in Summary page in P3 Ford 
  And Click on Email PDF in Summary page in P3 Ford 

    