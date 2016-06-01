package com.joker.common.service;

import com.joker.common.model.Dictionary;

import java.util.List;

/**
 * Created by Administrator on 2016/1/11.
 */
public interface BaseDataSer {
    /**
     * 根据字典类型获取字典
     * @param type
     * @return List<Dictionary>
     */
    List<Dictionary> getListByType(String type);
}
