package com.maker.crm.commons.model;

public class ReturnObject<T> {
    private int code; //0表示失败，1表示成功
    private String message;//错误提示
    private T object;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
