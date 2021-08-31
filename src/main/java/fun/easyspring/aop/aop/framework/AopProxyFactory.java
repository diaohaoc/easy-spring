package fun.easyspring.aop.aop.framework;

/**
 * Create by DiaoHao on 2021/8/27 15:52
 */
public interface AopProxyFactory {
    AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException;
}
