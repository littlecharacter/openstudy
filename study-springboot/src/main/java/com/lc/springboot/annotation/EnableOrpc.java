package com.lc.springboot.annotation;

import java.lang.annotation.*;

/**
 * @author gujixian
 * @since 2023/10/14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableOrpc {
    String path() default "";
}
