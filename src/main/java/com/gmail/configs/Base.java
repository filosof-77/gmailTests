package com.gmail.configs;

import com.gmail.pages.LoginPage;
import com.gmail.pages.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 14.01.16.
 * Time: 16:49
 */
public class Base {
    private static final Logger log = LoggerFactory.getLogger("TestNG");
    private static LoggingPreferences logPrefs;
    protected final DriverFramework framework = new DriverFramework();
    private WebDriver driver;


    @BeforeSuite(alwaysRun = true)
    public void setUpSuite() {
        createLogDirectories();
        logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.INFO);
        logPrefs.enable(LogType.DRIVER, Level.INFO);
    }

    private void createLogDirectories() {
        log.debug("mkdir LOG_FILES_DIRECTORY: " + new File(Params.LOG_FILES_DIRECTORY).mkdirs());
        log.debug("mkdir SCREENSHOT_FILES_DIRECTORY: " + new File(Params.SCREENSHOT_FILES_DIRECTORY).mkdirs());
//        log.debug("mkdir HAR_FILES_DIRECTORY: " + new File(Params.HAR_FILES_DIRECTORY).mkdirs());
    }

    protected void writeLogs(ITestResult result) {
        if (!result.isSuccess()) {
            String currentUrl = getCurrentUrl();
            String testName = result.getName();
            Reporter.log(testName + " - FAILED on page: " + currentUrl, true);
            log.error(testName + " - FAILED on page: " + currentUrl);
            writeLogs(Params.LOG_FILES_DIRECTORY + testName + getCurrentTime("D-H-m-s") + "-browser.log", LogType.BROWSER);
            writeLogs(Params.LOG_FILES_DIRECTORY + testName + getCurrentTime("yyyy-MM-dd") + "-driver.log", LogType.DRIVER);
            writePageSource(Params.LOG_FILES_DIRECTORY + testName + getCurrentTime("yyyy-MM-dd") + "-page-source.html");
        }
    }

    private void writeLogs(String fileName, String logType) {
        if (driver != null) {
            try {
                FileOutputStream fos = new FileOutputStream(fileName, true);
                PrintWriter out = new PrintWriter(fos);
                driver.manage().logs().get(logType).getAll().forEach(out::println);
                out.close();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    private void writePageSource(String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName, true);
            PrintWriter out = new PrintWriter(fos);
            out.println(driver.getPageSource());
            out.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static String getCurrentTime(String format) {
        DateFormat df = new SimpleDateFormat(format);
        Date getToday = Calendar.getInstance().getTime();
        return df.format(getToday);
    }

    protected String getCurrentUrl() {
        try {
            return driver.getCurrentUrl();
        } catch (Exception e) {
            return null;
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
                openPage(Params.ACCOUNT_MANAGE_HOST + "/ServiceLogin", LoginPage.class).logIn(Params.TEST_USER_NAME, Params.TEST_USER_PSWD);
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

    protected void setUPBrowser(DesiredCapabilities caps) {
        driver = framework.getDriver(caps);
    }

    public static <T extends Page> T getPage(WebDriver driver, Class<T> pageClass) {
        final T page = instantiatePage(driver, pageClass);
        page.driver = driver;
        log.info("Open page " + pageClass.getSimpleName());
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


    @AfterSuite(alwaysRun = true)
    public void tearDownTest() {
        framework.quitDriver();
    }

}
