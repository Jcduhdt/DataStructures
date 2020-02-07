package com.zx.linkedlist;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-07
 * 双向链表
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        //双向链表的测试
        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");

        //创建一个链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        //加入
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.addByOrder(hero2);
        doubleLinkedList.addByOrder(hero4);
        doubleLinkedList.addByOrder(hero3);

        doubleLinkedList.list();
        //修改
        HeroNode2 newHeroNode = new HeroNode2(4, "公孙胜", "入云龙");
        doubleLinkedList.update(newHeroNode);
        System.out.println("修改链表后情况");
        doubleLinkedList.list();

        //删除
        doubleLinkedList.del(3);
        System.out.println("删除后的链表情况");
        doubleLinkedList.list();
    }
}

//创建一个双向链表的类
class DoubleLinkedList{
    //先初始化一个头节点，头节点不要动
    private HeroNode2 head = new HeroNode2(0,"","");

    public HeroNode2 getHead() {
        return head;
    }

    //遍历双向链表
//显示链表[遍历]
    public void list(){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        //因为头节点不能动，因此我们需要一个辅助变量来遍历
        HeroNode2 temp = head.next;
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

    public void add(HeroNode2 heroNode){
        //因为head节点不能动，因此我们需要一个辅助遍历temp
        HeroNode2 temp = head;
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
        //形成双向链表
        temp.next = heroNode;
        heroNode.pre = temp;
    }

    public void addByOrder(HeroNode2 heroNode){
        //因为头节点不能动因此我们仍然通过一个辅助指针来帮助我们找到添加位置
        //因为时单链表，因此我们找到的temp是位于添加位置的前一个节点，否则插入不了
        HeroNode2 temp = head;
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
            heroNode.pre = temp;
            heroNode.pre .next= heroNode;
            if (heroNode.next != null){
                heroNode.next.pre = heroNode;
            }
        }
    }

    //修改一个节点内容
    public void update(HeroNode2 newHeroNode){
        //判断是否为空
        if (head.next == null){
            System.out.println("链表为空~");
            return;
        }
        //找到需要修改的节点，根据no编号
        //定义一个辅助变量
        HeroNode2 temp = head.next;
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
    //对于双向链表，直接找到要删除的节点
    public void del(int no){
        //判断当前链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        HeroNode2 temp = head.next;
        boolean flag = false;//标识是否找到待删除节点
        while (true){
            if (temp == null){//已经找到链表的最后节点的next
                break;
            }
            if (temp.next.no == no){
                flag = true;
                break;
            }
            temp = temp.next;//temp后移，遍历
        }
        if (flag){
            temp.pre.next = temp.next;
            if (temp.next != null){
                temp.next.pre = temp.pre;//可能存在temp是最后的节点，会出现空指针异常
            }
        }else {
            System.out.printf("要删除的%d节点不存在\n",no);
        }
    }
}

class HeroNode2{
    public int no;
    public String name;
    public String nickname;
    public HeroNode2 next;//指向下一个节点
    public HeroNode2 pre;//指向前一个节点
    //构造器
    public HeroNode2(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }
    //为了显示方法，我们重写toString

    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name=" + name +
                ", nickname=" + nickname +
                '}';
    }
}
