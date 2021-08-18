package fun.easyspring.beans.factory;

import fun.easyspring.beans.factory.config.BeanPostProcessor;
import fun.easyspring.beans.factory.config.SingletonBeanRegistry;

/**
 *
 *
 * Create by DiaoHao on 2021/7/16 16:40
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    void setBeanClassLoader(ClassLoader beanClassLoader);

    ClassLoader getBeanClassLoader();

    /**
     * 销毁单例对象
     */
    void destroySingletons();
}
