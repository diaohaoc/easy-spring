package fun.easyspring.aop.aop.framework;

import fun.easyspring.aop.aop.Advisor;
import fun.easyspring.aop.aop.MethodMatcher;
import fun.easyspring.aop.aop.PointcutAdvisor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by DiaoHao on 2021/8/28 16:56
 */
public class DefaultAdvisorChainFactory implements AdvisorChainFactory {
    public DefaultAdvisorChainFactory() {
    }

    @Override
    public List<Object> getInterceptorsAndDynamicMethodMatchers(AdvisedSupport config, Method method, Class<?> targetClass) {
        Advisor[] advisors = config.getAdvisors().toArray(new Advisor[0]);
        List<Object> interceptorList = new ArrayList<>(advisors.length);
        for (int i = 0; i < advisors.length; i++) {
            Advisor advisor = advisors[i];
            if (advisor instanceof PointcutAdvisor) {
                PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                if (pointcutAdvisor.getPointcut().getClassFilter().matches(targetClass)) {
                    MethodMatcher methodMatcher = pointcutAdvisor.getPointcut().getMethodMatcher();
                    if (methodMatcher.matches(method, targetClass)) {
                        interceptorList.add(advisor.getAdvice());
                    }
                }
            }
        }
        return interceptorList;
    }
}
