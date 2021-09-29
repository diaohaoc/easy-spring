package test.bean;


import fun.easyspring.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by DiaoHao on 2021/8/11 22:23
 */
@Component()
public class UserDao {
    private static Map<String, String> data = new HashMap<>();

    static {
        data.put("10001", "张三");
        data.put("10002", "李四");
        data.put("10003", "王五");
    }

    public void initMethod() {
        System.out.println("执行: UserDao.initMethod");
        data.put("10001", "张三");
        data.put("10002", "李四");
        data.put("10003", "王五");
    }

    public void destroyMethod() {
        System.out.println("执行: UserDao.destroyMethod");
        data.clear();
    }

    public String queryName(String id) {
        return data.get(id);
    }
}
