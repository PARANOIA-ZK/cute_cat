package com.paranoia.leetcode.easy;

import javax.xml.stream.util.StreamReaderDelegate;
import java.util.*;

/**
 * @author ZHANGKAI
 * @date 2019/9/6
 * @description https://leetcode-cn.com/problems/valid-parentheses/
 */
public class 有效括号 {

    private static List<String> wrongBegin;

    static {
        wrongBegin = new ArrayList<>();
        Collections.addAll(wrongBegin, ")", "}", "]");
    }

    public static void main(String[] args) {
        String s1 = "()";
        String s2 = "()[]{}";
        String s3 = "(]";
        String s4 = "([)]";
        String s5 = "{[]}";
        String s6 = "()[]{}";
        String s7 = "";
        String s8 = "(([]){})";
        String s9 = "{}{}()[]";
        String s10 = "{()}((";
        String s11 = "{}{{}}";
//        System.out.println("isValid(s1) = " + isValid(s1));
//        System.out.println("isValid(s2) = " + isValid(s2));
//        System.out.println("isValid(s3)false = " + isValid(s3));
//        System.out.println("isValid(s4)false = " + isValid(s4));
//        System.out.println("isValid(s5) = " + isValid(s5));
//        System.out.println("isValid(s6) = " + isValid(s6));
//        System.out.println("isValid(s7) = " + isValid(s7));
//        System.out.println("isValid(s8) = " + isValid(s8));
//        System.out.println("isValid(s9) = " + isValid(s9));
//        System.out.println("isValid(s10)false = " + isValid(s10));
        System.out.println("isValid(s11) = " + isValid(s11));

        Solution solution = new Solution();
        boolean valid = solution.isValid(s10);
        System.out.println("valid = " + valid);
    }

    public static boolean isValid(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }
        if (s.length() == 0) {
            return true;
        }

        String firstHalf = String.valueOf(s.charAt(0));
//        if (wrongBegin.indexOf(firstHalf) != -1) {
//            return false;
//        }

        String firstHalfReverseString = getReverseString(firstHalf);
        int firstHalfReverseStringIndex = s.lastIndexOf(firstHalfReverseString);
        if (firstHalfReverseStringIndex == 1) {
            return true;
        }
        String substring;
        String restString;
        try {
            substring = s.substring(1, firstHalfReverseStringIndex);
            restString = s.substring(firstHalfReverseStringIndex + 1);
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }

        return isValid(substring) && isValid(restString);
    }


    private static String getReverseString(String tar) {
        switch (tar) {
            case "(":
                return ")";
            case "{":
                return "}";
            case "[":
                return "]";
            case ")":
                return "(";
            case "}":
                return "{";
            case "]":
                return "[";
            default:
                return "";
        }
    }
}











class Solution {

    // Hash table that takes care of the mappings.
    private HashMap<Character, Character> mappings;

    // Initialize hash map with mappings. This simply makes the code easier to read.
    public Solution() {
        this.mappings = new HashMap<>();
        this.mappings.put(')', '(');
        this.mappings.put('}', '{');
        this.mappings.put(']', '[');
    }

    public boolean isValid(String s) {

        // Initialize a stack to be used in the algorithm.
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            // If the current character is a closing bracket.
            if (this.mappings.containsKey(c)) {

                // Get the top element of the stack. If the stack is empty, set a dummy value of '#'
                char topElement = stack.empty() ? '#' : stack.pop();

                // If the mapping for this bracket doesn't match the stack's top element, return false.
                if (topElement != this.mappings.get(c)) {
                    return false;
                }
            } else {
                // If it was an opening bracket, push to the stack.
                stack.push(c);
            }
        }

        // If the stack still contains elements, then it is an invalid expression.
        return stack.isEmpty();
    }
}



















