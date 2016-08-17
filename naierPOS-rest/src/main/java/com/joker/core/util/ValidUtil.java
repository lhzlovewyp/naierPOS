package com.joker.core.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * 校验工具类
 * Created by crell on 2015/12/7.
 */
public class ValidUtil {

    public static boolean validCaptcha(HttpServletRequest request,String captcha){
        if(captcha == null){
            return false;
        }

        if(captcha.equals(request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY))){
            return true;
        }

        return false;
    }
}
