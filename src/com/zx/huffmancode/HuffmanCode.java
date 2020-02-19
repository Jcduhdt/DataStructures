package com.zx.huffmancode;


import java.io.*;
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
        byte[] huffmanCodesBytes = huffmanZip(contentBytes);
        System.out.println("压缩后结果 = " + Arrays.toString(huffmanCodesBytes) + "长度=" + huffmanCodesBytes.length);
        byte[] sourceBytes = decode(huffmanCodes, huffmanCodesBytes);
        System.out.println("解码结果 = " + new String(sourceBytes));
        //测试压缩文件
        String srcFile = "C:\\Users\\Zhang\\Pictures\\Saved Pictures\\src.jpg";
        String dstFile = "E:\\dst.zip";
        zipFile(srcFile,dstFile);
        System.out.println("压缩完成");
        //测试解压文件
        String zipFile = "E:\\dst.zip";
        String decFile = "E:\\decode.jpg";
        unZipFile(zipFile,decFile);
        System.out.println("解压完成");
        /*
        分步过程
        List<Node> nodes = getNodes(contentBytes);
        System.out.println(nodes);
        System.out.println("哈夫曼树");
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        System.out.println("前序遍历");
        huffmanTreeRoot.preOrder();
        //测试是否生成了对应的huffman编码
        Map<Byte,String> huffmanCodes =  getCodes(huffmanTreeRoot);
        System.out.println("生成的哈夫曼编码表" + huffmanCodes);
        //测试
        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
        System.out.println("huffmanCodeBytes = " + Arrays.toString(huffmanCodeBytes));//17
        //发送huffmanCodeBytes数组
         */
    }

    /**
     * 使用一个方法，将前面的方法封装起来便于调用
     * @param bytes 原始字符串对应的字节数组
     * @return 经过哈夫曼编码处理后的字节数组（压缩后的数组）
     */
    private static byte[] huffmanZip(byte[] bytes){
        List<Node> nodes = getNodes(bytes);
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        Map<Byte,String> huffmanCodes =  getCodes(huffmanTreeRoot);
        byte[] huffmanCodeBytes = zip(bytes, huffmanCodes);
        return huffmanCodeBytes;
    }

    /**
     * 编写方法，将一个文件进行压缩
     * 因为使用的是对象写入，所以在解压时直接读取对象就能把压缩数据和哈夫曼表读出
     * @param srcFile 传入的希望压缩的文件全路径
     * @param dstFile 压缩后将压缩的文件放到那个目录
     */
    public static void zipFile(String srcFile,String dstFile){
        //创建输出流
        OutputStream os = null;
        ObjectOutputStream oos = null;
        //创建文件输出流
        FileInputStream is = null;
        try{
            is = new FileInputStream(srcFile);
            byte[] b = new byte[is.available()];
            //读取文件
            is.read(b);
            //直接对源文件压缩
            byte[] huffmanBytes = huffmanZip(b);
            //文件输出流，存放压缩文件
            os = new FileOutputStream(dstFile);
            //创建一个和文件输出流关联的ObjectOutputStream
            oos = new ObjectOutputStream(os);
            //把哈夫曼编码后的字节数组写入压缩文件
            oos.writeObject(huffmanBytes);
            //以对象流的方式写入哈夫曼编码，是为了以后恢复源文件时使用
            //注意 一定要把哈夫曼编码表写入
            oos.writeObject(huffmanCodes);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                is.close();
                oos.close();
                os.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * 完成对压缩文件的解压
     * @param zipFile 要解压的文件路径
     * @param dstFile 解压放置的路径
     */
    public static void unZipFile(String zipFile,String dstFile){
        //定义文件输出流
        InputStream is = null;
        //定义一个对象输入流
        ObjectInputStream ois = null;
        //定义文件输出流
        OutputStream os = null;
        try{
            //创建文件输入流
            File file;
            is = new FileInputStream(zipFile);
            //创建一个和is关联的对象输入流
            ois = new ObjectInputStream(is);
            //读取byte数组 huffmanBytes
            byte[] huffmanBytes = (byte[]) ois.readObject();
            //读取哈夫曼编码表
            Map<Byte,String> huffmanCodes = (Map<Byte,String>)ois.readObject();
            //解码
            byte[] bytes = decode(huffmanCodes, huffmanBytes);
            //将bytes数组写入到目标文件
            os = new FileOutputStream(dstFile);
            //写数据到dstFile文件
            os.write(bytes);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try{
                os.close();
                ois.close();
                is.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    //1.将huffmanCodeBytes[-88, -65, -56, -65, -56, -65, -55, 77, -57, 6, -24, -14, -117, -4, -60, -90, 28]
    //  重写转成哈夫曼编码对应的二进制01串
    //2.01串转成对应的字符串 i like ...
    /**
     *
     * @param huffmanCodes 哈夫曼编码表map
     * @param huffmanBytes 哈夫曼编码得到的字节数组
     * @return 原来字符串对应的数组
     */
    private static byte[] decode(Map<Byte,String> huffmanCodes,byte[] huffmanBytes){
        //1.先得到huffmanBytes对应的01串
        StringBuilder stringBuilder = new StringBuilder();
        //将byte数组转成01串
        for (int i = 0; i < huffmanBytes.length; i++) {
            //判断是不是最后一个字节
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag,huffmanBytes[i]));
        }
        //把字符串按照指定的哈夫曼编码进行查询
        //把哈夫曼编码表进行调换，因为反向查询 a->100 100->a 键值对调换
        HashMap<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte,String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(),entry.getKey());
        }
        //创建一个集合，存放byte
        ArrayList<Byte> list = new ArrayList<Byte>();
        for (int i = 0; i < stringBuilder.length();) {
            int count = 1;//小的计数器
            boolean flag = true;
            Byte b = null;
            while (flag){
                //取出一个bit
                String key = stringBuilder.substring(i,i+count);//i不动，直到匹配到一个字符
                b = map.get(key);
                if (b == null){
                    count++;
                }else {//匹配到
                    flag = false;
                }
            }
            list.add(b);
            i += count;//i直接移动到count
        }
        //for循环结束后，list中存放了所有的字符 把list数组中的数据放入到byte[]
        byte b[] = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }
        return b;

    }
    /**
     * @param flag 标志是否需要补高位，如果是true，表示需要补高位，如果是false表示不补，如果是最后一个字节无须补高位
     * @param b 传入的byte
     * @return b对应的二进制字符串（按补码返回）
     */
    private static String byteToBitString(boolean flag,byte b){
        //使用变量保存b
        int temp = b;//将b转成int
        //如果是正数还要补高位
        if (flag){
            temp |= 256;//按位与256   1 0000 0000 | 0000 0001 =>1 0000 0001
        }
        String str = Integer.toBinaryString(temp);//返回的是temp对应的二进制补码
        if (flag){
            return str.substring(str.length() - 8);
        }else {
            return str;
        }
    }

    /**
     * 编写一个方法，将字符串对应的byte[]数组，通过生成的哈夫曼编码表，返回一个哈夫曼编码后压缩的byte[]
     * @param bytes 这时原始的字符串对应的byte[]
     * @param huffmanCodes 生成的哈夫曼编码map
     * @return 返回哈夫曼编码处理后的byte[]
     * 举例：String content = "i like like like java doyou like a java";byte[] contentBytes = content.getBytes()
     * 返回的是01串
     * 对应的byte[] huffmanCodeBytes  即用8位对应的一个byte放入到huffmanCodeBytes
     * huffmanCodeBytes[0] = 10101000(补码) => [推导 10101000=>10101000-1=>10100111(反码)=>11011000
     * huffmanCodeBytes[1] = -88
     */
    private static byte[] zip(byte[] bytes,Map<Byte,String> huffmanCodes){
        //1.利用huffmanCodes将bytes转成哈夫曼编码对应的字符串
        StringBuilder stringBuilder = new StringBuilder();
        //遍历bytes数组
        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }
//        System.out.println("测试stringBuilder=" + stringBuilder.toString());
        //将得到的01串，转成byte[]
        //统计返回 byte[] huffmanCodeBytes长度
        /*
          int len;
          if(stringBuilder.length() % 8 == 0){
              len = stringBuilder.length() / 8;
          }else{
              len = stringBuilder.length() / 8 + 1;
          }
         */
        int len = (stringBuilder.length() + 7) / 8;
        //创建 存储压缩后的byte数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0;//记录第几个byte
        for (int i = 0; i < stringBuilder.length(); i += 8) {//每8位对应一个byte，所以步长+8
            String strByte;
            if (i+8 > stringBuilder.length()){//不够8位
                strByte = stringBuilder.substring(i);
            }else {
                strByte = stringBuilder.substring(i, i + 8);//字串，包头不包尾
            }
            //将strByte转成一个byte，放入到huffmanCodeBytes
            huffmanCodeBytes[index] = (byte)Integer.parseInt(strByte,2);
            index++;
        }
        return huffmanCodeBytes;
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
