package com.zx.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-16
 * 二分查找法，前提数组有序
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] arr ={1,8,10,89,100,100,100,666};
        int resIndex = binarySearch(arr,0,arr.length-1,88);
        System.out.println(resIndex);

        ArrayList resIndexList = binarySearch2(arr,0,arr.length-1,66);
        System.out.println(resIndexList);

    }

    /**
     * 二分查找法
     * @param arr 数组
     * @param left 左边索引
     * @param right 右边索引
     * @param findVal 要查找的值
     * @return 找到就返回下标，没有找到就返回-1
     */
    public static int binarySearch(int[] arr,int left,int right,int findVal){
        if (left > right){
            return -1;
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        if (findVal > midVal){//向右递归
            return binarySearch(arr,mid + 1,right,findVal);
        }else if (findVal < midVal){//向左递归
            return binarySearch(arr,left,mid - 1,findVal);
        }else {
            return mid;
        }
    }

    /**
     * 二分查找法查找数组中所有目标值
     * 思路
     * 1.再找到mid索引值，不要马上返回
     * 2.向mid索引值的左边扫描，将所有满足100的元素的下标，加入到集合ArrayList
     * 3.向mid索引值的右边扫描，将所有满足100的元素的下标，加入到集合ArrayList
     * 4.返回ArrayList
     * @param arr 数组
     * @param left 左边索引
     * @param right 右边索引
     * @param findVal 要查找的值
     * @return 找到就返回下标，没有找到就返回-1
     */
    public static ArrayList<Integer> binarySearch2(int[] arr,int left,int right,int findVal){
        if (left > right){
            return new ArrayList<Integer>();
        }
        int mid = (left + right) / 2;
        int midVal = arr[mid];
        if (findVal > midVal){//向右递归
            return binarySearch2(arr,mid + 1,right,findVal);
        }else if (findVal < midVal){//向左递归
            return binarySearch2(arr,left,mid - 1,findVal);
        }else {
            ArrayList<Integer> resIndexList = new ArrayList<Integer>();
            //向mid索引值的左边扫描，将所有满足100的元素的下标，加入到集合ArrayList
            int temp = mid - 1;
            while (true){
                if (temp < 0 || arr[temp] != findVal){
                    break;
                }
                //否则，就temp放入到resIndexList中
                resIndexList.add(temp);
                temp -= 1;//左移
            }
            resIndexList.add(mid);
            //向mid索引值的右边扫描，将所有满足100的元素的下标，加入到集合ArrayList
            temp = mid + 1;
            while (true){
                if (temp > arr.length -1 || arr[temp] != findVal){
                    break;
                }
                //否则，就temp放入到resIndexList中
                resIndexList.add(temp);
                temp += 1;//右移
            }
            return resIndexList;
        }
    }
}
