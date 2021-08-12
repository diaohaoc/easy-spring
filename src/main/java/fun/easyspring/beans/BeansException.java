package fun.easyspring.beans;

/**
 * Create by DiaoHao on 2021/7/16 14:36
 */
public class BeansException extends RuntimeException{

    public BeansException(String msg) {
        super(msg);
    }

    public BeansException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
