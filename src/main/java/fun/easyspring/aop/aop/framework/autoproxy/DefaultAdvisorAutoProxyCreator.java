package fun.easyspring.aop.aop.framework.autoproxy;

import cn.hutool.core.lang.Assert;
import fun.easyspring.aop.aop.*;
import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.BeanFactory;
import fun.easyspring.beans.factory.config.ConfigurableListableBeanFactory;


import java.lang.reflect.Method;
import java.util.*;

/**
 * Create by DiaoHao on 2021/8/26 17:02
 */
public class DefaultAdvisorAutoProxyCreator extends AbstractAdvisorAutoProxyCreator {
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName) {
        List<Advisor> advisors = this.findEligibleAdvisors(beanClass, beanName);
        return advisors.isEmpty() ? DO_NOT_PROXY : advisors.toArray();
    }

    protected List<Advisor> findEligibleAdvisors(Class<?> beanClass, String beanName) {
        List<Advisor> candidateAdvisors = this.findCandidateAdvisors();
        List<Advisor> eligibleAdvisors = this.findAdvisorsThatCanApply(candidateAdvisors, beanClass, beanName);

        // todo 这里还需要做一下排序拦截器的排序

        return eligibleAdvisors;
    }

    protected List<Advisor> findCandidateAdvisors() {
        // ((ConfigurableListableBeanFactory)getBeanFactory()).getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        String[] beanNamesForType = this.beanFactory.getBeanNamesForType(Advisor.class);
        if (beanNamesForType.length == 0) {
            return new ArrayList<>();
        } else {
            List<Advisor> advisors = new ArrayList<>();
            for (String beanName : beanNamesForType) {
                advisors.add(this.beanFactory.getBean(beanName, Advisor.class));
            }
            return advisors;
        }
    }

    protected List<Advisor> findAdvisorsThatCanApply(List<Advisor> candidateAdvisors, Class<?> targetClass, String beanName) {
        if (candidateAdvisors.isEmpty()) {
            return candidateAdvisors;
        }

        List<Advisor> eligibleAdvisor = new ArrayList<>();
        for (Advisor candidateAdvisor : candidateAdvisors) {
            if (canApply(candidateAdvisor, targetClass)) {
                eligibleAdvisor.add(candidateAdvisor);
            }
        }
        return eligibleAdvisor;
    }

    private boolean canApply(Advisor advisor, Class<?> targetClass) {
        if (advisor instanceof PointcutAdvisor) {
            PointcutAdvisor pca = (PointcutAdvisor) advisor;
            return canApply(pca.getPointcut(), targetClass);
        } else {
            return true;
        }
    }

    private boolean canApply(Pointcut pc, Class<?> targetClass) {
        Assert.notNull("Pointcut must not be null");
        // 先对 class 进行匹配
        if (!pc.getClassFilter().matches(targetClass)) {
            return false;
        }

        MethodMatcher methodMatcher = pc.getMethodMatcher();

        // todo 获取从父类继承的方法
        // Set<Class<?>> classes = new LinkedHashSet<>();

        // todo 暂时只实现该类所有的 public 方法
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (methodMatcher.matches(method, targetClass)) {
                return true;
            }
        }

        return false;
    }

}
