package com.dstudio.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by fish on 2017/11/9.
 */
@Getter
@Setter
@ToString
public class ResponseMsg {
    boolean status;
    int code;
    Object data;
    int total;
    String url;

    public static ResponseMsg success(Object result) {
        ResponseMsg rest = new ResponseMsg();
        rest.status = true;
        rest.code = 0;
        rest.data = result;
        return rest;
    }

    public static ResponseMsg success(Object result, int total) {
        ResponseMsg rest = new ResponseMsg();
        rest.status = true;
        rest.code = 0;
        rest.data = result;
        rest.total = total;
        return rest;
    }

    public static ResponseMsg fail(int code, String comment) {
        ResponseMsg rest = new ResponseMsg();
        rest.status = false;
        rest.code = code;
        rest.data = comment;
        return rest;
    }

    public static ResponseMsg fail(int code, String url, String comment) {
        ResponseMsg rest = new ResponseMsg();
        rest.status = false;
        rest.code = code;
        rest.data = comment;
        rest.url = url;
        return rest;
    }
}
