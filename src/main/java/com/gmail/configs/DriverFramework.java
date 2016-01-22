package com.gmail.configs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 15.01.16.
 * Time: 15:18
 */
public class DriverFramework {
    private WebDriver driver;

    private WebDriver createLocalDriver(DesiredCapabilities capabilities) {
        String browserType = capabilities.getBrowserName();
        switch (browserType) {
            case "firefox":
                driver = new FirefoxDriver(capabilities);
                break;
            case "chrome":
                driver = new ChromeDriver(capabilities);
                break;
            default:
                driver = new FirefoxDriver();
                break;
        }
        driver.manage().window().maximize();
        return driver;
    }


    protected WebDriver getDriver(DesiredCapabilities capabilities) {
        // 1. WebDriver instance is not created yet
        if (driver == null) {
            return createLocalDriver(capabilities);
        }
        // 2. Browser is dead
        try {
            driver.getCurrentUrl();
        } catch (Throwable t) {
            t.printStackTrace(Params.EXCEPTION_LOG);
            return createLocalDriver(capabilities);
        }
        // . Just use existing WebDriver instance
        return driver;
    }

    public void quitDriver() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            e.printStackTrace(Params.EXCEPTION_LOG);
        }
        driver = null;
    }
}
