package fun.easyspring.aop.aop.framework;

import fun.easyspring.aop.aop.Advisor;
import fun.easyspring.aop.aop.MethodMatcher;
import fun.easyspring.aop.aop.TargetSource;
import fun.easyspring.aop.aopalliance.intercept.MethodInterceptor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create by DiaoHao on 2021/8/26 10:30
 */
public class AdvisedSupport {

    private TargetSource targetSource;

    private MethodInterceptor methodInterceptor;

    private MethodMatcher methodMatcher;

    AdvisorChainFactory advisorChainFactory;

    private List<Advisor> advisors;

    // proxyconfig 相关配置  与动态代理的选择有关，默认为 jdk 代理
    private boolean proxyTargetClass;

    public AdvisedSupport() {
        advisorChainFactory = new DefaultAdvisorChainFactory();
        this.advisors = new ArrayList<>();
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        return this.advisorChainFactory.getInterceptorsAndDynamicMethodMatchers(this, method, targetClass);
    }

    public AdvisorChainFactory getAdvisorChainFactory() {
        return advisorChainFactory;
    }

    public void setAdvisorChainFactory(AdvisorChainFactory advisorChainFactory) {
        this.advisorChainFactory = advisorChainFactory;
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    public List<Advisor> getAdvisors() {
        return advisors;
    }

    public void addAdvisor(Advisor advisor) {
        this.advisors.add(advisor);
    }

    public void addAdvisors(Advisor... advisors) {
        this.advisors.addAll(Arrays.asList(advisors));
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }
}
