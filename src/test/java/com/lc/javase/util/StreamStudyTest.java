package com.lc.javase.util;

import com.alibaba.fastjson.JSON;
import com.lc.javase.pojo.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class StreamStudyTest {
    StreamStudy streamStudy = new StreamStudy();

    List<User> userList = Arrays.asList(
            new User(1L, "依依", 18),
            new User(2L, "尔尔", 17),
            new User(3L, "散散", 20),
            new User(4L, "思思", 19),
            new User(5L, "呜呜", 25)
    );

    List<String> list1 = Arrays.asList("hello", "hi", "你好");
    List<String> list2 = Arrays.asList("zhangsan", "lisi", "wangwu", "zhaoliu");

    String[][] data = {{"a", "b"}, {"c", "d"},{"e", "f"}};

    @Test
    public void testFilterUser() throws Exception {
        System.out.println(JSON.toJSONString(streamStudy.filterUser(userList)));
    }

    @Test
    public void testJoiningUserName() throws Exception {
        System.out.println(streamStudy.joiningUserName(userList));
    }

    @Test
    public void testDistinctUserAge() throws Exception {
        streamStudy.distinctUserAge(userList);
    }

    @Test
    public void testAverageUserAge() throws Exception {
        System.out.println(streamStudy.averageUserAge(userList));
    }

    @Test
    public void testSortUser() throws Exception {
        System.out.println(JSON.toJSONString(streamStudy.sortUser(userList)));
    }

    @Test
    public void testMatchUser() throws Exception {
        streamStudy.matchUser(userList);
    }

    @Test
    public void testReduceUser() throws Exception {
        System.out.println(JSON.toJSONString(streamStudy.reduceUser(userList)));
    }

    @Test
    public void testMergeListByMap() throws Exception {
        System.out.println(JSON.toJSONString(streamStudy.mergeListByMap(list1, list2)));
    }

    @Test
    public void testMergeListByFlatMap() throws Exception {
        System.out.println(JSON.toJSONString(streamStudy.mergeListByFlatMap(list1, list2)));
    }

    @Test
    public void testConvertArrayToList() throws Exception {
        System.out.println(JSON.toJSONString(streamStudy.convertArrayToList(data)));
    }

}