package com.joker.core.util;


import org.springframework.stereotype.Component;

import com.joker.common.model.SaleUser;

@Component("SystemUtil")
public class SystemUtil {
	
	private static ThreadLocal<SaleUser> local = new ThreadLocal<SaleUser>();
	
	public static void setUser(SaleUser user){
        local.set(user);
    }
    
    public static void removeUser(){
        local.remove();
    }
    
    public static SaleUser getUser(){
        return (SaleUser)local.get();
    }
    
}
