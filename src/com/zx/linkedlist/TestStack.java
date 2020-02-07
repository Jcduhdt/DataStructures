package com.zx.linkedlist;

import java.util.Stack;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-07
 * 演示栈stack的基本使用
 */
public class TestStack {

    public static void main(String[] args) {
        Stack<String> stack = new Stack();
        //入栈
        stack.add("jack");
        stack.add("tom");
        stack.add("smith");

        //出栈
        while (stack.size() > 0){
            System.out.println(stack.pop());//pop操作 就是将栈顶的数据取出
        }
    }
}
