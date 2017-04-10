package com.liu.custom.annotation.use;

import java.lang.reflect.Field;

import com.liu.custom.annotation.FruitName;

public class FruitProcessUtil {

	public static void getFruitInfo(Class<?> clazz){
		
		String strName = "水果名称为: ";
		String strColor = "水果颜色为: ";
		String strProvider = "水果供应商为: ";
		
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field fl  : fields){
			if(fl.isAnnotationPresent(FruitName.class)){
				FruitName fruitName = fl.getAnnotation(FruitName.class);
				strName = strName+fruitName.value();
				System.out.println(strName);
			}
		}
	}
}
