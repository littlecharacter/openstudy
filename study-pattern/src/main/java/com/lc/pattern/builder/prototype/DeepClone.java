package com.lc.pattern.builder.prototype;

import java.sql.Date;

/**
 * 具体Prototype角色
 * @author LittleRW
 */
public class DeepClone implements Cloneable{//Cloneable��Prototype��ɫ
	private String name;
	private Date birthday;	
	
	public DeepClone(String name, Date birthday) {
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
		return "DeepClone [name=" + name + ", birthday=" + birthday + "]";
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Object o = super.clone();
		DeepClone dc = (DeepClone)o;
		
		dc.birthday = (Date) this.birthday.clone();
		
 		return o;
	}
}
