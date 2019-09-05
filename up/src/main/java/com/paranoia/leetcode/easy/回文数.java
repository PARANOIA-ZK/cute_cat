package com.paranoia.leetcode.easy;

import java.util.Arrays;

/**
 * @author ZHANGKAI
 * @date 2019/9/4
 * @description
 */
public class 回文数 {

    public static void main(String[] args) {
        boolean palindrome = isPalindrome(121);
        System.out.println("palindrome = " + palindrome);
    }

    public static boolean isPalindrome(int x) {
        char[] chars = String.valueOf(x).toCharArray();
        char[] reverse = new char[chars.length];
        int reverseIndex = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            reverse[reverseIndex] = chars[i];
            reverseIndex++;
        }
        return Arrays.toString(chars).equals(Arrays.toString(reverse));
    }
}
