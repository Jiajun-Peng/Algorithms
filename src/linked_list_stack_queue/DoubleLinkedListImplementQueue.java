package linked_list_stack_queue;

/*
使用双向链表实现队列

实现add和poll方法
 */
public class DoubleLinkedListImplementQueue {

    public static class ListNode {
        int val;
        ListNode pre;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static class MyQueue {
        private ListNode head;
        private ListNode tail;

        public void add(int val) {
            ListNode node = new ListNode(val);
            if (tail == null) {
                head = tail = node;
                return;
            }
            tail.next = node;
            node.pre = tail;
            tail = node;
        }

        public ListNode poll() {
            if (head == null || head == tail) {
                ListNode result = head;
                head = tail = null;
                return result;
            }
            ListNode result = head;
            head = head.next;
            head.pre = null;
            result.next = null;
            return result;
        }
    }

    public static void main(String[] args) {
        MyQueue myQueue = new MyQueue();
        myQueue.add(1);
        myQueue.add(2);
        myQueue.add(3);

        System.out.println(myQueue.poll().val);
        System.out.println(myQueue.poll().val);
        System.out.println(myQueue.poll().val);
        System.out.println(myQueue.poll() == null);

        myQueue.add(1);
        System.out.println(myQueue.poll().val);
        System.out.println(myQueue.poll() == null);
        System.out.println(myQueue.poll() == null);
        myQueue.add(2);
        myQueue.add(3);

        System.out.println(myQueue.poll().val);
        System.out.println(myQueue.poll().val);
        System.out.println(myQueue.poll() == null);
        System.out.println(myQueue.poll() == null);
        System.out.println(myQueue.poll() == null);
    }
}
