package com.zx.nowcoder.base04;

/**
 * 已知一棵完全二叉树， 求其节点的个数
 * 要求： 时间复杂度低于O(N)， N为这棵树的节点个数
 * 搜索最叶子节点深度，再搜索根节点右子树的最左叶子节点深度，根据是否与总深度相符合
 * 若符合继续搜索它的右子树的左叶子节点，不符合搜索根节点左节点的右子树，这样反复
 */
public class Code_08_CompleteTreeNodeNumber {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static int nodeNum(Node head) {
		if (head == null) {
			return 0;
		}
		return bs(head, 1, mostLeftLevel(head, 1));
	}

	public static int bs(Node node, int l, int h) {
		if (l == h) {
			return 1;
		}
		if (mostLeftLevel(node.right, l + 1) == h) {
			return (1 << (h - l)) + bs(node.right, l + 1, h);
		} else {
			return (1 << (h - l - 1)) + bs(node.left, l + 1, h);
		}
	}

	public static int mostLeftLevel(Node node, int level) {
		while (node != null) {
			level++;
			node = node.left;
		}
		return level - 1;
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		System.out.println(nodeNum(head));

	}


	/**
	 * 常规的递归解法
	 */
	public int countNodes(Node root) {
		if (root == null){
			return 0;
		}
		return countNodes(root.left) + countNodes(root.right) + 1;
	}

}
