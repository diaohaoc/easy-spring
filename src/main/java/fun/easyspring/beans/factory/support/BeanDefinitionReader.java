package fun.easyspring.beans.factory.support;

import fun.easyspring.beans.BeansException;
import fun.easyspring.core.io.Resource;
import fun.easyspring.core.io.ResourceLoader;

/**
 * Create by DiaoHao on 2021/8/12 14:00
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinition(Resource resource) throws BeansException;

    void loadBeanDefinition(Resource... resources) throws BeansException;

    void loadBeanDefinition(String location) throws BeansException;

    void loadBeanDefinition(String[] locations) throws BeansException;
}
