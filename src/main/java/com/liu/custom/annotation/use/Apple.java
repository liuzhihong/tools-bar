package com.liu.custom.annotation.use;

import com.liu.custom.annotation.FruitColor;
import com.liu.custom.annotation.FruitColor.Color;
import com.liu.custom.annotation.FruitName;
import com.liu.custom.annotation.FruitProvider;

/**
 * 
 * @author liuzhihong
 *
 */
public class Apple {

	@FruitName("Apple")
	private String appleName;
	
	@FruitColor(fruitColor=Color.GREEN)
	private String appleColor;
	
	@FruitProvider(id=2,name="midea",address="顺德")
	private String appleProvider;

	public String getAppleName() {
		return appleName;
	}

	public void setAppleName(String appleName) {
		this.appleName = appleName;
	}

	public String getAppleColor() {
		return appleColor;
	}

	public void setAppleColor(String appleColor) {
		this.appleColor = appleColor;
	}

	public String getAppleProvider() {
		return appleProvider;
	}

	public void setAppleProvider(String appleProvider) {
		this.appleProvider = appleProvider;
	}
	
	public void display(){
		System.out.println("水果的名字是: "+appleName);
	}
}
