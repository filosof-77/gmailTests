package com.gmail;

import com.gmail.configs.IUser;
import com.gmail.configs.Params;
import com.gmail.configs.TestBase;
import com.gmail.datadriver.GenerateDate;
import com.gmail.pages.GmailMainPage;
import com.gmail.pages.SignUpPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;
import static org.testng.Reporter.log;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 15.01.16.
 * Time: 17:58
 */
public class RegistrationPageTest extends TestBase {
    SignUpPage signUpPage;
    public static final String PATH_REGISTER_USER = Params.ACCOUNT_MANAGE_HOST + "/SignUp";
    GmailMainPage gmailMainPage;
    String dob, name, pswd;

    @BeforeTest
    public void setUp() throws Exception {
        signUpPage = openPage(PATH_REGISTER_USER, SignUpPage.class);
        pswd = GenerateDate.getPassword(8);
        dob = GenerateDate.getDob("dd-MM-yyy");
        name = GenerateDate.getElvenName(5, 8) + GenerateDate.getInteger(100, 500);
        signUpPage.changeLanguage("en");
    }

    @Test
    public void testRegisterTestUser() throws Exception {
        signUpPage.enterFirstName(GenerateDate.getMaleName())
                .enterLastName(GenerateDate.getSurname())
                .enterUserName(name)
                .enterPswd(pswd)
                .repeatPswd(pswd)
                .selectBDay(dob)
                .selectGender("Male")
                .enterPhoneNumber(GenerateDate.getPhone())
                .enterCurrentEMail("blababla98765@gmail.com")
                .enterCaptcha()
                .selectLocation("Ukraine")
                .agreeTermsOfServices(true)
                .submitRegistrationForm();

        if (signUpPage.isElementPresent("//input[@id='signupidvinput']")) {
            log("Sorry we cant create user with random data without phone confirmation. We'll use already created user.");
            gmailMainPage = openPage(IUser.TEST_USER, Params.GM_URL, GmailMainPage.class);
            Params.setTmpUserName(Params.TEST_USER_NAME);
            Params.setTmpUserPswd(Params.TEST_USER_PSWD);
        } else {
            assertTrue(signUpPage.isElementPresent("//h2[contains(.,\"Your new email address is " + name + "@gmail.com\")]"));
            gmailMainPage.continueTOGMail();
            gmailMainPage.waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='gmail-warm-welcome']")), 10);
            gmailMainPage.closeWarmBar();
            Params.setTmpUserName(name);
            Params.setTmpUserPswd(pswd);
        }
        log("New registered user username: " + Params.getTmpUserName(), true);
        log("New registered user password: " + Params.getTmpUserPswd(), true);
    }
}
