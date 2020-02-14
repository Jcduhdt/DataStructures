package com.zx.sort;

import java.util.Arrays;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-14
 * 希尔排序 插入排序的一种 缩小增量排序
 * 以一个步长为单位，将数组分成几组，对每组继续冒泡排序
 * 逐渐减小步长，直至为1，组数也越来越少，最后就是对整个数组进行冒泡
 * eg{8,9,1,7,2,3,5,4,6,0}
 * 第一轮[3, 5, 1, 6, 0, 8, 9, 4, 7, 2]
 * 第二轮:[0, 2, 1, 4, 3, 5, 7, 6, 9, 8]
 * 第三轮:[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
 *
 * 两种方法交换法和移位法
 * 感觉交换法内部使用的是冒泡
 * 移位法内部使用的是插入排序
 */
public class ShellSort {
    public static void main(String[] args) {
        int[] arr = {8,9,1,7,2,3,5,4,6,0};

        int[] arr1 = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr1[i] = (int)(Math.random()*100000);
        }
        long startTime = System.currentTimeMillis();
        shellSort2(arr1);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时"+(endTime-startTime)+"毫秒");

    }

    //希尔排序
    //对有序序列再插入时使用交换法
    public static void shellSort(int[] arr){
        int temp = 0;
        int count = 0;
        for (int gap = arr.length/2; gap > 0 ; gap/=2) {
            for (int i = gap; i < arr.length; i++) {
                //遍历各组中所有的元素（共gap组，每组有 个元素），步长gap
                for (int j = i - gap; j >= 0 ; j-=gap) {
                    if (arr[j]>arr[j+gap]){
                        temp = arr[j];
                        arr[j] = arr[j+gap];
                        arr[j+gap] = temp;
                    }
                }
            }
//            System.out.println("希尔排序第"+(++count)+"轮:" + Arrays.toString(arr));
        }

        /*//逐步推导编写希尔排序
        //第一轮
        for (int i = 5; i < arr.length; i++) {
            //遍历各组中所有的元素（共gap组，每组有 个元素），步长gap
            for (int j = i - 5; j >= 0 ; j-=5) {
                if (arr[j]>arr[j+5]){
                    temp = arr[j];
                    arr[j] = arr[j+5];
                    arr[j+5] = temp;
                }
            }
        }
        System.out.println("希尔排序第1轮:" + Arrays.toString(arr));

        //第二轮
        for (int i = 2; i < arr.length; i++) {
            //遍历各组中所有的元素（共gap组，每组有 个元素），步长gap
            for (int j = i - 2; j >= 0 ; j-=2) {
                if (arr[j]>arr[j+2]){
                    temp = arr[j];
                    arr[j] = arr[j+2];
                    arr[j+2] = temp;
                }
            }
        }
        System.out.println("希尔排序第2轮:" + Arrays.toString(arr));

        //第三轮
        for (int i = 1; i < arr.length; i++) {
            //遍历各组中所有的元素（共gap组，每组有 个元素），步长gap
            for (int j = i - 1; j >= 0 ; j-=1) {
                if (arr[j]>arr[j+1]){
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        System.out.println("希尔排序第3轮:" + Arrays.toString(arr));*/
    }

    //对交换式的希尔排序进行优化->移位法
    public static void shellSort2(int[] arr){
        //增量gap,并逐步的缩小变量
        for (int gap = arr.length/2; gap > 0; gap/=2) {
            //从第gap个元素，逐个对其所在的组进行直接插入排序
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                if (arr[j] < arr[j-gap]){
                    while (j - gap >= 0 && temp < arr[j - gap]){
                        //移动
                        arr[j] = arr[j - gap];
                        j -= gap;
                    }
                    //当退出while循环，就给temp找到插入的位置
                    arr[j] = temp;
                }
            }
//            System.out.println("希尔排序第轮:" + Arrays.toString(arr));

        }
    }
}
