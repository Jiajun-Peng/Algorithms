package dp;

/*
剑指 Offer 46. 把数字翻译成字符串: https://leetcode-cn.com/problems/ba-shu-zi-fan-yi-cheng-zi-fu-chuan-lcof
 */
public class C05_CrackNumber {

    public static int crackNumber(int ciphertext) {
        if (ciphertext < 0) return 0;
        char[] charArray = String.valueOf(ciphertext).toCharArray();
        return crackNumber(charArray, 0);
    }

    private static int crackNumber(char[] charArray, int index) {
        if (index == charArray.length) {
            // 如果能全部正确的遍历完所有字符，那么说明整个方案是正确的，返回 1
            return 1;
        }
        // 取一位进行解析，并将指针移动到下一位
        int single = crackNumber(charArray, index + 1);

        int dual = 0;
        if ((index + 1 < charArray.length)) { // 以当前位置为起始，后面至少还有一位，可以与当前位置组成双数
            int tensPlace = charArray[index] - '0'; // 0~9 的数字字符在 ascii 码表的值是 48 ~ 57
            int unitsPlace = charArray[index + 1] - '0';
            if (tensPlace > 0 && tensPlace <= 2) { // 双数不能以 0 开始
                int num = tensPlace * 10 + unitsPlace;
                if (num <= 25) { // 字母的范围是 0 ~ 25
                    dual = crackNumber(charArray, index + 2);
                }
            }
        }
        return single + dual;
    }

    public static int crackNumberWithDp(int ciphertext) {
        if (ciphertext < 0) return 0;
        char[] charArray = String.valueOf(ciphertext).toCharArray();
        int length = charArray.length;
        int[] dp = new int[length + 1]; // 只与遍历位置单个变量有关

        dp[length] = 1; // 根据递归的终止条件，设置dp表

        // 从右往左填充，也就是从位数少的情况逐渐推算出位数多的情况
        for (int index = length - 1; index >= 0; index--) {
            int single = dp[index + 1]; // 将递归调用修改为从dp表中获取结果
            int dual = 0;
            if ((index + 1 < charArray.length)) { // 以当前位置为起始，后面至少还有一位，可以与当前位置组成双数
                int tensPlace = charArray[index] - '0'; // 0~9 的数字字符在 ascii 码表的值是 48 ~ 57
                int unitsPlace = charArray[index + 1] - '0';
                if (tensPlace > 0 && tensPlace <= 2) { // 双数不能以 0 开始
                    int num = tensPlace * 10 + unitsPlace;
                    if (num <= 25) { // 字母的范围是 0 ~ 25
                        dual = dp[index + 2]; // 将递归调用修改为从dp表中获取结果
                    }
                }
            }
            dp[index] = single + dual; // 将递归中的返回语句修改为对dp表的赋值语句
        }
        return dp[0];
    }

    public static void main(String[] args) {
        int ciphertext = 216612;
        System.out.println(crackNumber(ciphertext));
        System.out.println(crackNumberWithDp(ciphertext));
    }
}
