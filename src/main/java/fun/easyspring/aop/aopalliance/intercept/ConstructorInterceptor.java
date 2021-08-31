package fun.easyspring.aop.aopalliance.intercept;

/**
 * Create by DiaoHao on 2021/8/26 9:29
 */
public interface ConstructorInterceptor extends Interceptor{
    Object construct(ConstructorInvocation invocation) throws Throwable;
}
