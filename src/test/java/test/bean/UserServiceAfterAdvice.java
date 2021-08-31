package test.bean;

import fun.easyspring.aop.aop.AfterReturningAdvice;
import fun.easyspring.aop.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Create by DiaoHao on 2021/8/26 19:33
 */
public class UserServiceAfterAdvice implements AfterReturningAdvice {

    @Override
    public Object afterReturning(Object retVal, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("afterReturning 拦截方法: " + method.getName());
        return retVal;
    }
}
