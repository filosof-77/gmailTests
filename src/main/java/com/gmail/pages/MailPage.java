package com.gmail.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.openqa.selenium.By.xpath;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 17.01.16.
 * Time: 19:36
 */
public class MailPage extends Page {


    public MailPage enterReplyMessage(String bodyTxt) {
        findElement(xpath("//span[contains(@class,'ams')][1]")).click();
        waitFor(visibilityOfElementLocated(xpath("//div[@role='textbox' and contains(@class,'Am')]")));
        WebElement el = findElementByXpath("//div[@role='textbox' and contains(@class,'Am')]");
        el.clear();
        el.sendKeys(bodyTxt);
        return this;
    }

    public String getMailBodyText() {
        return getElementText("//div[@class='a3s']/div[@dir]");
    }

    public String getSenderEMailAddress() {
        String elementText = getElementAttribute(findElementByXpath("//h3[@class='iw']/span[@class='go']"), "textContent");
        Pattern compile = Pattern.compile("<(.*?)>");
        Matcher m = compile.matcher(elementText);
        return m.group(1);
    }

    public MailPage sendReply() {
        findElementByXpath("//td[contains(@class,'gU') and contains(@class,'Up')]//div[@role='button' and contains(.,\"Send\")]").click();
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='vh' and .='Your message has been sent.']")));
        return this;
    }
}
