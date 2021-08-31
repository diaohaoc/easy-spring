package fun.easyspring.aop.aop.framework;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Create by DiaoHao on 2021/8/28 16:54
 */
public interface AdvisorChainFactory {
    List<Object> getInterceptorsAndDynamicMethodMatchers(AdvisedSupport config, Method method, Class<?> targetClass);
}
