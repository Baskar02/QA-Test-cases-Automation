Feature: P1 Lincoln General Test scenarios

#This is how background can be used to eliminate duplicate steps

   
#   Scenario: Verify latest JS
#   Given Open Chrome browser on Lincoln
#   When Maximize browser and enter link "https://wwwdev.brandap.ford.com/content/lincoln/cn/zh_cn/home.html/etc/designs/guxfoap/clientlibs/guxfoap.js" on P1 Lincoln
#   Then See the latest version of JS "15.7" on P1 Lincoln
	
   
@P1
Scenario: P1 Verify Compare Three Models - Active Comparision
		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwperf.brandap.ford.com/content/lincoln/cn/zh_cn/home/vehicle/suvs-mkc/specs-and-compare.html" on P1 Lincoln
		When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html" on P1 Lincoln
		Then See all components on Model Compare page on P1 Lincoln
		When Click on Active Compare on P1 Lincoln
		Then See all checkboxes of compare models on P1 Lincoln
		When Select up to "2" items on the list on P1 Lincoln
		And Click on Compare Models button on P1 Lincoln
		Then See "2" components that are selected to compare on P1 Lincoln


@P1
Scenario: P1 Compare Model CN Check - Model Compare
	Given Open Chrome browser on Lincoln
#	When Maximize browser and enter link "https://wwwperf.brandap.ford.com/content/lincoln/cn/zh_cn/home/vehicle/suvs-mkc/specs-and-compare.html" on P1 Lincoln
	When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx/specs-and-compare.html" on P1 Lincoln
	Then See all components on Model Compare page on P1 Lincoln
	When Click on Active Compare on P1 Lincoln
	Then See all checkboxes of compare models on P1 Lincoln
	When Select up to 2 items on the list on P1 Lincoln
	And Click on Compare Models button on P1 Lincoln
	Then See "2" components that are selected to compare on P1 Lincoln
	When Select model of third vehicle on Model Compare on P1 Lincoln
	|MKX|MKX 尊享版| MKC | MKC 尊雅版 |
	And Click on Add Vehicle button on P1 Lincoln
	Then See "3" components that are selected to compare on P1 Lincoln




@P1
Scenario: P1 MKZ Overview Gallery
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/cars-mkz.html" on P1 Lincoln
		Then See all components on home page loaded without performance issue on P1 Lincoln
		When Click on MKZ secondary navigation on P1 INT Lincoln
		Then See MKZ dropdown list on P1 INT Lincoln
		|	车型概览  |	车型鉴赏 |	外观  |	 内饰 | 性能  | 科技  | 安全 | 车型参数与对比  |
		When Click on option in MKZ dropdown list on P1 Lincoln
		|	车型鉴赏	|
		Then See all exterior images on MKZ Gallery on P1 INT Lincoln
		|外观| image 1 | image 2 | image 3 | image 4 | 
		Then See all interior images on MKZ Gallery on P1 INT Lincoln
		|	内饰	| image 1	|	image 2	|	image 3 | image 4 | 
		When Click on exterior image on MKZ Gallery on P1 INT Lincoln
		Then See all components on exterior image on P1 INT Lincoln 
		When Click on Share button on exterior image on P1 INT Lincoln
		Then See all components on Share overlay on P1 INT Lincoln
		And Click on Close on Share overlay on P1 INT Lincoln
		When Click on Close on exterior image on P1 INT Lincoln
		Then See all exterior images on MKZ Gallery on P1 INT Lincoln
		|外观| image 1 | image 2 | image 3 | image 4 | image 5 | image 6 |
		Then See all interior images on MKZ Gallery on P1 INT Lincoln
		|	内饰	| image 1	|	image 2	|	image 3 | image 4 |   
		When Click on interior image on MKZ Gallery on P1 INT Lincoln
		Then See all components on interior image on P1 INT Lincoln
		When Click on Share button on interior image on P1 INT Lincoln
		Then See all components on Share overlay on P1 INT Lincoln
		And Click on Close on Share overlay on P1 INT Lincoln



@P1
Scenario: P1 LAD Search
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/locate-dealer.html" on P1 Lincoln
		Then Input text to Search and click Search
		||
		Then Appropriate validation message displayed
		When Input text to Search and click Search
		|invalidState|
		Then Appropriate validation message displayed
		When Input text to Search and click Search
		|北京市|
		Then See the result in Map on P1 Lincoln
		 



@P1
Scenario: P1 MKC Gallery
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/en_cn/home/vehicle/suvs-mkc/gallery.html" on P1 Lincoln
		Then See all components on MKC Gallery page in P1 INT Lincoln
		| EXTERIOR | INTERIOR | VIDEOS | image01 | image02 | image03 | image04 | image05 | image06 | image08 | interior1 | interior 2 | interior 3 | interior 4 | interior 5 | interior 6 | interior 7 |
		When Click on any image on Exterior section in P1 INT Lincoln
		| image01 |
		Then See all components on Exterior overlay in P1 INT Lincoln  



@P1
Scenario: P1 Press Release
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/about/press-release.html" on P1 Lincoln
		Then See all components on Press Release page on P1 Lincoln
		When Click on Select Article on Press Release page on P1 Lincoln
		And Select Year on Select Article dropdown list on P1 Lincoln
		|	2017 |
		Then See all articles in this year on Press Release page on P1 Lincoln
		| 2017 | “林肯之道”通达常熟，带来个性化豪华驾乘体验 | 北京第三家林肯中心开幕，满足消费者日益增长的个性化豪华需求 | 新款林肯MKZ H混合动力正式上市，高效动力激发无限可能 | 新款林肯MKZ H混合动力与全新林肯Navigator概念车亮相2017上海车展，以“静谧的豪华”定 | /content/lincoln/cn-aut/zh_cn/home/about/press-release/article-2017-04-28.html | /content/lincoln/cn-aut/zh_cn/home/about/press-release/article-2017-04-28-a.html | /content/lincoln/cn-aut/zh_cn/home/about/press-release/article-2017-04-19-a.html | /content/lincoln/cn-aut/zh_cn/home/about/press-release/article-2017-04-19.html |
		When Click an article on Press Release page on P1 Lincoln
		| “林肯之道”通达常熟，带来个性化豪华驾乘体验 |
		Then See all components on Article page loaded without performance issue on P1 Lincoln
		| “林肯之道”通达常熟，带来个性化豪华驾乘体验 | 2017/04/28 | /content/dam/lincoln/cn/l_cn_zh/homepage/experience-lincoln/press-release/1249260216829.jpeg |
		When Click on Share button on Press Release page on P1 Lincoln
		Then See all components on Share overlay on Press Release page on P1 INT Lincoln
		When Click on Close button on Share overlay on P1 Lincoln 
		Then See all components on Article page loaded without performance issue on P1 Lincoln
		| “林肯之道”通达常熟，带来个性化豪华驾乘体验 | 2017/04/28 | /content/dam/lincoln/cn/l_cn_zh/homepage/experience-lincoln/press-release/1249260216829.jpeg |


		

@P1
Scenario: P1 Check Nameplate Overview Page verify Galley Trigger, 360 Colorizer,check Share feature on images
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/cars-mkz.html" on P1 Lincoln
		Then See all components on home page loaded without performance issue on P1 INT Lincoln
		When Click on interior 360 in MKZ page on P1 Lincoln
		Then See interior 360 image in MKZ page on P1 Lincoln
		When Click on brown image on interior 360 in MKZ page on P1 Lincoln
		Then See brown image on interior 360 in MKZ page on P1 Lincoln
		When Click on exterior 360 in MKZ page on P1 Lincoln
		Then See exterior 360 image in MKZ page on P1 Lincoln
		When Click on midnight blue on exterior 360 in MKZ page on P1 Lincoln
		Then See midnight blue image on exterior 360 in MKZ page on P1 Lincoln
		When Click on Video in MKZ page on P1 Lincoln
		Then See all components in Video overlay on P1 Lincoln
		When Play video in Video overlay on P1 Lincoln
		Then Verify do not see Video Play button on P1 Lincoln
		When Click on Share button on exterior image on P1 PERF Lincoln
		Then See all components on Share overlay on P1 Lincoln
		And Click on Close on Share overlay on P1 INT Lincoln



@P1
Scenario: P1 Verify Comparision of Three Models Using Configuration Comparison button	
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/cars-mkz.html" on P1 Lincoln
		Then See all components on home page loaded without performance issue on P1 INT Lincoln
		When Click on Compare Model on P1 INT Lincoln
#		Then See all components on Compare Model page on P1 INT Lincoln	
		When Click on Configuration Comparison button on P1 INT Lincoln
		And Select up to "3" items on the list on P1 Lincoln
		And Click on Continue Comparison button on P1 INT Lincoln	
		Then See all components in Compare page on P1 Lincoln
		| 3 辆座驾已选择 | MKZ 尊悦版 | MKZ 尊享版 | MKZ 尊雅版 | 预约试驾 | 选择其他车型 | 全部展开 | 全部收起 |


	
#### COMPLETED SC for INT
@P1 
Scenario: P1 Compare Model EN Scenario1 - Click any model image to trigger comparator for EN and SK on INT/DEV
	Given Open Chrome browser on Lincoln
	When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/sk-aut/ko_sk/home/vehicle/continental/compare-models.html" on P1 Lincoln
	Then See all components on Model Compare page
	|Continental 3.0L Presidential|Continental 3.0L Reserve + Luxury Pack|Continental 2.7L Reserve + Luxury Pack|Continental 2.7L Reserve|
	When Click any model image to trigger comparator
	|Continental 3.0L Presidential|
	Then Populate selected model specs in MC page
	|Continental 3.0L Presidential||
	And Can only add vehicle from models under this selected NP
	|Continental 3.0L Presidential|Continental 3.0L Reserve + Luxury Pack|Continental 2.7L Reserve + Luxury Pack|Continental 2.7L Reserve|


#### COMPLETED SC for INT 
@P1
Scenario: P1 Compare Model EN Scenario2 - Model Compare in INT/Dev environment
	Given Open Chrome browser on Lincoln
	When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/sk-aut/ko_sk/home/vehicle/mkc/compare-models.html" on P1 Lincoln
	Then See all components on Model Compare page
	|MKC Select 200A AWD|MKC Reserve 300A AWD|
	When Click on Active Compare on P1 Lincoln
	When Select up to 2 items on the list on P1 Lincoln
	And Click on Compare Models button on P1 Lincoln
	Then See Add Vehicle button is not visible
	And Populate selected model specs in MC page
	|MKC Select 200A AWD|MKC Reserve 300A AWD|


####### COMPLETED SC for QA/PERF
##@P1
##Scenario: P1 Compare Model EN Scenario1 - Click any model image to trigger comparator for EN and SK on QA/PERF
##	Given Open Chrome browser on Lincoln
##	When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/sk/ko_sk/home/vehicles/continental/compare-models.html" on P1 Lincoln
##	Then See all components on Model Compare page
##	|Continental 3.0L Presidential|Continental 3.0L Reserve + Luxury Pack|Continental 2.7L Reserve + Luxury Pack|Continental 2.7L Reserve|
##	When Click any model image to trigger comparator
##	|Continental 3.0L Presidential|
##	Then Populate selected model specs in MC page
##	|Continental 3.0L Presidential||
##	And Can only add vehicle from models under this selected NP
##	|Continental 3.0L Presidential|Continental 3.0L Reserve + Luxury Pack|Continental 2.7L Reserve + Luxury Pack|Continental 2.7L Reserve|
##	
##@P1
######### COMPLETED SC for QA/PERF
##Scenario: P1 Compare Model EN Scenario2 - Model Compare in Perf/QA environment
##	Given Open Chrome browser on Lincoln
##	When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/sk-aut/ko_sk/home/vehicles/continental/compare-models.html" on P1 Lincoln
##	Then See all components on Model Compare page
##	|Continental 3.0L Presidential|Continental 3.0L Reserve + Luxury Pack|Continental 2.7L Reserve + Luxury Pack|Continental 2.7L Reserve|
##	When Click on Active Compare on P1 Lincoln
##	When Select up to 2 items on the list on P1 Lincoln
##	And Click on Compare Models button on P1 Lincoln
##	And Click on Add Vehicle button
##	And Select model of third vehicle on Model Compare
##	|Continental 2.7L Reserve|
##	And Click on Confirm button
##	Then Populate selected model specs in MC page
##	|Continental 2.7L Reserve||


@P1
Scenario: P1 Mini LAD Search at CN market
	Given Open Chrome browser on Lincoln
	When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html" on P1 Lincoln
	And Input text to Search and click Search
	||
	Then Appropriate validation message displayed
	When Input text to Search and click Search
	|invalidState|
	Then Appropriate validation message displayed
	When Input text to Search and click Search
	|北京奥吉通林肯中心|
	Then Dealer should be searched and shown on the page
	When Click on "+"
	Then Dealer section should be expanded and dealers should be displayed
	When Click on "X"
	Then Dealer section should be compressed and First dealer should be shown
	When Click on "+"
	Then Dealer section should be expanded and dealers should be displayed



@P1
Scenario: P1 Mini LAD Search at SK market
	Given Open Chrome browser on Lincoln
	When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/sk-aut/ko_sk/home.html" on P1 Lincoln
	And Input text to Search and click Search
	||
	Then Appropriate validation message displayed
	When Input text to Search and click Search
	|invalidState|
	Then Appropriate validation message displayed
	When Input text to Search and click Search
	|Korea Norte, Mexico|
	Then Dealer should be searched and shown on the page
	When Click on "+"
	Then Dealer section should be expanded and dealers should be displayed
#	And Check for an option of 5 dealers display based on the nearest location criteria 
	When Click on "X"
	Then Dealer section should be compressed and First dealer should be shown
	When Click on "+"
	Then Dealer section should be expanded and dealers should be displayed



@P1 
Scenario: P1 Verify functionality of Test Drive form
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P1 Lincoln
		Then See all components on home page loaded without performance issue on P1 Lincoln
		When Click on Arrange a test drive on Main Navigation on P1 Lincoln
		Then See Test Drive form on P1 Lincoln
		When Fill in Test Drive Form on P1 Lincoln
		|先生|Son|Dang|sondn@smartosc.com|13312345678|北京|北京|北京福瑞林肯中心|MKZ|3个月内|ABCDE|
		Then See Thank you overlay on P1 Lincoln



@P1 
Scenario: P1 Verify functionality of Keep me informed form
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P1 Lincoln
		Then See all components on home page loaded without performance issue on P1 Lincoln
		When Click on Keep me informed at the footer on P1 Lincoln
		Then See all components on Keep me informed overlay on P1 Lincoln
		|	姓名	| 电子邮箱 | 手机号码 | 选择偏好林肯车系 | 您是否愿意被经销商联系 | lastName | firstName | email | mobile | captchaValue | 林肯 MKZ | 林肯 MKC | 林肯 MKX | 林肯 NAVIGATOR | 林肯 CONTINENTAL | 请输入验证码|
		When Fill in Keep me informed on P1 Lincoln
		| Son | Dang | sondn@smartosc.com | 12212345678 | ABCD |
		Then See Thank you overlay on P1 Lincoln
   


@P1
Scenario: P1 Verify functionality of Request a brochure
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P1 Lincoln
		Then See all components on home page loaded without performance issue on P1 Lincoln
		When Click on Request a brochure at the footer on P1 Lincoln
		Then See all components on Request a brochure overlay on P1 Lincoln 
		|MKX|MKC|林肯MKX|林肯MKZ|
		When Select a vehicle from Vehicle list on Request a brochure overlay on P1 Lincoln
		|MKX| 林肯MKC |
		And Click on download button on Request a brochure overlay on P1 Lincoln
		And See brochure downloaded successfully
		|MKX|MKC|
		And Click on mailto button on Request a brochure overlay on P1 Lincoln
		Then See all components on Request a brochure mailto on P1 Lincoln
		| 请选择称呼	| 姓名 | 电子邮箱 | 手机号码 | 省 | 市 | 地址 | 邮编 | 请选择偏好林肯车系 | 何时计划购买 | 您是否愿意被经销商联系 | 您是否愿意被经销商联系 | title | state | nameplate | mktPurchaseTime | lastName | firstName | email | mobile | addressLine1 | postCode | captchaValue |
		When Fill in Request a brochure on P1 Lincoln 
		| 先生 | Son | Dang | 080817174330@mailinator.com | 12212345678 | 上海 | 上海 | DenLu | 123456 | MKX | 3个月内 | ABCDF |
		Then See Thank you overlay on P1 Lincoln


@P1
Scenario: P1 Verify functionality of Request a Quote Form
		Given Open Chrome browser on Lincoln
		When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P1 Lincoln
		Then See all components on home page loaded without performance issue on P1 Lincoln
		When Click on Request a Quote at the footer on P1 Lincoln
		And Fill in Request a Quote form on P1 Lincoln
		|	先生	|	Son	|	Dang	|	son.dang@vmlqais.com	|	13345678902	|	北京	|	北京	| 北京福瑞林肯中心 |	MKZ	| 3个月内	|	ABCDEF	|
		Then See Thank you overlay on P1 Lincoln

		
		

########################## PERF ENVT

## COMPLETED
#Scenario: Key Page Function Check - Mini LAD Search in PERF environment
#	Given Open Chrome browser on Lincoln
#	When Maximize browser and enter link "https://wwwdev.brandap.ford.com/content/lincoln/cn/zh_cn/home.html" on P1 Lincoln
#	And Input text to Search and click Search
#	||
#	Then Appropriate validation message displayed
#	When Input text to Search and click Search
#	|invalidState|
#	Then Appropriate validation message displayed
#	When Input text to Search and click Search
#	|北京奥吉通林肯中心|
#	Then Dealer should be searched and shown on the page
#	When Click on "+"
#	Then Dealer section should be expanded and dealers should be displayed
#	When Click on "X"
#	Then Dealer section should be compressed and First dealer should be shown
#	When Click on "+"
#	Then Dealer section should be expanded and dealers should be displayed
#
#
### COMPLETED PERF ENVT
#Scenario: Key Page Function Check - LAD Search in PERF environment
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwdev.brandap.ford.com/content/lincoln/cn/zh_cn/home/locate-dealer.html" on P1 Lincoln
#		Then Input text to Search and click Search
#		||
#		Then Appropriate validation message displayed
#		When Input text to Search and click Search
#		|invalidState|
#		Then Appropriate validation message displayed
#		When Input text to Search and click Search
#		|北京市|
#		Then See the result in Map on P1 Lincoln
#
### COMPLETED
#Scenario: Key Page Function Check - Compare Three Models in INT environment	
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwperf.brandap.ford.com/content/lincoln/cn/zh_cn/home/vehicle/suvs-mkc/specs-and-compare.html" on P1 Lincoln
#		Then See all components on Model Compare page on P1 Lincoln
#		When Click on Active Compare on P1 Lincoln
#		Then See all checkboxes of compare models on P1 Lincoln
#		When Select up to "3" items on the list on P1 Lincoln
#		And Click on Compare Models button on P1 Lincoln
#		Then See "3" components that are selected to compare on P1 Lincoln
#
#
###COMPLETED
#Scenario: Key Page Function Check - Model Compare in INT environment
#	Given Open Chrome browser on Lincoln
#	When Maximize browser and enter link "https://wwwperf.brandap.ford.com/content/lincoln/cn/zh_cn/home/vehicle/suvs-mkc/specs-and-compare.html" on P1 Lincoln
#	Then See all components on Model Compare page on P1 Lincoln
#	When Click on Active Compare on P1 Lincoln
#	Then See all checkboxes of compare models on P1 Lincoln
#	When Select up to 2 items on the list on P1 Lincoln
#	And Click on Compare Models button on P1 Lincoln
#	Then See "2" components that are selected to compare on P1 Lincoln
#	When Select model of third vehicle on Model Compare on P1 Lincoln
#	|MKX|MKX 尊享版| MKC | MKC 尊雅版 |
#	And Click on Add Vehicle button on P1 Lincoln
#	Then See "3" components that are selected to compare on P1 Lincoln
#
#
#@P1
#Scenario: P1 Verify Compare Three Models - Active Comparision
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwperf.brandap.ford.com/content/lincoln/cn/zh_cn/home/vehicle/suvs-mkc/specs-and-compare.html" on P1 Lincoln
#		Then See all components on Model Compare page on P1 Lincoln
#		When Click on Active Compare on P1 Lincoln
#		Then See all checkboxes of compare models on P1 Lincoln
#		When Select up to "2" items on the list on P1 Lincoln
#		And Click on Compare Models button on P1 Lincoln
#		Then See "2" components that are selected to compare on P1 Lincoln
#
#
#@P1
#Scenario: P1 MKC Gallery
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwperf.brandap.ford.com/content/lincoln/cn/zh_cn/home/vehicle/suvs-mkc/gallery.html" on P1 Lincoln
#		And Click on any image on Exterior section in P1 INT Lincoln
#		| image01 |
#		Then See all components on Exterior overlay in P1 INT Lincoln
#		
#		
#@P1
#Scenario: P1 Press Release
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwperf.brandap.ford.com/content/lincoln/cn/zh_cn/home/about/press-release.html" on P1 Lincoln
#		And Click on Select Article on Press Release page on P1 Lincoln
#		And Select Year on Select Article dropdown list on P1 Lincoln
#		|	2017 |
#		Then See all articles in this year on Press Release page on P1 Lincoln
#		| 2017 |寻·造藏地初心 林肯全系豪华SUV北纬30°试驾体验之旅 | 
#		When Click an article on Press Release page on P1 Lincoln
#		|寻·造藏地初心 林肯全系豪华SUV北纬30°试驾体验之旅 |
#		Then See all components on Article page loaded without performance issue on P1 Lincoln
#		|寻·造藏地初心 林肯全系豪华SUV北纬30°试驾体验之旅 | 2017/08/28 | /content/dam/lincoln/cn/l_cn_zh/homepage/experience-lincoln/press-release/1249260216829.jpeg |
#		When Click on Share button on Press Release page on P1 Lincoln
#		Then See all components on Share overlay on Press Release page on P1 INT Lincoln
#		When Click on Close button on Share overlay on P1 Lincoln 
#		Then See all components on Article page loaded without performance issue on P1 Lincoln
#		|寻·造藏地初心 林肯全系豪华SUV北纬30°试驾体验之旅 | 2017/08/28 | /content/dam/lincoln/cn/l_cn_zh/homepage/experience-lincoln/press-release/1249260216829.jpeg |
#
#
#@P1
#Scenario: P1 MKZ Overview Gallery
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwperf.brandap.ford.com/content/lincoln/cn/zh_cn/home/vehicle/cars-mkz.html" on P1 Lincoln
#		And Click on MKZ secondary navigation on P1 INT Lincoln
#		And Click on option in MKZ dropdown list on P1 Lincoln
#		|	车型鉴赏	|
#		Then See all exterior images on MKZ Gallery on P1 INT Lincoln
#		|外观| image 1 | image 2 | image 3 | image 4 | 
#		Then See all interior images on MKZ Gallery on P1 INT Lincoln
#		|	内饰	| image 1	|	image 2	|	image 3 | image 4 | 
#		When Click on exterior image on MKZ Gallery on P1 INT Lincoln
#		Then See all components on exterior image on P1 INT Lincoln 
#		When Click on Share button on exterior image on P1 INT Lincoln
#		Then See all components on Share overlay on P1 INT Lincoln
#		And Click on Close on Share overlay on P1 INT Lincoln
#		When Click on Close on exterior image on P1 INT Lincoln
#		Then See all exterior images on MKZ Gallery on P1 INT Lincoln
#		|外观| image 1 | image 2 | image 3 | image 4 | image 5 | image 6 |
#		Then See all interior images on MKZ Gallery on P1 INT Lincoln
#		|	内饰	| image 1	|	image 2	|	image 3 | image 4 |   
#		When Click on interior image on MKZ Gallery on P1 INT Lincoln
#		Then See all components on interior image on P1 INT Lincoln
#		When Click on Share button on interior image on P1 INT Lincoln
#		Then See all components on Share overlay on P1 INT Lincoln
#		And Click on Close on Share overlay on P1 INT Lincoln
#
#
#
######## COMPLETED SC for QA/PERF
#@P1
#Scenario: P1 Compare Model EN Scenario1 - Click any model image to trigger comparator for EN and SK on QA/PERF
#	Given Open Chrome browser on Lincoln
#	When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/sk/ko_sk/home/vehicles/continental/compare-models.html" on P1 Lincoln
#	Then See all components on Model Compare page
#	|Continental 3.0L Presidential|Continental 3.0L Reserve + Luxury Pack|Continental 2.7L Reserve + Luxury Pack|Continental 2.7L Reserve|
#	When Click any model image to trigger comparator
#	|Continental 3.0L Presidential|
#	Then Populate selected model specs in MC page
#	|Continental 3.0L Presidential||
#	And Can only add vehicle from models under this selected NP
#	|Continental 3.0L Presidential|Continental 3.0L Reserve + Luxury Pack|Continental 2.7L Reserve + Luxury Pack|Continental 2.7L Reserve|
#	
#@P1
######## COMPLETED SC for QA/PERF
#Scenario: P1 Compare Model EN Scenario2 - Model Compare in Perf/QA environment
#	Given Open Chrome browser on Lincoln
#	When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/sk/ko_sk/home/vehicles/continental/compare-models.html" on P1 Lincoln
#	And Click on Active Compare on P1 Lincoln
#	Then Select up to 2 items on the list on P1 Lincoln
#	When Click on Compare Models button on P1 Lincoln
#	And Click on Add Vehicle button
#	And Select model of third vehicle on Model Compare
#	|Continental 2.7L Reserve|
#	And Click on Confirm button	
#	Then Populate selected model specs in MC page
#	|Continental 2.7L Reserve||
#
#
### ERROR COMPLETED
#Scenario: Key Page Function Check - Test Drive in INT environment
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwdev.brandap.ford.com/content/lincoln/cn/zh_cn/home.html" on P1 Lincoln
#		Then See all components on home page loaded without performance issue on P1 Lincoln
#		When Click on Arrange a test drive on Main Navigation on P1 Lincoln
#		When Fill in Test Drive Form on P1 Lincoln
#		|	先生 |	Son	|	Dang	|	sondn@smartosc.com | 13312345678 | 北京市 | 北京市 | 北京福瑞林肯中心  | 林肯MKZ | 3个月内 | ABCDE |
#		Then See Thank you overlay on P1 Lincoln
#		When Click on Close on Thank you overlay on P1 Lincoln
#
#
#
### COMPLETED 
#Scenario: Key Page Function Check - Keep me informed in INT environment
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwdev.brandap.ford.com/content/lincoln/cn/zh_cn/home.html" on P1 Lincoln
#		Then See all components on home page loaded without performance issue on P1 Lincoln
#		When Click on Keep me informed at the footer on P1 Lincoln
#		Then See all components on Keep me informed overlay on P1 Lincoln
#		|	姓名	| 电子邮箱 | 手机号码 | 选择偏好林肯车系 | 您是否愿意被经销商联系 | lastName | firstName | email | mobile | captchaValue | 林肯 MKZ | 林肯 MKC | 林肯 MKX | 林肯 NAVIGATOR | 林肯 CONTINENTAL | 请输入验证码|
#		When Fill in Keep me informed on P1 Lincoln
#		| Son | Dang | sondn@smartosc.com | 12212345678 | ABCD |
#		Then See Thank you overlay on P1 Lincoln
#   
#
#
### COMPLETED
#Scenario: Key Page Function Check - Request a brochure
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwdev.brandap.ford.com/content/lincoln/cn/zh_cn/home.html" on P1 Lincoln
#		Then See all components on home page loaded without performance issue on P1 Lincoln
#		When Click on Request a brochure at the footer on P1 Lincoln
#		Then See all components on Request a brochure overlay on P1 Lincoln 
#		| 林肯MKC | 林肯MKX | 林肯MKZ | 林肯NAVIGATOR |
#		When Select a vehicle from Vehicle list on Request a brochure overlay on P1 Lincoln
#		| 林肯MKC |
#		And Click on download button on Request a brochure overlay on P1 Lincoln
#		And Click on mailto button on Request a brochure overlay on P1 Lincoln
#		And Fill in Request a brochure on P1 Lincoln 
#		| 先生 | Son | Dang | sondn@smartosc.com | 1221234567890 | 上海市 | 上海市 | DenLu | 12345 | 林肯MKX | 3个月内 | ABCDF |
#		Then See Thank you overlay on P1 Lincoln   
#
#
#
#
#
## COMPLETED
#Scenario: Key Page Function Check - Request a Quote in INT environment
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwdev.brandap.ford.com/content/lincoln/cn/zh_cn/home.html" on P1 Lincoln
#		Then See all components on home page loaded without performance issue on P1 Lincoln
#		When Click on Request a Quote at the footer on P1 Lincoln
#		And Fill in Request a Quote form on P1 Lincoln
#		|	先生	|	Son	|	Dang	|	son.dang@vmlqais.com	|	13345678902	|	北京	|	北京	| 北京福瑞林肯中心 |	MKZ	| 3个月内	|	ABCDEF	|
#		Then See Thank you overlay on P1 Linc		

## COMPLETED
#Scenario: Key Page Function Check - Overview
#		Given Open Chrome browser on Lincoln
#		When Maximize browser and enter link "https://wwwdev.brandap.ford.com/content/lincoln/cn/zh_cn/home/vehicle/cars-mkz.html" on P1 Lincoln
#		Then See all components on home page loaded without performance issue on P1 INT Lincoln
#		When Click on interior 360 in MKZ page on P1 Lincoln
#		Then See interior 360 image in MKZ page on P1 Lincoln
#		When Click on brown image on interior 360 in MKZ page on P1 Lincoln
#		Then See brown image on interior 360 in MKZ page on P1 Lincoln
#		When Click on exterior 360 in MKZ page on P1 Lincoln
#		Then See exterior 360 image in MKZ page on P1 Lincoln
#		When Click on midnight blue on exterior 360 in MKZ page on P1 Lincoln
#		Then See midnight blue image on exterior 360 in MKZ page on P1 Lincoln
#		When Click on Video in MKZ page on P1 Lincoln
#		Then See all components in Video overlay on P1 Lincoln
#		When Play video in Video overlay on P1 Lincoln
#		Then Verify do not see Video Play button on P1 Lincoln
#		When Click on Share button on exterior image on P1 PERF Lincoln
#		Then See all components on Share overlay on P1 Lincoln
#		And Click on Close on Share overlay on P1 INT Lincoln



