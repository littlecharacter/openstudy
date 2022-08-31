package com.lc.javase;

import com.alibaba.fastjson.JSON;
import com.lc.javase.other.pojo.Seller;
import com.lc.javase.other.pojo.User;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class BaseTest {
    @Test
    public void test() throws Exception {
        String userDir = System.getProperty("user.dir");
        System.out.println(userDir);
        System.out.println(LocalDateTime.parse("2022-12-31 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toInstant(ZoneOffset.of("+8")).toEpochMilli());
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

    @Test
    public void testPojo() {
        User user = new User();
        user.setId(1L);
        user.setName("zs");
        user.setAge(18);
        user.setSex(1);
        System.out.println(JSON.toJSONString(user));
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void testBitOperate() {
        int m = 10;
        int n = 4;
        int x = 3;

        System.out.println(m % n);
        System.out.println(m & (n - 1));
        System.out.println(m % x);
        System.out.println(m & (x - 1));
    }

    @Test
    public void testGetMinRun() {
        for (int i = 33; i < 100; i++) {
            System.out.println(minRunLength(i));
        }
    }

    private int minRunLength(int n) {
        assert n >= 0;
        int r = 0;
        while (n >= 32) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }
}