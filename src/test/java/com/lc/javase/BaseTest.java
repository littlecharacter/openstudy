package com.lc.javase;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class BaseTest {
    @Test
    public void test(){
        String userDir = System.getProperty("user.dir");
        System.out.println(userDir);
        Assert.assertTrue("这只是一个测试", LocalDate.now().getYear() < Integer.MAX_VALUE);
        String s = "abc";
        System.out.println(s.getBytes().length);
    }
}