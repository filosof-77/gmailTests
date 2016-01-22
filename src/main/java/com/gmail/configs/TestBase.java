package com.gmail.configs;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 14.01.16.
 * Time: 16:49
 */
public class TestBase extends Base {

    @BeforeTest(alwaysRun = true)
    @Parameters("BROWSER")
    public void setUpMethod(String browser) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setBrowserName(browser);
        setUPBrowser(caps);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownMethod(ITestResult result) {
        writeLogs(result);
    }


}
