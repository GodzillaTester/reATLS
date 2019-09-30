package com.lucysecurity;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DriverBase {

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

    private RemoteWebDriver driver;

    //private static List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<DriverFactory>());
    //private static ThreadLocal<DriverFactory> driverFactoryThread;

    //public static RemoteWebDriver getDriver() throws Exception {
    //    return driverFactoryThread.get().getDriver();
    //}


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

        driver.manage().deleteAllCookies();
        driver.close();
        driver.quit();
    }
}
