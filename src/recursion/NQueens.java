package recursion;

/*
52. N 皇后 II：https://leetcode.cn/problems/n-queens-ii
 */
public class NQueens {
    private static int totalNQueens(int n) {
        if (n < 1) return 0;
        int[] place = new int[n];
        return totalNQueens(n, 0, place);
    }

    private static int totalNQueens(int n, int row, int[] place) {
        if (row == n) return 1;

        int result = 0;
        for (int col = 0; col < n; col++) {
            if (isValid(row, col, place)) {
                place[row] = col;
                result += totalNQueens(n, row + 1, place);
            }
        }
        return result;
    }

    private static boolean isValid(int row, int col, int[] place) {
        for (int preRow = 0; preRow < row; preRow++) {
            if (col == place[preRow]) return false;
            if (Math.abs(row - preRow) == Math.abs(col - place[preRow])) return false;
        }
        return true;
    }

    // 使用位图记录有效的位置
    private static int totalNQueensWithBitmap(int n) {
        if (n < 1 || n > 32) return 0; // 使用 int 作为位图，无法计算超出 32 位情况

        // 用于限定有多少个皇后，即多少列，如果有10个皇后，那最右10位就都是1
        int nBits = n == 32 ? -1 : (1 << n) -1;
        return totalNQueensWithBitmap(nBits, 0, 0, 0);
    }

    private static int totalNQueensWithBitmap(int nBits, int colLimit, int leftSlashLimit, int rightSlashLimit) {
        if (colLimit == nBits) return 1; // 每一列都有放置皇后，说明是一种有效的方案

        int validColBitsForCurRow = nBits & (~(colLimit | leftSlashLimit | rightSlashLimit));
        int mostRightValidCol = 0;
        int result = 0;
        while (validColBitsForCurRow != 0) {
            mostRightValidCol = validColBitsForCurRow & (~validColBitsForCurRow + 1); // 取最右边的1，即当前行的最右边的有效的列

            result += totalNQueensWithBitmap(nBits,
                    colLimit | mostRightValidCol, // 或运算设置当前列已放置皇后
                    (leftSlashLimit | mostRightValidCol) << 1, // 下一行的左斜线的限制是当前左斜线限制左移一位
                    (rightSlashLimit | mostRightValidCol) >>> 1); // 下一行的右斜线的限制是当前右斜线限制右移一位

            validColBitsForCurRow = validColBitsForCurRow - mostRightValidCol;
        }
        return result;
    }

    public static void main(String[] args) {
        int n = 8;
        System.out.println(totalNQueens(n));
        System.out.println(totalNQueensWithBitmap(n));
    }
}
