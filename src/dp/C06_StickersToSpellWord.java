package dp;

import java.util.HashMap;
import java.util.Map;

/*
691. 贴纸拼词：https://leetcode.cn/problems/stickers-to-spell-word
 */
public class C06_StickersToSpellWord {

    public static int minStickers(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.isEmpty()) {
            return -1;
        }
        int useCount = minStickerRecursive(stickers, target);
        return useCount == Integer.MAX_VALUE ? -1 : useCount;
    }

    // 暴力递归求解
    public static int minStickerRecursive(String[] stickers, String target) {
        if (target.isEmpty()) {
            return 0;
        }
        int useCount = Integer.MAX_VALUE; // 使用整数最大值指示没有有效的贴纸的情况
        for (String sticker : stickers) {
            String restTarget = removeChars(target, sticker);
            boolean isValidSticker = target.length() != restTarget.length();
            if (isValidSticker)
                // 子过程对剩余的 restTarget 中的字符递归求解
                useCount = Math.min(useCount, minStickerRecursive(stickers, restTarget));
        }
        // happy path: 本次选取一张有效的贴纸替换部分target中的字符（具体是哪一张是无所谓的） + 子过程restTarget使用的最少的贴纸数
        // sad path: 当前没有贴纸可以替换掉target中的字符
        return useCount == Integer.MAX_VALUE ? useCount : useCount + 1;
    }

    private static String removeChars(String target, String sticker) {
        char[] targetChars = target.toCharArray();
        char[] stickerChars = sticker.toCharArray();

        int[] targetCharCounts = new int[26];
        // 统计 target 中都有哪些字符，每个字符有多少个
        for (char targetChar : targetChars) {
            targetCharCounts[targetChar - 'a']++;
        }
        // 使用贴纸中的字符，移除 target 中的字符（扣减字符计数）
        for (char stickerChar : stickerChars) {
            targetCharCounts[stickerChar - 'a']--;
        }
        return appendChars(targetCharCounts);
    }

    private static String appendChars(int[] targetCharCounts) {
        // 将剩余的字符根据计数拼接起来
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (targetCharCounts[i] > 0) {
                for (int j = 0; j < targetCharCounts[i]; j++) {
                    char restChar = (char) (i + 'a');
                    builder.append(restChar);
                }
            }
        }
        return builder.toString();
    }


//    -----------------------------------------------------------------------

    // 使用剪枝优化暴力递归
    public static int minStickerPruning(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.isEmpty()) {
            return -1;
        }
        int length = stickers.length;
        int[][] stickersCharCounts = new int[length][26];
        for (int i = 0; i < length; i++) {
            String sticker = stickers[i];
            stickersCharCounts[i] = getCharCounts(sticker);
        }

        int useCount = minStickerRecursivePruning(stickersCharCounts, target);
        return useCount == Integer.MAX_VALUE ? -1 : useCount;
    }

    public static int minStickerRecursivePruning(int[][] stickers, String target) {
        if (target.isEmpty()) {
            return 0;
        }
        int[] targetCharCounts = getCharCounts(target);
        int useCount = Integer.MAX_VALUE; // 使用整数最大值指示没有有效的贴纸的情况
        for (int i = 0; i < stickers.length; i++) {
            int anyTargetCharIndex = target.toCharArray()[0] - 'a';

            int[] stickerCharCounts = stickers[i];
            boolean validSticker = stickerCharCounts[anyTargetCharIndex] > 0;
            if (validSticker) {
                String restTarget = getRestTarget(targetCharCounts, stickerCharCounts);
                useCount = Math.min(useCount, minStickerRecursivePruning(stickers, restTarget));
            }
        }
        // happy path: 本次选取一张有效的贴纸替换部分target中的字符（具体是哪一张是无所谓的） + 子过程restTarget使用的最少的贴纸数
        // sad path: 当前没有贴纸可以替换掉target中的字符
        return useCount == Integer.MAX_VALUE ? useCount : useCount + 1;
    }

    private static int[] getCharCounts(String string) {
        int[] stringCharCounts = new int[26];
        for (char c : string.toCharArray()) {
            int i = c - 'a';
            stringCharCounts[i]++;
        }
        return stringCharCounts;
    }

    private static String getRestTarget(int[] targetCharCounts, int[] stickerCharCounts) {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < 26; j++) {
            if (targetCharCounts[j] > 0) {
                int diff = targetCharCounts[j] - stickerCharCounts[j];
                int restCharCount = Math.max(diff, 0);
                for (int k = 0; k < restCharCount; k++){
                    int i = j + 'a';
                    builder.append((char)i);
                }
            }
        }
        return builder.toString();
    }

//    -------------------------------------------------------------------------------

    // 记忆化搜索
    public static int minStickersWithCache(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.isEmpty()) {
            return -1;
        }
        Map<String, Integer> dp = new HashMap<>();
        dp.put("", 0);

        int useCount = minStickersWithCache(stickers, target, dp);
        return useCount == Integer.MAX_VALUE ? -1 : useCount;
    }

    public static int minStickersWithCache(String[] stickers, String target, Map<String, Integer> dp) {
        if (dp.containsKey(target)) {
            return dp.get(target);
        }
        int useCount = Integer.MAX_VALUE; // 使用整数最大值指示没有有效的贴纸的情况
        for (String sticker : stickers) {
            String restTarget = removeChars(target, sticker);
            boolean isValidSticker = target.length() != restTarget.length();
            if (isValidSticker) {
                // 子过程对剩余的 restTarget 中的字符递归求解
                useCount = Math.min(useCount, minStickersWithCache(stickers, restTarget, dp));
            }
        }
        int result = useCount == Integer.MAX_VALUE ? useCount : useCount + 1;
        dp.put(target, result);
        return result;
    }


    public static void main(String[] args) {
        String[] stickers = new String[]{"with","example","science"};
        String target = "thehat";
        System.out.println(minStickers(stickers, target));
        System.out.println(minStickerPruning(stickers, target));
        System.out.println(minStickersWithCache(stickers, target));
    }


}
