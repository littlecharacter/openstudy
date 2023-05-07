package com.lc.pattern.structure.facade;

/**
 * 子系统之子系统模块
 * @author LittleCharacter
 */
public class RecordInfo extends Record{
	ReadProperty rp;
	
	public RecordInfo(ReadProperty rp) {
		super();
		this.rp = rp;
	}

	@Override
	public void info(){
		rp.read();
		System.out.println("record log with info...");
	};
}
