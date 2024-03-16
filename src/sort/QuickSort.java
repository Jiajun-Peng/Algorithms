package sort;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/sort-an-array/
 * 快排
 * 快排也采用了分治的思想，通过先确定某个中间位置的数M的位置，将中间位置的数作为分界点（先排序好中间位置的数），把序列分为小于M的部分和大于M的部分
 * 再分别将左边部分排好序和右边部分排好序最后即可整体有序
 */
public class QuickSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int l, int r) {
        if (l == r) {
            return;
        }
        int divideTarget = arr[r - 1];
        DividedRange dividedRange = doDivide(arr, l, r);
        quickSort(arr, l, dividedRange.left - 1);
        quickSort(arr, dividedRange.right + 1, r);
    }

    private static DividedRange doDivide(int[] arr, int l, int r) {

        int less = l - 1;
        int greater = r + 1;
        int divideTarget = arr[r - 1];
        for (int i = 0; i < greater; i++) {
            if (arr[i] < divideTarget){
                swap(arr, i, ++less);
            } else if (arr[i] > divideTarget){
                swap(arr, i--, --greater);
            }
        }
        return new DividedRange(less + 1, greater - 1);
    }

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static class DividedRange {
        int left;
        int right;

        public DividedRange(int less, int greater) {
            left = less;
            right = greater;
        }
    }

    public static void main(String[] args) {
        int[] arr = {9, 5, 9, 3, 7, 4, 6, 8, 2};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
