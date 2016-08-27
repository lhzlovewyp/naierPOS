package com.joker.core.util;


import org.springframework.stereotype.Component;

import com.joker.common.model.Account;


@Component("SystemUtil")
public class SystemUtil {
	
	private static ThreadLocal<Account> local = new ThreadLocal<Account>();
	
	public static void setUser(Account user){
        local.set(user);
    }
    
    public static void removeUser(){
        local.remove();
    }
    
    public static Account getUser(){
        return (Account)local.get();
    }
    
}
