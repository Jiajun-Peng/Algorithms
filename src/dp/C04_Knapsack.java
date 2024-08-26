package dp;

/*
weight 数组表示货物的重量，value 数组表示对应货物的价值，bagCapacity 表示背包的载重量，求在不超重的情况下背包能放入的货物的最大价值是多少？

 */
public class C04_Knapsack {

    public static int maxValue(int[] weight, int[] value, int bagCapacity) {
        if (weight == null || value == null || weight.length != value.length || bagCapacity < 0) {
            return 0;
        }
        return maxValue(weight, value, bagCapacity, 0);
    }

    public static int maxValue(int[] weight, int[] value, int bagCapacity, int index) {
        if (index == weight.length) {
            return 0;
        }
        // 每一个位置的货物都有两种选择：放入背包或不放入背包
        // 当前货物不放入背包
        int skipCur = maxValue(weight, value, bagCapacity, index + 1);

        // 当前货物放入背包,但是不能超重
        int chosenCur = 0;
        bagCapacity = bagCapacity - weight[index];
        if (bagCapacity >= 0) {
            chosenCur = value[index] + maxValue(weight, value, bagCapacity, index + 1);
        }
        // 返回两种选择中的最大值
        return Math.max(skipCur, chosenCur);
    }

    public static int maxValueWithDp(int[] weight, int[] value, int bagCapacity) {
        if (weight == null || value == null || weight.length != value.length || bagCapacity < 0) {
            return 0;
        }
        int length = weight.length;
        int[][] dp = new int[length + 1][bagCapacity + 1];
        // dp[length][...] = 0; 默认已经是0了
        for (int index = length - 1; index >= 0; index--) {
            for (int rest = 0; rest <= bagCapacity; rest++) { // 任何rest的值都有可能，所以rest可以++一步步
                int skipCur = dp[index + 1][rest];
                int chosenCur = 0;
                int restWeight = rest - weight[index];
                if (restWeight >= 0) {
                    chosenCur = value[index] + dp[index + 1][restWeight];
                }
                dp[index][rest] = Math.max(skipCur, chosenCur);
            }
        }

        return dp[0][bagCapacity];
    }

    public static void main(String[] args) {
        int[] weight = {3, 2, 4, 7};
        int[] value = {5, 6, 3, 19};
        int bagCapacity = 11;
        System.out.println(maxValue(weight, value, bagCapacity));
        System.out.println(maxValueWithDp(weight, value, bagCapacity));
    }
}
