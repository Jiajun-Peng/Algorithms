package dp;

/*
516. 最长回文子序列：https://leetcode.cn/problems/longest-palindromic-subsequence
 */
public class C08_LongestPalindromeSubsequence {

    public static int longestPalindromeSubsequence(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] chars = s.toCharArray();
        return longestPalindromeSubsequenceByRecursive(chars, 0, chars.length - 1);
    }

    private static int longestPalindromeSubsequenceByRecursive(char[] chars, int L, int R) {
        if (L == R) {
            return 1;
        }
        if (L == R - 1) { // 判断回文
            return chars[L] == chars[R] ? 2 : 1;
        }

        int case1 = longestPalindromeSubsequenceByRecursive(chars, L, R - 1);
        int case2 = longestPalindromeSubsequenceByRecursive(chars, L + 1, R);
        int case3 = chars[L] == chars[R] ? longestPalindromeSubsequenceByRecursive(chars, L + 1, R - 1) + 2 :
                longestPalindromeSubsequenceByRecursive(chars, L + 1, R - 1);
        return Math.max(case1, Math.max(case2, case3));
    }

    public static int longestPalindromeSubsequenceByDp(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] chars = s.toCharArray();

        int length = s.length();
        int[][] dp = new int[length][length];

        dp[length - 1][length - 1] = 1;
        for (int L = 0; L < length - 1; L++) {
            dp[L][L] = 1;
            dp[L][L + 1] = chars[L] == chars[L + 1] ? 2 : 1;
        }

        for (int L = 0; L < length; L++) {
            for (int R = 0; R < length; R++) {
                if (L == R) dp[L][R] = 1;
                if (L == R - 1) dp[L][R] = chars[L] == chars[R] ? 2 : 1;
            }
        }

        for (int i = 2; i < length; i++)  {
            int L = 0;
            int R = i;
            while (R < length) {
                dp[L][R] = Math.max(dp[L][R - 1], dp[L + 1][R]);
                if (chars[L] == chars[R])
                    dp[L][R] = Math.max(dp[L][R], dp[L + 1][R - 1] + 2);
                L++;
                R++;
            }
        }
        return dp[0][length - 1];
    }

    public static void main(String[] args) {
        String s = "bbbab";
        System.out.println(longestPalindromeSubsequence(s));
        System.out.println(longestPalindromeSubsequenceByDp(s));
    }
}
