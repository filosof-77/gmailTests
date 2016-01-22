package com.gmail.configs;

import java.io.*;
import java.util.Properties;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 14.01.16.
 * Time: 16:57
 */
public class Params {
    public static final String LOG_FILES_DIRECTORY = "surefire-reports/html/logs/";
    public static final String SCREENSHOT_FILES_DIRECTORY = "surefire-reports/html/screenshots/";

    public static PrintStream EXCEPTION_LOG;
    public static String GM_URL;
    public static String ACCOUNT_MANAGE_HOST;

    private static String TMP_USER_NAME;
    private static String TMP_USER_PSWD;
    public static String TEST_USER_NAME;
    public static String TEST_USER_PSWD;


    static {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("gmail.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            EXCEPTION_LOG = new PrintStream(new File(LOG_FILES_DIRECTORY + "exceptions.log"));
            GM_URL = prop.getProperty("mainHost");
            ACCOUNT_MANAGE_HOST = prop.getProperty("accountManageHost");
            TEST_USER_NAME = prop.getProperty("userName");
            TEST_USER_PSWD = prop.getProperty("usePswd");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Can not read \"gmail.properties\"\" property file...");
        }
    }

    public static String getTmpUserName() {
        if (TMP_USER_NAME == null) {
            return TEST_USER_NAME;
        } else
            return TMP_USER_NAME;
    }

    public static String getTmpUserPswd() {
        if (TMP_USER_NAME == null) {
            return TEST_USER_PSWD;
        } else
            return TMP_USER_PSWD;
    }

    public static void setTmpUserName(String tmpUserName) {
        Params.TMP_USER_NAME = tmpUserName;
    }

    public static void setTmpUserPswd(String tmpUserSwd) {
        Params.TMP_USER_PSWD = tmpUserSwd;
    }

}
