package com.gmail;

import com.gmail.configs.IUser;
import com.gmail.configs.Params;
import com.gmail.configs.TestBase;
import com.gmail.pages.GmailMainPage;

import static org.testng.Assert.assertTrue;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 21.01.16.
 * Time: 17:06
 */
public class AfterTests extends TestBase {

    @org.testng.annotations.Test
    public void testRemoveTestMessagesForTempUser() throws Exception {
        GmailMainPage gmailMainPage = openPage(IUser.TEMP_USER, Params.GM_URL, GmailMainPage.class);
        gmailMainPage.removeAllMails();
        assertTrue(gmailMainPage.isElementPresent("//div[@class='aRv' and .=\"Your Primary tab is empty.\"]"), "Notice about empty mailBox is not visible.");
    }
}
