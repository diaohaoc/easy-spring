package fun.easyspring.aop.aop;

/**
 * Create by DiaoHao on 2021/8/26 9:41
 */
public interface ClassFilter {
    boolean matches(Class<?> clazz);
}
