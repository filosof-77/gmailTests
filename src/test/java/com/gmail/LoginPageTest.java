package com.gmail;

import com.gmail.configs.Params;
import com.gmail.configs.TestBase;
import com.gmail.pages.GmailMainPage;
import com.gmail.pages.LoginPage;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 14.01.16.
 * Time: 18:55
 */
public class LoginPageTest extends TestBase {
    public static final String PATH_LOGIN = Params.GM_URL;
    com.gmail.pages.LoginPage loginPage;


    @BeforeTest
    public void setUp() throws Exception {
        loginPage = openPage(PATH_LOGIN, LoginPage.class);
        loginPage.changeLanguage("en");
    }

    @Test
    public void testLogInByUser() throws Exception {
        GmailMainPage gmailMainPage = loginPage.enterUserName(Params.getTmpUserName())
                .enterPswd(Params.getTmpUserPswd())
                .submitForm();
        assertTrue(gmailMainPage.isElementPresent("//div[@class='gb_Jb']/a[@title='Gmail' or @title='Google Account settings']"), "Main Gmail logo is not visible.");
    }
}