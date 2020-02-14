package com.zx.sort;

import java.util.Arrays;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-14
 * 冒泡排序
 * 每次将最大的数放在最后
 * 要做arr.length()-1次冒泡排序
 * 时间复杂度O(n^2)
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random()*1000000);
        }
//        System.out.println("排序前:" + Arrays.toString(arr));
        long startTime = System.currentTimeMillis();
        bubbleSort(arr);
        long endTime = System.currentTimeMillis();
        System.out.println("冒泡排序耗时：" + (endTime - startTime) + "毫秒");

        /*
        冒泡思路，演变过程，总结规律吧
        int temp = 0;
        for (int j = 0; j < arr.length - 1; j++) {
            if (arr[j] > arr[j+1]){
                temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
            }
        }
        System.out.println("排序1后:" + Arrays.toString(arr));

        for (int j = 0; j < arr.length - 2; j++) {
            if (arr[j] > arr[j+1]){
                temp = arr[j];
                arr[j] = arr[j+1];
                arr[j+1] = temp;
            }
        }
        System.out.println("排序2后:" + Arrays.toString(arr));
        */
    }

    public static void bubbleSort(int[] arr){
        //时间复杂度O(n^2)
        boolean flag = false;//标识变量，表示是否进行过交换
        int temp = 0;//临时变量，用于交换
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {
                if (arr[j] > arr[j+1]){
                    flag = true;//如果在一趟排序中发生了交换，置flag=true 为了后面判断是否提前结束循环
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
            if (!flag){//在一趟排序中一次交换都没发生
                break;
            }else{
                flag = false;//重置flag进行下次判断
            }
//            System.out.println("排序"+i+"次后" + Arrays.toString(arr));
        }
    }
}
