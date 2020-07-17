package com.zx.tree;

import java.util.Arrays;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-18
 * 堆排序
 * 代码自己debug一下看过程
 * 1.先把数组弄成大顶堆或小顶堆
 * 2.循环，将根节点与最后一个节点交换，再将去除最后节点的大顶堆进行根节点与最大（小）的交换，反复
 */
public class HeapSort {
    public static void main(String[] args) {
//        int arr[] = {4,6,8,5,9};
//        heapSort(arr);
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random()*1000000);
        }
        long startTime = System.currentTimeMillis();
        heapSort(arr);
        long endTime = System.currentTimeMillis();
        System.out.println("堆排序耗时：" + (endTime - startTime) + "毫秒");
    }

    //编写一个堆排序的方法
    public static void heapSort(int arr[]){
        System.out.println("堆排序");
        int temp = 0;
        //分步完成
        /*
        adjustHeap(arr,1,arr.length);
        System.out.println("第一次" + Arrays.toString(arr));//{4,9,8,5,6}
        adjustHeap(arr,0,arr.length);
        System.out.println("第二次" + Arrays.toString(arr));//{9,6,8,5,4}
        */
        //最终
        //将无序序列构建成一个堆，根据升序降序需求选择大顶堆或小顶堆
        for (int i = arr.length / 2 - 1; i >= 0 ; i--) {
            adjustHeap(arr,i,arr.length);
        }
        /*
          2.将堆顶元素于末尾元素交换，将最大元素沉到数组末端
          3.重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序
         */
        for (int j = arr.length - 1; j > 0; j--) {
            temp = arr[j];
            arr[j] = arr[0];
            arr[0] = temp;
            adjustHeap(arr,0,j);
        }
//        System.out.println("数组=" + Arrays.toString(arr));
    }

    /**
     * 将一个数组（二叉树），调整成一个大顶堆
     * 功能：完成将i对应的非叶子节点的树调整成大顶堆
     * 举例：int[] arr = {4,6,8,5,9}; => i=1 => adjustHeap => 得到{4,9,8,5,6}
     * 如果再次调用adjustHeap传入的是 i = 0 => 得到{4,9,8,5,6} => {9,6,8,5,4}
     * @param arr 待调整的数组
     * @param i 表示非叶子节点在数组中的索引
     * @param length 表示对多少个元素继续调整，length是在逐渐减少的
     */
    public static void adjustHeap(int[] arr,int i,int length){
        int temp = arr[i];//取出当前元素的值保存在临时变量
        //开始调整
        //1.k = i * 2 + 1是i节点的左子节点
        for (int k = i * 2 + 1; k < length; k = k * 2 +1) {
            if (k+1 < length && arr[k] < arr[k+1]){//说明左子节点的值小于右子节点的值
                k++;//k指向右子节点
            }
            if (arr[k] > temp){//如果子节点大于父节点
                arr[i] = arr[k];//把较大的值赋给当前节点
                i = k;//i指向k，继续循环比较
            }else {
                break;
            }
        }
        //当for循环结束后，已经将以i为父节点的树的最大值，放到了最顶（局部）
        arr[i] = temp;//将temp值放到调整后的位置
    }
}

