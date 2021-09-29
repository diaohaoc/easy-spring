package test.bean;

import fun.easyspring.beans.factory.annotation.Autowired;
import fun.easyspring.stereotype.Component;

/**
 * Create by DiaoHao on 2021/8/30 15:59
 */
@Component
public class AnotherService {
    @Autowired
    private UserService userService;

    public String getUserService() {
        System.out.println("获取userService");
        return "123";
    }

//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }

}
