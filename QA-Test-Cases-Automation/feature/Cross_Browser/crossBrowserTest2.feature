Feature: Chrome Browser Test Scenarios


Scenario Outline: Chrome Browser Approach
Given Launch "<BrowserName>" browser and enter link "<URL>"
When Veify title of the page
Then Enter username "<UserName>" password "<Password>"
And Click on login button
And Verify title of the successful login
And Close the browser


Examples:
|BrowserName|URL|UserName|Password|
|Chrome|https://jira.uhub.biz/login.jsp|suresh.nagarajan@vmlyr.com|0ct-2020|


#Scenario Outline: P1 Home page
#	Given Launch "<BrowserName>" browser and enter link "<HomePageUrl>"
#	Then See all components on home page loaded without performance issue
#	When Input position "<PositionText>" to Mini LAD and Search
#	And Close the browser
#	
#	
#Examples:
#|BrowserName|HomePageUrl|PositionText|SearchedPosition|BlankData|InvalidData|
#|Chrome|https://wwwqa.brandaplb.ford.com/content/ford/ph/en_ph/home.html|Wellington Street, Antipolo, Rizal|Chennai Ford||InvalidData|
