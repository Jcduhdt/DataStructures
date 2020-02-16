package com.zx.search;

import java.util.Arrays;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-16
 * 插值查找
 */
public class InsertSearch {
    public static void main(String[] args) {
        int[] arr = new int[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = i + 1;
        }
//        System.out.println(Arrays.toString(arr));
        int index = insertValueSearch(arr,0,arr.length - 1,86);
        System.out.println(index);
    }

    /**
     * 插值排序，关键是mid的获取
     * @param arr 数组
     * @param left 左索引
     * @param right 右索引
     * @param findVal 查找值
     * @return 找到返回下标 没有返回-1
     */
    public static int insertValueSearch(int[] arr,int left,int right,int findVal){
        System.out.println("查找次数");
        //注意findVal< arr[0] || findVal > arr[arr.length -1]必要
        //否则我们得到的mid可能越界
        if (left > right || findVal< arr[0] || findVal > arr[arr.length -1]){
            return -1;
        }
        //求出mid 与二分差别的关键 自适应的方法 findVal参与了搜索
        int mid = left + (right -left) * (findVal - arr[left]) / (arr[right] - arr[left]);
        int midVal = arr[mid];
        if (findVal > midVal){
            return insertValueSearch(arr,mid + 1,right,findVal);
        }else if (findVal < midVal){
            return insertValueSearch(arr,left,mid - 1,findVal);
        }else {
            return mid;
        }
    }
}
