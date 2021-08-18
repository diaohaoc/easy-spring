package fun.easyspring.beans.factory.support;

import fun.easyspring.beans.factory.DisposableBean;
import fun.easyspring.beans.factory.config.BeanDefinition;
import fun.easyspring.beans.utils.StringUtils;

import java.lang.reflect.Method;

/**
 * Create by DiaoHao on 2021/8/14 15:56
 */
public class DisposableBeanAdapter implements DisposableBean {

    private Object bean;

    private String beanName;

    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        boolean isDisposableBean = bean instanceof DisposableBean;
        if (isDisposableBean) {
            ((DisposableBean)bean).destroy();
        }

        if (!StringUtils.isEmpty(destroyMethodName) && !(isDisposableBean && "destroy".equals(this.destroyMethodName))) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            destroyMethod.invoke(bean);
        }
    }
}
