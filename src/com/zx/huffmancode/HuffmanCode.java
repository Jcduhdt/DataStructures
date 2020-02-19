package com.zx.huffmancode;


import java.util.*;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-19
 * 哈夫曼编码
 */
public class HuffmanCode {
    public static void main(String[] args) {
        String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes();
        System.out.println(contentBytes.length);//40
        List<Node> nodes = getNodes(contentBytes);
        System.out.println(nodes);
        System.out.println("哈夫曼树");
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        System.out.println("前序遍历");
        huffmanTreeRoot.preOrder();
        //测试是否生成了对应的huffman编码
        Map<Byte,String> huffmanCodes =  getCodes(huffmanTreeRoot);
        System.out.println("生成的哈夫曼编码表" + huffmanCodes);
    }

    //生成哈夫曼树对应的编码
    //1.将哈夫曼编码表存放在Map<Byte,String>形式
    //  32->01 97->100 100->11000
    static Map<Byte,String> huffmanCodes = new HashMap<Byte, String>();
    //2.再生成哈夫曼编码表时，需要去拼接路径，定义一个StringBuilder存储某个叶子节点路径
    static  StringBuilder stringBuilder = new StringBuilder();


    //为调用方便，重载getCodes
    private static Map<Byte,String> getCodes(Node root){
        if (root == null){
            return null;
        }
        //处理root左子树
        getCodes(root.left,"0",stringBuilder);
        //处理root右子树
        getCodes(root.right,"1",stringBuilder);
        return huffmanCodes;
    }
    /**
     * 功能：将传入的node节点的所有叶子节点的哈夫曼编码得到，并放入huffmanCodes
     * @param node 传入节点
     * @param code 路径，左子节点表示0，右子节点表示1
     * @param stringBuilder 用于拼接路径
     */
    private static void getCodes(Node node,String code,StringBuilder stringBuilder){
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        //将code 加入到stringBuilder2
        stringBuilder2.append(code);
        if (node != null){
            if (node.data == null){//非叶子节点
                //递归
                //向左
                getCodes(node.left,"0",stringBuilder2);
                //向右
                getCodes(node.right,"1",stringBuilder2);
            }else {//说明是个叶子节点
                huffmanCodes.put(node.data,stringBuilder2.toString());
            }
        }
    }
    //前序遍历
    private static void preOrder(Node root){
        if (root != null){
            root.preOrder();
        }else {
            System.out.println("哈夫曼树为空");
        }
    }
    /**
     *
     * @param bytes 接收字节数组
     * @return 返回List [Node[date=97,weight=5],Node[data=32,weight=9]...]
     */
    private static List<Node> getNodes(byte[] bytes){
        //1.创建一个ArrayList
        ArrayList<Node> nodes = new ArrayList<>();
        //遍历 统计每个byte出现的次数->map[key,value]
        HashMap<Byte, Integer> counts= new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null){//Map还没有这个字符，第一次
                counts.put(b,1);
            }else {
                counts.put(b,count + 1);
            }
        }
        //把每个键值对转成一个Node对象，并加入到nodes集合
        //遍历map
        for (Map.Entry<Byte,Integer> entry:counts.entrySet()){
            nodes.add(new Node(entry.getKey(),entry.getValue()));
        }
        return nodes;
    }

    //通过List 创建对应的哈夫曼树
    private static Node createHuffmanTree(List<Node> nodes){
        while (nodes.size() > 1){
            //排序
            Collections.sort(nodes);
            //取出第一颗最小二叉树
            Node leftNode = nodes.get(0);
            //取出第二颗最小二叉树
            Node rightNode = nodes.get(1);
            Node parent = new Node(null,leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;
            //删除两颗二叉树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //添加新的nodes
            nodes.add(parent);
        }
        //nodes，最后的节点就是哈夫曼树的根节点
        return nodes.get(0);
    }
}

//创建Node 放数据和权值
class Node implements Comparable<Node>{
    Byte data;//存放数据（字符）本身，比如'a'=97 ' '=32
    int weight;//权值，表示字符出现的次数
    Node left;
    Node right;
    public Node(Byte data,int weight){
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;//从小到大排序
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    //前序遍历
    public void preOrder(){
        System.out.println(this);
        if(this.left != null){
            this.left.preOrder();
        }
        if (this.right != null){
            this.right.preOrder();
        }
    }
}
