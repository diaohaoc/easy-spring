package fun.easyspring.context;

import fun.easyspring.beans.BeansException;

/**
 * Create by DiaoHao on 2021/8/13 13:56
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器，完成容器的各项配置
     *
     * @throws BeansException
     */
    void refresh() throws BeansException;

    void registerShutdownHook();

    void close();


}
