package fun.easyspring.beans.factory.support;

import fun.easyspring.beans.factory.config.SingletonBeanRegistry;
import fun.easyspring.beans.utils.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by DiaoHao on 2021/7/16 16:11
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 真正存放单例 bean 的容器
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);



    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    public void registerSingleton(String beanName, Object singletonObject) {
        assert beanName != null : "Bean name must not be null";
        assert singletonObject != null : "Singleton object must not be null";
        synchronized (this.singletonObjects) {
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject + "] under bean name '" +
                        beanName + "': there is already object [" + oldObject + "] bound");
            } else {
                singletonObjects.put(beanName, singletonObject);
            }
        }

    }

    public boolean containsSingleton(String beanName) {
        return singletonObjects.containsKey(beanName);
    }

    public String[] getSingletonNames() {
        return StringUtils.toStringArray(singletonObjects.keySet());
    }
}
