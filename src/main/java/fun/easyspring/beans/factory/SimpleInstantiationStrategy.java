package fun.easyspring.beans.factory;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 基于 JDK 实现 bean 的实例化
 *
 * Create by DiaoHao on 2021/8/11 17:40
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiation(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object... args) throws BeansException {
        Class<?> clz = beanDefinition.getBeanClass();
        try {
            if (null != ctor) {
                return clz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                return clz.getDeclaredConstructor().newInstance();
            }
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new BeansException("Failed to instantiation [" + clz.getName() + "]", e);
        }
    }
}
