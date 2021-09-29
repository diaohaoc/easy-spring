package fun.easyspring.context.support;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.NoSuchBeanDefinitionException;
import fun.easyspring.beans.factory.config.BeanFactoryPostProcessor;
import fun.easyspring.beans.factory.config.BeanPostProcessor;
import fun.easyspring.beans.factory.config.ConfigurableListableBeanFactory;
import fun.easyspring.context.ApplicationEvent;
import fun.easyspring.context.ApplicationListener;
import fun.easyspring.context.ConfigurableApplicationContext;
import fun.easyspring.context.event.ApplicationEventMulticaster;
import fun.easyspring.context.event.ContextClosedEvent;
import fun.easyspring.context.event.ContextRefreshedEvent;
import fun.easyspring.context.event.SimpleApplicationEventMulticaster;
import fun.easyspring.core.io.DefaultResourceLoader;

import java.util.Collection;
import java.util.Map;

/**
 * Create by DiaoHao on 2021/8/13 13:58
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";


    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public void refresh() throws BeansException {

        // 1. 创建 beanFactory 并加载 BeanDefinition
        refreshBeanFactory();

        // 2. 获取 beanFactory
        ConfigurableListableBeanFactory beanFactory = this.obtainBeanFactory();

        // 3. 向容器中添加一些组件
        prepareBeanFactory(beanFactory);

        // 4. 在 bean 初始化之前，执行 beanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5. 将 beanPostProcessor 注册到 beanFactory
        registerBeanPostProcessors(beanFactory);

        // 6. 初始化事件发布者
        initApplicationEventMulticaster();

        // 7. 注册监听器
        registerListeners();

        // 8. 提前实例化单例 bean 对象
        beanFactory.preInstantiationSingletons();

        // 9. 发布容器刷新完成事件
        finishRefresh();

    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory obtainBeanFactory();

    private void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
    }

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessorMap.values()) {
            postProcessor.postProcessorBeforeInitialization(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor postProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(postProcessor);
        }
    }

    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = this.obtainBeanFactory();
        ApplicationEventMulticaster applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        this.applicationEventMulticaster = applicationEventMulticaster;
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh() {
        this.publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        this.publishEvent(new ContextClosedEvent(this));

        this.obtainBeanFactory().destroySingletons();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return this.obtainBeanFactory().getBean(name);
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeansException {
        return this.obtainBeanFactory().getBean(requiredType);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return this.obtainBeanFactory().getBean(name, requiredType);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return this.obtainBeanFactory().getBean(name, args);
    }

    @Override
    public boolean containsBean(String name) {
        return this.obtainBeanFactory().containsBean(name);
    }

    @Override
    public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return this.obtainBeanFactory().isSingleton(name);
    }

    @Override
    public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return this.obtainBeanFactory().isPrototype(name);
    }

    @Override
    public Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return this.obtainBeanFactory().getType(name);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return this.obtainBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return this.obtainBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return this.obtainBeanFactory().getBeanNamesForType(type);
    }
}
