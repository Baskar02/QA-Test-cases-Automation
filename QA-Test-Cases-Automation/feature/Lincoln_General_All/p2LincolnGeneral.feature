Feature: P2 Lincoln General Test scenarios- Owner, B&P and Credit


#@P2-NonJenkinsSc
Scenario: P2 Owner registration using Email/Phone,Email Id is unique across Ford and Lincoln,All type of phone values are unique across Lincoln
Given Open Chrome browser on P2 Lincoln	
And Generate unique string 
When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn-aut/zh_cn/home.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P2 Lincoln
And Click on Login link
And Enter User and Password
|AUTOTEST@mailinator.com|Test1212|
And Click on Submit button
Then See validation error message is displaying
|incorrect|||
And Click on Registration link
Then See Registration form is opened
When Enter invalid values in form fields
|先生|@!#$|%$#^|*&^^|Tes|345|212|
And Click on Submit button
Then verify validation message displayed for invalid values
When Enter existing users email id in the form field
|先生|FIRSTNAME|LASTNAME|3007172152132@mailinator.com|Test12345|Test12345||
And Select agreement check box
And Click on Submit button
Then verify validation message displayed
|user|
When Enter existing users email id in the form field
|先生|FIRSTNAME|LASTNAME|130917135450@mailinator.com|Test12345|Test12345||
And Click on Submit button
Then verify validation message displayed
|user|
When Enter valid values
And Enter Captcha
And Click on Submit button
Then See Thanks page overlay is displaying
And Click on Login link
And Enter User and Password
||Test12345|
And Click on Submit button
Then See validation error message is displaying
|activated|||
When Redirect to link "https://www.mailinator.com/v2/inbox.jsp?zone=public&query=REPLACETOUNIQUE" on P2 
And Click on Activation Email on MailInator page on P2 
When Click on Activate My Account link in activation mail
Then verify Registration Success message
When Redirect to link "https://wwwint.brandap.lincoln.com/content/lincoln/cn-aut/zh_cn/home.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P2
And Click on Login link
And Enter User and Password
||Test12345|
And Click on Submit button
Then User login should be successful and overview page should be displayed
|LASTNAME|先生|
And Click on Logout link on top



@P2
Scenario: P2 Owner Forgot Password
Given Open Chrome browser on P2 Lincoln	
When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
And Click on Login link
And Click on Forgot Password link
Then see forgot password overlay opened
When enter invalid email account
|AUTOTEST@mailinator.com|
And Click on Submit
Then see validation message displayed
|user doesn't|
When enter valid email id
And Click on Submit
Then See Thanks page overlay is displaying
When Redirect to link "https://www.mailinator.com/v2/inbox.jsp?zone=public&query=REPLACETOUNIQUE" on P2 
And Click on Activation Email on MailInator page on P2 
And Click on reset password link
Then See reset password link overlay opened
When Enter password and confirm password
|Test123456|Test123456|
And Click on submit
Then See Thanks page overlay is displaying
When Redirect to link "https://wwwint.brandap.lincoln.com/content/lincoln/cn-aut/zh_cn/home.html" on P2
And Click on Login link
And Enter User and Password
||Test12345|
And Click on Submit button
Then See validation error message is displaying
|incorrect|||
And Enter User and Password
||Test123456|
And Click on Submit button
Then User login should be successful and overview page should be displayed
|LASTNAME|先生|



@P2
#@P2-NonJenkinsSc
Scenario: P2 Owner Validate Overview page and Profile page field values,While Editing Profile - other than username, all fields can be duplicated with other lincoln user
Given Open Chrome browser on P2 Lincoln	
When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
And Click on Login link
And Enter User and Password
||Test123456|
And Click on Submit button
Then User login should be successful and overview page should be displayed
|LASTNAME|先生|
And verify KBA section links are navigating to correct page
#And verify Secondary navigation is routing to correct page  #Need to discuss - Arpit
When Click on Username beside welcome message
|LASTNAME|先生|
Then see user profile page is displayed
And verify all profile field values displayed correctly
|//div[@model='username']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='REPLACEEMAIL']|//div[@model='title']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='先生']|//div[@model='firstName']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='FIRSTNAME']|//div[@model='lastName']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='LASTNAME']|//div[@model='email']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='REPLACEEMAIL']|//div[@model='state']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='']|//div[@model='city']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='']|//div[@model='street']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='']|//div[@model='address']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='']|//div[@model='mobileNumber']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='REPLACEMOBILE']|//div[@model='postCode']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='']|
When update below profile field value
|title|太太|
And update below profile field value
|firstName|FIRSTNAME2|
And update below profile field value
|lastName|LASTNAME2|
And update below profile field value
|email|TempEmail@mailinator.com|
And update below profile field value
|email||
And Scroll down profile page
And update below profile field value
|state|上海|
And update below profile field value
|city|上海|
And update below profile field value
|street|street1|
And update below profile field value
|address|address|
And update below profile field value
|mobileNumber|32210171437|
And update below profile field value
|postCode|123456|
And Click on Logout link on top
And Click on Login link
And Enter User and Password
||Test123456|
And Click on Submit button
Then Click on Username beside welcome message
|LASTNAME2|太太|
And verify all profile field values displayed correctly
|//div[@model='username']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='REPLACEEMAIL']|//div[@model='title']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='太太']|//div[@model='firstName']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='FIRSTNAME2']|//div[@model='lastName']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='LASTNAME2']|//div[@model='email']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='REPLACEEMAIL']|//div[@model='state']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='上海']|//div[@model='city']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='上海']|//div[@model='street']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='street1']|//div[@model='address']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='address']|//div[@model='mobileNumber']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='32210171437']|//div[@model='postCode']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='123456']|



@P2
Scenario: P2 Owner Validate Overview page and Profile field values,Editing Profile - other than username, all fields can be duplicated with other lincoln user
Given Open Chrome browser on P2 Lincoln	
When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
And Click on Login link
And Enter User and Password
||Test123456|
And Click on Submit button
Then User login should be successful and overview page should be displayed
|LASTNAME2|太太|
And verify KBA section links are navigating to correct page
#And verify Secondary navigation is routing to correct page
When Click on Username beside welcome message
|LASTNAME2|太太|
Then see user profile page is displayed
When update below profile field value
|title|太太|
And update below profile field value
|firstName|FIRSTNAME2|
And Click on Logout link on top
And Click on Login link
And Enter User and Password
||Test123456|
And Click on Submit button
Then Click on Username beside welcome message
|LASTNAME2|太太|
And verify all profile field values displayed correctly
|//div[@model='username']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='REPLACEEMAIL']|//div[@model='title']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='太太']|//div[@model='firstName']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='FIRSTNAME2']|||||||||




@P2
Scenario: P2 Owner Schedule an appointment
Given Open Chrome browser on P2 Lincoln	
When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn-aut/zh_cn/home.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P2 Lincoln
And Click on Login link
And Enter User and Password
||Test123456|
And Click on Submit button
Then From secondary navigation click on Schedule an Appointment link
And See Schedule an appointment form opened
When Enter correct values and submit the form
|先生|FirstName|LastName|041017123115@mailinator.com|32210171437|上海|上海|MKX|1|22|MH43BE19|1|1|
Then See Form submission is successful
##And Redirect to link "https://www.mailinator.com/v2/inbox.jsp?zone=public&query=041017123115" on P2 
##And Click on Activation Email on MailInator page on P2 



@P2
Scenario: P2 Owner Login with Lincoln Mobile userid and verify the profile, Ford user can not login to Lincoln owner, Lincoln user can not login to Ford owner
Given Open Chrome browser on P2 Lincoln	
When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
And Click on Login link
And Enter User and Password
|130917135450|Test12345|
And Click on Submit button
Then See validation error message is displaying
|USER_DO_NOT_BELONG_TO_ACCOUNT|||
When Redirect to link "https://wwwint.brandap.ford.com/content/guxap-uat-demo/cn1/zh_cn/home.html#overlay/content/guxap-uat-demo/cn1/zh_cn/site-wide-content/overlays/form-overlays/login.html" on P2
And See the Login overlay on P2
When Enter credentials to login on P2
||Test123456|
And Click on Login button on P2
Then See validation error message is displaying
|登錄失敗|请输入正确的密码||
When Redirect to link "https://wwwint.brandap.lincoln.com/content/lincoln/cn-aut/zh_cn/home.html" on P2
And Click on Login link
And Enter User and Password
|13166189794|a1234567|
And Click on Submit button
And From secondary navigation click on my profile link
Then see user profile page is displayed
And update below profile field value
|email|testMobileUserid@mailinator.com|
And Click on Logout link on top
And Click on Login link
And Enter User and Password
|13166189794|a1234567|
And Click on Submit button
And From secondary navigation click on my profile link
Then see user profile page is displayed
And verify all profile field values displayed correctly
|||||//div[@model='email']//div[contains(@class,'owner-profile')]/div[contains(@class,'default-fields') and text()='testMobileUserid@mailinator.com']|||||||


#Commented due to known issue - Arpit
#@P2-NonJenkinsSc
#Scenario: P2 Owner My Build Configuration from B&P page When less than 3 vehicles already added in user profile,When 3 vehicles are added and user wants to add fourth vehicle 
#Given Open Chrome browser on P2 Lincoln	
#When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
#And Click on Build and Price
##|配置林肯|下一步|查看及保存| -Arpit
#|配置林肯|||
#And Select Vehicle "Lincoln_BP"
##Added Feature for clicking on Summary Page Link
#And Click on Build and Price
#|||查看及保存|
##Added Feature for clicking on Summary Page Link
#And see summary page
##|MKX 尊享版|MKX 尊雅版|
#|MKZ 尊悦版|
#And Click on Save vehicle link
#Then User should be directed to sign in overlay
#And Enter User and Password
#||Test123456|
#And Click on Submit button
#Then verify an overlay displayed confirming for vehicle adding
#And Verify the vehicle name format
#|4|
#When Provide vehicle name
#|TestModel1|
#And click on confirm save
#Then Vehicle should be added and shown at Model Display section
#|TestModel1|
#When Click on Build and Price
#|配置林肯|下一步|查看及保存|
#And see summary page
##|MKX 尊享版|MKX 尊雅版|
#|MKZ 尊悦版|
#And Click on Save vehicle link
#Then verify an overlay displayed confirming for vehicle adding
#When Provide vehicle name
#|TestModel2|
#And click on confirm save
#Then Vehicle should be added and shown at Model Display section
#|TestModel2|
#When Click on Build and Price
#|配置林肯|下一步|查看及保存|
#And see summary page
##|MKX 尊享版|MKX 尊雅版|
#|MKZ 尊悦版|
#And Click on Save vehicle link
#Then verify an overlay displayed confirming for vehicle adding
#When Provide vehicle name
#|TestModel3|
#And click on confirm save
#Then Vehicle should be added and shown at Model Display section
#|TestModel3|
#When Click on Build and Price
#|配置林肯|下一步|查看及保存|
#And see summary page
##|MKX 尊享版|MKX 尊雅版|
#|MKZ 尊悦版|
#And Click on Save vehicle link
#Then user should be prompted with an overlay to overrride the saved vehicle
#|TestModel1|TestModel2|TestModel3|
#When Select existing vehicle saved above
#|TestModel3|
#When Provide vehicle name
#|TestModelOverride|
#And click on confirm save
#Then Vehicle should be added and shown at Model Display section
#|TestModelOverride|
#When Click on Build and Price
#|配置林肯|下一步|查看及保存|
#And see summary page
##|MKX 尊享版|MKX 尊雅版|
#|MKZ 尊悦版|
#And Click on Save vehicle link
#Then user should be prompted with an overlay to overrride the saved vehicle
#|TestModel1|TestModel2|TestModelOverride|
#When Click on Cancel button
#Then overview page is displayed with existing three vehicles


#Commented due to known issue - Arpit
#@P2
#Scenario: P2 Owner My Build Configuration from B&P page When 3 vehicles are added and user wants to add fourth vehicle 
##Given Open Chrome browser on P2 Lincoln 
##When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
##And Click on Build and Price
##|配置林肯|下一步|查看及保存|
##Given Open Chrome browser on P2 Lincoln 
##And see summary page
##|MKX 尊享版|MKX 尊雅版|
#Given Open Chrome browser on P2 Lincoln	
#When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
#And Click on Build and Price
##|配置林肯|下一步|查看及保存| -Arpit
#|配置林肯|||
#And Select Vehicle "Lincoln_BP"
##Added Feature for clicking on Summary Page Link
#And Click on Build and Price
#|||查看及保存|
##Added Feature for clicking on Summary Page Link
#And see summary page
##|MKX 尊享版|MKX 尊雅版|
#|MKZ 尊悦版|
#And Click on Save vehicle link
#Then User should be directed to sign in overlay
#And Enter User and Password
#||Test123456|
#And Click on Submit button
#Then verify an overlay displayed confirming for vehicle adding
#And Verify the vehicle name format
#|4|
#When Select existing vehicle saved above
#|TestModelOverride|
#When Provide vehicle name
#|TestModelOverride|
#And click on confirm save
#Then Vehicle should be added and shown at Model Display section
#|TestModelOverride|
#When Click on Build and Price
#|配置林肯|下一步|查看及保存|
#And see summary page
#|MKX 尊享版|MKX 尊雅版|
#And Click on Save vehicle link
#Then user should be prompted with an overlay to overrride the saved vehicle
#|TestModel1|TestModel2|TestModelOverride|
#When Click on Cancel button
#Then overview page is displayed with existing three vehicles




###### CAN ONLY EXECUTE ON QA ENVT. 
#@P2
#Scenario: P2 Owner User registration using Social media login - not for first time user
#Given Open Chrome browser on P2 Lincoln 
#When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn/zh_cn/home.html" on P2 Lincoln
#And Click on Login link
#And Click on Registration link
#Then See Registration form is opened
#When Click on Social media option
#|weibo|qq|
#And Enter User and Password
#|czhang@nextdigital.com|a123456|
#And Submit social media login page
#Then see user profile page is displayed
#And Click on Logout link on top
#
#
#
###### CAN ONLY EXECUTE ON QA ENVT. 
#@P2
#Scenario: P2 Owner Login with Social media option not for first time
#Given Open Chrome browser on P2 Lincoln 
#When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn/zh_cn/home.html" on P2 Lincoln
#And Click on Login link
#And Click on Social media option
#|weibo|qq|
#And Enter User and Password
#|czhang@nextdigital.com|a123456|
#And Submit social media login page
#Then see user profile page is displayed
#And Click on Logout link on top



@P2
Scenario: P2 B&P Verify Select Vehicle page Nameplate/model selection, Navigation to Vehicle Configuration page and verify CTAs and links are functional 
Given Open Chrome browser on P2 Lincoln	
When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
And Click on Build and Price
|配置林肯|||
Then All components loads and CTAs and links are functional on the page
And Corresponding models listed down and able to select model at once
And Select Vehicle "Lincoln_BP"
#When Click on "下一步" Button Arpit
#Then Able to navigate back to vehicle configuration page Arpit
And Components loads and CTAs and links are functional on the page


@P2
Scenario: P2 B&P Verify Vehicle Configuration page Verify vehicle price,exterior and interiro features,Decorative features,Safe configuration features,external equipment feature,control system feature  
 Given Open Chrome browser on P2 Lincoln
 When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
 And Click on Build and Price
 |配置林肯|||
And Select Vehicle "Lincoln_BP"
And Click on Build and Price
 ||下一步||
 Then See all components on MKZ vehicle on P2 Lincoln 
 And See the price updated correctly on P2 Lincoln
 # | 449,800~331,800 |//div[3]/div[3]/div[2]/div/div[3]/form/div[1]/div/div[2]/ul/li[1]/p |
 | 10 |//span[@class='nameplate-price price ng-binding'] |
 
 When Click feature to close on engine tab on P2 Lincoln 
 Then Verify do not see engine content on P2 Lincoln
 When Click feature to open on engine tab on P2 Lincoln 
 When Click on exterior tab on P2 Lincoln 
 Then See all features on exterior tab on P2 Lincoln 
 And See the price updated correctly on P2 Lincoln 
 # | 449,800~331,800 | //div[3]/div[2]/div/div[3]/form/div[1]/div/div[2]/ul/li[1]/p  |
 | 10 |//span[@class='nameplate-price price ng-binding'] |
 When Click color feature to close on exterior tab on P2 Lincoln 
 When Click color feature to open on exterior tab on P2 Lincoln 
 Then Verify seeing color content on P2 Lincoln 
  | //div[@id='exterior0']//img |
 When Click color feature to close on exterior tab on P2 Lincoln 
 When Click wheels feature to open on exterior tab on P2 Lincoln 
 Then Verify seeing wheels feature content on P2 Lincoln 
 When Click wheels feature to close on exterior tab on P2 Lincoln 
 Then Verify do not see wheel content on P2 Lincoln 
 When Click color feature to open on exterior tab on P2 Lincoln 
 Then Verify seeing color content on P2 Lincoln 
  | //div[@id='exterior0']//img |
 Then Verify do not see wheel content on P2 Lincoln 
 When Click on interior tab on P2 Lincoln 
 And Click on Seat feature to close on interiror tab on P2 Lincoln 
 Then Verify do not see Seat content on P2 Lincoln 
  | //div[@id='interior0']/div/div/div/div/div/label/div/div[2]/p[1] |
 And See all features on interior tab on P2 Lincoln 
  | //div[@id='interior']//h4 |
# When Click on Decorative feature to open on P2 Lincoln 
# Then Verify seeing decorative content on P2 Lincoln 
#  | //div[@id='interior1']/div/div/div/div/div/label/div/div[1]/img |  
# When Click on Additional tab on P2 Lincoln 
# When Click on safe configuration feature to open on Additional tab on P2 Lincoln 
# When Click on safe configuration feature to close on Additional tab on P2 Lincoln 
# Then Verify do not see safe configuration content on P2 Lincoln 
# When Click on external equipment feature to open on Addition tab on P2 Lincoln 
# When Click on external equipment feature to close on Addition tab on P2 Lincoln 
# Then Verify do not see external equipment content on P2 Lincoln 
#  | //div[@id='extras']/div[2]/div/div[1]/h4/a/div |
# When Click on control system feature to open on Additional tab on P2 Lincoln 
# When Click on control system feature to close on Additional tab on P2 Lincoln 
# Then Verify do not see control system content on P2 Lincoln 
# When Click on Review and Save button on P2 Lincoln 
# Then See the price updated correctly on P2 Lincoln 
#  | 449,800~331,800 | //p[contains(text(),'¥')]  |


#Payment Calculator CTA on B&P - Vehicle Summary Page is redirecting to Vehicle Selector Page.Test Steps after clicking on Payment Calculator CTA are failing -Arpit
#@P2-tester
#Scenario: P2 B&P Verify Vehicle Summary page verify vehicle price details,Ability to navigate back to vehicle configuration page from summary page,Print and Share functionality,navigation to Payment Calculator page 
#Given Open Chrome browser on P2 Lincoln 
#When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
#And Click on Build and Price
#|配置林肯|||
#And Select Vehicle "Lincoln_BP"
#And Click on Build and Price
#||下一步|查看及保存|
#And see summary page
##|MKX 尊享版|MKX 尊雅版|
#|MKZ 尊悦版|MKZ 尊悦版|
#Then external and interior color selected should be shown
#And Should be given vehicle price details correctly
#And Should be listed down features selected
##|2.0T 涡轮增压汽油直喷发动机|CCD连续可调阻尼悬挂系统|自适应LED前大灯系统|ANC主动噪音控制系统|
#|自适应LED前大灯|连续可调阻尼悬挂系统(CCD)|ANC主动噪音控制系统|电子助力转向系统(EPAS)|
#When Navigate back to Vehicle Configuration page
#Then Able to navigate back to vehicle configuration page
#And Click on Build and price summary button
#|查看及保存|
#And see summary page
##|MKX 尊享版|MKX 尊雅版|
#|MKZ 尊悦版|MKZ 尊悦版|
#When Click on Print on Vehicle Summary page
#Then Successfully print the summary page
#When Click on Share on Vehicle Summary page
#Then Able to share the summary page
#When Click on Payment Calculator
#Then All components loaded successfully without performance issue
##|￥449,800|MKX 尊享版~MKX 尊雅版|更改车型|常规金融方案|含延保金融方案|红地毯弹性购车计划|半付半贷|常规金融方案|
#|￥10|MKZ 尊享版~MKX 尊雅版|更改车型|常规金融方案|含延保金融方案|红地毯弹性购车计划|半付半贷|常规金融方案|
#And CTAs and links are functional on the page
##|含延保金融方案|含延保金融方案|红地毯弹性购车计划|红地毯弹性购车计划|半付半贷|半付半贷|常规金融方案|常规金融方案|更改车型|
#




@P2
Scenario: P2 B&P Validation of sequence of nameplate and models coresponding to category reflected on B&P vehicle selection page
 Given Open Chrome browser on P2 Lincoln
 When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
 And Click on Build and Price
 |林肯金融|||
 Then See nameplate and model is in sequence



#Payment Calculator CTA on B&P - Vehicle Summary Page is redirecting to Vehicle Selector Page.Test Steps after clicking on Payment Calculator CTA are failing - Arpit
#@P2-test
#Scenario: P2 Credit Verify Standard Payment Program,verify Down payment via Input field, Change in Payment Terms 
# Given Open Chrome browser on P2 Lincoln 
# When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln 
# And Click on Build and Price 
# |配置林肯||| 
# And Select Vehicle "Lincoln_BP"
# And Click on Build and Price 
# ||下一步|| 
# #|||查看及保存|
# Then See all components on MKZ vehicle on P2 Lincoln 
# When Click on Review and Save button on P2 Lincoln 
# Then See all components on Review and Save on P2 Lincoln 
# When Redirect to Payment Calculator link on P2 Lincoln 
# Then See all components on Payment Calculator on P2 Lincoln 
# When Input amount of money on Payment Calculator on P2 Lincoln 
# Then See all components after input amount of money on Payment Calculator on P2 Lincoln 
#  |58~64| ￥17,769~￥13,529 | X12 | ￥499,900~￥449,800 | ￥289,960 | 58%~64% | 12月 | ￥17,769~￥13,529 |12|
# When Select value from Current Term on Payment Calculator on P2 Lincoln 
#  |24月| 
# Then See all components after selecting value from Current Term on Payment Calculator on P2 Lincoln 
#  |￥9,105~￥6,933| X24 | ￥499,900~￥449,800 | ￥289,960 | 58%~64% | 24月 | ￥9,105~￥6,933 |24|
# When Click on Share on Payment Calculator on P2 Lincoln 
# Then See all components on Share overlay on P2 Lincoln 
# When Click on Close button on Share overlay on P2 Lincoln 
# Then See all components after selecting value from Current Term on Payment Calculator on P2 Lincoln 
#  |￥9,105~￥6,933| X24 | ￥499,900~￥449,800 | ￥289,960 | 58%~64% | 24月 | ￥9,105~￥6,933 |24|
# And Click on PDF on Payment Calculator on P2 Lincoln
 


@P2
Scenario: P2 Credit Verify Vehicle Selector,Payment Calculater Header, Check for Down payment ratio changed in real time
 Given Open Chrome browser on P2 Lincoln 
 When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/payment-calculator-qa.html" on P2 Lincoln
 Then Verify page is navigating to Vehicle Selector Page
 And Select Vehicle "Lincoln_Cred"
 #When Click on Next CTA button - Arpit
 Then All components loaded successfully without performance issue
 #|||更改车型|常规金融方案|含延保金融方案|红地毯弹性购车计划|半付半贷|常规金融方案|
 |||更改车型|常规金融方案|含延保金融方案|红地毯弹性购车计划|常规金融方案|
 And CTAs and links are functional on the page
# |含延保金融方案|含延保金融方案|红地毯弹性购车计划|红地毯弹性购车计划|半付半贷|半付半贷|常规金融方案|常规金融方案||
 |含延保金融方案|含延保金融方案|红地毯弹性购车计划|红地毯弹性购车计划|常规金融方案|常规金融方案||
 And See Corret MSRP value is displayed and its not editable
 And Based on the slider bar the Down payment should changed in real time 
	



@P2
Scenario Outline: P2 Credit verify Standard Payment Program >> 1st Tab, Credit Tile with Two Terms,Accordion with subtitle & Compare,Financial Compare Selection,Page Action Bar,Save PDF,Share Social Media
 Given Open Chrome browser on P2 Lincoln 
 When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/payment-calculator-qa.html" on P2 Lincoln
 Then Verify page is navigating to Vehicle Selector Page
 And Select Vehicle "Lincoln_Cred"
 #When Click on Next CTA button
 And Navigate to Payment Program
 |常规金融方案|
 Then see UI controls and values
 And Check for Credit Tiles,Accordion with subtitle and Action Bar
 And Based on the slider bar the Down payment should changed in real time 
 When Scroll to Credit Tile With 2 Terms group panel "<Term><Currency>"
 And Click on Finance optons accordion
 Then see Finance details based on Credit Tile Terms selected above "<Term>"
 When Input amount of money on Payment Calculator on P2 Lincoln 
 And Click on the Compare Finance Details accordion
 Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
 |1|		
 When Input amount of money on Payment Calculator on P2 Lincoln 
 And Click on the Compare Finance Details accordion
 Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
 |2|	
 When Input amount of money on Payment Calculator on P2 Lincoln 
 And Click on the Compare Finance Details accordion
 Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
 |3|	
 When Input amount of money on Payment Calculator on P2 Lincoln 
 And Click on the Compare Finance Details accordion
 Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
 |4|	
 
 Examples:
 |Term|Currency|
 |24|月|
 
 
 
 

@P2
Scenario Outline: P2 Credit verify WE Contained Payment Program >> 2nd Tab, Credit Tile with Two Terms,Financial Compare Selection,Page Action Bar,Save PDF,Share Social Media
 Given Open Chrome browser on P2 Lincoln 
 When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/payment-calculator-qa.html" on P2 Lincoln
 Then Verify page is navigating to Vehicle Selector Page
 And Select Vehicle "Lincoln_Cred"
 #When Click on Next CTA button
 And Navigate to Payment Program
 |含延保金融方案|
 Then see UI controls and values
 And see existence of Extension plan
 And Check for Credit Tiles,Accordion with subtitle and Action Bar
 And Based on the slider bar the Down payment should changed in real time 
 When Scroll to Credit Tile With 2 Terms group panel "<Term><Currency>"
 And Click on Finance optons accordion
 Then see Finance details based on Credit Tile Terms selected above "<Term>"
 When Input amount of money on Payment Calculator on P2 Lincoln 
 And Click on the Compare Finance Details accordion
 Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
 |1|		
 When Input amount of money on Payment Calculator on P2 Lincoln 
 And Click on the Compare Finance Details accordion
 Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
 |2|	
 When Input amount of money on Payment Calculator on P2 Lincoln 
 And Click on the Compare Finance Details accordion
 Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
 |3|	
 When Input amount of money on Payment Calculator on P2 Lincoln 
 And Click on the Compare Finance Details accordion
 Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
 |4|	
 
 Examples:
 |Term|Currency|
 |36|月|
 
 


@P2
Scenario Outline: P2 Credit verify Red Carpet Option (RCO) Payment Program >> 3rd Tab, Credit Tile with Two Terms,Financial Compare Selection,Page Action Bar,Save PDF,Share Social Media
 Given Open Chrome browser on P2 Lincoln
 When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/payment-calculator-qa.html" on P2 Lincoln
 Then Verify page is navigating to Vehicle Selector Page
 And Select Vehicle "Lincoln_Cred"
 #When Click on Next CTA button
 And Navigate to Payment Program
 |红地毯弹性购车计划|
 Then see UI controls and values
 And Click on Finance optons accordion
 And Check for Credit Tiles,Accordion with subtitle and Action Bar
 And Based on the slider bar the Down payment should changed in real time
 When Scroll to Credit Tile With 2 Terms group panel "<Term><Currency>"
 And Click on Finance optons accordion
 Then see Finance details based on Credit Tile Terms selected above "<Term>"
 And see existence of Minimum Hedge/Balance amount field 
 When Input amount of money on Payment Calculator on P2 Lincoln 
 And Click on the Compare Finance Details accordion
 Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
 |1|	
 And See Hedge/Balance amount displayed in compare section
 When Input amount of money on Payment Calculator on P2 Lincoln 
 And Click on the Compare Finance Details accordion
 Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
 |2|	
 And See Hedge/Balance amount displayed in compare section
 When Input amount of money on Payment Calculator on P2 Lincoln 
 And Click on the Compare Finance Details accordion
 Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
 |3|	
 And See Hedge/Balance amount displayed in compare section
 When Input amount of money on Payment Calculator on P2 Lincoln 
 And Click on the Compare Finance Details accordion
 Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
 |4|	
 And See Hedge/Balance amount displayed in compare section
 When Enter downpayment amount greater than Hedge amount
 Then Check for validation
 Examples:
 |Term|Currency|
 |36|月|



#Nameplate with 50-50 option is not available on Payment Calculator Summary Page - Arpit 
#@P2
#Scenario Outline: P2 Credit verify 50-50 Payment Program >> 4th Tab, Credit Tile with Two Terms,Financial Compare Selection,Page Action Bar,Save PDF,Share Social Media
# Given Open Chrome browser on P2 Lincoln 
# When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/payment-calculator-qa.html" on P2 Lincoln
# Then Verify page is navigating to Vehicle Selector Page
# And Select Vehicle "Lincoln_Cred"
# #When Click on Next CTA button
# And Navigate to Payment Program
# |半付半贷|
# Then see Downpayment field,Balance Amount field,Downpayment Ratio and Term field is disabled
# And see downpayment and balance amt is half of model price
# And Check for Credit Tiles,Accordion with subtitle and Action Bar
# When Click on Finance optons accordion
# Then see Finance details based on Credit Tile Terms selected above "<Term>"
# When Click on the Compare Finance Details accordion
# Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
# |1|		
# When Click on the Compare Finance Details accordion
# Then Verify compare Finance details columns count and related Payment details "<Term><Currency>"
# |1|	
# 
# Examples:
# |Term|Currency|
# |12|月|
#

 

@P2
Scenario: P2 Credit verify KBA Items and Contact Bar
 Given Open Chrome browser on P2 Lincoln 
 When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/payment-calculator-qa.html" on P2 Lincoln
 Then Verify page is navigating to Vehicle Selector Page
 And Select Vehicle "Lincoln_Cred"
 #And Click on Next CTA button
 When Click on the configured Contact Bar icons
 Then See According to the Icon opens appropriate application
 When Click on one of KBA button
 Then See display of correct Form using a Overlay




@P2
Scenario Outline: P2 Credit Memorize the user nameplate path-Payment Calculator
 Given Open Chrome browser on P2 Lincoln 
 When Maximize browser and enter link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/cars-continental.html" on P2 Lincoln
 And Click on Build and Price 
 |林肯金融||| 
 Then See Nameplate "<Nameplate1>" and Model "<Model1>" is selected on payment calculator page	
 When Redirect to link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/payment-calculator.html" on P2
 Then See Nameplate "<Nameplate1>" and Model "<Model1>" is selected on payment calculator page	
 When Redirect to link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/vehicle/suvs-mkx.html" on P2
 And Click on Build and Price 
 |林肯金融||| 
 Then See Nameplate "<Nameplate2>" and Model "<Model2>" is selected on payment calculator page	
 When Redirect to link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/payment-calculator.html" on P2 
 Then See Nameplate "<Nameplate2>" and Model "<Model2>" is selected on payment calculator page	
# And Click on Next CTA button
 When Navigate back to Vehicle Configuration page
 Then See Nameplate "<Nameplate2>" and Model "<Model2>" is selected on payment calculator page	
 Examples:
 |Nameplate1|Model1|Nameplate2|Model2|
 |CONTINENTAL|CONTINENTAL|MKX|MKX|
 



@P2
Scenario: P2 Credit Verify Pre-configured default model and nameplate at Payment Calculator
 Given Open Chrome browser on P2 Lincoln 
 When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
 And Click on Build and Price 
 |林肯金融||| 
 Then See default nameplate and model is selected as configured in author
 And Close Browser
 When Redirect to link "https://wwwENVT.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/payment-calculator.html" on P2 
 Then See default nameplate and model is selected as configured
 



@P2
Scenario: P2 Credit Check Finance Overview page,Finance Product page,Limited Offer Page,Finance Guide Page and Credit Payment Estimator-Home
 Given Open Chrome browser on P2 Lincoln 
 When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance.html" on P2 Lincoln
 Then Verify that CTAs and links are functional on the page
 When Redirect to link "https://wwwdev.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/products-and-services.html" on P2 
 Then Verify that CTAs and links are functional on the page
 When Redirect to link "https://wwwdev.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/products-and-services/red-carpet-option.html" on P2 
 Then Verify that CTAs and links are functional on the page
 When Redirect to link "https://wwwdev.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/products-and-services/extended-warranty.html" on P2 
 Then Verify that CTAs and links are functional on the page
 When Redirect to link "https://wwwdev.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/products-and-services/hourly-loan.html" on P2 
 Then Verify that CTAs and links are functional on the page
 When Redirect to link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/offers.html" on P2 
 Then Verify that CTAs and links are functional on the page
 When Redirect to link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/shoppers-guide.html" on P2 
 Then Verify that CTAs and links are functional on the page
 When Redirect to link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance/payment-calculator-qa.html" on P2 
 Then Verify that CTAs and links are functional on the page


 

@P2
Scenario: P2 Credit Apply for Credit Form submission
 Given Open Chrome browser on P2 Lincoln 
 When Maximize browser and enter link "https://wwwqa.brandap.ford.com/content/lincoln/cn-aut/zh_cn/home/finance.html?captchaMark=1234&captchaMarkEncrypt=8U8ZdxsyiATvLaJ6eHIq4Q==&notToDB=true" on P2 Lincoln
 And Click on Secondary navigaion link
 And Click on Apply to Credit submenu
 Then see apply to credit form opened
 And Fill in Apply to Credit form details
 |	先生	|	Firstname	|	Lastname	|	Firstname.Lastname786@mailinator.com	|	13345678902	|	北京	|	北京	| 北京福瑞林肯中心 |	MKZ	| 3个月内	|	ABCDEF	|24|11|
 Then See Thank you overlay on P1 Lincoln




@P2
Scenario: P2 Owner Account Lockout
Given Open Chrome browser on P2 Lincoln	
When Maximize browser and enter link "https://wwwENVT.brandap.lincoln.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
And Click on Login link
And Enter User and Password
||Test12345|
And Click on Submit button
And Click on Submit button
And Click on Submit button
And Click on Submit button
And Click on Submit button
And Click on Submit button
Then See validation error message is displaying
|15|||
And Enter User and Password
||Test123456|
And Click on Submit button
Then See validation error message is displaying
|15|||
And Wait till 15 mins
And Enter User and Password
||Test123456|
And Click on Submit button
Then User login should be successful and overview page should be displayed
|LASTNAME2|太太|




@P2
Scenario: P2 Owner Session timeout
Given Open Chrome browser on P2 Lincoln	
When Maximize browser and enter link "https://wwwENVT.brandap.lincoln.com/content/lincoln/cn-aut/zh_cn/home.html" on P2 Lincoln
And Click on Login link
And Enter User and Password
||Test123456|
And Click on Submit button
Then User login should be successful and overview page should be displayed
|LASTNAME2|太太|
And Wait till 15 mins
When Click on Username beside welcome message
|LASTNAME2|太太|
And Click on Login link
Then Enter User and Password
||Test123456|



###@P2-NonJenkinsSc
###Scenario: P2 B&P VDM Verify that Admin can view List of nameplates and corresponding series, features, MPV and families
###Given Open Chrome browser on P2 Lincoln	
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/index.html?wcmmode=disabled&marketMakeID=145" on P2 Lincoln
###And Click Logon
###And Enter User and Password
###|Hfiaz|Ind33d12|
###And Click on Sign in
###Then Go to Build and price page
###And see Nameplates listed down for the selected market
###When Go to below CATALOGS
###|Features|
###Then See above details fetched from Gforce
###When Go to below CATALOGS
###|Families|
###Then See above details fetched from Gforce
###When Go to below CATALOGS
###|MPV|
###Then See above details fetched from Gforce
###
###
###
######@P2   -   Scenario NOT required as the functionality is not yet developed(RAMAN confirmed)
######Scenario: P2 B&P VDM Verify that Admin can close the image overlay by clicked on X
######Given Open Chrome browser on P2 Lincoln	
######When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/index.html?wcmmode=disabled&marketMakeID=145" on P2 Lincoln
######And Click Logon
######And Enter User and Password
######|Hfiaz|Ind33d12|
######And Click on Sign in
######Then Go to Build and price page
######And see Nameplates listed down for the selected market
######When Go to below CATALOGS
######|Images|
######And update the image paths
######|Root Folder|root|/test/images/path|
######Then Close the image overlay by clicked on X without saving
######When Go to below CATALOGS
######|Images|
######Then Should not be able to see images corresponding to updated image paths
######|Root Folder|root|
###
###
###
###
###@P2-NonJenkinsSc
###Scenario Outline: P2 B&P VDM Verify that Admin can Update Feature ID and Descriptions in a nameplate
###Given Open Chrome browser on P2 Lincoln	
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Click Logon
###And Enter User and Password
###|Hfiaz|Ind33d12|
###And Click on Sign in
###And Go to below CATALOGS
###|Features|
###And Update the description and Save "<FeatureId>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see the updated feature details on B&P pages
###And Click on Build and Price
###|||查看及保存|
###And see the updated feature details on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Go to below CATALOGS
###|Features|
###And Edit the Feature Id and Save "<FeatureId>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see the updated feature details on B&P pages
###And Click on Build and Price
###|||查看及保存|
###And see the updated feature details on B&P pages
###
###Examples:
###|FeatureId|
###|D2YAM	  |
###
###
###
###
###@P2-NonJenkinsSc
###Scenario Outline: P2 B&P VDM Verify that Admin can Delete and Add existing Feature to the Nameplate
###Given Open Chrome browser on P2 Lincoln	
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Click Logon
###And Enter User and Password
###|Hfiaz|Ind33d12|
###And Click on Sign in
###And Go to below CATALOGS
###|Features|
###Then Delete a Existing Feature "<FeatureId>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see no feature or family displayed on B&P pages
###And Click on Build and Price
###|||查看及保存|
###And see no feature or family displayed on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Go to below CATALOGS
###|Features|
###And Add a New Feature "<InvalidFeatureId>"
###Then See On VDM side invalid IDs should not be able to add "<InvalidFeatureId>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see no feature or family displayed on B&P pages
###And Click on Build and Price
###|||查看及保存|
###And see no feature or family displayed on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Go to below CATALOGS
###|Features|
###And Add a New Feature "<FeatureId>"
###And Add a New Feature "<FeatureId>"
###Then On VDM validate duplication ID should not be displayed "<FeatureId>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see the updated feature details on B&P pages
###And Click on Build and Price
###|||查看及保存|
###And see the updated feature details on B&P pages
###
###Examples:
###|FeatureId|InvalidFeatureId|
###|D2YAM    |InvalidFeature  |
###
###
###
###
###@P2-NonJenkinsSc
###Scenario Outline: P2 B&P VDM Verify that Admin can Update Delete and Add existing Family name to the Nameplate
###Given Open Chrome browser on P2 Lincoln	
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Click Logon
###And Enter User and Password
###|Hfiaz|Ind33d12|
###And Click on Sign in
###And Go to below CATALOGS
###|Families|
###And Update the family name and Save "<FamilyId>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see no feature or family displayed on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Go to below CATALOGS
###|Families|
###Then Delete a Existing Family name "<FamilyId>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see no feature or family displayed on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Go to below CATALOGS
###|Families|
###Then Add a New Family name "<InvalidFamilyId>" and "<FamilyName>"
###Then See On VDM side invalid IDs should not be able to add "<InvalidFamilyId>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see no feature or family displayed on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Go to below CATALOGS
###|Families|
###Then Add a New Family name "<FamilyId>" and "<FamilyName>"
###When Add a New Family name "<FamilyId>" and "<FamilyName>"
###Then On VDM validate duplication ID should not be displayed "<FamilyId>" 
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see the updated feature details on B&P pages
###
###Examples:
###|FamilyId|FamilyName|InvalidFamilyId|
###|SW1|轮毂|InvalidFamily|
###
###
###
###@P2-NonJenkinsSc
###Scenario Outline: P2 B&P VDM Verify that Admin can Update Delete and Add existing MPV ID, Price, Description in series level
###Given Open Chrome browser on P2 Lincoln	
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Click Logon
###And Enter User and Password
###|Hfiaz|Ind33d12|
###And Click on Sign in
###And Select a vehicle series "<VehicleSeries>"
###Then Edit the MPV Price and Description and Save details
###|5.0L 前驱|10|
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then New MPV should be view on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Select a vehicle series "<VehicleSeries>"
###Then Delete the MPV Price and Description and Save details
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see deleted MPV name and price does not viewed on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Select a vehicle series "<VehicleSeries>"
###Then Add MPV Key "<InvalidMPVKey>" and Price "<Price>" and Description "<Name>" and Save details
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see deleted MPV name and price does not viewed on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Select a vehicle series "<VehicleSeries>"
###Then Add MPV Key "<MPVKey>" and Price "<Price>" and Description "<Name>" and Save details
###And Select a vehicle series "<VehicleSeries>"
###Then Add MPV Key "<MPVKey>" and Price "<Price>" and Description "<Name>" and Save details
###Then On VDM validate duplicate MPV_ID "<MPVKey>" should not be displayed for vehicle series "<VehicleSeries>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then New MPV should be view on B&P pages
###
###Examples:
###|MPVKey				    |Name    |Price|InvalidMPVKey|VehicleSeries|
###|AFRAL_EN-TN_VS-GM_YZBAF|2.0L 前驱|999  |InvalidMPVKey|YZBAF		  |
###
###
###
###
###@P2-NonJenkinsSc
###Scenario Outline: P2 B&P VDM Verify that Admin can Add Delete and Update features price from series
###Given Open Chrome browser on P2 Lincoln	
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Click Logon
###And Enter User and Password
###|Hfiaz|Ind33d12|
###And Click on Sign in
###Then Go to update prices "<SeriesID>"
###And Delete the existing Feature "<FeaturesID>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see the feature prices not to display on B&P pages
###And Click on Build and Price
###|||查看及保存|
###And see the feature prices not to display on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###Then Go to update prices "<SeriesID>"
###And Click on Add new Features Price
###When Provide new feature id "<FeaturesID>" and price "<FeaturePrice>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see the newly added feature prices "<FeaturePrice>" display on B&P pages
###And Click on Build and Price
###|||查看及保存|
###And see the newly added feature prices "<FeaturePrice>" display on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###Then Go to update prices "<SeriesID>"
###And Change the existing Feature Price to "<NewFeaturePrice>" for existing Feature Id "<FeaturesID>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see the newly added feature prices "<NewFeaturePrice>" display on B&P pages
###And Click on Build and Price
###|||查看及保存|
###And see the newly added feature prices "<NewFeaturePrice>" display on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###Then Go to update prices "<SeriesID>"
###And Change the existing Feature Price to "<InvalidFeaturePrice>" for existing Feature Id "<FeaturesID>"
###When See validation of invalid feature prices entered on VDM
###Then on submit Gforce side does not be updated invalid feature prices
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see the newly added feature prices "<NewFeaturePrice>" display on B&P pages
###And see the feature prices not to display on B&P pages
###And Click on Build and Price
###|||查看及保存|
###And see the newly added feature prices "<NewFeaturePrice>" display on B&P pages
###And see the feature prices not to display on B&P pages
###
###Examples:
###|SeriesID|FeaturesID|FeaturePrice|NewFeaturePrice|InvalidFeaturePrice|
###|YZBAF   |D2YAM     |11          |111            |RS99.00		   	    |
###
###
###
###
###@P2-NonJenkinsSc
###Scenario Outline: P2 B&P VDM Verify the Admin Can Edit the Feature ID in series level
###Given Open Chrome browser on P2 Lincoln	
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###And Click Logon
###And Enter User and Password
###|Hfiaz|Ind33d12|
###And Click on Sign in
###Then Go to update prices "<SeriesID>"
###When Modify existing valid Feature id "<FeaturesID>" into invalid feature id "<InvalidFeaturesID>"
###Then see invalid feature id "<InvalidFeaturesID>" and valid feature id "<FeaturesID>" is removed from the feature list
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see the existing feature price displayed on B&P pages
###And Click on Build and Price
###|||查看及保存|
###And see the existing feature price displayed on B&P pages
###When Maximize browser and enter link "https://wwwint.brandauthorap.ford.com/content/lincoln-vdm/bpseries.html?wcmmode=disabled&marketMakeID=145&catalog=WSPAD-CC9-2017-LincolnMKZCHN&name=%E5%85%A8%E6%96%B0%E6%9E%97%E8%82%AFMKZ" on P2 Lincoln
###Then Go to update prices "<SeriesID>"
###And Click on Add new Features Price
###When Provide new feature id "<FeaturesID>" and price "<FeaturePrice>"
###When Maximize browser and enter link "https://wwwint.brandap.lincoln.com/content/lincoln/cn/zh_cn/home/build-and-price-vehicle-configuration.html?configKey=4dacd6e2-3ece-41f7-8573-0d08a5bd88a5" on P2 Lincoln
###Then see the newly added feature prices "<FeaturePrice>" display on B&P pages
###And Click on Build and Price
###|||查看及保存|
###And see the newly added feature prices "<FeaturePrice>" display on B&P pages
###
###Examples:
###|SeriesID|FeaturesID|FeaturePrice|InvalidFeaturesID|
###|YZBAF   |D2YAM     |11          |InvalidFeaturesID|
