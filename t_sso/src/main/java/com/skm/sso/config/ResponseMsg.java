package com.skm.sso.config;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ResponseMsg  {

    private HttpStatus status;
    private StatusEnum message;
    private Object data;

    public ResponseMsg() {
        this.status = null;
        this.data = null;
        this.message = StatusEnum.BAD_REQUEST;
    }

}