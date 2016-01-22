package com.gmail.datadriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 15.01.16.
 * Time: 18:55
 */
public class GenerateDate {
    public static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUYWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SYMBOLS = "!@#$%^&*()-_=+[]~\"";
    private static final String STRING_DELIMITER = ",";
    private static final long ONE_YEAR_AS_MILLISECONDS = 365L * 24L * 60L * 60L * 1000L;
    private static final long TWENTY_FIVE_YEARS_AS_MILLISECONDS = 25 * ONE_YEAR_AS_MILLISECONDS;
    private static final long FIFTY_YEARS_AS_MILLISECONDS = 50 * ONE_YEAR_AS_MILLISECONDS;
    private static final String DOMAIN = "data/domain.txt";
    private static final String ROMAN_NAMES = "data/romannames.txt";
    private static final String SURNAME = "data/surname.txt";
    private static final String MALE_NAME = "data/malename.txt";
    private static final String ELVEN_NAMES = "data/elvennames.txt";


    /**
     * @param min Minimum Integer
     * @param max Maximum Integer
     * @return Random Integer
     */
    public static int getInteger(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("Start cannot exceed End.");
        }
        long range = (long) max - (long) min + 1;
        long fraction = (long) (range * new Random().nextDouble());
        return (int) (fraction + min);
    }

    public static String getInteger(int length) {
        return getString(DIGITS, length);
    }


    public static String getString(String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(new Random().nextInt(characters.length()));
        }
        return new String(text);
    }

    public static String getPhone() {
        return "98" + String.valueOf(getInteger(7));
    }

    /**
     * @return Random e-mail address
     */
    public static String getEmail(Boolean... elven_names) {
        String DomainFile = readFileToString(DOMAIN);
        String[] DomainList = DomainFile != null ? DomainFile.split(STRING_DELIMITER) : new String[0];
        assert elven_names.length <= 1;
        boolean elven = elven_names.length > 0 && elven_names[0];
        String name;
        if (elven) {
            name = getElvenName(3, 6);
        } else {
            name = getRomanName();
        }
        String list = DomainList[produceNumber(DomainList)];
        return name.toLowerCase() + "@" + list;
    }


    public static String getSurname() {
        return getWordFromFile(SURNAME);
    }

    /**
     * @return Random Male Name from list (file "MALENAME.txt")
     */
    public static String getMaleName() {
        return getWordFromFile(MALE_NAME);
    }


    public static String getWordFromFile(String fileName) {
        String stringFromFile = readFileToString(fileName);
        String[] FemaleNamesList = stringFromFile != null ? stringFromFile.split(STRING_DELIMITER) : new String[0];
        return FemaleNamesList[produceNumber(FemaleNamesList)];
    }

    private static int produceNumber(String[] list) {
        return new Random().nextInt(list.length);
    }

    public static String getRomanName() {
        NameGenerator genName = new NameGenerator(ROMAN_NAMES);
        String name = genName.compose(getInteger(2, 4)).toLowerCase();
        char[] stringArray = name.toCharArray();
        stringArray[0] = Character.toUpperCase(stringArray[0]);
        return new String(stringArray);
    }

    private static String readFileToString(String filePath) {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath)) {
            StringBuilder fileData = new StringBuilder(1000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            char[] buf = new char[1024];
            int numRead;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[1024];
            }
            reader.close();
            return fileData.toString();
        } catch (IOException io) {
            io.printStackTrace();
            return null;
        }
    }

    /**
     * @param minLetters The minimum number of letters used in name.
     * @param maxLetters The maximum number of letters used in name.
     * @return Returns composed name as a String
     */
    public static String getElvenName(int minLetters, int maxLetters) {
        String name;
        do {
            String name2 = new NameGenerator(ELVEN_NAMES).compose(getInteger(minLetters, maxLetters)).toLowerCase();
            char[] stringArray = name2.toCharArray();
            stringArray[0] = Character.toUpperCase(stringArray[0]);
            name = new String(stringArray);
        } while (name.toCharArray().length < minLetters);
        if (name.toCharArray().length < maxLetters) {
            return name;
        } else {
            return name.substring(0, maxLetters);
        }
    }

    /**
     * @param length               length of Password
     * @param enableSpecialSymbols enable or disable symbols "!@#$%^&*"
     * @return Password
     */
    public static String getPassword(int length, Boolean... enableSpecialSymbols) {
        assert enableSpecialSymbols.length <= 1;
        boolean symbols = enableSpecialSymbols.length > 0 && enableSpecialSymbols[0];
        String password;
        String string = DIGITS + LETTERS.toUpperCase() + DIGITS + LETTERS.toLowerCase();
        if (symbols) {
            password = getString(string + SYMBOLS, length);
        } else {
            password = getString(string, length);
        }

        Matcher m = Pattern.compile(".*\\d.*").matcher(password);
        if (m.find()) {
            return password;
        } else {
            String integer = String.valueOf(getString(DIGITS, 2));
            return password.replaceAll("..$", integer);
        }
    }

    public static String getRandomString(int length) {
        String string = DIGITS + LETTERS.toUpperCase() + DIGITS + LETTERS.toLowerCase();
        return getString(string, length);
    }

    /**
     * @param letters The number of letters used in name.
     * @return Returns composed name as a String
     */
    public static String getElvenName(int letters) {
        String name;
        do {
            name = getElvenName(1, 10);
        } while (name.toCharArray().length < letters);
        return name.substring(0, letters);
    }

    /**
     * Date of birth
     *
     * @param format yyyy
     *               dd
     *               mm
     */

    public static String getDob(String format) {
        long someTimeBetween25And50YearsInMilliSeconds = TWENTY_FIVE_YEARS_AS_MILLISECONDS
                + (long) (Math.random() * ((FIFTY_YEARS_AS_MILLISECONDS - TWENTY_FIVE_YEARS_AS_MILLISECONDS) + 1));

        Date d = new Date(System.currentTimeMillis() - someTimeBetween25And50YearsInMilliSeconds);

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(d);
    }

}
