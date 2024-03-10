package leetcode_top_100;

import java.util.HashMap;
import java.util.Map;

/*
[3. 无重复字符的最长子串](https://leetcode.cn/problems/longest-substring-without-repeating-characters/)
 */
public class Code003_LongestSubstringWithoutRepeatingCharacters {

    // 方案二，比第一种方案更早结束向左的遍历
    public static int lengthOfLongestSubstringBetter(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] charArray = s.toCharArray();
        Map<Integer, Integer> indexToLeftmostIndex = new HashMap<>(); // 用于保存以index结尾的子串最左的起始位置
        indexToLeftmostIndex.put(0, 0);

        int maxLength = 1;
        for (int i = 1; i < charArray.length; i++) {
            Integer preLeftmostStartIndex = indexToLeftmostIndex.get(i - 1);
            char c = charArray[i];
            int curLeftmostStartIndex = i;
            for (int j = i - 1; j >= preLeftmostStartIndex; j--) { // 最左的位置不会比i - 1的最左起始位置更左，及时停止遍历
                if (charArray[j] == c) break;
                curLeftmostStartIndex = j; // 记录左边第一个等于当前位置的值
            }
            indexToLeftmostIndex.put(i, curLeftmostStartIndex);
            maxLength = Math.max(maxLength, i - curLeftmostStartIndex + 1);
        }
        return maxLength;
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] charArray = s.toCharArray();
        Map<Integer, Integer> indexToLeftmostIndex = new HashMap<>(); // 用于保存以index结尾的子串最左的起始位置
        indexToLeftmostIndex.put(0, 0);

        int maxLength = 1; // 全局最大长度，注意，最小的长度至少为1
        for (int i = 1; i < charArray.length; i++) {
            char c = charArray[i];
            int curLeftmostStartIndexWhenEqual = i; // 初始时，都是以自身为起始位置
            for (int j = i - 1; j >= 0; j--) { // 向左遍历，直到等于当前i位置的位置
                if (charArray[j] == c) break;
                curLeftmostStartIndexWhenEqual = j; // 记录左边第一个等于当前位置的值
            }
            int preLeftmostStartIndex = indexToLeftmostIndex.get(i - 1); // i - 1位置最左的起始位置
            // i位置最左的起始位置不会比i-1位置的最左起始位置更靠左
            int curLeftmostStartIndex = Math.max(curLeftmostStartIndexWhenEqual, preLeftmostStartIndex); // 靠右的位置更大
            maxLength = Math.max(maxLength, i - curLeftmostStartIndex + 1);
            indexToLeftmostIndex.put(i, curLeftmostStartIndex); // 保存当前位置的最左起始位置
        }
        return maxLength;
    }

    public static void main(String[] args) {
        int length = lengthOfLongestSubstring("abcabcbb");
        System.out.println(length);
    }
}
