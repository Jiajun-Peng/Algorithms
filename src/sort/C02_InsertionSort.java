package sort;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/sort-an-array/
 * 插入排序
 * 将某个数插入到已排序序列中
 */
public class C02_InsertionSort {
    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length; i++) { // 从未排序序列中拿一个数，这里循环选取未排序序列中的第一个数，作为待插入的数
            for (int j = i; j > 0; j--) { // 用选取的数从后向前依次比较交换（插入到前一个数的前面）
                if (arr[j] < arr[j - 1]) {
                    swap(arr, j, j - 1); // 当前一个数比当前数小时，需要交换位置（插入）
                } else {
                    break; // 不需要再往前插入时，及时停止（break会退出当前层的循环）
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
