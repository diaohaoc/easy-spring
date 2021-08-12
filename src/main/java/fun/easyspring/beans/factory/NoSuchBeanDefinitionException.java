package fun.easyspring.beans.factory;

import fun.easyspring.beans.BeansException;

/**
 * Create by DiaoHao on 2021/7/16 15:03
 */
public class NoSuchBeanDefinitionException extends BeansException {


    public NoSuchBeanDefinitionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public NoSuchBeanDefinitionException(String name) {
        super("No bean named '" + name + "' available");
    }
}
