package com.zx.algorithm.LeedCode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-03-21
 * LeedCode 365
 * 有两个容量分别为 x升 和 y升 的水壶以及无限多的水。请判断能否通过使用这两个水壶，从而可以得到恰好z升的水？
 * 如果可以，最后请用以上水壶中的一或两个来盛放取得的z升水。
 * 你允许：
 * 装满任意一个水壶
 * 清空任意一个水壶
 * 从一个水壶向另外一个水壶倒水，直到装满或者倒空
 */
public class CanMeasureWater {
    public static void main(String[] args) {
        int x = 60;
        int y = 42;
        int z = 34;
    }

    /**
     * 数学解法
     * 可以认为每次操作只会给水的总量带来 x 或者 y 的变化量。
     * 因此我们的目标可以改写成：找到一对整数 a, b使得 ax+by=z
     * 而只要满足 z≤x+y，且这样的 a,b 存在，那么我们的目标就是可以达成的。
     * 贝祖定理告诉我们，ax+by=z 有解当且仅当 z 是 x,y 的最大公约数的倍数。
     * 因此我们只需要找到 x,y 的最大公约数并判断 z 是否是它的倍数即可
     * @param x
     * @param y
     * @param z
     * @return
     */
    public boolean canMeasureWater1(int x, int y, int z) {
        if(x+y<z){
            return false;
        }
        if(x == 0 || y == 0){
            return z== 0 || x + y == z;
        }
        return z % gcd(x,y) == 0;

    }

    /**
     * 得到 两个数的最小公约数
     * @param x
     * @param y
     * @return
     */
    private int gcd(int x, int y){
        return y == 0 ? x : gcd(y, x%y);
    }


    /**
     * 广度搜索 BFS
     * 状态图
     * 直接计算水的总量，因为水壶不可能两个都是半满的状态
     * @param x
     * @param y
     * @param z
     * @return
     */
    public boolean canMeasureWater_BFS(int x, int y, int z) {
        if (z < 0 || z > x + y) {
            return false;
        }
        Set<Integer> set = new HashSet<>();
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        while (!q.isEmpty()) {
            int n = q.poll();
            if (n + x <= x + y && set.add(n + x)) {
                q.offer(n + x);
            }
            if (n + y <= x + y && set.add(n + y)) {
                q.offer(n + y);
            }
            if (n - x >= 0 && set.add(n - x)) {
                q.offer(n - x);
            }
            if (n - y >= 0 && set.add(n - y)) {
                q.offer(n - y);
            }
            if (set.contains(z)) {
                return true;
            }
        }
        return false;
    }
}
