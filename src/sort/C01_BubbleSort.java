package sort;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/sort-an-array/
 * 冒泡排序
 * 将未排序序列中大的数逐步向后放置，从后往前逐步确定好
 */
public class C01_BubbleSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = arr.length - 1; i > 0; i--) { // 依次确定好未排序序列的最后一个位置的数值
            for (int j = 0; j < i; j++) { // 每次确定最后一个位置的数时，都从前向后将大的数向后移动
                if (arr[j] > arr[j+1]) { // 前面的数比后面的数大时，交换位置（大的数向后移动）
                    swap(arr, j, j + 1);
                }
            }
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
