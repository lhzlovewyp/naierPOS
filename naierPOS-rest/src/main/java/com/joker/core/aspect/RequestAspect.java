package com.joker.core.aspect;

import com.joker.common.model.Account;
import com.joker.core.annotation.BodyFormat;
import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.dto.ParamsBody;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.LogUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 切面，对RequestMapping所有请求切入
 * 判断是否有效用户、参数值是否为空
 *
 * @Author crell
 * @Date 2016/1/1
 */
@Component("requestAspect")
@Aspect
public class RequestAspect {


    //Logger logger = LoggerFactory.getLogger(this.getClass());

    //定义切入点
    @SuppressWarnings("unused")
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) ")
    private  void pointCut(){}

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        ParamsBody paramsBody = null;
        Map<String,Object> params = null;
        for (int j = 0; j < args.length; j++) {
            if(args[j] instanceof ParamsBody){
                paramsBody = (ParamsBody)args[j];
            }
        }

        Method method = ((MethodSignature)pjp.getSignature()).getMethod();
        Annotation[] annotations = method.getAnnotations();

        for (Annotation annotation : annotations) {
            Class<? extends Annotation> type = annotation.annotationType();
            if(paramsBody != null) params = paramsBody.getBody();
            if(BodyFormat.class.equals(type) && params != null){
                BodyFormat ann = (BodyFormat)annotation;
                String value = ann.value();
                String[] fields = value.split(",");
                Map<String,Object> formatParams = new HashMap<String, Object>();
                for (String filed : fields) {
                    if(params.get(filed) != null) formatParams.put(filed,params.get(filed));
                }
                paramsBody.setBody(formatParams);
            }
            else if(NotNull.class.equals(type)){
                NotNull ann = (NotNull)annotation;
                String value = ann.value();
                boolean user = ann.user();
                ReturnBody returnbody = new ReturnBody();

                if(user){
                    if(StringUtils.isEmpty(paramsBody.getToken())){
                        returnbody.setStatus(ResponseState.INVALID_TOKEN);
                        returnbody.setMsg( "入参token为空");
                        LogUtil.error(method.getName() + "入参token为空");
                        return returnbody;
                    }else if(StringUtils.isEmpty(paramsBody.getTimestamp())){
                        returnbody.setStatus(ResponseState.INVALID_TOKEN);
                        returnbody.setMsg( "入参timestamp为空");
                        LogUtil.error(method.getName() + "入参timestamp为空");
                        return returnbody;
                    }else{
                        
                    }
                }

                String[] fields = value.split(",");
                Object keyValue = null;
                if(params != null){
                    for (String filed : fields) {
                        keyValue = params.get(filed);
                        if(StringUtils.isEmpty(keyValue)){
                        	if(filed.equals("token")){
                        		returnbody.setStatus(ResponseState.INVALID_TOKEN);
                        	}else{
                        		returnbody.setStatus(ResponseState.FAILED);
                        	}
                            returnbody.setMsg("入参" + filed + "值为空");
                            LogUtil.error(method.getName() + "入参" + filed + "值为空");
                            return returnbody;
                        }
                    }
                }

//                Class clazz = ParamsBody.class;
//                String getName = null;
//                Method getMethod = null;
//                Object keyValue = null;
//                for (int j = 0; j < values.length; j++) {
//                    getName = "get" + values[j].substring(0, 1).toUpperCase() + values[j].substring(1);
//                    getMethod = clazz.getMethod(getName, new Class[] {});
//                    keyValue = getMethod.invoke(paramsBody, new Object[] {});
//                    if(StringUtils.isEmpty(keyValue)){
//                        logger.error(method.getName() + "入参" + values[j] + "值为空");
//                        return null;
//                    }
//                }
            }
        }
        return pjp.proceed();
    }

}
