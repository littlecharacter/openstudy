package com.lc.pattern.structure.decorator;

/**
 * Decorator角色
 * @author LittleRW
 */
public class MyFilterInputStream extends MyInputStream{
	protected MyInputStream is;

	public MyFilterInputStream(MyInputStream is) {
		super();
		this.is = is;
	}

	@Override
	void read() {
		System.out.println("交给子类覆盖实现！");
	}
}
