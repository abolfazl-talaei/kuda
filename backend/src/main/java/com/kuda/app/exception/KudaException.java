package com.kuda.app.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class KudaException extends Exception {

    private String code;
    private HttpStatus status;
    private String resource;
    private String id;

    public KudaException(String code, HttpStatus status, String resource, String id) {
        super(code);
        this.code = code;
        this.status = status;
        this.resource = resource;
        this.id = id;
    }

}
