package fun.easyspring.beans.factory;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * 基于 Cglib 实现 bean 的实例化
 *
 * Create by DiaoHao on 2021/8/11 17:40
 */
public class CglibSubclassInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instantiation(BeanDefinition beanDefinition, String beanName, Constructor<?> ctor, Object... args) throws BeansException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if (null == ctor) {
            return enhancer.create();
        }
        return enhancer.create(ctor.getParameterTypes(), args);
    }
}
