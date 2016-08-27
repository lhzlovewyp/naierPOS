package com.joker.core.aspect;

import com.joker.core.annotation.DataSource;
import com.joker.core.interceptor.DynamicDataSourceHolder;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切面，数据库数据源配置
 *
 * @Author crell
 * @Date 2016/5/17 10:52

@Component("dataSourceAspect")
@Aspect
 */
public class DataSourceAspect {

    //定义切入点
    @SuppressWarnings("unused")
    @Pointcut("execution(* com.joker.common.mapper.*.*(..))")
    private  void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint point)
    {
        Object target = point.getTarget();
        String method = point.getSignature().getName();

        Class<?>[] classz = target.getClass().getInterfaces();

        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
                .getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource data = m.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.putDataSource(data.value());
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
