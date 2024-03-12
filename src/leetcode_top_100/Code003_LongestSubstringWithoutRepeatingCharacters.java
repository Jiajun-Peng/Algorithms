package leetcode_top_100;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*
[3. 无重复字符的最长子串](https://leetcode.cn/problems/longest-substring-without-repeating-characters/)
 */
public class Code003_LongestSubstringWithoutRepeatingCharacters {

    public static int lengthOfLongestSubstringBetter1(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        // 最近一次出现某一字符的位置,因为字符的ascii码的编码值就是数字，所以可以使用组的某一下标的位置代表对应的字符
        // 记录最近一次出现某一字符的位置，就代表当前字符最左可到达的位置之一，可以避免再次循环找向左找第一个等于当前字符的位置
        int[] charLastVisitIndex = new int[128];
        Arrays.fill(charLastVisitIndex, -1);

        char[] charArray = s.toCharArray();
        int preLeftmostIndex = -1; // 上一个位置（i - 1）位置结尾的子串的起始位置
        int maxLength = 1;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            int curLeftmostIndex = Math.max(preLeftmostIndex, charLastVisitIndex[c]); //大的值排在右边，也就是从后向前找到的最左
            int curLength = i - curLeftmostIndex;
            charLastVisitIndex[c] = i; // 记录当前c字符最佳一次出现的位置
            preLeftmostIndex = curLeftmostIndex;
            maxLength = Math.max(curLength, maxLength);
        }
        return maxLength;
    }

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
        int length = lengthOfLongestSubstringBetter1("pwwkew");
        System.out.println(length);
    }
}
