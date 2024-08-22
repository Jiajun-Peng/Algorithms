package dp;


/**
 * 机器人运动问题
 *
 * > 假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
 * >
 * > 初始时机器人在其中的M位置上(M 一定是 1~N 中的一个)
 * >
 * > 如果机器人来到第1位置，那么下一步只能往右来到第2个位置；
 * >
 * > 如果机器人来到第N位置，那么下一步只能往左来到第 N-1 位置；
 * >
 * > 如果机器人来到中间位置，那么下一步可以往左走或者往右走；
 * >
 * > 规定机器人必须走够K 步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
 * >
 * > 给定四个参数 N、M、K、P，返回方法数。
 *
 * 以下的暴力递归解法中包含重复计算的过程，重复计算的参数只与cur和restStep有关，所以可以将cur和restStep的组合参数的结果缓存起来
 */
public class C02_RobotWalkToTarget {
    // 暴力递归实现
    public static int f(int n, int target, int start, int restStep) {
        if (restStep == 0) {
           return start == target ? 1 : 0; // 无剩余步数，当前位置等于目标位置时，说明当前方法有效，返回1否则0
        }
        if (start == 1) {
            return f(n, target, start + 1, restStep - 1); // 当来到最左边时，只能向右走
        }
        if (start == n) {
            return f(n, target, start - 1, restStep - 1); // 当来到最右边时，只能向左走
        }
        return f(n, target, start - 1, restStep - 1)
                + f(n, target, start + 1, restStep - 1); // 来到中间时，可往左走也可往右走
    }

    /**
     * 使用简单缓存实现
     * cur的范围可以是 1~n，restStep的范围是 0~restStep
     * 所以可以创建一个int[n][restStep]的二维数组作为缓存
     */
    public static int f_cache(int n, int target, int start, int restStep) {
        if (n < 2 || target < 1 || target > n || start < 1 || start > n || restStep < 1) {
            return 0;
        }
        int[][] cache = new int[n + 1][restStep + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= restStep; j++) {
                cache[i][j] = -1;
            }
        }
        return f_cache(n, target, start, restStep, cache);
    }
    public static int f_cache(int n, int target, int start, int restStep, int[][] cache) {
        if (cache[start][restStep] != -1) {
            return cache[start][restStep];
        }
        int result;
        if (restStep == 0) {
            result = start == target ? 1 : 0; // 无剩余步数，当前位置等于目标位置时，说明当前方法有效，返回1否则0
        } else if (start == 1) {
            result = f_cache(n, target, start + 1, restStep - 1, cache); // 当来到最左边时，只能向右走
        } else if (start == n) {
            result =  f_cache(n, target, start - 1, restStep - 1, cache); // 当来到最右边时，只能向左走
        } else {
            result = f_cache(n, target, start - 1, restStep - 1, cache)
                    + f_cache(n, target, start + 1, restStep - 1, cache); // 来到中间时，可往左走也可往右走
        }
        cache[start][restStep] = result;
        return result;
    }

    /*
    因为最终的结果依赖于start和restStep的组合参数，这就是一个二维表,可以将start作为纵坐标、restStep作为横坐标

     */
    public static int f_dp(int n, int target, int start, int restStep) {
        if (n < 2 || target < 1 || target > n || start < 1 || start > n || restStep < 1) {
            return 0;
        }
        int[][] dp = new int[n + 1][restStep + 1]; // start的取值范围是 1~n

        // 根据尝试暴力递归过程，我们知道，当restStep == 0时，
        dp[target][0] = 1;

        // 依赖的过程是从左向右、从上到下
        for (int restIndex = 1; restIndex <= restStep; restIndex++) {
            dp[1][restIndex] = dp[2][restIndex - 1];
            for (int j = 2; j < n; j++) {
                dp[j][restIndex] = dp[j - 1][restIndex - 1] + dp[j + 1][restIndex - 1];
            }
            dp[n][restIndex] = dp[n - 1][restIndex - 1];
        }
        return dp[start][restStep];
    }

    public static void main(String[] args) {
        int f = f(10, 5, 3, 6);
        int cache = f_cache(10, 5, 3, 6);
        int dp = f_dp(10, 5, 3, 6);
        System.out.println(f);
        System.out.println(cache);
        System.out.println(dp);
    }
}
