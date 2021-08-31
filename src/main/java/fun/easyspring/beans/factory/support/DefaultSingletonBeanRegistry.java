package fun.easyspring.beans.factory.support;

import cn.hutool.core.lang.Assert;
import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.DisposableBean;
import fun.easyspring.beans.factory.ObjectFactory;
import fun.easyspring.beans.factory.config.SingletonBeanRegistry;
import fun.easyspring.beans.utils.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Create by DiaoHao on 2021/7/16 16:11
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 真正存放单例 bean 的容器
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
    private final Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    private final Map<String, DisposableBean> disposableBeanMap = new LinkedHashMap<>();


    protected void addSingleton(String beanName, Object singletObject) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.put(beanName, singletObject);
            this.earlySingletonObjects.remove(beanName);
            this.singletonFactories.remove(beanName);
        }
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletFactory) {
        Assert.notNull(singletFactory, "Singleton factory must not be null");
        synchronized (this.singletonObjects) {
            this.singletonFactories.put(beanName, singletFactory);
            this.earlySingletonObjects.remove(beanName);
        }
    }


    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }

    public Object getSingleton(String beanName, boolean allowEarlyReference) {
        Object singletonObject = singletonObjects.get(beanName);
        if (singletonObject == null && this.isSingletonCurrentlyInCreation(beanName)) {
            synchronized (this.singletonObjects) {
                singletonObject = earlySingletonObjects.get(beanName);
                if (singletonObject == null && allowEarlyReference) {
                    ObjectFactory<?> singletonFactory = singletonFactories.get(beanName);
                    if (singletonFactory != null) {
                        singletonObject = singletonFactory.getObject();
                        earlySingletonObjects.put(beanName, singletonObject);
                        singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return singletonObject;
    }

    public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
        Assert.notNull(beanName, "Bean name must not be null");
        synchronized (this.singletonObjects) {
            Object singletonObject = singletonObjects.get(beanName);
            if (singletonObject == null) {
                this.beforeSingletonCreation(beanName);
                boolean newSingleton = false;
                try {
                    singletonObject = singletonFactory.getObject();
                    newSingleton = true;
                } finally {
                    this.afterSingletonCreation(beanName);
                }

                if (newSingleton) {
                    this.addSingleton(beanName, singletonObject);
                }

            }
            return singletonObject;
        }
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

    public boolean isSingletonCurrentlyInCreation(String beanName) {
        return this.singletonsCurrentlyInCreation.contains(beanName);
    }

    protected void beforeSingletonCreation(String beanName) {
        this.singletonsCurrentlyInCreation.add(beanName);
    }

    protected void afterSingletonCreation(String beanName) {
        this.singletonsCurrentlyInCreation.remove(beanName);
    }
}
