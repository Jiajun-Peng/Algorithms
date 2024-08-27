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
        if ((index + 1 < charArray.length) && (charArray[index] > '0' && charArray[index + 1] <= '5')) { // 以当前位置为起始，后面至少还有一位，可以与当前位置组成双数
//            int tensPlace = Integer.parseInt(String.valueOf(charArray[index]));
            int tensPlace = charArray[index] - '0'; // 0~9 的数字字符在 ascii 码表的值是 48 ~ 57
            if (tensPlace != 0) { // 双数不能以 0 开始
//                int unitsPlace = Integer.parseInt(String.valueOf(charArray[index + 1]));
                int unitsPlace = charArray[index + 1] - '0';
                int num = tensPlace * 10 + unitsPlace;
                if (num <= 25) { // 字母的范围是 0 ~ 25
                    dual = crackNumber(charArray, index + 2);
                }
            }
        }
        return single + dual;
    }

    public static void main(String[] args) {
        int num = 216612;
        System.out.println(crackNumber(num));
    }
}
