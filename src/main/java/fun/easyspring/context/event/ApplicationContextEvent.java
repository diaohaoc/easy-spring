package fun.easyspring.context.event;

import fun.easyspring.context.ApplicationEvent;

/**
 * Create by DiaoHao on 2021/8/17 15:28
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationEvent getApplicationContext() {
        return (ApplicationContextEvent)this.getSource();
    }
}
