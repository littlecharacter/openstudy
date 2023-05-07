package com.lc.pattern.structure.proxy.dynamicproxy;

import java.lang.reflect.Method;

/**
 * java.lang.reflect.InvocationHandler
 * @author LittleCharacter
 */
public interface InvocationHandler {
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
