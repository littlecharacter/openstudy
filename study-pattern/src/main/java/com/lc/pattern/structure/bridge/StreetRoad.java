package com.lc.pattern.structure.bridge;

/**
 * Refined Abstraction角色
 * @author LittleCharacter
 */
public class StreetRoad extends Road{
	public StreetRoad(Vehicle vehicle) {
		super(vehicle);
	}

	@Override
	public void status() {
		System.out.print("StreetRoad路况：");
		vehicle.run();
	}
}
