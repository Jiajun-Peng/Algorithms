package dp;


/*
  机器人运动问题
  
 */
public class C02_RobotWalkToTarget {
    // 暴力递归实现
    public static int walk(int n, int target, int start, int restStep) {
        if (restStep == 0) {
           return start == target ? 1 : 0; // 无剩余步数，当前位置等于目标位置时，说明当前方法有效，返回1否则0
        }
        if (start == 1) {
            return walk(n, target, start + 1, restStep - 1); // 当来到最左边时，只能向右走
        }
        if (start == n) {
            return walk(n, target, start - 1, restStep - 1); // 当来到最右边时，只能向左走
        }
        return walk(n, target, start - 1, restStep - 1)
                + walk(n, target, start + 1, restStep - 1); // 来到中间时，可往左走也可往右走
    }

    /**
     * 使用简单缓存实现
     * cur的范围可以是 1~n，restStep的范围是 0~restStep
     * 所以可以创建一个int[n][restStep]的二维数组作为缓存
     */
    public static int walkWithCache(int n, int target, int start, int restStep) {
        if (n < 2 || target < 1 || target > n || start < 1 || start > n || restStep < 1) {
            return 0;
        }
        int[][] cache = new int[n + 1][restStep + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= restStep; j++) {
                cache[i][j] = -1;
            }
        }
        return walkWithCache(n, target, start, restStep, cache);
    }
    public static int walkWithCache(int n, int target, int start, int restStep, int[][] cache) {
        if (cache[start][restStep] != -1) {
            return cache[start][restStep];
        }
        int result;
        if (restStep == 0) {
            result = start == target ? 1 : 0; // 无剩余步数，当前位置等于目标位置时，说明当前方法有效，返回1否则0
        } else if (start == 1) {
            result = walkWithCache(n, target, start + 1, restStep - 1, cache); // 当来到最左边时，只能向右走
        } else if (start == n) {
            result =  walkWithCache(n, target, start - 1, restStep - 1, cache); // 当来到最右边时，只能向左走
        } else {
            result = walkWithCache(n, target, start - 1, restStep - 1, cache)
                    + walkWithCache(n, target, start + 1, restStep - 1, cache); // 来到中间时，可往左走也可往右走
        }
        cache[start][restStep] = result;
        return result;
    }

    public static int walkWithDp(int n, int target, int start, int restStep) {
        if (n < 2 || target < 1 || target > n || start < 1 || start > n || restStep < 1) {
            return 0;
        }
        int[][] dp = new int[n + 1][restStep + 1]; // start的取值范围是 1~n

        // 根据尝试暴力递归过程，我们知道，当已经处于目标位置，且 restStep == 0 时，
        dp[target][0] = 1; // 当已经处于目标位置时，剩余步数不为 0 的位置默认都是0

        // 剩余步数从 1 开始，逐步推导出剩余步数为 restStep 时的结果
        for (int rest = 1; rest <= restStep; rest++) {
            // 每次设置一个位置的值，只与前一个 rest 的值有关，所以这个循环内代码的顺序并不影响结果
            dp[1][rest] = dp[2][rest - 1];
            dp[n][rest] = dp[n - 1][rest - 1];
            for (int cur = 2; cur < n; cur++) {
                dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
            }
        }
        return dp[start][restStep];
    }

    public static void main(String[] args) {
        int f = walk(10, 5, 3, 6);
        int cache = walkWithCache(10, 5, 3, 6);
        int dp = walkWithDp(10, 5, 3, 6);
        System.out.println(f);
        System.out.println(cache);
        System.out.println(dp);
    }
}
