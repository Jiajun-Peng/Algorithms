package dp;

public class C09_HorseJump {

    // 暴力递归
    public static int horseJumpByRecursive(int targetX, int targetY, int restStep) {
        return horseJumpRecursiveByRecursive(targetX, targetY, restStep, 0, 0);
    }

    public static int horseJumpRecursiveByRecursive(int targetX, int targetY, int restStep, int curX, int curY) {
        if (curX < 0 || curX > 8 || curY < 0 || curY > 9) return 0;
        if (restStep == 0) return (curX == targetX && curY == targetY) ? 1 : 0;

        int waysCount = horseJumpRecursiveByRecursive(targetX, targetY, restStep - 1, curX + 1, curY + 2);
        waysCount += horseJumpRecursiveByRecursive(targetX, targetY, restStep - 1, curX + 2, curY + 1);
        waysCount += horseJumpRecursiveByRecursive(targetX, targetY, restStep - 1, curX + 2, curY - 1);
        waysCount += horseJumpRecursiveByRecursive(targetX, targetY, restStep - 1, curX + 1, curY - 2);
        waysCount += horseJumpRecursiveByRecursive(targetX, targetY, restStep - 1, curX - 1, curY - 2);
        waysCount += horseJumpRecursiveByRecursive(targetX, targetY, restStep - 1, curX - 2, curY - 1);
        waysCount += horseJumpRecursiveByRecursive(targetX, targetY, restStep - 1, curX - 2, curY + 1);
        waysCount += horseJumpRecursiveByRecursive(targetX, targetY, restStep - 1, curX - 1, curY + 2);

        return waysCount;
    }

    // 动态规划
    public static int horseJumpByDp(int targetX, int targetY, int restStep) {
        int[][][] dp = new int[9][10][restStep + 1];
        dp[targetX][targetY][0] = 1;

        for (int step = 1; step <= restStep; step++) {
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 10; y++) {
                    dp[x][y][step] += getValidValue(dp, x + 1, y + 2, step - 1);
                    dp[x][y][step] += getValidValue(dp, x + 2, y + 1, step - 1);
                    dp[x][y][step] += getValidValue(dp, x + 2, y - 1, step - 1);
                    dp[x][y][step] += getValidValue(dp, x + 1, y - 2, step - 1);
                    dp[x][y][step] += getValidValue(dp, x - 1, y - 2, step - 1);
                    dp[x][y][step] += getValidValue(dp, x - 2, y - 1, step - 1);
                    dp[x][y][step] += getValidValue(dp, x - 2, y + 1, step - 1);
                    dp[x][y][step] += getValidValue(dp, x - 1, y + 2, step - 1);
                }
            }
        }

        return dp[0][0][restStep];
    }

    private static int getValidValue(int[][][] dp, int curX, int curY, int restStep) {
        if (curX < 0 || curX > 8 || curY < 0 || curY > 9) return 0;
        return dp[curX][curY][restStep];
    }

    public static void main(String[] args) {
        int targetX = 7;
        int targetY = 7;
        int restStep = 10;
        System.out.println(horseJumpByRecursive(targetX, targetY, restStep)); // 515813
        System.out.println(horseJumpByDp(targetX, targetY, restStep)); // 515813
    }
}
