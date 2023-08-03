package com.lc.pattern.builder.factory.abstractfactory;

public class LinuxButton implements Button{
	@Override
	public void press() {
		System.out.println("LinuxButton pressed...");		
	}
}
