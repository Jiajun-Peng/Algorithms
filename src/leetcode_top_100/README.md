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
public static int lengthOfLongestSubstringBetter1(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        // 最近一次出现某一字符的位置,因为字符的ascii码的编码值就是数字，所以可以使用组的某一下标的位置代表对应的字符
        // 记录最近一次出现某一字符的位置，就代表当前字符最左可到达的位置之一，可以避免再次循环找向左找第一个等于当前字符的位置
        int[] charLastVisitIndex = new int[128];
        Arrays.fill(charLastVisitIndex, -1);

        char[] charArray = s.toCharArray();
        int preLeftmostIndex = -1; // 上一个位置（i - 1）位置结尾的子串的起始位置
        int maxLength = 1;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            int curLeftmostIndex = Math.max(preLeftmostIndex, charLastVisitIndex[c]); //大的值排在右边，也就是从后向前找到的最左
            int curLength = i - curLeftmostIndex;
            charLastVisitIndex[c] = i; // 记录当前c字符最佳一次出现的位置
            preLeftmostIndex = curLeftmostIndex;
            maxLength = Math.max(curLength, maxLength);
        }
        return maxLength;
    }
```



# 4.寻找两个正序数组的中位数

[4. 寻找两个正序数组的中位数](https://leetcode.cn/problems/median-of-two-sorted-arrays/)

https://leetcode.com/problems/median-of-two-sorted-arrays

给定两个大小分别为 `m` 和 `n` 的正序（从小到大）数组 `nums1` 和 `nums2`。请你找出并返回这两个正序数组的 **中位数** 。

算法的时间复杂度应该为 `O(log (m+n))` 。

**示例 1：**

```
输入：nums1 = [1,3], nums2 = [2]
输出：2.00000
解释：合并数组 = [1,2,3] ，中位数 2
```

**示例 2：**

```
输入：nums1 = [1,2], nums2 = [3,4]
输出：2.50000
解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
```

**提示：**

- `nums1.length == m`
- `nums2.length == n`
- `0 <= m <= 1000`
- `0 <= n <= 1000`
- `1 <= m + n <= 2000`
- `-106 <= nums1[i], nums2[i] <= 106`

## 解

一个升序数组中的中位数就是`数组长度为奇数时的中点值`，或`数组长度为偶数时的(左中点值 + 右中点值)/ 2`

所以关键的步骤是，在不合并两个数组的情况下如何找到中点或左中点和右中点的值。

如果能设计一个`函数g`，函数g能返回两个有序数组合并且整体有序后的第k个数的值

```java
g(nums1, nums2, k);// 返回两个有序数组合并且整体有序后的第k个数的值
```

那么，两个数组的中位数就是：

```java
if (isOdd) { // 两数组长度之和为基数时
    int mid = totalLength / 2 + 1;
    return g(nums1, nums2, mid); // 返回中点位置的值
} else {
    int leftMid = totalLength / 2;
    int rightMid = totalLength / 2 + 1;

    int midSum = g(nums1, nums2, leftMid) // 左中点的值
        + g(nums1, nums2, rightMid); // 右中点的值
    return (double) midSum / 2; 
}
```

接下来的关键就是如何实现`函数g`

前导知识：

两个等长的有序数组，求第k个数是有一定的规律：

1. 先求出这两个数组整体的中点，根据这个第k个数在中点的左边还是中点的右边，继续分解问题

再实现一个函数f

```java
f(arr1, arr2); // 当arr1和arr2长度一样的情况下，返回上中位数的值

// 等同于

f(arr1, L1, R1, arr2, L2, R2);// 其中R1 - L1 = R2 - L2
```



### 在不合并两个等长数组的情况下求其整体中的第k个数

时间复杂度为：O(logN)

#### 单个数组长度为偶数情况下

* arr1 和 arr2的上中位数相等时，整体的中位数就是这两个数组中位数之一
* 两上中位数不相等，那么就可以判断出哪一部分的数是不可能的，哪一部分的数是可能的

不相等的情况：

假设arr1中的下标为a1 a2 a3 a4

arr2中的下标为A1 A2 A3 A4

如果a2的值 > A2的值，那中位数可能在a1 a2 和 A3 A4位置上

接着递归调用g函数，最后可得：

a1 a2 A3 A4中的上中位数，就是arr1 + arr2中的上中位数



#### 单个数组长度为奇数情况下

* arr1 和 arr2的中位数相等时，整体的中位数就是这两个中位数之一
* 两中位数不相等时：

不相等时：

假设arr1中的下标为a1 a2 a3 a4 a5

arr2中的下标为A1 A2 A3 A4 A5

如果a3的值 > A3的值，那中位数可能在a1 a2 和 A3 A4 A5位置上

此时，如果需要继续调用g函数则不满足条件，需要验证一下：

* 如果A3 > a2，那么A3就是整体的中位数
* 如果A3 < a2，那么可以淘汰掉A3，仅保留a1 a2 和 A4 A5进入下一次递归

### g函数的情况

f函数的时间复杂度O(log(短数组的长度))

> 因为f函数是调用一次或两次g函数，而g函数由要求等长，那么N不会超过短数组的长度

在定义好f函数的实现后

利用g函数求两个可能不等长的有序数组的中位数

g函数可以返回两个函数合并排序后第k小的数

比如arr1 有10个数，arr2有17个数，那么有以下情况：

#### 情况1：1 < k <= 10

那么只需要从两个数组中分别取k个数求上中位数



#### 情况2：10 < k <= 17

那么



#### 情况3 17 < k <= 27

那么
