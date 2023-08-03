package com.lc.pattern.builder.factory.simplefactory;

public class SimpleFactoryDemo {
	public static void main(String[] args) {		
		Computer computer = ComputerFactory.produceLenovoComputer();
		computer.work();
		computer = ComputerFactory.produceMacComputer();
		computer.work();
	}	
}
