package fun.easyspring.context.annotation;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import fun.easyspring.beans.factory.annotation.AutowireAnnotationBeanPostProcessor;
import fun.easyspring.beans.factory.config.BeanDefinition;
import fun.easyspring.beans.factory.support.BeanDefinitionRegistry;
import fun.easyspring.beans.utils.StringUtils;
import fun.easyspring.stereotype.Component;

import java.util.Set;

/**
 * Create by DiaoHao on 2021/9/27 14:42
 */
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

    private BeanDefinitionRegistry registry;

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    public void scan(String... basePackages) {
        this.doScan(basePackages);
        registry.registerBeanDefinition("fun.easyspring.beans.factory.annotation.internalAutowiredAnnotationProcessor", new BeanDefinition(AutowireAnnotationBeanPostProcessor.class));
    }

    protected void doScan(String... basePackages) {
        Assert.notEmpty(basePackages, "At least one base package must be specified");
        for (String basePackage : basePackages) {
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition beanDefinition : candidates) {
                String beanScope = resolveScope(beanDefinition);
                if (null != beanScope) {
                    beanDefinition.setScope(beanScope);
                }
                registry.registerBeanDefinition(determineBeanName(beanDefinition), beanDefinition);
            }
        }
    }

    private String resolveScope(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Scope scope = beanClass.getAnnotation(Scope.class);
        if (null != scope) {
            return scope.value();
        }
        return null;
    }

    private String determineBeanName(BeanDefinition beanDefinition) {
        Class<?> beanClass = beanDefinition.getBeanClass();
        Component component = beanClass.getAnnotation(Component.class);
        String name = component.value();
        if (StringUtils.isEmpty(name)) {
            name = StrUtil.lowerFirst(beanClass.getSimpleName());
        }
        return name;
    }
}
