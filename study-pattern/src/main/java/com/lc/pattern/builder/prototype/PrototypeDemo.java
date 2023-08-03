package com.lc.pattern.builder.prototype;

import java.sql.Date;

/**
 * Client角色
 * @author LittleRW
 */
public class PrototypeDemo {
	public static void main(String[] args) throws CloneNotSupportedException {
		Date birthday = new Date(7656435234L);

		ShallowClone sc = new ShallowClone("莉莉", birthday);
		ShallowClone sClone = (ShallowClone)sc.clone();

		DeepClone dc = new DeepClone("莉莉", birthday);
		DeepClone dClone = (DeepClone)dc.clone();

		sClone.setName("多莉");
		dClone.setName("多莉");

		birthday.setTime(8656435234L);

		System.out.println(sc);
		System.out.println(sClone);
		System.out.println(dc);
		System.out.println(dClone);
	}
}
