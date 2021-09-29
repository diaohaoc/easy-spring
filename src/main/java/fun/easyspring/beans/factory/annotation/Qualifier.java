package fun.easyspring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * Create by DiaoHao on 2021/9/29 9:50
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Qualifier {
    String value() default "";
}
