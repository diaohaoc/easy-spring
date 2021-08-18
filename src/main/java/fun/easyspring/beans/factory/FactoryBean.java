package fun.easyspring.beans.factory;

/**
 * Create by DiaoHao on 2021/8/16 14:23
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }
}
