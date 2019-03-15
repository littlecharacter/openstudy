package com.lc.javase;

import com.lc.javase.pojo.Seller;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Objects;

public class BaseTest {
    @Test
    public void test() throws Exception {
        String userDir = System.getProperty("user.dir");
        System.out.println(userDir);
        Assert.assertTrue("这只是一个测试", LocalDate.now().getYear() < Integer.MAX_VALUE);
    }

    @Test
    public void testEquals() throws Exception {
        Seller seller1 = new Seller();
        Seller seller2 = new Seller();

        System.out.println(seller1.equals(seller2));

        String s1 = new String("abc");
        String s2 = new String("abc");

        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
        System.out.println(Objects.equals(s1, s2));

        Assert.assertTrue("这只是一个测试", LocalDate.now().getYear() < Integer.MAX_VALUE);
    }
}