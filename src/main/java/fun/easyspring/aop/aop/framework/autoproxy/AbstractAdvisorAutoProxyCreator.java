package fun.easyspring.aop.aop.framework.autoproxy;

import cn.hutool.core.bean.BeanException;
import fun.easyspring.aop.aop.Advisor;
import fun.easyspring.aop.aop.Pointcut;
import fun.easyspring.aop.aop.TargetSource;
import fun.easyspring.aop.aop.framework.AdvisedSupport;
import fun.easyspring.aop.aop.framework.ProxyFactory;
import fun.easyspring.aop.aopalliance.aop.Advice;
import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.BeanFactory;
import fun.easyspring.beans.factory.BeanFactoryAware;
import fun.easyspring.beans.factory.FactoryBean;
import fun.easyspring.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import fun.easyspring.beans.utils.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Create by DiaoHao on 2021/8/28 9:57
 */
public abstract class AbstractAdvisorAutoProxyCreator implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {
    protected static final Object[] DO_NOT_PROXY = null;
    private BeanFactory beanFactory;
    // 与被代理类的循环依赖相关
    private Map<Object, Object> earlyProxyReferences = new ConcurrentHashMap<>(16);

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        Object cacheKey = this.getCacheKey(bean.getClass(), beanName);
        this.earlyProxyReferences.put(cacheKey, bean);
        return this.wrapIfNecessary(bean, beanName, cacheKey);
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
        if (isInfrastructureClass(beanClass)) {
            return null;
        }

        TargetSource targetSource = getCustomTargetSource(beanClass, beanName);
        if (targetSource != null) {
            // 查找适合的拦截器，并且经过排序
            Object[] specificInterceptors = this.getAdvicesAndAdvisorsForBean(beanClass, beanName);
            Object proxy = createProxy(beanClass, beanName, specificInterceptors, targetSource);
            return proxy;
        } else {
            return null;
        }
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public Object postProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean != null) {
            Object cacheKey = getCacheKey(bean.getClass(), beanName);
            if (this.earlyProxyReferences.remove(cacheKey) != bean) {
                return this.wrapIfNecessary(bean, beanName, cacheKey);
            }
        }
        return bean;
    }

    protected Object getCacheKey(Class<?> beanClass, String beanName) {
        if (StringUtils.hasLength(beanName)) {
            return FactoryBean.class.isAssignableFrom(beanClass) ? "&" + beanName : beanName;
        } else {
            return beanClass;
        }
    }

    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {
        if (!isInfrastructureClass(bean.getClass())) {
            Object[] specificInterceptors = this.getAdvicesAndAdvisorsForBean(bean.getClass(), beanName);
            if (specificInterceptors != DO_NOT_PROXY) {
                Object proxy = createProxy(bean.getClass(), beanName, specificInterceptors, new TargetSource(bean));
                return proxy;
            }
        }
        return bean;
    }

    /**
     * 判断是否为aop的基础组件类, 如果是基础组件，则不需要进行代理增强
     * @param beanClass
     * @return
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass) || Pointcut.class.isAssignableFrom(beanClass) || Advisor.class.isAssignableFrom(beanClass);
    }

    /**
     * 用于自定义 targetSource， 否则返回空
     * 暂时没有此功能
     *
     * @param beanClass
     * @param beanName
     * @return
     */
    protected TargetSource getCustomTargetSource(Class<?> beanClass, String beanName) {
        return null;
    }

    protected Object createProxy(Class<?> beanClass, String beanName, Object[] specificInterceptors, TargetSource targetSource) {
        Advisor[] advisors = this.buildAdvisor(beanName, specificInterceptors);
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setProxyTargetClass(true);
        advisedSupport.addAdvisors(advisors);
        return new ProxyFactory(advisedSupport).getProxy();
    }

    private Advisor[] buildAdvisor(String beanName, Object[] specificInterceptors) {
        Advisor[] advisors = new Advisor[specificInterceptors.length];
        for (int i = 0; i < specificInterceptors.length; i++) {
            advisors[i] = wrap(specificInterceptors[i]);
        }
        return advisors;
    }

    private Advisor wrap(Object adviceObject) {
//        if (adviceObject instanceof Advisor) {
//            return (Advisor) adviceObject;
//        } else {
//            // todo 需要对 methodInterceptor 进行包装，当前xml的解析较为简单，因此暂时不需要考虑
//        }
        return (Advisor) adviceObject;
    }

    protected abstract Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName);

}
