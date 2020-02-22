package com.zx.algorithm.dynamic;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-22
 * 动态规划
 * 背包
 * 这个问题理解起来还是很简单的，不过记录的方式没弄清，可以改进
 */
public class KnapsackProblem {
    public static void main(String[] args) {
        int[] w = {1, 4, 3};//物品的重量
        int[] val = {1500, 3000, 2000};//物品的价值
        int m = 4;//背包的容量
        int n = val.length;//物品的个数

        //为了记录动态规划最终结果放入商品情况，定义二维数组
        int[][] path = new int[n+1][m+1];
        //创建二维数组
        //v[i][j]表示在前i个物品中能够装入容量为j的背包中的最大价值
        int[][] v = new int[n + 1][m + 1];
        //初始化第一行和第一列，因为默认为0，所以跳过
        for (int i = 0; i < v.length; i++) {
            v[i][0] = 0;
        }
        for (int j = 0; j < v[0].length; j++) {
            v[0][j] = 0;
        }
        //根据公式进行动态规划处理
        for (int i = 1; i < v.length; i++) {//不处理第一行 i是从1开始
            for (int j = 0; j < v[0].length; j++) {
                //公式
                if (w[i - 1] > j) {
                    //如果重量大于背包能承受重量，就直接拷贝上一轮规划结果
                    v[i][j] = v[i - 1][j];
                } else {
                    //如果小于等于，就将对应容量上一轮规划结果与该物品价值加上剩余容量在上一轮规划中的价值得出max
                    //v[i][j] = Math.max(v[i - 1][j], val[i - 1] + v[i - 1][j - w[i - 1]]);
                    //为了记录商品存放到背包的情况，要进行修改
                    if(v[i-1][j] < val[i - 1] + v[i - 1][j - w[i - 1]]){
                        v[i][j] = val[i - 1] + v[i - 1][j - w[i - 1]];
                        //把当前情况记录到path
                        path[i][j] = 1;
                    }else {
                        v[i][j] = v[i-1][j];
                    }
                }

            }
        }
        //输出
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[0].length; j++) {
                System.out.print(v[i][j] + " ");
            }
            System.out.println();
        }
        //输出放了哪些商品
        int i = path.length -1;//行的最大下标
        int j =path[0].length - 1;//列的最大下标
        while (i > 0 && j > 0){
            if (path[i][j] == 1){
                System.out.printf("第%d个商品放入到背包中\n",i);
                j -= w[i-1];
            }
            i--;
        }
    }
}
