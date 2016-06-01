package com.joker.common.model;

import java.io.Serializable;

/**
 * 字典
 * Created by Administrator on 2015/5/28.
 */
public class Dictionary implements Serializable {

    private int dictId;
    private String category;
    private String code;
    private String name;

    public int getDictId() {
        return dictId;
    }

    public void setDictId(int dictId) {
        this.dictId = dictId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
