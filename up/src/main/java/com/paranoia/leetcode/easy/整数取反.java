package com.paranoia.leetcode.easy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ZHANGKAI
 * @date 2019/9/4
 * @descriptionLine 23: java.lang.NumberFormatException: For input string: "9646324351"
 */
public class 整数取反 {

    public static void main(String[] args) {
        System.out.println(reverse(-123));
    }

    public static int reverse(int x) {
        boolean negative = false;
        if (x < 0) {
            x = -x;
            negative = true;
        }
        String tarString = String.valueOf(x);
        char[] chars = tarString.toCharArray();
        char[] resultChars = new char[chars.length];
        int realIndex = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == 0) {
                continue;
            }
            resultChars[realIndex] = chars[i];
            realIndex++;
        }
        String resultString = String.valueOf(resultChars);
        if (negative) {
            resultString = "-" + resultString;
        }
        int result;
        try {
            result = Integer.parseInt(resultString);
        } catch (Exception e) {
            return 0;
        }
        return result;
    }

}
