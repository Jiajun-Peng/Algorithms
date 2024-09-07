package dp;

import java.util.Arrays;

/*
给定一个正数数组 `nums`，将数组中的所有数分成两个集合。如果数组长度为偶数，两个集合包含的数的个数要一样多；
如果数组长度为奇数，两个集合包含的数的个数必须只差一个。尽量让两个集合的累加和接近。

求：在最接近的情况下，较小集合的累加和。
 */
public class C17_PartitionEqualSubsetSum_1 {

    public static int partitionSumLimitSubSetSize(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int sum = Arrays.stream(nums).sum();
        boolean isSameLength = nums.length % 2 == 0;
        int subSetSize = nums.length / 2;
        if (isSameLength) {
            return partitionSumLimitSubSetSizeByRecursive(nums, 0, subSetSize, sum/2);
        } else {
            int smallSet = partitionSumLimitSubSetSizeByRecursive(nums, 0, subSetSize, sum / 2);
            int bigSet = partitionSumLimitSubSetSizeByRecursive(nums, 0, subSetSize + 1, sum / 2);
            return Math.max(smallSet, bigSet);
        }
    }

    private static int partitionSumLimitSubSetSizeByRecursive(int[] nums, int index, int restSubSetSize, int restSum) {
        if (index == nums.length || restSubSetSize == 0) {
            return 0;
        }

        int notChosenSum = partitionSumLimitSubSetSizeByRecursive(nums, index + 1, restSubSetSize, restSum);
        int chosenSum = 0;
        if (nums[index] <= restSum) {
            chosenSum = nums[index] + partitionSumLimitSubSetSizeByRecursive(nums, index + 1, restSubSetSize - 1, restSum - nums[index]);
        }
        return Math.max(notChosenSum, chosenSum);
    }

    private static int partitionSumLimitSubSetSizeByDp(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }
        int sum = Arrays.stream(nums).sum();
        boolean isSameLength = nums.length % 2 == 0;
        int subSetSize = nums.length / 2;

        int[][][] dp = new int[nums.length + 1][subSetSize + 2][sum/2 + 1];
        for (int rest = 0; rest <= sum/2; rest++) {
            dp[nums.length][0][rest] = 0;
        }

        for (int index = nums.length - 1; index >= 0; index--) {
            for (int restSize = 1; restSize <= subSetSize + 1; restSize++) {
                for (int restSum = 0; restSum <= sum / 2; restSum++) {

                    int notChosenSum = dp[index + 1][restSize][restSum];
                    int chosenSum = 0;
                    if (nums[index] <= restSum) {
                        chosenSum = nums[index] + dp[index + 1][restSize - 1][restSum - nums[index]];
                    }
                    dp[index][restSize][restSum] = Math.max(notChosenSum, chosenSum);
                }
            }
        }

        if (isSameLength) {
            return dp[0][subSetSize][sum/2];
        } else {
            int smallSet = dp[0][subSetSize][sum / 2];
            int bigSet = dp[0][subSetSize + 1][sum / 2];
            return Math.max(smallSet, bigSet);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[] {100, 1, 1, 2, 2};
        System.out.println(partitionSumLimitSubSetSize(nums));
        System.out.println(partitionSumLimitSubSetSizeByDp(nums));
    }

}
