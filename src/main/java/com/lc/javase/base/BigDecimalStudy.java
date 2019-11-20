package com.lc.javase.base;

import java.math.BigDecimal;

public class BigDecimalStudy {
    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal("0.1");
        amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(amount.toString());
    }
}
