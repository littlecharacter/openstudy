package com.lc.javase.base;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 按照这个例子走一遍就明白了
 * @author gujixian
 * @since 2023/8/15
 */
public class StreamStudy {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(4);
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(-1);
        list.add(2);
        list.add(0);
        Stream<Integer> stream = list.stream();
        stream.filter((n) -> n > 0)
                .sorted()
                .collect(Collectors.toList())
                .forEach(System.out::println);
    }
}
