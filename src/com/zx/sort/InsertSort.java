package com.zx.sort;

import java.util.Arrays;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-14
 * 插入排序
 * 先找插入位置，再进行插入操作
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random()*1000000);
        }
//        System.out.println("排序前"+ Arrays.toString(arr));
        long startTime = System.currentTimeMillis();
        insertSort(arr);
        long endTime = System.currentTimeMillis();
        System.out.println("排序耗时：" + (endTime - startTime) + "毫秒");


    }

    public static void insertSort(int[] arr){
        for (int i = 1; i < arr.length; i++) {
            //定义待插入的数
            int insertVal = arr[i];
            int insertIndex = i-1;//即arr[i]前面这个数的下标
            //1.insertIndex >= 0保证在给insetVal找插入位置，不越界
            //2.insertVal < arr[insertIndex]待插入的数，还没有找到插入位置
            //3.就需要将arr[insertIndex]后移
            while (insertIndex >= 0 && insertVal < arr[insertIndex]){
                arr[insertIndex + 1] = arr[insertIndex];
                insertIndex--;
            }
            //当退出while循环时，说明插入位置找到，insertIndex +1
            arr[insertIndex+1] = insertVal;
//            System.out.println(i + "次排序后：" + Arrays.toString(arr));
        }

    }
}
