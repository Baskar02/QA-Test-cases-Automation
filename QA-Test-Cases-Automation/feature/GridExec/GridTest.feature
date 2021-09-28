Feature: FireFox Browser Test Scenarios


Scenario Outline: FireFox Browser Approach
Given Launch "<BrowserName>" browser on Grid and enter link "<URL>"
When Veify title of the page
Then Enter username "<UserName>" password "<Password>"
And Click on login and logout button
And Verify title of the successful login
And Close the browser


Examples:
|BrowserName|URL|UserName|Password|
|Chrome|https://jira.uhub.biz/login.jsp|suresh.nagarajan@vmlyr.com|M@y-2021|

 
