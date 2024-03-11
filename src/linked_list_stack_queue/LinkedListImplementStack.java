package linked_list_stack_queue;

/*
仅使用链表实现栈

栈的特点是先进后出，后进先出，所以只需要一个指针指向栈顶即可。

向链表就可以实现

实现 MyStack 类：

void push(int x) 将元素 x 压入栈顶。
int pop() 移除并返回栈顶元素。
int top() 返回栈顶元素。
boolean empty() 如果栈是空的，返回 true ；否则，返回 false 。

提示：
1 <= x <= 9
 */
public class LinkedListImplementStack {

    static class MyStack {

        static class ListNode {
            int val;
            ListNode next;
            ListNode(int x) {
                val = x;
            }
        }

        private ListNode head;

        public void push(int x) {
            if (head == null) {
                head = new ListNode(x);
            } else {
                ListNode newNode = new ListNode(x);
                newNode.next = head;
                head =newNode;
            }
        }

        public int pop() {
            if (empty())  {
                return -1;
            }
            ListNode next = head.next;
            head.next = null;
            int result  = head.val;
            head = next;
            return result;
        }

        public int top() {
            if (empty()) {
                return -1;
            }
            return head.val;
        }

        public boolean empty() {
            return head == null;
        }

    }

}
