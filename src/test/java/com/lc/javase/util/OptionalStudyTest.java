package com.lc.javase.util;

import com.alibaba.fastjson.JSON;
import com.lc.javase.pojo.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class OptionalStudyTest {
    OptionalStudy optionalStudy = new OptionalStudy();
    List<User> userList = Arrays.asList(
            new User(1L, "依依", 18),
            new User(2L, "尔尔", 17),
            new User(3L, "散散", 20),
            new User(4L, "思思", 19),
            new User(5L, "呜呜", 25)
    );

    @Test
    public void testGetUserName() throws Exception {
        System.out.println(JSON.toJSONString(optionalStudy.getUserName(new User(1L, "依依", 18))));
        System.out.println(JSON.toJSONString(optionalStudy.getUserName(null)));
    }

    @Test
    public void testHandleUsers() throws Exception {
        optionalStudy.handleUsers(userList);
    }
}