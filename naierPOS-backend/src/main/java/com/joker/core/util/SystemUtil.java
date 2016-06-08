package com.joker.core.util;

import com.joker.common.model.User;

import org.springframework.stereotype.Component;

@Component("SystemUtil")
public class SystemUtil {
	
	private static ThreadLocal<User> local = new ThreadLocal<User>();
	
	public static void setUser(User user){
        local.set(user);
    }
    
    public static void removeUser(){
        local.remove();
    }
    
    public static User getUser(){
        return (User)local.get();
    }
    
}
