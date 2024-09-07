package dp;

import java.util.Arrays;

/*
近似原题：
416. 分割等和子集：https://leetcode.cn/problems/partition-equal-subset-sum

给定一个正数数组 `nums`，将数组中的所有数分成两个集合，使得两个集合的累加和尽可能接近。
求：在最接近的情况下，较小集合的累加和。

 */
public class C17_PartitionEqualSubsetSum {

    public static int partitionSum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = Arrays.stream(nums).sum();

        return partitionSumByRecursive(nums, 0, sum/2);
    }

    private static int partitionSumByRecursive(int[] nums, int index, int rest) {
        if (index == nums.length) {
            return 0;
        }
        int notChosen = partitionSumByRecursive(nums, index + 1, rest);

        int chosen = 0;
        if (nums[index] <= rest) {
            chosen = nums[index] + partitionSumByRecursive(nums, index + 1, rest - nums[index]);
        }
        // notChosen 和 chosen 都不会大于 rest
        return Math.max(notChosen, chosen); // 最接近的就是其中值更大的那个
    }

    private static int partitionSumByDp(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = Arrays.stream(nums).sum();

        int halfSum = sum / 2;
        int[][] dp = new int[nums.length + 1][halfSum + 1];
        for (int i = 0; i <= halfSum; i++) {
            dp[nums.length][i] = 0;
        }

        for (int index = nums.length - 1; index >= 0; index--) {
            for (int rest = 0; rest <= halfSum; rest++) {
                int notChosen = dp[index + 1][rest];
                int chosen = 0;
                if (nums[index] <= rest) {
                    chosen = nums[index] + dp[index + 1][rest - nums[index]];
                }
                // notChosen 和 chosen 都不会大于 rest
                dp[index][rest] = Math.max(notChosen, chosen); // 最接近的就是其中值更大的那个
            }
        }
        return dp[0][halfSum];
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, 4, 5};
        System.out.println(partitionSum(nums));
        System.out.println(partitionSumByDp(nums));
    }

}
