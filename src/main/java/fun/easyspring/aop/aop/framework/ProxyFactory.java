package fun.easyspring.aop.aop.framework;

import cn.hutool.core.lang.Assert;

/**
 * Create by DiaoHao on 2021/8/26 16:57
 */
public class ProxyFactory {
    private AdvisedSupport advisedSupport;
    private AopProxyFactory aopProxyFactory;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
        this.aopProxyFactory = new DefaultAopProxyFactory();
    }

    public Object getProxy() {
        return this.getAopProxyFactory().createAopProxy(advisedSupport).getProxy();
    }

    public AopProxyFactory getAopProxyFactory() {
        return aopProxyFactory;
    }

    public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
        Assert.notNull("AopProxyFactory must not be null");
        this.aopProxyFactory = aopProxyFactory;
    }
}
