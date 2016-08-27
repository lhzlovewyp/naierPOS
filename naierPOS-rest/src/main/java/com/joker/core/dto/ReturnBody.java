package com.joker.core.dto;

/**
 * 请求返回封装对象
 *
 * @Author crell
 * @Date 2015/12/7 10:58
 */
public class ReturnBody {

    private int status;//状态码

    private String msg;//返回消息

    private Object data;//返回数据

    private int pages;//分页-页数

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
