package com.example.will.protocol;

import java.io.Serializable;

public class CommonRequest implements Serializable {
    /**
     * 服务号 某些接口需要
     */
    private String service;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
