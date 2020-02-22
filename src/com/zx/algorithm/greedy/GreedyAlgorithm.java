package com.zx.algorithm.greedy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author ZhangXiong
 * @version v12.0.1
 * @date 2020-02-22
 * 贪心算法
 * 每次取最优
 * 结果不一定是整体最优，但是趋近最优
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        //创建广播电台，放入到Map
        HashMap<String, HashSet<String>> broadcasts = new HashMap<>();
        //将各个电台放入到broadcasts
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");

        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");

        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");


        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");

        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");

        //加入到map
        broadcasts.put("k1", hashSet1);
        broadcasts.put("k2", hashSet2);
        broadcasts.put("k3", hashSet3);
        broadcasts.put("k4", hashSet4);
        broadcasts.put("k5", hashSet5);

        //allAreas 存放所有地区
        HashSet<String> allAreas = new HashSet<>();
        for (int i = 0; i < broadcasts.size(); i++) {
            for (Map.Entry<String, HashSet<String>> broadcast : broadcasts.entrySet()) {
                allAreas.addAll(broadcast.getValue());
            }
        }
        System.out.println(allAreas.toString());

        //创建ArrayList，存放选择的电台集合
        ArrayList<String> selects = new ArrayList<>();
        //定义临时集合，在遍历过程中，存放遍历过程中的电台覆盖的地区和当前还没有覆盖的地区的交集
        HashSet<String> tempSet = new HashSet<>();
        //定义maxKey，保存再一次遍历中，能够覆盖最大未覆盖地区的对应的电台的key
        String maxKey = null;
        //如果maxKey步为null,则会加入到selects
        while (allAreas.size() != 0) {//如果allAreas不为0，则表示还没有覆盖到所有的地区
            maxKey = null;//每进行一次循环就要置空
            //遍历broadcasts,取出对应key
            for (String key : broadcasts.keySet()) {
                tempSet.clear();//每进行一次for循环就清空
                //当前这个key能够覆盖的地区
                HashSet<String> areas = broadcasts.get(key);
                tempSet.addAll(areas);
                //求出tempSet和allArea集合的交集，交集会赋给tempSet
                tempSet.retainAll(allAreas);
                //如果当前集合包含的未覆盖地区的数量，比maxKey指向的集合地区还多
                //就需要重置maxKey,这个判断就是一轮后使maxKey指向覆盖未覆盖地区最多的广播
                //体现出贪心算法的特点
                if (tempSet.size() > 0 && (maxKey == null || tempSet.size() > broadcasts.get(maxKey).size())) {
                    maxKey = key;
                }
            }
            //maxKey != null,就应该将maxKey加入到selects
            if (maxKey != null) {
                selects.add(maxKey);
                //将maxKey指向的广播电台覆盖的区域从allAreas去掉
                //注意remove和removeAll的使用，第一次没注意用了remove导致程序一直循环。
                allAreas.removeAll(broadcasts.get(maxKey));
            }
        }
        System.out.println("得到的选择结果是" + selects);
    }
}
