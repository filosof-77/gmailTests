package com.gmail.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import java.util.concurrent.TimeUnit;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 14.01.16.
 * Time: 18:48
 */
public class Page {
    public WebDriver driver;

    public String getElementAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    public WebElement findElement(By by) {
        try {
            waitFor(ExpectedConditions.visibilityOfElementLocated(by), 5);
        } catch (Exception ignored) {
        }
        return driver.findElement(by);
    }

    public String getElementText(String xpath) {
        return findElementByXpath(xpath).getText();
    }

    public void waitFor(ExpectedCondition<?> condition, final int sec) {
        try {
            new FluentWait<>(driver).
                    withTimeout(sec, TimeUnit.SECONDS).
                    pollingEvery(500, TimeUnit.MILLISECONDS).
                    ignoring(NoSuchElementException.class).
                    until(condition);
        } catch (Exception ignored) {
        }
    }

    public void waitFor(ExpectedCondition<?> condition) {
        waitFor(condition, 10);
    }

    public WebElement findElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public WebElement findElementById(String id) {
        return driver.findElement(By.id(id));
    }

    public boolean isElementPresent(String xpath) {
        Reporter.log("Check element " + xpath + " present on page " + driver.getCurrentUrl() + "\n");
        return driver.findElements(By.xpath(xpath)).size() > 0;
    }


    /**
     * @param lang value from dropdown
     */
    public void changeLanguage(String lang) {
        WebElement elementById = findElementById("lang-chooser");
        new Select(elementById).selectByValue(lang);
    }

    protected void selectCheckbox(WebElement checkbox) {
        if (!isElementSelected(checkbox)) {
            checkbox.click();
        }
    }

    protected void deselectCheckbox(WebElement checkbox) {
        if (isElementSelected(checkbox)) {
            checkbox.click();
        }
    }

    public boolean isElementSelected(WebElement element) {
        try {
            return element.isSelected();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
