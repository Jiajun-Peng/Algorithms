package linked_list;

import java.util.List;

/*
    反转单向链表
    [206. 反转链表](https://leetcode-cn.com/problems/reverse-linked-list/)
 */
public class ReverseLinkedList {

    public static class ListNode {
        public int value;
        public ListNode next;
        public ListNode(int value) {
            this.value = value;
        }
    }

    public static ListNode reverseLinkedList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = null;
        ListNode next = null;
        ListNode cur = head;
        while (cur != null) {

            next = cur.next; // 每每来到一个节点，先反转其next的值，反转前需要先记住next的值
            cur.next = pre; // 设置next的值为前一个节点，初始时，前一个节点为null

            pre = cur; // 准备前往下一个节点，当前节点将作为下一个节点的next，所以先复制
            cur = next; // 前往下一个节点
        }

        return pre; // 考虑只剩最后一个节点未翻转时，将最后一个节点反转后，该节点的值就是pre的值，也就是说最后一个节点的值就是反转后头节点的值
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(0);
        ListNode it = head;
        for (int i = 1; i < 5; i++) {
            ListNode node = new ListNode(i);
            it.next = node;
            it = node;
        }

        ListNode node = reverseLinkedList(head);

        while (node != null) {
            System.out.println(node.value);
            node = node.next;
        }
    }
}
