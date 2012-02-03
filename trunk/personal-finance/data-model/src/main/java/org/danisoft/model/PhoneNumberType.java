package org.danisoft.model;

/**
 * Enumerator of the different phone number types.
 * 
 * @author Daniel Garcia
 *
 */
public enum PhoneNumberType {

	Mobile(1, "M", "Mobile"),
	Home(2, "H", "Home" ),
	Company(3, "C", "Company");
	
	private int id;
	private String code;
	private String displayName;
	
	private PhoneNumberType(int id, String code, String displayName) {
		this.id = id;
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

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public static PhoneNumberType fromCode(String type) {
		switch (type) {
		case "M":
			return Mobile;
		case "H":
			return Home;
		case "C":
			return Company;
		default:
			return null;
		}
	}
}
