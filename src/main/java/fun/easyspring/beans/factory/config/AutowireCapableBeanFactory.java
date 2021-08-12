package fun.easyspring.beans.factory.config;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.BeanFactory;
import fun.easyspring.beans.factory.BeanPostProcessor;

/**
 * 该接口提供了用于整合其他框架的能力，让 easy-spring 框架去管理不被框架管理的bean
 * 该接口的定义提高了 easy-spring 的扩展性
 *
 * Create by DiaoHao on 2021/7/16 16:43
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 根据给定的类完全创建一个 bean 实例
     *
     * 执行 bean 的完全初始化，包括所有可用的
     * {@link BeanPostProcessor BeanPostProcessors}
     *
     *
     * @param beanClass
     * @param <T>
     * @return
     */
    <T> T createBean(Class<T> beanClass) throws BeansException;

}
