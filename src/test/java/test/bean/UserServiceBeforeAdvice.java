package test.bean;

import fun.easyspring.aop.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Create by DiaoHao on 2021/8/26 19:33
 */
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("before 拦截方法: " + method.getName());
    }
}
