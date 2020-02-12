package com.zx.recursion;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-12
 */
public class RecursionTest {
    public static void main(String[] args) {
        //通过打印问题，回顾递归调用机制
        test(4);
        int res = factorial(5);
        System.out.println("res = " + res);
    }

    //打印问题
    public static void test(int n){
        if (n > 2){
            test( n-1);
        }//加了个else就会只打印2
        System.out.println("n = " + n);
    }
    //阶乘问题
    public static int factorial(int n){
        if(n == 1){
            return 1;
        }else{
            return factorial(n-1) * n;
        }
    }
}
