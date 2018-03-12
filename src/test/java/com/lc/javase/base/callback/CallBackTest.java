package com.lc.javase.base.callback;

import org.junit.Test;

public class CallBackTest {
    private MqClient mqClient = new MqClient();
    @Test
    public void testCallBack() {
        mqClient.sentMessage("这是一条消息!", new CallBack() {
            @Override
            public void onSuccess() {
                System.out.println("onSuccess:消息发送成功!");
            }

            @Override
            public void onFailed(String s) {
                System.out.println("onFailed:消息发送失败!s = " + s);
            }
        });
    }
}