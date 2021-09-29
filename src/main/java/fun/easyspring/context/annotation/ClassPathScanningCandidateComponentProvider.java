package fun.easyspring.context.annotation;

import cn.hutool.core.util.ClassUtil;
import fun.easyspring.beans.factory.config.BeanDefinition;
import fun.easyspring.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Create by DiaoHao on 2021/9/27 14:36
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clz : classes) {
            candidates.add(new BeanDefinition(clz));
        }
        return candidates;
    }
}
