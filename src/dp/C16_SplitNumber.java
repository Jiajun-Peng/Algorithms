package dp;

public class C16_SplitNumber {

    public static int splitNumber(int n) {
        if (n < 0) return 0;
        if (n == 1) return 1;
        return splitNumberByRecursive(n, 1);
    }

    private static int splitNumberByRecursive(int rest, int start) {
        if (rest == 0) return 1;
        int result = 0;
        for (int nextStart = start; nextStart <= rest; nextStart++) {
            result += splitNumberByRecursive(rest - nextStart, nextStart);
        }
        return result;
    }

    private static int splitNumberByDp(int n) {
        if (n < 0) return 0;
        if (n == 1) return 1;

        int[][] dp = new int[n + 1][n + 1];
        for (int start = 0; start <= n; start++) {
            dp[0][start] = 1;
        }

        for (int rest = 1; rest <= n; rest++) {
            for (int start = n; start >= 1; start--) {
                int result = 0;
                for (int nextStart = start; nextStart <= rest; nextStart++) {
                    result += dp[rest - nextStart][nextStart];
                }
                dp[rest][start] =  result;
            }
        }
        return dp[n][1];
    }

    private static int splitNumberByDpWithOptimize(int n) {
        if (n < 0) return 0;
        if (n == 1) return 1;

        int[][] dp = new int[n + 1][n + 1];
        for (int start = 0; start <= n; start++) {
            dp[0][start] = 1;
            dp[start][start] = 1;
        }

        for (int rest = 1; rest <= n; rest++) {
            for (int start = n - 1; start >= 1; start--) {
                if (start <= rest) {
                    dp[rest][start] = dp[rest - start][start];
                    dp[rest][start] += dp[rest][start + 1];
                }
            }
        }
        return dp[n][1];
    }

    public static void main(String[] args) {
        int n = 13; // 101
        System.out.println(splitNumber(n));
        System.out.println(splitNumberByDp(n));
        System.out.println(splitNumberByDpWithOptimize(n));
    }
}
