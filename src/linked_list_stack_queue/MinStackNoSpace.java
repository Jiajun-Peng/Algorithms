package linked_list_stack_queue;

import java.util.Stack;

/*
[155. 最小栈](https://leetcode-cn.com/problems/min-stack/)

设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。

实现 MinStack 类:

MinStack() 初始化堆栈对象。
void push(int val) 将元素val推入堆栈。
void pop() 删除堆栈顶部的元素。
int top() 获取堆栈顶部的元素。
int getMin() 获取堆栈中的最小元素。

提示：
-231 <= val <= 231 - 1
pop、top 和 getMin 操作总是在 非空栈 上调用
push, pop, top, and getMin最多被调用 3 * 104 次


解题思路：
    使用一个min字段保存当前的最小值，并使用栈保存加入元素与min差值
    那么，当栈的值弹出时，只要计算min和差值的关系就可以得到原始的值
 */
public class MinStackNoSpace {

    private Stack<Long> stack;
    private long min;

    public MinStackNoSpace() {
        stack = new Stack<>();
    }

    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(0L);
            min = val;
        } else {
            long diff = (long) val - min; // 负数-正数可能产生溢出，所以用long
            stack.push(diff);
            // 因为可能diff < 0，所以可能val < min
            min = Math.min(val, min);
        }
    }

    public void pop() {
        long diff = stack.pop();
        if (diff < 0) {
            min = min - diff; // diff < 0说明val比min小，所以上一步的min = min - diff，即栈中的原始弹出时，需要同步将min值还原
        }
    }

    public int top() {
        long diff = stack.peek();
        return (int) (diff < 0 ? min : min + diff); // diff < 0说明val比min小，所以当前的val就是min
    }

    public int getMin() {
        return (int) min;
    }
}
