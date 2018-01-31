package com.lc.javase.util;

import com.alibaba.fastjson.JSON;
import com.lc.javase.pojo.User;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaStudy {
    public void filterUser(List<User> userList) {
        System.out.println(JSON.toJSONString(userList.stream().filter(user -> user.getId() > 2 && user.getId() < 4).collect(Collectors.toList())));
    }

    public User findMaxUser(List<User> userList) {
        /*
        return userList.stream().max(new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                return u1.getAge().compareTo(u2.getAge());
            }
        }).orElseGet(User::new);
        */
        //return userList.stream().max((u1, u2) -> u1.getAge().compareTo(u2.getAge())).orElseGet(User::new);
        return userList.stream().max(Comparator.comparing(User::getAge)).orElseGet(User::new);
    }
}
