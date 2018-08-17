package com.lc.javase.util;

import com.lc.javase.pojo.Result;

public class ResultUtil {
    public static <T> Result<T> success() {
        return new Result<>(Result.ErrorCodeEnum.SUCCESS.getCode(), Result.ErrorCodeEnum.SUCCESS.getDesc(), null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(Result.ErrorCodeEnum.SUCCESS.getCode(), Result.ErrorCodeEnum.SUCCESS.getDesc(), data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(Result.ErrorCodeEnum.FAIL.getCode(), Result.ErrorCodeEnum.FAIL.getDesc(), null);
    }

    public static <T> Result<T> fail(String desc) {
        return new Result<>(Result.ErrorCodeEnum.FAIL.getCode(), desc, null);
    }

    public static <T> Result<T> fail(String desc, T data) {
        return new Result<>(Result.ErrorCodeEnum.FAIL.getCode(), desc, data);
    }

    public static <T> Result<T> fail(Result.ErrorCodeEnum errorCode) {
        return new Result<>(errorCode.getCode(), errorCode.getDesc(), null);
    }

    public static <T> Result<T> fail(Result.ErrorCodeEnum errorCode, T data) {
        return new Result<>(errorCode.getCode(), errorCode.getDesc(), data);
    }
}
