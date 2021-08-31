package fun.easyspring.aop.aopalliance.intercept;

import java.lang.reflect.Constructor;

/**
 * Create by DiaoHao on 2021/8/26 9:37
 */
public interface ConstructorInvocation extends Invocation {
    Constructor<?> getConstructor();
}
