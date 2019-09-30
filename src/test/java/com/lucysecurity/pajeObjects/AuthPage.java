package com.lucysecurity.pajeObjects;

import com.lucysecurity.DriverBase;
import org.openqa.selenium.By;
import com.lazerycode.selenium.util.Query;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

import static com.lazerycode.selenium.util.AssignDriver.initQueryObjects;

public class AuthPage {

    //private Query loginFormInput = new Query().defaultLocator(By.id("LoginForm_email"));
    //private Query passwordFormInput = new Query().defaultLocator(By.id("LoginForm_password"));
    //private Query submitLoginFormButton = new Query().defaultLocator(By.xpath("//button[@type=\'submit\']"));
    @FindBy (id="LoginForm_email")
    private WebElement email;


    public AuthPage() throws Exception {
        initQueryObjects(this, org.openqa.selenium.remote.RemoteWebDriver driver);
    }



}
