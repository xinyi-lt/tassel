package com.zuoquan.lt.rpc;

import java.io.Serializable;


public class Result<T> implements Serializable{

	private static final long serialVersionUID = 7577189576867489930L;
	//默认200 为成功
	int status = 200;
    String message;
    T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public Result(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
