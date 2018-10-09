package com.lc.javase.zother;

import org.junit.Test;

public class CallBackTest {
    private CallBack callBack = new CallBack();
    @Test
    public void testSentMessage() throws Exception {
        //这个新启一个线就是异步回调
        callBack.sentMessage("这是一条消息!", new CallBack.ICallBack() {
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