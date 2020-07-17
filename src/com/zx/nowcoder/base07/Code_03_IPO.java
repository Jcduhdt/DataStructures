package com.zx.nowcoder.base07;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 贪心
 * 利用堆结构
 * 先定义一个项目的节点类型，存放两个属性：cost profit
 * 将节点以cost的属性生成小顶堆
 * 判断当前持有的金额，将小顶堆中cost小于当前金额的节点弹出
 * 将弹出的节点按照profit属性进行生成大顶堆
 * 弹出大顶堆的堆顶即做该项目
 * 输入：
 * 		参数1， 正数数组costs
 * 		参数2， 正数数组profits
 * 		参数3，正数k 参数4， 正数m
 * costs[i]表示i号项目的花费 profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 * k表示你不能并行、 只能串行的最多做k个项目 m表示你初始的资金
 * 说明： 你每做完一个项目， 马上获得的收益， 可以支持你去做下一个项目。
 * 输出： 你最后获得的最大钱数
 */


public class Code_03_IPO {
	public static class Node {
		public int p; //收益
		public int c; // 花费

		public Node(int p, int c) {
			this.p = p;
			this.c = c;
		}
	}

	/**
	 * 按照cost属性进行排序，升序
	 */
	public static class MinCostComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.c - o2.c;
		}

	}

	/**
	 * 按照profit属性进行排序，降序
	 */
	public static class MaxProfitComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o2.p - o1.p;
		}

	}

	public static int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
		// 将每个项目的cost和profit生成对应的节点
		Node[] nodes = new Node[Profits.length];
		for (int i = 0; i < Profits.length; i++) {
			nodes[i] = new Node(Profits[i], Capital[i]);
		}

		// 传入比较器，定义优先级队列，即大顶堆/小顶堆
		PriorityQueue<Node> minCostQ = new PriorityQueue<>(new MinCostComparator());
		PriorityQueue<Node> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
		//将所有节点存入小顶堆
		for (int i = 0; i < nodes.length; i++) {
			minCostQ.add(nodes[i]);
		}
		// 因为可以做k次项目，所以循环k次
		// 因为收益是手中的金钱是不减的，所以从小顶堆弹到大顶堆的节点，不必做什么清空、重新生成堆的操作
		for (int i = 0; i < k; i++) {
			// 若堆不为空且堆顶的cost属性小于等于当前金额，弹出堆顶压入大顶堆
			while (!minCostQ.isEmpty() && minCostQ.peek().c <= W) {
				maxProfitQ.add(minCostQ.poll());
			}
			// 如果大顶堆里没有符合的节点，可解锁项目为空，则退出循环
			if (maxProfitQ.isEmpty()) {
				return W;
			}
			W += maxProfitQ.poll().p;
		}
		return W;
	}

}
