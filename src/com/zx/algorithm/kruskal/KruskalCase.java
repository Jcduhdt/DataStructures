package com.zx.algorithm.kruskal;


import java.util.Arrays;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-23
 * 克鲁斯卡尔算法
 */
public class KruskalCase {
    private int edgeNum;//边的个数
    private char[] vertexs;//顶点数组
    private int[][] matrix;//邻接矩阵
    private static final int INF = Integer.MAX_VALUE;//表示两个顶点不能联通

    public static void main(String[] args) {
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int matrix[][] = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {0, 12, INF, INF, INF, 16, 14},
                /*B*/ {12, 0, 10, INF, INF, 7, INF},
                /*C*/ {INF, 10, 0, 3, 5, 6, INF},
                /*D*/ {INF, INF, 3, 0, 4, INF, INF},
                /*E*/ {INF, INF, 5, 4, 0, 2, 8},
                /*F*/ {16, 7, 6, INF, 2, 0, 9},
                /*G*/ {14, INF, INF, INF, 8, 9, 0}};
        //创建KruskalCase对象实例
        KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);
        /*
        kruskalCase.print();
        System.out.println(kruskalCase.edgeNum);
        EData[] edges = kruskalCase.getEdges();
        System.out.println(Arrays.toString(edges));//没有排序
        kruskalCase.sortEdges(edges);
        System.out.println(Arrays.toString(edges));//排序后
        */
        kruskalCase.print();
        kruskalCase.kruskal();
    }

    public KruskalCase(char[] vertexs, int[][] matrix) {
        /*
        直接赋值方式
        this.vertexs = vertexs;
        this.matrix = matrix;
         */
        //采用复制拷贝的方式
        int vlen = vertexs.length;
        this.vertexs = new char[vlen];
        for (int i = 0; i < vlen; i++) {
            this.vertexs[i] = vertexs[i];
        }
        this.matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];
            }
        }
        //统计边
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (matrix[i][j] != INF) {
                    edgeNum++;
                }
            }
        }
    }

    public void kruskal() {
        int index = 0;//表示最后结果数组的索引
        int[] ends = new int[edgeNum];//用于保存“已有最小生成树”中的每个顶点在最小生成树中的终点
        //创建结果数组，保存最后的最小生成树
        EData[] rets = new EData[edgeNum];
        //获取图中所有边的集合，一共12边
        EData[] edges = getEdges();
        System.out.println("图的边集合：" + Arrays.toString(edges) + "共" + edges.length + "条边");
        //按照边的权值大小进行排序从小到大
        sortEdges(edges);
        //遍历edges数组，将边添加到最小生成树中，判断准备加入的边是否形成回路，没有就加入rets，否则不能加入
        for (int i = 0; i < edgeNum; i++) {
            //获取第i条边的第一个顶点（起点）
            int p1 = getPosition(edges[i].start);
            //获取第i条边的第二个顶点（终点）
            int p2 = getPosition(edges[i].end);

            //获取p1,p2这个顶点对应的已有最小生成树的终点
            int m = getEnd(ends, p1);
            int n = getEnd(ends, p2);
            if (m != n) {//没有构成回路
                ends[m] = n;
                rets[index++] = edges[i];//有一条边加入到rets数组
            }
        }
        //统计并打印"最小生成树"，输出rets
        System.out.println("最小生成树为");
        for (int i = 0; i < index; i++) {
            System.out.println(rets[i]);
        }
    }

    //打印邻接矩阵
    public void print() {
        System.out.println("邻接矩阵为：");
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                System.out.printf("%12d\t", matrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * 功能：对边进行排序处理，冒泡排序
     *
     * @param edges 边的集合
     */
    private void sortEdges(EData[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - i - 1; j++) {
                if (edges[j].weight > edges[j + 1].weight) {
                    EData temp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = temp;
                }
            }
        }
    }

    /**
     * @param ch 顶点的值 'A','B'
     * @return 返回ch顶点对应的下标， 如果找不到，返回-1
     */
    private int getPosition(char ch) {
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取图中的边，放到EData[]数组中，后面需要遍历该数组
     * 通过matrix邻接矩阵获取
     * EData[]形式[['A','B',12],['B','F',1]...]
     *
     * @return
     */
    private EData[] getEdges() {
        int index = 0;
        EData[] edges = new EData[edgeNum];
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = i + 1; j < vertexs.length; j++) {
                if (matrix[i][j] != INF) {
                    edges[index++] = new EData(vertexs[i], vertexs[j], matrix[i][j]);
                }
            }
        }
        return edges;
    }

    /**
     * 功能：获取下标为i的顶点的终点，用于判断是否形成回路
     *
     * @param ends 数组就是记录了各个顶点对应的终点是哪个，ends数组在遍历过程中，逐步形成
     * @param i    表示传入的顶点对应的下标
     * @return 返回的就是下标为i的这个顶点对应的终点的下标
     */
    private int getEnd(int[] ends, int i) {
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i;
    }
}

//创建一个类EData 它的对象实例就表示一条边
class EData {
    char start;//边的一个点
    char end;//变得另外一个点
    int weight;//边的权值

    public EData(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    //输出边的信息
    @Override
    public String toString() {
        return "EData[<" + start + ',' + end + ">=" + weight + ']';
    }
}