package com.liu.custom.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 水果供应商
 * @author liuzhihong
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface FruitProvider {

	/**供应商ID*/
	public int id() default -1;
	
	/**供应商名称*/
	String name() default "";
	
	/**供应商地址*/
	String address() default "";
	
}
