package fun.easyspring.aop.aop;

/**
 * Create by DiaoHao on 2021/8/26 16:38
 */
public interface PointcutAdvisor extends Advisor {
    Pointcut getPointcut();
}
