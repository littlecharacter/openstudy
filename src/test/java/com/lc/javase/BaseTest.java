package com.lc.javase;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;

public class BaseTest {
    @Test
    public void test(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        long [] data = new long[]{1516792200L,1516793486L,1516795437L};
        Arrays.sort(data);
        Arrays.stream(data).forEach(time -> System.out.println(LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault()).format(formatter)));
    }

    @Test
    public void testHello() {
        System.out.println("Hello World!");
    }

    @Test
    public void testTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //LocalDateTime time = LocalDateTime.of(2018, 1, 24, 19, 10, 0);
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time.format(formatter));
        System.out.println(time.toEpochSecond(ZoneOffset.of("+8")));
        System.out.println(time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(time.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(1516752000L), ZoneId.systemDefault());
        dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(1516789816018L), ZoneId.systemDefault());
        System.out.println(dateTime.format(formatter));
    }

    @Test
    public void testLinkedList() {
        LinkedList<Integer> handlers = new LinkedList<>();
        handlers.add(1);
        handlers.add(2);
        handlers.add(3);
        handlers.add(4);

        System.out.println(handlers.peekFirst());
        System.out.println(handlers.peekFirst());
        System.out.println(handlers.peekFirst());
        System.out.println(handlers.peekFirst());
        System.out.println(handlers.peekFirst());
    }

    @Test
    public void testDivideAndMod() {
        int serviceMonth = 201802;
        int year = serviceMonth / 100;
        int month = serviceMonth % 100;
        System.out.println("year:" + year + ",month:" + month);
        for (month = 1; month <= 12; month ++) {
            Date startCreateTime = new Date();
            //注意设置的先后顺序:因为用的是new Date();
            startCreateTime = DateUtils.setSeconds(startCreateTime, 0);
            startCreateTime = DateUtils.setMinutes(startCreateTime, 0);
            startCreateTime = DateUtils.setHours(startCreateTime, 0);
            startCreateTime = DateUtils.setDays(startCreateTime, 1);
            startCreateTime = DateUtils.setMonths(startCreateTime, month - 1);
            startCreateTime = DateUtils.setYears(startCreateTime, year);
            Date endCreateTime = DateUtils.addMonths(startCreateTime, 1);
            System.out.println("startCreateTime:" + startCreateTime + ",endCreateTime:" + endCreateTime);
        }
    }

    @Test
    public void testEncodeAndDecode() throws UnsupportedEncodingException {
        String source = "[{\"I\":10112,\"V\":\"1000\"},{\"I\":10113,\"V\":\"2000\"},{\"I\":10115,\"V\":\"165\"},{\"I\":10117,\"V\":\"200\"},{\"I\":10118,\"V\":\"13626\"},{\"I\":10119,\"V\":\"6600\"},{\"I\":10123,\"V\":\"9\"},{\"I\":10124,\"V\":\"16000\"},{\"I\":10125,\"V\":\"17000\"},{\"I\":20001,\"V\":\"1\"},{\"I\":20002,\"V\":\"1\"},{\"I\":20003,\"V\":\"2\"},{\"I\":20004,\"V\":\"2\"},{\"I\":20005,\"V\":\"500\"},{\"I\":20007,\"V\":\"4\"},{\"I\":20008,\"V\":\"123456789\"},{\"I\":20009,\"V\":\"1111111111111\"},{\"I\":20010,\"V\":\"987654321\"},{\"I\":20011,\"V\":\"33000\"},{\"I\":10101,\"V\":\"8000\"},{\"I\":10102,\"V\":\"3000\"},{\"I\":10103,\"V\":\"2000\"},{\"I\":10104,\"V\":\"3000\"},{\"I\":10105,\"V\":\"1000\"},{\"I\":10106,\"V\":\"1000\"},{\"I\":10107,\"V\":\"2000\"},{\"I\":10108,\"V\":\"3000\"},{\"I\":10109,\"V\":\"1000\"},{\"I\":10110,\"V\":\"1000\"},{\"I\":10111,\"V\":\"1000\"}]";
        System.out.println("source:" + source);
        String encode = Base64.getEncoder().encodeToString(source.getBytes("utf-8"));
        System.out.println("encode:" + encode);
        String decode = new String(Base64.getDecoder().decode(encode), "utf-8");
        System.out.println("decode:" + decode);
    }
}
