package com.lc.javase.base;

import com.lc.javase.base.CallBackStudy;
import org.junit.Test;

public class CallBackStudyTest {
    private CallBackStudy callBackStudy = new CallBackStudy();
    @Test
    public void testSentMessage() throws Exception {
        //这个新启一个线就是异步回调
        callBackStudy.sentMessage("这是一条消息!", new CallBackStudy.ICallBack() {
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