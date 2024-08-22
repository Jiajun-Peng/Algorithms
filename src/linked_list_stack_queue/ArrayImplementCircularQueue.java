package linked_list_stack_queue;

/*
使用数组实现循环队列
[622. 设计循环队列](https://leetcode-cn.com/problems/design-circular-queue/)
https://leetcode.com/problems/design-circular-queue/

你的实现应该支持如下操作：

MyCircularQueue(k): 构造器，设置队列长度为 k 。
Front: 从队首获取元素。如果队列为空，返回 -1 。
Rear: 获取队尾元素。如果队列为空，返回 -1 。
enQueue(value): 向循环队列插入一个元素。如果成功插入则返回真。
deQueue(): 从循环队列中删除一个元素。如果成功删除则返回真。
isEmpty(): 检查循环队列是否为空。
isFull(): 检查循环队列是否已满。

提示：

所有的值都在 0 至 1000 的范围内；
操作数将在 1 至 1000 的范围内；
请不要使用内置的队列库。

 */
public class ArrayImplementCircularQueue {

    public static class MyCircularQueue {

        private int[] array;

        private int curWrite; // 当前已经写到的位置，尾节点
        private int curRead; // 当前已经读到的位置，头节点的前一个节点
        private int usedSize;

        public MyCircularQueue(int k) {
            array = new int[k];
            curWrite = -1;
            curRead = -1;
            usedSize = 0;
        }

        public boolean enQueue(int value) {
            if (isFull()) {
                return false;
            }
            curWrite = curWrite == array.length - 1 ? 0 : curWrite + 1; // 写到数组的末尾后回到0位置开始写
            array[curWrite] = value;
            usedSize++;
            return true;
        }

        public boolean deQueue() {
            if (isEmpty()) {
                return false;
            }
            curRead = curRead == array.length - 1 ? 0 : curRead + 1; // 读到数组的末尾后回到0位置开始读
            usedSize--;
            return true;
        }

        public int Front() {
            if (isEmpty()) {
                return -1;
            }
            int front = curRead == array.length - 1 ? 0 : curRead + 1;
            return array[front];
        }

        public int Rear() {
            if (isEmpty()) {
                return -1;
            }
            return array[curWrite];
        }

        public boolean isEmpty() {
            return usedSize == 0;
        }

        public boolean isFull() {
            return usedSize == array.length;
        }

    }
}
