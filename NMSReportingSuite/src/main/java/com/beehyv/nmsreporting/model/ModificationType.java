package com.beehyv.nmsreporting.model;

public enum ModificationType {
	CREATE("CREATION"),
	UPDATE("UPDATION"),
	DELETE("DELETION");
	
	private String modificationType;
	
	private ModificationType(String modificationType) {
		this.modificationType = modificationType;
	}
	
	public String getModificationType() {
		return modificationType;
	}
}
