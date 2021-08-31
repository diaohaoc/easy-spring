package fun.easyspring.aop.aop;

import java.lang.reflect.Method;

/**
 * Create by DiaoHao on 2021/8/26 9:42
 */
public interface MethodMatcher {
    boolean matches(Method method, Class<?> clazz);
}
