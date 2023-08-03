package com.lc.pattern.builder.factory.factorymethod;

/**
 * 具体的产品，在Spring中就是具体的业务类
 * @author LittleCharacter
 */
public class ConcreteProduct implements Product{
	@Override
	public void print() {
		System.out.println("一种具体的产品...");
	}
}
