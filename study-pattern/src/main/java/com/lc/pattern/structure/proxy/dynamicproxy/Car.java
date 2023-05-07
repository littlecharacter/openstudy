package com.lc.pattern.structure.proxy.dynamicproxy;

import java.util.Random;

public class Car implements Moveable{
	@Override
	public void move() {		
		try {
			System.out.println("moving...");
			Thread.sleep(new Random().nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean setName(String name) {
		try {
			System.out.println("set name!");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int add(int x, int y) {
		int z = x + y;
		System.out.println("x + y = " + z);
		return z;
	}
}
