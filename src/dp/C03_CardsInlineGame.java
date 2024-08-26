package dp;

/*
  486. 预测赢家: https://leetcode.cn/problems/predict-the-winner
  877. 石子游戏：https://leetcode.cn/problems/stone-game
  1140. 石子游戏 II：https://leetcode-cn.com/problems/stone-game-ii

 */
public class C03_CardsInlineGame {

    public static int game(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return Math.max(
                first(arr, 0, arr.length - 1),//某个人在当前范围先手拿牌得到的分数
                then(arr, 0, arr.length - 1) //另一个人在当前范围后手拿牌得到的分数，是需要等先手的人拿完牌后再拿牌的
        );
    }

    //先手函数，返回先手拿能获得的最大分数
    public static int first(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];//先手，且在这个范围上只有一张牌了，那只能拿这一张了
        }
        return Math.max(//因为现在是我先手，所以我能现在拿一个大的
                arr[L] + then(arr, L + 1, R),//拿了左边的，再在L+1 ~ R上后手拿
                arr[R] + then(arr, L, R - 1)//拿了右边的，再在L ~ R-1上后手拿
        );
    }

    //后手函数，返回后手拿能得到的最大分数（先手会让后手拿到的分数尽可能小）
    public static int then(int[] arr, int L, int R) {
        if (L == R) {
            return 0; // 后手，所以在这个范围上仅剩的一张牌已经被先手的人拿走了
        }
        // 后手只能在两种先手方式下能获得的最大的得分中取得其中小的得分
        //被别人先手拿了，那自己只能得到别人先手挑剩下后的可能结果中较小的得分（但并不意味着较小的得分就一定输，只是在后手赢的情况下也让后手的分数较低）
        return Math.min(
                first(arr, L + 1, R), // 对手拿了arr[L]，所以现在就变成了在L+1 ~ R上的先手（先手时，需要让自己的最大化）
                first(arr, L, R - 1)  // 对手拿了arr[R]，所以现在就变成了在L ~ R - 1上的先手
        );
    }

    // 使用缓存优化递归的解法
    public static int gameWithCache(int[] arr) {
        int length = arr.length;
        int[][] firstCache = new int[length][length];
        int[][] thenCache = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                firstCache[i][j] = -1;
                thenCache[i][j] = -1;
            }
        }
        return Math.max(firstWithCache(arr, 0, length - 1, firstCache, thenCache),
                thenWithCache(arr, 0, length - 1, firstCache, thenCache));
    }

    public static int firstWithCache(int[] arr, int L, int R, int[][] firstCache, int[][] thenCache) {
        if (firstCache[L][R] != -1) {
            return firstCache[L][R];
        }
        int result;
        if (L == R) {
            result = arr[L];
        } else {
            result = Math.max(arr[L] + thenWithCache(arr, L + 1, R, firstCache, thenCache),
                    arr[R] + thenWithCache(arr, L, R - 1, firstCache, thenCache));
        }
        firstCache[L][R] = result;
        return result;
    }

    public static int thenWithCache(int[] arr, int L, int R, int[][] firstCache, int[][] thenCache) {
        if (thenCache[L][R] != -1) {
            return thenCache[L][R];
        }
        int result;
        if (L == R) {
            result = 0;
        } else {
            result = Math.min(firstWithCache(arr, L + 1, R, firstCache, thenCache),
                    firstWithCache(arr, L, R - 1, firstCache, thenCache));
        }
        thenCache[L][R] = result;
        return result;
    }

    // 使用动态规划的解法
    public static int gameWithDp(int[] arr) {
        int length = arr.length;
        int[][] firstDp = new int[length][length];
        int[][] thenDp = new int[length][length];

        // L == R 时
        for (int i = 0; i < length; i++) {
            firstDp[i][i] = arr[i]; // 先手函数的值就是arr[L]
            thenDp[i][i] = 0; // 默认就是 0，也可以不写
        }

        for (int i = 1; i < length; i++) {
            int L = 0;
            int R = i;
            while (R < length) {
                firstDp[L][R] = Math.max(arr[L] + thenDp[L + 1][R], arr[R] + thenDp[L][R - 1]);
                thenDp[L][R] = Math.min(firstDp[L + 1][R], firstDp[L][R - 1]);
                L++;
                R++;
            }
        }
        return Math.max(firstDp[0][length - 1], thenDp[0][length - 1]);
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 1};
        int score = game(arr);
        int scoreWithCache = gameWithCache(arr);
        int scoreWithDp = gameWithDp(arr);
        System.out.println(score);
        System.out.println(scoreWithCache);
        System.out.println(scoreWithDp);
    }

}
