package com.lc.pattern.structure.adapter;

/**
 * Adapter角色
 * @author LittleRW
 */
public class MyInputStreamReader extends MyReader{
	private MyInputStream is;

	public MyInputStreamReader(MyInputStream is) {
		super();
		this.is = is;
	}

	@Override
	public void readLine() {
		System.out.print("MyInputStreamReader:一行一行读 -> ");
		is.read();
	}
}

