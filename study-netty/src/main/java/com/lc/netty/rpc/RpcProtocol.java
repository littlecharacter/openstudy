package com.lc.netty.rpc;

import java.io.Serializable;

/**
 * @author gujixian
 * @since 2023/7/18
 */
public class RpcProtocol implements Serializable {
    public static class RpcHead implements Serializable {
        int flag;
        int dataLength;
        long requestId;
    }
    public static class Request implements Serializable {
        private String className;
        private String methodName;
        private Class<?>[] types;
        private Object[] params;
    }
    public static class Response implements Serializable {
        // 每个接口返回值的序列化字符串
        private String content;
    }
}