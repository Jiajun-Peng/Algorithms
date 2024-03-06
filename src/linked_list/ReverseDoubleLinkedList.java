package linked_list;


/*
    反转双向链表
 */
public class ReverseDoubleLinkedList {

    public static class ListNode {
        public int value;
        public ListNode pre;
        public ListNode next;

        public ListNode(int value) {
            this.value = value;
        }
    }

    public static ListNode reverseDoubleLinkedList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = null;
        ListNode next = null;
        ListNode cur = head;
        while(cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur.pre;
            cur.pre = next;

            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        ListNode it = head;
        ListNode pre = null;
        for (int i = 1; i < 5; i++) {
            ListNode node = new ListNode(i);
            it.next = node;
            it.pre = pre;
            pre = it;
            it = node;
        }

        ListNode node = reverseDoubleLinkedList(head);

        ListNode back = null;
        while (node != null) {
            System.out.println(node.value);
            if (node.next == null) {
                back = node;
            }
            node = node.next;

        }
        // 反向再输出一遍
        while (back != null) {
            System.out.println(back.value);
            back = back.pre;
        }
    }
}
