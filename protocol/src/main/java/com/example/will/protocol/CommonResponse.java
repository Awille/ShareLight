package com.example.will.protocol;

import java.io.Serializable;

public class CommonResponse implements Serializable {
    /**
     * 状态码
     */
    private String code;
    /**
     * 状态消息
     */
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
