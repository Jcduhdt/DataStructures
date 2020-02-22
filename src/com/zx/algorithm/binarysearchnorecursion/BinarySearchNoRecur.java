package com.zx.algorithm.binarysearchnorecursion;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-22
 * 算法
 * 二分查找法的非递归实现
 * 容易理解
 */
public class BinarySearchNoRecur {
    public static void main(String[] args) {
        int[] arr = {1,3,8,10,11,17,26,78,99};
        int index = binarySearch(arr, 60);
        System.out.println("index = " + index);
    }

    /**
     * 二分查找的非递归实现
     * @param arr 待查找的数组 升序数组
     * @param target 需要查找的数
     * @return 返回对应下标，-1表示没找到
     */
    public static int binarySearch(int[] arr,int target){
        int left = 0;
        int right = arr.length - 1;
        while (left <= right){
            int mid = (left + right) / 2;
            if (arr[mid] == target){
                return mid;
            }else if (arr[mid] < target){
                left = mid + 1; //向右边查找
            }else{
                right = mid - 1; //向左边查找
            }
        }
        return -1;
    }
}
