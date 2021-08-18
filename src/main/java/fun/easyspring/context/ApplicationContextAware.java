package fun.easyspring.context;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.Aware;

/**
 * Create by DiaoHao on 2021/8/15 10:02
 */
public interface ApplicationContextAware extends Aware {
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
