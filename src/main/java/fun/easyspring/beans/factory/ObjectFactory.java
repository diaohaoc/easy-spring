package fun.easyspring.beans.factory;

import fun.easyspring.beans.BeansException;

/**
 * Create by DiaoHao on 2021/8/30 15:03
 */
@FunctionalInterface
public interface ObjectFactory<T> {
    T getObject() throws BeansException;
}
