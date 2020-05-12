package com.lc.javase.jvm;

import com.lc.javase.other.pojo.User;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class RuntimeData {
    AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) {
        User user = new User();
        user.setId(1L);
        user.setName("zhangsan");
        user.setAge(18);
        int size = new Random().nextInt(10) + 5;
        for (int i = 0; i < size; i++) {
            user.setAge(user.getAge() + i);
        }
        System.out.println(user);
    }
}
