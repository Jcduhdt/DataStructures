package com.zx.tree;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-17
 * 二叉树
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
        //创建二叉树
        BinaryTree binaryTree = new BinaryTree();
        //创建结点
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");

        //说明，先手动创建二叉树，后面学习使用递归创建二叉树
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);
        binaryTree.setRoot(root);
        //测试
        System.out.println("前序遍历");//1 2 3 5 4
        binaryTree.preOrder();
        System.out.println("中序遍历");//2 1 5 3 4
        binaryTree.infixOrder();
        System.out.println("后序遍历");//2 5 4 3 1
        binaryTree.postOrder();
        //测试查找
        System.out.println("前序遍历查找测试");//4次
        HeroNode resNode = binaryTree.preOrderSearch(5);
        if (resNode != null){
            System.out.printf("找到了，信息为no=%d name=%s\n",resNode.getNo(),resNode.getName());
        }else {
            System.out.printf("没有找到no=%d的英雄",5);
        }

        System.out.println("中序查找遍历测试");//3次
        resNode = binaryTree.infixOrderSearch(5);
        if (resNode != null){
            System.out.printf("找到了，信息为no=%d name=%s\n",resNode.getNo(),resNode.getName());
        }else {
            System.out.printf("没有找到no=%d的英雄",5);
        }

        System.out.println("后序查找遍历测试");//2次
        resNode = binaryTree.postOrderSearch(5);
        if (resNode != null){
            System.out.printf("找到了，信息为no=%d name=%s\n",resNode.getNo(),resNode.getName());
        }else {
            System.out.printf("没有找到no=%d的英雄",5);
        }

        //测试删除
        System.out.println("删除前，前序遍历");
        binaryTree.preOrder();
        binaryTree.delNode(2);
        System.out.println("删除后，前序遍历");
        binaryTree.preOrder();
    }
}

//定义BinaryTree 二叉树
class BinaryTree{
    private HeroNode root;

    public void setRoot(HeroNode root) {
        this.root = root;
    }
    //删除节点
    public void delNode(int no){
        if (root !=null){
            if (root.getNo() == no){
                root = null;
            }else {
                root.delNode(no);
            }
        }else {
            System.out.println("空树，不能删除");
        }
    }
    //前序遍历
    public void preOrder(){
        if (this.root != null){
            this.root.preOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //中序遍历
    public void infixOrder(){
        if (this.root != null){
            this.root.infixOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    //后序遍历
    public void postOrder(){
        if (this.root != null){
            this.root.postOrder();
        }else {
            System.out.println("二叉树为空，无法遍历");
        }
    }
    //前序遍历查找
    public HeroNode preOrderSearch(int no){
        if (root != null){
            return root.preOrderSearch(no);
        }else {
            return null;
        }
    }
    //中序遍历查找
    public HeroNode infixOrderSearch(int no){
        if (root != null){
            return root.infixOrderSearch(no);
        }else {
            return null;
        }
    }
    //后序遍历查找
    public HeroNode postOrderSearch(int no){
        if (root != null){
            return root.postOrderSearch(no);
        }else {
            return null;
        }
    }
}

//创建HeroNode节点
class HeroNode{
    private int no;
    private String name;
    private HeroNode left;//默认null
    private HeroNode right;

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    //递归删除节点
    //1.如果删除的节点是叶子节点，则删除该节点
    //2.如果删除的节点是非叶子节点，则删除该子树
    public void delNode(int no){
        /*
          思路
            1.因为该二叉树是单向的，所以判断当前节点的子节点是否是需要删除节点，而不能判断当前这个节点是不是需要删除节点
            2.如果当前节点的左子节点不为空，并且左子节点就是要删除的节点，就this.left == null;并且返回(结束递归删除)
            3.如果当前节点的右子节点不为空，并且右子节点就是要删除的节点，就this.left == null;并且返回(结束递归删除)
            4.如果第二、三步没有删除节点，那么我们就需要向左子树进行递归删除
            5.如果第四步也没有删除节点，则应当向右子树递归删除
         */
        if(this.left != null && this.left.no == no){
            this.left= null;
            return;
        }
        if(this.right != null && this.right.no == no){
            this.right= null;
            return;
        }
        if (this.left != null){
            this.left.delNode(no);
        }
        if (this.right != null){
            this.right.delNode(no);
        }
    }
    //编写前序遍历的方法
    public void preOrder(){
        System.out.println(this);//先输出父节点
        //递归向左子树前序遍历
        if (this.left != null){
            this.left.preOrder();
        }
        //递归向右子树前序遍历
        if (this.right != null){
            this.right.preOrder();
        }
    }
    //编写中序遍历的方法
    public void infixOrder(){
        //递归向左子树前序遍历
        if (this.left != null){
            this.left.infixOrder();
        }
        //输出父节点
        System.out.println(this);
        //递归向右子树前序遍历
        if (this.right != null){
            this.right.infixOrder();
        }
    }
    //编写后序遍历的方法
    public void postOrder(){
        //递归向左子树前序遍历
        if (this.left != null){
            this.left.postOrder();
        }
        //递归向右子树前序遍历
        if (this.right != null){
            this.right.postOrder();
        }
        //输出父节点
        System.out.println(this);
    }
    //前序遍历查找
    public HeroNode preOrderSearch(int no){
        //比较当前节点是不是
        if (this.no == no){
            return this;
        }
        //1.判断当前节点的左子节点是否为空，如果不为空，则递归前序查找
        //2.如果左递归前序查找，找到节点则返回
        HeroNode resNode = null;
        if (this.left != null){
            resNode = this.left.preOrderSearch(no);
        }
        if (resNode != null){
            return resNode;//若左子树找到
        }
        //1.判断当前节点的右子节点是否为空，如果不为空，则递归前序查找
        //2.如果右递归前序查找，找到节点则返回
        if (this.right != null){
            resNode = this.right.preOrderSearch(no);
        }
        return resNode;
    }

    //中序遍历查找
    public HeroNode infixOrderSearch(int no){
        //1.判断当前节点的左子节点是否为空，如果不为空，则递归中序查找
        //2.如果左递归中序查找，找到节点则返回
        HeroNode resNode = null;
        if (this.left != null){
            resNode = this.left.infixOrderSearch(no);
        }
        if (resNode != null){
            return resNode;//若左子树找到
        }
        //比较当前节点是不是
        if (this.no == no){
            return this;
        }
        //1.判断当前节点的右子节点是否为空，如果不为空，则递归中序查找
        //2.如果右递归中序查找，找到节点则返回
        if (this.right != null){
            resNode = this.right.infixOrderSearch(no);
        }
        return resNode;
    }

    //后序遍历查找
    public HeroNode postOrderSearch(int no){
        //1.判断当前节点的左子节点是否为空，如果不为空，则递归后序查找
        //2.如果左递归后序查找，找到节点则返回
        HeroNode resNode = null;
        if (this.left != null){
            resNode = this.left.postOrderSearch(no);
        }
        if (resNode != null){
            return resNode;//若左子树找到
        }
        //1.判断当前节点的右子节点是否为空，如果不为空，则递归后序查找
        //2.如果右递归后序查找，找到节点则返回
        if (this.right != null){
            resNode = this.right.postOrderSearch(no);
        }
        if (resNode != null){
            return resNode;//若左子树找到
        }
        //若左右子树均未找到比较当前节点是不是
        if (this.no == no){
            return this;
        }else {
            return resNode;
        }
    }
}
