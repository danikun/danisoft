package org.danisoft.model;

public enum Period {
	YEARLY("Y"), 
	MONTHLY("M"), 
	WEEKLY("W"), 
	DAILY("D");

	private String code;

	private Period(String code) {
		this.code = code;
	}
	
	public static Period periodByCode(String period) {
		switch (period) {
		case "Y" :
			return YEARLY;
		case "M" :
			return MONTHLY;
		case "W" :
			return WEEKLY;
		case "D" :
			return DAILY;
		default:	
			return null;
		}
	}

	public String getCode() {
		return code;
	}

}
