package fun.easyspring.beans.factory.support;

import fun.easyspring.beans.factory.NoSuchBeanDefinitionException;
import fun.easyspring.beans.factory.config.BeanDefinition;

/**
 * Create by DiaoHao on 2021/7/17 20:44
 */
public interface BeanDefinitionRegistry {

    /**
     * 注册 bean 定义
     * @param beanName bean名称
     * @param beanDefinition bean 定义
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 删除 bean 的定义
     *
     * @param beanName bean 的名称
     * @return 被删除的 bean 定义
     * @throws NoSuchBeanDefinitionException 找不到该 bean 定义
     */
    BeanDefinition removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    /**
     * 获取 bean 定义
     *
     * @param beanName bean 名称
     * @return 指定名称的 bean 定义
     * @throws NoSuchBeanDefinitionException 找不到该 bean 定义
     */
    BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

    /**
     * 判断该 bean 定义是否存在
     *
     * @param beanName bean 的名称
     * @return 如果存在，则返回 true
     */
    boolean containsBeanDefinition(String beanName);

}
