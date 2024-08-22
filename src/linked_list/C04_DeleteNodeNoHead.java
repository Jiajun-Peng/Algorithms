package linked_list;

/*
    删除链表中的节点
    [237. 删除链表中的节点](https://leetcode-cn.com/problems/delete-node-in-a-linked-list/)

    删除链表中的节点，只给出要删除的节点，不给出头节点

    具体题目信息请查看leetcode原题

    提示：

    链表中节点的数目范围是 [2, 1000]
    -1000 <= Node.val <= 1000
    链表中每个节点的值都是 唯一 的
    需要删除的节点 node 是 链表中的节点 ，且 不是末尾节点


    解题思路：
        删除链表的节点，一般是通过将待删除节点的前一个节点的next指针指向待删除节点的后一个节点
        但是，这里并不能访问到待删除节点的前一个节点，所以只能使用后面的值来覆盖要删除的节点
 */
public class C04_DeleteNodeNoHead {

    public static class ListNode {
        public int val;
        public ListNode next;
        public ListNode(int value) {
            this.val = value;
        }
    }

    public static void deleteNode(ListNode node) {

        ListNode next = node.next;
        ListNode nextNext = next.next;

        node.val = next.val;
        next.next = null; // 断开下一个节点的next

        node.next = nextNext;

    }
}
