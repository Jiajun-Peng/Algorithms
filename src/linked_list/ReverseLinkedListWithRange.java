package linked_list;

/*
    反转单向链表
    [92. 反转链表ii](https://leetcode.cn/problems/reverse-linked-list-ii/)

    反转指定范围内的链表
 */
public class ReverseLinkedListWithRange {

    public static class ListNode {
        public int value;
        public ListNode next;
        public ListNode(int value) {
            this.value = value;
        }
    }

    public static ListNode reverseLinkedList(ListNode head, int left, int right) {
        if (head == null || head.next == null  || left > right) {
            return head;
        }

        // 确定left节点和left节点的前一个节点的位置
        ListNode preLeftNode = null;
        if (left > 1) {
            preLeftNode = head;
            for (int i = 1; i < left - 1; i++) {
                preLeftNode = preLeftNode.next;
            }
        }
        ListNode leftNode = preLeftNode != null ? preLeftNode.next : head;

        // 确定right节点和right节点的下一个节点的位置
        ListNode rightNode = leftNode;
        for (int i = left; i < right; i ++) {
            rightNode = rightNode.next;
        }
        ListNode rightNodeNext = rightNode.next;

        rightNode.next = null; // 断开链表，后续的反转行为从断开的位置终止

        ListNode node = reverseLinkedList(leftNode);

        // 如果preLeftNode为空的话，说明是从第一个节点开始反转，反转后的节点即为新的头节点
        if (preLeftNode == null) {
            head = node;
        } else {
            preLeftNode.next = node;
        }

        // 原来的左节点反转之后变为了最右的节点，连接后续的部分，如果rightNodeNext为空也同样正确
        leftNode.next = rightNodeNext;

        return head;
    }

    private static ListNode reverseLinkedList(ListNode head) {
        ListNode pre = null;
        ListNode next = null;
        ListNode cur = head; // 防止头节点的指针变化
        while (cur != null) {

            next = cur.next; // 每每来到一个节点，先反转其next的值，反转前需要先记住next的值
            cur.next = pre; // 设置next的值为前一个节点，初始时，前一个节点为null

            pre = cur; // 准备前往下一个节点，当前节点将作为下一个节点的next，所以先复制
            cur = next; // 前往下一个节点
        }

        return pre;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode it = head;
        for (int i = 2; i < 3; i++) {
            ListNode node = new ListNode(i);
            it.next = node;
            it = node;
        }

        ListNode node = reverseLinkedList(head, 1, 2);

        while (node != null) {
            System.out.println(node.value);
            node = node.next;
        }
    }
}
