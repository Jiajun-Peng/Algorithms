package sort;

import java.util.Arrays;

/*
912. 排序数组：https://leetcode.cn/problems/sort-an-array

归并排序
采用分治的思想:
 将一个数组分成两部分，分别对两部分进行排序，然后将两部分合并起来
    递归实现
    1. 递归地将数组分成两部分，直到数组的长度为1时，天然有序
    2. 将两个有序的数组合并成一个有序的数组
    3. 递归地将数组合并成一个有序的数组
    4. 递归的终止条件是数组的长度为1

 */
public class C04_MergeSort {

    public static void sort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        mergeSortRecursive(arr, 0, arr.length - 1); // 整体是对0 ~ n-1范围排序
    }

    // 递归实现
    private static void mergeSortRecursive(int[] arr, int l, int r) {
        if (r == l) { // 仅当整个范围只有一个数时，天然有序，停止递归
            return;
        }
        int mid = (l + r) / 2; // 并不需要严格界定在数组的中间位置只需要在近乎中间的位置将数组分隔成两个部分（数组元素个数为奇数时，左边比右边多一个）
        mergeSortRecursive(arr, l, mid); // 递归对左边部分排序
        mergeSortRecursive(arr, mid + 1, r); // 递归对右边部分排序
        merge(arr, l, mid, r); // 合并左右两边已经有序的序列
    }

    // 左右合并
    private static void merge(int[] arr, int l, int mid, int r) {
        int[] temp = new int[r - l + 1]; // 临时数组的长度只需满足能存放下(r - l + 1)个元素，超出这个大小也是可以的，只需要在重置原始数组时界定正确的范围即可
        int lp = l; // 遍历左边部分的指针
        int rp = mid + 1; // 遍历右边部分的指针
        int i = 0; // 遍历临时数组的指针

        while (lp <= mid && rp <= r) { // 左边和右边都还有数需要合并到临时数组
            temp[i++] = arr[lp] <= arr[rp] ? arr[lp++] : arr[rp++]; // 为了使排序具有稳定性，当两个数相等时，需要先将左边的数放置入临时数组
        }
        while (lp <= mid) { // 意味着左边还没合并完，右边已经合并完了，需要把左边剩下的数据合并到临时数组
            temp[i++] = arr[lp++];
        }
        while (rp <= r) { // 意味着右边还没合并完，左边已经合并完了，需要把右边剩下的数据合并到临时数组
            temp[i++] = arr[rp++];
        }

        for (i = 0; l + i <= r; i++) { // 使用临时数组中的数据重置原始数组中的数据
            arr[l + i] = temp[i];
        }
    }


    // 迭代实现
    public static void mergeSortIteration(int[] arr) {

    }

    public static void main(String[] args) {
        int[] arr = {9, 5, 9, 3, 7, 4, 6, 8, 2};
        System.out.println(Arrays.toString(arr));
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
