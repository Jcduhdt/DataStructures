package com.zx.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-10
 * 逆波兰表达式 (后缀表达式)
 * 中缀转后缀表达式
 * 就直接从左至右计算就行，特别适合计算机计算
 */
public class PolandNotation {
    public static void main(String[] args) {

        //完成将中缀表达式转成后缀表达式
        //说明
        //1.1+((2+3)*4)-5 转成 1 2 3 + 4 * + 5 -
        //2.因为是str 进行操作，不方便，因此将"1+((2+3)*4)-5"中缀表达式对应的List
        // 即"1+((2+3)*4)-5"=>ArrayList [1,+,(,(,2,+,3,),*,4,),-,5]
        //3.将得到的中缀表达式对应的List => 后缀表达式
        // 即[1, +, (, (, 2, +, 3, ), *, 4, ), -, 5] => ArrayList[1,2,3,+,4,*,+,5,-]
        String expression = "1+((2+3)*4)-5";
        List<String> infixExpressionList = toInfixExpressionList(expression);
        System.out.println("中缀表达式对应的List"+infixExpressionList);//[1, +, (, (, 2, +, 3, ), *, 4, ), -, 5]
        List<String> parseSuffixExpressionList = parseSuffixExpressionList(infixExpressionList);
        System.out.println("后缀表达式对应的List"+parseSuffixExpressionList);
        int res = caculate(parseSuffixExpressionList);
        System.out.println("计算的结果是：" + res);

        //先定义逆波兰表达式
        //(3+4)*5-6 => 3 4 + 5 * 6 -
        //4 * 5 - 8 + 60 + 8 / 2 => 4 5 * 8 - 60 + 8 2 / +
        //为说明方便，逆波兰表达式的数字和符号用空格分开
        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";
        //思路
        //1.先将"3 4 + 5 * 6 -"方法ArrayList中
        //2.将ArrayList传递给一个方法，遍历ArrayList配合栈完成计算
        List<String> rpnList = getListString(suffixExpression);
        System.out.println("renList=" + rpnList);
        int res1 = caculate(rpnList);
        System.out.println("计算的结果是：" + res1);
    }

    //将得到的中缀表达式对应的List => 后缀表达式
    public static List<String> parseSuffixExpressionList(List<String> ls){
        //定义两个栈
        Stack<String> s1 = new Stack<>();//符号栈
        //因为s2这个栈在整个转换过程中，没有pop操作，而且后面还要逆序输出
        //所以直接用List<String> s2
        ArrayList<String> s2 = new ArrayList<>();//存储中间结果
        //遍历ls
        for (String item : ls) {
            //如果是一个数，加入s2
            if (item.matches("\\d+")){
                s2.add(item);
            }else if (item.equals("(")){
                s1.push(item);
            }else if (item.equals(")")){
                //如果是右括号，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
                while (!s1.peek().equals("(")){
                    s2.add(s1.pop());
                }
                s1.pop();//将(弹出，消除小括号
            }else{
                //当item的优先级小于等于s1栈顶运算符，将s1栈顶的运算弹出并加入到s2中，再次转到(4.1)与s1中新栈顶运算符比较
                //问题：缺少一个比较优先级的算法
                while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)){
                    s2.add(s1.pop());
                }
                //还需要间item压入栈
                s1.push(item);
            }
        }
        //将s1中剩余的运算符依次弹出并加入s2
        while (s1.size() != 0){
            s2.add(s1.pop());
        }
        return s2;//因为是存放的List，所以按顺序输出就是对应的后缀表达式
    }


    //将中缀表达式转成对应的List
    //s = "1+((2+3)*4)-5"
    public static List<String> toInfixExpressionList(String s){
        //定义一个List，存放中缀表达式对应的内容
        ArrayList<String> ls = new ArrayList<>();
        int i = 0;//指针，用于遍历中缀表达式字符串
        String str;//对多位数的拼接
        char c;//每遍历到一个字符，就放到c中
        do{
            //如果c是一个非数字，需要加入到ls
            if ((c=s.charAt(i))<48 || (c=s.charAt(i))>57){
                ls.add(""+c);
                i++;//i后移
            }else{//若是数，则考虑多位数
                str = "";//先将str置成""  '0'[48] -> '9'[57]
                while (i < s.length() && (c=s.charAt(i)) >=48 && (c=s.charAt(i)) <= 57){
                    str += c;
                    i++;
                }
                ls.add(str);
            }
        }while (i < s.length());{
            return ls;//返回表达式
        }
    }
    //将一个逆波兰表达式，依次将数据和运算符放入到ArrayList中
    public static List<String> getListString(String suffixExpression){
        //将suffixExpression分割
        String[] split = suffixExpression.split(" ");
        List<String> list = new ArrayList<>();
        for (String ele : split) {
            list.add(ele);
        }
        return list;
    }

    //完成对逆波兰表达式的运算
    /*
        1.从左至右扫描，将3和4 压入堆栈
        2.遇到+运算符，因此弹出4和3，计算出3+4的值，得7，再将7入栈
        3.将5入栈
        4.*运算符，弹出5和7，计算7*5=35 入栈
        5.将6入栈
        6.-运算符，计算35-6=29，得到最终结果
     */
    public static int caculate(List<String> ls){
        //创建栈
        Stack<String> stack = new Stack<>();
        //遍历ls
        for (String item : ls) {
            if (item.matches("\\d+")){//正则表达式，匹配数字
                stack.push(item);
            }else{
                //pop出俩数，运算入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                int res = 0;
                if (item.equals("+")){
                    res = num1 + num2;
                }else if (item.equals("-")){
                    res = num1 - num2;
                }else if(item.equals("*")){
                    res = num1 * num2;
                }else if (item.equals("/")){
                    res = num1 / num2;
                }else {
                    throw new RuntimeException("运算符有误");
                }
                //把res入栈
                stack.push(""+res);
            }
        }
        //最后留在stack中的数据是结果
        return Integer.parseInt(stack.pop());
    }
}

//编写一个类Operation 可以返回运算符对应优先级
class Operation{
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    //写方法返回优先级数字
    public static int getValue(String operation){
        int result = 0;
        switch(operation){
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                System.out.println("不存在该运算符");
                break;
        }
        return result;
    }
}