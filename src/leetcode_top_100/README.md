# 1. 两数之和

# 2. 两数相加

[2. 两数相加](https://leetcode.cn/problems/add-two-numbers/)

给你两个 **非空** 的链表，表示两个非负的整数。它们每位数字都是按照 **逆序** 的方式存储的，并且每个节点只能存储 **一位** 数字。

请你将两个数相加，并以相同形式返回一个表示和的链表。

你可以假设除了数字 0 之外，这两个数都不会以 0 开头。

 

**示例 1：**

![img](./img/addtwonumber1.jpg)

```
输入：l1 = [2,4,3], l2 = [5,6,4]
输出：[7,0,8]
解释：342 + 465 = 807.
```

**示例 2：**

```
输入：l1 = [0], l2 = [0]
输出：[0]
```

**示例 3：**

```
输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
输出：[8,9,9,9,0,0,0,1]
```

 

**提示：**

- 每个链表中的节点数在范围 `[1, 100]` 内
- `0 <= Node.val <= 9`
- 题目数据保证列表表示的数字不含前导零

## 解

逆序代表低位在左，高位在右，即2 -> 4 -> 3表示324

不可用的方法：不可以将链表中的数字表示为一个单独的整数，因为链表的节点数可能超出了整数可表示的范围，造成溢出。

方法一：先将两个链表转换为两个数组，再将数组中的元素依次相加，最后可能会产生一个进位而超出数组的范围，这个进位可以用一个boolean变量保存。数组比链表更好操作，但是浪费空间。

因为链表已经是从低位到高位，那么依次从左向右相加就可以，此题主要考查链表的操作。

```java
/**
* Definition for singly-linked list.
* public class ListNode {
*     int val;
*     ListNode next;
*     ListNode() {}
*     ListNode(int val) { this.val = val; }
*     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
* }
*/
class Solution {
  public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
      ListNode preResultNode = new ListNode();
      ListNode resultNodeHead = preResultNode; // 使用一个空节点作为结果链表的头节点
      ListNode curNode1 = l1;
      ListNode curNode2 = l2;
      int carryOver = 0;
      while (curNode1 != null || curNode2 != null) {
          int curVal1 = curNode1 != null ? curNode1.val : 0;
          int curVal2 = curNode2 != null ? curNode2.val : 0;

          int curSum = curVal1 + curVal2 + carryOver;
          int curResultVal =  curSum % 10;
          carryOver = curSum / 10;
          // 还可以通过判断是否大于10来确定是否进位
          // int curResultVal =  curSum >= 10 ? curSum - 10 : curSum; // 个位相加不会大于20
          // carryOver = curSum >= 10 ? 1 : 0; // 大于10则需进位

          ListNode curResultNode = new ListNode(curResultVal);
          preResultNode.next = curResultNode;
          preResultNode = preResultNode.next;

          curNode1 = curNode1 != null ? curNode1.next : null;
          curNode2 = curNode2 != null ? curNode2.next : null;
      }
      // 最后一个进位
      if (carryOver > 0) {
          ListNode curResultNode = new ListNode(1);
          preResultNode.next = curResultNode;
          preResultNode = preResultNode.next;
      }
      // 第一个空节点的下一个节点就是结果链表的头节点
      ListNode resultNode = resultNodeHead.next;
      resultNodeHead.next = null; // 回收这个节点
      return resultNode;
  }
}
```

# 3. 无重复字符的最长子串

[3. 无重复字符的最长子串](https://leetcode.cn/problems/longest-substring-without-repeating-characters/)

[Longest Substring Without Repeating Characters - LeetCode](https://leetcode.com/problems/longest-substring-without-repeating-characters/description/?envType=featured-list&envId=top-100-liked-questions?envType=featured-list&envId=top-100-liked-questions)

给定一个字符串 `s` ，请你找出其中不含有重复字符的 **最长子串**的长度。

**示例 1:**

```
输入: s = "abcabcbb"
输出: 3 
解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
```

**示例 2:**

```
输入: s = "bbbbb"
输出: 1
解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
```

**示例 3:**

```
输入: s = "pwwkew"
输出: 3
解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
```

**提示：**

- `0 <= s.length <= 5 * 104`
- `s` 由英文字母、数字、符号和空格组成

## 解

从左向右分别求以i位置结尾的情况下最长的子串，i的取值是0~str.length-1，并使用一个缓存结构保存下以i位置结尾的子串最左的起始位置。

每来到一个位置，不断往左拼接以形成子串

往左拼接的终止条件：

* i位置时，往左做多能拼接到第一个等于当前位置字符的位置（不会比左边第一个等于当前值的位置更左）
* i-1位置时，从缓存中读出i-1位置的最左的起始位置（不会比i-1位置的最左起始位置更左）

以上两个结果中的靠右的位置是当前以i位置结尾的子串能抵达的最左的起始位置。

子串的右位置减去左位置就是子串的长度。取所有子串中最大长度的返回。

```java
public static int lengthOfLongestSubstringBetter(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] charArray = s.toCharArray();
        Map<Integer, Integer> indexToLeftmostIndex = new HashMap<>(); // 用于保存以index结尾的子串最左的起始位置
        indexToLeftmostIndex.put(0, 0);

        int maxLength = 1;
        for (int i = 1; i < charArray.length; i++) {
            Integer preLeftmostStartIndex = indexToLeftmostIndex.get(i - 1);
            char c = charArray[i];
            int curLeftmostStartIndex = i;
            for (int j = i - 1; j >= preLeftmostStartIndex; j--) { // 最左的位置不会比i - 1的最左起始位置更左，及时停止遍历
                if (charArray[j] == c) break;
                curLeftmostStartIndex = j; // 记录左边第一个等于当前位置的值
            }
            indexToLeftmostIndex.put(i, curLeftmostStartIndex);
            maxLength = Math.max(maxLength, i - curLeftmostStartIndex + 1);
        }
        return maxLength;
    }
```

