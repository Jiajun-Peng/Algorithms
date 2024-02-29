package dp;

/**
 * <a href="https://leetcode.cn/problems/predict-the-winner/">486. 预测赢家</a>
 *
 * 给你一个整数数组 nums 。玩家 1 和玩家 2 基于这个数组设计了一个游戏。
 *
 * 玩家 1 和玩家 2 轮流进行自己的回合，玩家 1 先手。
 * 开始时，两个玩家的初始分值都是 0 。每一回合，玩家从数组的任意一端取一个数字（即，nums[0] 或 nums[nums.length - 1]），
 * 取到的数字将会从数组中移除（数组长度减 1 ）。玩家选中的数字将会加到他的得分上。当数组中没有剩余数字可取时，游戏结束。
 *
 * 返回赢家的分数。
 *
 */
public class CardsInlineGame {

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

    //后手函数，返回后手拿能得到的最大分数
    public static int then(int[] arr, int L, int R) {
        if (L == R) {
            return 0; // 后手，所以在这个范围上仅剩的一张牌已经被先手的人拿走了
        }
        // 后手只能在两种先手方式下能获得的最大的得分中取得其中小的得分
        return Math.min(//被别人先手拿了，那自己只能得到别人先手挑剩下后的可能结果中较小的得分（但并不意味着较小的得分就一定输，只是在后手赢的情况下也让后手的分数较低）
                first(arr, L + 1, R), // 对手拿了arr[L]，所以现在就变成了在L+1 ~ R上的先手（先手时，需要让自己的最大化）
                first(arr, L, R - 1)  // 对手拿了arr[R]，所以现在就变成了在L ~ R - 1上的先手
        );
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 1};
        int score = game(arr);
        System.out.println(score);
    }

}
