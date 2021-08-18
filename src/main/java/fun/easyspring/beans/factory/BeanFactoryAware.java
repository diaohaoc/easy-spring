package fun.easyspring.beans.factory;

import fun.easyspring.beans.BeansException;

/**
 * Create by DiaoHao on 2021/8/15 9:59
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
