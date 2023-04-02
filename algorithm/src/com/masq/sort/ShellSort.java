package com.masq.sort;

import java.util.Arrays;

/**
 * 希尔排序
 * @author masq
 */
public class ShellSort {

    public static void main(String[] args) {
        int[] array = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        System.out.println(Arrays.toString(shellSort(array)));
    }

    private static int[] shellSort(int[] array) {
        int len = array.length;
        int temp, gap = len / 2;

        while(gap > 0) {
            for (int i = gap; i < len; i++) {
                temp = array[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && temp < array[preIndex]) {
                    array[preIndex + gap] = array[preIndex];
                    preIndex -= gap;
                }
                array[preIndex + gap] = temp;
            }

            gap = gap / 2;
        }
        return array;
    }

}
