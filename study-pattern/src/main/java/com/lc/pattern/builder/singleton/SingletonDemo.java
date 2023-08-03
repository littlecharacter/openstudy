package com.lc.pattern.builder.singleton;

import java.lang.Runtime;

public class SingletonDemo {

	public static void main(String[] args) {
		System.out.println("Runtime...");
		System.out.println(Runtime.getRuntime());
		System.out.println(Runtime.getRuntime());
		System.out.println("Toolkit...");
		System.out.println(Toolkit.getDefaultToolkit());
		System.out.println(Toolkit.getDefaultToolkit());
		System.out.println("ToolkitUp.class...");
		System.out.println(ToolkitUp.getDefaultToolkit());
		System.out.println(ToolkitUp.getDefaultToolkit());
		System.out.println("SingletonByStaticClass...");
		System.out.println(SingletonByStaticClass.getInstance());
		System.out.println(SingletonByStaticClass.getInstance());

	}

	/**
	 * 静态内部类实现单例
	 * @author LittleRW
	 */
	private static class SingletonByStaticClass{
		//私有化其空构造方法，这点很重要，不然可以new的！！！
		private SingletonByStaticClass(){}

		private static class SingletonByStaticClassInstance{
			private static SingletonByStaticClass sbsc = new SingletonByStaticClass();
		}

		public static SingletonByStaticClass getInstance(){
			return SingletonByStaticClassInstance.sbsc;
		}
	}
}


