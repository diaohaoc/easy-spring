package fun.easyspring.beans.factory.support;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.DisposableBean;
import fun.easyspring.beans.factory.config.SingletonBeanRegistry;
import fun.easyspring.beans.utils.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by DiaoHao on 2021/7/16 16:11
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 真正存放单例 bean 的容器
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    private final Map<String, DisposableBean> disposableBeanMap = new LinkedHashMap<>();


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

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        this.disposableBeanMap.put(beanName, bean);
    }

    public void destroySingletons() {
        Set<String> keySet = this.disposableBeanMap.keySet();
        String[] disposableNames = keySet.toArray(new String[0]);
        // 逆序遍历是为了与 bean 的注册顺序相反，bean 的创建和销毁是对称的
        for (int i = disposableNames.length - 1; i >= 0; i--) {
            String disposableName = disposableNames[i];
            DisposableBean disposableBean = disposableBeanMap.remove(disposableName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean [" + disposableName + "] throw an Exception", e);
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
