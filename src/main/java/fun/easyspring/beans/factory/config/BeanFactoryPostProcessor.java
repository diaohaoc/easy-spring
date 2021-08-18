package fun.easyspring.beans.factory.config;

import fun.easyspring.beans.BeansException;

/**
 * Create by DiaoHao on 2021/7/17 19:57
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有 beanDefinition 加载完成后，实例化 bean 之前，提供修改 BeanDefinition 的机制
     *
     * @param beanFactory      实际的 bean 容器
     * @throws BeansException
     */
    void postProcessorBeforeInitialization(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
