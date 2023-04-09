package com.lc.javase.base;

public enum EnumStudy {
    BILL99_PAY(EnumStudy.BILL99, "快钱支付"),
    CEB_PAY(EnumStudy.CEB, "光大支付"),
    CMB_PAY(EnumStudy.CMB, "招商支付"),
    YOP_PAY(EnumStudy.YOP, "易宝支付");

    public static final int BILL99 = 1;
    public static final int CEB = 2;
    public static final int CMB = 3;
    public static final int YOP = 4;

    private int code;
    private String desc;

    EnumStudy(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
