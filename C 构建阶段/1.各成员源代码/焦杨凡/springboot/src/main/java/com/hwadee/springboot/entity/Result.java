package com.hwadee.springboot.entity;

public class Result {
    private Integer code;
    private Object result;

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}
