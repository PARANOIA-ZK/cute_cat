package com.paranoia.leetcode.easy;

/**
 * @author ZHANGKAI
 * @date 2019/9/12
 * @description https://leetcode-cn.com/problems/remove-element/
 */
public class 移除元素27 {

    public static void main(String[] args) {
//        int[] nums = new int[]{3, 2, 2, 3};
        int[] nums = new int[]{0,1,2,2,3,0,4,2};
        int i = removeElement(nums, 2);
        System.out.println("i = " + i);
        for (int j = 0; j < i; j++) {
            System.out.println("nums = " + nums[j]);
        }
    }


    public static int removeElement(int[] nums, int val) {
        if (nums.length == 0) {
            return 0;
        }
        int x = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val) {
                x++;
                continue;
            }
            nums[i] = nums[i++];
        }
        return nums.length - x;
    }
}
