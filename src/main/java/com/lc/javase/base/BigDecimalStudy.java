package com.lc.javase.base;

import java.math.BigDecimal;

public class BigDecimalStudy {
    /**
     * 学习控制BigDecimal的小数位数
     */
    public void studyScale() {
        BigDecimal amount = new BigDecimal("0.1");
        BigDecimal returnAmount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
        System.out.println(amount.toString());
        System.out.println(returnAmount.toString());
    }
}
