package dp;

/*
322. 零钱兑换：https://leetcode.cn/problems/coin-change
 */
public class C15_CoinChange {
    public static int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        return coinChangeByRecursive(coins, amount, 0);
    }

    // 返回子过程所需的最少硬币数，所有的不可行情况都返回 -1
    private static int coinChangeByRecursive(int[] coins, int restAmount, int index) {
        if (index == coins.length) {
            return restAmount == 0 ? 0 : -1;
        }
        int minUsedCount = -1; // 可能 for 循环不会执行，所以定义为 -1
        for (int chosen = 0; restAmount >= coins[index] * chosen; chosen++) {
            int min = coinChangeByRecursive(coins, restAmount - chosen * coins[index], index + 1);
            // 当前位置不选，即 chosen = 0 时可能 min 就会等于 -1，即当前 chosen 存在必须大于 0 的情况
            if (min == -1) continue;
            if (minUsedCount == -1) { // 之前的情况都是失败的，需要重新为 minUseCount 设置一个有效的值
                minUsedCount = min + chosen;
            } else {
                minUsedCount = Math.min(min + chosen, minUsedCount);
            }
        }
        return minUsedCount;
    }

    private static int coinChangeByDp(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        int[][] dp = new int[coins.length + 1][amount + 1];
        for (int restAmount = 1; restAmount <= amount; restAmount++) {
            dp[coins.length][restAmount] = -1;
        }
        for (int index = coins.length - 1; index >= 0; index--) {
            for (int restAmount = 0; restAmount <= amount; restAmount++) {
                int minUsedCount = -1;
                for (int chosen = 0; restAmount >= coins[index] * chosen; chosen++) {
                    int min = dp[index + 1][restAmount - chosen * coins[index]];
                    if (min == -1) continue;
                    if (minUsedCount == -1) {
                        minUsedCount = min + chosen;
                    } else {
                        minUsedCount = Math.min(min + chosen, minUsedCount);
                    }
                }
                dp[index][restAmount] = minUsedCount;
            }
        }
        return dp[0][amount];
    }

    private static int coinChangeByDpWithOptimize(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        int[][] dp = new int[coins.length + 1][amount + 1];
        for (int restAmount = 1; restAmount <= amount; restAmount++) {
            dp[coins.length][restAmount] = -1;
        }
        for (int index = coins.length - 1; index >= 0; index--) {
            for (int restAmount = 0; restAmount <= amount; restAmount++) {

                dp[index][restAmount] = dp[index + 1][restAmount]; // 可能为 -1
                if (restAmount >= coins[index]
                        && dp[index][restAmount - coins[index]] != -1) {
                    if (dp[index][restAmount] == -1) { // 非合法的值
                        dp[index][restAmount] = dp[index][restAmount - coins[index]] + 1;
                    } else {
                        dp[index][restAmount] = Math.min(dp[index][restAmount], dp[index][restAmount - coins[index]] + 1);
                    }
                }
            }
        }
        return dp[0][amount];
    }

    public static void main(String[] args) {
        int[] coins = new int[]{1, 2, 5};
        int amount = 11;
        System.out.println(coinChange(coins, amount));
        System.out.println(coinChangeByDp(coins, amount));
        System.out.println(coinChangeByDpWithOptimize(coins, amount));
    }
}
