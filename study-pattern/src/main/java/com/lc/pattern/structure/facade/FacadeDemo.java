package com.lc.pattern.structure.facade;

/**
 * 主类，客户端
 * @author LittleCharacter
 */
public class FacadeDemo {
	public static void main(String[] args) {
		Logger logger = new Logger();
		logger.info();
		System.out.println("-----------------------");
		logger.error();
	}
}
