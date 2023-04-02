package com.masq.sort;

import java.util.Arrays;

/**
 * 选择排序<br/>
 * @author masq
 */
public class SelectSort {

    public static void main(String[] args) {
        int[] array = {7, 3, 12, 21, 13, 8, 1};
        System.out.println(Arrays.toString(selectSort(array)));
    }

    private static int[] selectSort(int[] array) {
        // 每次循环从数组中找到最小的一个值与下标为i的数据交换位置
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[minIndex] > array[j]) {
                    minIndex = j;
                }
            }
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }

        return array;
    }
}
