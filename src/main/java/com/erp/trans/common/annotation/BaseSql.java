package com.erp.trans.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseSql {
	/** 表名 */
	public String tableName() default "";
	/** resultMapName */
	public String resultName() default "";
	/** 枚举类型 */
	public enum OrderType { DESC, ASC };
	/** 排序方式 */
	public OrderType type() default OrderType.DESC;
}
