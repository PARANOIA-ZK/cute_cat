package com.paranoia.leetcode.easy;

/**
 * @author ZHANGKAI
 * @date 2019/9/5
 * @description
 */
public class 最长公共前缀 {

    public static void main(String[] args) {
        String[] param = new String[]{"flower", "flow", "flight"};
        String[] param1 = new String[]{"dog", "racecar", "car"};
        String[] param2 = new String[]{"c", "c"};
        String[] param3 = new String[]{"c", "acc", "ccc"};
        System.out.println("param:fl-->" + longestCommonPrefix(param));
        System.out.println("param1:-->" + longestCommonPrefix(param1));
        System.out.println("param2:c-->" + longestCommonPrefix(param2));
        System.out.println("param3:-->" + longestCommonPrefix(param3));
    }

    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        } else if (strs.length == 1) {
            return strs[0];
        }
        String firstStr = strs[0];
        String commonPre = "";
        String firstSubs;
        for (int i = 1; i <= firstStr.length(); i++) {
            firstSubs = firstStr.substring(0, i);
            boolean contains = false;
            if (!strs[1].startsWith(firstSubs)) {
                break;
            }
            for (int j = 2; j < strs.length; j++) {
                contains = strs[j].startsWith(firstSubs);
                if (contains) {
                    commonPre = firstSubs;
                    continue;
                }
                break;
            }
            if (!contains) {
//                if (firstStr.length() != 1) {
                commonPre = commonPre.substring(0, i - 1);
//                }
                break;
            }
        }
        return commonPre;
    }

}
