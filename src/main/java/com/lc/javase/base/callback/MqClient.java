package com.lc.javase.base.callback;

public class MqClient {
    public void sentMessage(String message, CallBack callBack){
        System.out.println("sentMessage:发送消息到消息服务器,message = " + message);
        if (Math.random() > 0.5) {
            callBack.onSuccess();
        } else {
            callBack.onFailed("发送消息到消息服务器失败!");
        }
    }
}
