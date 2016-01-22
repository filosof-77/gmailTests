package com.gmail;

/**
 * Created with Intellij IDEA
 * User: filosof_77
 * Date: 16.01.16.
 * Time: 20:09
 */
public class Test {
    public static void main(String[] args) {
        String month = "11";
        String m = Integer.toString(Integer.parseInt(month, 10), 16);

        System.out.println(m);
    }
}
