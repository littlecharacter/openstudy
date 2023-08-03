package com.lc.pattern.builder.builder;

/**
 * 具体Builder角色
 * @author LittleRW
 */
public class MacComputerBuilder implements ComputerBuilder{

	private Computer.Cpu cpu;
	private Computer.Memory memory;
	private Computer.Video video;

	@Override
	public ComputerBuilder buildCpu() {
		this.cpu = new Computer.Cpu("Mac cpu...");
		return this;
	}

	@Override
	public ComputerBuilder buildMemory() {
		this.memory = new Computer.Memory("Mac memory...");
		return this;
	}

	@Override
	public ComputerBuilder buildVideo() {
		this.video = new Computer.Video("Mac video...");
		return this;
	}

	@Override
	public Computer build() {
		return new Computer(this.cpu, this.memory, this.video);
	}

}

