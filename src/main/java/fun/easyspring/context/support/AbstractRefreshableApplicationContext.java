package fun.easyspring.context.support;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.config.ConfigurableListableBeanFactory;
import fun.easyspring.beans.factory.support.DefaultListableBeanFactory;

/**
 * Create by DiaoHao on 2021/8/13 14:29
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {
    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = this.createBeanFactory();
        loadBeanDefinition(beanFactory);
        this.beanFactory = beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinition(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory obtainBeanFactory() {
        return this.beanFactory;
    }
}
