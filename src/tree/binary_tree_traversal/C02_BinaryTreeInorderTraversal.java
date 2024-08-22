package tree.binary_tree_traversal;

import java.util.ArrayList;
import java.util.List;
import tree.TreeNode;

/*
94. 二叉树的中序遍历：https://leetcode-cn.com/problems/binary-tree-inorder-traversal

二叉树的中序遍历

 */
public class C02_BinaryTreeInorderTraversal {

    // 中序遍历
    public List<Integer> inorderTraversal(TreeNode root) {
        if (root == null) { // 递归终止条件
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        List<Integer> leftResult = inorderTraversal(root.left); // 递归遍历左子树
        List<Integer> rightResult = inorderTraversal(root.right); // 递归遍历右子树

        // 中序遍历，保存结果集的顺序是：左-根-右
        result.add(root.val); // 将当前节点的值加入结果集
        if (leftResult != null) result.addAll(leftResult); // 将左子树的遍历结果加入结果集
        if (rightResult != null) result.addAll(rightResult); // 将右子树的遍历结果加入结果集
        return result;
    }
}
