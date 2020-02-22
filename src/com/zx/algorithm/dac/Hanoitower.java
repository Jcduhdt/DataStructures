package com.zx.algorithm.dac;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-22
 * 分治算法
 * 汉诺塔
 * 怪我太愚蠢！！！
 * 不管总共有多少个盘
 * 1.总是看成两个盘，最底下的盘是一个盘，其余的总共是第二个盘
 * 2.总是先把第二个盘移到B塔
 * 3.把第一个盘移到C塔
 * 4.把第二个盘从B塔移到C塔
 * 5.按这思路进行递归即可
 */
public class Hanoitower {
    public static void main(String[] args) {
        hanoitower(5,'A','B','C');
    }

    //汉诺塔的移动方法
    //使用分治算法
    public static void hanoitower(int num,char a,char b,char c){
        //如果只有一个盘
        if (num == 1){
            System.out.println("第1个盘从" + a + "->" + c);
        }else {
            //如果是n>=2情况，总是可以看作是两个盘1，最下面的盘2，上面所有的盘
            //1.先把最上面的所有盘A->B,移动过程中会使用到C
            hanoitower(num - 1,a,c,b);
            //2.把最下面的盘A->C
            System.out.println("第" + num + "个盘从" + a + "->" + c);
            //3.把B塔的所有盘从B->C,移动过程使用到a塔
            hanoitower(num - 1,b,a,c);
        }
    }
}
