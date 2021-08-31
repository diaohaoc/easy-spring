package fun.easyspring.aop.aop;


import java.lang.reflect.Method;

/**
 * Create by DiaoHao on 2021/8/26 16:12
 */
public interface AfterReturningAdvice extends AfterAdvice {
    /**
     * 目标方法调用完成之后调用
     *
     * @param retVal    拦截器链上一个拦截器完成之后的返回值
     * @param method    目标方法
     * @param args      目标方法的参数
     * @param target    目标对象
     * @return
     * @throws Throwable
     */
    Object afterReturning(Object retVal, Method method, Object[] args, Object target) throws Throwable;
}
