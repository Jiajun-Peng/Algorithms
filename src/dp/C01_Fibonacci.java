package dp;

/*
  剑指 Offer 10- I. 斐波那契数列：https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof

  注意，Leetcode 中要求：答案需要取模 1e9+7(1000000007) ，如计算初始结果为：1000000008，请返回 1。
 */
public class C01_Fibonacci {
    public int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    // 使用缓存优化递归的解法
    public int fibWithCache(int n) {
        if (n < 2) return n;
        int[] cache = new int[n + 1];
        cache[0] = 0;
        cache[1] = 1;
        return fibWithCache(n, cache);
    }
    private int fibWithCache(int n, int[] cache) {
        if (n < 2 || cache[n] != 0) return cache[n];
        cache[n] = (fibWithCache(n - 1, cache) + fibWithCache(n - 2, cache));
        return cache[n];
    }

    // 使用动态规划的解法
    public int fibDp(int n) {
        if (n < 2) return n;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]);
        }
        return dp[n];
    }
}
