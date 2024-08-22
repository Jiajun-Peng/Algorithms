package leetcode_top_100;

import java.util.HashMap;
import java.util.Map;

/*
https://leetcode.com/problems/two-sum/

给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出和为目标值target的那两个整数，并返回它们的数组下标。
你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
你可以按任意顺序返回答案。

示例 1：
输入：nums = [2,7,11,15], target = 9
输出：[0,1]
解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。

示例 2：
输入：nums = [3,2,4], target = 6
输出：[1,2]

示例 3：
输入：nums = [3,3], target = 6
输出：[0,1]

提示：
2 <= nums.length <= 104
-109 <= nums[i] <= 109
-109 <= target <= 109
只会存在一个有效答案

进阶：你可以想出一个时间复杂度小于 O(n2) 的算法吗？

 */
public class C01_TwoSum {

    /**
     * 从头遍历每一个数a，并从a的后一个位置开始找到另一个数b，直到a+b=target。
     * 时间复杂度为O(n^2)
     */
    public int[] twoSum(int[] nums, int target) {

        int[] result = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target)
                {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * 使用map将遍历过程中访问到的数及其下标缓存起来，当后续需要某个差值的坐标时可以快速从map中获取到对应的坐标。
     * 通过空间换时间的方式减少遍历的开销。
     * 时间复杂度O(n)，空间复杂度O(n)
     */
    public int[] twoSum_better(int[] nums, int target) {

        int[] result = new int[2];
        Map<Integer, Integer> numToIndex = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int balance = target - nums[i];
            if (numToIndex.containsKey(balance)) {
                result[0] = i;
                result[1] = numToIndex.get(balance);
                return result;
            }
            numToIndex.put(nums[i], i);
        }
        return result;
    }
}
