package fun.easyspring.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.PropertyValue;
import fun.easyspring.beans.PropertyValues;
import fun.easyspring.beans.factory.BeanFactory;
import fun.easyspring.beans.factory.BeanFactoryAware;
import fun.easyspring.beans.factory.config.ConfigurableListableBeanFactory;
import fun.easyspring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import fun.easyspring.beans.utils.ClassUtils;

import java.lang.reflect.Field;

/**
 * Create by DiaoHao on 2021/9/29 9:55
 */
public class AutowireAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        clazz = ClassUtils.isCglibProxyClass(clazz) ? clazz.getSuperclass() : clazz;
        Field[] declaredFields = clazz.getDeclaredFields();

        // autowired, 目前仅支持属性注入
        for (Field declaredField : declaredFields) {
            Autowired autowired = declaredField.getAnnotation(Autowired.class);
            if (null != autowired) {
                Class<?> fieldType = declaredField.getType();
                Qualifier qualifier = declaredField.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                if (null != qualifier) {
                    String dependentName = qualifier.value();
                    dependentBean = beanFactory.getBean(dependentName, fieldType);
                } else {
                    dependentBean = beanFactory.getBean(fieldType);
                }
                pvs.addPropertyValue(new PropertyValue(declaredField.getName(), dependentBean));
            }
        }
        return pvs;
    }
}
