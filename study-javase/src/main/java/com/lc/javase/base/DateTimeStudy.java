package com.lc.javase.base;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DateTimeStudy {
    public void showDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        long[] data = new long[]{1556639999L, 1553961599L, 1516792200L, 1516793486L, 1516795437L, 1479692912L};
        //Arrays.sort(data);
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

    public int isUrgency() {
        // 获取当前日期和时间
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
        String dateTime = LocalDateTime.now().format(df);
        String[] dateTimes = dateTime.split(" ");
        String date = dateTimes[0];
        String time = dateTimes[1];
        // 获取非提现日
        String noDrawDayStr = "2019-05-01,2019-05-02,2019-05-03,2019-05-04,2019-05-11,2019-05-12,2019-05-18,2019-05-19,2019-05-25,2019-05-26";
        if (noDrawDayStr != null) {
            List<String> noDrawDays = Arrays.asList(noDrawDayStr.split(","));
            if (noDrawDays.contains(date)) {
                return 0;
            }
        }
        // 获取提现时间段
        String drawTimeStr = "09:00:00-18:00:00";
        if (drawTimeStr != null) {
            List<String> drawTimeList = Arrays.asList(drawTimeStr.split(","));
            String result = drawTimeList.stream().filter(drawTime -> {
                String[] drawTimes = drawTime.split("-");
                return time.compareTo(drawTimes[0]) >= 0 && time.compareTo(drawTimes[1]) <= 0;
            }).findFirst().orElse(null);
            if (result == null) {
                return 0;
            } else {
                return 1;
            }
        }
        // 工作日，无付款时间段配置
        return 0;
    }

    public void getCurrentTime() {
        long currentTime = System.nanoTime();
        System.out.println(currentTime);
        currentTime = System.currentTimeMillis();
        System.out.println(currentTime);
    }

    public static void main(String[] args) throws InterruptedException {
        // 运行后将时间倒退一分钟
        System.out.println("Start:\t" + new Date());

        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            System.out.println("Executor:\t" + new Date());
        }, 60, TimeUnit.SECONDS);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer:\t" + new Date());
            }
        }, 60000);

        for (; ; ) {
            System.out.println(System.nanoTime());
            System.out.println(System.currentTimeMillis());
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
