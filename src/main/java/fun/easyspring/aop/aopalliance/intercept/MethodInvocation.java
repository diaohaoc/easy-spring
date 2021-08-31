package fun.easyspring.aop.aopalliance.intercept;

import java.lang.reflect.Method;

/**
 * Create by DiaoHao on 2021/8/26 9:31
 */
public interface MethodInvocation extends Invocation {
    Method getMethod();
}
