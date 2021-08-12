package test.bean;

/**
 * Create by DiaoHao on 2021/7/17 22:27
 */
public class UserService {

    private String name;

    private UserDao userDao;

    public void queryUserInfo(){
        System.out.println("查询用户信息: " + userDao.queryName(this.name));
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
