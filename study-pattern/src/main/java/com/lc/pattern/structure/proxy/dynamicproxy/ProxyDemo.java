package com.lc.pattern.structure.proxy.dynamicproxy;

public class ProxyDemo {
	public static void main(String[] args) {
		Moveable car = new Car();
		InvocationHandler h = new LogHandler(car);
		Moveable m = (Moveable)Proxy.newProxyInstance(Car.class.getClassLoader(), Car.class.getInterfaces(), h);
		m.move();
		m.setName("zhangsan");
		m.add(1, 3);
	}
}
