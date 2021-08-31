package fun.easyspring.aop.aopalliance.intercept;

/**
 * Create by DiaoHao on 2021/8/26 9:29
 */
@FunctionalInterface
public interface MethodInterceptor extends Interceptor{
    Object invoke(MethodInvocation invocation) throws Throwable;
}
