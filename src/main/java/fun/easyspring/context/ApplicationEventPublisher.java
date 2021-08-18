package fun.easyspring.context;

/**
 * Create by DiaoHao on 2021/8/17 17:17
 */
public interface ApplicationEventPublisher {
    void publishEvent(ApplicationEvent event);
}
