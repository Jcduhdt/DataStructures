package com.zx.algorithm.dynamic;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-03-08
 * 力扣 题322
 * 给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。
 * 如果没有任何一种硬币组合能组成总金额，返回 -1。 每种硬币数量无限
 * 示例：
 * 输入: coins = [1, 2, 5], amount = 11
 * 输出: 3
 * 解释: 11 = 5 + 5 + 1
 *
 * 该题有多种解法DFS（深度优先） 及 dp（动态规划）
 * 这里看dp的代码
 *
 * 当前状态的问题可以转化成之前状态问题的，一般就是动态规划的套路
 */
public class CoinChange {

    public static void main(String[] args) {
        CoinChange ch = new CoinChange();
        int[] coins = new int[]{186,419,83,408};
        int amount = 6249;
        System.out.println(ch.coinChange(coins, amount));
    }

    /**
     * 摘自力扣大佬的题解 简单易懂
     *
     * 分析：
     * 1.动态规划的核心思想是：把Question划归成Subquestion，再把Subquestion划归成Subsubquestion。直到可以求解
     *
     * 对于这道题，以coins = [1, 2, 5], amount = 11为例
     * 我们要求组成11的最少硬币数，可以考虑组合中的最后一个硬币分别是1，2，5的情况，比如
     *
     * 最后一个硬币是1的话，最少硬币数应该为【组成10的最少硬币数】+ 1枚（1块硬币）
     * 最后一个硬币是2的话，最少硬币数应该为【组成9的最少硬币数】+ 1枚（2块硬币）
     * 最后一个硬币是5的话，最少硬币数应该为【组成6的最少硬币数】+ 1枚（5块硬币）
     * 在这3种情况中硬币数最少的那个就是结果
     * 按同样的道理，我们也可以分别再求出组成10的最少硬币数，组成9的最少硬币数，组成6的最少硬币数。。。
     * 发现了吗，这种当前状态的问题可以转化成之前状态问题的，一般就是动态规划的套路
     * 所以我们自底向上依次求组成1，2...一直到11的最少硬币数
     * 对每一个数，依次比较最后一个硬币是不同面额的情况，从中选出最小值
     *
     * 预设一个0位方便后续计算，组成0的最少硬币数是0，所以dp[0] = 0
     * 给每一个数预设一个最小值amount+1，因为硬币面额最小为整数1，所以只要有解，最小硬币数必然小于amount+1
     * dp的最后一项就是答案
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        //金额从1~amount，依次得到当前金额对应的最少硬币数
        for (int i = 1; i < amount + 1; i++) {
            dp[i] = amount + 1;
            //因为有多枚硬币，所以要尝试当前硬币对应的子问题的解，选取最优
            for (int coin : coins) {
                if (i >= coin) {
                    //dp[i - coin] + 1是核心，这是表示当前金额减去当前硬币的额度对应的最少硬币数+该硬币一枚
                    //进行比较大小的dp[i]不能改成amount + 1，因为dp[i]会随着每个硬币的循环而改动
                    dp[i] = Math.min(dp[i - coin] + 1, dp[i]);
                }
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
}
