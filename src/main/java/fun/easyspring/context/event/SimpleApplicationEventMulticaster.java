package fun.easyspring.context.event;

import fun.easyspring.beans.factory.BeanFactory;
import fun.easyspring.context.ApplicationEvent;
import fun.easyspring.context.ApplicationListener;

/**
 * Create by DiaoHao on 2021/8/17 17:14
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
