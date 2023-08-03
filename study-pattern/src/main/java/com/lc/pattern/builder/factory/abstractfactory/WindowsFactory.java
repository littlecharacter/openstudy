package com.lc.pattern.builder.factory.abstractfactory;

public class WindowsFactory extends SystemFactory{
	@Override
	public Button createButton() {
		return new WindowsButton();
	}

	@Override
	public Text createText() {
		return new WindowsText();
	}
}
