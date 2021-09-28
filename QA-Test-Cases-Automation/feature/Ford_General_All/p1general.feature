Feature: P1 General Test Scenarios

Background:
Given Open Chrome browser on P1General
 

@Ford-Sanity
Scenario Outline: P1 Home page
	When Maximize browser and enter link "<HomePageUrl>"
	Then See all components on home page loaded without performance issue
	When Input position "<BlankData>" to Mini LAD and Search
	Then Appropriate validation message displayed
	When Input position "<InvalidData>" to Mini LAD and Search
	Then Appropriate validation message displayed
	When Input position "<PositionText>" to Mini LAD and Search
	Then Dealer should be searched and shown on the page
	When Click on "+"
	Then Dealer section should be expanded and dealers should be displayed
	When Click on "X"
	Then Dealer section should be compressed and First dealer should be shown
	When Click on "+"
	Then Dealer section should be expanded and dealers should be displayed
	
Examples:
|HomePageUrl|PositionText|BlankData|InvalidData|
|https://wwwqa.brandaplb.ford.com/content/ford/ph/en_ph/home.html|Wellington Street, Antipolo, Rizal||InvalidData|


@Ford-Sanity
Scenario Outline: P1 Showroom 
	When Maximize browser and enter link "<LandingPageUrl>"
	Then See all components on AU home page is loaded without performance issue
	When Click on Main Nav "<VehicleMenuItemName>"
	And Click on tab in Vehicle "<TabName>" 
	Then Verify vehicle name in menu "<Vehicle1>"
	When Click on a model "<Vehicle1>"   
	And See all components on "<Vehicle1>" page loaded without performance issue
	
Examples:
| LandingPageUrl | VehicleMenuItemName | TabName | Vehicle1 | Vehicle2 | VehiclePageUrl | Vehicle2PageUrl |
| https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home.html | Vehicles | SUVs | EcoSport | Escape | https://wwwqa.brandap.ford.com/content/guxap-uat-demo/ph/en_ph/home/vehicles.html | https://wwwqa.brandap.ford.com/content/guxap-uat-demo/ph/en_ph/home/cars/fiesta.html |



@Ford-Sanity
Scenario Outline: P1 Nameplate Page
	When Maximize browser and enter link "<HomePageUrl>"
	Then See all components on AU home page is loaded without performance issue
	When Click on Main Nav "<VehicleMenuItemName>"
	Then Search SubMenu link accros the Main Navigation link "<SubMenuItem>"
	And See all components on "<SubMenuItem>" page loaded without performance issue
	When Click on Secondary menu switcher
	And Click on Secondary Menu "<GalleryItemName>"
	When Click on image on Gallery
	Then Verify overlay open from image Gallery
	When Click on View Details
	Then Verify seen Share and Download button
	When Click on Share
	Then See the sharing popup
	When Click on Download
	And Click to close overlay
	Then Verify 360 images
	When Click on Colorize tab
	Then Verify Image with Color is seen
	When Click on Black color to change
	Then Verify the color of image and title are changed
	When Click on Disclosure to Collapse
	Then Verify don't see Disclosure content 
	When Click on Disclosure
	Then Verify see Disclosure Content

Examples:
| HomePageUrl | VehicleMenuItemName | SubMenuItem | GalleryItemName |
| https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home.html | All Vehicles | EcoSport | Gallery |



@Ford-Sanity
Scenario Outline: P1 Model compare
	When Maximize browser and enter link "<MondeoPageUrl>"
	Then See all components on Mondeo page loaded without performance issue
	When Click on Model Compare button
	Then Verify user is taken to model compare page
	When Click on Active Compare button
	And Select up to 2 items on the list
	And Click on Compare button
	Then Click on select other models to compare button
	And Select any model from the list and unselect after that
	When Select any model
	And Click on Confirm button
	Then See the old model should be replaced with the new selected models
	When Click on Add Models To Compare button
	And Select a model and click on Cancel button
	Then See selected vehicle should not be added in compare model page
	And Click on Add Models To Compare button
	When Select any model
	And Click on Confirm button
	Then See the old model should be replaced with the new selected models
	When Click on Back to Overview CTA
	And Click on Active Compare button
	And Select up to 3 items on the list
	And Click on Compare button
	Then Compare model result page should be displayed with selected model listed with details
Examples:
| MondeoPageUrl | 
| https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/suv/escape.html |


@P1
Scenario Outline: P1 Locate a dealer at Philipines
	When Maximize browser and enter link "<HomeLandingPageUrl>"
	Then See all components on home page loaded without performance issue
	When Click on locate a dealer at the header
	Then See page redirected to link contains "<LocateADealerPage>"
	When Input text to Search Input and click Search "<Position>"
	Then Verify seen search result
	
Examples:
| HomeLandingPageUrl | LocateADealerPage | Position |
| https://wwwqa.brandap.ford.com/content/guxap-uat-demo/ph/en_ph/home.html | locate-a-dealer.html | Manila |


@P1
Scenario Outline: P1 Locate a dealer at Australia
	When Maximize browser and enter link "<HomeLandingPageUrl>"
	And Input text to Search Input and click Search "<Position>"
	And Click on Submit on AU LAD
	Then Verify seen search result
	And Verify Delears details on Map
Examples:
| HomeLandingPageUrl | LocateADealerPage | Position |
| https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/shopping/locate-dealer.html | locate-dealer.html | Queensland Avenue, Broadbeach, Queensland |



@P1
Scenario Outline: P1 Engineering 
	When Maximize browser and enter link "<EngineeringPageUrl>"
	Then See all components on Engineering page loaded without performance issue
	When Click on boron steel in the engineering page
	When Go to the hotspot component and hover on the plus sign

Examples:
| EngineeringPageUrl | BoronSteelPageUrl |
| https://wwwqa.brandap.ford.com/content/guxap-uat-demo/ph/en_ph/home/engineering.html | https://wwwqa.brandap.ford.com/content/guxap-uat-demo/ph/en_ph/home/engineering/boron-steel.html |	


@P1
Scenario Outline: P1 Gallery
	When Maximize browser and enter link "<FiestaPageUrl>"
	Then See all components on Fiesta page loaded without performance issue
	When Click on Secondary menu switcher on Fiesta
	And Click on Secondary Menu "<GalleryItemName>"
	When Click on image on Gallery
	Then Verify overlay open from image
	When Click on Show Details
	Then Verify seen Share and Download button
	When Click on Share
	Then See the sharing popup
	And Click on Download
	When Click on the X button on the top left
	When Click on view more button
	Then Verify see more images
	When Click on Video
	Then see video is playing without error
Examples:
| FiestaPageUrl | GalleryItemName | GalleryItemUrl |
| https://wwwqa.brandaplb.ford.com/content/ford/nz/en_nz/home/suv/escape.html | Gallery | https://wwwqa.brandaplb.ford.com/content/ford/nz/en_nz/home/suv/escape/gallery.html |



@P1
Scenario Outline: P1 Accessories
	When Maximize browser and enter link "<FiestaPageUrl>"
	Then See all components on Fiesta page loaded without performance issue
	When Click on Secondary menu switcher on Fiesta
	And Click on Secondary Menu "<AccessoriesItemName>"
	When Click on image on Accessories
	Then Verify overlay open from image Accessories
	When Click on Show Details
	Then Verify seen Share and Download button
	When Click on Share
	Then See the sharing popup
	And Click on Download
	When Click on the X button on the top left

Examples:
| FiestaPageUrl | AccessoriesItemName | AccessoriesItemUrl |
| https://wwwqa.brandap.ford.com/content/guxap-uat-demo/ph/en_ph/home/cars/fiesta.html | Accessories | https://wwwqa.brandap.ford.com/content/guxap-uat-demo/ph/en_ph/home/cars/fiesta/accessories.html |


@Ford-Sanity
Scenario Outline: P1 Newsroom - Verification of search functionality
	When Maximize browser and enter link "<NewsRoomUrl>"
	Then See all components in newsroom page	
	And Verify the content of news article page
	Then Verify Sort By Functionality 
Examples:
	|NewsRoomUrl|
	|https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/about-ford/newsroom.html|



@Ford-Sanity
Scenario: P1 Search functionality on Home page
	When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/in/en_in/home.html"
	Then Click on Search Icon from Home Page
	And Input any word into Search field in newsroom popup page
	|fiesta|
	Then See the searched word display in newsroom page
	|Fiesta|
	Then Input any word into Search field in newsroom page
	|invalidsearch| 
	Then See the searched word display in newsroom page
	|No results for "invalidsearch"|


@Ford-Sanity
Scenario Outline: P1 Model details page
When Maximize browser and enter link "<HomePageUrl>"
Then see all the componets and CTAs are functioning
When Click on Back to Overview CTA
Then Model Compare page should be displayed
When Click on any model link CTA on Model Compare page
Then see user is taken to model details page
And see select other models to compare CTA is disabled
When Click on Add Models To Compare button
Then Click on New Model checkbox "<ModelName>"
And Click on Confirm button on model compare	
When Click on select other models to compare CTA
And Click on Other Model checkbox
And Click on Confirm button on model compare
When Click on Book a Test Drive CTA
Then Test drive form should be opened

Examples:
|HomePageUrl|ModelName|
|https://wwwqa.brandaplb.ford.com/content/ford/au/en_au/home/suv/escape/models/ambiente-awd.html|Escape Trend 1.5L EcoBoost Petrol FWD|



@Ford-Sanity
Scenario: P1 Validate Country Selector (MEA)
	When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/sau/en_sa/home/country-lang-selector.html"
	Then Page UI open correctly and Reseptive country page opened as per selection
	|المملكة العربية السعودية‬|
	|أفغانستان|


@Ford-Sanity
Scenario: P1 Verify Bright Cove functinality when it configured inside a page (Not like a overlay) 
	When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/th/en_th/home-test-page-1/trucks/ranger/gallery.html#overlay_id=Gallery-videos_video_hill-start-assist-and-hill-descent-control-new"
	And Click on the play Icon
	Then Bright cove should be playing


	
@P1
Scenario: P1 Test Drive Form
When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/in/en_in/home/new-test-drive-generic.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true"
Then see all componets loads successfully without performance issue
When On overlay do not input anything and click on submit
Then Verify error message is seen
When On overlay enter an incorrect item on one of the fields and click submit
|TestFirstName|TestLastName|080817174330|7123456789|Karnataka|Mangalore|Cauvery Ford, Padil|All-New EcoSport|1-3 Months|
Then Verify error message is seen
When On overlay enter all items correctly and click on submit
|TestFirstName|TestLastName|080817174330@mailinator.com|7123456789|Karnataka|Mangalore|Cauvery Ford, Padil|All-New EcoSport|1-3 Months|
Then Verify thank you page is seen



@P1
Scenario: P1 Request a brochure
When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/guxap-uat-demo/ph/en_ph/home/vehicles.html#overlay/content/guxap-uat-demo/ph/en_ph/home/forms/request-a-brochure.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true"
Then Verify brochure download overlay is seen
When Do not select any vehicle and click on Download button
Then see validation message is displayed
When Select a vehicle on brochure download overlay 
And click on Get By Email button
Then verify Email form is opened 
And  Fill all the details on Email form
|TestName|080817174330@mailinator.com|
When Click on submit email form
Then verify Thank you message
When Redirect to link "https://wwwqa.brandap.ford.com/content/guxap-uat-demo/ph/en_ph/home/vehicles.html#overlay/content/guxap-uat-demo/ph/en_ph/home/forms/request-a-brochure.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P1
Then Select a vehicle and click on download brochure button
And Verify PDF file is downloaded and thank you page is seen
|VN_Fiesta|



#@P1
Scenario: P1 Fleet Registration Form
When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/in/en_in/home/vehicles.html#overlay/content/ford/in/en_in/site-wide-content/overlays/forms/fleet-registration.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true"
Then user is navigated to Ford Fleet Registration overlay
When On overlay do not input anything and click on submit
Then See validation message is displayed
When Enter an incorrect item on one of the fields and click submit
|Mr|TestFirstName|TestLastName|080817174330|0123456789|testbusinessname|0123456789|100|
Then Verify error message is seen
When enter all items correctly and click on submit
|080817174330@mailinator.com|
Then Verify thank you page is seen



@P1
Scenario: P1 Keep Me Informed Form
When Maximize browser and enter link "https://wwwqa.brandaplb.ford.com/content/ford/in/en_in/home/cars/aspire/accessories.html#overlay/content/ford/in/en_in/site-wide-content/overlays/forms/kmi.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true"
And On overlay do not input anything and click on submit
Then See validation message is displayed
When Enter an incorrect item on one of the fields and click submit kmi
|Mr|TestFirstName|TestLastName|080817174330|0123456789|1234|0-3 Months|
Then Verify error message is seen
When enter all items correctly and click on submit
|080817174330@mailinator.com|
Then Verify thank you page is seen




	