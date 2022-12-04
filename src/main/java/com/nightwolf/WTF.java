package com.nightwolf;
public class WTF extends RuntimeException {
	public WTF() {
		super("What the fuck is wrong with you?");
	}

	public static WTF wtf() {
		return new WTF();
	}

}
