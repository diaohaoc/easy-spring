package fun.easyspring.aop.aop.framework;

/**
 * Create by DiaoHao on 2021/8/27 15:52
 */
public class AopConfigException extends RuntimeException {
    public AopConfigException(String msg) {
        super(msg);
    }

    public AopConfigException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
