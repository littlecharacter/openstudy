package com.lc.javase.base;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortStudy {
    public void studyJavaSort() {
        int[] arr = new int[]{6,8,4,7,6,5,9,2};
        // CollectionStudy[] a = new CollectionStudy[4];
        List<Integer> list = Arrays.asList(6,8,4,7,6,5,9,2);
        Arrays.sort(arr);
        // Arrays.sort(a, (o1, o2) -> 0);
        System.out.println("数组排序：" + JSON.toJSONString(arr));
        Collections.sort(list);
        // Collections.sort(list, (o1, o2) -> 0);
        System.out.println("集合排序：" + JSON.toJSONString(list));
    }
}
