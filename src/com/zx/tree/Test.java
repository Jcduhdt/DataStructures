package com.zx.tree;

import java.util.ArrayList;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-17
 * 测试ArrayList的扩展原理
 * 底层也是数组，每次加入先判断容量是否足够，不够就创建1.5倍的数组
 * 增长 grow()方法
 */
public class Test {
    @SuppressWarnings("unused")
    public static void main(String[] args){
        //以ArrayList为例，看看是怎样进行数组扩容的
        @SuppressWarnings("rawtypes")
        ArrayList arrayList = new ArrayList();
    }
}
