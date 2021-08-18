package fun.easyspring.beans.factory;

/**
 * Create by DiaoHao on 2021/8/14 15:13
 */
public interface InitializingBean {

    /**
     * 在属性填充之后，进行初始化时调用，bean 的初始化函数
     *
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;
}
