package com.zx.recursion;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-12
 * 递归解觉迷宫问题，回溯
 * 就每到一个路口进行上下左右策略的判断赋值，看能否走通
 */
public class MiGong {
    public static void main(String[] args) {
        //创建二维数组，模拟迷宫
        //地图
        int[][] map = new int[8][7];
        //使用1表示墙
        //上下全部置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        //左右置1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        //设置挡板
        map[3][1] = 1;
        map[3][2] = 1;
        map[5][3] = 1;
        map[5][4] = 1;
        map[5][5] = 1;
        //输出地图
        System.out.println("地图情况");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        //使用递归回溯给小球找路
        setWay(map,1,1);
        //输出新地图，小球走过并标识
        System.out.println("小球走过情况");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 使用递归回溯来给小球找路
     * 1.map表示地图
     * 2.i、j表示从地图的哪个位置出发(1,1)
     * 3.如果小球能到达map[6][5]位置，则说明通路找到
     * 4.约定：当map[i][j]为0表示该点没走过 当为1表示墙 2表示通路可以走 3表示该点以走过，但是走不通
     * 5.在走迷宫时，需要确定一个策略（方法）下->右->上->左，如果该点走不通，再回溯
     * @param map 表示地图
     * @param i 从哪个位置开始找
     * @param j
     * @return 如果找到通路，就返回true，否则返回false
     */
    public static boolean setWay(int[][] map, int i, int j){
        if (map[6][5] == 2){//通路已经找到
            return true;
        }else {
            if (map[i][j] == 0){//如果当前这个点还没有走过
                //按照策略下->右->上->左
                map[i][j] = 2;//假定可以走通
                if (setWay(map, i+1, j)){//向下
                    return true;
                }else if (setWay(map, i, j+1)){//向右
                    return true;
                }else if (setWay(map,i-1,j)){//向上
                    return true;
                }else if (setWay(map, i, j-1)){//向左
                    return true;
                }else {
                    //说明该点走不通，是死路
                    map[i][j] = 3;
                    return false;
                }
            }else {//如果map[i][j] != 0 可能是1 2 3
                return false;
            }
        }
    }
}
