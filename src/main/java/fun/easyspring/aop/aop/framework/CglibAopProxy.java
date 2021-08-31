package fun.easyspring.aop.aop.framework;

import fun.easyspring.aop.aop.Advisor;
import fun.easyspring.aop.aop.TargetSource;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Create by DiaoHao on 2021/8/26 14:24
 */
public class CglibAopProxy implements AopProxy {

    private final AdvisedSupport config;

    public CglibAopProxy(AdvisedSupport config) {
        this.config = config;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        // cglib 不允许重复代理， 通过这种方式获取原生的被代理类，从而间接实现重复代理的功能
        Class<?> rootClass = this.config.getTargetSource().getTarget().getClass();
        Class<?> proxySuperClass = rootClass;
        if (rootClass.getName().contains("$$")) {
            proxySuperClass = rootClass.getSuperclass();
        }

        enhancer.setSuperclass(proxySuperClass);
        enhancer.setInterfaces(config.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(config));
        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {

        private final AdvisedSupport config;

        public DynamicAdvisedInterceptor(AdvisedSupport config) {
            this.config = config;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            TargetSource targetSource = this.config.getTargetSource();
            List<Object> chain = this.config.getInterceptorsAndDynamicInterceptionAdvice(method, targetSource.getTarget().getClass());
            if (chain.isEmpty()) {
                return methodProxy.invoke(targetSource.getTarget(), objects);
            } else {
                CglibMethodInvocation methodInvocation = new CglibMethodInvocation(config.getTargetSource().getTarget(), method, objects, methodProxy, chain);
                return methodInvocation.proceed();
            }
        }
    }

    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        private final MethodProxy methodProxy;

        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy, List<Object> interceptorsAndDynamicMethodMatchers) {
            super(target, method, arguments, interceptorsAndDynamicMethodMatchers);
            this.methodProxy = methodProxy;
        }

        @Override
        public Object proceed() throws Throwable {
            return super.proceed();
        }
    }
}
