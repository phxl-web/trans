package com.erp.trans.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 接口需要的权限，有权限该接口才能访问，无权限不能直接访问该接口
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequiredPermission {
	/** 权限代码，为空则默认无权限限制 */
	public String value() default "";
	
}