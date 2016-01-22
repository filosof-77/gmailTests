package com.gmail.configs;

import com.gmail.pages.LoginPage;
import com.gmail.pages.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 14.01.16.
 * Time: 16:49
 */
public class Base {
    private final DriverFramework framework = new DriverFramework();
    private WebDriver driver;

    public static <T extends Page> T getPage(WebDriver driver, Class<T> pageClass) {
        final T page = instantiatePage(driver, pageClass);
        page.driver = driver;
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.startsWith("https://")) {
            Reporter.log("httpS Issue on page: " + currentUrl);
        }
        return page;
    }

    private static <T> T instantiatePage(WebDriver driver, Class<T> pageClassToProxy) {
        try {
            try {
                Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
                return constructor.newInstance(driver);
            } catch (NoSuchMethodException e) {
                return pageClassToProxy.newInstance();
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    protected <T extends Page> T openPage(String user, String path, Class<T> pageClass) {
        driver.manage().deleteAllCookies();
        openLoggedUserPage(user);
        return openPage(path, pageClass);
    }

    private void openLoggedUserPage(String user) {
        switch (user) {
            case IUser.TEMP_USER:
                openPage(Params.ACCOUNT_MANAGE_HOST + "/ServiceLogin", LoginPage.class).logIn(Params.getTmpUserName(), Params.getTmpUserPswd());
                break;
            case IUser.TEST_USER:
                openPage(Params.ACCOUNT_MANAGE_HOST + "/ServiceLogin", LoginPage.class).logIn(Params.getTestUserName(), Params.getTestUserPswd());
                break;
            default:
                break;
        }
        new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='lE' and .='My Account']")));

    }

    /**
     * @param pageClass PageObject class, extends <code>com.gmail.pages.Page.class</code>
     * @param <T>       returned Page Object class
     * @return instance of Page Object class
     */
    protected <T extends Page> T openPage(String path, Class<T> pageClass) {
        return getPage(path, pageClass);
    }

    /**
     * @param <T>       returned Page Object class
     * @param path      The path to resource (for example, "Params.ACCOUNT_MANAGE_HOST or Params.GM_URL" etc. in future.... =) ).
     * @param pageClass PageObject class, extends <code>com.gmail.pages.Page.class</code>
     */
    private <T extends Page> T getPage(String path, Class<T> pageClass) {
        String url;
        url = "https://" + path;
        driver.get(url);
        Reporter.log("Open URL: " + url + "\tPage Class: " + pageClass.getName(), 2, true);
        return getPage(driver, pageClass);
    }

    void setUPBrowser(DesiredCapabilities caps) {
        driver = framework.getDriver(caps);
    }

    @AfterTest(alwaysRun = true)
    public void tearDownTest() {
        framework.quitDriver();
    }
}
