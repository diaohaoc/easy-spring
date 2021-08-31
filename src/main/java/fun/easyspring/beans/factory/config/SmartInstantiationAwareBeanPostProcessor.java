package fun.easyspring.beans.factory.config;


import fun.easyspring.beans.BeansException;

/**
 * Create by DiaoHao on 2021/8/26 16:49
 */
public interface SmartInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessor {

    default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
