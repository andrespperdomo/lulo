package com.lulo.lulobank.enums;

public enum ViolationsEnum {
	ACCOUNT_ALREADY_INIT("account-already-initialized"),
	ACCOUNT_NOT_INIT("account-not-initialized"),
	CARD_NOT_ACTIVE("Card-not-active"),
	INSUFFICIENT_LIMIT("insufficiente-limit"),
	HIGH_FREQUENCY_SMALL_INTERVAL("high-frequency-small-interval"),
	DOUBLE_TRANSACTION("doubled-transaction");
	
	private String descripcion;
	
	ViolationsEnum(String descripcion){
		this.descripcion=descripcion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
