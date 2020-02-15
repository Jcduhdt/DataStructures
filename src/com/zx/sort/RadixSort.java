package com.zx.sort;

import java.util.Arrays;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-15
 * 基数排序 基于桶排序
 * 想出这方案的人真的是人才
 * 依次对个十百千··位进行排序
 * 不过这方案耗费空间太大，处理次数与空间与max值所占位数有关
 * 该代码只对不含负数的数组进行处理
 * 若要对含负数数组处理，先将正负数区分开，分别进行基数排序
 * 对负数取模进行基数排序，将得到的数组反序在*-1与正数排序数组进行拼接即可
 * 稳定排序
 */
public class RadixSort {
    public static void main(String[] args) {
        int[] arr = {53,3,542,748,14,214};
        radixSort(arr);
    }

    public static void radixSort(int[] arr){
        //得到数组中最大的数的位数
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max){
                max = arr[i];
            }
        }
        //得到最大数是几位数
        int maxLength = (max + "").length();
        //定义一个二维数组，表示10个桶，每个桶就是一个一维数组
        //1.二维数组包含10个一维数组
        //2.为了防止在放入数的时候，数据溢出，则每个一维数组（桶）大小定为arr.length
        //3.明确，基数排序是使用空间换时间的经典算法
        int[][] bucket = new int[10][arr.length];
        //为记录每个桶有多少个元素，定义一个一维数组记录各个桶的每次放入的数据个数
        int[] bucketElementCounts = new int[10];

        //使用循环将代码处理
        for (int i = 0, n =1; i < maxLength; i++,n *= 10) {
            //针对每个元素的对应位进行排序处理，依次是个十百千···
            for (int j = 0; j < arr.length; j++) {
                //取出每个元素的个位的值
                int digitOfElement = arr[j] /n % 10;
                //放到对应的桶中
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                bucketElementCounts[digitOfElement]++;
            }
            //按照桶顺序依次取出数据放入原来数组
            int index = 0;
            //遍历每一个桶，并将桶中数据，放到原数组
            for (int k = 0; k < bucketElementCounts.length; k++) {
                //如果桶中，有数据，放到原数组
                if (bucketElementCounts[k] != 0){
                    //循环该桶，放入
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        //取出元素放到arr
                        arr[index] = bucket[k][l];
                        index++;
                    }
                }
                //第i+1轮处理后需要将每个bucketElementCounts[k] = 0
                bucketElementCounts[k] = 0;
            }
            System.out.println("第"+(i+1)+"轮对排序处理：" + Arrays.toString(arr));
        }
        /*
        推导
        //第一轮 针对每个元素的个位数进行排序处理
        for (int j = 0; j < arr.length; j++) {
            //取出每个元素的个位的值
            int digitOfElement = arr[j] % 10;
            //放到对应的桶中
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }
        //按照桶顺序依次取出数据放入原来数组
        int index = 0;
        //遍历每一个桶，并将桶中数据，放到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            //如果桶中，有数据，放到原数组
            if (bucketElementCounts[k] != 0){
                //循环该桶，放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    //取出元素放到arr
                    arr[index] = bucket[k][l];
                    index++;
                }
            }
            //第一轮处理后需要将每个bucketElementCounts[k] = 0
            bucketElementCounts[k] = 0;
        }
        System.out.println("第一轮对个位数排序处理：" + Arrays.toString(arr));

        //第二轮 针对每个元素的十位数进行排序处理
        for (int j = 0; j < arr.length; j++) {
            //取出每个元素的个位的值
            int digitOfElement = arr[j] /10 % 10;
            //放到对应的桶中
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }
        //按照桶顺序依次取出数据放入原来数组
        index = 0;
        //遍历每一个桶，并将桶中数据，放到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            //如果桶中，有数据，放到原数组
            if (bucketElementCounts[k] != 0){
                //循环该桶，放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    //取出元素放到arr
                    arr[index] = bucket[k][l];
                    index++;
                }
            }
            bucketElementCounts[k] = 0;
        }
        System.out.println("第二轮对十位数排序处理：" + Arrays.toString(arr));

        //第三轮 针对每个元素的百位数进行排序处理
        for (int j = 0; j < arr.length; j++) {
            //取出每个元素的个位的值
            int digitOfElement = arr[j] /100 % 10;
            //放到对应的桶中
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            bucketElementCounts[digitOfElement]++;
        }
        //按照桶顺序依次取出数据放入原来数组
        index = 0;
        //遍历每一个桶，并将桶中数据，放到原数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            //如果桶中，有数据，放到原数组
            if (bucketElementCounts[k] != 0){
                //循环该桶，放入
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    //取出元素放到arr
                    arr[index] = bucket[k][l];
                    index++;
                }
            }
            bucketElementCounts[k] = 0;
        }
        System.out.println("第三轮对百位数排序处理：" + Arrays.toString(arr));
        */
    }
}
