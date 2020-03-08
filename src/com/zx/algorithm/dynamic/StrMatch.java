package com.zx.algorithm.dynamic;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-29
 * LeedCode上的第10题
 * 写一个正则方法，对字符串进行匹配
 * text只能是a-z的字数串，pattern只能是a-z以及.和*的组成
 * .匹配任意字符，*表示上一个字符出现0或多次
 * 可使用回溯、动态规划
 * 但是我好菜，一个都想不到
 * 这个是官方解达，使用的自底向上进行动态规划
 */
public class StrMatch {
    public static void main(String[] args) {
        String text = "abcab";
        String pattern = "abhab";
        System.out.println(isMatch(text,pattern));
    }
    public static boolean isMatch(String text, String pattern) {
        boolean[][] dp = new boolean[text.length() + 1][pattern.length() + 1];
        dp[text.length()][pattern.length()] = true;

        //从末尾进行动态规划
        for (int i = text.length(); i >= 0; i--){
            for (int j = pattern.length() - 1; j >= 0; j--){
                //对在要匹配位置的地方进行判断两者是否相等
                boolean first_match = (i < text.length() &&
                        (pattern.charAt(j) == text.charAt(i) ||
                                pattern.charAt(j) == '.'));
                //要考虑pattern的后面个字符是否是*
                if (j + 1 < pattern.length() && pattern.charAt(j+1) == '*'){
                    dp[i][j] = dp[i][j+2] || first_match && dp[i+1][j];
                } else {
                    dp[i][j] = first_match && dp[i+1][j+1];
                }
            }
        }
        return dp[0][0];
    }
}
