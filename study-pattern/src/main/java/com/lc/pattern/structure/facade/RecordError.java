package com.lc.pattern.structure.facade;

/**
 * 子系统之子系统模块
 * @author LittleCharacter
 */
public class RecordError extends Record{
	ReadProperty rp;
	ExceptionAnalyse ea;
	
	public RecordError(ReadProperty rp, ExceptionAnalyse ea) {
		super();
		this.rp = rp;
		this.ea = ea;
	}

	@Override
	public void error() {
		rp.read();
		ea.analyse();
		System.out.println("record log with error level...");
	}
}
