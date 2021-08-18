package test.postprocessor;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.PropertyValue;
import fun.easyspring.beans.factory.config.BeanDefinition;
import fun.easyspring.beans.factory.config.BeanFactoryPostProcessor;
import fun.easyspring.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * Create by DiaoHao on 2021/8/13 16:22
 */
public class TestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessorBeforeInitialization(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");

        beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("company", "改为：啦啦啦"));
    }
}
