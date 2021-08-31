package fun.easyspring.aop.aop.framework;

/**
 * Create by DiaoHao on 2021/8/27 15:55
 */
public class DefaultAopProxyFactory implements AopProxyFactory {
    public DefaultAopProxyFactory() {
    }

    @Override
    public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
        if (config.isProxyTargetClass()) {
            return new CglibAopProxy(config);
        }
        return new JdkDynamicAopProxy(config);
    }
}
