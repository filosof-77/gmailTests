package com.gmail.pages;

import com.gmail.configs.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 15.01.16.
 * Time: 16:53
 */
public class LoginPage extends Page {

    public LoginPage enterUserName(String tmpUserName) {
        if (isElementPresent("//*[@id='Passwd']")) {
            signInWithDiffAccount();
        }
        WebElement element = findElement(By.id("Email"));
        element.clear();
        element.sendKeys(tmpUserName);
        findElement(By.id("next")).click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.id("Passwd")));
        return this;
    }

    private LoginPage signInWithDiffAccount() {
        findElementById("account-chooser-link").click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.id("account-chooser-add-account")));
        findElementById("account-chooser-add-account").click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.id("Email")));
        return this;
    }

    public LoginPage enterPswd(String tmpUserPswd) {
        WebElement element = findElementById("Passwd");
        element.clear();
        element.sendKeys(tmpUserPswd);
        if (isElementPresent("//input[@id='PersistentCookie']")) {
            deselectCheckbox(driver.findElement(By.id("PersistentCookie")));
        }
        return this;
    }

    public GmailMainPage submitForm() {
        findElementById("signIn").click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='gb_Jb']/a[@title='Gmail' or @title='Google Account settings']")), 5);
        return Base.getPage(driver, GmailMainPage.class);
    }

    public GmailMainPage logIn(String name, String pswd) {
        if (isElementPresent("//*[@id='Passwd']") && isElementPresent("//span[@id='reauthEmail' and contains(.,'" + name + "')]")) {
            enterPswd(pswd);
            submitForm();
        } else if (isElementPresent("//span[@id='reauthEmail' and contains(.,'" + name + "')]")) {
            signInWithDiffAccount();
            enterUserName(name);
            enterPswd(pswd);
            submitForm();
        } else {
            enterUserName(name);
            enterPswd(pswd);
            submitForm();
        }
        return Base.getPage(driver, GmailMainPage.class);
    }
}
