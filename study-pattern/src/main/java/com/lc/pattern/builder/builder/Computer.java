package com.lc.pattern.builder.builder;

/**
 * Product角色（通常是不可变对象）
 * @author LittleRW
 */
public class Computer {
	private final Cpu cpu;
	private final Memory memory;
	private final Video video;

    public Computer(Cpu cpu, Memory memory, Video video) {
        this.cpu = cpu;
        this.memory = memory;
        this.video = video;
    }

    public Cpu getCpu() {
        return cpu;
    }

    public Memory getMemory() {
        return memory;
    }

    public Video getVideo() {
        return video;
    }

    static class Cpu{
		private String name;

		public Cpu(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	static class Memory{
		private String name;

		public Memory(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	static class Video{
		private String name;

		public Video(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}

