package com.lc.algorithm;

import java.util.concurrent.TimeUnit;

/**
 * @author gujixian
 * @since 2023/5/13
 */
public class NC0006ShortUrlGenerator {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            long id = new NC0006Snowflake().getId(1, 5);
            System.out.println("----------------------" + id);
            System.out.println("----------------------" + new NC0006ShortUrlGenerator().decimalTo62(id));
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }

    /**
     * 十进制转 62 进制
     *
     * @param decimalNum
     * @return
     */
    public static String decimalTo62(long decimalNum) {
        String output = "";
        String baseDigits = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        long quotient = decimalNum;

        do {
            long remainder = quotient % 62;
            output = baseDigits.charAt((int) remainder) + output;
            quotient = quotient / 62;
        } while (quotient > 0);

        return output;
    }
}
