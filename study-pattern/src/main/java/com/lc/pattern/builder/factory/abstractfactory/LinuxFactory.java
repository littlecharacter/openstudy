package com.lc.pattern.builder.factory.abstractfactory;

public class LinuxFactory extends SystemFactory{
	@Override
	public Button createButton() {
		return new LinuxButton();
	}

	@Override
	public Text createText() {
		// TODO Auto-generated method stub
		return new LinuxText();
	}
}
