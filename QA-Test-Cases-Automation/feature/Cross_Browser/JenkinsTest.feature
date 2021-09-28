Feature: P1 General Test Scenarios

Background:
Given Open Chrome browser on P1General
 

@Ford-Sanity
Scenario Outline: P1 Home page
	When Maximize browser and enter link "<HomePageUrl>"
#	Then See all components on home page loaded without performance issue
#	When Input position "<BlankData>" to Mini LAD and Search
#	Then Appropriate validation message displayed
#	When Input position "<InvalidData>" to Mini LAD and Search
#	Then Appropriate validation message displayed
#	When Input position "<PositionText>" to Mini LAD and Search
#	Then Dealer should be searched and shown on the page
#	When Click on "+"
#	Then Dealer section should be expanded and dealers should be displayed
#	When Click on "X"
#	Then Dealer section should be compressed and First dealer should be shown
#	When Click on "+"
#	Then Dealer section should be expanded and dealers should be displayed
	
Examples:
|HomePageUrl|PositionText|BlankData|InvalidData|
|https://wwwqa.brandaplb.ford.com/content/ford/ph/en_ph/home.html|Wellington Street, Antipolo, Rizal||InvalidData|