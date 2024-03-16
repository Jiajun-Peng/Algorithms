package leetcode_top_100;

public class Code_004_MedianOfTwoSortedArrays {

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int totalLength = nums1.length + nums2.length;
        int i = totalLength % 2;
        boolean isOdd = i != 0;

        if (isOdd) {
            int mid = totalLength / 2 + 1;
            return g(nums1, nums2, mid);
        } else {
            int leftMid = totalLength / 2;
            int rightMid = totalLength / 2 + 1;

            int midSum = g(nums1, nums2, leftMid) + g(nums1, nums2, rightMid);
            return (double) midSum / 2;
        }
    }

    private int g(int[] nums1, int[] nums2, int k) {


        return 0;
    }

}
