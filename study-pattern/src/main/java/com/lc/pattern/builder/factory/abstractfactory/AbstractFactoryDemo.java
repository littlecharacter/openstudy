package com.lc.pattern.builder.factory.abstractfactory;

public class AbstractFactoryDemo {
	public static void main(String[] args) {
//		SystemFactory factory = new WindowsFactory();
		//此处用反射和实现任意切换和添加
		SystemFactory factory = new LinuxFactory();//
		
		Button button = factory.createButton();
		Text text = factory.createText();
		
		button.press();
		text.show();
	}	
}
