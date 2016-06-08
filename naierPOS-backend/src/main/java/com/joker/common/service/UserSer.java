package com.joker.common.service;


import com.joker.common.model.User;

/**
 * Created by Administrator on 2015/4/17.
 */
public interface UserSer {

    public User selectByName(String userName);

    public User selectByNickName(String nickName);

    public int updateToken(String userId, String token);

    public int addUser(User user);

    public Boolean update(User user);

    public Boolean del(String userId);

    public User login(User user);

    public Boolean logout(String token);

    public User selectByToken(String token);

    public User selectById(String userId);
}
