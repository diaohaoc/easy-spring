package fun.easyspring.beans.factory;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 动态代理实现的实例化策略
 *
 * Create by DiaoHao on 2021/8/11 17:34
 */
public interface InstantiationStrategy {

    Object instantiation(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object... args) throws BeansException;
}
