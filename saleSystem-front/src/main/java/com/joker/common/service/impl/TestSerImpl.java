package com.joker.common.service.impl;

import com.joker.common.mapper.DictionaryMapper;
import com.joker.common.model.Dictionary;
import com.joker.common.model.User;
import com.joker.common.service.TestSer;
import com.joker.common.service.UserSer;
import com.joker.common.test.PushTask;
import com.joker.core.dto.Page;
import com.joker.core.servlet.ThreadPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Administrator on 2015/4/17.
 */
@Service
public class TestSerImpl implements TestSer {

    @Autowired
    UserSer userSer;

    @Autowired
    TaskExecutor taskExecutor;

    @Autowired
    DictionaryMapper mapper;

    public String selectUser() {
        return "cq";
    }

    public String testException() throws Exception {
        if (true){
            throw new Exception("service报错");
        }
        return null;
    }

    @Cacheable(value="cache30m")  //测试，spring redis cache。"default" 为配置的cacheManager的名称，可配多个
    public List<String> testPage(Map<String, Object> body, Page page) {
        List<String> list = new ArrayList<String>();
        int pageNo = page.getPageNo();
        for (int i = 1; i < page.getPageSize(); i++) {
            list.add(String.valueOf((pageNo-1)*10+i));
        }
        return list;
    }

    public void testThread() {
        User user = userSer.selectById("1");
        User user1 = null;
        List<User> list = new ArrayList<User>();
        for(int i=0;i<15;i++){
            user1 = new User();
            user1.setUserName(user.getUserName() + i);
            list.add(user1);
        }

        try{
            ThreadPoolExecutor pool = ThreadPool.getThreadPool();
            for(User u : list){
                PushTask pushTask = new PushTask(u.getUserName());
                pool.execute(pushTask);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

//        try{
//            for(User u : list){
//                PushTask pushTask = new PushTask(u.getUserName());
//                taskExecutor.execute(pushTask);
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }

    }

    public void addTransactionTest() throws Exception{
        Dictionary dictionary = new Dictionary();
        dictionary.setCategory("bl");
        dictionary.setName("cq1");
        dictionary.setCode("1");
        mapper.add(dictionary);

        //int i = 1 / 0;  //runtimeException

        try {
            throw new Exception();   //spring事务 默认回滚runtimeException,非运行时异常需配置rollback-for
        } catch (Exception e) {
            throw new Exception();
        }
    }

    @CacheEvict(value="cache30m",allEntries=true)
    public void clearAllCache() {

    }
}
