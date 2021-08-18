package fun.easyspring.beans.factory.config;

import fun.easyspring.beans.BeansException;

/**
 * Create by DiaoHao on 2021/7/17 19:57
 */
public interface BeanPostProcessor {

    /**
     *  在 bean 对象初始化之前执行
     *
     * @param bean      指定的 bean
     * @param beanName  指定的 beanName
     * @return
     * @throws BeansException
     */
    default Object postProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     *  在 bean 对象初始化之后执行
     *
     * @param bean      指定的 bean
     * @param beanName  指定的 beanName
     * @return
     * @throws BeansException
     */
    default Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
