package com.example.rongfu.util;
/**
 * 响应数据格式的类
 *
 * @param <T>携带的数据类型
 */
public class JsonResult<T> {
    private Integer state;//状态码
    private String message;//与状态码相应的描述信息
    private T data;

    public JsonResult(Throwable e) {
        this.message = e.getMessage();
    }

    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public JsonResult(Integer state, T data) {
        this.state = state;
        this.data = data;
    }

    public JsonResult(T data) {
        this.data = data;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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
