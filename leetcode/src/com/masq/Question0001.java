package com.masq;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 1. 两数之和
 * 给定一个整数数组num和一个整数目标值target，从数组中找出和为目标值target的两个整数，并返回他们的下标<br/>
 * 可以假设每种输入只对应一个答案，但是数组中同一个元素在答案中不能同时出现<br/>
 * @author masq
 */
public class Question0001 {
    public static void main(String[] args) {
        int[] nums = {2,7,11,15};
        int target = 9;

        Solution0001 solution = new Solution0001();
//        System.out.println(Arrays.toString(solution.twoSum1(nums, target)));
        System.out.println(Arrays.toString(solution.twoSum2(nums, target)));
    }
}

class Solution0001 {


    public int[] twoSum1(int[] nums, int target) {
        // 暴力枚举，双重循环
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[0];
    }

    public int[] twoSum2(int[] nums, int target) {
        // target - index1 = index2
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // 从map中查找key为target - nums[i]的value
            if (map.containsKey(target - nums[i])) {
                return new int[]{ map.get(target - nums[i]), i};
            } else {
                map.put(nums[i], i);
            }
        }

        return new int[0];
    }
}