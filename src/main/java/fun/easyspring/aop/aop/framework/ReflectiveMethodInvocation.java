package fun.easyspring.aop.aop.framework;

import fun.easyspring.aop.aop.aspectj.AspectJExpressionPointcutAdvisor;
import fun.easyspring.aop.aopalliance.intercept.MethodInterceptor;
import fun.easyspring.aop.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Create by DiaoHao on 2021/8/26 10:40
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

    // 目标对象
    protected final Object target;

    // 目标方法
    protected final Method method;

    // 入参
    protected Object[] arguments;

    protected final List<?> interceptorsAndDynamicMethodMatchers;
    private int currentInterceptorsIndex = -1;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments, List<?> interceptorsAndDynamicMethodMatchers) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Object proceed() throws Throwable {
        if (this.currentInterceptorsIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return method.invoke(target, arguments);
        } else {
            Object interceptorOrInterceptionAdvice = interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorsIndex);
            return ((MethodInterceptor)interceptorOrInterceptionAdvice).invoke(this);
        }
    }

    @Override
    public Object getThis() {
        return target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}
