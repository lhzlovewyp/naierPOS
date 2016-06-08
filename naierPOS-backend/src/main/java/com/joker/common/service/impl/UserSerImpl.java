package com.joker.common.service.impl;

import com.joker.common.mapper.UserMapper;
import com.joker.common.model.User;
import com.joker.common.service.UserSer;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * Created by crell on 2016/1/19.
 */
@Service
public class UserSerImpl implements UserSer {
    @Autowired
    UserMapper mapper;

    @Autowired
    RedisTemplate redis;

    public User selectByName(String userName) {
        return mapper.selectByName(userName);
    }

    public int updateToken(String userId,String token) {
        return mapper.updateToken(userId, token);
    }

    public User selectByNickName(String nickName) {
        return mapper.selectByNickName(nickName);
    }

    public int addUser(User user){
        int i = mapper.insertUser(user);

        BoundHashOperations<String, String, String> userToken = redis.boundHashOps("userToken");
        Date date = DateUtils.addDays(new Date(), 1);
        userToken.put(user.getToken(),user.getUserId() + "," + date.getTime());
        return i;
    }

    public Boolean update(User user) {
        return mapper.update(user);
    }

    public Boolean del(String userId) {
        return mapper.del(userId);
    }

    public User login(User user){
        mapper.setLoginInfo(user);

        BoundHashOperations<String, String, String> userToken = redis.boundHashOps("userToken");
        Date date = DateUtils.addDays(new Date(), 1);
        userToken.put(user.getToken(),user.getUserId() + "," + date.getTime());
        return user;
    }

    public Boolean logout(String token){
        BoundHashOperations<String, String, String> userToken = redis.boundHashOps("userToken");
        userToken.delete(token);
        return true;
    }

    public User selectByToken(String token){
        BoundHashOperations<String, String, String> userToken = redis.boundHashOps("userToken");
        String value = userToken.get(token);
        String userId = "";
        User user;

        if(StringUtils.isEmpty(value)){
            return null;
        }else{
            userId = value.split(",")[0];
            user = selectById(userId);
        }
        return user;
    }

    public User selectById(String userId) {
        return mapper.selectById(userId);
    }
}
