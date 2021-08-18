package fun.easyspring.context.support;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.config.BeanPostProcessor;
import fun.easyspring.context.ApplicationContext;
import fun.easyspring.context.ApplicationContextAware;

/**
 * Create by DiaoHao on 2021/8/15 10:08
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {
    private ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessorBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }
}
