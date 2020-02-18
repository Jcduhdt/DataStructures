package com.zx.tree;


/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-18
 * 顺序存储二叉树
 * 二叉树的节点以数组方式存放
 * 要求在遍历数组时，仍可以以前中后序遍历
 * 特点
 *  1.顺序二叉树通常只考虑完全二叉树
 *  2.第n个元素的左子节点为2*n +1
 *  3.第n个元素的右子节点为2*n +2
 *  4.第n个元素的父节点为(n-1)/2
 *  5.n:表示二叉树中的第几个元素（从0开始编号）
 */
public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5,6,7};
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        arrBinaryTree.preOrder();//1245367
//        arrBinaryTree.infixOrder();//4251637
//        arrBinaryTree.postOrder();//4526731
    }
}

//编写一个ArrayBinaryTree实现顺序存储二叉树遍历
class ArrBinaryTree{
    private int[] arr;//存储数据节点的数组
    public ArrBinaryTree(int[] arr){
        this.arr = arr;
    }
    //重载preOrder
    public void preOrder(){
        this.preOrder(0);
    }
    //重载infixOrder
    public void infixOrder(){
        this.infixOrder(0);
    }
    //重载postOrder
    public void postOrder(){
        this.postOrder(0);
    }
    /**
     * 编写一个方法，完成顺序存储二叉树的前序遍历
     * @param index 数组下标
     */
    public void preOrder(int index){
        //数组为空或arr.length == 0
        if (arr == null || arr.length == 0){
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }
        //输出当前这个元素
        System.out.println(arr[index]);
        //向左递归遍历
        if ((index * 2 + 1) < arr.length){
            preOrder(2 * index + 1);
        }
        //向右递归遍历
        if ((index * 2 + 2) < arr.length){
            preOrder(2 * index + 2);
        }
    }

    /**
     * 编写一个方法，完成中序存储二叉树的前序遍历
     * @param index 数组下标
     */
    public void infixOrder(int index){
        //数组为空或arr.length == 0
        if (arr == null || arr.length == 0){
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }
        //向左递归遍历
        if ((index * 2 + 1) < arr.length){
            infixOrder(2 * index + 1);
        }
        //输出当前这个元素
        System.out.println(arr[index]);
        //向右递归遍历
        if ((index * 2 + 2) < arr.length){
            infixOrder(2 * index + 2);
        }
    }

    /**
     * 编写一个方法，完成后序存储二叉树的前序遍历
     * @param index 数组下标
     */
    public void postOrder(int index){
        //数组为空或arr.length == 0
        if (arr == null || arr.length == 0){
            System.out.println("数组为空，不能按照二叉树的前序遍历");
        }
        //向左递归遍历
        if ((index * 2 + 1) < arr.length){
            postOrder(2 * index + 1);
        }
        //向右递归遍历
        if ((index * 2 + 2) < arr.length){
            postOrder(2 * index + 2);
        }
        //输出当前这个元素
        System.out.println(arr[index]);
    }
}
