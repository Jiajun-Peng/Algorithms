package linked_list;

import java.util.ArrayList;
import java.util.List;

/*
    查找链表的中间节点
    [876. 链表的中间结点](https://leetcode-cn.com/problems/middle-of-the-linked-list/)

    给定一个头节点为 head 的非空单链表，返回链表的中间节点。

    如果有两个中间节点，则返回第二个中间节点。

    解题思路：
        解法1. 使用快慢指针，快指针每次走两步，慢指针每次走一步，当快指针走到链表尾部时，慢指针刚好走到链表的中间位置

        解法2. 将链表的节点放入数组中，利用数组随机访问的性质，就可以随机访问任意节点
              已知数组的(arr.size()-1) / 2得到的是数组的中点或左中点的位置
              数组的arr.size() / 2得到的是数组的中点或右中点的位置。
 */
public class LinkedListMid {

    public static class ListNode {
        public int val;
        public ListNode next;
        public ListNode(int value) {
            this.val = value;
        }
    }

    // 中点或左中点位置
    public static ListNode midOrLeftMidNode(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) { // 少于等于两个节点
            return head;
        }
        // 从同一个位置开始，当fast指针到末尾时，slow指针将来到链表的左中点（链表长度为偶数时）或中点（链表长度为奇数时）上
        ListNode fast = head;
        ListNode slow = head;

        // fast一次走两步，知道fast无法进行下一条时
        while (fast.next != null && fast.next.next != null){//因为fast.next可能为null会出现空指针异常
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // 中点或右中点位置
    public static ListNode midOrRightMidNode(ListNode head) {
        if (head == null || head.next == null) { // 少于等于一个节点
            return head;
        }
        if (head.next.next == null) { // 两个节点
            return head.next;
        }
        // 如果同时往前进一步，相当于将原链表截断第一个节点后，从新链表的第一个位置开始
        // 最终慢指针将来到新链表的左中点或中点上。
        // 当原链表为奇数时，慢指针将来到新链表（偶数）的左中点（原链表的中点）
        // 当原链表为偶数时，慢指针将来到新链表（奇数）的中点（原链表的右中点）
        ListNode fast = head.next;
        ListNode slow = head.next;

        while (fast.next != null && fast.next.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // （中点或左中点）的前一个节点
    public static ListNode midOrLeftMidPreNode(ListNode head){
        if (head == null || head.next == null || head.next.next == null){
            return head;
        }

        ListNode fast = head;
        ListNode slow = head;
        ListNode pre = null;

        while (fast.next != null && fast.next.next != null){
            pre = slow;

            slow = slow.next; // slow最后将会来到中点或左中点，那么在slow最后移动前的位置就是（中点或左中点）的前一个节点
            fast = fast.next.next;
        }
        return pre;

    }

    // （中点或右中点）的前一个节点
    public static ListNode midOrRightMidPreNode(ListNode head){
        if (head == null || head.next == null || head.next.next == null){
            return head;
        }

        ListNode fast = head.next;
        ListNode slow = head.next;

        ListNode pre = null;

        while (fast.next != null && fast.next.next != null){
            pre = slow;

            slow = slow.next; // slow最后将会来到中点或右中点，那么在slow最后移动前的位置就是（中点或右中点）的前一个节点
            fast = fast.next.next;
        }
        return pre;

    }

    //===========================================

    // 转换为数组后返回中点或左中点
    public static ListNode midOrLeftMidNodeWithArray(ListNode head) {
        if (head == null) {
            return head;
        }
        List<ListNode> list = convertToArray(head);
        int midOrLeftMidIndex = (list.size() - 1) / 2;
        return list.get(midOrLeftMidIndex);
    }

    // 转换为数组后返回中点或右中点
    public static ListNode midOrRightMidNodeWithArray(ListNode head) {
        if (head == null) {
            return head;
        }
        List<ListNode> list = convertToArray(head);
        int midOrLeftMidIndex = (list.size()) / 2;
        return list.get(midOrLeftMidIndex);
    }

    // 将链表转换为数组
    private static List<ListNode> convertToArray(ListNode head) {
        List<ListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }
        return list;
    }


    public static void main(String[] args) {

        ListNode head = new ListNode(1);
        ListNode cur = head;
        for (int i = 2; i <= 6; i++) { // 1~5时为奇数，仅有中点，中点是3；1~6时为偶数，左中点是3，右中点是4
            ListNode node = new ListNode(i);
            cur.next = node;
            cur = node;
        }

        ListNode listNode = midOrLeftMidPreNode(head);
        System.out.println(listNode.val);

        ListNode listNode1 = midOrRightMidPreNode(head);
        System.out.println(listNode1.val);

        ListNode listNode2 = midOrLeftMidNodeWithArray(head);
        System.out.println(listNode2.val);

        ListNode listNode3 = midOrRightMidNodeWithArray(head);
        System.out.println(listNode3.val);


    }
}
