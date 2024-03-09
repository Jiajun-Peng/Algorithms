package linked_list;

/*
    [141. 环形链表（判断一条单链表是否有环）](https://leetcode-cn.com/problems/linked-list-cycle/)
    给定一个链表，判断链表中是否有环。

    解题思路：
        使用快慢指针，快指针每次走两步，慢指针每次走一步，在有环的情况下，入环后快慢指针一定会在环中位置相遇。
 */
public class IsLinkedListCycle {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) { // 无节点或一个节点
            return false;
        }

        ListNode slow = head;
        ListNode fast = head;

        do {
            if (fast.next == null || fast.next.next == null) { // 判断是否有终点
                return false; // 有终点
            }
            slow = slow.next;
            fast = fast.next.next;
        } while (fast != slow);

        return true;
    }
}
