package com.lc.pattern.structure.adapter;

/**
 * Client角色
 * @author LittleRW
 */
public class AdapterDemo {
	public static void main(String[] args) {
		MyReader mr = new MyInputStreamReader(new MyInputStream());
		mr.readLine();
	}
}
