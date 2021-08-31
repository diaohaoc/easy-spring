package fun.easyspring.aop.aop;

import java.lang.reflect.Method;

/**
 * Create by DiaoHao on 2021/8/26 16:16
 */
public interface MethodBeforeAdvice extends BeforeAdvice {
    /**
     * 调用目标方法前调用
     *
     * @param method    目标方法
     * @param args      目标方法的参数
     * @param target    目标对象
     * @throws Throwable
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
