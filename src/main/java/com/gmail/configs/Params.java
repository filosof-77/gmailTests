package com.gmail.configs;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 14.01.16.
 * Time: 16:57
 */
public class Params {
    public static String GM_URL;
    public static String ACCOUNT_MANAGE_HOST;

    private static String TMP_USER_NAME;
    private static String TMP_USER_PSWD;
    private static String TEST_USER_NAME;
    private static String TEST_USER_PSWD;

    static {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("gmail.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            GM_URL = prop.getProperty("mainHost");
            ACCOUNT_MANAGE_HOST = prop.getProperty("accountManageHost");
            TEST_USER_NAME = prop.getProperty("userName");
            TEST_USER_PSWD = prop.getProperty("usePswd");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getTestUserName() {
        return TEST_USER_NAME;
    }

    public static String getTestUserPswd() {
        return TEST_USER_PSWD;
    }

    public static String getTmpUserName() {
        if (TMP_USER_NAME == null) {
            return TEST_USER_NAME;
        } else
            return TMP_USER_NAME;
    }

    public static void setTmpUserName(String tmpUserName) {
        Params.TMP_USER_NAME = tmpUserName;
    }

    public static String getTmpUserPswd() {
        if (TMP_USER_NAME == null) {
            return TEST_USER_PSWD;
        } else
            return TMP_USER_PSWD;
    }

    public static void setTmpUserPswd(String tmpUserSwd) {
        Params.TMP_USER_PSWD = tmpUserSwd;
    }

}
