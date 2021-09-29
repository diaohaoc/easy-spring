package fun.easyspring.stereotype;

import java.lang.annotation.*;

/**
 * Create by DiaoHao on 2021/9/27 14:34
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {
    String value() default "";
}
