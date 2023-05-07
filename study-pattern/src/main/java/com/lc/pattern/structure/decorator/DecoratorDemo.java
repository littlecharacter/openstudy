package com.lc.pattern.structure.decorator;

/**
 * Client角色
 * @author LittleRW
 */
public class DecoratorDemo {
	public static void main(String[] args) {
		MyBufferedInputStream bis = new MyBufferedInputStream(new MyFileInputStream());
		bis.read();
	}	
}
