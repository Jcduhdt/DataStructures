package com.zx.newcoder.base07;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 贪心
 * 策略：按照最先结束的项目来排，然后去掉因为该项目而不能安排的项目
 * 一些项目要占用一个会议室宣讲， 会议室不能同时容纳两个项目的宣讲。
 * 给你每一个项目开始的时间和结束的时间(给你一个数组，里面是一个个具体的项目）
 * 你来安排宣讲的日程， 要求会议室进行的宣讲的场次最多。 返回这个最多的宣讲场次。
 */
public class Code_06_BestArrange {

	public static class Program {
		public int start;
		public int end;

		public Program(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	//按结束时间早排在前面
	public static class ProgramComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o1.end - o2.end;
		}

	}

	public static int bestArrange(Program[] programs, int cur) {
		Arrays.sort(programs, new ProgramComparator());
		int result = 0;
		for (int i = 0; i < programs.length; i++) {
			//不断推进当前时刻
			if (cur <= programs[i].start) {
				result++;
				cur = programs[i].end;
			}
		}
		return result;
	}

	public static void main(String[] args) {

	}
}
