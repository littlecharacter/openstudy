package com.lc.pattern.builder.builder;

/**
 * 具体Director角色
 * @author LittleRW
 */
public class ComputerDirector{
	private ComputerBuilder computerBuilder;

	ComputerDirector(ComputerBuilder computerBuilder) {
		this.computerBuilder = computerBuilder;
	}

	// 控制Computer的构建过程
	public Computer buildComputer() {
		return computerBuilder.buildCpu().buildMemory().buildVideo().build();
	}

}
