package com.lc.javase.zother;

public class CallBack {
    public void sentMessage(String message, ICallBack callBack){
        System.out.println("sentMessage:发送消息到消息服务器,message = " + message);
        if (Math.random() > 0.5) {
            callBack.onSuccess();
        } else {
            callBack.onFailed("发送消息到消息服务器失败!");
        }
    }

    interface ICallBack {
        void onSuccess();
        void onFailed(String s);
    }
}
