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
public class RobotToTarget {
    // 暴力递归实现
    public static int f(int n, int target, int cur, int restStep) {
        if (restStep == 0) {
           return cur == target ? 1 : 0; // 无剩余步数，当前位置等于目标位置时，说明当前方法有效，返回1否则0
        }
        if (cur == 1) {
            return f(n, target, cur + 1, restStep - 1); // 当来到最左边时，只能向右走
        }
        if (cur == n) {
            return f(n, target, cur - 1, restStep - 1); // 当来到最右边时，只能向左走
        }
        return f(n, target, cur - 1, restStep - 1)
                + f(n, target, cur + 1, restStep - 1); // 来到中间时，可往左走也可往右走
    }

    /**
     * 使用简单缓存实现
     * cur的范围可以是 1~n，restStep的范围是 0~restStep
     * 所以可以创建一个int[n][restStep]的二维数组作为缓存
     */
    public static int f_cache(int n, int target, int cur, int restStep) {
        int[][] cache = new int[n + 1][restStep + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= restStep; j++) {
                cache[i][j] = -1;
            }
        }
        return f_cache(n, target, cur, restStep, cache);
    }
    public static int f_cache(int n, int target, int cur, int restStep, int[][] cache) {
        if (cache[cur][restStep] != -1) {
            return cache[cur][restStep];
        }
        int result;
        if (restStep == 0) {
            result = cur == target ? 1 : 0; // 无剩余步数，当前位置等于目标位置时，说明当前方法有效，返回1否则0
        } else if (cur == 1) {
            result = f_cache(n, target, cur + 1, restStep - 1, cache); // 当来到最左边时，只能向右走
        } else if (cur == n) {
            result =  f_cache(n, target, cur - 1, restStep - 1, cache); // 当来到最右边时，只能向左走
        } else {
            result = f_cache(n, target, cur - 1, restStep - 1, cache)
                    + f_cache(n, target, cur + 1, restStep - 1, cache); // 来到中间时，可往左走也可往右走
        }
        cache[cur][restStep] = result;
        return result;
    }

    public static void main(String[] args) {
        int f = f(10, 5, 3, 6);
        int cache = f_cache(10, 5, 3, 6);
        System.out.println(f);
        System.out.println(cache);
    }
}
