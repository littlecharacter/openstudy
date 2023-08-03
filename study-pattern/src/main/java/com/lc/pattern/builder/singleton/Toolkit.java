package com.lc.pattern.builder.singleton;

/**
 * java.awt.Toolkit
 * @author LittleCharacter
 * 注：JDK中Toolkit是abstract，是用反射获取其子类实例
 */
public class Toolkit {
	//懒汉式
	private static Toolkit toolkit;

	//私有化其空构造方法，这点很重要，不然可以new的！！！
	private Toolkit() {}

	public static synchronized Toolkit getDefaultToolkit() {
		if (toolkit == null) {
			toolkit = new Toolkit();//JDK中Toolkit是abstract，是用反射获取其子类实例
		}
		return toolkit;
	}
}
