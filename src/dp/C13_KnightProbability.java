package dp;

/*
688. 骑士在棋盘上的概率：https://leetcode.cn/problems/knight-probability-in-chessboard
 */
public class C13_KnightProbability {

    public static double knightProbability(int n, int k, int row, int column) {
        if (n < 0 || k < 0 || row < 0 || row >= n || column < 0 || column >= n) {
            return 0;
        }
        int alive = knightProbabilityByRecursive(n, row, column, k);
        return alive / Math.pow(8, k);
    }

    private static int knightProbabilityByRecursive(int n, int row, int column, int restK) {
        if (row < 0 || row >= n || column < 0 || column >= n) return 0;
        if (restK == 0) return 1;

        int result = knightProbabilityByRecursive(n, row - 2, column + 1, restK - 1);
        result += knightProbabilityByRecursive(n, row - 1, column + 2, restK - 1);
        result += knightProbabilityByRecursive(n, row + 1, column + 2, restK - 1);
        result += knightProbabilityByRecursive(n, row + 2, column + 1, restK - 1);
        result += knightProbabilityByRecursive(n, row + 2, column - 1, restK - 1);
        result += knightProbabilityByRecursive(n, row + 1, column - 2, restK - 1);
        result += knightProbabilityByRecursive(n, row - 1, column - 2, restK - 1);
        result += knightProbabilityByRecursive(n, row - 2, column - 1, restK - 1);
        return result;
    }

    // 动态规划，但是这个方案可能会在计算过程中造成溢出或者精度丢失
    private static double knightProbabilityByDp(int n, int k, int row, int column) {
        if (n < 0 || k < 0 || row < 0 || row >= n || column < 0 || column >= n) {
            return 0;
        }

        int[][][] dp = new int[n][n][k + 1];
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                dp[x][y][0] = 1;
            }
        }

        for (int restK = 1; restK <= k; restK++) {
            for (int x = 0; x < n; x++) {
                for (int y =0; y < n; y++) {
                    int result = getValue(dp,x - 2,y + 1,restK - 1);
                    result += getValue(dp,x - 1,y + 2,restK - 1);
                    result += getValue(dp,x + 1,y + 2,restK - 1);
                    result += getValue(dp,x + 2,y + 1,restK - 1);
                    result += getValue(dp,x + 2,y - 1,restK - 1);
                    result += getValue(dp,x + 1,y - 2,restK - 1);
                    result += getValue(dp,x - 1,y - 2,restK - 1);
                    result += getValue(dp,x - 2,y - 1,restK - 1);
                    dp[x][y][restK] = result;
                }
            }

        }
        return dp[row][column][k] / Math.pow(8, k);
    }

    public static int getValue(int[][][] dp, int x, int y, int k) {
        if (x < 0 || x >= dp.length || y < 0 || y >= dp.length || k < 0) {
            return 0;
        }
        return dp[x][y][k];
    }

    private static double knightProbabilityByDpWithProbability(int n, int k, int row, int column) {
        if (n < 0 || k < 0 || row < 0 || row >= n || column < 0 || column >= n) {
            return 0;
        }

        double[][][] dp = new double[n][n][k + 1];
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                dp[x][y][0] = 1;
            }
        }

        double everyStepProbability = 1.0 / 8;
        for (int restK = 1; restK <= k; restK++) {
            for (int x = 0; x < n; x++) {
                for (int y =0; y < n; y++) {
                    double result = getValue(dp,x - 2,y + 1,restK - 1) * everyStepProbability;
                    result += getValue(dp,x - 1,y + 2,restK - 1) * everyStepProbability;
                    result += getValue(dp,x + 1,y + 2,restK - 1) * everyStepProbability;
                    result += getValue(dp,x + 2,y + 1,restK - 1) * everyStepProbability;
                    result += getValue(dp,x + 2,y - 1,restK - 1) * everyStepProbability;
                    result += getValue(dp,x + 1,y - 2,restK - 1) * everyStepProbability;
                    result += getValue(dp,x - 1,y - 2,restK - 1) * everyStepProbability;
                    result += getValue(dp,x - 2,y - 1,restK - 1) * everyStepProbability;
                    dp[x][y][restK] = result;
                }
            }

        }
        return dp[row][column][k];
    }

    public static double getValue(double[][][] dp, int x, int y, int k) {
        if (x < 0 || x >= dp.length || y < 0 || y >= dp.length || k < 0) {
            return 0;
        }
        return dp[x][y][k];
    }

    public static void main(String[] args) {
        int n = 3, k = 2, row = 0, column = 0;
        System.out.println(knightProbability(n, k, row, column));
        System.out.println(knightProbabilityByDp(n, k, row, column));
        System.out.println(knightProbabilityByDpWithProbability(n, k, row, column));
    }


}
