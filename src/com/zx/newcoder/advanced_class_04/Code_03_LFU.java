package com.zx.newcoder.advanced_class_04;

import java.util.HashMap;

/**
 * LFU是一个著名的缓存算法
 * 设计并实现最不经常使用（LFU）缓存的数据结构。它应该支持以下操作：get 和 set。
 * get(key) - 如果键存在于缓存中，则获取键的值（总是正数），否则返回 -1。
 * set(key, value) - 如果键不存在，请设置或插入值。
 * 当缓存达到其容量时，它应该在插入新项目之前，使最不经常使用的项目无效。
 * 在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，最近最少使用的键将被去除。
 * 要求： 两个方法的时间复杂度都为O(1)
 * 左神牛逼，不过今天在力扣上的每日一题LFU给的测试样例竟然然有capacity为0的操作，惊了
 * 双向链表
 */
public class Code_03_LFU {

	// 定义节点
	public static class Node {
		// 键
		public Integer key;
		// 值
		public Integer value;
		// 频次
		public Integer times;
		// 前驱节点
		public Node up;
		// 后继结点
		public Node down;

		public Node(int key, int value, int times) {
			this.key = key;
			this.value = value;
			this.times = times;
		}
	}

	public static class LFUCache {

		/**
		 * 内部定义的一个list
		 */
		public static class NodeList {
			// 头
			public Node head;
			// 尾
			public Node tail;
			// 前
			public NodeList last;
			// 后
			public NodeList next;

			// 要想建立，必须给我一个node
			public NodeList(Node node) {
				head = node;
				tail = node;
			}

			/**
			 * 把node加到头部
			 * @param newHead
			 */
			public void addNodeFromHead(Node newHead) {
				newHead.down = head;
				head.up = newHead;
				head = newHead;
			}

			// 检查是否为空
			public boolean isEmpty() {
				return head == null;
			}

			// 删除其中一个任何节点
			public void deleteNode(Node node) {
				// 若只有一个
				if (head == tail) {
					head = null;
					tail = null;
				} else {
					if (node == head) {
						head = node.down;
						head.up = null;
					} else if (node == tail) {
						tail = node.up;
						tail.down = null;
					} else {
						node.up.down = node.down;
						node.down.up = node.up;
					}
				}
				// 要删除的node断开连接
				node.up = null;
				node.down = null;
			}
		}

		// 缓存容量
		private int capacity;
		// 当前缓存数
		private int size;
		// key->node 不是词频表，因为我们用的key是Integer的
		private HashMap<Integer, Node> records;
		// key来自哪个NodeList，一个node要找到其所在的NodeList
		private HashMap<Node, NodeList> heads;
		// 整个大结构中第一个NodeList是谁，频次最低
		private NodeList headList;

		/**
		 * 初始化
		 * @param capacity
		 */
		public LFUCache(int capacity) {
			this.capacity = capacity;
			this.size = 0;
			this.records = new HashMap<>();
			this.heads = new HashMap<>();
			headList = null;
		}

		/**
		 * 存数据
		 * @param key
		 * @param value
		 */
		public void set(int key, int value) {
			// 贴代码的时候，力扣出现capacity为0的缓存。。。
			if(capacity == 0){
				return;
			}
			/**
			 * key存不存在
			 */
			if (records.containsKey(key)) {
				Node node = records.get(key);
				// 新设置的value
				node.value = value;
				// 词频增加
				node.times++;
				// 找到该node属于哪个大链表
				NodeList curNodeList = heads.get(node);
				// 因为词频变换了，所以要更改NodeList
				// 与当前词频List分离，并加到下一词频的List去
				move(node, curNodeList);
			} else {
				// 存储容量到了阈值
				if (size == capacity) {
					// 删掉大NodeList的尾节点的NodeList
					Node node = headList.tail;
					headList.deleteNode(node);
					// 删掉后，该次频可能没东西了，换头操作
					modifyHeadList(headList);
					// 删掉其影响
					records.remove(node.key);
					heads.remove(node);
					size--;
				}
				Node node = new Node(key, value, 1);
				// 如果没有大头，先建大头
				if (headList == null) {
					headList = new NodeList(node);
				} else {
					// 当前头部次数等不等于1
					if (headList.head.times.equals(node.times)) {
						headList.addNodeFromHead(node);
					} else {
						// 如果不是1，新建一个1的大头
						NodeList newList = new NodeList(node);
						newList.next = headList;
						headList.last = newList;
						headList = newList;
					}
				}
				records.put(key, node);
				heads.put(node, headList);
				size++;
			}
		}

		/**
		 * 新增记录后，因为频次变换，所以要将该节点从原List脱离并存到下一词频List
		 * @param node
		 * @param oldNodeList
		 */
		private void move(Node node, NodeList oldNodeList) {
			// 在原List删掉该节点
			oldNodeList.deleteNode(node);
			// 判断老链表是不是删掉了，找到node的前后环境
			NodeList preList = modifyHeadList(oldNodeList) ? oldNodeList.last
					: oldNodeList;
			NodeList nextList = oldNodeList.next;
			// 若为词频尾部
			if (nextList == null) {
				NodeList newList = new NodeList(node);
				// 前后重连
				if (preList != null) {
					preList.next = newList;
				}
				newList.last = preList;
				if (headList == null) {
					headList = newList;
				}
				heads.put(node, newList);
			} else {
				// 下一个属于当前词频
				if (nextList.head.times.equals(node.times)) {
					nextList.addNodeFromHead(node);
					heads.put(node, nextList);
				} else {
					// 不属于则新建
					NodeList newList = new NodeList(node);
					if (preList != null) {
						preList.next = newList;
					}
					newList.last = preList;
					newList.next = nextList;
					nextList.last = newList;
					if (headList == nextList) {
						headList = newList;
					}
					heads.put(node, newList);
				}
			}
		}

		// return whether delete this head
		private boolean modifyHeadList(NodeList nodeList) {
			if (nodeList.isEmpty()) {
				if (headList == nodeList) {
					headList = nodeList.next;
					if (headList != null) {
						headList.last = null;
					}
				} else {
					nodeList.last.next = nodeList.next;
					if (nodeList.next != null) {
						nodeList.next.last = nodeList.last;
					}
				}
				return true;
			}
			return false;
		}

		/**
		 * 取操作
		 * @param key 键
		 * @return 值
		 */
		public int get(int key) {
			if (!records.containsKey(key)) {
				return -1;
			}
			Node node = records.get(key);
			node.times++;
			NodeList curNodeList = heads.get(node);
			move(node, curNodeList);
			return node.value;
		}

	}
}