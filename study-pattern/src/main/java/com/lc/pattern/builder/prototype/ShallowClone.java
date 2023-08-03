package com.lc.pattern.builder.prototype;

import java.sql.Date;

/**
 * 具体Prototype角色
 * @author LittleRW
 */
public class ShallowClone implements Cloneable{//Cloneable：Prototype角色
	private String name;
	private Date birthday;

	public ShallowClone(String name, Date birthday) {
		super();
		this.name = name;
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "ShallowClone [name=" + name + ", birthday=" + birthday + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

