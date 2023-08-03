package com.lc.pattern.builder.builder;

public class BuilderDemo {
	public static void main(String[] args) {
	    // 1.正规的方式
		ComputerDirector cd = new ComputerDirector(new LenovoComputerBuilder());
		Computer computer = cd.buildComputer();
		
		System.out.println(computer.getCpu().getName());
		System.out.println(computer.getMemory().getName());
		System.out.println(computer.getVideo().getName());

		// 2.简单常用的方式（通常复杂的对象属性都是不变的，如果构建过程不复杂可以省略Director）
        computer = new MacComputerBuilder().buildCpu().buildMemory().buildVideo().build();
        System.out.println(computer.getCpu().getName());
        System.out.println(computer.getMemory().getName());
        System.out.println(computer.getVideo().getName());

        // 3.参见《Effective Java》第2条
	}
}
