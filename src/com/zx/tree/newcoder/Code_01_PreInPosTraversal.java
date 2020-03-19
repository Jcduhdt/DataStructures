package com.zx.tree.newcoder;

import java.util.Stack;

/**
 * 牛客网左神代码
 * 实现二叉树的先序、 中序、 后序遍历
 * 包括递归方式和非递归方式
 */

public class Code_01_PreInPosTraversal {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	//递归 前序
	public static void preOrderRecur(Node head) {
		if (head == null) {
			return;
		}
		System.out.print(head.value + " ");
		preOrderRecur(head.left);
		preOrderRecur(head.right);
	}

	//递归 中序
	public static void inOrderRecur(Node head) {
		if (head == null) {
			return;
		}
		inOrderRecur(head.left);
		System.out.print(head.value + " ");
		inOrderRecur(head.right);
	}

	//递归 后序
	public static void posOrderRecur(Node head) {
		if (head == null) {
			return;
		}
		posOrderRecur(head.left);
		posOrderRecur(head.right);
		System.out.print(head.value + " ");
	}

	/**
	 * 前序遍历 非递归
	 * 使用栈结构进行处理，因为递归本质上也是使用的栈
	 * 主要思想是先对根节点处理，判断根是否为空，不空就创栈，将头节点压栈
	 * 循环条件：栈不空
	 * 因为是前序，所以弹栈，
	 * 令头节点为弹出节点，打印
	 * 因为栈式先进后出，所以先将右压栈，再将左压栈
	 * 如此循环
	 * @param head
	 */
	public static void preOrderUnRecur(Node head) {
		System.out.print("pre-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.add(head);
			while (!stack.isEmpty()) {
				head = stack.pop();
				System.out.print(head.value + " ");
				if (head.right != null) {
					stack.push(head.right);
				}
				if (head.left != null) {
					stack.push(head.left);
				}
			}
		}
		System.out.println();
	}

	/**
	 * 中序遍历
	 * 还是栈
	 * 循环条件：栈不空 或 head不为null
	 * 循环里是先将最左叶子节点找到，中间过程一直压栈
	 * 找到左叶子节点后，因为其left为null，所以弹栈，令head等于弹出的节点，打印
	 * 再令head==head.right  因为一个子树，中序遍历，这个过程是先打印左子节点，再打印父节点，左后打印右子节点
	 * 而在搜寻过程中父节点会在栈中，要想找到右子节点，必须先弹出父节点打印，所以实现了中序遍历
	 * @param head
	 */
	public static void inOrderUnRecur(Node head) {
		System.out.print("in-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			while (!stack.isEmpty() || head != null) {
				if (head != null) {
					stack.push(head);
					head = head.left;
				} else {
					head = stack.pop();
					System.out.print(head.value + " ");
					head = head.right;
				}
			}
		}
		System.out.println();
	}

	/**
	 * 后序
	 * 两个栈
	 * 第一个while循环，首先弹栈压入栈2，再将其左右节点压入栈1
	 * 第二个while循环依次弹栈打印
	 * 就拿一个子树来说，首先是父节点入栈2，再是右节点入栈2，后是左节点入栈2
	 * 弹出的时候就是左右中的后序
	 * @param head
	 */
	public static void posOrderUnRecur1(Node head) {
		System.out.print("pos-order: ");
		if (head != null) {
			Stack<Node> s1 = new Stack<Node>();
			Stack<Node> s2 = new Stack<Node>();
			s1.push(head);
			while (!s1.isEmpty()) {
				head = s1.pop();
				s2.push(head);
				if (head.left != null) {
					s1.push(head.left);
				}
				if (head.right != null) {
					s1.push(head.right);
				}
			}
			while (!s2.isEmpty()) {
				System.out.print(s2.pop().value + " ");
			}
		}
		System.out.println();
	}

	/**
	 * 后序
	 * 一个栈
	 * @param h
	 */
	public static void posOrderUnRecur2(Node h) {
		System.out.print("pos-order: ");
		if (h != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.push(h);
			Node c = null;
			while (!stack.isEmpty()) {
				c = stack.peek();
				if (c.left != null && h != c.left && h != c.right) {
					stack.push(c.left);
				} else if (c.right != null && h != c.right) {
					stack.push(c.right);
				} else {
					System.out.print(stack.pop().value + " ");
					h = c;
				}
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head = new Node(5);
		head.left = new Node(3);
		head.right = new Node(8);
		head.left.left = new Node(2);
		head.left.right = new Node(4);
		head.left.left.left = new Node(1);
		head.right.left = new Node(7);
		head.right.left.left = new Node(6);
		head.right.right = new Node(10);
		head.right.right.left = new Node(9);
		head.right.right.right = new Node(11);

		// recursive
		System.out.println("==============recursive==============");
		System.out.print("pre-order: ");
		preOrderRecur(head);
		System.out.println();
		System.out.print("in-order: ");
		inOrderRecur(head);
		System.out.println();
		System.out.print("pos-order: ");
		posOrderRecur(head);
		System.out.println();

		// unrecursive
		System.out.println("============unrecursive=============");
		preOrderUnRecur(head);
		inOrderUnRecur(head);
		posOrderUnRecur1(head);
		posOrderUnRecur2(head);

	}

}
