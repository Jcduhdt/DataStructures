package com.zx.algorithm.LeedCode;


import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-03-10
 * 力扣题 543
 * 给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过根结点。
 * 看评论抄的算法，太难了
 * 递归方面还是太弱了
 */
public class DiameterOfBinaryTree543 {
    public static void main(String[] args) {
        List<Integer> arr = new ArrayList<>();
        arr.add(1);
        Integer[] array = arr.toArray(new Integer[arr.size()]);
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(4);
        TreeNode node4 = new TreeNode(5);
        TreeNode node5 = new TreeNode(6);
        TreeNode node6 = new TreeNode(7);
        TreeNode node7 = new TreeNode(8);
        TreeNode node8 = new TreeNode(9);

        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;
        node3.left = node5;
        node5.right = node6;
        node2.left = node7;
        node7.right = node8;

        DiameterOfBinaryTree543 tree = new DiameterOfBinaryTree543();
        System.out.println(tree.diameterOfBinaryTree(root));
    }

    int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root != null) {
            //遍历每一个节点,求出此节点作为根的树的深度,那么,左子树深度加右子树深度的最大值即是答案
            setDepth(root);
            return max;
        }
        return 0;
    }

    public int setDepth(TreeNode root) {
        if (root != null) {
            //递归右节点，返回的是右子树中最长的边
            int right = setDepth(root.right);
            //递归左节点，返回左子树最长的边
            int left = setDepth(root.left);
            //判断左右子树递归后两边最长的边之和是否大于max
            if (right + left > max) {
                max = right + left;
            }
            return Math.max(right, left) + 1;
        }
        return 0;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}