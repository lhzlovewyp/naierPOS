package com.joker.common.service.impl;

import com.joker.common.mapper.BusinessMapper;
import com.joker.common.model.Business;
import com.joker.common.service.BusinessSer;
import com.joker.core.dto.Page;
import com.joker.core.dto.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by FunkySoya on 2015/6/18.
 */
@Service
public class BusinessSerImpl implements BusinessSer {

    @Autowired
    BusinessMapper businessMapper;

    public Boolean add(Business business) {
        return businessMapper.add(business);
    }

    public Boolean update(Business business) {
        return businessMapper.update(business);
    }

    public Boolean del(String businessId) {
        return businessMapper.del(businessId);
    }

    public Page getBusinessList(Map<String, Object> body, Page page) {
        page.setParams(body);
        List<Business> results = businessMapper.getBusinessList(page);
        page.setResults(results);
        return page;
    }

    public Business getBusinessById(String businessId) {
        return businessMapper.getBusinessById(businessId);
    }
}
