package fun.easyspring.context.annotation;

import java.lang.annotation.*;

/**
 * Create by DiaoHao on 2021/9/27 14:26
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {
    String value() default "singleton";
}
