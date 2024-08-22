package linked_list;

/*

160. 相交链表（两条无环单链表的第一个相交节点）: https://leetcode-cn.com/problems/intersection-of-two-linked-lists

题目中明确了两链表无环。

无环的两条链表，要么不相交、相交的话只能是按合并的方式相交（因为无环）

解题思路：
    分别遍历两条链表到最后一个节点（不空），分别记录下长度和最后一个节点，再对比最后一个节点是否内存相等（==），如果相等则相交。
    再找出相交节点，因为记录了长度，让更长的链表先走多余的步数，在同步向前走，一定会在相交节点相遇。
 */
public class C07_3_IntersectionOfTwoLinkedLists {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode curA = headA;
        ListNode curB = headB;
        int lengthA = 1;
        int lengthB = 1;
        while (curA.next != null) {
            lengthA++;
            curA = curA.next;
        }
        while (curB.next != null) {
            lengthB++;
            curB = curB.next;
        }
        if (curA != curB) {
            return null;
        }

        int diff = lengthA - lengthB;

        if (diff > 0) {
            for (int i = 1; i <= diff; i++) {
                headA = headA.next;
            }
        } else {
            for (int i = 1; i <= -diff; i++) {
                headB = headB.next;
            }
        }

        while (headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }
}
