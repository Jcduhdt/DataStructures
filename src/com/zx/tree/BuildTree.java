package com.zx.tree;

import java.util.HashMap;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-04-18
 * 根据前中后序其二遍历结果，构建相应的二叉树
 */
public class BuildTree {

    class TreeNode {
        private int val;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 使用前序+中序结果构建
     * 1. 前序遍历第⼀一位数字⼀一定是这个二叉树的根结点。
     * 2. 中序遍历中，根结点将序列分为了左右两个区间。
     * 左边的区间是左⼦子树的结点集合，右边的区间是右⼦子树的结点集合
     * @param preOrder 前序遍历结果
     * @param inOrder 中序遍历结果
     * @return
     */
    public TreeNode buildTreeByPreIn(int[] preOrder, int[] inOrder) {
        // 用 HashMap 存储中序遍历，目的是查找方便。
        // 因为我们从前序遍历找到根节点后，还要寻找根节点在中序遍历的哪个位置
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inOrder.length; i++) {
            map.put(inOrder[i], i);
        }
        return build(preOrder, map, 0, preOrder.length - 1, 0);
    }

    /**
     *
     * @param preOrder 先序序列
     * @param map 中序序列
     * @param preStart 先序序列的开始
     * @param preEnd 先序序列的结束
     * @param inStart 中序序列的开始
     * @return
     */
    public TreeNode build(int[] preOrder, HashMap<Integer, Integer> map,
                          int preStart, int preEnd, int inStart) {
        // 递归边界
        if (preEnd < preStart) {
            return null;
        }
        // 先序序列的第一位是根节点
        TreeNode root = new TreeNode(preOrder[preStart]);
        //找到中序序列中，根节点的索引index
        int rootIndex = map.get(root.val);
        // len 代表左子树的结点个数
        int len = rootIndex - inStart;
        // 左右子树的递归调⽤用
        root.left = build(preOrder, map, preStart + 1, preStart + len,
                inStart);
        root.right = build(preOrder, map, preStart + len + 1, preEnd,
                rootIndex + 1);
        return root;
    }

    /**
     * 通过中序和后续遍历结果构建二叉树
     * @param inOrder
     * @param postOrder
     * @return
     */
    public TreeNode buildTreeByInPost(int[] inOrder, int[] postOrder) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < inOrder.length; i++) {
            map.put(inOrder[i],i);
        }
        return build2(postOrder, map, 0, postOrder.length - 1, 0);
    }

    /**
     *
     * @param postOrder 后序序列
     * @param map 中序序列键值对
     * @param postStart 后序序列开始
     * @param postEnd 后序序列结束
     * @param inStart 中序序列开始
     * @return
     */
    public TreeNode build2(int[] postOrder, HashMap<Integer,Integer> map,
                           int postStart, int postEnd, int inStart){
        if(postEnd < postStart) {
            return null;
        }
        TreeNode root = new TreeNode(postOrder[postEnd]);
        int rootIndex = map.get(root.val);
        int len = rootIndex - inStart;
        // 前面与先序遍历是一样的，仅仅是划分左右子树的地⽅方不同。
        root.left = build2(postOrder, map, postStart, postStart + len - 1, inStart);
        root.right = build2(postOrder, map, postStart + len, postEnd - 1,rootIndex + 1);
        return root;
    }


    public static void main(String[] args) {
        int[] preOrder = {3,9,20,15,7};
        int[] inOrder = {9,3,15,20,7};
        int[] postOrder = {9,15,7,20,3};
        BuildTree bt = new BuildTree();
        TreeNode root = bt.buildTreeByPreIn(preOrder, inOrder);
        bt.postOrderPrint(root);
        System.out.println("----------------");
        TreeNode root2 = bt.buildTreeByInPost(inOrder, postOrder);
        bt.preOrderPrint(root2);
    }

    /**
     * 前序遍历
     * @param root
     */
    public void preOrderPrint(TreeNode root){
        if(root == null){
            return;
        }
        System.out.println(root.val);
        preOrderPrint(root.left);
        preOrderPrint(root.right);
    }

    /**
     * 中序遍历
     * @param root
     */
    public void inFixPrint(TreeNode root){
        if(root == null){
            return;
        }
        inFixPrint(root.left);
        System.out.println(root.val);
        inFixPrint(root.right);
    }

    /**
     * 后序遍历
     * @param root
     */
    public void postOrderPrint(TreeNode root){
        if(root == null){
            return;
        }
        postOrderPrint(root.left);
        postOrderPrint(root.right);
        System.out.println(root.val);
    }
}
