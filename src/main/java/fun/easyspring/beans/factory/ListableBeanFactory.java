package fun.easyspring.beans.factory;

import fun.easyspring.beans.BeansException;

import java.util.Map;

/**
 *
 *
 * Create by DiaoHao on 2021/7/16 16:50
 */
public interface ListableBeanFactory extends BeanFactory {

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    String[] getBeanDefinitionNames();

    String[] getBeanNamesForType(Class<?> type);
}
