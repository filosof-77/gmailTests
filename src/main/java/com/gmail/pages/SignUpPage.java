package com.gmail.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 15.01.16.
 * Time: 18:05
 */
public class SignUpPage extends Page {

    public SignUpPage enterFirstName(String name) {
        WebElement firstNameTf = findElementById("FirstName");
        enterText(firstNameTf, name);
        return this;
    }

    public SignUpPage enterLastName(String name) {
        WebElement firstNameTf = findElementById("LastName");
        enterText(firstNameTf, name);
        return this;
    }

    public SignUpPage enterUserName(String name) {
        if (isElementPresent("//*[@id='EmailAddress']")) {
            findElement(By.xpath("//a[@id='signup-with-gmail-link']")).click();
            waitFor(ExpectedConditions.visibilityOfElementLocated(By.id("GmailAddress")));
        }
        WebElement firstNameTf = findElementById("GmailAddress");
        enterText(firstNameTf, name);
        return this;
    }

    public SignUpPage enterPswd(String pswd) {
        WebElement pswdTf = findElementById("Passwd");
        enterText(pswdTf, pswd);
        return this;
    }

    public SignUpPage repeatPswd(String pswd) {
        WebElement pswdRepTf = findElementById("PasswdAgain");
        enterText(pswdRepTf, pswd);
        return this;
    }

    public SignUpPage selectBDay(String dob) {
        String[] dateParts = dob.split("-");
        enterBDDay(dateParts[0]);
        selectBDMonth(dateParts[1]);
        enterBDYear(dateParts[2]);
        return this;
    }

    private void enterBDYear(String year) {
        WebElement element = findElementById("BirthYear");
        enterText(element, year);
    }

    private void selectBDMonth(String month) {
        WebElement selector = findElementById("BirthMonth");
        selector.click();
        waitFor(ExpectedConditions.visibilityOf(selector.findElement(By.xpath("./div[contains(@class,'goog-menu-vertical')]"))));
        String m = Integer.toString(Integer.parseInt(month, 10), 16);
        selector.findElement(By.xpath("./div[@role='listbox']/div[@id=':" + m + "']")).click();
    }

    private void enterBDDay(String day) {
        WebElement element = findElementById("BirthDay");
        enterText(element, day);
    }

    /**
     * @param gender Male
     *               Female
     *               Other
     * @return this page
     */
    public SignUpPage selectGender(String gender) {
        WebElement element = findElement(By.id("Gender"));
        element.click();
        waitFor(ExpectedConditions.visibilityOf(element.findElement(By.xpath("./div[contains(@class,'goog-menu-vertical')]"))));

        switch (gender) {
            case "Male":
                element.findElement(By.id(":f")).click();
                break;
            case "Female":
                element.findElement(By.id(":e")).click();
                break;
            case "Other":
                element.findElement(By.id(":g")).click();
                break;
            default:
                element.findElement(By.id(":f")).click();
                break;
        }
        return this;
    }

    public SignUpPage enterPhoneNumber(String phone) {
        WebElement phoneNumberTf = findElementById("RecoveryPhoneNumber");
        phoneNumberTf.sendKeys(phone);
        return this;
    }

    public SignUpPage enterCurrentEMail(String email) {
        WebElement element = findElementById("RecoveryEmailAddress");
        enterText(element, email);
        return this;
    }

    public SignUpPage enterCaptcha() {
        System.out.println("Please enter recaptcha manually");

        wait_(20);
        return this;
    }

    public SignUpPage selectLocation(String location) {
        WebElement element = findElementById("CountryCode");
        element.click();
        waitFor(ExpectedConditions.visibilityOf(element.findElement(By.xpath("./div[contains(@class,'goog-menu-vertical')]"))));
        element.findElement(By.xpath(".//div[@role='option']/div[contains(.,'" + location + "')]")).click();
        waitFor(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'goog-menu-vertical')]")));
        return this;
    }

    public SignUpPage agreeTermsOfServices(boolean b) {
        WebElement element = findElementById("TermsOfService");
        if (b)
            selectCheckbox(element);
        else deselectCheckbox(element);
        return this;
    }

    public void submitRegistrationForm() {
        findElementById("submitbutton").click();
    }


}
