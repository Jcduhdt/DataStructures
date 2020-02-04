package com.zx.linkedlist;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-04
 * 链表顺序添加水浒英雄
 * HeroNode那里属性是public应该是为了方便修改，应用private
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        //测试
        //先创建节点
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");

        //创建一个链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        //加入
//        singleLinkedList.add(hero1);
//        singleLinkedList.add(hero2);
//        singleLinkedList.add(hero3);
//        singleLinkedList.add(hero4);

        //加入按照编号
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero1);

        //显示
        singleLinkedList.list();

        reverseList(singleLinkedList.getHead());
        System.out.println("反转后");
        singleLinkedList.list();

        //测试修改节点代码
        HeroNode newHeroNode = new HeroNode(2, "小卢", "王麒麟");
        singleLinkedList.update(newHeroNode);

        //测试删除节点
        singleLinkedList.del(2);
        System.out.println("修改链表后的情况");
        singleLinkedList.list();

        //测试单链表中有效节点个数
        System.out.println("有效节点个数="+getLength(singleLinkedList.getHead()));
        //测试得到倒数第k个节点
        HeroNode res = findlastIndexNode(singleLinkedList.getHead(), 2);
        System.out.println("res=" + res);
    }

    //将单链表反转
    public static void reverseList(HeroNode head){
        //如果当前链表为空，或者只有一个节点，无需反转，直接返回
        if (head.next == null || head.next.next == null){
            return;
        }

        //定义辅助变量，帮助遍历原来的链表
        HeroNode cur = head.next;
        HeroNode next = null;//指向当前节点[cur]的下一个节点
        HeroNode reverseHead = new HeroNode(0, "", "");
        //遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表reversehead的最前端
        while (cur != null){
            next = cur.next;//先暂时保存当前节点的下一个节点，因为后面需要使用
            cur.next = reverseHead.next;//将cur的下一个节点指向新的链表的最前端
            reverseHead.next = cur;//头插法
            cur = next;//cur后移
        }
        //将head.next指向reverseHead.next 实现单链表反转
        head.next = reverseHead.next;
    }

    //查找单链表中的倒数第k个节点【新浪面试题】
    //1.编写一个方法，接收head节点，同时接收一个index
    //2.index表示倒数第index节点
    //3.先把链表从头到尾遍历，得到链表的总长度getLength
    //4.得到size后，从链表的第一个开始遍历（size-index）个，就得到
    //5.如果找到了，则返回该节点，否则返回null
    public static HeroNode findlastIndexNode(HeroNode head,int index){
        //判断如果链表为空，返回null
        if (head.next == null){
            return null;
        }
        //第一个遍历得到链表的长度（节点个数）
        int size = getLength(head);
        //第二次遍历size-index位置，就是倒数的第k个节点
        if (index<0 || index>size){
            return null;
        }
        //定义一个辅助变量，for循环定位到倒数的index
        HeroNode cur = head.next;
        for (int i = 0; i < size-index; i++) {
            cur = cur.next;
        }
        return cur;
    }


    //方法：获取到单链表的节点的个数（如果是带头节点的链表，需求不统计头节点）
    /**
     *
     * @param head 链表的头节点
     * @return 返回的就是有效节点的个数
     */
    public static int getLength(HeroNode head){
        if (head.next == null){
            return 0;
        }
        int length = 0;
        //定义辅助变量，没有统计头节点
        HeroNode cur = head.next;
        while (cur != null){
            length++;
            cur = cur.next;
        }
        return length;
    }
}

//定义SingleLinkedList 管理我们的英雄
class SingleLinkedList{
    //先初始化一个头节点，头节点不要动
    private HeroNode head = new HeroNode(0,"","");

    public HeroNode getHead() {
        return head;
    }

    //添加节点到大你选哪个链表
    //思路，当不考虑标号顺序时
    //1.找到当前链表的最后节点
    //2.将最后这个节点的next指向新的节点
    public void add(HeroNode heroNode){
        //因为head节点不能动，因此我们需要一个辅助遍历temp
        HeroNode temp = head;
        //遍历链表，找到最后
        while(true){
            //找到链表的最后
            if (temp.next == null){
                break;
            }
            //如果没有找到最后，将temp后移
            temp = temp.next;
        }
        //当退出while循环时，temp就指向了列表的最后
        //将最后这个节点的next指向新的节点
        temp.next = heroNode;
    }

    //第二种方式添加英雄 根据排名将英雄插入到指定的位置
    //如果有这个排名，则添加失败 并给出提示
    public void addByOrder(HeroNode heroNode){
        //因为头节点不能动因此我们仍然通过一个辅助指针来帮助我们找到添加位置
        //因为时单链表，因此我们找到的temp是位于添加位置的前一个节点，否则插入不了
        HeroNode temp = head;
        boolean flag = false;//标识添加编号是否存在，默认为false
        while (true){
            if (temp.next == null){//说明temp已在链表的最后
                break;
            }
            if (temp.next.no > heroNode.no){//位置找到，就在temp的后面插入
                break;
            }else if (temp.next.no == heroNode.no){//说明希望添加的heroNode编号已经存在
                flag = true;
                break;
            }
            temp = temp.next;//后移，遍历当前列表
        }
        //判断flag的值
        if (flag){
            System.out.printf("准备插入的英雄编号%d已经存在了，不能加入\n",heroNode.no);
        }else{
            //插入到链表中，temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    //修改节点的信息，根据no编号来修改，即no编号不能改
    //说明
    //1.根据newHeroNode 的 no来修改即可
    public void update(HeroNode newHeroNode){
        //判断是否为空
        if (head.next == null){
            System.out.println("链表为空~");
            return;
        }
        //找到需要修改的节点，根据no编号
        //定义一个辅助变量
        HeroNode temp = head.next;
        boolean flag = false;//表示是否找到该节点
        while (true){
            if (temp == null){
                break;//已经遍历完毕
            }
            if (temp.no == newHeroNode.no){
                //找到
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //根据flag判断是否找到要修改的节点
        if (flag){
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        }else {
            System.out.printf("没有找到编号%d的节点，不能修改\n",newHeroNode.no);
        }

    }

    //删除节点
    //1.head不能动，因此需要一个temp辅助节点找到删除节点的前一个节点
    //2.说明我们在比较时，是temp.next.no 和 需要删除的节点的no比较
    public void del(int no){
        HeroNode temp = head;
        boolean flag = false;//标识是否找到待删除节点
        while (true){
            if (temp.next == null){
                break;
            }
            if (temp.next.no == no){
                flag = true;
                break;
            }
            temp = temp.next;//temp后移，遍历
        }
        if (flag){
            temp.next = temp.next.next;
        }else {
            System.out.printf("要删除的%d节点不存在\n",no);
        }
    }
    //显示链表[遍历]
    public void list(){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        //因为头节点不能动，因此我们需要一个辅助变量来遍历
        HeroNode temp = head.next;
        while (true){
            //判断是否到达链表最后
            if (temp == null){
                break;
            }
            //输出节点信息
            System.out.println(temp);
            //将temp后移
            temp = temp.next;
        }
    }
}

//定义一个HeroNode，每个HeroNode对象就是一个节点
class HeroNode{
    public int no;
    public String name;
    public String nickname;
    public HeroNode next;//指向下一个节点
    //构造器
    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    //为了显示方法，我们重写toString
    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name=" + name +
                ", nickname=" + nickname +
                '}';
    }
}