Feature: MasterCard General Test scenarios

  
Scenario: Verify Broken links
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/sau/en_sa/home/country-lang-selector.html" and check if link is broken


Scenario: Verification of Flyout navigation Menu and Sub-Menu
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
		And Check for Menu's
		|About|Insights & Solutions|Events|News & Media|
		Then Magnifying glass should in proper position in desktop view
		When Check display of MasterCard logo on page
		Then Mouse over on flyout menu's
		|About|Our Story|Our People|Our Work|
		|Insights & Solutions|Featured Stories|Research & Findings|
		|Events|Upcoming|Previous|
		|News & Media|Newsroom|Multimedia|
		Then There should be two level of Menu's
		And Dropdown options should be displayed for each
		And A highlighter bar should be displayed below the headers	--- > Code pending as unable to script for header highlight 
		When click on each of the dropdown options provided
		|About|Our Story|Our People|Our Work|
		|Insights & Solutions|Featured Stories|Research & Findings|
		|Events|Upcoming|Previous|
		|News & Media|Newsroom|Multimedia|
		Then Page redirected to another expected page/URL



Scenario Outline: Validate Search bar functionality
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
		And Click on Search icon from Magnifying glass icon
		Then Search bar should be displayed along with X
		When Enter a search string "<Validstring>" and Click on search icon
		Then "<Validstring>" string should be displayed in serch result page
		When Enter a search string "<Invalidstring>" and Click on search icon
		Then "<test123test>" string should not be displayed in serch result page
		When click on X to close the search bar
		Then Search bar should be disappear
Examples:
		|Validstring|Invalidstring|
		|Masterpass|test123test|
		
		

Scenario: Stay Informed Form in header
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
		And Click on Join the Community CTA from header menu
		Then I should see the form displayed in an overlay
		When Do not input anything and click on submit
		Then See error message is seen
		When enter an incorrect item on one of the fields
		|mailinator.com|$#@|*&^|~!@#|Singapore|
		And Click on Joint the Community
		Then See error message is seen
		When input all items and enter incorrect captcha
		|testuser@mailinator.com|firstname|lastname|company|singapore|
		And Click on Joint the Community
		Then Form should not be submitted
		And See error message is seen
		When input all items and enter correct captcha
		And Click on Joint the Community
		Then Form submission should be successful and User details should feed to Pardot
		


Scenario: Verification of Footer links and buttons 
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
		Then Existence of mastercard logo to bottom of footer
		And see Copyrights text aligned bottom center of the footer
		|Copyrights text|
		And footer consists of Additional Links at left side
		|Our Partners|Legal|Contact Us|Mastercard Global Policy|
		And verify existence of Sign up buttons and Social Share icons at right side of footer
		When Click on Sign up
		Then I should see the form displayed in an overlay
		But Close the Sign up popup
		When Click on Social Share icon
		Then see social share window should open

		
		
#Scenario: Social Sharing link
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Events>>Upcoming"
#		And click on any Article
#		And Click on social sharing link
#		Then Sharing options should be visible and active
#		When Click on Share with Twitter
#		Then selected article should be shared using Twitter option
#		
#
#Scenario: Click to Tweet feature
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Events>>Upcoming"
#		And click on any Article
#		And Click to Tweet
#		Then A prepared tweet text should be as it is shared on twitter without any discrepancy
#		
#		
#Scenario: Download (in articles)
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Events>>Upcoming"
#		And click on any Article
#		And Click on Download Media link
#		Then Media version of articles should be downloaded in Zip file
#		And check downloaded file should not be empty
#		
#
#
#Scenario Outline: Filtering
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Insight & Solutions>>Featured Stories"
#		Then See Advanced Filtering option should be visible
#		When Select any value from "Topics" drop down as "<Topics>"
#		And Click on Filter button
#		Then Filtered Contents displayed having "<Topics>"
#		When Select any value from "Author" drop down as "<Author>"
#		And Click on Filter button
#		Then Filtered Contents displayed having "<Author>"
#		When Select any value from "Region" drop down as "<Region>"
#		And Click on Filter button
#		Then Filtered Contents displayed having "<Region>"
#Examples:
#		|Topics|Author|Region|
#		|TopicName|AuthorName|region|				
#
#
#
#Scenario Outline: Search
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Insight & Solutions>>Featured Stories"
#		Then See advanced Search and Filtering option should be seen and active
#		When enter valid text in Search field "<Search>"
#		And Click on Search button
#		Then Available contents should be searched and display matching "<Search>"
#		When Select any value from "Author" drop down as "<Author>"
#		And Click on Filter button
#		Then Filtered Contents displayed having "<Author>"
#Examples:
#		|Search|Author|
#		|SearchStr|AuthorName|
#			
#			
#			
#Scenario: Stay in touch Form
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Events>>Upcoming"
#		And click on any Article
#		And Click on Get in touch button
#		And do not input anything and click on submit
#		Then See error message is seen
#		When enter an incorrect item on one of the fields and click submit
#		Then See error message is seen
#		When input all items and enter incorrect captcha
#		And Click submit
#		Then Form should not be submitted
#		And See error message is seen
#		When input all items and enter correct captcha
#		|firstname|lastname|testuser@mailinator.com|company|Message|
#		And Click submit
#		Then Form submission should be successful and User details should feed to Pardot
#		
#		
#		
#Scenario: Contact Us (Send a message form)
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Click on Contact Us link from footer
#		And do not input anything and click on submit
#		Then See error message is seen
#		When enter an incorrect item on one of the fields and click submit
#		Then See error message is seen		
#		When input all items correctly
#		|firstname|lastname|testuser@mailinator.com|company|Message|
#		And Click submit
#		Then Form submission should be successful and Email should sent to user
#		
#		
#		
#Scenario: Events
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Events>>Upcoming"
#		Then Center sponsered upcoming events should be displayed
#		And see existence of Even details link against each Events displayed
#		When click on any Event Details link againt the event sponsed/hosted by center
#		Then event page should be displayed showing the details from Pardot
#		When Add to Outlook
#		Then events should be added and visible to personal calender
#		When Add to Google
#		Then events should be added and visible to personal calender
#		When Go back to upcoming Events page
#		Then List of events should be displayed
#		When click on any Event details link againt the event those are Non-Sponsed/Hosted by center
#		Then event details link should be redirected to external website for event details
#		
#		
#
#Scenario: Upcoming Event Details Page
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Events>>Upcoming"
#		Then See upcoming events displayed
#		And Even details link against each Events displayed
#		When click on any Event Details link againt the event sponsed/hosted by center
#		Then event page should be displayed showing the details from Pardot
#		And See below page details should exist on page
#		|event schedule|location|pic of speaker|registration link pointing to external website|
#		
#		
#		
#
#Scenario: Events registration link [Center hosted event]
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Events>>Upcoming"
#		Then Centered/Sponsered upcoming events should be displayed
#		When click on any Event Details link againt the event sponsed/hosted by center
#		Then event page should be displayed showing the details from Pardot
#		When Click on event registration link
#		Then Check User redirected to Pardot registration form
#		And do not input anything and click on submit
#		Then See error message is seen
#		When enter an incorrect item on one of the fields and click submit
#		Then See error message is seen		
#		When Fill all mandatory registration details
#		|firstname|lastname|testuser@mailinator.com|company|Message|
#		And Click submit
#		Then Form submission is successful and data feed into Pardot
#		
#		
#		
#
#Scenario: Events registration link [Non-Center hosted event]
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Events>>Upcoming"
#		Then Non-Center hosted upcoming events should be displayed
#		When click on any Event Details link againt the Non-Center hosted event
#		Then event page should be displayed showing the details from Pardot
#		When Click on event registration link
#		Then Check User redirected to Partner registration form
#		
#		
#		
#Scenario: Previous Events Summary page and Event Details page
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Events>>Previous"
#		Then Summary of all Previous events, dates, location and time should be seen
#		And Check events displayed upto 2 years
#		When click on any Event Details link againt the event sponsed/hosted by center
#		Then event page should be displayed showing the details
#		And See event media should be exist and active
#		And feature of Share, Print and Download media options should be available
#		And See event materials like Agenda and Speakers Biographic available for download
#
#
#
#
#Scenario: Download (in Event details)
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Events>>Upcoming"
#		Then Centered/Sponsered upcoming events should be displayed
#		When click on any Event Details link againt the event sponsed/hosted by center
#		Then Event Agenda and Speakers Biographies link is visible and clickable
#		When Click on Event Agenda link
#		Then event agenda should be downloaded in PDF file
#		When Click on Speakers Biographies link
#		Then Bios should be downloaded in PDF file
#		When Redirect to link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html" on P2
#		When Navigate "Events>>Previous"
#		And click on any Event Details link againt the event sponsed/hosted by center
#		Then event page should be displayed showing the details
#		When Click on Event Agenda link
#		Then event agenda should be downloaded in PDF file
#		When Click on Speakers Biographies link
#		Then Bios should be downloaded in PDF file
#
#
#
#
#Scenario: About section (Our People)
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		And Navigate "About>>Our People"
#		Then See below 3 tabs are visible on Our People page
#		|Our Team|Our Fellows|Our Contributors|
#		But Click on Tabs one by one and see image of person is visible with name of person
#		|Our Team|
#		|Our Fellows|
#		|Our Contributors|
#		When Click on an image of each person
#		Then User should be redirected to details about the person
#
#		
#
#
#Scenario Outline: Multimedia Gallery
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "News & Media>>Multimedia"
#		Then Meda gallery of videos,audio,graphs,images, infographics and slideshow should display
#		When Select any value from "Topics" drop down as "<Topics>"
#		And Click on Filter button
#		Then Filtered Contents displayed having "<Topics>"
#		When Select any value from "Author" drop down as "<Type>"
#		And Click on Filter button
#		Then Filtered Contents displayed having "<Type>"
#		When Select any value from "Region" drop down as "<Region>"
#		And Click on Filter button
#		Then Filtered Contents displayed having "<Region>"
#		But Enter valid text in Search field "<SearchText>"
#		And Click on Search button
#		Then Resulting data "<SearchText>" displayed should be as per Search and applied Filter criteria
#Examples:
#		|Topics|Type|Region|AllTopics|SearchText|
#		|TopicName|TypeString|region|All Topics|SearchText|
#		
#		
#		
#
#Scenario Outline: Newsroom
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "News & Media>>Newsroom"
#		Then Media gallery of videos,audio,graphs,images, infographics and slideshow should display
#		When Select any value from "Topics" drop down as "<Topics>"
#		And Click on Filter button
#		Then Filtered Contents displayed having "<Topics>"
#		When Select any value from "Author" drop down as "<Type>"
#		And Click on Filter button
#		Then Filtered Contents displayed having "<Type>"
#		When Select any value from "Region" drop down as "<Region>"
#		And Click on Filter button
#		Then Filtered Contents displayed having "<Region>"
#		But Enter valid text in Search field "<SearchText>"
#		And Click on Search button
#		Then Resulting data "<SearchText>" displayed should be as per Search and applied Filter criteria
#		When Click on PRESS KIT
#		Then See user is able to download the press kit
#		When Click on SPEAKER REQUESTS 
#		And Fillout all the required details on form and submit
#		Then Form submission should be successful
#Examples:
#		|Topics|Type|Region|AllTopics|SearchText|
#		|TopicName|TypeString|region|All Topics|SearchText|
#
#
#
#
#Scenario: Full Screen Slideshows
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "News & Media>>Multimedia"
#		And Click on photos where slideshow is available
#		Then image should appear in full screen with slider
#		But slide the image using screen slider
#		And imgages should be channged based on each slide and view in full screen
#		
#		
#		
#		
#Scenario Outline: Commenting
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Events>>Upcoming"
#		Then Centered/Sponsered upcoming events should be displayed
#		When click on any Event Details link againt the event sponsed/hosted by center
#		And see Comment functionality is available
#		And Add below text in comment section "<Comment>"
#		But check for login functionality mandatory for commenting
#		And login for commenting
#		Then Check for successful commenting "<Comment>"
#Examples:
#		|Comment|
#		|CommentText|	
#
#
#
#
#Scenario: Recent posts
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Insights & Solutions>>Featured Stories"
#		Then verify display of teasers on webpages and clickable
#		And Teasers should navigate users to read the recent posts added to the blog
#		
#		
#		
#
#Scenario: Stay Informed on article
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Data For Good>>Data Stories"
#		And Select any available article
#		Then See Stay Informed details displayed next to Author's name		
#
#		
#		
#		
#Scenario: Social sharing for callouts in articles
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html"
#		Then Verify all components loads successfully without performance issue
#		When Navigate "Events>>Upcoming"
#		Then Centered/Sponsered upcoming events should be displayed
#		When click on any Event Details link againt the event sponsed/hosted by center
#		And Select a Specific element within the artic 
#		Then Verify Sharing options is visible and active
#		When Click to Share
#		And Share with Twitter
#		Then see selected article should be shared using Twitter option