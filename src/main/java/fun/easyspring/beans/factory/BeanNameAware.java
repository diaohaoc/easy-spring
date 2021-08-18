package fun.easyspring.beans.factory;

/**
 * Create by DiaoHao on 2021/8/15 9:59
 */
public interface BeanNameAware extends Aware {
    void setBeanName(String beanName);
}
