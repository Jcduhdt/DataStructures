package com.zx.algorithm.dynamic;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-03-08
 * 力扣 题32
 * 动态规划
 * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
 *
 * 怎么做都做不出来，为什么啊
 *
 * 官方解答
 * 这个问题可以通过动态规划解决。我们定义一个 dp 数组，其中第i个元素表示以下标为 i 的字符结尾的最长有效子字符串的长度。
 * 我们将 dp 数组全部初始化为 0 。现在，很明显有效的子字符串一定以 ‘)’ 结尾。
 * 这进一步可以得出结论：以 ‘(’ 结尾的子字符串对应的 dp 数组位置上的值必定为 0 。
 * 所以说我们只需要更新 ‘)’ 在 dp 数组中对应位置的值。
 */
public class LongestValidParentheses {
    public static void main(String[] args) {
        LongestValidParentheses lvp = new LongestValidParentheses();
        String s = "(())()(()((";
        System.out.println(lvp.longestValidParentheses(s));
    }

    public int longestValidParentheses(String s) {
        int maxans = 0;
        int dp[] = new int[s.length()];
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                //判断前一位是否为(，是就在前两位的dp位置+2
                //因为若前两位是(,那就表示重新开始计数，因为以(结尾的都看作无效子串
                //因为若前两位是),则直接在原有基础上+2
                //注意判断是否越界
                //判断前一位是否为),则查看dp[i-1]所记录的长度子串的头部的前一位是否是(，是就在dp[i-1]的基础上+2
                //不是就在dp[i-1]的基础上+2+dp[i-1]所记录的长度子串的头部的前两位的dp值
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }
                //在每次记录后，更新max
                maxans = Math.max(maxans, dp[i]);
            }
        }
        return maxans;
    }
}
