package fun.easyspring.aop.aop.framework;

import fun.easyspring.aop.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * Create by DiaoHao on 2021/8/26 10:36
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advisedSupport;

    public JdkDynamicAopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), advisedSupport.getTargetSource().getTargetClass(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> chain = this.advisedSupport.getInterceptorsAndDynamicInterceptionAdvice(method, advisedSupport.getTargetSource().getTarget().getClass());
        if (chain.isEmpty()) {
            return method.invoke(advisedSupport.getTargetSource().getTarget(), args);
        } else {
            MethodInvocation invocation = new ReflectiveMethodInvocation(advisedSupport.getTargetSource().getTarget(), method, args, chain);
            return invocation.proceed();
        }

    }
}
