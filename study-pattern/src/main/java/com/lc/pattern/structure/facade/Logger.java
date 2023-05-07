package com.lc.pattern.structure.facade;

/**
 * Facade角色
 * @author LittleCharacter
 */
public class Logger {
	ReadProperty rp = new ReadProperty();
	ExceptionAnalyse ea = new ExceptionAnalyse();
	RecordInfo ri = new RecordInfo(rp);
	RecordError re = new RecordError(rp, ea);
	
	public void info() {
		ri.info();
	}
	
	public void error() {
		re.error();
	}
}
