package com.zx.sort;

import java.util.Arrays;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-15
 * 归并排序 分治算法
 * 将数组先分开，每个模块进行排序合并，递归得到
 */
public class MergeSort {
    public static void main(String[] args) {
//        int[] arr = {8,4,5,7,1,3,6,2};
        int[] arr = new int[800000];
        for (int i = 0; i < 800000; i++) {
            arr[i] = (int)(Math.random()*1000000);
        }
        int[] temp = new int[arr.length];//归并需要的额外空间
        long startTime = System.currentTimeMillis();
        mergeSort(arr,0,arr.length-1,temp);
        long endTime = System.currentTimeMillis();
        System.out.println("耗时：" + (endTime - startTime) + "毫秒");
//        System.out.println("归并排序后=" + Arrays.toString(arr));
    }

    public static void mergeSort(int[] arr,int left,int right,int[] temp){
        if (left < right){
            int mid = (left + right)/2;//中间索引
            //向左递归进行分解
            mergeSort(arr,left,mid,temp);
            //向右递归进行分解
            mergeSort(arr,mid+1,right,temp);
            //合并
            merge(arr,left,mid,right,temp);
        }
    }

    /**
     * 合并的方法
     * @param arr 排序的原始数组
     * @param left  左边有序序列的初始索引
     * @param mid   中间索引
     * @param right 右边索引
     * @param temp  做中转的数组
     */
    public static void merge(int[] arr,int left,int mid,int right,int[] temp){
        int i = left;// 初始化i,左边有序序列的初始索引
        int j = mid + 1;//初始化j 右边有序序列的初始索引
        int t = 0;// 指向temp数组的当前索引

        //先把左右两边（有序）的数据按照规则填充到temp数组
        //直到左右两边的有序序列，有一边处理完毕
        while (i <= mid && j <= right){
            //如果左边的有序序列的当前元素，小于等于右边有序序列的当前元素
            //即将左边的当前元素，拷贝到temp数组
            //然后t++，i++
            if (arr[i] <= arr[j]){
                temp[t] = arr[i];
                t += 1;
                i += 1;
            }else{//反之，将右边有序序列的当前元素，填充到temp数组
                temp[t] = arr[j];
                t += 1;
                j += 1;
            }
        }

        //把有剩余数据的一边的数据一次全部填充到temp
        while (i <= mid){
            temp[t] = arr[i];
            t += 1;
            i += 1;
        }
        while (j <= right){
            temp[t] = arr[j];
            t += 1;
            j += 1;
        }

        //将temp数组的元素拷贝到arr
        //注意并不是每次都拷贝所有
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right){//第一次合并tempLeft=0,right=1 | tempLeft=2,right=3 | tempLeft=0,right=3
            //tempLeft=0,right=7
            arr[tempLeft] = temp[t];
            t += 1;
            tempLeft += 1;
        }
    }
}
