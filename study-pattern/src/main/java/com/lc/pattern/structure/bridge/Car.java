package com.lc.pattern.structure.bridge;

/**
 * Concrete Implementor角色
 * @author LittleCharacter
 */
public class Car implements Vehicle{
	@Override
	public void run() {
		System.out.println("Car Run...");	
	}
}
