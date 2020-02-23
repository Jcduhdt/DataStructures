package com.zx.algorithm.prim;


import java.util.Arrays;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-23
 * 普里姆算法
 * 修路问题
 * 最小生成树
 */
public class PrimAlgorithm {
    public static void main(String[] args) {
        //测试图是否创建成功
        char[] data = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int verxs = data.length;
        //邻接矩阵的关系用二维数组表示
        int[][] weight = new int[][]{
                {10000, 5, 7, 10000, 10000, 10000, 2},
                {5, 10000, 10000, 9, 10000, 10000, 3},
                {7, 10000, 10000, 10000, 8, 10000, 10000},
                {10000, 9, 10000, 10000, 10000, 4, 10000},
                {10000, 10000, 8, 10000, 10000, 5, 4},
                {10000, 10000, 10000, 4, 5, 10000, 6},
                {2, 3, 10000, 10000, 4, 6, 10000},
        };
        //创建MGraph
        MGraph mGraph = new MGraph(verxs);
        //创建MinTree
        MinTree minTree = new MinTree();
        minTree.createGraph(mGraph, verxs, data, weight);
        //输出
        minTree.showGraph(mGraph);
        minTree.prim(mGraph, 4);
    }
}

//创建最小生成树->村庄的图
class MinTree {
    /**
     * 创建图的邻接矩阵
     *
     * @param graph  图对象
     * @param verxs  图对应的顶点的个数
     * @param data   图的各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(MGraph graph, int verxs, char data[], int[][] weight) {
        int i, j;
        for (i = 0; i < verxs; i++) {//顶点
            graph.data[i] = data[i];
            for (j = 0; j < verxs; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }

    //邻接矩阵的输出
    public void showGraph(MGraph graph) {
        for (int[] link : graph.weight) {
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     * 编写prim算法，得到最小生成树
     *
     * @param graph 图
     * @param v     表示从图的第几个顶点开始生成'A'->0 'B'->1
     */
    public void prim(MGraph graph, int v) {
        //visited[]表示顶点是否被访问过
        int[] visited = new int[graph.verxs];
        //visited默认为0，表示没有被访问过
        //把当前节点标记已访问
        visited[v] = 1;
        //h1和h2记录两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        int minWeight = 10000;//将minWeight初始成一个大树，后面在遍历过程中会被替换
        for (int k = 1; k < graph.verxs; k++) {//因为有graph.verxs个顶点，普利姆算法结束后，有graph.verxs-1个边
            //确定每一次生成的子图，和哪个节点的距离最近
            for (int i = 0; i < graph.verxs; i++) {//i表示遍历已访问过的节点
                for (int j = 0; j < graph.verxs; j++) { //j表示遍历没有访问过的节点
                    if (visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight) {//替换最小值
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            //找到一条边是最小的
            System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + ">权值：" + minWeight);
            //将当前节点标记为已访问
            visited[h2] = 1;
            minWeight = 10000;//重置
        }
    }
}

class MGraph {
    int verxs;//表示图的节点数
    char[] data;//存放节点数据
    int[][] weight;//存放边，就是邻接矩阵

    public MGraph(int verxs) {
        this.verxs = verxs;
        data = new char[verxs];
        weight = new int[verxs][verxs];
    }
}