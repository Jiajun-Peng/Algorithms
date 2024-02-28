package leetcode_top_100;

/*
https://leetcode.cn/problems/add-two-numbers/

给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
请你将两个数相加，并以相同形式返回一个表示和的链表。
你可以假设除了数字 0 之外，这两个数都不会以 0 开头。

示例 1：
输入：l1 = [2,4,3], l2 = [5,6,4]
输出：[7,0,8]
解释：342 + 465 = 807.

示例 2：
输入：l1 = [0], l2 = [0]
输出：[0]

示例 3：
输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
输出：[8,9,9,9,0,0,0,1]

提示：
每个链表中的节点数在范围 [1, 100] 内
0 <= Node.val <= 9
题目数据保证列表表示的数字不含前导零
 */
public class Code2_AddTwoNumbers {

    /*
    根据题意，链表的头节点是整数的个位，那么相加时，正好从头开始遍历两个链表进行相加操作
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode resultHeadNode = new ListNode();
        ListNode curResultNode = resultHeadNode;

        int carryOver = 0; // 进位值，0或1，因为两个个位数之和小于20
        while (l1 != null || l2 != null) {
            int partOne = l1 != null ? l1.val : 0;
            int partTwo = l2 != null ? l2.val : 0;

            int sum = partOne + partTwo + carryOver;
            carryOver = sum >= 10 ? 1 : 0;
            int curVal = sum >= 10 ? sum - 10 : sum; // 两个个位数之和小于20

            ListNode curNode = new ListNode(curVal);
            curResultNode.next = curNode;
            curResultNode = curNode;

            // 加数后移
            l1 = l1 != null ? l1.next : null;
            l2 = l2 != null ? l2.next : null;
        }

        // 最后的进位
        if (carryOver == 1) {
            curResultNode.next = new ListNode(1);
        }

        return resultHeadNode.next; // 第一个空节点下一个节点就是首个有效的节点
    }

    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
    }
}
