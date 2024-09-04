package dp;

/*
64. 最小路径和：https://leetcode.cn/problems/minimum-path-sum
 */
public class C11_MinPathSum {

    public static int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        return minPathSumRecursive(grid, 0, 0);
    }

    private static int minPathSumRecursive(int[][] grid, int x, int y) {
        int n = grid.length;
        int m = grid[0].length;

        if (x == n || y == m) return Integer.MAX_VALUE;
        if (x == n - 1 && y == m - 1) return grid[x][y];

        // 向下走
        int toRight = minPathSumRecursive(grid, x + 1, y);
        // 向右走
        int toDown = minPathSumRecursive(grid, x, y + 1);

        return grid[x][y] + Math.min(toRight, toDown);
    }

    // 动态规划方法一
    public static int minPathSumByDp1(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];
        // dp[i][j] 表示从 (i, j) 到 (n-1, m-1) 的最小路径和
        dp[n - 1][m - 1] = grid[n - 1][m - 1];

        // 根据题意，需要从右下角开始往左上角推导，依赖的方向是从下到上，从右到左逐步推导
        for (int x = n - 1; x >= 0; x--) {
            for (int y = m - 1; y >= 0; y--) {
                if (x == n - 1 && y == m - 1) continue;
                int toRight = y + 1 < m ? dp[x][y + 1] : Integer.MAX_VALUE;
                int toDown = x + 1 < n ? dp[x + 1][y] : Integer.MAX_VALUE;
                dp[x][y] = grid[x][y] + Math.min(toRight, toDown);
            }
        }
        return dp[0][0];
    }

    // 动态规划方法二
    public static int minPathSumByDp2(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];
        // dp[i][j] 表示从 (0, 0) 到 (i, j) 的最小路径和
        dp[0][0] = grid[0][0];

        // 根据题意，需要从左上角开始往右下角推导，依赖的方向是从上到下，从左到右逐步推导
        for (int x = 0; x < n; x ++) {
            for (int y = 0; y < m; y++) {
                if (x == 0 && y == 0) continue;
                int fromLeft = y - 1 >= 0 ? dp[x][y - 1] : Integer.MAX_VALUE;
                int fromUp = x - 1 >= 0 ? dp[x - 1][y] : Integer.MAX_VALUE;
                dp[x][y] = grid[x][y] + Math.min(fromLeft, fromUp);
            }
        }
        return dp[n - 1][m - 1];
    }

    // 动态规划方法二优化，逐行更新
    public static int minPathSumByDp2OptimizeByRow(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        int n = grid.length;
        int m = grid[0].length;

        int[] dp = new int[m];
        dp[0] = grid[0][0];
        for (int y = 1; y < m; y++) {
            dp[y] = dp[y - 1] + grid[0][y]; // 根据第一行，初始化 dp
        }

        for (int x = 1; x < n; x++) {
            for (int y = 0; y < m; y++) {
                if (y == 0) {
                    // 当前行的第一列，只与上一行的第一列位置的值有关，当前 dp[y] 是上一行第一列的值，即上边位置的值
                    dp[y] = dp[y] + grid[x][y];
                } else {
                    // 当前 dp[y - 1] 是左边位置的值，dp[y] 的值是上一行第 y 列的值，即上边位置的值
                    dp[y] = Math.min(dp[y - 1], dp[y]) + grid[x][y];
                }
            }
        }
        return dp[m - 1];
    }

    // 动态规划方法二优化，逐列更新
    public static int minPathSumByDp2OptimizeByCol(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        int n = grid.length;
        int m = grid[0].length;

        int[] dp = new int[n];
        dp[0] = grid[0][0];
        for (int x = 1; x < n; x++) {
            dp[x] = dp[x - 1] + grid[x][0]; // 根据第一列，初始化 dp
        }

        for (int y = 1; y < m; y++) {
            for (int x = 0; x < n; x++) {
                if (x == 0) dp[x] = dp[x] + grid[x][y];
                else dp[x] = Math.min(dp[x - 1], dp[x]) + grid[x][y];
            }
        }
        return dp[n - 1];
    }


    public static void main(String[] args) {
        int[][] grid = {
                {1, 3, 1},
                {1, 5, 1},
                {4, 2, 1}
        };
        System.out.println(minPathSum(grid)); // 7
        System.out.println(minPathSumByDp1(grid));
        System.out.println(minPathSumByDp2(grid));
        System.out.println(minPathSumByDp2OptimizeByRow(grid));
        System.out.println(minPathSumByDp2OptimizeByCol(grid));
    }

}
