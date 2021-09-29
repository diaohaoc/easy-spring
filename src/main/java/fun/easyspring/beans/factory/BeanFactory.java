package fun.easyspring.beans.factory;

import fun.easyspring.beans.BeansException;

public interface BeanFactory {

    String FACTORY_BEAN_PREFIX = "$";

    /**
     * 根据指定的 bean 的名称获取 bean 实例
     *
     * @param name bean的名称
     * @return 指定 bean 名称对应的 bean 实例， 如果不存在就返回 null
     * @throws BeansException 在获取 bean 实例过程中发生的异常
     */
    Object getBean(String name) throws BeansException;

    /**
     * 根据指定的 bean 的类型获取 bean 实例
     *
     * @param requiredType bean 的类型
     * @param <T> </T>指定的 bean 的类型
     * @return  bean 的实例，如果不存在就返回 null
     * @throws BeansException 在获取 bean 实例过程中发生的异常
     */
    <T> T getBean(Class<T> requiredType) throws BeansException;

    /**
     * 根据指定的 bean 的名称和类型获取 bean 实例
     *
     * @param name bean的名称
     * @param requiredType bean 的类型
     * @param <T> </T>指定的 bean 的类型
     * @return  bean 的实例，如果不存在就返回 null
     * @throws BeansException 在获取 bean 实例过程中发生的异常
     */
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

    /**
     * 根据指定的 bean 的名称和类型获取 bean 实例
     *
     * @param name bean的名称
     * @param args bean 的类型
     * @return  bean 的实例，如果不存在就返回 null
     * @throws BeansException 在获取 bean 实例过程中发生的异常
     */
    Object getBean(String name, Object... args) throws BeansException;

    /**
     * 判断是否存在指定名称的bean
     *
     * @param name bean 的名称
     * @return 是否存在该 bean 实例，如果存在就返回 true
     */
    boolean containsBean(String name);

    /**
     * 判断指定名称的 bean 是否为单例
     *
     * @param name bean 的名称
     * @return 如果 bean 为单例，则返回 true
     * @throws NoSuchBeanDefinitionException 如果没有找到该 bean 则发生 NoSuchBeanDefinitionException 异常
     */
    boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

    /**
     *判断 bean 是否为多例
     *
     * @param name bean 的名称
     * @return 如果 bean 为多例，则返回 true
     * @throws NoSuchBeanDefinitionException 如果没有找到该 bean 则发生 NoSuchBeanDefinitionException 异常
     */
    boolean isPrototype(String name) throws NoSuchBeanDefinitionException;

    /**
     * 获取 bean 的类型
     *
     * @param name bean 的名称
     * @return bean 的类型
     * @throws NoSuchBeanDefinitionException 如果没有找到该 bean 则发生 NoSuchBeanDefinitionException 异常
     */
    Class<?> getType(String name) throws NoSuchBeanDefinitionException;

}
