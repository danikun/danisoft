package org.danisoft.model;

/**
 * Enumerator to define the available contact types.
 * 
 * @author dgarcia
 *
 */
public enum ContactType {
	Person	("P", "Person"),
	Company	("C", "Company"),
	Family	("F", "Family");
	
	private String code;
	private String displayName;

	private ContactType(String code, String displayName) {
		this.setCode(code);
		this.setDisplayName(displayName);
	}

	public String getCode() {
		return code;
	}

	private void setCode(String code) {
		this.code = code;
	}

	public String getDisplayName() {
		return displayName;
	}

	private void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
