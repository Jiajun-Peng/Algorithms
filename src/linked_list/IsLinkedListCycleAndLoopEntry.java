package linked_list;

/*
[142. 环形链表 II（环单链表的入环节点）](https://leetcode-cn.com/problems/linked-list-cycle-ii/)

给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。

使用快慢指针，如果有环，快慢指针一定会在环中的某个位置相遇。
快指针回到头节点变成一次走一步，慢指针继续从相遇位置向下一次一步，最终一定会在入环的节点再次相遇。原理是追击问题。

原理：
    假设链表起点到环入口的距离是`D`，环入口到快慢指针首次相遇点的距离是`S1`，从这个相遇点继续前进并回到环入口的距离是`S2`，所以环的长度是`(S1 + S2)`。
    因此，当快慢指针首次相遇时，慢指针走了`D + S1`步，快指针走了`D + S1 + n(S1 + S2)`步，其中`n`是快指针在环内多转的圈数。
    因为快指针的速度是慢指针的两倍，所以快指针走的步数是慢指针的两倍，即`2(D + S1) = D + S1 + n(S1 + S2)`。
    通过简化这个等式，我们可以得到`D = n(S1 + S2) - S1`。
    在`n`等于`1`的情况下`D = S2`，即从起点到入环点的距离等于从相遇点到入环点的距离。所以他们会在入环点相遇。
 */
public class IsLinkedListCycleAndLoopEntry {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode detectCycleAndLoopEntry(ListNode head) {
        if (head == null || head.next == null) { // 少于等于一个节点时，一定没有入环节点
            return null;
        }

        // 为了得到正确的值，必须从同一个节点出发
        ListNode slow = head;
        ListNode fast = head;
        do {
            if (fast.next == null || fast.next.next == null) { // 判断是否有终点
                return null; // 有终点，无环，直接退出
            }
            slow = slow.next;
            fast = fast.next.next;
        } while (slow != fast); // 直到在环中相遇

        fast = head; // 回到头节点，直到相遇
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }
}
