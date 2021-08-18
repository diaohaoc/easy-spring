package test.bean;

import fun.easyspring.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Create by DiaoHao on 2021/8/16 14:54
 */
public class ProxyUserDao implements FactoryBean<IUserDao> {


    @Override
    public IUserDao getObject() throws Exception {
        InvocationHandler handler = (proxy, method, args) -> {
            Map<String, String> data = new HashMap<>();
            data.put("10001", "张三");
            data.put("10002", "李四");
            data.put("10003", "王五");
            return "你被代理了 " + method.getName() + ": " + data.get(args[0].toString());
        };
        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{IUserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }
}
