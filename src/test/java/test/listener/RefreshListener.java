package test.listener;

import fun.easyspring.context.ApplicationListener;
import fun.easyspring.context.event.ContextRefreshedEvent;

/**
 * Create by DiaoHao on 2021/8/17 22:35
 */
public class RefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("容器刷新事件");
    }
}
