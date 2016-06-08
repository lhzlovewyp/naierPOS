package com.joker.common.service.impl;

import com.joker.common.model.Dictionary;
import com.joker.common.service.BaseDataSer;
import com.joker.core.constant.BaseResources;
import com.joker.core.constant.ResponseState;
import com.joker.core.dto.ReturnBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/11.
 */
@Service
public class BaseDataSerImpl implements BaseDataSer{

    @Autowired
    RedisTemplate redis;

    public List<Dictionary> getListByType(String type) {
        List<Dictionary> dictList = new ArrayList<Dictionary>();
        Dictionary dict = null;
        BoundHashOperations boundHashOperations = redis.boundHashOps(type);

        Map<String,String> map = boundHashOperations.entries();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            dict = new Dictionary();
            dict.setCode(entry.getKey());
            dict.setName(entry.getValue());
            dictList.add(dict);
        }
        //取出为map无序，此处排序
        //Collections.sort(dictList);

        return dictList;
    }

    @RequestMapping(value = {"/appData"},method = RequestMethod.GET)
    @ResponseBody
    public ReturnBody getAppData() throws Exception {

        Map<String,String> map = BaseResources.getDomains();
        ReturnBody returnBody = new ReturnBody();
        returnBody.setStatus(ResponseState.SUCCESS);
        returnBody.setData(map);

        return  returnBody;
    }
}
