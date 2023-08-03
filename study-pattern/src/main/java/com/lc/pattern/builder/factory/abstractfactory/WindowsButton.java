package com.lc.pattern.builder.factory.abstractfactory;

public class WindowsButton implements Button{
	@Override
	public void press() {
		System.out.println("WindowsButton pressed...");
	}
}
