package leetcode_top_100;

public class C14_LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0 || strs[0].isEmpty()) return "";
        String prefix = strs[0]; // 以第一个字符串为基准
        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(prefix)) {
                // 如果不是以 prefix 开头，就缩短 prefix
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        }
        return prefix;
    }
}
