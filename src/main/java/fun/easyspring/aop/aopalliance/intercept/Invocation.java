package fun.easyspring.aop.aopalliance.intercept;

/**
 * Create by DiaoHao on 2021/8/26 9:26
 */
public interface Invocation extends Joinpoint {
    Object[] getArguments();
}
