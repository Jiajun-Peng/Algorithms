package dp;

/**
 * [剑指 Offer 10- I. 斐波那契数列](https://leetcode-cn.com/problems/fei-bo-na-qi-shu-lie-lcof/)
 *
 * 斐波拉契数列是这样一个数列：0、1、1、2、3、5、8、13、21、34、……
 * 在数学上，斐波那契数列以如下递推的方法定义：F(0)=0，F(1)=1, F(n)=F(n - 1)+F(n - 2)（n ≥ 2，n ∈ N*）。
 *
 *
 * 在暴力递归法中包含很多重复过程，比如：f(7) = f(6) + f(5),但是f(6) = f(5) + f(4),这里面f(5)的计算过程就重复了两次。
 * 当n越大时，重复的次数就越多。为了避免计算重复值，可以将中间计算过程缓存起来，这就是动态规划。
 *
 * 当使用缓存时，计算f(n)时的时间复杂度时O(n)
 */
public class Fibonacci {
    public int f(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return f(n - 1) + f(n - 2);
    }
}
