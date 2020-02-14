package com.zx.sort;

import javax.management.QueryEval;
import java.util.Arrays;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-14
 * 选择排序
 * 每次假定第一个数为最小，找到最小的数以及它的下标
 * 将找到的最小与每次的第一个数进行交换
 * 时间复杂度O(n^2)
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random()*1000000);
        }
//        System.out.println("排序前"+ Arrays.toString(arr));
        long startTime = System.currentTimeMillis();
        selectSort(arr);
        long endTime = System.currentTimeMillis();
        System.out.println("排序耗时：" + (endTime - startTime) + "毫秒");

        /*
        选择排序思路
        拆解
        //第一次排序
        int min = arr[0];
        int index = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min){
                min = arr[i];
                index = i;
            }
        }
        arr[index] = arr[0];
        arr[0] = min;
        System.out.println("第一次排序后"+ Arrays.toString(arr));


        //第二次排序
        min = arr[1];
        index = 1;
        for (int i = 1+1; i < arr.length; i++) {
            if (arr[i] < min){
                min = arr[i];
                index = i;
            }
        }
        arr[index] = arr[1];
        arr[1] = min;
        System.out.println("第二次排序后"+ Arrays.toString(arr));

        //第三次排序
        min = arr[2];
        index = 2;
        for (int i = 1+2; i < arr.length; i++) {
            if (arr[i] < min){
                min = arr[i];
                index = i;
            }
        }
        arr[index] = arr[2];
        arr[2] = min;
        System.out.println("第三次排序后"+ Arrays.toString(arr));*/
    }

    public static void selectSort(int[] arr){
        //时间复杂度O(n^2)
        int min = 0;
        int index = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            //每次选择排序总是先默认最小值是当前需要排序的第一个数
            min = arr[i];
            index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < min){ //若要从大到小，就将<换成>即可
                    min = arr[j];
                    index = j;
                }
            }
            if (index != i) {
                arr[index] = arr[i];
                arr[i] = min;
            }
//            System.out.println((i+1) + "次排序后"+ Arrays.toString(arr));
        }
    }
}
