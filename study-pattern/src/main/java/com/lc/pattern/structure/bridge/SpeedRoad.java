package com.lc.pattern.structure.bridge;

/**
 * Refined Abstraction角色
 * @author LittleCharacter
 */
public class SpeedRoad extends Road{
	public SpeedRoad(Vehicle vehicle) {
		super(vehicle);
	}

	@Override
	public void status() {
		System.out.print("SpeedRoad路况：");
		vehicle.run();
	}
}
