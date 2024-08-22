package sort;

import java.util.Arrays;

/*
912. 排序数组：https://leetcode.cn/problems/sort-an-array

选择排序
从未排序序列中找个最小的数，放置到已排序（升序）序列的后一位，得以形成更长一位的有序序列，直至所有元素都有序

 */
public class C03_SelectionSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i; j < arr.length; j++) {
                minIndex = arr[minIndex] > arr[j] ? j : minIndex;
            }
            swap(arr, i, minIndex);
        }
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {9, 5, 9, 3, 7, 4, 6, 8, 2};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }

}
