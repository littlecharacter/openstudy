package com.lc.pattern.structure.bridge;

/**
 * Abstaction角色
 * @author LittleCharacter
 */
public abstract class Road {
	Vehicle vehicle;

	public Road(Vehicle vehicle) {
		super();
		this.vehicle = vehicle;
	}

	public abstract void status();
}
