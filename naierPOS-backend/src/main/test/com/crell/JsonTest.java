package com.crell;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.crell.common.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testBean() throws Exception {
        User user = new User();
        user.setUserId("1");
        user.setUserName("user1");

        String jsonString = JSON.toJSONString(user);
        System.out.println(jsonString);

        user = JSON.parseObject(jsonString,User.class);
        System.out.println(user.getUserName());
    }

    @Test
    public void testListBean() throws Exception {
        User user = new User();
        user.setUserId("1");
        user.setUserName("user1");

        User user1 = new User();
        user1.setUserId("2");
        user1.setUserName("user2");

        List<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user1);

        String jsonString = JSON.toJSONString(users);
        System.out.println(jsonString);

        List<User> users1 = JSON.parseArray(jsonString,User.class);
        System.out.println(users1.get(1).getUserName());
    }

    @Test
    public void testListString() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("fastjson1");
        list.add("fastjson2");
        list.add("fastjson3");
        String jsonString = JSON.toJSONString(list);
        System.out.println("json字符串:" + jsonString);

        //解析json字符串
        List<String> list1 = JSON.parseObject(jsonString, new TypeReference<List<String>>() {
        });
        System.out.println(list1.get(0)+"::"+list1.get(1)+"::"+list1.get(2));

        List<String> list2 = JSON.parseArray(jsonString, String.class);
        System.out.println(list1.get(0)+"::"+list1.get(1)+"::"+list1.get(2));
    }

    @Test
    public void testListMap() throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("key1", 1);
        map2.put("key2", 2);
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        list.add(map);
        list.add(map2);
        String jsonString = JSON.toJSONString(list);
        System.out.println("json字符串:" + jsonString);

        List<Map<String,Object>> list1 = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>() {});

        System.out.println("map的key1值"+list1.get(0).get("key1"));
        System.out.println("map的key2值" + list1.get(0).get("key2"));
        System.out.println("map2的key1值" + list1.get(1).get("key1"));
        System.out.println("map2的key2值"+list1.get(1).get("key2"));
    }
}