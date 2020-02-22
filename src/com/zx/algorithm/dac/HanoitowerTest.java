package com.zx.algorithm.dac;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-22
 * 汉诺塔，按自己理解看能否写出
 */
public class HanoitowerTest {
    public static void main(String[] args) {
        hannoitowerTest(3,'A','B','C');
    }

    public static void hannoitowerTest(int num,char a,char b,char c){
        if (num == 1){
            //分解到只有一个盘的时候肯定是a->c的
            System.out.println("第1个盘从" + a + "->" + c);
        }else {
            //然后不停分解
            hannoitowerTest(num - 1,a,c,b);
            //把A塔中的第二个盘从A->B
            System.out.println("第" + num + "个盘从" + a + "->" + c);
            //在前面最大盘已经到C，其余盘已经到B的基础上将B塔中的盘移动到C
            hannoitowerTest(num - 1,b,a,c);
        }
    }
}
