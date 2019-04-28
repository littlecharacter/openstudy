package com.lc.javase.base;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class DateTimeStudy {
    public void showDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        long [] data = new long[]{1516792200L,1516793486L,1516795437L,1479692912L};
        Arrays.sort(data);
        Arrays.stream(data).forEach(time -> System.out.println(LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.systemDefault()).format(formatter)));

        //LocalDateTime time = LocalDateTime.of(2018, 1, 24, 19, 10, 0);
        LocalDateTime time = LocalDateTime.now();
        System.out.println(time.format(formatter));
        System.out.println(time.toEpochSecond(ZoneOffset.of("+8")));
        System.out.println(time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(time.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(1516752000L), ZoneId.systemDefault());
        //dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(1531670400000L), ZoneId.systemDefault());
        System.out.println(dateTime.format(formatter));
    }
}
