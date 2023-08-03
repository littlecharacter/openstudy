package com.lc.pattern.builder.singleton;

/**
 * java.lang.Runtime
 * @author LittleCharacter
 */
public class Runtime {
	//饿汉式
	private static Runtime currentRuntime = new Runtime();

	//私有化其空构造方法，这点很重要，不然可以new的！！！
	private Runtime() {}

	public static Runtime getRuntime() {
		return currentRuntime;
	}
}
