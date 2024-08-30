package dp;

public class C12_CoinsCombination {

    public static int coinsCombination(int[] coins, int target) {
        if (coins == null || coins.length == 0 || target < 0) {
            return 0;
        }
        return coinsCombinationRecursive(coins, target, 0);
    }

    private static int coinsCombinationRecursive(int[] coins, int restTarget, int index) {
        if (restTarget < 0) return 0;
        if (index == coins.length) return restTarget == 0 ? 1 : 0;

        int chosenCurCoin = coinsCombinationRecursive(coins, restTarget - coins[index], index + 1);
        int notChosenCurCoin = coinsCombinationRecursive(coins, restTarget, index + 1);

        return chosenCurCoin + notChosenCurCoin;
    }

    public static int coinsCombinationDp(int[] coins, int target) {
        if (coins == null || coins.length == 0 || target < 0) {
            return 0;
        }
        int length = coins.length;
        int[][] dp = new int[length + 1][target + 1];
        dp[length][0] = 1;

        for (int index = length - 1; index >= 0; index--) {
            for (int restTarget = 0; restTarget <= target; restTarget++) {
                int chosenCurCoin = 0;
                if (restTarget - coins[index] >= 0) {
                    chosenCurCoin = dp[index + 1][restTarget - coins[index]];
                }
                int notChosenCurCoin = dp[index + 1][restTarget];
                dp[index][restTarget] = chosenCurCoin + notChosenCurCoin;
            }
        }
        return dp[0][target];
    }

    public static void main(String[] args) {
        int[] coins = {1, 1, 1};
        int target = 2;
        System.out.println(coinsCombination(coins, target)); // 3
        System.out.println(coinsCombinationDp(coins, target));
    }
}
