package org.danisoft.model;

/**
 * Enumerator to define the available contact types.
 * 
 * @author Daniel Garcia
 * 
 */
public enum ContactType {
	Person(1, "P", "Person"), Company(2, "C", "Company"), Family(3, "F", "Family");
	
	private int id;
	private String code;
	private String displayName;

	private ContactType(int id, String code, String displayName) {
		setId(id);
		this.setCode(code);
		this.setDisplayName(displayName);
	}

	private void setId(int id) {
		this.id = id;
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

	public static ContactType contactTypeByCode(String code) {
		switch (code) {
		case "P":
			return Person;
		case "C":
			return Company;
		case "F":
			return Family;
		default:
			return null;
		}
	}

	public int getId() {
		return id;
	}
}
