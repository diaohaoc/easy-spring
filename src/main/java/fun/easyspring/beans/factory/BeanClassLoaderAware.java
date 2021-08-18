package fun.easyspring.beans.factory;

/**
 * Create by DiaoHao on 2021/8/15 10:00
 */
public interface BeanClassLoaderAware extends Aware {
    void setBeanClassLoader(ClassLoader classLoader);
}
