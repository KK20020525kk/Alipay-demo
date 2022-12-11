package com.xxxx.crm.annoation;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 定义方法需要对应的资源权限码
 *
 */
public @interface RequiredPermission {
    //授权码
    String code() default "";
}
