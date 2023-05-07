package com.lc.pattern.structure.proxy.staticproxy;

public class GenerationCar extends Car{
	public void move(){
		Long start = System.currentTimeMillis();
		super.move();
		Long end = System.currentTimeMillis();
		System.out.println("In Time:" + (end - start));
	}
}
