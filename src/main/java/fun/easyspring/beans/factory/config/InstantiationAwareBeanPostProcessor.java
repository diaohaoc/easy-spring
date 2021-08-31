package fun.easyspring.beans.factory.config;

import cn.hutool.core.bean.BeanException;
import fun.easyspring.beans.BeansException;

/**
 * Create by DiaoHao on 2021/8/26 16:49
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
        return null;
    }

    default boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }
}
