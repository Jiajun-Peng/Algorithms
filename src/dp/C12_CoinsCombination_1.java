package dp;

/*
377. 组合总和 Ⅳ: https://leetcode.cn/problems/combination-sum-iv
 */
public class C12_CoinsCombination_1 {

    public static int coinsCombination(int[] coins, int target) {
        if (coins == null || coins.length == 0 || target < 0) {
            return 0;
        }

        return coinsCombinationRecursive(coins, target, 0);
    }

    private static int coinsCombinationRecursive(int[] coins, int restTarget, int index) {
        if (index == coins.length) return restTarget == 0 ? 1 : 0;

        int result = 0;
        for (int chosen = 0; chosen * coins[index] <= restTarget; chosen++) {
            result += coinsCombinationRecursive(coins, restTarget - chosen * coins[index], index + 1);
        }
        return result;
    }

    private static int coinsCombinationByDp(int[] coins, int target) {
        if (coins == null || coins.length == 0 || target < 0) {
            return 0;
        }
        int[][] dp = new int[coins.length + 1][target + 1];
        dp[coins.length][0] = 1;

        for (int index = coins.length - 1; index >= 0; index--) {
            for (int restTarget = 0; restTarget <= target; restTarget++) {
                int result = 0;
                for (int chosen = 0; chosen * coins[index] <= restTarget; chosen++) {
                    result += dp[index + 1][restTarget - chosen * coins[index]];
                }
                dp[index][restTarget] = result;
            }
        }
        return dp[0][target];
    }

    private static int coinsCombinationByDpWithOptimize(int[] coins, int target) {
        if (coins == null || coins.length == 0 || target < 0) {
            return 0;
        }
        int[][] dp = new int[coins.length + 1][target + 1];
        dp[coins.length][0] = 1;

        for (int index = coins.length - 1; index >= 0; index--) {
            for (int restTarget = 0; restTarget <= target; restTarget++) {
                dp[index][restTarget] = dp[index + 1][restTarget];
                if (restTarget - coins[index] >= 0) {
                    dp[index][restTarget] += dp[index][restTarget - coins[index]];
                }
            }
        }
        return dp[0][target];
    }



    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        int target = 4;
        System.out.println(coinsCombination(nums, target));
        System.out.println(coinsCombinationByDp(nums, target));
        System.out.println(coinsCombinationByDpWithOptimize(nums, target));
    }
}
