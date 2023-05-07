package com.lc.pattern.structure.proxy.dynamicproxy;
//这是生成的代理类源码, package com.lc.dp.proxy.dynamicproxy;是没有的
public class $Proxy0 implements Moveable
{
	LogHandler h;
	 public $Proxy0(LogHandler h) {
		this.h = h;
	}

	@Override
	public int add(int arg0,int arg1) {
		Object obj = null;
		try {
			obj = h.invoke(this, Moveable.class.getMethod("add",int.class,int.class), new Object[]{arg0,arg1});
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return (Integer)obj;
	}
	@Override
	public boolean setName(String arg0) {
		Object obj = null;
		try {
			obj = h.invoke(this, Moveable.class.getMethod("setName",String.class), new Object[]{arg0});
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return (Boolean)obj;
	}
	@Override
	public void move() {
		Object obj = null;
		try {
			obj = h.invoke(this, Moveable.class.getMethod("move",new Class<?>[]{}), new Object[]{});
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return;
	}
}