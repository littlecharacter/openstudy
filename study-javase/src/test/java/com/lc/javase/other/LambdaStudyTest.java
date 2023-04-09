package com.lc.javase.other;

import com.alibaba.fastjson.JSON;
import com.lc.javase.other.pojo.User;
import com.lc.javase.grow.LambdaStudy;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class LambdaStudyTest {
    LambdaStudy lambdaStudy = new LambdaStudy();
    List<User> userList = Arrays.asList(
            new User(1L, "依依", 18),
            new User(2L, "尔尔", 17),
            new User(3L, "散散", 20),
            new User(4L, "思思", 19),
            new User(5L, "呜呜", 25)
    );

    @Test
    public void testFilterUser() throws Exception {
        lambdaStudy.filterUser(userList);
    }

    @Test
    public void testFindMaxUser() throws Exception {
        System.out.println(JSON.toJSONString(lambdaStudy.findMaxUser(userList)));
    }

}