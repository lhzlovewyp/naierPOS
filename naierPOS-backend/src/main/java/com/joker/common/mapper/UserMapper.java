package com.joker.common.mapper;

import com.joker.common.model.User;
import com.joker.core.annotation.DataSource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户Mapper
 * Created by crell on 2016/1/17.
 */
@Repository
public interface UserMapper {

    /**
     * 根据用户名获取用户
     * @param userName
     * @return User
     */
    @DataSource("slave")
    User selectByName(String userName);

    /**
     * 根据昵称获取用户
     * @param nickName
     * @return User
     */
    @DataSource("slave")
    User selectByNickName(String nickName);

    /**
     * 用户登录后更新用户token
     * @param userId
     * @param token
     * @return
     */
    @DataSource("master")
    int updateToken(@Param("userId") String userId, @Param("token") String token);

    /**
     * 插入用户
     * @param user
     * @return int
     */
    @DataSource("master")
    int insertUser(User user);

    @DataSource("master")
    Boolean update(User user);

    @DataSource("master")
    Boolean del(@Param("userId") String userId);

    /**
     * 根据用户id获取用户
     * @param userId
     * @return User
     */
    @DataSource("slave")
    User selectById(String userId);

    /**
     * 保存登录信息
     * @param user
     * @return Boolean
     */
    @DataSource("master")
    Boolean setLoginInfo(User user);
}
