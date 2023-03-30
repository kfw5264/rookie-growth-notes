package com.masq.leetcode;

import java.util.*;

/**
 * 3. 无重复字符的最长字串  <br/>
 * 给定一个字符串s，找出其中不含有重复字符的最长字串的长度。 <br/>
 * 字符由英文字母、数字、符号和字符组成 <br/>
 *
 * @author masq
 */
public class Question0003 {
    public static void main(String[] args) {
        String s = "au";
        Solution0003 solution = new Solution0003();
        System.out.println(solution.lengthOfLongestSubstring(s));
    }
}

class Solution0003 {
    public int lengthOfLongestSubstring(String s) {
        // 只有一个字符的时候直接返回1
        int len = s.length();
        if (len == 1) {
            return len;
        }
        int cursor = 0, ans = 0;
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < len; i++) {
            // 左指针每次向右移动一个位置，移除第一个元素
            if (i != 0) {
                set.remove(s.charAt(i - 1));
            }
            // 游标向右移动，直到set中包含当前游标所在位置的元素
            while(cursor + 1 <= len && !set.contains(s.charAt(cursor))) {
                set.add(s.charAt(cursor));
                cursor ++;
                System.out.println(set);
            }

            ans = Math.max(ans, cursor - i);
        }

        return ans;
    }
}
