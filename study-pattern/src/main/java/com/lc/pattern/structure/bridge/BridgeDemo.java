package com.lc.pattern.structure.bridge;

/**
 * 主类
 * @author LittleCharacter
 */
public class BridgeDemo {
	public static void main(String[] args) {		
		Vehicle vehicle = new Car();
		Road road = new SpeedRoad(vehicle);	
		road.status();
	}
}
