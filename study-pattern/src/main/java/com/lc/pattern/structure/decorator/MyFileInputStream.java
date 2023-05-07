package com.lc.pattern.structure.decorator;

/**
 * ConcreteComponent角色
 * @author LittleRW
 */
public class MyFileInputStream extends MyInputStream{
	@Override
	void read() {
		System.out.println("MyFileInputStream:读取文件...");
	}
}
