package com.zx.recursion;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-12
 * 八皇后问题 牛逼
 * 写一个判断当前皇后与前面的皇后是否冲突
 * 写一个放置方法，写一个for循环8次，每次放到循环的地方，如果不冲突就传入下一个皇后递归该方法
 */
public class Queen8 {
    //定义一个max表示共有多少个皇后
    int max = 8;
    //定义数组array，保存皇后放置位置，比如arr={0,4,7,5,2,6,1,3}
    int[] array = new int[max];
    //初始化摆放数
    static int count = 0;
    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        queen8.check(0);
        System.out.printf("总共右%d种解法",count);
    }

    /**
     * 放置第n个皇后
     * 特别注意：check是每一次递归进入到check中都有一套for循环
     * 可以找到所有摆法
     * @param n
     */
    private void check(int n){
        if (n == max){
            print();
            return;
        }

        //依次放入皇后，判断是否冲突
        for (int i = 0; i < max; i++) {
            //先把当前皇后n放到第一列
            array[n] = i;
            //判断当前放置是否冲突
            if (judge(n)){
                //接着放n+1个皇后，即开始递归
                check(n+1);
            }
            //如果冲突就继续执行array[n] = i;即将第n个皇后放置在本行的后一个位置
        }
    }
    /**
     * 查看当我们放置第n个皇后，就去检测该皇后是否和前面已经摆放的皇后冲突
     * @param n
     * @return
     */
    public boolean judge(int n){
        for (int i = 0; i < n; i++) {
            //1.array[i] == array[n]表示是否同一列
            //2.Math.abs(n-i) == Math.abs(array[n] - array[i])是否同一斜线
            if (array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i])){
                return false;
            }
        }
        return true;
    }

    //写一个方法，可以将皇后摆放位置输出
    public void print(){
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        count++;
        System.out.println();
    }
}
