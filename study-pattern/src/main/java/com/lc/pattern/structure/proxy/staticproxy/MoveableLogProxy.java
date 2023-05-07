package com.lc.pattern.structure.proxy.staticproxy;

public class MoveableLogProxy implements Moveable{
	Moveable m;

	public MoveableLogProxy(Moveable m) {
		super();
		this.m = m;
	}

	@Override
	public void move() {
		System.out.println("Start Move...");
		m.move();
		System.out.println("End Move...");
	}	
}
