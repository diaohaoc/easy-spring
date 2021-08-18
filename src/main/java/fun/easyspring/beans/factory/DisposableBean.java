package fun.easyspring.beans.factory;

/**
 * Create by DiaoHao on 2021/8/14 15:13
 */
public interface DisposableBean {

    /**
     * bean 在销毁时调用
     *
     * @throws Exception
     */
    void destroy() throws Exception;
}
