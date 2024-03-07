package linked_list;

/*

删除链表中的节点
[LCR 136. 删除链表的节点](https://leetcode.cn/problems/shan-chu-lian-biao-de-jie-dian-lcof/)

给定单向链表的头指针和一个要删除的节点的值，定义一个函数删除该节点。

返回删除后的链表的头节点。

示例 1:

输入: head = [4,5,1,9], val = 5
输出: [4,1,9]
解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
示例 2:

输入: head = [4,5,1,9], val = 1
输出: [4,5,9]
解释: 给定你链表中值为 1 的第三个节点，那么在调用了你的函数之后，该链表应变为 4 -> 5 -> 9.


说明：

题目保证链表中节点的值互不相同
若使用 C 或 C++ 语言，你不需要 free 或 delete 被删除的节点

 */
public class DeleteNodeWithHead {

    public static class ListNode {
        public int val;
        public ListNode next;
        public ListNode(int value) {
            this.val = value;
        }
    }

    public static ListNode deleteNode(ListNode head, int val) {
        if (head == null) {
            return head;
        }

        // 删除的是头节点时，那么将头节点断开，并返回头节点的下一个节点
        if (head.val == val) {
            ListNode next = head.next;
            head.next = null;
            return next;
        }

        // 非头节点时
        ListNode curNode = head;
        ListNode preNode = null;
        while (curNode != null) {
            if (curNode.val == val) { // 无论是中间节点还是尾节点，都可以通过将前一个节点连接上当前节点的下一个节点来完成删除当前节点
                ListNode next = curNode.next; // 如果当前节点是尾节点，下一个节点为null，preNode指向null是没问题的
                curNode.next = null;
                preNode.next = next;
            }
            // 当前节点后移
            ListNode next = curNode.next;
            preNode = curNode;
            curNode = next;
        }
        return head;
    }

}
