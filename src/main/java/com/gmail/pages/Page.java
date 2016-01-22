package com.gmail.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
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

    public String getElementText(By by) {
        waitFor(ExpectedConditions.visibilityOfAllElementsLocatedBy(by), 5);
        return driver.findElement(by).getText();
    }

    public String getElementAttribute(WebElement element, String attribute) {
        return element.getAttribute(attribute);
    }

    public WebElement findElement(By by) {
        try {
            wait_(5).until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception ignored) {
        }
        return driver.findElement(by);
    }

    public WebDriverWait wait_(int sec) {
        return new WebDriverWait(driver, sec);
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
        } catch (Exception e) {
            e.printStackTrace(); //todo add log file
        }
    }

    public void enterText(WebElement element, String text) {
        Reporter.log("Enter text " + text + " to element on page " + driver.getCurrentUrl() + "\n");
        element.clear();
        element.sendKeys(text);
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
