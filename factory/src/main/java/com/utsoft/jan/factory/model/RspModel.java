package com.utsoft.jan.factory.model;

/**
 * Created by Administrator on 2019/6/26.
 * <p>
 * by author wz
 * <p>
 * com.utsoft.jan.factory.model
 */
public class RspModel<T> {
    public static final int SUCCEED = 1;

    public static final int UNKOWN_ERROR = 0;

    private String dateTime;
    private String message;
    private int code;
    private T result;

    public boolean success(){
        return code == SUCCEED;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
