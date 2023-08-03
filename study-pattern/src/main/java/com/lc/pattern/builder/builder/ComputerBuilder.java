package com.lc.pattern.builder.builder;

/**
 * 抽象Builder角色
 * @author LittleRW
 */
public interface ComputerBuilder {
	ComputerBuilder buildCpu();
	ComputerBuilder buildMemory();
	ComputerBuilder buildVideo();

	Computer build();
}
