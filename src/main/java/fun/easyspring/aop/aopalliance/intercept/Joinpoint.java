package fun.easyspring.aop.aopalliance.intercept;

import java.lang.reflect.AccessibleObject;

/**
 * Create by DiaoHao on 2021/8/26 9:24
 */
public interface Joinpoint {
    Object proceed() throws Throwable;

    Object getThis();

    AccessibleObject getStaticPart();
}
