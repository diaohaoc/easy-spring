package fun.easyspring.aop.aopalliance.aop;

/**
 * Create by DiaoHao on 2021/8/26 9:22
 */
public class AspectException extends RuntimeException {

    public AspectException(String message) {
        super(message);
    }

    public AspectException(String message, Throwable cause) {
        super(message, cause);
    }
}
