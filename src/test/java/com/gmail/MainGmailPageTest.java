package com.gmail;

import com.gmail.configs.IUser;
import com.gmail.configs.Params;
import com.gmail.configs.TestBase;
import com.gmail.pages.GmailMainPage;
import com.gmail.pages.MailPage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 17.01.16.
 * Time: 18:39
 */
public class MainGmailPageTest extends TestBase {
    private GmailMainPage gmailMainPage;
    private MailPage mailPage;
    private String title;
    private String body;
    private String receiver;

    @BeforeTest
    public void setUpTest() throws Exception {
        title = "Marry christmas and Happy New Year!";
        body = "This is test message.";
        receiver = Params.getTmpUserName();
    }

    @BeforeMethod
    public void setUp() throws Exception {
        gmailMainPage = openPage(IUser.TEMP_USER, Params.GM_URL, GmailMainPage.class);
        gmailMainPage.waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@gh='cm' and .='COMPOSE']")));
    }

    @Test(priority = -1)
    public void testSendMail() throws Exception {
        gmailMainPage.openNewMailForm()
                .enterReceiver(receiver)
                .enterTitle(title)
                .enterMailText(body)
                .sendMail();
        gmailMainPage.waitFor(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='no']//div[@class='vh' and contains(.,\"Your message has been sent.\")]")));
        assertTrue(gmailMainPage.isElementPresent("//div[@class='no']//div[@class='vh' and contains(.,\"Your message has been sent.\")]"));
    }

    @Test(priority = 0)
    public void testCheckNewMail() throws Exception {
        assertEquals(gmailMainPage.getElementText("(//tr[contains(@class,'zE')]//*[@class='y6']//b)[1]"), title, "Mail is not received!");
        assertTrue(gmailMainPage.getElementText("(//tr[contains(@class,'zE')]//*[@class='y2'])[1]").contains(body), "Title on main page is incorrect!");
    }

    @Test(priority = 1)
    public void testCheckMail() throws Exception {
        mailPage = gmailMainPage.openNewUnReadMessage();
        assertTrue(mailPage.isElementPresent("//h2[@class='hP' and .=\"" + title + "\"]"), "Title is incorrect!");
        assertEquals(mailPage.getMailBodyText(), body, "Body text is incorrect!");
        String senderEMailAddress = mailPage.getSenderEMailAddress();
        assertEquals(senderEMailAddress, receiver + "@gmail.com", "Receiver mail is incorrect!");
    }

    @Test(priority = 2)
    public void testReplyToTestMail() throws Exception {
        mailPage = gmailMainPage.openReceivedMail();
        mailPage.enterReplyMessage("Thanks bro! Have a great holidays!");
        mailPage.sendReply();
        assertTrue(mailPage.getElementText("(//div[@role='listitem']//div[@dir='ltr'])[2]").contains("Thanks bro! Have a great holidays!"));
    }
}
