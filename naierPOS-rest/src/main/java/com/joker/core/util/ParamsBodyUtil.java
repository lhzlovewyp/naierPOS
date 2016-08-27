package com.joker.core.util;

import com.joker.core.dto.Page;
import com.joker.core.dto.ParamsBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 获取GET请求ParamsBody
 * Created by crell on 2016/4/17.
 */
public class ParamsBodyUtil {

    public static ParamsBody getBody(HttpServletRequest request) throws Exception {
        Class<ParamsBody> clazz = ParamsBody.class;
        ParamsBody bean = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        String fieldName = null;
        Object value = null;
        String letter = null;
        String setName = null;
        Method setMethod = null;
        Map<String,Object> body = new HashMap<String,Object>();
        Page page = new Page();
        Class<?> bodyType = null;
        Class<?> pageType = null;
        Map<String, String[]> bodyParam = request.getParameterMap();
        for(Map.Entry<String, String[]> entry : bodyParam.entrySet()){
            value = entry.getValue()[0];
            for(int i=0; i<fields.length; i++){
                fieldName = fields[i].getName();
                if(fieldName.equals(entry.getKey())){
                    letter = fieldName.substring(0, 1).toUpperCase();
                    setName = "set" + letter + fieldName.substring(1);
                    setMethod = clazz.getMethod(setName, new Class[]{ fields[i].getType() });

                    setMethod.invoke(bean, new Object[]{ value });
                }else if(bodyType==null && fieldName.equals("body")){
                    bodyType = fields[i].getType();
                }else if(bodyType==null && fieldName.equals("page")){
                    pageType = fields[i].getType();
                }
            }

            if(entry.getKey().indexOf("body[")!=-1 && entry.getKey().indexOf("]")!=-1){
                body.put(entry.getKey().substring(entry.getKey().indexOf("[")+1,entry.getKey().indexOf("]")),value);
            }else if(entry.getKey().indexOf("page[")!=-1 && entry.getKey().indexOf("]")!=-1){
                String pageKey = entry.getKey().substring(entry.getKey().indexOf("[")+1,entry.getKey().indexOf("]"));
                if("pageNo".equals(pageKey)){
                    page.setPageNo(Integer.parseInt(value.toString()));
                }else if("pageSize".equals(pageKey)){
                    page.setPageSize(Integer.parseInt(value.toString()));
                }
            }
        }
        setMethod = clazz.getMethod("setBody", new Class[]{ bodyType });
        setMethod.invoke(bean, new Object[]{ body });

        setMethod = clazz.getMethod("setPage", new Class[]{ pageType });
        setMethod.invoke(bean, new Object[]{ page });
        return bean;
    }

}
