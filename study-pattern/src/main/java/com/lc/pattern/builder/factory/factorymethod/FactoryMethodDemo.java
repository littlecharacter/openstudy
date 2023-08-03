package com.lc.pattern.builder.factory.factorymethod;

public class FactoryMethodDemo {
	public static void main(String[] args) throws Exception {
		BeanFactory factory = new ClassPathXmlApplicationContext("com/lc/dp/factory/factorymethod/applicationContext.xml");
		ConcreteProduct cp = (ConcreteProduct)factory.getBean("v");
		cp.print();
	}
}
