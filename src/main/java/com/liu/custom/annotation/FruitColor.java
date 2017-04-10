package com.liu.custom.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface FruitColor {

	/**
	 * 颜色枚举 
	 * @author liuzhihong
	 *
	 */
	public enum Color{RED,GREEN,BLUE};
	
	/**
	 * 颜色属性
	 * @return
	 */
	Color fruitColor() default Color.RED;
}
