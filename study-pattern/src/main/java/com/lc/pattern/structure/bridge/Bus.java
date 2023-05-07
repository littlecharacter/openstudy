package com.lc.pattern.structure.bridge;

public class Bus implements Vehicle{
	@Override
	public void run() {
		System.out.println("Bus run...");
	}
}
