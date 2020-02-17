package com.zx.hashTab;

import java.util.Scanner;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-17
 * 数据结构 哈希表
 */
public class HashTabDemo {
    public static void main(String[] args) {
        //创建哈希表
        HashTab hashTab = new HashTab(7);
        //菜单
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("add:添加雇员");
            System.out.println("find:查找雇员");
            System.out.println("list:显示雇员");
            System.out.println("exit:退出系统");

            key = scanner.next();
            switch (key){
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt();
                    System.out.println("输入姓名");
                    String name = scanner.next();
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "find":
                    System.out.println("请输入要查找的id");
                    id = scanner.nextInt();
                    hashTab.findEmpById(id);
                case "list":
                    hashTab.list();
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    break;
            }
        }
    }

}

//创建HashTab 管理多条链表
class HashTab{
    private EmpLinkedList[] empLinkedListArray;
    private int size;
    //

    public HashTab(int size) {
        this.size = size;
        //初始化empLinkedListArray
        empLinkedListArray = new EmpLinkedList[size];
        //初始化每个链表
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    //添加雇员
    public void add(Emp emp){
        //根据员工id得到该员工应当添加到哪条链表
        int empLinkedListNo = hashFun(emp.id);
        //将emp添加到对应的链表中
        empLinkedListArray[empLinkedListNo].add(emp);
    }
    //编写散列函数，使用一个简单的取模法
    public int hashFun(int id){
        return id % size;
    }
    //遍历所有链表
    public void list(){
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i].list(i);
        }
    }
    //根据输入id查找雇员
    public void findEmpById(int id){
        int empLinkedListNo = hashFun(id);
        Emp emp = empLinkedListArray[empLinkedListNo].findEmpById(id);
        if (emp != null){
            System.out.printf("在第%d条链表找到雇员id=%d\n",empLinkedListNo + 1,id);
        }else {
            System.out.println("在哈希表中，没有找到该雇员");
        }
    }
}

//表示一个雇员
class Emp{
    public int id;
    public String name;
    public Emp next;//默认null
    public Emp(int id,String name){
        super();
        this.id = id;
        this.name = name;
    }
}

//创建EmpLinkedList表示链表
class EmpLinkedList{
    //头指针，执行第一个Emp，因此这个链表的head是直接指向第一个Emp
    private Emp head;
    //添加雇员到链表
    //1.假定添加雇员时，id自增长，即id的分配总是从小到大
    //因此总是添加到链表最后即可
    public void add(Emp emp){
        //第一个雇员
        if (head == null){
            head = emp;
            return;
        }
        Emp curEmp = head;
        while (true){
            if (curEmp.next == null) {
                break;
            }
            curEmp = curEmp.next;
        }
        curEmp.next = emp;
    }

    //遍历链表的雇员信息
    public void list(int no){
        if (head == null){
            System.out.println("第"+(no + 1)+"链表为空");
            return;
        }
        System.out.print("第"+(no + 1)+"链表信息：");
        Emp curEmp = head;
        while (true){
            System.out.printf("=> id=%d name=%s\t",curEmp.id,curEmp.name);
            if (curEmp.next == null){
                break;
            }
            curEmp = curEmp.next;
        }
        System.out.println();
    }

    //根据id查找雇员
    public Emp findEmpById(int id){
        if (head == null){
            System.out.println("链表为空");
            return null;
        }
        Emp curEmp = head;
        while (true){
            if (curEmp.id == id){
                break;
            }
            if (curEmp.next == null){
                curEmp = null;
                break;
            }
            curEmp = curEmp.next;
        }
        return curEmp;
    }
}
