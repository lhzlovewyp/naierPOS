package com.joker.common.service;

import com.joker.core.dto.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/17.
 */
public interface TestSer {
    public String selectUser();

    public String testException() throws Exception;

    public List<String> testPage(Map<String, Object> body, Page page);

    public void testThread();

    public void addTransactionTest() throws Exception;

    public void clearAllCache();
}
