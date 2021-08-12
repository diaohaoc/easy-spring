package fun.easyspring.beans.factory.config;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.ConfigurableBeanFactory;
import fun.easyspring.beans.factory.HierarchicalBeanFactory;
import fun.easyspring.beans.factory.ListableBeanFactory;

/**
 *
 *
 * Create by DiaoHao on 2021/7/16 16:40
 */
public interface ConfigurableListableBeanFactory extends ConfigurableBeanFactory, ListableBeanFactory, AutowireCapableBeanFactory {

    void preInstantiationSingletons() throws BeansException;

}
