package com.paranoia.leetcode.easy;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHANGKAI
 * @date 2019/9/4
 * @description https://leetcode-cn.com/problems/roman-to-integer/
 */
public class 罗马数字转整数 {
    public static void main(String[] args) {
        int i = romanToInt3("LVIII");
        System.out.println("i = " + i);
    }

    public static int romanToInt(String s) {
        if (s.length() == 2) {
            if (s.startsWith("I")) {
                if (s.equals("IV")) {
                    return 4;
                } else if (s.equals("IX")) {
                    return 9;
                }
            } else if (s.startsWith("X")) {
                if (s.equals("XL")) {
                    return 40;
                } else if (s.equals("XC")) {
                    return 90;
                }
            } else if (s.startsWith("C")) {
                if (s.equals("CD")) {
                    return 400;
                } else if (s.equals("CM")) {
                    return 900;
                }
            }
        }
        Integer result = 0;
        Map<String, Integer> twoSizeMap = new HashMap<>();
        twoSizeMap.put("IV",4);
        twoSizeMap.put("IX",9);
        twoSizeMap.put("XL",40);
        twoSizeMap.put("XC",90);
        twoSizeMap.put("CD",400);
        twoSizeMap.put("CM",900);
        for (String s1 : twoSizeMap.keySet()) {
            if (s.contains(s1)){
                result += twoSizeMap.get(s1);
                s = s.replaceFirst(s1,"");
            }
        }

        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
        char[] chars = s.toCharArray();

        for (char aChar : chars) {
            result+=map.get(String.valueOf(aChar));
        }
        return result;
    }

    public int romanToInt1(String s) {
        // 算法一：判断后面数值是否大于前面
        Map<Character, Integer> romaNumber = new HashMap<>();
        romaNumber.put('I', 1);
        romaNumber.put('V', 5);
        romaNumber.put('X', 10);
        romaNumber.put('L', 50);
        romaNumber.put('C', 100);
        romaNumber.put('D', 500);
        romaNumber.put('M', 1000);

        int firstValue = 0;
        int nextValue = 0;
        int sum = 0;

        for (int i = 0; i < s.length(); i++){
            firstValue = romaNumber.get(s.charAt(i));
            if (i == s.length()-1){
                sum += firstValue;
            }else {
                nextValue = romaNumber.get(s.charAt(i+1));
                if (firstValue >= nextValue){
                    sum += firstValue;
                }else{
                    sum -= firstValue;
                }
            }
        }
        return sum;
    }


    private static int getValue(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new IllegalArgumentException("Illegal character");
        }
    }

    public static int romanToInt3(String s) {
        int result = 0, i = 0, n = s.length();
        while (i < n) {
            int current = getValue(s.charAt(i));
            if (i == n - 1 || current >= getValue(s.charAt(i + 1))) {
                result += current;
            } else {
                result -= current;
            }
            i++;
        }
        return result;
    }

}
