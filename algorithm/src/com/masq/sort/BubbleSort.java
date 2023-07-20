package com.masq.sort;

import java.util.Arrays;

/**
 * 冒泡排序<br/><br/>
 * 从前往后两两比较，将大的数放到后面<br/>
 * 平均时间复杂度 O(n<sup>2</sup>)<br/>
 * 最好情况 O(n)<br/>
 * 空间复杂度O(1)<br/>
 * @author masq
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] array = {7, 3, 12, 21, 13, 8, 1};
        System.out.println(Arrays.toString(bubbleSort(array)));
    }

    private static int[] bubbleSort(int[] array) {
        for (int i = 0; i < array.length; i++) {

            // 每次由当前下标整数对比下一位整数，将大的放到后面
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }

        return array;
    }
}
