package com.gmail.pages;

import com.gmail.configs.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 15.01.16.
 * Time: 17:10
 */
public class GmailMainPage extends Page {


    public GmailMainPage openNewMailForm() {
        WebElement element = findElement(By.xpath("//div[@gh='cm' and .='COMPOSE']"));
        element.click();
        new WebDriverWait(driver, 50).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='AD']")));
        return this;
    }

    public GmailMainPage enterReceiver(String mail) {
        if (isElementPresent("//table[@class='GS' and not(@tabindex='-1')]")) {
            findElementByXpath("//div[contains(@class,'oL') and .=\"Recipients\"]").click();
            waitFor(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//textarea[@class='vO' and @name='to']"))));
        }
        WebElement field = findElementByXpath("//textarea[@class='vO' and @name='to']");
        field.clear();
        field.sendKeys(mail + "@gmail.com");
        field.sendKeys(Keys.RETURN);
        return this;
    }

    public GmailMainPage enterTitle(String title) {
        WebElement titleTf = findElementByXpath("//input[@name='subjectbox']");
        titleTf.clear();
        titleTf.sendKeys(title);
        return this;
    }

    public GmailMainPage enterMailText(String text) {
        WebElement mailBodyTf = findElementByXpath("//div[@role='textbox' and @aria-label='Message Body']");
        mailBodyTf.click();
        mailBodyTf.clear();
        mailBodyTf.sendKeys(text);
        return this;
    }

    public GmailMainPage sendMail() {
        WebElement sendMailBtn = findElementByXpath("//div[@role='button' and .='Send']");
        sendMailBtn.click();
        waitFor(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='AD']")), 5);
        return this;
    }

    public MailPage openNewUnReadMessage() {
        WebElement newMessageForMe = findElementByXpath("//tr[contains(@class,'zE')]//div[@class='yW']/span[@name='me']");
        newMessageForMe.click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@class='Bu']")));
        return Base.getPage(driver, MailPage.class);
    }

    public MailPage openReceivedMail() {
        findElementByXpath("//tr[contains(@class,'zA')][1]").click();
        return Base.getPage(driver, MailPage.class);
    }

    public GmailMainPage continueTOGMail() {
        findElementById("submitbutton").click();
        return this;
    }

    public GmailMainPage closeWarmBar() {
        findElementById("gmail-warm-welcome").click();
        return this;
    }

    public GmailMainPage removeAllMails() {
        selectAllMessagesCbx();
        removeSelectedMails();
        return this;
    }

    private void removeSelectedMails() {
        WebElement element = findElementByXpath("//div[@data-tooltip='Delete']");
        element.click();
        new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='bofITb' and .=\"The conversation has been moved to the Trash and will be permanently deleted in 30 days.\"]")));
//        waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='bofITb' and .=\"The conversation has been moved to the Trash and will be permanently deleted in 30 days.\"]")));
    }

    private void selectAllMessagesCbx() {
        WebElement element = findElementByXpath("//div[@class='T-Jo-auh' and @role='presentation']");
        element.click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-tooltip='Delete']")));
    }
}
