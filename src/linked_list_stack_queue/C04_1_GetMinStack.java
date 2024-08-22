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
 */
public class C04_1_GetMinStack {

    private Stack<Integer> dataStack;
    private Stack<Integer> minStack;

    public C04_1_GetMinStack() {
        dataStack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        dataStack.push(val);
        if (minStack.isEmpty()) {
            minStack.push(val);
        } else {
            Integer peek = minStack.peek();
            if (val <= peek) {
                minStack.push(val);
            }
        }
    }

    public void pop() {
        Integer pop = dataStack.pop();

        Integer min = minStack.peek();
        if (pop <= min) {
            minStack.pop();
        }
    }

    public int top() {
        return dataStack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }

    public static void main(String[] args) {
        C04_1_GetMinStack minStack1 = new C04_1_GetMinStack();
        minStack1.push(-2);
        minStack1.push(0);
        minStack1.push(-3);

        System.out.println(minStack1.getMin());

    }
}
