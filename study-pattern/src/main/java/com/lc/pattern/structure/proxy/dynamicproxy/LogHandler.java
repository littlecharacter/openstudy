package com.lc.pattern.structure.proxy.dynamicproxy;

import java.lang.reflect.Method;

public class LogHandler implements InvocationHandler{
	//被代理对象
	private Object target;

	public LogHandler(Object target) {
		super();
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		//System.out.println(proxy);
		String methodName = method.getName();

		Object result = null;
		if ("move".equals(methodName)) {
			System.out.println("before move...");
			// 执行目标对象的方法
			result = method.invoke(target, args);
			System.out.println("after move...");
		} else if ("setName".equals(methodName)) {
			System.out.println("before set name...");
			// 执行目标对象的方法
			result = method.invoke(target, args);
			System.out.println("after set name...");
		} else if ("add".equals(methodName)) {
			System.out.println("before add...");
			// 执行目标对象的方法
			result = method.invoke(target, args);
			System.out.println("after add...");
		}

		return result;
	}
}
