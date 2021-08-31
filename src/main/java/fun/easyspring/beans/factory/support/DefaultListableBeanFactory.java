package fun.easyspring.beans.factory.support;

import cn.hutool.core.lang.Assert;
import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.NoSuchBeanDefinitionException;
import fun.easyspring.beans.factory.config.BeanDefinition;
import fun.easyspring.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by DiaoHao on 2021/7/17 20:42
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        Assert.notNull(beanName, "Bean name must not be null");
        Assert.notNull(beanDefinition, "BeanDefinition must not be null");
        synchronized (this.beanDefinitionMap) {
            BeanDefinition oldBeanDefinition = beanDefinitionMap.get(beanName);
            if (oldBeanDefinition != null) {
                throw new IllegalStateException("Could not register beanDefinition [" + beanDefinition + "] under bean name '" +
                        beanName + "': there is already object [" + oldBeanDefinition + "] bound");
            } else {
                beanDefinitionMap.put(beanName, beanDefinition);
            }
        }
    }

    @Override
    public BeanDefinition removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        Assert.notNull(beanName, "Bean name must not be null");
        return this.beanDefinitionMap.remove(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NoSuchBeanDefinitionException("The beanName [" + beanName + "] not exist");
        }
        return beanDefinition;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        Assert.notNull(beanName, "Bean name must not be null");
        return this.beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> T createBean(Class<T> beanClass) throws BeansException {
        BeanDefinition beanDefinition = new BeanDefinition(beanClass);
        return (T) this.createBean(beanClass.getName(), beanDefinition, null);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        Map<String, T> result = new HashMap<>();
        this.beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            if (type.isAssignableFrom(beanDefinition.getBeanClass())) {
                result.put(beanName, (T) getBean(beanName));
            }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[0]);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        Set<String> beanNamesSet = getBeansOfType(type).keySet();
        return beanNamesSet.toArray(new String[0]);
    }

    @Override
    public void preInstantiationSingletons() throws BeansException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }

}
