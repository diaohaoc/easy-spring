package fun.easyspring.aop.aop.framework.adapt;

import cn.hutool.core.lang.Assert;
import fun.easyspring.aop.aop.BeforeAdvice;
import fun.easyspring.aop.aop.MethodBeforeAdvice;
import fun.easyspring.aop.aopalliance.intercept.MethodInterceptor;
import fun.easyspring.aop.aopalliance.intercept.MethodInvocation;

/**
 * Create by DiaoHao on 2021/8/26 16:27
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor, BeforeAdvice {

    private MethodBeforeAdvice advice;

    public MethodBeforeAdviceInterceptor() {
    }

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        Assert.notNull("Advice must not be null");
        this.advice = advice;
    }

    public void setAdvice(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        this.advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
