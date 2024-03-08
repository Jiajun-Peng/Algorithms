package linked_list;

import java.util.ArrayList;
import java.util.List;

/*
    [234. 回文链表](https://leetcode.cn/problems/palindrome-linked-list/)
    请判断一个链表是否为回文链表。

    回文的性质：无论是从头遍历到尾，还是从尾遍历到头，遍历到的节点的值组成的序列都是一样的。
              所以，同时从首尾按同样的步长进行遍历时，同一时刻遍历到的节点的值是一样的。

    解题思路：
        因为是单向链表所以无法直接反向遍历，所以需要创造出可双向遍历的条件。

    方案一：
        1. 遍历链表，将链表的节点值存入数组中。
        2. 使用双指针法，从数组的首尾同时遍历，判断遍历到的节点值是否相等。
        3. 如果遍历到的节点值都相等，则链表是回文链表，否则不是。

    方案二：
        1. 使用快慢指针法，找到链表的中间节点。
        2. 将链表的后半部分反转。
        3. 使用双指针法，从链表的首尾同时遍历，判断遍历到的节点值是否相等。
        4. 如果遍历到的节点值都相等，则链表是回文链表，否则不是。

     比较：
        方案一需要额外的O(n)的空间，而方案二不需要额外的空间。
        在笔试时应该使用更简单的方法。

     注意：当需要返回原链表时，需要将方案二中的链表反转还原
 */
public class PalindromeLinkedList {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }

    // 将链表保存在数组中，使用双指针从头尾遍历链表
    public static boolean palindromeLinkedListWithArray(ListNode head) {
        if (head == null) {
            return false;
        }

        // 将链表的值逐个保存进数组中
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }

        int L = 0;
        int R = list.size() - 1;
        while (L <= R) { // 双指针双向靠拢时，当链表长度为奇数时，中点位置L==R, 当链表长度为偶数时，有左右两个中点，L + 1 == R,
            if (list.get(L) != (int) list.get(R)) return false;
            L++; // 双指针双向奔赴
            R--;
        }
        return true;
    }


    public static boolean palindromeLinkedList(ListNode head) {
        if (head == null) {
            return false;
        } else if (head.next == null) { // 只有一个节点
            return true;
        } else if (head.next.next == null) { // 只有两个节点。其实只有两个节点是，第一个节点就是左中点
            return head.val == head.next.val;
        }

        // 找到链表的中点，两个节点同时出发，slow指针将来到中点或左中点
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        // 无论是中点还是左中点，以slow划分为左右两部分
        // 反转右边的部分
        ListNode pre = slow;
        ListNode cur = slow.next;
        slow.next = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        // 经过反转之后，pre将来到最后一个节点的位置
        ListNode tail = pre;

        while (head != null && tail != null) { // 链表长度为奇数是最后一个比较的值是中点位置，也可以只判断(head != null)
            if (head.val != tail.val) return false;
            head = head.next;
            tail = tail.next;
        }
        return true;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(2);
        listNode.next.next.next = new ListNode(1);

        boolean b = palindromeLinkedListWithArray(listNode);
        System.out.println(b);

        boolean b1 = palindromeLinkedList(listNode);
        System.out.println(b1);
    }
}
