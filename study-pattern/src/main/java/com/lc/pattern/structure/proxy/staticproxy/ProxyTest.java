package com.lc.pattern.structure.proxy.staticproxy;

import java.lang.reflect.Proxy;

public class ProxyTest {
	public static void main(String[] args) {
		Moveable car = new Car();
		Moveable mlp = new MoveableLogProxy(car);
		Moveable mtp = new MoveableTimeProxy(mlp);
		Long start = System.currentTimeMillis();
		mtp.move();
		Long end = System.currentTimeMillis();
		//这样写还包括JDK为move方法准备运行的时间
		System.out.println("Time:" + (end - start));
	}
}
