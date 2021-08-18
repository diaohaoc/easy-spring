package fun.easyspring.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.PropertyValue;
import fun.easyspring.beans.PropertyValues;
import fun.easyspring.beans.factory.*;
import fun.easyspring.beans.factory.config.AutowireCapableBeanFactory;
import fun.easyspring.beans.factory.config.BeanDefinition;
import fun.easyspring.beans.factory.config.BeanPostProcessor;
import fun.easyspring.beans.factory.config.BeanReference;
import fun.easyspring.beans.utils.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 *
 * 定义 bean 的自动装配的相关功能
 *
 * Create by DiaoHao on 2021/7/17 20:15
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {

        Object bean = null;

        try {
            bean = createBeanInstantiation(beanDefinition, beanName, args);
            // 填充属性信息
            populateBean(beanName, bean, beanDefinition);

            // 执行 bean 的初始化方法和 前置后置处理器
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 如果该 bean 为 DisposableBean，则将其注册到 DisposableBean 容器中
        this.registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

        if (beanDefinition.isSingleton()) {
            registerSingleton(beanName, bean);
        }

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

    protected void populateBean(String beanName, Object bean, BeanDefinition beanDefinition) {
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

    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
        }


        Object wrappedBean = applyBeanPostProcessorBeforeInitialization(bean, beanName);

        try {
            invokeInitializeBean(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName +"] failed", e);
        }

        wrappedBean = applyBeanPostProcessorAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    private void invokeInitializeBean(String beanName, Object wrappedBean, BeanDefinition beanDefinition) throws Exception {
        boolean isInitializingBean = wrappedBean instanceof InitializingBean;
        if (isInitializingBean) {
            ((InitializingBean) wrappedBean).afterPropertiesSet();
        }

        if (beanDefinition != null) {
            String initMethodName = beanDefinition.getInitMethodName();
            // 可以防止调用两次初始化函数
            if (!StringUtils.isEmpty(initMethodName) && !isInitializingBean) {
                Method initMethod = wrappedBean.getClass().getMethod(initMethodName);
                initMethod.invoke(wrappedBean);
            }
        }
    }

    private Object applyBeanPostProcessorBeforeInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessorBeforeInitialization(result, beanName);
            if (null == current) {
                return result;
            }
            result = current;
        }
        return result;
    }

    private Object applyBeanPostProcessorAfterInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanPostProcessor beanPostProcessor : getBeanPostProcessors()) {
            Object current = beanPostProcessor.postProcessorAfterInitialization(result, beanName);
            if (null == current) {
                return result;
            }
            result = current;
        }
        return result;
    }

    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (!beanDefinition.isSingleton()) {
            return;
        }

        if (bean instanceof DisposableBean || !StringUtils.isEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    private void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }
}
