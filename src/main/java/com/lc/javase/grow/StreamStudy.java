package com.lc.javase.grow;

import com.alibaba.fastjson.JSON;
import com.lc.javase.other.pojo.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class StreamStudy {
    public List<User> filterUser(List<User> userList) {
        return userList.stream().filter(user -> user.getId() > 2 && user.getId() < 4).collect(Collectors.toList());
    }

    public String joiningUserName(List<User> userList) {
        return userList.stream().map(User::getName).collect(Collectors.joining(","));
    }

    public void distinctUserAge(List<User> userList) {
        userList.parallelStream().map(User::getName).distinct().collect(Collectors.toList()).forEach(System.out::println);
        System.out.println(JSON.toJSONString(userList.stream().map(User::getAge).distinct().collect(Collectors.toList())));
    }

    public double averageUserAge(List<User> userList) {
        return userList.stream().mapToDouble(User::getAge).map(a -> a * a).average().orElse(0d);
    }

    public List<User> sortUser(List<User> userList) {
        return userList.stream().sorted(Comparator.comparing(User::getAge)).collect(Collectors.toList());
    }

    public void matchUser(List<User> userList) {
        System.out.println(userList.stream().allMatch(apple -> apple.getName().contains("依")));
        System.out.println(userList.stream().anyMatch(apple -> apple.getName().contains("依")));
        System.out.println(userList.stream().noneMatch(apple -> apple.getName().contains("依")));
    }

    public User reduceUser(List<User> userList) {
        return userList.stream()
                .filter(apple -> apple.getName().equals("依"))
                .reduce(BinaryOperator.maxBy(Comparator.comparing(User::getAge)))
                .orElse(new User(-1L, "无名", -1));
    }

    public List<String> mergeListByMap(List<String> list1, List<String> list2) {
        List<String> list = new ArrayList<>();
         list1.stream()
                .map(item1 -> list2.stream().map(item2 -> item1 + " " + item2)) //Stream<Stream<String>>
                .collect(Collectors.toList())
                .forEach(item -> list.addAll(item.collect(Collectors.toList())));
        return list;
    }

    public List<String> mergeListByFlatMap(List<String> list1, List<String> list2) {
        return list1.stream()
                .flatMap(item1 -> list2.stream().map(item2 -> item1 + " " + item2)) //Stream<String>
                .collect(Collectors.toList());
    }

    public List<String> convertArrayToList(String[][] data) {
        return Arrays.stream(data).flatMap(Arrays::stream).collect(Collectors.toList());
    }

    public static void main(String[] args){

    }
}
