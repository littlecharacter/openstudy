package com.lc.pattern.structure.proxy.staticproxy;

public class MoveableTimeProxy implements Moveable{
	Moveable m;

	public MoveableTimeProxy(Moveable m) {
		super();
		this.m = m;
	}

	@Override
	public void move() {
		Long start = System.currentTimeMillis();
		System.out.println("Start Time:" + start);
		m.move();
		Long end = System.currentTimeMillis();
		System.out.println("End Time:" + end);
	}	
}
