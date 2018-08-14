package com.demon.dstudio.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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
    List<String> stackTrace = new ArrayList<>();

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

    public static ResponseMsg fail(int code, String comment, Throwable e) {
        ResponseMsg rest = new ResponseMsg();
        rest.status = false;
        rest.code = code;
        rest.data = comment;
        rest.setStackTraceInfo(e);
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

    public static ResponseMsg fail(int code, String url, String comment, Throwable e) {
        ResponseMsg rest = new ResponseMsg();
        rest.status = false;
        rest.code = code;
        rest.data = comment;
        rest.url = url;
        rest.setStackTraceInfo(e);
        return rest;
    }



    public static ResponseMsg fail(String url, Throwable e) {
        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.status = false;
        responseMsg.code = 500;
        responseMsg.data = e.toString();
        responseMsg.url = url;
        responseMsg.setStackTraceInfo(e);
        return responseMsg;
    }

    private void setStackTraceInfo(Throwable e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        for (int i = 0; i < stackTraceElements.length; i++) {
            stackTrace.add(stackTraceElements[i].toString());
        }
    }
}
