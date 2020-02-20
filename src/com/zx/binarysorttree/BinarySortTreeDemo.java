package com.zx.binarysorttree;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-20
 * 二叉排序树
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7,3,10,12,5,1,9,2};
        BinarySortTree binarySortTree = new BinarySortTree();
        //循环添加节点到二叉排序树
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }
        //中序遍历二叉排序树
        System.out.println("中序遍历二叉排序树");
        binarySortTree.infixOrder();//1 2 3 5 7 9 10 12
        //测试删除叶子节点
        binarySortTree.delNode(2);
        binarySortTree.delNode(5);
        binarySortTree.delNode(9);
        binarySortTree.delNode(12);
        binarySortTree.delNode(7);
        binarySortTree.delNode(3);
        binarySortTree.delNode(10);
        binarySortTree.delNode(1);
        System.out.println("删除节点后");
        binarySortTree.infixOrder();
    }
}

//创建二叉排序树
class BinarySortTree{
    private Node root;
    //查找要删除的节点
    public Node search(int value){
        if(root == null){
            return null;
        }else {
            return root.search(value);
        }
    }
    //查找父节点
    public Node searchParent(int value){
        if(root == null){
            return null;
        }else {
            return root.searchParent(value);
        }
    }

    /**
     * 1.返回以node为根节点的二叉排序树的最小节点的值
     * 2.删除node为根节点的二叉排序树的最小节点
     * @param node 传入的节点，当作根节点
     * @return 返回以node为根节点的二叉排序树的最小节点的值
     */
    public int delRightTreeMin(Node node){
        Node target = node;
        //循环查找左子节点，就会找到最小值
        while (target.left != null){
            target = target.left;
        }
        //此时target指向最小节点
        //删除最小节点
        delNode(target.value);
        return target.value;
    }
    //删除节点
    public void delNode(int value){
        if(root == null){
            return;
        }else {
            //1.需求先去找到要删除的节点 targetNode
            Node targetNode = search(value);
            //如果没有找到要删除的节点
            if (targetNode == null){
                return;
            }
            //如果该二叉排序树只有根节点
            if (root.left == null && root.right == null){
                root = null;
                return;
            }
            //去找到targetNode的父节点
            Node parent = searchParent(value);
            //如果要删除的节点是叶子节点
            if (targetNode.left == null && targetNode.right == null){
                //判断targetNode是父节点的左子节点还是右子节点
                if (parent.left == targetNode){
                    parent.left = null;
                }else if (parent.right == targetNode){
                    parent.right = null;
                }
            }else if (targetNode.left != null && targetNode.right != null){//删除有两颗子节点的树
                int minVal = delRightTreeMin(targetNode.right);
                targetNode.value = minVal;

            }else {//删除只有一颗子节点的树
                //如果要删除的节点有左子节点
                if (targetNode.left != null){
                    if (parent !=null) {
                        //如果targetNode是parent的左子节点
                        if (parent.left == targetNode) {
                            parent.left = targetNode.left;
                        } else {
                            parent.right = targetNode.left;
                        }
                    }else {
                        root = targetNode.left;
                    }
                }else {//如果要删除的节点有右子节点
                    if (parent != null) {
                        //如果targetNode是parent的左子节点
                        if (parent.left == targetNode) {
                            parent.left = targetNode.right;
                        } else {
                            parent.right = targetNode.right;
                        }
                    }else {
                        root = targetNode.right;
                    }
                }
            }
        }

    }
    //添加结点的方法
    public void add(Node node){
        if (root == null){
            root = node;//如果root为空直接让root指向node
        }else {
            root.add(node);
        }
    }
    //中序遍历
    public void infixOrder(){
        if (root != null){
            root.infixOrder();
        }else {
            System.out.println("二叉排序树为空，不能遍历");
        }
    }
}

//创建节点
class Node{
    int value;
    Node left;
    Node right;
    public Node(int value){
        this.value = value;
    }

    /**
     * 查找要删除的节点
     * @param value 希望删除的结点的值
     * @return 如果找到返回该节点，否则返回null
     */
    public Node search(int value){
        if(value == this.value){
            return this;
        }else if (value < this.value){
            //如果左子节点为空
            if (this.left == null){
                return null;
            }
            return this.left.search(value);
        }else {
            if (this.right == null){
                return null;
            }
            return this.right.search(value);
        }
    }

    /**
     * 查找要删除的节点的父节点
     * @param value 要删除的节点的值
     * @return 返回要删除的结点的父节点，没有就返回null
     */
    public Node searchParent(int value){
        //如果当前节点是要删除的节点的父节点
        if ((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)){
            return this;
        }else {
            //如果查找的值小于当前节点的值，并且当前结点的左子节点不为空
            if (value < this.value && this.left != null){
                return this.left.searchParent(value);//向左树递归查找
            }else if(value >= this.value && this.right != null){
                return this.right.searchParent(value);//向右树递归查找
            }else {
                return null;//没有找到父节点
            }
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    //添加节点的方法
    //递归形式添加节点，注意要满足二叉排序树
    public void add(Node node){
        if (node == null){
            return;
        }
        //判断传入的节点的值，和当前子树的根节点的值的关系
        if (node.value < this.value){
            //如果当前节点左子节点为null
            if (this.left == null){
                this.left = node;
            }else {//向左树递归添加
                this.left.add(node);
            }
        }else {
            if (this.right == null){
                this.right = node;
            }else {//向右树递归添加
                this.right.add(node);
            }
        }
    }

    //中序遍历
    public void infixOrder(){
        if (this.left != null){
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null){
            this.right.infixOrder();
        }
    }
}
