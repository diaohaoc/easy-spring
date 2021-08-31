package fun.easyspring.beans.factory.support;

import cn.hutool.core.lang.Assert;
import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.ConfigurableBeanFactory;
import fun.easyspring.beans.factory.FactoryBean;
import fun.easyspring.beans.factory.NoSuchBeanDefinitionException;
import fun.easyspring.beans.factory.config.BeanDefinition;
import fun.easyspring.beans.factory.config.BeanPostProcessor;
import fun.easyspring.beans.utils.ClassUtils;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Create by DiaoHao on 2021/7/17 20:07
 */
public abstract class AbstractBeanFactory extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();

    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null, null);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return doGetBean(name, requiredType, null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, null, args);
    }

    protected <T> T doGetBean(final String beanName, Class<?> requiredType, final Object[] args) {
        Object sharedInstance = getSingleton(beanName);
        Object bean = null;
        if (sharedInstance != null) {
            bean = getObjectForBeanInstance(sharedInstance, beanName);
        } else {
            BeanDefinition beanDefinition = getBeanDefinition(beanName);
            if (beanDefinition.isSingleton()) {
                sharedInstance = this.getSingleton(beanName, () ->{
                    return this.createBean(beanName, beanDefinition, args);
                });
                bean = getObjectForBeanInstance(sharedInstance, beanName);
            } else if (beanDefinition.isPrototype()) {
                Object prototypeInstance = this.createBean(beanName, beanDefinition, args);
                bean = getObjectForBeanInstance(prototypeInstance, beanName);
            }
        }

        return (T) bean;
    }

    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        Object object = getCachedObjectForFactoryBean(beanName);

        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }
        return object;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return beanClassLoader;
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
        this.beanClassLoader = beanClassLoader;
    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        Assert.notNull(beanPostProcessor, "Bean name must not be null");
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return false;
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return null;
    }

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;
}
