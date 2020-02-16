package com.zx.search;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-16
 * 线性查找法
 */
public class SeqSearch {
    public static void main(String[] args) {
        int[] arr = {1,9,11,-1,34,89};
        int index = seqSearch(arr,-1);
        if (index == -1){
            System.out.println("没有找到");
        }else {
            System.out.println("找到，下标为：" + index);
        }
    }

    public static int seqSearch(int[] arr,int value){
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value){
                return i;
            }
        }
        return -1;
    }
}
