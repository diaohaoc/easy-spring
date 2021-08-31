package fun.easyspring.aop.aop.framework.adapt;

import cn.hutool.core.lang.Assert;
import fun.easyspring.aop.aop.AfterAdvice;
import fun.easyspring.aop.aop.AfterReturningAdvice;
import fun.easyspring.aop.aopalliance.intercept.MethodInterceptor;
import fun.easyspring.aop.aopalliance.intercept.MethodInvocation;

/**
 * Create by DiaoHao on 2021/8/26 16:34
 */
public class AfterReturningAdviceInterceptor implements MethodInterceptor, AfterAdvice {
    private AfterReturningAdvice advice;

    public AfterReturningAdviceInterceptor() {
    }

    public AfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        Assert.notNull("Advice must not be null");
        this.advice = advice;
    }

    public void setAdvice(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object retVal = invocation.proceed();
        this.advice.afterReturning(retVal, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return retVal;
    }
}
