package com.paranoia.leetcode.easy;

/**
 * @author ZHANGKAI
 * @date 2019/10/25
 * @description https://leetcode-cn.com/problems/search-insert-position/
 */
public class 搜索插入位置 {

    public static void main(String[] args) {
        int[] array = new int[]{1,3,5,6,10};
        System.out.println(searchInsert(array, 8));
        int aa = 5/2;
        System.out.println(aa);
    }


    public static int searchInsert(int[] nums, int target) {
        int len = nums.length;

        if (len == 0) {
            return 0;
        }

        int left = 0;
        int right = len;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
