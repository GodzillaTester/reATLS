package com.lucysecurity;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MethodsDesigner {

    //https://stellarbit.atlassian.net/wiki/spaces/PHISH/pages/721518689/Automated+Testing

    public ChromeDriver driver;
    public WebDriverWait wait;
    public Integer waitInSec = 10;

    public String gagarinMail;
    public String pswrd = "Lucy1234";
    public String urlLucy = "https://update.lucysecurity.host:8443";
    public String superGagarin = "gagarin@phishing.services";

    public String campName;
    public String domain;
    public String recipGroup;
    public String scenarioTemplate;
    public String sndrEml;
    public String smtpSN;
    public String campHref;
    public String campID;

    //Types of Awareness Scenario
    public final String videoType = "Video";
    public final String quizType = "Quiz";
    public final String gameType = "Game";
    public final String staticType = "Static";
    public final String mixedType = "Mixed";
    public final String testType = "Test";
    public final String posterType = "Poster";
    public final String trainingLibraryType = "Training Library";


    // before and after each test methods

    @Before
    public void beforeTest() {

        System.setProperty("webdriver.chrome.driver", "ChromeDriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, waitInSec);
        driver.manage().timeouts().implicitlyWait(waitInSec, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1600, 1000));
    }

    @After
    public void afterTest() {

        driver.close();
        driver.quit();
    }

    // All using methods in Tests

    // !NB! Use fullPageWaiter() for full load of page
    // !NB! ALL METHODS doesn't start with getting the page EXCEPT logIn() Methods

    public void fullPageWaiter() {
        jqwaiter();

    }

    public void singInLucy(String actMail) {
        driver.findElement(By.id("LoginForm_email")).clear();
        driver.findElement(By.id("LoginForm_email")).sendKeys(actMail);
        driver.findElement(By.id("LoginForm_password")).clear();
        driver.findElement(By.id("LoginForm_password")).sendKeys(pswrd);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type=\'submit\']")));
        driver.findElement(By.xpath("//button[@type=\'submit\']")).click();
        if (!By.xpath("//button[contains(.,\"I Accept the Agreement\")]").findElements(driver).isEmpty())
            driver.findElement(By.xpath("//button[contains(.,\'I Accept the Agreement\')]")).click();
        fullPageWaiter();
    }

    public void singInLucy(String actMail, String actpass) {
        driver.findElement(By.id("LoginForm_email")).clear();
        driver.findElement(By.id("LoginForm_email")).sendKeys(actMail);
        driver.findElement(By.id("LoginForm_password")).clear();
        driver.findElement(By.id("LoginForm_password")).sendKeys(actpass);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type=\'submit\']")));
        driver.findElement(By.xpath("//button[@type=\'submit\']")).click();
        if (!By.xpath("//button[contains(.,\"I Accept the Agreement\")]").findElements(driver).isEmpty())
            driver.findElement(By.xpath("//button[contains(.,\'I Accept the Agreement\')]")).click();
        fullPageWaiter();
    }

    public void signOutLucy() {
        byte i = 0;
        while (!driver.getTitle().contains("Login") && i < 5) {
            driver.findElement(By.xpath("//span[@class=\"glyphicon glyphicon-user\"]")).click();
            driver.findElement(By.xpath("//a[contains(.,\"Logout\")]")).click();
            i++;
            fullPageWaiter();
        }
        //Emergency exit for slow connection
        if (!driver.getTitle().contains("Login")) {
            driver.findElement(By.xpath("//span[@class=\"glyphicon glyphicon-user\"]")).click();
            zzz(2000);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(.,\"Logout\")]")));
            driver.findElement(By.xpath("//a[contains(.,\"Logout\")]")).click();
            zzz(2000);
            fullPageWaiter();
            //assertThat(driver.getTitle(),is.("Lucy Test Update — Login"));
            assertThat(driver.getTitle(), containsString("Login"));
        }
    }

    public void zzz(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void goHome() {
        driver.get(urlLucy);
        fullPageWaiter();
    }

    public void jqwaiter() { //In progress
        //wait.until((ExpectedCondition<Boolean>) driver -> ((JavascriptExecutor)driver).executeScript("return jQuery.active == 0").equals(true));
        //wait.until((ExpectedCondition<Boolean>) driver -> ((JavascriptExecutor)driver).executeScript("return(((window.jQuery != null) && (jQuery.active === 0)) || ((document.readyState === \"complete\") && (window.jQuery = null)) || ())").equals(true));
        wait.until((ExpectedCondition<Boolean>) driver -> ((JavascriptExecutor) driver).executeScript("return((window.jQuery != null) && (jQuery.active === 0))").equals(true));
    }


    public void goToTheGagarinMailBox() {
        driver.get("http://mail.phishing.services");
    }

    public void logInGagarinMailBox() {
        driver.get("http://mail.phishing.services");
        fullPageWaiter();
        if (driver.findElements(By.id("rcmloginsubmit")).isEmpty())
            //if (!driver.getTitle().equals("Roundcube Webmail :: Welcome to Roundcube Webmail"))
            driver.findElement(By.id("rcmbtn102")).click();

        driver.findElement(By.id("rcmloginuser")).sendKeys(gagarinMail);
        driver.findElement(By.id("rcmloginpwd")).sendKeys(pswrd);
        driver.findElement(By.id("rcmloginsubmit")).click();
        fullPageWaiter();
        assertThat(driver.findElement(By.xpath("//span[@class=\"username\"]")).getText(), is(gagarinMail));
    }

    public void cleanUpGagarinMailBox() {
        assertThat(driver.findElement(By.xpath("//span[@class=\"username\"]")).getText(), is(gagarinMail));
        if (!driver.findElement(By.id("rcmcountdisplay")).getText().equals("Mailbox is empty")) {
            driver.findElement(By.xpath("//span[@class=\'handle\' and text() = \"Select\"]")).click();
            driver.findElement(By.xpath("//span[@class=\'icon mail\' and text() = \"All\"]")).click();
            driver.findElement(By.id("rcmbtn112")).click();//Delete button
            fullPageWaiter();
            zzz(2000); //this is a necessary measure
            driver.findElement(By.id("rcmbtn107")).click();//Refresh
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id=\"rcmcountdisplay\" and contains(.,\"Mailbox is empty\")]")));
        }
        assertThat(driver.findElement(By.id("rcmcountdisplay")).getText(), is("Mailbox is empty"));
    }

    public void signOutGagarinMailBox() {
        driver.get("http://mail.phishing.services");
        fullPageWaiter();
        //NB! TITLE MUST BE IN ENGLISH
        if (!driver.getTitle().equals("Roundcube Webmail :: Welcome to Roundcube Webmail")) {
            driver.findElement(By.id("rcmbtn102")).click();
            fullPageWaiter();
        }
        assertThat(driver.getTitle(), is("Roundcube Webmail :: Welcome to Roundcube Webmail"));
    }

    public void deleteCamp() {
        if (!driver.getTitle().contains(" — Campaigns"))
            goHome();
        driver.findElement(By.xpath("//input[@class=\"form-control\"]")).clear();
        driver.findElement(By.xpath("//input[@class=\"form-control\"]")).sendKeys(campName);
        driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
        fullPageWaiter();
        while (!By.xpath("//a[contains(.,'" + campName + "')]").findElements(driver).isEmpty()) {
            if (!driver.findElements(By.xpath("//span[@class=\"glyphicon glyphicon-play\"]/../../..//a[text()='" + campName + "']")).isEmpty()) {
                driver.findElement(By.xpath("//a[contains(.,'" + campName + "')]")).click();
                fullPageWaiter();
                //Emergency DELETING
                if (!driver.getTitle().equals("Error Page")) {
                    goHome();
                    fullPageWaiter();
                    zzz(4000);
                    driver.findElement(By.xpath("//input[@class=\"form-control\"]")).clear();
                    driver.findElement(By.xpath("//input[@class=\"form-control\"]")).sendKeys(campName);
                    driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
                    fullPageWaiter();
                    zzz(4000);
                    driver.findElement(By.xpath("//a[contains(.,'" + campName + "')]")).click();
                    fullPageWaiter();
                    zzz(4000);
                }

                //stop campaign if it is running.
                stopRunningCamp();
                goHome();
            }
            fullPageWaiter();
            driver.findElement(By.xpath("//a[contains(.,'" + campName + "')]/../../../td[@class=\"actions\"]/a[@title=\"Delete\"]")).click();
            driver.switchTo().alert().accept();
            driver.switchTo().alert().accept();
            fullPageWaiter();

            //NB! Lucy's slowmotion mesure to use Zzz
            zzz(2000);

            driver.findElement(By.xpath("//input[@class=\"form-control\"]")).clear();
            driver.findElement(By.xpath("//input[@class=\"form-control\"]")).sendKeys(campName);
            driver.findElement(By.xpath("//button[@type=\"submit\"]")).click();
            fullPageWaiter();
        }
    }


    public void replyToRL(String actMessage) {
        //GagarinMailbox
        //Message searching
        driver.findElement(By.xpath("//span[@title='" + sndrEml + "']")).click();
        driver.findElement(By.xpath("//span[@title='" + sndrEml + "']")).click();
        //Reply
        driver.findElement(By.id("rcmbtn109")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("composebody")));
        driver.findElement(By.id("composebody")).sendKeys(actMessage);
        driver.findElement(By.id("rcmbtn108")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("messagelistcontainer")));
    }

    public void assertResponceRL(String folderCamp) {
        //ASSERT RAINLOOP
        //go to folders


        driver.findElement(By.xpath("//span[text()='" + campName + "']/../../div/div/a/span[text()='" + folderCamp + "']")).click();
        driver.findElement(By.xpath("//i[@class=\"icon-spinner\"]")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//i[@class=\"icon-spinner animated\"]")));
        assertThat(driver.findElement(By.xpath("//span[@class=\"sender\"]")).getText(), is(gagarinMail));
        //Deleting
        driver.findElement(By.xpath("//span[text()='" + campName + "']/../../div/div/a/span[text()='" + folderCamp + "']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".checkboxCkeckAll")));
        driver.findElement(By.cssSelector(".checkboxCkeckAll")).click();
        driver.findElement(By.xpath("//a[4]/i")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,\'Empty list.\')]")));
    }

    public void addRecipients() {
        driver.get(campHref + "/recipients");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("add-button")));
        driver.findElement(By.id("add-button")).click();

        //If gagarin recipient group is not existing - we have to create one. In progress
        if (By.xpath("//option[. = '" + recipGroup + "']").findElements(driver.findElement(By.id("CampaignRecipientEditForm_groupId"))).isEmpty()) {
            By.xpath("//option[. =\"New Group\"]").findElement(driver.findElement(By.id("CampaignRecipientEditForm_groupId"))).click();
            fullPageWaiter();
            //one extra click. Lucy is too slow
            zzz(1500);
            driver.findElement(By.id("CampaignRecipientEditForm_groupName")).sendKeys(gagarinMail);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,' Add')]")));
            driver.findElement(By.xpath("//button[contains(.,' Add')]")).click();
            fullPageWaiter();
            driver.findElement(By.xpath("//input[@name='newGroupForm[recipients][0][email]']")).sendKeys(gagarinMail);
            driver.findElement(By.xpath("//input[@name='newGroupForm[recipients][0][name]']")).sendKeys(gagarinMail);
        } else {
            By.xpath("//option[. = '" + recipGroup + "']").findElement(driver.findElement(By.id("CampaignRecipientEditForm_groupId"))).click();
        }
        fullPageWaiter();
        zzz(2000); //it's necessary measure
        driver.findElement(By.xpath("//label[contains(.,\"Select All\")]/input")).click();
        driver.findElement(By.xpath("//button[contains(.,\'Save\')]")).click();
        {
            //Smtimes LUCY too slowly
            wait = new WebDriverWait(driver, 15);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(.,\"Recipients added\")]")));
            wait = new WebDriverWait(driver, waitInSec);
        }

    }

    public void addRecipients(String recipMail) {
        driver.get(campHref + "/recipients");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("add-button")));
        driver.findElement(By.id("add-button")).click();

        //If gagarin recipient group is not existing - we have to create one. In progress
        if (By.xpath("//option[. = '" + recipMail + "']").findElements(driver.findElement(By.id("CampaignRecipientEditForm_groupId"))).isEmpty()) {
            By.xpath("//option[. =\"New Group\"]").findElement(driver.findElement(By.id("CampaignRecipientEditForm_groupId"))).click();
            fullPageWaiter();
            //one extra click. Lucy is too slow
            zzz(1500);
            driver.findElement(By.id("CampaignRecipientEditForm_groupName")).sendKeys(recipMail);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,' Add')]")));
            driver.findElement(By.xpath("//button[contains(.,' Add')]")).click();
            fullPageWaiter();
            driver.findElement(By.xpath("//input[@name='newGroupForm[recipients][0][email]']")).sendKeys(recipMail);
            driver.findElement(By.xpath("//input[@name='newGroupForm[recipients][0][name]']")).sendKeys(recipMail);
        } else {
            By.xpath("//option[. = '" + recipMail + "']").findElement(driver.findElement(By.id("CampaignRecipientEditForm_groupId"))).click();
        }
        fullPageWaiter();
        driver.findElement(By.xpath("//label[contains(.,\"Select All\")]/input")).click();
        driver.findElement(By.xpath("//button[contains(.,\'Save\')]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(.,\"Recipients added\")]")));
    }

    public void logInLucy() {
        //GO!
        goHome();
        //We need to logout at first (this method signout from Lucy if we are singIN)
        signOutLucy();
        //Login
        singInLucy(gagarinMail);
        //If this gagarin is not an existing user we have to create this gagarin from SuperGagarin user (default robot)
        if (!By.xpath("//div[contains(.,\"Incorrect username or password.\")]").findElements(driver).isEmpty()) {
            //singIn as SuperGagarin (or other default user) with singInLucy("default@user.com", "123") - for example. See this method below
            singInLucy(superGagarin);
            driver.findElement(By.xpath("//a[contains(.,'Settings ')]")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=\"Users\"]")));
            driver.findElement(By.xpath("//a[text()=\"Users\"]")).click();
            fullPageWaiter();
            driver.findElement(By.xpath("//a[contains(.,'New User')]")).click();
            fullPageWaiter();
            //Creating of gagarin
            driver.findElement(By.id("UserEditForm_email")).sendKeys(gagarinMail);
            driver.findElement(By.id("UserEditForm_name")).sendKeys(gagarinMail);
            driver.findElement(By.id("UserEditForm_password")).sendKeys(pswrd);
            driver.findElement(By.id("UserEditForm_passwordRepeat")).sendKeys(pswrd);
            By.xpath("//option[. = \"Administrator\"]").findElement(driver.findElement(By.id("UserEditForm_roleId"))).click();
            driver.findElement(By.xpath("//button[contains(.,'Save')]")).click();
            fullPageWaiter();
            //SignOut from SuperGagarin to Current gagarin
            signOutLucy();
            singInLucy(gagarinMail);
        }
    }

    public void createCampTrackResp() {
        // Create a campaign
        driver.findElement(By.xpath("//div[@id=\'new-campaign-button\']/div/button")).click();
        driver.findElement(By.xpath("//div[@id=\"new-campaign-button\"]/div/ul/li[1]/a")).click();
        fullPageWaiter();
        //New Campaign page
        driver.findElement(By.id("CampaignEditForm_name")).clear();
        driver.findElement(By.id("CampaignEditForm_name")).sendKeys(campName);
        By.id("CampaignEditForm_clientId").findElement(driver).findElement(By.xpath("//option[. = 'Lucy Test']")).click();
        if (!driver.findElement(By.id("CampaignEditForm_trackResponses")).isSelected())
            driver.findElement(By.id("CampaignEditForm_trackResponses")).click();
        if (!driver.findElement(By.xpath("//label[contains(.,\'Expert Setup (Manual Configuration)\')]")).isSelected())
            driver.findElement(By.xpath("//label[contains(.,\'Expert Setup (Manual Configuration)\')]")).click();
        driver.findElement(By.id("CampaignReportForm_save")).click();
        fullPageWaiter();
        //Get campagn's url and ID
        campHref = driver.findElement(By.xpath("//ol[@class='breadcrumb']/li[3]/a")).getAttribute("href");
        //Be careful with counting extra characters at the beginning. In this example is 53 https://update.lucysecurity.host:8443/admin/campaign/"
        campID = campHref.substring(53);
        //To check Campaign ID
        //System.out.println(campID);
    }

    public void startCampSkipChecks() {
        driver.get(campHref);
        fullPageWaiter();
        driver.findElement(By.xpath("//div[@id='start-button']/button")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Start")));
        driver.findElement(By.linkText("Start")).click();
        //Skip checks
        if (!By.xpath("//p[@class='form-description']").findElements(driver).isEmpty()) {
            driver.findElement(By.id("skipChecks")).click();
            driver.switchTo().alert().accept();
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(., 'Campaign started.')]")));


        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@id='start-button']")));
        //zzz(1500); //necessary measure
        //assertTrue("Campaign is not running", driver.findElements(By.xpath("//div[@class='campaign-status']/span[contains(.,'Running')]")).isEmpty());
        //assertThat(driver.findElement(By.xpath("//div[@class=\'campaign-status\']/span[contains(.,Running)]")).getText(), is("Running"));
    }

    public void stopRunningCamp() {
        //Checking page
        //NB drown condition!!!!
        if (!driver.getTitle().contains(campName))
            driver.get(campHref);

        if (!driver.findElements(By.xpath("//div[@class=\'campaign-status\']/span[contains(.,'Running')]")).isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='start-button']")));
            driver.findElement(By.xpath("//a[@id='start-button']")).click();
            driver.switchTo().alert().accept();
            // Driver waits for the message "Campaign stopped"
            //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(., \"Campaign stopped.\")]")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='campaign-status']/span[contains(.,\"Stopped\")]")));
        }
    }

    public void checkBoxWithID(Boolean checkTheBox, String checkBoxID) {
        if (checkTheBox) {
            if (!driver.findElement(By.id(checkBoxID)).isSelected())
                driver.findElement(By.id(checkBoxID)).click();
        } else {
            if (driver.findElement(By.id(checkBoxID)).isSelected())
                driver.findElement(By.id(checkBoxID)).click();
        }

    }

    //Hard version + no notes&industry
    public void campDesignerExpertSetup(Boolean trackResponses,
                                        Boolean allowAwarenessRescheduling,
                                        Boolean ignoreRepeatedAnswersInAwareness,
                                        Boolean pinned,
                                        Boolean deleteProtection,
                                        Boolean enabledAnonymousMode,
                                        Boolean enableSSOAwarenessWebsites,
                                        Boolean enduserProfilesEnabled,
                                        Boolean directLoginEnduserProfiles,
                                        Boolean emailTracking,
                                        String mailboxEmailTracking,
                                        String intervalEmailTracking,
                                        Boolean stopCampAutomatically,
                                        String dateStopCampAutomatically,
                                        Boolean reportSend,
                                        String templateReportSend,
                                        String formatReportSend) {

        if (!driver.getTitle().contains(" — Campaigns"))
            goHome();

        // Create a campaign
        driver.findElement(By.xpath("//div[@id=\'new-campaign-button\']/div/button")).click();
        driver.findElement(By.xpath("//div[@id=\"new-campaign-button\"]/div/ul/li[1]/a")).click();
        fullPageWaiter();

        //New Campaign page
        driver.findElement(By.id("CampaignEditForm_name")).clear();
        driver.findElement(By.id("CampaignEditForm_name")).sendKeys(campName);
        By.id("CampaignEditForm_clientId").findElement(driver).findElement(By.xpath("//option[. = 'Lucy Test']")).click();

        //Radio-button "Expert setup mode"
        if (!driver.findElement(By.xpath("//label[contains(.,\'Expert Setup (Manual Configuration)\')]")).isSelected())
            driver.findElement(By.xpath("//label[contains(.,\'Expert Setup (Manual Configuration)\')]")).click();

        //Pure Checkboxes
        checkBoxWithID(trackResponses, "CampaignEditForm_trackResponses");
        checkBoxWithID(allowAwarenessRescheduling, "CampaignEditForm_awarenessReschedule");
        checkBoxWithID(ignoreRepeatedAnswersInAwareness, "CampaignEditForm_awarenessQuizBan");
        checkBoxWithID(pinned, "CampaignEditForm_pinned");
        checkBoxWithID(deleteProtection, "CampaignEditForm_deleteProtection");
        checkBoxWithID(enabledAnonymousMode, "CampaignEditForm_anonymousMode");
        checkBoxWithID(enableSSOAwarenessWebsites, "CampaignEditForm_ssoEnabled");

        //Other Checkboxes
        //Enduser profiles. IP adress as default, no whitelabel option provided
        checkBoxWithID(enduserProfilesEnabled, "CampaignEditForm_enduserProfiles");
        checkBoxWithID(directLoginEnduserProfiles, "CampaignEditForm_enduserDirectLogin");

        //emailTracking
        checkBoxWithID(emailTracking, "CampaignEditForm_tracking");
        if (emailTracking) {
            driver.findElement(By.id("CampaignEditForm_trackingEmail")).clear();
            driver.findElement(By.id("CampaignEditForm_trackingEmail")).sendKeys(mailboxEmailTracking);
            driver.findElement(By.id("CampaignEditForm_trackingInterval")).clear();
            driver.findElement(By.id("CampaignEditForm_trackingInterval")).sendKeys(intervalEmailTracking);
        }
        //Autostop
        checkBoxWithID(stopCampAutomatically, "CampaignEditForm_enableStopDate");
        if (stopCampAutomatically) {
            driver.findElement(By.xpath("//input[@name=\"CampaignEditForm[stopDate]\"]")).clear();
            driver.findElement(By.xpath("//input[@name=\"CampaignEditForm[stopDate]\"]")).sendKeys(dateStopCampAutomatically);
            //08.09.2020 00:00 - format
        }
        //Report Data
        checkBoxWithID(reportSend, "CampaignEditForm_report");
        if (reportSend) {
            //Template
            By.xpath("//option[. ='" + templateReportSend + "']").findElement(driver.findElement(By.id("CampaignReportForm_templateId"))).click();
            //Fornmat PDF, HTML, DOCX
            By.xpath("//option[. ='" + formatReportSend + "']").findElement(driver.findElement(By.id("CampaignReportForm_format"))).click();
            //Font Size and Font family as default
        }

        driver.findElement(By.id("CampaignReportForm_save")).click();


        if (!(By.xpath("//h4[contains(.,\"Benchmark Sector settings\")]")).findElements(driver).isEmpty()) {
            driver.findElement(By.xpath("//input[@name=\"BenchmarkSettingsForm[benchmarkDontAsk]\"]")).click();
            driver.findElement(By.id("submitBenchmarkSettings")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[contains(.,\"Benchmark Sector settings\")]")));
            driver.findElement(By.id("CampaignReportForm_save")).click();
        }
        fullPageWaiter();

        //Get campagn's url and ID
        campHref = driver.findElement(By.xpath("//ol[@class='breadcrumb']/li[3]/a")).getAttribute("href");
        //Be careful with counting extra characters at the beginning. In this example is 53 https://update.lucysecurity.host:8443/admin/campaign/"
        campID = campHref.substring(53);
        //To check Campaign ID
        //System.out.println(campID);


    }

    //Lite version with pure checks + no notes&industry
    public void campDesignerExpertSetup(Boolean trackResponses,
                                        Boolean allowAwarenessRescheduling,
                                        Boolean ignoreRepeatedAnswersInAwareness,
                                        Boolean pinned,
                                        Boolean deleteProtection,
                                        Boolean enabledAnonymousMode,
                                        Boolean enableSSOAwarenessWebsites,
                                        Boolean enduserProfilesEnabled,
                                        Boolean directLoginEnduserProfiles) {

        if (!driver.getTitle().contains(" — Campaigns"))
            goHome();

        // Create a campaign
        driver.findElement(By.xpath("//div[@id=\'new-campaign-button\']/div/button")).click();
        driver.findElement(By.xpath("//div[@id=\"new-campaign-button\"]/div/ul/li[1]/a")).click();
        fullPageWaiter();

        //New Campaign page
        driver.findElement(By.id("CampaignEditForm_name")).clear();
        driver.findElement(By.id("CampaignEditForm_name")).sendKeys(campName);
        By.id("CampaignEditForm_clientId").findElement(driver).findElement(By.xpath("//option[. = 'Lucy Test']")).click();

        //Radio-button "Expert setup mode"
        if (!driver.findElement(By.xpath("//label[contains(.,\'Expert Setup (Manual Configuration)\')]")).isSelected())
            driver.findElement(By.xpath("//label[contains(.,\'Expert Setup (Manual Configuration)\')]")).click();

        //Pure Checkboxes
        checkBoxWithID(trackResponses, "CampaignEditForm_trackResponses");
        checkBoxWithID(allowAwarenessRescheduling, "CampaignEditForm_awarenessReschedule");
        checkBoxWithID(ignoreRepeatedAnswersInAwareness, "CampaignEditForm_awarenessQuizBan");
        checkBoxWithID(pinned, "CampaignEditForm_pinned");
        checkBoxWithID(deleteProtection, "CampaignEditForm_deleteProtection");
        checkBoxWithID(enabledAnonymousMode, "CampaignEditForm_anonymousMode");
        checkBoxWithID(enableSSOAwarenessWebsites, "CampaignEditForm_ssoEnabled");

        //Other Checkboxes
        //Enduser profiles. IP adress as default, no whitelabel option provided
        checkBoxWithID(enduserProfilesEnabled, "CampaignEditForm_enduserProfiles");
        checkBoxWithID(directLoginEnduserProfiles, "CampaignEditForm_enduserDirectLogin");


        driver.findElement(By.id("CampaignReportForm_save")).click();
        if (!(By.xpath("//h4[contains(.,\"Benchmark Sector settings\")]")).findElements(driver).isEmpty()) {
            driver.findElement(By.xpath("//input[@name=\"BenchmarkSettingsForm[benchmarkDontAsk]\"]")).click();
            driver.findElement(By.id("submitBenchmarkSettings")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[contains(.,\"Benchmark Sector settings\")]")));
            driver.findElement(By.id("CampaignReportForm_save")).click();
        }
        fullPageWaiter();

        //Get campagn's url and ID
        campHref = driver.findElement(By.xpath("//ol[@class='breadcrumb']/li[3]/a")).getAttribute("href");
        //Be careful with counting extra characters at the beginning. In this example is 53 https://update.lucysecurity.host:8443/admin/campaign/"
        campID = campHref.substring(53);
        //To check Campaign ID
        //System.out.println(campID);
    }

    //simple version + no notes&industry
    public void campDesignerExpertSetup() {
        if (!driver.getTitle().contains(" — Campaigns"))
            goHome();

        // Create a campaign
        driver.findElement(By.xpath("//div[@id=\'new-campaign-button\']/div/button")).click();
        driver.findElement(By.xpath("//div[@id=\"new-campaign-button\"]/div/ul/li[1]/a")).click();
        fullPageWaiter();

        //New Campaign page
        driver.findElement(By.id("CampaignEditForm_name")).clear();
        driver.findElement(By.id("CampaignEditForm_name")).sendKeys(campName);
        By.id("CampaignEditForm_clientId").findElement(driver).findElement(By.xpath("//option[. = 'Lucy Test']")).click();

        //Radio-button "Expert setup mode"
        if (!driver.findElement(By.xpath("//label[contains(.,\'Expert Setup (Manual Configuration)\')]")).isSelected())
            driver.findElement(By.xpath("//label[contains(.,\'Expert Setup (Manual Configuration)\')]")).click();

        driver.findElement(By.id("CampaignReportForm_save")).click();
        if (!(By.xpath("//h4[contains(.,\"Benchmark Sector settings\")]")).findElements(driver).isEmpty()) {
            driver.findElement(By.xpath("//input[@name=\"BenchmarkSettingsForm[benchmarkDontAsk]\"]")).click();
            driver.findElement(By.id("submitBenchmarkSettings")).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//h4[contains(.,\"Benchmark Sector settings\")]")));
            driver.findElement(By.id("CampaignReportForm_save")).click();
        }
        fullPageWaiter();

        //Get campagn's url and ID
        campHref = driver.findElement(By.xpath("//ol[@class='breadcrumb']/li[3]/a")).getAttribute("href");
        //Be careful with counting extra characters at the beginning. In this example is 53 https://update.lucysecurity.host:8443/admin/campaign/"
        campID = campHref.substring(53);
        //To check Campaign ID
        //System.out.println(campID);
    }

    //Helper
    public void scenarioTemplateSearchingThenAdding(String type, String scenarioTemplate) {
        //TYPES for helper method
        //Hyperlink
        //File-Based
        //Mixed
        //Web Based
        //Awareness only

        //For Awareness
        //Video
        //Quiz
        //Game
        //Static
        //Mixed
        //Test
        //Poster
        //Training library


        //Scenario's select
        //clear filter's
        driver.findElement(By.xpath("//button[contains(.,\"Clear Filters\")]")).click();
        fullPageWaiter();
        driver.findElement(By.xpath("//button/div[contains(.,\"Type\")]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(.,'Type')]/../..//li/a[contains(.,'" + type + "')]")));
        driver.findElement(By.xpath("//div[contains(.,'Type')]/../..//li/a[contains(.,'" + type + "')]")).click();
        zzz(2000);
        fullPageWaiter();
        driver.findElement(By.xpath("//input[@name=\'s\']")).sendKeys(scenarioTemplate);
        driver.findElement(By.xpath("//span/button/span")).click();
        fullPageWaiter();
        //Checking existing templates
        assertTrue("NO TEMPLATES FOUNDED", !driver.findElements(By.xpath("//div[@class='scenario-template-list']")).isEmpty());
        driver.findElement(By.xpath("//button[@type='button' and contains(.,'Use')]")).click();
        driver.findElement(By.linkText("English")).click();
        fullPageWaiter();
    }

    //Helper
    public void scenarioTemplateSearchingThenAdding(String type) {
        //TYPES for helper method
        //Hyperlink
        //File-Based
        //Mixed
        //Web Based
        //Awareness only

        //For Awareness
        //see PUBLIC FINAL STRING
        //Video
        //Quiz
        //Game
        //Static
        //Mixed
        //Test
        //Poster
        //Training library

        //Scenario's select
        //clear filter's
        driver.findElement(By.xpath("//button[contains(.,\"Clear Filters\")]")).click();
        fullPageWaiter();
        driver.findElement(By.xpath("//button/div[contains(.,\"Type\")]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(.,'Type')]/../..//li/a[contains(.,'" + type + "')]")));
        driver.findElement(By.xpath("//div[contains(.,'Type')]/../..//li/a[contains(.,'" + type + "')]")).click();
        zzz(2000);
        fullPageWaiter();
        //Checking existing templates
        assertTrue("NO TEMPLATES FOUNDED", !driver.findElements(By.xpath("//div[@class='scenario-template-list']")).isEmpty());
        //driver.findElement(By.id("button-use")).click(); only for phish scenarios
        driver.findElement(By.xpath("//button[@type='button' and contains(.,'Use')]")).click();
        driver.findElement(By.linkText("English")).click();
        fullPageWaiter();
    }

    public void addingScenarioTemplateToScenarioHyperlink(Boolean trackOpenedEmails,
                                                          Boolean sendLinkToAwarenessWebsiteAutomatically,

                                                          Boolean byClickRateSendLinkToAwarenessWebsiteAutomatically,
                                                          String valueClickRateSendLinkToAwarenessWebsiteAutomatically,
                                                          Boolean bySuccessRateSendLinkToAwarenessWebsiteAutomatically,
                                                          String valueSuccessRateSendLinkToAwarenessWebsiteAutomatically,
                                                          String awarenessDelaySendLinkToAwarenessWebsiteAutomatically,

                                                          Boolean advancedInformationGathering,
                                                          Boolean browserDetailsAdvancedInformationGathering,
                                                          Boolean firebugInformationAdvancedInformationGathering,
                                                          Boolean popupBlockerAdvancedInformationGathering,
                                                          Boolean geoLocationAdvancedInformationGathering,
                                                          Boolean socialNetworkAdvancedInformationGathering,
                                                          Boolean proxyAdvancedInformationGathering,

                                                          Boolean doubleBarrelAttack,
                                                          String lureDelayDoubleBarrelAttack,

                                                          Boolean urlShortener,

                                                          Boolean redirectURL,
                                                          String stringRedirectURL,

                                                          String scenarioTemplate,
                                                          String scenarioName) {

        //Please notice this method use only System Domain!
        //location check
        if (!driver.findElements(By.xpath(" //li[contains(.,\"Base Settings\")]/..//li[contains(.,'" + campName + "')]")).isEmpty()) {
            driver.get(urlLucy + "/admin/campaign/" + campID + "/settings");
            fullPageWaiter();
        }
        driver.findElement(By.id("new-scenario-button")).click();
        fullPageWaiter();
        scenarioTemplateSearchingThenAdding("Hyperlink", scenarioTemplate);
        //New scenario page - Hyperlink options
        driver.findElement(By.id("CampaignScenarioEditForm_name")).sendKeys(scenarioName);
        By.xpath("//option[. = 'System Domain']").findElement(driver.findElement(By.id("CampaignScenarioEditForm_domain"))).click();
        //Track Opened Emails option
        checkBoxWithID(trackOpenedEmails, "CampaignScenarioEditForm_trackOpenedEmails");
        //Send Link to Awareness Website Automatically option
        checkBoxWithID(sendLinkToAwarenessWebsiteAutomatically, "LandingTemplateEditForm_sendAwarenessLink");
        if (sendLinkToAwarenessWebsiteAutomatically) {
            checkBoxWithID(byClickRateSendLinkToAwarenessWebsiteAutomatically, "sendAwarenessByClickRate_checkbox");
            if (byClickRateSendLinkToAwarenessWebsiteAutomatically)
                driver.findElement(By.id("CampaignScenarioEditForm_sendAwarenessByClickRate")).sendKeys(valueClickRateSendLinkToAwarenessWebsiteAutomatically);
            checkBoxWithID(bySuccessRateSendLinkToAwarenessWebsiteAutomatically, "sendAwarenessBySuccessRate_checkbox");
            if (bySuccessRateSendLinkToAwarenessWebsiteAutomatically)
                driver.findElement(By.id("CampaignScenarioEditForm_sendAwarenessBySuccessRate")).sendKeys(valueSuccessRateSendLinkToAwarenessWebsiteAutomatically);
            driver.findElement(By.id("CampaignScenarioEditForm_awarenessDelay")).sendKeys(awarenessDelaySendLinkToAwarenessWebsiteAutomatically);
        }
        //Advanced Information Gathering option
        checkBoxWithID(advancedInformationGathering, "CampaignScenarioEditForm_analyse");
        if (advancedInformationGathering) {
            checkBoxWithID(browserDetailsAdvancedInformationGathering, "CampaignScenarioEditForm_analyseFieldBrowserDetails");
            checkBoxWithID(firebugInformationAdvancedInformationGathering, "CampaignScenarioEditForm_analyseFieldFirebug");
            checkBoxWithID(popupBlockerAdvancedInformationGathering, "CampaignScenarioEditForm_analyseFieldPopupBlocker");
            checkBoxWithID(geoLocationAdvancedInformationGathering, "CampaignScenarioEditForm_analyseFieldGeolocation");
            checkBoxWithID(socialNetworkAdvancedInformationGathering, "CampaignScenarioEditForm_analyseFieldSocialNetworks");
            checkBoxWithID(proxyAdvancedInformationGathering, "CampaignScenarioEditForm_analyseFieldProxy");
        }
        //Double Barrel Attack option
        checkBoxWithID(doubleBarrelAttack, "CampaignScenarioEditForm_doubleBarrel");
        if (doubleBarrelAttack) {
            driver.findElement(By.id("CampaignScenarioEditForm_lureDelay")).clear();
            driver.findElement(By.id("CampaignScenarioEditForm_lureDelay")).sendKeys(lureDelayDoubleBarrelAttack);
        }
        //URL Shortener default option - is.gd
        if (urlShortener)
            By.xpath("//option[. = 'is.gd']").findElement(driver.findElement(By.id("CampaignScenarioEditForm_urlShortener"))).click();
        //Redirect URL
        if (redirectURL) {
            driver.findElement(By.id("CampaignScenarioEditForm_redirectUrl")).clear();
            driver.findElement(By.id("CampaignScenarioEditForm_redirectUrl")).sendKeys(stringRedirectURL);
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,\'Save\')]")));
        driver.findElement(By.xpath("//button[contains(.,\'Save\')]")).click();
        fullPageWaiter();
        //Scenario page saved
    }

    public void addingScenarioTemplateToScenarioHyperlink(String scenarioTemplate, String scenarioName) {

        //Please notice this method use only System Domain!
        //location check
        if (!driver.findElements(By.xpath(" //li[contains(.,\"Base Settings\")]/..//li[contains(.,'" + campName + "')]")).isEmpty()) {
            driver.get(urlLucy + "/admin/campaign/" + campID + "/settings");
            fullPageWaiter();
        }
        driver.findElement(By.id("new-scenario-button")).click();
        fullPageWaiter();
        scenarioTemplateSearchingThenAdding("Hyperlink", scenarioTemplate);
        //New scenario page - Hyperlink options
        driver.findElement(By.id("CampaignScenarioEditForm_name")).sendKeys(scenarioName);
        By.xpath("//option[. = 'System Domain']").findElement(driver.findElement(By.id("CampaignScenarioEditForm_domain"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,\'Save\')]")));
        driver.findElement(By.xpath("//button[contains(.,\'Save\')]")).click();
        fullPageWaiter();
        //Scenario page saved

    }

    public void addingScenarioTemplateToScenarioHyperlink() {
        //In this version ScenarioName is CampName
        //Please notice this method use only System Domain!
        //location check
        if (!driver.findElements(By.xpath(" //li[contains(.,\"Base Settings\")]/..//li[contains(.,'" + campName + "')]")).isEmpty()) {
            driver.get(urlLucy + "/admin/campaign/" + campID + "/settings");
            fullPageWaiter();
        }
        driver.findElement(By.id("new-scenario-button")).click();
        fullPageWaiter();
        scenarioTemplateSearchingThenAdding("Hyperlink");
        //New scenario page - Hyperlink options
        driver.findElement(By.id("CampaignScenarioEditForm_name")).sendKeys(campName);
        By.xpath("//option[. = 'System Domain']").findElement(driver.findElement(By.id("CampaignScenarioEditForm_domain"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,\'Save\')]")));
        driver.findElement(By.xpath("//button[contains(.,\'Save\')]")).click();
        fullPageWaiter();
        //Scenario page saved

    }

    public void scenarioMailSettings() {
        //This method starts from saved Scenario Settings page
        //without option USE s/mime and no custom ssl certif
        smtpSN = "mail." + domain;

        assertTrue("Method scenarioMailSettings started from invalid page", !driver.findElements(By.xpath("//a[contains(.,'Summary')]/../..//a[contains(.,'Scenario Settings')]")).isEmpty());
        driver.findElement(By.xpath("//a[contains(.,'Summary')]/../..//a[contains(.,'Scenario Settings')]")).click();
        fullPageWaiter();
        driver.findElement(By.xpath("//li/a[contains(.,'Scenario Settings')]/../ul/li/a[contains(.,'Mail Settings')]")).click();
        fullPageWaiter();
        // if not postfix - gagarin have to select this delivery method
        /////////
        if (!driver.findElement(By.id("MailSettingsForm_mailDeliveryMethod")).isSelected()) {
            By.xpath("//option[. = 'Internal Postfix Server']").findElement(driver.findElement(By.id("MailSettingsForm_mailDeliveryMethod"))).click();
            fullPageWaiter();
            //wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='form-group deliver-internal' and @style='display: block;']")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MailSettingsForm_smtpServerName")));
            driver.findElement(By.id("MailSettingsForm_smtpServerName")).clear();
            driver.findElement(By.id("MailSettingsForm_smtpServerName")).sendKeys(smtpSN);
            if (driver.findElement(By.xpath("//label[contains(.,'Use S/MIME Certificate')]")).isSelected())
                driver.findElement(By.xpath("//label[contains(.,'Use S/MIME Certificate')]")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value=\"Save\"]")));
            driver.findElement(By.xpath("//input[@value=\"Save\"]")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(.,\"Please wait while the system is checking the mail server\")]")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(.,\"Please wait while the system is checking the mail server\")]")));
            fullPageWaiter();
        }


        //Checking that ssl-generator is unselected
        driver.findElement(By.xpath("//li/a[contains(.,'Scenario Settings')]/../ul/li/a[contains(.,'SSL Settings')]")).click();
        fullPageWaiter();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("SslConfigForm_ssl")));
        if (driver.findElement(By.id("SslConfigForm_ssl")).isSelected()) {
            driver.findElement(By.id("SslConfigForm_ssl")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(.,'Settings saved.')]")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(.,'Settings saved.')]")));
        }

        driver.findElement(By.id("submit")).click();
        //waiting by validator (Phish-5012 )

        fullPageWaiter();

    }

    public void scenarioMessageTemplate() {
        //sndrEml = campName + '@' + domain;

        //This method starts from saved Scenario Settings page

        //Assert base page (scenario Base settings page)
        assertTrue("Method scenarioMessageTemplate started from invalid page", !driver.findElements(By.xpath("//a[contains(.,'Summary')]/../..//a[contains(.,'Message Template')]")).isEmpty());
        driver.findElement(By.xpath("//a[contains(.,'Message Template')]")).click();
        fullPageWaiter();
        //Message template page
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[@role=\"presentation\"]")));
        driver.findElement(By.id("EmailTemplateEditForm_senderName")).clear();
        driver.findElement(By.id("EmailTemplateEditForm_senderName")).sendKeys(campName);
        driver.findElement(By.id("EmailTemplateEditForm_senderEmail")).clear();
        driver.findElement(By.id("EmailTemplateEditForm_senderEmail")).sendKeys(sndrEml);
        driver.findElement(By.id("submit")).click();
        //waiting by validator
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(.,\"Template saved\")]")));


    }

    public void awarenessScenarioMessage() {
        sndrEml = campName + '@' + domain;

        //This method starts from saved Awareness Scenario Settings page

        //Assert base page (Awareness scenario Base settings page)
        assertTrue("Method awarenessScenarioMessage started from invalid page", !driver.findElements(By.xpath("//a[contains(.,'Base Settings')]/../..//a[contains(.,'Message')]")).isEmpty());

        driver.findElement(By.xpath("//a[contains(.,'Base Settings')]/../..//a[contains(.,'Message')]")).click();
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[@role=\"presentation\"]")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CampaignAwarenessEmailEditForm_content")));
        fullPageWaiter();
        driver.findElement(By.id("CampaignAwarenessEmailEditForm_subject")).clear();
        driver.findElement(By.id("CampaignAwarenessEmailEditForm_subject")).sendKeys(campName);
        driver.findElement(By.id("CampaignAwarenessEmailEditForm_senderName")).clear();
        driver.findElement(By.id("CampaignAwarenessEmailEditForm_senderName")).sendKeys(campName);
        driver.findElement(By.id("CampaignAwarenessEmailEditForm_senderEmail")).clear();
        driver.findElement(By.id("CampaignAwarenessEmailEditForm_senderEmail")).sendKeys(sndrEml);
        driver.findElement(By.xpath("//button[contains(.,\'Save\')]")).click();
        //waiting by validator
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(.,'Template saved')]")));
    }

    public void scenarioTemplateAddingToScenarioAwarenessOnly(Boolean websiteEnabled,
                                                              Boolean quizOption,
                                                              Boolean createAwarenessTrainingDiploma,
                                                              Boolean doNotSendEmails,
                                                              String riskLevel,
                                                              String awarenessScenarioType,
                                                              String awarenessScenarioTemplate) {

        //Please notice this method use only LUCY Based Website without ssl
        //location check
        if (!driver.findElements(By.xpath(" //li[contains(.,\"Base Settings\")]/..//li[contains(.,'" + campName + "')]")).isEmpty()) {
            driver.get(urlLucy + "/admin/campaign/" + campID + "/settings");
            fullPageWaiter();
        }
        driver.findElement(By.id("new-scenario-button")).click();
        fullPageWaiter();
        scenarioTemplateSearchingThenAdding("Awareness Only");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,\"Please don't forget to configure the awareness website.\")]")));
        scenarioTemplateSearchingThenAdding(awarenessScenarioType, awarenessScenarioTemplate);
        //New scenario page - Awareness options

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CampaignAwarenessEditForm")));
        fullPageWaiter();
        //driver.findElement(By.xpath("CampaignAwarenessEditForm_riskLevel"))
        driver.findElement(By.id("CampaignAwarenessEditForm_riskLevel")).clear();
        driver.findElement(By.id("CampaignAwarenessEditForm_riskLevel")).sendKeys(riskLevel);

        checkBoxWithID(websiteEnabled, "CampaignAwarenessEditForm_publish");
        if (websiteEnabled) {
            if (createAwarenessTrainingDiploma) {
                assertTrue("No Awareness Training Diploma for this Awareness Template", driver.findElement(By.id("CampaignAwarenessEditForm_certificate")).isEnabled());
                //wait.until(ExpectedConditions.elementToBeClickable(By.id("CampaignAwarenessEditForm_certificate")));
                checkBoxWithID(createAwarenessTrainingDiploma, "CampaignAwarenessEditForm_certificate");
            }
        }
        checkBoxWithID(doNotSendEmails, "CampaignAwarenessEditForm_notSend");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Save')]")));
        driver.findElement(By.xpath("//button[contains(.,'Save')]")).click();
        fullPageWaiter();
        //Scenario page saved
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(.,'Settings updated.')]")));
        //Website if enabled
        if (websiteEnabled) {

            driver.findElement(By.xpath("//a[contains(.,'Base Settings')]/../..//a[contains(.,'Website')]")).click();

            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[@role=\"presentation\"]")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CampaignAwarenessWebsiteEditForm_content")));
            driver.findElement(By.id("CampaignAwarenessWebsiteEditForm_domain")).clear();
            driver.findElement(By.id("CampaignAwarenessWebsiteEditForm_domain")).sendKeys(domain);
            //Quiz option
            if (quizOption) {
                assertTrue("No Quiz option for this Awareness Template", driver.findElement(By.id("CampaignAwarenessWebsiteEditForm_quiz")).isEnabled());
                checkBoxWithID(quizOption, "CampaignAwarenessWebsiteEditForm_quiz");

            } else {
                checkBoxWithID(quizOption, "CampaignAwarenessWebsiteEditForm_quiz");
            }

            driver.findElement(By.xpath("//button[contains(.,\'Save\')]")).click();
            //Emergency Saving
            if (!driver.findElements(By.xpath("//span[contains(.,'Content cannot be blank.')]")).isEmpty()) {
                zzz(5000);
                driver.findElement(By.id("CampaignAwarenessWebsiteEditForm_domain")).clear();
                driver.findElement(By.id("CampaignAwarenessWebsiteEditForm_domain")).sendKeys(domain);
                //Quiz option
                if (quizOption) {
                    assertTrue("No Quiz option for this Awareness Template", driver.findElement(By.id("CampaignAwarenessWebsiteEditForm_quiz")).isEnabled());
                    checkBoxWithID(quizOption, "CampaignAwarenessWebsiteEditForm_quiz");
                }
                driver.findElement(By.xpath("//button[contains(.,\'Save\')]")).click();
            }
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text() = \"File saved.\"]")));

            //Go to SSL
            driver.findElement(By.xpath("//a[contains(.,'Base Settings')]/../..//a[contains(.,'SSL Settings')]")).click();
            if (driver.findElement(By.id("SslConfigForm_ssl")).isSelected()) {
                driver.findElement(By.id("SslConfigForm_ssl")).click();
                driver.findElement(By.xpath("//button[@type='submit']")).click();
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(.,'Settings saved.')]")));
            }

        }


    }

    public void scenarioTemplateAddingToScenarioAwarenessOnly(Boolean websiteEnabled,
                                                              Boolean quizOption,
                                                              Boolean createAwarenessTrainingDiploma,
                                                              Boolean doNotSendEmails,
                                                              String riskLevel,
                                                              String awarenessScenarioType) {

        //Please notice this method use only LUCY Based Website!
        //location check
        if (!driver.findElements(By.xpath(" //li[contains(.,\"Base Settings\")]/..//li[contains(.,'" + campName + "')]")).isEmpty()) {
            driver.get(urlLucy + "/admin/campaign/" + campID + "/settings");
            fullPageWaiter();
        }
        driver.findElement(By.id("new-scenario-button")).click();
        fullPageWaiter();
        scenarioTemplateSearchingThenAdding("Awareness Only");
        zzz(5000);
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[contains(.,\"Please don't forget to configure the awareness website.\")]")));
        scenarioTemplateSearchingThenAdding(awarenessScenarioType);
        //adding to slow
        //New scenario page - Awareness options

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CampaignAwarenessEditForm")));
        fullPageWaiter();
        //driver.findElement(By.xpath("CampaignAwarenessEditForm_riskLevel"))
        driver.findElement(By.id("CampaignAwarenessEditForm_riskLevel")).clear();
        driver.findElement(By.id("CampaignAwarenessEditForm_riskLevel")).sendKeys(riskLevel);

        checkBoxWithID(websiteEnabled, "CampaignAwarenessEditForm_publish");
        if (websiteEnabled) {
            if (createAwarenessTrainingDiploma) {
                assertTrue("No Awareness Training Diploma for this Awareness Template", driver.findElement(By.id("CampaignAwarenessEditForm_certificate")).isEnabled());
                //wait.until(ExpectedConditions.elementToBeClickable(By.id("CampaignAwarenessEditForm_certificate")));
                checkBoxWithID(createAwarenessTrainingDiploma, "CampaignAwarenessEditForm_certificate");
            }
        }
        checkBoxWithID(doNotSendEmails, "CampaignAwarenessEditForm_notSend");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Save')]")));
        driver.findElement(By.xpath("//button[contains(.,'Save')]")).click();
        fullPageWaiter();
        //Scenario page saved
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(.,'Settings updated.')]")));
        //Website if enabled
        if (websiteEnabled) {

            driver.findElement(By.xpath("//a[contains(.,'Base Settings')]/../..//a[contains(.,'Website')]")).click();

            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//span[@role=\"presentation\"]")));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("CampaignAwarenessWebsiteEditForm_content")));
            driver.findElement(By.id("CampaignAwarenessWebsiteEditForm_domain")).clear();
            driver.findElement(By.id("CampaignAwarenessWebsiteEditForm_domain")).sendKeys(domain);
            //Quiz option
            if (quizOption) {
                assertTrue("No Quiz option for this Awareness Template", driver.findElement(By.id("CampaignAwarenessWebsiteEditForm_quiz")).isEnabled());
                checkBoxWithID(quizOption, "CampaignAwarenessWebsiteEditForm_quiz");
            }
            driver.findElement(By.xpath("//button[contains(.,\'Save\')]")).click();
            //Emergency Saving
            if (!driver.findElements(By.xpath("//span[contains(.,'Content cannot be blank.')]")).isEmpty()) {
                zzz(5000);
                driver.findElement(By.id("CampaignAwarenessWebsiteEditForm_domain")).clear();
                driver.findElement(By.id("CampaignAwarenessWebsiteEditForm_domain")).sendKeys(domain);
                //Quiz option
                if (quizOption) {
                    assertTrue("No Quiz option for this Awareness Template", driver.findElement(By.id("CampaignAwarenessWebsiteEditForm_quiz")).isEnabled());
                    checkBoxWithID(quizOption, "CampaignAwarenessWebsiteEditForm_quiz");
                } else {
                    checkBoxWithID(quizOption, "CampaignAwarenessWebsiteEditForm_quiz");
                }
                driver.findElement(By.xpath("//button[contains(.,\'Save\')]")).click();
            }
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text() = \"File saved.\"]")));

            //Go to SSL
            driver.findElement(By.xpath("//a[contains(.,'Base Settings')]/../..//a[contains(.,'SSL Settings')]")).click();
            if (driver.findElement(By.id("SslConfigForm_ssl")).isSelected()) {
                driver.findElement(By.id("SslConfigForm_ssl")).click();
                driver.findElement(By.xpath("//button[@type='submit']")).click();
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(.,'Settings saved.')]")));
            }
        }
    }

    public void awarenessScenarioMailSettings () {
        //Just postfix
        // Location check
        assertTrue("Method awarenessScenarioMailSettings started from invalid page", !driver.findElements(By.xpath("//a[contains(.,'Base Settings')]/../..//a[contains(.,'Mail Settings')]")).isEmpty());

        driver.findElement(By.xpath("//a[contains(.,'Base Settings')]/../..//a[contains(.,'Mail Settings')]")).click();
        fullPageWaiter();

        // if not postfix - gagarin have to select this delivery method
        /////////
        if (!driver.findElement(By.id("MailSettingsForm_mailDeliveryMethod")).isSelected()) {
            By.xpath("//option[. = 'Internal Postfix Server']").findElement(driver.findElement(By.id("MailSettingsForm_mailDeliveryMethod"))).click();
            fullPageWaiter();
            //wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='form-group deliver-internal' and @style='display: block;']")));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("MailSettingsForm_smtpServerName")));
            driver.findElement(By.id("MailSettingsForm_smtpServerName")).clear();
            driver.findElement(By.id("MailSettingsForm_smtpServerName")).sendKeys(smtpSN);
            if (driver.findElement(By.xpath("//label[contains(.,'Use S/MIME Certificate')]")).isSelected())
                driver.findElement(By.xpath("//label[contains(.,'Use S/MIME Certificate')]")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value=\"Save\"]")));
            driver.findElement(By.xpath("//input[@value=\"Save\"]")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(.,\"Please wait while the system is checking the mail server\")]")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(.,\"Please wait while the system is checking the mail server\")]")));
            fullPageWaiter();
        }

    }

}