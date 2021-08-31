package test.bean;

import fun.easyspring.beans.BeansException;
import fun.easyspring.beans.factory.*;
import fun.easyspring.context.ApplicationContext;

/**
 * Create by DiaoHao on 2021/7/17 22:27
 */
public class UserService implements IUserService {

    private String name;

    private String company;

    private String location;

    private IUserDao userDao;

//    private AnotherService anotherService;

    public void queryUserInfo(){
        System.out.println("查询用户信息: ");
        System.out.println(this.toString());
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

//    public AnotherService getAnotherService() {
//        System.out.println("获取anotherService");
//        return anotherService;
//    }
//
//    public void setAnotherService(AnotherService anotherService) {
//        this.anotherService = anotherService;
//    }

}
