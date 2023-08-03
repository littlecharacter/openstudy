package com.lc.pattern.builder.factory.simplefactory;

public class ComputerFactory {
	public static LenovoComputer produceLenovoComputer() {
		return new LenovoComputer();
	}
	
	public static MacComputer produceMacComputer() {
		return new MacComputer();
	}
}
