package com.joker.common.test;

import com.joker.common.dto.LoginForm;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Administrator on 2016/3/8.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        int [] intArray1 = { 2, 4, 8, 16 };

        int [][] intArray2 = { { 1, 2 }, { 2, 4 }, { 3, 8}, { 4, 16 } };

        System.out.println( "intArray1: " + intArray1.toString());

        System.out.println("intArray1: " + ArrayUtils.toString(intArray1));

        System.out.println("intArray2: " + ArrayUtils.toString(intArray2));

        String str1 = null ;

        String str2 = "123" ;

        System.out.println( "Is str1 blank? " + StringUtils.isNotBlank(str1));

        System.out.println("Is str2 numeric? " + StringUtils.isNumeric(str2));

        LoginForm login = new LoginForm();
        login.setUserName("chenqi");
        login.setPassword("123456");

        LoginForm login2 =  (LoginForm) BeanUtils.cloneBean(login);
        System.out.println(login.getUserName() + ">>" + login.getPassword());

        Map map = new HashMap();
        map.put("userName", "chen");
        map.put("password", "654321");
        //将map转化为一个LoginForm对象
        LoginForm login3 = new LoginForm();
        BeanUtils.populate(login3, map);
        System.out.println(login3.getUserName() + "<<" + login3.getPassword());

        List<String> list1 = new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        List<String> list2 = new ArrayList<String>();
        list2.add("2");
        list2.add("3");
        Collection c = CollectionUtils.retainAll(list1, list2);
        System.out.println(c);
        System.out.println(CollectionUtils.isEmpty(list1));

    }
}
