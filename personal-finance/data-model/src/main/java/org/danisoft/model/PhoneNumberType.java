package org.danisoft.model;

/**
 * Enumerator of the different phone number types.
 * 
 * @author Daniel Garcia
 *
 */
public enum PhoneNumberType {

	Mobile("C", "Mobile"),
	Home("H", "Home" ),
	Company("E", "Company");
	
	private String code;
	private String displayName;
	
	private PhoneNumberType(String code, String displayName) {
		this.code = code;
		this.displayName = displayName;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
}
