package test.postprocessor;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.config.BeanPostProcessor;
import test.bean.UserService;

/**
 * Create by DiaoHao on 2021/8/13 16:23
 */
public class TestBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("该为：山东");
        }
        return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
