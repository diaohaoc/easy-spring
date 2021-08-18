package fun.easyspring.context.event;

import fun.easyspring.context.ApplicationEvent;
import fun.easyspring.context.ApplicationListener;

/**
 * Create by DiaoHao on 2021/8/17 15:47
 */
public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    void multicastEvent(ApplicationEvent event);
}
