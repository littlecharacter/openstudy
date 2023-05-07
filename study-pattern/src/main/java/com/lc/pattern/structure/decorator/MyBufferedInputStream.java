package com.lc.pattern.structure.decorator;

/**
 * ConcreteDecorator角色
 * @author LittleRW
 */
public class MyBufferedInputStream extends MyFilterInputStream{
	public MyBufferedInputStream(MyInputStream is) {
		super(is);
	}

	@Override
	void read() {
		System.out.println("MyBufferedInputStream:先创建一个缓冲区...");
		super.is.read();
	}
}
