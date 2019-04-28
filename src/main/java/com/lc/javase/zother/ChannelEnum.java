package com.lc.javase.zother;

/**
 * @author daojia
 */
public enum ChannelEnum {
    BILL99_PAY(ChannelEnum.BILL99, "快钱支付"),
    CEB_PAY(ChannelEnum.CEB, "光大支付"),
    CMB_PAY(ChannelEnum.CMB, "招商支付"),
    YOP_PAY(ChannelEnum.YOP, "易宝支付");

    public static final int BILL99 = 1;
    public static final int CEB = 2;
    public static final int CMB = 3;
    public static final int YOP = 4;

    private int code;
    private String desc;

    ChannelEnum(int code, String desc) {
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
