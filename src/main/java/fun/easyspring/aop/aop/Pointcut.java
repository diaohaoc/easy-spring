package fun.easyspring.aop.aop;

/**
 * Create by DiaoHao on 2021/8/26 9:43
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();
}
