package com.zx.sort;

import java.util.Arrays;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-14
 * 快速排序
 * 找一个基准，将小于基准的放到基准左边，大于基准的放到基准右边
 * 判断进行左/右递归
 * 代码好难懂
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] arr = {-9,78,0,23,-567,70};
        int[] arr1 = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            arr1[i] = (int)(Math.random()*10000000);
        }
        long startTime = System.currentTimeMillis();
        quickSort(arr1,0,arr.length-1);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时"+(endTime-startTime)+"毫秒");
//        System.out.println("arr=" + Arrays.toString(arr));
    }

    public static void quickSort(int[] arr,int left,int right){
        int l = left;//左下标
        int r = right;//右下标
        //pivot中轴值
        int pivot = arr[(left+right)/2];
        int temp = 0;//交换值
        //while循环的目的是让bipivot值效地放到左边
        //比pivot值大的放到右边
        while(l < r){
            //在pivot的左边一直找，找到大于等于pivot的值，才退出
            while (arr[l] < pivot){
                l += 1;
            }
            //在pivot的右边一直找，找到小于等于pivot的值，才退出
            while (arr[r] > pivot){
                r -= 1;
            }
            //如果 l>=r 说明pivot的左右两边的值已经按照左边全部是小于等于pivot
            //右边全部大于等于pivot值
            if (l >= r){
                break;
            }

            //交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            //如果交换完后，发现这个arr[l] == pivot值相等r--,前移
            if (arr[l] == pivot){
                r -= 1;
            }
            //如果交换完后，发现这个arr[r] == pivot值相等l++,后移
            if (arr[r] == pivot){
                l += 1;
            }
        }
        //如果 l==r 必须l++,r--,否则会出现栈溢出
        if (l == r){
            l +=1;
            r -= 1;
        }
        //向左递归
        if (left < r){
            quickSort(arr,left,r);
        }
        //向右递归
        if (right > l){
            quickSort(arr,l,right);
        }
    }
}
