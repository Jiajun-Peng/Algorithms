package dp;

/*
1143. 最长公共子序列：https://leetcode-cn.com/problems/longest-common-subsequence
 */
public class C07_LongestCommonSubsequence {

    public static int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text1.isEmpty() || text2 == null || text2.isEmpty()) {
            return 0;
        }
        char[] textChars1 = text1.toCharArray();
        char[] textChars2 = text2.toCharArray();

        return longestCommonSubsequenceRecursive(textChars1, textChars1.length - 1, textChars2, textChars2.length - 1);
    }

    private static int longestCommonSubsequenceRecursive(char[] chars1, int i, char[] chars2, int j) {
        if (i == 0 && j == 0) {
            return chars1[i] == chars2[j] ? 1 : 0;
        } else if (i == 0) {
            if (chars1[i] == chars2[j]) return 1;
            // 判断仅剩的一个字符是否能和另一个串中的某个字符相等，即可构成子序列
            return longestCommonSubsequenceRecursive(chars1, i, chars2, j - 1);
        } else if (j == 0) {
            if (chars1[i] == chars2[j]) return 1;
            return longestCommonSubsequenceRecursive(chars1, i - 1, chars2, j);
        } else { // i > 0 && j > 0
            // i 不可能是子序列的结尾，但 j 可能是子序列的结尾
            int p1 = longestCommonSubsequenceRecursive(chars1, i - 1, chars2, j);
            // j 不可能是子序列的结尾，但 i 可能是子序列的结尾
            int p2 = longestCommonSubsequenceRecursive(chars1, i, chars2,j - 1);

            // i 和 j 是子序列的结尾 或者 i 和 j 都不是子序列的结尾，相等时 i 和 j 位置的元素就是子序列中的其中一个元素，所以长度 + 1
            int p3 = chars1[i] == chars2[j] ? longestCommonSubsequenceRecursive(chars1, i - 1, chars2, j - 1) + 1 :
                    longestCommonSubsequenceRecursive(chars1, i - 1, chars2, j - 1);

            return Math.max(p1, Math.max(p2, p3));
        }
    }

    public static int longestCommonSubsequenceByDp(String text1, String text2) {
        if (text1 == null || text1.isEmpty() || text2 == null || text2.isEmpty()) {
            return 0;
        }
        char[] chars1 = text1.toCharArray();
        char[] chars2 = text2.toCharArray();

        int[][] dp = new int[text1.length()][text2.length()];

        // 根据递归的终止条件初始化dp
        dp[0][0] = chars1[0] == chars2[0] ? 1 : 0;

        for (int j = 1; j < text2.length(); j++) {
            dp[0][j] = chars1[0] == chars2[j] ? 1 : dp[0][j - 1];
        }
        for (int i = 1; i < text1.length(); i++)  {
            dp[i][0] = chars1[i] == chars2[0] ? 1 : dp[i - 1][0];
        }

        for (int i = 1; i < text1.length(); i++) {
            for (int j = 1; j < text2.length(); j++) {
                // i 不可能是子序列的结尾，但 j 可能是子序列的结尾
                int p1 = dp[i - 1][j];
                // j 不可能是子序列的结尾，但 i 可能是子序列的结尾
                int p2 = dp[i][j - 1];

                // i 和 j 是子序列的结尾 或者 i 和 j 都不是子序列的结尾，相等时 i 和 j 位置的元素就是子序列中的其中一个元素，所以长度 + 1
                int p3 = chars1[i] == chars2[j] ?  dp[i - 1][j - 1] + 1 : dp[i - 1][j - 1];

                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[text1.length() - 1][text2.length() - 1];
    }

    public static void main(String[] args) {
        String text1 = "abcde", text2 = "ace";
        System.out.println(longestCommonSubsequence(text1, text2));
        System.out.println(longestCommonSubsequenceByDp(text1, text2));
    }
}
