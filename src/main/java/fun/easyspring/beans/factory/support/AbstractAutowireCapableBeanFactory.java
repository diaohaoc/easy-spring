package fun.easyspring.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.PropertyValue;
import fun.easyspring.beans.PropertyValues;
import fun.easyspring.beans.factory.AbstractBeanFactory;
import fun.easyspring.beans.factory.CglibSubclassInstantiationStrategy;
import fun.easyspring.beans.factory.InstantiationStrategy;
import fun.easyspring.beans.factory.config.BeanDefinition;
import fun.easyspring.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;

/**
 *
 * 定义 bean 的自动装配的相关功能
 *
 * Create by DiaoHao on 2021/7/17 20:15
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {

        Object bean = null;

        try {
            bean = createBeanInstantiation(beanDefinition, beanName, args);
            // 填充属性信息
            addPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        registerSingleton(beanName, bean);

        return bean;
    }

    protected Object createBeanInstantiation(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor<?> constructorToUse = null;
        Class<?> clz = beanDefinition.getBeanClass();
        Constructor<?>[] constructors = clz.getDeclaredConstructors();
        for (Constructor<?> ctor : constructors) {

            // todo 需要更复杂的参数判断
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
        return getInstantiationStrategy().instantiation(beanDefinition, beanName, constructorToUse, args);
    }

    protected void addPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues pvs = beanDefinition.getPropertyValues();
            for (PropertyValue pv : pvs.getPropertyValues()) {
                String name = pv.getName();
                Object value = pv.getValue();

                if (value instanceof BeanReference) {
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                BeanUtil.setFieldValue(bean, name, value);

            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values", e);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    private void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
