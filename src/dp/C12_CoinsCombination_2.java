package dp;

/*
有一个硬币面额数组 coins，其中的值都是正数，每个位置代表一种面额，面额不重复，
再给定一个数组 amounts，其中表示上面s的 coins 数组中对应面额的硬币的数量。
再给定一个正数 target， 求面额之和等于 target 的有效组合数。
 */
public class C12_CoinsCombination_2 {

    public static int coinsCombination(int[] coins, int[] amounts, int target) {
        if (coins == null || coins.length == 0 || amounts == null || amounts.length == 0|| target < 0) {
            return 0;
        }
        return coinsCombinationRecursive(coins, amounts, target, 0);
    }

    private static int coinsCombinationRecursive(int[] coins, int[] amounts, int restTarget, int index) {
        if (index == coins.length) return restTarget == 0 ? 1 : 0;
        int result = 0;
        for (int chosen = 0; chosen <= amounts[index] && restTarget >= chosen * coins[index]; chosen++) {
            result += coinsCombinationRecursive(coins, amounts, restTarget - chosen * coins[index], index + 1);
        }
        return result;
    }

    public static int coinsCombinationByDp(int[] coins, int[] amounts, int target) {
        if (coins == null || coins.length == 0 || amounts == null || amounts.length == 0|| target < 0) {
            return 0;
        }
        int n = coins.length;
        int[][] dp = new int[n + 1][target + 1];
        dp[n][0] = 1;

        for (int index = n - 1; index >= 0; index--) {
            for (int restTarget = 0; restTarget <= target; restTarget++) {
                int result = 0;
                for (int chosen = 0; chosen <= amounts[index] && restTarget >= chosen * coins[index]; chosen++) {
                    result += dp[index + 1][restTarget - chosen * coins[index]];
                }
                dp[index][restTarget] = result;
            }

        }
        return dp[0][target];
    }

    public static int coinsCombinationByDpWithOptimize(int[] coins, int[]amounts, int target) {
        if (coins == null || coins.length == 0 || amounts == null || amounts.length == 0|| target < 0) {
            return 0;
        }
        int n = coins.length;
        int[][] dp = new int[n + 1][target + 1];
        dp[n][0] = 1;

        for (int index = n - 1; index >= 0; index--) {
            for (int restTarget = 0; restTarget <= target; restTarget++) {
                dp[index][restTarget] = dp[index + 1][restTarget];
                if (restTarget - coins[index] >= 0) {
                    dp[index][restTarget] += dp[index][restTarget - coins[index]];
                }
                // restTarget 比该面额的所有硬币加起来还要大，说明硬币不够
                if (restTarget >= coins[index] * (amounts[index] + 1)) {
                    int reduceTarget = restTarget - coins[index] * (amounts[index] + 1);
                    dp[index][restTarget] -= dp[index + 1][reduceTarget];
                }
            }
        }
        return dp[0][target];
    }

    public static void main(String[] args) {
        int[] coins = new int[]{15, 1, 12, 8, 6, 4, 13, 18, 2};
        int[] amounts = new int[]{1, 1, 1, 1, 1, 2, 1, 1, 7};
        int target = 16;
        System.out.println(coinsCombination(coins, amounts, target));
        System.out.println(coinsCombinationByDp(coins, amounts, target));
        System.out.println(coinsCombinationByDpWithOptimize(coins, amounts, target));
    }

}
