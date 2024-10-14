package edu.du.sb1010.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Order(2)
public class CacheAspect {

    private Map<Long,Object> cache = new HashMap<Long,Object>();

    @Around("execution(public * *..chap07..*(..))")
    public Object cacheAround(ProceedingJoinPoint pjp) throws Throwable {
        Long num = (Long) pjp.getArgs()[0];
        if(cache.containsKey(num)){
            System.out.printf("CacheAspect: find at Cache[%d]\n",num);
            return cache.get(num);
        }
        Object result = pjp.proceed();
        cache.put(num,result);
        System.out.printf("CacheAspect: append at Cache[%d]\n",num);
        return result;
    }
}
