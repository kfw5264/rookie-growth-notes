package com.masq.leetcode;

/**
 * 4. 寻找两个正序数组的中位数  <br/>
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。 <br/>
 * 算法的时间复杂度应该为 O(log (m+n))  <br/>
 *
 * @author masq
 */
public class Question0004 {
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 5};
        int[] nums2 = {3, 4, 7};

        Solution0004 solution = new Solution0004();
        solution.findMedianSortedArrays(nums1, nums2);
    }
}

class Solution0004 {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
       // 合并数组
        int[] nums = new int[m + n];
        System.arraycopy(nums1, 0, nums, 0, m);
        System.arraycopy(nums2, 0, nums, m, n);

        // 排序


        // 取中位数

        return 0;
    }
}


