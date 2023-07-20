package com.masq.sort;

import java.util.Arrays;

/**
 * 插入排序<br/>
 * @author masq
 */
public class InsertionSort {
    public static void main(String[] args) {
        int[] array = {7, 3, 12, 21, 13, 8, 1};
        System.out.println(Arrays.toString(insertionSort(array)));
    }

    private static int[] insertionSort(int[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            // 下标为i+1的数作为基数，依次跟前面的数比较，直到比基数小
            int current = arr[i+1];
            int preIndex = i;
            while(preIndex >= 0 && current < arr[preIndex]) {
                // 向后挪一位
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
        return arr;
    }

}
