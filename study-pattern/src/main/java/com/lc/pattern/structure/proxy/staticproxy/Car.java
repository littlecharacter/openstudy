package com.lc.pattern.structure.proxy.staticproxy;

import java.util.Random;

public class Car implements Moveable{
	@Override
	public void move() {		
		System.out.println("Car Moving...");
		try {
			Thread.sleep(new Random().nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
