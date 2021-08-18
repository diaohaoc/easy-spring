package fun.easyspring.context;

import java.util.EventListener;

/**
 * Create by DiaoHao on 2021/8/17 15:49
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {
    void onApplicationEvent(E event);
}
