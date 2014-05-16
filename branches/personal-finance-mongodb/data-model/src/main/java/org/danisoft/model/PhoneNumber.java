package org.danisoft.model;


/**
 * Data object to represent a phone number.
 * 
 * @author Daniel García
 *
 */
public class PhoneNumber {
	
	/**
	 * Default Constructor.
	 */
	public PhoneNumber() {
	}
	
	public PhoneNumber(PhoneNumberType type, String number) {
		super();
		this.type = type;
		this.number = number;
	}

	/**
	 * Type of phone number.
	 */
	private PhoneNumberType type;
	/**
	 * The actual phone number.
	 */
	private String number;
	
	/**
	 * @return the type Id
	 */
	public int getTypeId() {
		return type.getId();
	}
	/**
	 * @return the type
	 */
	public PhoneNumberType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = PhoneNumberType.fromCode(type);
	}
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return String.format("[Type: %s Number: %s]", this.type, this.number);
	}
}
